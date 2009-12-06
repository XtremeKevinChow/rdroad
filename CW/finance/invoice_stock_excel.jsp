<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%
DecimalFormat myformat = new DecimalFormat("###,###.00");

			String pid=request.getParameter("pid");
						 pid=(pid==null)?"0":pid.trim();
			String type=request.getParameter("type");
						 type=(type==null)?"":type.trim();	
						 
			String tag=request.getParameter("tag");
						 tag=(tag==null)?"":tag.trim();						 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="库存商品台账明细及汇总";
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
		jxlbean.setTitle(name,8);				
		header="产品类型,销售数量,销售金额,不含税销售金额,销售成本,销售毛利";
		//jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;   
		jxlbean.mergeCells(0,1,0,2);
		jxlbean.addCell(new jxl.write.Label(0,1,"产品类型",wcf));	
		jxlbean.mergeCells(1,1,2,1);
		//jxlbean.mergeCells(1,1,2,1);
		jxlbean.addCell(new jxl.write.Label(1,1,"期初数据",wcf));
		jxlbean.addCell(new jxl.write.Label(1,2,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(2,2,"金额",wcf));
		jxlbean.mergeCells(3,1,4,1);
		//jxlbean.mergeCells(3,1,4,1);	
		jxlbean.addCell(new jxl.write.Label(3,1,"本期入库",wcf));
		jxlbean.addCell(new jxl.write.Label(3,2,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(4,2,"金额",wcf));	
		jxlbean.mergeCells(5,1,6,1);
		//jxlbean.mergeCells(5,1,6,1);	
		jxlbean.addCell(new jxl.write.Label(5,1,"本期入库",wcf));
		jxlbean.addCell(new jxl.write.Label(5,2,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(6,2,"金额",wcf));	
		jxlbean.mergeCells(7,1,8,1);
		//jxlbean.mergeCells(7,1,8,1);	
		jxlbean.addCell(new jxl.write.Label(7,1,"本期入库",wcf));
		jxlbean.addCell(new jxl.write.Label(7,2,"数量",wcf));
		jxlbean.addCell(new jxl.write.Label(8,2,"金额",wcf));	
								   
		try{
		 conn = DBManager.getConnection();
		sql="select b.item_type,c.name,";
			sql+=" sum(a.first_amt) as first_amt,sum(a.first_qty) as first_qty,";
			sql+=" sum(a.in_qty) as in_qty,sum(a.out_qty) as out_qty,sum(a.last_qty) as last_qty,";
			sql+=" sum(a.in_amt) as in_amt,sum(a.out_amt) as out_amt,sum(a.last_amt) as last_amt,";
			sql+=" sum(a.out_dis_amt) as out_dis_amt ";			
			sql+=" from fin_stock_mst a ";
			sql+=" inner join prd_items b on ";
			sql+=" a.item_id=b.item_id";
			sql+=" inner join s_item_type c on ";
			sql+=" b.item_type=c.id ";			
			if(Integer.parseInt(pid)>0){
			     sql+=" and a.pid="+pid;
			}
			if(type.length()>0){
			     sql+=" and b.item_type="+type;
			}			
			sql+=" group by b.item_type,c.name order by b.item_type";
			//out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();  
			
			int all_first_qty=0;	
			double all_first_amt=0;	
			int all_in_qty=0;	
			double all_in_amt=0;			
			int all_out_qty=0;	
			double all_out_amt=0;				
			int all_last_qty=0;	
			double all_last_amt=0;
			double all_out_dis_amt=0;
			 int i=0;
			while(rs.next()){
			String s_item_type=rs.getString("item_type");
			String type_name=rs.getString("name");	
			int s_first_qty=rs.getInt("first_qty");	
			double s_first_amt=rs.getDouble("first_amt");	
			int s_in_qty=rs.getInt("in_qty");	
			double s_in_amt=rs.getDouble("in_amt");			
			int s_out_qty=rs.getInt("out_qty");	
			double s_out_amt=rs.getDouble("out_amt");				
			int s_last_qty=rs.getInt("last_qty");	
			double s_last_amt=rs.getDouble("last_amt");
			double out_dis_amt=rs.getDouble("out_dis_amt");	
			
			/******************* 合计 ********************/
			 all_first_qty=all_first_qty+s_first_qty;	
			 all_first_amt=all_first_amt+s_first_amt;	
			 all_in_qty=all_in_qty+s_in_qty;	
			 all_in_amt=all_in_amt+s_in_amt;			
			 all_out_qty=all_out_qty+s_out_qty;	
			 all_out_amt=all_out_amt+s_out_amt;				
			 all_last_qty=all_last_qty+s_last_qty;	
			 all_last_amt=all_last_amt+s_last_amt;	
			 all_out_dis_amt=all_out_dis_amt+out_dis_amt;	
			 
			 
		jxlbean.addCell(new jxl.write.Label(0,i+3,type_name,wcf));
		jxlbean.addCell(new jxl.write.Number(1,i+3,s_first_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(2,i+3,myformat.format(s_first_amt),wcf));	
		jxlbean.addCell(new jxl.write.Number(3,i+3,s_in_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(4,i+3,myformat.format(s_in_amt),wcf));	
		jxlbean.addCell(new jxl.write.Number(5,i+3,s_out_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(6,i+3,myformat.format(s_out_amt),wcf));	
		jxlbean.addCell(new jxl.write.Number(7,i+3,s_last_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(8,i+3,myformat.format(s_last_amt),wcf));								


																																						
		i++;
		}
		
		jxlbean.addCell(new jxl.write.Label(0,i+3,"合计",wcf1));
		jxlbean.addCell(new jxl.write.Number(1,i+3,all_first_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(2,i+3,myformat.format(all_first_amt),wcf));	
		jxlbean.addCell(new jxl.write.Number(3,i+3,all_in_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(4,i+3,myformat.format(all_in_amt),wcf));	
		jxlbean.addCell(new jxl.write.Number(5,i+3,all_out_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(6,i+3,myformat.format(all_out_amt),wcf));	
		jxlbean.addCell(new jxl.write.Number(7,i+3,all_last_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(8,i+3,myformat.format(all_last_amt),wcf));

						
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