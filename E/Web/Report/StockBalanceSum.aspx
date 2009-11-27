<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockBalanceSum.aspx.cs" Inherits="Report_StockBalanceSum" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>出入库统计</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <style>
    .nowrap { white-space:nowrap;}
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="出入库统计" ExtInfo="每一个产品类型可以点击，查看属于该产品类型的所有产品出入库明细情况" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    库存期间:
                 </td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpPeriod" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td></td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />                </td>
            </tr>
        </table>
        <div style=" text-align:center;">
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="MagicToolBar1">
                <Items>
                    <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                        ImageUrl="../images/b_download.jpg"
                        Text="导出">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
        </div>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h nowrap" rowspan="2">产品类型</td>
                    <td class="h nowrap" colspan="2">期初</td>
                    <td class="h nowrap" colspan="2">采购收货</td>
                    <td class="h nowrap" colspan="2">采购退货</td>
                    <td class="h nowrap" colspan="2">销售</td>
                    <td class="h nowrap" colspan="2">销售退回</td>
                    <td class="h nowrap" colspan="2">产品领用</td>
                    <td class="h nowrap" colspan="2">产品入库</td>
                    <td class="h nowrap" colspan="2">盘点</td>
                    <td class="h nowrap" colspan="2">手工调整</td>
                    <td class="h nowrap" colspan="2">报废</td>
                    <td class="h nowrap" rowspan="2">差异金额</td>
                    <td class="h nowrap" colspan="2">期末</td>
                </tr>
                <tr>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                    <td class="h nowrap">数量</td>
                    <td class="h nowrap">金额</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nowrap"><a href='StockBalanceDetail.aspx?mode=fix&cat=<%# Eval("CategoryID") %>&pd=<%= this.drpPeriod.SelectedValue %>'><%# Eval("CatName")%></a></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("BeginQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("BeginAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurReturnQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurReturnAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleReturnQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleReturnAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("UsedQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("UsedAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("OtherInQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("OtherInAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("CheckQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("CheckAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("AdjustQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("AdjustAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("ScrapQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("ScrapAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("DiffAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("EndQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("EndAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
    </div>
    <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>

