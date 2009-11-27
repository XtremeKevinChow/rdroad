<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PipelineInvQuery.aspx.cs" Inherits="Purchase_PipelineInvQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>在途采购量查询</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
</head>
<body>
<form id="form1" runat="server">
    <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="在途采购量查询" />
    <table class="queryArea" cellpadding="0" cellspacing="0" style="width:460px;">
        <tr>
            <td class="label">产品名称&nbsp;</td>
            <td colspan="5"><asp:Label ID="lblItemName" runat="server" Text=""></asp:Label></td>
        </tr>
        <tr>
            <td class="label" style="width:60px;">货号&nbsp;</td>
            <td style="width:90px;"><asp:Label ID="lblItemCode" runat="server" Text=""></asp:Label></td>
            <td class="label" style="width:45px;">颜色&nbsp;</td>
            <td style="width:120px;"><asp:Label ID="lblColor" runat="server" Text=""></asp:Label></td>
            <td class="label" style="width:70px;">尺码&nbsp;</td>
            <td style="width:50px;"><asp:Label ID="lblSize" runat="server" Text=""></asp:Label></td>
        </tr>
        <tr>
            <td class="label">库存量&nbsp;</td>
            <td><asp:Label ID="lblStockQty" runat="server" Text=""></asp:Label></td>
            <td class="label">冻结量&nbsp;</td>
            <td><asp:Label ID="lblFrozenQty" runat="server" Text=""></asp:Label></td>
            <td class="label">虚拟库存量&nbsp;</td>
            <td><asp:Label ID="lblOVSQty" runat="server" Text=""></asp:Label></td>
        </tr>
    </table>
    <div>在途采购量:&nbsp;<asp:Label ID="lblPipelineStock" runat="server" Text=""></asp:Label></div>
    <table class="datalist2" cellpadding="0" cellspacing="0" style="width:460px;">
        <tr>
            <td class="h">采购订单</td>
            <td class="h">行号</td>
            <td class="h">在途数量</td>
            <td class="h">预计到货日期</td>
        </tr>
        <asp:Repeater ID="repeater" runat="server">
            <ItemTemplate>
                <tr>
                    <td><%# Eval("OrderNumber") %></td>
                    <td><%# Eval("LineNumber") %></td>
                    <td><%# this.PipelineInvQty(Eval("PurchaseQty"), Eval("ReceiveQty"))%></td>
                    <td><%# RenderUtil.FormatDate(Eval("PlanDate"))%></td>
                </tr>
            </ItemTemplate>
        </asp:Repeater>
    </table>
    <div runat="server" id="divMsg"></div>
</form>
</body>
</html>