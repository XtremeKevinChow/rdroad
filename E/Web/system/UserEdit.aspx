<%@ Page Language="C#" AutoEventWireup="true" CodeFile="UserEdit.aspx.cs" Inherits="system_UserEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register src="../Controls/FunctionTitle.ascx" tagname="FunctionTitle" tagprefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>编辑用户</title>
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
     <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
     <link href="../css/treeview.css" rel="Stylesheet" type="text/css" />
     <style type="text/css">
        .treeview li { background-image: url(../images/tree-gray-line.gif); }
        .treeview .hitarea, .treeview li.lastCollapsable, .treeview li.lastExpandable { background-image: url(../images/tree-gray.gif); }
     </style>
     <script src="../script/jquery.js" type="text/javascript"></script>    
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/jquery.treeview.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/queryPage.js" type="text/javascript"></script>
    <script src="../script/org.js" type="text/javascript"></script>
    <script type="text/javascript">
        function ValidateData()
        {
            if($.trim($('#txtUserName').val()).length == 0)
            {
                ShowMsg("用户帐号不能为空","警告");
                return false;
            }
            if($.trim($('#txtFullName').val()).length == 0)
            {
                ShowMsg("用户姓名不能为空","警告");
                return false;
            }
            return true;
        }
        
        $(document).ready(function(){
         $("#txtOrgDesc").bind("click", function(){  
                mm.query( {
                    id:"org", //选组织传org，选合作企业传partner
                    getter: new orgSelect(), title: "选择组织", 
                    mode:"s", //s单选，m多选
                    values: $("#txtOrgId").val(), 
                    callback: function(data, settings){
                        $("#txtOrgId").val(data.id);
                        $("#txtOrgDesc").val(data.desc);
                    }
                });
            });//选择组织
        });
    </script>
</head>
<body>
      <form id="form1" runat="server">
       <div>
      <input type="hidden" id="txtUserId"  value="" runat="server" />
      <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="编辑用户" />
      <table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
            <td class="label">用户帐号：</td>
            <td>
                <asp:TextBox ID="txtUserName" runat="server" MaxLength="20"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td class="label">用户密码：</td>
            <td>
                <asp:TextBox ID="txtPassword" runat="server" TextMode="Password" CssClass="input"></asp:TextBox>
            </td>
        </tr>   
        <tr>
            <td class="label">用户姓名：</td>
            <td><asp:TextBox ID="txtFullName" runat="server" CssClass="input" MaxLength="28"></asp:TextBox></td>
        </tr>  
        <tr>
            <td class="label">Email：</td>
            <td><asp:TextBox ID="txtEmail" runat="server" CssClass="input" MaxLength="40" Width="250px"></asp:TextBox></td>
        </tr>
        <tr>
            <td class="label">分机：</td>
            <td><asp:TextBox ID="txtExt" runat="server" CssClass="input" MaxLength="8"></asp:TextBox></td>
        </tr>
        <tr>
            <td class="label">类型：</td>
            <td><asp:DropDownList runat="server" ID="ddlUserType"></asp:DropDownList></td>
        </tr>
         <tr runat="server" id="trOrg">
            <td class="label">部门：</td>
            <td><asp:TextBox runat="server" ID="txtOrgDesc" CssClass="input readonly"></asp:TextBox>
            <asp:TextBox ID="txtOrgId" runat="server" style="display:none;"></asp:TextBox></td>
        </tr>        
        <tr>
            <td class="label">上次登陆时间：</td>
            <td><asp:TextBox ID="txtLastLogonTime" runat="server" ReadOnly="true" CssClass="input readonly"></asp:TextBox></td>
        </tr>   
        <tr>
            <td class="label">状态：</td>
            <td><asp:RadioButtonList ID="rblStatus" runat="server" RepeatDirection="Horizontal" Width="150px"></asp:RadioButtonList>
            </td>
        </tr> 
        <tr>
            <td></td>
            <td></td>
        </tr>    
        <tr>
            <td></td>
            <td>
                 <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Span" ID="toolbarbottom" ToolbarStyle="valign:center;">     
                     <Items>
                        <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                         OnClientClick="ValidateData()" CancelServerEvent="true"
                        ImageUrl="../images/b_save.gif" Text="保存" >
                        </mwu:MagicItem>
                         <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                            ImageUrl="../images/b_stop.gif" >
                        </mwu:MagicItem>
                    </Items>
                </mwu:MagicToolBar>
            </td>
        </tr>      
    </table>
    </div>
</form>
</body>
</html>