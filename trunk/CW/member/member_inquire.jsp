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
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language=javascript>
function ref(){
       document.inquiryForm.action="../member/InquiryRef.do";
       document.inquiryForm.submit();
}

function addSubmit(){
	if(document.inquiryForm.INQUIRY_TYPE.value==""){
		alert("没有投诉类型,请重新选择");
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">客户服务</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员投诉与咨询</font><font color="838383"> 
      	</td>
   </tr>
</table>

<html:form method="post" action="/InquiryCreate.do" onsubmit="return addSubmit();">
<table  border=0 cellspacing=1 cellpadding=1  width="95％" align="center" >

	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;会员号码：</td>
		<td width="*%" align="left" colspan="1" width="20%" ><%=card_id%>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;会员姓名：</td>
		<td  align="left" width="40%"><%=name%>&nbsp;
		</td>
	</tr>

	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;内容：</td>
		<td align="left" colspan="3">
			<textarea cols=70 rows=5 name="SOLVE_METHOD" ><%=SOLVE_METHOD%></textarea>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >类型：</td>
		<td colspan="3">
			<input type="radio" name="IS_SOLVE" value="1" <%if(IS_SOLVE.equals("1")){%>checked<%}%> onclick="ref()">投诉
			<input type="radio" name="IS_SOLVE" value="0" <%if(IS_SOLVE.equals("0")){%>checked<%}%> onclick="ref()">咨询
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >是否需要回复：</td>
		<td colspan="3">
			<input type="radio" name="IS_ANSWER" value="1" <%if(IS_ANSWER.equals("1")){%>checked<%}%>>是
			<input type="radio" name="IS_ANSWER" value="0" <%if(IS_ANSWER.equals("0")){%>checked<%}%>>否
			
		</td>
	</tr>	
	<%if(IS_SOLVE.equals("1")){%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >投诉部门：</td>
		<td colspan="3">
		<select name="deptid" onchange="ref()">
		  <option value="1" <%if(deptid.equals("1")){%>selected<%}%> >市场部</value>
			<option value="2" <%if(deptid.equals("2")){%>selected<%}%> >客服部</value>
			<option value="3" <%if(deptid.equals("3")){%>selected<%}%> >编辑部</value>
			<option value="4" <%if(deptid.equals("4")){%>selected<%}%> >人事行政部</value>
		  <option value="5" <%if(deptid.equals("5")){%>selected<%}%> >IT部</value>
			<option value="6" <%if(deptid.equals("6")){%>selected<%}%> >物流部</value>
			<option value="7" <%if(deptid.equals("7")){%>selected<%}%> >总经理室</value>
			<option value="8" <%if(deptid.equals("8")){%>selected<%}%> >财务部</value>
			<option value="9" <%if(deptid.equals("9")){%>selected<%}%> >网站</value>							
			</select>		
		</td>
	</tr>	
	<%}else{%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >咨询种类：</td>
		<td colspan="3">
		<select name="deptid" onchange="ref()">
		  <option value="1" <%if(deptid.equals("1")){%>selected<%}%> >查询订单情况</value>
			<option value="2" <%if(deptid.equals("2")){%>selected<%}%> >查询会员信息</value>
			<option value="3" <%if(deptid.equals("3")){%>selected<%}%> >帐户查询</value>
			<option value="4" <%if(deptid.equals("4")){%>selected<%}%> >查询市场活动</value>			
			<option value="5" <%if(deptid.equals("5")){%>selected<%}%> >意见和建议</value>			
			</select>
		</td>
	</tr>		
	<%}%>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >
		<%if(IS_SOLVE.equals("1")){%>	
		投诉类型：
		<%}else{%>
		咨询类型：
		<%}%>
		</td>
		<td colspan="3">
			<html:select property="INQUIRY_TYPE">
				<option value="">-- 请选择 --</option>
			  <html:options collection="InquiryType" property="inquiryID" labelProperty="inquiryName"/>
			</html:select>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >紧急程度：</td>
		<td colspan="3">
			<input type="radio" name="INQUIRY_LEVEL" value="1">高（1天之内处理）
			<input type="radio" name="INQUIRY_LEVEL" value="2" checked >中（2天之内处理）
			<input type="radio" name="INQUIRY_LEVEL" value="3">低（3天之内处理）
		</td>
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" id="submitButton" type="submit" class="button2" value=" 确定 " >&nbsp;
		<input type="reset" class="button2" value=" 取消 " >
	</tr>
</table>
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="tag" value="5">
</html:form>

</body>
</html>
