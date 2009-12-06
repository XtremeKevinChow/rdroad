<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
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
		<td><B>生成多少张</B></td>
	</td>
	<tr><td>1张<input type="radio" name="type" value="1"  onclick="checkType(this)">&nbsp;&nbsp;
	多张<input type="radio" name="type" value="2" onclick="checkType(this)"></td></tr>

	<tr id="gift" style="display:none">
	<td>
		<TABLE >
		<tr >
			<td>请输入礼券号：<input type="text" name="giftNo"></td>
		</tr>
		<TR>
			<TD>设置密码<input type="checkbox" name="needPwd" onclick="checkPWD(this);">&nbsp;<input type="text" name="password" disabled></TD>
		</TR>
		</TABLE>
	</td>
    </tr>
	
	<tr id="number" style="display:none">
	<td>
	<TABLE >
	<tr >
		<td>请输入生成张数：<input type="text" name="number" size="8">&nbsp;张</td>
	</tr>
	<TR>
		<TD>需要生成密码<input type="checkbox" name="needPwdBat" ></TD>
		</TR>
	</TABLE>
	</td>
	</tr>

	<tr>
		<td align=""><input type="button" name="nextBtn" value="<<上一步">&nbsp;&nbsp;
		<input type="button" name="nextBtn" value="下一步>>"></td>
		</td>
	</tr>
</table>

</form>
</body>
</html>
