using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Web.UI;
using Magic.Sys;
using Magic.Basis;

public partial class system_PurchaseGroup2UserManager : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.txtGroupCode.Value = this.PurchaseGroupCode;
            this.toolbarTop["Return"].NavigateUrl = "PurchaseGroupManager.aspx";
            this.toolbarBottom["Return"].NavigateUrl = "PurchaseGroupManager.aspx";
            using (ISession session = new Session())
            {
                QueryAndBindData(session);
            }
        }
    }
    public string PurchaseGroupCode
    {
        get
        {
            return WebUtil.Param("purchaseGroupCode");
        }
    }
    void QueryAndBindData(ISession session)
    {
        this.rptPurchaseGroup.DataSource = session.CreateObjectQuery(@"
select u.* from User u where u.UserId in (select UserID from PurchaseGroup2User where PurchGroupCode=?)
")
            .Attach(typeof(User)).Attach(typeof(PurchaseGroup2User))
            .SetValue(0, this.PurchaseGroupCode, EntityManager.GetEntityMapping(typeof(PurchaseGroup2User)).GetPropertyMapping("PurchGroupCode").DbTypeInfo)
            .List<User>();
        this.rptPurchaseGroup.DataBind();
        User u = new User();
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
                        HtmlInputHidden hidUserID = item.FindControl("hidUserID") as HtmlInputHidden;
                        if (chk != null && chk.Checked && Cast.Int(hidUserID.Value) > 0)
                        {
                            Magic.Basis.PurchaseGroup2User.Delete(session, this.PurchaseGroupCode, Cast.Int(hidUserID.Value));
                            deleted = true;
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        this.QueryAndBindData(session);
                        WebUtil.ShowMsg(this, "选择的采购用户已经被删除", "操作成功");
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
    protected void cmdAddUser_Click(object sender, EventArgs e)
    {
        string[] userList = this.txtUserList.Value.Trim().Split(';');
        bool added = false;
        using (ISession session = new Session())
        {
            foreach (string s in userList)
            {
                if (!string.IsNullOrEmpty(s) && Cast.Int(s, 0) > 0)
                {
                    PurchaseGroup2User pgu = PurchaseGroup2User.Retrieve(session, this.PurchaseGroupCode, Cast.Int(s, 0));
                    if (pgu == null)
                    {
                        pgu = new PurchaseGroup2User();
                        pgu.PurchGroupCode = this.PurchaseGroupCode;
                        pgu.UserID = Cast.Int(s);
                        pgu.Create(session);
                        added = true;
                    }
                }
            }
            if (added)
            {
                this.QueryAndBindData(session);
            }
        }
    }
}
