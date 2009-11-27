<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MbrAccountSum.aspx.cs" Inherits="Report_MbrAccountSum" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>帐户变动汇总</title>
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
  function viewDetail(flushId, paymentId) {
      var intFlush = parseInt(flushId), intPayment = parseInt(paymentId);
      intFlush = isNaN(intFlush) ? 0 : intFlush;
      intPayment = isNaN(intPayment) ? 0 : intPayment;
      window.location.href = "MbrAccountDetail.aspx?mode=fix&pd=" + $("#drpPeriod").val() + "&df=" + $("#txtDateFrom").val() + "&dt=" + $("#txtDateTo").val() + "&flush=" + intFlush + "&payment=" + intPayment + "&return=" + escape($("#hidReturnUrl").val());
  }
  function view_account() {
      var pdid = parseInt($("#drpPeriod").val());
      pdid = isNaN(pdid) ? 0 : pdid;
      if (pdid <= 0) {
          alert("请选择一个库存期间，再查询帐户余额明细情况");
          return;
      }
      window.location.href = "MbrAccountBalance.aspx?pd=" + pdid + "&return=" + escape($("#hidReturnUrl").val());
  }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="帐户变动汇总" ExtInfo="每一个汇总金额可以点击，查看该汇总金额的详细情况" />
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
<br />
<div>
    <asp:Label ID="Label1" runat="server" Text="期初余额: "></asp:Label>
    <asp:Label ID="lblBegin" runat="server" Text=""></asp:Label>
    <asp:Label ID="Label2" runat="server" Text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></asp:Label>
    <asp:Label ID="Label3" runat="server" Text="期末余额: "></asp:Label>
    <asp:Label ID="lblEnd" runat="server" Text=""></asp:Label>
    <asp:Label ID="Label4" runat="server" Text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></asp:Label>
    <a href="javascript:view_account();">查看帐户余额明细</a>
</div>
        <table class="datalist2" cellpadding="0" cellspacing="0" style="width:350px;">
                <tr>
                    <td class="h">变动原因</td>
                    <td class="h">支付方式</td>
                    <td style="width:60px;" class="h">汇总金额</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td><%# Eval("FlushType")%></td>
                            <td><%# Eval("PaymentType")%></td>
                            <td align="right"><a href='javascript:viewDetail("<%# Eval("FlushID")%>","<%# Eval("PaymentID")%>");'><%# RenderUtil.FormatNumber(Eval("TotalAmt"), "#,##0.#0")%></a></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
    </div>
    </form>
</body>
</html>

