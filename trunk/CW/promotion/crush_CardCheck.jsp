<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function setfocus(){
	document.forms[0].cardNum.focus();
}
function checkInput(){
	var form = document.forms[0];
	if(form.cardNum.value == "" || form.pass.value == ""){
		alert("�����������ۿ��ź����룡");
		form.cardNum.select();
		return false;
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">���ۿ����</font><font color="838383"> 
      	</td>
   </tr> 
</table>

<br><br>
<html:form action="/crush_CardCheck.do" method="post" onsubmit="return checkInput();">
  <table align="center" border=0 cellspacing=1 cellpadding=3 >
    <tr>
		
      <td width="80">���ۿ��ţ�</td>
      <td width="*"> 
        <input name = "cardNum" value="" type="text" size=16/>
 	
		</td>
	</tr>
	<tr>
		
      <td>���ۿ����룺</td>
      <td>  
        <input name = "pass" value="" type="text" size=16/>
        
		
		</td>
	</tr>
	<tr>
		
      <td>�Ƿ������</td>
      <td>  
        <input name = "isUse" value="0" type="radio" />��
        <input name = "isUse" value="4" type="radio" checked />��
		
		</td>
	</tr>
	<tr height="60">
		
      <td width="95%" colspan="2" align="center">
      	<input name="check" type="submit" value=" ��� ">
		<input name="cancel" type="reset" value=" ���� ">
		</td>
	</tr>
</table>
</html:form>
 <table align="center" width=80% border=0 cellspacing=1 cellpadding=3 >
 	<tr></td>ע���˹���ֻ��������ƿ���˾���������ۿ����ź������Ƿ���ȫƥ�䣬���ѡ�����������ۿ��Ժ󲻿��Ա�ʹ�ã������Ժ󻹿���ʹ�á�</td></tr>
 </table>
</body>
</html>
