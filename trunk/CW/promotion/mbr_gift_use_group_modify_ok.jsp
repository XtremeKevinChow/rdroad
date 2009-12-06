<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String gift_number=request.getParameter("gift_number");
String group_no=request.getParameter("group_no");
String old_gift_number=request.getParameter("old_gift_number");
String old_group_no=request.getParameter("old_group_no");
String gift_type=request.getParameter("gift_type");
String id=request.getParameter("id");

Mbr_use_gift_group info=new Mbr_use_gift_group();
info.setGift_type(Integer.parseInt(gift_type));
info.setGift_number(gift_number);
info.setGroup_no(group_no);
info.setID(Integer.parseInt(id));
if(gift_type.equals("3")){
info.setGift_number("any");
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
      int ifcheck=0;


try{
		 conn = DBManager.getConnection();
		 if(!old_gift_number.equals("gift_number")||!old_group_no.equals("group_no")){
		 	ifcheck=Mbr_use_gift_groupDAO.checkGiftNo(conn,info);
		 }
		 if(ifcheck>0){%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作失败，同组礼券号不能重复!<a href="javascript:history.back(-1);">返回</a></font></td>
	
		</td>		
	</tr>	
</table>		 
		 <%}else{		 
		 Mbr_use_gift_groupDAO.update(conn,info);
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="mbr_gift_use_group_list.jsp">返回列表</a></font></td>
	
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
