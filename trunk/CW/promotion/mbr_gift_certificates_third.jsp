<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.order.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%


Ticket info=new Ticket();
info=(Ticket)session.getAttribute("info");



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

	if(document.form.product_group_id[0].checked&&document.form.item_group_id.value==""){
	alert('请选择产品组!');
	return false;
	}
	document.form.action="mbr_gift_certificates_third1.jsp";
	document.form.submit();
	document.form.input.disabled = true;
}
function initFocus(){
	document.form.item_group_id.disabled=true;
	return true;
}

function ifchecked(){
        document.form.item_group_id.disabled=false;          	
	return true;
}
function ifchecked1(){
        document.form.item_group_id.disabled=true;   
        document.form.item_group_id.value="";        	
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">

	<form   action="" method="post" name="form" >
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
	
	<tr>		
		<td bgcolor="#FFFFFF" width="300">是否和礼券产品组挂钩</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="product_group_id" value="1" onclick="ifchecked()">是
		<input type="radio"  name="product_group_id" value="-1" onclick="ifchecked1()" checked>否				
		</td>
		<td bgcolor="#FFFFFF" width="150">选择产品组</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_id">
		 <option value="">--请选择--</option>
	<%
		  String sql="select * from MBR_GIFT_ITEM_MST where status<>-1 order by item_group_id desc ";
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  
	%>		 
		 <option value="<%=rs.getString("ITEM_GROUP_ID")%>"><%=rs.getString("GROUP_DESC")%></option>	
	<%}%>	 
		</select>				
		</td>	
	</tr>
		<tr>	
		<td bgcolor="#FFFFFF"  align="center" colspan="4">
		<input type="button"  name="input" value="下一步" onclick="next()">如果没有你需要的产品组，你可以<a href="mbr_gift_item_mst.jsp">新增产品组</a>和礼券挂钩

		
		</td>				
			</tr>	
		</table>


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
