<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.util.Constants"%>
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	String today=sdf.format(new Date()).toString().trim();	
	
String begin_date = request.getParameter("begin_date");
       begin_date=(begin_date==null)?today:begin_date;	
String end_date = request.getParameter("end_date");
       end_date=(end_date==null)?today:end_date;
String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;
Connection conn=null;
ResultSet rs=null;
PreparedStatement pstmt=null;
String condition=""; 
String cond2 = "";
String sql="";
try{
conn = DBManager.getConnection();
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">

function f_export_excel() {
    if(document.getElementById("data") ==null) {
        alert("没有查询数据不能导出");
        return;
    }
    document.forms[0].action="excel_export.jsp";
    document.target="_blank";
	document.forms[0].submit();
}
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
					alert('请按格式填写开始日期,并且注意你的日期是否正确!');
					document.form.begin_date.focus();
					return false;
			 }
		 }
		 var edate = document.form.end_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.end_date.value!=""){
			 if(edate==null){
					alert('请按格式填写结束日期,并且注意你的日期是否正确!');
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">数据分析</font><font color="838383"> 
      		-&gt; </font><font color="838383">商品日销售统计2</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="sale_total_stats2.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td width="10%">起始日期</td>
		<td width="20%"><input type="text" name="begin_date" value="<%=begin_date%>">
		<a href="javascript:show_calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		<td width="10%">结束日期</td>
		<td width="20%"><input type="text" name="end_date" value="<%=end_date%>">
		<a href="javascript:show_calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		<td><input type="submit" name="search" value=" 查询 "></td>	
	</tr>	
<input name="flag" type="hidden" value="">	
<input type="hidden" name="tag" value="1">
</table>
</form>
<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  noWrap id="data">
	<tr class="OraTableRowHeader" >
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>日期</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>新会员数量</th>		
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>首次购买数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单总数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单商品数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单平均商品数</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>平均商品单价</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单总金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>已发货金额</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>退货金额</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>净销售金额</th>	
					
	</tr>
<%
		if(begin_date.length()>0){
		   condition+=" and create_date >= date'" + begin_date + "'";
		   cond2 += " and release_date >= date'" + begin_date + "'";
		}
		if(end_date.length()>0){
		   condition+=" and create_date < date '"+end_date+"'+1";
		   cond2 +=" and release_date < date '"+end_date+"'+1";
		}
		
		
		String count_sql=			
            " with tt1 as (select to_char(create_date,'yyyy-mm-dd') op_date,count(1) new_qty "
            + " from mbr_members"
            + " where 1=1 " + condition
            + " group by to_char(create_date,'yyyy-mm-dd')),"
            + " tt2 as (select to_char(release_date,'yyyy-mm-dd') op_date,count(1) new_buy_qty"
            + " from ord_headers "
            + " where order_category=0" + cond2
            + " group by to_char(release_date,'yyyy-mm-dd')),"
            + " tt3 as ("
            + " select to_char(release_date,'yyyy-mm-dd') op_date,count(*) total_qty,sum(goods_fee) total_sum,"
            + " sum(case when status>98 then goods_fee else 0 end) real_sum,"
            + " sum(case when status >98 or status=-8 then goods_fee else 0 end) ship_sum,"
            + " sum(case when status=-8 then goods_fee else 0 end) return_sum"
            + " from ord_headers"
            + " where 1=1 " + cond2
            + " group by to_char(release_date,'yyyy-mm-dd')),"
            + " tt4 as ("
            + " select to_char(release_date,'yyyy-mm-dd') op_date,sum(t2.quantity) goods_qty,"
            + " round(sum(t2.quantity)/count(1),1) avg_qty,round(sum(t2.quantity*t2.price)/sum(t2.quantity),2) avg_price"
            + " from ord_headers t1 join ord_lines t2 on t1.id=t2.order_id"
            + " where 1=1 " + cond2
            + " group by to_char(release_date,'yyyy-mm-dd'))"
            
            + " select tt3.op_date,tt1.new_qty,tt2.new_buy_qty,tt3.total_qty,"
            + " tt4.goods_qty,tt4.avg_qty,tt4.avg_price,tt3.total_sum,tt3.ship_sum,tt3.return_sum,tt3.real_sum"
            + " from tt3 left join tt2 on tt3.op_date = tt2.op_date"
            + " left join tt1 on tt1.op_date = tt3.op_date"
            + " left join tt4 on tt3.op_date = tt4.op_date";
		

        request.getSession().setAttribute("excel_name","商品日销售统计2");
        request.getSession().setAttribute("excel_title","日期,新会员数量,首次购买数量,订单总数量,订单商品数量,订单平均商品数,平均商品单价,订单总金额,已发货金额,退货金额,净销售金额");
		request.getSession().setAttribute("excel_sql",count_sql);

		//out.println(count_sql);
			pstmt=conn.prepareStatement(count_sql);
			rs=pstmt.executeQuery();
			while(rs.next()){ 
				
%>
	<tr>
		<td bgcolor="#FFFFFF"><%=rs.getString("op_date")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("new_qty")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("new_buy_qty")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("total_qty")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("goods_qty")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("avg_qty")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("avg_price")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("total_sum")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("ship_sum")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("return_sum")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("real_sum")%></td>		
        
	</tr>
	
<%					
	} 
%>

</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td  align="center">
		
		<a href="excel_export.jsp" target=_blank>
		导出记录</a>
		
	</td>
</tr>
</table>

<%
}//if(tag)
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
