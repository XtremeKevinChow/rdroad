<%
/* 
 * @author CodeGen 0.1 
 * create on Thu Sep 04 16:12:24 CST 2008
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
      alert("���id����Ϊ��");
      return false;
	} 
	if(document.forms[0].name.value=="") { 
      alert("������Ʋ���Ϊ��");
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
	<td><b><font color="838383">Ŀ¼����</font></b><font color="838383"> : </font><font color="838383">-&gt; </font><font color="838383">Ŀ¼�������</font></td> 
</tr> 
</table> 

<html:form action="/s_catalog_edition.do?type=update" method="post" onsubmit="return checkSubmit();"> 
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 > 
	<tr>
		<td class="oraTableRowHeader" noWrap>���id</td> 
		<td><html:text property="id" readonly="true"/>&nbsp;&nbsp;<font color="red">**</font></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>�������</td> 
		<td><html:text property="name"/>&nbsp;&nbsp;<font color="red">*</font></td> 
	</tr> 
	<tr>
		<td class="oraTableRowHeader" noWrap>����</td> 
		<td><html:text property="description"/></td> 
	</tr> 
	<tr> 
<td colspan="2" align="center"><input type="submit" value=" ȷ �� " >&nbsp;&nbsp;<input type="button" value=" �� �� " onclick="history.back();"></td>
	</tr> 
</table> 
</html:form>
</body> 

</html>