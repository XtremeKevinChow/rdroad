<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table align="center" width="900" border="1">
	<tr>
		<td colspan="4"><B>根据订单金额设置抵扣规则：</B></td>
	</tr>
	<tr>
		<td width="225">普通会员<input type="checkbox" name="levelId" value="1"></td>
		<td width="225">银卡会员<input type="checkbox" name="levelId" value="2"></td>
		<td width="225">金卡会员<input type="checkbox" name="levelId" value="3"></td>
		<td width="225">白金卡会员<input type="checkbox" name="levelId" value="4"></td>
	</tr>
	<tr>
		<td>折扣<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;单一<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;累计<input type="radio" value="2" name="disType"></td>
		<td>折扣<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;单一<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;累计<input type="radio" value="2" name="disType"></td>
		<td>折扣<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;单一<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;累计<input type="radio" value="2" name="disType"></td>
		<td>折扣<input type="checkbox" name="levelId" value="1">&nbsp;&nbsp;单一<input type="radio" value="1" name="disType">&nbsp;&nbsp;&nbsp;累计<input type="radio" value="2" name="disType"></td>
	</tr>
	<tr>
		<td>订单起止日期&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
		<td>订单起止日期&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
		<td>订单起止日期&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
		<td>订单起止日期&nbsp;<input size="10" name="orderStartDate">&nbsp;<input size="10" name="orderEndDate"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
	<tr>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
		<td>订单金额(满)<input size="6" name="orderRequire">&nbsp;&nbsp;抵用<input size="4" name="disAmt"></td>
	</tr>
</table>

</form>
</body>
</html>
