<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function detail_f(id) {
	location.href = "./customer.do?type=initModify&customerNO=" + id;
}

function add_f(obj) {
	document.forms[0].action = "./customer.do?type=initAdd";
	obj.disabled = true;
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/customer.do?type=query" method="POST">
<bean:define name="customerForm" property="pager" id="pager" />
<html:hidden name="pager" property="offset"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ͻ���ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><input type="button" value=" ���� " onclick="add_f(this);"></td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
	<tr>
		<td>
		<logic:present name="pager">
		<bean:write name="pager" property="pageNavigation" filter="false"/>
		</logic:present>
	</td>
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ͻ�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ͻ�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ͻ�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��ע</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<logic:equal name="list" property="isHref" value="1">
		<a href="javascript:detail_f('<bean:write name="list" property="customerNO"/>');">
		<bean:write name="list" property="customerNO"/>
		</a>
		</logic:equal>

		<logic:equal name="list" property="isHref" value="0">
		<bean:write name="list" property="customerNO"/>
		</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="customerName"/></td>
		<td class=OraTableCellText noWrap align=middle >
		<logic:equal name="list" property="typeID" value="1">�ʾ�</logic:equal>
		<logic:equal name="list" property="typeID" value="2">��Ա</logic:equal>
		<logic:equal name="list" property="typeID" value="3">�ŵ�</logic:equal>
		<logic:equal name="list" property="typeID" value="4">�Ź�</logic:equal>
		<logic:equal name="list" property="typeID" value="5">����</logic:equal>
		<logic:equal name="list" property="typeID" value="6">����</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="remark"/></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
