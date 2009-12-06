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
String province_detail = request.getParameter("province_detail");
String sum_order_sum= request.getParameter("sum_order_sum");
String sum_order_count = request.getParameter("sum_order_count");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">分析报表</font><font color="838383"> 
      		-&gt; </font><font color="838383">销售地域分布(市县)</font>&nbsp;&nbsp;<a href="mbrsalezone_detail_Excel.jsp?province_detail=<%=province_detail%>&begin_date=<%=begin_date%>&end_date=<%=end_date%>&sum_order_sum=<%=sum_order_sum%>&sum_order_count=<%=sum_order_count%>">生成Excel</a><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>市县</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>销售金额</td>		
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>所占百分比</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单数量</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>所占百分比</td>		
	</tr>
<%
			Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";

			if(begin_date.length()>0){
			   condition+=" and a.release_date > date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and a.release_date < date '"+end_date+"'+1";
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



%>
	<tr align="center">
		<td width="15%" bgcolor="#FFFFFF"><%=province%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=order_sum%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(order_sum*10000.0/Double.parseDouble(sum_order_sum))/100.0%>%</td>
		<td width="15%" bgcolor="#FFFFFF"><%=ordercount%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(ordercount*10000.0/Integer.parseInt(sum_order_count))/100.0%>%</td>

	</tr>
	
<%					
	
	
	} 

%>

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

%>
</body>
</html>
