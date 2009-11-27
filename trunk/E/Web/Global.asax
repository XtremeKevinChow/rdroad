<%@ Application Language="C#" %>

<script runat="server">

    void Application_Start(object sender, EventArgs e) 
    {
        AppUtil.InitLog4Net();
        EnumUtil.InitializeEnumText();

        using (Magic.Framework.ORM.ISession session = new Magic.Framework.ORM.Session())
        {
            Magic.ERP.ERPUtil.Initialize(session);
        }
    }
    
    void Application_End(object sender, EventArgs e) 
    {
    }
        
    void Application_Error(object sender, EventArgs e) 
    { 
    }

    void Session_Start(object sender, EventArgs e) 
    {
        if(!Magic.ERP.ERPUtil.HasInitialized)
            using (Magic.Framework.ORM.ISession session = new Magic.Framework.ORM.Session())
            {
                Magic.ERP.ERPUtil.Initialize(session);
            }
        
        System.Collections.Generic.IList<string> excludes = new System.Collections.Generic.List<string>();
        excludes.Add("Purchase/PipelineInvQuery.aspx");

        bool needCheck = true;
        string url = HttpContext.Current.Request.RawUrl;
        foreach(string s in excludes)
            if (url.IndexOf(s) >= 0)
            {
                needCheck = false;
                break;
            }
        if (!needCheck) return;
        if (!Magic.Security.SecuritySession.Authenticated())
            Magic.Security.SecuritySession.SignOut();
    }

    void Session_End(object sender, EventArgs e) 
    {
        Session.Clear();
    }

    void Application_BeginRequest(object sender, EventArgs e)
    {
    }
</script>
