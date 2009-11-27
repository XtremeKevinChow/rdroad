<%@ Page Language="C#" AutoEventWireup="true" CodeFile="DeliveryQuery.aspx.cs" Inherits="SaleDelivery_DeliveryQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>发货单查询</title>
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
    <script type="text/javascript" src="../script/note.js"></script>
    <script type="text/javascript">
	  $(document).ready(function(){ 
	      $("#txtDateFrom").datePicker({startDate: '2008-01-01'});
	      $("#txtDateTo").datePicker({startDate: '2008-01-01'});
      });//$(document).ready(function()
      
      function viewDetail(ordNumber){
          var url = "DeliveryQueryDetail.aspx?ordNum="+ordNumber+"&return="+escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="发货单查询" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">发货单&nbsp;</td>
                <td style="width:90px;">
                    <asp:TextBox ID="txtSDNumber" runat="server" MaxLength="13" CssClass="input" Width="85px"></asp:TextBox>
                </td>
                <td class="label" style="width:60px;">订单&nbsp;</td>
                <td style="width:105px;">
                    <asp:TextBox ID="txtSONumber" runat="server" MaxLength="13" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:52px;">运单号&nbsp;</td>
                <td style="width:95px;">
                    <asp:TextBox ID="txtShippingNumber" runat="server" MaxLength="30" CssClass="input" Width="90px"></asp:TextBox>
                </td>
                <td class="label" style="width:37px;">发票&nbsp;</td>
                <td style="width:95px;">
                    <asp:TextBox ID="txtInvoice" runat="server" MaxLength="30" CssClass="input" Width="90px"></asp:TextBox>
                </td>
                <td class="label" style="width:37px;">日期&nbsp;</td>
                <td style="width:225px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label">发票已录&nbsp;</td>
                <td>
                    <asp:DropDownList ID="drpInvoice" runat="server" Width="60px" CssClass="select">
                        <asp:ListItem Text="　" Value=""></asp:ListItem>
                        <asp:ListItem Text="是" Value="1"></asp:ListItem>
                        <asp:ListItem Text="否" Value="0"></asp:ListItem>
                    </asp:DropDownList>
                </td>
                <td class="label">物流公司&nbsp;</td>
                <td>
                    <asp:DropDownList ID="drpLogis" runat="server" Width="105px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label">
                    状态&nbsp;
                </td>
                <td colspan="5">
                    <asp:CheckBox ID="chkDistributing" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkChecked" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkPackaged" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkInterchanged" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkReturn" runat="server" Text="" />&nbsp;
                    <asp:CheckBox ID="chkPartReturn" runat="server" Text="" />&nbsp;
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="24" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h nw" style="width:30px;">操作</td>
                    <td class="h nw">发货单</td>
                    <td class="h nw">订单</td>
                    <td class="h nw">状态</td>
                    <td class="h nw">发票状态</td>
                    <td class="h nw">创建日期</td>
                    <td class="h nw">订单日期</td>
                    <td class="h nw">打印日期</td>
                    <td class="h nw">包装日期</td>
                    <td class="h nw">会员</td>
                    <td class="h nw">联系人</td>
                    <td class="h nw">运单</td>
                    <td class="h nw">物流公司</td>
                    <td class="h nw">省,市&nbsp;&nbsp;区,县</td>
                </tr>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw">
                                <a href='javascript:viewDetail("<%# Eval("OrderNumber") %>");'>明细</a>
                            </td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("OrderNumber"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("SaleOrderNumber"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("StatusText"))%></td>
                            <td class="nw"><%# this.FormatInvoiceStatus(Eval("InvoiceNumber"))%></td>
                            <td class="nw"><%# RenderUtil.FormatDate(Eval("CreateTime"))%></td>
                            <td class="nw"><%# RenderUtil.FormatDate(Eval("OrdReleaseDate"))%></td>
                            <td class="nw"><%# RenderUtil.FormatDate(Eval("PrintDate"))%></td>
                            <td class="nw"><%# RenderUtil.FormatDate(Eval("PackageDate"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("MemberName"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("Contact"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("ShippingNumber"))%></td>
                            <td class="nw"><%# RenderUtil.FormatString(Eval("LogisName"))%></td>
                            <td class="nw"><%# this.DistrictString(Eval("Province"), Eval("City"))%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="24" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>