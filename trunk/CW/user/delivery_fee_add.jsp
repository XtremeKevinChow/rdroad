<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/dateselect.js"></script>

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
function add() {
	var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=add";
	frm.addBtn.disabled = true;
	frm.submit();
}
function changeProvince() {
	var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=selectProvince";
	frm.searchCity.value = "";
	changeCity(frm.searchCity.value);
	frm.submit();
}
function changeCity() {
	var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=selectCity";
	frm.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="deliveryFeeSetting.do?type=add" method="POST">
<input type="hidden" name="forward" value="add">
<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : ϵͳ���� -&gt; ���ͷ�����</td>
   </tr>
</table>
<TABLE width="600" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput"><font color="red">*</font>ʡ��</td>
		<td>
		<html:select property="searchProvince" >
		<html:optionsCollection name="deliveryFeeForm" property="provinceList" value="code" label="name"/> 
		</html:select>
		</td>

	</tr>
	<tr>
		<td class="dataInput">���ͷ�ʽ��</td>
		<td>
		<html:select property="deliveryTypeM">
		<html:option value="-1">����</html:option>
		<html:optionsCollection  property="deliveryTypes" />
		</html:select>
		</td>
    </tr>
    <tr>
		<td class="dataInput">��Ա�ȼ���</td>
		<td>
		<html:select property="levelId">
			<html:option value="-1">����</html:option>
			<html:optionsCollection  property="mbrLevels" />
		</html:select>
		</td>
	</tr>
	<!--
	<tr>
		<td class="dataInput">��ʼ���ڣ�</td>
		<td>
		<html:text property="beginDateM" size="10"/>
		<a href="javascript:show_calendar(document.forms[0].beginDateM)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>

		<td class="dataInput">�������ڣ�</td>
		<td>
		<html:text property="endDateM" size="10"/>
		<a href="javascript:show_calendar(document.forms[0].endDateM)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
	</tr>
	-->
	<tr>
		<td class="dataInput">���ͷѣ�</td>
		<td>
			<html:text property="feeM" size="6" />
		</td>

	<tr>
	<!--
	<tr>
		<td class="dataInput">��ע��</td>
		<td colspan="3">
			<html:textarea property="remarkM" cols="48" rows="3"/>
		</td>
	<tr>
	-->
</table>
<!--
<TABLE width=600 align="center">
<TR>
	<TD class="tableLabel">�����б�</TD>
</TR>
</TABLE>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap>
		<td width="100">���</td>
		<td width="250">�ʱ�</td>
		<td width="250">����</td>
		
	</tr>
	<bean:define id="list" name="deliveryFeeForm" property="detailList"/>
	<%int i = 0;%>
	<logic:iterate name="list" id="list">
	<% i ++;%>
	<tr>
		<td><font color=blue><%=i%></font></td>
		<td>
			&nbsp;<input type="hidden" name="postcode" value=<bean:write name="list" property="postcode"/> ><bean:write name="list" property="postcode"/>
		</td>
		<td>
			&nbsp;<bean:write name="list" property="cityCode"/>
		</td>
	</tr>
	</logic:iterate>
	
</table>
-->
<TABLE width="500" align="center">
<tr align="center">
	<td >
		<input type="button" name="addBtn" value=" �ύ " onclick="add();"></td>
	</td>
</tr>
</TABLE>
</html:form>
</body>
</html>
