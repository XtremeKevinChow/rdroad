<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="com.magic.crm.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String name="";
String id="";
String card_id="";
Member mb = (Member)request.getSession().getAttribute(Constants.CURRENT_MEMBER_KEY);
if (mb != null) {
	id = String.valueOf(mb.getID());
	card_id = mb.getCARD_ID();
	name = mb.getNAME();
}
/**
String name="";
String id="";
String card_id="";
String tag=request.getParameter("tag");
if(tag.equals("1")){
	name=request.getParameter("name");
	id=request.getParameter("id");
	card_id=request.getParameter("card_id");
}else{
 	tag="5";
	Member member=new Member();
	member=(Member)request.getAttribute("member");
	name=member.getNAME();
	id=String.valueOf(member.getID());
	card_id=member.getCARD_ID();
}
*/
String IS_SOLVE=request.getParameter("IS_SOLVE");
if(IS_SOLVE==null){
	IS_SOLVE="0";
}
String IS_ANSWER=request.getParameter("IS_ANSWER");
if(IS_ANSWER==null){
	IS_ANSWER="0";
}
String deptid=request.getParameter("deptid");
if(deptid==null){
	deptid="2";
}
String SOLVE_METHOD=request.getParameter("SOLVE_METHOD");
if(SOLVE_METHOD==null){
	SOLVE_METHOD="";
}
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language=javascript>
function ref(){
       document.inquiryForm.action="../member/InquiryRef.do";
       document.inquiryForm.submit();
}

function addSubmit(){
	if(document.inquiryForm.INQUIRY_TYPE.value==""){
		alert("û��Ͷ������,������ѡ��");
		document.inquiryForm.INQUIRY_TYPE.focus();
			return false;
	}
	if (document.forms[0].IS_SOLVE[1].checked)
	{
		document.forms[0].IS_ANSWER[1].checked = true;
		document.forms[0].IS_ANSWER[1].disabled = false;
	}
	
	return;

}
function init_page() {

	if(document.forms[0].IS_SOLVE[1].checked) { 
	
		document.forms[0].IS_ANSWER[0].checked = false;
		document.forms[0].IS_ANSWER[0].disabled = true;
		
		document.forms[0].IS_ANSWER[1].checked = true;
		document.forms[0].IS_ANSWER[1].disabled = true;
		
	}
}
 //-->
</script>    
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_page()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ͻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ԱͶ������ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>

<html:form method="post" action="/InquiryCreate.do" onsubmit="return addSubmit();">
<table  border=0 cellspacing=1 cellpadding=1  width="95��" align="center" >

	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;��Ա���룺</td>
		<td width="*%" align="left" colspan="1" width="20%" ><%=card_id%>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;��Ա������</td>
		<td  align="left" width="40%"><%=name%>&nbsp;
		</td>
	</tr>

	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;���ݣ�</td>
		<td align="left" colspan="3">
			<textarea cols=70 rows=5 name="SOLVE_METHOD" ><%=SOLVE_METHOD%></textarea>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >���ͣ�</td>
		<td colspan="3">
			<input type="radio" name="IS_SOLVE" value="1" <%if(IS_SOLVE.equals("1")){%>checked<%}%> onclick="ref()">Ͷ��
			<input type="radio" name="IS_SOLVE" value="0" <%if(IS_SOLVE.equals("0")){%>checked<%}%> onclick="ref()">��ѯ
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�Ƿ���Ҫ�ظ���</td>
		<td colspan="3">
			<input type="radio" name="IS_ANSWER" value="1" <%if(IS_ANSWER.equals("1")){%>checked<%}%>>��
			<input type="radio" name="IS_ANSWER" value="0" <%if(IS_ANSWER.equals("0")){%>checked<%}%>>��
			
		</td>
	</tr>	
	<%if(IS_SOLVE.equals("1")){%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >Ͷ�߲��ţ�</td>
		<td colspan="3">
		<select name="deptid" onchange="ref()">
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
	</tr>	
	<%}else{%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >��ѯ���ࣺ</td>
		<td colspan="3">
		<select name="deptid" onchange="ref()">
		  <option value="1" <%if(deptid.equals("1")){%>selected<%}%> >��ѯ�������</value>
			<option value="2" <%if(deptid.equals("2")){%>selected<%}%> >��ѯ��Ա��Ϣ</value>
			<option value="3" <%if(deptid.equals("3")){%>selected<%}%> >�ʻ���ѯ</value>
			<option value="4" <%if(deptid.equals("4")){%>selected<%}%> >��ѯ�г��</value>			
			<option value="5" <%if(deptid.equals("5")){%>selected<%}%> >����ͽ���</value>			
			</select>
		</td>
	</tr>		
	<%}%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >
		<%if(IS_SOLVE.equals("1")){%>	
		Ͷ�����ͣ�
		<%}else{%>
		��ѯ���ͣ�
		<%}%>
		</td>
		<td colspan="3">
			<html:select property="INQUIRY_TYPE">
				<option value="">-- ��ѡ�� --</option>
			  <html:options collection="InquiryType" property="inquiryID" labelProperty="inquiryName"/>
			</html:select>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�����̶ȣ�</td>
		<td colspan="3">
			<input type="radio" name="INQUIRY_LEVEL" value="1">�ߣ�1��֮�ڴ���
			<input type="radio" name="INQUIRY_LEVEL" value="2" checked >�У�2��֮�ڴ���
			<input type="radio" name="INQUIRY_LEVEL" value="3">�ͣ�3��֮�ڴ���
		</td>
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="submit" class="button2" value=" ȷ�� " >&nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
	</tr>
</table>
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="tag" value="5">
</html:form>

</body>
</html>
