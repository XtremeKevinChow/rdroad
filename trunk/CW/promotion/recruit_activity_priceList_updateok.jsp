<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
User user=new User();
user = (User)session.getAttribute("user");
String id=request.getParameter("id");
String sectionid=request.getParameter("sectionid");
String queryItemCode=request.getParameter("queryItemCode");
       queryItemCode=(queryItemCode==null)?"0":queryItemCode;      
String queryItemId=request.getParameter("queryItemId");

String selltype=request.getParameter("selltype");
String price=request.getParameter("price");
String start_date=request.getParameter("start_date");
String end_date=request.getParameter("end_date");
String overx=request.getParameter("overx");

Recruit_Activity_PriceList info=new Recruit_Activity_PriceList();
info.setId(Integer.parseInt(id));
info.setSectionId(Integer.parseInt(sectionid));
info.setItemId(Integer.parseInt(queryItemId));
info.setItemCode(queryItemCode);
info.setSellType(Integer.parseInt(selltype));
info.setPrice(Double.parseDouble(price));
info.setOverx(Double.parseDouble(overx));
info.setStartDate(start_date);
info.setEndDate(end_date);
info.setCreatorId(Integer.parseInt(user.getId()));
info.setLastModifierId(Integer.parseInt(user.getId()));

String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();

	
		Recruit_Activity_PriceListDAO.update(conn,info);
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="recruit_activity_priceList_list.jsp">返回列表</a></font></td>
	
		</td>		
	</tr>	
</table>


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
