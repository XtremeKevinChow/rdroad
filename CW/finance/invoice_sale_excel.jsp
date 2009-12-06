<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%@page import="com.magic.crm.finance.entity.FinSales,com.magic.crm.finance.dao.*"%>

<%

DecimalFormat myformat = new DecimalFormat("###,###.00");

			String startDate=request.getParameter("startDate");
						 startDate=(startDate==null)?"":startDate.trim();
			String endDate=request.getParameter("endDate");
						 endDate=(endDate==null)?"":endDate.trim();
									 				 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="销售成本结算明细及汇总表";

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
		jxlbean.setTitle(name,34);				
		header="产品类型,销售数量,销售金额,不含税销售金额,销售成本,销售毛利";
		//jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;
		
	
		jxlbean.mergeCells(1,1,2,1);
		jxlbean.addCell(new jxl.write.Label(1,1,"开始日期:"+startDate));	
		jxlbean.mergeCells(3,1,4,1);
		jxlbean.addCell(new jxl.write.Label(3,1,"结束日期:"+endDate));
	
		//****************************生成表格头部*************************************//
		//列，行，合并到第X列，合并到第Y行
	
		jxlbean.mergeCells(0,2,0,4);
		jxlbean.addCell(new jxl.write.Label(0,2,"产品类型",wcf));	
		jxlbean.mergeCells(1,2,2,3);
		jxlbean.mergeCells(1,2,2,1);
		jxlbean.addCell(new jxl.write.Label(1,2,"正常销售",wcf));
		jxlbean.addCell(new jxl.write.Label(1,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(2,4,"金额",wcf));
		jxlbean.mergeCells(3,2,4,3);
		jxlbean.mergeCells(3,2,4,1);	
		jxlbean.addCell(new jxl.write.Label(3,2,"正常销售退回",wcf));
		jxlbean.addCell(new jxl.write.Label(3,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(4,4,"金额",wcf));		
		jxlbean.mergeCells(5,2,6,3);
		jxlbean.mergeCells(5,2,6,1);	
		jxlbean.addCell(new jxl.write.Label(5,2,"领用产品出库",wcf));
		jxlbean.addCell(new jxl.write.Label(5,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(6,4,"金额",wcf));		
		jxlbean.mergeCells(7,2,8,3);
		jxlbean.mergeCells(7,2,8,1);	
		jxlbean.addCell(new jxl.write.Label(7,2,"借用产品出库",wcf));
		jxlbean.addCell(new jxl.write.Label(7,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(8,4,"金额",wcf));		
		jxlbean.mergeCells(9,2,10,3);
		jxlbean.mergeCells(9,2,10,1);	
		jxlbean.addCell(new jxl.write.Label(9,2,"借用产品入库",wcf));
		jxlbean.addCell(new jxl.write.Label(9,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(10,4,"金额",wcf));		
		jxlbean.mergeCells(11,2,12,3);
		jxlbean.mergeCells(11,2,12,1);	
		jxlbean.addCell(new jxl.write.Label(11,2,"扣单上架入库",wcf));
		jxlbean.addCell(new jxl.write.Label(11,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(12,4,"金额",wcf));		
		jxlbean.mergeCells(13,2,14,3);
		jxlbean.mergeCells(13,2,14,1);	
		jxlbean.addCell(new jxl.write.Label(13,2,"库存零成本调整",wcf));	
		jxlbean.addCell(new jxl.write.Label(13,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(14,4,"金额",wcf));
		jxlbean.mergeCells(15,2,18,2);	
		jxlbean.mergeCells(15,3,16,1);	
		jxlbean.mergeCells(17,3,18,1);	
		jxlbean.addCell(new jxl.write.Label(15,2,"盘点",wcf));	
		jxlbean.addCell(new jxl.write.Label(15,3,"盘赢",wcf));
		jxlbean.addCell(new jxl.write.Label(17,3,"盘亏",wcf));
		jxlbean.addCell(new jxl.write.Label(15,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(16,4,"金额",wcf));	
		jxlbean.addCell(new jxl.write.Label(17,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(18,4,"金额",wcf));	
		jxlbean.mergeCells(19,2,30,2);
		jxlbean.addCell(new jxl.write.Label(19,2,"各项差异",wcf));
		jxlbean.mergeCells(19,3,20,1);
		jxlbean.addCell(new jxl.write.Label(19,3,"门店调拨入库",wcf));	
		jxlbean.addCell(new jxl.write.Label(19,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(20,4,"金额",wcf));			
		jxlbean.mergeCells(21,3,22,1);	
		jxlbean.addCell(new jxl.write.Label(21,3,"库存调整",wcf));
		jxlbean.addCell(new jxl.write.Label(21,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(22,4,"金额",wcf));				
		jxlbean.mergeCells(23,3,24,1);	
		jxlbean.addCell(new jxl.write.Label(23,3,"扣单上架",wcf));
		jxlbean.addCell(new jxl.write.Label(23,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(24,4,"金额",wcf));				
		jxlbean.mergeCells(25,3,26,1);
		jxlbean.addCell(new jxl.write.Label(25,3,"退货上架",wcf));	
		jxlbean.addCell(new jxl.write.Label(25,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(26,4,"金额",wcf));				
		jxlbean.mergeCells(27,3,28,1);	
		jxlbean.addCell(new jxl.write.Label(27,3,"退货破损",wcf));
		jxlbean.addCell(new jxl.write.Label(27,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(28,4,"金额",wcf));				
		jxlbean.mergeCells(29,3,30,1);
		jxlbean.addCell(new jxl.write.Label(29,3,"换货破损",wcf));
		jxlbean.addCell(new jxl.write.Label(29,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(30,4,"金额",wcf));		
		jxlbean.mergeCells(31,3,32,1);
		jxlbean.addCell(new jxl.write.Label(31,3,"手工调库存",wcf));
		jxlbean.addCell(new jxl.write.Label(31,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(32,4,"金额",wcf));	
		jxlbean.mergeCells(33,3,34,1);					
		jxlbean.addCell(new jxl.write.Label(33,3,"手工调成本",wcf));
		jxlbean.addCell(new jxl.write.Label(33,4,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(34,4,"金额",wcf));												
			
								

		
%>
<%
 		int j=1;
		try{
		 conn = DBManager.getConnection();
		Collection rst = new ArrayList();
		FinSalesDAO fsd=new FinSalesDAO();
		FinSales info=new FinSales();
		
		rst=fsd.fin_stock_detail_list(conn,startDate,endDate);
		int all_qty_11=0;
		double all_amt_11=0;
		int all_qty_05=0;
		double all_amt_05=0;
		int all_qty_06=0;
		double all_amt_06=0;
		int all_qty_14=0;
		double all_amt_14=0;
		int all_qty_15=0;
		double all_amt_15=0;
		int all_qty_03=0;
		double all_amt_03=0;
		int all_qty_31=0;
		double all_amt_31=0;
		int all_qty_32=0;
		double all_amt_32=0;
		int all_qty_62=0;
		double all_amt_62=0;
		int all_qty_20=0;
		double all_amt_20=0;
		int all_qty_63=0;
		double all_amt_63=0;
		int all_qty_64=0;
		double all_amt_64=0;
		int all_qty_66=0;
		double all_amt_66=0;
		int all_qty_67=0;
		double all_amt_67=0;
		int all_qty_70=0;
		double all_amt_70=0;
				
		double all_amt_71=0;
		int all_qty_72=0;
		double all_amt_72=0;
		
		String type_name="";
		for(int i=1;i<7;i++){
		int qty_11=fsd.getFin_qty(rst,String.valueOf(i),"11");
		double amt_11=fsd.getFin_amt(rst,String.valueOf(i),"11");
		int qty_05=fsd.getFin_qty(rst,String.valueOf(i),"05");
		double amt_05=fsd.getFin_amt(rst,String.valueOf(i),"05");
		int qty_06=fsd.getFin_qty(rst,String.valueOf(i),"06");
		double amt_06=fsd.getFin_amt(rst,String.valueOf(i),"06");
		int qty_14=fsd.getFin_qty(rst,String.valueOf(i),"14");
		double amt_14=fsd.getFin_amt(rst,String.valueOf(i),"14");
		int qty_15=fsd.getFin_qty(rst,String.valueOf(i),"15");
		double amt_15=fsd.getFin_amt(rst,String.valueOf(i),"15");
		int qty_03=fsd.getFin_qty(rst,String.valueOf(i),"03");
		double amt_03=fsd.getFin_amt(rst,String.valueOf(i),"03");
		int qty_31=fsd.getFin_qty(rst,String.valueOf(i),"31");
		double amt_31=fsd.getFin_amt(rst,String.valueOf(i),"31");
		int qty_32=fsd.getFin_qty(rst,String.valueOf(i),"32");
		double amt_32=fsd.getFin_amt(rst,String.valueOf(i),"32");
		int qty_62=fsd.getFin_qty(rst,String.valueOf(i),"62");
		double amt_62=fsd.getFin_amt(rst,String.valueOf(i),"62");
		int qty_20=fsd.getFin_qty(rst,String.valueOf(i),"20");
		double amt_20=fsd.getFin_amt(rst,String.valueOf(i),"20");
		int qty_63=fsd.getFin_qty(rst,String.valueOf(i),"63");
		double amt_63=fsd.getFin_amt(rst,String.valueOf(i),"63");
		int qty_64=fsd.getFin_qty(rst,String.valueOf(i),"64");
		double amt_64=fsd.getFin_amt(rst,String.valueOf(i),"64");
		int qty_66=fsd.getFin_qty(rst,String.valueOf(i),"66");
		double amt_66=fsd.getFin_amt(rst,String.valueOf(i),"66");
		int qty_67=fsd.getFin_qty(rst,String.valueOf(i),"67");
		double amt_67=fsd.getFin_amt(rst,String.valueOf(i),"67");

		int qty_70=fsd.getFin_qty(rst,String.valueOf(i),"70");
		double amt_70=fsd.getFin_amt(rst,String.valueOf(i),"70");		
		double amt_71=fsd.getFin_amt(rst,String.valueOf(i),"71");
		int qty_72=fsd.getFin_qty(rst,String.valueOf(i),"72");
		double amt_72=fsd.getFin_amt(rst,String.valueOf(i),"72");		
		all_qty_11=all_qty_11+qty_11;
		all_amt_11=all_amt_11+amt_11;
		all_qty_05=all_qty_05+qty_05;
		all_amt_05=all_amt_05+amt_05;
		all_qty_06=all_qty_06+qty_06;
		all_amt_06=all_amt_06+amt_06;
		all_qty_14=all_qty_14+qty_14;
		all_amt_14=all_amt_14+amt_14;
		all_qty_15=all_qty_15+qty_15;
		all_amt_15=all_amt_15+amt_15;
		all_qty_03=all_qty_03+qty_03;
		all_amt_03=all_amt_03+amt_03;
		all_qty_31=all_qty_31+qty_31;
		all_amt_31=all_amt_31+amt_31;
		all_qty_32=all_qty_32+qty_32;
		all_amt_32=all_amt_32+amt_32;
		all_qty_62=all_qty_62+qty_62;
		all_amt_62=all_amt_62+amt_62;
		all_qty_20=all_qty_20+qty_20;
		all_amt_20=all_amt_20+amt_20;
		all_qty_63=all_qty_63+qty_63;
		all_amt_63=all_amt_63+amt_63;
		all_qty_64=all_qty_64+qty_64;
		all_amt_64=all_amt_64+amt_64;
		all_qty_66=all_qty_66+qty_66;
		all_amt_66=all_amt_66+amt_66;
		all_qty_67=all_qty_67+qty_67;
		all_amt_67=all_amt_67+amt_67;
		all_qty_70=all_qty_70+qty_70;
		all_amt_70=all_amt_70+amt_70;		
		all_amt_71=all_amt_71+amt_71;	
		all_qty_72=all_qty_72+qty_72;
		all_amt_72=all_amt_72+amt_72;		
		if(i==1){
		   type_name="图书";
		}
		if(i==2){
		   type_name="影视";
		}
		if(i==3){
		   type_name="音乐";
		}
		if(i==4){
		   type_name="游戏软件";
		}
		if(i==5){
		   type_name="礼品";
		}
		if(i==6){
		   type_name="其他";
		}	
		jxlbean.addCell(new jxl.write.Label(0,i+4,type_name,wcf));
		jxlbean.addCell(new jxl.write.Number(1,i+4,qty_11,wcf));
		jxlbean.addCell(new jxl.write.Label(2,i+4,myformat.format(amt_11),wcf));
		jxlbean.addCell(new jxl.write.Number(3,i+4,qty_05,wcf));
		jxlbean.addCell(new jxl.write.Label(4,i+4,myformat.format(amt_05),wcf));

		jxlbean.addCell(new jxl.write.Number(5,i+4,qty_14,wcf));
		jxlbean.addCell(new jxl.write.Label(6,i+4,myformat.format(amt_14),wcf));
		jxlbean.addCell(new jxl.write.Number(7,i+4,qty_15,wcf));
		jxlbean.addCell(new jxl.write.Label(8,i+4,myformat.format(amt_15),wcf));
		jxlbean.addCell(new jxl.write.Number(9,i+4,qty_03,wcf));
		jxlbean.addCell(new jxl.write.Label(10,i+4,myformat.format(amt_03),wcf));
		jxlbean.addCell(new jxl.write.Number(11,i+4,qty_06,wcf));
		jxlbean.addCell(new jxl.write.Label(12,i+4,myformat.format(amt_06),wcf));		
		jxlbean.addCell(new jxl.write.Label(13,i+4,"",wcf));
		jxlbean.addCell(new jxl.write.Label(14,i+4,myformat.format(amt_71),wcf));
		jxlbean.addCell(new jxl.write.Number(15,i+4,qty_31,wcf));
		jxlbean.addCell(new jxl.write.Label(16,i+4,myformat.format(amt_31),wcf));
		jxlbean.addCell(new jxl.write.Number(17,i+4,qty_32,wcf));
		jxlbean.addCell(new jxl.write.Label(18,i+4,myformat.format(amt_32),wcf));
		jxlbean.addCell(new jxl.write.Number(19,i+4,qty_62,wcf));
		jxlbean.addCell(new jxl.write.Label(20,i+4,myformat.format(amt_62),wcf));
		jxlbean.addCell(new jxl.write.Number(21,i+4,qty_20,wcf));
		jxlbean.addCell(new jxl.write.Label(22,i+4,myformat.format(amt_20),wcf));
		jxlbean.addCell(new jxl.write.Number(23,i+4,qty_64,wcf));
		jxlbean.addCell(new jxl.write.Label(24,i+4,myformat.format(amt_64),wcf));
		jxlbean.addCell(new jxl.write.Number(25,i+4,qty_63,wcf));
		jxlbean.addCell(new jxl.write.Label(26,i+4,myformat.format(amt_63),wcf));
		jxlbean.addCell(new jxl.write.Number(27,i+4,qty_66,wcf));
		jxlbean.addCell(new jxl.write.Label(28,i+4,myformat.format(amt_66),wcf));
		jxlbean.addCell(new jxl.write.Number(29,i+4,qty_67,wcf));
		jxlbean.addCell(new jxl.write.Label(30,i+4,myformat.format(amt_67),wcf));
		jxlbean.addCell(new jxl.write.Number(31,i+4,qty_70,wcf));
		jxlbean.addCell(new jxl.write.Label(32,i+4,myformat.format(amt_70),wcf));		
		jxlbean.addCell(new jxl.write.Number(33,i+4,qty_72,wcf));
		jxlbean.addCell(new jxl.write.Label(34,i+4,myformat.format(amt_72),wcf));																																						
		j++;
		}
		jxlbean.addCell(new jxl.write.Label(0,j+4,"合计",wcf1));
		jxlbean.addCell(new jxl.write.Number(1,j+4,all_qty_11,wcf));
		jxlbean.addCell(new jxl.write.Label(2,j+4,myformat.format(all_amt_11),wcf));
		jxlbean.addCell(new jxl.write.Number(3,j+4,all_qty_05,wcf));
		jxlbean.addCell(new jxl.write.Label(4,j+4,myformat.format(all_amt_05),wcf));

		jxlbean.addCell(new jxl.write.Number(5,j+4,all_qty_14,wcf));
		jxlbean.addCell(new jxl.write.Label(6,j+4,myformat.format(all_amt_14),wcf));
		jxlbean.addCell(new jxl.write.Number(7,j+4,all_qty_15,wcf));
		jxlbean.addCell(new jxl.write.Label(8,j+4,myformat.format(all_amt_15),wcf));
		jxlbean.addCell(new jxl.write.Number(9,j+4,all_qty_03,wcf));
		jxlbean.addCell(new jxl.write.Label(10,j+4,myformat.format(all_amt_03),wcf));
		jxlbean.addCell(new jxl.write.Number(11,j+4,all_qty_06,wcf));
		jxlbean.addCell(new jxl.write.Label(12,j+4,myformat.format(all_amt_06),wcf));		
		jxlbean.addCell(new jxl.write.Label(13,j+4,"",wcf));
		jxlbean.addCell(new jxl.write.Label(14,j+4,myformat.format(all_amt_71),wcf));
		jxlbean.addCell(new jxl.write.Number(15,j+4,all_qty_31,wcf));
		jxlbean.addCell(new jxl.write.Label(16,j+4,myformat.format(all_amt_31),wcf));
		jxlbean.addCell(new jxl.write.Number(17,j+4,all_qty_32,wcf));
		jxlbean.addCell(new jxl.write.Label(18,j+4,myformat.format(all_amt_32),wcf));
		jxlbean.addCell(new jxl.write.Number(19,j+4,all_qty_62,wcf));
		jxlbean.addCell(new jxl.write.Label(20,j+4,myformat.format(all_amt_62),wcf));
		jxlbean.addCell(new jxl.write.Number(21,j+4,all_qty_20,wcf));
		jxlbean.addCell(new jxl.write.Label(22,j+4,myformat.format(all_amt_20),wcf));
		jxlbean.addCell(new jxl.write.Number(23,j+4,all_qty_64,wcf));
		jxlbean.addCell(new jxl.write.Label(24,j+4,myformat.format(all_amt_64),wcf));
		jxlbean.addCell(new jxl.write.Number(25,j+4,all_qty_63,wcf));
		jxlbean.addCell(new jxl.write.Label(26,j+4,myformat.format(all_amt_63),wcf));
		jxlbean.addCell(new jxl.write.Number(27,j+4,all_qty_66,wcf));
		jxlbean.addCell(new jxl.write.Label(28,j+4,myformat.format(all_amt_66),wcf));
		jxlbean.addCell(new jxl.write.Number(29,j+4,all_qty_67,wcf));
		jxlbean.addCell(new jxl.write.Label(30,j+4,myformat.format(all_amt_67),wcf));
		jxlbean.addCell(new jxl.write.Number(31,j+4,all_qty_70,wcf));
		jxlbean.addCell(new jxl.write.Label(32,j+4,myformat.format(all_amt_70),wcf));	
		jxlbean.addCell(new jxl.write.Number(33,j+4,all_qty_72,wcf));
		jxlbean.addCell(new jxl.write.Label(34,j+4,myformat.format(all_amt_72),wcf));								
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




		
    



