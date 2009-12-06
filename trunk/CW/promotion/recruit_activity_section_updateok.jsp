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

String msc_code=request.getParameter("MSC_CODE");
String id=request.getParameter("id");

String type=request.getParameter("type");
String maxgoods=request.getParameter("maxgoods");
String mingoods=request.getParameter("mingoods");
String sectionImg=request.getParameter("sectionImg");
sectionImg=(sectionImg==null)?"":sectionImg;
Recruit_Activity_Section info=new Recruit_Activity_Section();


info.setId(Integer.parseInt(id));
info.setMsc_Code(msc_code);
info.setCreatorId(Integer.parseInt(user.getId()));
info.setType(type);
info.setMaxGoods(Integer.parseInt(maxgoods));
info.setMinGoods(Integer.parseInt(mingoods));
info.setCreatorId(Integer.parseInt(user.getId()));
info.setSectionImg(sectionImg);
String err="";

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();

	
		Recruit_Activity_SectionDAO.update(conn,info);
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">操作成功<a href="recruit_activity_section_list.jsp">返回列表</a></font></td>
	
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
