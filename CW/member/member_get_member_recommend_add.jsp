<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function add_recommend_member() {
	
	document.forms[0].recommendedCardId.value = document.forms[0].MemgetmemID.value;

	if(document.forms[0].recommendedCardId.value==""){
		alert('���Ƽ���Ա�Ų���Ϊ��!');
		document.forms[0].recommendedCardId.focus();
		return ;
	}
	
	if(document.forms[0].itemCode.value==""){
		alert('���Ų���Ϊ��!');
		document.forms[0].itemCode.focus();
		return ;
	}
	
	if(document.forms[0].price.value==""){
		alert('�۸���Ϊ��!');
		document.forms[0].price.focus();
		return ;
	}

	if(isNaN(document.forms[0].price.value)) {
		alert("�۸����Ϊ����");
		document.forms[0].price.focus();
		return ;
	}
	
}
function setId(id, name) {

	document.forms[0].parentID.value = id;
	document.forms[0].parentName.value = name;
	document.forms[0].parentName.focus();
}

function getOpenwinValue(ret){
	document.forms[0].itemCode.value = ret;
	if(document.forms[0].itemCode.value.length >6) {
		document.forms[0].itemCode.value =document.forms[0].itemCode.value.substring(0,6);
	}
}

function setItemInfo(str) {
	document.forms[0].itemCode.value = str;
}

function winopen(url,title) 
{ 
	window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 

function back_f() {
	document.forms[0].action = "/member/memberGetMemberInfo.do?&member_id="+document.forms[0].memberId.value;
	document.forms[0].submit();
}
function init_f() {
	document.forms[0].MemgetmemID.focus();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_f()">

<!-- <table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> <span class="OraHeader"><b>��Ա���� -&gt; �����Ƽ���Ϣ</b></span>
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width="100%" background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table> -->

<html:form method="post" action="/memberGetMemberAdd.do" >
<html:hidden property="cardId" />
<html:hidden property="recommendedId" />
<table border=0 cellspacing=1 width="600" align="center">
	<tr>
	<td align="right"  class="OraTableRowHeader" noWrap >��ȯ����</td>
	<td align="left">
	    <logic:empty name="memberGetMemberForm" property="giftNumber">
	        û����Ч�Ƽ���Ա��ȯ
	    </logic:empty>
	    <logic:notEmpty name="memberGetMemberForm" property="giftNumber">
	    <bean:write name="memberGetMemberForm" property="giftNumber"/>
	    </logic:notEmpty>
	    <html:hidden property="giftNumber"/>
	</td>
	<td align="right"  class="OraTableRowHeader" noWrap >��������</td>
	<td align="left">
	    ������<bean:write name="memberGetMemberForm" property="orderMoney"/>Ԫ����
	    <bean:write name="memberGetMemberForm" property="giftMoney"/>Ԫ
	</td>
	</tr>
	
	<tr>
	<td align="right"  class="OraTableRowHeader" noWrap >��������</td>
	<td align="left">
			<bean:write name="memberGetMemberForm" property="keepDays"/>
	</td>
	<td align="right"  class="OraTableRowHeader" noWrap >��������</td>
	<td align="left">
			<bean:write name="memberGetMemberForm" property="keepDays"/>
			<html:hidden property="keepDays" />
	</td>
    </tr>
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;�Ƽ���</td>
		<td align="left">
			<input name="MemgetmemID" size="10" />&nbsp;
			<input type="button" onClick="javascript:winopen('queryList.do','ѡ������˻�Ա��')" value=" ѡ �� "> 
		</td>
		<td align="center" colspan="2">
		<input name = "submitButton" type="button" onclick="check_recommended_f()" class="button2" value=" ȷ�� " >
		</td>
	</tr>
</table>
</html:form>

</body>
</html>
