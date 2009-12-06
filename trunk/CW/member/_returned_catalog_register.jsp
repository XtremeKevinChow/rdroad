<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.member.entity.Member"%>
<%@ page import="com.magic.crm.util.CallCenterHander"%>
<%@ page import="com.magic.crm.util.DBManager,com.magic.crm.util.ChangeCoding"%>

<%
   //得到会员
	CallCenterHander hander = new CallCenterHander(request
							.getSession());
	Member member = (Member) hander.getServicedMember();

    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;
	ResultSet rs1=null;
    PreparedStatement pstmt1=null;
	ResultSet rs2=null;
    PreparedStatement pstmt2=null;
    String sql=null;
	try{
		conn = DBManager.getConnection();      
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="0" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td width="50">会员：</td>
		<td  bgcolor="#FFFFFF" width="120">
		<%= member.getCARD_ID() %>    <%= member.getNAME() %>
		<input type="hidden" name="member_id" value="<%= member.getID() %>">
		<td width="80">目录期号：</td>
		<td  bgcolor="#FFFFFF">
		<select name="catalog_id">
		<%
			sql="select id, catalogs_name "
			+ "from prd_catalogs_basic "
			+ "where 1 = 1 order by operate_time desc ";
			
		    //out.println(sql);
			pstmt1=conn.prepareStatement(sql);
			rs1=pstmt1.executeQuery();
			while(rs1.next()){
				int id = rs1.getInt("id");
				String catalogs_name = rs1.getString("catalogs_name");
		%>	
			<option value="<%= id %>"><%= catalogs_name %></option>
		<% } %>
		</select>	
		</td>	
		<td width="80">退回原因：</td>
		<td  bgcolor="#FFFFFF">
		<select name="return_reason">
		<%
			sql="select id, name "
			+ "from s_catalog_return_reason "
			+ "where 1 = 1 order by id ";
			
		    //out.println(sql);
			pstmt2=conn.prepareStatement(sql);
			rs2=pstmt2.executeQuery();
			while(rs2.next()){
				int id = rs2.getInt("id");
				String name = rs2.getString("name");
		%>	
			<option value="<%= id %>"><%= name %></option>
		<% } %>
		</select>	
		</td>	
		<td  bgcolor="#FFFFFF">
		<input type=button name="addBtn" value=" 登记 " onclick="_add_returned_catalog_reason();">	
		</td>
	</tr>	
</table>

<br>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" id="DataTable">
	<tr bgcolor="#FFFFFF" align="center">
		<th width="20%" class="OraTableRowHeader" noWrap  >目录期号</th>	
		<th width="20%" class="OraTableRowHeader" noWrap  >退回原因</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >登记人</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >登记日期</th>
	</tr>
	<%
			sql="select c.catalogs_name, d.name as return_reason, b.name, a.create_date "
			+ "from mbr_catalog_return a inner join org_persons b on a.creator = b.id "
			+ "inner join prd_catalogs_basic c on a.catalog_id = c.id "
			+ "inner join s_catalog_return_reason d on a.return_reason = d.id "
			+ "where a.member_id = ?  order by a.create_date desc ";
			
		    //out.println(sql+"***"+member.getID());
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, member.getID());
			rs=pstmt.executeQuery();
			while(rs.next()){
				String catalog_name = rs.getString("catalogs_name");
				String return_reason = rs.getString("return_reason");
				String create_person = rs.getString("name");
				String create_date = rs.getString("create_date");
			
	%>	
	<tr bgcolor="#FFFFFF" align="center">	
		<td bgcolor="#FFFFFF" align="left"><%=catalog_name%></td>
		<td bgcolor="#FFFFFF" align="left"><%=return_reason%></td>
		<td bgcolor="#FFFFFF" align="left"><%=create_person%></td>
		<td bgcolor="#FFFFFF" align="left"><%=create_date%></td>
	</tr>	
	<% } %>
</table>



</form>
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
if (rs1 != null)
	try {
		rs1.close();
	} catch (Exception e) {}			
if (pstmt1 != null)
	try {
		pstmt1.close();
	} catch (Exception e) {}
 try {
	 conn.close();
 	} catch(SQLException sqe) {}

}
%>