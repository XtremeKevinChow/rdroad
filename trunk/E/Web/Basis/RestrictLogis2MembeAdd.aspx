<%@ Page Language="C#" AutoEventWireup="true" CodeFile="RestrictLogis2MembeAdd.aspx.cs" Inherits="Basis_RestrictLogis2MemberManager" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>指定会员的配送公司</title>
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
	  
	function on_add() {
	    if ($("#drpLogis").val() == "") {
	        ShowMsg("请选择物流公司", "警告");
	        return false;
	    }
	    if (!hasSelect("#data_list_table")) {
	        ShowMsg("请选择会员", "警告");
	        return false;
	    }
	    return true;
	}
    </script>
    </head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="指定会员的配送公司" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td>物流公司</td>
                <td colspan="4">
                    <asp:DropDownList ID="drpLogis" runat="server">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:70px;">
                    会员姓名&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtMemberName" runat="server" CssClass="input" 
                        Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">
                    会员号&nbsp;
                </td>
                <td style="width:100px;">
                    &nbsp;<asp:TextBox ID="txtMemberNumber" runat="server" CssClass="input" Width="80px"></asp:TextBox>
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
                            <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" Text="添加"
                                 CancelServerEvent="true" OnClientClick="on_add()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="18" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:30px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                  </th>
                    <th style="width:150px;">
                        会员号</th>
                    <th>
                        会员姓名</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptVendor" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td style="width:30px;">
                                <input id="checkbox" value='<%# Eval("MemberID") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width:150px;">
                            <%# Eval("MemberNumber")%>
                             </td>
                            <td>
                                 <%# Eval("Name")%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1" >
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem CommandName="Add" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" Text="添加"
                                CancelServerEvent="true" OnClientClick="on_add()">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="18" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
