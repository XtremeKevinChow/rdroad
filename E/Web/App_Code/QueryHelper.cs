using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Text;
using Magic.Framework.Utils;

/// <summary>
/// 查询辅助类
/// 用以管理页面的查询参数等
/// </summary>
public class QueryHelper
{
    Page _page = null;
    Dictionary<string, string> _nameValue = new Dictionary<string, string>();
    StringBuilder _urlBuilder = new StringBuilder();
    bool _useQueryString = true;
   
    /// <summary>
    /// 查询辅助类，默认使用QueryString组织参数
    /// </summary>
    /// <param name="page"></param>
    public QueryHelper(Page page)
        :this(page,true)
    {
        
    }

    /// <summary>
    /// 查询辅助类
    /// </summary>
    /// <param name="page"></param>
    /// <param name="useQueryString">使用QueryString组织参数</param>
    public QueryHelper(Page page, bool useQueryString)
    {
        _page = page;
        _useQueryString = useQueryString;
       
    }

    /// <summary>
    /// 是否有查询的参数
    /// </summary>
    /// <returns></returns>
    public bool HasQueryParameter()
    {
        //return true;
        return (!_page.IsPostBack && _page.Request["flag"] != null);       
    }

    /// <summary>
    /// 加入参数名称和值
    /// </summary>
    /// <param name="name"></param>
    /// <param name="value"></param>
    public void Push(string name, object value)
    {
        if (_useQueryString)
        {
            _urlBuilder.AppendFormat("&{0}={1}", name, Microsoft.JScript.GlobalObject.escape(value));
        }
        else
        {
            _nameValue.Add(name, Cast.String(value));
        }
    }

    /// <summary>
    /// 获取指定名称的参数值
    /// </summary>
    /// <param name="name"></param>
    /// <returns></returns>
    public string Pop(string name)
    {
        return Pop(name, string.Empty);
    }

    /// <summary>
    /// 获取指定名称的参数值，如果没有则返回默认值
    /// </summary>
    /// <param name="name"></param>
    /// <param name="defaultValue"></param>
    /// <returns></returns>
    public string Pop(string name, string defaultValue)
    {

        if (!_useQueryString && _nameValue.ContainsKey(name))
        {
            return _nameValue[name];
        }
        else if(_useQueryString && _page.Request[name] != null)
        {
            return Microsoft.JScript.GlobalObject.unescape(_page.Request[name]);
        }
        return defaultValue;
    }

    #region 重载对各种控件取值
    /// <summary>
    /// 将控件对象中的值取出来，以控件ID作为key，push到参数中
    /// </summary>
    /// <param name="control"></param>
    public void GetValue(Control control)
    {
        bool cannotHandle = false;
        if (control != null)
        {
            string key = control.ID;
            string value = string.Empty;
            if (control is HtmlControl) //HtmlControls
            {
                if (control is HtmlInputControl)
                {
                    if (control is HtmlInputCheckBox)
                    {
                        value = ((HtmlInputCheckBox)control).Checked.ToString();
                    }
                    else if (control is HtmlInputRadioButton)
                    {
                        value = ((HtmlInputRadioButton)control).Checked.ToString();
                    }
                    else if (control is HtmlInputImage)
                    {
                        value = ((HtmlInputImage)control).Src;
                    }
                    else
                    {
                        value = ((HtmlInputControl)control).Value;
                    }
                }
                else if (control is HtmlContainerControl)
                {
                    if (control is HtmlSelect)
                    {
                        value = ((HtmlSelect)control).Value;
                    }
                    else if (control is HtmlTextArea)
                    {
                        value = ((HtmlTextArea)control).Value;
                    }
                    else
                    {
                        cannotHandle = true;
                    }
                }
                else if(control is HtmlImage)
                {
                    value = ((HtmlImage)control).Src;
                }
                else if (control is HtmlTitle)
                {
                    value = ((HtmlTitle)control).Text;
                }
                else
                {
                    cannotHandle = true;
                }
            }
            else if (control is WebControl) // WebControls
            {
                if (control is ListControl)
                {
                    ListControl list = (ListControl)control;
                    IList<string> selValues = WebUtil.GetSelectedValues(list);

                    value = FormatMultiValue(selValues);
                }
                else if (control is ITextControl)
                {
                    ITextControl txt = (ITextControl)control;
                    value = txt.Text;
                }
                else if (control is ICheckBoxControl)
                {
                    ICheckBoxControl chk = (ICheckBoxControl)control;
                    value = chk.Checked.ToString();
                }
                else
                {
                    cannotHandle = true;
                }
            }
            else
            {
                cannotHandle = true;
            }

            if (cannotHandle)
                throw new Exception(string.Format("It hasn't implemented the value getter for control {0}, id is {1};\n\t you would better get the value by youself and then add the value use Push()! ", control.GetType().FullName, control.ID));
            Push(key, value);
        }
    }   
    #endregion
    #region 重载对各种控件的赋值
    /// <summary>
    /// 以控件ID为Key取出参数值，并将该值赋给控件对象
    /// </summary>
    /// <param name="ctrl"></param>
    /// <param name="defaultValue"></param>
    public void SetValue(Control control,  object defaultValue)
    {
        bool cannotHandle = false;
        if (control != null)
        {
            string name = control.ID;
            string value = Pop(name);

            if (control is HtmlControl) //HtmlControls
            {
                if (control is HtmlInputControl)
                {
                    if (control is HtmlInputCheckBox)
                    {
                        ((HtmlInputCheckBox)control).Checked = Magic.Framework.Utils.Cast.Bool(value, Convert.ToBoolean(defaultValue));
                    }
                    else if (control is HtmlInputRadioButton)
                    {
                        ((HtmlInputCheckBox)control).Checked = Magic.Framework.Utils.Cast.Bool(value, Convert.ToBoolean(defaultValue));
                    }
                    else if (control is HtmlInputImage)
                    {
                        ((HtmlInputImage)control).Src = Cast.String(value, defaultValue.ToString());
                    }
                    else
                    {
                        ((HtmlInputControl)control).Value = Cast.String(value, defaultValue.ToString()); ;
                    }
                }
                else if (control is HtmlContainerControl)
                {
                    if (control is HtmlSelect)
                    {
                        ((HtmlSelect)control).Value = Cast.String(value, defaultValue.ToString()); ;
                    }
                    else if (control is HtmlTextArea)
                    {
                        ((HtmlTextArea)control).Value = Cast.String(value, defaultValue.ToString()); ;
                    }
                    else
                    {
                        cannotHandle = true;
                    }
                }
                else if (control is HtmlImage)
                {
                    ((HtmlImage)control).Src = Cast.String(value, defaultValue.ToString()); ;
                }
                else if (control is HtmlTitle)
                {
                    ((HtmlTitle)control).Text = Cast.String(value, defaultValue.ToString()); ;
                }
                else
                {
                    cannotHandle = true;
                }
            }
            else if (control is WebControl) // WebControls
            {
                if (control is ListControl)
                {
                    ListControl list = (ListControl)control;
                    string[] selValues = GetMultiValue(name);
                    if (selValues == null || selValues.Length == 0)
                    {
                        selValues = new string[] { defaultValue.ToString() };
                    }
                    WebUtil.SetSelectedValues(list, selValues);
                }
                else if (control is ITextControl)
                {
                    ITextControl txt = (ITextControl)control;
                    txt.Text = Cast.String(value, defaultValue.ToString()); ;
                }
                else if (control is ICheckBoxControl)
                {
                    ICheckBoxControl chk = (ICheckBoxControl)control;
                    chk.Checked = Cast.Bool(value, Convert.ToBoolean(defaultValue));
                }
                else
                {
                    cannotHandle = true;
                }
            }
            else
            {
                cannotHandle = true;
            }
        }
        if (cannotHandle)
            throw new Exception(string.Format("It hasn't implemented the value getter for control {0}, id is {1};\n\t you would better get the value by youself and then add the value use Push()! ", control.GetType().FullName, control.ID));
    }

    /// <summary>
    /// 以控件ID为Key取出参数值，使用默认值为：
    /// Boolean:false, String:string.emty
    /// </summary>
    /// <param name="ctrl"></param>
    public void SetValue(Control control)
    {
        bool cannotHandle = false;
        if (control != null)
        {
            string name = control.ID;
            string value = Pop(name);

            if (control is HtmlControl) //HtmlControls
            {
                if (control is HtmlInputControl)
                {
                    if (control is HtmlInputCheckBox)
                    {
                        ((HtmlInputCheckBox)control).Checked = Magic.Framework.Utils.Cast.Bool(value);
                    }
                    else if (control is HtmlInputRadioButton)
                    {
                        ((HtmlInputCheckBox)control).Checked = Magic.Framework.Utils.Cast.Bool(value);
                    }
                    else if (control is HtmlInputImage)
                    {
                        ((HtmlInputImage)control).Src = value;
                    }
                    else
                    {
                        ((HtmlInputControl)control).Value = value;
                    }
                }
                else if (control is HtmlContainerControl)
                {
                    if (control is HtmlSelect)
                    {
                        ((HtmlSelect)control).Value = value;
                    }
                    else if (control is HtmlTextArea)
                    {
                        ((HtmlTextArea)control).Value = value;
                    }
                    else
                    {
                        cannotHandle = true;
                    }
                }
                else if (control is HtmlImage)
                {
                    ((HtmlImage)control).Src = value;
                }
                else if (control is HtmlTitle)
                {
                    ((HtmlTitle)control).Text = value;
                }
                else
                {
                    cannotHandle = true;
                }
            }
            else if (control is WebControl) // WebControls
            {
                if (control is ListControl)
                {
                    ListControl list = (ListControl)control;
                    string[] selValues = GetMultiValue(name);
                    if (selValues != null && selValues.Length > 0)
                    {
                        WebUtil.SetSelectedValues(list, selValues);
                    }
                }
                else if (control is ITextControl)
                {
                    ITextControl txt = (ITextControl)control;
                    txt.Text = value;
                }
                else if (control is ICheckBoxControl)
                {
                    ICheckBoxControl chk = (ICheckBoxControl)control;
                    chk.Checked = Cast.Bool(value);
                }
                else
                {
                    cannotHandle = true;
                }
            }
            else
            {
                cannotHandle = true;
            }
        }
        if (cannotHandle)
            throw new Exception(string.Format("It hasn't implemented the value getter for control {0}, id is {1};\n\t you would better get the value by youself and then add the value use Push()! ", control.GetType().FullName, control.ID));
    }
    #endregion

    /// <summary>
    /// 将参数添加到指定的Url后
    /// </summary>
    /// <param name="url"></param>
    /// <returns></returns>
    public string OutputReturnUrl(string url)
    {
        StringBuilder sb = new StringBuilder(url);
        bool haspara = url.IndexOf("?") >= 0;
        if (!haspara) sb.Append("?");
        else sb.Append( "&");
        if (_useQueryString)
        {
            sb.Append("flag=1");
            return sb.Append(_urlBuilder.ToString()).ToString();
        }
        else
        {
            
            if (_nameValue.Count > 0)
            {
                Dictionary<string, string>.Enumerator enumerator = _nameValue.GetEnumerator();
                while (enumerator.MoveNext())
                {
                    sb.AppendFormat(string.Format("{0}={1}&", enumerator.Current.Key, enumerator.Current.Value));
                }
                sb.Append("flag=1");
            }
            return sb.ToString();
        }
    }

    /// <summary>
    /// 以Page的文件名，后面加QueryString(?)
    /// </summary>
    /// <returns></returns>
    public string OutputReturnUrl()
    {
        string url = _page.Request.Path;
        return OutputReturnUrl(url);
    }

   
    #region Private Methods
    private string FormatMultiValue(IEnumerable<string> values)
    {
        StringBuilder sb = new StringBuilder();
        foreach (string str in values)
        {
            sb.AppendFormat("{0}|", str);
        }
        return sb.ToString();
    }

    private string[] GetMultiValue(string name)
    {
        string[] result = null;

        string value = Pop(name);
        if (!string.IsNullOrEmpty(value))
        {
           result =  value.Split(new string[] { "|" }, StringSplitOptions.RemoveEmptyEntries);
        }
        return result;
    }

    
    #endregion
}
