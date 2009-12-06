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
		 
	String tag=request.getParameter("tag");
		 tag=(tag==null)?"":tag.trim();	
	String res_no=request.getParameter("res_no");
		 res_no=(res_no==null)?"":res_no.trim();			 					 			 						 						 
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
      		-&gt; </font><font color="838383">采购入库产品暂估成本汇总</font><font color="838383"> 
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
		入库单号：
		<input type="text" name="res_no" size="10"  value="<%=res_no%>">		

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
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算成本</th>
	</tr>


<%
	try{
	  conn = DBManager.getConnection();
	  int total_pur_qty=0;
	  double total_pur_amt=0;

sql="SELECT ";
sql+="e.id,sum(A.OP_QTY) as pur_qty,sum(A.PRE_AMT) as pur_amt ";
sql+="FROM magic.fin_stock_detail A ";
sql+="INNER JOIN   prd_items d on a.item_id=d.item_id ";
sql+="inner join s_item_type e on d.item_type=e.id ";
sql+="WHERE A.IS_TEMP='Y' and a.doc_type='01' ";
sql+="AND OPERATION_DATE >= date '" + startDate + "' ";
sql+=" AND OPERATION_DATE < date '" + endDate + "' + 1 ";
sql+="AND  ";
sql+="to_number(A.res_no) in ";
sql+="( ";
sql+="select ps_dtl_Id from magic.fin_ps_dtl a ";
sql+="inner join magic.fin_ps_mst b on a.ps_id=b.ps_id  ";
sql+="where 1=1 ";
sql+="and b.purchasedate >= date '" + startDate + "' ";
sql+="AND B.PURCHASEDATE < DATE '" + endDate + "'+1 ";
sql+=") ";
if(res_no.length()>0){
	sql+=" and b.res_no='"+res_no+"' ";
}	
sql+="group by e.id ";


//out.println(sql);
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
					
		while(rs.next()){	
	
		String item_type=rs.getString("id");
		int pur_qty=rs.getInt("pur_qty");			
	        double pur_amt=rs.getDouble("pur_amt");//预算变成本	      		   
	        total_pur_qty=total_pur_qty+pur_qty;			
	        total_pur_amt=total_pur_amt+pur_amt;
		      
	
	%>
	<tr>
    <td bgcolor="#FFFFFF" noWrap align="center">
    <a href="invoice_ps_cgrk_dtl.jsp?type=<%=item_type%>&startDate=<%=startDate%>&endDate=<%=endDate%>&res_no=<%=res_no%>">
		<%
		if(item_type.equals("1")){
		   out.println("图书");
		}
		if(item_type.equals("2")){
		   out.println("影视");
		}
		if(item_type.equals("3")){
		   out.println("音乐");
		}
		if(item_type.equals("4")){
		   out.println("游戏软件");
		}
		if(item_type.equals("5")){
		   out.println("礼品");
		}
		if(item_type.equals("6")){
		   out.println("其他");
		}
										
		%>   
		</a> 
    </td>    
		<td bgcolor="#FFFFFF" noWrap align="right"><%=pur_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(pur_amt)%></td>
	</tr>	
	<%}%>
	<tr>
	  <td bgcolor="#FFFFFF" noWrap align="center">合计</td>    
		<td bgcolor="#FFFFFF" noWrap align="right"><%=total_pur_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(total_pur_amt)%></td>
	
	</tr>	
	<tr>
	  <td bgcolor="#FFFFFF" noWrap align="center" colspan="3"><a href="invoice_ps_cgrk_excel.jsp?startDate=<%=startDate%>&endDate=<%=endDate%>&res_no=<%=res_no%>">导出excel</a></td>    
	
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
