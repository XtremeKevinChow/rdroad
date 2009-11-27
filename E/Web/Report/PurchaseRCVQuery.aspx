<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseRCVQuery.aspx.cs" Inherits="Report_PurchaseRCVQueryLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购收货凭证</title>
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
          var url = "PurchaseRCVQueryLine.aspx?ordNum=" + ordNumber + "&return=" + escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购收货凭证" ExtInfo="每一个收货单号码可以点击，查看该收货单详细记录" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    供应商&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpVendor" runat="server" Width="100px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:60px;">
                    收货单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtRcvNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">
                    采购单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtPONumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
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
                    <td class="h" style="width:90px;">收货日期</td>
                    <td class="h" style="width:110px;">收货单号</td>
                    <td class="h" style="width:100px;">采购单号</td>
                    <td class="h" style="width:110px;">含税金额</td>
                    <td class="h" style="width:100px;">税额</td>
                    <td class="h" style="width:110px;">成本金额</td>
                    <td class="h">供应商</td>
                </tr>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td>
                                <%# Magic.Framework.Utils.Cast.DateTime(Eval("TransDate")).ToString("yyyy-MM-dd")%>
                            </td>
                            <td>
                                <a href='javascript:viewDetail("<%# Eval("OrderNumber") %>");'><%# Eval("OrderNumber")%></a>
                            </td>
                            <td style="width:130px;"><%# RenderUtil.FormatString(Eval("PONumber"))%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TaxInAmt"), "#,##0.#0", "0.00")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TaxAmt"), "#,##0.#0", "0.00")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TaxExAmt"), "#,##0.#0", "0.00")%></td>
                            <td><%# RenderUtil.FormatString(Eval("VendorName"))%></td>
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