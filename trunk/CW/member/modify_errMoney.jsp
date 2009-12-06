<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String id =request.getParameter("id");
String deposit=request.getParameter("deposit");
String card_id =request.getParameter("card_id");
%>

<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";

		try{
		conn = DBManager.getConnection();
		sql="delete from mbr_money_history where id="+id;		 		 
		pstmt=conn.prepareStatement(sql);
		pstmt.executeUpdate();
		pstmt.close();
		String sql1="update mbr_members set deposit=(deposit-"+deposit+") where card_id='"+card_id+"'";
		pstmt=conn.prepareStatement(sql1);
		pstmt.executeUpdate();
				
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

