using System;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_PurchaseGroupManager : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            using (ISession session = new Session())
            {
                QueryAndBindData(session);
            }
        }
    }

    void QueryAndBindData(ISession session)
    {
        ObjectQuery query = session.CreateObjectQuery(@"select p.PurchGroupCode as PurchGroupCode,p.PurchGroupText as PurchGroupText from PurchaseGroup p").Attach(typeof(PurchaseGroup));
        this.rptPurchaseGroup.DataSource = query.DataSet();
        this.rptPurchaseGroup.DataBind();
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Delete")
        {
            bool deleted = false;
            using (ISession session = new Session())
            {
                session.BeginTransaction();
                try
                {
                    foreach (RepeaterItem item in this.rptPurchaseGroup.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && !string.IsNullOrEmpty(chk.Value))
                        {
                            Magic.Basis.PurchaseGroup purhcasegroup = Magic.Basis.PurchaseGroup.Retrieve(session, Cast.String(chk.Value));
                            if (purhcasegroup != null)
                            {
                                purhcasegroup.PurchGroupCode = Cast.String(chk.Value);
                                purhcasegroup.Delete(session);
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        QueryAndBindData(session);
                        WebUtil.ShowMsg(this, "选择的采购组已经被删除", "操作成功");
                    }
                }
                catch (Exception ex)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, ex);
                }
            }
        }
    }
}
