<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ProductInApprove.aspx.cs" Inherits="Approve_ProductInApprove" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>产品入库审核</title>
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
      
      function Edit(ordNumber){
          var url = "ProductInEdit.aspx";
          if(ordNumber!=null && $.trim(ordNumber).length>0)
            url = url + "?mode=edit&ordNumber=" + ordNumber;
          else url = url + "?mode=new";
          url = url + "&return=" + escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
      function viewDetail(ordNumber){
          var url = "ProductInLine.aspx?ordNum=" + ordNumber + "&return=" + escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
	  function view(ordNumber){
	    if($.trim($("#hidViewUrl").val()).length<=0) return;
	     window.open(
	        $.trim($("#hidViewUrl").val())+"?ordNum="+ordNumber+"&m=sp"
	        , "viewasso"
	        , "height=550px,width=960px,left=20px,top=50px,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes");
	  }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="产品入库审核" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:56px;">
                    入库单号&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtOrderNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:33px;">
                    日期&nbsp;
                </td>
                <td style="width:240px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td class="label" style="width:33px;">
                    状态&nbsp;
                </td>
                <td>
                    &nbsp;
                    <asp:CheckBox ID="chkUnapproved" runat="server" Text="未审批" Checked="true" />&nbsp;
                    <asp:CheckBox ID="chkApproved" runat="server" Text="已审批" />&nbsp;
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
                        ShowPageSizeBox="true" PageSize="19" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
            <tr>
                <td class="h" style="width:70px;">操作</td>
                <td class="h" style="width:100px;">入库单号</td>
                <td class="h" style="width:50px;">状态</td>
                <td class="h" style="width:80px;">签核结果</td>
                <td class="h" style="width:70px;">处理人</td>
                <td class="h" style="width:110px;">处理时间</td>
                <td class="h">备注</td
            </tr>
            <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                <ItemTemplate>
                    <tr>
                        <td style="width:70px;">
                            <a href='javascript:Edit("<%# Eval("OrderNumber") %>");'>查看&nbsp;</a>
                            <a href='javascript:viewDetail("<%# Eval("OrderNumber") %>");'>明细</a>
                        </td>
                        <td style="width:100px;">
                            <a href='javascript:view("<%# Eval("OrderNumber")%>");'><%# RenderUtil.FormatString(Eval("OrderNumber"))%></a>
                        </td>
                        <td style="width:50px;">
                            <%# RenderUtil.FormatString(Eval("StatusText"))%>
                        </td>
                        <td style="width:80px;">
                            <asp:Label ID="lblApproveResult" runat="server" Text=""></asp:Label>
                        </td>
                        <td style="width:70px;">
                            <%# RenderUtil.FormatString(Eval("CreateUser"))%>
                        </td>
                        <td style="width:110px;"><%# RenderUtil.FormatDatetime(Eval("CreateTime"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("Note"))%></td>
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
                        PageSize="19" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>