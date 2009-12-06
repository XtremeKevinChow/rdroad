<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%

String name="";
String id="";
String card_id="";
String is_fenye=request.getParameter("is_fenye");//is_fenye=1 说明是分页查询过来的
is_fenye=(is_fenye==null)?"":is_fenye;

String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;
String type=request.getParameter("type");
String event_id=request.getParameter("event_id");
if(tag.equals("1")){
	name=request.getParameter("name");
	id=request.getParameter("id");
	card_id=request.getParameter("card_id");
}else{
	Member member=new Member();
	member=(Member)request.getAttribute("member");
	name=member.getNAME();
	id=String.valueOf(member.getID());
	card_id=member.getCARD_ID();
}
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function inquireSubmit() {

	if(document.complaintForm.cmpt_content.value==""){
		alert('请输入解决方法!');
		document.complaintForm.cmpt_content.focus();
		return false;;
	}

	document.complaintForm.submit();
}

function backComplaintList(url) {
	location.href=url;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">投诉/咨询列表</font><font color="838383"> <font color="838383"> 
      		-&gt; </font><font color="838383">投诉/咨询解决</font><font color="838383"> 
      	</td>
   </tr>
</table>
 -->

<table  border=0 cellspacing=1 cellpadding=3  width="95%" align="center" >	
	<tr>
		<td align="right" colspan="2"><input name="order_serch" value="返回列表" type="button" onclick=backComplaintList('./complaintList.do?iscallcenter=1&name=<%=name%>&card_id=<%=card_id%>&type=<%=type%>')></td>
	</tr>
</table>
<table  border=0 cellspacing=1 cellpadding=3  width="95%" align="center" class="OraTableRowHeader" noWrap >
	  <logic:iterate id="complaint" name="ColListComplaint" >	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap  width="160">&nbsp;会员号码：</td>
		<td width="*%" align="left" colspan="1" width="*%"  bgcolor="#FFFFFF">
			<%=card_id%>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >&nbsp;会员姓名：</td>
		<td  align="left" bgcolor="#FFFFFF"><%=name%>

		</td>
	</tr>
	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;内容：</td>
		<td align="left" bgcolor="#FFFFFF">
			<bean:write name="complaint" property="cmpt_content"/>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >种类：</td>
		<td bgcolor="#FFFFFF">
		<logic:equal name="complaint" property="type" value="0">投诉</logic:equal>
		<logic:equal name="complaint" property="type" value="1">咨询</logic:equal>	
		
			
		</td>
	</tr>	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >类型：</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="cmpt_type_name"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >是否回复：</td>
		<td  bgcolor="#FFFFFF">
		<logic:equal name="complaint" property="is_answer" value="1">需要回复</logic:equal>
		<logic:equal name="complaint" property="is_answer" value="0">不需要回复</logic:equal>	
		
		</td>
	</tr>

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >操作员：</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="creatorName"/>
			
		</td>
	</tr>
		</logic:iterate>	
</table>
<br>

<%
int i=0;
%>
	  <logic:iterate id="complaint" name="ColListComplaintDeal" >	
<table  border=0 cellspacing=1 cellpadding=3  width="95％" align="center" bgcolor="#dd3366">	
<%

  i++;
%>

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">第<%=i%>次解决方法：</td>
		<td bgcolor="#FFFFFF">
		<bean:write name="complaint" property="cmpt_content"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >操作员：</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="creatorName"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >操作日期：</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="create_date"/>
			
		</td>
	</tr>	
</table>
<br>
		</logic:iterate>


<html:form action="/complaintDeal.do" method="post">
<table  border=0 cellspacing=1 cellpadding=1  width="95%" align="center">
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">解决方法：</td>
		<td bgcolor="#FFFFFF">
		<textarea cols="50" rows="5" name="cmpt_content"></textarea>					
		</td>
		</tr>
		<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">结果：</td>
		<td bgcolor="#FFFFFF">
		<input type=radio name="cmt_status" value="1"  checked>解决
		<input type=radio name="cmt_status" value="2"  >再次处理处理	
				
		</td>		
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input type="button" value=" 确定" onclick="inquireSubmit()" >&nbsp;
		<input type="reset" class="button2" value=" 取消 " >
	</tr>
</table>
	  <bean:define id="complaint" name="complaintForm" />
	  <html:hidden name="complaint" property="mbr_id"/>	
	  <html:hidden name="complaint" property="deptID"/>
	  <html:hidden name="complaint" property="cmpt_level"/>
	  <html:hidden name="complaint" property="creator"/>
	  <html:hidden name="complaint" property="cmpt_id"/>
	<input type="hidden" name="cmpt_type_id" value="<bean:write name='complaint' property='cmpt_type_id'/>">
	  <input type="hidden" name="type" value="<%=type%>">
	  
	  <input type="hidden" name="name" value="<%=name%>">
	  <input type="hidden" name="card_id" value="<%=card_id%>"> 	  
	  <input type="hidden" name="id" value="<%=id%>"> 
	  <input type="hidden" name="event_id" value="<%=event_id%>"> 
	  <input type="hidden" name="iscallcenter" value="1"> 
	  <%
	  if(is_fenye.equals("1")){//从查询页面过来的参数，返回查询页面时也要带回去
 	//上面已经有的参数如creator、cmpt_type_id就不重新设值了

	String status=request.getParameter("status");
	       status=(status==null)?"":status;
	String solve_date = request.getParameter("solve_date");
		solve_date=(solve_date==null)?"":solve_date;
	String solve_date2 = request.getParameter("solve_date2");
		solve_date2=(solve_date2==null)?"":solve_date2;
	String parent_id = request.getParameter("parent_id");
		parent_id=(parent_id==null)?"":parent_id;		
	String is_answer = request.getParameter("is_answer");//是否需要回复 1是，0否
		is_answer=(is_answer==null)?"":is_answer;          	
	  
	  %>
	  <input type="hidden" name="is_answer" value="<%=is_answer%>"> 
	  <input type="hidden" name="solve_date" value="<%=solve_date%>"> 
	  <input type="hidden" name="solve_date2" value="<%=solve_date2%>"> 
	  <input type="hidden" name="parent_id" value="<%=parent_id%>"> 
	  
	  <input type="hidden" name="status" value="<%=status%>"> 
	  <input type="hidden" name="is_fenye" value="1"> 


	  <%
	  }
	  %>

</html:form>


</body>
</html>
