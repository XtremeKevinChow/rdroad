<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%

     int id= Integer.parseInt(request.getParameter("crush_id"));
	 int money= Integer.parseInt(request.getParameter("crush_money"));
     String desc = request.getParameter("crush_desc");
           Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		 conn = DBManager.getConnection();      
        sql=" insert into CRUSH_CARD_VALUE (id,money,description) values(?,?,?)";			
		pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1,id);
		pstmt.setInt(2,money);
		pstmt.setString(3,desc);
		pstmt.executeQuery();


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
response.sendRedirect("crush_card_type_list.jsp");
%>