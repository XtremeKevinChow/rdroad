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
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language=javascript>


function addComplaint(){
	if(document.complaintForm.cmpt_content.value==""){
	   alert("请输入投诉内容");
	   document.complaintForm.cmpt_content.focus();
	   return false;
	}
	if(document.complaintForm.parent_id.value==""){
		alert("请选择大类!");
		document.complaintForm.parent_id.focus();
			return false;
	}
	if(document.complaintForm.cmpt_type_id.value==""){
		alert("请选择小项!");
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">客户服务</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员投诉新增</font><font color="838383"> 
      	</td>
   </tr>
</table> -->

<html:form method="post" action="/complaintAdd.do">
<table  border=0 cellspacing=1 cellpadding=1  width="95％" align="center" >

	<!-- <tr>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;会员号码：</td>
		<td width="*%" align="left" colspan="1" width="20%" ><%=card_id%>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;会员姓名：</td>
		<td  align="left" width="40%"><%=name%>&nbsp;
		</td>
	</tr>
 -->


	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >类型：</td>
		<td  >
			<input type="radio" name="type" value="0" <%if(type.equals("0")){%>checked<%}%> onclick="ref2()">投诉
			<input type="radio" name="type" value="1" <%if(type.equals("1")){%>checked<%}%> onclick="ref2()">咨询
			
		</td>
		<td align="right" class="OraTableRowHeader" noWrap >是否需要回复：</td>
		<td  >
			<input type="radio" name="is_answer" value="1" <%if(is_answer.equals("1")){%>checked<%}%>>是
			<input type="radio" name="is_answer" value="0" <%if(is_answer.equals("0")){%>checked<%}%>>否
			
		</td>		
	</tr>	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;内容：</td>
		<td align="left" colspan="3">
			<textarea cols=70 rows=5 name="cmpt_content" ><%=cmpt_content%></textarea>
		</td>
	</tr>	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >
		大类：
		</td>
		<td>
			<html:select property="parent_id" onchange="ref(1)">
				<option value="">-- 请选择 --</option>
			  <html:options collection="complaintType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>
		<td align="right" class="OraTableRowHeader" noWrap >
		小类：
		</td>
		<td>
			<html:select property="cmpt_type_id">
				<option value="">-- 请选择 --</option>
			  <html:options collection="complaintSunType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>		
	</tr>

	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="button" class="button2" value=" 确定 " onclick="addComplaint();">&nbsp;
		<input type="reset" class="button2" value=" 取消 " >
	</tr>
</table>

<input type="hidden" name="id" value="<%=id%>">
</html:form>

</body>
</html>
