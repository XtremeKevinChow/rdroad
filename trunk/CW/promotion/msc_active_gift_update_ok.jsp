<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.crm.user.entity.*"%>
<%


String pid=request.getParameter("pid");
String id=request.getParameter("id");
String item_code=request.getParameter("itemCode");
String sell_type=request.getParameter("sell_type");
String common_price=request.getParameter("common_price");

Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";
User user=new User();
user = (User)session.getAttribute("user");
int item_id=0;
try{
	 conn = DBManager.getConnection();
	 String sql="";	
         sql=" SELECT item_id  FROM prd_items WHERE item_code ='"+item_code+"'";
	 pstmt=conn.prepareStatement(sql);
	 rs=pstmt.executeQuery();
	 if(rs.next()){ 	 
         item_id=rs.getInt(1);
         } 	 
		 sql="update PRD_PRICELIST_LINES set ITEM_ID="+item_id+",SELL_TYPE="+sell_type+",COMMON_PRICE="+common_price+" where id="+id;
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

