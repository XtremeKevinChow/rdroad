<%@ Page Language="C#" AutoEventWireup="true" CodeFile="DeliveryQueryDetail.aspx.cs" Inherits="SaleDelivery_DeliveryQueryDetail" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Register Src="../Controls/SNInfo.ascx" TagName="sn" TagPrefix="sn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>发货明细</title>
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
          var url = "InterchangeEdit.aspx";
          if(ordNumber!=null && $.trim(ordNumber).length>0)
            url = url + "?mode=edit&ordNumber=" + ordNumber;
          else url = url + "?mode=new";
          url = url + "&return=" + escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
      function viewDetail(ordNumber){
          var url = "InterchangeLine.aspx?ordNum="+ordNumber+"&return="+escape($("#hidReturnUrl").val());
          window.location.href=url;
      }
	  function ondelete(){
	    if(!hasSelect("#data_list_table")) {
	        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
	        return false;
	    }
	    return confirm("确实要删除选择的交接单？");
	  }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="发货明细" />
        <sn:sn ID="snView" runat="server" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="h" style="width:110px;">SKU</td>
                    <td class="h" style="width:110px;">货号</td>
                    <td class="h">名称</th>
                    <td class="h" style="width:120px;">颜色</td>
                    <td class="h" style="width:70px;">尺码</td>
                    <td class="h" style="width:80px;">数量</td>
                    <td class="h" style="width:80px;">价格</td>
                </tr>
                <asp:Repeater ID="repeatControl" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td style="width:110px;">
                                <%# RenderUtil.FormatString(Eval("BarCode"))%>
                            </td>
                            <td style="width:110px;">
                                <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemName"))%>
                            </td>
                            <td style="width:120px;">
                                <%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%>
                            </td>
                            <td style="width:70px;"><%# Eval("SizeCode")%></td>
                            <td style="width:80px;"><%# RenderUtil.FormatNumber(Eval("Quantity"), "#0.##")%></td>
                            <td style="width:80px;"><%# RenderUtil.FormatNumber(Eval("Price"), "#0.##")%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn2" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>