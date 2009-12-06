<%@ page contentType="text/html;charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function update_f() {
	document.forms[0].updateBtn.disabled = true;
	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="importPostcode.do?type=update">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">会员管理</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">更改邮编查询</font>
      	</td>
   </tr>
</table>
<TABLE>
<TR>
	<TD><input name="updateBtn" type="button" value="更改地址" onclick="update_f()"></TD>
</TR>
</TABLE>
<table width="95%" align="center" cellspacing="1" border="0"  >

	<tr>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>订单号</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>订单邮编</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>发送地址</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>会员号</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>会员邮编</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>新邮编</td>
		
	</tr>
	<logic:iterate name="list" id="list"> 

	<tr height="26">
		<td bgcolor="#FFFFFF"><bean:write name="list" property="ordNumber"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="list" property="ordPostcode"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="list" property="ordAddress"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="list" property="cardId"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="list" property="mbrPostcode"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="list" property="newPostcode"/></td>
	</tr>
</logic:iterate>
</table>

</html:form>
</body>
</html>
