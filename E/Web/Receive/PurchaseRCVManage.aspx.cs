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
using Magic.Basis;
using Magic.Web.UI;

public partial class Receive_PurchaseRCVManage : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtDateFrom.Text = DateTime.Now.AddDays(-7).ToString("yyyy-MM-dd");
            this.txtDateTo.Text = DateTime.Now.ToString("yyyy-MM-dd");
            this.chkNew.Checked = true;
            this.chkOpen.Checked = true;

            using (ISession session = new Session())
            {
                OrderStatusDef statusDef = OrderStatusDef.Retrieve(session, RCVHead.ORD_TYPE_PUR, (int)ReceiveStatus.New);
                this.chkNew.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, RCVHead.ORD_TYPE_PUR, (int)ReceiveStatus.Release);
                this.chkRelease.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, RCVHead.ORD_TYPE_PUR, (int)ReceiveStatus.Open);
                this.chkOpen.Text = statusDef.StatusText;
                statusDef = OrderStatusDef.Retrieve(session, RCVHead.ORD_TYPE_PUR, (int)ReceiveStatus.Close);
                this.chkClose.Text = statusDef.StatusText;

                OrderTypeDef typeDef = OrderTypeDef.Retrieve(session, RCVHead.ORD_TYPE_PUR);
                if (typeDef != null)
                    this.hidViewUrl.Value = typeDef.ViewURL;

                this.LoadVendor(session);
                this.RestoreLastQuery(session);
            }
        }
    }
    private void LoadVendor(ISession session)
    {
        //加载该用户可以签核的单据类型
        this.drpVendor.Items.Clear();
        IList<Vendor> vendors = session.CreateEntityQuery<Vendor>()
            .Where(Exp.Eq("Status", VendorStatus.Enable))
            .OrderBy("ShortName")
            .List<Vendor>();
        this.drpVendor.Items.Add(new ListItem("　", ""));
        foreach (Vendor ven in vendors)
            this.drpVendor.Items.Add(new ListItem(ven.ShortName, ven.VendorID.ToString()));
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
select h.OrderNumber as OrderNumber,h.RefOrderNumber as PONumber,v.FullName as VendorName
    ,h.Status as Status,sd.StatusText as StatusText,h.ApproveResult as ApproveResult,u.FullName as UserName,h.CreateTime as CreateTime
from RCVHead h
left join Vendor v on h.ObjectID=v.VendorID
left join User u on h.CreateUser=u.UserId
left join OrderStatusDef sd on sd.OrderTypeCode=h.OrderTypeCode and sd.StatusValue=h.Status
where h.OrderTypeCode=?ordType
order by OrderNumber desc")
            .Attach(typeof(RCVHead)).Attach(typeof(Vendor)).Attach(typeof(User)).Attach(typeof(OrderStatusDef))
            .SetValue("?ordType", RCVHead.ORD_TYPE_PUR, "h.OrderTypeCode")
            .SetPage(pageIndex, pageSize);
        int vendorId = Cast.Int(this.drpVendor.SelectedValue, 0);
        if (vendorId > 0) query.And(Exp.Eq("h.ObjectID", vendorId));
        if (this.txtRcvNumber.Text.Trim().Length > 0)
            query.And(Exp.Like("h.OrderNumber", this.txtRcvNumber.Text.Trim() + "%"));
        DateTime fromDate = Cast.DateTime(this.txtDateFrom.Text.Trim(), new DateTime(1900, 1, 1));
        DateTime toDate = Cast.DateTime(this.txtDateTo.Text.Trim(), new DateTime(1900, 1, 1));
        if (fromDate > new DateTime(1900, 1, 1)) query.And(Exp.Ge("h.CreateTime", fromDate));
        if (toDate > new DateTime(1900, 1, 1)) query.And(Exp.Le("h.CreateTime", toDate.AddDays(1)));
        if (this.txtPONumber.Text.Trim().Length > 0)
            query.And(Exp.Like("h.RefOrderNumber", this.txtPONumber.Text.Trim() + "%"));
        IList<ReceiveStatus> status = new List<ReceiveStatus>();
        if (this.chkNew.Checked) status.Add(ReceiveStatus.New);
        if (this.chkRelease.Checked) status.Add(ReceiveStatus.Release);
        if (this.chkOpen.Checked) status.Add(ReceiveStatus.Open);
        if (this.chkClose.Checked) status.Add(ReceiveStatus.Close);
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

        string rcvNumber = Cast.String(drv["OrderNumber"]);

        ReceiveStatus status = Cast.Enum<ReceiveStatus>(drv["Status"]);
        HtmlInputCheckBox chk = e.Item.FindControl("checkbox") as HtmlInputCheckBox;
        //LinkButton cmdDelete = e.Item.FindControl("cmdDelete") as LinkButton;
        //cmdDelete.Attributes["ordNum"] = rcvNumber;
        if (status != ReceiveStatus.New)
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
            this.drpVendor.SelectedValue = helper.Pop("ven");
            this.txtRcvNumber.Text = helper.Pop("onum");
            this.txtDateFrom.Text = helper.Pop("df");
            this.txtDateTo.Text = helper.Pop("dt");
            this.txtPONumber.Text = helper.Pop("po");
            if (helper.Pop("new") == "1") this.chkNew.Checked = true;
            else this.chkNew.Checked = false;
            if (helper.Pop("release") == "1") this.chkRelease.Checked = true;
            else this.chkRelease.Checked = false;
            if (helper.Pop("open") == "1") this.chkOpen.Checked = true;
            else this.chkOpen.Checked = false;
            if (helper.Pop("close") == "1") this.chkClose.Checked = true;
            else this.chkClose.Checked = false;
        }
        this.QueryAndBindData(session, Cast.Int(helper.Pop("pi"), 1), Cast.Int(helper.Pop("ps"), this.magicPagerMain.PageSize), true);
    }

    private string GetReturnUrl()
    {
        QueryHelper helper = new QueryHelper(this);
        helper.Push("ven", this.drpVendor.SelectedValue);
        helper.Push("onum", this.txtRcvNumber.Text.Trim());
        helper.Push("df", this.txtDateFrom.Text.Trim());
        helper.Push("dt", this.txtDateTo.Text.Trim());
        helper.Push("po", this.txtPONumber.Text.Trim());
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
                    if (chk.Checked && chk.Value.Trim().Length > 0)
                    {
                        RCVHead head = RCVHead.Retrieve(session, chk.Value.Trim());
                        if (head == null || head.Status != ReceiveStatus.New) continue;

                        head.Delete(session);
                        session.CreateEntityQuery<RCVLine>()
                            .Where(Exp.Eq("OrderNumber", head.OrderNumber))
                            .Delete();
                        deleted = true;
                    }
                }

                session.Commit();
                if (deleted)
                {
                    WebUtil.ShowMsg(this, "选择的收货单已经删除");
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