<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function querySubmit() {
	if(document.forms[0].item_ID.value==""){
	alert('��ѯ��������Ϊ��!');
	return false;;
	}
document.forms[0].submit();
}
function popup(str) 
{ 
window.opener.document.all["GIFT_ID"].value=str;
window.close()  
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��Ա����</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ�Ų�ѯ</font>
      	</td>
   </tr>
</table>
<html:form  action="/queryAwardList.do" method="post">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>���ţ�<html:text property="item_ID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="��ѯ"  onclick="querySubmit()"></td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="500"  cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <logic:iterate id="memberGIFT" name="allmemberGIFT" >
	<tr height="26">
		<td width="50">��Ʒ�ţ�</td>
		<td width="200" bgcolor="#FFFFFF"><a href="#" onClick="javascript:popup(<bean:write name='memberGIFT' property='ID'/>)"><bean:write name="memberGIFT" property="ID"/></a></td>
		<td width="50">���ţ�</td>
		<td width="200" bgcolor="#FFFFFF"><bean:write name="memberGIFT" property="item_ID"/></td>
	</tr>
</logic:iterate>
</table>
  </td></tr>
</table>
</body>
</html>
