<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ASSItemEdit.aspx.cs" Inherits="numTwo_ASSItemEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register src="../Controls/FunctionTitle.ascx" tagname="FunctionTitle" tagprefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Untitled Page</title>
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
        function validate()
        {
            if($.trim($('#txtItemCode').val()).length == 0)
            {
                ShowMsg("辅料编号不能为空","警告");
                return false;
            }
            if ($.trim($('#txtItemName').val()).length == 0) {
                ShowMsg("辅料名称不能为空", "警告");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <input type="hidden" id="txtID"  value="" runat="server" />
      <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="编辑辅料" />
      <table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
            <td class="label">辅料编号(条码)：</td>
            <td>
                <asp:TextBox ID="txtItemCode" runat="server" MaxLength="20" CssClass="input"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td class="label">辅料状态：</td>
            <td>
                <asp:DropDownList ID="ddlItemStatus" runat="server" CssClass="select">
                </asp:DropDownList>
            </td>
        </tr>     
        <tr>
            <td class="label">辅料名称：</td>
            <td><asp:TextBox ID="txtItemName" runat="server" CssClass="input" MaxLength="40" 
                    Width="266px"></asp:TextBox></td>
        </tr>
        <tr>
            <td class="label">辅料描述：</td>
            <td>
                <asp:TextBox ID="txtItemSpec" runat="server" TextMode="MultiLine" 
                    CssClass="input" Height="80px" Width="266px"></asp:TextBox>
                    </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
        </tr>    
        <tr>
            <td></td>
            <td>
                 <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="mbar_ItemCommand"  runat="server" Layout="Span" ID="mbar" ToolbarStyle="valign:center;">     
                     <Items>
                        <mwu:MagicItem CommandName="save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                         OnClientClick="validate()" CancelServerEvent="true"
                        ImageUrl="../images/b_save.gif" Text="保存" >
                        </mwu:MagicItem>
                         <mwu:MagicItem CommandName="back" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
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
