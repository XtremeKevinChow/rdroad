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
      String name="目录促销产品加印分析报表";
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




		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,21);				
		header="货号,产品名称,供应商代码,供应商名称,定价,采购成本,库存数量,在途数量,最后到货日期,最后到货数量,累计销量,退厂出库数量,90天内销量,30天内销量,15天内销量,7天内销量,3天内销量,预计库存销售天数,建议补货量";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;  
		int i=0; 
								   
		try{
		 conn = DBManager.getConnection();
   
        sql=" select a.item_id,a.name,a.item_code as code,a.standard_price,a.purchasing_cost,b.available_quantity,";
        sql+=" b.afloat_quantity,d.pro_name,d.pro_no,e.qty3,e.qty7,e.qty15,e.qty30,e.qty90,f.use_qty,f.write_date,b.total_quantity,b.pro_rv_qty  from prd_items a ";
        sql+=" inner join prd_sell_analyze1 b on a.item_id=b.item_id and b.release_date=trunc(sysdate)";
	//sql+=" inner join prd_sell_analyze1 b on a.item_id=b.item_id and b.release_date=date'2007-06-13'";
	sql+=" inner join providers d on a.supplier_id=d.id";
	sql+=" inner join (";
	sql+=" SELECT JXC.STO_RK_DTL.ITEM_ID,JXC.STO_RK_DTL.USE_QTY,JXC.STO_RK_MST.WRITE_DATE";
	sql+=" FROM JXC.STO_RK_DTL";
	sql+=" INNER JOIN JXC.STO_RK_MST ON JXC.STO_RK_MST.RK_NO=JXC.STO_RK_DTL.RK_NO";
	sql+=" INNER JOIN ";
	sql+=" (";
	sql+=" SELECT ITEM_ID,MAX(B.RK_NO)   AS RK_NO FROM ";
	sql+=" JXC.STO_RK_DTL A";
	sql+=" INNER JOIN JXC.STO_RK_MST B ON A.RK_NO=B.RK_NO "; 
	sql+=" WHERE B.RK_CALSS='P' ";
	sql+=" GROUP BY A.ITEM_ID";
	sql+=" ) AA ON JXC.STO_RK_DTL.RK_NO=AA.RK_NO AND AA.ITEM_ID=JXC.STO_RK_DTL.ITEM_ID  ";

	sql+=" ) f on f.item_id=a.item_id ";
	sql+=" inner join (select ord_lines.item_id,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=3 then nvl(ord_lines.quantity,0) else 0 end) as qty3,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=7 then nvl(ord_lines.quantity,0) else 0 end) as qty7,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=15 then nvl(ord_lines.quantity,0) else 0 end) as qty15,   ";             
        sql+=" sum(case when sysdate-ord_headers.release_date<=30 then nvl(ord_lines.quantity,0) else 0 end) as qty30,";
        sql+=" sum(case when sysdate-ord_headers.release_date<=90 then nvl(ord_lines.quantity,0) else 0 end) as qty90 ";
        sql+=" from ord_headers,ord_lines ";
        sql+=" where ord_headers.id=ord_lines.order_id ";
        sql+=" and ord_headers.release_date>=sysdate-90 ";
        sql+=" and ord_headers.release_date<=sysdate ";
      	sql+=" group by ord_lines.item_id ";
     	sql+="  ) e on a.item_id=e.item_id ";
	sql+=" where a.is_web=1 ";
	if(type.length()>0){
	sql+="  and a.item_type ="+type;
	}	
	if(pro_code.length()>0){
	sql+="  and d.pro_no like '%"+pro_code+"%'"; 
	}

	sql+="  order by e.qty7 desc "; 
        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	while(rs.next()){	
		
	String rs_name=rs.getString("name");
	String item_code=rs.getString("code");
	int item_id=rs.getInt("item_id");
	String pro_no=rs.getString("pro_no");
	String rs_pro_name=rs.getString("pro_name");	
	String standard_price=rs.getString("standard_price");//定价
	String purchasing_cost=rs.getString("purchasing_cost");//采购成本
	int qty=rs.getInt("available_quantity");//库存数量
	int afloat_quantity=rs.getInt("afloat_quantity");//在途数量
	int qty3=rs.getInt("qty3");
	int qty7=rs.getInt("qty7");
	int qty15=rs.getInt("qty15");
	int qty30=rs.getInt("qty30");
	int qty90=rs.getInt("qty90");
	String qty_sell="0";//预计库存销售天数=库存/7天平均	
        if(qty7>0){
	qty_sell=myformat.format(qty/qty7);
	}	
	int in_qty=qty7*30;//建议补货量=7天平均*30
	int use_qty=rs.getInt("use_qty");
	String write_date=rs.getString("write_date");
	String total_quantity=rs.getString("total_quantity");	
	String pro_rv_qty=rs.getString("pro_rv_qty");
	
		jxlbean.addCell(new jxl.write.Label(0,i+3,rs_name,wcf));	 
		jxlbean.addCell(new jxl.write.Label(1,i+3,item_code,wcf));	 
		
		
		jxlbean.addCell(new jxl.write.Label(2,i+3,pro_no,wcf));
		jxlbean.addCell(new jxl.write.Label(3,i+3,rs_pro_name,wcf));		
		jxlbean.addCell(new jxl.write.Label(4,i+3,standard_price,wcf));
		jxlbean.addCell(new jxl.write.Label(5,i+3,purchasing_cost,wcf));
		jxlbean.addCell(new jxl.write.Number(6,i+3,qty,wcf));
		jxlbean.addCell(new jxl.write.Number(7,i+3,afloat_quantity,wcf));
		jxlbean.addCell(new jxl.write.Label(8,i+3,write_date,wcf));
		jxlbean.addCell(new jxl.write.Number(9,i+3,use_qty,wcf));
		jxlbean.addCell(new jxl.write.Label(10,i+3,total_quantity,wcf));	
		jxlbean.addCell(new jxl.write.Label(11,i+3,pro_rv_qty,wcf));	
		jxlbean.addCell(new jxl.write.Number(12,i+3,qty90,wcf));	
		jxlbean.addCell(new jxl.write.Number(13,i+3,qty30,wcf));
		jxlbean.addCell(new jxl.write.Number(14,i+3,qty15,wcf));
		jxlbean.addCell(new jxl.write.Number(15,i+3,qty7,wcf));		
		jxlbean.addCell(new jxl.write.Number(16,i+3,qty3,wcf));	
		jxlbean.addCell(new jxl.write.Label(17,i+3,qty_sell,wcf));		
		jxlbean.addCell(new jxl.write.Number(18,i+3,in_qty,wcf));	



																																						
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