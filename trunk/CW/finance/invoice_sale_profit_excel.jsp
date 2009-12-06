<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%

DecimalFormat myformat = new DecimalFormat("###,###.00");

			String startDate=request.getParameter("startDate");
						 startDate=(startDate==null)?"":startDate.trim();
			String endDate=request.getParameter("endDate");
						 endDate=(endDate==null)?"":endDate.trim();
			String type=request.getParameter("type");						 				 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="销售毛利发票汇总";

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
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,13);
		jxlbean.setColWidth(2,13);
		jxlbean.setColWidth(3,13);
		jxlbean.setColWidth(4,13);		 
		jxlbean.setColWidth(5,13);	



		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,5);				
		header="产品类型,销售数量,销售金额,不含税销售金额,销售成本,销售毛利";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;
		
		jxlbean.addCell(new jxl.write.Label(0,1,"开始日期"));
		//jxlbean.mergeCells(1,1,4,1);	
		jxlbean.addCell(new jxl.write.Label(1,1,startDate));	
		jxlbean.addCell(new jxl.write.Label(2,1,"结束日期"));
		//jxlbean.mergeCells(6,1,7,1);
		jxlbean.addCell(new jxl.write.Label(3,1,endDate));	
%>
<%
 
		try{
		 conn = DBManager.getConnection();

			int total_op_qty=0;
		  double total_fact_amt=0;
		  double total_pre_amt=0;
		  double total_pre_amt2=0;
		  double total_chayi=0;
			sql=" select b.item_type,";
			sql+=" sum(a.pre_price) as pre_price ,sum(a.fact_price) as fact_price,";
			sql+=" sum(a.fact_amt) as fact_amt,sum(a.pre_amt) as pre_amt,sum(a.op_qty) as op_qty ";
			sql+=" from magic.fin_stock_detail a"; 
			sql+=" inner join prd_items b on"; 
			sql+=" b.item_id=a.item_id";
			sql+=" where a.doc_type=2 ";
			sql+=" and a.operation_date>=date'"+startDate+"' and a.operation_date<date'"+endDate+"'+1";
			if(type.length()>0){
			sql+=" and b.item_type="+type;
			}
			sql+=" group by b.item_type order by item_type";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//out.println(sql);	

		int i=0;
	        String type_name="";
		while(rs.next()){	
		      String all_item_type=rs.getString("item_type");
		      int all_op_qty=rs.getInt("op_qty");			
		      double all_fact_amt=rs.getDouble("pre_amt");//销售金额
		      double all_pre_amt=0;//不含税销售金额
		      double all_pre_amt2=rs.getDouble("fact_amt");//销售成本
		      
		      if(all_item_type.equals("1")){
		      		all_pre_amt=all_fact_amt/1.13;
		      }else if(all_item_type.equals("6")) {
		      		all_pre_amt=all_fact_amt;
		      }else{
		      		all_pre_amt=all_fact_amt/1.17;
		      }
		      
		      double all_chayi=all_pre_amt-all_pre_amt2;			
		      
		      total_op_qty=total_op_qty+all_op_qty;			
		      total_fact_amt=total_fact_amt+all_fact_amt;
		      total_pre_amt=total_pre_amt+all_pre_amt;
		      total_pre_amt2=total_pre_amt2+all_pre_amt2;
		      total_chayi=total_chayi+all_chayi;
		
		if(all_item_type.equals("1")){
		   type_name="图书";
		}
		if(all_item_type.equals("2")){
		   type_name="影视";
		}
		if(all_item_type.equals("3")){
		   type_name="音乐";
		}
		if(all_item_type.equals("4")){
		   type_name="游戏软件";
		}
		if(all_item_type.equals("5")){
		   type_name="礼品";
		}
		if(all_item_type.equals("6")){
		   type_name="其他";
		}      		
      
		jxlbean.addCell(new jxl.write.Label(0,i+jxl_line,type_name,wcf));
		jxlbean.addCell(new jxl.write.Number(1,i+jxl_line,all_op_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(2,i+jxl_line,myformat.format(all_fact_amt),wcf));
		jxlbean.addCell(new jxl.write.Label(3,i+jxl_line,myformat.format(all_pre_amt),wcf));
		jxlbean.addCell(new jxl.write.Label(4,i+jxl_line,myformat.format(all_pre_amt2),wcf));
		jxlbean.addCell(new jxl.write.Label(5,i+jxl_line,myformat.format(all_chayi),wcf));
		    i++;
		    }		
                jxlbean.addCell(new jxl.write.Label(0,i+jxl_line,"合计",wcf1)); 
                jxlbean.addCell(new jxl.write.Number(1,i+jxl_line,total_op_qty,wcf)); 
                jxlbean.addCell(new jxl.write.Label(2,i+jxl_line,myformat.format(total_fact_amt),wcf)); 
                jxlbean.addCell(new jxl.write.Label(3,i+jxl_line,myformat.format(total_pre_amt),wcf)); 
                jxlbean.addCell(new jxl.write.Label(4,i+jxl_line,myformat.format(total_pre_amt2),wcf)); 
                jxlbean.addCell(new jxl.write.Label(5,i+jxl_line,myformat.format(total_chayi),wcf)); 
		
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




		
    



