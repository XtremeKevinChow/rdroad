<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<%

String gift_id=request.getParameter("gift_id");
gift_id=(gift_id==null)?"":gift_id;
String item_id=request.getParameter("item_id");
item_id=(item_id==null)?"":item_id;
String msc=request.getParameter("msc");
msc=(msc==null)?"":msc;
String status=request.getParameter("status");
String id=request.getParameter("id");

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 String sql="";
		 int is_item_add=0;	
		 /*	 
		 if(gift_id.equals("1")){
			 sql="select * from ord_lines where item_id="+item_id+" and status>98";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){ 
			 is_item_add=1;
	           %>
			  
			  
	           <%			 

	                 }
		 }else{
			 sql="select * from ord_headers where status=100 and gift_number='"+item_id+"'";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){ 
			 is_item_add=1;
	           %>
			  
			  
	           <%
	                 }	 
		 }	  
		 if(is_item_add==0){
                 sql="update mbr_msc_gift set status=1 where id= "+id;
		 					 
		 stmt = conn.createStatement(); 
	 	 stmt.executeUpdate(sql); 		 
		 }       
		 */   
		 sql="update mbr_msc_gift set status="+status+" where id= "+id;
		 					 
		 stmt = conn.createStatement(); 
	 	 stmt.executeUpdate(sql);     		                

			 
		 
//response.sendRedirect("gift_add.jsp?query=1&msc="+msc+"&item_id="+item_id+"&gift_id="+gift_id);

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
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="gift_add.jsp?query=1&gift_id=<%=gift_id%>&item_id=<%=item_id%>">返回</a></font></td>
	
		</td>		
	</tr>	
</table>
</body>
</html>
