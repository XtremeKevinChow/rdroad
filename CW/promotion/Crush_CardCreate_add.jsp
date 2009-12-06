<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean"%>
<%@ page import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.promotion.entity.Crush_Card_MST"%>
<%@page import="com.magic.crm.promotion.dao.*"%>
<%@page import="com.magic.crm.user.entity.User"%>
<%@page import="java.sql.*,java.util.*,java.text.SimpleDateFormat"%>

<%
	User user=new User();
	user = (User)session.getAttribute("user");
	        
	        String card_type=request.getParameter("card_type").trim();
	        String card_qty=request.getParameter("card_qty").trim();
	        String pass_num=request.getParameter("pass_num").trim();
	        Crush_Card_MST ccm=new Crush_Card_MST();
	        Crush_Card_MstDAO ccmDAO=new Crush_Card_MstDAO();
	        ccm.setCard_type(card_type);
	        ccm.setPass_num(pass_num);	        
	        ccm.setCreate_person(Integer.parseInt(user.getId()));
	        Connection conn = null;
	        Random r=new Random();

	        String name="卡号和密码";
			//response.reset();
			//response.setContentType("application/vnd.ms-excel");
			String filename = new String(name.getBytes("gb2312"),"iso8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + ""); 
			JxlBean jxlbean=new JxlBean();
	                jxlbean.create(response.getOutputStream());
			jxlbean.create_sheet(name,0);			    
			jxlbean.setRowHeigth(0,500);
			jxlbean.setColWidth(0,13);  
			jxlbean.setColWidth(1,13);
			jxlbean.setColWidth(2,13);
			//设置浮点数的格式
			jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
			jxlbean.setTitle(name,3);	

			String header="卡号,密码,面值类型";
			jxlbean.setHeader(header,1);				  
			int jxl_line=3;
			int success_num=0;
			int fail_num=0;        
		    try{
		    	
		     	conn = DBManager.getConnection();

		     	RandomStr.setCharset("1-9");//指定字符内容为"0"-"z"，当然也可以是别的，比如"a-z"或"A-Z" 
		     	RandomStr.setLength("8");//指定长度
		     	RandomStr.generateRandomObject();//组合字符串		     	
		     	for(int i=0;i<Integer.parseInt(card_qty);i++){
		     		String s_rnum=RandomStr.getRandom();
		     		String p_rnum=RandomStr.getRandom()+String.valueOf(r.nextInt(10));
			     	if(ccmDAO.checkCardNum(conn,s_rnum)==1){
			     		System.out.println("卡号重复"+s_rnum);
			     		fail_num++;
			     	}else{
			     		ccm.setPass(p_rnum);
			     		ccm.setCard_num(s_rnum);
			     	        ccmDAO.insert(conn,ccm);
			     	    
			    		jxlbean.addCell(new jxl.write.Label(0,jxl_line,s_rnum));
			    		jxlbean.addCell(new jxl.write.Label(1,jxl_line,p_rnum));
			    		jxlbean.addCell(new jxl.write.Label(2,jxl_line,card_type));


			    		jxl_line++;
			    		success_num++;
			     	}
			     	
		     	}
		     	
		     	jxlbean.close();		     	
			} catch(Exception se) {
	
				se.printStackTrace();
		
			 } finally {
			
				 try {
					 conn.close();
				 	} catch(SQLException sqe) {}
		
			 }

%>
dddddddddddddddddd
