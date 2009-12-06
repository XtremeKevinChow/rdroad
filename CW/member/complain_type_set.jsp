<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%
DecimalFormat myformat = new DecimalFormat("#");
      String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag;
      String kind=request.getParameter("kind");
      kind=(kind==null)?"0":kind;
      String class_type=request.getParameter("class_type");
      class_type=(class_type==null)?"0":class_type;      

      
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		 conn = DBManager.getConnection();      
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--
function ref(){
       document.form.action="";
       document.form.submit();
}

function query_f() {

	if(document.form.type_name.value==""){
	   alert("请输入类型描述");
	   document.form.type_name.focus();
	   return false;
	}        
	document.form.action="complain_type_add.jsp";
	document.form.submit();
}



//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">基础数据</font><font color="838383"> 
      		-&gt; </font><font color="838383">咨询投诉类型设置</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="90%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="90%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td width="150">投诉、咨询：</td>
		<td  bgcolor="#FFFFFF">
		<input type=radio name="kind" value="0" <%if(kind.equals("0")){%>checked<%}%> onclick="ref()">投诉
		<input type=radio name="kind" value="1" <%if(kind.equals("1")){%>checked<%}%> onclick="ref()">咨询

		</td>	
		<td width="150">大类、小类：</td>
		<td  bgcolor="#FFFFFF">
		<input type=radio name="class_type" value="0" <%if(class_type.equals("0")){%>checked<%}%> onclick="ref()">大类
		<input type=radio name="class_type" value="1" <%if(class_type.equals("1")){%>checked<%}%> onclick="ref()">小类

		</td>		
							
	</tr>	

	 <%if(class_type.equals("1")){//新增投诉小类
	 %> 
		<tr>	
			<td width="150">类型选择：</td>
			<td  bgcolor="#FFFFFF">
	                <select name="parent_id">
	                <%
		       sql=" select * from complaint_type where type="+kind+" and parent_id=0 and status=0";
		
		        //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			int i=0;
			while(rs.next()){
			String cmpt_type_name=rs.getString("cmpt_type_name");
			String cmpt_id=rs.getString("cmpt_type_id");
	                %>
	                <option value="<%=cmpt_id%>"><%=cmpt_type_name%></option>
	                <%}%>
	                </select>
			</td>	
			<td width="150">类型描述：</td>
			<td  bgcolor="#FFFFFF">
	                <input type="text" name="type_name">
			</td>	
								
		</tr>
	<%}else{%>
		<tr>	
			<td width="150">类型描述：</td>
			<td  bgcolor="#FFFFFF">
	                <input type="text" name="type_name">
			</td>	
			<%
			if(kind.equals("0")){//只有是投诉才会新增部门ID
			%>
			<td width="150">对应部门：</td>
			<td  bgcolor="#FFFFFF">
			<select name="dept_id">
			<option value="1">市场部</option>
			<option value="2">客服部</option>
			<option value="3">编辑部</option>			
			<option value="4">人事行政部</option>
			<option value="5">IT部</option>			
			<option value="6">物流部</option>			
			<option value="7">总经理室</option>			
			<option value="8">财务部</option>			
			<option value="9">网站</option>			
			<option value="10">销售部</option>			
			</select>
			</td>		
	               <%}%>
								
		</tr>
	<%}%>		


			<td width="" colspan="4" align="center" bgcolor="#FFFFFF">
			&nbsp;<input type="button" name="btn_query" value=" 新 增 " onclick="query_f();">
			<input type="hidden" name="tag" value="1">
			</td>		
								
		</tr>	
		
</table>
<table width="90%" border="0"  cellpadding="1" cellspacing="2" align="center" noWrap >
	<tr>	
		<td width="300" class="OraTableRowHeader">类型结构图</td>
		<td  bgcolor="#FFFFFF" align="left">
		<%
		       if(kind.equals("1")){
				sql="select * from complaint_type where type=1 and status=0 start with parent_id=0 connect by parent_id=prior cmpt_type_id";
 			}else{
			    sql="select a.cmpt_type_name,a.parent_id from complaint_type a, dept_complaint b where a.type=0 and a.status=0";
			    sql+=" and a.cmpt_type_id=b.cmpt_type_id(+) ";
			    sql+=" start with parent_id=0 connect by parent_id=prior a.cmpt_type_id  ";			
 			}
	
		        //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			String type_name="";
			while(rs.next()){
			type_name=rs.getString("cmpt_type_name");
			int parent_id=rs.getInt("parent_id");
			if(parent_id!=0){
			   out.println("----"+type_name+"<br>");
			}else{
			   out.println("<font color=red>"+type_name+"</font><br>");
			}
			
			}		
		%>
		</td>		
							
	</tr>	

	
		
</table>



</form>
</body>
</html>
<%
} catch(Exception se) {

se.printStackTrace();

} finally {
if (rs != null)
	try {
		rs.close();
	} catch (Exception e) {}			
if (pstmt != null)
	try {
		pstmt.close();
	} catch (Exception e) {}
 try {
	 conn.close();
 	} catch(SQLException sqe) {}

}
%>