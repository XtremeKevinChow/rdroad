<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StockAdjustmentManage.aspx.cs" Inherits="Inventory_StockAdjustmentManage" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>库存调整</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left; }
        a.dp-choose-date{ margin-top: 1px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
<script type="text/javascript">  
$(document).ready(function(){ 
    bindTableBehavior('#data_list_table', '#chkSelectAll');
    $('#txtBeginDate').datePicker({startDate:'2008-01-01'}) ;
    $('#txtEndDate').datePicker({startDate:'2008-01-01'}) ;
});	   

function checkSelect()
{
    if(!hasSelect("#data_list_table")){
        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
        return false;
    }
    return true;
}	  
function Edit(id){
}
function onnew() {
    window.location.href = "StockAdjustmentEdit.aspx?mode=new" + "&return=" + escape($("#hidReturnUrl").val());
}
function ondetail(ordNum) {
    window.location.href = "StockAdjustmentLine.aspx?ordNum="+ordNum+"&return="+escape($("#hidReturnUrl").val());
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="StockAdjustmentManage.aspx" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="库存调整" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label" style="width:60px;">
                    调整单号&nbsp;
                </td>
                <td style="width:108px;">
                    <asp:TextBox ID="txtAdjustmentNumber" runat="server" CssClass="input" Width="100px" MaxLength="16"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    日期&nbsp;
                </td>
                <td style="width:250px;">
                    <div class="left" style="width:3px; height:5px;"></div>
                    <asp:TextBox ID="txtBeginDate" runat="server" onKeyPress="return false;" CssClass="input left" Width="75px" MaxLength="10"></asp:TextBox>
                    <span class="left">&nbsp;到&nbsp;</span>
                    <asp:TextBox ID="txtEndDate" runat="server" onKeyPress="return false;"  CssClass="input left" Width="75px" MaxLength="10"></asp:TextBox>
                </td>
                <td class="label" style="width:40px;">
                    仓库&nbsp;
                </td>
                <td style="width:108px;">
                   <asp:DropDownList runat="server" ID="ddlLocation" CssClass="select" Width="100px"></asp:DropDownList>
                </td>
                <td class="label" style="width:40px;">
                    状态&nbsp;
                </td>
                <td class="label" style="text-align:left;">
                    <asp:CheckBox ID="chkNew" Text="" runat="server" />
                    <asp:CheckBox ID="chkRelease" Text="" runat="server" />
                    <asp:CheckBox ID="chkClose" Text="" runat="server" />
                </td>
                <td>
                     <asp:ImageButton runat="server" ID="ImageButton1" ImageUrl="../Images/search.gif" OnClick="btnQuery_Click" CssClass="cmdQuery" />
                </td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarTop" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton" ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onnew()"
                                Text="新建">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif"
                                Text="删除">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                    <mwu:MagicPager ID="magicPagerMain" runat="server" OnPageChanged="MagicPager_PageChanged"
                        PageSize="20" ShowCustomInfoSection="Never" MaxPageCount="20">
                    </mwu:MagicPager>

                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;"><input id="chkSelectAll" type="checkbox" title="全选" /></th>
                    <th style="width:70px;">编辑</th>
                    <th style="width:120px;">调整单号</th>
                    <th style="width:50px;">状态</th>
                    <th style="width:60px;">签核状态</th>
                    <th>仓库</th>
                    <th style="width:100px;">创建人</th>
                    <th style="width:120px;">创建时间</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptSDHead" runat="server" 
                    onitemdatabound="rptSDHead_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td class="" style="width: 20px;">
                                <input id="checkbox" value='<%# Eval("OrderNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width: 70px;">
                                <a href="javascript:onedit('<%# Eval("OrderNumber") %>');">编辑</a>
                                <a href="javascript:ondetail('<%# Eval("OrderNumber") %>');">明细</a>
                            </td>
                            <td>
                                <%#  RenderUtil.FormatString(Eval("OrderNumber"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("StatusText"))%>
                            </td>
                            <td><asp:Label ID="lblApprStatus" runat="server" Text=""></asp:Label></td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("Location"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatString(Eval("UserName"))%>
                            </td>
                            <td>
                                <%# RenderUtil.FormatDatetime(Eval("CreateDate"))%>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="1" cellspacing="1">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="toolbarBottom">
                        <Items>
                            <mwu:MagicItem CommandName="New" ItemCssClass="toolbutton" ItemType="ImageButton" ImageUrl="../images/b_addL.gif" OnlyClient="true" OnClientClick="onnew()"
                                Text="新建">
                            </mwu:MagicItem>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_delete.gif"
                                Text="删除">
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
