<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/ajaxtabs.js"></script>
<script language="JavaScript">
function validate(theForm) {
	if(trim(theForm.cardId.value) == '') {
		alert("�������Ա�ţ�");
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form name="submitForm" action="../member/memberDetail.do?tab=1" method="POST">
<input type="hidden" name="doc_type" value="2010" >
<table width="98%" border=0 cellspacing=1 cellpadding=2>
  <tr> 
    <td width="27%" align="right" nowrap>&nbsp;�� Ա �ţ�</td>
    <td width="73%" align="left" nowrap>      
      <input name="cardId" maxlength="9" size="12"/><input name="BtnQuery" value=" ��ѯ " type="button" onClick="openWin('../member/popQuery.do?isorg=0', 'PopWin', 600, 450);"></td>
  </tr>
  <!-- <tr> 
    <td width="27%" align="right" nowrap>������Դ��</td>
    <td width="73%" align="left" nowrap>
		
	<select name="prTypeId" style="width:135">
		<option value="2">�绰����</option>
		<option value="1">�ʹ�����</option>
		<option value="4">�ŵ궩��</option>
		<option value="5">�����Ա����</option>
		<option value="6">���涩��</option>
		<option value="7">Email����</option>
		<option value="8">�ż�����</option>
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
      <input name="BtnNextStep" type="button" onclick="validate(document.forms[0]);" class="button2" value="��һ�� &gt;&gt;">
    </td>
  </tr>
</table>
</form>
</body>
</html>
