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
<title>�Ϻ�������Ա��ϵ����ϵͳ</title>
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
		alert('��ѯ��������Ϊ��!');
		return false;;
	}
	
	document.memberForm.search.disabled = true;
	//document.memberForm.submit();
	
	//if("<%=service%>" == "1") 
//	{
		//ˢ�¿��"toolframe"
		//document.memberForm.action = "/top.jsp";
		//document.memberForm.target = "toolframe";
		//document.memberForm.submit();

		//ˢ�¿��"menutool"
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font>
			<font color="838383">��></font><font color="838383">��Ա��ѯ</font>
			<font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form  action="/query.do" method="post" onsubmit="return querySubmit();">
<input type="hidden" name="detailFlag" value="detail">
<input type="hidden" name="service" value="1">
<input type="hidden" name="isPageRequest" value="true"><!-- ҳ������ -->
<table class="searchtable">
  <tr>
    <td class="inputLabel">
        ��Ա�ţ�
        <html:text property="CARD_ID" size="10"/>
        ��Ա������
        <html:text property="NAME" size="12"/>
        ��ϵ�绰
        <html:text property="TELEPHONE" size="10"/>
        �ʼ���ַ��
        <html:text property="EMAIL" size="16"/>
        �Ա������ţ�
        <html:text property="taobaoWangId" size="20" />
        <input name="search" type="submit" value=" ��ѯ " />
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
        <td width="10%">��Ա��</td>
        <td width="10%">��Ա����</td>
        <td width="10%">��Ա�ȼ�</td>
        <td width="15%">��ϵ�绰</td>
        <td width="10%">�ʼ���ַ</td>
        <td width="10%">�ʱ�</td>
        <td width="10%">�Ա�������</td>
        <td >��ַ</td>
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
		<logic:equal name="member" property="LEVEL_ID" value="1">��ͨ��Ա</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="2">��ʽ��Ա</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="3">VIP��Ա</logic:equal>
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