<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function initFocus() {
	//document.forms[0].itemCode.focus();
}
function check_f() {

	/*if(document.forms[0].itemCode.value==""){
		alert('���Ų���Ϊ��!');
		document.forms[0].itemCode.focus();
		return false;
	}
	
	if(isNaN(document.forms[0].itemCode.value)){
		alert('���ű���Ϊ����!');
		document.forms[0].itemCode.focus();
		return false;
	}*/
	if(document.forms[0].money.value==""){
		alert('����Ϊ��!');
		document.forms[0].money.focus();
		return false;
	}
	if(isNaN(document.forms[0].money.value)){
		alert('������Ϊ����!');
		document.forms[0].money.focus();
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
<html:form method="post" action="/memberAddMoneyGiftSetup.do?type=add" onsubmit="return check_f();">
<table width="750.0" border=0 cellspacing=1 cellpadding=5>
	<tr>
	<td> <span class="OraHeader"><b>�ʻ����� -&gt; ��ԱԤ��������ȯ����</b></span>
		
	</td>
	</tr>
</table>

<table  border=0 align="center" cellspacing=1 cellpadding=1  width="95%" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��ȯ����</td>
		<td align="left">
			<html:select property="gift_number">
				<option value="">��ѡ��...</option>
        <html:optionsCollection   property="gifts" />
			</html:select>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;���ý��</td>
		<td  align="left" >
			<html:text property="money" size="10"/>
		</td>
	</tr>
	<html:hidden property="price" />&nbsp;
	</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��������</td>
		<td  align="left" >
			<html:text property="keepDays"  size="10"/>
			
		</td>
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
<tr>
		<td align="center"><input type="submit" name="addBtn" value=" �� �� " >&nbsp;&nbsp;
		<input type="button" value=" �� �� " onclick="back_f()"></td>
		
	</tr>
</table>
</html:form>

</body>
</html>
