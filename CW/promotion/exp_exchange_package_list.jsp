<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
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
function view_f(packageNo) {
	window.location.href="/promotion/expExchangePackage.do?type=view&packageNo="+packageNo;
}
function show_add_package() {
	window.location.href="/promotion/expExchangePackage.do?type=showAddPage";
}
function delete_f(packageNo) {
	if (confirm("ȷʵҪɾ����"))
	{
		location.href="/promotion/expExchangePackage.do?type=delete&packageNo="+packageNo;
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangePackage.do?type=query"  method="POST">
<bean:define name="packageForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<table width="600" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : �г����� -&gt;����б� </td>
   </tr>
</table>

<TABLE width="600" align="center">
  <tr>
	
  </tr>
</TABLE>

<table width="600" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
		<td align="left">
			<input type="button" value=" ���� " onclick="show_add_package();">
		</td>
		<td><font color="red" id="ajaxMessage">
		<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
		</font></td>
	</tr>
</table>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap >
		<td width="80">�����</td>
		<td width="200">�������</td>
		<td width="60">״̬</td>
		<td width="60">����</td>
	</tr>
	<logic:iterate name="list" id="list">
	<tr class=OraTableCellText noWrap>
		
		<td><a href="javascript:view_f('<bean:write name="list" property="packageNo"/>');"><bean:write name="list" property="packageNo"/></a></td>
		<td><bean:write name="list" property="desc"/></td>
		
		<td>
		<logic:equal name="list" property="status" value="Y">
		��Ч
		</logic:equal>
		<logic:equal name="list" property="status" value="N">
		��Ч
		</logic:equal>
		</td>
		<td><input type="button" value="ɾ��" onclick=delete_f('<bean:write name="list" property="packageNo"/>')></td>
	</tr>
	</logic:iterate>
</table>
<table width="600" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>

</html:form>
</body>
</html>
