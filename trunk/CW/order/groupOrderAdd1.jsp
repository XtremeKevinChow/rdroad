<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function validate(theForm) {
	if(trim(theForm.cardId.value) == '') {
		alert("请输入会员号！");
		theForm.cardId.focus();
		return false;
	}
	
	//document.forms[0].prTypeName.value = //document.forms[0].prTypeId.options[document.forms[0].prTypeId.selectedIndex].text;
	return true;
}

function setCardId(pValue) {
	document.forms[0].cardId.value = pValue;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table width="100%" border="0" cellspacing="0" cellpadding="0" onload="document.forms[0].btnsearch.disabled;">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 新增团体订单</font></td>
  </tr>
</table>
<html:form action="/groupOrderAdd.do?type=addSecond" onsubmit="return validate(this);">
<input type="hidden" name="doc_type" value="2010" >
<!-- 市场销售销售 whichStock = sales -->
<input name="whichStock" type="hidden" value="<%= request.getAttribute("whichStock") %>">
<table width="98%" border=0 cellspacing=1 cellpadding=2>
  <tr> 
    <td width="27%" align="right" nowrap>&nbsp;会 员 号：</td>
    <td width="73%" align="left" nowrap>      
      <html:text name="orderForm" property="cardId" maxlength="9" size="12"/><input name="BtnQuery" value=" 查询 " type="button" onClick="openWin('../member/popQuery.do?isorg=1', 'PopWin', 600, 450);"></td>
  </tr>
  <tr> 
    <td width="27%" align="right" nowrap >&nbsp;</td>
    <td width="73%" align="left" nowrap >&nbsp; </td>
  </tr>
  <tr>
    <td width="27%" align="right" nowrap>&nbsp;</td>
    <td width="73%" align="left" nowrap> 
      <input name="BtnNextStep" type="submit" class="button2" value="下一步 &gt;&gt;">
    </td>
  </tr>
</table>
</html:form>
</body>
</html>
