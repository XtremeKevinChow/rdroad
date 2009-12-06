<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ page import="com.magic.crm.user.entity.User"%>
<%

int catalog_id= Integer.parseInt(request.getParameter("catalog_id"));
int member_id = Integer.parseInt(request.getParameter("member_id"));
int return_reason = Integer.parseInt(request.getParameter("return_reason"));

User user = new User();
user = (User) session.getAttribute("user");


Connection conn=null;
PreparedStatement pstmt = null;


try{
		conn = DBManager.getConnection();
		pstmt = conn.prepareStatement("insert into mbr_catalog_return "
		+ "(member_id, catalog_id, return_reason, creator, create_date) "
		+ "values(?, ?, ?, ?, sysdate)");
		pstmt.setInt(1, member_id);
	    pstmt.setInt(2, catalog_id);
		pstmt.setInt(3, return_reason);
		pstmt.setInt(4, Integer.parseInt(user.getId()));
		pstmt.execute();

} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {		
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			if (conn != null)
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}
response.sendRedirect("_returned_catalog_register.jsp");
%>

