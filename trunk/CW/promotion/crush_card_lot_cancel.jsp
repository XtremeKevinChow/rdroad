<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%@ page import="com.magic.crm.user.entity.*"%>

<%
String lot_no = request.getParameter("lot_no");
Collection promotionCol = new ArrayList();
User user=new User();
user = (User)session.getAttribute("user");

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String sql="update crush_card_mst set status=-1,operator=?,op_time=sysdate where card_lot=? ";
      String sql1 = "update crush_card_lot set status=-1,operator=?,op_time=sysdate where lot_no=? ";
try{
	conn = DBManager.getConnection();  
	pstmt=conn.prepareStatement(sql);
	pstmt.setInt(1,Integer.parseInt(user.getId()));
	pstmt.setString(2,lot_no);
	pstmt.executeUpdate();
	pstmt.close();
	
	pstmt = conn.prepareStatement(sql1);
	pstmt.setInt(1,Integer.parseInt(user.getId()));
	pstmt.setString(2,lot_no);
	pstmt.executeUpdate();
	
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

response.sendRedirect("crush_card_lot_list.jsp");
%>

