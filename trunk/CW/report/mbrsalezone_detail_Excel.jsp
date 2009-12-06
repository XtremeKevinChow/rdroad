<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.magic.utils.JxlBean"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String begin_date = request.getParameter("begin_date");
			 begin_date=(begin_date==null)?"":begin_date;	
String end_date = request.getParameter("end_date");
			 end_date=(end_date==null)?"":end_date;	
String sum_order_sum= request.getParameter("sum_order_sum");
String sum_order_count = request.getParameter("sum_order_count");
String province_detail = request.getParameter("province_detail");
String name=province_detail+"销售地域分布";

		 response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   

		JxlBean jxlbean=new JxlBean();
        jxlbean.create(response.getOutputStream());
		jxlbean.create_sheet(province_detail+"销售地域分布",0);			    
		jxlbean.setRowHeigth(0,500);
		jxlbean.setColWidth(0,13);  
		jxlbean.setColWidth(1,13);
		jxlbean.setColWidth(2,13);

	
		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		jxlbean.setTitle(province_detail+"销售地域分布",4);	

		String header="市县,订单数量,所占百分比";
		jxlbean.setHeader(header,1);
			     
		int jxl_line=3;
%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";

			if(begin_date.length()>0){
			   condition+=" and release_date > date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and release_date < date '"+end_date+"'+1";
			}
		try{
		 conn = DBManager.getConnection();								 		 
		 String sql="";
		sql+=" select count(1) as order_count,sum(a.order_sum) as order_sum,b.city ";
		sql+=" from ord_headers a,s_postcode b where b.postcode=substr(a.postcode,0,4) and b.province='"+province_detail+"'"+condition;
		sql+="  group by b.city"; 
		      
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
							
				while(rs.next()){ 
				int ordercount=rs.getInt("order_count");
				String province=rs.getString("city");
				double order_sum=rs.getDouble("order_sum");
					
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,province));
		jxlbean.addCell(new jxl.write.Number(1,jxl_line,ordercount));
		jxlbean.addCell(new jxl.write.Label(2,jxl_line,Math.round(ordercount*10000.0/Integer.parseInt(sum_order_count))/100.0+"%"));

		jxl_line++;	
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




		
    



