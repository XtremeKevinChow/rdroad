<%@ Page Language="C#" AutoEventWireup="true" CodeFile="UserManage.aspx.cs" Inherits="UserManagePage" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>用户管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript">  
	  $(document).ready(function(){ 
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	   
      });	   
	  
	  function checkSelect()
	  {
	    if(!hasSelect("#data_list_table"))
	    {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    return true;
	  }
	  
	  function Edit(id)
	  {
	        var url = "UserEdit.aspx?mode=edit&id=" + id ;
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function AddNew()
	  {
	     var url = "UserEdit.aspx?mode=new";
        var returnUrl = $("#hidReturnUrl").val();
        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function UserGroup(id)
	  {
	     var url = "UserToGroup.aspx?uid=" + id ;
        var returnUrl = $("#hidReturnUrl").val();
        window.location.href = url + "&return="+escape(returnUrl);
	  }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="用户管理" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    帐号名称&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtUserName" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    用户姓名&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtFullName" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:35px;">
                    状态&nbsp;
                </td>
                <td>
                    <asp:CheckBoxList runat="server" ID="cklStatus" RepeatDirection="Horizontal" RepeatLayout="Flow">
                        <asp:ListItem Text="启用" Value="Enabled" Selected="True"></asp:ListItem>
                        <asp:ListItem Text="禁用" Value="Disabled"></asp:ListItem>
                    </asp:CheckBoxList>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="javascript:AddNew()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="checkSelect()"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th>
                        编辑
                    </th>
                    <th>
                        用户组
                    </th>
                    <th>
                        用户类型
                    </th>
                    <th>
                        帐号
                    </th>
                    <th>
                        姓名
                    </th>
                    <th>
                        状态
                    </th>
                    <th>
                        联系电话/分机
                    </th>
                    <th>
                        上次登陆时间
                    </th>
                    <th>
                        修改日期
                    </th>
                    <th>
                        修改人
                    </th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptUser" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="" style="width: 20px;">
                                <input id="checkbox" value='<%# Eval("UserId") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width: 35px;">
                                <a href='javascript:Edit(<%# Eval("UserId") %>)'>编辑</a>
                            </td>
                            <td style="width: 35px;" align="center">
                                <a href='javascript:UserGroup(<%# Eval("UserId") %>)'>
                                    <img src="../images/cuser.gif" width="16" height="16" alt="" /></a>
                            </td>
                            <td>
                                <%# Magic.Sys.User.UserTypeText(Eval("UserType")) %>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("UserName")) %>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("FullName")) %>
                            </td>
                            <td>
                                <%# Magic.Sys.User.UserStatusText(Eval("UserStatus"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("Ext")) %>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("LastLogonTime"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDate(Eval("ModifyTime"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ModifyBy"))%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem ItemCssClass="toolbutton" ItemType="Navigate" ImageUrl="../images/b_addL.gif"
                                Text="新增" NavigateUrl="javascript:AddNew()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" Text="删除" CancelServerEvent="true" OnClientClick="checkSelect()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="20" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
