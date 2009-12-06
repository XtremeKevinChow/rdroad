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
      String supply_id=request.getParameter("supply_id");

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      
		try{
		 conn = DBManager.getConnection();      

			int ship_id=SupplementDAO.createSupply(conn,Integer.parseInt(supply_id),Integer.parseInt(user.getId()));

		if(ship_id>0){
		%>
		<a href="supplement_list.jsp">审核成功，请返回！</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../order/snView.do?sn_id=<%=ship_id%>">查看新发货单</a></a>	
		<%}else{
		     if(ship_id==-5){
		     %>
		       <a href="../stock/Inbound.do?type=initReturnRk">审核失败，发货单产品缺货，请做退货处理！</a>		
		     <%}else{%>
		    	<a href="supplement_list.jsp">审核失败，请返回！</a>
		    	<%}%>	
		<%}//%>	
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
