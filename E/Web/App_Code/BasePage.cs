using System;
using System.Web.UI;

/// <summary>
/// Summary description for BasePage
/// </summary>
public class BasePage:Page
{
    public BasePage():base()
    {
        this.Init += new EventHandler(BasePage_Init);
    }

    void BasePage_Init(object sender, EventArgs e)
    {
        if (Request["return"] != null) ReturnUrl = Request["return"];
    }

    /// <summary>
    /// 返回的Url
    /// </summary>
    protected string ReturnUrl
    {
        get
        {
            if (ViewState["RETURNURL"] != null)
                return ViewState["RETURNURL"].ToString();
            if (Request != null)
            {
                return Request.Url.AbsolutePath;
            }
            return "";
        }
        set { ViewState["RETURNURL"] = value; }
    }


    /// <summary>
    /// 返回
    /// </summary>
    protected void Redirect2ReturnUrl()
    {
        if (!string.IsNullOrEmpty(ReturnUrl))
        {
            Response.Redirect(Microsoft.JScript.GlobalObject.unescape(ReturnUrl), false);
        }
    }
         
  
    
}
