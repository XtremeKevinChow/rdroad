<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.crm.user.entity.*"%>
<%

String id=request.getParameter("id");
String pid=request.getParameter("pid");
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";
User user=new User();
user = (User)session.getAttribute("user");

try{
		 conn = DBManager.getConnection();
		 String sql="";	
		 sql="UPDATE PRD_PRICELIST_LINES SET status = -1,OPERATOR_ID ="+user.getId()+" WHERE id ="+id;			 
		 stmt = conn.createStatement(); 
	 	 stmt.executeUpdate(sql);     		                

			 
		 
response.sendRedirect("msc_active_gift_list.jsp?id="+pid);

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

