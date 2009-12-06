<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ page import="com.magic.crm.finance.entity.FinSales,com.magic.crm.finance.dao.*,com.magic.crm.common.*"%>
<%
DecimalFormat myformat = new DecimalFormat("###,###.00");

	String startDate=request.getParameter("startDate");
		startDate=(startDate==null)?"":startDate.trim();
	String endDate=request.getParameter("endDate");
		endDate=(endDate==null)?"":endDate.trim();
	String type=request.getParameter("type");
	       type=(type==null)?"":type.trim();
	String c_page=request.getParameter("page");
	       c_page=(c_page==null)?"":c_page.trim();
	String qty_type=request.getParameter("qty_type");
	       qty_type=(qty_type==null)?"0":qty_type.trim();	       	
	
	       int cr_page=1;					 			 						 						 
        Connection conn=null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
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
      		-&gt; </font><font color="838383">销售成本结算明细及汇总表</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>


<br>
<%
	try{
  	conn = DBManager.getConnection();  
	Collection rst = new ArrayList();
	FinSalesDAO fsd=new FinSalesDAO();
	ArrayList finCol = new ArrayList();
	FinSales info=new FinSales();
	

	//************************************************* 分页代码 **********************************************************

	
	rst=fsd.fin_stock_item_list(conn,type,startDate,endDate);	 
        
        
%>	
<body>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="left" >
    <td>开始日期</td><td  bgcolor="#FFFFFF"><%=startDate%></td>
    <td>结束日期</td><td  bgcolor="#FFFFFF"><%=endDate%></td>
    <td>类型</td><td  bgcolor="#FFFFFF">
<font color="red">
 <%
 if(qty_type.equals("14")){
  out.println("领用出库");
}
if(qty_type.equals("15")){
  out.println("借用出库");
}
if(qty_type.equals("03")){
  out.println("借用入库");
}
if(qty_type.equals("06")){
  out.println("扣单上架入库");
}
if(qty_type.equals("31")){
  out.println("盘赢");
}
if(qty_type.equals("32")){
  out.println("盘亏");
}
if(qty_type.equals("62")){
  out.println("门店调拨入库");
}
if(qty_type.equals("20")){
  out.println("库存调整");
}
if(qty_type.equals("64")){
  out.println("扣单上架差异");
}
if(qty_type.equals("63")){
  out.println("退货上架差异");
}
if(qty_type.equals("66")){
  out.println("退货破损");
}
if(qty_type.equals("67")){
  out.println("换货破损");
}
if(qty_type.equals("70")){
  out.println("手工调库存");
}

 %>
 </font>    
    </td>
  </tr>
 </table>

 <br>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="center" >
    <td  width="300"  bgcolor="#FFFFFF">产品名称</td>
    <td  bgcolor="#FFFFFF">货号</td>
    <td  bgcolor="#FFFFFF">数量</td>
    <td  bgcolor="#FFFFFF">金额</td>

    
  </tr>
  <%

	sql=" SELECT distinct c.item_code,c.name";
	sql+=" FROM FIN_STOCK_DETAIL a";
	sql+=" INNER JOIN PRD_ITEMS c ON  a.ITEM_ID=c.ITEM_ID";
	sql+=" WHERE DOC_TYPE <>'03' AND  OPERATION_DATE >=DATE '"+startDate+"' AND OPERATION_DATE <DATE '"+endDate+"'+1";
	sql+=" and c.item_type="+type;
	//System.out.println(sql);

	 pstmt = conn.prepareStatement(sql);
	rs = pstmt.executeQuery();
	int qty=0;
	double amt=0;
	int sum_qty=0;
	double sum_amt=0;	
	while (rs.next()) {

	    String rs_item_name=rs.getString("name");
	    String rs_item_code=rs.getString("item_code");
            qty=fsd.getFin_qty(rst,rs_item_code,qty_type);
            amt=fsd.getFin_amt(rst,rs_item_code,qty_type);
            sum_qty=sum_qty+qty;
            sum_amt=sum_amt+amt;
	if(qty!=0||amt!=0){						
  %>
  
  <tr   bgcolor="#FFFFFF">
    <td width="300" align="left"> <%=rs_item_name%></td>
    <td width="100" align="left"><%=rs_item_code%></td>
   
    <td width="" align="right"><%=fsd.getFin_qty(rst,rs_item_code,qty_type)%></td>  
    <td width="" align="right"><%=fsd.getFin_amt(rst,rs_item_code,qty_type)%></td>      
  </tr>
 <%}%>
 <%}%>
  <tr   bgcolor="#FFFFFF">
    <td width="300" align="left">&nbsp;</td>
    <td width="100" align="right">合计</td>
   
    <td width="" align="right"><%=sum_qty%></td>  
    <td width="" align="right"><%=myformat.format(sum_amt)%></td>      
  </tr> 
</table>





<%
		} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {

			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
%>

</form>
</body>
</html>
