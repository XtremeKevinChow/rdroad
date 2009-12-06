<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%
DecimalFormat myformat = new DecimalFormat("###,###");

      String pro_name=request.getParameter("pro_name");
      pro_name=(pro_name==null)?"":pro_name;
      String release_date=request.getParameter("release_date");
      release_date=(release_date==null)?"":release_date;
      String pro_code=request.getParameter("pro_code");
      pro_code=(pro_code==null)?"":pro_code;    
      String type=request.getParameter("type");
      type=(type==null)?"":type;           					 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String name="产品采购销售分析表";   
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

		String header="";
		jxlbean.setTitle(name,13);				
		header="货号,产品名称,可用数量,冻结数量,最晚采购入库日期,采购数量,最后一次采购数量,前台总销售数量,后台总销售数量,总退厂数量,标准价,成本,库龄";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;  
		int i=0; 
								   
		try{
		 conn = DBManager.getConnection();
		sql=" WITH ";
		sql+=" TAB1 AS ";
		sql+=" ( ";
		sql+=" SELECT A.ITEM_ID,b.ITEM_CODE,b.NAME AS ITEM_NAME,a.USE_QTY,a.FROZEN_QTY,b.standard_price,b.unpurchasing_cost ";
		sql+=" FROM  ";
		sql+=" jxc.sto_stock  a ";
		sql+=" INNER JOIN prd_items b ON a.ITEM_ID=b.ITEM_ID ";
		sql+=" WHERE a.sto_no='000'  AND a.use_qty>0 ";
		sql+=" ), ";
		sql+=" TAB2 AS ";
		sql+=" ( ";
		sql+="   SELECT TAB_PUR.item_id,TAB_PUR.pur_date,TAB_PUR.pur_qty,JXC.STO_RK_DTL.USE_QTY AS FIRST_QTY FROM  ";
		sql+="   ( ";
		sql+="   SELECT C.ITEM_ID,max(c.rk_no) AS pur_no,max(WRITE_DATE) AS PUR_DATE,SUM(c.USE_QTY) AS pur_qty ";
		sql+="   FROM jxc.sto_rk_dtl c INNER JOIN jxc.sto_rk_mst  d ON c.RK_NO=d.RK_NO ";
		sql+="   WHERE d.RK_CALSS='P' ";
		sql+="   GROUP BY C.ITEM_ID ";
		sql+="   ) TAB_PUR  ";
		sql+="   INNER JOIN JXC.STO_RK_DTL ON TAB_PUR.ITEM_ID=JXC.STO_RK_DTL.ITEM_ID AND  ";
		sql+="                TAB_PUR.PUR_NO=JXC.STO_RK_DTL.RK_NO ";
		sql+="  ) ";
		sql+="  SELECT TAB1.ITEM_CODE,TAB1.ITEM_NAME,TAB1.USE_QTY,TAB1.FROZEN_QTY,TAB2.*,TAB3.total_quantity,tab3.total_quantity_b, tab3.pro_rv_qty,TAB1.standard_price,TAB1.unpurchasing_cost,(sysdate-TAB2.pur_date) as stock_age  ";         
		sql+="  FROM  ";
		sql+="  TAB1  ";
		sql+="  INNER JOIN TAB2 ON TAB1.ITEM_ID=TAB2.ITEM_ID ";
		sql+="  LEFT OUTER JOIN PRD_SELL_ANALYZE1 TAB3  ";
		sql+="       ON TAB3. RELEASE_DATE=TRUNC(SYSDATE-1) AND TAB3.ITEM_ID=TAB1.ITEM_ID order by stock_age desc ";
        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	while(rs.next()){	
		
	String item_name=rs.getString("item_name");
	String item_code=rs.getString("item_code");
	int use_qty=rs.getInt("use_qty");//可用数量
	int frozen_qty=rs.getInt("frozen_qty");
	
	String pur_date=rs.getString("pur_date");	
	int pur_qty=rs.getInt("pur_qty");//总采购数量
	int first_qty=rs.getInt("first_qty");//最后一次采购数量
	
	int total_quantity=rs.getInt("total_quantity");//前台总销售数量
	int total_quantity_b=rs.getInt("total_quantity_b");//后台总销售数量
	String pro_rv_qty=rs.getString("pro_rv_qty");
        double standard_price=rs.getDouble("standard_price");
        double unpurchasing_cost=rs.getDouble("unpurchasing_cost");
        int stock_age =rs.getInt("stock_age");
	
		jxlbean.addCell(new jxl.write.Label(0,i+3,item_code,wcf));	 
		jxlbean.addCell(new jxl.write.Label(1,i+3,item_name,wcf));	 
		
		
		jxlbean.addCell(new jxl.write.Number(2,i+3,use_qty,wcf));
		jxlbean.addCell(new jxl.write.Number(3,i+3,frozen_qty,wcf));		
		jxlbean.addCell(new jxl.write.Label(4,i+3,pur_date,wcf));
		jxlbean.addCell(new jxl.write.Number(5,i+3,pur_qty,wcf));
		jxlbean.addCell(new jxl.write.Number(6,i+3,first_qty,wcf));
		jxlbean.addCell(new jxl.write.Number(7,i+3,total_quantity,wcf));
		jxlbean.addCell(new jxl.write.Number(8,i+3,total_quantity_b,wcf));	
		jxlbean.addCell(new jxl.write.Label(9,i+3,pro_rv_qty,wcf));
		jxlbean.addCell(new jxl.write.Number(10,i+3,standard_price,wcf));	
		jxlbean.addCell(new jxl.write.Number(11,i+3,unpurchasing_cost,wcf));	
		jxlbean.addCell(new jxl.write.Number(12,i+3,stock_age,wcf));


																																						
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
 
