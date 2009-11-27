using System;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_ReturnReasonEdit : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(ReturnReason));

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            using (_session = new Session())
            {
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit) RetrieveReturnReasonData(_session);
            }
        }
    }

    void RetrieveReturnReasonData(ISession session)
    {
        int ReasonID = WebUtil.ParamInt("id", -1);
        if (ReasonID > 0)
        {
            ReturnReason returnreason = Magic.Basis.ReturnReason.Retrieve(session, ReasonID);
            this.txtReturnReason.Text = returnreason.ReasonText;
            this.txtReturnID.Value = returnreason.ReasonID.ToString();
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            SaveReturnReason();
        }
    }

    void SaveReturnReason()
    {
        using (ISession session = new Session())
        {
            try
            {
                ReturnReason returnreason = new ReturnReason();
                returnreason.ReasonText = this.txtReturnReason.Text;
                if (this.IsAddNew())
                {
                    returnreason.Create(session);
                }
                else
                {
                    returnreason.ReasonID = Cast.Int(this.txtReturnID.Value, -1);
                    returnreason.Update(session, "ReasonText");
                }
                this.Response.Redirect("ReturnReasonManager.aspx");
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
        return Cast.Int(this.txtReturnID.Value.Trim(), -1) <= 0;
    }
}
