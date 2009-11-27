using System.Text.RegularExpressions;

/// <summary>
/// Summary description for UrlUtil
/// </summary>
public static class UrlUtil
{
    /// <summary>
    /// 是否是相对路径
    /// </summary>
    /// <param name="url"></param>
    /// <returns></returns>
	public static bool IsRelative(string url)
	{
		if (string.IsNullOrEmpty(url)) return true;
		return !(url.IndexOf("http:") >= 0 || url.IndexOf("www.") >= 0 || url.IndexOf(".com") >= 0);
	}
    /// <summary>
    /// 是否是绝对路径
    /// </summary>
    /// <param name="url"></param>
    /// <returns></returns>
	public static bool IsAbsolute(string url)
	{
		if (string.IsNullOrEmpty(url)) return false;
		return url.IndexOf("http:") >= 0 || url.IndexOf("www.") >= 0 || url.IndexOf(".com") >= 0;
	}
    /// <summary>
    /// 拼接两个URL
    /// </summary>
    /// <param name="url1"></param>
    /// <param name="url2"></param>
    /// <returns></returns>
	public static string Combine(string url1, string url2)
	{
		if (string.IsNullOrEmpty(url1)) return url2;
		if (string.IsNullOrEmpty(url2)) return url1;
		string url = url1.Trim()+"/"+url2.Trim();
		url = Regex.Replace(url, @"(?<!(http:))[/\\]{2,}|[\\]+", "/", RegexOptions.Singleline);
		return url;
	}
}