<%@ page contentType="text/html;charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function validate(theForm) {
	if(trim(theForm.oldCardId.value) == '') {
		alert("请输入老卡号！");
		theForm.oldCardId.focus();
		return;
	}
	if(trim(theForm.newCardId.value) == '') {
		alert("请输入新卡号！");
		theForm.newCardId.focus();
		return;
	}
	document.forms[0].BtnUpdate.disabled = "true";
	document.forms[0].submit();
	
}

function setCardId(pValue) {
	document.forms[0].oldCardId.value = pValue;
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000"  onload="javascript:document.forms[0].oldCardId.focus();">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">更改会员卡号</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" action="/member/member_change_card_code_ok.jsp">


<table width="500" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr height="22">
		<td width="100">旧卡号：</td>
		<td width="400" bgcolor="#FFFFFF">
		<input type="text" maxlength="8" name="oldCardId" maxlength="9" size="12">&nbsp;&nbsp;8位数字</td>
	</tr>
	<tr height="22">
		<td width="100">新卡号：</td>
		<td width="400" bgcolor="#FFFFFF">
		<input type="text" maxlength="8" name="newCardId" maxlength="9" size="12">&nbsp;&nbsp;8位数字</td>
	</tr>
	
</table>
<table width="500" align=center>
	<tr align=center>
		<td><input name="BtnUpdate" value=" 更改 " type="button" onclick="validate(document.forms[0]);"></td>
	</tr>
</table>
<br>
<!--
<table width="100%" border="0"  cellpadding="0" cellspacing="0" align="center">
  	<tr>
		<td><font color="#990000"><b>基本信息</b></font></td>
		<TD width="30" align="left"></td>
	</tr>
</table>

 <table width="100%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr height="22">
		<td width="80">会员号：</td>
		<td  width=90 bgcolor="#FFFFFF">&nbsp;</td>

		<td width="80">姓名：</td>
		<td width=90 bgcolor="#FFFFFF">&nbsp;</td>

		<td width="80">出生日期：</td>
		<td  width=90 bgcolor="#FFFFFF">&nbsp;</td>

		<td>性别：</td>
		<td  width=90 bgcolor="#FFFFFF">&nbsp;</td>
	</tr>
</table> -->
</form>

</body>
</html>
