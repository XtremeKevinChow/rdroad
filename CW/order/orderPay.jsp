<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function resetReason() {
	var frm = document.forms[0];
	frm.reason.value = "0";
}

function init() {
	var frm = document.forms[0];
	if (frm.paymentTypeId.value == "10")
	{
		frm.reason.disabled = false;
	} else {
		resetReason();
		frm.reason.disabled = true;
	}
	
}

function changeReason(payMethod) {
	var frm = document.forms[0];
	if (payMethod == "10") //邮政汇款
	{
		frm.reason.disabled = false;
	} else {
		resetReason();
		frm.reason.disabled = true;
	}
}
function checkmoney() {
    if(parseInt(document.orderForm.mbMoney.value)<0){
        document.orderForm.auto_Level[1].checked=true;
        document.orderForm.auto_Level[1].disabled=true;
        document.orderForm.auto_Level[0].disabled=true;
        document.orderForm.auto_Increase[1].checked=true;
        document.orderForm.auto_Increase[1].disabled=true;
        document.orderForm.auto_Increase[0].disabled=true;
        document.orderForm.auto_Gift[1].checked=true;
        document.orderForm.auto_Gift[1].disabled=true;
        document.orderForm.auto_Gift[0].disabled=true;
    }else{
        document.orderForm.auto_Level[1].disabled=false;
        document.orderForm.auto_Level[0].disabled=false;
        document.orderForm.auto_Increase[1].disabled=false;
        document.orderForm.auto_Increase[0].disabled=false;
        document.orderForm.auto_Gift[1].disabled=false;
        document.orderForm.auto_Gift[0].disabled=false;
    }
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init()">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">手工充值</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form action="orderPay.do?type=submitPay" onsubmit="return f_checkForm();">
<table width="750.0" border=0 cellspacing=1 cellpadding=5>

	<tr>
      <td width="30%" align="right" >&nbsp;订单号</td>
			<td align="left" width="40%" nowarp>
        <html:text property="orderNumber"/>
	</td>

	</tr>
	<tr>
      <td width="30%" align="right" >&nbsp;会员号</td>
			<td align="left" width="40%" nowarp>
        <html:text property="cardId"/>
	</td>

	</tr>	
		<tr>
      <td width="30%" align="right" ><font color=red>*</font>&nbsp;金额</td>
      <td align="left" width="40%" nowarp>
			  <html:text property="mbMoney" />
			  
      </td>
			<td width="30%" class=OraTipText align="left"></td>
		</tr>
    <tr>
      <td width="30%" align="right" ><font color=red>*</font>&nbsp;支付方式</td>
      <td align="left" width="40%" nowarp>
        <html:select property="paymentTypeId" onchange="changeReason(this.value);">
         <option value="-1">请选择</option>
         <html:optionsCollection property="payments" value="id" label="name"/> 
        </html:select>
      </td>

    </tr>
   <tr>
      <td width="30%" align="right" >&nbsp;原因：</td>
      <td align="left" width="40%" nowarp>
		<html:select property="reason">
		<html:optionsCollection name="orderForm" property="reasonColl" value="code" label="name" /> 
		</html:select>&nbsp;
	
	</td>
    </tr>
<tr>
	<td width="30%" align="right" >&nbsp;是否升级</td>
	<td align="left" width="40%" nowarp>
	<html:radio property="auto_Level"  name="orderForm" value="1" />是
	<html:radio property="auto_Level" name="orderForm" value="0" />否
	</td>
</tr>  
<!--
<tr>
	<td width="30%" align="right" >&nbsp;是否送礼金</td>
	<td align="left" width="40%" nowarp>
	<html:radio property="auto_Increase" name="orderForm" value="1" />是
	<html:radio property="auto_Increase" name="orderForm" value="0" />否
	</td>
</tr>  
<tr>
	<td width="30%" align="right" >&nbsp;是否送礼品</td>
	<td align="left" width="40%" nowarp>
	<html:radio property="auto_Gift" name="orderForm" value="1" />是
	<html:radio property="auto_Gift" name="orderForm" value="0" />否
	</td>
</tr> 
-->
   	<input type="hidden" name="auto_Increase" value="0">
   	<input type="hidden" name="auto_Gift" value="0">
<tr>
      <td width="30%" align="right" ><font color=red>*</font>&nbsp;日期</td>
      <td align="left" width="40%" nowarp>
        <html:text property="createDate" />
			(格式:YYYY-MM-DD)
      </td>
			<td width="30%" class=OraTipText align="left"></td>
	</tr>
    <tr>
      <td width="30%" align="right" >&nbsp;备注</td>
      <td align="left" width="40%" nowarp>
        <html:textarea property="remark" cols="30" rows="5" />
      </td>
			<td width="30%" class=OraTipText align="left"></td>
		</tr>
    <tr>
		  <td colspan="3">
        <table width="750.0" border="0" cellspacing="0" cellpadding="0" background="/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <tr><td></td></tr>
				</table>
			</td>
		</tr>
    <tr>
      <td align="center" colspan="3">
        <input type="submit" class="button2" value="提交" >&nbsp;
        <!--<input type="button" class="button2" value="取消" onClick="history.back();">-->
			</td>
    </tr>
  </table>
	
</html:form>
</body>
</html>
<script lanuage=javascript>
function f_checkForm() {
	

	
	if( isNaN(document.forms[0].mbMoney.value)) {
		alert("金额必须为数字");
		return false;
	}
	if( parseDouble(document.forms[0].mbMoney.value)==0) {
		alert("金额必须是不为0数字");
		return false;
	}	
	if( document.forms[0].paymentTypeId.value == -1) {
		alert("支付方式必须选择");
		return false;
	}
	
	if( !f_checkDate()) {
		alert("事件日期格式不正确");
		return false;
	}
	
	/*if( !f_checkRemark()) {
		alert("备注中有不支持的字符");
		return false;
	}*/
	
	return true;
}
function f_checkOrderNumber() {
	var regex = /^\w?\d{12}$/;
	return regex.exec(document.forms[0].orderNumber.value);
}
function f_checkMbMoney() {
	var regex = /^\d{1,6}(\.\d{1,2})?$/;
	return regex.exec(document.forms[0].mbMoney.value);
}
function f_checkDate() {
	var regex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
	return regex.exec(document.forms[0].createDate.value);
}
function f_checkRemark() {
	var regex = /^[\u4e00-\u9fa5,a-z,A-Z,\,0-9\.\，\。\,]*$/
	return regex.exec(document.forms[0].remark.value);
}

</script>