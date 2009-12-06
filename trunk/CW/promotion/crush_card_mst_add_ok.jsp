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

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      PreparedStatement pstmt1=null;
      String condition="";
      StringBuffer newRandomStr=new StringBuffer();
      StringBuffer newPwd=new StringBuffer();
      String sql="";


      /**************************
      		生成EXCEL
      ***********************************/
      
      int num;
      int pnum1,pnum2;
      String pnum;                                      
      java.util.Random r=new java.util.Random();//这里把随机数定义放到了外边
      r.setSeed(new java.util.Date().getTime()); 
      
 	
      String name="充值卡信息";
	    response.reset();
		 response.setContentType("application/vnd.ms-excel");
		String filename = new String(name.getBytes("gb2312"),"iso8859-1");
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
               /********************************************************************/
               
		jxlbean.create_sheet(name,0);			    
		//jxlbean.setRowHeigth(0,300);
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,25);
		jxlbean.setColWidth(2,13);
		jxlbean.setColWidth(3,13);
	
		String header="";
		jxlbean.setTitle(name,3);				

		jxlbean.addCell(new jxl.write.Label(0,1,"卡号"));	
		jxlbean.addCell(new jxl.write.Label(1,1,"密码"));
		jxlbean.addCell(new jxl.write.Label(2,1,"面值"));
		jxlbean.addCell(new jxl.write.Label(3,1,"批次"));
		
		sql="select CARD_NUM from crush_card_mst where CARD_NUM=?";	
		
        String in_sql=" insert into crush_card_mst (CARD_NUM,PASS,CARD_TYPE,card_lot,operator) values(?,?,?,?,?) ";						      	       
		try{
		 conn = DBManager.getConnection();  
		 pstmt=conn.prepareStatement(sql);
		 pstmt1=conn.prepareStatement(in_sql);
		 
 		  String[] types = request.getParameterValues("type_id");
 		  String[] moneys = request.getParameterValues("money");
 		  String[] lots = request.getParameterValues("lot_no");
 		  String[] quantitys = request.getParameterValues("quantity");
 		  int j=0;
 		  
 		  for(int k=0;k<quantitys.length;k++ ) {
 		    int count = Integer.parseInt(quantitys[k]);
 		    if (count == 0) {
 		        continue;
 		    }
 		    
    	    for(int i=0;i<count;i++){
    	    
    	    num =10000000+r.nextInt(90000000);  
    	    pnum1=10000000+r.nextInt(90000000);
    	    pnum2=10000000+r.nextInt(90000000);
    	    pnum =String.valueOf(pnum1) + String.valueOf(pnum2); 
    	    
     			
			pstmt.setString(1,String.valueOf(num));
			rs=pstmt.executeQuery();
			if(rs.next()){		        
    			i--;
    			rs.close();
                continue ;
			} else{
			    rs.close();
			}
			 
			pstmt1.setString(1, String.valueOf(num));
    		pstmt1.setString(2, pnum);
    		pstmt1.setInt(3,Integer.parseInt(types[k]));
    		pstmt1.setString(4, lots[k]);
    		pstmt1.setString(5, user.getId());		  	  
    		pstmt1.executeUpdate();
    		
            
		      /**************************
		      		生成EXCEL
		      ***********************************/			
			jxlbean.addCell(new jxl.write.Label(0,j+2,String.valueOf(num),wcf));
			jxlbean.addCell(new jxl.write.Label(1,j+2,pnum,wcf));
			jxlbean.addCell(new jxl.write.Number(2,j+2,Integer.parseInt(moneys[k]),wcf));	
			jxlbean.addCell(new jxl.write.Label(3,j+2,lots[k],wcf));			
				j++;
			}
			}
			//pstmt.close();
			//pstmt.close();
			jxlbean.close();
			
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
if (pstmt1 != null)
	try {
		pstmt1.close();
	} catch (Exception e) {}	
 try {
	 conn.close();
 	} catch(SQLException sqe) {}

}

%>
