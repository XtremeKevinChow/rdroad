<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<SCRIPT LANGUAGE="JavaScript">
function checkType(obj) {
	if (obj.value == "1")
	{
		if (obj.checked)
		{
			document.getElementById("gift").style.display = "block";
			document.getElementById("number").style.display = "none";
		}
	}

	if (obj.value == "2")
	{
		if (obj.checked)
		{
			document.getElementById("number").style.display = "block";
			document.getElementById("gift").style.display = "none";
		}
	}
}

function checkPWD(obj) {
	
	if (obj.checked)
	{
		document.forms[0].password.disabled = false;
	} else {
		document.forms[0].password.value = "";
		document.forms[0].password.disabled = true;
	}
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table align="center" width="300" border="1">
    <tr>
		<td><B>���ɶ�����</B></td>
	</td>
	<tr><td>1��<input type="radio" name="type" value="1"  onclick="checkType(this)">&nbsp;&nbsp;
	����<input type="radio" name="type" value="2" onclick="checkType(this)"></td></tr>

	<tr id="gift" style="display:none">
	<td>
		<TABLE >
		<tr >
			<td>��������ȯ�ţ�<input type="text" name="giftNo"></td>
		</tr>
		<TR>
			<TD>��������<input type="checkbox" name="needPwd" onclick="checkPWD(this);">&nbsp;<input type="text" name="password" disabled></TD>
		</TR>
		</TABLE>
	</td>
    </tr>
	
	<tr id="number" style="display:none">
	<td>
	<TABLE >
	<tr >
		<td>����������������<input type="text" name="number" size="8">&nbsp;��</td>
	</tr>
	<TR>
		<TD>��Ҫ��������<input type="checkbox" name="needPwdBat" ></TD>
		</TR>
	</TABLE>
	</td>
	</tr>

	<tr>
		<td align=""><input type="button" name="nextBtn" value="<<��һ��">&nbsp;&nbsp;
		<input type="button" name="nextBtn" value="��һ��>>"></td>
		</td>
	</tr>
</table>

</form>
</body>
</html>
