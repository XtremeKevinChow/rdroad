<%@ Page Language="C#" AutoEventWireup="true" CodeFile="MbrAccountDetail.aspx.cs" Inherits="Report_MbrAccountDetail" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>帐户变动明细</title>
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
    <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="帐户变动明细" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">库存期间&nbsp;</td>
                <td style="width:95px;">
                    <asp:DropDownList ID="drpPeriod" runat="server" Width="90px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:60px;">发货日期&nbsp;</td>
                <td style="width:220px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="65px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="65px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">变动原因&nbsp;</td>
                <td style="width:90px;">
                    <asp:DropDownList ID="drpFlush" runat="server" Width="85px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:60px;">支付方式&nbsp;</td>
                <td style="width:90px;">
                    <asp:DropDownList ID="drpPayment" runat="server" Width="85px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="label">凭证号码&nbsp;</td>
                <td style="width:95px;">
                    <asp:TextBox ID="txtOrder" runat="server" CssClass="input" Width="90px"></asp:TextBox>&nbsp;</td>
                <td class="label">会员号&nbsp;</td>
                <td>
                    <asp:TextBox ID="txtMbrID" runat="server" CssClass="input" Width="90px"></asp:TextBox>
                    　姓名
                    <asp:TextBox ID="txtMbrName" runat="server" CssClass="input" Width="60px"></asp:TextBox>
                </td>
                <td class="label">操作人&nbsp;</td>
                <td colspan="3">
                    <asp:TextBox ID="txtUser" runat="server" CssClass="input" Width="76px"></asp:TextBox>&nbsp;</td>
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
                    <td style="width:75px;" class="h">日期</td>
                    <td style="width:80px;" class="h">变动原因</td>
                    <td style="width:95px;" class="h">支付方式</td>
                    <td style="width:65px;" class="h">会员号</td>
                    <td style="width:55px;" class="h">姓名</td>
                    <td style="width:110px;" class="h">凭证号</td>
                    <td style="width:65px;" class="h">变动金额</td>
                    <td style="width:50px;" class="h">操作人</td>
                    <td class="h">操作备注</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td><%# Cast.DateTime(Eval("FlushDate")).ToString("yyyy-MM-dd")%></td>
                            <td><%# Eval("FlushType")%></td>
                            <td><%# Eval("PaymentType")%></td>
                            <td><%# Eval("MbrNum")%></td>
                            <td><%# Eval("MbrName")%></td>
                            <td style="width:110px;"><%# Eval("OrderNumber")%></td>
                            <td align="right"><%# RenderUtil.FormatNumber(Eval("FlushAmt"), "#,##0.#0")%>&nbsp;</td>
                            <td><%# Eval("UserName")%></td>
                            <td><%# Eval("comments")%></td>
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

