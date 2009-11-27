<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SaleReturnQuery.aspx.cs" Inherits="Report_SaleReturnQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>退货明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        a.dp-choose-date{ margin-top: 1px; }
        .hidden { display:none;}
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript" src="../script/selector.js"></script>
    <script type="text/javascript">
	  $(document).ready(function(){ 
	      $("#txtDateFrom").datePicker({startDate: '2008-01-01'});
	      $("#txtDateTo").datePicker({startDate: '2008-01-01'});
      });//$(document).ready(function()
      function viewDetail(ordNumber){
          var url = "SaleReturnQueryLine.aspx?ordNum=" + ordNumber + "&return=" + escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="退货明细" ExtInfo="每一个退货单号可以点击，查看退货详细信息" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    退货类型&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpReturnType" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:55px;">物流公司</td>
                <td>
                    <asp:DropDownList ID="drpLogis" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:40px;">
                    日期&nbsp;
                </td>
                <td style="width:230px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
            <tr>
                <td class="label" style="width:60px;">
                    退货单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtRTNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    发货单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtSNNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    订单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtSONumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td></td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdDownload1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg" Text="导出">
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
        <table class="datalist2" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h nw">退货日期</td>
                    <td class="h nw">退货单号</td>
                    <td class="h nw">发货单号</td>
                    <td class="h nw">订单号</td>
                    <td class="h nw">运单号</td>
                    <td class="h nw">退货类型</td>
                    <td class="h nw">物流公司</td>
                    <td class="h nw">销售收入</td>
                    <td class="h nw">发送费</td>
                    <td class="h nw">包装费</td>
                    <td class="h nw">礼券<br />抵扣</td>
                    <td class="h nw">销售<br />折扣</td>
                    <td class="h nw">退款合计</td>
                    <td class="h nw">退回<br />礼金</td>
                    <td class="h nw">退回<br />帐户</td>
                    <td class="h nw">POS<br />机收款</td>
                    <td class="h nw">物流<br />应收款</td>
                </tr>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><%# Eval("inner_date")%></td>
                            <td class="nw">
                                <a href='javascript:viewDetail("<%# Eval("RTNumber") %>");'><%# Eval("RTNumber")%></a>
                            </td>
                            <td class="nw"><%# Eval("SNNumber")%></td>
                            <td class="nw" style="width:95px;"><%# Eval("SONumber")%></td>
                            <td class="nw"><%# Eval("ShippingNumber")%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("OrderType"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("LogisName"))%></td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("SaleAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("TransportAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("PackageAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("CouponsAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("DiscountAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("ReturnAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("EMoneyAmt"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("AccountReceivable"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("Posreceivable"), "#,##0.#0", "0.00")%>&nbsp;</td>
                            <td class="nw" style="text-align:right;"><%# RenderUtil.FormatNumber(Eval("LogisReceivable"), "#,##0.#0", "0.00")%>&nbsp;</td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdDownload2" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg" Text="导出">
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
                        PageSize="25" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </div>
    </form>
</body>
</html>