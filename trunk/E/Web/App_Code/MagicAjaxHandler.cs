using System;
using System.Web;
using Magic.Framework;

/// <summary>
/// Summary description for MagicAjaxHandler
/// </summary>
public class MagicAjaxHandler : IHttpHandler, System.Web.SessionState.IRequiresSessionState
{
    log4net.ILog logger = log4net.LogManager.GetLogger(typeof(MagicAjaxHandler));
    public MagicAjaxHandler()
    {
    }

    public void ProcessRequest(HttpContext context)
    {
        if (!Magic.Security.SecuritySession.Authenticated())
        {
            context.Response.Write((new SimpleJson().HandleError("您还没有登陆或者登陆已经过期，请登陆系统!")).ToJsonString("yyyy-MM-dd HH:mm"));
            return;
        }

        string type = WebUtil.Param("type").Trim();
        if (type.ToLower() == "1002") //execute the ascx control and return the response html
            try
            {
                context.Server.Execute("AjaxControlsHandler.aspx",context.Response.Output, true);
                return;
            }
            catch (Exception error)
            {
                logger.Error("ExecuteControl", error);
                context.Response.Write(error.Message);
                return;
            }

        if (type.ToLower() == "1001")
        {
            try
            {
                string result = MagicAjax.InvokeAjaxCall2String();
                context.Response.Write(result);
                return;
            }
            catch (Exception error)
            {
                context.Response.Write(error.Message);
                return;
            }
        }

        //return json object
        SimpleJson json = new SimpleJson();
        try
        {
            SimpleJson j = MagicAjax.InvokeAjaxCall2Json();
            if (j != null) json = j;
        }
        catch (Exception error)
        {
            json.HandleError(error.Message);
        }
        context.Response.Write(json.ToJsonString("yyyy-MM-dd HH:mm"));
    }
    public bool IsReusable
    {
        get
        {
            return false;
        }
    }
}
