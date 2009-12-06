<%
/* 
 * @author CodeGen 0.1 
 * create on Tue Nov 18 21:10:43 CST 2008
 * 
 * todo 
 */ 
%>

<%@ page contentType="text/html;charset=GBK"%> 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<html> 
<head> 
<title></title> 
<link rel="stylesheet" href="../css/style.css" type="text/css"> 
<script language="JavaScript" src="../script/default.js"></script> 
<script language="JavaScript">
function checkSubmit() {
	if(document.forms[0].id.value=="") { 
      alert("id不能为空");
      return false;
	} 
	if(document.forms[0].name.value=="") { 
      alert("name不能为空");
      return false;
	} 
	if(document.forms[0].person_use.value=="") { 
      alert("person_use不能为空");
      return false;
	} 
	if(document.forms[0].org_use.value=="") { 
      alert("org_use不能为空");
      return false;
	} 
	if(document.forms[0].discount.value=="") { 
      alert("discount不能为空");
      return false;
	} 
	return true; 
} 

</script>
</head>

<body> 
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
<tr> 
	<td width="21">&nbsp;</td> 
	<td><b><font color="838383">xxxx</font></b><font color="838383"> : </font><font color="838383">-&gt; </font><font color="838383">xxxx</font></td> 
</tr> 
</table> 

<html:form action="/s_payment_method.do?type=update" method="post" onsubmit="return checkSubmit();"> 
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 > 
	<tr>
		<td class="oraTableRowHeader" noWrap>id</td> 
		<td><html:text property="id"/>&nbsp;&nbsp;<font color="red">**</font></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>name</td> 
		<td><html:text property="name"/>&nbsp;&nbsp;<font color="red">*</font></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>description</td> 
		<td><html:text property="description"/></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>person_use</td> 
		<td><html:text property="person_use"/>&nbsp;&nbsp;<font color="red">*</font></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>org_use</td> 
		<td><html:text property="org_use"/>&nbsp;&nbsp;<font color="red">*</font></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>ref_dept</td> 
		<td><html:text property="ref_dept"/></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>bank</td> 
		<td><html:text property="bank"/></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>discount</td> 
		<td><html:text property="discount"/>&nbsp;&nbsp;<font color="red">*</font></td> 
	</tr> 
	<tr> 
<td colspan="2" align="center"><input type="submit" value=" 确 定 " >&nbsp;&nbsp;<input type="button" value=" 返 回 " onclick="history.back();"></td>
	</tr> 
</table> 
</html:form>
</body> 

</html>