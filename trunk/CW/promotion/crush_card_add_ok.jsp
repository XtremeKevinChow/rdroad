
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
     String code=request.getParameter("code");
           Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      PreparedStatement pstmt1=null;
      String condition="";
      StringBuffer newRandomStr=new StringBuffer();
      StringBuffer newPwd=new StringBuffer();
      String sql="";

      MD5 m= new MD5();
      /**************************
      		生成EXCEL
      ***********************************/
      
      int   num[]   =   new   int[Integer.parseInt(number)];       
      int   p_num[]   =   new   int[Integer.parseInt(number)];                                       
      java.util.Random r=new java.util.Random();//这里把随机数定义放到了外边  
      /**************************求n位随机数***************************
      for(int i=0;i<Integer.parseInt(number);i++){
                        //newRandomStr=RandomStr.getRadom(8,0);
                        //newPwd=RandomStr.getRadom(7,0);
      num[i]=10000000+r.nextInt(90000000);  
      p_num[i]=1000000+r.nextInt(9000000);  
      			out.println(num[i]+","+p_num[i]+"<br>");
      }
 	*/
 	
      String name="充值卡号密码";
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
		jxlbean.setRowHeigth(0,300);
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,13);
	
		String header="";
		jxlbean.setTitle(name,1);				
		header="卡号,密码";
		jxlbean.addCell(new jxl.write.Label(0,1,"卡号"));	
		jxlbean.addCell(new jxl.write.Label(1,1,"密码"));
		jxlbean.addCell(new jxl.write.Label(2,1,"类型"));
		
		sql="select CARD_NUM from crush_card_mst where CARD_NUM=?";	
		       String in_sql=" insert into crush_card_mst (CARD_NUM,PASS,CARD_TYPE,STATUS,BEGIN_DATE,END_DATE,CREATE_DATE,SALE_PERSON,SALE_DATE,CRUSH_PERSON,CRUSH_DATE)";
		       	      in_sql+= " values(?,?,?,1,sysdate,sysdate,sysdate,?,sysdate,?,sysdate) ";						      	       
		try{
		 conn = DBManager.getConnection();  
 			int j=0;
		      for(int i=0;i<Integer.parseInt(number);i++){
		      num[i]=10000000+r.nextInt(90000000);  
		      p_num[i]=1000000+r.nextInt(9000000);  
     			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,String.valueOf(num[i]));
			rs=pstmt.executeQuery();
			if(rs.next()){		        
			i--;
                        continue ;
			}
			  pstmt1=conn.prepareStatement(in_sql);
			  pstmt1.setString(1, String.valueOf(num[i]));
			  pstmt1.setString(2, m.getMD5ofStr(String.valueOf(p_num[i])).toLowerCase());
			  pstmt1.setString(3, code);
			  pstmt1.setString(4, user.getId());
			  pstmt1.setString(5, user.getId());		  	  
			  pstmt1.executeQuery();
			  pstmt1.close();  			
			
			
			rs.close();
			pstmt.close();			
                        Crush_Card_MST ccm=new Crush_Card_MST();
                        //ccm.setCard_num(String.valueOf(num[i]));
                        //ccm.setPass(m.getMD5ofStr(String.valueOf(p_num[i])).toLowerCase());
                        //ccm.setCard_type(code);
                        //promotionCol.add(ccm);
 
		      /**************************
		      		生成EXCEL
		      ***********************************/			
				jxlbean.addCell(new jxl.write.Label(0,j+3,String.valueOf(num[i]),wcf));
				jxlbean.addCell(new jxl.write.Number(1,j+3,p_num[i],wcf));
				jxlbean.addCell(new jxl.write.Label(2,j+3,code,wcf));			
					j++;
			}
				jxlbean.close();
				//session.setAttribute("crush_promotionCol",promotionCol);

			
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
<a href="crush_card_add_finish.jsp">把数据插入数据库</a>