<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
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

	if(document.memberForm.NAME.value=="" 
	&& document.memberForm.CARD_ID.value==""
	){
		alert('��ѯ��������Ϊ��!');
		return false;;
	}
	document.memberForm.search.disabled = true;
	document.memberForm.submit();
}

function initFocus(){
	document.forms[0].NAME.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�����Ա��ѯ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form  action="/memberOrgList.do" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>�����Ա�ţ�<html:text property="CARD_ID" size="12"/>&nbsp;&nbsp;��˾���ƣ�<html:text property="NAME" size="16"/>&nbsp;&nbsp;&nbsp;&nbsp;
	
			<input type="submit" value=" ��ѯ " name="search" >
		</td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
<%
int i=0;
if (request.getMethod().equals("POST")){
%>
<%@ include file = "../common/page.jsp" %>
<bean:define id="pageModel" name="memberPageModel" scope="request" type="CommonPageUtil"/>
<%
    //ȡ���ܼ�¼����ҳ����
     int totalNum= 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>


<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">

		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>�����Ա����</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>��˾����</td>		
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>��ַ</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>�ʱ�</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>��ϵ�绰</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>��ϵ�绰��</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>��ϵ��</td>
	</tr>
	<bean:define id="a" name="pageModel" property="modelList"/>
  <logic:iterate id="member" name="a" >
	<tr align="center">
		<td width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="CARD_ID"/></td>
		<td width="20%" bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
		<td width="20%" bgcolor="#FFFFFF"><bean:write name="member" property="addressDetail"/></td>
		<td width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="postcode"/></td>
		<td width="15%" bgcolor="#FFFFFF"><bean:write name="member" property="TELEPHONE"/></td>
		<td width="15%" bgcolor="#FFFFFF"><bean:write name="member" property="FAMILY_PHONE"/></td>
		<td width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="address1"/></td>
	</tr>
</logic:iterate>
</table>
<%}%>
</html:form>
</body>
</html>
