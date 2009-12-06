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
					alert('请按格式yyyy-mm-dd填写日期,并且注意你的日期是否正确!');
					document.form.begin_date.focus();
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
      		-&gt; </font><font color="838383">商品缺货排行</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="sale_goods_short_stats.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td width="10%">日期</td>
		<td width="20%"><input type="text" name="begin_date" value="<%=begin_date%>">
		<a href="javascript:show_calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
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
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>货号</th>
		<th width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>颜色</th>		
		<th width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>尺寸</th>
		<th width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>名称</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>类别</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>现有库存</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>缺货数量</th>			
	</tr>
<%
		if(begin_date.length()>0){
		   condition+=" and t1.release_date < 1+date'" + begin_date + "'";
		}
		String count_sql=			
            " select t3.itm_code,t3.COLOR_CODE,t3.SIZE_CODE,t4.itm_name,"
            +" t5.catalog_name,t7.use_qty,sum(t2.quantity) as sum_qty"
            +" from ord_headers t1 join ord_lines t2 on t1.id=t2.order_id"
            +" join prd_item_sku t3 on t2.sku_id = t3.SKU_ID"
            +" join prd_item t4 on t3.itm_code = t4.ITM_CODE"
            +" join prd_item_category t5 on t4.CATEGORY_ID = t5.CATALOG_ID"
            +" join sto_stock t7 on t3.sku_id = t7.sku_id"
            +" where 1=1 " + condition
            +" and t1.status in (20,21) and t2.status in (5,20,21)"
            +" group by t3.itm_code,t3.COLOR_CODE,t3.SIZE_CODE,t4.itm_name,t5.CATALOG_NAME,t7.use_qty order by sum_qty desc";
		

        request.getSession().setAttribute("excel_name","商品缺货排行");
        request.getSession().setAttribute("excel_title","货号,颜色,尺寸,名称,类别,现有库存,缺货数量");
		request.getSession().setAttribute("excel_sql",count_sql);

		//out.println(count_sql);
			pstmt=conn.prepareStatement(count_sql);
			rs=pstmt.executeQuery();


			while(rs.next()){ 
				
%>
	<tr>
		<td bgcolor="#FFFFFF"><%=rs.getString("itm_code")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("COLOR_CODE")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("SIZE_CODE")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("itm_name")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getString("catalog_name")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getInt("use_qty")%></td>
		<td bgcolor="#FFFFFF"><%=rs.getInt("sum_qty")%></td>		
        
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
