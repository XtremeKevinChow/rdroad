<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
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

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="deliveryFeeSetting.do?" method="POST">

<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : ϵͳ���� -&gt; ���ͷѲ鿴</td>
   </tr>
</table>
<br>
<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput" width="30%">���ͷ�ʽ��</td>
		<td>&nbsp;
		<logic:equal name="deliveryFee" property="deliveryType" value="1">
			�ʼ�
			</logic:equal>
			<logic:equal name="deliveryFee" property="deliveryType" value="2">
			��Ա����
			</logic:equal>
			<logic:equal name="deliveryFee" property="deliveryType" value="3">
			ֱ��
		</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">��Ա�ȼ���</td>
		<td>&nbsp;
		<logic:equal name="deliveryFee" property="levelId" value="-1">
			����
			</logic:equal>
			<logic:equal name="deliveryFee" property="levelId" value="1">
			��ͨ
			</logic:equal>
			<logic:equal name="deliveryFee" property="levelId" value="2">
			��ʽ
			</logic:equal>
			<logic:equal name="deliveryFee" property="levelId" value="3">
			VIP
			</logic:equal>
			
		</td>
	</tr>
	<tr>
		<td class="dataInput">�ʱࣺ</td>
		<td>&nbsp;
		<bean:write name="deliveryFee" property="postcode"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">���ͷѣ�</td>
		<td>&nbsp;
		<bean:write name="deliveryFee" property="fees"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">��ʼ���ڣ�</td>
		<td>&nbsp;
		<bean:write name="deliveryFee" property="beginDate"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">�������ڣ�</td>
		<td>&nbsp;
		<bean:write name="deliveryFee" property="endDate"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">Ҫ���</td>
		<td>&nbsp;
		<bean:write name="deliveryFee" property="requireAmt"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">��ע��</td>
		<td>&nbsp;
		<bean:write name="deliveryFee" property="remark"/>
		</td>
	</tr>
</table>

<TABLE width="500" align="center">
<tr align="center">
	<td >
		<input type="button" name="modifyBtn" value=" ���� " onclick="history.back();"></td>
	</td>
</tr>
</TABLE>
</html:form>
</body>
</html>
