<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@page import="com.magic.crm.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String iscallcenter = request.getParameter("iscallcenter");
String name="";
String id="";
String s_card_id="";
if (iscallcenter != null && iscallcenter.equals("1")) {
	Member mb = (Member)request.getSession().getAttribute(Constants.CURRENT_MEMBER_KEY);
	if (mb != null) {
		id = String.valueOf(mb.getID());
		s_card_id = mb.getCARD_ID();
		name = mb.getNAME();
	}
} else {
	s_card_id=request.getParameter("s_card_id");
	if(s_card_id==null){
		s_card_id="";
	}
}
String type=request.getParameter("type");
/**
String s_card_id=request.getParameter("s_card_id");
if(s_card_id==null){
s_card_id="";
}
*/
if(type==null){
type="0";
}
String IS_SOLVE=request.getParameter("IS_SOLVE");
if(IS_SOLVE==null){
	IS_SOLVE="0";
}
String deptid=request.getParameter("deptid");
if(deptid==null){
	deptid="";
}
String SOLVE_DATE=request.getParameter("SOLVE_DATE");
if(SOLVE_DATE==null){
	SOLVE_DATE="";
}
String SOLVE_DATE2=request.getParameter("SOLVE_DATE2");
if(SOLVE_DATE2==null){
	SOLVE_DATE2="";
}
String INQUIRY_TYPE=request.getParameter("INQUIRY_TYPE");
if(INQUIRY_TYPE==null){
	INQUIRY_TYPE="";
}
String SOLVE_PERSON=request.getParameter("SOLVE_PERSON");
if(SOLVE_PERSON==null){
	SOLVE_PERSON="";
}
String IS_ANSWER = request.getParameter("IS_ANSWER");
if(IS_ANSWER == null){
	IS_ANSWER = "";
}
String is_fenye=request.getParameter("is_fenye");
if(is_fenye==null){
	is_fenye="";
}
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language=javascript>
      function ref(){
        document.inquiryForm.action="../member/InquiryRef.do";
        document.inquiryForm.submit();
      }
function querySubmit() {
		if(document.inquiryForm.SOLVE_DATE.value!=""){
		var bdate = document.inquiryForm.SOLVE_DATE.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 				
		 if(bdate==null){
				alert('�밴��ʽ��д����,����ע����������Ƿ���ȷ!');
				document.inquiryForm.SOLVE_DATE.focus();
				return false;
		 }	
		}
		if(document.inquiryForm.SOLVE_DATE2.value!=""){
		var bdate2 = document.inquiryForm.SOLVE_DATE2.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 				
		 if(bdate2==null){
				alert('�밴��ʽ��д����,����ע����������Ƿ���ȷ!');
				document.inquiryForm.SOLVE_DATE2.focus();
				return false;
		 }	
		}	
document.inquiryForm.search.disabled = true;			
document.inquiryForm.submit();
}


function load_page() {
	if ("<%=s_card_id%>" != null && "<%=s_card_id%>" != "")
	{
		document.forms[0].search.attachEvent("onClick");
	}
}
    //-->
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ�߲�ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form  action="/InquiryList2.do?is_fenye=1" method="post" onsubmit="return querySubmit();">
<input type="hidden" name="iscallcenter" value="<%=request.getAttribute("iscallcenter")%>">
<input type="hidden" name="tag" value="6">
<table  border=0 cellspacing=1 cellpadding=1  width="95%" align="center" >
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">��Ա�ţ�</td>
		<td colspan="3" width="35%">
			<input type="text" name="s_card_id"  size="12" value="<%=s_card_id%>">&nbsp;&nbsp;
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">���ͣ�</td>
		<td colspan="3">
			<input type="radio" name="IS_SOLVE" value="1" <%if(IS_SOLVE.equals("1")){%>checked<%}%> onclick="ref()">Ͷ��
			<input type="radio" name="IS_SOLVE" value="0" <%if(IS_SOLVE.equals("0")){%>checked<%}%> onclick="ref()">��ѯ
			
		</td>
	</tr>	
	<%if(IS_SOLVE.equals("1")){%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  >Ͷ�߲��ţ�</td>
		<td colspan="3" width="35%">
			<select name="deptid" onchange="ref()">
				<option value="" <%if(deptid.equals("")){%>selected<%}%> >--����--</value>
			  	<option value="1" <%if(deptid.equals("1")){%>selected<%}%> >�г���</value>
				<option value="2" <%if(deptid.equals("2")){%>selected<%}%> >�ͷ���</value>
				<option value="3" <%if(deptid.equals("3")){%>selected<%}%> >�༭��</value>
				<option value="4" <%if(deptid.equals("4")){%>selected<%}%> >����������</value>
			  	<option value="5" <%if(deptid.equals("5")){%>selected<%}%> >IT��</value>
				<option value="6" <%if(deptid.equals("6")){%>selected<%}%> >������</value>
				<option value="7" <%if(deptid.equals("7")){%>selected<%}%> >�ܾ�����</value>
				<option value="8" <%if(deptid.equals("8")){%>selected<%}%> >����</value>
				<option value="9" <%if(deptid.equals("9")){%>selected<%}%> >��վ</value>						
			</select>		
		</td>

	<%}else{%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >��ѯ���ࣺ</td>
		<td colspan="3">
			<select name="deptid" onchange="ref()">
				<option value="" <%if(deptid.equals("")){%>selected<%}%> >--����--</value>
			  	<option value="1" <%if(deptid.equals("1")){%>selected<%}%> >��ѯ�������</value>
				<option value="2" <%if(deptid.equals("2")){%>selected<%}%> >��ѯ��Ա��Ϣ</value>
				<option value="3" <%if(deptid.equals("3")){%>selected<%}%> >�ʻ���ѯ</value>
				<option value="4" <%if(deptid.equals("4")){%>selected<%}%> >��ѯ�г��</value>		
				<option value="5" <%if(deptid.equals("5")){%>selected<%}%> >����ͽ���</value>				
			</select>
		</td>
		
	<%}%>

		<td align="right" class="OraTableRowHeader" noWrap >
		<%if(IS_SOLVE.equals("1")){%>	
		Ͷ�����ͣ�
		<%}else{%>
		��ѯ���ͣ�
		<%}%>
		</td>
		<td colspan="3">
			<html:select property="INQUIRY_TYPE">
				<option value="" >--����--</value>
			  <html:options collection="InquiryType" property="inquiryID" labelProperty="inquiryName"/>
			</html:select>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">״̬��</td>
		<td colspan="3" width="35%">
		<select name="type" >
		      <option value="0" <%if(type.equals("0")){%>selected<%}%> >δ���</option>	
		      <option value="1" <%if(type.equals("1")){%>selected<%}%> >�ѽ��</option>	
		</select>
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">�����ˣ�</td>
		<td colspan="3">
				<html:select property="SOLVE_PERSON">		
    					<option value="">--����--</option>
	                    <html:options collection="colUser" property="id" labelProperty="NAME"/>
		        </html:select>					
		</td>
	</tr>		
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">Ͷ�����ڣ�</td>
		<td colspan="3" width="35%">
			<input type=text name="SOLVE_DATE" value="<%=SOLVE_DATE%>" size="10">
			<a href="javascript:calendar(document.forms[0].SOLVE_DATE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a> 
			��
			<input type=text name="SOLVE_DATE2" value="<%=SOLVE_DATE2%>"size="10">
			<a href="javascript:calendar(document.forms[0].SOLVE_DATE2)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">�Ƿ�Ҫ�ظ���</td>
		<td colspan="3" width="35%">
		<select name="IS_ANSWER" >
		      <option value="1" <%if(IS_ANSWER.equals("1")){%>selected<%}%> >��</option>	
		      <option value="0" <%if(IS_ANSWER.equals("0")){%>selected<%}%> >��</option>	
		</select>
		<input type="submit" name="search" value=" ��ѯ ">&nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
		</td>
		
	</tr>		
	
</table>

<input type="hidden" name="isquery" value="1">
<%//&& !s_card_id.equals("")
int i=0;
if (is_fenye.length()>0 ){
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


<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#003366" >
	<tr >
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >ʱ��</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >Ͷ������</th>
		
		<th width="*%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >״̬</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
	</tr>
	<bean:define id="a" name="pageModel" property="modelList"/>
  <logic:iterate id="memberInquiry" name="a" >	


	<tr>
		<td bgcolor="#FFFFFF" align=middle >
		<a href="InquiryDetail.do?createid=<bean:write name="memberInquiry" property="CREATEID"/>
		&card_id=<bean:write name="memberInquiry" property="MEMBERID"/>
		&tag=2&is_query=0
		&s_card_id=<%=s_card_id%>&deptid=<%=deptid%>
		&type=<%=type%>&INQUIRY_TYPE=<%=INQUIRY_TYPE%>
		&SOLVE_DATE=<%=SOLVE_DATE%>
		&SOLVE_DATE2=<%=SOLVE_DATE2%>
		&SOLVE_PERSON=<%=SOLVE_PERSON%>
		">
		<bean:write name="memberInquiry" property="CREATEID"/></a></td>
		
		
		<td bgcolor="#FFFFFF" align=middle ><a href="../member/memberDetail.do?s_card_id=<bean:write name="memberInquiry" property="MEMBERID"/>"><bean:write name="memberInquiry" property="MEMBERID"/></a></td>
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="memberInquiry" property="SOLVE_DATE"/></td>
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="memberInquiry" property="inquiryName"/></td>
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="memberInquiry" property="SOLVE_METHOD"/></td>
		<td bgcolor="#FFFFFF" align=middle >
		<logic:equal name="memberInquiry" property="status" value="0">δ���</logic:equal>
		<logic:equal name="memberInquiry" property="status" value="1">�ѽ��</logic:equal>
		<logic:equal name="memberInquiry" property="status" value="2">�ͷ�ȷ���ѽ��</logic:equal>

		</td>	
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="memberInquiry" property="SOLVE_PERSON"/></td>	
	</tr>
</logic:iterate>
</table>
<%}%>
</html:form>
</body>
</html>
