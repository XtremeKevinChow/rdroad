<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Stock_Sale_Diff.aspx.cs" Inherits="Report_Stock_Sale_Diff" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>库存－销售 情况</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="库存－销售 情况" />
        <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:33px;">
                    排序&nbsp;
                </td>
                <td style="width:120px;">
                    <asp:DropDownList ID="drpSort" runat="server" CssClass="select" Width="100px">
                        <asp:ListItem Value="1" Text="按SKU"></asp:ListItem>
                        <asp:ListItem Value="2" Text="按定购数量"></asp:ListItem>
                        <asp:ListItem Value="3" Text="按库存数量"></asp:ListItem>
                        <asp:ListItem Value="4" Text="按缺货数量" Selected="True"></asp:ListItem>
                    </asp:DropDownList>
                </td>
                <td>
                    <asp:CheckBox ID="chkOnlyStoLack" runat="server" Checked="true" Text="只查缺货的" /></td>
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
                    <td style="width:120px;" class="h nw">SKU</td>
                    <td style="width:70px;" class="h nw">货号</td>
                    <td class="h nw">名称</td>
                    <td style="width:95px;" class="h nw">颜色</td>
                    <td style="width:50px;" class="h nw">尺码</td>
                    <td style="width:90px;" class="h nw">当前定购数量</td>
                    <td style="width:100px;" class="h nw">暂存架待发数量</td>
                    <td style="width:60px;" class="h nw">库存数量</td>
                    <td style="width:60px;" class="h nw">非正式仓</td>
                    <td style="width:60px;" class="h nw">缺货数量</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><%# Eval("SKU")%></td>
                            <td class="nw"><%# Eval("ItemCode")%></td>
                            <td class="nw"><%# Eval("ItemName")%></td>
                            <td class="nw"><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td class="nw"><%# Eval("SizeCode")%></td>
                            <td class="nw" align="right" style="padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("OrdQty"), "#,##", "0")%></td>
                            <td class="nw" align="right" style="padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TempQty"), "#,##", "0")%></td>
                            <td class="nw" align="right" style="padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("StoQty"), "#,##", "0")%></td>
                            <td class="nw" align="right" style="padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("PeddingQty"), "#,##", "0")%></td>
                            <td class="nw" align="right" style="padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("LackQty"), "#,##", "0")%></td>
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