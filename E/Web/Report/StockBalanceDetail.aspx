<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockBalanceDetail.aspx.cs" Inherits="Report_StockBalanceDetail" %>
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
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="出入库统计" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    库存期间:
                 </td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpPeriod" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:55px;">
                    产品类型
                </td>
                <td style="width:80px;">
                    <asp:DropDownList ID="drpItemCat" runat="server" Width="70px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:35px;">货号</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtItemCode" CssClass="input" Width="80px" runat="server"></asp:TextBox></td>
                <td class="label" style="width:35px;">名称</td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtItemName" CssClass="input" Width="100px" runat="server"></asp:TextBox></td>
                <td class="label" style="width:35px;">颜色</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtColor" CssClass="input" Width="60px" runat="server"></asp:TextBox></td>
                <td class="label" style="width:35px;">尺码</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtSize" CssClass="input" Width="50px" runat="server"></asp:TextBox></td>
                <td></td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="cmdDownload1">
                <Items>
                    <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                        ImageUrl="../images/b_download.jpg"
                        Text="导出">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
            <mwu:MagicToolBar CssClass="toolbar" runat="server"
                Layout="Div" ID="cmdReturn1">
                <Items>
                    <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                        ImageUrl="../images/b_back.gif" OnlyClient="true"
                        Text="返回">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="25" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h nowrap" rowspan="2">类型</td>
                    <td class="h nowrap" rowspan="2">货号</td>
                    <td class="h nowrap" rowspan="2">名称</td>
                    <td class="h nowrap" rowspan="2">颜色</td>
                    <td class="h nowrap" rowspan="2">尺码</td>
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
                            <td class="nowrap"><%# Eval("CatName")%></td>
                            <td class="nowrap"><%# Eval("ItemCode")%></td>
                            <td class="nowrap"><%# Eval("ItemName")%></td>
                            <td class="nowrap"><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td class="nowrap"><%# Eval("SizeCode")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("BeginQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("BeginAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurReturnQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("PurReturnAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleReturnQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("SaleReturnAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("UsedQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("UsedAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("OtherInQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("OtherInAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("CheckQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("CheckAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("AdjustQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("AdjustAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("ScrapQty"), "#0.##", "0")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("ScrapAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("DiffAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("EndQty"), "#0.##")%>&nbsp;</td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("EndAmt"), "#,##0.#0")%>&nbsp;</td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="cmdDownload2">
                <Items>
                    <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                        ImageUrl="../images/b_download.jpg"
                        Text="导出">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
            <mwu:MagicToolBar CssClass="toolbar" runat="server"
                Layout="Div" ID="cmdReturn2">
                <Items>
                    <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                        ImageUrl="../images/b_back.gif" OnlyClient="true"
                        Text="返回">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="25" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>

