<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function initFocus() {
	document.forms[0].itemCode.focus();
}
function check_f() {

	if(document.forms[0].itemCode.value==""){
		alert('货号不能为空!');
		document.forms[0].itemCode.focus();
		return false;
	}

	if(document.forms[0].money.value==""){
		alert('金额不能为空!');
		document.forms[0].money.focus();
		return false;
	}
	if(isNaN(document.forms[0].money.value)){
		alert('金额必须为数字!');
		document.forms[0].money.focus();
		return false;
	}

	if(document.forms[0].price.value==""){
		alert('兑换价格不能为空!');
		document.forms[0].price.focus();
		return false;
	}
	if(isNaN(document.forms[0].price.value)){
		alert('兑换价格必须为数字!');
		document.forms[0].price.focus();
		return false;
	}

	
}
function getOpenwinValue(ret){
	document.forms[0].itemCode.value = ret;
	if(document.forms[0].itemCode.value.length >6) {
		document.forms[0].itemCode.value =document.forms[0].itemCode.value.substring(0,6);
	}
}
function back_f() {
	document.forms[0].action = "/member/memberAddMoneyGiftSetup.do?type=query";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="initFocus()">
<html:form method="post" action="/memberAddMoneyGiftSetup.do?type=modify" onsubmit="return check_f();">
<table width="750.0" border=0 cellspacing=1 cellpadding=5>
	<tr>
	<td> <span class="OraHeader"><b>帐户管理 -&gt; 会员充值礼品设置修改</b></span>
		
	</td>
	</tr>
</table>

<table  border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;货号</td>
		<td align="left">
			<html:hidden property="id" />
			<html:hidden property="itemID" />
			<html:text property="itemCode" size="10"/>&nbsp;
			<input name="query" type="button" value="查询" onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);">
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;设置金额</td>
		<td  align="left" >
			<html:text property="money" size="10"/>
		</td>
	</tr>
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;兑换价格</td>
		<td align="left">
			
			<html:text property="price" size="10" />&nbsp;
			
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;保留天数</td>
		<td  align="left" >
			<bean:write name="memberAddMoneyGiftSetupForm" property="keepDays" />
		</td>
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
<tr>
	
		<td align="center">
		<logic:equal name="memberAddMoneyGiftSetupForm" property="status" value="0">
		<input type="submit" name="addBtn" value=" 保 存 " >&nbsp;&nbsp;
		</logic:equal>
		<input type="button" value=" 返 回 " onclick="back_f()"></td>
		
	</tr>
</table>
</html:form>

</body>
</html>
