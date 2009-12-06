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
      		-&gt; </font><font color="838383">客服订单统计</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="orderStat.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>起始日期</td>
		<td><input type="text" name="begin_date" value="<%=begin_date%>"></td>
		<td>结束日期</td>
		<td><input type="text" name="end_date" value="<%=end_date%>">(日期格式:YYYY-MM-DD)	
		<input type="submit" name="search" value="查  询">&nbsp;&nbsp;<%if(tag.equals("1")){%><a href="orderStatExcel.jsp?begin_date=<%=begin_date%>&end_date=<%=end_date%>">生成Excel</a><%}%></td>		
	</tr>	
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>客服人员</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>接收总数量</td>		
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>完成总数量</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>当天完成率(%)</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>接收总金额</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>完成总金额</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>平均金额完成率(%)</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>投诉</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>咨询</td>
		
		
	</tr>
<%
			Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";

			if(begin_date.length()>0){
			   condition+=" and release_date >= date '"+begin_date+"'";
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
			sql+=" ord_headers where order_type<>40 "+condition+" and status>98 group by creator_id ) b,org_persons c";
			sql+=" where a.creator_id=b.creator_id and a.creator_id=c.id and c.department_id in (0,2) order by b.finishmoney desc";			
			pstmt=conn.prepareStatement(sql);
			//out.println(sql);
			rs=pstmt.executeQuery();
			int sumordercount=0;
			int sumfinishcount=0;
			int sumordermoney=0;
			int sumfinishmoney=0;

				while(rs.next()){ 
				String name=rs.getString("name");
				String id=rs.getString("id");
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
				

%>
	<tr align="center">
		<td bgcolor="#FFFFFF"><%=name%></td>
		<td bgcolor="#FFFFFF"><%=ordercount%></td>
		<td bgcolor="#FFFFFF"><%=finishcount%></td>
		<td bgcolor="#FFFFFF"><%=ratecount%>%</td>
		<td bgcolor="#FFFFFF"><%=ordermoney%></td>
		<td bgcolor="#FFFFFF"><%=finishmoney%></td>
		<td bgcolor="#FFFFFF"><%=ratemoney%>%</td>
		<td bgcolor="#FFFFFF"> 
		<%
      ResultSet rs1=null;
      PreparedStatement pstmt1=null;		
		String solve="select count(*) from mbr_inquiries where solve_date>=date '"+begin_date+"' and solve_date< date '"+end_date+"'+1 and is_solve=1 and solve_person="+id;
		//out.println(solve);
			pstmt1=conn.prepareStatement(solve);
			rs1=pstmt1.executeQuery();
			if(rs1.next()){
			out.println(rs1.getInt(1));
			}
			rs1.close();
		  pstmt1.close();
		%>
		</td>
		<td bgcolor="#FFFFFF">
		<%
      ResultSet rs2=null;
      PreparedStatement pstmt2=null;		
		String solve2="select count(*) from mbr_inquiries where solve_date>=date '"+begin_date+"' and solve_date< date '"+end_date+"'+1 and is_solve=0 and solve_person="+id;
		//out.println(solve2);
			pstmt2=conn.prepareStatement(solve2);
			rs2=pstmt2.executeQuery();
			if(rs2.next()){
			out.println(rs2.getInt(1));
			}
			rs2.close();
		  pstmt2.close();
		%>		
		</td>
	</tr>
	
<%					
	
	
	} 

%>
	<tr align="center">
		<td bgcolor="#FFFFFF">合计</td>
		<td bgcolor="#FFFFFF"><%=sumordercount%></td>
		<td bgcolor="#FFFFFF"><%=sumfinishcount%></td>
		<td bgcolor="#FFFFFF"><%=Math.round(sumfinishcount*10000.0/sumordercount)/100.0%>%</td>
		<td bgcolor="#FFFFFF"><%=sumordermoney%></td>
		<td bgcolor="#FFFFFF"><%=sumfinishmoney%></td>
		<td width="10%" bgcolor="#FFFFFF"><%=Math.round(sumfinishmoney*10000.0/sumordermoney)/100.0%>%</td>
		<td bgcolor="#FFFFFF"> </td>
		<td bgcolor="#FFFFFF"> </td>		
	</tr>
</table>
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
