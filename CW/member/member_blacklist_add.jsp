<%@ page contentType="text/html;charset=GBK" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function add_f() {
	if(document.forms[0].cardID == null || document.forms[0].cardID.value == "") {
		alert("会员号不能为空!");
		document.forms[0].cardID.focus();
		return;
	}
	document.forms[0].submit();
}

function f_delete( bkid ) {
	document.forms[0].action = "memberBlackList.do?type=delete&ID=" + bkid;
	document.forms[0].submit();
}

function load_f() {

	document.forms[0].cardID.focus();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f()">
<html:form action="/memberBlackList.do?type=add" method="post">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td width="280"><nobr>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">黑名单管理</font><font color="838383"> </nobr>
      	</td>
      	<td align="right">
      		&nbsp;
      	</td>
   </tr>
</table>
<br>
<table width="95%" align="center" cellspacing="1" cellspacing="1" border="0">
	<tr height="26">
		<td ><font color="red">*</font>会员号：</td>
		<td  bgcolor="#FFFFFF">
		<html:text property="cardID" />
		</td>
		<td >原因：</td>
		<td bgcolor="#FFFFFF">
			<html:text property="description" size="50"/>
		</td>
        <td align="center">
			<input type="button"  value=" 确定 " onclick="add_f();"> 
		</td>
	</tr>
</table>
<table>
<table width="95%" align="center" cellspacing="1" cellspacing="1" border="0">
    <tr class="oraTableRowHeader" noWrap>
        <td width="10%">会员号</td>
        <td width="70%">原因</td>
        <td width="10%">日期</td>
        <td width="10%">操作</td>
    </tr>
	<bean:define name="list" id="mb_black" type="java.util.Collection"/>
	<logic:iterate name="mb_black" id="member"> 
	<tr>
	<td><bean:write name="member" property="cardID"/></td>
	<td>&nbsp;<bean:write name="member" property="description"/></td>
	<td><bean:write name="member" property="createDate"/></td>
	<td><input type="button" name="delete" value="删除" onclick="javascript:f_delete('<bean:write name="member" property="ID"/>');"></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
