<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%
DecimalFormat myformat = new DecimalFormat("###,###.00");

	String searchRefId=request.getParameter("searchRefId");
	searchRefId=(searchRefId==null)?"":searchRefId.trim();
	
	String searchMbName=request.getParameter("searchMbName");
	searchMbName=(searchMbName==null)?"":searchMbName.trim();	
						 
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String searchPayMethod = request.getParameter("searchPayMethod");
	String status = request.getParameter("status");

    Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="书香卡";

		response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   
		 

		JxlBean jxlbean=new JxlBean();
		
		jxlbean.create(response.getOutputStream());
		
        
		jxl.write.WritableFont red = new jxl.write.WritableFont(jxl.write.WritableFont.ARIAL,
		                                           9,
		                                           jxl.write.WritableFont.BOLD,
		                                           false,
		                                           jxl.format.UnderlineStyle.NO_UNDERLINE,
		                                           jxl.format.Colour.BLACK);  
	   WritableCellFormat wcf1=new WritableCellFormat(red);
	   wcf1.setAlignment(Alignment.LEFT);
       wcf1.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);
       
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
		jxlbean.setRowHeigth(0,500);
		jxlbean.setColWidth(0,20);  
		jxlbean.setColWidth(1,20);
		jxlbean.setColWidth(2,13);
		jxlbean.setColWidth(3,13);
		jxlbean.setColWidth(4,13);		 
		jxlbean.setColWidth(5,13);
		jxlbean.setColWidth(6,13);	
		jxlbean.setColWidth(7,30);	



		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,7);				
		header="汇号,订单号,会员号,姓名,金额,单据日期,付款方式,备注";
		jxlbean.setHeader(header,2,wcf1);
			     
		
								   
		try{
			conn = DBManager.getConnection();
			sql = "select * from mbr_money_input where 1 = 1 "
				+ "and create_date >= to_date(?, 'yyyy-mm-dd') "
				+ "and create_date < to_date(?, 'yyyy-mm-dd') + 1 "
				+ "and use_type = '1' ";	
			if (searchRefId != null && searchRefId.trim().length() > 0) {
				sql += "and ref_id = '" + searchRefId + "' ";
			}
			if (searchMbName != null && searchMbName.trim().length() > 0) {
				sql += "and mbr_name = '" + searchMbName + "' ";
			}
			
			if (Integer.parseInt(searchPayMethod) != -1) {
				sql += "and pay_method = " + searchPayMethod;
			}
			if (Integer.parseInt(status) != -1) {
				sql += "and status = '" + status + "' ";
			}
			//out.println(sql);
			pstmt=conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beginDate);
			pstmt.setString(2, endDate);
			rs=pstmt.executeQuery();  
			
			
			 int i=0;
			 String refId = null;
			 String orderCode =null;
			 String cardId = null;
			 String mbName = null;
			 double money = 0d;
			 String billDate = null;
			 String useType = null;
			 int payMethod = -1;
			 String payMethodName=null;
			 String remark = null;

			double total_money = 0d;
			while(rs.next()) {
				refId=rs.getString("ref_id");
				orderCode=rs.getString("order_code");	
				cardId=rs.getString("mb_code");	
				mbName =rs.getString("mbr_name");	
				money=rs.getDouble("money");	
				total_money += money;
				billDate=rs.getString("bill_date")==null?"":rs.getString("bill_date").substring(0,10);			
				useType=rs.getString("use_type");	
				payMethod=rs.getInt("pay_method");	
				if (payMethod == 6) {
					payMethodName = "邮政汇款";
				} else if (payMethod == 14) {
					payMethodName = "现金";
				} else if (payMethod == 90) {
					payMethodName = "建行回单";
				}
				remark=rs.getString("remark");	
				
				jxlbean.addCell(new jxl.write.Label(0,i+3,refId,wcf));
				jxlbean.addCell(new jxl.write.Label(1,i+3,orderCode,wcf));
				jxlbean.addCell(new jxl.write.Label(2,i+3,cardId,wcf));	
				jxlbean.addCell(new jxl.write.Label(3,i+3,mbName,wcf));
				jxlbean.addCell(new jxl.write.Number(4,i+3,money/100,wcf));	
				jxlbean.addCell(new jxl.write.Label(5,i+3,billDate,wcf));
				
				jxlbean.addCell(new jxl.write.Label(6,i+3,payMethodName,wcf));
				jxlbean.addCell(new jxl.write.Label(7,i+3,remark,wcf));								
	
				i++;
		}
		
			jxlbean.addCell(new jxl.write.Label(0,i+3,"合计：",wcf));
			jxlbean.addCell(new jxl.write.Label(1,i+3,"",wcf));
			jxlbean.addCell(new jxl.write.Label(2,i+3,"",wcf));	
			jxlbean.addCell(new jxl.write.Label(3,i+3,"",wcf));
			jxlbean.addCell(new jxl.write.Number(4,i+3,total_money/100,wcf));	
			jxlbean.addCell(new jxl.write.Label(5,i+3,"",wcf));
			jxlbean.addCell(new jxl.write.Label(6,i+3,"",wcf));
			jxlbean.addCell(new jxl.write.Label(7,i+3,"",wcf));		
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
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
		 %>