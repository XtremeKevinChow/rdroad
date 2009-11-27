<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseGroup2UserManager.aspx.cs"
    Inherits="system_PurchaseGroup2UserManager" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购用户组管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .hidden
        {
            display: none;
        }
    </style>

    <script src="../script/jquery.js" type="text/javascript"></script>

    <script src="../script/jquery.cookie.js" type="text/javascript"></script>

    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>

    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>

    <script src="../script/interface.fix.js" type="text/javascript"></script>

    <script src="../script/magic.js" type="text/javascript"></script>

    <script type="text/javascript" src="../script/QueryPage.js"></script>

    <script src="../script/selector.js" type="text/javascript"></script>

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
	    return confirm("确认将选择的用户删除？");
	  }
	  
	  function Edit(purchasecode,id)
	  {
	        var url = "PurchaseGroup2UserEdit.aspx?mode=edit&id=" + id + "&purchasecode="+purchasecode;
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function AddNew()
	  {
            var q = new query("PurchaseGroupUser");
            q.fnPopup({
                top:10, left:50, width:400, height:250, mode: "m", title: "选择用户",
                data: { groupCode:$("#txtGroupCode").val(), selected: [] },
                on_select: function(r) {
                    if(r.length>0){
                        var userList = "";
                        for(var i=0; i<r.length; i++){
                            userList=userList+r[i].userId+";";
                        }
                        $("#txtUserList").val(userList);
                        $("#cmdAddUser").click();
                    }//if(r.length>0){
                }//on_select
            });//q.fnPopup
	  }
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="text" style="display: none;" id="txtUserList" runat="server" />
        <asp:Button ID="cmdAddUser" runat="server" Text="Button" CssClass="hidden" OnClick="cmdAddUser_Click" />
        <input type="text" style="display: none;" id="txtGroupCode" runat="server" />
        <input type="hidden" id="hidReturnUrl" runat="server" value="PurchaseGroup2UserManager.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购组用户" />
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
                            <mwu:MagicItem ItemCssClass="toolbutton" CommandName="Return" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    &nbsp;
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:100px;">
                        用户姓名
                    </th>
                    <th>
                        电话
                    </th>
                    <th>手机</th>
                    <th>电子邮箱</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptPurchaseGroup" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <input id="checkbox" type="checkbox" runat="server" />
                                <input type="hidden" id="hidUserID" runat="server" value='<%# Eval("UserId")%>' />
                            </td>
                            <td>
                                <%# Eval("FullName")%>
                            </td>
                            <td>
                                <%# Eval("Ext")%>
                            </td>
                            <td>
                                <%# Eval("Mobile")%>
                            </td>
                            <td>
                                <%# Eval("Email")%>
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
                            <mwu:MagicItem ItemCssClass="toolbutton" CommandName="Return" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
