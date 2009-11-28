<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POSearch.aspx.cs" Inherits="Purchase_POSearch" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购查询</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        a.dp-choose-date{ margin-top: 1px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript">  
	  $(document).ready(function(){ 
	      $("#txtStartDate").datePicker({startDate: '2008-01-01'}); 
	      $("#txtEndDate").datePicker({startDate: '2008-01-01'});    
      });	   
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div style="width:98%;">
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购查询" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label">
                    采购订单：
                </td>
                <td>
                    <asp:TextBox ID="txtOrderNum" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label">
                    日期：
                </td>
                <td colspan="3">
                  <div style="width:3px;height:10px; overflow:hidden;float:left;"></div>
                  <input type="text" class="input" id="txtStartDate" 
                        style="float:left;width:70px;" maxlength="10" 
                        runat="server" />
                  <span style="float:left;">&nbsp;到&nbsp;</span>                    
                  <input type="text" 
                        class="input" id="txtEndDate" 
                        style="float:left;width:70px;" maxlength="10" 
                        runat="server" />
                </td>
                <td class="label">
                    状态：
                </td>
                <td>
                    <asp:CheckBoxList ID="cklStatus" RepeatDirection="Horizontal" RepeatLayout="Flow"
                        runat="server">
                        <asp:ListItem Value="1">新建</asp:ListItem>
                        <asp:ListItem Value="2" Selected="True">发布</asp:ListItem>
                        <asp:ListItem Value="3">完成</asp:ListItem>
                    </asp:CheckBoxList>
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click"
                        CssClass="cmdQuery" />
                </td>
            </tr>
            <tr height="20px">
                <td class="label">
                    货号：
                </td>
                <td>
                    <asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label">
                    商品名称：
                </td>
                <td>
                    <asp:TextBox ID="txtItemName" runat="server" CssClass="input" Width="66px"></asp:TextBox>
                </td>
                <td class="label">
                    颜色：
                </td>
                <td>
                    <asp:TextBox ID="txtColorCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label">
                    尺码：
                </td>
                <td>
                    <asp:TextBox ID="txtSizeCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" OnItemCommand="MagicItemCommand"
                        Layout="Div" ID="cmdDownload1">
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
                        ShowPageSizeBox="true" PageSize="20" MaxPageCount="20" ShowCustomInfoSection="Left"
                        Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr height="20">
                    <th>采购订单</th>
                    <th>行号</th>
                    <th>货号</th>
                    <th>颜色</th>
                    <th>尺码</th>
                    <th>商品名称</th>
                    <th>采购数量</th>
                    <th>单价</th>
                    <th>金额</th>
                    <th>收货时间</th>
                    <th>收货数量</th>
                    <th>收货金额</th>
                    <th>差异数量</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptPO" runat="server">
                    <ItemTemplate>
                        <tr height="20">
                            <td align="center"><%# Eval("OrderNumber")%></td>
                            <td align="center"><%# Eval("LineNumber")%></td>
                            <td><%# Eval("ItemCode")%></td>
                            <td><%# Eval("ColorCode")%></td>
                            <td><%# Eval("SizeCode")%></td>
                            <td><%# Eval("ItemName")%></td>
							<td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("PurchaseQty"), "#0.##") %></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("Price"), "#0.##")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("TaxInclusiveAmt"), "#0.##")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatDate(Eval("ActualDate"))%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("IQCQty"), "#0.##")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("IQCAMT"), "#0.##")%></td>
                            <td style="text-align:right;padding-right:4px;"><%# RenderUtil.FormatNumber(Eval("DiffQty"), "#0.##", "0")%></td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
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
                        PageSize="20" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </div>
    </form>
</body>
</html>
