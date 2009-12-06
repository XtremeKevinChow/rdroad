<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
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
Mbr_gift_item_mst info=new Mbr_gift_item_mst();
String id=request.getParameter("id");
String status=request.getParameter("status");
String err="";
info.setItem_group_id(Integer.parseInt(id));
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 String sql="";


	
		 if(Mbr_gift_itemDAO.checkItem_group(conn,info)>0&&status.equals("-1")){
		 %>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作失败，相关礼券在使用此产品组<a href="javascript:history.back(-1);">返回</a></font></td>
	
		</td>		
	</tr>	
</table>		 
		 <%
		 }else{ 		                
                 String up_sql="update  MBR_GIFT_ITEM_MST set status="+status+" where status<>-1 and ITEM_GROUP_ID= "+id;				 
		 stmt = conn.createStatement(); 
	 	 stmt.executeUpdate(up_sql); 
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="mbr_gift_item_mst_list.jsp">返回列表</a></font></td>
	
		</td>		
	</tr>	
</table>
<%}%>

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
