<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<%
User user=new User();
user = (User)session.getAttribute("user");
String gift_id=request.getParameter("gift_id");
gift_id=(gift_id==null)?"":gift_id;
String item_id=request.getParameter("item_id");
item_id=(item_id==null)?"":item_id;
String money=request.getParameter("money");
money=(money==null||money.equals(""))?"0":money;
      String id=request.getParameter("id");
String addmoney=request.getParameter("addmoney");
addmoney=(addmoney==null||addmoney.equals(""))?"0":addmoney;
      String clubid=request.getParameter("club_id");      
      
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 int  is_item_add=0;
		 String sql="";		 
		 if(gift_id.equals("1")){
			 sql="select item_id from prd_items where  item_id='"+item_id+"'";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){ 
	                 	is_item_add=1;
	                 }else{
	           %>
			  
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作错误：你输入的礼品号<%=item_id%>不存在,<a href="javascript:history.back()">返回</a></font></td>
	
		</td>		
	</tr>	
</table>			  
	           <%
			 }

		 }else{
			 sql="select * from mbr_gift_certificates where gift_number='"+item_id+"'";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){ 
	                 	is_item_add=1;
	                 }else{
	           %>
			  
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作错误：你输入的礼券号 <%=item_id%> 不存在,<a href="javascript:history.back()">返回</a></font></td>
	
		</td>		
	</tr>	
</table>			  
	           <%	                 
			 } 
		 }
		if(is_item_add==1){	                		                
	                 //String update_sql="update  mbr_msc_gift set item_id='"+item_id+"',type="+gift_id+",create_date=sysdate,operator_id="+user.getId()+",order_require="+money+" where id="+id;
			String update_sql=" update  mbr_msc_gift set item_id='"+item_id+"',type="+gift_id+",create_date=sysdate,";
					  update_sql+= " operator_id="+user.getId()+",order_require="+money;
					  if(gift_id.equals("1")){
					  update_sql+=" ,clubid="+clubid ;
					  }
					  update_sql+=" ,addmoney="+addmoney+" where id="+id;		 	 				 
			 stmt = conn.createStatement(); 
		 	 stmt.executeUpdate(update_sql); 
		 	 %>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="gift_add.jsp?query=1&gift_id=<%=gift_id%>&item_id=<%=item_id%>">返回</a></font></td>
	
		</td>		
	</tr>	
</table>
<%		 	 
		}
		
%>

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
//response.sendRedirect("gift_add.jsp");
%>

</body>
</html>

