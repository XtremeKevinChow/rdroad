using System;
using System.Collections.Generic;
using System.Data;
using Magic.Framework.Utils;
using Magic.Framework.ORM;
using Magic.Web.UI;
using Magic.Basis;
using Magic.COMObject;
using Magic.ERP.Orders;

public partial class Basis_InventoryCheckImport : System.Web.UI.Page
{
    log4net.ILog logger = WebUtil.Logger(typeof(Basis_InventoryCheckImport));

    protected void Page_Load(object sender, EventArgs e)
    {
        this.toolbarbottom["Return"].NavigateUrl = WebUtil.Param("return");
    }

    protected void MagicItemCommand(object sender, MagicItemEventArgs e)
    {
        if (e.CommandName == "Save")
        {
            if (this.FileUpload1.FileName.Trim().Length <= 0)
            {
                WebUtil.ShowMsg(this, "请选择盘点结果文件");
                return;
            }
            string fileName = this.FileUpload1.FileName;
            if (!fileName.EndsWith(".xls"))
            {
                WebUtil.ShowMsg(this, "请选择有效的Excel文件");
                return;
            }
            string filePath = System.IO.Path.Combine(DownloadUtil.DownloadFolder, "CK_IMP_" + DateTime.Now.ToString("yyMMdd_HHmmss") + ".xls");
            this.FileUpload1.SaveAs(filePath);
            IList<INVCheckLine> lines = new List<INVCheckLine>();

            #region 读取文件
            ExcelApp excelapp = null;
            ExcelWorkbook excelBook = null;
            ExcelWorksheet excelSheet = null;
            try
            {
                excelapp = new ExcelApp();
                excelapp.DisplayAlerts = false;
                excelBook = excelapp.Open(filePath, 0);
                excelSheet = excelBook.Worksheets(1);
                int rowIndex = 2;
                string lineNum = Cast.String(excelSheet.Range(rowIndex, rowIndex, 1, 1).Value).Trim();
                decimal qty;
                while (lineNum.Length==4)
                {
                    qty = Cast.Decimal(excelSheet.Range(rowIndex, rowIndex, 9, 9).Value, 0M);
                    INVCheckLine line = new INVCheckLine();
                    line.LineNumber = lineNum;
                    line.CurrentQty = qty;
                    lines.Add(line);
                    rowIndex++;
                    lineNum = Cast.String(excelSheet.Range(rowIndex, rowIndex, 1, 1).Value).Trim();
                }
            }
            catch (Exception er)
            {
                WebUtil.ShowError(this, er.Message);
                return;
            }
            finally
            {
                if (excelSheet != null)
                    System.Runtime.InteropServices.Marshal.ReleaseComObject(excelSheet.COMObject);
                if (excelBook != null)
                {
                    excelBook.Close();
                    System.Runtime.InteropServices.Marshal.ReleaseComObject(excelBook.COMObject);
                }
                if (excelapp != null)
                {
                    excelapp.Quit();
                    System.Runtime.InteropServices.Marshal.ReleaseComObject(excelapp.COMObject);
                }
            }
            #endregion

            using (ISession session = new Session())
            {
                try
                {
                    INVCheckHead head = INVCheckHead.Retrieve(session, WebUtil.Param("ordNum"));
                    if (head == null)
                    {
                        WebUtil.ShowError(this, "盘点单"+WebUtil.Param("ordNum")+"不存在");
                        return;
                    }
                    session.BeginTransaction();
                    head.ClearCheckQty(session);
                    head.UpdateLines(session, lines);
                    session.Commit();
                }
                catch (Exception er)
                {
                    session.Rollback();
                    WebUtil.ShowError(this, er.Message);
                    return;
                }
            }

            this.Response.Redirect(WebUtil.Param("return"));
        }
    }
}