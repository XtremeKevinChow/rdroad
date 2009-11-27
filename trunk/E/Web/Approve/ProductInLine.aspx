<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ProductInLine.aspx.cs" Inherits="Approve_ProductInLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>入库单明细</title>
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
function onrelease(){
    return confirm("确信完成入库价格审核？");
}
function onprint() {
    if ($.trim($("#txtOrderNumber").val()).length <= 0) return;
    if ($.trim($("#hidViewUrl").val()).length <= 0) return;
    window.open(
	        $.trim($("#hidViewUrl").val()) + "?ordNum=" + $("#txtOrderNumber").val()+"&m=sp"
	        , "viewasso"
	        , "height=550px,width=960px,left=20px,top=50px,toolbar=no,status=no,menubar=no,location=no,scrollbars=yes");
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="入库单明细" />
        <input id="txtOrderNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtReturnUrl" type="text" runat="server" class="hidden" value="" />
        <input type="hidden" id="hidViewUrl" runat="server" value="" />
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存入库价格">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
                                Text="提交">
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
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
            <tr>
                <td class="h" style="width:40px;">行号</td>
                <td class="h" style="width:100px;">SKU</td>
                <td class="h" style="width:100px;">货号</td>
                <td class="h" style="width:100px;">颜色</td>
                <td class="h" style="width:40px;">尺码</td>
                <td class="h">商品名称</td>
                <td class="h" style="width:40px;">库位</td>
                <td class="h" style="width:90px;">货架</td>
                <td class="h" style="width:60px;">入库数量</td>
                <td class="h" style="width:60px;">入库价格</td>
            </tr>
            <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                <ItemTemplate>
                    <tr>
                        <td style="width:40px;">
                            <input type="hidden" runat="server" id="hidLine" sku='<%#Eval("BarCode") %>' value='<%# Eval("LineNumber") %>' />
                            <%# RenderUtil.FormatString(Eval("LineNumber"))%>
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
                        <td style="width:40px;">
                            <%# RenderUtil.FormatString(Eval("AreaCode"))%>
                        </td>
                        <td style="width:90px;">
                            <%# RenderUtil.FormatString(Eval("SectionCode"))%>
                        </td>
                        <td style="width:60px;">
                            <%# RenderUtil.FormatNumber(Eval("Quantity"), "#0.##")%>
                        </td>
                        <td style="width:60px;">
                            <asp:TextBox ID="txtPrice" runat="server" CssClass="input" Width="55px"></asp:TextBox>
                        </td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存入库价格">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif" CancelServerEvent="true" OnClientClick="onrelease()"
                                Text="提交">
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
