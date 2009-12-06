<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%
DecimalFormat myformat = new DecimalFormat("###,###");

      String pricelist_id=request.getParameter("pricelist_id");
      pricelist_id=(pricelist_id==null)?"":pricelist_id;
      String release_date=request.getParameter("release_date");
      release_date=(release_date==null)?"":release_date;					 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="网站产品加印分析报表";
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
		jxlbean.setColWidth(6,13);
		jxlbean.setColWidth(7,13);
		jxlbean.setColWidth(8,13);
		jxlbean.setColWidth(9,13);
		jxlbean.setColWidth(10,13);
		jxlbean.setColWidth(11,13);	
		jxlbean.setColWidth(12,13);
		jxlbean.setColWidth(13,13);
		jxlbean.setColWidth(14,13);
		jxlbean.setColWidth(15,13);
		jxlbean.setColWidth(16,13);
		jxlbean.setColWidth(17,13);
		jxlbean.setColWidth(18,13);
		jxlbean.setColWidth(19,13);



		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,19);				
		header="货号,产品名称,昨日冻结数量,总冻结数量,已下单未冻结,库存数量,在途数量,后台老会员销量,后台新会员销量,前台昨日销量,后台昨日销量,前台5天平均销量,后台5天平均销量,前台10天平均销量,后台10天平均销量,前台15天平均销量,后台15天平均销量,库存支持天数,总库存支持天数A,总库存支持天数B";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;   
								   
		try{
		 conn = DBManager.getConnection();
        sql=" select a.*,c.name,c.item_code as code from prd_sell_analyze1  a"; 
	sql+=" inner join prd_pricelists b on a.pricelist_id=b.id ";
	sql+=" inner join prd_items c on a.item_id=c.item_id";
	sql+=" inner join providers d on c.supplier_id=d.id";
	sql+=" where b.id="+pricelist_id+"  and price_type_id in (3,5) "; 
	sql+=" and to_char(a.release_date,'yyyy-mm-dd')='"+release_date+"'";
	sql+=" order by   (old_sell_quantity_b+new_sell_quantity_b) desc ";

	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	int i=0;
	while(rs.next()){	
		
	String item_name=rs.getString("name");
	String item_code=rs.getString("code");
	
	int all_sell_quantity=rs.getInt("old_sell_quantity")+rs.getInt("new_sell_quantity");//昨日销售数量_前台
	int all_sell_quantity_b=rs.getInt("old_sell_quantity_b")+rs.getInt("new_sell_quantity_b");//昨日销售数量后台
	int frozen_quantity=rs.getInt("frozen_quantity");//冻结数量
	int frozen_quantity_daily=rs.getInt("frozen_quantity_daily");//昨日冻结数量
	int nofrozen_in_order_qty=rs.getInt("nofrozen_in_order_qty");//已下单未冻结数量
	int available_quantity=rs.getInt("available_quantity");//库存数量
	int afloat_quantity=rs.getInt("afloat_quantity");//在途数量
	int old_sell_quantity_b=rs.getInt("old_sell_quantity_b");//后台老会员销量
	int new_sell_quantity_b=rs.getInt("new_sell_quantity_b");//后台新会员销量	
	double sell_last_five=rs.getDouble("sell_last_five");
	double sell_last_ten=rs.getDouble("sell_last_ten");
	double sell_last_fifteen=rs.getDouble("sell_last_fifteen");
	double sell_last_five_b=rs.getDouble("sell_last_five_b");
	double sell_last_ten_b=rs.getDouble("sell_last_ten_b");
	double sell_last_fifteen_b=rs.getDouble("sell_last_fifteen_b");
        String stock_day="";
        String all_stock_day_a="";
        String all_stock_day_b="";
        if(sell_last_ten>0){
	stock_day=myformat.format((available_quantity-frozen_quantity_daily)/sell_last_ten);
	}
	if(sell_last_ten>0){
	all_stock_day_a=myformat.format((available_quantity+afloat_quantity-frozen_quantity_daily)/sell_last_ten);
	}
	if(all_sell_quantity>0){
	all_stock_day_b=myformat.format((available_quantity+afloat_quantity-frozen_quantity_daily)/all_sell_quantity);
	}		 
		jxlbean.addCell(new jxl.write.Label(0,i+3,item_code,wcf));	 
		jxlbean.addCell(new jxl.write.Label(1,i+3,item_name,wcf));
		
		jxlbean.addCell(new jxl.write.Number(2,i+3,frozen_quantity_daily,wcf));
		jxlbean.addCell(new jxl.write.Number(3,i+3,frozen_quantity,wcf));
		jxlbean.addCell(new jxl.write.Number(4,i+3,nofrozen_in_order_qty,wcf));
		jxlbean.addCell(new jxl.write.Number(5,i+3,available_quantity,wcf));
	
		
		jxlbean.addCell(new jxl.write.Number(6,i+3,afloat_quantity,wcf));
		jxlbean.addCell(new jxl.write.Number(7,i+3,old_sell_quantity_b,wcf));
		jxlbean.addCell(new jxl.write.Number(8,i+3,new_sell_quantity_b,wcf));		
		jxlbean.addCell(new jxl.write.Number(9,i+3,all_sell_quantity,wcf));	
		jxlbean.addCell(new jxl.write.Number(10,i+3,all_sell_quantity_b,wcf));		
		jxlbean.addCell(new jxl.write.Number(11,i+3,sell_last_five,wcf));	
		jxlbean.addCell(new jxl.write.Number(12,i+3,sell_last_five_b,wcf));
		jxlbean.addCell(new jxl.write.Number(13,i+3,sell_last_ten,wcf));
		jxlbean.addCell(new jxl.write.Number(14,i+3,sell_last_ten_b,wcf));
		jxlbean.addCell(new jxl.write.Number(15,i+3,sell_last_fifteen,wcf));
		jxlbean.addCell(new jxl.write.Number(16,i+3,sell_last_fifteen_b,wcf));	
		jxlbean.addCell(new jxl.write.Label(17,i+3,stock_day,wcf));
		jxlbean.addCell(new jxl.write.Label(18,i+3,all_stock_day_a,wcf));
		jxlbean.addCell(new jxl.write.Label(19,i+3,all_stock_day_b,wcf));							


																																						
		i++;
		}

						
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