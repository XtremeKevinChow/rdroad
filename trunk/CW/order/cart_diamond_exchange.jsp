<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	String isAdd = (String)request.getAttribute("isAdd");
%>
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
	background-color:#ECECD1;
	font-size:12px; 
	color:#000000; 
	padding-left:20px; 
	padding-top:3px; 
	padding-bottom:3px;
	text-align:center;
	font-weight:bold;
}
/* ��ͷ */
.tabletitle2{
	background-color:#DD9442;
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

.style1{ font-size:12px; color:red; font-weight: bold;}
</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function exchange() {
	var selectItem;
	var flag = false;
	var len = detailTable.rows.length;
	for (i = 0; i < len; i ++) {
		var row = detailTable.rows(i);
		var obj = row.getElementsByTagName("INPUT")[0];
		if (typeof(obj) != "undefined")
		{
			if (obj.checked)
			{
				selectItem = obj.value;
				flag = true;
				break;
			}
		}
	}
	if (flag == true)
	{
		if (confirm("ȷʵҪ�һ���?"))
		{
			window.close();
			self.opener.diamond_exchange(selectItem, "<%=isAdd%>");
		}
		
	} else {
		alert("��ѡ���¼!");
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="/orderAddSecond.do"  method="POST">

<!-- ����Ϣ -->
<bean:define id="diamond" name="diamond"/>
<bean:define id="member" name="diamond" property="member"/>
<!-- ����Ϣ -->
<bean:define id="sets" name="sets"/>

<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : ��Ա���� -&gt; �ž���һ� </td>
   </tr>
</table>
<table  align="center" width="90%" border=0 cellspacing=1 cellpadding=5 >
	
	<tr>
		<td>
			<bean:write name="member" property="NAME"/>(<bean:write name="member" property="CARD_ID"/>)
			�ɶһ����������<bean:write name="diamond" property="nomalCount" format="#0"/>&nbsp;
			<td align="right"><input type="button" name="addBtn" value=" �һ� " onclick="exchange(this)"></td>
		</td>
		
	</tr>
	
</table>
<%@ include file="../member/common_diamond_exchange.jsp" %>
<table  align="center" width="90%" border=0 cellspacing=1 cellpadding=5 >
	
	<tr>
		<td align="right">
			<input type="button" name="addBtn" value=" �һ� " onclick="exchange(this)">
		</td>
		
	</tr>
	
</table>
</html:form>
</body>
</html>
