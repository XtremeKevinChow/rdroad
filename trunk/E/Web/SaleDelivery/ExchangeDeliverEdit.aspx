<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ExchangeDeliverEdit.aspx.cs" Inherits="ExchangeDeliverEdit" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>换货发货单编辑</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="UserManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="换货发货单编辑" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:10%;">
                    发货单编号&nbsp;
                </td>
                <td style="width:15%;">
                    <asp:TextBox ID="txtSaleDeliver" Enabled="false" runat="server" CssClass="input" Width="95%"></asp:TextBox>
                </td>
                <td class="label" style="width:10%;">
                    换货收货单编号&nbsp;
                </td>
                <td style="width:15%;">
                   <asp:TextBox ID="txtSaleOrder" runat="server" Enabled="false" CssClass="input" Width="95%"></asp:TextBox>
                </td>
                <td class="label" style="width:10%;">
                    交接单编号&nbsp;
                </td>
                <td style="width:15%;">
                    <asp:TextBox ID="txtInterChange" runat="server" Enabled="false" CssClass="input" Width="95%"></asp:TextBox>
                </td>
                <td class="label" style="width:10%;">
                    状态&nbsp;
                </td>
                <td class="label" style="width:15%;">
                    <asp:TextBox ID="txtStatus" Enabled="false" runat="server" CssClass="input" Width="95%"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:10%;">
                    会员名称&nbsp;
                </td>
                <td style="width:13%;">
                    <asp:TextBox ID="txtCusName" runat="server" Enabled="false" CssClass="input" Width="95%"></asp:TextBox>
                </td>
                <td class="label" style="width:10%;">
                    送货地址&nbsp;
                </td>
                <td colspan="5">
                    <asp:TextBox ID="txtAddress" runat="server" Enabled="false" CssClass="input" Width="99%"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:10%;">
                    备注&nbsp;
                </td>
                <td colspan="7">
                    <asp:TextBox ID="txtMemo" runat="server" Enabled="false" CssClass="input" Width="99%"></asp:TextBox>
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr style="width:100%">
                <td style="width:100%"><br />发货明细列表
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>
                        行号
                    </th>
                    <th style="width:10%">
                        订单行号
                    </th>
                    <th style="width:10%">
                        物料号码
                    </th>
                    <th>
                        物料名称
                    </th>
                    <th>
                        颜色
                    </th>
                    <th>
                        尺码
                    </th>
                    <th>
                        数量
                    </th>
                    <th style="width:12%">
                        仓库区域
                    </th>
                    <th style="width:12%">
                        货架
                    </th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptSDLine" runat="server">
                    <ItemTemplate>
                        <tr>
                            <td id="tdLineNumber" runat="server">
                                <%# RenderUtil.FormatString(Eval("SDLineNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("SOLineNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemCode"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ItemName"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ColorText"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("SizeText"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("ShipQty"))%>
                            </td>
                            <td>
                                <asp:DropDownList runat="server" AutoPostBack ="true" OnSelectedIndexChanged="ddlArea_SelectedIndexChanged" ID="ddlArea" Width="100%" CssClass="input" DataTextField="Name" DataValueField="AreaCode"></asp:DropDownList>
                            </td>
                            <td>
                                <asp:DropDownList runat="server" AutoPostBack="true"  ID="ddlSection" Width="100%" CssClass="input" DataTextField="Text" DataValueField="SectionCode"></asp:DropDownList>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td style="width:40%"></td>
                <td align="center">
                     <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand"  runat="server" Layout="Span" ID="toolbarbottom" ToolbarStyle="valign:center;">     
                         <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton"  ItemType="ImageButton"
                            ImageUrl="../images/b_save.gif" Text="保存" >
                            </mwu:MagicItem>
                             <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_stop.gif" >
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
            </tr>      
        </table>
    </div>
    </form>
</body>
</html>
