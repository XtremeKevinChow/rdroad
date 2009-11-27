using System;

public partial class download : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            try
            {
                //文件路径，包括文件名：D:\Products\SRM\DataFolder\FileExport\WOItemSearchReport2005041415155.XLS
                string filePath = WebUtil.Param("path").Trim();
                string fileName = WebUtil.Param("name").Trim(); //文件名：WOItem.XLS
                if (filePath.Length <= 0 || fileName.Length <= 0) return;
                //type: p,文件路径为物理目录路径；l,文件路径为IIS虚拟目录路径
                string type = WebUtil.Param("type").ToLower();
                type = type == "p" || type == "l" ? type : "p";
                if (type == "l") filePath = this.Server.MapPath(filePath);

                this.Response.Clear();
                this.Response.Charset = "utf-8";
                System.IO.FileInfo fileInfo = new System.IO.FileInfo(filePath);
                this.Response.ContentType = "application/ms-excel";
                //inline(在线打开)，attachment（下载）
                this.Response.AddHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                //this.Response.AddHeader("Content-Length", fileInfo.Length.ToString());
                this.Response.TransmitFile(fileInfo.FullName);
            }
            catch (Exception Ex)
            {
                this.RegisterStartupScript("downex", "<script language='txt/javascript'>alert('" + Ex.Message.Replace('\'', ' ').Replace("\r", "\\r").Replace("\n", "\\n") + "');</script>");
            }
        }
    }
}
