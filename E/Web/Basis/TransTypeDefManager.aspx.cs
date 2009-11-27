using System;
using System.Web.UI.WebControls;
using Magic.ERP.Core;
using Magic.Framework.ORM;

public partial class Basis_TransTypeDefManager : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                this.QueryAndBindData(session);
            }
        }
    }

    private void QueryAndBindData(ISession session)
    {
        this.repeaterControl.DataSource = session.CreateEntityQuery<TransTypeDef>()
            .OrderBy("TransTypeCode")
            .List<TransTypeDef>();
        this.repeaterControl.DataBind();
    }

    protected void cmdDelete_Click(object sender, EventArgs e)
    {
        LinkButton cmd = sender as LinkButton;
        if (cmd == null) return;
        string tansTypeCode = cmd.Attributes["typeCode"];
        if (string.IsNullOrEmpty(tansTypeCode) || tansTypeCode.Trim().Length <= 0) return;
        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                TransTypeDef.Retrieve(session, tansTypeCode).Delete(session);
                session.Commit();
                this.QueryAndBindData(session);
            }
            catch (Exception er)
            {
                session.Rollback();
                WebUtil.ShowError(this, er);
            }
        }
    }
    public string TransPropertyText(Magic.ERP.TransProperty property)
    {
        switch (property)
        {
            case Magic.ERP.TransProperty.In: return "入";
            case Magic.ERP.TransProperty.Out: return "出";
        }
        return "";
    }
}