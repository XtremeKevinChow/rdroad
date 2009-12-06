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
gift_id=(gift_id==null)?"":gift_id.trim();;
String item_id=request.getParameter("item_id");
item_id=(item_id==null)?"":item_id.trim();
String msc=request.getParameter("msc");
msc=(msc==null)?"":msc;
String money=request.getParameter("money");
money=(money==null||money.equals(""))?"0":money;
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
		 String sql="";

		 boolean is_msc_add=false;
		 boolean is_item_add=false;

		 sql="select * from prd_pricelists where  msc='"+msc+"' and msc is not null ";
		 pstmt=conn.prepareStatement(sql);
		 rs=pstmt.executeQuery();
		 if(rs.next()){ 
                 	is_msc_add=true;
                 }else{
	           %>
			  
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作错误：你输入的MSC号<%=msc%>不存在,<a href="javascript:history.back()">返回</a></font></td>
	
		</td>		
	</tr>	
</table>			  
	           <%
		 }
		 if(gift_id.equals("1")){
			 sql="select item_id from prd_items where  item_code='"+item_id+"'";
			 pstmt=conn.prepareStatement(sql);
			 rs=pstmt.executeQuery();
			 if(rs.next()){ 
			 	item_id=rs.getString("item_id");
	                 	is_item_add=true;
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
	                 	is_item_add=true;
	                 }else{
	           %>
			  
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作错误：你输入的礼券号<%=item_id%>不存在,<a href="javascript:history.back()">返回</a></font></td>
	
		</td>		
	</tr>	
</table>			  
	           <%	                 

			 }	 
		 }	 	
                 if(is_msc_add==true&&is_item_add==true){	
			 int id=0;
			 sql="select max(id) from mbr_msc_gift";
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){ 
		                 	id=rs.getInt(1)+1;
		                }
		                
			 sql="select * from mbr_msc_gift where msc_code='"+msc+"' and item_id='"+item_id+"'";
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){ 
	           %>
			  
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作错误：对应MSC的礼品号或礼券号已存在 <%=item_id%> 已经存在,<a href="javascript:history.back()">返回</a></font></td>
	
		</td>		
	</tr>	
</table>			  
	           <%
		                }else{		
		                                		                
		                         String in_sql="insert into mbr_msc_gift(id,msc_code,item_id,operator_id,type,order_require,addmoney,clubid) ";
					 in_sql+=" VALUES("+id+" ,'"+msc+"','"+item_id+"',"+user.getId()+","+gift_id+","+money+","+addmoney+","+clubid+")";
				 	 //System.out.println(in_sql);
				 	 //out.println(in_sql);					 
					 stmt = conn.createStatement(); 
				 	 stmt.executeUpdate(in_sql); 
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="gift_add.jsp?query=1&gift_id=<%=gift_id%>&item_id=<%=item_id%>">返回</a></font></td>
	
		</td>		
	</tr>	
</table>
<%
			 	 } 
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
//response.sendRedirect("gift_add.jsp?err="+err);
%>
</body>
</html>
