<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CSLogQuery.aspx.cs" Inherits="SaleDelivery_CSLogQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>CRM电话记录查询</title>
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
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="CRM电话记录查询" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:50px;">大类&nbsp;</td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpMainType" runat="server" CssClass="select" 
                        Width="100px" onselectedindexchanged="drpMainType_SelectedIndexChanged" 
                        AutoPostBack="True">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:50px;">小类&nbsp;</td>
                <td style="width:110px;">
                    <asp:DropDownList ID="drpSubType" runat="server" CssClass="select" Width="100px">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:60px;">
                    日期&nbsp;
                </td>
                <td style="width:225px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label">经办人&nbsp;</td>
                <td>
                    <asp:DropDownList ID="drpUser" runat="server" Width="60px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label">会员号&nbsp;</td>
                <td>
                    <asp:TextBox ID="txtMbrNum" runat="server" CssClass="input" Width="80px"></asp:TextBox></td>
                <td class="label">会员姓名&nbsp;</td>
                <td>
                    <asp:TextBox ID="txtMbrName" runat="server" CssClass="input" Width="80px"></asp:TextBox></td>
                <td><asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" /></td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
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
                <td class="h" style="width:45px;">类别</td>
                <td class="h" style="width:110px;">大类</td>
                <td class="h" style="width:110px;">小类</td>
                <td class="h" style="width:50px;">经办人</td>
                <td class="h" style="width:105px;">时间</td>
                <td class="h" style="width:74px;">会员号</td>
                <td class="h" style="width:55px;">会员姓名</td>
                <td class="h">内容</td>
            </tr>
            <asp:Repeater ID="repeatControl" runat="server">
                <ItemTemplate>
                    <tr>
                        <td><%# RenderUtil.FormatString(Eval("Type"))%></td>
                        <td style="width:110px;"><%# RenderUtil.FormatString(Eval("MainType"))%></td>
                        <td style="width:110px;"><%# RenderUtil.FormatString(Eval("SubType"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("CreateUser"))%></td>
                        <td><%# RenderUtil.FormatDatetime(Eval("Create_Date"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("MbrNum"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("MbrName"))%></td>
                        <td><%# RenderUtil.FormatStringEx(Eval("Content"))%></td>
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
                        PageSize="25" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>