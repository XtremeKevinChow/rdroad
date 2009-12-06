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
      		-&gt; </font><font color="838383">销售地域分布</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbrsalezone.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>起始日期</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle><input type="text" name="begin_date" value="<%=begin_date%>"></td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>结束日期</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle><input type="text" name="end_date" value="<%=end_date%>">(日期格式:YYYY-MM-DD)	
		<input type="submit" name="search" value="查  询">&nbsp;&nbsp;<%if(tag.equals("1")){%><a href="mbrsalezoneExcel.jsp?begin_date=<%=begin_date%>&end_date=<%=end_date%>" target=_blank>生成Excel</a><%}%></td>		
	</tr>	
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>省份</td>
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
			   condition+=" and a.release_date >= date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and a.release_date < date '"+end_date+"'+1";
			}
		try{
		 conn = DBManager.getConnection();
		 
		 String sql1="select count(1) as all_order_count,sum(a.order_sum) as all_order_sum from ord_headers a where rownum>0 "+condition;
			pstmt=conn.prepareStatement(sql1);

			rs=pstmt.executeQuery();
			rs.next();
		 int all_order_count=rs.getInt(1);
		 int all_order_sum=rs.getInt(2);			
			rs.close();
			pstmt.close();	
							 
		 
		 String sql="";
		 sql+=" select count(1) as order_count,sum(a.order_sum) as order_sum,b.province ";
		 sql+=" from ord_headers a,s_postcode b where b.postcode=substr(a.postcode,0,4) "+condition;
		 sql+=" group by b.province ";
        
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
							
				while(rs.next()){ 
				int ordercount=rs.getInt("order_count");
				String province=rs.getString("province");
				double order_sum=rs.getDouble("order_sum");



%>
	<tr align="center">
		<td width="15%" bgcolor="#FFFFFF"><a href="mbrsalezone_detail.jsp?province_detail=<%=province%>&begin_date=<%=begin_date%>&end_date=<%=end_date%>&sum_order_sum=<%=order_sum%>&sum_order_count=<%=ordercount%>" target=_blank><%=province%></a></td>
		<td width="15%" bgcolor="#FFFFFF"><%=order_sum%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(order_sum*10000.0/all_order_sum)/100.0%>%</td>
		<td width="15%" bgcolor="#FFFFFF"><%=ordercount%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(ordercount*10000.0/all_order_count)/100.0%>%</td>

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

}%>
</body>
</html>
