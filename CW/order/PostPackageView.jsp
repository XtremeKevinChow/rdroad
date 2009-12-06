<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>邮包信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function init(){
	
	var code = document.getElementById("packageCode").innerText;
	
	
	if (code != null && code.length == 13)
	{
		var center = code.substring(6,10);
		document.getElementById("packageCode").innerHTML=code.substring(0,6) + "<font color=red><B>"+center+"</B></font>" + code.substring(10,13);

	}
	
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init();">
<html:form action="/showPostPackage.do"> 
<table cellspacing="0" border="0" >
  <tr> 
    <td class="OraTableRowHeader" noWrap >包裹号:</td><td  id="packageCode"><bean:write name="orderForm" property="postPackageCode"/></td>
  </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap >包裹重:</td>
    <td >&nbsp;<bean:write name="orderForm"  property="postPackageWeight"/></td>
  </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap >包裹费：</td>
    <td>&nbsp;<bean:write name="orderForm" property="postPackageFee"/></td>
  </tr>
  <tr>
    <td class="OraTableRowHeader" noWrap >包裹日期：</td>
    <td >&nbsp;<bean:write name="orderForm" property="postPackageDate"/></td>
  </tr>
</table>
</html:form> 
<p>&nbsp;</p></body>
</html>
