<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.magic.crm.member.dao.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();

%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title></title>
<script language="JavaScript">

function popup(str){ 
window.opener.document.all["MSC_CODE"].value=str;
window.close()  
} 

</script>
</head>
<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">会员管理</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">会员MSC列表</font> 
      	</td>
   </tr>
</table>
<br>
<html:form  action="/queryMSCList.do" method="post">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>MSC号码：<html:text property="MSC"/>&nbsp;&nbsp;MSC名称：<html:text property="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="查询"></td>
		
	</tr>
</table>
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="500"  cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
		<td width="120">MSC号码：</td>
		<td width="*">MSC名称：</td>
  <logic:iterate id="memberPriceList" name="memberMSC" >
	<tr height="26">
		<td  bgcolor="#FFFFFF">

		<a href="#" onClick="javascript:popup('<bean:write name='memberPriceList' property='MSC'/>')"><bean:write name="memberPriceList" property="MSC"/></a></td>
		
		<td  bgcolor="#FFFFFF"><bean:write name="memberPriceList" property="name"/></td>
	</tr>
</logic:iterate>
</table>
  </td></tr>
</table>      
</body>

</html>