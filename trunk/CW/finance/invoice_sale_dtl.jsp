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
	       int cr_page=1;					 			 						 						 
        Connection conn=null;

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
	
	sql=" SELECT distinct c.item_code,c.name";
	sql+=" FROM FIN_STOCK_DETAIL a";
	sql+=" INNER JOIN PRD_ITEMS c ON  a.ITEM_ID=c.ITEM_ID";
	sql+=" WHERE DOC_TYPE <>'03' AND  OPERATION_DATE >=DATE '"+startDate+"' AND OPERATION_DATE <DATE '"+endDate+"'+1";
	sql+=" and c.item_type="+type;
	//System.out.println(sql);
	//************************************************* 分页代码 **********************************************************
	PageAttribute pageUtil = new PageAttribute(20);
	if(c_page.length()>0){
	cr_page=Integer.parseInt(c_page);	
		finCol=fsd.fin_stock_page(conn,sql,(cr_page-1)*20,cr_page*20);
	}else{
		finCol=fsd.fin_stock_page(conn,sql,pageUtil.getFrom(),pageUtil.getTo());
	}
	
	pageUtil.setRecordCount(FinSalesDAO.queryListCount(conn,sql));
	
	rst=fsd.fin_stock_item_list(conn,type,startDate,endDate);	 
        int pageno=pageUtil.getPageNo();
        
%>	
<body>
<table width="2350" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="left" >
    <td  bgcolor="#FFFFFF">
    共<font color="red"><%=pageUtil.getRecordCount()%></font>条记录，<font color="red"><%=pageUtil.getPageCount()%></font>页&nbsp;当前第<font color="red"><%=cr_page%></font>页&nbsp;
    <%if(cr_page>1){%>
    <a href="invoice_sale_dtl.jsp?page=<%=pageno%>&type=<%=type%>&startDate=<%=startDate%>&endDate=<%=endDate%>">首页</a>&nbsp;
    <a href="invoice_sale_dtl.jsp?page=<%=cr_page-1%>&type=<%=type%>&startDate=<%=startDate%>&endDate=<%=endDate%>">上一页</a>
    <%}%>
    <%if(cr_page<pageUtil.getPageCount()){%>
    <a href="invoice_sale_dtl.jsp?page=<%=cr_page+1%>&type=<%=type%>&startDate=<%=startDate%>&endDate=<%=endDate%>">下一页</a>&nbsp;
    <a href="invoice_sale_dtl.jsp?page=<%=pageUtil.getPageCount()%>&type=<%=type%>&startDate=<%=startDate%>&endDate=<%=endDate%>">末页</a>
    <%}%>
    </td>
  </tr>
 </table>
<table width="2350" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="center" >
    <td rowspan="3" width="100" bgcolor="#FFFFFF">产品名称</td>
    <td rowspan="3" width="100" bgcolor="#FFFFFF">货号</td>
    <td colspan="2" rowspan="2" width="150" bgcolor="#FFFFFF">正常销售</td>
    <td colspan="2" rowspan="2" width="150" bgcolor="#FFFFFF">正常销售退回</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">领用产品出库</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">借用产品出库</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">借用产品入库</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">扣单上架入库</td>
    <td  rowspan="2" width="150"   bgcolor="#FFFFFF">库存零成本调整</td>    
    <td colspan="4"  width="300"   bgcolor="#FFFFFF">盘点</td>
    <td colspan="14"  width="900"  align="center" bgcolor="#FFFFFF">各项差异</td>
  </tr>
  <tr  align="center" bgcolor="#FFFFFF">
  <td colspan="2" width="150" align="center">盘赢</td>
  <td colspan="2" width="150" align="center">盘亏</td>
    <td colspan="2" width="150" align="center">门店调拨入库</td>
    <td colspan="2" width="150" align="center">库存调整</td>
    <td colspan="2" width="150" align="center">扣单上架</td>
    <td colspan="2" width="150" align="center">退货上架</td>
    <td colspan="2" width="150" align="center">退货破损</td>
    <td colspan="2" width="150" align="center">换货破损</td>
    <td colspan="2" width="150" align="center">手工调库存</td>
    
  </tr>
  <tr  align="center" bgcolor="#FFFFFF" >

    <td>数量</td>
    <td>金额</td>  
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>
    <td>数量</td>
    <td>金额</td>     
    <td>数量</td>
    <td>金额</td>   
    <td>数量</td>
    <td>金额</td>  
    <td>数量</td>
    <td>金额</td>        
  </tr>
  <%


	java.util.Iterator it=finCol.iterator();
	while(it.hasNext()){
	info=(FinSales)it.next();
	String item_code=info.getItem_code();							
  %>
  
  <tr  align="center" bgcolor="#FFFFFF">
    <td width="240" > <%=info.getItem_name()%></td>
    <td width="60"><%=item_code%></td>
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"11")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"11")%></td>
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"05")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"05")%></td>    
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"14")%></td> 
        <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"14")%></td>
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"06")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"06")%></td>       

    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"15")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"15")%></td>
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"03")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"03")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"71")%></td>
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"31")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"31")%></td>  
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"32")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"32")%></td>       
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"62")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"62")%></td>   
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"20")%></td>
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"20")%></td>
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"64")%></td>  
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"64")%></td>      
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"63")%></td>  
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"63")%></td>  
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"66")%></td>  
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"66")%></td>  
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"67")%></td>  
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"67")%></td>    
    <td width="75" align="right"><%=fsd.getFin_qty(rst,item_code,"70")%></td>  
    <td width="75" align="right"><%=fsd.getFin_amt(rst,item_code,"70")%></td>      
  </tr>
 
 <%}%>
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
