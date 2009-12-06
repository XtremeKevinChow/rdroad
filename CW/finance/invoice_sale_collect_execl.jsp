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
String cust_no=request.getParameter("cust_no");
	 cust_no=(cust_no==null)?"":cust_no.trim();	
String cust_name=request.getParameter("cust_name");
	 cust_name=(cust_name==null)?"":cust_name.trim();	 	
String s_type=request.getParameter("s_type");
	 s_type=(s_type==null)?"":s_type.trim();
String s_date=startDate+"至"+endDate;
String name="";
if(s_type.equals("1")){
 name="正常销售发票汇总";
}
if(s_type.equals("2")){
 name="正常销售退回发票汇总";
}
if(s_type.equals("3")){
 name="非正常销售发票汇总";
}
if(s_type.equals("2")&&cust_no.equals("98")){
 name="部分退货发票汇总";
}

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


		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
		jxlbean.setTitle(name,7);				
		header="销售类别,图书,影视,游戏&软件,音乐,礼品,其他,合计";
		jxlbean.setHeader(header,2,wcf1);
			     
		int jxl_line=3;
		
		jxlbean.addCell(new jxl.write.Label(0,1,"客户名称"));
		jxlbean.mergeCells(1,1,4,1);	
		jxlbean.addCell(new jxl.write.Label(1,1,cust_name));	
		jxlbean.addCell(new jxl.write.Label(5,1,"日期段"));
		jxlbean.mergeCells(6,1,7,1);
		jxlbean.addCell(new jxl.write.Label(6,1,s_date));	
%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		 conn = DBManager.getConnection();

			sql=" select d.id,nvl(sum(f.total_amt),0) as so_amt  ";
				
			sql+=" from s_item_type d left outer join ";
			sql+=" ( ";
			sql+=" select b.total_amt,c.item_type from fin_ar_mst a,fin_ar_dtl b,prd_items c";
			sql+=" where a.ar_id=b.ar_id and b.item_id=c.item_id ";
			if(startDate.length()>0&&endDate.length()>0){
			sql+=" and a.so_date>=date'"+startDate+"'";
			 sql+=" and a.so_date<date'"+endDate+"'+1 ";
			}
			sql+=" and a.so_type="+s_type+"  and a.status=3 ";

			if(cust_no.length()>0){
			sql+=" and a.custom_id='"+cust_no+"'  ";								
			}

			sql+=" ) f on d.id=f.item_type ";
			sql+=" group by d.id ";
			
			
			double all_so_amt=0;

			double other_amt=0;		
		
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();

		int i=0;
		
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,"销售收入",wcf1));	
		while(rs.next()){	
			double so_amt=rs.getDouble("so_amt");
			all_so_amt=all_so_amt+so_amt;

			if(i==5){
			other_amt=so_amt;
			}
			//销售收入小计＝销售收入+礼金折扣+配送费用
      
      
		jxlbean.addCell(new jxl.write.Label(i+1,jxl_line,myformat.format(so_amt),wcf));
		    i++;
		    }		
                jxlbean.addCell(new jxl.write.Label(7,jxl_line,myformat.format(all_so_amt),wcf)); 
		
		/*********************************************************************************/
		if(s_type.equals("4")){
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+1,"礼金差异",wcf1)); 	
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+2,"配送费差异",wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+3,"销售收入小计",wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+4,"已付金额差异"	,wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+5,"退回金额",wcf1)); 
		}else{
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+1,"礼金折扣",wcf1)); 	
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+2,"配送费用",wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+3,"包装费用",wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+4,"销售收入小计",wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+5,"会员帐户支付"	,wcf1)); 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line+6,"会员实际支付"	,wcf1)); 		
		}
		jxlbean.mergeCells(1,jxl_line+1,6,jxl_line+1);
		jxlbean.mergeCells(1,jxl_line+2,6,jxl_line+2);
		jxlbean.mergeCells(1,jxl_line+3,6,jxl_line+3);
		jxlbean.mergeCells(1,jxl_line+4,6,jxl_line+4);
		jxlbean.mergeCells(1,jxl_line+5,6,jxl_line+5);
		jxlbean.mergeCells(1,jxl_line+6,6,jxl_line+6); //最后一行
	  String sum_sql="select sum(so_amt) as so_amt,sum(deliver_amt) as deliver_amt ,sum(ar_amt) as ar_amt,sum(payed_amt) as payed_amt,sum(gift_amt) as gift_amt, sum(package_amt) as package_amt";
	  sum_sql+=" from fin_ar_mst a,cust_mst b where a.custom_id=b.cust_no and so_type="+s_type;
			if(startDate.length()>0&&endDate.length()>0){
			sum_sql+=" and so_date>=date'"+startDate+"'";
			 sum_sql+=" and so_date<date'"+endDate+"'+1 ";
			}	
			if(cust_no.length()>0){
			sum_sql+=" and a.custom_id='"+cust_no+"'  ";								
			}			  
			sum_sql+=" and a.status=3 ";

			pstmt=conn.prepareStatement(sum_sql);
			rs=pstmt.executeQuery();
			if(rs.next()){			
			double slxj=rs.getDouble("so_amt");	
			double deliver_amt=rs.getDouble("deliver_amt");
			double package_amt = rs.getDouble("package_amt");
			double gift_amt=rs.getDouble("gift_amt");			
			double ar_amt=rs.getDouble("ar_amt");			
			double payed_amt=rs.getDouble("payed_amt");
						
		jxlbean.addCell(new jxl.write.Label(7,jxl_line+1,myformat.format(gift_amt),wcf)); 	
		jxlbean.addCell(new jxl.write.Label(7,jxl_line+2,myformat.format(deliver_amt),wcf)); 
		jxlbean.addCell(new jxl.write.Label(7,jxl_line+3,myformat.format(package_amt),wcf)); 
		jxlbean.addCell(new jxl.write.Label(7,jxl_line+4,myformat.format(slxj+gift_amt+deliver_amt+package_amt),wcf)); 
		jxlbean.addCell(new jxl.write.Label(7,jxl_line+5,myformat.format(payed_amt),wcf)); 
		jxlbean.addCell(new jxl.write.Label(7,jxl_line+6,myformat.format(ar_amt),wcf)); 
		}
		for(int  j=1;j<7;j++){
		      for(int  k=1;k<6;k++)
		jxlbean.addCell(new jxl.write.Label(k,jxl_line+j,"",wcf)); 
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




		
    



