<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
/**
function query_f() {
	var frm = document.forms[0];
	
	if (frm.arNO.value == "" && frm.soNO.value == "")
	{
		if ( frm.customerID.value == "" && frm.customerName.value == "" )
		{
			alert("�������ѯ����");
			frm.customerName.focus();
			return;
		}
	}
	
	
	frm.submit();
}
*/
function query_f() {
	if (document.forms[0].startDate.value == "")
	{
		alert("��ʼ���ڲ���Ϊ��");
		document.forms[0].startDate.focus();
		return;
	} else {
		
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
				document.forms[0].startDate.focus();
				return;
		 }

	}

	if (document.forms[0].endDate.value == "")
	{
		alert("�������ڲ���Ϊ��");
		document.forms[0].endDate.focus();
		return;
	} else {
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
				document.forms[0].endDate.focus();
				return;
		 }
	}

	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="finSalesInvoiceManage.do?type=queryInvoiceList" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">���۷�Ʊ��ѯ</font><font color="838383"> 
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
		<td>��ʼ���ڣ�</td>
		<td>
			<html:text property="startDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>�������ڣ�</td>
		<td>
			<html:text property="endDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	<tr>
		<td>��Ʊ�ţ�</td>
		<td>
			<html:text property="arNO" size="10"/>
		</td>
		<td>���۶����ţ�</td>
		<td>
			<html:text property="soNO" size="10"/>
		</td>
	</tr>
	<tr>
		<td>�ͻ����룺</td>
		<td>
			<html:text property="customerID" size="10"/>
		</td>
		<td>�ͻ����ƣ�</td>
		<td>
			<html:text property="customerName" size="35"/>&nbsp;
			<input type="button" value=" ��ѯ " name="btn_query" onclick="query_f();">
		</td>
	</tr>
</table>
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���۷�Ʊ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���۶�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ͻ�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ͻ�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>ҵ������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʊ״̬</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		
		<td class=OraTableCellText noWrap align=middle >
		<a href="./finSalesInvoiceManage.do?type=queryInvoiceItems&arID=<bean:write name="list" property="arID"/>">
		<bean:write name="list" property="arNO"/></a></td>

		<td class=OraTableCellText noWrap align=middle >
		<a href="./finSales.do?type=showSalesItems&soID=<bean:write name="list" property="soID"/>">
		<bean:write name="list" property="soNO"/></a></td>

		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="customerID"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="customerName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="operationClassName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="soDate"/></td>
		<td class=OraTableCellText noWrap align=middle >
		<logic:equal name="list" property="status" value="1">�½�</logic:equal>
		<logic:equal name="list" property="status" value="2">���</logic:equal>
		<logic:equal name="list" property="status" value="3">����</logic:equal>
		</td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
