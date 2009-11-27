﻿<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseBillingQuery.aspx.cs" Inherits="Report_PurchaseBillingQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购收货汇总</title>
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
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购收货汇总" />
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
                <td class="label" style="width:50px;">
                    供应商&nbsp;
                </td>
                <td style="width:120px;">
                    <asp:DropDownList ID="drpVendor" runat="server" CssClass="select" Width="110px">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:50px;">
                    采购单&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtPO" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    货号&nbsp;
                </td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="70px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    颜色&nbsp;
                </td>
                <td style="width:45px;">
                    <asp:TextBox ID="txtColor" runat="server" CssClass="input" Width="40px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    尺码&nbsp;
                </td>
                <td style="width:45px;">
                    <asp:TextBox ID="txtSize" runat="server" CssClass="input" Width="40px"></asp:TextBox>
                </td>
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
                    <td class="h">SKU</td>
                    <td class="h">货号</td>
                    <td class="h">名称</td>
                    <td class="h">颜色</td>
                    <td class="h">尺码</td>
                    <td style="width:80px;" class="h">总收货数量</td>
                    <td style="width:100px;" class="h">含税总金额</td>
                    <td style="width:80px;" class="h">税额</td>
                    <td style="width:80px;" class="h">成本金额</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td><%# Eval("BarCode")%></td>
                            <td><%# Eval("ItemCode")%></td>
                            <td><%# Eval("ItemName")%></td>
                            <td><%# Eval("ColorCode")%> <%# Eval("ColorText")%></td>
                            <td><%# Eval("SizeCode")%></td>
                            <td align="right"><%# Eval("TransQty")%></td>
                            <td align="right"><%# RenderUtil.FormatNumber(Eval("TaxInAmt"), "#,##0.#0")%></td>
                            <td align="right"><%# RenderUtil.FormatNumber(Eval("TaxAmt"), "#,##0.#0")%></td>
                            <td align="right"><%# RenderUtil.FormatNumber(Eval("TaxExAmt"), "#,##0.#0")%></td>
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
                    <mwu:MagicPager ID="magicPager1" runat="server" OnPageChanged="MagicPager_PageChanged" 
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

