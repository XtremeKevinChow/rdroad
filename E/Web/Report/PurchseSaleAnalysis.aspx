<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchseSaleAnalysis.aspx.cs" Inherits="Report_PurchseSaleAnalysis" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>出入库统计</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
        a.dp-choose-date{ margin-top: 1px; }
        .datalist2 td { padding-right:2px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <style>
    .nowrap { white-space:nowrap;}
    </style>
<script type="text/javascript">
    $(document).ready(function() {
        $("#txtDateFrom").datePicker({ startDate: '2008-01-01' });
        $("#txtDateTo").datePicker({ startDate: '2008-01-01' });
    }); //$(document).ready(function()
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="库存情况查询" ExtInfo="日期范围不要太大，否则速度会很慢。勾选[全部产品]后会将没有采购、销售的产品也查询出来" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:33px;">
                    日期&nbsp;
                </td>
                <td style="width:230px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td class="label" style="width:35px;">货号</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtItemCode" CssClass="input" Width="80px" runat="server"></asp:TextBox></td>
                <td class="label" style="width:35px;">名称</td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtItemName" CssClass="input" Width="100px" runat="server"></asp:TextBox></td>
                <td>
                    <asp:CheckBox ID="chkAll" runat="server" Text="全部产品" /></td>
                <!--td class="label" style="width:35px;">颜色</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtColor" CssClass="input" Width="60px" runat="server"></asp:TextBox></td>
                <td class="label" style="width:35px;">尺码</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtSize" CssClass="input" Width="50px" runat="server"></asp:TextBox></td>
                <td></td-->
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
                    <td class="h nowrap" rowspan="2">货号</td>
                    <td class="h nowrap" rowspan="2">名称</td>
                    <td class="h nowrap" rowspan="2">当前库存</td>
                    <td class="h nowrap" colspan="2">开始日期</td>
                    <td class="h nowrap" colspan="2">采购收货</td>
                    <td class="h nowrap" colspan="2">采购退货</td>
                    <td class="h nowrap" colspan="2">销售</td>
                    <td class="h nowrap" colspan="2">销售退回</td>
                    <td class="h nowrap" rowspan="2">其他出入库</td>
                    <td class="h nowrap" colspan="2">结束日期</td>
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
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nowrap"><%# Eval("ItemCode")%></td>
                            <td class="nowrap"><%# Eval("ItemName")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("sto_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("begin_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("begin_amt"), "#,##0.#0")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("pur_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("pur_amt"), "#,##0.#0")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("pur_rtn_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("pur_rtn_amt"), "#,##0.#0")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("sale_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("sale_amt"), "#,##0.#0")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("sale_rtn_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("sale_rtn_amt"), "#,##0.#0")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("other_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("end_qty"), "#0.##")%></td>
                            <td class="nowrap" align="right"><%# RenderUtil.FormatNumber(Eval("end_amt"), "#,##0.#0")%></td>
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

