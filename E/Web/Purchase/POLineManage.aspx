<%@ Page Language="C#" AutoEventWireup="true" CodeFile="POLineManage.aspx.cs" Inherits="Purchase_POLineManage" %>

<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>采购明细管理</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <link href="../CSS/popups.css" rel="stylesheet" type="text/css" />
    <link href="../CSS/jquery.datePicker.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .hidden
        {
            display: none;
        }
        .left { float:left; }
        .quickAdd { margin-left:20px;}
        a.dp-choose-date{ margin-top: 1px; }
        a.toolbutton {color:#002f76;text-decoration:none;height:18px;line-height:18px;padding:0 2px 0 2px;white-space:nowrap; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
    <script src="../script/selector.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/date.js"></script>
    <script type="text/javascript" src="../script/jquery.datepicker.js"></script>
    <script src="../script/jquery.cookie.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="../script/jquery.contextmenu.r2.js"></script>
    <script type="text/javascript" src="../script/QueryPage.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    var focusItem = $("#txtItemCode");
    if(focusItem.length>0) {
        try{
            focusItem[0].select();
            focusItem[0].focus();
        }catch(e) {}
    }
    bindTableBehavior('#data_list_table', '#chkSelectAll');
    if($("#hidStatus").val()=="New") {
        $(".dateinput").datePicker({startDate: '2008-01-01'});
        $("#txtDemandDate").datePicker({startDate: '2008-01-01'});
    }

    $("#cmdSelectItem1").bind("click", onadditem);
    $("#cmdSelectItem2").bind("click", onadditem);
});
function onadditem(){
    var q = new query("Item");
    q.fnPopup({
        top:10, left:20, width:560, height:400, mode: "m", title: "选择要采购的商品",
        data: { selected: [] },
        on_select: function(r) { 
            if(r.length>0){
                var skuids = "";
                for(var i = 0; i<r.length; i++){
                    if(i>0) skuids = skuids+";";
                    skuids = skuids + r[i].skuId;
                }
                $("#txtSkuId").val(skuids);
                $("#cmdProduct").click();
            }//if(r.length>0){
            else{
                $("#txtSkuId").val("");
                return;
            }
        }//on_select
    });//q.fnPopup
}
function checkSelect()
{
    if(!hasSelect("#data_list_table"))
    {
        ShowMsg("你没有选择任何数据，请先选择要操作的数据！","警告");
        return false;
    }
    return true;
}
function ondelete(){
    if(!checkSelect()) return false;
    return confirm("确认要删除选择的明细？");
}
function oncancel(){
    if(!checkSelect()) return false;
    return confirm("确认要取消选择的明细？");
}
function onrelease(){
    return confirm("确认发布这张采购订单？");
}
function onclose(){
    return confirm("确认关闭这张采购订单？关闭之后该订单将无法收货");
}
function onquickadd(){
    var objAlert = $("#txtAlertMsg");
    if($.trim($("#txtItemCode").val()).length<=0){
        objAlert.text("请填写货号"); return false;
    }
    if($.trim($("#txtColorCode").val()).length<=0){
        objAlert.text("请填写颜色"); return false;
    }
    if($.trim($("#txtSizeCode").val()).length<=0){
        objAlert.text("请填写尺码"); return false;
    }
    if($.trim($("#txtPurchaseQty").val()).length<=0){
        objAlert.text("请填写采购数量"); return false;
    }
    if($.trim($("#txtPrice").val()).length<=0){
        objAlert.text("请填写采购单价"); return false;
    }
    if($.trim($("#txtDemandDate").val()).length<=0){
        objAlert.text("请填写需求日期"); return false;
    }
    if($.trim($("#drpTax").val()).length<=0){
        objAlert.text("请选择税率"); return false;
    }
    return true;
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input type="hidden" id="hidReturnUrl" runat="server" value="POManager.aspx" />
        <input type="hidden" id="hidStatus" runat="server" value="" class="hidden" />
        <input id="txtSkuId" type="text" runat="server" class="hidden" value="" />
        <asp:Button ID="cmdProduct" runat="server" Text="Button" CssClass="hidden" OnClick="cmdProduct_Click" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="采购订单明细" />
        <table class="queryArea" cellpadding="0" cellspacing="0" style="background-color: #aaa;">
            <tr>
                <td class="label">
                    采购单号：
                </td>
                <td>
                    <asp:Label ID="LabOrderNumber" runat="server"></asp:Label>
                </td>
                <td class="label">
                    供 应 商<span class="style1">：</span>
                </td>
                <td>
                    <asp:Label ID="LabVendorID" runat="server"></asp:Label>
                </td>
                <td class="label">
                    采购组<span class="style1">：</span>
                </td>
                <td>
                    <asp:Label ID="LabPurchGroupCode" runat="server"></asp:Label>
                </td>
                <td colspan="2"></td>
            </tr>
            <tr>
                <td class="label">
                    汇总采购数量：
                </td>
                <td>
                    <asp:Label ID="lblTotalQty" runat="server"></asp:Label>
                </td>
                <td class="label">
                    金额：
                </td>
                <td>
                    <asp:Label ID="LabTaxInclusiveAmt" runat="server"></asp:Label>
                </td>
                <td class="label">
                </td>
                <td>
                </td>
                <td class="label">
                </td>
                <td>
                </td>
            </tr>
        </table>
        
        <div runat="server" id="divQuickAdd1" class="queryArea" style="background-color: #eee;margin-top:6px;border:solid 1px #ccc;width:100%;height:20px;padding-top:2px;">
            <span class="left label">　　货号：</span>
            <input type="text" runat="server" id="txtItemCode" class="input left" style="width:70px;" />
            <span class="left label" style="margin-left:37px;">颜色：</span>
            <input type="text" runat="server" id="txtColorCode" class="input left" style="width:50px;" />
            <span class="left label" style="margin-left:15px;">尺码：</span>
            <input type="text" runat="server" id="txtSizeCode" class="input left" style="width:50px;" />
            <span class="left label" style="margin-left:15px;">数量：</span>
            <input type="text" runat="server" id="txtPurchaseQty" class="input left" style="width:70px;" />
            <span class="left label" style="margin-left:15px;">单价：</span>
            <input type="text" runat="server" id="txtPrice" class="input left" style="width:70px;" />
            <mwu:MagicToolBar CssClass="toolbar quickAdd left" runat="server" Layout="Div" ID="cmdQuickAdd" OnItemCommand="MagicItemCommand"
                ToolbarStyle="">
                <Items>
                    <mwu:MagicItem ItemCssClass="toolbutton" ItemType="ImageButton" CommandName="QuickAdd"
                        CancelServerEvent="true" OnClientClick="onquickadd()" ImageUrl="../images/b_addL.gif" Text="快速添加">
                    </mwu:MagicItem>
                </Items>
            </mwu:MagicToolBar>
        </div>
        <div runat="server" id="divQuickAdd2" class="queryArea" style="padding-top:2px;margin-bottom:4px;background-color: #eee;border-left:solid 1px #ccc;border-right:solid 1px #ccc;border-bottom:solid 1px #ccc;border-top:0;width:100%;height:20px;">
            <span class="left label">需求日期：</span>
            <input type="text" runat="server" id="txtDemandDate" class="input left" style="width:70px;" />
            <span class="left label" style="margin-left:25px;color:Red;" id="txtAlertMsg" runat="server"></span>
        </div>
        
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Div" ID="cmdSelectItem1"
                        ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem OnlyClient="true" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_addL.gif" Text="添加明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdDelete1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_delete.gif" OnClientClick="ondelete()" Text="删除明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdSave1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_save.gif" Text="保存">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdCancel1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Cancel" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_cancel.jpg" Text="取消明细" OnClientClick="oncancel()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdRelease1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_confirm.gif" Text="发布订单" OnClientClick="onrelease()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdClose1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_finish.jpg" Text="订单完成" OnClientClick="onclose()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
        <table class="datalist" id="data_list_table" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width:20px;">
                        <input name="" value="" id="chkSelectAll" type="checkbox" title="全选" />
                    </th>
                    <th style="width:30px;">行号</th>
                    <th style="width:45px;">状态</th>
                    <th style="width:60px;">货号</th>
                    <th>商品名称</th>
                    <th style="width:40px;">颜色</th>
                    <th style="width:40px;">尺码</th>
                    <th style="width:60px;">数量</th>
                    <th style="width:60px;">单价</th>
                    <th style="width:100px;">需求日期</th>
                    <th style="width:70px;">总金额</th>
                </tr>
            </thead>
            <tbody>
                <asp:Repeater ID="rptPL" runat="server" OnItemDataBound="rptPL_ItemDataBound">
                    <ItemTemplate>
                        <tr>
                            <td style="width:20px;">
                                <input id="checkbox" value='<%# Eval("LineNumber") %>' type="checkbox" runat="server" />
                            </td>
                            <td style="width:30px;"><%# Eval("LineNumber")%></td>
                            <td>
                                <asp:Label ID="lblLineStatus" runat="server" Text=""></asp:Label>
                            </td>
                            <td><%# Eval("ItemCode")%></td>
                            <td><%# Eval("ItemName")%></td>
                            <td><%# Eval("ColorCode")%></td>
                            <td><%# Eval("SizeCode")%></td>
                            <td align="center">
                                <input type="text" style="width: 50px" id="txtPurchaseQty" class="input" runat="server"
                                    value='<%# RenderUtil.FormatNumber(Eval("PurchaseQty"),"##.##")%>' />
                            </td>
                            <td align="center">
                                <input type="text" style="width: 50px" id="txtPrice" class="input" runat="server"
                                    value='<%# RenderUtil.FormatNumber(Eval("Price"),"#0.##")%>' />
                            </td>
                            <td align="center">
                                <input type="text" style="width: 70px" id="txtPlanDate" class="dateinput input" runat="server"
                                    value='<%# Magic.Framework.Utils.Cast.DateTime(Eval("PlanDate")).ToString("yyyy-MM-dd") %>' />
                            </td>
                            <td align="right">
                                <%# RenderUtil.FormatNumber(Eval("TaxInclusiveAmt"), "#0.#0", "0") %>
                            </td>
                        </tr>
                    </ItemTemplate>
                </asp:Repeater>
            </tbody>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar" runat="server" Layout="Div" ID="cmdSelectItem2"
                        ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem OnlyClient="true" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_addL.gif" Text="添加明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdDelete2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Delete" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_delete.gif" OnClientClick="ondelete()" Text="删除明细">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdSave2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_save.gif" Text="保存">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdCancel2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Cancel" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_cancel.jpg" Text="取消明细" OnClientClick="oncancel()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdRelease2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Release" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_confirm.gif" Text="发布订单" OnClientClick="onrelease()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdClose2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Close" ItemCssClass="toolbutton" ItemType="ImageButton"
                                CancelServerEvent="true" ImageUrl="../images/b_finish.jpg" Text="订单完成" OnClientClick="onclose()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar" OnItemCommand="MagicItemCommand" runat="server"
                        Layout="Div" ID="cmdReturn2" ToolbarStyle="">
                        <Items>
                            <mwu:MagicItem CommandName="Return" Text="返回" ItemCssClass="toolbutton" ItemType="Navigate"
                                ImageUrl="../images/b_stop.gif">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>
