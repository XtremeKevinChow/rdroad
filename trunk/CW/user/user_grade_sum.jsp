<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
	<link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
function query_f() {
	if (document.forms[0].beginDate.value == "")
	{
		alert("��ʼ���ڲ���Ϊ��");
		document.forms[0].beginDate.focus();
		return;
	} else {
		
		var bdate = document.forms[0].beginDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
				document.forms[0].beginDate.focus();
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

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<html:form method="post" action="/findPersonalGrade.do">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">�û�����</font><font color="838383">
              -&gt; </font><font color="838383">Ա�������ͳ��</font>
          </td>
   </tr>
</table>
<br>
<TABLE width="95%" align="center">
<tr>
		<td>��ʼ���ڣ�</td>
		<td>
			<html:text property="beginDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>�������ڣ�</td>
		<td>
			<html:text property="endDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>������</td>
		<td>
			<html:text property="userName" size="10"/>
		</td>
		<td>���ţ�</td>
		<td>
			<html:text property="personId" size="10"/>
		</td>
		<td>
			<input type="button" value=" ��ѯ " name="btn_query" onclick="query_f()">
		</td>
	</tr>
</TABLE>
<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
  <tr>
        <th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��½��</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ܴ���</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���������</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ٷֱ�</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�������</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ٷֱ�</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >һ�����</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ٷֱ�</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���������</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ٷֱ�</th>
	</tr>

  <logic:iterate  name="list" id="grade"> 
	<tr>		
    <td class=OraTableCellText noWrap align=left ><bean:write name="grade" property="userId"/></td>
	<td class=OraTableCellText noWrap align=left ><bean:write name="grade" property="userName"/></td>
	<td class=OraTableCellText noWrap align=left ><bean:write name="grade" property="empNo"/></td>
    <td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="total" format="#0"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="amount1" format="#0"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="rate1" format="#0"/>%</td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="amount2" format="#0"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="rate2" format="#0"/>%</td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="amount3" format="#0"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="rate3" format="#0"/>%</td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="amount4" format="#0"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="grade" property="rate4" format="#0"/>%</td>
	</tr>
	</logic:iterate>

</table>

</html:form>

</body>

</html>
