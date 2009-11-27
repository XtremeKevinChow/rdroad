using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Basis;

public partial class Basis_ExcelTemplateManager : System.Web.UI.Page
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
                    foreach (RepeaterItem item in this.rpt.Items)
                    {
                        HtmlInputCheckBox chk = item.FindControl("checkbox") as HtmlInputCheckBox;
                        if (chk != null && chk.Checked && Cast.Int(chk.Value) > 0)
                        {
                            ExcelTemplate.Delete(session, Cast.Int(chk.Value));
                            deleted = true;
                        }
                    }
                    session.Commit();
                    if (deleted)
                    {
                        this.QueryAndBindData(session);
                        WebUtil.ShowMsg(this, "选择的模板文件已经被删除", "操作成功");
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

    private string GetReturnUrl()
    {
        return "ExcelTemplateManager.aspx";
    }

    void QueryAndBindData(ISession session)
    {
        IList<ExcelTemplate> templates = ExcelTemplate.ListAll(session);
        this.rpt.DataSource = templates;
        this.rpt.DataBind();
        this.hidReturnUrl.Value = GetReturnUrl();
    }
}