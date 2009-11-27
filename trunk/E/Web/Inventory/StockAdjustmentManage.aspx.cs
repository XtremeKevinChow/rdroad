using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;

public partial class Inventory_StockAdjustmentManage : System.Web.UI.Page
{
    log4net.ILog logger = WebUtil.Logger(typeof(Inventory_StockAdjustmentManage));

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtBeginDate.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
            this.txtEndDate.Text = DateTime.Now.ToString("yyyy-MM-dd");
            this.chkNew.Checked = true;
            this.chkRelease.Checked = true;

            using (ISession session = new Session())
            {
                this.ddlLocation.Items.Clear();
                this.ddlLocation.Items.Add(new ListItem("　", ""));
                foreach (WHLocation loc in WHLocation.EffectiveList(session))
                    this.ddlLocation.Items.Add(new ListItem(loc.Name, loc.LocationCode));

                OrderStatusDef def = OrderStatusDef.Retrieve(session, INVCheckHead.ORDER_TYPE_ADJ, (int)INVCheckStatus.New);
                this.chkNew.Text = def.StatusText;
                def = OrderStatusDef.Retrieve(session, INVCheckHead.ORDER_TYPE_ADJ, (int)INVCheckStatus.Release);
                this.chkRelease.Text = def.StatusText;
                def = OrderStatusDef.Retrieve(session, INVCheckHead.ORDER_TYPE_ADJ, (int)INVCheckStatus.Close);
                this.chkClose.Text = def.StatusText;

                WebUtil.SetMagicPager(magicPagerMain, magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, magicPagerMain.PageSize, 1);

                RestoreLastQuery(session);
            }
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
            QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            try
            {
                using (ISession session = new Session())
                {
                    List<INVCheckHead> heads = new List<INVCheckHead>();
                    for (int i = 0; i < this.rptSDHead.Items.Count; i++)
                    {
                        System.Web.UI.HtmlControls.HtmlInputCheckBox chk = this.rptSDHead.Items[i].FindControl("checkbox") as System.Web.UI.HtmlControls.HtmlInputCheckBox;
                        if (chk.Checked)
                            heads.Add(INVCheckHead.Retrieve(session, chk.Value));
                    }

                    session.BeginTransaction();
                    try
                    {
                        foreach (INVCheckHead h in heads) h.Delete(session);
                        session.Commit();
                        QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
                    }
                    catch
                    {
                        session.Rollback();
                        throw;
                    }
                }
            }
            catch (Exception ex)
            {
                WebUtil.ShowMsg(this, "删除失败:" + ex.Message);
            }
        }
    }

    private void RestoreLastQuery(ISession session)
    {
        //如果是从其它页面返回该页面，恢复条件值
        QueryHelper helper = new QueryHelper(this);
        if (helper.HasQueryParameter())
        {
            helper.SetValue(this.txtBeginDate);
            helper.SetValue(this.txtEndDate);
            helper.SetValue(this.txtAdjustmentNumber);
            helper.SetValue(this.ddlLocation);
            if (helper.Pop("new") == "1") this.chkNew.Checked = true; else this.chkNew.Checked = false;
            if (helper.Pop("release") == "1") this.chkRelease.Checked = true; else this.chkRelease.Checked = false;
            if (helper.Pop("close") == "1") this.chkClose.Checked = true; else this.chkClose.Checked = false;
        }
        int pageSize = Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize);
        int pageIndex = Cast.Int(helper.Pop("pi"), 1);
        QueryAndBindData(session, pageIndex, pageSize, true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.GetValue(this.txtBeginDate);
        helper.GetValue(this.txtEndDate);
        helper.GetValue(this.txtAdjustmentNumber);
        helper.GetValue(this.ddlLocation);
        if (this.chkNew.Checked) helper.Push("new", "1");
        else helper.Push("new", "0");
        if (this.chkRelease.Checked) helper.Push("release", "1");
        else helper.Push("release", "0");
        if (this.chkClose.Checked) helper.Push("close", "1");
        else helper.Push("close", "0");
        helper.Push("ps", this.magicPagerMain.PageSize);
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        return helper.OutputReturnUrl();
    }

    private void QueryAndBindData(ISession session, int pageIndex, int pageSize, bool fetchRecordCount)
    {
        try
        {
            string strAdjustmentNumber = this.txtAdjustmentNumber.Text.Trim().ToUpper();
            DateTime dtBegin = Cast.DateTime(this.txtBeginDate.Text, new DateTime(1900, 1, 1));
            DateTime dtEnd = Cast.DateTime(this.txtEndDate.Text, new DateTime(1900, 1, 1));
            IList<INVCheckStatus> status = new List<INVCheckStatus>();
            if (this.chkNew.Checked) status.Add(INVCheckStatus.New);
            if (this.chkRelease.Checked) status.Add(INVCheckStatus.Release);
            if (this.chkClose.Checked) status.Add(INVCheckStatus.Close);

            ObjectQuery query = session.CreateObjectQuery(@"
SELECT A.OrderNumber AS OrderNumber,A.Status as Status,C.StatusText AS StatusText,A.ApproveResult as ApproveResult
    ,loc.Name as Location,D.FullName AS UserName,A.CreateTime AS CreateDate
FROM INVCheckHead A
left join WHLocation loc on loc.LocationCode=A.LocationCode
LEFT JOIN OrderStatusDef C ON A.Status=C.StatusValue AND C.OrderTypeCode=A.OrderTypeCode
LEFT JOIN User D ON A.CreateUser=D.UserId
WHERE A.OrderTypeCode=?
order by A.CreateTime desc")
            .Attach(typeof(INVCheckHead))
            .Attach(typeof(WHLocation))
            .Attach(typeof(OrderStatusDef))
            .Attach(typeof(Magic.Sys.User))
            .SetValue(0, INVCheckHead.ORDER_TYPE_ADJ, "A.OrderTypeCode")
            .And(Exp.In("A.Status", status))
            .SetPage(pageIndex, pageSize);

            if (strAdjustmentNumber.Length < 0) query.And(Exp.Like("A.OrderNumber", "%" + strAdjustmentNumber + "%"));
            if (dtBegin > new DateTime(1900, 1, 1)) query.And(Exp.Ge("A.CreateTime", dtBegin));
            if (dtEnd > new DateTime(1900, 1, 1)) query.And(Exp.Le("A.CreateTime", dtEnd.AddDays(1)));
            if (this.ddlLocation.SelectedValue.Length > 0) query.And(Exp.Eq("A.LocationCode", this.ddlLocation.SelectedValue));

            this.rptSDHead.DataSource = query.DataSet();
            this.rptSDHead.DataBind();

            if (fetchRecordCount)
                this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();

            WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
            WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
            this.hidReturnUrl.Value = GetReturnUrl();
            logger.DebugFormat("return url to sto adj: {0}", GetReturnUrl());
        }
        catch (Exception ex)
        {
            logger.Info("error on query sto adj head", ex);
            WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
        }
    }
    protected void rptSDHead_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        System.Data.DataRowView drv = e.Item.DataItem as System.Data.DataRowView;
        if (drv == null) return;
        INVCheckStatus status = Cast.Enum<INVCheckStatus>(drv["Status"]);
        if (status != INVCheckStatus.New)
        {
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            if (chk != null) chk.Visible = false;
        }
        Label lblApprStatus = e.Item.FindControl("lblApprStatus") as Label;
        ApproveStatus apprStatus = Cast.Enum<ApproveStatus>(drv["ApproveResult"]);
        lblApprStatus.Text = ERPUtil.EnumText<ApproveStatus>(apprStatus);
        switch (apprStatus)
        {
            case ApproveStatus.UnApprove:
                lblApprStatus.Text = "";
                break;
            case ApproveStatus.Approve:
                lblApprStatus.ForeColor = System.Drawing.Color.Blue;
                break;
            case ApproveStatus.Reject:
                lblApprStatus.ForeColor = System.Drawing.Color.Red;
                break;
        }
    }
}