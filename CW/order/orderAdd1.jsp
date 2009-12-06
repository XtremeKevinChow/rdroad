<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/ajaxtabs.js"></script>
<script language="JavaScript">
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
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:document.forms[0].cardId.focus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">订单管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增订单</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form name="submitForm" action="../member/memberDetail.do?tab=1" method="POST">
<input type="hidden" name="doc_type" value="2010" >
<table width="98%" border=0 cellspacing=1 cellpadding=2>
  <tr> 
    <td width="27%" align="right" nowrap>&nbsp;会 员 号：</td>
    <td width="73%" align="left" nowrap>      
      <input name="cardId" maxlength="9" size="12"/><input name="BtnQuery" value=" 查询 " type="button" onClick="openWin('../member/popQuery.do?isorg=0', 'PopWin', 600, 450);"></td>
  </tr>
  <!-- <tr> 
    <td width="27%" align="right" nowrap>订单来源：</td>
    <td width="73%" align="left" nowrap>
		
	<select name="prTypeId" style="width:135">
		<option value="2">电话订单</option>
		<option value="1">邮购订单</option>
		<option value="4">门店订单</option>
		<option value="5">团体会员订单</option>
		<option value="6">传真订单</option>
		<option value="7">Email订单</option>
		<option value="8">信件订单</option>
	</select>    
		  
      
    </td>
  </tr> -->
  <tr> 
    <td width="27%" align="right" nowrap >&nbsp;</td>
    <td width="73%" align="left" nowrap >&nbsp; </td>
  </tr>
  <tr>
    <td width="27%" align="right" nowrap>&nbsp;</td>
    <td width="73%" align="left" nowrap> 
      <input name="BtnNextStep" type="button" onclick="validate(document.forms[0]);" class="button2" value="下一步 &gt;&gt;">
    </td>
  </tr>
</table>
</form>
</body>
</html>
