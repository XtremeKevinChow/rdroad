<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<style>
TABLE{COLOR: #000000; FONT-SIZE: 12px; LINE-HEIGHT: 12pt}
BODY{COLOR: #000000; FONT-SIZE: 12px; LINE-HEIGHT: 14pt}
SELECT
{
	
	FONT-SIZE: 12px
}

input 
{
	font-size: 12px;
}
/* ��ͷ */
.tabletitle{
	background-color:#cc3300;
	font-size:12px; 
	color:#FFFFFF; 
	padding-left:20px; 
	padding-top:3px; 
	padding-bottom:3px;
	text-align:center;
	font-weight:bold;
}
/* ��ͷ */
.tabletitle2{
	background-color:#cc3300;
	font-size:12px; 
	color:#FFFFFF; 
	padding-left:20px; 
	padding-top:3px; 
	padding-bottom:3px;
	text-align:center;
	font-weight:bold;
}
/* ��ǩ */
.tableLabel{ font-size:12px; color:#990000; font-weight: bold;}
/* �����Ԫ�� */
.dataInput {background-color:#f0f0f0;text-align:right;}
/* ������ǩ */
.navigationLabel{ font-size:12px; color:#000000; font-weight: bold;}

</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function sync_activity() {
	if (confirm("����ͬ����ϵͳ������˹��Ļ��ֻͬ������վ���ݿ�"))
	{
		var frm = document.forms[0];
		frm.action = "expExchangeActivity.do?type=syncActivity";
		frm.submit();
	}
}
function view_f(activityNo) {
	window.location.href="/promotion/expExchangeActivity.do?type=view&activityNo="+activityNo;
}
function show_add_activity() {
	window.location.href="/promotion/expExchangeActivity.do?type=showAddActivity";
}
function delete_f(activityNo) {
	if (confirm("ȷʵҪɾ����"))
	{
		location.href="/promotion/expExchangeActivity.do?type=deleteActivity&activityNo="+activityNo;
	}
}
function check_f(activityNo) {
	if (confirm("ȷʵҪ��ˣ�"))
	{
		location.href="/promotion/expExchangeActivity.do?type=checkActivity&activityNo="+activityNo;
	}
}
function recheck_f(activityNo) {
	if (confirm("ȷʵҪ������ˣ�"))
	{
		location.href="/promotion/expExchangeActivity.do?type=recheckActivity&activityNo="+activityNo;
	}
}
function uncheck_f(activityNo) {
	if (confirm("ȷʵҪ����"))
	{
		location.href="/promotion/expExchangeActivity.do?type=uncheckActivity&activityNo="+activityNo;
	}
}
function close_f(activityNo) {
	if (confirm("ȷʵҪ�رգ�"))
	{
		location.href="/promotion/expExchangeActivity.do?type=closeActivity&activityNo="+activityNo;
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ���ֻ���</font></td>
  </tr>
</table>

<TABLE width="800" align="center">
  <tr>
	<td align="left">
	   <input type="button" value=" ���� " onclick="show_add_activity();">&nbsp;&nbsp;&nbsp;&nbsp;
	   <!--<input type="button" value="ͬ������" onclick="sync_activity();">-->
	</td>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>

<table align="center"  width="800" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap >
		<td width="80">���</td>
		<td width="180">�����</td>
		<td width="100">�һ���ʽ</td>
		<td width="100">����ʽ</td>
		<td width="180">��ֹ����</td>
		<td width="60">״̬</td>
		<td width="100">����</td>
	</tr>
	<logic:iterate name="list" id="list">
	<tr class=OraTableCellText noWrap align=center >
		<td>
			<a href="javascript:view_f('<bean:write name="list" property="activityNo"/>');"><bean:write name="list" property="activityNo"/></a>
		</td>

		<td>
			&nbsp;<bean:write name="list" property="activityDesc"/>
		</td>

		<td>
			<logic:equal name="list" property="exchangeType" value="A">
			&nbsp;һ���Զһ�
			</logic:equal>
			<logic:equal name="list" property="exchangeType" value="B">
			&nbsp;ʵʱ�һ��һ�
			</logic:equal>
		</td>

		<td>
			&nbsp;<logic:equal name="list" property="dealType" value="A">
			����
			</logic:equal>
			<logic:equal name="list" property="dealType" value="B">
			����ʣ�����
			</logic:equal>
			<logic:equal name="list" property="dealType" value="">
			��
			</logic:equal>
		</td>

		<td>
			&nbsp;��<bean:write name="list" property="beginDate" />��<bean:write name="list" property="endDate"/>
		</td>

		<td>
		<logic:equal name="list" property="status" value="-2">
		����
		</logic:equal>
		<logic:equal name="list" property="status" value="-1">
		ɾ��
		</logic:equal>
		<logic:equal name="list" property="status" value="0">
		�½�
		</logic:equal>
		<logic:equal name="list" property="status" value="1">
		���
		</logic:equal>
		<logic:equal name="list" property="status" value="2">
		�ر�
		</logic:equal>
		</td>

		<td>
		&nbsp;<logic:equal name="list" property="status" value="0">
		<input type="button" value="���" onclick="check_f('<bean:write name="list" property="activityNo"/>');">
		<input type="button" value="ɾ��" onclick="delete_f('<bean:write name="list" property="activityNo"/>');">
		</logic:equal>
		<logic:equal name="list" property="status" value="1">
		<input type="button" value="����" onclick="uncheck_f('<bean:write name="list" property="activityNo"/>'
		);">
		</logic:equal>
		<logic:equal name="list" property="status" value="-2">
		<input type="button" value="�����" onclick="recheck_f('<bean:write name="list" property="activityNo"/>');">
		</logic:equal>
		<logic:equal name="list" property="status" value="1">
		<input type="button" value="�ر�" onclick="close_f('<bean:write name="list" property="activityNo"/>');">
		</logic:equal>
		</td>
	</tr>
	</logic:iterate>
</table>

</html:form>
</body>
</html>
