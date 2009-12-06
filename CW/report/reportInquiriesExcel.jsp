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
String status=request.getParameter("status");
status=(status==null)?"":status;

String name="客服来电记录";

		 response.reset();
		 response.setContentType("application/vnd.ms-excel");
		 String filename = new String(name.getBytes("gb2312"),"iso8859-1");
		 response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls" + "");   

		JxlBean jxlbean=new JxlBean();
        jxlbean.create(response.getOutputStream());
		jxlbean.create_sheet("客服来电记录",0);			    
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
	if(status.equals("1")){
		jxlbean.setColWidth(9,13);
		jxlbean.setColWidth(10,13);
		jxlbean.setColWidth(11,13);

	}

		//设置浮点数的格式
		jxl.write.WritableCellFormat cf2 = new jxl.write.WritableCellFormat(jxl.write.NumberFormats.FLOAT);
		String header="";
	if(status.equals("1")){
		jxlbean.setTitle("客服订单统计",11);	
		
		header="会员号,姓名,地区,事件级别,事件类型,相关部门,事件描述,受理人,受理日期,解决方法,解决人,解决日期";
	}else{
		jxlbean.setTitle("客服订单统计",8);	
		
		header="会员号,姓名,地区,事件级别,事件类型,相关部门,事件描述,受理人,受理日期";	
	}
	
		jxlbean.setHeader(header,1);
			     
		int jxl_line=3;
%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";

		try{
		 conn = DBManager.getConnection();
		 
    if(status.equals("0")){
		if(begin_date.length()>0){
		   condition+=" and b.solve_date > date '"+begin_date+"'";
		}
		if(end_date.length()>0){
		   condition+=" and b.solve_date < date '"+end_date+"'+1";
		}  
		String sql="";
		sql="select a.card_id,a.name,";
		sql+=" (select d.city from mbr_addresses c,s_postcode d where d.postcode=substr(c.postcode,0,4) and c.id=a.address_id) as city,";
		sql+=" (select name from s_inquiry_level where id=b.inquiry_level) as  lname,";
		sql+=" (select name from s_inquiry_type where id=b.inquiry_type) as  tname,";
		sql+="  b.ref_department,b.solve_method,";
		sql+=" (select name from org_persons where id=b.solve_person) as  oname,";
		sql+=" b.solve_date";
		sql+=" from mbr_members a,mbr_inquiries b ";
		sql+=" where  b.is_solve=1  ";
		sql+=" and a.id=b.memberid ";
		sql+=" and b.rootid=0 and b.status=0 "+condition;
		sql+=" order by b.ref_department,a.name	";
		
		
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();

			while(rs.next()){ 
			String card_id=rs.getString("card_id");
			String mb_name=rs.getString("name");
			String cityname=rs.getString("city");
			String level_name=rs.getString("lname");
			String level_type=rs.getString("tname");
			String ref_department=rs.getString("ref_department");
					if(ref_department.equals("0")){
					   ref_department="（无）";
					 }
					if(ref_department.equals("1")){
					   ref_department="市场部";
					 }
					if(ref_department.equals("2")){
					   ref_department="客服部";
					 }
					if(ref_department.equals("3")){
					   ref_department="编辑部";
					 }
					if(ref_department.equals("4")){
					   ref_department="人事行政部";
					 }
					if(ref_department.equals("5")){
					   ref_department="IT部";
					 }
					if(ref_department.equals("6")){
					   ref_department="物流部";
					 }
					if(ref_department.equals("7")){
					   ref_department="总经理室";
					 }
					if(ref_department.equals("8")){
					   ref_department="财务部";
					 }
					if(ref_department.equals("9")){
					   ref_department="网站";
					 }			
			String solve_method=rs.getString("solve_method");
			       solve_method=(solve_method==null)?"":solve_method;
			String solve_name=rs.getString("oname");
			String solve_date=rs.getString("solve_date");
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,card_id));
		jxlbean.addCell(new jxl.write.Label(1,jxl_line,mb_name));
		jxlbean.addCell(new jxl.write.Label(2,jxl_line,cityname));
		jxlbean.addCell(new jxl.write.Label(3,jxl_line,level_name));
		jxlbean.addCell(new jxl.write.Label(4,jxl_line,level_type));
		jxlbean.addCell(new jxl.write.Label(5,jxl_line,ref_department));
		jxlbean.addCell(new jxl.write.Label(6,jxl_line,solve_method));
		jxlbean.addCell(new jxl.write.Label(7,jxl_line,solve_name));
		jxlbean.addCell(new jxl.write.Label(8,jxl_line,solve_date));
		jxl_line++;				
			}
	}			
if(status.equals("1")){
		if(begin_date.length()>0){
		   condition+=" and solve_date > date '"+begin_date+"'";
		}
		if(end_date.length()>0){
		   condition+=" and solve_date < date '"+end_date+"'+1";
		}

			String sql="";
			sql+=" select a.card_id,a.name,";
			sql+=" (select d.city from mbr_addresses c,s_postcode d where d.postcode=substr(c.postcode,0,4) and c.id=a.address_id) as city,";
			sql+=" (select name from s_inquiry_level where id=b.inquiry_level) as  lname,";
			sql+=" (select name from s_inquiry_type where id=b.inquiry_type) as  tname,";
			sql+=" b.ref_department,";
			sql+=" b.solve_method1,b.solve_date1,";
			sql+=" (select name from org_persons where id=b.solve_person1) as sname1,";
			sql+=" b.solve_method2 ,b.solve_date2 ,";
			sql+=" (select name from org_persons where id=b.solve_person2) as sname2";
			sql+=" from mbr_members a,";
			sql+=" (select a.event_id,a.is_solve,a.solve_method as solve_method1,a.solve_date as solve_date1,a.solve_person as solve_person1,";
			sql+="  a.rootid,a.status,a.ref_department,a.createid,a.memberid,a.inquiry_level,a.inquiry_type,";
			sql+="  b.solve_method as solve_method2,b.solve_date as solve_date2 ,b.solve_person as solve_person2,b.createid";
			sql+="  from  ";
			sql+="  (select event_id,is_solve,solve_method,solve_date,createid,rootid,status,memberid,ref_department,solve_person,inquiry_level,inquiry_type from  mbr_inquiries ";
			sql+="  where rootid=0 "+condition+" and is_solve=1 and status=1) a,";
			sql+="  (select a.solve_method,a.solve_date,a.createid,a.solve_person from ";
			sql+="  mbr_inquiries a inner join (select createid,max(solve_date) as solve_date from mbr_inquiries ";
			sql+="  group by  createid having count(createid)>1) b";
			sql+="  on b.createid=a.createid and a.solve_date=b.solve_date and rootid=1  and a.is_solve=1 ) b";
			sql+="  where a.event_id=b.createid ";
			sql+=" ) b";
			sql+=" where a.id=b.memberid ";
			sql+=" order by b.ref_department";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();


			while(rs.next()){ 
			String card_id=rs.getString("card_id");
			String mb_name=rs.getString("name");
			String cityname=rs.getString("city");
			String level_name=rs.getString("lname");
			String level_type=rs.getString("tname");
			String ref_department=rs.getString("ref_department");
					if(ref_department.equals("0")){
					   ref_department="（无）";
					 }
					if(ref_department.equals("1")){
					   ref_department="市场部";
					 }
					if(ref_department.equals("2")){
					   ref_department="客服部";
					 }
					if(ref_department.equals("3")){
					   ref_department="编辑部";
					 }
					if(ref_department.equals("4")){
					   ref_department="人事行政部";
					 }
					if(ref_department.equals("5")){
					   ref_department="IT部";
					 }
					if(ref_department.equals("6")){
					   ref_department="物流部";
					 }
					if(ref_department.equals("7")){
					   ref_department="总经理室";
					 }
					if(ref_department.equals("8")){
					   ref_department="财务部";
					 }
					if(ref_department.equals("9")){
					   ref_department="网站";
					 }			
			String solve_method1=rs.getString("solve_method1");
			       solve_method1=(solve_method1==null)?"":solve_method1;	
			String solve_name1=rs.getString("sname1");			       					
			String solve_date1=rs.getString("solve_date1");

			String solve_method2=rs.getString("solve_method2");
			       solve_method2=(solve_method2==null)?"":solve_method2;						
			String solve_date2=rs.getString("solve_date2");
			String solve_name2=rs.getString("sname2");
			
		jxlbean.addCell(new jxl.write.Label(0,jxl_line,card_id));
		jxlbean.addCell(new jxl.write.Label(1,jxl_line,mb_name));
		jxlbean.addCell(new jxl.write.Label(2,jxl_line,cityname));
		jxlbean.addCell(new jxl.write.Label(3,jxl_line,level_name));
		jxlbean.addCell(new jxl.write.Label(4,jxl_line,level_type));
		jxlbean.addCell(new jxl.write.Label(5,jxl_line,ref_department));
		jxlbean.addCell(new jxl.write.Label(6,jxl_line,solve_method1));
		jxlbean.addCell(new jxl.write.Label(7,jxl_line,solve_name1));
		jxlbean.addCell(new jxl.write.Label(8,jxl_line,solve_date1));
		jxlbean.addCell(new jxl.write.Label(9,jxl_line,solve_method2));
		jxlbean.addCell(new jxl.write.Label(10,jxl_line,solve_date2));
		jxlbean.addCell(new jxl.write.Label(11,jxl_line,solve_name2));
		jxl_line++;						
				}
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




		
    



