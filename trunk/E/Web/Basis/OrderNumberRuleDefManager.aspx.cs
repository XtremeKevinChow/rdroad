using System;
using System.Web.UI.WebControls;
using Magic.ERP.Core;
using Magic.Framework.ORM;

public partial class Basis_OrderNumberRuleDefManager : System.Web.UI.Page
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
        this.repeaterControl.DataSource = session.CreateEntityQuery<OrderRuleDef>()
            .OrderBy("RuleDefineID")
            .List<OrderRuleDef>();
        this.repeaterControl.DataBind();
    }

    protected void cmdDelete_Click(object sender, EventArgs e)
    {
        LinkButton cmd = sender as LinkButton;
        if (cmd == null) return;
        int defId = Magic.Framework.Utils.Cast.Int(cmd.Attributes["defId"], 0);
        if (defId <= 0) return;
        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                OrderRuleDef.Retrieve(session, defId).Delete(session);
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
}