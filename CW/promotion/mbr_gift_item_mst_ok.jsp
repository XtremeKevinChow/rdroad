<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
User user=new User();
user = (User)session.getAttribute("user");
Ticket info=new Ticket();
info=(Ticket)session.getAttribute("info");

String number=request.getParameter("number");
String group_name=request.getParameter("group_name");
String item_group_type=request.getParameter("item_group_type");
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 String sql="";


	
		                                		                
                 String in_sql="insert into MBR_GIFT_ITEM_MST(ITEM_GROUP_ID,MIN_ITEM_COUNT,GROUP_DESC,STATUS,itemgroup_type) ";
		 in_sql+=" VALUES(SEQ_MBR_GIFT_BY_ITEMS_ID.Nextval ,"+number+",'"+group_name+"',0,"+item_group_type+")";
	 	 //System.out.println(in_sql);
	 	 //out.println(in_sql);					 
		 stmt = conn.createStatement(); 
	 	 stmt.executeUpdate(in_sql); 
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" >
		<font color="red">�����ɹ�</font>
				<%if(info.getGiftNumber()!=null&&info.getGiftNumber().length()>0){//�򵼲���%>
					<a href="mbr_gift_certificates_third.jsp">������</a>
				<%}else{%>			
					<a href="mbr_gift_item_mst_list.jsp">�����б�</a>
				<%}%>			
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
