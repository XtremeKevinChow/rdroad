using System;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_PurchaseGroupEdit : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(PurchaseGroup));

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            using (_session = new Session())
            {
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit) RetrievePurchaseGroupData(_session);
            }
        }
    }

    void RetrievePurchaseGroupData(ISession session)
    {
        string PurchGroupCode = WebUtil.Param("id");
        if (!string.IsNullOrEmpty(PurchGroupCode))
        {
            PurchaseGroup purchasegroup = Magic.Basis.PurchaseGroup.Retrieve(session, PurchGroupCode);
            this.txtPurchGroupCode.Text = purchasegroup.PurchGroupCode;
            this.txtPurchGroupCode.Enabled = false;
            this.txtPurchGroupText.Text = purchasegroup.PurchGroupText;
            this.txtCode.Value = purchasegroup.PurchGroupCode;
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SavePurchaseGroup();
        }
    }

    void SavePurchaseGroup()
    {
        using (ISession session = new Session())
        {
            try
            {
                PurchaseGroup purchasegroup = new PurchaseGroup();
                purchasegroup.PurchGroupCode = this.txtPurchGroupCode.Text;
                purchasegroup.PurchGroupText = this.txtPurchGroupText.Text;
                if (this.IsAddNew())
                {
                    //检测code是否重复
                    int existsCount = session.CreateEntityQuery<Magic.Basis.PurchaseGroup>().Where(Magic.Framework.ORM.Query.Exp.Eq("PurchGroupCode", purchasegroup.PurchGroupCode)).Count();
                    if (existsCount > 0)
                    {
                        WebUtil.ShowMsg(this, string.Format("采购组编号{0}已经存在", purchasegroup.PurchGroupCode), "错误");
                        return; 
                    }
                    purchasegroup.Create(session);
                }
                else
                {
                    purchasegroup.Update(session, "PurchGroupText");
                }
                this.Response.Redirect("PurchaseGroupManager.aspx");
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
        return string.IsNullOrEmpty(this.txtCode.Value);
    }
}
