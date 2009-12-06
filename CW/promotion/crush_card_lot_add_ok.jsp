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
      String lot_no = request.getParameter("lot_no");
      String begin_date = request.getParameter("begin_date");
      String end_date = request.getParameter("end_date");
      
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		 conn = DBManager.getConnection();      
        sql=" insert into CRUSH_CARD_LOT (lot_no,begin_date,end_date) values(?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'))";			
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,lot_no);
		pstmt.setString(2,begin_date);
		pstmt.setString(3,end_date);
		pstmt.executeQuery();
		
        response.sendRedirect("crush_card_mst_add.jsp?lot_no=" + lot_no);
        
} catch(Exception se) {

    se.printStackTrace();
    Message.setErrorMsg(request,se.toString());
    request.getRequestDispatcher("/message.jsp").forward(request, response); 
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