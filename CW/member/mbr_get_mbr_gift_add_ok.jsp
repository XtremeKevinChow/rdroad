<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<%
User user=new User();
user = (User)session.getAttribute("user");
String gift_number=request.getParameter("gift_number");
String keep_days=request.getParameter("keep_days");
String end_date=request.getParameter("end_date");
String begin_date=request.getParameter("begin_date");
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


	   try{
		 conn = DBManager.getConnection();
		 
		 String in_sql="insert into mbr_get_mbr_gift(id,gift_number,begin_date,end_date,is_valid,operator_id,keep_days) ";
	     in_sql+=" VALUES(SEQ_MBR_GET_MBR_GIFT_ID.Nextval,'"+gift_number+"',to_date('"+begin_date+"','YYYY-MM-DD'),to_date('"+end_date+"','YYYY-MM-DD'),0,"+user.getId()+","+keep_days+")";
		 	 System.out.println(in_sql);
		 	 //out.println(in_sql);					 
			 stmt = conn.createStatement(); 
		 	 stmt.executeUpdate(in_sql); 
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="mbr_get_mbr_gift_list.jsp">返回</a></font></td>
	
		</td>		
	</tr>	
</table>

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
//response.sendRedirect("gift_add.jsp?err="+err);
%>
</body>
</html>
