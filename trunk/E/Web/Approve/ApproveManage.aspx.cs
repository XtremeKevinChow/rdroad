using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Sys;
using Magic.ERP;
using Magic.Basis;
using Magic.ERP.Core;
using Magic.ERP.Orders;

public partial class Approve_ApproveManage : System.Web.UI.Page
{
    private bool QueryApproved = false;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.QueryApproved = this.rdoApprove.Checked;
            using (ISession session = new Session())
            {
                this.txtDateFrom.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
                this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
                this.rdoUnapprove.Checked = true;
                this.LoadOrderType(session);
                this.QueryAndBindData(session, 1, this.magicPagerMain.PageSize, true);
            }
        }
    }
    private void LoadOrderType(ISession session)
    {
        //加载该用户可以签核的单据类型
        this.drpOrderType.Items.Clear();
        DataSet ds = session.CreateObjectQuery(@"
select distinct ot.OrderTypeCode as OrderTypeCode, ot.TypeText as TypeText
from OrderApproveDef oa
inner join OrderTypeDef ot on oa.OrderTypeCode=ot.OrderTypeCode
order by ot.OrderTypeCode")
            .Attach(typeof(OrderApproveDef)).Attach(typeof(OrderTypeDef))
            .Where(Exp.Eq("oa.UserID", Magic.Security.SecuritySession.CurrentUser.UserId))
            .And(Exp.Eq("ot.NeedApprove", true))
            .DataSet();
        this.drpOrderType.Items.Add(new ListItem("　", ""));
        foreach (DataRow row in ds.Tables[0].Rows)
            this.drpOrderType.Items.Add(new ListItem(Cast.String(row["TypeText"]), Cast.String(row["OrderTypeCode"])));
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
select ai.ApproveID as ApproveID,ai.OrderTypeCode as OrderTypeCode,td.TypeText as TypeText,ai.OrderNumber as OrderNumber
    ,ai.Status as Status,ai.ApproveTime as ApproveTime
    ,u1.FullName as SubmitUser,u2.FullName as CreateUser,ai.SubmitTime as SubmitTime,ai.CreateTime as CreateTime
    ,ar.HasFinished as HasFinished,ar.ActiveItem as ActiveItem, td.ViewURL as ViewURL
    ,ar.ApproveResultID as ApproveResultID, ' ' as Vendor
from OrderApproveItem ai 
inner join OrderApproveResult ar on ai.ApproveID=ar.ApproveID
inner join OrderTypeDef td on td.OrderTypeCode=ai.OrderTypeCode
left join User u1 on u1.UserId=ai.SubmitUser
left join User u2 on u2.UserId=ai.CreateUser
where ar.ApproveUser=?userId")
            .Attach(typeof(OrderApproveItem)).Attach(typeof(OrderApproveResult)).Attach(typeof(OrderTypeDef))
            .Attach(typeof(Magic.Sys.User))
            .SetValue("?userId", Magic.Security.SecuritySession.CurrentUser.UserId, "ar.ApproveUser")
            .SetPage(pageIndex, pageSize);
        if (this.drpOrderType.SelectedValue.Trim().Length > 0)
            query.And(Exp.Eq("ai.OrderTypeCode", this.drpOrderType.SelectedValue.Trim()));
        if (this.txtOrderNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("ai.OrderNumber", "%" + this.txtOrderNumber.Text.Trim() + "%"));
        if (this.rdoUnapprove.Checked)
            query.And(Exp.Eq("ar.ActiveItem", true)).OrderBy("ai.SubmitTime", Order.Desc);
        else
        {
            query.And(Exp.Eq("ar.HasFinished", true)).OrderBy("ai.ApproveTime", Order.Desc);
            DateTime dateFrom = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
            DateTime dateTo = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1)).AddDays(1);
            if (dateFrom > new DateTime(1900, 1, 1)) query.And(Exp.Ge("ai.ApproveTime", dateFrom));
            if (dateTo > new DateTime(1900, 1, 1)) query.And(Exp.Le("ai.ApproveTime", dateTo));
        }

        DataSet ds = query.DataSet();
        foreach (DataRow row in ds.Tables[0].Rows)
        {
            string ordType = Cast.String(row["OrderTypeCode"]);
            if (ordType == POHead.ORDER_TYPE)
            {
                string poNum = Cast.String(row["OrderNumber"]);
                POHead po = POHead.Retrieve(session, poNum);
                if (po != null && po.VendorID>0)
                {
                    Vendor vendor = Vendor.Retrieve(session, po.VendorID);
                    if (vendor != null)
                        row["Vendor"] = vendor.ShortName;
                }
            }
        }
        this.repeatControl.DataSource = ds;
        this.repeatControl.DataBind();
        if (fetchRecordCount)
            this.magicPagerMain.RecordCount = this.magicPagerSub.RecordCount = query.Count();
        WebUtil.SetMagicPager(magicPagerMain, pageSize, pageIndex);
        WebUtil.SetMagicPager(magicPagerSub, pageSize, pageIndex);
    }
    protected void repeatControl_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        DataRowView drv = e.Item.DataItem as DataRowView;
        if (drv == null) return;
        //签核、驳回按钮
        ImageButton cmd;
        if (this.rdoUnapprove.Checked)
        {
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = true;
            cmd = e.Item.FindControl("cmdPass") as ImageButton;
            cmd.Visible = true;
            cmd.Attributes["onclick"] = "noteForm(1, " + Cast.Int(drv["ApproveResultID"]).ToString() + ");return false;";
            cmd = e.Item.FindControl("cmdReject") as ImageButton;
            cmd.Visible = true;
            cmd.Attributes["onclick"] = "noteForm(2, " + Cast.Int(drv["ApproveResultID"]).ToString() + ");return false;";
        }
        else
        {
            HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
            chk.Visible = false;
            cmd = e.Item.FindControl("cmdPass") as ImageButton;
            cmd.Visible = false;
            cmd = e.Item.FindControl("cmdReject") as ImageButton;
            cmd.Visible = false;
        }
        //查看单据详细信息
        HtmlAnchor cmdView = e.Item.FindControl("cmdView") as HtmlAnchor;
        string viewUrl = Cast.String(drv["ViewURL"], "").Trim();
        if (viewUrl.Length <= 0) cmdView.HRef = "javascript:alert('该类型的单据没有设置查看页面，请和管理员联系');";
        else
        {
            string ot = Cast.String(drv["OrderTypeCode"]).Trim();
            string url = WebUtil.AppendParam(viewUrl, "ordNum", Cast.String(drv["OrderNumber"]));
            if (ot == StockInHead.ORD_TYPE_PRD_IN || ot==StockInHead.ORD_TYPE_PRD_OUT)
                url = WebUtil.AppendParam(url, "m", "sp");
            cmdView.HRef = "javascript:viewOrder('" + url + "');";
        }
        //查看签核历史记录
        cmd = e.Item.FindControl("cmdNote") as ImageButton;
        if (cmd != null)
        {
            cmd.Attributes.Add("ordNum", Cast.String(drv["OrderNumber"]));
            cmd.Attributes.Add("onclick", "showTips(this);");
        }
        //状态文本
        ApproveStatus status = Cast.Enum<ApproveStatus>(drv["Status"]);
        Label lbl = e.Item.FindControl("lblStatus") as Label;
        switch (status)
        {
            case ApproveStatus.Approve:
                lbl.Text = "通过";
                lbl.ForeColor = System.Drawing.Color.Blue;
                break;
            case ApproveStatus.UnApprove:
                lbl.Text = "签核中";
                lbl.ForeColor = System.Drawing.Color.Red;
                break;
            case ApproveStatus.Reject:
                lbl.Text = "驳回";
                lbl.ForeColor = System.Drawing.Color.DarkGray;
                break;
        }
    }
    protected void cmdApprove_Click(object sender, EventArgs e)
    {
        this.ApproveAction("Pass", Cast.Int(this.txtAppItemId.Value, -1));
    }
    protected void cmdReject_Click(object sender, EventArgs e)
    {
        this.ApproveAction("Reject", Cast.Int(this.txtAppItemId.Value, -1));
    }
    private void ApproveAction(string command, int appItemId)
    {
        if (command != "Pass" && command != "Reject")
        {
            WebUtil.ShowError(this, "无效操作");
            return;
        }

        bool approved = false;
        //MagicToolBar的事件
        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                if (appItemId > 0)
                {
                    if (command == "Pass") OrderApproveItem.Approve(session, appItemId, this.txtAppNote.Value.Trim());
                    else if (command == "Reject") OrderApproveItem.Reject(session, appItemId, this.txtAppNote.Value.Trim());
                    approved = true;
                }
                else
                {
                    foreach (RepeaterItem item in this.repeatControl.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            int appId = Cast.Int(chk.Value, 0);
                            if (appId > 0)
                            {
                                if (command == "Pass") OrderApproveItem.Approve(session, appId, this.txtAppNote.Value.Trim());
                                else if (command == "Reject") OrderApproveItem.Reject(session, appId, this.txtAppNote.Value.Trim());
                                approved = true;
                            }
                        }
                    }
                }
                session.Commit();
            }
            catch (Exception err)
            {
                session.Rollback();
                WebUtil.ShowError(this, err.Message, 300, 100);
            }
            if (approved)
            {
                WebUtil.ShowMsg(this, "选择的单据已经完成签核处理", "操作成功");
                this.QueryAndBindData(session, this.magicPagerMain.CurrentPageIndex, this.magicPagerMain.PageSize, false);
            }
        }
    }
}