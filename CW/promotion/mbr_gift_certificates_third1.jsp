<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.order.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
Ticket info=new Ticket();
info=(Ticket)session.getAttribute("info");
 
  String product_group_id=request.getParameter("product_group_id");
  String item_group_id=request.getParameter("item_group_id");   

if(product_group_id.equals("1")){
info.setProductGroupId(Integer.parseInt(item_group_id));
}else{
info.setProductGroupId(Integer.parseInt(product_group_id));
}


%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
User user=new User();
user = (User)session.getAttribute("user");


String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
 conn = DBManager.getConnection();

	 if(TicketDAO.checkGift(conn,info)>0){//先判断礼券号是否重复		 	 	
	 %>
 	<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
		<tr>
			<td bgcolor="#FFFFFF" ><font color="red">操作失败，礼券号 <%=info.getGiftNumber()%> 已经存在。<a href="javascript:history.back(-1);">返回</a></font></td>
		
			</td>		
		</tr>	
	</table>
	<%
	 }else{
	
	TicketDAO.insert(conn,info);
	
	%>
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
				<td bgcolor="#FFFFFF" ><font color="red">
			
					批号&nbsp;<%=info.getGiftNumber()%>&nbsp;已经设置成功！	<a href="ggcard_add.jsp?gift_number=<%=info.getGiftNumber()%>">下一步设置礼券数量</a>

				</font></td>
			
				</td>		
			</tr>	
		</table>
	<%}%>

<!--*************************************************************************** -->
<%
request.getSession(true).removeAttribute("info");
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
