<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@page import="com.magic.crm.user.entity.*"%>
<%@page import="com.magic.crm.shippingnotice.entity.*"%>
<%@page import="com.magic.crm.shippingnotice.dao.*"%>
<%@page import="com.magic.crm.member.dao.*"%>
<%@page import="com.magic.crm.product.dao.*"%>

<%
User user=new User();
user = (User)session.getAttribute("user");
Supplement info=new Supplement();
      String id=request.getParameter("id");

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      
		try{
		 conn = DBManager.getConnection();      
			sql="update jxc.sto_supplement_mst set status=3 where id="+id;
	
				pstmt=conn.prepareStatement(sql);
				pstmt.executeUpdate();
		%>	
		<a href="supplement_list.jsp">状态修改成功，请返回！</a>
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
