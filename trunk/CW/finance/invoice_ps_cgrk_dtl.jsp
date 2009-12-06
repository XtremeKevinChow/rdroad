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



<br>
		
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>货号</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>单价</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算成本</th>
	</tr>


<%
	try{
	  conn = DBManager.getConnection();
	  int total_pur_qty=0;
	  double total_pur_amt=0;
	  double total_pur_price=0;
/*
		sql=" select b.item_code,b.name,sum(c.pur_qty) as pur_qty,sum(c.pur_amt) as pur_amt from prd_items b ";
		sql+=" inner join  fin_ps_dtl c on b.item_id=c.item_id ";
		sql+=" inner join  fin_ps_mst d on c.ps_id=d.ps_id ";
		sql+=" where d.purchasedate>=date'"+startDate+"' and d.purchasedate<date'"+endDate+"'+1 and b.item_type="+type;
	*/
	sql="SELECT d.item_code,d.name,sum(A.OP_QTY) as pur_qty,sum(A.PRE_PRICE) as PRE_PRICE,sum(A.PRE_AMT) as PRE_AMT";
	sql+=" FROM magic.fin_stock_detail A";
	sql+=" INNER JOIN magic.FIN_PS_DTL C ON TO_NUMBER(A.RES_NO)=C.PS_DTL_ID";
	sql+=" INNER JOIN magic.FIN_PS_MST B ON C.PS_ID=B.PS_ID";
	sql+=" INNER JOIN prd_items d ON a.item_id=d.item_id and  d.item_type="+type;
	sql+=" WHERE B.PUR_TYPE<>2 AND A.IS_TEMP='Y' ";
	sql+=" AND OPERATION_DATE>=DATE'"+startDate+"' AND OPERATION_DATE<DATE'"+endDate+"'+1";
		if(res_no.length()>0){
		sql+=" and b.res_no='"+res_no+"'";
		}	
				
		sql+=" group by d.item_code,d.name";
	//System.out.println(sql);
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		System.out.println(sql);			
		while(rs.next()){	
		String item_code=rs.getString("item_code");
		String name=rs.getString("name");
		int pur_qty=rs.getInt("pur_qty");		
	        double pur_amt=rs.getDouble("PRE_AMT");//预算变成本
	        double pur_price=0;
	        
	        if(pur_qty!=0){
	           pur_price=pur_amt/pur_qty;
	        }	 
	              	      		   
	        total_pur_qty=total_pur_qty+pur_qty;			
	        total_pur_amt=total_pur_amt+pur_amt;
		      
	
	%>
	<tr>
    		<td bgcolor="#FFFFFF" noWrap align="center"><%=name%></td> 
    		<td bgcolor="#FFFFFF" noWrap align="center"><%=item_code%></td>    
		<td bgcolor="#FFFFFF" noWrap align="right"><%=pur_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(pur_price)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(pur_amt)%></td>
	</tr>	
	<%}%>
	<tr>
	  <td bgcolor="#FFFFFF" noWrap colspan="2" align="center">合计</td>    
		<td bgcolor="#FFFFFF" noWrap align="right"><%=total_pur_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right">&nbsp;</td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(total_pur_amt)%></td>
	
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

</form>
</body>
</html>
