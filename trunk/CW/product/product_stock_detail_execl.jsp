<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%
DecimalFormat myformat = new DecimalFormat("###,###");

       String personid=request.getParameter("personid");
      personid=(personid==null)?"":personid;
      String item_code=request.getParameter("item_code");
      item_code=(item_code==null)?"":item_code; 					 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="产品进销存详细";
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



		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,10);				
		header="货号,产品名称,初次采购入库时间,入库次数,总入库数量,总退库数量,总退厂数量,5天平均销量,10天平均销量,15天平均销量,结算方式";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;   
								   
		try{
		 conn = DBManager.getConnection();
 sql=" select a.*,c.name,c.item_code as code,c.balance_method,b.write_date,b.time,b.rk_qty from prd_sell_analyze1  a"; 
	sql+=" inner join (select b.item_id,min(a.write_date)as write_date, count(a.rk_no) as time,sum(b.use_qty) as rk_qty ";
	sql+=" from jxc.sto_rk_mst a  inner join jxc.sto_rk_dtl b ";
	sql+=" on a.rk_no=b.rk_no ";
	sql+=" where a.rk_calss='P' group by b.item_id ";
	sql+=" ) b on a.item_id=b.item_id ";	
	sql+=" inner join prd_items c on a.item_id=c.item_id";

	sql+=" where   a.release_date=trunc(sysdate)"; 
	if(item_code.length()>0){
	sql+=" and c.item_code='"+item_code+"' ";
	}
	if(personid.length()>0){
	sql+=" and c.product_owner_id="+personid;
	}	
	//sql+=" order by   all_sell_quantity desc ";
        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	int i=0;
	while(rs.next()){	
		
	String item_name=rs.getString("name");
	String rs_item_code=rs.getString("code");
	
	int time=rs.getInt("time");//入库次数
	int rk_qty=rs.getInt("rk_qty");//总入库数量
	String write_date=rs.getString("write_date").substring(0,10);//初次采购入库时间
	int hy_rv_qty=rs.getInt("hy_rv_qty");//总退库数量
	int pro_rv_qty=rs.getInt("pro_rv_qty");//总退厂数量
	int balance_method=rs.getInt("balance_method");//结算方式
	String s_balance_method="";
		if(balance_method==1){
			s_balance_method="代销";
		}
		if(balance_method==2){
			s_balance_method="现结";
		}
		if(balance_method==3){
			s_balance_method="包销";
		}
		if(balance_method==4){
			s_balance_method="实销实结";
		}	
	double sell_last_five=rs.getDouble("sell_last_five");
	double sell_last_ten=rs.getDouble("sell_last_ten");
	double sell_last_fifteen=rs.getDouble("sell_last_fifteen");
			 
		jxlbean.addCell(new jxl.write.Label(0,i+3,rs_item_code,wcf));	 
		jxlbean.addCell(new jxl.write.Label(1,i+3,item_name,wcf));
		
		jxlbean.addCell(new jxl.write.Label(2,i+3,write_date,wcf));
		jxlbean.addCell(new jxl.write.Number(3,i+3,time,wcf));
		jxlbean.addCell(new jxl.write.Number(4,i+3,rk_qty,wcf));	
		jxlbean.addCell(new jxl.write.Number(5,i+3,hy_rv_qty,wcf));
		jxlbean.addCell(new jxl.write.Number(6,i+3,pro_rv_qty,wcf));	
		jxlbean.addCell(new jxl.write.Number(7,i+3,sell_last_five,wcf));	
		jxlbean.addCell(new jxl.write.Number(8,i+3,sell_last_ten,wcf));
		jxlbean.addCell(new jxl.write.Number(9,i+3,sell_last_fifteen,wcf));
		jxlbean.addCell(new jxl.write.Number(10,i+3,s_balance_method,wcf));

							


																																						
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