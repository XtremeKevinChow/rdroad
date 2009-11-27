<%@ Control Language="C#" AutoEventWireup="false" %>
<%@ Import Namespace="System.ComponentModel" %>
<script runat="server" language="c#">
    [Description("发货单号码")]
    [Browsable(true)]
    public string SNNumber
    {
        get { return this.txt_SNNumber.Value; }
        set { this.txt_SNNumber.Value = value; }
    }
</script>
<script type="text/javascript">
    $(document).ready(function() {
        var snNumber = $(".cls_txt_SNNumber").val();
        if (snNumber != undefined && $.trim(snNumber).length > 0)
            snView_loadShippingNotice(snNumber);
        else snView_Clear();
    });   //$(document).ready(function()
//ajax加载发货单主档信息，写成ajax调用，因为重用加载发货单主档信息返回json对象的方法
function snView_loadShippingNotice(ordNumber) {
    $.ajax({
        async: true,
        dataType: "json",
        timeout: 10000,
        type: "POST",
        url: "../ajax.ashx",
        data: { action: "DeliverOrder", ordNumber: ordNumber },
        error: function(xmlhttp) {
            alert("加载发货单" + ordNumber + "出错，请和管理员联系");
        },
        success: function(data, status) {
            if (data._error == true) {
                alert("加载发货单" + ordNumber + "出错：" + data._error_msg);
                return;
            }
            $("#lblSDONumber").text(data.orderNumber);  //显示发货单信息
            $("#lblSONumber").text(data.saleOrder);
            $("#lblMemberName").text(data.member);
            $("#lblContact").text(data.contact);
            $("#lblMobile").text(data.mobile);
            $("#lblPhone").text(data.phone);
            $("#lblAddr").text(data.district);
            $("#lblPackageType").text(data.packageType);
            $("#lblNote").text(data.note);
            $("#lblDeliverType").text(data.deliverType);
            $("#lblPaymentMethod").text(data.paymentMethod);
            $("#lblPackageUser").text(data.packageUser);

            $("#lblSNNumber").text(data.shippingNumber);
            $("#lblLogisName").text(data.logisticsName);

            $(".txt_SN_Loaded").val("1");
        } //success
    });  //$.ajax({
}
function snView_Clear() {
    if ($(".txt_SN_Loaded").val() != "1") return;
    
    $("#lblSDONumber").text(" ");
    $("#lblSONumber").text("");
    $("#lblMemberName").text("");
    $("#lblContact").text("");
    $("#lblMobile").text("");
    $("#lblPhone").text("");
    $("#lblAddr").text("");
    $("#lblPackageType").text("");
    $("#lblNote").text("");
    $("#lblDeliverType").text("");
    $("#lblPaymentMethod").text("");
    $("#lblSNNumber").text("");
    $("#lblLogisName").text("");
    $("#lblPackageUser").text("");

    $(".txt_SN_Loaded").val("0");
}
</script>
<input type="text" runat="server" id="txt_SNNumber" class="cls_txt_SNNumber" value="" style="display:none;" />
<input type="text" runat="server" id="txt_SN_Loaded" class="cls_txt_SN_Loaded" value="0" style="display:none;" />
<table id="div_sdo_head" class="infoArea" cellpadding="0" cellspacing="0">
    <tr>
        <td class="label" style="width:60px;">发货单&nbsp;</td>
        <td style="width:100px;"><label id="lblSDONumber"></label></td>
        <td class="label" style="width:60px;">订单&nbsp;</td>
        <td style="width:110px;"><label id="lblSONumber"></label></td>
        <td class="label" style="width:60px;">运单&nbsp;</td>
        <td style="width:200px;"><label id="lblSNNumber"></label></td>
        <td class="label" style="width:95px;">物流公司&nbsp;</td>
        <td><label id="lblLogisName"></label></td>
    </tr>
    <tr>
        <td class="label">会员姓名&nbsp;</td>
        <td><label id="lblMemberName"></label></td>
        <td class="label">收货人&nbsp;</td>
        <td><label id="lblContact"></label></td>
        <td class="label">电话&nbsp;</td>
        <td><label id="lblMobile"></label>&nbsp;<label id="lblPhone"></label></td>
        <td class="label" style="width:95px;">省,&nbsp;市&nbsp;&nbsp;&nbsp;区,&nbsp;县&nbsp;</td>
        <td><label id="lblAddr"></label></td>
    </tr>
    <tr>
        <td class="label">送货方式&nbsp;</td>
        <td><label id="lblDeliverType"></label></td>
        <td class="label">付款方式&nbsp;</td>
        <td><label id="lblPaymentMethod"></label></td>
        <td class="label">包装方式&nbsp;</td>
        <td><label id="lblPackageType"></label></td>
        <td class="label">核货人&nbsp;</td>
        <td><label id="lblPackageUser"></label></td>
    </tr>
    <tr>
        <td class="label">备注&nbsp;</td>
        <td colspan="7"><label id="lblNote"></label></td>
    </tr>
</table>