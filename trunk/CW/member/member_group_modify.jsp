<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function initFocus() {
	document.forms[0].compayName.focus();
}
function check_f() {

	if(document.forms[0].compayName.value==""){
		alert('��˾���Ʋ���Ϊ��!');
		document.forms[0].compayName.focus();
		return false;
	}

	if(document.forms[0].telephone.value==""){
		alert('��ϵ�绰����Ϊ��!');
		document.forms[0].telephone.focus();
		return false;
	}
	if(document.forms[0].telephone2.value==""){
		alert('��ϵ�绰������Ϊ��!');
		document.forms[0].telephone2.focus();
		return false;
	}

	if(document.forms[0].postcode.value==""||document.forms[0].postcode.value.length!=6||isNaN(document.forms[0].postcode.value)){
		alert('�ʱ಻��Ϊ�ղ��ҳ���Ϊ6λ����!');
		document.forms[0].postcode.focus();
		return false;
	}
	if(document.forms[0].relationPerson.value==""){
		alert('��ϵ�˲���Ϊ��!');
		document.forms[0].relationPerson.focus();
		return false;
	}
	if(document.forms[0].address.value==""){
		alert('��Ա��ַ����Ϊ��!');
		document.forms[0].address.focus();
		return false;
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="initFocus()">
<html:form method="post" action="/memberGroup.do?type=modify" onsubmit="return check_f();">
<!-- ��¼ID -->
<html:hidden property="ID"/>
<input type="hidden" value=<bean:write name="memberGroupForm" property="ID"/>>
<table width="750.0" border=0 cellspacing=1 cellpadding=5>
	<tr>
	<td> <span class="OraHeader"><b>��Ա����->>�޸������Ա</b></span>
		
	</td>
	</tr>
</table>

<table  border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�����Ա����</td>
		<td align="left">
			<html:text property="groupCode" readonly="true"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��˾����</td>
		<td  align="left" >
			<html:text property="compayName"/>
		</td>
	</tr>
	
	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��ϵ�绰</td>
		<td  align="left" >
			<html:text property="telephone"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��ϵ�绰��</td>
		<td  align="left" >
			<html:text property="telephone2"/>
		</td>
	</tr>
	

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�ʱ�</td>
		<td align="left" >
			<html:text property="postcode"/>
		</td>
		<!-- <td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��ϵ��</td>
		<td align="left">
			<html:text property="relationPerson"/>
		</td> -->
	</tr>
	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ա��ַ</td>
		<td align="left" colspan="3">
			<html:textarea property="address" cols="65"/>
		</td>
	</tr>
	
	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="submit" class="button2" value=" ȷ�� ">&nbsp;
		<input type="button" class="button2" value=" ���� " onclick="window.history.back()">
	</tr>
</table>
</html:form>

</body>
</html>
