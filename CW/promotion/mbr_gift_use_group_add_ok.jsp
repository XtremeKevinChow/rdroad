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
String gift_type=request.getParameter("gift_type");


Mbr_use_gift_group info=new Mbr_use_gift_group();
info.setGift_type(Integer.parseInt(gift_type));
info.setGift_number(gift_number);
info.setGroup_no(group_no);
if(gift_type.equals("3")){
info.setGift_number("any");
}
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
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
		 if(Mbr_use_gift_groupDAO.checkGiftNo(conn,info)>0){%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">����ʧ�ܣ�ͬ����ȯ�Ų����ظ�!<a href="javascript:history.back(-1);">����</a></font></td>
	
		</td>		
	</tr>	
</table>		 
		 <%}else{
		 

		 Mbr_use_gift_groupDAO.insert(conn,info);
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">�����ɹ�<a href="mbr_gift_use_group_list.jsp">�����б�</a></font></td>
	
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
