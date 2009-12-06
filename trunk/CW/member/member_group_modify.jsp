<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function initFocus() {
	document.forms[0].compayName.focus();
}
function check_f() {

	if(document.forms[0].compayName.value==""){
		alert('公司名称不能为空!');
		document.forms[0].compayName.focus();
		return false;
	}

	if(document.forms[0].telephone.value==""){
		alert('联系电话不能为空!');
		document.forms[0].telephone.focus();
		return false;
	}
	if(document.forms[0].telephone2.value==""){
		alert('联系电话二不能为空!');
		document.forms[0].telephone2.focus();
		return false;
	}

	if(document.forms[0].postcode.value==""||document.forms[0].postcode.value.length!=6||isNaN(document.forms[0].postcode.value)){
		alert('邮编不能为空并且长度为6位数字!');
		document.forms[0].postcode.focus();
		return false;
	}
	if(document.forms[0].relationPerson.value==""){
		alert('联系人不能为空!');
		document.forms[0].relationPerson.focus();
		return false;
	}
	if(document.forms[0].address.value==""){
		alert('会员地址不能为空!');
		document.forms[0].address.focus();
		return false;
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="initFocus()">
<html:form method="post" action="/memberGroup.do?type=modify" onsubmit="return check_f();">
<!-- 记录ID -->
<html:hidden property="ID"/>
<input type="hidden" value=<bean:write name="memberGroupForm" property="ID"/>>
<table width="750.0" border=0 cellspacing=1 cellpadding=5>
	<tr>
	<td> <span class="OraHeader"><b>会员管理->>修改团体会员</b></span>
		
	</td>
	</tr>
</table>

<table  border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;团体会员号码</td>
		<td align="left">
			<html:text property="groupCode" readonly="true"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;公司名称</td>
		<td  align="left" >
			<html:text property="compayName"/>
		</td>
	</tr>
	
	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;联系电话</td>
		<td  align="left" >
			<html:text property="telephone"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;联系电话二</td>
		<td  align="left" >
			<html:text property="telephone2"/>
		</td>
	</tr>
	

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;邮编</td>
		<td align="left" >
			<html:text property="postcode"/>
		</td>
		<!-- <td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;联系人</td>
		<td align="left">
			<html:text property="relationPerson"/>
		</td> -->
	</tr>
	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;会员地址</td>
		<td align="left" colspan="3">
			<html:textarea property="address" cols="65"/>
		</td>
	</tr>
	
	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="submit" class="button2" value=" 确定 ">&nbsp;
		<input type="button" class="button2" value=" 返回 " onclick="window.history.back()">
	</tr>
</table>
</html:form>

</body>
</html>
