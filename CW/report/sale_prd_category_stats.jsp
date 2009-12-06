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
					alert('请按格式填写起始日期,并且注意你的日期是否正确!');
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
      		-&gt; </font><font color="838383">产品类别销售统计</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="sale_prd_category_stats.jsp" method="post" name="form" onsubmit="return querySubmit();">
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
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap id="data">
	<tr>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>产品类别</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>MSC</th>		
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单来源</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>总金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>金额百分比%</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>总数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>数量百分比%</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>毛利</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>毛利百分比%</th>			
	</tr>
<%
		if(begin_date.length()>0){
		   condition+=" and t1.release_date >= date '"+begin_date+"'";
		}
		if(end_date.length()>0){
		   condition+=" and t1.release_date < date '"+end_date+"'+1";
		}
			
		String count_sql=			
        " select t.catalog_name,t.msc_code,t.name,t.total_amt,round(RATIO_TO_REPORT(t.total_amt) over()*100,2) ratio_amt,"
        + " t.total_qty,round(RATIO_TO_REPORT(t.total_qty) over()*100,2) ratio_qty,"
        + " t.total_profit,round(RATIO_TO_REPORT(t.total_profit) over()*100,2) ratio_profit"
        + " from ("
        + " select t5.catalog_name,t1.msc_code,t6.name,sum(t2.quantity) total_qty,"
        + " sum(t2.quantity*t2.price) total_amt,"
        + " sum(t2.quantity*t2.price-t2.quantity*t3.itm_cost) total_profit "
        + " from ord_headers t1 join ord_lines t2 on t1.id=t2.order_id"
        + " join prd_item_sku t3 on t2.sku_id = t3.SKU_ID"
        + " join prd_item t4 on t3.itm_code = t4.ITM_CODE"
        + " join prd_item_category t5 on t4.CATEGORY_ID = t5.CATALOG_ID"
        + " join s_pr_type t6 on t1.pr_type = t6.id"
        + " where 1=1 " + condition
        + " and t1.status>0 and t2.status>0"
        + " group by t5.catalog_name,t1.msc_code,t6.name) t";
		

        request.getSession().setAttribute("excel_name","产品类别销售统计");
        request.getSession().setAttribute("excel_title","类别,MSC,订单来源,总金额,金额百分比,总数量,数量百分比,毛利,毛利百分比");
		request.getSession().setAttribute("excel_sql",count_sql);

		//out.println(count_sql);
			pstmt=conn.prepareStatement(count_sql);
			rs=pstmt.executeQuery();
			
			//销售金额,数量，毛利 合计
			int sum_qty = 0;
			double sum_amt = 0;
			double sum_profit =0;

			while(rs.next()){ 
				String catalog_name=rs.getString("catalog_name");
				String msc_code=rs.getString("msc_code");
				String pr_name=rs.getString("name");
				double total_amt=rs.getDouble("total_amt");
				int total_qty=rs.getInt("total_qty");
				double total_profit=rs.getDouble("total_profit");
				double ratio_amt = rs.getDouble("ratio_amt");
				double ratio_qty = rs.getDouble("ratio_qty");
				double ratio_profit = rs.getDouble("ratio_profit");
				sum_qty += total_qty;
				sum_amt += total_amt;
				sum_profit += total_profit;
%>
	<tr>
		<td bgcolor="#FFFFFF"><%=catalog_name%></td>
		<td bgcolor="#FFFFFF"><%=msc_code%></td>
		<td bgcolor="#FFFFFF"><%=pr_name%></td>
		<td bgcolor="#FFFFFF"><%=total_amt%></td>
		<td bgcolor="#FFFFFF"><%=ratio_amt%></td>		
		<td bgcolor="#FFFFFF"><%=total_qty%></td>
		<td bgcolor="#FFFFFF"><%=ratio_qty%></td>	
		<td bgcolor="#FFFFFF"><%=total_profit%></td>
		<td bgcolor="#FFFFFF"><%=ratio_profit%></td>		

	</tr>
	
<%					
	} 
%>
	<tr align="center">
		<td colspan="3" bgcolor="#FFFFFF"></td>
		<td  bgcolor="#FFFFFF"><%=String.valueOf(Math.round(sum_qty*100.0)/100.0)%></td>
		<td bgcolor="#FFFFFF"></td>
		<td bgcolor="#FFFFFF"><%=String.valueOf(Math.round(sum_amt*100.0)/100.0)%></td>	
		<td bgcolor="#FFFFFF"></td>
		<td  bgcolor="#FFFFFF"><%=String.valueOf(Math.round(sum_profit*100.0)/100.0)%></td>	
		<td bgcolor="#FFFFFF"></td>	

	</tr>
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
