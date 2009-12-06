<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
String isorg=request.getParameter("isorg");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
if(document.deliveryInfoForm.mbName.value==""
    &&document.deliveryInfoForm.cardId.value==""
    &&document.deliveryInfoForm.phone.value=="" )
{
    alert('查询条件不能为空!');
    return ;
}

document.deliveryInfoForm.submit();
}

function copyMbr() {
    if (document.forms[0].mbId.value == "0") {
        alert("请选择会员");
        return;
    }
    document.forms[0].action="deliveryInfoCopy.do?type=copy";
    document.forms[0].submit();
	
}

function list_addr() {	
	document.forms[0].action="deliveryInfoList.do?mbId=" + document.forms[0].mb2Id.value;
	document.forms[0].submit();
	
}
function setMbrId(mbid) {
    document.forms[0].mbId.value = mbid;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置:</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">复制会员信息</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/deliveryInfoCopy.do?type=query" method="post"> 
<html:hidden property="page"/>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
  <tr> 
    <td>会员号：<html:text property="cardId" size="8"/>&nbsp;
        会员姓名：<html:text property="mbName" size="8"/>&nbsp;
        电话：<html:text property="phone" size="12"/>&nbsp;
      <input type="button" value=" 查询 "  onclick="querySubmit()">
      <input type="button" value=" 确定 " onclick="copyMbr();" >
      <input type="button" value=" 取消 " onclick="list_addr();">
    </td>
  </tr>
</table>
<html:hidden property="mbId"/>
<html:hidden property="mb2Id"/>
</html:form> 
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td> 
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <bean:define name="list" id="items" type="java.util.Collection"/> 
  <logic:iterate name="items" id="member"> 
  <tr height="26"> 
    <td rowspan="2" bgcolor="#FFFFFF" width="33" valign="top"> 
      <input type="radio" name="CARD_ID" value="<bean:write name="member" property="ID"/>" onclick="setMbrId(<bean:write name="member" property="ID"/>)"> </td>
    <td width="75" height="26" nowrap>会员号：</td>
    <td width="100" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="CARD_ID"/></td>
    <td width="39" height="26" nowrap>姓名：</td>
    <td width="117" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="NAME"/></td>
    <td width="74" nowrap>会员等级：</td>
    <td  width="248" bgcolor="#FFFFFF"> 
      <logic:equal name="member" property="OLD_LEVEL" value="1">普通会员</logic:equal>	
      <logic:equal name="member" property="OLD_LEVEL" value="2">正式会员</logic:equal>	
      <logic:equal name="member" property="OLD_LEVEL" value="3">VIP会员</logic:equal>	
    </td>
  </tr>
  <tr height="26"> 
    <td width="75" height="26" nowrap>联系电话：</td>
    <td width="100" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="FAMILY_PHONE"/></td>
    <td width="39" nowrap>地址：</td>
    <td  bgcolor="#FFFFFF" colspan="3"><bean:write name="member" property="addressDetail"/></td>
  </tr>
  <tr height="1" bgcolor="#000000"> 
    <td colspan="7" height="1"></td>
  </tr>
  </logic:iterate> 
  
</table>

</body>
</html>