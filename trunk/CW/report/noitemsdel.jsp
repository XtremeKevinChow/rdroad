<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String rk_no = request.getParameter("rk_no");
			 rk_no=(rk_no==null)?"":rk_no;	
			 
String postcode = request.getParameter("postcode");
			 postcode=(postcode==null)?"":postcode;	
String address = request.getParameter("address");
			 address=(address==null)?"":address;	
String return_reason = request.getParameter("return_reason");
			 return_reason=(return_reason==null)?"":return_reason;	
String other_special = request.getParameter("other_special");
			 other_special=(other_special==null)?"":other_special;	

Connection conn=null;
      Statement stmt=null;

		try{
				 conn = DBManager.getConnection();
					String sql="update  jxc.sto_rk_ns_mst set logout='Y' where rk_no='"+rk_no+"'";
					stmt=conn.createStatement();
					stmt.executeQuery(sql);

			} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {	
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {}
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
response.sendRedirect("noitemsquery.jsp?postcode="+postcode+"&address="+address+"&other_special="+other_special+"&return_reason="+return_reason+"&tag=1");
%>
	
