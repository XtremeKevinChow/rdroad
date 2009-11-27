<%@ Page Language="C#" AutoEventWireup="true" CodeFile="VendorEdit.aspx.cs" Inherits="system_VenderEdit" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑供应商</title>
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../css/treeview.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .treeview li
        {
            background-image: url(../images/tree-gray-line.gif);
        }
        .treeview .hitarea, .treeview li.lastCollapsable, .treeview li.lastExpandable
        {
            background-image: url(../images/tree-gray.gif);
        }
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
            if($.trim($('#txtShortName').val()).length == 0)
            {
                ShowMsg("供应商简称不得为空","警告");
                return false;
            }
            if($.trim($('#txtFullName').val()).length == 0)
            {
                ShowMsg("供应商全称不得为空","警告");
                return false;
            }
            return true;
        }

    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="txtVendorId" value="" runat="server" />
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="编辑供应商" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">
                    供应商简称：
                </td>
                <td>
                    <asp:TextBox ID="txtShortName" runat="server" CssClass="input" Width="300px" MaxLength="15"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    供应商全称：
                </td>
                <td>
                    <asp:TextBox ID="txtFullName" runat="server" CssClass="input" Width="300px" MaxLength="35"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    供应商地址：
                </td>
                <td>
                    <asp:TextBox ID="txtAddress" runat="server" CssClass="input" Width="300px" MaxLength="60"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    联系人：
                </td>
                <td>
                    <asp:TextBox ID="txtContact" runat="server" CssClass="input" Width="80px" MaxLength="15"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    邮编：
                </td>
                <td>
                    <asp:TextBox ID="txtZipCode" runat="server" CssClass="input" Width="80px" MaxLength="10"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    联系电话：
                </td>
                <td>
                    <asp:TextBox ID="txtPhone" runat="server" CssClass="input" Width="120" MaxLength="16"></asp:TextBox>
                </td>
            </tr>
            <tr runat="server" id="trOrg">
                <td class="label">
                    传真：
                </td>
                <td>
                    <asp:TextBox ID="txtFax" runat="server" CssClass="input" Width="120" MaxLength="16"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    银行帐号：
                </td>
                <td>
                    <asp:TextBox ID="txtBankAccount" runat="server" CssClass="input" Width="300" MaxLength="25"></asp:TextBox>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td align="right">
                    税率：
                </td>
                <td>
                    <asp:DropDownList ID="drpTax" runat="server">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td align="right">
                    状态：
                </td>
                <td>
                    <asp:DropDownList ID="drpStatus" runat="server">
                        <asp:ListItem Value="2">有效</asp:ListItem>
                        <asp:ListItem Value="1">禁用</asp:ListItem>
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Span" ID="toolbarbottom" ToolbarStyle="valign:center;">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                OnClientClick="ValidateData()" CancelServerEvent="true" ImageUrl="../images/b_save.gif"
                                Text="保存">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
