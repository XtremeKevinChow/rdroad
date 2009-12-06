<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ page import="com.magic.crm.util.DateUtil"%>
<%
User user=new User();
user = (User)session.getAttribute("user");
Ticket info1=new Ticket();
info1=(Ticket)session.getAttribute("info");
 
String gift_number=request.getParameter("gift_number");
String level_id=request.getParameter("level_id");
String order_require=request.getParameter("order_require");
String dis_amt=request.getParameter("dis_amt");
String is_discount = request.getParameter("is_discount");
String item_group_id=request.getParameter("item_group_id");
if(is_discount==null) {
	is_discount="N";
}
String dis_type = request.getParameter("dis_type");
String begin_date=request.getParameter("begin_date");
String end_date=request.getParameter("end_date");
Mbr_gift_money_by_order info=new Mbr_gift_money_by_order();
info.setLevel_id(Integer.parseInt(level_id));
info.setGift_number(gift_number);
info.setOrder_require(Double.parseDouble(order_require));
info.setDis_amt(Double.parseDouble(dis_amt));
info.setIs_discount(is_discount);
info.setItem_group_id(Integer.parseInt(item_group_id));
info.setDis_type(Integer.parseInt(dis_type));

	
info.setBegin_date( DateUtil.getSqlDate(DateUtil.getDate(begin_date,"yyyy-MM-dd")) );
info.setEnd_date( DateUtil.getSqlDate(DateUtil.getDate(end_date,"yyyy-MM-dd")) );
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
 

String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{

 conn = DBManager.getConnection();
 
  if(Mbr_gift_money_by_orderDAO.checkGiftItemGroupID(conn,info)>0){
 %>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作失败，每一个礼券不能使用不同的产品组!<a href="javascript:history.back(-1);">返回</a></font></td>
	
		</td>		
	</tr>	
</table> 
 <%}else if(Mbr_gift_money_by_orderDAO.checkGiftItemDisCount(conn,info)>0){
 %>
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
				<td bgcolor="#FFFFFF" ><font color="red">操作失败，每一个礼券的抵用级别是否使用折扣要统一!<a href="javascript:history.back(-1);">返回</a></font></td>
			
				</td>		
			</tr>	
		</table>  
 		<%	     
}else if(Mbr_gift_money_by_orderDAO.checkGiftItemDisType(conn,info)>0){
 %>
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
				<td bgcolor="#FFFFFF" ><font color="red">操作失败，抵扣策略和之前的礼券不同!<a href="javascript:history.back(-1);">返回</a></font></td>
			
				</td>		
			</tr>	
		</table>  
 		<%	     
}else if(Mbr_gift_money_by_orderDAO.checkGiftMoney(conn,info)>0){
 %>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作失败，相同抵用级别不能重复!<a href="javascript:history.back(-1);">返回</a></font></td>
	
		</td>		
	</tr>	
</table> 
 <%
 }else{

		 Mbr_gift_money_by_orderDAO.insert(conn,info);
		
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">
				<%if(info1!=null){//向导操作%>
					礼券批号&nbsp;<%=info1.getGiftNumber()%> 抵用级别已经设置成功！&nbsp;&nbsp;&nbsp;<a href="mbr_gift_certificates_third.jsp">下一步</a>
				<%}else{%>			
					操作成功<a href="mbr_gift_level_list.jsp">返回列表</a>
				<%}%>		

		
					
		</font></td>
	
		</td>		
	</tr>	
</table>


<!--*************************************************************************** -->
<%
}
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
