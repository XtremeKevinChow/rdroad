<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockWarningMgt.aspx.cs" Inherits="Inventory_StockWarningMgt" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>预警设置</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
        .c { text-align:center;}
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
<script type="text/javascript">
  $(document).ready(function(){ 
  }); //$(document).ready(function()
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="预警设置" />
        <input type="text" runat="server" id="hidReturnUrl" value="" style="display:none;" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:33px;">
                    货号&nbsp;
                </td>
                <td style="width:90px;">
                    <asp:TextBox ID="txtItemCode" runat="server" Width="80px" MaxLength="18" CssClass="input"></asp:TextBox>
                </td>
                <td class="label" style="width:33px;">
                    名称&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtItemName" runat="server" Width="100px" MaxLength="30" CssClass="input"></asp:TextBox>
                </td>
                <td class="label" style="width:33px;">
                    颜色&nbsp;
                </td>
                <td style="width:70px;">
                    <asp:TextBox ID="txtColor" runat="server" Width="60px" MaxLength="8" CssClass="input"></asp:TextBox>
                </td>
                <td class="label" style="width:33px;">
                    尺码&nbsp;
                </td>
                <td style="width:60px;">
                    <asp:TextBox ID="txtSize" runat="server" Width="50px" MaxLength="8" CssClass="input"></asp:TextBox>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="cmdSave1">
                <Items>
                    <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                        ImageUrl="../images/b_save.gif"
                        Text="保存">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
                <tr>
                    <td  class="h nw" style="width:90px;">货号</td>
                    <td  class="h nw">名称</td>
                    <td  class="h nw" style="width:90px;">颜色</td>
                    <td  class="h nw" style="width:60px;">尺码</td>
                    <td  class="h nw" style="width:55px;">是否预警</td>
                    <td  class="h nw" style="width:60px;">Max.</td>
                    <td  class="h nw" style="width:60px;">Safe.</td>
                    <td  class="h nw" style="width:60px;">Min.</td>
                    <td  class="h nw" style="width:60px;">当前库存</td>
                </tr>
                <asp:Repeater ID="repeater" runat="server" 
                    onitemdatabound="repeater_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td class="nw"><%# Eval("ItemCode")%></td>
                            <td class="nw"><%# Eval("ItemName")%></td>
                            <td class="nw"><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                            <td class="nw"><%# Eval("SizeCode")%></td>
                            <td class="nw c">
                                <asp:CheckBox ID="chkMethod" runat="server" /></td>
                            <td class="nw" align="right">
                                <asp:HiddenField ID="hidSkuId" runat="server" />
                                <asp:TextBox ID="txtMax" CssClass="input" Width="50px" MaxLength="5" runat="server"></asp:TextBox></td>
                            <td class="nw" align="right">
                                <asp:TextBox ID="txtSafe" CssClass="input" Width="50px" MaxLength="5" runat="server"></asp:TextBox></td>
                            <td class="nw" align="right">
                                <asp:TextBox ID="txtMin" CssClass="input" Width="50px" MaxLength="5" runat="server"></asp:TextBox></td>
                            <td class="nw" align="right"><%# RenderUtil.FormatNumber(Eval("StoQty"), "#,##")%>&nbsp;</td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
            <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                Layout="Div" ID="cmdSave2">
                <Items>
                    <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                        ImageUrl="../images/b_save.gif"
                        Text="保存">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>