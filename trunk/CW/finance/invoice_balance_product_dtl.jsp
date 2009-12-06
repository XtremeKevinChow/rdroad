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
			String item_type=request.getParameter("item_type");
				 
					 			 						 						 
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
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
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
<table width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td align="left">
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">发票结算汇总</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="99%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>



<br>
<table width="99%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>

		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>货号</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>发票日期</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>发票号</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算日期</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算单价</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算成本</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>成本差异</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>税金金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>应付金额</th>


	</tr>

<%

		try{
		 conn = DBManager.getConnection();

			sql="select a.*, b.item_code, b.name as item_name,d.name as item_type_name,c.createdate,c.invoice_date,c.fact_ap_code ";			
			sql+=" from fin_ap_dtl a inner join prd_items b on a.item_id = b.item_id ";
      sql+=" inner join fin_ap_mst c on c.ap_id = a.ap_id";
      sql+=" inner join s_item_type d on b.item_type=d.id "; 
      sql+=" where c.status=3 ";
      if(startDate.length()>0&&endDate.length()>0){
            sql+=" and c.createdate >= date'"+startDate+"'";
            sql+=" and c.createdate < date'"+endDate+"'+1";
      }

      if(item_type.length()>0){
      			sql+=" and d.id ="+item_type;
      }
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){	
					String item_code=rs.getString("item_code");
					String item_name=rs.getString("item_name");
					String item_type_name=rs.getString("item_type_name");
					String invoice_date=rs.getString("invoice_date").substring(0,10);
					int qty=rs.getInt("qty");
					String fact_ap_code=rs.getString("fact_ap_code");
					String createdate=rs.getString("createdate").substring(0,10);
					
					double purPrice=Arith.round(rs.getDouble("pur_Price"),2);//预算单价
				  double purPrice_amt=Arith.round(purPrice*qty,2);//预算成本
					double apPrice=Arith.round(rs.getDouble("ap_Price"),2);//结算单价
					double amt=Arith.round(rs.getDouble("amt"),2);//结算金额
					double tax=Arith.round(rs.getDouble("tax"),2);					
					double totalAmt=Arith.round(rs.getDouble("total_Amt"),2);
					double disAmt=Arith.round(rs.getDouble("dis_Amt"),2);//成本差异
					double final_amt=Arith.round(rs.getDouble("total_amt"),2);//应付金额=结算+税后
					double taxAmt=Arith.round(final_amt-amt,2);//税金金额					
					
%>	
	<tr align=center>

		<td bgcolor="#FFFFFF" noWrap align="left"><%=item_name%></td>
		<td bgcolor="#FFFFFF" noWrap><%=item_code%></td>
		<td bgcolor="#FFFFFF" noWrap><%=item_type_name%></td>
		<td bgcolor="#FFFFFF" noWrap><%=invoice_date%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=fact_ap_code%></td>	
		<td bgcolor="#FFFFFF" noWrap align="right"><%=createdate%></td>	
		<td bgcolor="#FFFFFF" noWrap align="right"><%=qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(purPrice)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(purPrice_amt)%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(disAmt)%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(taxAmt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(final_amt)%></td>
</tr>
<%}%>



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
	
</table>

</body>
</html>
