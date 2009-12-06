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
      String member_id=request.getParameter("member_id");
      String require_date=request.getParameter("require_date");
      String ship_id=request.getParameter("ship_id");
      String redelivery_type1=request.getParameter("redelivery_type1");
      String is_return_orgin=request.getParameter("is_return_orgin");
      String is_postage=request.getParameter("is_postage");
      String id[]=request.getParameterValues("id");
      String redelivery_type[]=request.getParameterValues("redelivery_type");
      String new_item_code[]=request.getParameterValues("new_item_code");
      String rs_item_code[]=request.getParameterValues("rs_item_code");
      String item_num[]=request.getParameterValues("item_num");
      String row_num[]=request.getParameterValues("row_num");
      String old_qty[]=request.getParameterValues("old_qty");
      int error_item_code=0;
      	
      
      info.setRequire_date(require_date);            
      info.setRedelivery_type(Integer.parseInt(redelivery_type1)); 
       
      info.setIs_return_orgin(Integer.parseInt(is_return_orgin)); 
      info.setIs_postage(Integer.parseInt(is_postage)); 
      info.setWriter(Integer.parseInt(user.getId()));
      info.setMember_id(Integer.parseInt(member_id));
      info.setShip_id(Integer.parseInt(ship_id));
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      
		try{
		 conn = DBManager.getConnection();      


      		conn.setAutoCommit(false);
			
			int j=0;
			int if_succes=0;
			//插入头信息	
			SupplementDAO.insertSupplementMst(conn,info);
			for(int i=0;i<row_num.length;i++){
			j=Integer.parseInt(row_num[i]);
		
					if(rs_item_code[j]==null){
					     info.setItem_id(0);
					}else{
					     
					     info.setItem_id(ProductDAO.getItemID(conn,new_item_code[j]));
					     
					}
					if(info.getItem_id()<=0){
					  //error_item_code=-1;
						out.println("<a href=\"javascript:history.back()\">数据新增失败！,货号不匹配！</a>	");
						conn.rollback();
						return;
					}
					
				
	
						//price,sell_type 暂时为0
					info.setQty(Integer.parseInt(item_num[j]));
		
					info.setRemark("补货单");
					info.setDtl_type(Integer.parseInt(redelivery_type[j]));
					info.setOrgin_dtl_id(Integer.parseInt(id[j]));	
						
						if(info.getQty()>Integer.parseInt(old_qty[j])){	
								//if_succes=-1;	
							out.println("<a href=\"javascript:history.back()\">数据新增失败！,有产品补发数量大于原数量，请返回修改！</a>");
							conn.rollback();
							return;
	   			
						}else{		
								
							SupplementDAO.insertSupplementDtl(conn,info);
			
						}
						
				
			}//for
					
		conn.commit();
		out.println("<a href=\"supplement_add.jsp\">数据新增成功，请返回！</a>");

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
