<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function priorStep() {
	document.forms[0].action = "orderAddSecond.do?type=refresh";
	document.forms[0].submit();
}

function submitForm() {
	if(document.forms[0].receiptorAddressId.value == 0) {
		alert("请选择送货地址！");
		return;
	}
	if(document.forms[0].deliveryTypeId.value == 0) {
		alert("请选择送货方式！");
		return;
	}
	if(document.forms[0].paymentTypeId.value == 0) {
		alert("请选择付款方式！");
		return;
	}
	document.forms[0].actionType.value = "insertOrder";	
	document.forms[0].submit();
	document.forms[0].BtnSubmit.disabled = true;
}

function changeAddress(newValue) {
	if(newValue != null && trim(newValue) != "") {
		
		document.forms[0].action = "orderAddThird.do";
		document.forms[0].receiptorAddressId.value = newValue;
		document.forms[0].actionType.value = "changeAddress";
		document.forms[0].submit();
	}
}

function changeDelivery(newValue) {
	if(newValue != null && trim(newValue) != "" && document.forms[0].deliveryTypeId.value != newValue) {
		document.forms[0].action = "orderAddThird.do";
		document.forms[0].deliveryTypeId.value = newValue;
		document.forms[0].actionType.value = "changeDelivery";
		document.forms[0].submit();
	}
}

function changePayment(newValue) {
	if(newValue != null && trim(newValue) != "" && document.forms[0].paymentTypeId.value != newValue) {
		document.forms[0].action = "orderAddThird.do";
		document.forms[0].paymentTypeId.value = newValue;
		document.forms[0].actionType.value = "changePayment";
		document.forms[0].submit();
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- shopping cart -->
<bean:define id="cart" name="orderForm" property="cart" type="com.magic.crm.order.entity.ShoppingCart2"/>
<!-- member -->
<bean:define id="member" name="cart" property="member" type="com.magic.crm.member.entity.Member"/>
<!-- DeilveryInfo -->
<bean:define id="deliveryInfo" name="cart" property="deliveryInfo" type="com.magic.crm.order.entity.DeliveryInfo"/>
<bean:define id="otherInfo" name="cart" property="otherInfo" type="com.magic.crm.order.entity.OtherInfo"/>
<!-- <table width="98%" border=0 cellspacing=1 cellpadding=1 >
	<tr>
	<td> <span class="OraHeader"><b><font color="#838383"><b>当前位置：</b></font></b><font color="#838383">客户服务->>新增订单->>订单确认</font></span> 
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif">
			<tr background="../images/headerlinepixel_onwhite.gif">
			<td height="1" width=100% background="../images/headerlinepixel_onwhite.gif"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table> -->
<html:form action="/orderAddThird.do">
 <html:hidden property="prTypeId"/>
 <html:hidden property="msc"/>
 <input type="hidden" name="mbId" value="<bean:write name="member" property="ID" format="#"/>">
<!--<input type="hidden" name="mscCode" value="<bean:write name="member" property="MSC_CODE"/>">
<input type="hidden" name="levelId" value="<bean:write name="member" property="LEVEL_ID" format="#0"/>">
<input type="hidden" name="oldMember" value="<bean:write name="member" property="oldMember"/>"> -->

<!-- <table  align="center" width="95%" cellspacing="0" border="0">
	<tr>
		<td><b>会员基本信息</b></td>
	</tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <tr height="26"> 
    <td >会员号：</td>
    <td  bgcolor="#FFFFFF" ><a href="/member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a></td>
    <td >会员姓名：</td>
    <td  bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
    <td>会员帐户:</td>
    <td bgcolor="#FFFFFF"><bean:write name="member" property="DEPOSIT" format="#0.00"/></td>
    <td>冻结帐户:</td>
    <td bgcolor="#FFFFFF"><bean:write name="member" property="FORZEN_CREDIT" format="#0.00"/></td>
  </tr>
</table> -->

<table  align="center" width="95%" cellspacing="0" border="0">
  <tr> 
	<TD align="right"><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></TD>
  </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
  <tr class="OraTableRowHeader" noWrap  height="26">
		<td align="center"><b>货号</b></td>
		<td align="center"><b>名称</b></td>
		<td align="center"><b>销售方式</b></td>
		<td align="center"><b>库存状态</b></td>
		<td align="center"><b>颜色</b></td>
    <td align="center"><b>尺寸</b></td>
		<td align="center"><b>数量</b></td>
		<td align="center"><b>单位</b></td>
		<td align="center"><b>单价</b></td>
		<!--<td align="center"><b>到货天数</b></td>-->
		<td align="center"><b>合计</b></td>
	</tr>
	
    <bean:define id="items" name="cart" property="items" type="java.util.Collection"/> 
	
    <logic:iterate name="items" id="item">
	<tr>
		<td align="center">&nbsp;<bean:write name="item" property="itemCode"/>
		<logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
		</td>
		<td align="left">&nbsp;<bean:write name="item" property="itemName"/></td>
		<td align="left">&nbsp;<bean:write name="item" property="sellTypeName"/></td>
		<td align="left">&nbsp;<bean:write name="item" property="stockStatusName"/></td>
		<td align="left">&nbsp;<bean:write name="item" property="color_name"/>(<bean:write name="item" property="color_code"/>)</td>
        <td align="left">&nbsp;<bean:write name="item" property="size_code"/></td>
        <td align="right">&nbsp;<bean:write name="item" property="itemQty" format="#"/></td>
		<td align="left">&nbsp;<bean:write name="item" property="itemUnit"/></td>
		<td align="right">&nbsp;<bean:write name="item" property="itemPrice" format="#0.00"/></td>
		<!--<td align="left">&nbsp;<bean:write name="item" property="landDate"/></td>-->
		<td align="right">&nbsp;<bean:write name="item" property="itemMoney" format="#0.00"/></td>
	</tr>
    </logic:iterate>
    
    <bean:define id="gifts" name="cart" property="gifts2" type="java.util.Collection"/> 
    <logic:iterate name="gifts" id="gift">
	<tr >
		<td align="center">&nbsp;<bean:write name="gift" property="itemCode" format="#"/>
		<logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_code"/>)
        </logic:notEmpty>
		</td>
		<td align="left">&nbsp;<bean:write name="gift" property="itemName"/>
		      		
		</td>
		<td align="left">&nbsp;<bean:write name="gift" property="sellTypeName"/></td>
		<td align="left">&nbsp;<bean:write name="gift" property="stockStatusName"/></td>
		<td align="left">&nbsp;<bean:write name="gift" property="color_name"/>(<bean:write name="gift" property="color_code"/>)</td>
        <td align="left">&nbsp;<bean:write name="gift" property="size_code"/></td>
		<td align="right">&nbsp;<bean:write name="gift" property="itemQty" format="#"/></td>
		<td align="left">&nbsp;<bean:write name="gift" property="itemUnit"/></td>
		<td align="right">&nbsp;<bean:write name="gift" property="itemPrice" format="#0.00"/></td>
		<!--<td align="left">&nbsp;<bean:write name="gift" property="landDate"/></td>-->
		<td align="right">&nbsp;<bean:write name="gift" property="itemMoney" format="#0.00"/></td>
	</tr>
    </logic:iterate>
    
    <bean:define id="gifts" name="cart" property="gifts" type="java.util.Collection"/> 
    <logic:iterate name="gifts" id="gift">
	<tr >
		<td align="center">&nbsp;<bean:write name="gift" property="itemCode" format="#"/>
		<logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_code"/>)
        </logic:notEmpty>
		</td>
		<td align="left">&nbsp;<bean:write name="gift" property="itemName"/>
		      		
		</td>
		<td align="left">&nbsp;<bean:write name="gift" property="sellTypeName"/></td>
		<td align="left">&nbsp;<bean:write name="gift" property="stockStatusName"/></td>
		<td align="left">&nbsp;<bean:write name="gift" property="color_name"/>(<bean:write name="gift" property="color_code"/>)</td>
        <td align="left">&nbsp;<bean:write name="gift" property="size_code"/></td>
		<td align="right">&nbsp;<bean:write name="gift" property="itemQty" format="#"/></td>
		<td align="left">&nbsp;<bean:write name="gift" property="itemUnit"/></td>
		<td align="right">&nbsp;<bean:write name="gift" property="itemPrice" format="#0.00"/></td>
		<!--<td align="left">&nbsp;<bean:write name="gift" property="landDate"/></td>-->
		<td align="right">&nbsp;<bean:write name="gift" property="itemMoney" format="#0.00"/></td>
	</tr>
    </logic:iterate>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;购物金额：</td>
		<td align="right">&nbsp;<bean:write name="cart" property="totalMoney" format="#0.00"/></td>
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;发送费：</td>
		<td align="right">&nbsp;<bean:write name="deliveryInfo" property="deliveryFee" format="#0.00"/></td>
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;包装费：</td>
	    <td align="right" id="packageFee"><bean:write name="cart" property="packageFee" format="#0.00"/></td>
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;礼券抵用：</td>
		<td align="right">&nbsp;-<bean:write name="cart" property="ticketKill" format="#0.00"/></td>
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;付款方式折扣</td>
		<td align="right">&nbsp;-<bean:write name="cart" property="discount_fee" format="#0.00"/></td>
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;订单应付：</td>
		<td align="right" id="payable"><bean:write name="cart" property="payable" format="#0.00"/></td>
		<input type=hidden name="payable" value="<bean:write name="cart" property="payable"/>">
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right">&nbsp;帐户抵用：</td>
		<td align="right" id="orderUse"><bean:write name="cart" property="orderUse" format="#0.00"/></td>
		<input type=hidden name="usefulMoney" value="<bean:write name="cart" property="usefulMoney"/>">
	</tr>
	<tr>
		<td colspan="8">&nbsp;</td><td align="right"><b>&nbsp;总计欠款：</b></td>
		<td align="right" id="orderOwe"><bean:write name="cart" property="orderOwe" format="#0.00"/></td>
	</tr>	
	
</table>
<br>

<table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
	<tr>
		<td colspan="2">
			<img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
			<font color="#990000"><b>送货地址信息</b></font>
		</td>
		<td width="50%">
			<img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
			<font color="#990000"><b>送货方式</b></font>
		</td>
	</tr>
	<tr>
		<td width="75">&nbsp;&nbsp;姓名：</td>
		<td width="350">&nbsp;<bean:write name="deliveryInfo" property="receiptor" ignore="true"/></td>
		<td >
			&nbsp;&nbsp;<bean:write name="deliveryInfo" property="deliveryType" ignore="true"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;电话：</td>
		<td><bean:write name="deliveryInfo" property="phone" ignore="true"/>&nbsp;&nbsp;
		<bean:write name="deliveryInfo" property="phone2" ignore="true"/></td>
		<td><input name="chgpay" type="button" value="修改送货方式" onclick="openWin('../order/deliveryType.do?mbId=<bean:write name="orderForm" property="mbId" format="#"/>', '', 650, 500);">
	  <input type="hidden" name="deliveryTypeId" value="<bean:write name="deliveryInfo" property="deliveryTypeId" format="#"/>">
    </td>					
	</tr>
	<tr>
		<td>&nbsp;&nbsp;送货地址：</td>
		<td>[<bean:write name="deliveryInfo" property="sectionName" ignore="true"/>]<bean:write name="deliveryInfo" property="address" ignore="true"/></td>
		<td>
			<img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
			<font color="#990000"><b>付款方式</b></font>
		</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;邮编：</td>
		<td><bean:write name="deliveryInfo" property="postCode" ignore="true"/></td>
		<td>
			&nbsp;&nbsp;<bean:write name="deliveryInfo" property="paymentType" ignore="true"/>
			<logic:equal name="deliveryInfo" property="paymentType" value="货到付款">
                <html:radio property="use_deposit" value="1"/>使用余额
                <html:radio property="use_deposit" value="0"/>不使用余额
            </logic:equal>
            <logic:equal name="deliveryInfo" property="paymentType" value="仓库代收">
                <html:radio property="use_deposit" value="1"/>使用余额
                <html:radio property="use_deposit" value="0"/>不使用余额
            </logic:equal>
            <logic:equal name="deliveryInfo" property="paymentType" value="POS机付款">
                <html:radio property="use_deposit" value="1"/>使用余额
                <html:radio property="use_deposit" value="0"/>不使用余额
            </logic:equal>
		</td>
	</tr>
	<tr>
		<td colspan="2"><input name="chgaddress" type="button" value="修改送货地址" onclick="openWin('../order/deliveryInfoList.do?mbId=<bean:write name="member" property="ID" format="#"/>', '', 650, 500)">
      <input type="hidden" name="actionType" value="">
	  <input type="hidden" name="receiptorAddressId" value="<bean:write name="deliveryInfo" property="addressId" ignore="true" format="#"/>">
    </td>
		<td >
			<input name="chgpay" type="button" value="修改付款方式" onclick="openWin('../order/paymentType.do?mbId=<bean:write name="orderForm" property="mbId" format="#"/>', '', 650, 500);">
            <input type="hidden" name="paymentTypeId" value="<bean:write name="deliveryInfo" property="paymentTypeId" ignore="true" format="#"/>">
		</td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<!-- OtherInfo -->
<bean:define id="otherInfo" name="cart" property="otherInfo" type="com.magic.crm.order.entity.OtherInfo"/>
<table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
	<tr>
		<td width="50%">
			<b><font color="#990000">缺货商品处理方式</font></b>
		</td>
		<td width="50%">
			<b><font color="#990000">您需要发票吗？</font></b>
		</td>
	</tr>
	<tr>
		
    <td> <input type="radio" name="OOSPlan" value="2"<logic:equal name="otherInfo" property="OOSPlan" value="2"> 
      checked</logic:equal>> 全部商品到齐再发 </td>
		<td>
			<input type="radio" name="needInvoice" value="1" >要
			&nbsp;&nbsp;发票抬头：<input type="text" name="invoice_title" size="50" value="">
		</td>
	</tr>
	<tr>
		<td>
			<input type="radio" name="OOSPlan" value="3" <logic:equal name="otherInfo" property="OOSPlan" value="3"> checked</logic:equal> >
      不等待，取消订单中缺货商品
		</td>
		<td>
			<input type="radio" name="needInvoice" value="0" checked />不要
		</td>
	</tr>
	<tr>
		<td>可选包装
		</td>
		<td rowspan="3">
			<table>
				<tr>
					<td>						
						<input id="manualFreeFreight" type="checkbox" name="manualFreeFreight"
						<logic:equal name="orderForm" property="manualFreeFreight" value="true"> checked</logic:equal>
						/><label for="manualFreeFreight">免发送费</label>			
					</td>
				</tr>
				<tr>
					<td>
						<html:textarea name="orderForm" property="freeFreightReason" />						
					</td>
				</tr>
		</table>
	</td>
	</tr>
	<tr>
		<td>
			<table>
	<logic:iterate id="pack" name="orderForm" property="packages">
	<tr>
		<td>
		<input type="radio" name="package_type" value="<bean:write name="pack" property="id"/>"
		<logic:equal name="pack" property="id" value="1"> checked </logic:equal>
		onclick="f_changePackage(<bean:write name="pack" property="price"/>)"
		>
		<bean:write name="pack" property="name"/>&nbsp;
		<bean:write name="pack" property="price"/>&nbsp;
		<bean:write name="pack" property="description"/>
		</td>
	</tr>
	</logic:iterate>
</table>
</td>
</tr>
	<tr>
		<td colspan="2"><strong><font color="#990000">备注信息</font></strong><br>
			
      <textarea name="remark" cols="85" rows="3"><bean:write name="otherInfo" property="remark" ignore="true"/></textarea>
		</td>
	</tr>

</table>

<table width="95%" cellspacing="0" border="0"  >
	
	<tr><td height="10"></td></tr>
	<tr>
		<td align="center">
			
        <input name="BtnBack" type="button" value="&lt;&lt; 上一步" onclick="priorStep();">
		<input name="BtnSubmit" type="button" value="提交订单" onclick="submitForm();">
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
