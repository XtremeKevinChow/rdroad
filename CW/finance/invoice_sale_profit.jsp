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
DecimalFormat myformat = new DecimalFormat("###,###.00");
			String startDate=request.getParameter("startDate");
						 startDate=(startDate==null)?"":startDate.trim();
			String endDate=request.getParameter("endDate");
						 endDate=(endDate==null)?"":endDate.trim();
			String type=request.getParameter("type");
						 type=(type==null)?"":type.trim();	
						 
			String tag=request.getParameter("tag");
						 tag=(tag==null)?"":tag.trim();						 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function checkAll(bln, type) {
	
	var len = DataTable.rows.length;
	for (var i = 1; i < len; i ++) {
		var row = DataTable.rows(i);
		if(bln) {
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}


function query_f() {


	
if (document.forms[0].startDate.value == ""&&document.forms[0].endDate.value != "")
	{
		alert("开始日期不能为空");
		document.forms[0].startDate.focus();
		return;
	}

	if (document.forms[0].endDate.value == ""&&document.forms[0].startDate.value != "")
	{
		alert("结束日期不能为空");
		document.forms[0].endDate.focus();
		return;
	}
	if(document.forms[0].endDate.value != ""&&document.forms[0].startDate.value != ""){
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return;
		 }	
	 
	}


	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">销售毛利发票汇总</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
			开始日期：
			<input type="text" name="startDate" size="10"  value="<%=startDate%>">
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>
			结束日期：
			<input type="text" name="endDate" size="10"  value="<%=endDate%>">
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		</tr>

		<tr>
		<td>
		产品类型：
			<select name="type">
			 <option value="">--所有--</option>
			 <option value="1"<%if(type.equals("1")){%>selected<%}%>>图书</option>
			 <option value="2"<%if(type.equals("2")){%>selected<%}%>>影视</option>
			 <option value="3"<%if(type.equals("3")){%>selected<%}%>>音乐</option>
			 <option value="4"<%if(type.equals("4")){%>selected<%}%>>游戏软件</option>
			 <option value="5"<%if(type.equals("5")){%>selected<%}%>>礼品</option>
			 <option value="6"<%if(type.equals("6")){%>selected<%}%>>其他</option>
			</select>
		</td>		
		<td>
			<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">
		</td>
		</tr>		
</table>

<br>
<%
if(tag.length()>0){


%>		
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>不含税销售金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售成本</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售毛利</th>
	</tr>


<%
		try{
		  conn = DBManager.getConnection();
		  int total_op_qty=0;
		  double total_fact_amt=0;
		  double total_pre_amt=0;
		  double total_pre_amt2=0;
		  double total_chayi=0;
			sql=" select b.item_type,";
			sql+=" sum(a.pre_price) as pre_price ,sum(a.fact_price) as fact_price,";
			sql+=" sum(a.fact_amt) as fact_amt,sum(a.pre_amt) as pre_amt,sum(a.op_qty) as op_qty ";
			sql+=" from magic.fin_stock_detail a"; 
			sql+=" inner join prd_items b on"; 
			sql+=" b.item_id=a.item_id";
			sql+=" where a.doc_type=2 ";
			sql+=" and a.operation_date>=date'"+startDate+"' and a.operation_date<date'"+endDate+"'+1";
			if(type.length()>0){
			sql+=" and b.item_type="+type;
			}
			sql+=" group by b.item_type order by item_type";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//out.println(sql);			
			while(rs.next()){	

			String all_item_type=rs.getString("item_type");
			int all_op_qty=rs.getInt("op_qty");			
      double all_fact_amt=rs.getDouble("pre_amt");//销售金额
      double all_pre_amt=0;//不含税销售金额
      double all_pre_amt2=rs.getDouble("fact_amt");//销售成本
      
      if(all_item_type.equals("1")||all_item_type.equals("2")||all_item_type.equals("3")||all_item_type.equals("4")){
      		all_pre_amt=all_fact_amt/1.13;
      }else if(all_item_type.equals("6")) {
      		all_pre_amt=all_fact_amt;
      }else{
      		all_pre_amt=all_fact_amt/1.17;
      }
      
      double all_chayi=all_pre_amt-all_pre_amt2;			
      
      total_op_qty=total_op_qty+all_op_qty;			
      total_fact_amt=total_fact_amt+all_fact_amt;
      total_pre_amt=total_pre_amt+all_pre_amt;
      total_pre_amt2=total_pre_amt2+all_pre_amt2;
      total_chayi=total_chayi+all_chayi;		      
	
	%>
	<tr>
    <td bgcolor="#FFFFFF" noWrap align="center">
    <a href="invoice_sale_profit_dtl.jsp?type=<%=all_item_type%>&startDate=<%=startDate%>&endDate=<%=endDate%>">
		<%
		if(all_item_type.equals("1")){
		   out.println("图书");
		}
		if(all_item_type.equals("2")){
		   out.println("影视");
		}
		if(all_item_type.equals("3")){
		   out.println("音乐");
		}
		if(all_item_type.equals("4")){
		   out.println("游戏软件");
		}
		if(all_item_type.equals("5")){
		   out.println("礼品");
		}
		if(all_item_type.equals("6")){
		   out.println("其他");
		}
										
		%>   
		</a> 
    </td>    
		<td bgcolor="#FFFFFF" noWrap align="right"><%=all_op_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_fact_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_pre_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_pre_amt2)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_chayi)%></td>	
	</tr>	
	<%}%>
	<tr>
	  <td bgcolor="#FFFFFF" noWrap align="center">合计</td>    
		<td bgcolor="#FFFFFF" noWrap align="right"><%=total_op_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(total_fact_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(total_pre_amt)%></td>
		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(total_pre_amt2)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(total_chayi)%></td>	
	</tr>	
	<tr>
	  <td bgcolor="#FFFFFF" noWrap align="center" colspan="6"><a href="invoice_sale_profit_excel.jsp?startDate=<%=startDate%>&endDate=<%=endDate%>&type=<%=type%>">导出excel</a></td>    
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
%>
<%}%>
</form>
</body>
</html>
