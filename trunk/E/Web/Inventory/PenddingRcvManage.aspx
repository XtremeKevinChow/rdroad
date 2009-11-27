<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PenddingRcvManage.aspx.cs" Inherits="Inventory_PenddingRcvManage" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>汇总库存查询</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <style type="text/css">
        .textright { text-align: right; margin-right:4px; padding-right:2px; }
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <input type="hidden" id="hidReturnUrl" runat="server" value="StockSummaryManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="汇总库存查询" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:105px;">
                    <asp:CheckBox ID="chkFlag" runat="server" Text="只查有数量的&nbsp;" /></td>
                <td class="label" style="width:85px;">SKU(条码)：</td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtSku" runat="server" CssClass="input" Width="100px" MaxLength="18"></asp:TextBox>
                </td>
                <td class="label" style="width:45px;">货号：</td>
                <td style="width:95px;">
                    <asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="90px" MaxLength="18"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">商品名称：</td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtItemName" runat="server" CssClass="input" Width="100px" MaxLength="30"></asp:TextBox>
                </td>
                <td class="label" style="width:45px;">颜色：</td>
                <td style="width:65px;">
                    <asp:TextBox ID="txtColorCode" runat="server" CssClass="input" Width="60px"></asp:TextBox>
                </td>
                <td class="label" style="width:45px;">尺码：</td>
                <td style="width:65px;">
                    <asp:TextBox ID="txtSizeCode" runat="server" CssClass="input" Width="60px"></asp:TextBox>
                </td>
                <td></td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存">
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
                    <td style="width:110px;" class="h">SKU(条码)</td>
                    <td style="width:80px;" class="h">货号</td>
                    <td style="width:90px;" class="h">颜色</td>
                    <td style="width:60px;" class="h">尺码</td>
                    <td style="width:90px;" class="h">数量</td>
                    <td class="h">商品名称</td>
                </tr>
                <asp:Repeater ID="rptPO" runat="server" onitemdatabound="rptPO_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:110px;"><%# Eval("BarCode") %></td>
                            <td><%# Eval("ItemCode") %></td>
                            <td><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td><%# Eval("SizeCode")%></td>
                            <td  align="right">
                                <asp:HiddenField ID="hidsku" runat="server" />
                                <asp:TextBox ID="txtQty" runat="server" Width="75" MaxLength="6" CssClass="input textright"></asp:TextBox>
                            </td>
                            <td><%# Eval("ItemName") %></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="MagicToolBar1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
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

