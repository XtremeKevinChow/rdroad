<%@ page contentType="text/html;charset=GBK" %>
<%@page import="com.magic.crm.util.DBManager"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >

	
<%
	String orderId = request.getParameter("orderId");
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;
	String sql=" select a.mod_date, b.name from ord_headers a "
			+ "inner join org_persons b on a.modifier_id = b.id "
			+ "where a.id = ? and a.status = -1";
		try{
			conn = DBManager.getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(orderId));
			rs = pstmt.executeQuery();	
 
			if(rs.next()){	
				String cancelDate = rs.getString("mod_date");
				String cancelPersons = rs.getString("name");
%>
<table width="200" align="center" border=0 cellspacing=1>
<TR>
	<TD class="OraTableRowHeader" noWrap >取消时间：</TD>
	<TD><%= cancelDate %></TD>
</TR>
<TR>
	<TD class="OraTableRowHeader" noWrap >取消人：</TD>
	<TD><%= cancelPersons %></TD>
</TR>
<TR>
	<TD colspan="2" align="center"><input type="button" value=" 关闭 " onclick="window.close();"></TD>
</TR>

</TABLE>
			
<%
}// if
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
		</table>
	</form>


</body>
</html>
