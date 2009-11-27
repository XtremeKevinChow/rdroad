<%@ Page Language="C#" %>
<%@ Import Namespace="System.Data" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.ERP" %>
<%@ Import Namespace="Magic.ERP.Core" %>
<%@ Import Namespace="Magic.ERP.Orders" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>扫描入库</title>
    <link rel="Stylesheet" type="text/css" href="../CSS/queryPage.css" />
    <style type="text/css">
        .left
        {
            float: left;
        }
        .pad20
        {
            margin-left: 20px;
        }
        .divCommand
        {
            width: 200px;
            margin-left: 10px;
        }
        .divCommand img
        {
            margin-top: 2px;
            float: left;
        }
        .divCommand span
        {
            margin-top: 0px;
            float: left;
            cursor: pointer;
        }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
    <script type="text/javascript">
function state_object() {
    this._innerObject = $("#txtState");
    this.wh_loaded = false;
};
state_object.prototype.string = function(attr, selector, val) {
    if (val == null || val == undefined) {
        if (selector == undefined || selector == null) return this._innerObject.attr(attr);
        else {
            if (attr == null || attr == undefined) return $(selector).text() == undefined ? "" : $(selector).text();
            else return $(selector).attr(attr) == undefined ? "" : $(selector).attr(attr);
        }
    }
    if (selector == null || selector == undefined) { this._innerObject.attr(attr, val); return this; }
    else {
        if (attr == null || attr == undefined) { $(selector).text(val == undefined ? "" : val); return this; }
        else { $(selector).attr(attr, val); return this; }
    }
}
state_object.prototype.integer = function(attr, selector, val) {
    if (val == null) {
        var i = parseInt(this.string(attr, selector, val));
        return isNaN(i) ? 0 : i;
    }
    var j = parseInt(val);
    j = isNaN(j) ? 0 : j;
    return this.string(attr, selector, j);
}
state_object.prototype.line = function(val) { return this.string("line", null, val); }
state_object.prototype.sku = function(val) { return this.string("sku", null, val); }
state_object.prototype.purQty = function(val) { return this.integer("purQty", null, val); }
state_object.prototype.rcvQty = function(val) { return this.integer("rcvQty", null, val); }
state_object.prototype.tempQty = function(val) { return this.integer("tempQty", null, val); }
state_object.prototype.maxQty = function(val) { return this.integer("maxQty", null, val); }
state_object.prototype.scanQty = function(val) { return this.integer("scanQty", null, val); }
state_object.prototype.area = function(val) { return this.string("area", null, val); }
state_object.prototype.section = function(val) { return this.string("section", null, val); }
state_object.prototype.capacity = function(val) { return this.integer("capacity", null, val); }
state_object.prototype.stored = function(val) { return this.integer("stored", null, val); }
state_object.prototype.clear = function() {
    this._innerObject.attr("line", "").attr("sku", "")
        .attr("purQty", "0").attr("rcvQty", "0").attr("tempQty", "0").attr("maxQty", "0").attr("scanQty", "0")
        .attr("area", "").attr("section", "").attr("capacity", "0").attr("stored", "0");
    this.wh_loaded = false;
}
state_object.prototype.notSaved = function() {
    return this.scanQty() > 0;
}
state_object.prototype.scaned = function() {
    return this.scanQty()>0 && this.line()!=undefined && this.line().length > 0;
}
state_object.prototype.loadwh = function(wh, sec, callback) {
    if ($.trim(wh).length <= 0) {
        errorMsg(obj_sdo_msg, "请选择库位，填写货架号");
        return;
    }
    $.ajax({
        async: true,
        dataType: "json",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: { action: "GetWHInfo", area: wh, section: sec },
        error: function(xmlhttp) {
            errorMsg(obj_sdo_msg, "加载库位、货架信息时发生异常，请和管理员联系");
            alert("加载库位、货架信息时发生异常，请和管理员联系");
        },
        success: function(data, status) {
            if (data._error == true) {
                errorMsg(obj_sdo_msg, "加载库位、货架信息时发生异常：" + data._error_msg);
                alert("加载库位、货架信息时发生异常：" + data._error_msg);
                return;
            }
            state.area(data.area).section(data.section).capacity(data.capacity).stored(data.stored);
            state.wh_loaded = true;

            infoMsg(obj_sdo_msg, "库位{" + data.area + "}, 货架{" + data.section + "} : 容量" + data.capacity + ", 已存放商品" + data.stored + "件");
            callback();
        } //success
    }); //$.ajax({
}
state_object.prototype.save = function() {
    if (!this.scaned()) return;
    if (!this.wh_loaded) {
        errorMsg(obj_sdo_msg, "请提供入库库位、货架信息（选择库位，填写货架，在货架输入框中键入回车键）");
        return;
    }
    if (this.capacity() - this.stored() <= 0) {
        errorMsg(obj_sdo_msg, "库位{" + this.area() + "}, 货架{" + this.section() + "}容量已满，请选择其它货架");
        return;
    }
    var qty = parseInt($("#txtAssignQty").val());
    qty = isNaN(qty) ? 0 : qty;
    if (qty <= 0) {
        errorMsg(obj_sdo_msg, "本次入库量为无效数字");
        return;
    }
    if (qty > this.capacity() - this.stored()) {
        errorMsg(obj_sdo_msg, "库位{" + this.area() + "}, 货架{" + this.section() + "}的剩余容量" + (this.capacity() - this.stored()) + "，小于本次入库量" + qty);
        return;
    }
    if (qty > this.scanQty()) {
        errorMsg(obj_sdo_msg, "本次入库量" + qty + "大于待入库量" + this.scanQty());
        return;
    }
    if (!confirm("入库区域: 库位{" + state.area() + "}, 货架{" + state.section() + "}，入库数量: " + qty + "，确认保存入库资料？")) return;
    var sec = state.section();
    sec = (sec == null || sec=="undefined" || $.trim(sec).length <= 0 ? " " : sec);
    $.ajax({
        async: true,
        dataType: "json",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: { action: "SaveRCVLine", ordNum: '<%= WebUtil.Param("ordNum") %>', poline: state.line(), area: state.area(), section: sec, qty: qty },
        error: function(xmlhttp) {
            errorMsg(obj_sdo_msg, "保存入库数据时发生异常，请和管理员联系");
            alert("保存入库数据时发生异常，请和管理员联系");
        },
        success: function(data, status) {
            if (data._error == true) {
                errorMsg(obj_sdo_msg, "保存入库数据时发生异常：" + data._error_msg);
                alert("保存入库数据时发生异常：" + data._error_msg);
                return;
            }
            $("#drpArea").val("");
            $("#txtSection").val("");
            $("#txtAssignQty").val("");
            $("#txtCapacity").val("");
            state.scanQty(state.scanQty() - qty);
            var trLine = $("#tblLines").find("tr[line=" + state.line() + "]");
            var leftQty = state.purQty() - state.rcvQty() - state.tempQty();
            if (leftQty == 0) trLine.children().css("background-color", "green").css("color", "#FFF");
            else if (leftQty < 0) trLine.children().css("background-color", "red").css("color", "#FFF");
            else trLine.children().css("background-color", "yellow");
            trLine.children(":eq(8)").text(state.tempQty() - state.scanQty()).end().children(":eq(9)").text(leftQty + state.scanQty());
            if (state.scanQty() > 0) {
                state.area("").section("").capacity("0").stored("0").wh_loaded = false;
                $("#txtQty").val(state.scanQty());
                $("#div_po_line").children(":eq(0)").children(":eq(0)").children(":eq(13)").text(leftQty + state.scanQty());
            } else {
                state.clear();
                $("#div_po_line").children(":eq(0)").children(":eq(0)")
                    .children(":eq(1)").text("")
                    .end().children(":eq(3)").text("")
                    .end().children(":eq(5)").text("")
                    .end().children(":eq(7)").text("")
                    .end().children(":eq(9)").text("")
                    .end().children(":eq(11)").text("")
                    .end().children(":eq(13)").text("");
                $("#txtQty").val("");
            }

            infoMsg(obj_sdo_msg, "保存成功");
        } //success
    }); //$.ajax({
}

var obj_sdo_msg = null, state;
var interval_sdo = null, scan_time = 300, sdo_status=false;
$(document).ready(function() {
    state = new state_object();
    obj_sdo_msg = $("#txtSdoMsg");
    $("#txtSku").bind("keypress", skuInput);
    $("#txtSection").bind("keypress", whInput);
    setScan();
});
function disableScan() {
    $("#txtSku").attr("disabled", true).addClass("readonly").attr("st", "0");
}
function enableScan() {
    $("#txtSku").removeAttr("disabled").removeClass("readonly").attr("st", "1");
}
function setScan() { $("#txtSku").select(); }
//SKU(条码)输入框的按键事件，如果输入回车符号，则开始匹配退货信息
function skuInput(e) {
    if ($("#txtSku").attr("st") == "0") return;
    if (e.which != 13) {
        $("#txtSdoMsg").empty();
        return;
    }
    if ($.trim($(this).val()).length <= 0) return;
    var sku = $(this).val();
    if ($.trim($("#txtState").attr("sku")).length > 0) {
        if ($.trim($("#txtState").attr("sku")) == sku) { onScanDo(); return; }
        errorMsg(obj_sdo_msg, "请先保存SKU " + $("#txtState").attr("sku") + "的扫描结果，再扫描其它SKU");
        alert("请先保存SKU " + $("#txtState").attr("sku") + "的扫描结果，再扫描其它SKU");
        $("#txtSku").val("");
        setScan();
        return;
    }
    loadInfo4Scan(sku);
}
function loadInfo4Scan(sku) {
    var items = [];
    $("#tblLines").find("tr[sku=" + sku + "]").each(function(i, elm) {  //循环所有的收货单明细，如果SKU匹配则取出来
        items.push({ line: $(elm).attr("line"), st: $(elm).attr("st"), sku: sku
            , purQty: state.integer(null, $(elm).children(":eq(6)"))
            , rcvQty: state.integer(null, $(elm).children(":eq(7)"))
            , maxQty: state.integer("maxqty", elm)
            , tempQty: state.integer(null, $(elm).children(":eq(8)")) 
        });
    });
    if (items.length <= 0) {  //采购订单中没有这个sku
        errorMsg(obj_sdo_msg, "采购订单明细中没有SKU: " + sku);
        alert("采购订单明细中没有SKU: " + sku);
        $("#txtSku").val("");
        setScan();
        return;
    }
    //正常收货
    var tempItems = [];
    for (var i = 0; i < items.length; i++) {
        if (items[i].purQty > items[i].rcvQty + items[i].tempQty) tempItems.push(items[i]);
    }
    if (tempItems.length > 1) { popupSelect(tempItems); return; }
    if (tempItems.length == 1) { loadSku(tempItems[0]); return; }
    //如果还有可以超收的采购订单明细
    for (var i = 0; i < items.length; i++) {
        if (items[i].maxQty+items[i].purQty > items[i].rcvQty+items[i].tempQty) tempItems.push(items[i]);
    }
    if (tempItems.length > 1) { popupSelect(tempItems); return; }
    if (tempItems.length == 1) { loadSku(tempItems[0]); return; }
    //该sku不可以再收货
    errorMsg(obj_sdo_msg, "SKU " + sku + "无法再收货");
    alert("SKU " + sku + "无法再收货");
    $("#txtSku").val("");
    setScan();
}
function popupSelect(items) {
    var html = "";
    for (var i = 0; i < items.length; i++) {
        html = html + '<input class="left"' + (i == 0 ? " checked" : "") + ' type="radio" name="dupitemSelect" value="' + items[i].line + '" /><label for="rdo_' + items[i].line + '" class="left" style="margin-top:3px;text-indent:0;">行' + items[i].line + '</label>';
    }
    html = html + '<div class="left divCommand"><a id="cmdDupSelect" href="#a" class="toolbutton left" style="text-indent:0;"><img src="../images/b_confirm.gif" alt="" /><span>&nbsp;确定</span></a></div>';
    $("#txtSdoMsg").empty();
    $(html).appendTo("#txtSdoMsg");
    disableScan(); //禁止扫描，必须选择之后才能继续扫描
    $("#cmdDupSelect").bind("click", function() {
        var selectedItem = $("#txtSdoMsg").find("input[name=dupitemSelect][checked]");
        if (selectedItem.length <= 0) {
            alert("您必须选择针对哪个采购订单明细收货");
            return;
        }
        for (var j = 0; j < items.length; j++) {
            if (items[j].line == selectedItem.val()) { loadSku(items[j]); break; }
        }
        $("#txtSdoMsg").empty();
        enableScan(); //选择之后启用扫描
        setScan();
    });
}
function loadSku(item) {
    state.sku(item.sku).line(item.line).purQty(item.purQty).rcvQty(item.rcvQty).tempQty(item.tempQty).maxQty(item.maxQty);
    var srcobj = $("#tblLines").find("tr[line=" + item.line + "]");
    $("#div_po_line").children(":eq(0)").children(":eq(0)")
        .children(":eq(1)").text(item.sku)
        .end().children(":eq(3)").text(srcobj.children(":eq(2)").text())
        .end().children(":eq(5)").text(srcobj.children(":eq(3)").text())
        .end().children(":eq(7)").text(srcobj.children(":eq(4)").text())
        .end().children(":eq(9)").text(srcobj.children(":eq(5)").text())
        .end().children(":eq(11)").text(item.purQty)
        .end().children(":eq(13)").text(srcobj.children(":eq(9)").text());
    onScanDo();
}
function onScanDo() {
    if (state.rcvQty() + state.tempQty() >= state.purQty() + state.maxQty()) {
        errorMsg(obj_sdo_msg, "已经超过最大可收货数量");
        alert("已经超过最大可收货数量");
        $("#txtSku").val("");
        disableScan();
        return;
    }
    state.tempQty(state.tempQty() + 1).scanQty(state.scanQty() + 1);
    $("#txtQty").val(state.scanQty());
    $("#txtSku").val("");
    setScan();
    //友好提示信息
    if (state.purQty() - state.rcvQty() - state.tempQty() > 0) return;
    if (state.purQty() - state.rcvQty() - state.tempQty() == 0) {
        infoMsg(obj_sdo_msg, "收货数量已经达到采购数量，请选择入库的库位、货架，保存本次扫描入库结果");
        return;
    }
    if (state.purQty() + state.maxQty() == state.rcvQty() + state.tempQty()) {
        errorMsg(obj_sdo_msg, "已经达到系统允许的最大收货数量，请选择入库的库位、货架，保存本次扫描入库结果");
        disableScan();
        return;
    }
    infoMsg(obj_sdo_msg, "收货数量已经超过采购数量<strong><font color='red'>" + (state.tempQty() + state.rcvQty() - state.purQty()) + "</font></strong>件，请选择入库的库位、货架，保存本次扫描入库结果");
}
function whInput(e) {
    if (e.which != 13) return;
    if (!state.scaned()) return;
    state.loadwh($("#drpArea").val(), $("#txtSection").val(), function() {
        $("#txtCapacity").val(state.capacity());
        var diffQty = state.capacity() - state.stored();
        if (diffQty <= 0) return;
        $("#txtAssignQty").val(state.scanQty()>diffQty?diffQty:state.scanQty());
    });
}
function errorMsg(obj, msg) {
    obj.empty().css("color", "red").html(msg);
}
function infoMsg(obj, msg) {
    obj.empty().css("color", "blue").html(msg);
}
function msg(obj, msg) {
    obj.empty().css("color", "gray").html(msg);
}
function clearInfo(){
    if (state.notSaved() && !confirm("确实清除当前未保存的数据？")) return;
    if (!state.scaned()) return;
    var sku = state.sku();
    state.clear();
    $("#txtSku").val("");
    enableScan();
    $("#txtQty").val("");
    $("#drpArea").val("");
    $("#txtSection").val("");
    $("#txtCapacity").val("");
    $("#txtAssignQty").val("");
    $("#div_po_line").children(":eq(0)").children(":eq(0)")
        .children(":eq(1)").text("")
        .end().children(":eq(3)").text("")
        .end().children(":eq(5)").text("")
        .end().children(":eq(7)").text("")
        .end().children(":eq(9)").text("")
        .end().children(":eq(11)").text("")
        .end().children(":eq(13)").text("");
    msg(obj_sdo_msg, "SKU "+sku+"扫描的数据已清除");
}
function onsave() {
    state.save();
}
function onreturn() {
    var ret = true;
    if (state.notSaved()) ret = confirm("当前扫描的数据尚未保存，您确实要离开吗？");
    if (!ret) return;
    var url = $("#txtReturnUrl").val();
    window.location.href = url;
}
    </script>
    <script language="c#" runat="server">
        private static log4net.ILog log = log4net.LogManager.GetLogger("ERP.Web.Receive.PurchaseRCVLineScan");
        
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (log.IsDebugEnabled)
                    log.Debug("PageLoad - rcv line scan: ordNum="+WebUtil.Param("ordNum")+", return="+WebUtil.Param("return"));
                WebUtil.DisableControl(this.txtQty);
                using (ISession session = new Session())
                {
                    string rcvNumber = WebUtil.Param("ordNum");
                    RCVHead head = RCVHead.Retrieve(session, rcvNumber);
                    IList<WHArea> areas = ERPUtil.GetWHArea(session, RCVHead.ORD_TYPE_PUR, null, head.LocationCode);
                    this.drpArea.Items.Clear();
                    this.drpArea.Items.Add(new ListItem("", ""));
                    foreach (WHArea area in areas)
                        this.drpArea.Items.Add(new ListItem(area.AreaCode, area.AreaCode));

                    ObjectQuery query = session.CreateObjectQuery(@"
select l.LineNumber as LineNumber,s.BarCode as BarCode,m.ItemCode as ItemCode,m.ItemName as ItemName
    ,s.ColorCode as ColorCode,color.ColorText as ColorText,s.SizeCode as SizeCode
    ,l.PurchaseQty as PurchaseQty,l.ReceiveQty as ReceiveQty,l.UnfinishedReceiveQty as UnfinishedReceiveQty
    ,f_pur_rcv_tolerance_ratio(l.OrderNumber) as Tolerance
from POLine l
inner join ItemSpec s on s.SKUID=l.SKUID
inner join ItemMaster m on m.ItemID=s.ItemID
left join ItemColor color on color.ColorCode=s.ColorCode
where l.OrderNumber=?ordNum and l.LineStatus=?status 
    and l.PurchaseQty+l.PurchaseQty*f_pur_rcv_tolerance_ratio(l.OrderNumber)-l.ReceiveQty-l.UnfinishedReceiveQty>0
order by l.LineNumber")
                        .Attach(typeof(POLine)).Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                        .SetValue("?ordNum", head.RefOrderNumber, "l.OrderNumber")
                        .SetValue("?status", POLineStatus.Open, "l.LineStatus");
                    this.repeatControl.DataSource = query.DataSet();
                    this.repeatControl.DataBind();
                }
            }
        }
        public decimal TransQty(object purQty, object rcvQty, object tempQty)
        {
            return Magic.Framework.Utils.Cast.Decimal(purQty, 0M)
                - Magic.Framework.Utils.Cast.Decimal(rcvQty, 0M)
                - Magic.Framework.Utils.Cast.Decimal(tempQty, 0M);
        }
        public decimal MaxTransQty(object purQty, object exceedQty)
        {
            return Magic.Framework.Utils.Cast.Decimal(purQty, 0M) *Magic.Framework.Utils.Cast.Decimal(exceedQty, 0M);
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="扫描入库" />
        <input type="text" id="txtState" style="display: none;" value="" sku='' line='' purQty='' rcvQty='' tempQty='' maxQty='' scanQty='' area='' section='' capacity='' stored='' />
        <input type="text" id="txtReturnUrl" style="display:none;" value='<%= WebUtil.Param("return") %>' />
        <table id="div_sdo_info_input" class="queryArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width: 70px;">SKU(条码)&nbsp;</td>
                <td style="width: 125px;">
                    <input type="text" id="txtSku" class="input" style="width: 115px;" maxlength="13" st="1" />
                </td>
                <td style="color: red; text-indent: 10px;" id="txtSdoMsg"></td>
            </tr>
        </table>
        <div style="margin-top: 1px; margin-bottom: 2px; height: 20px;margin-left:2px;">
            <div class="left divCommand">
                <a href='javascript:clearInfo();' class="toolbutton" id="cmdClear" style="width: 44px; float: left;">
                    <img src="../images/b_delete.gif" alt="" /><span>&nbsp;取消</span>
                </a>
                <a href='javascript:onreturn();' class="toolbutton" id="A1" style="width: 44px; float: left;">
                    <img src="../images/b_back.gif" alt="" /><span>&nbsp;返回</span>
                </a>
            </div>
        </div>
        <table class="queryArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width: 65px;">待入库量&nbsp;</td>
                <td style="width: 60px;"><input type="text" id="txtQty" class="input" style="width: 50px;" maxlength="4" runat="server" /></td>
                <td class="label" style="width: 35px;">库位&nbsp;</td>
                <td style="width: 90px;"><asp:DropDownList ID="drpArea" runat="server" Width="80px" CssClass="select"></asp:DropDownList></td>
                <td class="label" style="width: 35px;">货架&nbsp;</td>
                <td style="width: 70px;"><input type="text" id="txtSection" class="input" style="width: 60px;" maxlength="10" /></td>
                <td class="label" style="width: 35px;">容量&nbsp;</td>
                <td style="width:50px;"><input type="text" id="txtCapacity" class="input readonly" readonly="-1" style="width: 40px;" maxlength="3" /></td>
                <td class="label" style="width: 80px;">批次入库量&nbsp;</td>
                <td style="width:50px;"><input type="text" id="txtAssignQty" class="input" style="width: 40px;" maxlength="4" /></td>
                <td><div class="left divCommand">
                        <a href='javascript:onsave();' class="toolbutton" id="cmdSave" style="width: 50px; float: left;">
                            <img src="../images/b_save.gif" alt="" /><span>&nbsp;保存</span>
                        </a>
                    </div>
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        <table id="div_po_line" class="queryArea" cellpadding="0" cellspacing="0" style="border-top: none;">
            <tr>
                <td class="label" style="width: 35px;">SKU&nbsp;</td>
                <td style="width: 105px;"></td>
                <td class="label" style="width: 35px;">货号&nbsp;</td>
                <td style="width: 75px;"></td>
                <td class="label" style="width: 60px;">商品名称&nbsp;</td>
                <td style="width: 200px;"></td>
                <td class="label" style="width: 35px;">颜色&nbsp;</td>
                <td style="width: 80px;"></td>
                <td class="label" style="width: 35px;">尺码&nbsp;</td>
                <td style="width: 35px;"></td>
                <td class="label" style="width: 60px;">采购数量&nbsp;</td>
                <td style="width: 50px;"></td>
                <td class="label" style="width: 84px;">剩余可收货量&nbsp;</td>
                <td style="width: 50px;"></td>
                <td></td>
            </tr>
        </table>
        <div>采购订单剩余未完成收货的明细：</div>
        <table id="tblLines" class="datalist2" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label h" style="width: 55px;">PO行号</td>
                <td class="label h" style="width: 110px;">SKU(条码)</td>
                <td class="label h" style="width: 80px;">货号</td>
                <td class="label h">商品名称</td>
                <td class="label h" style="width: 100px;">颜色</td>
                <td class="label h" style="width: 40px;">尺码</td>
                <td class="label h" style="width: 60px;">采购数量</td>
                <td class="label h" style="width: 60px;">已收货</td>
                <td class="label h" style="width: 90px;">处理中收货量</td>
                <td class="label h" style="width: 90px;">剩余可收货量</td>
            </tr>
            <asp:Repeater ID="repeatControl" runat="server">
                <ItemTemplate>
                    <tr sku='<%# Eval("BarCode") %>' line='<%# Eval("LineNumber") %>' maxqty='<%# this.MaxTransQty(Eval("PurchaseQty"), Eval("Tolerance")) %>' st='0'>
                        <td><%# RenderUtil.FormatString(Eval("LineNumber"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("BarCode"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("ItemCode"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("ItemName"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("ColorCode"))%>&nbsp;<%# RenderUtil.FormatString(Eval("ColorText"))%></td>
                        <td><%# RenderUtil.FormatString(Eval("SizeCode"))%></td>
                        <td><%# RenderUtil.FormatNumber(Eval("PurchaseQty"), "#0.##", "0")%></td>
                        <td><%# RenderUtil.FormatNumber(Eval("ReceiveQty"), "#0.##", "0")%></td>
                        <td><%# RenderUtil.FormatNumber(Eval("UnfinishedReceiveQty"), "#0.##", "0")%></td>
                        <td><%# RenderUtil.FormatNumber(this.TransQty(Eval("PurchaseQty"), Eval("ReceiveQty"), Eval("UnfinishedReceiveQty")), "#0.##", "0")%></td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
        </table>
    </div>
    </form>
</body>
</html>