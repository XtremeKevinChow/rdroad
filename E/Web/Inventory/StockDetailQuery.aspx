<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockDetailQuery.aspx.cs" Inherits="Inventory_StockDetailQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>明细库存查询</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <input type="hidden" id="hidReturnUrl" runat="server" value="StockDetailManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="明细库存查询" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:85px;">
                    SKU(条码)：  
                 </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtSku" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:45px;">
                    货号：  
                 </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:80px;">
                商品名称：</td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtItemName" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:45px;">
                    颜色：</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtColorCode" runat="server" CssClass="input" Width="70px"></asp:TextBox>
                </td>
                <td class="label" style="width:45px;">
                    尺码：</td>
                <td style="width:80px;">
                    <asp:TextBox ID="txtSizeCode" runat="server" CssClass="input" Width="70px"></asp:TextBox>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />                </td>
            </tr>
            <tr>
                <td class="label">
                    库位：</td>
                <td>
                    <asp:DropDownList ID="drpArea" runat="server" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label">
                    货架：</td>
                <td>
                    <asp:TextBox ID="txtSection" runat="server" CssClass="input" Width="70px"></asp:TextBox>
                </td>
                <td colspan="7">
                    &nbsp;</td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="23" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
            <tr>
                <td style="width:110px;" class="h">SKU(条码)</td>
                <td style="width:80px;" class="h">货号</td>
                <td style="width:90px;" class="h">颜色</td>
                <td style="width:60px;" class="h">尺码</td>
                <td style="width:60px;" class="h">库位</td>
                <td style="width:80px;" class="h">货架</td>
                <td style="width:60px;" class="h">库存量</td>
                <td class="h">商品名称</td>
            </tr>
            <asp:Repeater ID="rptPO" runat="server">
                <ItemTemplate>
                    <tr>
                        <td style="width:110px;"><%# Eval("BarCode") %></td>
                        <td><%# Eval("ItemCode") %></td>
                        <td><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                        <td><%# Eval("SizeCode")%></td>
                        <td><%# Eval("WHAName")%></td>
                        <td><%# Eval("SectionCode")%></td>
                        <td align="right" style="padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("StockQty"), "#0.##")%></td>
                        <td><%# Eval("ItemName") %></td>
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
                        PageSize="20" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>

