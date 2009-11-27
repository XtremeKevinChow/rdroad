<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SaleReturnQueryLine.aspx.cs" Inherits="Receive_SaleReturnQueryLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>退货明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    function onprint() {
        if ($.trim($("#txtOrderNumber").val()).length <= 0) return;
        window.open("../Receive/SaleReturnPrint.aspx?ordNum=" + $("#txtOrderNumber").val(), "print", "width=990,height=600,left=10,top=10,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
    }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="退货明细" />
        <input type="text" style="display:none;" id="txtOrderNumber" value='' runat="server" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_print.gif" OnClientClick="onprint()" Text="打印">
                            </mwu:MagicItem>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
        <table class="datalist2" cellpadding="0" cellspacing="0">
            <tr>
                <td class="h" style="width:40px;">行号</td>
                <td class="h" style="width:85px;">货号</td>
                <td class="h">商品名称</td>
                <td class="h" style="width:110px;">颜色</td>
                <td class="h" style="width:50px;">尺码</td>
                <td class="h" style="width:60px;">销售方式</td>
                <td class="h" style="width:60px;">退货数量</td>
                <td class="h" style="width:50px;">单价</td>
            </tr>
            <asp:Repeater ID="repeatControl" runat="server">
                <ItemTemplate>
                    <tr>
                        <td><%# Eval("LineNumber")%></td>
                        <td><%# Eval("ItemCode")%></td>
                        <td><%# Eval("ItemName")%></td>
                        <td><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                        <td><%# Eval("SizeCode")%></td>
                        <td><%# Eval("SaleType")%></td>
                        <td style="text-align: right;"><%# RenderUtil.FormatNumber(Eval("ReturnQty"), "##.##", "0")%></td>
                        <td style="text-align: right;"><%# RenderUtil.FormatNumber(Eval("Price"), "##.##", "0")%></td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn2" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_print.gif" OnClientClick="onprint()" Text="打印">
                            </mwu:MagicItem>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>