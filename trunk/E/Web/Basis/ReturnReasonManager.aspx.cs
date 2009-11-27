using System;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Basis;

public partial class system_ReturnReasonManager : System.Web.UI.Page
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
        ObjectQuery query = session.CreateObjectQuery(@"select r.ReasonID as ReasonID,r.ReasonText as ReasonText from ReturnReason r").Attach(typeof(ReturnReason));
        this.rptReturnReason.DataSource = query.DataSet();
        this.rptReturnReason.DataBind();
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
                    foreach (RepeaterItem item in this.rptReturnReason.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            Magic.Basis.ReturnReason returnreason = Magic.Basis.ReturnReason.Retrieve(session, Cast.Int(chk.Value));
                            if (returnreason != null)
                            {
                                //逻辑删除，仅将状态update成UserStatus.Deleted
                                returnreason.ReasonID = Cast.Int(chk.Value);
                                returnreason.Delete(session);
                                deleted = true;
                            }
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        QueryAndBindData(session);
                        WebUtil.ShowMsg(this, "选择的退货原因已经被删除", "操作成功");
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
