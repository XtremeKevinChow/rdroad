<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String card_id=request.getParameter("card_id");
String name=request.getParameter("name");
String telephone=request.getParameter("telephone");
String company_phone=request.getParameter("company_phone");
String postcode=request.getParameter("postcode");
String address=request.getParameter("address");
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
	 
	                		                
	                 String update_sql="update  mbr_members set  name='"+name+"',telephone='"+telephone+"',company_phone='"+company_phone+"',postcode='"+postcode+"',address='"+address+"' where card_id='"+card_id+"'";
		 	 System.out.println(update_sql);				 
			 stmt = conn.createStatement(); 
		 	 stmt.executeUpdate(update_sql); 


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
response.sendRedirect("member_orgModify.jsp?card_id="+card_id);
%>

