<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%

			String startDate=request.getParameter("startDate");
						 startDate=(startDate==null)?"":startDate.trim();
			String endDate=request.getParameter("endDate");
						 endDate=(endDate==null)?"":endDate.trim();
			String cust_no=request.getParameter("cust_no");
						 cust_no=(cust_no==null)?"":cust_no.trim();	
			String tag=request.getParameter("tag");
						 tag=(tag==null)?"":tag.trim();						 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";



try{
		conn = DBManager.getConnection();
		 sql="select cust_name from cust_mst where cust_no='"+cust_no+"'";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			String 	cust_name="";
			if(rs.next()){
			cust_name=rs.getString(1);	 
			}
String name="销售普通发票汇总";
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
			WritableCellFormat wcf=new WritableCellFormat(red);
			wcf.setAlignment(Alignment.LEFT);
			wcf.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);			
			jxl.write.WritableFont style = new jxl.write.WritableFont(jxl.write.WritableFont.ARIAL,
			                                       9,
			                                       jxl.write.WritableFont.NO_BOLD,
			                                       false,
			                                       jxl.format.UnderlineStyle.NO_UNDERLINE,
			                                       jxl.format.Colour.BLACK);       
			WritableCellFormat wcf1=new WritableCellFormat(style);
			wcf1.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcf1.setAlignment(Alignment.CENTRE);       
						
			jxlbean.create_sheet("采购发票详细",0);		
			
			jxlbean.setRowHeigth(0,500);
			jxlbean.setColWidth(0,15);  
			jxlbean.setColWidth(1,10);
			jxlbean.setColWidth(2,10);
			jxlbean.setColWidth(3,10);
			jxlbean.setColWidth(4,10);
			jxlbean.setColWidth(5,10);
			jxlbean.setColWidth(6,10);
			jxlbean.setColWidth(7,10);
			jxlbean.setColWidth(8,10);		
		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		  cf2.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);
	    cf2.setAlignment(Alignment.CENTRE);       
	    		
		jxlbean.setTitle("采购发票详细",7);	

		String header="销售类别,图书,影视,游戏软件,音乐,礼品,其他,合计";				
		jxlbean.setHeader(header,2,wcf);	
		String header1="销售收入,,,,,,,";				
		jxlbean.setHeader(header1,3,wcf);		
		String header2="礼金折扣,,,,,,,";				
		jxlbean.setHeader(header2,4,wcf);		
		String header3="配送费用,,,,,,,";				
		jxlbean.setHeader(header3,5,wcf);
		String header4="配送费用,,,,,,,";				
		jxlbean.setHeader(header4,6,wcf);	
		String header5="销售收入小计,,,,,,,";				
		jxlbean.setHeader(header5,7,wcf);		
		String header6="会员帐户支付,,,,,,,";				
		jxlbean.setHeader(header6,8,wcf);		
		String header7="会员实际支付,,,,,,,";				
		jxlbean.setHeader(header7,9,wcf);		
													
		sql=" select d.id,nvl(sum(f.so_amt),0) as so_amt,nvl(sum(f.deliver_amt),0) as deliver_amt, ";
		sql+=" nvl(sum(f.gift_amt),0) as gift_amt ,";
		sql+=" nvl(sum(f.payed_amt),0) as payed_amt ,";
		sql+=" nvl(sum(f.ar_amt),0) as ar_amt, ";
		sql+=" nvl(sum(f.package_amt),0) as package_amt ";
		sql+=" from s_item_type d left outer join ";
		sql+=" ( ";
		sql+=" select a.so_amt,a.deliver_amt,a.ar_amt,a.payed_amt,a.gift_amt,c.item_type, a.package_amt from fin_ar_mst a,fin_ar_dtl b,prd_items c,cust_mst e ";
		sql+=" where a.ar_id=b.ar_id and b.item_id=c.item_id  ";
		sql+=" and a.so_date>=date'"+startDate+"' and a.so_date<date'"+endDate+"'+1 ";
		sql+=" and e.cust_no=a.custom_id  ";
		sql+=" and e.cust_no='"+cust_no+"'  ";
		sql+=" ) f on d.id=f.item_type ";
		sql+=" group by d.id ";

     //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
						          						
			double all_so_amt=0;
			double deliver_amt=0;
			double package_amt=0;
			double gift_amt=0;
			double ar_amt=0;
			double payed_amt=0;
      double other_amt=0;      
		  int i=1;
		
			while(rs.next()){	
      double so_amt=Arith.round(rs.getDouble("so_amt"),2);
      all_so_amt=Arith.round(all_so_amt+so_amt,2);
      deliver_amt=Arith.round(deliver_amt+rs.getDouble("deliver_amt"),2);
	  package_amt=Arith.round(package_amt+rs.getDouble("package_amt"),2);
      gift_amt=Arith.round(gift_amt+rs.getDouble("gift_amt"),2);
      ar_amt=Arith.round(ar_amt+rs.getDouble("ar_amt"),2);
      payed_amt=Arith.round(payed_amt+rs.getDouble("payed_amt"),2);
      if(i==5){
      other_amt=so_amt;
      }
      //销售收入小计＝销售收入-礼金折扣+配送费用+包装费用
      
         


		jxlbean.addCell(new jxl.write.Number(i,3,so_amt,wcf1));			
    i++;}//while
    jxlbean.addCell(new jxl.write.Number(7,3,all_so_amt,wcf1));	
    
    for(int j=4;j<9;j++){
    jxlbean.mergeCells(1,j,6,j);//第几列，第几行，跨到第几列，跨到第几行，
    jxlbean.addCell(new jxl.write.Label(1,j,"",wcf1));	
    jxlbean.addCell(new jxl.write.Label(2,j,"",wcf1));	
    jxlbean.addCell(new jxl.write.Label(3,j,"",wcf1));	
    jxlbean.addCell(new jxl.write.Label(4,j,"",wcf1));	
    jxlbean.addCell(new jxl.write.Label(5,j,"",wcf1));	
    jxlbean.addCell(new jxl.write.Label(6,j,"",wcf1));	
    }
    jxlbean.addCell(new jxl.write.Number(7,4,gift_amt,wcf1));	
    jxlbean.addCell(new jxl.write.Number(7,5,deliver_amt,wcf1));
	 jxlbean.addCell(new jxl.write.Number(7,6,package_amt,wcf1));
    jxlbean.addCell(new jxl.write.Number(7,7,Arith.round((all_so_amt-gift_amt+deliver_amt+package_amt),2),wcf1));	
    jxlbean.addCell(new jxl.write.Number(7,8,payed_amt,wcf1));	
    jxlbean.addCell(new jxl.write.Number(7,9,ar_amt,wcf1));	
    
    jxlbean.addCell(new jxl.write.Label(0,1,"客户:",wcf));	
    jxlbean.mergeCells(1,1,4,1);
    jxlbean.addCell(new jxl.write.Label(1,1,cust_name,wcf));	
    jxlbean.addCell(new jxl.write.Label(5,1,"日期段:",wcf));	
    jxlbean.addCell(new jxl.write.Label(6,1,startDate,wcf));	
    jxlbean.addCell(new jxl.write.Label(7,1,endDate,wcf));	
    
    //*****************************************************
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

