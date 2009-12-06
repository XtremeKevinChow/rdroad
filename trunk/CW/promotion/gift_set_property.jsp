<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<SCRIPT LANGUAGE="JavaScript">
function check_type(obj) {
	if (obj.value == "N" || obj.value == "A")
	{
		if (obj.checked)
		{
			document.getElementById("nMember").style.display = "block";
		} else {
			document.getElementById("nMember").style.display = "none";
		}
	} else { // old member can use
		
		document.getElementById("nMember").style.display = "none";
		
		document.forms[0].regStartDate.value = "";
		document.forms[0].regEndDate.value = "";
	}
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table align="center" width="600" border="1">
	<tr>
		<td colspan="2"><B>设置礼券属性</B></td>
	</tr>
	<tr>
		<td>礼券有效日期：</td>
		<td>从&nbsp;<input type="text" name="startDate" size="10">&nbsp;到&nbsp;<input type="text" name="endDate" size="10"></td>
	</tr>
	<tr>
		<td>礼券默认金额：</td><td><input type="text" name="giftMoney">&nbsp;元</td>
	</tr>
	<tr>
		<td>使用礼券的最低定单金额：</td><td><input type="text" name="personNum">&nbsp;元</td>
	</tr>
	<tr>
		<td>个人最多使用次数：</td><td><input type="text" name="personNum">&nbsp;次</td>
	</tr>
	<tr>
		<td>总共可使用次数：</td><td><input type="text" name="totalNum">&nbsp;次</td>
	</tr>
	<!-- N-new;O-old;A-all -->
	<tr>
		<td colspan="2">
			只适合网站使用<input type="radio" value="NET" name="useWhere">&nbsp;&nbsp;&nbsp;
			只适合网下使用<input type="radio" value="CRM" name="useWhere">&nbsp;&nbsp;&nbsp;
			网上网下都能用<input type="radio" value="ALL" name="useWhere">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			只适合新会员用<input type="radio" value="N" name="memberType" onclick="check_type(this)">&nbsp;&nbsp;&nbsp;
			只适合老会员用<input type="radio" value="O" name="memberType" onclick="check_type(this)">&nbsp;&nbsp;&nbsp;
			新老会员都能用<input type="radio" value="A" name="memberType" onclick="check_type(this)">
		</td>
	</tr>
	<tr id="nMember" style="display:none">
		<td colspan="2">新会员注册日期从&nbsp;<input type="text" name="regStartDate" size="10">&nbsp;到&nbsp;<input type="text" name="regEndDate" size="10">&nbsp;才能使用</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="button" name="nextBtn" value="<<上一步">&nbsp;&nbsp;
		<input type="button" name="nextBtn" value="下一步>>"></td>
		</td>
	</tr>
</table>

</form>
</body>
</html>
