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
<script language="JavaScript" src="../script/sortTable.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript">
function querySubmit() {

	if(document.memberForm.NAME.value=="" 
	&& document.memberForm.CARD_ID.value==""
	&&document.memberForm.event_type.value==""
	&&document.memberForm.begin_date.value==""
	&&document.memberForm.end_date.value==""
	){
		alert('��ѯ��������Ϊ��!');
		return false;;
	}
	if(document.memberForm.begin_date.value!=""){
		var bdate = document.memberForm.begin_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
				document.memberForm.begin_date.focus();
				return false;
		 }
 	}
 		if(document.memberForm.end_date.value!=""){
		var bdate = document.memberForm.end_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
				document.memberForm.end_date.focus();
				return false;
		 }
 	}	
	document.memberForm.search.disabled = true;
	document.memberForm.submit();
}

function initFocus(){

	if (typeof(DataTable) != "undefined")
	{
		loadSort(DataTable);
	}
	
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
      		-&gt; </font><font color="838383">��Ա��ѯ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form  action="/memberEventList.do" method="post" onsubmit="return querySubmit();">
<table width="80%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td class="OraTableRowHeader">�� Ա �ţ�<html:text property="CARD_ID" size="10"/>&nbsp;&nbsp;&nbsp;
		��Ա������<html:text property="NAME" size="10"/>&nbsp;&nbsp;&nbsp;
		�¼����ͣ�<html:select property="event_type">
         <option value="">��ѡ��</option>
         <html:optionsCollection property="typeList" value="id" label="name"/> 
        </html:select></td>
	</tr>
	<tr>
		<td class="OraTableRowHeader">��ʼ���ڣ�<input type=text name="begin_date" size="10"><a href="javascript:calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		�������ڣ�<input type=text name="end_date" size="10"><a href="javascript:calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a> (��ʽ:YYYY-MM-DD)&nbsp;
		<input type="submit" value=" ��ѯ " name="search" >
		</td>
	</tr>	
</table>
<input type="hidden" name="isquery" value="1">
<%
int i=0;
if (request.getMethod().equals("POST") || (request.getParameter("CARD_ID")!=null && !request.getParameter("CARD_ID").equals("")) ){
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


<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap   id="DataTable">
	<tr>
		<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>���</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>��Ա����</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>��Ա����</td>		
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>�¼�����</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>�¼�����</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>������Ա</td>
		<td   width="" class="OraTableRowHeader" noWrap  noWrap align=middle>��ע</td>
	</tr>
	<bean:define id="a" name="pageModel" property="modelList"/>
  <logic:iterate id="member" name="a" >
	<tr align="center">
		<td width="5%" bgcolor="#FFFFFF"><bean:write name="member" property="CATEGORY_ID" format="#"/></td>
		<td width="10%" bgcolor="#FFFFFF"><a href="../../member/memberDetail.do?id=<bean:write name='member' property='ID' format='#'/>"><bean:write name="member" property="CARD_ID"/></a></td>
		<td width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
		<td width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="address1"/></td>
		<td width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="address"/></td>
		<td  width="10%" bgcolor="#FFFFFF"><bean:write name="member" property="ADDRESS_ID"/></td>
		<td   width="" bgcolor="#FFFFFF"><bean:write name="member" property="addressDetail"/></td>
	</tr>
</logic:iterate>
</table>
<%}%>

</html:form>
</body>
</html>
