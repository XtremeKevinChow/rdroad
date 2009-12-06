<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String id=request.getParameter("id");

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String sql="";


try{
		 conn = DBManager.getConnection();
		  sql="select * from MBR_GIFT_CERTIFICATES where GIFT_NUMBER='"+id + "'";
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String rs_gift_number=rs.getString("gift_number");
		  String person_num=rs.getString("person_num");
		  String amount=rs.getString("amount");

		  String gift_money=rs.getString("gift_money");
		  String order_money=rs.getString("order_money");
		  String start_date=rs.getString("start_date");
		         start_date=(start_date==null)?"":start_date.substring(0,10);
		  String end_date=rs.getString("end_date");
		  	 end_date=(end_date==null)?"":end_date.substring(0,10);
		  String gift_type=rs.getString("gift_type");
		  String member_start_date=rs.getString("member_start_date");
		         member_start_date=(member_start_date==null)?"":member_start_date.substring(0,10);
		  String member_end_date=rs.getString("member_end_date");
		  	 member_end_date=(member_end_date==null)?"":member_end_date.substring(0,10);
		  String description=rs.getString("description");
		         description=(description==null)?"":description;
		  String is_new_member=rs.getString("is_new_member");
		  String is_old_member=rs.getString("is_old_member");
		  String is_web=rs.getString("is_web");
		  String is_member_level=rs.getString("is_member_level");
		  String product_group_id=rs.getString("product_group_id");
		  String is_money_for_order=rs.getString("is_money_for_order");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">礼券活动详细</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td width="150">活动号</td>
		<td bgcolor="#FFFFFF"><%=rs_gift_number%></td>
		<td width="150">礼券类型</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(gift_type.equals("1")){
		   out.println("入会");
		}
		if(gift_type.equals("2")){
		   out.println("普通");
		}
		if(gift_type.equals("4")){
		   out.println("公共礼券");
		}
		if(gift_type.equals("5")){
		   out.println("绑定私有礼券");
		}	
		if(gift_type.equals("6")){
		   out.println("不绑定私有礼券");
		}									        
	        %>
		</td>	
	</tr>
	<!--
	<tr>		
		<td width="150">个人使用次数</td>
		<td bgcolor="#FFFFFF"><%=person_num%> </td>	
		<td width="150">总计使用次数</td>
		<td bgcolor="#FFFFFF"><%=amount%></td>
	
	</tr>-->
	<tr>		
		<td width="150">抵用金额</td>
		<td bgcolor="#FFFFFF"><%=gift_money%></td>
		<td width="150">订单金额</td>
		<td bgcolor="#FFFFFF"><%=order_money%></td>		
	
	</tr>
	<tr>		
		<td width="150">开始日期</td>
		<td bgcolor="#FFFFFF"><%=start_date%>
		<td width="150">结束日期</td>
		<td bgcolor="#FFFFFF"><%=end_date%></td>		
	
	</tr>
	<!--
	<tr>		
		<td width="150">会员注册开始日期</td>
		<td bgcolor="#FFFFFF"><%=member_start_date%></td>
		<td width="150">会员注册结束日期</td>
		<td bgcolor="#FFFFFF"><%=member_end_date%></td>		
	
	</tr>

	<tr>		
		<td width="150">是否新会员用</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(is_new_member.equals("1")){
		   out.println("是");
		}
		if(is_new_member.equals("0")){
		   out.println("否");
		}
		if(is_new_member.equals("-1")){
		   out.println("不做考虑");
		}		
		%>		
		</td>
		<td width="150">是否老会员用</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(is_old_member.equals("1")){
		   out.println("是");
		}
		if(is_old_member.equals("0")){
		   out.println("否");
		}
		if(is_old_member.equals("-1")){
		   out.println("不做考虑");
		}		
		%>			
		</td>		
	
	</tr>

	<tr>		

		<td width="150">是否和会员级别挂钩</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(Integer.parseInt(is_member_level)>=1){
		   out.println("是");
		}
		if(is_member_level.equals("-1")){
		   out.println("否");
		}
		
		%>					
		</td>	
		<td width="150">会员级别</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(Integer.parseInt(is_member_level)>=1){
			if(is_member_level.equals("0")){
			   out.println("临时会员");
			}
			if(is_member_level.equals("1")){
			   out.println("普通会员");
			}
			if(is_member_level.equals("2")){
			   out.println("银卡会员");
			}
			if(is_member_level.equals("3")){
			   out.println("金卡会员");
			}
			if(is_member_level.equals("4")){
			   out.println("白金卡会员");
			}								
		}

		
		%>					
		</td>				
	
	</tr>	
	<tr>		
		<td width="150">是否和礼券产品组挂钩</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(Integer.parseInt(product_group_id)>=1){
		   out.println("是");
		}
		if(product_group_id.equals("-1")){
		   out.println("否");
		}
			
		%>				
		</td>
		<td width="150">产品组</td>
		<td bgcolor="#FFFFFF">
	        <%
	        if(Integer.parseInt(product_group_id)>=1){
			  String sql1="select * from MBR_GIFT_ITEM_MST where  item_group_id="+product_group_id;
			 PreparedStatement pstmt1=conn.prepareStatement(sql1);
			 ResultSet rs1=pstmt1.executeQuery();
			  if(rs1.next()){
			      out.println(rs1.getString("GROUP_DESC"));
			  }
			  rs1.close();
			  pstmt1.close();	
		}
		%>					
		</td>		
	
	</tr>	
	<tr>
		<td width="150">是否仅供网上使用</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(is_web.equals("0")){
		   out.println("否");
		}
		if(is_web.equals("1")){
		   out.println("是");
		}
		%>		
		
		</td>
		<td width="150">是否和订单抵用级别挂钩</td>
		<td bgcolor="#FFFFFF">
	        <%
		if(is_money_for_order.equals("1")){
		   out.println("是");
		}
		if(is_money_for_order.equals("-1")){
		   out.println("否");
		}
		
		%>			
		</td>				
	</tr>
	-->
	<tr>		
		<td width="150">描述</td>
		<td bgcolor="#FFFFFF" colspan="3"><%=description%></td>
	
	</tr>


</table>


</body>
</html>
<%}%>
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
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
%>