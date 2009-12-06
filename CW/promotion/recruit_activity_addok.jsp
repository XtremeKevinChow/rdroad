<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
User user=new User();
user = (User)session.getAttribute("user");

String msc_code=request.getParameter("msc_code");
String name=request.getParameter("name");
String remark=request.getParameter("remark");
String start_date=request.getParameter("start_date");
String end_date=request.getParameter("end_date");
String scope=request.getParameter("scope");
String headhtml=request.getParameter("headhtml");
headhtml=(headhtml==null)?"":headhtml;
Recruit_Activity info=new Recruit_Activity();


info.setName(name);
info.setMsc_Code(msc_code);
info.setStartDate(start_date);
info.setEndDate(end_date);
info.setScope(Integer.parseInt(scope));
info.setCreatorId(Integer.parseInt(user.getId()));
info.setLastModifierId(Integer.parseInt(user.getId()));
info.setPromulgatorId(Integer.parseInt(user.getId()));
info.setRemarks(remark);
info.setHeadhtml(headhtml);
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 if(Recruit_ActivityDAO.checkMsc(conn,info)>0){
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作失败！活动编号或活动名称已存在。<a href="javascript:history.back(-1);">请重新设置</a></font></td>
	
		</td>		
	</tr>	
</table>
<%		 
		 }else{
	
		Recruit_ActivityDAO.insert(conn,info);
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="recruit_activity_list.jsp">返回列表</a></font></td>
	
		</td>		
	</tr>	
</table>
<%}%>


<!--*************************************************************************** -->
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
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}

%>
</body>
</html>
