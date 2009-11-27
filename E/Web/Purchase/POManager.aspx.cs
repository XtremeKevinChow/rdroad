using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Sys;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.ERP.Core;
using Magic.Security;

public partial class Purchase_POManager : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtStartDate.Value = DateTime.Now.AddMonths(-1).ToString("yyyy-MM-dd");
            this.txtEndDate.Value = DateTime.Now.ToString("yyyy-MM-dd");
            using (ISession session = new Session())
            {
                WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, magicPagerMain.PageSize, 1);

                this.drpVendor.Items.Clear();
                this.drpVendor.Items.Add(new ListItem(" ", "0"));
                IList<Vendor> vendors = session.CreateEntityQuery<Vendor>()
                    .List<Vendor>();
                foreach (Vendor v in vendors)
                    this.drpVendor.Items.Add(new ListItem(v.ShortName, v.VendorID.ToString()));

                OrderTypeDef orderTypeDef = OrderTypeDef.Retrieve(session, POHead.ORDER_TYPE);
                if (orderTypeDef != null)
                    this.hidViewUrl.Value = orderTypeDef.ViewURL;
                RestoreLastQuery(session);
            }
        }
    }

    void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            helper.SetValue(this.txtOrderNum);
            helper.SetValue(this.drpVendor);
            helper.SetValue(this.cklStatus);
            helper.SetValue(this.txtStartDate);
            helper.SetValue(this.txtEndDate);
        }
        int pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
        int pageIndex = Cast.Int(helper.Pop("pi"), 1);
        QueryAndBindData(session, pageIndex, pageSize, true);
    }

    string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.GetValue(this.txtOrderNum); //传参_方法1
        helper.GetValue(this.drpVendor); //传参_方法2
        helper.GetValue(this.cklStatus);
        helper.GetValue(this.txtStartDate);
        helper.GetValue(this.txtEndDate);
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        string orderNum = this.txtOrderNum.Text.Trim();
        int vendorId = Cast.Int(this.drpVendor.SelectedValue);
        DateTime startDate = Cast.DateTime(this.txtStartDate.Value.Trim(), new DateTime(1900, 1, 1));
        DateTime endDate = Cast.DateTime(this.txtEndDate.Value.Trim(), new DateTime(1900, 1, 1));
        IList<POStatus> status = new List<POStatus>();
        foreach (ListItem item in cklStatus.Items)
            if (item.Selected) status.Add(Cast.Enum<POStatus>(item.Value));
        ObjectQuery query = session.CreateObjectQuery(@"
select p.OrderNumber as OrderNumber,p.Status as Status,p.ApproveResult as ApproveResult,p.CurrentLineNumber as CurrentLineNumber
    ,p.PurchGroupCode as PurchGroupCode,w.Name as Name
    ,p.LocationCode as LocationCode,p.VendorID as VendorID,v.FullName as FullName
    ,p.TaxInclusiveAmt as TaxInclusiveAmt,p.TaxExclusiveAmt as TaxExclusiveAmt,p.TaxAmt as TaxAmt
    ,p.CreateTime as CreateTime,p.CreateUser as CreateUser,us.FullName as uFullName
from POHead p
inner join Vendor v on v.VendorID=p.VendorID
inner join WHLocation w on w.LocationCode=p.LocationCode
left join User us on us.UserId=p.CreateUser
WHERE p.PurchGroupCode in (select PurchGroupCode from PurchaseGroup2User where UserID=?userId)
order by p.OrderNumber desc")
            .Attach(typeof(POHead)).Attach(typeof(Vendor)).Attach(typeof(WHLocation)).Attach(typeof(User)).Attach(typeof(PurchaseGroup2User))
            .SetValue("?userId", SecuritySession.CurrentUser.UserId, EntityManager.GetPropMapping(typeof(PurchaseGroup2User), "UserID").DbTypeInfo)
            .And(Exp.In("p.Status", status))
            .SetPage(pageIndex, pageSize);
        if (!string.IsNullOrEmpty(orderNum)) query.And(Exp.Like("p.OrderNumber", "%" + orderNum + "%"));
        if (vendorId > 0) query.And(Exp.Eq("p.VendorID", vendorId));
        if (startDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("p.CreateTime", startDate));
        if (endDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("p.CreateTime", endDate.AddDays(1)));
        this.rptPO.DataSource = query.DataSet();
        this.rptPO.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this.hidReturnUrl.Value = GetReturnUrl();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            #region 删除
            bool deleted = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPO.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        HtmlInputHidden hidStatus = item.FindControl("hidStatus") as HtmlInputHidden;
                        if (chk != null && chk.Checked && !string.IsNullOrEmpty(chk.Value))
                        {
                            if (Cast.Enum<POStatus>(hidStatus.Value) == POStatus.New)
                            {
                                session.CreateEntityQuery<POLine>()
                                    .Where(Exp.Eq("OrderNumber", chk.Value))
                                    .Delete();
                                POHead.Delete(session, chk.Value);
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                        WebUtil.ShowMsg(this, "选择的采购订单已经被删除", "操作成功");
                    }
                }
                catch (Exception ex)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, ex);
                }
            }
            #endregion
        }
        else if (e.CommandName == "Complete")
        {
            #region 关闭采购订单
            bool completed = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPO.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && !string.IsNullOrEmpty(chk.Value))
                        {
                            POHead poHead = POHead.Retrieve(session, chk.Value);
                            if (poHead.Status == POStatus.Release && poHead.ApproveResult == ApproveStatus.Approve)
                            {
                                poHead.Close(session);
                                completed = true;
                            }
                        }
                    }
                    session.Commit();
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
                if (completed)
                {
                    this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                    WebUtil.ShowMsg(this, "选择的采购订单已经完成", "操作成功");
                }
            }
            #endregion
        }
        else if (e.CommandName == "Publish")
        {
            #region 发布(送签单据)
            bool published = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPO.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && !string.IsNullOrEmpty(chk.Value))
                        {
                            POHead poHead = POHead.Retrieve(session, chk.Value.Trim());
                            if (poHead.Status == POStatus.New)//为新建状态
                            {
                                poHead.Release(session);
                                published = true;
                            }
                        }
                    }
                    session.Commit();
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er);
                }
                if (published)
                {
                    this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                    WebUtil.ShowMsg(this, "选择的采购订单已经发布", "操作成功");
                }
            }
            #endregion
        }
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        //翻页事件
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize; //如果页面有2个翻页控件，则必须写上这一句(控件bug)
        using (ISession session = new Session())
        {
            QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }
    protected void rptPO_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        System.Data.DataRowView drv = e.Item.DataItem as System.Data.DataRowView;
        if (drv == null) return;
        POStatus status = Cast.Enum<POStatus>(drv["Status"]);
        if (status != POStatus.New)
        {
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = false;
        }
    }
}
