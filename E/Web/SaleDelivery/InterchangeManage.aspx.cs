using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Sys;
using Magic.ERP;
using Magic.ERP.Core;
using Magic.ERP.Orders;
using Magic.Web.UI;
using Magic.Basis;

public partial class SaleDelivery_InterchangeManage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddMonths(-1).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            this.chkNew.Checked = true;
            this.chkRelease.Checked = true;
            this.chkOpen.Checked = true;

            using (ISession session = new Session())
            {
                OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, ICHead.ORDER_TYPE);
                if (typeDef != null)
                    this.hidViewUrl.Value = typeDef.ViewURL;

                OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, ICHead.ORDER_TYPE, (int)InterchangeStatus.New);
                this.chkNew.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, ICHead.ORDER_TYPE, (int)InterchangeStatus.Release);
                this.chkRelease.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, ICHead.ORDER_TYPE, (int)InterchangeStatus.Open);
                this.chkOpen.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, ICHead.ORDER_TYPE, (int)InterchangeStatus.Close);
                this.chkClose.Text = statusDef.StatusText;

                this.LoadLogistics(session);

                WebUtil.SetMagicPager(magicPagerMain, this.magicPagerMain.PageSize, 1);
                WebUtil.SetMagicPager(magicPagerSub, this.magicPagerMain.PageSize, 1);
                this.RestoreLastQuery(session);
            }
        }
    }
    private void LoadLogistics(ISession session)
    {
        IList<Logistics> logistics = session.CreateEntityQuery<Logistics>()
            .Where(Exp.Eq("Status", LogisticsStatus.Enable))
            .OrderBy("ShortName")
            .List<Logistics>();
        this.drpLogistic.Items.Clear();
        this.drpLogistic.Items.Add(new ListItem("　", "0"));
        foreach (Logistics log in logistics)
            this.drpLogistic.Items.Add(new ListItem(log.ShortName, log.LogisticCompID.ToString()));
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
    ,u.FullName as CompanyUser,h.InterchangeTime as InterchangeTime,h.Note as Note
from ICHead h
left join User u on h.CompanyUser=u.UserId
left join OrderStatusDef s on s.OrderTypeCode=h.OrderTypeCode and h.Status=s.StatusValue
where h.OrderTypeCode=?orderType
order by h.InterchangeTime desc")
            .Attach(typeof(ICHead)).Attach(typeof(User)).Attach(typeof(OrderStatusDef))
            .SetValue("?orderType", ICHead.ORDER_TYPE, "h.OrderTypeCode")
            .SetPage(pageIndex, pageSize);
        if (this.txtOrderNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("h.OrderNumber", "%" + this.txtOrderNumber.Text.Trim() + "%"));
        DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
        DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
        if (fromDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("h.InterchangeTime", fromDate));
        if (toDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("h.InterchangeTime", toDate.AddDays(1)));
        IList<InterchangeStatus> status = new List<InterchangeStatus>();
        if (this.chkNew.Checked) status.Add(InterchangeStatus.New);
        if (this.chkRelease.Checked) status.Add(InterchangeStatus.Release);
        if (this.chkOpen.Checked) status.Add(InterchangeStatus.Open);
        if (this.chkClose.Checked) status.Add(InterchangeStatus.Close);
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

        InterchangeStatus status = Cast.Enum<InterchangeStatus>(drv["Status"]);
        HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
        if (status != InterchangeStatus.New)
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
            this.drpLogistic.SelectedValue = helper.Pop("lg");
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
        helper.Push("lg", this.drpLogistic.SelectedValue);
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
                        ICHead.Delete(session, chk.Value.Trim());
                        deleted = true;
                    }
                }

                session.Commit();
                if (deleted)
                {
                    WebUtil.ShowMsg(this, "选择的交接单已经删除");
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