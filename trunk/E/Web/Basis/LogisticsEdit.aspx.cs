using System;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_LogisticsEdit : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Logistics));
    private int LogisticsId
    {
        get
        {
            return Cast.Int(this.txtLogisticID.Value, -1);
        }
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            this.txtLogisticID.Value = WebUtil.ParamInt("id", -1).ToString();
            using (_session = new Session())
            {
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit) RetrieveLogisticsData(_session); //如果编辑物流公司，则加载显示物流公司资料
            }
        }
    }

    void RetrieveLogisticsData(ISession session)
    {
        if (this.LogisticsId > 0)
        {
            Logistics logistics = Magic.Basis.Logistics.Retrieve(session, this.LogisticsId);
            this.txtAddress.Text = logistics.Address;
            this.txtBankAccount.Text = logistics.BankAccount;
            this.txtContact.Text = logistics.Contact;
            this.txtFax.Text = logistics.Fax;
            this.txtFullName.Text = logistics.FullName;
            this.txtLogisticsScope.Text = logistics.LogisticsScope;
            this.txtPhone.Text = logistics.Phone;
            this.txtPledgeAmount.Text = logistics.PledgeAmount.ToString();
            this.txtSettlementPeriod.Text = logistics.SettlementPeriod.ToString();
            this.txtShortName.Text = logistics.ShortName;
            this.txtZipCode.Text = logistics.ZipCode;
            this.txtLogisticID.Value = logistics.LogisticCompID.ToString();
            if (logistics.HasPledge)
                this.drpHasPledge.SelectedValue = "是";
            else
                this.drpHasPledge.SelectedValue = "否";
            this.drpStatus.SelectedValue =Cast.String(((int)logistics.Status));
            
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SaveLogistics();
        }
    }

    void SaveLogistics()
    {
        using (ISession session = new Session())
        {
            try
            {
                Logistics logistics = new Logistics();
                logistics.Status = Cast.Enum<LogisticsStatus>(this.drpStatus.Text);
                logistics.ShortName = this.txtShortName.Text;
                logistics.FullName = this.txtFullName.Text;
                logistics.Address = this.txtAddress.Text;
                logistics.ZipCode = this.txtZipCode.Text;
                logistics.Contact = this.txtContact.Text;
                logistics.Phone = this.txtPhone.Text;
                logistics.Fax = this.txtFax.Text;
                logistics.SettlementPeriod = Cast.Int(this.txtSettlementPeriod.Text,-1);
                if (this.drpHasPledge.SelectedValue == "是") 
                    logistics.HasPledge = true;
                else 
                    logistics.HasPledge = false;
                logistics.PledgeAmount = Cast.Int(this.txtPledgeAmount.Text,-1);
                logistics.LogisticsScope = this.txtLogisticsScope.Text;
                logistics.BankAccount = this.txtBankAccount.Text;
                if (this.IsAddNew())
                {
                    logistics.Create(session);
                    this.txtLogisticID.Value = logistics.LogisticCompID.ToString();
                }
                else
                {
                    logistics.LogisticCompID = Cast.Int(this.txtLogisticID.Value, -1);
                    logistics.Update(session,"Status", "ShortName", "FullName", "Address", "ZipCode", "Contact", "Phone", "Fax", "SettlementPeriod", "HasPledge", "PledgeAmount", "LogisticsScope", "BankAccount");
                }
                this.Response.Redirect(WebUtil.Param("return")); 
            }
            catch (Exception ex)
            {
                logger.Info("保存Logistics", ex);
                WebUtil.ShowMsg(this, "发生未处理的异常,请刷新页面重新操作，或者联系系统管理员");
            }
        }
    }

    private bool IsAddNew()
    {
        return this.LogisticsId <= 0;
    }
}