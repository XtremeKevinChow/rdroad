using System.Web;


/// <summary>
/// Summary description for CmsUtil
/// </summary>
public sealed class AppUtil
{
   
    private AppUtil()
    {
    }

    public static void InitLog4Net()
    {
        log4net.Config.XmlConfigurator.Configure(new System.IO.FileInfo(System.IO.Path.Combine(HttpContext.Current.Request.PhysicalApplicationPath, "log4net.config")));
    }
}