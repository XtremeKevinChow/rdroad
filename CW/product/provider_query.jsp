<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    response.setHeader("expires","0");
    response.setHeader("Cache-Control", "no-store"); //http1.1
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache"); //http1.0
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
if (document.forms[0].providerName.value=="" && document.forms[0].connecter.value==""){
	alert("�������ѯ������");
	return false;
}
return true;
}
function selectItem(){
	var selectedid = getSelectedItem();
	if(selectedid !="") {
		if(opener.document.forms[0].flag.value == "provider")
			opener.document.forms[0].supplierID.value=selectedid;
		else
			opener.document.forms[0].publishingHouse.value=selectedid;
		self.close();
	}
	
}
function setfocus(){
	document.forms[0].providerName.focus();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000"  onload="return setfocus();">

<html:form action="/providerQuery.do" method="post" onsubmit="return checkInput();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ӧ�̲�ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>


  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">
      	��Ӧ�����ƣ�<html:text property="providerName" size="22"/> 
        ��ϵ�ˣ�<html:text property="connecter" size="12"/>
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" ��ѯ ">
		<input name="actn" type="hidden" value="1">
		</td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<%

if (request.getMethod().equals("POST")){
%>


  <table width="95%" onclick="changeItem()" ondblclick="selectItem()" align="center" border=0 cellspacing=1 cellpadding=2 >
    <tr height="26"> 
    	
	  <th width="60%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >��Ӧ������</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >��ϵ��</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >����</th>
	</tr>
    
    <bean:define id="modelList" name="<%=Constants.PROVIDER_LIST%>"/>
    <logic:iterate id="provider" name="modelList">
    
    <tr style="cursor:hand" id ="<bean:write name="provider" property="ID" filter="false"/>"> 
    	<td align=left ><bean:write name="provider" property="providerName" filter="false"/></td>
      <td align=left ><bean:write name="provider" property="connecter" filter="false"/></td>
      <td align=left ><bean:write name="provider" property="city" filter="false"/></td>
    </tr>
    </logic:iterate>
    <tr><td colspan="3"><font color="red">ʹ��˵��:��˫��ѡ�в�Ʒ���ÿ�β�ѯֻ����ʾ���30����¼��</font></td></tr>
  </table>
  
<%
}
%>
</html:form>


</body>
</html>
