<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
String service = (String)request.getAttribute("service");
service = service == null ? "" : service;
%>
<html>
<head>
<title>上海佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

	if(document.memberForm.NAME.value=="" 
	&& document.memberForm.CARD_ID.value==""
	&&document.memberForm.EMAIL.value==""
	&&document.memberForm.TELEPHONE.value==""	
	&& document.memberForm.taobaoWangId.value == ""
	){
		alert('查询条件不能为空!');
		return false;;
	}
	
	document.memberForm.search.disabled = true;
	//document.memberForm.submit();
	
	//if("<%=service%>" == "1") 
//	{
		//刷新框架"toolframe"
		//document.memberForm.action = "/top.jsp";
		//document.memberForm.target = "toolframe";
		//document.memberForm.submit();

		//刷新框架"menutool"
		//document.forms[0].action = "/menu/sel.jsp";
		//document.memberForm.target = "menutool";
		//document.memberForm.submit();
	//}
	return true;
}

function initFocus(){
	document.forms[0].CARD_ID.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="1" cellpadding="0">
    <tr>
      	
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font>
			<font color="838383">—></font><font color="838383">会员查询</font>
			<font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form  action="/query.do" method="post" onsubmit="return querySubmit();">
<input type="hidden" name="detailFlag" value="detail">
<input type="hidden" name="service" value="1">
<input type="hidden" name="isPageRequest" value="true"><!-- 页面请求 -->
<table class="searchtable">
  <tr>
    <td class="inputLabel">
        会员号：
        <html:text property="CARD_ID" size="10"/>
        会员姓名：
        <html:text property="NAME" size="12"/>
        联系电话
        <html:text property="TELEPHONE" size="10"/>
        邮件地址：
        <html:text property="EMAIL" size="16"/>
        淘宝旺旺号：
        <html:text property="taobaoWangId" size="20" />
        <input name="search" type="submit" value=" 查询 " />
    </td>
  </tr>
</table>

<br>
<input type="hidden" name="isquery" value="1">
<input type="hidden" name="isorg" value="0">
<table width="100%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td><bean:write name="memberForm" property="pageNavigator" filter="false"/></td>
  </tr>
</table>
</html:form>


<table width="95%" border=0 cellspacing=1 cellpadding=2 >
    <tr class="OraTableRowHeader">
        <td width="10%">会员号</td>
        <td width="10%">会员姓名</td>
        <td width="10%">会员等级</td>
        <td width="15%">联系电话</td>
        <td width="10%">邮件地址</td>
        <td width="10%">邮编</td>
        <td width="10%">淘宝旺旺号</td>
        <td >地址</td>
    </tr>
            
    <% int i=0; %>
    <bean:define id="items" name="memberForm" property="items" type="java.util.Collection"/> 
    <logic:iterate name="items" id="member"> 
    <tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> >
	  <td >
		<a href="./memberDetail.do?id=<bean:write name="member" property="ID" />&service=1">
		<!-- <html:link page="/memberDetail.do" paramId="id" paramName="member" paramProperty="ID"> -->
		<bean:write name="member" property="CARD_ID"/>
		</a>
		<!-- </html:link> -->
		</td>
		
		<td><bean:write name="member" property="NAME"/></td>
		
		<td>
		<logic:equal name="member" property="LEVEL_ID" value="1">普通会员</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="2">正式会员</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="3">VIP会员</logic:equal>
		</td>
		<td ><bean:write name="member" property="TELEPHONE"/></td>
	    <td ><bean:write name="member" property="EMAIL"/></td>
		<td ><bean:write name="member" property="postcode"/></td>
			<td ><bean:write name="member" property="taobaoWangId"/></td>
		<td ><bean:write name="member" property="addressDetail"/></td>
	</tr>
	<% i++; %>
</logic:iterate>
</table>

</body>
</html>
