<%@ Page Language="C#" ClassName="Logon" %>
<%@ Import Namespace="Magic.Security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script runat="server">
    protected void LoginButton_Click(object sender, EventArgs e)
    {
        try
        {
            string uname = this.txtUserName.Value.Trim();
            string pwd = this.txtPassword.Value.Trim();
            if (SecuritySession.Authenticate(uname, pwd))
                Server.Transfer("index.aspx", false);
            else 
                this.FailureText.Text = "用户名或密码错误！";
        }
        catch (Exception ex)
        {
            if (ex is System.Threading.ThreadAbortException)
                throw ex;
            else
            {
                log4net.ILog logger = log4net.LogManager.GetLogger(this.GetType());
                logger.Info("Login Failure", ex);
                this.FailureText.Text = "发生未处理的异常，请联系系统管理员！";
            }
        }
    }
</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Magic ERP</title>
    <link href="CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        #minWidth { height:1px;width:600px;overflow:hidden; }
        #top  
        {
    	   height:123px;width:100%; 
    	   background-color:#FFFFFF;background-image:url(images/ilogo.jpg); background-position:left;background-repeat:no-repeat;
           margin-top:10px;margin-bottom:15px;padding:0;
        }
        #loginImage 
        {
        	position:absolute;top:133px;left:0;width:51px;height:82px;
        	background-image:url(images/dl-on.gif);
        }
        #loginformWrap
        {
        	width:100%;height:250px;
        	border-top:solid 1px #FFFFFF;border-bottom:solid 1px #FFFFFF;
        	background-color:#5E86CE;
        }
        #loginform
        {
        	width:300px;height:70px;
        	margin-left:auto;margin-right:auto;margin-top:90px;margin-bottom:90px;
        }
        #LoginButton { margin-top:10px; }
        #copyrightWrap{ width:100%;border:0;margin:0;padding:0;position:absolute; }
        #copyright { width:250px; margin-left:auto; margin-right:auto;color:#FFFFFF; }
        #copyright a { color:#FFFFFF; text-decoration:none; }
        #copyright a:hover { color:#FFFFFF; text-decoration:underline; }
        #copyright a:link { color:#FFFFFF; text-decoration:none; }
        
        .red { color:Red; }
    </style>
    <script src="script/jquery.js" type="text/javascript"></script>
    <script src="script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        $("#txtUserName")[0].select();
        setPosition();
    });
    function setPosition(){
        var docHeight = $(document).height();
        docHeight = docHeight<=420 ? 420 : docHeight;
        $("#copyrightWrap").css("top", (docHeight-20)+"px").show();
    }
    </script>
</head>
<body bgcolor="#24447E" style="padding:0; margin:0;">
    <form runat="server" id="form1">
    <div id="minWidth"></div>
    <div id="top"></div>
    <div id="loginImage"></div>
    <div id="loginformWrap">
        <div id="loginform">
                <table style="width:100%;height:100%;" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td style="color:#FFFFFF;">用户名</td>
                        <td>
                            <input type="text" id="txtUserName" class="input" runat="server" />
                        </td>
                    </tr>
                    <tr>
                        <td style="color:#FFFFFF;">密&nbsp;&nbsp;&nbsp;码</td>
                        <td>
                            <input type="password" id="txtPassword" runat="server" class="input" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="text-align:left;height:30px;">
                            <asp:ImageButton ID="LoginButton" runat="server" ImageUrl="images/signin.gif" OnClick="LoginButton_Click" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="color:#FFFFFF;">
                            <div style="height:5px;width:5px; overflow:hidden;"></div>
                            <asp:Literal ID="FailureText" runat="server" EnableViewState="False"></asp:Literal>
                        </td>
                    </tr>
                </table>
        </div>
    </div>
    <div id="copyrightWrap" style="display:none;">
        <div id="copyright">
            <span>Magic ERP,&nbsp;</span>
            <a href="http://www.thoughtsoft.com.cn" target="_blank">&#169;上海轶伦信息技术有限公司</a>
        </div>
    </div>
    </form>
</body>
</html>
