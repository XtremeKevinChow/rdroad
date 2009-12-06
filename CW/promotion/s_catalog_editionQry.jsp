<%
/* 
 * @author CodeGen 0.1 
 * create on Thu Sep 04 09:24:48 CST 2008
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
	return true; 
} 
function add() {
	document.forms[0].action="s_catalog_edition.do?type=initAdd"; 
	document.forms[0].submit(); 
}
function edit(id) {
	document.forms[0].action="s_catalog_edition.do?type=initEdit"+"&id="+id; 
	document.forms[0].submit(); 
} 
function f_delete(id) {
	if(!confirm("您确实要删除该记录吗")) { 
      return ;
	} 

	document.forms[0].action="s_catalog_edition.do?type=delete"+"&id="+id; 
	document.forms[0].submit(); 
} 

</script>
</head>

<body> 
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
<tr> 
	<td width="21">&nbsp;</td> 
	<td><b><font color="838383">目录管理</font></b><font color="838383"> : </font><font color="838383">-&gt; </font><font color="838383">目录板块设置</font></td> 
</tr> 
</table> 

<html:form action="/s_catalog_edition.do?type=list" method="post" onsubmit="return checkSubmit();"> 
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 > 
	<tr><td><input type="button" value=" 新增 " onclick="add()"></td></tr> 
</table> 
</html:form>

<table width="95%"  cellspacing="1" border="0" align="center"> 
	<tr class="oraTableRowHeader" noWrap > 
		<td>板块ID</td> 
		<td>板块名称</td> 
		<td>描述</td>
		<td>操作</td>
	</tr> 

	<% int i=0; %> 
	<logic:iterate id="info" name="list" > 
	<tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> align="center" > 
		<td ><a href="javascript:edit(<bean:write name="info" property="id"/>)"><bean:write name="info" property="id"/></a></td> 
		<td><bean:write name="info" property="name"/></td> 
		<td><bean:write name="info" property="description"/></td> 
		<td><input type=button value=" 删除 " onclick="f_delete(<bean:write name="info" property="id"/>)"></td> 
	</tr> 
	<% i++; %> 
	</logic:iterate> 
</table> 
</body> 

</html>