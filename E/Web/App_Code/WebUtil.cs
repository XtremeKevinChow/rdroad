using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Reflection;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Magic.Framework.Utils;
using Magic.Web.UI;
using Magic.Framework.File;


/// <summary>
/// 窗体打开模式
/// </summary>
public enum WindowTarget
{
    _blank = 1,
    _self = 2,
    _top = 3,
    _parent = 4,
    _back = 5,
    _close = 6
}

/// <summary>
/// WebUtil 的摘要说明
/// </summary>
public sealed class WebUtil
{
    static log4net.ILog logger = log4net.LogManager.GetLogger(typeof(WebUtil));
    private static System.Text.RegularExpressions.Regex regexp = new System.Text.RegularExpressions.Regex("\r|\n", System.Text.RegularExpressions.RegexOptions.IgnoreCase);
    private static int _funcID = 1;

    static WebUtil()
    {

    }
    private static string FormatMessage(string msg)
    {
        return regexp.Replace(msg, "").Replace("\"", "\\\"");
    }

    /// <summary>
    /// 客户端显示消息
    /// </summary>
    /// <param name="page"></param>
    /// <param name="strMsg"></param>
    public static void ShowMsg(Page page, string msgContent)
    {
        // page.Response.Write(GetAlertScript(strMsg));
        //mm.msg({msg:"部门代码或名称必须填写", title:"资料不完整", timeout:2, width:180, valign: "top-in", vmargin:100 });
        ShowMsg(page, msgContent, "提示", 1, 250, "top-in", "100");
    }

    public static void ShowMsg(Page page, string msgContent, string title)
    {
        ShowMsg(page, msgContent, title, 2, 250, "top-in", "100");
    }

    public static void ShowMsg(Page page, string msgContent, string title, int timeout, int width, string valign, string vmargin)
    {
        string script = string.Format("mm.msg({{msg:\"{0}\", title:\"{1}\", timeout:{2}, width:{3},height: 20, valign: \"{4}\", vmargin:{5} }});",
                                               FormatMessage(msgContent), title, timeout, width, valign, vmargin);
        ClientScript(page, script);

    }

    public static void ShowMsg(Page page, string msgContent, string title, int timeout)
    {
        ShowMsg(page, msgContent, title, 0, 250, "top-in", "100");
    }

    public static void ShowMsg(Page page, string msgContent, string title, int timeout, int width, int height, string valign, string vmargin)
    {
        string script = string.Format("mm.msg({{msg:\"{0}\", title:\"{1}\", timeout:{2}, width:{3},height: {4}, valign: \"{5}\", vmargin:{6} }});",
                                                       FormatMessage(msgContent), title, timeout, width, height, valign, vmargin);
        ClientScript(page, script);
    }

    public static void ShowMsg(Page page, string msgContent, string title, int timeout, int width, int height)
    {
        ShowMsg(page, FormatMessage(msgContent), title, timeout, width, height, "top-in", "100");
    }

    public static void ShowError(Page page, string errorInfo)
    {
        ShowError(page, errorInfo, 250, 50);
    }

    public static void ShowError(Page page, Exception ex)
    {
        ShowError(page, ex.Message, 250, 50);
    }
    public static void ShowError(Page page, string ex, int width, int height)
    {
        string script = string.Format("mm.msg({{msg:\"{0}\", title:\"{1}\",  width:{2}, height: {3}, valign: \"{4}\", vmargin:{5} }});",
                                               FormatMessage(ex), "错误", width, height, "top-in", "100");
        ClientScript(page, script);
    }

    /// <summary>
    /// 确定跳转
    /// </summary>
    /// <param name="strMsg"></param>
    /// <param name="strUrl"></param>
    public static void AlertReload(string strMsg, string strUrl)
    {
        HttpResponse httpResponse = HttpContext.Current.Response;
        httpResponse.Write(String.Format("<script type=\"text/javascript\" defer>alert(unescape('{0}'));window.location.href='{1}';</script>", Microsoft.JScript.GlobalObject.escape(strMsg), strUrl));
        httpResponse.End();
    }


    private static string GetAlertScript(string strMsg)
    {
        return
            string.Format("<script type=\"text/javascript\" defer>alert(unescape('{0}'));</script>", Microsoft.JScript.GlobalObject.escape(strMsg));

    }

    /// <summary>
    /// 确定跳转
    /// </summary>
    /// <param name="strMsg"></param>
    /// <param name="strUrl"></param>
    /// <param name="isEnd"></param>
    public static void AlertReload(string strMsg, string strUrl, bool isEnd)
    {
        HttpResponse httpResponse = HttpContext.Current.Response;
        httpResponse.Write(string.Concat(new string[] { "<script type=\"text/javascript\" defer>alert('", strMsg.Replace("'", "\\'").Replace("\r\n", @"\r\n"), "');window.location.href='", strUrl, "';</script>" }));
        if (isEnd)
        {
            httpResponse.End();
        }
    }

    /// <summary>
    /// 执行客户端脚本
    /// </summary>
    /// <param name="scriptInner"></param>
    public static void ExecClientScrpt(Page page, string key, string scriptInner)
    {
        string script = string.Format("<script type=\"text/javascript\" defer>{0}</script>", scriptInner);
        if (!page.ClientScript.IsClientScriptBlockRegistered(page.GetType(), page.Title))
        {
            page.ClientScript.RegisterStartupScript(page.GetType(), key, script);
        }

    }

    /// <summary>
    /// 确定跳转
    /// </summary>
    /// <param name="strMsg"></param>
    /// <param name="strUrl"></param>
    /// <param name="tag"></param>
    /// <param name="isEnd"></param>
    public static void AlertReload(string strMsg, string strUrl, WindowTarget tag, bool isEnd)
    {
        HttpResponse httpResponse = HttpContext.Current.Response;
        httpResponse.Write("<script type=\"text/javascript\" defer>");
        httpResponse.Write("alert('" + strMsg.Replace("'", "\\'").Replace("\r\n", @"\r\n") + "');");
        switch (((int)tag))
        {
            case 1:
                {
                    httpResponse.Write("window.open(\"" + strUrl + "\");");
                    break;
                }
            case 2:
                {
                    httpResponse.Write("window.location.href='" + strUrl + "';");
                    break;
                }
            case 3:
                {
                    httpResponse.Write("window.top.location.href='" + strUrl + "';");
                    break;
                }
            case 4:
                {
                    httpResponse.Write("window.parent.location.href='" + strUrl + "';");
                    break;
                }
            case 5:
                {
                    httpResponse.Write("window.history.back();");
                    break;
                }
            default:
                {
                    httpResponse.Write("window.location.href='" + strUrl + "';");
                    break;
                }
        }
        httpResponse.Write("</script>");
        if (isEnd)
        {
            httpResponse.End();
        }
    }

    /// <summary>
    /// 给定Value从dropdownlist中查找对应的Text，没有返回空字符串
    /// </summary>
    /// <param name="dropdownlist"></param>
    /// <param name="value"></param>
    /// <param name="ignoreCase"></param>
    public static string FindTextByValue(ListControl dropdownlist, object value, bool ignoreCase)
    {
        if (dropdownlist != null && dropdownlist.Items.Count > 0 && value != null)
        {
            string strValue = value.ToString();

            foreach (ListItem item in dropdownlist.Items)
            {
                if (ignoreCase)
                {
                    if (item.Value.Trim().ToLower() == strValue.Trim().ToLower())
                        return item.Text;
                }
                else
                {
                    if (item.Value.Trim() == strValue.Trim())
                        return item.Text;
                }
            }
        }
        return "";
    }

    public static string GetWebRootUrl()
    {
        return "http://" + HttpContext.Current.Request.Url.Authority + HttpContext.Current.Request.ApplicationPath + "/";
    }

    /// <summary>
    /// 向url追加参数
    /// </summary>
    /// <param name="url"></param>
    /// <param name="name"></param>
    /// <param name="value"></param>
    /// <returns></returns>
    public static string AppendParam(string url, string name, string value)
    {
        string val = Microsoft.JScript.GlobalObject.escape(value);
        if (url.IndexOf('?') >= 0)
            return url + "&" + name + "=" + val;
        else
            return url + "?" + name + "=" + val;
    }

    /// <summary>
    /// 获取Request参数
    /// </summary>
    /// <param name="name"></param>
    /// <returns></returns>
    public static string Param(string name)
    {
        string result = HttpContext.Current.Request[name];
        if (!string.IsNullOrEmpty(result))
        {
            if (result.IndexOf('%') >= 0 && result.IndexOf("&return") <= 0)
                result = Microsoft.JScript.GlobalObject.unescape(HttpContext.Current.Request[name]);
        }
        else result = "";
        return result;
    }
    /// <summary>
    /// 获取int型Request参数
    /// </summary>
    /// <param name="name"></param>
    /// <param name="defaultValue"></param>
    /// <returns></returns>
    public static int ParamInt(string name, int defaultValue)
    {
        return Cast.Int(Param(name), defaultValue);

    }
    /// <summary>
    /// 获取decimal型Request参数
    /// </summary>
    /// <param name="name"></param>
    /// <param name="defaultValue"></param>
    /// <returns></returns>
    public static decimal ParamDecimal(string name, decimal defaultValue)
    {
        return Cast.Decimal(Param(name), defaultValue);
    }

    public static void ClientScript(Page page, string scripts)
    {
        string functionName = "fn_" + (_funcID++).ToString();
        page.ClientScript.RegisterStartupScript(page.GetType(), functionName, "<script type=\"text/javascript\">function " + functionName + "(){" + scripts + "} $(document).ready(" + functionName + ");</script>");
    }

    /// <summary>
    /// 是否数字字符串
    /// </summary>
    /// <param name="strInput">输入字符串</param>
    /// <returns></returns>
    public static bool IsNumber(string strInput)
    {
        return new System.Text.RegularExpressions.Regex("^[0-9]+$").Match(strInput).Success;
    }

    /// <summary>
    /// 从列表控件获取选定的值
    /// </summary>
    /// <param name="listControl"></param>
    /// <returns></returns>
    public static IList<string> GetSelectedValues(ListControl listControl)
    {
        //  string[] selValues = null;
        IList<string> valList = new List<string>();

        if (listControl == null)
            return valList;

        foreach (ListItem item in listControl.Items)
        {
            if (item.Selected)
            {
                if (!string.IsNullOrEmpty(item.Value))
                    valList.Add(item.Value);
            }
        }
        return valList;
    }

    /// <summary>
    /// 获取指定Enum类型的值列表
    /// </summary>
    /// <typeparam name="enumT"></typeparam>
    /// <param name="listControl"></param>
    /// <returns></returns>
    public static IList<enumT> GetSelectedValues<enumT>(ListControl listControl)
    {
        IList<enumT> valList = new List<enumT>();
        if (listControl == null) return null;

        foreach (ListItem item in listControl.Items)
        {
            if (item.Selected)
            {
                if (!string.IsNullOrEmpty(item.Value))
                    valList.Add(Cast.Enum<enumT>(item.Value));
            }
        }
        return valList;
    }

    ///// <summary>
    ///// 向listControl设置选中的项
    ///// </summary>
    ///// <param name="listControl"></param>
    ///// <param name="values"></param>
    //public static void SetSelectedValues(ListControl listControl, IEnumerable<string> values)
    //{
    //    if (values != null && listControl != null && listControl.Items.Count > 0)
    //    {
    //        IList<string> temps = new List<string>(values);
    //        int i = 0;
    //        foreach (ListItem item in listControl.Items)
    //        {
    //            item.Selected = false;
    //            for (i = 0; i < temps.Count; i++)
    //            {
    //                if (item.Value == temps[i])
    //                {
    //                    item.Selected = true;
    //                    break;
    //                }
    //            }
    //            // temps.RemoveAt(i);
    //        }
    //    }
    //}
    /// <summary>
    /// 
    /// </summary>
    /// <param name="listControl"></param>
    /// <param name="values"></param>
    public static void SetSelectedValues(ListControl listControl, IEnumerable<object> values)
    {
        if (values != null && listControl != null && listControl.Items.Count > 0)
        {
            foreach (ListItem item in listControl.Items)
            {
                item.Selected = false;
                IEnumerator<object> enumberator = values.GetEnumerator();
                while (enumberator.MoveNext())
                {
                    string vale = Cast.String(enumberator.Current);
                    if (string.Compare(vale, item.Value) == 0)
                    {
                        item.Selected = true;
                        break;
                    }
                }
            }
        }
    }

    public static void SetSelectedValues(ListControl listControl, DataTable dt, string valueColumnName)
    {
        if (dt != null && listControl != null && listControl.Items.Count > 0)
        {
            foreach (ListItem item in listControl.Items)
            {
                item.Selected = false;
                foreach (DataRow row in dt.Rows)
                {
                    if (string.Compare(item.Value, Convert.ToString(row[valueColumnName])) == 0)
                    {
                        item.Selected = true;
                        dt.Rows.Remove(row);
                        break;
                    }
                }
            }
        }
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="pager"></param>
    /// <param name="pageSize"></param>
    /// <param name="recordCount"></param>
    /// <param name="currentIndex"></param>
    public static void SetMagicPager(MagicPager pager, int pageSize, int recordCount, int currentIndex)
    {
        pager.RecordCount = recordCount;
        SetMagicPager(pager, pageSize, currentIndex);
    }

    /// <summary>
    /// 设置翻页控件的属性，并且添加CustomInfo
    /// </summary>
    /// <param name="pager"></param>
    /// <param name="pageSize"></param>
    /// <param name="pageIndex"></param>
    public static void SetMagicPager(MagicPager pager, int pageSize, int pageIndex)
    {
        pager.Visible = true;
        pager.AlwaysShow = true;
        pager.PageSize = pageSize;
        pager.CurrentPageIndex = pageIndex;
        pager.CustomInfoText = "";
        pager.ShowMaxPageCountBox = false;
        pager.ShowPageSizeBox = false;

        //pager.CustomInfoText += string.Format("<span class='pager_text'>记录总数：</span><span style='font-weight:bold;' class='pager_text'>{0}</span>", pager.RecordCount);
        if (pager.RecordCount > 0)
        {
            pager.CustomInfoText += string.Format("<span style='font-weight:bold;' class='pager_text'>{1}</span><span class='pager_text'>/{0}&nbsp;</span>", pager.PageCount, pageIndex);
        }
        pager.Layout = LayoutType.Div;
        pager.PagingButtonType = PagingButtonType.Text;
        pager.PagingButtonSpacing = 2;
        pager.FirstPageText = "首页";
        pager.PrevPageText = "上一页";
        pager.NextPageText = "下一页";
        pager.LastPageText = "尾页";
        pager.TextBeforeInputBox = "";
        pager.TextAfterInputBox = "&nbsp;页&nbsp;";
        pager.ShowPageIndex = false;
        pager.CustomInfoTextAlign = HorizontalAlign.Right;
        pager.ShowInputBox = ShowInputBox.Always;
        pager.CustomInfoSectionWidth = Unit.Pixel(280);
    }

    /// <summary>
    /// 获取当前页面的操作状态
    /// </summary>
    /// <param name="page"></param>
    /// <returns></returns>
    public static Mode GetActionMode(Page page)
    {
        Mode mode = Mode.Undefined;
        if (page != null && page.Request["mode"] != null)
        {
            mode = (Mode)Enum.Parse(typeof(Mode), page.Request["mode"], true);

        }
        return mode;
    }

    /// <summary>
    /// string的字符数组转换成int型的数组
    /// </summary>
    /// <param name="vals"></param>
    /// <returns></returns>
    public static int[] ToIntArray(params string[] vals)
    {
        List<int> tempList = new List<int>();
        int[] results = null;
        int tempId = -1;
        foreach (string sid in vals)
        {
            if (int.TryParse(sid, out tempId))
            {
                tempList.Add(tempId);
            }
        }

        if (tempList.Count > 0)
        {
            results = new int[tempList.Count];
            tempList.CopyTo(results, 0);
        }
        return results;
    }

    /// <summary>
    /// 将指定的DataTable数据写入CSV文件中
    /// </summary>
    /// <param name="fileName"></param>
    /// <param name="columnTexts">可以为空</param>
    /// <param name="columnName">可以为空</param>
    /// <param name="dt"></param>
    /// <returns>ResouceKey</returns>
    public static string Write2CSVFile(string fileName, string[] columnTexts, string[] writeColumns, DataTable dt)
    {

        string filePath = HttpContext.Current.Server.MapPath("../temp/" + fileName);
        FileStoreManager fsm = new FileStoreManager("Download");
        fsm.FileNameSavedAs = fileName;
        string msg = string.Empty;
        string resouceKey = string.Empty;
        bool success = false;
        bool givenColumnNames = writeColumns != null && writeColumns.Length > 0;
        if (dt != null)
        {
            int dtColCount = dt.Columns.Count;
            using (StreamWriter sw = new StreamWriter(fsm.OutputStream, Encoding.GetEncoding("GB2312")))
            {
                if (columnTexts != null && columnTexts.Length > 0)
                {
                    sw.Write(columnTexts[0]);
                    for (int i = 1; i < columnTexts.Length; i++)
                    {
                        sw.Write(string.Format(",{0}", columnTexts[i]));
                    }
                    sw.WriteLine();
                }

                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    if (givenColumnNames)
                    {
                        for (int j = 0; j < writeColumns.Length; j++)
                        {
                            if (j > 0)
                                sw.Write(",");
                            sw.Write(dt.Rows[i][writeColumns[j]]);
                        }
                    }
                    else
                    {

                        for (int j = 0; j < dtColCount; j++)
                        {
                            if (j > 0)
                                sw.Write(",");
                            sw.Write(dt.Rows[i][j]);
                        }
                    }
                    sw.WriteLine();
                }

                fsm.Upload(out msg, out resouceKey, false);
            }
            return resouceKey;
        }
        return "";
    }


    public static void DownloadFile(Page page, string resouceKey)
    {
        string script = string.Format("window.open('../download.aspx?key={0}','download', 'height=150, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');", Microsoft.JScript.GlobalObject.escape(resouceKey));
        ClientScript(page, script);
    }

    public static string RenderUserControl(string virtualPath, string[] propertyNames, object[] propertyValues)
    {
        try
        {
            UserControl userControl = new UserControl();
            userControl = userControl.LoadControl(virtualPath) as UserControl;
            System.Type type = userControl.GetType();
            if (propertyNames != null && propertyValues != null && propertyNames.Length == propertyValues.Length)
            {
                for (int i = 0; i < propertyValues.Length; i++)
                {
                    PropertyInfo prop = type.GetProperty(propertyNames[i]);
                    if (prop != null)
                    {
                        prop.SetValue(userControl, propertyValues[i], null);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            using (TextWriter tw = new StringWriter(sb))
            {
                HtmlTextWriter htw = new HtmlTextWriter(tw);
                userControl.RenderControl(htw);
            }
            return sb.ToString();
        }
        catch (Exception ex)
        {
            logger.Error("呈现用户控件错误", ex);
            throw new ApplicationException("发生未处理的错误，请查看日志文件！", ex);

        }
    }


    /// <summary>
    /// 格式化挂号时间
    /// </summary>
    /// <param name="registerTimeVal"></param>
    /// <returns></returns>
    public static string FormatRegisterTime(object registerTimeVal)
    {
        DateTime registerTime = Cast.DateTime(registerTimeVal);
        return string.Format("{0} {1}", registerTime.ToString("MM月dd日"), EnumUtil.GetEnumTypeText<DayOfWeek>(registerTime.DayOfWeek));
    }

    public static void EnableControl(object control)
    {
        EnableControl(control, true);
    }
    public static void DisableControl(object control)
    {
        EnableControl(control, false);
    }
    private static void EnableControl(object control, bool enable)
    {
        if (control == null) return;
        Type type = control.GetType();
        switch (type.Name)
        {
            case "TextBox":
                TextBox textbox = control as TextBox;
                if (enable)
                {
                    if (textbox.CssClass != null && textbox.CssClass.IndexOf("readonly") > 0)
                        textbox.CssClass = textbox.CssClass.Replace("readonly", "");
                    textbox.Attributes.Remove("readonly");
                }
                else
                {
                    if (textbox.CssClass == null || textbox.CssClass.IndexOf("readonly") < 0)
                        textbox.CssClass = textbox.CssClass + " readonly";
                    textbox.Attributes["readonly"] = "readonly";
                }
                break;
            case "HtmlInputText":
                HtmlInputText input = control as HtmlInputText;
                if (enable)
                {
                    if (input.Attributes["class"] != null && input.Attributes["class"].IndexOf("readonly") > 0)
                        input.Attributes["class"] = input.Attributes["class"].Replace("readonly", "");
                    input.Attributes.Remove("readonly");
                }
                else
                {
                    if (input.Attributes["class"] == null || input.Attributes["class"].IndexOf("readonly") < 0)
                        input.Attributes["class"] = input.Attributes["class"] + " readonly";
                    input.Attributes["readonly"] = "readonly";
                }
                break;
            case "DropDownList":
                DropDownList ddl = control as DropDownList;
                ddl.Enabled = enable;
                break;
            case "CheckBox":
                CheckBox chk = control as CheckBox;
                chk.Enabled = enable;
                break;
        }
    }
    public static string ShippingNoticeNumberLength
    {
        get { return "12"; }
    }

    public static string escape(string s)
    {
        return Microsoft.JScript.GlobalObject.escape(s);
    }
    public static string unescape(string s)
    {
        return Microsoft.JScript.GlobalObject.unescape(s);
    }
    public static log4net.ILog Logger(Type page)
    {
        if (page == null) return log4net.LogManager.GetLogger("ERP.Web");

        StringBuilder builder = new StringBuilder();
        if (page.Name.IndexOf("_") < 0)
            builder.Append("ERP.Web.").Append(page.Name);
        else
        {
            string[] tokens = page.Name.Split('_');
            builder.Append("ERP.Web");
            foreach (string s in tokens)
                builder.Append(".").Append(s);
        }
        return log4net.LogManager.GetLogger(builder.ToString());
    }
    public static void DownloadHack(Page page, string dateFromId, string dateToId)
    {
        //点击下载按钮之后日期框后面的选择按钮消失了
        StringBuilder script = new StringBuilder();
        script.Append("<script type='text/javascript'>");
        if (!string.IsNullOrEmpty(dateFromId) && dateFromId.Trim().Length > 0)
            script.Append("$('#").Append(dateFromId.Trim()).Append("').datePicker({startDate: '2008-01-01'});");
        if (!string.IsNullOrEmpty(dateToId) && dateToId.Trim().Length > 0)
            script.Append("$('#").Append(dateToId.Trim()).Append("').datePicker({startDate: '2008-01-01'});");
        script.Append("</script>");
        page.RegisterStartupScript("download_hack",  script.ToString());
    }
}