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
			document.forms[0].action = "/member/mbrGetAward.do?type=expChangeGift";
			document.forms[0].submit();
		}
		
	} else {
		alert("��ѡ���¼!");
	}
}
function query() {
	document.forms[0].action = "/member/mbrGetAward.do?type=showExchangePage";
	document.forms[0].submit();
}

function jsd(a){
 var divArray = document.getElementsByTagName("div");
 for (var i=0;i<divArray.length;i++) {
  if (divArray[i].id == a) {
   divArray[i].style.display='';
  }else {
   divArray[i].style.display='none';
  }
 }
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="/mbrGetAward.do?type=showExchangePage"  method="POST">

<!-- ������Ϣ -->
<bean:define id="exp" name="exp"/>
<bean:define id="member" name="exp" property="member"/>
<!-- ���ֻ��Ϣ -->
<bean:define id="activity" name="activity"/>

<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			��ǰλ�� : ��Ա���� -&gt; ���ֶһ� </td>
   </tr>
</table>
<table  align="center" width="90%" border=0 cellspacing=1 cellpadding=5 >
	<tr >
		<td class="OraTableRowHeader" >��Ա���ţ�
		<html:text  property="cardID" size="10"/>&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;
		��Ա������<bean:write name="member" property="NAME"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="queryBtn" value=" ��ѯ " onclick="query(this)">
		</td>
		
	</tr>
	
	<tr>
		<td>
			<logic:present parameter="member">
				<bean:write name="member" property="NAME"/>(<bean:write name="member" property="CARD_ID"/>)
			</logic:present>
			<logic:equal name="activity" property="exchangeType" value="B">
				���λ�ɶһ����֣�<bean:write name="exp" property="amountExp" format="#0"/>&nbsp;
				��Ա��ǰ���֣�<bean:write name="exp" property="amountExp" format="#0"/>
			</logic:equal>
			<logic:equal name="activity" property="exchangeType" value="A">
				���λ�ɶһ����֣�<bean:write name="exp" property="oldAmountExp" format="#0"/>&nbsp;
				��Ա��ǰ���֣�<bean:write name="exp" property="amountExp" format="#0"/>
			</logic:equal>
			<td align="right"><input type="button" name="addBtn" value=" �һ� " onclick="exchange(this)"></td>
		</td>
		
	</tr>
	
</table>
<TABLE width="90%" align=center>
<TR>
	<TD>&nbsp;���ζһ����<bean:write name="activity" property="activityNo"/>��Ϊ
	<logic:equal  name="activity" property="exchangeType" value="A">һ���Զһ�</logic:equal>
	<logic:equal  name="activity" property="exchangeType" value="B">ʵʱ�һ�</logic:equal>
	
	<logic:equal  name="activity" property="dealType" value="A">�һ����������</logic:equal>
	<logic:equal  name="activity" property="dealType" value="B">���ֿɶ�ζһ�</logic:equal>
	
	</TD>
	
</TR>
</TABLE>
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	<!-- ���ֵ��� -->
	<bean:define id="stepMstList" name="activity" property="mstList"/>
	<logic:iterate id="stepMstList" name="stepMstList" > 
	<tr>
	    <td colspan="2" align="left"><strong><bean:write name="stepMstList" property="beginExp" format="#0"/>���ֿ��Զһ�һ����Ʒ����ȯ</strong></td>
	</tr>
	<tr><td height="5"></td></tr>
	<!-- ���ֵ�����ϸ -->
	<bean:define id="stepDtlList" name="stepMstList" property="dtlList"/>
	<logic:iterate id="stepDtlList" name="stepDtlList" > 
	<logic:equal name="stepDtlList" property="stepType" value="P">
	<tr>
		<td valign="top"><input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> <font color="blue">��� <bean:write name="stepDtlList" property="no"/></font></td>
	</tr>
	<tr>
		<td align=left align="right">
			<TABLE width="90%" border="0" align="right" cellSpacing=0 bordercolorlight="#DD9442" bordercolordark="#ffffff" cellpadding="3">
			
			<!-- ��� -->
			<bean:define id="packMst" name="stepDtlList" property="packMst"/>
			<!-- �����ϸ -->
			<bean:define id="packDtlList" name="packMst" property="dtlList"/>
			
			<logic:iterate id="packDtlList" name="packDtlList" > 
			<TR>
				<TD>
				<logic:equal name="packDtlList" property="packageType" value="G">
				<!-- ��Ʒ -->
				<bean:define name="packDtlList" property="gift" id="gift"/>
				<bean:define name="gift" id="stock" property="stock"/><!-- ��Ʒ��� -->
				��Ʒ
				<bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/>
				(<bean:write name="stock" property="statusName"/> )
				<bean:write name="packDtlList" property="quantity" format="#0" />��
				</logic:equal>
				
				<logic:equal name="packDtlList" property="packageType" value="T">
				��ȯ
				<bean:write name="packDtlList" property="no" />
				<bean:write name="packDtlList" property="quantity" format="#0" />��
				</logic:equal>
				
				</TD>
			</TR>
			
			</logic:iterate>
			</TABLE>
		
		</td>
	 </tr>
	 
	 </logic:equal>
	 <logic:equal name="stepDtlList" property="stepType" value="G">
	 <!-- ��Ʒ -->
	 <bean:define name="stepDtlList" property="gift" id="gift"/>
	 <tr>
		<td valign="top"><input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> 
		<font color="blue">��Ʒ <bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/> 
		</font></td>
		<td></td>
	 </tr>
	 <tr>
    	    <td>
	 <div id="<bean:write name="stepDtlList" property="id" format="#0"/>" style="display: none;" >
    	 
    	    ��ɫ<select ></select>&nbsp;&nbsp;�ߴ�<select ></select>
    	   
	 </div>
	  </td>
    	 </tr>
	 </logic:equal>
	 <logic:equal name="stepDtlList" property="stepType" value="T">
	 <tr>
		<td valign="top">
		<input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> 
		<font color="blue">��ȯ <bean:write name="stepDtlList" property="no" /></font></td>
		<td></td>
	 </tr>
	 </logic:equal>
	</logic:iterate>
	</logic:iterate> 
</table>
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
