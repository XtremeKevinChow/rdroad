<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ReturnReasonEdit.aspx.cs"
    Inherits="system_ReturnReasonEdit" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>编辑物流公司</title>
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
            if($.trim($('#txtReturnReason').val()).length == 0)
            {
                ShowMsg("退货原因不得为空","警告");
                return false;
            }
            return true;
        }

    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="txtReturnID" value="" runat="server" />
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="退货原因编辑" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">
                    退货原因：
                </td>
                <td>
                    <asp:TextBox ID="txtReturnReason" runat="server" Height="80px" TextMode="MultiLine"
                        Width="266px" CssClass="input"></asp:TextBox>
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
        </table>
    </div>
    </form>
</body>
</html>
