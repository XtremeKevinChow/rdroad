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
	||document.form.end_date.value==""
	){
		alert('开始日期和结束日期均不能为空!');
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
      		-&gt; </font><font color="838383">商品日销售统计1</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="sale_total_stats1.jsp" method="post" name="form" onsubmit="return querySubmit();">
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
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" id="data">
<%
		if(begin_date.length()>0){
		   condition+=" and t1.release_date >= date'" + begin_date + "'";
		}
		if(end_date.length()>0){
		   condition+=" and t1.release_date < date '"+end_date+"'+1";
		}
		
		int total_count=0,tel_count=0,web_count=0,post_count=0,ems_count=0,express_count=0,web_pay_count=0;
		int union_pay_count=0,post_pay_count=0,bank_pay_count=0;
		int money_short_count=0,goods_short_count=0,first_buy_count=0,order_cancel_count=0,order_complete_count=0;
		double total_sum=0,tel_sum=0,web_sum=0,money_short_sum=0,goods_short_sum=0,order_cancel_sum=0;
		
		String count_sql=" select count(1) total_count,sum(goods_fee) total_sum, "
              +"  sum(decode(pr_type,3,0,1)) tel_count,sum(decode(pr_type,3,0,goods_fee)) tel_sum,"
              +"  sum(decode(pr_type,3,1,0)) web_count,sum(decode(pr_type,3,goods_fee,0)) web_sum,"
              +"  sum(decode(delivery_type,1,1,0)) post_count,"
              +"  sum(decode(delivery_type,4,1,0)) ems_count,"
              +"  sum(decode(delivery_type,3,1,5,1,0)) express_count,"
              +"  sum(case when pr_type=3 and payment_method not in (1,2,3) then 1 else 0 end ) web_pay_count,"
              +"  sum(case when payment_method in (94) then 1 else 0 end ) union_pay_count,"
              +"  sum(case when payment_method in (6) then 1 else 0 end ) post_pay_count,"
              +"  sum(case when payment_method in (15) then 1 else 0 end ) bank_pay_count,"
              +"  sum(case when status = 15 then 1 else 0 end) money_short_count,"
              +"  sum(case when status = 15 then goods_fee else 0 end) money_short_sum,"
              +"  sum(case when status in (20,21) then 1 else 0 end) goods_short_count,"
              +"  sum(case when status in (20,21) then goods_fee else 0 end) goods_short_sum,"
              +"  sum(case when status in (-1,-2,-3) then 1 else 0 end) order_cancel_count,"
              +"  sum(case when status in (-1,-2,-3) then goods_fee else 0 end) order_cancel_sum,"
              +"  sum(case when status in (99,100,-8) then 1 else 0 end) order_complete_count,"
              +"  sum(case when order_category = 0 then 1 else 0 end) first_buy_count"
              +" from ord_headers t1 "
              +" where 1=1 " 
              + condition;
            
		pstmt=conn.prepareStatement(count_sql);
		rs=pstmt.executeQuery();
        if(rs.next()){ 
		    total_count = rs.getInt("total_count");
		    total_sum = rs.getDouble("total_sum");
		    tel_count = rs.getInt("tel_count");
		    tel_sum = rs.getDouble("tel_sum");
		    web_count = rs.getInt("web_count");
		    web_sum = rs.getDouble("web_sum");
		    post_count = rs.getInt("post_count");
		    ems_count = rs.getInt("ems_count");
		    express_count = rs.getInt("express_count");
		    web_pay_count = rs.getInt("web_pay_count");
		    union_pay_count = rs.getInt("union_pay_count");
		    post_pay_count = rs.getInt("post_pay_count");
		    bank_pay_count = rs.getInt("bank_pay_count");
		    money_short_count = rs.getInt("money_short_count");
		    money_short_sum = rs.getDouble("money_short_sum");
		    goods_short_count = rs.getInt("goods_short_count");
		    goods_short_sum = rs.getDouble("goods_short_sum");
		    order_cancel_count = rs.getInt("order_cancel_count");
		    order_cancel_sum = rs.getDouble("order_cancel_sum");
		    order_complete_count = rs.getInt("order_complete_count");
		    first_buy_count = rs.getInt("first_buy_count");
		    
		
		}
		rs.close();
		pstmt.close();
		
		// 取订单产品总数和平均每单产品数
		int goods_total=0;
		double goods_avg = 0;
		count_sql = " select sum(t2.quantity) goods_total,round(avg(t2.quantity),1) goods_avg "
            + " from ord_headers t1 "
            + " join ord_lines t2 on t1.id=t2.order_id "
            + " where 1=1 " + condition;
		pstmt = conn.prepareStatement(count_sql);
		rs = pstmt.executeQuery();
		if(rs.next()) {
		    goods_total = rs.getInt("goods_total");
		    goods_avg = rs.getDouble("goods_avg");
		}
		rs.close();
		pstmt.close();
		
		// 取换货信息
		int order_change_count = 0;
		double order_change_sum = 0;
		count_sql = " select count(distinct t1.id) order_change_count,sum(t3.quantity*t3.price) order_change_sum"
            + " from ord_headers t1 join ord_headers t2 on t1.ref_order_id =t2.id "
            + " join ord_lines t3 on t2.id =t3.order_id and t3.status=22 "
            + " where 1=1 " + condition
            + " and t1.order_type=20 and t2.order_type in (5,10) ";
        pstmt = conn.prepareStatement(count_sql);
        rs = pstmt.executeQuery();
        if(rs.next()) {
            order_change_count = rs.getInt("order_change_count");
            order_change_sum = rs.getDouble("order_change_sum");
        }
		rs.close();
		pstmt.close();
		
		//取退货数据
		int order_return_count = 0;
		double order_return_sum =0;
		count_sql = " select count(distinct t1.id) order_return_count,sum(t1.goods_fee) order_return_sum"
            + " from ord_headers t1  "
            + " where t1.status =-8 "
            + " and t1.release_date >= date'" + begin_date + "' and t1.release_date< date'" + end_date + "' +1 ";
        pstmt = conn.prepareStatement(count_sql);
        rs = pstmt.executeQuery();
        if(rs.next()){
            order_return_count = rs.getInt("order_return_count") ;
            order_return_sum = rs.getDouble("order_return_sum");
        }
        rs.close();
        pstmt.close();
        
        //取当日发包信息
        int all_complete_count = 0;
        double all_complete_sum = 0;
        count_sql = "select count(1) all_complete_count,sum(goods_fee) all_complete_sum "
            + " from ord_headers t1 where complete_date>=date'" + begin_date + "' and complete_date< date'" + end_date + "'+1";
        pstmt = conn.prepareStatement(count_sql);
        rs = pstmt.executeQuery();
        if(rs.next()) {
            all_complete_count = rs.getInt("all_complete_count");
            all_complete_sum = rs.getDouble("all_complete_sum");
        }    
				
		//取网站入会和线下入会人数
		int web_join_count = 0, crm_join_count = 0;
		count_sql = " select sum(decode(msc_code,'NETSHOP',1,0)) web_join_count, sum(decode(msc_code,'NETSHOP',0,1)) crm_join_count "
            + " from mbr_members where create_date>= date'" + begin_date + "' and create_date< date'" + end_date + "'+1";
		pstmt = conn.prepareStatement(count_sql);
		rs = pstmt.executeQuery();
		if(rs.next()) {
		    web_join_count = rs.getInt("web_join_count");
		    crm_join_count = rs.getInt("crm_join_count");
		}
		
				
	    request.getSession().setAttribute("excel_name","商品缺货排行");
        request.getSession().setAttribute("excel_title","货号,颜色,尺寸,名称,类别,现有库存,缺货数量");
		request.getSession().setAttribute("excel_sql",count_sql);
%>
	<tr bgcolor="#FFFFFF">
	    <td>总订单量</td>
	    <td><%= total_count%></td>
	    <td>平邮</td>
	    <td><%= post_count %></td>		
    </tr>
    <tr bgcolor="#FFFFFF">
	    <td>总订单金额</td>
	    <td><%= total_sum%></td>
	    <td>EMS</td>
	    <td><%= ems_count %></td>	
    </tr>	
   <tr bgcolor="#FFFFFF">
    	<td>话务订单量</td>
	    <td><%= tel_count%></td>
	    <td>快递</td>
	    <td><%= express_count %></td>		
    </tr>
    <tr bgcolor="#FFFFFF">
	    <td>话务订单比例</td>
	    <td><%= tel_count*100/total_count %>%</td>
	    <td>商品订购量</td>
	    <td><%= goods_total%></td>		
    </tr>
    <tr bgcolor="#FFFFFF">
	    <td>话务订单金额</td>
	    <td><%= tel_sum %></td>
	    <td>平均包裹数</td>
	    <td><%= goods_avg%></td>		
    </tr>  
    <tr bgcolor="#FFFFFF">
	    <td>网上订单量</td>
	    <td><%= web_count %></td>
	    <td>缺货量</td>
	    <td><%= goods_short_count%></td>		
    </tr>    
    <tr bgcolor="#FFFFFF">
	    <td>网上订单比例</td>
	    <td><%= web_count*100/total_count %>%</td>
	    <td>缺货金额</td>
	    <td><%= goods_short_sum%></td>		
    </tr>   
    <tr bgcolor="#FFFFFF">
	    <td>网上订单金额</td>
	    <td><%= web_sum %></td>
	    <td>欠款订单量</td>
	    <td><%= money_short_count%></td>		
    </tr> 
   <tr bgcolor="#FFFFFF">
	    <td>换货订单量</td>
	    <td><%= order_change_count%></td>
	    <td>欠款订单比例</td>
	    <td><%=money_short_count*100/total_count%>%</td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>换货订单比例</td>
	    <td><%= order_change_count*100/total_count%>%</td>
	    <td>欠款订单金额</td>
	    <td><%= money_short_sum%></td>		
    </tr>
    <tr bgcolor="#FFFFFF">
	    <td>换货订单金额</td>
	    <td><%= order_change_sum %></td>
	    <td>在线支付</td>
	    <td><%= web_pay_count %></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>退货订单量</td>
	    <td><%= order_return_count%></td>
	    <td>银行电汇</td>
	    <td><%= bank_pay_count%></td>		
    </tr>  
    <tr bgcolor="#FFFFFF">
	    <td>退货订单金额</td>
	    <td><%= order_return_sum%></td>
	    <td>邮局汇款</td>
	    <td><%=post_pay_count%></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>作废订单量</td>
	    <td><%=order_cancel_count%></td>
	    <td>信用卡支付</td>
	    <td><%=union_pay_count %></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>作废金额</td>
	    <td><%=order_cancel_sum%></td>
	    <td>首次购买顾客数</td>
	    <td><%=first_buy_count %></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>作废比例</td>
	    <td><%=order_cancel_count*100/total_count%>%</td>
	    <td>网站新注册会员</td>
	    <td><%= web_join_count%></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>当日订单发包量</td>
	    <td><%=order_complete_count%></td>
	    <td>电话注册新会员</td>
	    <td><%=crm_join_count%></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>当日订单发包率</td>
	    <td><%=order_complete_count*100/total_count%>%</td>
	    <td></td>
	    <td></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>当日总发包量</td>
	    <td><%=all_complete_count%></td>
	    <td></td>
	    <td></td>		
    </tr> 
    <tr bgcolor="#FFFFFF">
	    <td>当日总发包金额</td>
	    <td><%=all_complete_sum%></td>
	    <td></td>
	    <td></td>		
    </tr> 

</table>
<!--
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td  align="center">
		
		<a href="excel_export.jsp" target=_blank>
		导出记录</a>
		
	</td>
</tr>
</table>
-->
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
