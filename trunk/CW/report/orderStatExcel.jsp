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


String name="客服订单统计";

		 response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   

		JxlBean jxlbean=new JxlBean();
        jxlbean.create(response.getOutputStream());
		jxlbean.create_sheet("客服订单统计",0);			    
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
	

		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		jxlbean.setTitle("客服订单统计",6);	

		String header="客服人员,接收订单总数量,完成订单总数量,当天订单完成率(%),接收订单总金额,完成订单总金额,平均订单金额完成率(%),投诉,咨询";
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
			sql="select c.id,c.name,a.ordercount,b.finishcount,a.ordermoney,b.finishmoney,";
			sql+=" round(b.finishcount/a.ordercount*100,2) as ratecount,round(b.finishmoney/a.ordermoney*100,2) as ratemoney from ";
			sql+=" ( select count(id) as ordercount ,sum(order_sum) as ordermoney,creator_id from ";
			sql+=" ord_headers where order_type<>40 "+condition+" group by creator_id ) a,";
			sql+=" ( select count(id) as finishcount,sum(order_sum) as finishmoney ,creator_id  from ";
			sql+=" ord_headers where order_type<>40 "+condition+" and status=100 group by creator_id ) b,org_persons c";
			sql+=" where a.creator_id=b.creator_id and a.creator_id=c.id and c.department_id in (0,2)  order by b.finishmoney desc";			
			pstmt=conn.prepareStatement(sql);
			//System.out.print(sql);
			rs=pstmt.executeQuery();
			int sumordercount=0;
			int sumfinishcount=0;
			int sumordermoney=0;
			int sumfinishmoney=0;

				while(rs.next()){ 
				String id=rs.getString("id");
				String customer_name=rs.getString("name");
				int ordercount=rs.getInt("ordercount");
				int finishcount=rs.getInt("finishcount");
				int ordermoney=rs.getInt("ordermoney");					
				int finishmoney=rs.getInt("finishmoney");
				double ratecount=rs.getDouble("ratecount");
				double ratemoney=rs.getDouble("ratemoney");
				sumordercount=ordercount+sumordercount;
				sumfinishcount=sumfinishcount+finishcount;
				sumordermoney=sumordermoney+ordermoney;
				sumfinishmoney=sumfinishmoney+finishmoney;	
      ResultSet rs1=null;
      PreparedStatement pstmt1=null;		
		String solve="select count(*) from mbr_inquiries where solve_date>=date '"+begin_date+"' and solve_date< date '"+end_date+"'+1 and is_solve=1 and solve_person="+id;
			pstmt1=conn.prepareStatement(solve);
			rs1=pstmt1.executeQuery();
			int count_solve=0;
			if(rs1.next()){
			count_solve=rs1.getInt(1);
			}
			rs1.close();
		  pstmt1.close();			
      ResultSet rs2=null;
      PreparedStatement pstmt2=null;		
		String solve2="select count(*) from mbr_inquiries where solve_date>=date '"+begin_date+"' and solve_date< date '"+end_date+"'+1 and is_solve=0 and solve_person="+id;
			pstmt2=conn.prepareStatement(solve2);
			rs2=pstmt2.executeQuery();
			int count_solve2=0;
			if(rs2.next()){
			count_solve2=rs2.getInt(1);
			}
			rs2.close();
		  pstmt2.close();		  					
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,customer_name));
		jxlbean.addCell(new jxl.write.Number(1,jxl_line,ordercount));
		jxlbean.addCell(new jxl.write.Number(2,jxl_line,finishcount));
		jxlbean.addCell(new jxl.write.Label(3,jxl_line,ratecount+"%"));
		jxlbean.addCell(new jxl.write.Number(4,jxl_line,ordermoney));
		jxlbean.addCell(new jxl.write.Number(5,jxl_line,finishmoney));
		jxlbean.addCell(new jxl.write.Label(6,jxl_line,ratemoney+"%"));
		jxlbean.addCell(new jxl.write.Number(7,jxl_line,count_solve));
		jxlbean.addCell(new jxl.write.Number(8,jxl_line,count_solve2));
		jxl_line++;	
		}
		//总计
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,"合计"));
		jxlbean.addCell(new jxl.write.Number(1,jxl_line,sumordercount));
		jxlbean.addCell(new jxl.write.Number(2,jxl_line,sumfinishcount));
		jxlbean.addCell(new jxl.write.Label(3,jxl_line,Math.round(sumfinishcount*10000.0/sumordercount)/100.0+"%"));
		jxlbean.addCell(new jxl.write.Number(4,jxl_line,sumordermoney));
		jxlbean.addCell(new jxl.write.Number(5,jxl_line,sumfinishmoney));
		jxlbean.addCell(new jxl.write.Label(6,jxl_line,Math.round(sumfinishmoney*10000.0/sumordermoney)/100.0+"%"));
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




		
    



