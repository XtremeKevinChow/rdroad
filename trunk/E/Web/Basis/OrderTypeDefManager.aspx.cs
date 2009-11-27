using System;
using System.Web.UI.WebControls;
using Magic.Framework.ORM;
using Magic.ERP.Core;

public partial class Basis_OrderTypeDefManager : System.Web.UI.Page
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
        this.repeatControl.DataSource = session.CreateObjectQuery(@"
select t.OrderTypeCode as OrderTypeCode,t.TypeText as TypeText,t.SupportApprove as SupportApprove,t.NeedApprove as NeedApprove
    ,t.ViewURL as ViewURL,r.RuleDefineText as RuleDefineText
from OrderTypeDef t
left join OrderRuleDef r on t.RuleDefineID=r.RuleDefineID
order by t.OrderTypeCode")
            .Attach(typeof(OrderTypeDef)).Attach(typeof(OrderRuleDef))
            .DataSet();
        this.repeatControl.DataBind();
    }

    protected void cmdDelete_Click(object sender, EventArgs e)
    {
        LinkButton cmd = sender as LinkButton;
        if (cmd == null) return;
        string typeCode = cmd.Attributes["typeCode"];
        if (string.IsNullOrEmpty(typeCode) || typeCode.Trim().Length <= 0) return;
        using (ISession session = new Session())
        {
            try
            {
                session.BeginTransaction();
                OrderTypeDef.Retrieve(session, typeCode).Delete(session);
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