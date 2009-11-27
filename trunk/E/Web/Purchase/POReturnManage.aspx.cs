using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.ERP;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Sys;
using Magic.Web.UI;

public partial class Purchase_POReturnManage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-30).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            this.chkNew.Checked = true;
            this.chkRelease.Checked = true;
            this.chkOpen.Checked = true;

            using (ISession session = new Session())
            {
                OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, POReturnHead.ORD_TYPE_CODE);
                if (typeDef != null)
                    this.hidViewUrl.Value = typeDef.ViewURL;

                OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, POReturnHead.ORD_TYPE_CODE, (int)POReturnStatus.New);
                this.chkNew.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, POReturnHead.ORD_TYPE_CODE, (int)POReturnStatus.Release);
                this.chkRelease.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, POReturnHead.ORD_TYPE_CODE, (int)POReturnStatus.Open);
                this.chkOpen.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, POReturnHead.ORD_TYPE_CODE, (int)POReturnStatus.Close);
                this.chkClose.Text = statusDef.StatusText;

                WebUtil.SetMagicPager(magicPagerMain, this.magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, this.magicPagerMain.PageSize, 1);
                this.RestoreLastQuery(session);
            }
        }
    }

    protected void MagicPager_PageChanged(object src, Magic.Web.UI.PageChangedEventArgs e)
    {
        this.magicPagerSub.PageSize = this.magicPagerMain.PageSize;
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, e.NewPageIndex, e.Pager.PageSize, false);
        }
    }

    protected void btnQuery_Click(object sender, ImageClickEventArgs e)
    {
        using (ISession session = new Session())
        {
            this.QueryAndBindData(session, 1, magicPagerMain.PageSize, true);
        }
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        ObjectQuery query = session.CreateObjectQuery(@"
select h.OrderNumber as OrderNumber,h.Status as Status,s.StatusText as StatusText,h.ApproveResult as ApproveResult
    ,u.FullName as CreateUser,h.CreateTime as CreateTime,h.Note as Note
    ,v.ShortName as VendorName
from POReturnHead h
left join User u on h.CreateUser=u.UserId
left join OrderStatusDef s on s.OrderTypeCode=?type and h.Status=s.StatusValue
left join Vendor v on v.VendorID=h.VendorID
order by CreateTime desc")
            .Attach(typeof(POReturnHead)).Attach(typeof(User)).Attach(typeof(OrderStatusDef)).Attach(typeof(Vendor))
            .SetValue("?type", POReturnHead.ORD_TYPE_CODE, "s.OrderTypeCode")
            .SetPage(pageIndex, pageSize);
        if (this.txtOrderNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("h.OrderNumber", "%" + this.txtOrderNumber.Text.Trim() + "%"));
        DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
        DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
        if (fromDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("h.CreateTime", fromDate));
        if (toDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("h.CreateTime", toDate.AddDays(1)));
        IList<POReturnStatus> status = new List<POReturnStatus>();
        if (this.chkNew.Checked) status.Add(POReturnStatus.New);
        if (this.chkRelease.Checked) status.Add(POReturnStatus.Release);
        if (this.chkOpen.Checked) status.Add(POReturnStatus.Open);
        if (this.chkClose.Checked) status.Add(POReturnStatus.Close);
        if (status.Count > 0) query.And(Exp.In("h.Status", status));

        this.repeatControl.DataSource = query.DataSet();
        this.repeatControl.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
        this.hidReturnUrl.Value = this.GetReturnUrl();
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;

        string number = Cast.String(drv["OrderNumber"]);

        POReturnStatus status = Cast.Enum<POReturnStatus>(drv["Status"]);
        HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
        if (status != POReturnStatus.New)
            chk.Visible = false;

        ApproveStatus approveResult = Cast.Enum<ApproveStatus>(drv["ApproveResult"]);
        Label lblApproveText = e.Item.FindControl("lblApproveResult") as Label;
        switch (approveResult)
        {
            case ApproveStatus.UnApprove:
                lblApproveText.Text = "";
                break;
            case ApproveStatus.Approve:
                lblApproveText.Text = "通过";
                lblApproveText.ForeColor = System.Drawing.Color.Blue;
                break;
            case ApproveStatus.Reject:
                lblApproveText.Text = "驳回";
                lblApproveText.ForeColor = System.Drawing.Color.Red;
                break;
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            this.txtOrderNumber.Text = helper.Pop("ordnum");
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            if (helper.Pop("new") == "1") this.chkNew.Checked = true;
            else this.chkNew.Checked = false;
            if (helper.Pop("release") == "1") this.chkRelease.Checked = true;
            else this.chkRelease.Checked = false;
            if (helper.Pop("open") == "1") this.chkOpen.Checked = true;
            else this.chkOpen.Checked = false;
            if (helper.Pop("close") == "1") this.chkClose.Checked = true;
            else this.chkClose.Checked = false;
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), 19), true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("ordnum", this.txtOrderNumber.Text.Trim());
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        if (this.chkNew.Checked) helper.Push("new", "1");
        if (this.chkRelease.Checked) helper.Push("release", "1");
        if (this.chkOpen.Checked) helper.Push("open", "1");
        if (this.chkClose.Checked) helper.Push("close", "1");
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        helper.Push("ps", this.magicPagerMain.PageSize);
        return helper.OutputReturnUrl();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        using (ISession session = new Session())
        {
            bool deleted = false;
            try
            {
                session.BeginTransaction();
                foreach (RepeaterItem item in this.repeatControl.Items)
                {
                    HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                    if (chk != null && chk.Checked && chk.Value.Trim().Length > 0)
                    {
                        POReturnHead.Delete(session, chk.Value.Trim());
                        deleted = true;
                    }
                }

                session.Commit();
                if (deleted)
                {
                    WebUtil.ShowMsg(this, "选择的采购退货单已经删除");
                    this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, true);
                }
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
}