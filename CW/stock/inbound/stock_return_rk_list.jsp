<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function query_f() {
	var frm = document.forms[0];
	if ( (frm.startDate.value == "" || frm.endDate.value == "") && frm.rkNO.value == "" && frm.rrNO.value == "" &&  frm.purNO.value =="")
	{
		alert("�������ѯ����");
		frm.rkNO.focus();
		return;
	}

	if (frm.startDate.value == "")
	{
		
	} else {
		
		var bdate = frm.startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
				frm.startDate.focus();
				return;
		 }

	}

	if (frm.endDate.value == "")
	{
		
	} else {
		var edate = frm.endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
				frm.endDate.focus();
				return;
		 }
	}
	frm.offset.value = 0;
	frm.btn_query.disabled = "true";
	frm.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="inboundQuery.do?type=query" method="POST">
<bean:define name="inboundForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�˻�����ѯ</font><font color="838383"> 
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
		<td>�˻�ԭ��</td>
		<td bgcolor="#FFFFFF">
			<html:select property="rrNO" style="width:160">
				<option value="">-- ��ѡ�� --</option>
					<html:optionsCollection property="rrList" value="code" label="name"/> 
			</html:select>
			
		</td>
		
	</tr>
	<tr>
		<td>��ⵥ�ţ�</td>
		<td>
			<html:text property="rkNO" size="10"/>
		</td>
		<td>�������ţ�</td>
		<td>
			<html:text property="purNO" size="14"/>
		</td>
		<td></td><td><input type="button" value=" ��ѯ " name="btn_query" onclick="query_f();"></td>
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

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��ⵥ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�Ƿ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�˻�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�˻�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��(��)��ԭ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>����</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<a href="./inboundQuery.do?type=showReturnRK&rkNO=<bean:write name="list" property="rkNO"/>">
		<bean:write name="list" property="rkNO"/></a></td>
		<td class=OraTableCellText noWrap align=middle >
		<a href="../order/snView.do?sn_id=<bean:write name="list" property="purID" format="#"/>"><bean:write name="list" property="purNO"/></a></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="writeDate"/></td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="isBad" value="Y">��</logic:equal>
		<logic:equal name="list" property="isBad" value="N">��</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="returnType" value="A">�ڲ�</logic:equal>
		<logic:equal name="list" property="returnType" value="B">��Ա</logic:equal>
		<logic:equal name="list" property="returnType" value="C">������</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="returnClass" value="A">ȫ��</logic:equal>
		<logic:equal name="list" property="returnClass" value="P">������</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="rrName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="postNum"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="postage" format="#0.00"/></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
