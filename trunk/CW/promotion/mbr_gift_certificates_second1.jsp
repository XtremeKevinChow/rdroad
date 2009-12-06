<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.order.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
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

Ticket info=new Ticket();
if(gift_type.equals("4")&&id.equals("")){
gift_number="P"+gift_number;
}
if(gift_type.equals("5")&&id.equals("")){
gift_number="E"+gift_number;
}

info.setGiftNumber(gift_number);
info.setPersonNum(Integer.parseInt(person_num));
info.setAmount(Integer.parseInt(amount));
info.setGiftMoney(Double.parseDouble(gift_money));
info.setOrderMoney(Double.parseDouble(order_money));
if(is_member_level.equals("1")){
info.setIsMemberLevel(Integer.parseInt(level_id));

}else{
info.setIsMemberLevel(Integer.parseInt(is_member_level));

}
info.setStartDate(start_date);
info.setEndDate(end_date);
info.setMemberStartDate(member_start_date);
info.setMemberEndDate(member_end_date);



info.setGiftType(Integer.parseInt(gift_type));

info.setIsNewMember(Integer.parseInt(is_new_member));
info.setIsOldMember(Integer.parseInt(is_old_member));
info.setIsWeb(Integer.parseInt(is_web));


info.setDescription(description);
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
 conn = DBManager.getConnection();
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function next() {

	if(document.form.is_money_for_order[0].checked){
		document.form.action="mbr_gift_level_add.jsp";
	}
	if(document.form.is_money_for_order[1].checked){
		document.form.action="mbr_gift_certificates_third.jsp";
	}
	document.form.submit();
	document.form.input.disabled = true;

}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
	<%
	 if(TicketDAO.checkGift(conn,info)>0){//先判断礼券号是否重复		 	 	
	 %>
 	<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
		<tr>
			<td bgcolor="#FFFFFF" ><font color="red">操作失败，礼券批号 <%=info.getGiftNumber()%> 已经存在。<a href="javascript:history.back(-1);">返回</a></font></td>
		
			</td>		
		</tr>	
	</table>
	<%
	}else{
	
	%>
	<form   action="" method="post" name="form" >
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
	
		<td bgcolor="#FFFFFF" width="300">礼券批号<font color="red"><%=info.getGiftNumber()%> </font>是否和订单抵用级别挂钩</td>
		<td bgcolor="#FFFFFF" align="left" width="150">
		<input type="radio"  name="is_money_for_order" value="1">是
		<input type="radio"  name="is_money_for_order" value="0" checked>否	
		</td>
		<td bgcolor="#FFFFFF"  align="left">
		<input type="hidden"  name="gift_number" value="<%=info.getGiftNumber()%>">
		<input type="hidden"  name="tag" value="1">
		<input type="button"  name="input" value="下一步" onclick="next()">
		</td>				
			</tr>	
		</table>
		<%
		session.setAttribute("info", info);

		%>
	<%}%>

</body>
</html>
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
