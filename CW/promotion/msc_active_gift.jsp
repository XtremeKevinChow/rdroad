<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%

String id=request.getParameter("id");
String tag=request.getParameter("tag");
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 String sql="";	
                 if(tag.equals("0")){
                 sql="update PRD_PRICELISTS set status=-1 where id= "+id;
                 }
                 if(tag.equals("1")){
                 sql="update PRD_PRICELISTS set status=100 where id= "+id;
                 }			 
		 stmt = conn.createStatement(); 
	 	 stmt.executeUpdate(sql);     		                

			 
		 
response.sendRedirect("prom_msc_list.jsp?tag=1");

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

