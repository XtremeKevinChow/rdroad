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

public partial class Approve_ProductInApprove : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-20).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");

            using (ISession session = new Session())
            {
                OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, StockInHead.ORD_TYPE_PRD_IN);
                if (typeDef != null)
                    this.hidViewUrl.Value = typeDef.ViewURL;

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
    ,h.Responsible as Responsible,u.FullName as CreateUser,h.CreateTime as CreateTime,h.Note as Note
from StockInHead h
left join User u on h.CreateUser=u.UserId
left join OrderStatusDef s on s.OrderTypeCode=h.OrderTypeCode and h.Status=s.StatusValue
where h.OrderTypeCode=?orderType
order by CreateTime desc")
            .Attach(typeof(StockInHead)).Attach(typeof(User)).Attach(typeof(OrderStatusDef))
            .SetValue("?orderType", StockInHead.ORD_TYPE_PRD_IN, "h.OrderTypeCode")
            .SetPage(pageIndex, pageSize);
        if (this.txtOrderNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("h.OrderNumber", "%" + this.txtOrderNumber.Text.Trim() + "%"));
        DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
        DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
        if (fromDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("h.CreateTime", fromDate));
        if (toDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("h.CreateTime", toDate.AddDays(1)));
        IList<StockInStatus> status = new List<StockInStatus>();
        if (this.chkUnapproved.Checked) status.Add(StockInStatus.Confirm);
        if (this.chkApproved.Checked)
        {
            status.Add(StockInStatus.Confirm);
            status.Add(StockInStatus.Open);
            status.Add(StockInStatus.Close);
        }
        query.And(Exp.In("h.Status", status));

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

        ApproveStatus approveResult = Cast.Enum<ApproveStatus>(drv["ApproveResult"]);
        Label lblApproveText = e.Item.FindControl("lblApproveResult") as Label;
        switch (approveResult)
        {
            case ApproveStatus.UnApprove:
                lblApproveText.Text = "";
                break;
            case ApproveStatus.Approve:
                lblApproveText.Text = "Í¨¹ý";
                lblApproveText.ForeColor = System.Drawing.Color.Blue;
                break;
            case ApproveStatus.Reject:
                lblApproveText.Text = "²µ»Ø";
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
            if (helper.Pop("app") == "1") this.chkApproved.Checked = true;
            else this.chkApproved.Checked = false;
            if (helper.Pop("unapp") == "1") this.chkUnapproved.Checked = true;
            else this.chkUnapproved.Checked = false;
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), 19), true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("ordnum", this.txtOrderNumber.Text.Trim());
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        if (this.chkApproved.Checked) helper.Push("app", "1");
        if (this.chkUnapproved.Checked) helper.Push("unapp", "1");
        helper.Push("pi", this.magicPagerMain.CurrentPageIndex);
        helper.Push("ps", this.magicPagerMain.PageSize);
        return helper.OutputReturnUrl();
    }
}