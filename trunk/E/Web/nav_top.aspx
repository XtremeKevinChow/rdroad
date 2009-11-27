<%@ Page Language="C#" %>

<%@ Import Namespace="Magic.Security" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.Data" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.Sys" %>
<%@ Import Namespace="System.Collections.Generic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script runat="server">
    private string _LogonUserName = string.Empty;
    private ISession _session = null;
    private int _selectedGroupId = -1;
    protected void Page_Load()
    {
        if (!IsPostBack)
        {
            if (SecuritySession.Authenticated())
            {
                _LogonUserName = Magic.Security.SecuritySession.CurrentUser.UserName;
                LoadMenus();
            }
        }
    }

    private void LoadMenus()
    {
        using (_session = new Magic.Framework.ORM.Session())
        {
            IList<Operation> moduleList = Permission.GetPrevilegedUserGroupOperations(_session, Magic.Security.SecuritySession.CurrentUser.UserId, OperationType.Module, true); //Permission.GetPrevilegedOperations(_session, PermissionType.OnUserGroup, _selectedGroupId, OperationType.Module);

            this.menuItems.DataSource = moduleList;
            this.menuItems.DataBind();
        }
    }

    private string GetLiClass(string name)
    {
        string temp = name.Trim();
        switch (temp.Length)
        {
            case 2:
                return "li2em";
            case 3:
                return "li3em";
            case 4:
                return "li4em";
            case 5:
                return "li5em";
            case 6:
                return "li6em";
            case 7:
                return "li7em";
            case 8:
                return "li8em";
            default:
                return "li8em";
        }
    }

    protected void menuItems_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        RepeaterItem item = e.Item;
        if (item != null)
        {
            Operation operation = item.DataItem as Operation;
            Repeater subMenuItems = item.FindControl("menuSubItems") as Repeater;
            if (subMenuItems != null && operation != null)
            {

                subMenuItems.DataSource = Permission.GetPrevilegedUserGroupChildOperations(_session, SecuritySession.CurrentUser.UserId, OperationType.Feature, operation.OperationId);
                subMenuItems.DataBind();
            }
        }
    }

    protected void ddlUserGroup_SelectedIndexChanged(object sender, EventArgs e)
    {
        LoadMenus();
    }
</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>导航菜单</title>
    <style type="text/css">
        body
        {
            margin: 0 10px;
            padding: 0;
            font-family: arial, 宋体, serif;
            font-size: 12px;
            background: url(images/nav_top_bg.gif) repeat-x right top;
        }
        a
        {
            text-decoration: underline;
            cursor: hand;
        }
        .toptable
        {
            width: 100%;
            height: 30px;
            margin: 0;
            padding: 0;
        }
        .toptable .rightTd
        {
            width: 300px;
            text-align: right;
        }
        .toptable .midTd
        {
            vertical-align: top;
            text-align: right;
            padding-top: 6px;
            line-height: 20px;
        }
        .toptable .leftTd
        {
            width: 450px;
        }
        .toptable .leftTd img
        {
            margin-left: 1em;
        }
        .fb12
        {
            font-size: 12px;
            font-weight: bold;
        }
        #nav
        {
            line-height: 22px;
            list-style-type: none;
            margin: 0;
            padding: 0;
        }
        #nav a
        {
            display: block;
            text-align: left;
        }
        #nav a:link
        {
            color: #fff;
            width: auto;
            white-space: nowrap;
            padding: 0;
            text-decoration: none;
            font-weight: bold;
            text-align: center;
        }
        #nav a:visited
        {
            color: #fff;
            width: auto;
            white-space: nowrap;
            padding: 0;
            text-decoration: none;
            font-weight: bold;
            text-align: center;
        }
        #nav a:hover
        {
            color: #FFF;
            width: auto;
            white-space: nowrap;
            padding: 0;
            text-decoration: none;
            font-weight: bold;
            text-align: center;
        }
        #nav li
        {
            float: left;
            width: 6em;
            height: 26px;
            line-height: 26px;
            margin: 0;
            padding: 0;
        }
        #nav .li5em
        {
            float: left;
            width: 7em;
            height: 26px;
            line-height: 26px;
            margin: 0;
            padding: 0;
        }
        #nav .li6em
        {
            float: left;
            width: 8.5em;
            height: 26px;
            line-height: 26px;
            margin: 0;
            padding: 0;
        }
        #nav .li8em
        {
            float: left;
            width: 10em;
            height: 26px;
            line-height: 26px;
            margin: 0;
            padding: 0;
        }
        #nav .on4em
        {
            width: 6em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on4.gif) no-repeat center bottom;
        }
        #nav .on5em
        {
            width: 7em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on5.gif) no-repeat center bottom;
        }
        #nav .on6em
        {
            width: 8.5em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on6.gif) no-repeat center bottom;
        }
        #nav .on8em
        {
            width: 10em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on8.gif) no-repeat center bottom;
        }
        #nav .on4em a:link
        {
            width: 6em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on4.gif) no-repeat center bottom;
        }
        #nav .on4em a:hover
        {
            width: 6em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on4.gif) no-repeat center bottom;
        }
        #nav .on4em a:visited
        {
            width: 6em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on4.gif) no-repeat center bottom;
        }
        #nav .on5em a:link
        {
            width: 7em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on5.gif) no-repeat center bottom;
        }
        #nav .on5em a:hover
        {
            width: 7em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on5.gif) no-repeat center bottom;
        }
        #nav .on5em a:visited
        {
            width: 7em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on5.gif) no-repeat center bottom;
        }
        #nav .on6em a:link
        {
            width: 8.5em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on6.gif) no-repeat center bottom;
        }
        #nav .on6em a:hover
        {
            width: 8.5em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on6.gif) no-repeat center bottom;
        }
        #nav .on6em a:visited
        {
            width: 8.5em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on6.gif) no-repeat center bottom;
        }
        #nav .on8em a:link
        {
            width: 10em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on8.gif) no-repeat center bottom;
        }
        #nav .on8em a:hover
        {
            width: 10em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on8.gif) no-repeat center bottom;
        }
        #nav .on8em a:visited
        {
            width: 10em;
            color: #333333;
            height: 26px;
            line-height: 26px;
            background: url(images/on8.gif) no-repeat center bottom;
        }
        #nav li a:hover
        {
            width: 6em;
            height: 26px;
            line-height: 26px;
            background: url(images/nav_lihover.gif) no-repeat center bottom;
        }
        #nav .li5em a:hover
        {
            width: 7em;
            height: 26px;
            line-height: 26px;
            background: url(images/nav_lihover5.gif) no-repeat center bottom;
        }
        #nav .li6em a:hover
        {
            width: 8.5em;
            height: 26px;
            line-height: 26px;
            background: url(images/nav_lihover6.gif) no-repeat center bottom;
        }
        #nav .li8em a:hover
        {
            width: 10em;
            height: 26px;
            line-height: 26px;
            background: url(images/nav_lihover8.gif) no-repeat center bottom;
        }
        /*-------二级菜单----------*/#nav li ul
        {
            line-height: 20px;
            list-style-type: none;
            text-align: left;
            left: -900em;
            width: 960px;
            position: absolute;
        }
        #nav li ul li
        {
            float: left;
            line-height: 20px;
            width: 5em;
            text-align: center;
        }
        #nav li ul .li2em
        {
            float: left;
            line-height: 20px;
            width: 3em;
            text-align: center;
        }
        #nav li ul .li4em
        {
            float: left;
            line-height: 20px;
            width: 5em;
            text-align: center;
        }
        #nav li ul .li5em
        {
            float: left;
            line-height: 20px;
            width: 6.2em;
            text-align: center;
        }
        #nav li ul .li6em
        {
            float: left;
            line-height: 20px;
            width: 7em;
            text-align: center;
        }
        #nav li ul .li7em
        {
            float: left;
            line-height: 20px;
            width: 9em;
            text-align: center;
        }
        #nav li ul .li8em
        {
            float: left;
            line-height: 20px;
            width: 10em;
            text-align: center;
        }
        #nav li ul a
        {
            line-height: 26px;
            text-decoration: none;
            width: 5em;
            text-align: center;
        }
        #nav li ul li a
        {
            cursor: pointer;
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 5em;
            text-align: center;
        }
        #nav li ul .li2em a
        {
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 3em;
            text-align: center;
            background: none;
        }
        #nav li ul .li4em a
        {
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 5em;
            text-align: center;
            background: none;
        }
        #nav li ul .li5em a
        {
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 6.2em;
            text-align: center;
            background: none;
        }
        #nav li ul .li6em a
        {
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 7em;
            text-align: center;
            background: none;
        }
        #nav li ul .li7em a
        {
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 9em;
            text-align: center;
            background: none;
        }
        #nav li ul .li8em a
        {
            display: block;
            line-height: 26px;
            text-decoration: none;
            width: 10em;
            text-align: center;
            background: none;
        }
        #nav li ul a:link
        {
            color: #333;
            text-decoration: none;
            height: 26px;
            line-height: 26px;
        }
        #nav li ul a:visited
        {
            color: #333;
            text-decoration: none;
            height: 26px;
            line-height: 26px;
        }
        #nav li ul a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 5em;
            background: none;
        }
        #nav li ul .li2em a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 3em;
            background: none;
        }
        #nav li ul .li4em a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 5em;
            background: none;
        }
        #nav li ul .li5em a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 6.2em;
            background: none;
        }
        #nav li ul .li6em a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 7em;
            background: none;
        }
        #nav li ul .li7em a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 9em;
            background: none;
        }
        #nav li ul .li8em a:hover
        {
            color: #f60;
            height: 26px;
            text-decoration: none;
            background: #;
            line-height: 26px;
            width: 10em;
            background: none;
        }
        #nav li.sfhover ul
        {
            height: 20px;
            line-height: 20px;
            left: 2em;
            margin: 0;
            padding: 0;
        }
        #content
        {
            clear: left;
        }
        .table_topNav
        {
            font-weight: bold;
            margin: 0;
            padding: 0;
            width: 100%;
            background: url(images/nav_bg.gif) repeat-x left top;
        }
        .navDiv
        {
            width: 1001px;
            height: 0px;
            overflow: hidden;
        }
        .nowrap
        {
            white-space: nowrap;
        }
        .width1000
        {
            width: 1000px;
            height: 0;
            overflow: hidden;
            clear: both;
        }
    </style>

    <script type="text/javascript">
var currentShowLiId = "";
function menuFix()
 {
    var level1Menus = document.getElementById("nav").childNodes;
    
    for(var i=0;i<level1Menus.length; i++)
    {
    }
}

function ShowMenu(menuId)
{
     HideCurrent();
    if(currentShowLiId != menuId)
    {
        currentShowLiId =menuId;       
   }
  
   ShowCurrent();
     
}

function ShowCurrent()
{
      
        var currentShowLi = document.getElementById(currentShowLiId);
          Show(currentShowLi);
}

function HideCurrent()
{
    var currentShowLi = document.getElementById(currentShowLiId);
       Hide(currentShowLi);
}

function Show(element)
{
     if(element !=null)
        {
             var sfEls = element.getElementsByTagName("li");
            for(j=0;j<sfEls.length;j++)
            {
                sfEls[j].className+=(sfEls[j].className.length>0? " ": "") + "sfhover";
                
            }
            
             element.className+=(element.className.length>0? " ": "") + "sfhover";
             element.className = element.className.replace(new RegExp("li"),"on");
         } 
}

function Hide(element)
{
     if(element !=null)
        {
             var sfEls = element.getElementsByTagName("li");
            for(j=0;j<sfEls.length;j++)
            {
                sfEls[j].className=sfEls[j].className.replace(new RegExp("( ?|^)sfhover\\b"),"");
            }
            
            element.className = element.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
            element.className =  element.className.replace(new RegExp("on"),"li");
         } 
}

window.onload=menuFix;
    </script>

    <script type="text/javascript">
    function FunctionNavigate(url,funcId,sender)
    {
        if(typeof(sender) != "undefined" && sender == "topFrame")
            return;
    }
    
    function PublishNavigate(url,funcId)
    {
        if(window.parent != null && window.parent["mainFrame"] != null)
        {
         if(typeof(window.parent.FunctionNavigate) != "undefined")
            window.parent.FunctionNavigate(url,funcId,"topFrame");
        }
        else
        {
            window.location.href = url;
        }    
    }
       
   function logout()
   {
        var topWin = window;
        while(topWin!=topWin.parent) topWin=topWin.parent;
        topWin.location.href = "Logon.aspx";
   }
    
    </script>

</head>
<body>
    <form runat="server">
    <table cellpadding="0" cellspacing="0" class="toptable">
        <tr>
            <td class="leftTd">
                <span></span><span style="font-size: 17pt;">佰明ERP管理系统</span>
            </td>
            <td align="right" class="midTd nowrap">
                <span class="fb12">· <a onclick="javascript:PublishNavigate('common/Dashboard.aspx','');">
                    桌面</a> ·<a href="javascript:logout();"> 注销</a></span>&nbsp;&nbsp;&nbsp; <span>
                    <%=DateTime.Today.ToLongDateString()%></span>
            </td>
            <td class="rightTd nowrap">
                <span class="fb12">欢迎您,</span><span class="fb12" id="txtLogonUser"><a onclick="javascript:PublishNavigate('system/UserInfoView.aspx','');">
                    <%= _LogonUserName %></a></span>
                <img src="images/kuser.gif" width="16" height="16" align="absmiddle" />
            </td>
        </tr>
    </table>
    <table cellpadding="0" cellspacing="0" class="table_topNav">
        <tr>
            <td style="width: 15px; height: 52px; background: url(images/nav_left.gif) no-repeat left top;
                margin: 0; padding: 0;">
            </td>
            <td style="width: auto; height: 52px; vertical-align: top; margin: 0; padding: 0;">
                <div style="margin: 4px 0 0 0; padding: 0; width: 100%;">
                    <ul id="nav">
                        <asp:Repeater runat="server" ID="menuItems" OnItemDataBound="menuItems_ItemDataBound">
                            <ItemTemplate>
                                <li id='menu<%# Eval("OperationId") %>' class='<%# GetLiClass(Cast.String(Eval("Description"))) %>'>
                                    <a href="javascript:ShowMenu('menu<%# Eval("OperationId") %>')">
                                        <%# Eval("Description") %></a>
                                    <ul>
                                        <asp:Repeater runat="server" ID="menuSubItems">
                                            <ItemTemplate>
                                                <li class='<%# GetLiClass(Cast.String(Eval("Description"))) %>' onmouseover="this.style.color='#f60';"
                                                    onmouseout="this.style.color='#333';"><a onclick="javascript:PublishNavigate('<%# Eval("Entry") %>','<%# Eval("OperationId") %>');">
                                                        <%# Eval("Description") %></a> </li>
                                            </ItemTemplate>
                                        </asp:Repeater>
                                    </ul>
                                </li>
                            </ItemTemplate>
                        </asp:Repeater>
                    </ul>
                    <div class="width1000">
                    </div>
                </div>
            </td>
            <td style="width: 15px; height: 52px; background: url(images/nav_right.gif) no-repeat right top;">
            </td>
        </tr>
    </table>
    </form>
</body>
</html>
