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
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
document.forms[0].submit();
}
function initFocus(){
	document.forms[0].item_ID.focus();
	return true;
}

function popup(str) 
{ 

	self.opener.setItemInfo(str);
	self.close()  
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��Ա����</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ�Ų�ѯ</font>
      	</td>
   </tr>
</table>
<html:form  action="/queryGIFTList.do" method="post">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>���ţ�<input type=text name="item_ID" size="10"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="��ѯ"  onclick="querySubmit()">
		</td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="100%"  cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <logic:iterate id="memberGIFT" name="allmemberGIFT" >
	<tr height="26">
		<td width="50">���ţ�</td>
		<td width="60" bgcolor="#FFFFFF"><a href="#" onClick="javascript:popup(<bean:write name='memberGIFT' property='item_ID' format='#'/>)"><bean:write name="memberGIFT" property="item_Code" format='#'/></a></td>	
		<td width="60">��Ʒ���ƣ�</td>
		<td width="220" bgcolor="#FFFFFF"><bean:write name="memberGIFT" property="NAME" /></td>
		<td width="60">��Ʒ�۸�</td>
		<td width="60" bgcolor="#FFFFFF"><bean:write name="memberGIFT" property="price"  format="#0.0"/></td>		

	</tr>
</logic:iterate>
</table>
  </td></tr>
</table>
<TABLE align="center">
<TR>
	<TD><input type="button" value="�ر�" onclick="window.close()"></TD>
</TR>
</TABLE>
</body>
</html>
