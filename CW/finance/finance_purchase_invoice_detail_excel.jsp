<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean,jxl.write.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%

			String apID=request.getParameter("apID");
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";

		try{
		 conn = DBManager.getConnection();
            sql = "select a.*, b.pro_name from fin_ap_mst a "
                    + "inner join providers b on a.pro_no = b.pro_no "
                    + "where ap_id = "+apID ;
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			String FACT_AP_CODE="";
			String AP_TYPE="";
			String AP_TYPE_NAME="";
			String CREATEDATE="";
			String CREATOR="";
			String CHECKDATE="";
			String CHECKPERSON="";
			String TALLY_DATE="";
			String TALLIER="";
			String AMT="";
			String INVOICE_DATE="";
			String STATUS="";
			String STATUS_NAME="";
			String PRO_NO="";
			String PRO_NAME="";
			if(rs.next()){
					FACT_AP_CODE=rs.getString("FACT_AP_CODE");
			    AP_TYPE=rs.getString("AP_TYPE");
			    if(AP_TYPE.equals("1")){
			       AP_TYPE_NAME="蓝票";
			    }else{
			       AP_TYPE_NAME="红票";
			    }
			CREATEDATE=rs.getString("CREATEDATE").substring(0,10);
			CREATOR=rs.getString("CREATOR");
			CHECKDATE=rs.getString("CHECKDATE");
			CHECKDATE=(CHECKDATE==null)?"无":CHECKDATE;
			CHECKPERSON=rs.getString("CHECKPERSON");
			CHECKPERSON=(CHECKPERSON==null)?"无":CHECKPERSON;
			TALLY_DATE=rs.getString("TALLY_DATE");
			TALLY_DATE=(TALLY_DATE==null)?"无":TALLY_DATE;
			TALLIER=rs.getString("TALLIER");
			TALLIER=(TALLIER==null)?"无":TALLIER;
			AMT=rs.getString("AMT");
			INVOICE_DATE=rs.getString("INVOICE_DATE");
			STATUS=rs.getString("STATUS");
			    if(STATUS.equals("1")){
			       STATUS_NAME="新建";
			    }else if(STATUS.equals("2")){
			       STATUS_NAME="审核";
			    }else{
			       STATUS_NAME="记帐";
			    }		
			PRO_NO=rs.getString("PRO_NO");		
			PRO_NAME=rs.getString("PRO_NAME");		
			}
							
                    
String name="采购发票详细";

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
		jxlbean.setColWidth(0,10);  
		jxlbean.setColWidth(1,10);
		jxlbean.setColWidth(2,10);
		jxlbean.setColWidth(3,10);
		jxlbean.setColWidth(4,10);
		jxlbean.setColWidth(5,10);
		jxlbean.setColWidth(6,10);
		jxlbean.setColWidth(7,10);
		jxlbean.setColWidth(8,10);
		jxlbean.setColWidth(9,10);
		jxlbean.setColWidth(10,10);
		jxlbean.mergeCells(1,1,5,1);//发票号
		jxlbean.mergeCells(1,2,5,2);//供应商代码
		jxlbean.mergeCells(1,3,5,3);//
		jxlbean.mergeCells(1,4,5,4);//
		jxlbean.mergeCells(1,5,5,5);//
		jxlbean.mergeCells(1,6,5,6);//金额
		jxlbean.mergeCells(1,7,12,7);//状态
	


		jxlbean.mergeCells(7,1,12,1);//第几列，第几行，跨到第几列，跨到第几行，
		jxlbean.mergeCells(7,2,12,2);
		jxlbean.mergeCells(7,3,12,3);
		jxlbean.mergeCells(7,4,12,4);
		jxlbean.mergeCells(7,5,12,5);
		jxlbean.mergeCells(7,6,12,6);





		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		  cf2.setBorder(Border.ALL,jxl.format.BorderLineStyle.THIN);
	    cf2.setAlignment(Alignment.CENTRE);       
	    		
		jxlbean.setTitle("采购发票详细",12);	

		String header="发票号,"+FACT_AP_CODE+",1,1,1,1,发票类型,"+AP_TYPE_NAME;
		String header1="供应商代码,"+PRO_NO+",1,1,1,1,供应商名称,"+PRO_NAME;
		String header2="建立日期,"+CREATEDATE+",1,1,1,1,建立人,"+CREATOR;
		String header3="审核日期,"+CHECKDATE+",1,1,1,1,审核人,"+CHECKPERSON;
		String header4="记帐日期,"+TALLY_DATE+",1,1,1,1,记帐人,"+TALLIER;
		String header5="金额,"+AMT+",1,1,1,1,发票日期,"+INVOICE_DATE.substring(0,10);
		String header6="状态,"+STATUS_NAME+",,";
			jxlbean.mergeCells(1,8,3,8);
		
		
		String header9="产品代码,产品名称,'','',本次数量,预算单价,预算成本,记帐单价,记帐成本,税率,税额,价税合计,成本差异,";

		
		jxlbean.setHeader(header,1,wcf);
		jxlbean.setHeader(header1,2,wcf);
		jxlbean.setHeader(header2,3,wcf);
		jxlbean.setHeader(header3,4,wcf);
		jxlbean.setHeader(header4,5,wcf);
		jxlbean.setHeader(header5,6,wcf);
		jxlbean.setHeader(header6,7,wcf);
		jxlbean.setHeader(header9,8,wcf);		
		
		
			     

		sql = "select distinct b.item_type "
		      + "from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id "
		      + "where a.ap_id ="+apID;		 
		pstmt = conn.prepareStatement(sql);
		ResultSet rs1 = pstmt.executeQuery();


		int jxl_line=9;
    double final_purPrice_all=0;
  	double final_apPrice_all=0;
    double final_taxAmtAll_all=0;			
		double final_amt_all=0;		
		double final_disAmt_all=0;	
		while(rs1.next()){ 
		String item_type=rs1.getString("item_type"); 
		String item_type_name=""; 
	    if(item_type.equals("1")){
	       item_type_name="图书";
	    }
	    if(item_type.equals("2")){
	       item_type_name="影视";
	    }
	    if(item_type.equals("3")){
	       item_type_name="音乐";
	    }
	    if(item_type.equals("4")){
	       item_type_name="游戏/软件";
	    }
	    if(item_type.equals("5")){
	       item_type_name="礼品";
	    }
	    if(item_type.equals("6")){
	       item_type_name="其他";
	    }		 
	  jxlbean.mergeCells(1,jxl_line,12,jxl_line);//图书
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,item_type_name,wcf));
		
		
			sql = "select a.*, b.item_code, b.name as item_name, (a. qty + c.use_qty) as use_qty "
			          + "from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id "
			          + "inner join fin_ps_dtl c on a.ps_dtl_id = c.ps_dtl_id "
			          + "where a.ap_id ="+apID+" and b.item_type="+item_type;		
			          
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			double purPrice_all=0;
			double apPrice_all=0;
			double taxAmtAll_all=0;
			double amt_all=0;
			double disAmt_all=0;	
		  int jxl_line_next=jxl_line+1;
							          
			while(rs.next()){ //while detail 
					String item_code=rs.getString("item_code");
					String item_name=rs.getString("item_name");
					int qty=rs.getInt("qty");
					double purPrice=rs.getDouble("pur_Price");
					double apPrice=rs.getDouble("ap_Price");
					double amt=rs.getDouble("amt");
					double tax=rs.getDouble("tax");					
					double totalAmt=rs.getDouble("total_Amt");
					double disAmt=rs.getDouble("dis_Amt");
					//double taxAmt=Arith.round(tax*amt,2);
					double taxAmt = Arith.round(totalAmt-amt,2);
					
	        purPrice_all=purPrice_all+purPrice*qty;
	        apPrice_all=apPrice_all+amt;
	        taxAmtAll_all=taxAmtAll_all+taxAmt;		
	
					amt_all=amt_all+totalAmt;//价税合计
					disAmt_all=disAmt_all+disAmt;	//成本差异	
		jxlbean.mergeCells(1,jxl_line_next,3,jxl_line_next);
		jxlbean.addCell(new jxl.write.Label(0,jxl_line_next,item_code,wcf1));		
		jxlbean.addCell(new jxl.write.Label(1,jxl_line_next,item_name,wcf1));	
		jxlbean.addCell(new jxl.write.Label(2,jxl_line_next,item_name,wcf1));
		jxlbean.addCell(new jxl.write.Label(3,jxl_line_next,item_name,wcf1));
		jxlbean.addCell(new jxl.write.Number(4,jxl_line_next,qty,wcf1));	
		jxlbean.addCell(new jxl.write.Number(5,jxl_line_next,purPrice,cf2));	
		jxlbean.addCell(new jxl.write.Number(6,jxl_line_next,purPrice*qty,cf2));	
		jxlbean.addCell(new jxl.write.Number(7,jxl_line_next,apPrice,cf2));	
		jxlbean.addCell(new jxl.write.Number(8,jxl_line_next,amt,cf2));	
		jxlbean.addCell(new jxl.write.Number(9,jxl_line_next,tax,cf2));	
		jxlbean.addCell(new jxl.write.Number(10,jxl_line_next,taxAmt,cf2));	
		jxlbean.addCell(new jxl.write.Number(11,jxl_line_next,totalAmt,cf2));	
		jxlbean.addCell(new jxl.write.Number(12,jxl_line_next,-disAmt,cf2));		
		jxl_line_next++;		
		  } //while detail 
		  jxlbean.mergeCells(1,jxl_line_next,3,jxl_line_next);
		
		jxlbean.addCell(new jxl.write.Label(0,jxl_line_next,"小计",wcf1));		
		jxlbean.addCell(new jxl.write.Label(1,jxl_line_next,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(2,jxl_line_next,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(3,jxl_line_next,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(4,jxl_line_next,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(5,jxl_line_next,"",wcf1));			
		jxlbean.addCell(new jxl.write.Number(6,jxl_line_next,purPrice_all,cf2));	
		jxlbean.addCell(new jxl.write.Label(7,jxl_line_next,"",wcf1));	
		jxlbean.addCell(new jxl.write.Number(8,jxl_line_next,apPrice_all,cf2));	
		jxlbean.addCell(new jxl.write.Label(9,jxl_line_next,"",wcf1));	
		jxlbean.addCell(new jxl.write.Number(10,jxl_line_next,taxAmtAll_all,cf2));	
		jxlbean.addCell(new jxl.write.Number(11,jxl_line_next,amt_all,cf2));	
		jxlbean.addCell(new jxl.write.Number(12,jxl_line_next,-disAmt_all,cf2));	

    final_purPrice_all=final_purPrice_all+purPrice_all;	
    final_apPrice_all	=final_apPrice_all+apPrice_all;	
    final_taxAmtAll_all	=final_taxAmtAll_all+taxAmtAll_all; 		
		final_amt_all	=	final_amt_all+amt_all;
		final_disAmt_all=final_disAmt_all+disAmt_all;
		
		jxl_line=jxl_line_next+1;
		jxl_line++;
		}			
		jxlbean.mergeCells(1,jxl_line-1,3,jxl_line-1);	 
		jxlbean.addCell(new jxl.write.Label(0,jxl_line-1,"合计",wcf1));		
		jxlbean.addCell(new jxl.write.Label(1,jxl_line-1,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(2,jxl_line-1,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(3,jxl_line-1,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(4,jxl_line-1,"",wcf1));	
		jxlbean.addCell(new jxl.write.Label(5,jxl_line-1,"",wcf1));			
		jxlbean.addCell(new jxl.write.Number(6,jxl_line-1,final_purPrice_all,cf2));	
		jxlbean.addCell(new jxl.write.Label(7,jxl_line-1,"",wcf1));	
		jxlbean.addCell(new jxl.write.Number(8,jxl_line-1,final_apPrice_all,cf2));	
		jxlbean.addCell(new jxl.write.Label(9,jxl_line-1,"",wcf1));	
		jxlbean.addCell(new jxl.write.Number(10,jxl_line-1,final_taxAmtAll_all,cf2));	
		jxlbean.addCell(new jxl.write.Number(11,jxl_line-1,final_amt_all,cf2));	
		jxlbean.addCell(new jxl.write.Number(12,jxl_line-1,-final_disAmt_all,cf2));			 

		
	
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




		
    



