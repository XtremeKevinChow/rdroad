using System;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Basis;

public partial class Basis_ExcelTemplate : System.Web.UI.Page
{
    Mode _actionMode = Mode.Undefined;
    ISession _session = null;
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(Logistics));

    protected void Page_Load(object sender, EventArgs e)
    {
        if (Request["return"] != null) this.toolbarbottom["Return"].NavigateUrl = Request["return"];
        if (!IsPostBack)
        {
            this.txtId.Value = WebUtil.ParamInt("id", 0).ToString();
            using (_session = new Session())
            {
                _actionMode = WebUtil.GetActionMode(this);
                if (_actionMode == Mode.Edit) ShowInfo(_session);
            }
        }
    }

    private void ShowInfo(ISession session)
    {
        int id = WebUtil.ParamInt("id", 0);
        if (id > 0)
        {
            ExcelTemplate t = ExcelTemplate.Retrieve(session, id);
            if (t == null) return;
            this.txtName.Text = t.TemplateName;
            if (t.Status== ExcelTemplateStatus.Enable)
                this.drpStatus.SelectedValue = ((int)ExcelTemplateStatus.Enable).ToString();
            else
                this.drpStatus.SelectedValue = ((int)ExcelTemplateStatus.Disable).ToString();
        }
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            bool saved = false;
            using (ISession session = new Session())
            {
                ExcelTemplate template;
                if (this.IsAddNew())
                {
                    if (!this.FileUpload1.HasFile)
                    {
                        WebUtil.ShowError(this, "请选择模板文件");
                        return;
                    }
                    if (!this.FileUpload1.FileName.EndsWith(".xls"))
                    {
                        WebUtil.ShowError(this, "请选择有效的Excel文件");
                        return;
                    }
                    string fileName = DateTime.Now.ToString("SN_yyMMdd_HHmmss") + ".xls";
                    string virtualPath = "/Template/" + fileName;
                    this.FileUpload1.SaveAs(Server.MapPath(virtualPath));
                    template = new ExcelTemplate();
                    template.TemplateName = this.txtName.Text.Trim();
                    template.Status = ExcelTemplateStatus.Enable;
                    template.FileVirtualPath = virtualPath;
                    template.Create(session);
                    saved = true;
                }
                else
                {
                    template = ExcelTemplate.Retrieve(session, Cast.Int(this.txtId.Value));
                    if (template == null) return;

                    if (this.FileUpload1.HasFile)
                        //保存文件
                        this.FileUpload1.SaveAs(Server.MapPath(template.FileVirtualPath));
                    template.TemplateName = this.txtName.Text.Trim();
                    if (this.drpStatus.SelectedValue == ((int)ExcelTemplateStatus.Enable).ToString())
                        template.Status = ExcelTemplateStatus.Enable;
                    else
                        template.Status = ExcelTemplateStatus.Disable;
                    template.Update(session, "TemplateName", "Status");
                    saved = true;
                }
            }
            if (saved)
                this.Response.Redirect("ExcelTemplateManager.aspx");
        }
    }

    private bool IsAddNew()
    {
        return Cast.Int(this.txtId.Value.Trim(), -1) <= 0;
    }
}