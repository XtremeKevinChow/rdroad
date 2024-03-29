using System;
using System.Text;
using Magic.Framework.Utils;
using Magic.Framework.File;

/// <summary>
/// 页面处理字符串、日期对象的帮助类
/// </summary>
public sealed class RenderUtil
{
    private RenderUtil()
    {
    }

    /// <summary>
    /// 格式化字符串
    /// </summary>
    /// <param name="val"></param>
    /// <returns></returns>
    public static string FormatString(object val)
    {
        if (val == null || val is DBNull || string.IsNullOrEmpty(val.ToString()))
            return "&nbsp;";
        else
            return val.ToString();
    }

    /// <summary>
    /// 格式化成HTML，回车换行会替换为html换行标签
    /// </summary>
    /// <param name="val"></param>
    /// <returns></returns>
    public static string FormatStringEx(object val)
    {
        if (val == null || val is DBNull || string.IsNullOrEmpty(val.ToString()))
            return "&nbsp;";
        else
            return val.ToString().Replace("\r", "<br />");
    }

    public static string FormatString(object value, IFormatProvider formatProvider)
    {
        if (value == null || string.IsNullOrEmpty(value.ToString()))
            return "&nbsp;";
        else
            return Convert.ToString(value, formatProvider);
    }

    /// <summary>
    /// format object to string, if the length of string is greater than the given length, return the trancted string
    /// </summary>
    /// <param name="val"></param>
    /// <param name="maxLength"></param>
    /// <returns></returns>
    public static string FormatString(object val, int length)
    {
        if (val == null || string.IsNullOrEmpty(val.ToString()))
            return "&nbsp;";
        else
        {
            string s = val.ToString();
            if (s.Length >= length)
                s = s.Substring(0, length) + "...";
            return s;
        }
    }

    /// <summary>
    /// 得到字符串的长度。注意如果是中文，将算2个字符串
    /// </summary>
    /// <param name="str">需要求长的字符串</param>
    /// <returns>字符串长度</returns>
    public static int GetAnsiStringLength(string str)
    {
        int result = 0;
        for (int i = 0; i < str.Length; ++i)
            result += (str[i] > 255) ? 2 : 1;
        return result;
    }

    /// <summary>
    /// 按照ANSI计算字符串长度，并截取字符串；
    /// 中文字符长度需要乘2倍，
    /// 比如：“上海轶伦信息技术有限公司”，截取前4个字符，则参数length应为:8
    /// </summary>
    /// <param name="value"></param>
    /// <param name="length"></param>
    /// <returns></returns>
    public static string AnsiSubstring(object value, int length)
    {
        if (value == null || string.IsNullOrEmpty(value.ToString()))
            return "&nbsp;";
        else
            return AnsiSubstring(value.ToString(), length);
    }

    /// <summary> 
    ///  按照ANSI计算字符串长度，并截取字符串；
    /// 中文字符长度需要乘2倍，
    /// 比如：“上海轶伦信息技术有限公司”，截取前4个字符，则参数length应为:8/// 根据字符串的实际所占长度提取字符串
    /// </summary>
    /// <param name="value">字符串</param>
    /// <param name="length">提取长度</param>
    /// <returns>返回字符串</returns>
    public static string AnsiSubstring(string value, int length)
    {
        if (value == null) return string.Empty;

        string newString = "";
        int len = 0;
        int i = 0;
        for (i = 0; i < value.Length; i++)
        {
            byte[] byte_len = Encoding.Default.GetBytes(value.Substring(i, 1));

            if (byte_len.Length > 1)
            {
                if ((len + 2) > length)
                {
                    break;
                }
                len += 2; //如果长度大于1，是中文，占两个字节，+2
            }
            else
            {
                if ((len + 1) > length)
                {
                    break;
                }
                len += 1; //如果长度等于1，是英文，占一个字节，+1
            }

            newString += value.Substring(i, 1);
        }

        if (i < value.Length - 1) newString += "...";
        return newString;
    }

    public static string RenderHtml(string val)
    {
        if (string.IsNullOrEmpty(val))
        {
            return "&nbsp;";
        }
        else
        {
            return val.Replace(Environment.NewLine, "<br/>");
        }
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="value"></param>
    /// <param name="format"></param>
    /// <returns></returns>
    public static string FormatDatetime(object value, string format)
    {
        try
        {
            DateTime dt = Convert.ToDateTime(value);
            if (dt > new DateTime(1900, 1, 1))
                return dt.ToString(format);
            else return "";
        }
        catch
        {
            return FormatString(null);
        }
    }

    public static string FormatDatetime(DateTime dt)
    {
        if (dt > new DateTime(1900, 1, 1))
            return FormatDatetime(dt, "yyyy.MM.dd HH:mm");
        else
            return "";
    }

    public static string FormatDatetime(object val)
    {
        if (val != null && val.GetType() == typeof(DateTime))
            return FormatDatetime(Convert.ToDateTime(val));
        return FormatString(null);
    }

    public static string FormatDate(DateTime dt)
    {
        return FormatDatetime(dt, "yyyy.MM.dd");
    }

    public static string FormatDate(object val)
    {
        if (val != null && val.GetType() == typeof(DateTime))
            return FormatDate(Convert.ToDateTime(val));
        return FormatString(null);
    }

    public static string FormatBool(object value)
    {
        return FormatBool(value, "是", "否");
    }

    public static string FormatBool(object value, string trueString, string falseString)
    {
        bool castValue = Cast.Bool(value);
        return castValue ? trueString : falseString;
    }

    public static string FormatNumber(object value, string format)
    {
        return FormatNumber(value, format, string.Empty);
    }
    public static string FormatNumber(object value, string format, string empty)
    {
        if (value == null || value == DBNull.Value) return empty;
        decimal v = Cast.Decimal(value);
        return v == 0M ? empty : v.ToString(format);
    }

    public static string FormatEnableDisable(object value)
    {
        if (value == null || value == DBNull.Value) return "";
        switch (value.ToString().ToLower())
        {
            case "enable": return "启用";
            case "disable": return "禁用";
        }
        switch (Cast.Bool(value))
        {
            case true: return "启用";
            case false: return "禁用";
        }
        return "";
    }

    public static string ImageView(object val)
    {
        if (val == null) return "&nbsp;";
        string s = val.ToString().Trim();
        if (s.Length <= 0) return "&nbsp;";
        s = FileStoreManager.GetAbsoluteUri(s);
        if (string.IsNullOrEmpty(s) || s.Trim().Length <= 0) return "&nbsp;";
        return string.Format("<a target='_blank' href='{0}' title='查看图片'>查看&nbsp;<img src='../images/b_view_img.gif' /></a>", s);
    }

    /// <summary>
    /// T必须为枚举类型，根据val值是否等于current决定是否应当为option元素输出一个selected="selected"属性
    /// </summary>
    /// <typeparam name="T">枚举类型</typeparam>
    /// <param name="val">实体的属性值</param>
    /// <param name="current">在循环为select元素添加options节点时，当前循环的枚举值</param>
    /// <returns></returns>
    public static string IsSelected<T>(object val, T current)
    {
        Type type = typeof(T);
        if (!type.IsEnum) return "";
        T em = Cast.Enum<T>(val);
        return Enum.Equals(em, current) ? "selected=\"selected\"" : "";
    }

    public static string IsChecked(bool isChecked)
    {
        if (isChecked) return "checked";
        else return "";
    }
}
