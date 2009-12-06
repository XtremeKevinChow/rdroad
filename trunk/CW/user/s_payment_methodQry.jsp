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
	return true; 
} 
function f_add() {
	document.forms[0].action="s_payment_method.do?type=initAdd"; 
	document.forms[0].submit(); 
}
function f_edit(id) {
    
	document.forms[0].action="s_payment_method.do?type=initEdit"+ "&id" + id; 
	document.forms[0].submit(); 
	
} 
function f_update(id) {
    document.forms[0].payid.value= id;
	document.forms[0].action="s_payment_method.do?type=update";
	document.forms[0].submit(); 
	
}
function f_delete(id) {
	if(!confirm("您确实要删除该记录吗")) { 
      return ;
	} 

	document.forms[0].action="s_payment_method.do?type=delete"+"&id="+id; 
	document.forms[0].submit(); 
} 
function check(txt) {
    if (isNaN(txt.value)) {
        alert("折扣只能是数字");
        txt.select();
        return;
    }
    if (txt.value > 1 || txt.value < 0) {
        alert("折扣只能在0-1中");
        txt.select();
        return;
    }
}

</script>
</head>

<body> 
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
<tr> 
	<td width="21">&nbsp;</td>  
	<td><b>当前位置 : 基础数据 -> 付款折扣维护</b></td> 
</tr> 
</table>
<html:form action="/s_payment_method.do?type=update" method="post"> 
<input type=hidden name="payid">
<table width="80%" align="center" cellspacing="1" border="0"> 
	<tr class="oraTableRowHeader" noWrap> 
		<td>id</td> 
		<td>名称</td> 
		<td>描述</td> 
		<td>折扣率</td> 
		<td></td>
	</tr> 

	<% int i=0; %> 
	<logic:iterate id="info" name="list" > 
	<tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> align="center"> 
		<td ><bean:write name="info" property="id"/></td> 
		<td><bean:write name="info" property="name"/></td> 
		<td><bean:write name="info" property="description"/></td> 
		<td><input type=text maxlength=4 name ="txt<bean:write name="info" property="id"/>" value="<bean:write name="info" property="discount"/>" onblur="check(this);"> </td> 
		<td><input type=button value=" 更 改 " onclick="f_update(<bean:write name="info" property="id"/>)"></td>
	</tr> 
	<% i++; %> 
	</logic:iterate> 
</table>
</html:form>  
</body> 

</html>