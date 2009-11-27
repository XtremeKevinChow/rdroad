<%@ Page Language="C#" AutoEventWireup="true" CodeFile="WHTransQuery.aspx.cs" Inherits="Inventory_WHTransQuery" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>出入库查询</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        a.dp-choose-date{ margin-top: 1px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
	      $("#txtDateFrom").datePicker({startDate: '2008-01-01'});
	      $("#txtDateTo").datePicker({startDate: '2008-01-01'});
        });
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="WHTransLineManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="出入库查询" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label">
                    库位：
                </td>
                <td>
                    <asp:DropDownList ID="drpArea" runat="server" Width="100px">
                    </asp:DropDownList>
                </td>
                <td class="label">
                    货架：
                </td>
                <td>
                    <asp:TextBox ID="txtSection" runat="server" CssClass="input" Width="70px" MaxLength="8"></asp:TextBox>
                </td>
                <td class="label">
                    日期：
                </td>
                <td colspan="3">
                    <div style="width:3px; height:10px; overflow:hidden;" class="left"></div>
                    <asp:TextBox ID="txtDateFrom" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtDateTo" runat="server" Width="70px" MaxLength="10" CssClass="left input"></asp:TextBox>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td class="label" style="width:70px;">
                    货号：
                </td>
                <td>
                    <asp:TextBox ID="txtItemCode" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">
                    商品名称：
                </td>
                <td>
                    <asp:TextBox ID="txtItemName" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label" style="width:70px;">
                    颜色：
                </td>
                <td>
                    <asp:TextBox ID="txtColorCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    尺码：
                </td>
                <td>
                    <asp:TextBox ID="txtSizeCode" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td class="label">
                    交易类型：
                </td>
                <td>
                    <asp:DropDownList ID="drpTransTypeDef" runat="server" Width="100px">
                    </asp:DropDownList>
                </td>
                <td class="label">
                    出入库单：
                </td>
                <td>
                    <asp:TextBox ID="txtRefOrderNumber" runat="server" CssClass="input" Width="100px"></asp:TextBox>
                </td>
                <td class="label">
                    引用单据：
                </td>
                <td>
                    <asp:TextBox ID="txtOriginalOrderNumber" runat="server" CssClass="input" Width="80px"></asp:TextBox>
                </td>
                <td>
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
                <td>
                    <asp:ImageButton runat="server" ID="btnQuery" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click"
                        CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged"
                        ShowPageSizeBox="true" PageSize="22" MaxPageCount="20" ShowCustomInfoSection="Left"
                        Width="">
                    </mwu:MagicPager>
                </td>
            </tr>
        </table>
        <table class="datalist2" id="data_list_table" cellpadding="0" cellspacing="0">
            <tr>
                <td class="h">货号</td>
                <td class="h">商品名称</td>
                <td class="h">颜色</td>
                <td class="h">尺码</td>
                <td class="h">库位</td>
                <td class="h">货架</td>
                <td class="h">出入库单据号</td>
                <td class="h">引用单据号</td>
                <td class="h">交易时间</td>
                <td class="h">交易类型</td>
                <td class="h">交易数量</td>
            </tr>
            <asp:Repeater ID="rptPO" runat="server" onitemdatabound="rptPO_ItemDataBound">
                <ItemTemplate>
                    <tr>
                        <td>
                        <%# Eval("ItemCode")%>
                        </td>
						 <td>
                         <%# Eval("ItemName")%>
                        </td>
                         <td>
                            <%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%>
                        </td>
                         <td>
                         <%# Eval("SizeCode")%>
                        </td>
                         <td>
                        <%# Eval("WName")%>
                        </td>
                          <td>
                         <%# Eval("SectionCode")%>
                        </td>
                          <td>
                        <%# Eval("RefOrderNumber")%>
                          </td>
                          <td>
                          <%# Eval("OriginalOrderNumber")%>
                          </td>
                          <td>
                            <asp:Label ID="lblTime" runat="server" Text=""></asp:Label>
                          </td>
                           <td>
                         <%# Eval("TransDefText")%>
                          </td>
                           <td  align="right">
                         <%# RenderUtil.FormatNumber(Eval("TransQty"), "#0.##")%>
                          </td>
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
