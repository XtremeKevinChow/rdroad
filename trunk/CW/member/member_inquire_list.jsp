<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//String name=request.getParameter("name");
//String id=request.getParameter("id");
//String card_id=request.getParameter("card_id");
String iscallcenter = request.getParameter("iscallcenter");
String name="";
String id="";
String card_id="";
if (iscallcenter != null && iscallcenter.equals("1")) {
	Member mb = (Member)request.getSession().getAttribute(Constants.CURRENT_MEMBER_KEY);
	if (mb != null) {
		id = String.valueOf(mb.getID());
		card_id = mb.getCARD_ID();
		name = mb.getNAME();
	}
}
Collection listInquiry=(Collection)request.getAttribute("listInquiry");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function load_f(){
	
	for (var i=1; i < myTable.rows.length; i ++) {
		var tr =  myTable.rows(i);
		if (tr.cells(5).innerText == "未解决 ") {
			tr.bgColor = "lightyellow";
		}
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f()">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">投诉查询</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95％" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>会员号：<a href="memberDetail.do?id=<%=id%>"><%=card_id%></a>&nbsp;
		会员姓名：<%=name%>&nbsp;&nbsp;
		
		
	</tr>
</table>

<table id="myTable" width="95%" align="center" border=1 cellspacing=0 bordercolordark="lightblue" bordercolorlight="#ffffff">
	<tr >
		<th width="40"  class="OraTableRowHeader" noWrap  noWrap align=middle  >序号</th>
		
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >时间</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >投诉类型</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >部门</th>
		
		<th width="*%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >内容</th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >状态</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作人</th>
	</tr>
<%
	   Iterator it=listInquiry.iterator();
	   MemberInquiry mi=new MemberInquiry();
	   while(it.hasNext()){
	   mi=(MemberInquiry)it.next();
%>  
	<tr>
		<td  align=middle ><a href="InquiryDetail.do?tag=1&createid=<%=mi.getCREATEID()%>&name=<%=name%>&card_id=<%=card_id%>&id=<%=id%>&is_query=1"><%=mi.getEVENT_ID()%></a></td>
		<td  align=middle ><%=mi.getSOLVE_DATE()%></td>

		<td  align=middle ><%=mi.getInquiryName()%></td>
		<td  align=middle >
			<%if(mi.getref_department()==1){%>市场部<%}%>
			<%if(mi.getref_department()==2){%>客服部<%}%> 
			<%if(mi.getref_department()==3){%>编辑部<%}%>
			<%if(mi.getref_department()==4){%>人事行政部<%}%> 
			<%if(mi.getref_department()==5){%>IT部<%}%> 
			<%if(mi.getref_department()==6){%>物流部<%}%> 
			<%if(mi.getref_department()==7){%>总经理室<%}%>
			<%if(mi.getref_department()==8){%>财务部<%}%> 
			<%if(mi.getref_department()==9){%>网站<%}%> 
		
		</td>		
		<td align=middle ><%=mi.getSOLVE_METHOD()%></td>
		<td align=middle >
		<%
		if(mi.getStatus()==0){
		   out.println("未解决");
		}
		if(mi.getStatus()==1){
		   out.println("已解决");
		}
		if(mi.getStatus()==2){
		   out.println("客服确认已解决");
		}				
		%></td>
		<td align=middle ><%=mi.getSOLVE_PERSON()%></td>	
	</tr>
<%}%>
</table>

</body>
</html>
