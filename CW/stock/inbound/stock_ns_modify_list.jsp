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
function detail_f(id) {
	location.href = "./nsInbound.do?type=initModify&rkNO=" + id;
}

function query_f() {
	var frm = document.forms[0];
	if (  (frm.startDate.value == "" || frm.endDate.value == "") && frm.rkNO.value == "" && frm.memberName.value == ""  )
	{
		alert("��ѯ��������Ϊ��");
		frm.rkNO.focus();
		return;
	}

	if (document.forms[0].startDate.value == "")
	{
		
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
<html:form action="/nsInbound.do?type=queryModifyRecords" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ѯδȷ�ϵ�������ⵥ(�޸�)</font><font color="838383"> 
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
		<td>��ⵥ�ţ�</td>
		<td>
			<html:text property="rkNO" size="10"/>
		</td>
		<td>�ͻ�������</td>
		<td>
			<html:text property="memberName" size="10"/>&nbsp;
			<input type="button" value=" ��ѯ " name="btn_query" onclick="query_f();">
		</td>
	</tr>
	
</table>
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��ⵥ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ʱ�</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��ַ</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�˻�ԭ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�ʴ�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�Ƿ�ȷ��</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>�Ƿ����</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<a href="javascript:detail_f('<bean:write name="list" property="rkNO"/>');">
		<bean:write name="list" property="rkNO"/>
		</a>
		</td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postcode"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="address"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="memberName"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="returnReason"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="otherSpecial"/></td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="logOut" value="N">��</logic:equal>
			<logic:equal name="list" property="logOut" value="Y">��</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="isRk" value="N">��</logic:equal>
			<logic:equal name="list" property="isRk" value="Y">��</logic:equal>
		</td>

	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
