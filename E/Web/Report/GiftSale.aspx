<%@ Page Language="C#" AutoEventWireup="true" CodeFile="GiftSale.aspx.cs" Inherits="Report_GiftSale" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>礼品销售</title>
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
      $('#txtDateFrom').datePicker({startDate: '2008-01-01'});
      $('#txtDateTo').datePicker({startDate: '2008-01-01'});
  }); //$(document).ready(function()
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="礼品销售" />
        <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
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
                    <td  class="h nw" style="width:120px;">SKU</td>
                    <td  class="h nw" style="width:90px;">货号</td>
                    <td  class="h nw">名称</td>
                    <td  class="h nw" style="width:110px;">颜色</td>
                    <td  class="h nw" style="width:50px;">尺码</td>
                    <td  class="h nw" style="width:70px;">数量</td>
                    <td  class="h nw" style="width:100px;">平均成本</td>
                    <td  class="h nw" style="width:100px;">成本金额</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><%# Eval("BarCode")%></td>
                            <td class="nw"><%# Eval("ItemCode")%></td>
                            <td class="nw"><%# Eval("ItemName")%></td>
                            <td class="nw"><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td class="nw"><%# Eval("SizeCode")%></td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("Qty"), "#,##")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("AvgPrice"), "#,#0.#0")%>&nbsp;</td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("CostAmt"), "#,#0.#0")%>&nbsp;</td>
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