<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PurchaseRCVLine.aspx.cs" Inherits="Receive_PurchaseRCVLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>收货明细</title>
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
function onadd() {
    window.location.href = "PurchaseRCVLineScan.aspx?ordNum=" + $("#txtRCVNumber").val() + "&return=" + escape($("#txtReturnUrl").val());
}
function ondelete(){
    if(!checkSelect()) return false;
    return confirm("确定要删除选择的收货明细？");
}
function onrelease(){
    return confirm("确信发布收货单？");
}
function onclose(){
    return confirm("确信关闭收货单？");
}
function onprint() {
    if ($.trim($("#txtRCVNumber").val()).length <= 0) return;
    window.open("PurchaseRCVPrint.aspx?ordNum=" + $("#txtRCVNumber").val(), "print", "width=990,height=600,left=10,top=10,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="收货明细" />
        <input id="txtStatus" type="text" runat="server" class="hidden" value="" />
        <input id="txtRCVNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtPONumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtReturnUrl" type="text" runat="server" class="hidden" value="" />
        <input id="txtPOLines" type="text" runat="server" class="hidden" value="" />
        <asp:Button ID="cmdAddLines" runat="server" Text="" CssClass="hidden" OnClick="cmdAddLines_Click" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onadd()"
                                Text="扫描入库">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除明细">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存">
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
                        Layout="Div" ID="cmdPrint1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_print.gif" OnClientClick="onprint()" Text="打印">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
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
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:40px;">行号</th>
                    <th style="width:55px;">采购行号</th>
                    <th style="width:110px;">SKU(条码)</th>
                    <th style="width:70px;">货号</th>
                    <th>商品名称</th>
                    <th style="width:100px;">颜色</th>
                    <th style="width:40px;">尺码</th>
                    <th style="width:60px;">库位</th>
                    <th style="width:90px;">货架</th>
                    <th style="width:60px;">收货数量</th>
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
                            <td style="width:55px;">
                                <%# RenderUtil.FormatString(Eval("RefOrderLine"))%>
                            </td>
                            <td style="width:110px;">
                                <%# RenderUtil.FormatString(Eval("BarCode"))%>
                            </td>
                            <td style="width:70px;">
                                <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemName"))%>
                            </td>
                            <td style="width:100px;">
                                <%# RenderUtil.FormatString(Eval("ColorCode"))%>&nbsp;
                                <%# RenderUtil.FormatString(Eval("ColorText"))%>
                            </td>
                            <td style="width:40px;">
                                <%# RenderUtil.FormatString(Eval("SizeCode"))%>
                            </td>
                            <td style="width:60px;">
                                <asp:DropDownList ID="drpArea" runat="server" Width="55px" Height="19px">
                                </asp:DropDownList>
                            </td>
                            <td style="width:90px;">
                                <asp:TextBox ID="txtSection" runat="server" CssClass="input" Width="85px" MaxLength="10"></asp:TextBox>
                            </td>
                            <td style="width:60px;">
                                <asp:TextBox ID="txtQualifiedQty" runat="server" CssClass="input" MaxLength="10" Width="55px"></asp:TextBox>
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
                                Text="扫描入库">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif" CancelServerEvent="true" OnClientClick="ondelete()"
                                Text="删除明细">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存">
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
                        Layout="Div" ID="cmdPrint2" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Print" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_print.gif" OnClientClick="onprint()" Text="打印">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
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

<!--
<mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton"
    ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onadd()"
    Text="添加明细">
</mwu:MagicItem>
-->