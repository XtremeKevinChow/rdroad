<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%

Collection promotionCol=(Collection)session.getAttribute("crush_promotionCol");
User user=new User();
user = (User)session.getAttribute("user");

           Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      PreparedStatement pstmt1=null;
      String condition="";
      StringBuffer newRandomStr=new StringBuffer();
      StringBuffer newPwd=new StringBuffer();
      String sql=""; 			      	       
		try{
		 conn = DBManager.getConnection();  

	   Crush_Card_MST ccm=new Crush_Card_MST();
	   Iterator it=promotionCol.iterator();
	   
		       String sql1="select CARD_NUM from crush_card_mst where  CARD_NUM=? ";
		       
		       sql=" insert into crush_card_mst (CARD_NUM,PASS,CARD_TYPE,STATUS,BEGIN_DATE,END_DATE,CREATE_DATE,SALE_PERSON,SALE_DATE,CRUSH_PERSON,CRUSH_DATE)";
		       sql+= " values(?,?,?,0,sysdate,sysdate,sysdate,?,sysdate,?,sysdate) ";			
		  	 			
	   while(it.hasNext()){
	   ccm=(Crush_Card_MST)it.next();
		        pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, ccm.getCard_num());
			rs = pstmt1.executeQuery();
			if(!rs.next()){	
			  pstmt=conn.prepareStatement(sql);
			  pstmt.setString(1, ccm.getCard_num());
			  pstmt.setString(2, ccm.getPass());
			  pstmt.setString(3, ccm.getCard_type());
			  pstmt.setString(4, user.getId());
			  pstmt.setString(5, user.getId());		  	  
			  pstmt.executeQuery();
			  pstmt.close();  
			  
	                }else{
	                  System.out.println(ccm.getCard_num());
	                }
			rs.close();        
                        pstmt1.close();			


		}


			
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
<a href="javascript:window.close()">操作成功，请关闭窗口</a>&nbsp;&nbsp;&nbsp;