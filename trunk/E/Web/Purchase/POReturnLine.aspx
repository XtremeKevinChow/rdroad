<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POReturnLine.aspx.cs" Inherits="Purchase_POReturnLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>退货明细</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
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
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript" src="../script/selector.js"></script>
<script type="text/javascript">
  $(document).ready(function(){ 
      bindTableBehavior('#data_list_table', '#chkSelectAll');	
  });//$(document).ready(function()
      
function checkSelect()
{
    if(!hasSelect("#data_list_table"))
    {
        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
        return false;
    }
    return true;
}
function onadd(){
    if ($.trim($("#txtOrderNumber").val()).length <= 0) return;
    window.location.href = "POReturnNewLine.aspx?ordNum=" + $("#txtOrderNumber").val()
        + "&return=" + escape($("#txtReturnUrl").val());
}
function ondelete(){
    if(!checkSelect()) return false;
    return confirm("确定要删除选择的退货明细？");
}
function onrelease(){
    return confirm("确信发布采购退货单？");
}
function onclose(){
    return confirm("确信关闭采购退货单？");
}
function onprint() {
    if ($.trim($("#txtOrderNumber").val()).length <= 0) return;
    if ($.trim($("#hidViewUrl").val()).length <= 0) return;
    window.open(
	        $.trim($("#hidViewUrl").val()) + "?ordNum=" + $("#txtOrderNumber").val()
	        , "viewasso"
	        , "height=550px,width=960px,left=20px,top=50px,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes");
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="退货明细" />
        <input id="txtOrderNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtReturnUrl" type="text" runat="server" class="hidden" value="" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onadd()"
                                Text="添加明细">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除明细">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
                                Text="发布">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdClose1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_finish.jpg" Text="完成" OnClientClick="onclose()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="ImageButton" OnlyClient="true"
                                ImageUrl="../images/b_print.gif" OnClientClick="onprint()"
                                Text="打印">
                            </mwu:MagicItem>
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
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:40px;">行号</th>
                    <th style="width:80px;">PO号码</th>
                    <th style="width:40px;">PO行</th>
                    <th style="width:100px;">SKU</th>
                    <th style="width:100px;">货号</th>
                    <th style="width:100px;">颜色</th>
                    <th style="width:40px;">尺码</th>
                    <th>商品名称</th>
                    <th style="width:60px;">库位</th>
                    <th style="width:90px;">货架</th>
                    <th style="width:60px;">退货数量</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:20px;">
                                <input id="checkbox" value='<%# Eval("LineNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width:40px;">
                                <%# RenderUtil.FormatString(Eval("LineNumber"))%>
                            </td>
                            <td style="width:80px;">
                                <%# RenderUtil.FormatString(Eval("PONumber"))%>
                            </td>
                            <td style="width:40px;">
                                <%# RenderUtil.FormatString(Eval("POLine"))%>
                            </td>
                            <td style="width:100px;">
                                <%# RenderUtil.FormatString(Eval("BarCode"))%>
                            </td>
                            <td style="width:100px;">
                                <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ColorCode"))%>&nbsp;
                                <%# RenderUtil.FormatString(Eval("ColorText"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("SizeCode"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemName"))%>
                            </td>
                            <td style="width:60px;">
                                <%# RenderUtil.FormatString(Eval("AreaCode"))%>
                            </td>
                            <td style="width:90px;">
                                <%# RenderUtil.FormatString(Eval("SectionCode"))%>
                            </td>
                            <td style="width:60px;">
                                <%# RenderUtil.FormatNumber(Eval("Qty"), "#0.##")%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onadd()"
                                Text="添加明细">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除明细">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
                                Text="发布">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdClose2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_finish.jpg" Text="完成" OnClientClick="onclose()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdReturn2" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="ImageButton" OnlyClient="true"
                                ImageUrl="../images/b_print.gif" OnClientClick="onprint()"
                                Text="打印">
                            </mwu:MagicItem>
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
