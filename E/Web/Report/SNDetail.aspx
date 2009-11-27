<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SNDetail.aspx.cs" Inherits="SaleDelivery_DeliveryQueryDetail" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Register Src="../Controls/SNInfo.ascx" TagName="sn" TagPrefix="sn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>销售发货明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="销售发货明细" />
        <sn:sn ID="snView" runat="server" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
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
                    <td class="h" style="width:110px;">SKU</td>
                    <td class="h" style="width:110px;">货号</td>
                    <td class="h">名称</td>
                    <td class="h" style="width:120px;">颜色</td>
                    <td class="h" style="width:70px;">尺码</td>
                    <td class="h" style="width:80px;">销售类型</td>
                    <td class="h" style="width:60px;">数量</td>
                    <td class="h" style="width:60px;">价格</td>
                    <td class="h" style="width:60px;">金额</td>
                    <td class="h" style="width:60px;">成本价</td>
                    <td class="h" style="width:60px;">成本</td>
                </tr>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td><%# RenderUtil.FormatString(Eval("BarCode"))%> </td>
                            <td> <%# RenderUtil.FormatString(Eval("ItemCode"))%> </td>
                            <td><%# RenderUtil.FormatString(Eval("ItemName"))%></td>
                            <td><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td><%# Eval("SizeCode")%></td>
                            <td><%# Eval("SellTypeName")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("Quantity"), "#0.##")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("Price"), "#,##0.#0")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("Amt"), "#,##0.#0")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("AvgMoveCost"), "#,##0.#0")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("CostAmt"), "#,##0.#0")%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
                <tr>
                    <td colspan="8" style="text-align:right;">**合计**&nbsp;&nbsp;</td>
                    <td style="text-align:right;">
                        <asp:Label ID="lblTotalAmt" runat="server" Text=""></asp:Label></td>
                    <td>&nbsp;</td>
                    <td style="text-align:right;"><asp:Label ID="lblCostAmt" runat="server" Text=""></asp:Label>&nbsp;</td>
                </tr>
        </table>
    </div>
    </form>
</body>
</html>