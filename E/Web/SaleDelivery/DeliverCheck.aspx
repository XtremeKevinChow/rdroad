<%@ Page Language="C#" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>核货</title>
    <link rel="Stylesheet" type="text/css" href="../CSS/queryPage.css" />
    <style type="text/css">
        .chk_line /*lines*/
        {
        	width:100%;margin:0;padding:0;
        }
        .i1,.i2,.i3,.i4,.i5,.i6,.i7 /*line cells*/
        {
        	height:18px;float:left; text-indent:3px;
        	list-style-type:none;margin:0;padding:0; 
        	border-collapse:collapse;border-left:solid 1px #CCCCCC;border-bottom:solid 1px #CCCCCC;
        }
        .last /*the last cells*/
        { border-right:solid 1px #CCCCCC; }
        .chk_hi /*head cells*/
        {
            background-color:#eee;font-weight:600;text-align:center;
            border-top:solid 1px #CCCCCC;
        }
        .i1 {width:140px;}
        .i2 {width:110px;}
        .i3 {width:125px;}
        .i4 {width:90px;}
        .i5 {width:80px; text-align:right;}
        .i6 {width:80px; text-align:right;}
        .left { float: left; }
        #divCommand { width:170px; margin-left:15px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
<script type="text/javascript">
var obj_line_template =  null, obj_sdo_msg = null, obj_sku_msg = null;
var interval_sdo = null, interval_sku = null, scan_time = 300;
var sdo_length = 12, sku_length = 13, sdo_status = false, sku_status = false, check_status = 0;
$(document).ready(function(){
    var wth = 0;
    for(var i=1; i<=6; i++) wth += $("#chk_h"+i).width();
    var docWith = $("#chk_head").width();
    $(".last").width(docWith-wth-8);
    $(".chk_line").width(docWith);
    $("#div_sdo_info_input").width(docWith);
    $("#div_sdo_head").width(docWith);
    $("#div_cmd").width(docWith);
    obj_line_template = $("#line_template");
    obj_sdo_msg = $("#txtSdoMsg");
    obj_sku_msg = $("#txtSkuMsg");
    $("#txtSDONumber").bind("focus", beginSdoScan);
    $("#txtSDONumber").bind("blur", stopSdoScan);
    $("#txtItemSku").bind("focus", beginSkuScan);
    $("#txtItemSku").bind("blur", stopSkuScan);
    
    $("#cmdCancel").bind("click", function(){
        if(check_status==0 || check_status==1){
            $("#txtSDONumber").select();
            return;
        }
        if(confirm("确认取消该发货单的核货作业？")) { cancelCheck(); return; }
        $("#txtItemSku").select();
        return;
    });
    $("#cmdFinish").bind("click", function(i){
        if(check_status==0 || check_status==1){
            $("#txtSDONumber").select();
            return;
        }
        var message = "";
        $("#div_sdo_lines").find("ul").each(function(){
            var sku = $(this).attr("id").substring(4);
            if($(this).attr("chkstate")=="0") message=message+sku+"没有核货; ";
            else if($(this).attr("chkstate")=="50") message=message+sku+"核货数量不足; ";
            else if($(this).attr("chkstate")=="150") message=message+sku+"核货数量超过发货数量; ";
        });
        if(message.length>0) {
            //核货未完成
            errorMsg(obj_sku_msg, message);
            alert(message);
            $("#txtItemSku").select();
            return;
        }else{
            //核货完成
            var orderNumber = $("#lblSDONumber").text();
            if(!confirm("确认发货单"+orderNumber+"核货完成？")){
                $("#txtItemSku").select();
                return;
            }
            $.ajax({
                async: true,
                dataType: "json",
                timeout: 10000,
                type: "POST",
                url: "../ajax.ashx",
                data: { action: "CheckDeliverOrderFinish", ordNumber: orderNumber },
                error: function(xmlhttp) { 
                    errorMsg(obj_sdo_msg, "发货单"+orderNumber+"完成核货时发生异常，请和管理员联系"); 
                    alert("发货单"+orderNumber+"完成核货时发生异常，请和管理员联系");
                    $("#txtItemSku").select();
                },
                
                success: function(data, status){
                    if(data._error==true) { 
                        errorMsg(obj_sdo_msg, "发货单"+orderNumber+"完成核货时发生异常："+data._error_msg); 
                        alert("发货单"+orderNumber+"完成核货时发生异常："+data._error_msg);
                        $("#txtItemSku").select();
                        return;
                    }
                    initState();//继续下一张核货单作业
                    msg(obj_sdo_msg, "发货单"+orderNumber+"核货完成");
                }//success
            });//$.ajax({
        }
    });
    
    initState();
});
function cancelCheck(){
    var orderNumber = $("#lblSDONumber").text();
    initState(); 
    msg(obj_sdo_msg, "发货单"+orderNumber+"取消核货"); 
}
function initState(){
    check_status = 0;
    clearInfo();
    stopSdoScan();
    stopSkuScan();
    waitSdo();
}
function clearInfo(){
    //是否需要提示确认
    $("#div_sdo_lines").empty();
    $("#lblSDONumber").text("");
    $("#lblSONumber").text("");
    $("#lblMemberName").text("");
    $("#lblContact").text("");
    $("#lblPhone").text("");
    $("#lblAddr").text("");
    obj_sdo_msg.text("");
    obj_sku_msg.text("");
}

function waitSdo(){
    check_status = 1;
    //自动触发txtSDONumber上的onfocus事件，开始扫描txtSDONumber是否录入了完整的发货单号码
    document.getElementById("txtSDONumber").select();
}
function beginSdoScan(){
    if(check_status!=1) return; //不是等待发货单录入状态则不进行任何处理
    sdo_status = true;
    interval_sdo = setTimeout(scanSdoHandler, scan_time);
}
function stopSdoScan(){ sku_status = false; if(interval_sdo!=null) clearTimeout(interval_sdo); }
function scanSdoHandler(){
    if(check_status!=1) return; //不是等待发货单录入状态则不进行任何处理
    var ordNumber = $.trim(document.getElementById("txtSDONumber").value);
    if(ordNumber.length > sdo_length)  errorMsg(obj_sdo_msg, "发货单号码不正确");
    else if(ordNumber.length>0) infoMsg(obj_sdo_msg, "");
    if(ordNumber.length!=sdo_length) {  //继续定时扫描
        if(sdo_status) interval_sdo = setTimeout(scanSdoHandler, scan_time); 
        return; 
    }
    //发货单号码长度匹配，尝试从服务器加载
    loadSDO(ordNumber);
}

function loadSDO(ordNumber){
    document.getElementById("txtSDONumber").value = "";
    $.ajax({
        async: true,
        dataType: "json",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: { action: "DeliverOrder4Check", ordNumber: ordNumber },
        error: function(xmlhttp) { 
            errorMsg(obj_sdo_msg, "加载发货单"+ordNumber+"出错，请和管理员联系"); 
            alert("加载发货单"+ordNumber+"出错，请和管理员联系");
            $("#txtSDONumber").select();
        },
        
        success: function(data, status){
            if(data._error==true) { 
                errorMsg(obj_sdo_msg, "加载发货单"+ordNumber+"出错："+data._error_msg); 
                alert("加载发货单"+ordNumber+"出错："+data._error_msg);
                $("#txtSDONumber").select();
                return;
            }
            //显示发货单信息
            $("#lblSDONumber").text(data.orderNumber);
            $("#lblSONumber").text(data.saleOrder);
            $("#lblMemberName").text(data.member);
            $("#lblContact").text(data.contact);
            $("#lblPhone").text(data.phone);
            $("#lblAddr").text(data.district);
            if(data.lines!=null){
                for(var i=0; i<data.lines.length; i++){
                    var line = data.lines[i];
                    var newLineElement = obj_line_template.clone();
                    newLineElement.attr("id", "sku_" + line.sku);
                    newLineElement.find("li.i1").text(line.sku)
                        .end().find("li.i2").text(line.itemCode)
                        .end().find("li.i3").text(line.color)
                        .end().find("li.i4").text(line.size)
                        .end().find("li.i5").text(line.qty)
                        .end().find("li.i6").text("0")
                        .end().find("li.i7").text(line.itemName)
                        .end().appendTo("#div_sdo_lines");
                    newLineElement.show();
                }
            }
            msg(obj_sdo_msg, "发货单"+ordNumber+"正在核货中，请将当前输入焦点放在SKU(条码)输入框中，扫描或者手工录入商品条码");
            waitSku();//开始扫描sku条码录入
        }//success
    });//$.ajax({
}

function waitSku(){
    check_status = 2;
    //自动触发txtItemSku上的onfocus事件，开始扫描txtItemSku是否录入了完整的sku条码
    document.getElementById("txtItemSku").select();
}
function beginSkuScan(){
    if(check_status!=2) return; //不是等待sku录入状态则不进行任何处理
    sku_status = true;
    interval_sku = setTimeout(scanSkuHandler, scan_time);
}
function stopSkuScan(){ sku_status = false; if(interval_sku!=null) clearTimeout(interval_sku); }
function scanSkuHandler(){
    if(check_status!=2) return; //不是等待sku录入状态则不进行任何处理
    var itemSku = $.trim(document.getElementById("txtItemSku").value);
    if(itemSku.length > sku_length)  {
        errorMsg(obj_sku_msg, "SKU "+itemSku+"不正确");
        document.getElementById("txtItemSku").value = "";
        alert("SKU "+itemSku+"不正确");
        if(sku_status) interval_sku = setTimeout(scanSkuHandler, scan_time);
        return;
    }
    if(itemSku.length >0) obj_sku_msg.text("");
    if(itemSku.length!=sku_length) {  //继续定时扫描
        if(sku_status) interval_sku = setTimeout(scanSkuHandler, scan_time); 
        return;
    }
    //sku长度匹配
    var checkRow = $("#sku_"+itemSku); //$("#div_sdo_lines").find("ul[id=sku_"+itemSku+"]");
    if(checkRow==null || checkRow.length<=0)  {
        document.getElementById("txtItemSku").value = "";
        errorMsg(obj_sku_msg, "SKU "+itemSku+"不存在");
        if(confirm("SKU"+itemSku+"不存在，是否取消该发货单的核货作业？")){
            cancelCheck();
            return;
        }else{
            //if(sku_status) interval_sku = setTimeout(scanSkuHandler, scan_time); 
            return;
        }
    }
    var checkCell = checkRow.find(".i6"), totalCell = checkRow.find(".i5");
    var curQty = parseInt(checkCell.text()), totalQty = parseInt(totalCell.text());
    curQty = isNaN(curQty) ? 1 : (++curQty);
    totalQty = isNaN(totalQty) ? 0 : totalQty;
    setCheckState(checkRow, checkCell, itemSku, curQty, totalQty);

    if(sku_status) interval_sku = setTimeout(scanSkuHandler, scan_time); 
}

function errorMsg(obj, msg){
    obj.css("color", "red").text(msg);
}
function infoMsg(obj, msg){
    obj.css("color", "blue").text(msg);
}
function msg(obj, msg){
    obj.css("color", "gray").text(msg);
}
function setCheckState(checkRow, checkCell, sku, qty, totalQty){
    checkCell.text(qty); 
    if(qty==totalQty)
        checkRow.attr("chkstate", "100")
            .css("background-color", "green").css("color", "white")
            .find("li").css("background-color", "green");
    else if(qty<totalQty)
        checkRow.attr("chkstate", "50")
            .css("background-color", "yellow").css("color", "black")
            .find("li").css("background-color", "yellow");
    else
        checkRow.attr("chkstate", "150")
            .css("background-color", "red").css("color", "white")
            .find("li").css("background-color", "red");
    document.getElementById("txtItemSku").value = "";
    if(qty<=totalQty) infoMsg(obj_sku_msg, "SKU "+sku+"已核货"+qty+"件");
    else errorMsg(obj_sku_msg, "SKU "+sku+"已核货"+qty+"件");
}
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="核货" ExtInfo="发货明细背景色说明. 白色:未核货, 黄色:已有核货数量但未达到订单数量, 绿色:核货数量等于订单数量, 红色:核货数量超过订单数量" />

        <table id="div_sdo_info_input" class="queryArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width:65px;">发货单&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" id="txtSDONumber" class="input" style="width:115px;" maxlength="13" />
                </td>
                <td id="txtSdoMsg" style="color:red; text-indent:10px;"></td>
            </tr>
            <tr>
                <td class="label">SKU(条码)&nbsp;</td>
                <td>
                    <input type="text" id="txtItemSku" class="input" style="width:115px;" maxlength="13" />
                </td>
                <td id="txtSkuMsg" style="color:red; text-indent:10px;"></td>
            </tr>
        </table>
        <div id="div_cmd" style="margin-top:5px;margin-bottom:2px;height:20px;">
            <span class="left" style="margin-top:4px;">发货单信息</span>
            <div id="divCommand" class="left">
                <a href="#a" class="toolbutton" id="cmdFinish" style="width:60px; float:left;"><img src="../images/b_confirm.gif" alt="" /><span>&nbsp;核货完成</span></a> 
                <a href="#a" class="toolbutton" id="cmdCancel" style="width:60px; float:right;"><img src="../images/b_delete.gif" alt="" /><span>&nbsp;取消核货</span></a> 
            </div>
            <span class="tips">核货数量与订单数量一致，可以执行"核货完成"；如果需要清空当前已经加载的发货单信息，重新扫描核货，请执行"取消核货"</span>
        </div>
        <table id="div_sdo_head" class="infoArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width:60px;">发货单&nbsp;</td>
                <td style="width:125px;"><label id="lblSDONumber"></label></td>
                <td class="label" style="width:50px;">订单&nbsp;</td>
                <td style="width:125px;"><label id="lblSONumber"></label></td>
                <td class="label" style="width:35px;"></td>
                <td style="width:120px;"></td>
                <td class="label" style="width:95px;"></td>
                <td></td>
            </tr>
            <tr>
                <td class="label">会员姓名&nbsp;</td>
                <td><label id="lblMemberName"></label></td>
                <td class="label">收货人&nbsp;</td>
                <td><label id="lblContact"></label></td>
                <td class="label">电话&nbsp;</td>
                <td><label id="lblPhone"></label></td>
                <td class="label">省,&nbsp;市&nbsp;&nbsp;&nbsp;&nbsp;区,&nbsp;县</td>
                <td><label id="lblAddr"></label></td>
            </tr>
        </table>
        <div style="margin-top:5px;">发货单明细</div>
        <div id="div_sdo_line_head">
            <ul id="line_template" chkstate="0" class="chk_line" style="display:none;">
                <li class="i1"></li>
                <li class="i2"></li>
                <li class="i3"></li>
                <li class="i4"></li>
                <li class="i5"></li>
                <li class="i6"></li>
                <li class="i7 last"></li>
            </ul>
            <ul id="chk_head" class="chk_line">
                <li id="chk_h1" class="i1 chk_hi">SKU(条码)</li>
                <li id="chk_h2" class="i2 chk_hi">货号</li>
                <li id="chk_h3" class="i3 chk_hi">颜色</li>
                <li id="chk_h4" class="i4 chk_hi">尺码</li>
                <li id="chk_h5" class="i5 chk_hi">发货数量</li>
                <li id="chk_h6" class="i6 chk_hi">检货数量</li>
                <li class="i7 chk_hi last">商品名称</li>
            </ul>
        </div>
        <div id="div_sdo_lines"></div>
        <div class="tips">
            <span class="tips">执行"核货完成"，系统将进行出入库操作，并从会员帐户扣除实际支付金额，向会员发放积分，订单变为"已发货"状态，发货单变为"核货成功"状态</span><br />
            <span class="tips">务必在确保准确无误的情况下再执行"核货完成"操作</span>
        </div>
    </div>
    </form>
</body>
</html>
