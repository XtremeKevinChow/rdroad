<%@ Page Language="C#" AutoEventWireup="true" CodeFile="LogisReturnScan.aspx.cs" Inherits="Receive_LogisReturnScan" %>
<%@ Register Assembly="Magic.Web.UI" Namespace="Magic.Web.UI" TagPrefix="mwu" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<%@ Register Src="../Controls/SNInfo.ascx" TagName="sn" TagPrefix="sn" %>
<%@ Import Namespace="Magic.Framework.Utils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>退货明细录入</title>
    <link href="../CSS/queryPage.css" rel="Stylesheet" type="text/css" />
    <style type="text/css">
        .left { float:left;}
        .hidden { display:none;}
        .rtqty { font-weight:600; }
        #divCommand { width:60px; margin-left:15px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script src="../script/magic.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#txtSku").bind("keypress", skuInput);    
    setScan();    
});
function setScan(){
    $("#txtSku").val("");
    document.getElementById("txtSku").select();
}
function reScan(){
    $("#txtSku").val("");
    $("#txtMsg").empty();
    setScan();
}
function disableScan(){
    $("#txtSku").attr("disabled", true).addClass("readonly").attr("st", "0");
}
function enableScan(){
    $("#txtSku").removeAttr("disabled").removeClass("readonly").attr("st", "1");
}
function errorMsg(msg){
    $("#txtMsg").text(msg);
}
//SKU(条码)输入框的按键事件，如果输入回车符号，则开始匹配退货信息
function skuInput(e){
    if($("#txtSku").attr("st")=="0") return;
    if(e.which!=13) {
        $("#txtMsg").empty();
        return;
    }
    if($.trim($(this).val()).length<=0) return;
    var sku = $(this).val(), items = [], dlvQty, rtnQty;
    $("#tblSNList").find(".rtl").each(function(i, elm){  //循环所有的收货单明细，如果SKU匹配则取出来
        if($(elm).children(":eq(1)").text()==sku){
            dlvQty = parseInt($(elm).children(":eq(8)").text());
            dlvQty = (isNaN(dlvQty) || dlvQty<0) ? 0 : dlvQty;
            rtnQty = parseInt($(elm).children(":eq(9)").text());
            rtnQty = (isNaN(rtnQty) || rtnQty<0) ? 0 : rtnQty;
            items.push({ id: $(this).attr("id"), index: $(elm).children(":eq(0)").text(), dlvQty: dlvQty, rtnQty: rtnQty });
        }
    });
    if(items.length<=0) {  //发货单中没有这个sku
        errorMsg("发货明细中没有SKU: " + sku);
        alert("发货明细中没有SKU: " + sku);
        setScan();
        return;
    }
    var count = items.length; //删除退货数量已经等于发货数量的明细
    var ccc = 0;
    for(var i=0; i<count; ){
        if(items[i].rtnQty>=items[i].dlvQty) {
            items.splice(i, 1);
            count--;
            continue;
        }
        i++;
    }
    if(items.length<=0) {
        errorMsg("SKU " + sku + "的退货数量超过发货数量");
        alert("SKU " + sku + "的退货数量超过发货数量");
        setScan();
        return;
    }
    addReturnQty(items[0].id);
    return;
}
function addReturnQty(id){
    var objID = "#"+id;
    var rtnQty, dlvQty;
    dlvQty = parseInt($(objID).children(":eq(8)").text());
    dlvQty = isNaN(dlvQty) || dlvQty<0 ? 0 : dlvQty;
    rtnQty = parseInt($(objID).children(":eq(9)").text());
    rtnQty = isNaN(rtnQty) || rtnQty<0 ? 0 : rtnQty;
    $(objID).attr("vv_changed", "1").children(":eq(9)").text(rtnQty+1)
        .end().children(":last").children(":first").attr("vv_rtnqty", rtnQty+1).val(rtnQty+1);
    if(rtnQty+1<dlvQty) $(objID).attr("vv_stat", "1").css("color", "black").children().css("background-color", "yellow");
    else if (rtnQty + 1 == dlvQty) $(objID).attr("vv_stat", "2").css("color", "white").children().css("background-color", "green");
    else $(objID).attr("vv_stat", "3").css("color", "white").children().css("background-color", "red");
    reScan();
}
function onreturn(){
    var changed = false, redirect=true;
    $("#tblSNList").find(".rtl").each(function(i, elm){
        if($(elm).attr("vv_changed")=="1") changed=true;
    });
    if(changed){
        redirect = confirm("您添加了退货明细还没有保存，确信要离开？");
    }
    if(redirect) window.location.href=$("#txtReturnUrl").val();
}
function onsave() {
    var err = [], changedCount = 0, isOK = true;
    var rtnQty, dlvQty;
    //物流公司退货，目前只支持全退
    $("#tblSNList").find(".rtl").each(function(i, elm) {
        if ($(elm).attr("vv_changed") == "1") changedCount++;
        if ($(elm).attr("vv_stat") != "2") isOK = false;
    });
    if (changedCount <= 0) window.location.href = $("#txtReturnUrl").val();
    if(!isOK){
        errorMsg("物流公司不能部分退货，退货明细必须与发货明细相同");
        return false;
    }
    return confirm("确认退货录入已经完成？");
}
</script>
</head>
<body>
    <form id="form1" runat="server">
        <input id="txtOrderNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtSNNumber" type="text" runat="server" class="hidden" value="" />
        <input id="txtReturnUrl" type="text" runat="server" class="hidden" value="" />
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="退货明细录入" ExtInfo="物流公司退货" />
        <sn:sn ID="snView" runat="server" />
        <table id="div_sdo_info_input" class="queryArea" cellpadding="0" cellspacing="0" style="margin-top:10px;">
            <tr>
                <td class="label" style="width:70px;">SKU(条码)&nbsp;</td>
                <td style="width:120px;">
                    <input type="text" id="txtSku" class="input" style="width:115px;" maxlength="13" />
                </td>
                <td id="txtMsg" style="color:red; text-indent:10px;"></td>
            </tr>
        </table>
        <table class="gridbar" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <mwu:MagicToolBar CssClass="toolbar left" runat="server"
                        Layout="Div" ID="cmdEdit1" ToolbarStyle="" 
                         OnItemCommand ="cmdEdit1_ItemCommand">
                        <Items>
                            <mwu:MagicItem CommandName="Save" ItemCssClass="toolbutton" ItemType="ImageButton"
                                ImageUrl="../images/b_save.gif" Text="保存" CancelServerEvent="true" OnClientClick="onsave()">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                    <mwu:MagicToolBar CssClass="toolbar left" runat="server"
                        Layout="Div" ID="cmdReturn1" ToolbarStyle="">
                        <Items>
                           <mwu:MagicItem CommandName="Return" ItemCssClass="toolbutton" ItemType="Navigate" OnlyClient="true"
                                ImageUrl="../images/b_back.gif" NavigateUrl="javascript:onreturn();"
                                Text="返回">
                            </mwu:MagicItem>
                        </Items>
                    </mwu:MagicToolBar>
                </td>
                <td align="right">
                </td>
            </tr>
        </table>
        <table id="tblSNList" class="dataArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="h" style="width:40px;">行号</td>
                <td class="h" style="width:110px;">SKU</td>
                <td class="h" style="width:85px;">货号</td>
                <td class="h">商品名称</td>
                <td class="h" style="width:110px;">颜色</td>
                <td class="h" style="width:50px;">尺码</td>
                <td class="h" style="width:60px;">销售方式</td>
                <td class="h" style="width:50px;">单价</td>
                <td class="h" style="width:60px;">发货数量</td>
                <td class="h" style="width:60px;">退货数量</td>
            </tr>
            <asp:Repeater ID="repeatControl" runat="server">
                <ItemTemplate>
                    <!--
                        vv_sndid: 发货明细ID;
                        vv_changed: 0没有修改，1有修改
                    -->
                    <tr class="rtl" id='rtl_<%# Eval("INDEX")%>' vv_sndid='<%# Eval("SNDID")%>' vv_changed='0'>
                        <td><%# Eval("INDEX")%></td>
                        <td style="width:110px;"><%# Eval("BarCode")%></td>
                        <td><%# Eval("ItemCode")%></td>
                        <td><%# Eval("ItemName")%></td>
                        <td><%# Eval("ColorCode")%>&nbsp;<%# Eval("ColorText")%></td>
                        <td><%# Eval("SizeCode")%></td>
                        <td><%# Eval("SaleType")%></td>
                        <td style="text-align: right;"><%# RenderUtil.FormatNumber(Eval("Price"), "##.##", "0")%></td>
                        <td style="text-align: right;"><%# RenderUtil.FormatNumber(Eval("ShippingQty"), "##.##", "0")%></td>
                        <td class="rtqty" style="text-align: right;"><%# RenderUtil.FormatNumber(Eval("ReturnQty"), "##.##", "0")%></td>
                        <td style="display:none;">
                            <input id="hidQty" type="text" style="display:none;" runat="server" value='<%# Eval("ReturnQty") %>' vv_sndid='<%# Eval("SNDID")%>' vv_rtnqty='<%# Eval("ReturnQty") %>' />
                        </td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
        </table>
    </form>
</body>
</html>