
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

DecimalFormat myformat = new DecimalFormat("###,###.00");
Collection promotionCol = new ArrayList();

User user=new User();
user = (User)session.getAttribute("user");
     String number=request.getParameter("number");
     String need_pass=request.getParameter("need_pass");
	StringBuffer newRandomStr=new StringBuffer();
	StringBuffer newPwd=new StringBuffer();	
     String gift_number=request.getParameter("gift_number");
           Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      PreparedStatement pstmt1=null;
      String condition="";

      String sql="";
      
      MD5 m= new MD5();
      /**************************
      		生成EXCEL
      ***********************************/


      /**************************求n位随机数***************************/
      
      int   num[]   =   new   int[Integer.parseInt(number)];       
      int   p_num[]   =   new   int[Integer.parseInt(number)];    
                                         
      java.util.Random r=new java.util.Random();//这里把随机数定义放到了外边  




      String name="电子礼券";
	response.reset();
		 response.setContentType("application/vnd.ms-excel");
		String filename = new String((name+gift_number).getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   

		JxlBean jxlbean=new JxlBean();
	
        jxlbean.create(response.getOutputStream());

       
		   jxl.write.WritableFont style = new jxl.write.WritableFont(jxl.write.WritableFont.ARIAL,
		                                           9,
		                                           jxl.write.WritableFont.NO_BOLD,
		                                           false,
		                                           jxl.format.UnderlineStyle.NO_UNDERLINE,
		                                           jxl.format.Colour.BLACK);       
		  WritableCellFormat wcf=new WritableCellFormat(style);
		  wcf.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);
	   	  wcf.setAlignment(Alignment.CENTRE);         
    
               
		jxlbean.create_sheet(name,0);			    
		jxlbean.setRowHeigth(0,300);
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,13);
	
		String header="";
		jxlbean.setTitle(name,1);				
		header="卡号,密码";
		jxlbean.addCell(new jxl.write.Label(0,1,"卡号"));	
		//jxlbean.addCell(new jxl.write.Label(1,1,"密码"));
		 sql="select GIFT_NO from MBR_GIFT_LISTS where gift_no=? ";	
	     String in_sql=" insert into MBR_GIFT_LISTS (id,GIFT_NO,PASS,ISNEEDPASS,CREATE_DATE,gift_number,create_person)";
		  in_sql+= " values(sql_MBR_GIFT_LISTS_id.Nextval,?,?,?,sysdate,?,?) ";		 		      	       
		try{
		 conn = DBManager.getConnection();
		 pstmt=conn.prepareStatement(sql);  
		 pstmt1=conn.prepareStatement(in_sql);
 			int j=0;
		
		      for(int i=0;i<Integer.parseInt(number);i++){
	
		   
		      num[i]=10000000+r.nextInt(90000000);  
		      p_num[i]=1000000+r.nextInt(9000000);  
		      	      		      		                                   
			
			pstmt.setString(1, gift_number.substring(0,1)+String.valueOf(num[i]));		        
			rs=pstmt.executeQuery();
			if(rs.next()){		        
			            i--;
			            rs.close();
                        continue ;
			}
				
			pstmt1.setString(1, gift_number.substring(0,1)+String.valueOf(num[i]));
		
			if(Integer.parseInt(need_pass)>0){
				pstmt1.setString(2, m.getMD5ofStr(String.valueOf(p_num[i])).toLowerCase());
			}else{
				pstmt1.setString(2, "");
			}
			pstmt1.setString(3, need_pass);
			pstmt1.setString(4, gift_number);
			pstmt1.setString(5, user.getId());
			 	 
			  pstmt1.executeQuery(); 
			  						
			rs.close();
				
			
			
            Crush_Card_MST ccm=new Crush_Card_MST();
			
			jxlbean.addCell(new jxl.write.Label(0,j+3,gift_number.substring(0,1)+String.valueOf(num[i]),wcf));
			//if(Integer.parseInt(need_pass)>0){
			//	jxlbean.addCell(new jxl.write.Label(1,j+3,String.valueOf(p_num[i]),wcf));
			//}else{
			//	jxlbean.addCell(new jxl.write.Label(1,j+3,"",wcf));
			//}
			//ccm.setCard_num( gift_number.substring(0,1)+String.valueOf(num[i]));
                        //ccm.setPass(m.getMD5ofStr(String.valueOf(p_num[i])).toLowerCase());				
                        //promotionCol.add(ccm);				
		
					j++;
			}
			
			pstmt1.close();
			pstmt.close();
				jxlbean.close();
				//session.setAttribute("promotionCol",promotionCol);
				//session.setAttribute("need_pass",need_pass);
				//session.setAttribute("gift_number",gift_number);

			
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
