<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="com.magic.crm.util.Constants"%>
<%@page import="com.magic.crm.util.ChangeCoding"%>
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
String is_answer="";
String tag=request.getParameter("tag");
	tag=(tag==null)?"":tag;	
	//System.out.println("tag is "+tag);	
String type=request.getParameter("type");
	type=(type==null)?"1":type;
	
	if(type.equals("1")){
		is_answer="0";
		//System.out.println("is_answer2 is "+is_answer);
	}else{
		is_answer="1";
		//System.out.println("is_answer1 is "+is_answer);	
	}
	if(tag.equals("1")){
		is_answer=request.getParameter("is_answer");
			is_answer=(is_answer==null)?"":is_answer;
			//System.out.println("is_answer3 is "+is_answer);	
	}	

	
String cmpt_content=request.getParameter("cmpt_content");
	cmpt_content=(cmpt_content==null)?"":cmpt_content;
cmpt_content = ChangeCoding.unescape(ChangeCoding.toUtf8String(cmpt_content));

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language=javascript>


function addComplaint(){
	if(document.complaintForm.cmpt_content.value==""){
	   alert("������Ͷ������");
	   document.complaintForm.cmpt_content.focus();
	   return false;
	}
	if(document.complaintForm.parent_id.value==""){
		alert("��ѡ�����!");
		document.complaintForm.parent_id.focus();
			return false;
	}
	if(document.complaintForm.cmpt_type_id.value==""){
		alert("��ѡ��С��!");
		document.complaintForm.cmpt_type_id.focus();
			return false;
	}		
}
function ref2(){
       document.complaintForm.action="../member/initComplaintCreate.do";
       document.complaintForm.submit();
}
function ref(tag){
       document.complaintForm.action="../member/initComplaintCreate.do?tag="+tag;
       document.complaintForm.submit();
}
function init_page() {

}
 //-->
</script>    
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_page()">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ͻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ԱͶ������</font><font color="838383"> 
      	</td>
   </tr>
</table> -->

<html:form method="post" action="/complaintAdd.do">
<table  border=0 cellspacing=1 cellpadding=1  width="95��" align="center" >

	<!-- <tr>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;��Ա���룺</td>
		<td width="*%" align="left" colspan="1" width="20%" ><%=card_id%>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;��Ա������</td>
		<td  align="left" width="40%"><%=name%>&nbsp;
		</td>
	</tr>
 -->


	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >���ͣ�</td>
		<td  >
			<input type="radio" name="type" value="0" <%if(type.equals("0")){%>checked<%}%> onclick="ref2()">Ͷ��
			<input type="radio" name="type" value="1" <%if(type.equals("1")){%>checked<%}%> onclick="ref2()">��ѯ
			
		</td>
		<td align="right" class="OraTableRowHeader" noWrap >�Ƿ���Ҫ�ظ���</td>
		<td  >
			<input type="radio" name="is_answer" value="1" <%if(is_answer.equals("1")){%>checked<%}%>>��
			<input type="radio" name="is_answer" value="0" <%if(is_answer.equals("0")){%>checked<%}%>>��
			
		</td>		
	</tr>	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;���ݣ�</td>
		<td align="left" colspan="3">
			<textarea cols=70 rows=5 name="cmpt_content" ><%=cmpt_content%></textarea>
		</td>
	</tr>	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >
		���ࣺ
		</td>
		<td>
			<html:select property="parent_id" onchange="ref(1)">
				<option value="">-- ��ѡ�� --</option>
			  <html:options collection="complaintType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>
		<td align="right" class="OraTableRowHeader" noWrap >
		С�ࣺ
		</td>
		<td>
			<html:select property="cmpt_type_id">
				<option value="">-- ��ѡ�� --</option>
			  <html:options collection="complaintSunType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>		
	</tr>

	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="button" class="button2" value=" ȷ�� " onclick="addComplaint();">&nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
	</tr>
</table>

<input type="hidden" name="id" value="<%=id%>">
</html:form>

</body>
</html>
