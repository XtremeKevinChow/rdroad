<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseGroupManager.aspx.cs"
    Inherits="system_PurchaseGroupManager" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购组管理</title>
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
	    return confirm("提示:确认删除所选采购组?");
	  }
	  
	  function Edit(id)
	  {
	        var url = "PurchaseGroupEdit.aspx?mode=edit&id=" + id ;
	        var returnUrl = $("#hidReturnUrl").val();
	        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  
	  function AddNew()
	  {
	     var url = "PurchaseGroupEdit.aspx?mode=new";
        var returnUrl = $("#hidReturnUrl").val();
        window.location.href = url + "&return="+escape(returnUrl);
	  }
	  

    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="PurchaseGroupManager.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购组管理" />
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
                    &nbsp;
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width: 20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width: 70px;">
                        编辑
                    </th>
                    <th style="width: 90px;">
                        采购组
                    </th>
                    <th>
                        描述
                    </th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptPurchaseGroup" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <input id="checkbox" value='<%# Eval("PurchGroupCode") %>' type="checkbox" runat="server" />
                            </td>
                            <td>
                                <a href='javascript:Edit("<%# Eval("PurchGroupCode") %>")'>编辑</a>&nbsp; <a href='PurchaseGroup2UserManager.aspx?purchaseGroupCode=<%# Eval("PurchGroupCode") %>'>
                                    用户</a>
                            </td>
                            <td style="text-align: center;">
                                <%# Eval("PurchGroupCode")%>
                            </td>
                            <td>
                                <%# Eval("PurchGroupText")%>
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
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
