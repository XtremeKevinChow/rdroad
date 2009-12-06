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
      		-&gt; </font><font color="838383">MSC效果统计</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="msc_stats.jsp" method="post" name="form" onsubmit="return querySubmit();">
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
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>MSC</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>会员数量</th>		
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>新会员订单数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>新会员订单金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>老会员订单数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>老会员订单金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>网上订单数量</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>网上订单金额</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>网下订单数量</th>	
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>网下订单金额</th>	
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
              "with t1 as (select msc_code,count(*) mb_count "
            + "from mbr_members "
            + "where 1=1 " + condition
            + "group by msc_code), "
            + "t2 as (select msc_code,count(*) ord_count,sum(goods_fee) ord_amt, "
            + "sum(decode(order_category,0,1,0)) new_count,sum(decode(order_category,0,goods_fee,0)) new_amt, "
            + "sum(decode(order_category,0,0,1)) old_count,sum(decode(order_category,0,0,goods_fee)) old_amt, "
            + "sum(decode(pr_type,3,1,0)) net_count,sum(decode(pr_type,3,goods_fee,0)) net_amt, "
            + "sum(decode(pr_type,3,0,1)) tel_count,sum(decode(pr_type,3,0,goods_fee)) tel_amt "
            + "from ord_headers "
            + "where order_type<>20 and status>0 " + cond2
            + "group by msc_code) "
            + "select nvl(t1.msc_code,t2.msc_code) msc,nvl(t1.mb_count,0) mb_count,nvl(t2.ord_count,0) ord_count,"
            + "nvl(t2.ord_amt,0) ord_amt,nvl(t2.new_count,0) new_count,nvl(t2.new_amt,0) new_amt, "
            + "nvl(t2.old_count,0) old_count,nvl(t2.old_amt,0) old_amt,"
            + "nvl(t2.net_count,0) net_count,nvl(t2.net_amt,0) net_amt,nvl(t2.tel_count,0) tel_count,nvl(t2.tel_amt,0) tel_amt "
            + "from t1 full join t2 on t1.msc_code = t2.msc_code ";
		

        request.getSession().setAttribute("excel_name","MSC效果统计");
        request.getSession().setAttribute("excel_title","MSC,会员数量,订单数量,订单金额,新会员订单数量,新会员订单金额,老会员订单数量,老会员订单金额,网上订单数量,网上订单金额,网下订单数量,网下订单金额");
		request.getSession().setAttribute("excel_sql",count_sql);

		//out.println(count_sql);
			pstmt=conn.prepareStatement(count_sql);
			rs=pstmt.executeQuery();
			while(rs.next()){ 
				
%>
	<tr>
		<td bgcolor="#FFFFFF"><%=rs.getString("msc")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("mb_count")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("ord_count")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("ord_amt")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("new_count")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("new_amt")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("old_count")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("old_amt")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("net_count")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("net_amt")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("tel_count")%></td>		
        <td bgcolor="#FFFFFF"><%=rs.getString("tel_amt")%></td>
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
