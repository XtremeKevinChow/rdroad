<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String queryItemCode=request.getParameter("queryItemCode");
       queryItemCode=(queryItemCode==null)?"0":queryItemCode;  
String group_id=request.getParameter("group_id");
String item_id=request.getParameter("queryItemId");
item_id=(item_id==null||item_id.equals(""))?queryItemCode:item_id; 
String group_desc=request.getParameter("group_desc");
String min_item_count=request.getParameter("min_item_count");
String is_must=request.getParameter("is_must");

Mbr_gift_item info=new Mbr_gift_item();
info.setItem_group_id(Integer.parseInt(group_id));
info.setItem_id(Integer.parseInt(item_id));
info.setIs_must(Integer.parseInt(is_must));
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
      String sql="";


try{

conn = DBManager.getConnection();
  sql="select * from prd_items where item_code='"+queryItemCode+"'";
  pstmt=conn.prepareStatement(sql);
  rs=pstmt.executeQuery();
  if(!rs.next()){	
  %>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">����ʧ�ܣ�û�������Ʒ��<a href="javascript:history.back(-1);">����������</a></font></td>
	
		</td>		
	</tr>	
</table>  
 <%
 }else{	
 		if(is_must.equals("0")){
		  sql="select count(*) from MBR_GIFT_BY_ITEMS where ITEM_GROUP_ID="+group_id+" and is_must=0";;
	
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  int min_count=0;
		  if(rs.next()){
		  min_count=rs.getInt(1);	
		  }
		  if(min_count==Integer.parseInt(min_item_count)){
		 %>
			<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
				<tr>
					<td bgcolor="#FFFFFF" ><font color="red">�������󣡱�ѡ��Ʒ�����Ѿ��㹻������������<a href="javascript:history.back(-1)">�����б�</a></font></td>
				
					</td>		
				</tr>	
			</table>		 
		 <% 
	 	  }else{
	 	        Mbr_gift_itemDAO.insert(conn,info);
	 	   %>
			<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
				<tr>
					<td bgcolor="#FFFFFF" ><font color="red">�����ɹ�<a href="mbr_gift_item_list.jsp?min_item_count=<%=min_item_count%>&group_id=<%=group_id%>&group_desc=<%=group_desc%>">�����б�</a></font></td>
				
					</td>		
				</tr>	
			</table>	 	   
	 	   <%
	 	  }
  }else{
	Mbr_gift_itemDAO.insert(conn,info);
		 
%>
	<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
		<tr>
			<td bgcolor="#FFFFFF" ><font color="red">�����ɹ�<a href="mbr_gift_item_list.jsp?min_item_count=<%=min_item_count%>&group_id=<%=group_id%>&group_desc=<%=group_desc%>">�����б�</a></font></td>
		
			</td>		
		</tr>	
	</table>
<%}%>
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
