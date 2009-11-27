<%@ Page Language="C#" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="Magic.Basis" %>
<%@ Import Namespace="Magic.Framework.ORM" %>
<%@ Import Namespace="Magic.Framework.ORM.Query" %>
<%@ Register Src="../Controls/FunctionTitle.ascx" TagName="FunctionTitle" TagPrefix="uc1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>包装</title>
    <link rel="Stylesheet" type="text/css" href="../CSS/queryPage.css" />
    <style type="text/css">
        .left { float: left; }
        .pad20 { margin-left:20px; }
        #divCommand { width:500px; margin-left:15px; }
        #divCommand img { margin-top:2px; float:left; } 
        #divCommand span { margin-top:0px; float:left; cursor: pointer; } 
    </style>
    <script src="../script/jquery.js" type="text/javascript"></script>
    <script src="../script/jquery.dimensions.min.js" type="text/javascript"></script>
    <script src="../script/interface.fix.js" type="text/javascript"></script>
<script type="text/javascript">
var obj_sdo_msg = null;
var interval_sdo = null, scan_time = 300, sdo_status=false;
var sdo_length = <%= WebUtil.ShippingNoticeNumberLength %>;
$(document).ready(function(){
    obj_sdo_msg = $("#txtSdoMsg");
    $("#txtSDONumber").bind("focus", beginSdoScan);
    $("#txtSDONumber").bind("blur", stopSdoScan);
    waitSdo();
});
function clearInfo(){
    obj_sdo_msg.text("");
    $("#lblSDONumber").text("");
    $("#lblSONumber").text("");
    $("#lblMemberName").text("");
    $("#lblContact").text("");
    $("#lblPhone").text("");
    $("#lblAddr").text("");
    $("#lblPackageType").text("");
    $("#lblNote").text("");
    $("#txtShippingNumber").val("");
    $("#txtPackageWeight").val("");
    $("#txtSDONumber").val("");
    $("#txtInvoice").val("");
    $("#txtPackageCount").val("");
    $("#drpLog").val("0");
    
    $("#hidShippingNumber").val("");
    $("#hidWeight").val("");
    $("#hidNeedInvoice").val("");
    $("#hidInvoiceNumber").val("");
}
function waitSdo(){
    //自动触发txtSDONumber上的onfocus事件，开始扫描txtSDONumber是否录入了完整的发货单号码
    sdo_status = true;
    document.getElementById("txtSDONumber").select();
}
function beginSdoScan(){
    if(!sdo_status) return;
    interval_sdo = setTimeout(scanSdoHandler, scan_time);
}
function stopSdoScan(){ if(interval_sdo!=null) clearTimeout(interval_sdo); }
function scanSdoHandler(){
    var ordNumber = $.trim(document.getElementById("txtSDONumber").value);
    if(ordNumber.length > sdo_length)  {
        errorMsg(obj_sdo_msg, "发货单"+ordNumber+"号码不正确");
    }else if(ordNumber.length>0) infoMsg(obj_sdo_msg, "");
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
        data: { action: "DeliverOrder4Package", ordNumber: ordNumber },
        error: function(xmlhttp) { 
            errorMsg(obj_sdo_msg, "加载发货单"+ordNumber+"出错，请和管理员联系"); 
            alert("加载发货单"+ordNumber+"出错，请和管理员联系");
            document.getElementById("txtSDONumber").value="";
            waitSdo();
        },
        
        success: function(data, status){
            if(data._error==true) { 
                errorMsg(obj_sdo_msg, "加载发货单"+ordNumber+"出错："+data._error_msg); 
                alert("加载发货单"+ordNumber+"出错："+data._error_msg);
                document.getElementById("txtSDONumber").value="";
                waitSdo();
                return;
            }
            //显示发货单信息
            $("#hidShippingNumber").val(data.shippingNumber);
            $("#hidWeight").val(data.packageWeight);
            $("#hidNeedInvoice").val(data.isInvoice?"1":"0");
            $("#hidInvoiceNumber").val(data.invoice);
            
            $("#lblSDONumber").text(data.orderNumber);
            $("#lblSONumber").text(data.saleOrder);
            $("#lblMemberName").text(data.member);
            $("#lblContact").text(data.contact);
            $("#lblPhone").text(data.phone);
            $("#lblAddr").text(data.district);
            $("#lblPackageType").text(data.packageType);
            $("#lblNote").text(data.note);
            $("#lblDeliverType").text(data.deliverType);
            $("#lblPaymentMethod").text(data.paymentMethod);
            
            $("#txtPackageWeight").val(data.packageWeight);
            $("#txtShippingNumber").val(data.shippingNumber);
            $("#txtInvoice").val(data.invoice);
            $("#txtPackageCount").val(data.packageCount);
            if(data.logistics>0)
                $("#drpLog").val(data.logistics);

            sdo_status = false;
            $("#txtShippingNumber").select();
            msg(obj_sdo_msg, "发货单"+ordNumber+"的相关信息已经加载");
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
function onsave(){
    var orderNumber = $.trim($("#lblSDONumber").text());
    if(orderNumber.length<=0) return;
    var logisticId = parseInt($("#drpLog").val());
    if(isNaN(logisticId) || logisticId<=0) {
        errorMsg(obj_sdo_msg, "必须选择物流公司");
        return;
    }
    var packageCount = parseInt($("#txtPackageCount").val());
    if(isNaN(packageCount) || packageCount<=0) {
        errorMsg(obj_sdo_msg, "包裹数量请填写整数数字");
        return;
    }
    $.ajax({
        async: true,
        dataType: "json",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: { action: "PackageDeliverOrderSave"
            , ordNumber: orderNumber
            , packageWeight: $.trim($("#txtPackageWeight").val())
            , shippingNumber: $.trim($("#txtShippingNumber").val())
            , invoiceNumber:  $.trim($("#txtInvoice").val())
            , logisticsId: logisticId
            , packageCount: packageCount },
        error: function(xmlhttp) { 
            errorMsg(obj_sdo_msg, "发货单"+orderNumber+"包装时发生异常，请和管理员联系"); 
            alert("发货单"+orderNumber+"包装时发生异常，请和管理员联系");
        },
        
        success: function(data, status){
            if(data._error==true) { 
                errorMsg(obj_sdo_msg, "发货单"+orderNumber+"包装时发生异常："+data._error_msg); 
                alert("发货单"+orderNumber+"包装时发生异常："+data._error_msg);
                return;
            }
            //设置发货单隐藏字段的值
            $("#hidShippingNumber").val($.trim($("#txtShippingNumber").val()));
            $("#hidWeight").val($.trim($("#txtPackageWeight").val()));
            $("#hidInvoiceNumber").val($.trim($("#txtInvoice").val()));
            
            errorMsg(obj_sdo_msg, "发货单"+orderNumber+"包装信息保存成功");
        }//success
    });//$.ajax({
}
function onconfirm(){
    var orderNumber = $.trim($("#lblSDONumber").text());
    if(orderNumber.length<=0) return;
    if(!confirm("确认发货单"+orderNumber+"包装完成？")) return;
    $.ajax({
        async: true,
        dataType: "json",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: { action: "PackageDeliverOrderFinish", ordNumber: orderNumber },
        error: function(xmlhttp) { 
            errorMsg(obj_sdo_msg, "发货单"+orderNumber+"包装时发生异常，请和管理员联系"); 
            alert("发货单"+orderNumber+"包装时发生异常，请和管理员联系");
        },
        
        success: function(data, status){
            if(data._error==true) { 
                errorMsg(obj_sdo_msg, "发货单"+orderNumber+"包装时发生异常："+data._error_msg); 
                alert("发货单"+orderNumber+"包装时发生异常："+data._error_msg);
                return;
            }
            clearInfo();
            waitSdo();
            msg(obj_sdo_msg, "发货单"+orderNumber+"包装完成");
        }//success
    });//$.ajax({
}
function onprint(){
    var orderNumber = $.trim($("#lblSDONumber").text());
    if(orderNumber.length<=0) return;
    var message = "";
    //没有运单号不允许打印
    if($.trim($("#hidShippingNumber").val()).length<=0)
        message = "必须输入运单号; ";
    //没有包裹重量，不允许打印
    var weight = parseFloat($("#hidWeight").val());
    if(isNaN(weight) || weight<=0)
        message = message + "必须输入包裹重量; ";
    //如果会员索要发票，没有填写发票号码不允许打印
    if($("#hidNeedInvoice").val()=="1" && $.trim($("#hidInvoiceNumber").val()).length<=0)
        message = message + "必须输入发票号码; ";
    if(message.length>0) message = message +"保存之后才能打印. ";
    var templateId = parseInt($("#drpTemplate").val());
    if(isNaN(templateId) || templateId<=0)
        message = message + "必须选择打印模板";
    if(message.length>0){
        errorMsg(obj_sdo_msg, message);
        alert(message);
        return;
    }
    document.getElementById("frameDownload").src = "DeliverDownload.aspx?ordNum="+orderNumber+"&tid="+templateId;
}
</script>
<script language="c#" runat="server">
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            this.drpLog.Items.Clear();
            using (ISession session = new Session())
            {
                IList<Logistics> logs = Logistics.GetEffectiveLogistics(session);
                this.drpLog.Items.Clear();
                this.drpLog.Items.Add(new ListItem("", "0"));
                foreach (Logistics log in logs)
                    this.drpLog.Items.Add(new ListItem(log.ShortName, log.LogisticCompID.ToString()));
                IList<ExcelTemplate> templates = ExcelTemplate.GetEnabledList(session);
                this.drpTemplate.Items.Clear();
                foreach (ExcelTemplate t in templates)
                    this.drpTemplate.Items.Add(new ListItem(t.TemplateName, t.TemplateID.ToString()));
            }
        }
    }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <uc1:FunctionTitle ID="functionTitle" runat="server" PageTitle="包装" />
        <input type="text" id="hidShippingNumber" style="display:none;" value="" />
        <input type="text" id="hidWeight" style="display:none;" value="" />
        <input type="text" id="hidInvoiceNumber" style="display:none;" value="" />
        <input type="text" id="hidNeedInvoice" style="display:none;" value="" />

        <table id="div_sdo_info_input" class="queryArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width:62px;">发货单&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" id="txtSDONumber" class="input" style="width:115px;" maxlength="13" />
                </td>
                <td colspan="3" style="color:red; text-indent:10px;" id="txtSdoMsg"></td>
            </tr>
        </table>
        <table id="Table1" class="queryArea" cellpadding="0" cellspacing="0" style="border-top:none;">
            <tr>
                <td class="label" style="width:62px;">物流公司&nbsp;</td>
                <td style="width:125px;">
                    <asp:DropDownList ID="drpLog" runat="server" Width="115px" CssClass="select">
                    </asp:DropDownList>
                </td>
                <td class="label" style="width:55px;">运单号&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" id="txtShippingNumber" class="input" style="width:115px;" maxlength="20" />
                </td>
                <td class="label" style="width:55px;">发票号&nbsp;</td>
                <td style="width:125px;">
                    <input type="text" id="txtInvoice" class="input" style="width:115px;" maxlength="20" />
                </td>
                <td id="tdTitleWeight" class="label" style="width:65px;">包裹重量&nbsp;</td>
                <td style="width:75px;">
                    <input type="text" id="txtPackageWeight" class="input" style="width:70px;" maxlength="10" />
                </td>
                <td class="label" style="width:65px;">包裹数量&nbsp;</td>
                <td style="width:38px;">
                    <input type="text" id="txtPackageCount" class="input" style="width:30px;" maxlength="2" />
                </td>
                <td></td>
            </tr>
        </table>
        <div style="margin-top:5px;margin-bottom:2px;height:20px;">
            <span class="left" style="margin-top:4px;">发货单信息</span>
            <div id="divCommand" class="left">
                <a href='javascript:onsave();' class="toolbutton" id="cmdSave" style="width:44px; float:left;"><img src="../images/b_save.gif" alt="" /><span>&nbsp;保存</span></a> 
                <a href='javascript:clearInfo();waitSdo();' class="toolbutton" id="cmdClear" style="width:44px; float:left;"><img src="../images/b_back.gif" alt="" /><span>&nbsp;取消</span></a> 
                <asp:DropDownList ID="drpTemplate" runat="server" CssClass="select left pad20" Width="160px">
                </asp:DropDownList>
                <a href='javascript:onprint();' class="toolbutton" id="cmdPrint" style="width:70px; float:left;"><img src="../images/b_download.jpg" alt="" /><span>&nbsp;下载打印</span></a> 
                <a href='javascript:onconfirm();' class="toolbutton" id="cmdConfirm" style="width:70px; float:left;"><img src="../images/b_confirm.gif" alt="" /><span>&nbsp;包装完成</span></a> 
            </div>
        </div>
        <table id="div_sdo_head" class="infoArea" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label" style="width:60px;">发货单&nbsp;</td>
                <td style="width:100px;"><label id="lblSDONumber"></label></td>
                <td class="label" style="width:60px;">订单&nbsp;</td>
                <td style="width:115px;"><label id="lblSONumber"></label></td>
                <td class="label" style="width:35px;"></td>
                <td style="width:200px;"></td>
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
            <tr>
                <td class="label">送货方式&nbsp;</td>
                <td><label id="lblDeliverType"></label></td>
                <td class="label">付款方式&nbsp;</td>
                <td colspan="5"><label id="lblPaymentMethod"></label></td>
            </tr>
            <tr>
                <td class="label">包装方式&nbsp;</td>
                <td><label id="lblPackageType"></label></td>
                <td class="label">备注&nbsp;</td>
                <td colspan="5"><label id="lblNote"></label></td>
            </tr>
        </table>
        <div>
            <span class="tips">操作说明</span><br />
            <span class="tips">1. 扫描发货单，系统将自动加载发货单信息。只有"核货成功"状态的发货单才能在这里执行包装作业</span><br />
            <span class="tips">2. 选择投递包裹的物流公司，扫描运单号码，填写发票号码、包裹重量、包裹数量</span><br />
            <span class="tips">&nbsp;&nbsp;&nbsp;&nbsp;注：如果会员索要发票，则发票号码必须填写；否则发票号码可以不填</span><br />
            <span class="tips">3. "保存"按钮用于保存步骤2中填写的资料，"保存"操作不会改变发货单状态；"取消"按钮用于清空当前加载的发货单信息，清空之后可以扫描其它发货单，进行包装作业</span><br />
            <span class="tips">4. 选择正确的运单模板，下载打印快递公司运单</span><br />
            <span class="tips">5. 整个包装作业完成后，执行"包装完成"操作，发货单状态变为"包装完成"状态</span>
        </div>
    </div>
    <iframe id="frameDownload" runat="server" style="width:1px; height:1px; overflow:auto;margin:0;padding:0;border:0;" src=""></iframe>
    </form>
</body>
</html>
