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

function delete_f(row) {
	var frm = document.forms[0];
	if (detailTable.rows.length <= 2)
	{
		alert("����Ҫ��һ����ϸ��ҳ����");
		return;
	}
	//detailTable.deleteRow(row);
	frm.action = "/promotion/expExchangePackage.do?type=deleteDtlLine&forward=add&rowId="+(row-1);
	frm.submit();
}
function additem_f2() {
	var frm = document.forms[0];
	
	frm.action = "/promotion/expExchangePackage.do?type=addDtlLine&forward=add";
	frm.submit();
	
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

	newTd0.innerHTML = "<select name='packageType'><option value=''>ѡ��...</option><option value='T'>��ȯ</option><option value='G'>��Ʒ</option></select>"; 
	newTd1.innerHTML = "<input name='no' size='20'>";  
	
	newTd2.innerHTML= "<input name='quantity' size='2' value='1'>";
	newTd3.innerHTML= "��Ч";
	newTd4.innerHTML= "<input type='button' value='ɾ��' onclick='delete_f(this.parentElement.parentElement.rowIndex);'>";

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	newTd3.bgColor = "#f7f7e7";
	newTd4.bgColor = "#f7f7e7";

	newTd0.setAttribute("align", "left");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "left");
	newTd3.setAttribute("align", "left");
	newTd4.setAttribute("align", "left");
	
}

function add_package() {
	var frm = document.forms[0];
	if (frm.packageNo.value == "")
	{
		alert("�����������!");
		frm.packageNo.focus();
		return;
	}
	if (frm.desc.value == "")
	{
		alert("����������!");
		frm.desc.focus();
		return;
	}
	frm.submit();
}
function list_f() {
	//window.location.href="/promotion/expExchangePackage.do?type=view&packageNo="+packageNo;
	window.location.href="/promotion/expExchangePackage.do?type=query";
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangePackage.do?type=add"  method="POST">
<table width="940" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : �г����� -&gt; ������� </td>
   </tr>
</table>
<table align="center" width="400" >
   <tr>
     <td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
   </tr>
</table>
<table align="center" width="400" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">�����</td>
		<td><html:text property="packageNo"/></td>
	</tr>

	<tr>
		<td class="dataInput">״̬</td>
		<td>
		<logic:equal name="packageForm" property="status" value="Y">
		��Ч
		</logic:equal>
		<logic:equal name="packageForm" property="status" value="N">
		��Ч
		</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">����</td>
		<td><html:textarea cols="24" rows="3" property="desc"/></td>
	</tr>
	<tr>
		<td class="dataInput">URL</td>
		<td><html:text property="url" size="50"/></td>
	</tr>
</table>

<TABLE width="600" align="center">
  <tr>
	<td class="tableLabel">�����ϸ</td>
	<td align="right">
	    <input type="button" value="�����Ʒ/��ȯ" onclick="additem_f2();">
		
	</td>
  </tr>
</TABLE>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap>
		<td width="100">����</td>
		<td width="200">����</td>
		<td width="100">����</td>
		<td width="100">״̬</td>
		<td width="100">����</td>
	</tr>
	<bean:define name="package" property="dtlList" id="packDtlList"/>
	<logic:iterate name="packDtlList" id="packageDtl">
	<tr>
		
		<td>
			<select name="packageType" >
				<option value="">ѡ��...</option>
				<option value="T" <logic:equal name="packageDtl" property="packageType" value="T"> selected</logic:equal>>��ȯ</option>
				<option value="G" <logic:equal name="packageDtl" property="packageType" value="G"> selected</logic:equal>>��Ʒ</option>
			</select>
		</td>
		<td>
			<input name="no" size="20" value="<bean:write name="packageDtl" property="no" />">
		</td>
		<td>
			<input name="quantity" readonly="true" size="2" value="<bean:write name="packageDtl" property="quantity" format="#0"/>">
		</td>
		<td>��Ч</td>
		<td>
			<input type="button" value="ɾ��" onclick="delete_f(this.parentElement.parentElement.rowIndex);">
		</td>
	</tr>
	<!-- <tr>
		<td>
			<select name="packageType">
				<option value="">ѡ��...</option>
				<option value="T">��ȯ</option>
				<option value="G">��Ʒ</option>
			</select>
		</td>
		<td>
			<input name="no" size="20">
		</td>
		<td>
			<input name="quantity" readonly="true" size="2" value="1">
		</td>
		<td>��Ч</td>
		<td>
			<input type="button" value="ɾ��" onclick="delete_f(this.parentElement.parentElement.rowIndex);">
		</td>
	</tr> -->
	</logic:iterate>
	
</table>
<TABLE width="600" align="center">
  <tr>
	<td align="right">
	    <input type="button" value="�����Ʒ/��ȯ" onclick="additem_f2();">
		
	</td>
  </tr>
</TABLE>
<TABLE width="600" align="center">
  <tr>
	<td align="center">
		<input type="button" value=" ���� " onclick="list_f();">
	    <input type="button" value=" ���� " onclick="add_package();">
		
	</td>
  </tr>
</TABLE>
</html:form>
</body>
</html>
