using System;
using System.Data;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Basis;
using Magic.ERP;
using Magic.ERP.Orders;
using Magic.ERP.Core;

public partial class Purchase_POEdit : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(POHead));

    private bool IsNew
    {
        get
        {
            return this.txtOrderNumber.Text.Trim().Length <= 0;
        }
    }
    private string OrderNumber
    {
        get { return this.txtOrderNumber.Text; }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.hidStatus.Value = POStatus.New.ToString();
            this.txtOrderNumber.Text = WebUtil.Param("OrderNum");
            this.lblPaid.Text = "否";
            POHead po = null;
            using (_session = new Session())
            {
                InitDrp(_session);
                _actionMode = WebUtil.GetActionMode(this);
                this.txtDemandDate.Text = DateTime.Now.ToString("yyyy-MM-dd");

                if (_actionMode == Mode.Edit)
                {
                    po = POHead.Retrieve(_session, this.OrderNumber);
                    RetrievePOHeadData(po, this.OrderNumber);
                }
            }
            this.SetView(po);
        }
    }

    void InitDrp(ISession session)
    {
        ListItem li;
        //初始化仓库
        ObjectQuery query = session.CreateObjectQuery(@"select w.LocationCode as LocationCode,w.Name as Name,w.Status as Status from WHLocation w where w.Status=?").Attach(typeof(WHLocation)).SetValue(0, WHStatus.Enable, "w.Status");
        this.drpLocationCode.DataTextField = "Name";
        this.drpLocationCode.DataValueField = "LocationCode";
        this.drpLocationCode.DataSource = query.DataSet();
        this.drpLocationCode.DataBind();
        li = new ListItem("　", "");
        li.Selected = true;
        this.drpLocationCode.Items.Insert(0, li);
        //初始供应商
        query = session.CreateObjectQuery(@"
select v.VendorID as VendorID,v.Status as Status,v.ShortName as ShortName 
from Vendor v where v.Status=?
order by v.ShortName")
            .Attach(typeof(Vendor)).SetValue(0, VendorStatus.Enable, "v.Status");
        this.drpVendorID.DataTextField = "ShortName";
        this.drpVendorID.DataValueField = "VendorID";
        this.drpVendorID.DataSource = query.DataSet();
        this.drpVendorID.DataBind();
        li = new ListItem("　", "");
        li.Selected = true;
        this.drpVendorID.Items.Insert(0, li);
        //根据当前登陆用户获得绑定采购组编码
        int userID = Magic.Security.SecuritySession.CurrentUser.UserId;
        query = session.CreateObjectQuery(@"
select distinct p2.PurchGroupCode as PurchGroupCode
from PurchaseGroup2User p,PurchaseGroup p2 
where p2.PurchGroupCode=p.PurchGroupCode AND p.UserID=?
order by p2.PurchGroupCode")
            .Attach(typeof(PurchaseGroup2User)).Attach(typeof(PurchaseGroup))
            .SetValue(0, userID, "p.UserID");
        this.drpPurchGroupCode.DataTextField = "PurchGroupCode";
        this.drpPurchGroupCode.DataValueField = "PurchGroupCode";
        this.drpPurchGroupCode.DataSource = query.DataSet();
        this.drpPurchGroupCode.DataBind();
        if (this.drpPurchGroupCode.Items.Count > 0) this.drpPurchGroupCode.SelectedIndex = 0;
    }

    void RetrievePOHeadData(POHead po, string poNumber)
    {
        string orderNumber = poNumber;
        if (!string.IsNullOrEmpty(orderNumber))
        {
            if (po != null)
            {
                this.txtOrderNumber.Text = po.OrderNumber;
                this.drpPurchGroupCode.SelectedValue = po.PurchGroupCode;
                this.drpLocationCode.SelectedValue = po.LocationCode;
                this.drpVendorID.SelectedValue = po.VendorID.ToString();
                this.txtShippingAddress.Text = po.ShippingAddress;
                this.txtNote.Text = po.Note;
                this.hidStatus.Value = po.Status.ToString();
                this.txtDemandDate.Text = po.DefaultPlanDate.ToString("yyyy-MM-dd");
                this.lblPaid.Text = po.HasPaid ? "是" : "否";
                if (po.Status != POStatus.New)
                {
                    WebUtil.DisableControl(this.drpPurchGroupCode);
                    WebUtil.DisableControl(this.drpLocationCode);
                    WebUtil.DisableControl(this.drpVendorID);
                    WebUtil.DisableControl(this.txtShippingAddress);
                    WebUtil.DisableControl(this.txtNote);
                    WebUtil.DisableControl(this.txtDemandDate);
                    this.cmdSave.Visible = false;
                }
            }
        }
    }
    private void SetView(POHead po)
    {
        if (this.IsNew)
        {
            this.cmdDetail.Visible = false;
            this.cmdPaid.Visible = false;
        }
        else
        {
            this.cmdDetail.Visible = true;
            this.cmdPaid.Visible = false;
            if (po != null && !po.HasPaid)
                this.cmdPaid.Visible = true;
            this.cmdDetail["Detail"].NavigateUrl = "POLineManage.aspx?OrderNum=" + this.OrderNumber + "&return=" + Microsoft.JScript.GlobalObject.escape(WebUtil.Param("return"));
        }
        this.cmdReturn["Return"].NavigateUrl = WebUtil.Param("return");
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            this.SetView(SavePOHead());
        }
        else if (e.CommandName == "Paid")
        {
            try
            {
                using (ISession session = new Session())
                {
                    POHead po = POHead.Retrieve(session, this.OrderNumber);
                    po.HasPaid = true;
                    po.Update(session, "HasPaid");
                    this.cmdPaid.Visible = false;
                    this.lblPaid.Text = "是";
                    WebUtil.ShowMsg(this, "已付款操作成功");
                }
            }
            catch (Exception er)
            {
                WebUtil.ShowError(this, "操作失败：" + er.Message);
            }
        }
    }

    public POHead SavePOHead()
    {
        using (ISession session = new Session())
        {
            try
            {
                POHead poHead = null;
                if (this.IsNew)
                {
                    //新增
                    poHead = new POHead();
                    poHead.OrderNumber = ERPUtil.NextOrderNumber(POHead.ORDER_TYPE);
                    this.txtOrderNumber.Text = poHead.OrderNumber;
                    poHead.CompanyID = -1;
                    poHead.LocationCode = this.drpLocationCode.SelectedValue;
                    poHead.PurchGroupCode = this.drpPurchGroupCode.SelectedValue;
                    poHead.VendorID = Cast.Int(this.drpVendorID.SelectedValue, 0);
                    poHead.Status = POStatus.New;
                    poHead.TaxAmt = 0M;
                    poHead.TaxExclusiveAmt = 0M;
                    poHead.TaxInclusiveAmt = 0M;
                    poHead.ShippingAddress = this.txtShippingAddress.Text.Trim();
                    poHead.CreateUser = Magic.Security.SecuritySession.CurrentUser.UserId;
                    poHead.CreateTime = DateTime.Now;
                    poHead.ApproveResult = ApproveStatus.UnApprove;
                    poHead.ApproveTime = new DateTime(1900, 1, 1);
                    poHead.ApproveUser = 0;
                    poHead.ApproveNote = " ";
                    poHead.CurrentLineNumber = "0000";
                    poHead.Note = this.txtNote.Text.Trim();
                    poHead.DefaultPlanDate = Cast.DateTime(this.txtDemandDate.Text, DateTime.Now);
                    poHead.Create(session);
                    WebUtil.ShowMsg(this, "采购订单保存成功", "操作成功");
                    return poHead;
                }
                else
                {
                    //编辑
                    poHead = POHead.Retrieve(session, this.txtOrderNumber.Text.Trim());
                    poHead.LocationCode = this.drpLocationCode.SelectedValue;
                    poHead.PurchGroupCode = this.drpPurchGroupCode.SelectedValue;
                    poHead.VendorID = Cast.Int(this.drpVendorID.SelectedValue, 0);
                    poHead.ShippingAddress = this.txtShippingAddress.Text.Trim();
                    poHead.Note = this.txtNote.Text.Trim();
                    poHead.DefaultPlanDate = Cast.DateTime(this.txtDemandDate.Text, poHead.DefaultPlanDate);
                    poHead.Update(session, "PurchGroupCode", "LocationCode", "VendorID", "ShippingAddress", "Note", "DefaultPlanDate");
                    WebUtil.ShowMsg(this, "采购订单保存成功", "操作成功");
                    return poHead;
                }
            }
            catch (Exception ex)
            {
                //throw;
                logger.Info("保存POHead", ex);
                WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
            }
            return null;
        }
    }
    protected void drpLocationCode_SelectedIndexChanged(object sender, EventArgs e)
    {
        //如果已经填写送货地址，不再加载默认送货地址
        if (this.txtShippingAddress.Text.Trim().Length > 0 || this.drpLocationCode.SelectedValue == "") return;
        using (ISession session = new Session())
        {
            Magic.ERP.Core.WHLocation whLocation = Magic.ERP.Core.WHLocation.Retrieve(session, this.drpLocationCode.SelectedValue);
            if (whLocation != null)
                this.txtShippingAddress.Text = whLocation.Address;
        }
    }
}