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

	String res_no=request.getParameter("res_no");
		 res_no=(res_no==null)?"":res_no.trim();					 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="采购入库产品暂估成本汇总";
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
		



		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,2);				
		header="产品类型,数量,预算成本";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;   
		jxlbean.addCell(new jxl.write.Label(0,1,"日期",wcf));
		jxlbean.addCell(new jxl.write.Label(1,1,startDate,wcf));
		jxlbean.addCell(new jxl.write.Label(2,1,endDate,wcf));
								   
		try{
		 conn = DBManager.getConnection();
	  int total_pur_qty=0;
	  double total_pur_amt=0;		 
	sql="SELECT ";
sql+="e.id,sum(A.OP_QTY) as pur_qty,sum(A.PRE_AMT) as pur_amt ";
sql+="FROM magic.fin_stock_detail A ";
sql+="INNER JOIN   prd_items d on a.item_id=d.item_id ";
sql+="inner join s_item_type e on d.item_type=e.id ";
sql+="WHERE A.IS_TEMP='Y' and a.doc_type='01' ";
sql+="AND OPERATION_DATE >= date '" + startDate + "' ";
sql+=" AND OPERATION_DATE < date '" + endDate + "' + 1 ";
sql+="AND  ";
sql+="to_number(A.res_no) in ";
sql+="( ";
sql+="select ps_dtl_Id from magic.fin_ps_dtl a ";
sql+="inner join magic.fin_ps_mst b on a.ps_id=b.ps_id  ";
sql+="where 1=1 ";
sql+="and b.purchasedate >= date '" + startDate + "' ";
sql+="AND B.PURCHASEDATE < DATE '" + endDate + "'+1 ";
sql+=") ";
if(res_no.length()>0){
	sql+=" and b.res_no='"+res_no+"' ";
}	
sql+="group by e.id ";

	//System.out.println(sql);
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
			 int i=0;
			 String type_name="";		
		while(rs.next()){	
	
		String item_type=rs.getString("id");
		int pur_qty=rs.getInt("pur_qty");			
	        double pur_amt=rs.getDouble("pur_amt");//预算变成本	      		   
	        total_pur_qty=total_pur_qty+pur_qty;			
	        total_pur_amt=total_pur_amt+pur_amt;
	        
		if(item_type.equals("1")){
		   type_name="图书";
		}
		if(item_type.equals("2")){
		   type_name="影视";
		}
		if(item_type.equals("3")){
		   type_name="音乐";
		}
		if(item_type.equals("4")){
		   type_name="游戏软件";
		}
		if(item_type.equals("5")){
		   type_name="礼品";
		}
		if(item_type.equals("6")){
		   type_name="其他";
		}      		
      			
			
			 
			 
		jxlbean.addCell(new jxl.write.Label(0,i+3,type_name,wcf));
		jxlbean.addCell(new jxl.write.Number(1,i+3,pur_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(2,i+3,myformat.format(pur_amt),wcf));	
							


																																						
		i++;
		}
		
		jxlbean.addCell(new jxl.write.Label(0,i+3,"合计",wcf1));
		jxlbean.addCell(new jxl.write.Number(1,i+3,total_pur_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(2,i+3,myformat.format(total_pur_amt),wcf));	


						
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