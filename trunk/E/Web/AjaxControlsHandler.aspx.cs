using System;
using System.Web.UI;

public partial class AjaxControlsHandler : System.Web.UI.Page
{
    private static log4net.ILog logger = log4net.LogManager.GetLogger(typeof(AjaxControlsHandler));
    protected void Page_Load(object sender, EventArgs e)
    {
        string action = WebUtil.Param("action");
        string path = string.Empty;
        switch (action)
        {
            case "SelectUser":
                path = "controls/SelectUserTable.ascx";
                break;
            default:
                path = "controls/" + action + ".ascx";
                break;
        }
        //if (!action.EndsWith(".ascx")) action = action + ".ascx";
        Control control = null;
        try
        {
            control = this.LoadControl(path);
        }
        catch (Exception loadException)
        {
            logger.Error("LoadControl", loadException);
            Response.Write(string.Format("Can not execute the action {0}, error message is: {1}", WebUtil.Param("action"), loadException.Message));
            return;
        }

        this.Controls.Add(control);
    }
}