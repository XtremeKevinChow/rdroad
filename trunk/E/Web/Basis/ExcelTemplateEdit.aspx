<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ExcelTemplateEdit.aspx.cs" Inherits="Basis_ExcelTemplate" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>修改运单模板</title>
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/queryPage.js" type="text/javascript"></script>
    <script type="text/javascript">
        function ValidateData()
        {
            if($.trim($('#txtName').val()).length == 0)
            {
                ShowMsg("模板名称不能为空","警告");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="txtId" value="" runat="server" />
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="修改运单模板" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td align="right" width="30%">
                    状态：
                </td>
                <td>
                    <asp:DropDownList ID="drpStatus" runat="server">
                        <asp:ListItem Value="1">有效</asp:ListItem>
                        <asp:ListItem Value="2">无效</asp:ListItem>
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label">
                    名称：
                </td>
                <td>
                    <asp:TextBox ID="txtName" runat="server" CssClass="input" Width="200px" MaxLength="20"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    上传模板文件：
                </td>
                <td>
                    <asp:FileUpload ID="FileUpload1" runat="server" CssClass="input" Width="350px" />
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
