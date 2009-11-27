<%@ Page Language="C#" AutoEventWireup="true" CodeFile="InventoryCheckDetail.aspx.cs" Inherits="Inventory_InventoryCheckDetail" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>盘点明细清单</title>
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
    function onupload() {
        window.location.href = "InventoryCheckImport.aspx?ordNum=" + $("#txtOrdNumber").val() + "&return=" + escape($("#txtReturnToThisUrl").val());
}
function onquickinput(){
}
function onrelease(){
    return confirm("确认发布本次盘点作业？");
}
function onclose(){
    return confirm("确认关闭本次盘点作业？");
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="盘点明细清单" />
        <input id="txtOrdNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtReturnToThisUrl" type="text" runat="server" class="hidden" value="" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:75px;">
                    显示方式&nbsp;
                </td>
                <td style="width:55px;">
                    <asp:DropDownList ID="drpViewType" runat="server" Width="50px" CssClass="select">
                        <asp:ListItem Text="全部" Value="1"></asp:ListItem>
                        <asp:ListItem Text="差异" Value="2"></asp:ListItem>
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:40px;">
                    库位&nbsp;
                </td>
                <td style="width:85px;">
                    <asp:DropDownList ID="drpArea" runat="server" Width="80px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:40px;">
                    货架&nbsp;
                </td>
                <td style="width:55px;">
                    <asp:TextBox ID="txtSection" runat="server" CssClass="input" Width="50px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    SKU&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtSKU" runat="server" CssClass="input" Width="105px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    货号&nbsp;
                </td>
                <td style="width:85px;">
                    <asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    名称&nbsp;
                </td>
                <td style="width:85px;">
                    <asp:TextBox ID="txtName" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    颜色&nbsp;
                </td>
                <td style="width:55px;">
                    <asp:TextBox ID="txtColor" runat="server" CssClass="input" Width="50px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    尺码&nbsp;
                </td>
                <td style="width:55px;">
                    <asp:TextBox ID="txtSize" runat="server" CssClass="input" Width="50px"></asp:TextBox>
                </td>
                <td></td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdDownload1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg"
                                Text="下载">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit1" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Upload" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_upload.gif" OnlyClient="true" OnClientClick="onupload()"
                                Text="导入盘点结果">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="页面录入保存">
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
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
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
                    <td style="width:40px;" class="h">行号</td>
                    <td style="width:60px;" class="h">库位</td>
                    <td style="width:90px;" class="h">货架</td>
                    <td style="width:110px;" class="h">SKU</td>
                    <td style="width:100px;" class="h">货号</td>
                    <td style="width:110px;" class="h">颜色</td>
                    <td style="width:50px;" class="h">尺码</td>
                    <td style="width:60px;" class="h">系统数量</td>
                    <td style="width:60px;" class="h">盘点数量</td>
                    <td class="h">商品名称</td>
                </tr>
                <asp:Repeater ID="repeatControl" runat="server" onitemdatabound="repeatControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:40px;"><%# RenderUtil.FormatString(Eval("LineNumber"))%></td>
                            <td style="width:60px;"><%# RenderUtil.FormatString(Eval("AreaCode"))%></td>
                            <td style="width:90px;"><%# RenderUtil.FormatString(Eval("SectionCode"))%></td>
                            <td style="width:110px;"><%# RenderUtil.FormatString(Eval("BarCode"))%></td>
                            <td style="width:100px;"><%# RenderUtil.FormatString(Eval("ItemCode"))%></td>
                            <td style="width:110px;">
                                <%# RenderUtil.FormatString(Eval("ColorCode"))%>&nbsp;
                                <%# RenderUtil.FormatString(Eval("ColorText"))%>
                            </td>
                            <td style="width:40px;"><%# RenderUtil.FormatString(Eval("SizeCode"))%></td>
                            <td style="width:60px;"><asp:Label ID="lblBefQty" runat="server" Text=""></asp:Label></td>
                            <td style="width:60px;">
                                <asp:TextBox ID="txtQty" runat="server" CssClass="input" lineNumber='<%# Eval("LineNumber")%>' MaxLength="8" Width="55px"></asp:TextBox>
                            </td>
                            <td><%# RenderUtil.FormatString(Eval("ItemName"))%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdDownload2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Download" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_download.jpg"
                                Text="下载">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"
                        Layout="Div" ID="cmdEdit2" ToolbarStyle="" OnItemCommand="MagicItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Upload" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_upload.gif" OnlyClient="true" OnClientClick="onupload()"
                                Text="导入盘点结果">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="页面录入保存">
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
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="19" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </div>
    </form>
</body>
</html>
