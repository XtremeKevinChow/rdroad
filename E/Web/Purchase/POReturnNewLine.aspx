<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POReturnNewLine.aspx.cs" Inherits="Purchase_POReturnNewLine" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>添加退货明细</title>
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
    <script type="text/javascript">
        $(document).ready(function() {
        $("#txtDateFrom").datePicker({ startDate: '2008-01-01' });
        $("#txtDateTo").datePicker({ startDate: '2008-01-01' });
	      bindTableBehavior('#data_list_table', '#chkSelectAll');	
      });//$(document).ready(function()
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="添加退货明细" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:33px;">
                    日期&nbsp;
                </td>
                <td style="width:245px;">
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td class="label" style="width:35px;">
                    PO&nbsp;
                </td>
                <td style="width:110px;">
                    <asp:TextBox ID="txtPO" runat="server" CssClass="input" Width="105px"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">
                    SKU(条码)&nbsp;
                </td>
                <td style="width:120px;">
                    <asp:TextBox ID="txtSku" runat="server" CssClass="input" Width="115px"></asp:TextBox>
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
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif"
                                Text="确定">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" OnlyClient="true"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged" 
                        ShowPageSizeBox="true" PageSize="17" MaxPageCount="20" ShowCustomInfoSection="Left" Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:100px;">PO</th>
                    <th style="width:40px;">PO行</th>
                    <th style="width:105px;">SKU(条码)</th>
                    <th style="width:80px;">货号</th>
                    <th>商品名称</th>
                    <th style="width:85px;">颜色</th>
                    <th style="width:40px;">尺码</th>
                    <th style="width:55px;">收货数量</th>
                    <th style="width:40px;">库位</th>
                    <th style="width:75px;">货架</th>
                    <th style="width:45px;">退货数</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="repeatControl" runat="server" 
                    onitemdatabound="repeatControl_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:20px;">
                                <input id="checkbox" po='<%# Eval("PONumber") %>' poline='<%# Eval("POLine") %>' sku='<%# Eval("SKUID") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width:100px;">
                                <%# RenderUtil.FormatString(Eval("PONumber"))%>
                            </td>
                            <td style="width:40px;">
                                <%# RenderUtil.FormatString(Eval("POLine"))%>
                            </td>
                            <td style="width:105px;">
                                <%# RenderUtil.FormatString(Eval("BarCode"))%>
                            </td>
                            <td style="width:80px;">
                                <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemName"))%>
                            </td>
                            <td style="width:85px;">
                                <%# RenderUtil.FormatString(Eval("ColorCode"))%>&nbsp;
                                <%# RenderUtil.FormatString(Eval("ColorText"))%>
                            </td>
                            <td style="width:40px;">
                                <%# RenderUtil.FormatString(Eval("SizeCode"))%>
                            </td>
                            <td style="width:55px;">
                                <%# RenderUtil.FormatNumber(Eval("Qty"), "#0.##")%>
                            </td>
                            <td style="width:40px;">
                                <asp:DropDownList ID="drpArea" runat="server" CssClass="select" Width="45px"></asp:DropDownList>
                            </td>
                            <td style="width:75px;">
                                <asp:TextBox ID="txtSec" CssClass="input" Width="70px" runat="server"></asp:TextBox>
                            </td>
                            <td style="width:45px;">
                                <asp:TextBox ID="txtQty" CssClass="input" Width="40px" runat="server"></asp:TextBox>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server"  OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_confirm.gif"
                                Text="确定">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_back.gif" OnlyClient="true"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerSub" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="17" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>