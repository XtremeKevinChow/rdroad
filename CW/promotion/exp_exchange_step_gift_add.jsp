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
function delete_f(detailTable,row) {

	if (detailTable.rows.length <= 2)
	{
		alert("����Ҫ��һ����ϸ��ҳ����");
		return;
	}
	detailTable.deleteRow(row);
}
function additem_f(detailTable) {

	//���һ��
	var newTr = detailTable.insertRow();

	//���6��
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	var newTd2 = newTr.insertCell();
	var newTd3 = newTr.insertCell();
	var newTd4 = newTr.insertCell();
	var newTd5 = newTr.insertCell();



	//���������ݺ����� 

	newTd0.innerHTML = "<select><option value=''>ѡ��...</option>"
	<!--<option value='P'>���</option><option value='G'>��Ʒ</option>-->
	+ "<option value='T'>��ȯ</option</select>"; 
	newTd1.innerHTML = "<input type='no' size='20'>";  
	
	newTd2.innerHTML= "��<input type='orderRequire' size='4' value='0' disabled>&nbsp;��<input type='quantity' size='2' value='0'>&nbsp;Ԫ";
	newTd4.innerHTML= "��Ч";
	newTd3.innerHTML= "��<input type='beginDate' size='10' >&nbsp;��<input type='endDate' size='10' >";
	newTd5.innerHTML= "<input type='button' value='ɾ��'onclick='delete_f(detailTable,this.parentElement.parentElement.rowIndex);'>";

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	newTd3.bgColor = "#f7f7e7";
	newTd4.bgColor = "#f7f7e7";
	newTd5.bgColor = "#f7f7e7";

	newTd0.setAttribute("align", "left");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "left");
	newTd3.setAttribute("align", "left");
	newTd4.setAttribute("align", "left");
	newTd5.setAttribute("align", "left");
	
}

function save_step_gift() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=addFinish";
	frm.submit();
}
function prevStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=backShowAddStep";
	frm.submit();
}
function additem_f2(beginExp) {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=addStepGift&forward=add_step_gift&queryExp=" + beginExp;
	frm.submit();
}
function delete_f2(beginExp, rowId) {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=deleteStepGift&forward=add_step_gift&queryExp=" + beginExp+"&rowId=" + (rowId-1);
	frm.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : �г����� -&gt; ���ֵ������� </td>
   </tr>
</table>
<TABLE width="800" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<table align="center" width="800" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">���ֻ�ţ�</td>
		<td><bean:write name="activity" property="activityNo"/></td>
	</tr>
	
	<tr>
		<td class="dataInput">�����</td>
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
<TABLE width="800" align="center">
  <tr>
	<td align="center">
	  <input type="button" name="nextBtn" value="<<��һ��" onclick="prevStep();">
	  <input type="button" value=" ���� " onclick="save_step_gift();">
	</td>
  </tr>
</TABLE>

<bean:define name="activity" property="mstList" id="stepMstList"/>
<logic:iterate name="stepMstList" id="stepMst"><!-- ���ֵ��� -->
<TABLE width="800" align="center">
  <tr>
	<td class="tableLabel">����<font color="red"><bean:write name="stepMst" property="beginExp" format="#0"/>��</font>���ε�������ϸ</td>
	<td align="right">
	    <input type="button" value="���������ϸ" onclick="additem_f2('<bean:write name="stepMst" property="beginExp" format="#0"/>');">
	</td>
  </tr>
</TABLE>
<table align="center" width="800" border="1" cellSpacing=0  cellpadding="3" id="detailTable4">
	<tr class="OraTableRowHeader" noWrap>
		<td width="100">��������</td>
		<td width="150">����</td>
		<!--<td width="160">ʹ������</td>-->
		<!--<td width="300">��Ч����</td>-->
		<td width="70">״̬</td>
		<td width="70">����</td>
	</tr>
	<bean:define name="stepMst" property="dtlList" id="stepDtlList"/>
	<logic:iterate name="stepDtlList" id="stepDtl"><!-- ������ϸ -->
	<tr>
	<input type="hidden" name="stepDtlId" value="<bean:write name="stepDtl" property="id" format="#0"/>">
	<input type="hidden" name="beginExp" value="<bean:write name="stepMst" property="beginExp" format="#0"/>">
		<td>
			<select name="stepType">
				<option value="">ѡ��...</option>
				<option value="G" <logic:equal name="stepDtl" property="stepType" value="G">selected</logic:equal>>��Ʒ</option>
				<option value="T" selected >��ȯ</option>
			</select>
		</td>
		<td>
			<input name="no" size="20" value="<bean:write name="stepDtl" property="no"/>">
		</td>
        <input type=hidden name="addMoney" value="0">
        <input type=hidden name="stepBeginDate" value="<bean:write name="stepDtl" property="beginDate" />">
		<input type=hidden name="stepEndDate" value="<bean:write name="stepDtl" property="endDate" />">
		<td>
		<logic:equal name="stepDtl" property="status" value="Y">
			��Ч
			</logic:equal>
			<logic:equal name="stepDtl" property="status" value="N">
			��Ч
		</logic:equal>
		</td>
		<td>
			<input type="button" value="ɾ��" onclick="delete_f2('<bean:write name="stepMst" property="beginExp" format="#0"/>',this.parentElement.parentElement.rowIndex);">
		</td>
	</tr>
	</logic:iterate><!-- ������ϸ -->
</table>
</logic:iterate><!-- ���ֵ��� -->

<TABLE width="800" align="center">
  <tr>
	<td align="center">
	  <input type="button" name="nextBtn" value="<<��һ��" onclick="prevStep();">
	  <input type="button" value=" ���� " onclick="save_step_gift();">
	</td>
  </tr>
</TABLE>
</html:form>
</body>
</html>
