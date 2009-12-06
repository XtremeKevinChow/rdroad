<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>退(换)货信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/showPostPackage.do"> 
<table cellspacing="0" border="0" >
  <tr> 
    <td class="OraTableRowHeader" noWrap >退(换)货原因:</td><td><bean:write name="orderForm" property="retReason"/></td>
  </tr>
</table>
</html:form> 
<p>&nbsp;</p></body>
</html>
