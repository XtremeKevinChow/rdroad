<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SaleAmtSum.aspx.cs" Inherits="Report_SaleAmtSum" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>销售统计</title>
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
  function viewDetail(logisId) {
      window.location.href = $("#hidReturnUrl").val() + "&lg="+logisId;
  }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="销售统计" ExtInfo="每个物流公司可以点击，查看详细的发货清单" />
        <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    库存期间:
                 </td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpPeriod" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:33px;">
                    日期&nbsp;
                </td>
                <td style="width:240px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
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
                    <td  class="h nw">物流公司</td>
                    <td  class="h nw">销售收入</td>
                    <td  class="h nw">发送费</td>
                    <td  class="h nw">包装费</td>
                    <td  class="h nw">礼券抵扣</td>
                    <td  class="h nw">销售折扣</td>
                    <td  class="h nw">订单金额合计</td>
                    <td  class="h nw">礼金支付</td>
                    <td  class="h nw">帐户支付</td>
                    <td  class="h nw">POS机收款</td>
                    <td  class="h nw">物流应收款</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><a href='javascript:viewDetail(<%# Eval("LogisID") %>);'><%# Eval("LogisName")%></a></td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("SaleAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("TransportAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("PackageAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("CouponsAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("DiscountAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("OrderAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("EMoneyAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("AccountReceivable"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("PosReceivable"), "#,##0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("LogisReceivable"), "#,##0.#0")%>&nbsp;</td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
    </div>
    <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>