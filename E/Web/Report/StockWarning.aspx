<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockWarning.aspx.cs" Inherits="Report_StockWarning" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>库存预警</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
        a.dp-choose-date{ margin-top: 1px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="库存预警" />
        <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
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
                    <td  class="h nw" style="width:120px;">SKU</td>
                    <td  class="h nw" style="width:90px;">货号</td>
                    <td  class="h nw">名称</td>
                    <td  class="h nw" style="width:100px;">颜色</td>
                    <td  class="h nw" style="width:50px;">尺码</td>
                    <td  class="h nw" style="width:70px;">Max.</td>
                    <td  class="h nw" style="width:70px;">库存量</td>
                    <td  class="h nw" style="width:70px;">Safe.</td>
                    <td  class="h nw" style="width:70px;">Min.</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><%# Eval("SKU")%></td>
                            <td class="nw"><%# Eval("ItemCode")%></td>
                            <td class="nw"><%# Eval("ItemName")%></td>
                            <td class="nw"><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td class="nw"><%# Eval("SizeCode")%></td>
                            <td class='nw' style='background-color:<%# this.ClassName(Eval("WarnType"), 1) %>;padding-right:4px;' align="right" title='<%# this.GetTitle(Eval("WarnType"), 1) %>'><%# RenderUtil.FormatNumber(Eval("MaxQty"), "#,##", "&nbsp;")%></td>
                            <td class='nw' style='background-color:<%# this.ClassName(Eval("WarnType"), 2) %>;padding-right:4px;' align="right" title='<%# this.GetTitle(Eval("WarnType"), 2) %>'><%# RenderUtil.FormatNumber(Eval("StoQty"), "#,##")%></td>
                            <td class='nw' style='background-color:<%# this.ClassName(Eval("WarnType"), 3) %>;padding-right:4px;' align="right" title='<%# this.GetTitle(Eval("WarnType"), 3) %>'><%# RenderUtil.FormatNumber(Eval("SafeQty"), "#,##", "&nbsp;")%></td>
                            <td class='nw' style='background-color:<%# this.ClassName(Eval("WarnType"), 4) %>;padding-right:4px;' align="right" title='<%# this.GetTitle(Eval("WarnType"), 4) %>'><%# RenderUtil.FormatNumber(Eval("MinQty"), "#,##", "&nbsp;")%></td>
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