<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page contentType="text/html;charset=GBK" language="java" import="com.magic.crm.util.Constants"%>

<html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>right</title>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="70%" height="80" align="center" bgcolor="#cccccc">
<jsp:useBean id="controlledError" scope="request" class="com.magic.crm.util.ControlledError"/>
<tr>
<td bgcolor="#9999cc" height="30" align="left" valign="top"><jsp:getProperty name="controlledError" property="errorTitle"/></td>
</tr>
<tr>
<td valign="top">
<jsp:getProperty name="controlledError" property="errorBody"/>
</td>
</tr>
</table>
</body>
</html>
