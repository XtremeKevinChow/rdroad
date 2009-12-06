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
String isQuery = request.getParameter("isquery");
isQuery = isQuery == null ? "0" : isQuery;
%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>

<script language="JavaScript" src="../script/dateselect.js"></script>
<title></title>

<script language="JavaScript">
function querySubmit() {

	if(
	document.forms[0].NAME.value==""
	&&document.forms[0].CARD_ID.value==""
	&&document.forms[0].EMAIL.value==""
	&&document.forms[0].CREATE_DATE.value==""
	&&document.forms[0].COMPANY_PHONE.value==""
	&&document.forms[0].MSC_CODE.value==""
	&&document.forms[0].TELEPHONE.value==""
	&&document.forms[0].postcode.value==""
	&&document.forms[0].addressDetail.value==""
	
	
	){
	alert('请输入查询条件!');
	
	return false;
	}
	if(document.forms[0].CREATE_DATE.value!=""){
		var bdate = document.forms[0].CREATE_DATE.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写招募起始日期,并且注意你的日期是否正确!');
				document.forms[0].CREATE_DATE.focus();
				return false;
		 }
 	}
 		if(document.forms[0].COMPANY_PHONE.value!=""){
		var bdate = document.forms[0].COMPANY_PHONE.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写招募结束日期,并且注意你的日期是否正确!');
				document.forms[0].COMPANY_PHONE.focus();
				return false;
		 }
 	}
 	document.forms[0].search.disabled = true;
	//document.forms[0].submit(); //提交了2遍 (commented by user 2008-03-20)
}

function checkAll(bln) {
	var objs = DataTable.getElementsByTagName("INPUT");
	for (var i = 0; i < objs.length; i ++)
	{
		
		if (bln)
		{
			objs[i].checked = true;
		}
		else 
		{
			objs[i].checked = false;
		}
		
	}
	
}
function stopCatalog() {
	document.forms[0].action = "stopCatalog.do";
	document.forms[0].stopBtn.disabled = true;
	document.forms[0].submit();
}
</script>
</head>
<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">高级查询</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/aquery.do" method="post" onsubmit="return querySubmit();">

<table width="95%" class="searchtable">  	
	<tr> 
	  <td >会员号：<html:text property="CARD_ID" /></td>
	  <td >会员姓名：<html:text property="NAME" /></td>
	  <td >EMAIL：<html:text property="EMAIL" /></td>
	  <td >MSC号：<html:text property="MSC_CODE" /></td>
	</tr>
	<tr>  
	  <td >联系电话：<html:text property="TELEPHONE"/></td>	  
	  <td >邮编：<html:text property="postcode"/></td>
	 	  
	  <td>招募开始日期：<html:text property="CREATE_DATE" size="10"/>
	  <a href="javascript:show_calendar(document.forms[0].CREATE_DATE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	  </td>
	  
	  <td >招募结束日期：<html:text property="COMPANY_PHONE" size="10"/>
	  <a href="javascript:show_calendar(document.forms[0].COMPANY_PHONE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	  </td>
	</tr>
	<tr> 	 
	  <td >会员等级：
	  <html:select name="memberForm"   property="LEVEL_ID">
	    <option value="-1">--所有--</option>
	    <html:option value="1">普通会员</html:option>
	    <html:option value="2">正式会员</html:option>
	    <html:option value="3">VIP会员</html:option>
	  </html:select>
	  </td> 
	  <td >地址：<html:text property="addressDetail" />&nbsp;&nbsp;&nbsp;
	  </td>
	  <td colspan=2>
	  <input type="submit" value=" 查询 " name="search"></td>
	</tr>	
</table>
	  </td>
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
<input type="hidden" name="isorg" value="0">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
    <td><bean:write name="memberForm" property="pageNavigator" filter="false"/></td>
  </tr>
</table>

<% if (isQuery.equals("1")) { %>
<table width="95%" align="center" cellspacing="1" border="0">
   <tr>
	  <td align="left"><input type="button" name="stopBtn" value="不发目录" onclick="stopCatalog();"></td>
   </tr>
</table>
<% } %>
<table width="95%" align="center" cellspacing="1" border="0" noWrap  id="DataTable">
	<tr class="OraTableRowHeader">
				<td width="8%"><input type="checkbox" name="all" onclick="checkAll(this.checked);">全选</td>
        <td width="10%">会员号</td>
        <td width="10%">会员姓名</td>
        <td width="8%">会员等级</td>
        <td width="10%">联系电话</td>
        <td width="10%">邮件地址</td>
        <td width="10%">邮编</td>
        <td >地址</td>
    </tr>
 <% int i=0; %>
    <bean:define id="items" name="memberForm" property="items" type="java.util.Collection"/> 
    <logic:iterate name="items" id="member"> 
	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %> >
		<td align="center"><input type="checkbox" name="memberId" value="<bean:write name="member" property="ID"/>"></td>
		<td ><html:link page="/memberDetail.do" paramId="id" paramName="member" paramProperty="ID"><bean:write name="member" property="CARD_ID"/></html:link></td>
		<td ><bean:write name="member" property="NAME"/></td>
		<td >
		<logic:equal name="member" property="LEVEL_ID" value="1">普通会员</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="2">正式会员</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="3">VIP会员</logic:equal>
		</td>
		<td ><bean:write name="member" property="TELEPHONE"/></td>
	  <td  ><bean:write name="member" property="EMAIL"/></td>
		<td  ><bean:write name="member" property="postcode"/></td>
		<td ><bean:write name="member" property="addressDetail"/></td>
	</tr>
	<% i++; %>
</logic:iterate>
</table>
</html:form>
</body>

</html>