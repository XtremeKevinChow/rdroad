<%@ Page Language="C#" AutoEventWireup="true" CodeFile="OrderTypeDefEdit.aspx.cs"
    Inherits="Basis_OrderTypeDefEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>修改单据配置</title>
    <link href="../css/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/queryPage.js" type="text/javascript"></script>
    <script type="text/javascript">
        function ValidateData()
        {
            if($.trim($('#txtTypeCode').val()).length == 0)
            {
                ShowMsg("单据类型代码不能为空","警告");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="txtIsNew" value="" runat="server" />
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="修改单据配置" ExtInfo="" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width:30%;">
                    单据类型代码：
                </td>
                <td>
                    <asp:TextBox ID="txtTypeCode" runat="server" CssClass="input" Width="50px" MaxLength="3"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:30%;">
                    描述：
                </td>
                <td>
                    <asp:TextBox ID="txtText" runat="server" CssClass="input" Width="300px" MaxLength="20"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    查看地址：
                </td>
                <td>
                    <asp:TextBox ID="txtUrl" runat="server" Width="300px" MaxLength="70" CssClass="input"></asp:TextBox>
                    <span class="tips">查看该单据详细信息的页面地址</span>
                </td>
            </tr>
            <tr runat="server" id="trRule">
                <td class="label">
                    编码规则：
                </td>
                <td>
                    <asp:DropDownList ID="drpRule" runat="server" Width="200px" CssClass="select">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td class="label">
                    是否支持签核：
                </td>
                <td>
                    <asp:Label ID="lblSupportAppr" runat="server" Text=""></asp:Label>
                    <span class="tips">系统属性，指示该单据类型是否有实现签核功能</span>
                </td>
            </tr>
            <tr runat="server" id="trNeedAppr">
                <td class="label">
                    是否需要签核：
                </td>
                <td>
                    <asp:DropDownList ID="drpNeedAppr" runat="server" Width="60px" CssClass="select">
                        <asp:ListItem Text="否" Value="0"></asp:ListItem>
                        <asp:ListItem Text="是" Value="1"></asp:ListItem>
                    </asp:DropDownList>
                    <span class="tips">这个设置与"签核设置"功能中的"是否需要签核"为同一个设置</span>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarbottom" ToolbarStyle="valign:center;">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                OnClientClick="ValidateData()" CancelServerEvent="true" ImageUrl="../images/b_save.gif"
                                Text="保存">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true" NavigateUrl="OrderTypeDefManager.aspx"
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
