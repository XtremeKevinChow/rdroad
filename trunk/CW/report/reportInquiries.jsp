<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String begin_date = request.getParameter("begin_date");
			 begin_date=(begin_date==null)?"":begin_date;	
String end_date = request.getParameter("end_date");
			 end_date=(end_date==null)?"":end_date;	

String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;
String status=request.getParameter("status");
status=(status==null)?"":status;

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

	if(document.form.begin_date.value==""
	&&document.form.end_date.value==""	
	){
		alert('查询条件不能为空!');
		return false;;
	}else{
		var bdate = document.form.begin_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.begin_date.value!=""){
			 if(bdate==null){
					alert('请按格式填写招募起始日期,并且注意你的日期是否正确!');
					document.form.begin_date.focus();
					return false;
			 }
		 }	
		var edate = document.form.end_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.end_date.value!=""){
			 if(edate==null){
					alert('请按格式填写招募结束日期,并且注意你的日期是否正确!');
					document.form.end_date.focus();
					return false;
			 }
		}		 
	}
	
	document.form.search.disabled = true;
	document.form.submit();
}

function initFocus(){
	document.form.begin_date.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">分析报表</font><font color="838383"> 
      		-&gt; </font><font color="838383">客服来电记录</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="reportInquiries.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>起始日期</td>
		<td><input type="text" name="begin_date" value="<%=begin_date%>"></td>
		<td>结束日期</td>
		<td><input type="text" name="end_date" value="<%=end_date%>">(日期格式:YYYY-MM-DD)	
		</td>
		<td>状态</td>
		<td><select  name="status">
		    <option value="0" <%if(status.equals("0")){%>selected<%}%> >未解决</option>
		    <option value="1" <%if(status.equals("1")){%>selected<%}%> >已解决</option>
		    </select>
		&nbsp;&nbsp;<input type="submit" name="search" value="查  询">
		&nbsp;&nbsp;<%if(tag.equals("1")){%><a href="reportInquiriesExcel.jsp?begin_date=<%=begin_date%>&end_date=<%=end_date%>&status=<%=status%>">生成Excel</a><%}%>
		</td>			
	</tr>	
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
if(tag.equals("1")){
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
%>
		<table width="1500" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
			<tr">
				<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>会员号</td>
				<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>姓名</td>		
				<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>地区</td>
				<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件级别</td>
				<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件类型</td>
				<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>相关部门</td>
				<td width="45%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件描述</td>
				<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>受理人</td>
				<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>受理日期</td>	
				
			</tr>
		<%


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
				String solve_method=rs.getString("solve_method");
				       solve_method=(solve_method==null)?"":solve_method;
				String solve_name=rs.getString("oname");
				String solve_date=rs.getString("solve_date");
		
		
		%>
			<tr align="center">
				<td  bgcolor="#FFFFFF"><%=card_id%></td>
				<td  bgcolor="#FFFFFF"><%=mb_name%></td>
				<td  bgcolor="#FFFFFF"><%=cityname%></td>
				<td  bgcolor="#FFFFFF"><%=level_name%></td>
				<td  bgcolor="#FFFFFF"><%=level_type%></td>
				<td  bgcolor="#FFFFFF">
					<%
					if(ref_department.equals("0")){
					   out.println("（无）");
					 }
					if(ref_department.equals("1")){
					   out.println("市场部");
					 }
					if(ref_department.equals("2")){
					   out.println("客服部");
					 }
					if(ref_department.equals("3")){
					   out.println("编辑部");
					 }
					if(ref_department.equals("4")){
					   out.println("人事行政部");
					 }
					if(ref_department.equals("5")){
					   out.println("IT部");
					 }
					if(ref_department.equals("6")){
					   out.println("物流部");
					 }
					if(ref_department.equals("7")){
					   out.println("总经理室");
					 }
					if(ref_department.equals("8")){
					   out.println("财务部");
					 }
					if(ref_department.equals("9")){
					   out.println("网站");
					 }
					 					 					 					 					 					 					 					 					 					 
					%>
				</td>
				<td  bgcolor="#FFFFFF"><%=solve_method%></td>
				<td  bgcolor="#FFFFFF"><%=solve_name%></td>
				<td  bgcolor="#FFFFFF"><%=solve_date%></td>
		
			</tr>			
		<%}%> 
		</table>
 <%}%>		

		
<%if(status.equals("1")){
			if(begin_date.length()>0){
			   condition+=" and solve_date > date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and solve_date < date '"+end_date+"'+1";
			}

%>
		<table width="1700" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
			<tr">
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>会员号</td>
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>姓名</td>		
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>地区</td>
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件级别</td>
				<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件类型</td>
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>相关部门</td>
				<td width="26%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件描述</td>
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>受理人</td>
				<td width="8%" class="OraTableRowHeader" noWrap  noWrap align=middle>受理日期</td>
				<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>解决方法</td>
				<td width="4%" class="OraTableRowHeader" noWrap  noWrap align=middle>解决人</td>
				<td width="8%" class="OraTableRowHeader" noWrap  noWrap align=middle>解决日期</td>					
				
			</tr>
			
		<%


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
		//out.println("1 "+sql);
		
						while(rs.next()){ 
						String card_id=rs.getString("card_id");
						String mb_name=rs.getString("name");
						String cityname=rs.getString("city");
						String level_name=rs.getString("lname");
						String level_type=rs.getString("tname");
						String ref_department=rs.getString("ref_department");
						String solve_method1=rs.getString("solve_method1");
						       solve_method1=(solve_method1==null)?"":solve_method1;						
						String solve_date1=rs.getString("solve_date1");
						String solve_name1=rs.getString("sname1");
						String solve_method2=rs.getString("solve_method2");
						       solve_method2=(solve_method2==null)?"":solve_method2;						
						String solve_date2=rs.getString("solve_date2");
						String solve_name2=rs.getString("sname2");						
		
						
						
						
		
		%>
			<tr align="center">
				<td  bgcolor="#FFFFFF"><%=card_id%></td>
				<td  bgcolor="#FFFFFF"><%=mb_name%></td>
				<td  bgcolor="#FFFFFF"><%=cityname%></td>
				<td  bgcolor="#FFFFFF"><%=level_name%></td>
				<td  bgcolor="#FFFFFF"><%=level_type%></td>
				<td  bgcolor="#FFFFFF">
					<%
					if(ref_department.equals("0")){
					   out.println("（无）");
					 }
					if(ref_department.equals("1")){
					   out.println("市场部");
					 }
					if(ref_department.equals("2")){
					   out.println("客服部");
					 }
					if(ref_department.equals("3")){
					   out.println("编辑部");
					 }
					if(ref_department.equals("4")){
					   out.println("人事行政部");
					 }
					if(ref_department.equals("5")){
					   out.println("IT部");
					 }
					if(ref_department.equals("6")){
					   out.println("物流部");
					 }
					if(ref_department.equals("7")){
					   out.println("总经理室");
					 }
					if(ref_department.equals("8")){
					   out.println("财务部");
					 }
					if(ref_department.equals("9")){
					   out.println("网站");
					 }
					 					 					 					 					 					 					 					 					 					 
					%>
				</td>
				<td  bgcolor="#FFFFFF"><%=solve_method1%></td>
				<td  bgcolor="#FFFFFF"><%=solve_name1%></td>
				<td  bgcolor="#FFFFFF"><%=solve_date1%></td>
				<td  bgcolor="#FFFFFF"><%=solve_method2%></td>
				<td  bgcolor="#FFFFFF"><%=solve_name2%></td>
				<td  bgcolor="#FFFFFF"><%=solve_date2%></td>				
		
			</tr>			
		<%}%> 
		</table>
<%}%>		
		
		<%
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

}%>
</body>
</html>
