<%@ Page Language="C#" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>交接明细录入</title>
    <link rel="Stylesheet" type="text/css" href="../CSS/queryPage.css" />
    <style type="text/css">
        .chk_line /*lines*/
        {
        	width:100%;margin:0;padding:0;
        }
        .i1,.i2,.i3,.i4,.i5,.i6 /*line cells*/
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
        .i5 {width:80px;}
        .i6 {width:80px;}
        .left { float: left; }
        #divCommand { width:160px; margin-left:15px; }
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
<script type="text/javascript">
var obj_line_template =  null, obj_sdo_msg = null;
var interval_sdo = null, scan_time = 300, sdo_status=false;
var sdo_length = 12;
$(document).ready(function(){
    var wth = 0;
    for(var i=1; i<=5; i++) wth += $("#chk_h"+i).width();
    var docWith = $("#chk_head").width();
    $(".last").width(docWith-wth-7);
    $(".chk_line").width(docWith);
    $("#div_sdo_info_input").width(docWith);
    obj_line_template = $("#line_template");
    obj_sdo_msg = $("#txtSdoMsg");
    $("#txtSDONumber").bind("focus", beginSdoScan);
    $("#txtSDONumber").bind("blur", stopSdoScan);
    
    waitSdo();
});
function clearInfo(){
    obj_sdo_msg.text("");
}
function waitSdo(){
    //自动触发txtSDONumber上的onfocus事件，开始扫描txtSDONumber是否录入了完整的发货单号码
    document.getElementById("txtSDONumber").select();
}
function beginSdoScan(){
    sdo_status = true;
    interval_sdo = setTimeout(scanSdoHandler, scan_time);
}
function stopSdoScan(){ sku_status = false; if(interval_sdo!=null) clearTimeout(interval_sdo); }
function scanSdoHandler(){
    var ordNumber = $.trim(document.getElementById("txtSDONumber").value);
    if(ordNumber.length > sdo_length)  {
        errorMsg(obj_sdo_msg, "发货单"+ordNumber+"号码不正确");
        document.getElementById("txtSDONumber").value="";
    }else if(ordNumber.length>0) infoMsg(obj_sdo_msg, "");
    if(ordNumber.length!=sdo_length) {  //继续定时扫描
        if(sdo_status) interval_sdo = setTimeout(scanSdoHandler, scan_time); 
        return; 
    }
    //判断发货单是否已经扫描过了
    if(document.getElementById("sdo_"+ordNumber)!=null){
        errorMsg(obj_sdo_msg, "发货单"+ordNumber+"重复录入！");
        document.getElementById("txtSDONumber").value="";
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
        data: { action: "AddDeliverOrder2IC", icNumber: $("#txtICNumber").val(), ordNumber: ordNumber },
        error: function(xmlhttp) { 
            errorMsg(obj_sdo_msg, "添加发货单"+ordNumber+"出错，请和管理员联系"); 
            alert("添加发货单"+ordNumber+"出错，请和管理员联系");
            waitSdo();
        },
        
        success: function(data, status){
            if(data._error==true) { 
                errorMsg(obj_sdo_msg, "加载发货单"+ordNumber+"出错："+data._error_msg); 
                alert("加载发货单"+ordNumber+"出错："+data._error_msg);
                waitSdo();
                return;
            }
            //显示发货单信息
            var newLineElement = obj_line_template.clone();
            newLineElement.attr("id", "sdo_" + data.orderNumber);
            newLineElement.find("li.i1").text(data.orderNumber)
                .end().find("li.i2").text(data.saleOrder)
                .end().find("li.i3").text(data.member)
                .end().find("li.i4").text(data.contact)
                .end().find("li.i5").text(data.phone)
                .end().find("li.i6").text(data.district)
                .end().appendTo("#div_sdo_lines");
            newLineElement.show();
                
            msg(obj_sdo_msg, "发货单"+ordNumber+"已经添加");
            waitSdo();
        }//success
    });//$.ajax({
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
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="交接明细录入" />
        <input type="text" style="display:none;" id="txtICNumber" value='<%= WebUtil.Param("ordNumber") %>' />

        <table id="div_sdo_info_input" class="queryArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width:65px;">发货单&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" id="txtSDONumber" class="input" style="width:115px;" maxlength="13" />
                </td>
                <td id="txtSdoMsg" style="color:red; text-indent:10px;"></td>
            </tr>
        </table>
        <div style="margin-top:5px;margin-bottom:2px;height:20px;">
            <span class="left" style="margin-top:4px;">添加的发货单列表</span>
            <div id="divCommand" class="left">
                <a href='InterchangeLine.aspx?ordNum=<%= WebUtil.Param("ordNumber") %>&return=<%= Microsoft.JScript.GlobalObject.escape(WebUtil.Param("return")) %>' class="toolbutton" id="cmdDetail" style="width:70px; float:left;"><img src="../images/b_detail.gif" alt="" /><span>&nbsp;管理明细</span></a> 
                <a href='<%= WebUtil.Param("return") %>' class="toolbutton" id="cmdReturn" style="width:50px; float:left;"><img src="../images/b_back.gif" alt="" /><span>&nbsp;返回</span></a> 
            </div>
        </div>
        <div id="div_sdo_line_head">
            <ul id="line_template" chkstate="0" class="chk_line" style="display:none;">
                <li class="i1"></li>
                <li class="i2"></li>
                <li class="i3"></li>
                <li class="i4"></li>
                <li class="i5"></li>
                <li class="i6 last"></li>
            </ul>
            <ul id="chk_head" class="chk_line">
                <li id="chk_h1" class="i1 chk_hi">发货单号码</li>
                <li id="chk_h2" class="i2 chk_hi">订单号码</li>
                <li id="chk_h3" class="i3 chk_hi">会员姓名</li>
                <li id="chk_h4" class="i4 chk_hi">联系人</li>
                <li id="chk_h5" class="i5 chk_hi">电话</li>
                <li class="i6 chk_hi last">省,&nbsp;市&nbsp;&nbsp;&nbsp;&nbsp;区,&nbsp;县</li>
            </ul>
        </div>
        <div id="div_sdo_lines"></div>
        <div class="tips">
            <span class="tips">操作说明:</span><br />
            <span class="tips">扫描发货单号码后，系统会自动将发货单添加到交接明细中</span><br />
            <span class="tips">发货单能够添加到交接明细中的条件: 必须为"包装完成"状态; 发货单还没有被添加到交接单中; 发货单包装时选择的物流公司与交接单的物流公司相同</span><br />
        </div>
    </div>
    </form>
</body>
</html>
