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
function delete_f2(row) {
	var frm = document.forms[0];
	if (confirm("ȷʵҪɾ����"))
	{
		frm.action="expExchangeActivity.do?type=deleteStep&forward=modify_step&rowId=" + (row-1);
		frm.submit();
	}
	
}
function additem_f2() {
	var frm = document.forms[0];
	frm.action="expExchangeActivity.do?type=addStep&forward=modify_step";
	frm.submit();
}
function delete_f(row) {

	if (detailTable.rows.length <= 2)
	{
		alert("����Ҫ��һ����ϸ��ҳ����");
		return;
	}
	detailTable.deleteRow(row);
}
function additem_f() {

	//���һ��
	var newTr = detailTable.insertRow();

	//���6��
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	var newTd2 = newTr.insertCell();
	var newTd3 = newTr.insertCell();
	var newTd4 = newTr.insertCell();


	//���������ݺ����� 

	newTd0.innerHTML = "<input type='quantity' size='4' value=''>&nbsp;��"; 
	newTd1.innerHTML= "��Ч";
	newTd2.innerHTML= "<input type='button' value='ɾ��' onclick='delete_f(this.parentElement.parentElement.rowIndex);'>";
	

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	
	newTd0.setAttribute("align", "left");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "left");
}
function save_step() {
	window.location.href="��ͼ/Exp_Exchange_Activity_List.html";
}
function prevStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=backShowModifyActivity";
	frm.submit();
}
function nextStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=showModifyStepGift";
	frm.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<input type="hidden" name="activityNo" value="<bean:write name="activity" property="activityNo"/>">
<table width="940" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : �г����� -&gt; ���ֵ����޸� </td>
   </tr>
</table>
<TABLE width="500" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>

<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">���ֻ�� :</td>
		<td><bean:write name="activity" property="activityNo"/></td>
	</tr>
	<tr>
		<td class="dataInput">����� :</td>
		<td><bean:write name="activity" property="activityDesc"/></td>
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
<br>
<TABLE width="500" align="center">
  <tr>
	<td align="center">
	   <!-- <input type="button" name="nextBtn" value="<<��һ��" onclick="prevStep();"> -->
	   <input type="button" name="nextBtn" value="��һ��>>" onclick="nextStep();">
	   <!-- <input type="button" value=" ���� " onclick="save_step();"> -->
	</td>
  </tr>
</TABLE>
<TABLE width="500" align="center">
  <tr>
	<td class="tableLabel">���ֵ���</td>
	<td align="right">
	  <input type="button" value="��ӻ��ֵ���" onclick="additem_f2();">
	</td>
  </tr>
</TABLE>
<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap>
		<td width="200">��ʼ����</td>
		<td width="200">״̬</td>
		<td width="100">����</td>
	</tr>
	<bean:define name="activity" id="stepMstList" property="mstList"/>
	<logic:iterate id="stepMst" name="stepMstList">
	<tr>
		<td>
			<input type="hidden" name="stepMstId" value="<bean:write name="stepMst" property="id" format="#0"/>">
			<logic:equal name="stepMst" property="enabled" value="false">
			<input type="hidden" name="beginExp" value="<bean:write name="stepMst" property="beginExp" format="#0"/>"><bean:write name="stepMst" property="beginExp" format="#0"/>&nbsp;��
			</logic:equal>
			<logic:equal name="stepMst" property="enabled" value="true">
			<input name="beginExp" size="4" value="<bean:write name="stepMst" property="beginExp" format="#0"/>">&nbsp;��
			</logic:equal>
			
		</td>
		<td>
			<logic:equal name="stepMst" property="status" value="Y">
			��Ч
			</logic:equal>
			<logic:equal name="stepMst" property="status" value="N">
			��Ч
			</logic:equal>
		</td>
		<td>
			<input type="button" value="ɾ��" onclick="delete_f2(this.parentElement.parentElement.rowIndex);">
		</td>
	</tr>
	</logic:iterate>
</table>
<TABLE width="500" align="center">
  <tr>
	<td align="right">
	    <input type="button" value="��ӻ��ֵ���" onclick="additem_f2();">
		
	</td>
  </tr>
</TABLE>
<TABLE width="500" align="center">
  <tr>
	<td align="center">
	   <!-- <input type="button" name="nextBtn" value="<<��һ��" onclick="prevStep();"> -->
	   <input type="button" name="nextBtn" value="��һ��>>" onclick="nextStep();">
	   <!-- <input type="button" value=" ���� " onclick="save_step();"> -->
	</td>
  </tr>
</TABLE>
</html:form>
</body>
</html>
