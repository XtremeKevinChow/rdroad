<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<style>
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
/* ��ǩ */
.tableLabel{ font-size:12px; color:#990000; font-weight: bold;}
/* �����Ԫ�� */
.dataInput {background-color:#f0f0f0;text-align:right;}
/* ������ǩ */
.navigationLabel{ font-size:12px; color:#000000; font-weight: bold;}
</style>
<SCRIPT LANGUAGE="JavaScript">
function view_f() {
	window.open("Exp_Exchange_Package_View.html");
}
function modify_activity() {
	window.location.href="expExchangeActivity.do?type=showModifyActivity&activityNo=<bean:write name="activity" property="activityNo"/>";
}
function modify_step() {
	window.location.href="expExchangeActivity.do?type=showModifyStep&activityNo=<bean:write name="activity" property="activityNo"/>";
}
function back_f() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=queryActivity";
	frm.submit();
}
function view_package(no) {
	window.open('expExchangePackage.do?type=view&packageNo='+no)
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ���ֻ���鿴</font></td>
  </tr>
</table>
<TABLE width="600" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<table width="600" border="0" cellspacing="0" cellpadding="0" align="center" >
    <tr>
      	
    	<td align="right">
		<logic:equal name="activity" property="status" value="0">
		<input type="button" value="�޸Ļ" onclick="javascript:modify_activity();">
		</logic:equal>
		<logic:equal name="activity" property="status" value="-2">
		<input type="button" value="�޸Ļ" onclick="javascript:modify_activity();">
		</logic:equal>
		</td>
   </tr>
</table>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" >
    <tr >
		<td class="dataInput">����ͣ�</td>
		<td>&nbsp;
		<logic:equal name="activity" property="activityType" value="1">
		���ֶһ�
		</logic:equal>
		<logic:equal name="activity" property="activityType" value="2">
		���ܻ�Ա�һ�
		</logic:equal></td>
	</tr>
    <tr>
		<td class="dataInput">�붨����ֻ�ţ�</td>
		<td>&nbsp;<bean:write name="activity" property="activityNo"/></td>
	</tr>
	<tr>
		<td class="dataInput">�붨����ֻ���ƣ�</td>
		<td>&nbsp;<bean:write name="activity" property="activityDesc"/></td>
	</tr>
	
	<tr>
		<td class="dataInput">������һ���ֹ���ڣ�</td>
			
		<td>
			&nbsp;��&nbsp;<bean:write name="activity" property="beginDate"/>
			��&nbsp;<bean:write name="activity" property="endDate"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">�һ���ʽ��</td>
			
		<td>
			<logic:equal name="activity" property="exchangeType" value="A">
			&nbsp;һ���Զһ�
			</logic:equal>
			<logic:equal name="activity" property="exchangeType" value="B">
			&nbsp;ʵʱ�һ�
			</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">����ʽ��</td>
			
		<td>
			<logic:equal name="activity" property="dealType" value="A">
			&nbsp;����
			</logic:equal>
			<logic:equal name="activity" property="dealType" value="B">
			&nbsp;����ʣ�����
			</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">��Ʒ��������ڣ�</td>
			
		<td>
			&nbsp;<bean:write name="activity" property="giftLastDate"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">״̬��</td>
		<td>
		<logic:equal name="activity" property="status" value="-2">
		����
		</logic:equal>
		<logic:equal name="activity" property="status" value="-1">
		ɾ��
		</logic:equal>
		<logic:equal name="activity" property="status" value="0">
		�½�
		</logic:equal>
		<logic:equal name="activity" property="status" value="1">
		���
		</logic:equal>
		<logic:equal name="activity" property="status" value="2">
		�ر�
		</logic:equal>
		</td>
	</tr>
</table>

<TABLE width="600" align="center">
  <tr>
	<td class="tableLabel">���ֵ�����ϸ</td>
	<td align="right">
	<logic:equal name="activity" property="status" value="0">
	<input type="button" value="�޸ĵ���" onclick="javascript:modify_step();">
	</logic:equal>
	<logic:equal name="activity" property="status" value="-2">
	<input type="button" value="�޸Ļ" onclick="javascript:modify_step();">
	</logic:equal>
	</td>
  </tr>
</TABLE>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<bean:define name="activity" id="stepMstList" property="mstList"/>
	<logic:iterate name="stepMstList" id="stepMst"><!-- ���ֵ��� -->
	<bean:define name="stepMst" property="dtlList" id="stepDtlList"/>
	<tr class="OraTableRowHeader" noWrap>
		<td align="left">������<bean:write name="stepMst" property="beginExp" />�ֿ�ѡ��������Ʒ</td>
	</tr>
	
	<logic:iterate name="stepDtlList" id="stepDtl"><!-- ������ϸ -->
	<tr>
		<td>
		<logic:equal name="stepDtl" property="stepType" value="P">
		���
		</logic:equal>
		<logic:equal name="stepDtl" property="stepType" value="G">
		��Ʒ
		</logic:equal>
		<logic:equal name="stepDtl" property="stepType" value="T">
		��ȯ
		</logic:equal>&nbsp;
		<logic:equal name="stepDtl" property="stepType" value="P">
		<a href="javascript:view_package('<bean:write name="stepDtl" property="no"/>');">
		</logic:equal>
		<bean:write name="stepDtl" property="no"/>
		<logic:equal name="stepDtl" property="stepType" value="P">
		</a>
		</logic:equal>

		<logic:equal name="stepDtl" property="stepType" value="G">
		����<bean:write name="stepDtl" property="addMoney" format="#0"/>Ԫ����Ʒ	
		������<bean:write name="stepDtl" property="orderRequire" format="#0.00"/>��ʹ�ã�
		</logic:equal>
		</td>
	</tr>
	</logic:iterate>
	</logic:iterate>
</table>
<TABLE width="600" align="center">
  <tr>
	<td align="center"><input type="button" value=" ���� " onclick="back_f();"></td>
  </tr>
</table>
</html:form>
</body>
</html>
