<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
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
		<td colspan="2"><B>������ȯ����</B></td>
	</tr>
	<tr>
		<td>��ȯ��Ч���ڣ�</td>
		<td>��&nbsp;<input type="text" name="startDate" size="10">&nbsp;��&nbsp;<input type="text" name="endDate" size="10"></td>
	</tr>
	<tr>
		<td>��ȯĬ�Ͻ�</td><td><input type="text" name="giftMoney">&nbsp;Ԫ</td>
	</tr>
	<tr>
		<td>ʹ����ȯ����Ͷ�����</td><td><input type="text" name="personNum">&nbsp;Ԫ</td>
	</tr>
	<tr>
		<td>�������ʹ�ô�����</td><td><input type="text" name="personNum">&nbsp;��</td>
	</tr>
	<tr>
		<td>�ܹ���ʹ�ô�����</td><td><input type="text" name="totalNum">&nbsp;��</td>
	</tr>
	<!-- N-new;O-old;A-all -->
	<tr>
		<td colspan="2">
			ֻ�ʺ���վʹ��<input type="radio" value="NET" name="useWhere">&nbsp;&nbsp;&nbsp;
			ֻ�ʺ�����ʹ��<input type="radio" value="CRM" name="useWhere">&nbsp;&nbsp;&nbsp;
			�������¶�����<input type="radio" value="ALL" name="useWhere">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			ֻ�ʺ��»�Ա��<input type="radio" value="N" name="memberType" onclick="check_type(this)">&nbsp;&nbsp;&nbsp;
			ֻ�ʺ��ϻ�Ա��<input type="radio" value="O" name="memberType" onclick="check_type(this)">&nbsp;&nbsp;&nbsp;
			���ϻ�Ա������<input type="radio" value="A" name="memberType" onclick="check_type(this)">
		</td>
	</tr>
	<tr id="nMember" style="display:none">
		<td colspan="2">�»�Աע�����ڴ�&nbsp;<input type="text" name="regStartDate" size="10">&nbsp;��&nbsp;<input type="text" name="regEndDate" size="10">&nbsp;����ʹ��</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="button" name="nextBtn" value="<<��һ��">&nbsp;&nbsp;
		<input type="button" name="nextBtn" value="��һ��>>"></td>
		</td>
	</tr>
</table>

</form>
</body>
</html>
