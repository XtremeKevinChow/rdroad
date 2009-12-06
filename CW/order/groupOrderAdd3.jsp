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
	document.forms[0].action = "groupOrderAdd.do?type=refresh";
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
}

function changeAddress(newValue) {
	if(newValue != null && trim(newValue) != "") {
		document.forms[0].action = "groupOrderAdd.do?type=changeAddress";
		document.forms[0].receiptorAddressId.value = newValue;
		document.forms[0].actionType.value = "changeAddress";
		document.forms[0].submit();
	}
}

function changeDelivery(newValue) {
	if(newValue != null && trim(newValue) != "" && document.forms[0].deliveryTypeId.value != newValue) {
		document.forms[0].action = "groupOrderAdd.do?type=changeDelivery";
		document.forms[0].deliveryTypeId.value = newValue;
		document.forms[0].actionType.value = "changeDelivery";
		document.forms[0].submit();
	}
}

function changePayment(newValue) {
	if(newValue != null && trim(newValue) != "" && document.forms[0].paymentTypeId.value != newValue) {
		document.forms[0].action = "groupOrderAdd.do?type=changePayment";
		document.forms[0].paymentTypeId.value = newValue;
		document.forms[0].actionType.value = "changePayment";
		document.forms[0].submit();
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<bean:define id="orgCart" name="orderForm" property="orgCart"/>
<bean:define id="orgMember" name="orgCart" property="orgMember"/>
<bean:define id="orgOrder" name="orgCart" property="orgOrder"/>
<bean:define id="deliveryInfo" name="orgCart" property="deliveryInfo"/>
<bean:define id="otherInfo" name="orgCart" property="otherInfo"/>


<table width="100%" border="0" cellspacing="0" cellpadding="0" onload="document.forms[0].btnsearch.disabled;">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 新增团体订单</font></td>
  </tr>
</table>

<html:form action="/groupOrderAdd.do?type=addSubmit">
<!-- 折扣 -->
<html:hidden property="discount"/>
<!-- 市场销售销售 whichStock = sales -->
<input name="whichStock" type="hidden" value="<%= request.getAttribute("whichStock") %>">
<!-- <table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td><b>订单来源：</b><bean:write name="orderForm" property="prTypeName"/></td>
  </tr>
</table>
 -->
<table  align="center" width="95%" cellspacing="0" border="0">
	<tr>
		<td><b>会员基本信息</b></td>
	</tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <tr height="26"> 
    <td >会员号：</td>
    <td  bgcolor="#FFFFFF" >
	<bean:write name="orgMember" property="CARD_ID" />
	<input type="hidden" name="mbId" value="<bean:write name="orgMember" property="ID" format="#0"/>"></td>
    <td >会员姓名：</td>
    <td  bgcolor="#FFFFFF"><bean:write name="orgMember" property="NAME"/></td>
    
  </tr>
</table>

<br>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
  <tr class="OraTableRowHeader" noWrap  height="26">
		<td align="center"><b>货号</b></td>
		<td align="center"><b>名称</b></td>
		<td align="center"><b>库存数</b></td>
		<td align="center"><b>销售方式</b></td>
		<td align="center"><b>库存状态</b></td>
		<td align="center"><b>数量</b></td>
		<td align="center"><b>单位</b></td>
		<td align="center"><b>成本价</b></td>
		<td align="center"><b>市场价</b></td>
		<td align="center"><b>会员价</b></td>
		<td align="center"><b>VIP价</b></td>
		<td align="center"><b>团购价</b></td>
		<!-- <td align="center"><b>到货日期</b></td> -->
		<td align="center"><b>合计</b></td>
	</tr>
    <bean:define id="items" name="orgCart" property="items" type="java.util.List"/>  
    <logic:iterate name="items" id="item">
	<tr >
		<td align="center">&nbsp;<bean:write name="item" property="itemCode"/>
		<logic:notEmpty name="item" property="set_code">
            (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
		</td>
		<td align="center">&nbsp;<bean:write name="item" property="itemName"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="availQty" format="#"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="sellTypeName"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="stockStatusName"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="itemQty" format="#"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="itemUnit"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="purchaseingCost" format="#0.00"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="standardPrice" format="#0.00"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="silverPrice" format="#0.00"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="goldenPrice" format="#0.00"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="discountPrice" format="#0.00"/></td>
		<!-- <td align="center">&nbsp;<bean:write name="item" property="landDate"/></td> -->
		<td align="center">&nbsp;<bean:write name="item" property="groupItemMomey" format="#0.00"/></td>
	</tr>
    </logic:iterate>
    <!-- <bean:define id="gifts" name="orderForm" property="gifts" type="java.util.Collection"/> 
    <logic:iterate name="gifts" id="gift">
	<tr >
		<td align="center">&nbsp;<bean:write name="gift" property="itemId" format="#"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="itemName"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="sellTypeName"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="stockStatusName"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="itemQty" format="#"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="itemUnit"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="itemPrice" format="#0.00"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="landDate"/></td>
		<td align="center">&nbsp;<bean:write name="gift" property="itemMoney" format="#0.00"/></td>
	</tr>
    </logic:iterate> -->
	<tr>
	<td colspan="9">购物金额：</td>
		
    <td colspan="3">参考总额:<bean:write name="orgCart" property="groupTotalMoney" format="#0.00" ignore="true"/></td>
    <td  id="totalMoney">&nbsp;
	实际总额:<bean:write name="orgCart" property="totalMoney" format="#0.00" ignore="true"/>
	</td>
	</tr>
</table>
<br>
<table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
	<tr>
		<td colspan="2">
			<img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
			<font color="#990000"><b>送货地址信息</b></font>
		</td>
		<td width="180">
			<img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
			<font color="#990000"><b>送货方式</b></font>
		</td>
	</tr>
	<tr>
		<td width="75">&nbsp;&nbsp;姓名：</td>
		<td width="350">&nbsp;
		<bean:write name="deliveryInfo" property="receiptor" ignore="true"/></td>
		<td width="180">
			&nbsp;&nbsp;<bean:write name="deliveryInfo" property="deliveryType" ignore="true"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;电话：</td>
		<td><bean:write name="deliveryInfo" property="phone" ignore="true"/></td>
		<td><input name="chgpay" type="button" value="修改送货方式" onclick="openWin('deliveryType.do?mbId=<bean:write name="orderForm" property="mbId" format="#"/>', '', 650, 500);">
	  <input type="hidden" name="deliveryTypeId" value="<bean:write name="deliveryInfo" property="deliveryTypeId" format="#0"/>">
    </td>					
	</tr>
	<tr>
		<td>&nbsp;&nbsp;送货地址：</td>
		<td><bean:write name="deliveryInfo" property="address" ignore="true"/></td>
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
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<!--
		<input name="chgaddress" type="button" value="修改送货地址" onclick="openWin('deliveryInfoList.do?is_org=true&mbId=<bean:write name="orderForm" property="mbId" format="#"/>', '', 650, 500)">
        -->
      <input type="hidden" name="actionType" value="">
	  <input type="hidden" name="receiptorAddressId" value="<bean:write name="deliveryInfo" property="addressId" ignore="true" format="#0"/>">
    </td>
		<td width="180">
		    
			<input name="chgpay" type="button" value="修改付款方式" onclick="openWin('paymentType.do?is_org=true&mbId=<bean:write name="orderForm" property="mbId" format="#"/>', '', 650, 500);">
		    <input type="hidden" name="paymentTypeId" value="<bean:write name="deliveryInfo" property="paymentTypeId" format="#0"/>"> 
		</td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
	<tr>
		<td width="50%">
			<b><font color="#990000">是否需要发票吗？</font></b>
		</td>
	</tr>
	<tr>
		<td>
			<input type="radio" name="needInvoice" value="1"<logic:equal name="otherInfo" property="needInvoice" value="1"> checked</logic:equal>>要
		</td>
	</tr>
	<tr>
		
		<td>
			<input type="radio" name="needInvoice" value="0"<logic:equal name="otherInfo" property="needInvoice" value="0"> checked</logic:equal>>不要
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
