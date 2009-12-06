<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function submit_f() {
	if (document.forms[0].year == null || document.forms[0].year.value == "0" || document.forms[0].year.value == "")
	{
		alert("会计年不能为空");
		document.forms[0].year.focus();
		return;
	}
	if (isNaN(document.forms[0].year.value))
	{
		alert("会计年必须为数字");
		document.forms[0].year.focus();
		return;
	}
	if (document.forms[0].month == null || document.forms[0].month.value == "0" || document.forms[0].month.value == "")
	{
		alert("会计月不能为空");
		document.forms[0].month.focus();
		return;
	}
	if (isNaN(document.forms[0].month.value))
	{
		alert("会计月必须为数字");
		document.forms[0].month.focus();
		return;
	}
	if(!f_checkBeginDate()) {
		alert("开始日期输入不正确");
		document.forms[0].beginDate.focus();
		return false;
	}	
	if(!f_checkEndDate()) {
		alert("结束日期不正确");
		document.forms[0].endDate.focus();
		return false;
	}
	document.forms[0].submit();
}

function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].endDate.value);
}
function f_checkBeginDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].beginDate.value);
}

function init_f() {
	
	document.forms[0].isUsed[0].checked = "true";
	zeroz2Space(document.forms[0].year);
	zeroz2Space(document.forms[0].month);
	document.forms[0].year.focus();
}

function zeroz2Space(element) {
	if (element.value == "0")
	{
		element.value = "";
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_f();">
<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> 
		<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增会计期</font><font color="838383">
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width=100% background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table>

<html:form action="/period?type=add" method="post">
<table  border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;会计年</td>
		<td width="*%" align="left">
			<html:text property="year"/>&nbsp;(如：2006)
		</td>

		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;会计月</td>
		<td width="*%" align="left" colspan="3">
			<html:text property="month"/>&nbsp;(如：12)
		</td>
	</tr>
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;开始日期</td>
		<td  align="left" >
			<html:text property="beginDate"/>
			<a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;结束日期</td>
		<td  align="left" >
			<html:text property="endDate"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;是否启用</td>
		<td  align="left" >
			<html:radio property="isUsed" value="0" />否&nbsp;&nbsp;
			<html:radio property="isUsed" value="1"/>是
		</td>
	</tr>
	
	<tr height="40">
		<td align="center" colspan=4>
		<input type="button" class="button2" value=" 确定 " onclick="submit_f()">&nbsp;
		<input type="button" class="button2" value=" 返回 " onclick="history.go(-1)">
	</tr>
</table>
</html:form>

</body>
</html>
