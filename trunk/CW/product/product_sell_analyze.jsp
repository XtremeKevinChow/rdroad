<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%
DecimalFormat myformat = new DecimalFormat("#");
      String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag;
      String pricelist_id=request.getParameter("pricelist_id");
      pricelist_id=(pricelist_id==null)?"":pricelist_id;
      String release_date=request.getParameter("release_date");
      release_date=(release_date==null)?"":release_date;      
      
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
<script language="JavaScript" src="../crmjsp/calendar.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
function query_f() {

	var sdate = document.form.release_date.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	if(sdate==null){
	alert('请按格式填写日期!');
	document.form.release_date.select();
	return false;
	}



	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">网下产品加印分析</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="0" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td>日期：</td>
		<td bgcolor="#FFFFFF"><input type=text name="release_date" size="25" value="<%=release_date%>">
		<a href="javascript:calendar(form.release_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(格式:YYYY-MM-DD)
		</td>
		<td>当前有效目录：</td>
		<td  bgcolor="#FFFFFF">
		<select name="pricelist_id">
		<%
       		sql=" SELECT * from prd_pricelists where status=100 and price_type_id in (3,5) and sysdate>=effect_date and sysdate<=expired_date+1";
      			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){	
			String name=rs.getString("name");	
			String id=rs.getString("id");			
		%>
		<option value="<%=id%>" <%if(pricelist_id.equals(id)){%>selected<%}%>><%=name%></option>
		<%}%>
		</select>
		</td>			
	
		
	</tr>	
	<tr>	
		<td colspan="4" align="center" bgcolor="#FFFFFF">
		<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">
		<input type="hidden" name="tag" value="1">
		</td>						
	</tr>	
		
</table>
<%if(tag.equals("1")){%>
<br>
<table width="100" align="center" border=0 cellspacing=1 cellpadding=2 class="OraTableRowHeader" noWrap >
	<tr>

      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">货号</th>
      <th width="220"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">产品名称</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">昨日冻结数量</th>
       <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">总冻结数量</th>
     <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">已下单未冻结</th>      
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">库存数量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">在途数量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2" >后台老会员销量</th> 
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2" >后台新会员销量</th>       
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  colspan="2" >昨日销量</th>   
   
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  colspan="2">5天平均销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  colspan="2">10天平均销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  colspan="2">15天平均销量</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">库存支持天数</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">总库存支持天数A</th>
      <th width="60"  bgcolor="#FFFFFF" noWrap align=middle  rowspan="2">总库存支持天数B</th>
</tr>
	<tr align=center>		

		<td bgcolor="#FFFFFF" noWrap align="right">前台</td>
		<td bgcolor="#FFFFFF" noWrap align="right">后台</td>		
		<td bgcolor="#FFFFFF" noWrap align="right">前台</td>
		<td bgcolor="#FFFFFF" noWrap align="right">后台</td>
		<td bgcolor="#FFFFFF" noWrap align="right">前台</td>
		<td bgcolor="#FFFFFF" noWrap align="right">后台</td>
		<td bgcolor="#FFFFFF" noWrap align="right">前台</td>
		<td bgcolor="#FFFFFF" noWrap align="right">后台</td>		



	</tr>
<%

    
    
    
        sql=" select a.*,c.name,c.item_code as code from prd_sell_analyze1  a"; 
	sql+=" inner join prd_pricelists b on a.pricelist_id=b.id ";
	sql+=" inner join prd_items c on a.item_id=c.item_id";
	sql+=" inner join providers d on c.supplier_id=d.id";
	sql+=" where b.id="+pricelist_id+" and price_type_id in (3,5) "; 
	sql+=" and to_char(a.release_date,'yyyy-mm-dd')='"+release_date+"'";
	sql+=" order by   (old_sell_quantity_b+new_sell_quantity_b) desc ";
        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	while(rs.next()){	
		
	String name=rs.getString("name");
	String item_code=rs.getString("code");
	
	int all_sell_quantity=rs.getInt("old_sell_quantity")+rs.getInt("new_sell_quantity");//昨日销售数量_前台
	int all_sell_quantity_b=rs.getInt("old_sell_quantity_b")+rs.getInt("new_sell_quantity_b");//昨日销售数量后台
	int frozen_quantity=rs.getInt("frozen_quantity");//冻结数量
	int frozen_quantity_daily=rs.getInt("frozen_quantity_daily");//昨日冻结数量
	int nofrozen_in_order_qty=rs.getInt("nofrozen_in_order_qty");//已下单未冻结数量
	int available_quantity=rs.getInt("available_quantity");//库存数量
	int afloat_quantity=rs.getInt("afloat_quantity");//在途数量
	int old_sell_quantity_b=rs.getInt("old_sell_quantity_b");//后台老会员销量
	int new_sell_quantity_b=rs.getInt("new_sell_quantity_b");//前台老会员销量
	
	double sell_last_five=rs.getDouble("sell_last_five");
	double sell_last_ten=rs.getDouble("sell_last_ten");
	double sell_last_fifteen=rs.getDouble("sell_last_fifteen");
	double sell_last_five_b=rs.getDouble("sell_last_five_b");
	double sell_last_ten_b=rs.getDouble("sell_last_ten_b");
	double sell_last_fifteen_b=rs.getDouble("sell_last_fifteen_b");
        String stock_day="";
        String all_stock_day_a="";
        String all_stock_day_b="";
        if(sell_last_ten>0){
	stock_day=myformat.format((available_quantity-frozen_quantity_daily)/sell_last_ten);
	}
	if(sell_last_ten>0){
	all_stock_day_a=myformat.format((available_quantity+afloat_quantity-frozen_quantity_daily)/sell_last_ten);
	}
	if(all_sell_quantity>0){
	all_stock_day_b=myformat.format((available_quantity+afloat_quantity-frozen_quantity_daily)/all_sell_quantity);
	}		

%>	
	<tr align=center>

		
		<td bgcolor="#FFFFFF" noWrap align="left"><%=item_code%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=name%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=frozen_quantity_daily%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=frozen_quantity%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=nofrozen_in_order_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=available_quantity%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=afloat_quantity%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=old_sell_quantity_b%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=new_sell_quantity_b%></td>		
		

		<td bgcolor="#FFFFFF" noWrap align="right"><%=all_sell_quantity%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=all_sell_quantity_b%></td>				
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_five%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_five_b%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_ten%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_ten_b%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_fifteen%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_fifteen_b%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=stock_day%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=all_stock_day_a%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=all_stock_day_b%></td>

	</tr>
<%
}
%>
	<tr align=center>

		<td bgcolor="#FFFFFF" noWrap colspan="20"><a href="product_sell_analyze_execl.jsp?release_date=<%=release_date%>&pricelist_id=<%=pricelist_id%>">生成execl文件</a></td>
	
	</tr>
</table>
<%}%>
</form>
</body>
</html>
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