<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>

</head>
<body bgcolor="#FFFFFF" >
<html:form action="/consoleExp.do?iscallcenter=1" method="post">

<table width="100%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" id="DataTable">
	<tr>
		
		<th width="10%"   class="OraTableRowHeader" noWrap   >����</th>
		<th width="15%"   class="OraTableRowHeader" noWrap   >��ֵ</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >����</th>
		
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
    
		<td bgcolor="#FFFFFF" noWrap align="right" >
		<bean:write name="giftList" property="exp"/>
		</td>

		<td bgcolor="#FFFFFF" noWrap align="right" >
		<bean:write name="giftList" property="totalExp"/>
		</td>
		<td bgcolor="#FFFFFF" noWrap align="center" >
		<input type="button" value="����" name="saveBtn">&nbsp;<input type="button" value="ɾ��" name="deleteBtn">
		</td>
	</tr>
	</logic:iterate>
</table>

</html:form>
</body>
</html>
