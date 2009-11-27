<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SaleAmtDetail.aspx.cs" Inherits="Report_SaleAmtDetail" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>销售款明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
        a.dp-choose-date{ margin-top: 1px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
<script type="text/javascript">
  $(document).ready(function(){ 
      $("#txtDateFrom").datePicker({startDate: '2008-01-01'});
      $("#txtDateTo").datePicker({startDate: '2008-01-01'});
  }); //$(document).ready(function()
  function snDetail(sn) {
      window.location.href = "SNDetail.aspx?ordNum=" + sn + "&return=" + escape($("#hidReturnUrl").val());
  }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="销售款明细" ExtInfo="实际应收款一栏方便对账之用，其金额为0表明会员拒收包裹(直接由物流公司退回)，实际物流应收款(或POS机收款)应为0；点击发货单号码查看发货清单" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">库存期间</td>
                <td style="width:70px;">
                    <asp:DropDownList ID="drpPeriod" runat="server" Width="66px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:55px;">发货日期</td>
                <td style="width:220px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="65px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="65px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td class="label" style="width:56px;">物流公司</td>
                <td style="width:80px;">
                    <asp:DropDownList ID="drpLogis" runat="server" Width="76px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:45px;">发货单</td>
                <td style="width:84px;">
                    <asp:TextBox ID="txtSNNumber" runat="server" CssClass="input" Width="76px"></asp:TextBox>&nbsp;</td>
                <td class="label" style="width:35px;">订单</td>
                <td style="width:84px;">
                    <asp:TextBox ID="txtSONumber" runat="server" CssClass="input" Width="76px"></asp:TextBox>&nbsp;</td>
                <td class="label" style="width:45px;">运单号</td>
                <td style="width:84px;">
                    <asp:TextBox ID="txtShippingNumber" runat="server" CssClass="input" Width="76px"></asp:TextBox>&nbsp;</td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="cmdDownload">
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
                    <td class="h nw">发货日期</td>
                    <td class="h nw">发货单</td>
                    <td class="h nw">订单</td>
                    <td class="h nw">运单号</td>
                    <td class="h nw">成本金额</td>
                    <td class="h nw">销售收入</td>
                    <td class="h nw">发送费</td>
                    <td class="h nw">包装费</td>
                    <td class="h nw">礼券抵扣</td>
                    <td class="h nw">销售折扣</td>
                    <td class="h nw">礼金支付</td>
                    <td class="h nw">帐户支付</td>
                    <td class="h nw">POS机收款</td>
                    <td class="h nw">物流应收款</td>
                    <td class="h nw">实际应收款</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><%# Cast.DateTime(Eval("TransDate")).ToString("yyyy-MM-dd")%></td>
                            <td class="nw"><a href='javascript:snDetail("<%# Eval("SNNumber") %>");'><%# Eval("SNNumber")%></a></td>
                            <td class="nw"><%# Eval("SONumber")%></td>
                            <td class="nw"><%# Eval("ShippingNumber")%></td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("CostAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("SaleAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("TransportAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("PackageAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("CouponsAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("DiscountAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("EMoneyAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("AccountReceivable"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("PosReceivable"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("LogisReceivable"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("ActualReceivable"), "#,##0.#0")%>&nbsp;</td>
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

