<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseRCVQueryLine.aspx.cs" Inherits="Receive_PurchaseRCVLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>收货明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    function onprint() {
        if ($.trim($("#txtOrderNumber").val()).length <= 0) return;
        window.open("../Receive/PurchaseRCVPrint.aspx?ordNum=" + $("#txtOrderNumber").val(), "print", "width=990,height=600,left=10,top=10,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
    }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="收货明细" />
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
                <td class="h" style="width:50px;">PO行号</td>
                <td class="h" style="width:110px;">SKU(条码)</td>
                <td class="h" style="width:70px;">货号</td>
                <td class="h">商品名称</td>
                <td class="h" style="width:100px;">颜色</td>
                <td class="h" style="width:35px;">尺码</td>
                <td class="h" style="width:60px;">收货数量</td>
                <td class="h" style="width:50px;">单价</td>
                <td class="h" style="width:70px;">金额</td>
                <td class="h" style="width:35px;">税率</td>
                <td class="h" style="width:60px;">税额</td>
                <td class="h" style="width:70px;">成本</td>
            </tr>
            <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                <ItemTemplate>
                    <tr>
                        <td><%# RenderUtil.FormatString(Eval("LineNumber"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("RefOrderLine"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("BarCode"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("ItemCode"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("ItemName"))%></td>
                        <td>
                            <%# RenderUtil.FormatString(Eval("ColorCode"))%>&nbsp;
                            <%# RenderUtil.FormatString(Eval("ColorText"))%>
                        </td>
                        <td><%# RenderUtil.FormatString(Eval("SizeCode"))%></td>
                        <td style="text-align:right; padding-right:4px;"><%# RenderUtil.FormatString(Eval("QualifiedQty"))%></td>
                        <td style="text-align:right; padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("Price"), "#,##0.#0", "0.00")%></td>
                        <td style="text-align:right; padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TotalAmt"), "#,##0.#0", "0.00")%></td>
                        <td style="text-align:right; padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TaxValue"), "#,##0.#0", "0.00")%></td>
                        <td style="text-align:right; padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TaxAmt"), "#,##0.#0", "0.00")%></td>
                        <td style="text-align:right; padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("CostAmt"), "#,##0.#0", "0.00")%></td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
            <tr>
                <td colspan="7" style="text-align:right;">合计:&nbsp;&nbsp;</td>
                <td style="text-align:right; padding-right:4px;"><asp:Label ID="lblRcvQtyTotal" runat="server" Text=""></asp:Label></td>
                <td></td>
                <td style="text-align:right; padding-right:4px;">
                    <asp:Label ID="lblTotalAmt" runat="server" Text=""></asp:Label></td>
                <td>&nbsp;</td>
                <td style="text-align:right; padding-right:4px;"><asp:Label ID="lblTotalTax" runat="server" Text=""></asp:Label></td>
                <td style="text-align:right; padding-right:4px;"><asp:Label ID="lblTotalCost" runat="server" Text=""></asp:Label></td>
            </tr>
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