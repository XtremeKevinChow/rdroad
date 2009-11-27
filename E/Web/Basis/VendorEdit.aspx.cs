using System;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_VenderEdit : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Vendor));
    private int VendorId
    {
        get
        {
            return Cast.Int(this.txtVendorId.Value, -1);
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            this.txtVendorId.Value = WebUtil.ParamInt("id", -1).ToString();
            using (_session = new Session())
            {
                InitdrpTax(_session);
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit) RetrieveVendorData(_session); //如果时编辑供应商，则加载显示供应商资料
            }
 
        }
    }

    void InitdrpTax(ISession session)
    {
        ObjectQuery query = session.CreateObjectQuery(@"select t.TaxID as TaxID,t.TaxText as TaxText,t.TaxValue as TaxValue,t.TaxIndex as TaxIndex from TaxDef t").Attach(typeof(TaxDef));
        this.drpTax.DataTextField = "TaxValue";
        this.drpTax.DataValueField = "TaxID";
        this.drpTax.DataSource = query.DataSet();
        this.drpTax.DataBind();
    }

    void RetrieveVendorData(ISession session)
    {
        int vendorID = WebUtil.ParamInt("id", -1);
        if (vendorID > 0)
        {
            Vendor vendor = Magic.Basis.Vendor.Retrieve(session, vendorID);
            this.txtAddress.Text = vendor.Address;
            this.txtBankAccount.Text = vendor.BankAccount;
            this.txtContact.Text = vendor.Contact;
            this.txtFax.Text = vendor.Fax;
            this.txtFullName.Text = vendor.FullName;
            this.txtPhone.Text = vendor.Phone;
            this.txtShortName.Text = vendor.ShortName;
            this.txtVendorId.Value = vendor.VendorID.ToString();
            this.txtZipCode.Text = vendor.ZipCode;
            this.drpTax.Text = vendor.TaxID.ToString();
            this.drpStatus.Text = this.drpStatus.SelectedValue = Cast.String(((int)vendor.Status)); 
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SaveVendor();
        }
    }

    void SaveVendor()
    {
        using (ISession session = new Session())
        {
            try
            {
                Vendor vendor = new Vendor();
                vendor.Status = Cast.Enum<VendorStatus>(this.drpStatus.Text);
                vendor.ShortName = this.txtShortName.Text;
                vendor.FullName = this.txtFullName.Text;
                vendor.Address = this.txtAddress.Text;
                vendor.Contact = this.txtContact.Text;
                vendor.ZipCode = this.txtZipCode.Text;
                vendor.Phone = this.txtPhone.Text;
                vendor.Fax = this.txtFax.Text;
                vendor.BankAccount = this.txtBankAccount.Text;
                vendor.TaxID = Cast.Int(this.drpTax.SelectedValue, -1);
                vendor.Tax = Cast.Decimal(this.drpTax.SelectedItem.Text, -1);
                if (this.IsAddNew())
                {
                    vendor.Create(session);
                    this.txtVendorId.Value = vendor.VendorID.ToString();
                }
                else
                {
                    vendor.VendorID = Cast.Int(this.txtVendorId.Value, -1);
                    vendor.Update(session,"Status", "ShortName", "FullName", "Address", "Contact", "ZipCode", "Phone", "Fax", "BankAccount", "TaxID", "Tax");
                }
                this.Response.Redirect(WebUtil.Param("return"));
            }
            catch (Exception ex)
            {
                logger.Info("保存Vendor", ex);
                WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
            }
        }
    }

    private bool IsAddNew()
    {
        return this.VendorId <= 0;
    }
}
