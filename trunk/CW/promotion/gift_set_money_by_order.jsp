<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table align="center" width="900" border="1">
	<tr>
		<td colspan="4"><B>���ݶ���������õֿ۹���</B></td>
	</tr>
	<tr>
		<td width="225">��ͨ��Ա<input type="checkbox" name="levelId" value="1"></td>
		<td width="225">������Ա<input type="checkbox" name="levelId" value="2"></td>
		<td width="225">�𿨻�Ա<input type="checkbox" name="levelId" value="3"></td>
		<td width="225">�׽𿨻�Ա<input type="checkbox" name="levelId" value="4"></td>
	</tr>
	<tr>
		<td>�ۿ�<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;��һ<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;�ۼ�<input type="radio" value="2" name="disType"></td>
		<td>�ۿ�<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;��һ<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;�ۼ�<input type="radio" value="2" name="disType"></td>
		<td>�ۿ�<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;��һ<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;�ۼ�<input type="radio" value="2" name="disType"></td>
		<td>�ۿ�<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;��һ<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;�ۼ�<input type="radio" value="2" name="disType"></td>
	</tr>
	<tr>
		<td>������ֹ����&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
		<td>������ֹ����&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
		<td>������ֹ����&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
		<td>������ֹ����&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
		<td>�������(��)<input size="6" name="orderRequire">&nbsp;&nbsp;����<input size="4" name="disAmt"></td>
	</tr>
</table>

</form>
</body>
</html>
