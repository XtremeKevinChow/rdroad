<%@ Page Language="C#" AutoEventWireup="true" CodeFile="TransTypeDefEdit.aspx.cs"
    Inherits="Basis_TransTypeDefEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>修改交易类型</title>
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
            if($.trim($('#txtCode').val()).length == 0)
            {
                ShowMsg("代码不能为空","警告");
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
        <uc1:FunctionTitle ID="FunctionTitle1" runat="server" PageTitle="修改交易类型" />
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <td class="label" style="width: 30%;">
                    代码：
                </td>
                <td>
                    <asp:TextBox ID="txtCode" runat="server" CssClass="input" Width="40px" MaxLength="3"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    描述：
                </td>
                <td>
                    <asp:TextBox ID="txtText" runat="server" CssClass="input" Width="180px" MaxLength="12"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label">
                    价格来源：
                </td>
                <td>
                    <asp:DropDownList ID="drpPriceSource" runat="server" Width="180px" CssClass="select">
                    </asp:DropDownList>
                    <span class="tips">设置该类型的交易如何获取交易价格</span>
                </td>
            </tr>
            <tr>
                <td class="label">
                    是否成本交易：
                </td>
                <td>
                    <asp:DropDownList ID="drpIsCostTrans" runat="server" Width="60px" CssClass="select">
                    </asp:DropDownList>
                    <span class="tips">成本交易会计算并更新商品的移动平均价</span>
                </td>
            </tr>
            <tr>
                <td class="label">
                    交易属性：
                </td>
                <td>
                    <asp:DropDownList ID="drpTransProp" runat="server" Width="60px" CssClass="select">
                    </asp:DropDownList>
                    <span class="tips">交易属性为"入"时，系统以正数更新库存；为"出"时系统以负数更新库存</span>
                </td>
            </tr>
            <tr>
                <td class="label">
                    备注：
                </td>
                <td>
                    <asp:TextBox ID="txtDesc" runat="server" CssClass="input" Width="180px" MaxLength="30"></asp:TextBox>
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
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true" NavigateUrl="TransTypeDefManager.aspx"
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
