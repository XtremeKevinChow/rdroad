<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.order.dao.*"%>
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
Mbr_gift_money_by_order info=new Mbr_gift_money_by_order();

String id=request.getParameter("id");
String status=request.getParameter("status");
info.setID(Integer.parseInt(id));
info.setStatus(Integer.parseInt(status));
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
	conn = DBManager.getConnection();
	String sql="";
	/*
	sql="select * from MBR_GIFT_MONEY_BY_ORDER where id= "+id;
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	if(rs.next()){		  
	String rs_gift_number=rs.getString("gift_number");
	info.setGift_number(rs_gift_number);
	}
	 
   if(TicketDAO.checkIfused(conn,info.getGift_number())>0){//判断礼券是否已经被使用，被使用过就不能修改记录
    	*/		                                		                
                 Mbr_gift_money_by_orderDAO.delete(conn,info);
%>
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
				<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="mbr_gift_level_list.jsp">返回列表</a></font></td>
			
				</td>		
			</tr>	
		</table>


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
