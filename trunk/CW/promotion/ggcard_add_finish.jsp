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

Collection promotionCol=(Collection)session.getAttribute("promotionCol");
User user=new User();
user = (User)session.getAttribute("user");
String gift_number=session.getAttribute("gift_number").toString();
String need_pass=session.getAttribute("need_pass").toString();
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
	   String sql1="select GIFT_NO from MBR_GIFT_LISTS where  GIFT_NO=? ";
	          sql=" insert into MBR_GIFT_LISTS (id,GIFT_NO,PASS,ISNEEDPASS,CREATE_DATE,gift_number,create_person)";
		  sql+= " values(sql_MBR_GIFT_LISTS_id.Nextval,?,?,?,sysdate,?,?) ";			
	  	  	   
	   while(it.hasNext()){
	   ccm=(Crush_Card_MST)it.next();
	   
	   

			pstmt1=conn.prepareStatement(sql1);
			pstmt1.setString(1, ccm.getCard_num());
			rs=pstmt1.executeQuery();
			if(!rs.next()){	
				   	 
			pstmt=conn.prepareStatement(sql);	
			pstmt.setString(1, ccm.getCard_num());
			if(need_pass.length()>0){
				pstmt.setString(2, ccm.getPass());
			}else{
				pstmt.setString(2, "");
			}
			pstmt.setString(3, need_pass);
			pstmt.setString(4, gift_number);
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