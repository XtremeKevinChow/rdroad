<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
/**
function query_f() {
	var name;
	name = document.forms[0].name;
	
	
	document.forms[0].search.disabled = true;
	document.forms[0].submit();
}
*/
function doSelect(id, name) {	
	self.close();
	
	self.opener.setId(trim(id), trim(name));
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >

<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置:</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">积分礼品设置查询</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/memberExpExchangeSetup.do" method="post" onsubmit="return query_f()"> 

<!-- <table width="95%" align="center" border="0" cellspacing="1" cellpadding="5" >
  <tr> 
    <td>
	
	活动名称：
	
      <input type="submit" value="查询"  name="search">
	  <input type="button" value="关闭" onclick="window.close();">
    </td>
  </tr>
</table> -->
</html:form>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td> 
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr> 
    <td></td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <logic:iterate id="exp" name="expList" >
  
  <tr height="26"> 
    <td rowspan="2" bgcolor="#FFFFFF" width="33" valign="top"> 
      <input type="radio" name="ID" value="<bean:write name='exp' property='ID'/>
      " onclick="doSelect(this.value, '<bean:write name="exp" property="name"/>');"> </td>
    <td width="10%" height="26" nowrap>活动名称：</td>
    <td width="20%" bgcolor="#FFFFFF" height="26"><bean:write name="exp" property="name"/></td>
    <td width="10%" height="26" nowrap>开始日期：</td>
    <td width="15%" bgcolor="#FFFFFF" height="26"><bean:write name="exp" property="startDate"/></td>
    <td width="10%" nowrap>结束日期：</td>
    <td  width="15%" bgcolor="#FFFFFF"><bean:write name="exp" property="endDate"/></td>
	<td width="10%" nowrap>兑换类型：</td>
    <td  width="10%" bgcolor="#FFFFFF">
	<logic:equal name="exp" property="expType" value="1">年度兑换</logic:equal>
	<logic:equal name="exp" property="expType" value="2">实时兑换</logic:equal></td>
  </tr>
  
  <tr height="1" bgcolor="#000000"> 
    <td colspan="7" height="1"></td>
  </tr>
   </logic:iterate> 
   
</table>
<TABLE align="center">
	<tr height="40">
		<td align="center" colspan=>
		<input type="button" class="button2" value=" 关闭 " onclick="window.close()">
	</tr>
</TABLE>
</body>
</html>