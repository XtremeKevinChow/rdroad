using System;
using System.Data;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Web;
using Magic.Framework.Utils;
using Magic.COMObject;

public enum DataType
{
    Text = 1,
    NumberText = 4,
    Number = 2,
    Date = 3,
}
public class DownloadFormat
{
    public DownloadFormat(DataType type, string title, params object[] index)
    {
        this.Type = type;
        this.Title = title;
        this.ColumnIndex = index;
    }
    public DataType Type;
    public string Title;
    public object[] ColumnIndex;
}

public static class DownloadUtil
{
    public static string DownloadFolder
    {
        get
        {
            string folder = System.Configuration.ConfigurationManager.AppSettings["downloadFolder"];
            if (folder == null) folder = HttpContext.Current.Server.MapPath("/Download/");
            if (!folder.EndsWith("/") && !folder.EndsWith("\\"))
                folder = folder + "\\";
            return folder;
        }
    }
    /// <summary>
    /// 简单格式下载，只下载<paramref name="ds"/>的数据行
    /// </summary>
    /// <param name="name">用户下载框中显示的保存文件名，例如：SN_08082600012.xls</param>
    /// <param name="prefix">内部生成的下载文件前缀，例如：SN</param>
    /// <param name="format">数据列的格式描述信息</param>
    /// <param name="ds"></param>
    /// <returns>返回下载文件的链接地址（使用download.aspx）</returns>
    public static string DownloadXls(string name, string prefix, IList<DownloadFormat> format, DataSet ds)
    {
        string fileName = prefix + DateTime.Now.ToString("_yyMMdd_HHmmss") + ".xls";
        string filePath = DownloadFolder + fileName;

        ExcelApp excelapp = null;
        ExcelWorkbook excelBook = null;
        ExcelWorksheet excelSheet = null;
        try
        {
            excelapp = new ExcelApp();
            excelapp.DisplayAlerts = false;
            excelBook = excelapp.NewWorkBook();
            excelSheet = excelBook.Worksheets(1);
            int rowIndex = 1;

            for (int i = 0; i < format.Count; i++)
                excelSheet.Cells(rowIndex, i + 1).Value = format[i].Title;
            ExcelRange rg = excelSheet.Range(rowIndex, rowIndex, 1, format.Count);
            rg.SelectRange();
            rg.Font.Bold = true;
            rg.HorizontalAlignment = 3;
            rg.Interior.SetColor(221, 221, 221);
            rowIndex++;

            #region 写文件
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                for (int i = 0; i < format.Count; i++)
                {
                    DownloadFormat ft = format[i];
                    if (ft.ColumnIndex == null)
                        continue;
                    for (int j = 0; j < ft.ColumnIndex.Length; j++)
                        SetCellValue(excelSheet.Cells(rowIndex, i + 1), j, ft.Type, row, ft.ColumnIndex[j]);
                }
                rowIndex++;
            }
            #endregion

            ExcelRange excelRange = excelSheet.Cells();
            excelRange.SelectRange();
            excelRange.AutoFit();
            excelRange.Font.Size = 10;
            excelBook.SaveAs(filePath);
        }
        catch (Exception er)
        {
            throw er;
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

        return "/download.aspx?type=p&name=" + Microsoft.JScript.GlobalObject.escape(name) + "&path=" + Microsoft.JScript.GlobalObject.escape(filePath);
    }
    /// <summary>
    /// 模板格式下载，用<paramref name="items"/>中每个key, value替换模板中的标签（标签名称为key）
    /// </summary>
    /// <param name="name">用户下载框中显示的保存文件名，例如：SN_08082600012.xls</param>
    /// <param name="prefix">内部生成的下载文件前缀，例如：SN</param>
    /// <param name="template">模板文件的物理路径（不是IIS虚拟路径）</param>
    /// <param name="items">标签的键、值对</param>
    /// <returns>返回下载文件的链接地址（使用download.aspx）</returns>
    public static string DownloadXls(string name, string prefix, string template, IDictionary<string, string> items)
    {
        ExcelApp excelApp = null;
        ExcelWorkbook workbook = null;
        ExcelWorksheet sheet = null;

        string fileName = prefix + DateTime.Now.ToString("_yyMMdd_HHmmss") + ".xls";
        string filePath = DownloadFolder + fileName;
        try
        {
            excelApp = new ExcelApp();
            workbook = excelApp.Open(template, 0);
            workbook.SaveAs(filePath);
            sheet = workbook.Worksheets(1);
            ExcelRange range = sheet.Cells();
            foreach (KeyValuePair<string, string> kv in items)
                range.Replace(kv.Key, kv.Value, false);
            workbook.Save();
        }
        catch (Exception er)
        {
            throw er;
        }
        finally
        {
            if (sheet != null)
                System.Runtime.InteropServices.Marshal.ReleaseComObject(sheet.COMObject);
            if (workbook != null)
            {
                workbook.Close();
                System.Runtime.InteropServices.Marshal.ReleaseComObject(workbook.COMObject);
            }
            if (excelApp != null)
            {
                excelApp.Quit();
                System.Runtime.InteropServices.Marshal.ReleaseComObject(excelApp.COMObject);
            }
        }

        return "/download.aspx?type=p&name=" + Microsoft.JScript.GlobalObject.escape(name) + "&path=" + Microsoft.JScript.GlobalObject.escape(filePath);
    }
    /// <summary>
    /// 模板格式+简单格式下载，先用<paramref name="items"/>中每个key, value替换模板中的标签（标签名称为key），再下载<paramref name="ds"/>的数据行
    /// </summary>
    /// <param name="name">用户下载框中显示的保存文件名，例如：SN_08082600012.xls</param>
    /// <param name="prefix">内部生成的下载文件前缀，例如：SN</param>
    /// <param name="template">模板文件的物理路径（不是IIS虚拟路径）</param>
    /// <param name="items">标签的键、值对</param>
    /// <param name="rowIndex">数据行的开始位置（1开始的索引，即Excel中的行号）</param>
    /// <param name="ds"></param>
    /// <returns>返回下载文件的链接地址（使用download.aspx）</returns>
    public static string DownloadXls(string name, string prefix, string template, IDictionary<string, string> items, int rowIndex, IList<DownloadFormat> format, DataSet ds)
    {
        ExcelApp excelApp = null;
        ExcelWorkbook workbook = null;
        ExcelWorksheet sheet = null;
        string fileName = prefix + DateTime.Now.ToString("_yyMMdd_HHmmss") + ".xls";
        string filePath = DownloadFolder + fileName;

        try
        {
            excelApp = new ExcelApp();
            workbook = excelApp.Open(template, 0);
            workbook.SaveAs(filePath);
            sheet = workbook.Worksheets(1);

            //标签替换
            ExcelRange range = sheet.Cells();
            if (items != null)
                foreach (KeyValuePair<string, string> kv in items)
                    range.Replace(kv.Key, kv.Value, false);

            //数据行
            int index = rowIndex;
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                for (int i = 0; i < format.Count; i++)
                {
                    DownloadFormat ft = format[i];
                    if (ft.ColumnIndex == null)
                        continue;
                    for (int j = 0; j < ft.ColumnIndex.Length; j++)
                        SetCellValue(sheet.Cells(index, i + 1), j, ft.Type, row, ft.ColumnIndex[j]);
                }
                index++;
            }

            workbook.Save();
        }
        catch (Exception er)
        {
            throw er;
        }
        finally
        {
            if (sheet != null)
                System.Runtime.InteropServices.Marshal.ReleaseComObject(sheet.COMObject);
            if (workbook != null)
            {
                workbook.Close();
                System.Runtime.InteropServices.Marshal.ReleaseComObject(workbook.COMObject);
            }
            if (excelApp != null)
            {
                excelApp.Quit();
                System.Runtime.InteropServices.Marshal.ReleaseComObject(excelApp.COMObject);
            }
        }

        return "/download.aspx?type=p&name=" + Microsoft.JScript.GlobalObject.escape(name) + "&path=" + Microsoft.JScript.GlobalObject.escape(filePath);
    }
    private static void SetCellValue(ExcelRange range, int mergeIndex, DataType dataType, DataRow row, object rowIndex)
    {
        object value = null;
        if (rowIndex.GetType() == typeof(int)) value = row[(int)rowIndex];
        else value = row[rowIndex.ToString()];
        if (dataType == DataType.Date)
        {
            DateTime dt = Cast.DateTime(value, new DateTime(1900, 1, 1));
            if (dt > new DateTime(1900, 1, 1)) value = dt.ToString("yyyy-MM-dd");
            else value = " ";
        }
        if (mergeIndex == 0 && dataType == DataType.NumberText)
            range.Value = "'" + Cast.String(value);
        else if (mergeIndex == 0)
            range.Value = value;
        else if (mergeIndex > 0)
        {
            string s = Cast.String(range.Value).Trim();
            s = s.Length <= 0 && dataType == DataType.NumberText ? "'" : s.Length > 0 ? s + " " : s;
            range.Value = s + Cast.String(value);
        }
    }
}