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
function checkAll(bln, type) {
	
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if(bln) {
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}

function uncheck_f() {

	var flag = false;
	var len = DataTable.rows.length;
	for (var i = 1; i < len; i ++)
	{
		var row = DataTable.rows(i);
		if (row.getElementsByTagName("INPUT")[0].checked == true)
		{
			flag = true;
			break;
		}
	}
	if (flag == true)//�м�¼���Ϲ�
	{
		if (confirm("ȷ��������(Щ)��¼��?"))
		{
			
			document.forms[0].action = "finPurchaseInvoiceUncheck.do?type=uncheck";
			document.forms[0].btn_uncheck.disabled = "true";
			document.forms[0].submit();
			
		}
	} else {
		alert("��ѡ���¼!");
		return;
	}
}
function query_f() {
	var frm = document.forms[0];
	
	if ( frm.proNO.value == "" && frm.proName.value == "" && frm.factAPCode.value == "" && frm.invoiceDate.value == "" )
	{
		alert("�������ѯ����");
		frm.proNO.focus();
		return;
	}
	if (document.forms[0].invoiceDate.value == "")
	{
		
	} else {
		var edate = document.forms[0].invoiceDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
				document.forms[0].invoiceDate.focus();
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
<html:form action="finPurchaseInvoiceUncheck.do?type=queryCheckedList" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ɹ���Ʊ����</font><font color="838383"> 
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
		<td>
			��Ӧ�̴��룺
			<html:text property="proNO" size="8"/>&nbsp;&nbsp;&nbsp;
		</td>
		<td>			
			��Ӧ�����ƣ�
			<html:text property="proName" size="35"/>

		</td>
	</tr>
  <tr>
		<td>
			��Ʊ�ţ�&nbsp;&nbsp;
			<html:text property="factAPCode" size="10"/>&nbsp;&nbsp;&nbsp;
		</td>
		<td>
			��Ʊ���ڣ�&nbsp;&nbsp;
			<html:text property="invoiceDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].invoiceDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btn_query" value=" ��ѯ " onclick="query_f();">
			<input type="button" name="btn_uncheck" value=" ���� " onclick="uncheck_f();">
		</td>		
	</tr>	
</table>

<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>ȫѡ<input type="checkbox" onclick="checkAll(this.checked, 0)"></th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ɹ���Ʊ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ӧ�̴���</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ӧ������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>����״̬</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<input type="checkbox" name="apID" value="<bean:write name="list" property="apID"/>"></td>
		<td class=OraTableCellText noWrap align=middle >
		<a href="./finPurchaseInvoiceManage.do?type=showPurchasesItemsView&apID=<bean:write name="list" property="apID"/>">
		<bean:write name="list" property="factAPCode"/></a></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="proNO"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="proName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="createDate"/></td>
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
