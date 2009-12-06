<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.order.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
 String tag=request.getParameter("tag");
       tag=(tag==null)?"":tag;  
  String id=request.getParameter("id");
         id=(id==null)?"":id;
  String gift_number=request.getParameter("gift_number").toUpperCase();
  String person_num=request.getParameter("person_num");
  String amount=request.getParameter("amount");
  String gift_money=request.getParameter("gift_money");
  String order_money=request.getParameter("order_money");
  String start_date=request.getParameter("start_date");
  String item_group_id=request.getParameter("item_group_id");   
  String end_date=request.getParameter("end_date");

  String gift_type=request.getParameter("gift_type");
  String member_start_date=request.getParameter("member_start_date");
         member_start_date=(member_start_date==null)?"":member_start_date;
  String member_end_date=request.getParameter("member_end_date");
  	 member_end_date=(member_end_date==null)?"":member_end_date;
  String description=request.getParameter("description");
  String level_id=request.getParameter("level_id");
  String is_new_member=request.getParameter("is_new_member");
  String is_old_member=request.getParameter("is_old_member");
  String is_web=request.getParameter("is_web");
  String is_member_level=request.getParameter("is_member_level");
  String product_group_id=request.getParameter("product_group_id");
  String is_money_for_order=request.getParameter("is_money_for_order");
Ticket info=new Ticket();
if(gift_type.equals("4")&&id.equals("")){
gift_number="P"+gift_number;
}
if(gift_type.equals("5")&&id.equals("")){
gift_number="E"+gift_number;
}


if(is_member_level.equals("1")){
info.setIsMemberLevel(Integer.parseInt(level_id));

}else{
info.setIsMemberLevel(Integer.parseInt(is_member_level));

}

if(product_group_id.equals("1")){
info.setProductGroupId(Integer.parseInt(item_group_id));
}else{
info.setProductGroupId(Integer.parseInt(product_group_id));
}

info.setGiftNumber(gift_number);
info.setPersonNum(Integer.parseInt(person_num));
info.setAmount(Integer.parseInt(amount));
info.setGiftMoney(Double.parseDouble(gift_money));
info.setOrderMoney(Double.parseDouble(order_money));

info.setStartDate(start_date);
info.setEndDate(end_date);
info.setMemberStartDate(member_start_date);
info.setMemberEndDate(member_end_date);

//if(member_start_date.length()>0){
//info.setMemberStartDate(java.sql.Date.valueOf(member_start_date));
//}
//if(member_end_date.length()>0){
//info.setMemberEndDate(java.sql.Date.valueOf(member_end_date));
//}

info.setGiftType(Integer.parseInt(gift_type));

info.setIsNewMember(Integer.parseInt(is_new_member));
info.setIsOldMember(Integer.parseInt(is_old_member));
info.setIsWeb(Integer.parseInt(is_web));

info.setIsMoneyForOrder(Integer.parseInt(is_money_for_order));
info.setDescription(description);
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
if(id.length()>0){//修改操作
   if(TicketDAO.checkIfused(conn,info.getGiftNumber())>0){//判断礼券是否已经被使用，被使用过就不能被修改
	%>
 	<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
		<tr>
			<td bgcolor="#FFFFFF" ><font color="red">操作失败，礼券号 <%=info.getGiftNumber()%> 已经被使用，不能修改。<a href="javascript:history.back(-1);">返回</a></font></td>
		
			</td>		
		</tr>	
	</table>
	<%	   
   }else{
	info.setId(Integer.parseInt(id));
	TicketDAO.update(conn,info);
	%>
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
				<td bgcolor="#FFFFFF" ><font color="red">
				操作成功<a href="mbr_gift_certificates_list.jsp">返回列表</a>
				</font></td>
			
				</td>		
			</tr>	
		</table>
	<%	
    }
}else{//新增
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
			
					批号&nbsp;<%=info.getGiftNumber()%>&nbsp;已经设置成功！	<a href="mbr_gift_certificates_list.jsp">返回列表</a>

				</font></td>
			
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
