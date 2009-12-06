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
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
if (document.forms[0].providerName.value=="" && document.forms[0].connecter.value==""){
	alert("请输入查询条件！");
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">供应商查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>


  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">
      	供应商名称：<html:text property="providerName" size="22"/> 
        联系人：<html:text property="connecter" size="12"/>
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" 查询 ">
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
    	
	  <th width="60%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >供应商名称</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >联系人</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >城市</th>
	</tr>
    
    <bean:define id="modelList" name="<%=Constants.PROVIDER_LIST%>"/>
    <logic:iterate id="provider" name="modelList">
    
    <tr style="cursor:hand" id ="<bean:write name="provider" property="ID" filter="false"/>"> 
    	<td align=left ><bean:write name="provider" property="providerName" filter="false"/></td>
      <td align=left ><bean:write name="provider" property="connecter" filter="false"/></td>
      <td align=left ><bean:write name="provider" property="city" filter="false"/></td>
    </tr>
    </logic:iterate>
    <tr><td colspan="3"><font color="red">使用说明:请双击选中产品类别。每次查询只能显示最多30条记录。</font></td></tr>
  </table>
  
<%
}
%>
</html:form>


</body>
</html>
