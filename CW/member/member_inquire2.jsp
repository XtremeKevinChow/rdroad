<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*,com.magic.crm.user.entity.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.magic.crm.member.entity.Member"%>
<%@ page import="java.text.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript">
function winFocus(){
	document.forms[0].MemgetmemID.focus();
	return true;
	
}
function addSubmit() {
		if(document.forms[0].MemgetmemID.value==""){
		alert('��Ա���������д!');
		document.forms[0].MemgetmemID.select();
		return false;
		}

		if(isNaN(document.forms[0].MemgetmemID.value)){
		alert('��Ա�������Ϊ����!');
		document.forms[0].MemgetmemID.select();
		return false;
		}
	
}
function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000"  onload="javascript:winFocus()">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ԱͶ��</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form method="post" action="/initInquiryCreate3.do" onsubmit="return addSubmit();">

<table  border=0 cellspacing=1 cellpadding=1  width="700" align="center" >
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ա����</td>
		<td align="left" >
<input type="text"  name="MemgetmemID" maxlength="10" value=""> 
<input type="button" onClick="javascript:winopen('queryList.do','ѡ���Ա��')" value="ѡ��"> 			
		</td>
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input type="submit"  value="ȷ��"> &nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
	</tr>
</table>
<input type="hidden" name="tag" value="2">
</html:form>

</body>
</html>
