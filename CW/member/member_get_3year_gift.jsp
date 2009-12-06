<%@ page contentType="text/html;charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function validate(theForm) {
	if(trim(theForm.cardId.value) == '') {
		alert("请输入会员号！");
		theForm.cardId.focus();
		return;
	}
	
	//document.forms[0].prTypeName.value = document.forms[0].prTypeId.options[document.forms[0].prTypeId.selectedIndex].text;
	document.forms[0].submit();
}

function setCardId(pValue) {
	document.forms[0].cardId.value = pValue;
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000"  onload="javascript:document.forms[0].cardId.focus();">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">获取3周年纪念礼品</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" action="/member/memberGet3YearGift.do?type=insert">
会员号:<input type="text" name="cardId" maxlength="9" size="12"><input name="BtnQuery" value=" 查询 " type="button" onClick="openWin('../member/popQuery.do?isorg=0', 'PopWin', 600, 450);">
<input type="button" value="获取礼品" onclick="validate(document.forms[0]);">
</form>

</body>
</html>
