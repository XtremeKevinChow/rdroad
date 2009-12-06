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


	
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>

		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售单价</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>不含税销售金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售成本</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售毛利</th>
	</tr>


<%
		try{
		  conn = DBManager.getConnection();
			sql=" select b.name,b.item_code,b.item_type,";
			sql+=" sum(a.pre_price) as pre_price ,sum(a.fact_price) as fact_price,";
			sql+=" sum(a.pre_price) as pre_price,sum(a.fact_amt) as fact_amt,sum(a.pre_amt) as pre_amt,sum(a.op_qty) as op_qty ";
			sql+=" from magic.fin_stock_detail a"; 
			sql+=" inner join prd_items b on"; 
			sql+=" b.item_id=a.item_id";
			sql+=" where a.doc_type=2 ";
			if(startDate.length()>0&&endDate.length()>0){
			sql+=" and a.operation_date>=date'"+startDate+"' and a.operation_date<date'"+endDate+"'+1";
			}
			if(type.length()>0){
			sql+=" and b.item_type="+type;
			}
			sql+=" group by b.name,b.item_code,b.item_type order by sum(a.op_qty) desc";

      //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();

      
%>	
	<tr>

		<%
		 double all_fact_amt=0;
		 double all_pre_amt2=0;
		 double all_pre_amt=0;
		 double all_chayi=0;
			while(rs.next()){	
			String item_name=rs.getString("name");
			String item_code=rs.getString("item_code");
			String item_type=rs.getString("item_type");
			String op_qty=rs.getString("op_qty").trim();
      double fact_amt=rs.getDouble("pre_amt");//销售金额
      double pre_amt=0;	 //不含税销售金额     
      double pre_amt2=rs.getDouble("fact_amt");//销售成本
	  if(item_type.equals("1")||item_type.equals("2")||item_type.equals("3")||item_type.equals("4")){
      		pre_amt=fact_amt/1.13;
      }else if(item_type.equals("6")) {
      		pre_amt=fact_amt;
      }else{
      		pre_amt=fact_amt/1.17;
      }  
            		
      int qty=Integer.parseInt(op_qty);		
      double pre_price=0;
      double fact_price=0;
      if(qty==0){
      pre_price=0;
      fact_price=0;
      }else{
     pre_price=fact_amt/qty;
      fact_price=pre_amt/qty;
      }
               
      double chayi=pre_amt-pre_amt2;
      all_fact_amt=all_fact_amt+fact_amt;
      all_pre_amt2=all_pre_amt2+pre_amt2;
      all_pre_amt=all_pre_amt+pre_amt;
      all_chayi=all_chayi+chayi;
      
      %>	
	<tr>

		<td bgcolor="#FFFFFF" noWrap align="center"><%=item_name%></td>
		<td bgcolor="#FFFFFF" noWrap align="center"><%=item_code%></td>
		<td bgcolor="#FFFFFF" noWrap align="center">
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
		</td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=op_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(pre_price)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(fact_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(pre_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(pre_amt2)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(chayi)%></td>
		
	</tr>			
		<%}%>
<tr>

		<td bgcolor="#FFFFFF" noWrap align="center"></td>
		<td bgcolor="#FFFFFF" noWrap align="center"></td>
		<td bgcolor="#FFFFFF" noWrap align="center">
		</td>
		<td bgcolor="#FFFFFF" noWrap align="center"></td>
		<td bgcolor="#FFFFFF" noWrap align="center"></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_fact_amt)%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_pre_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_pre_amt2)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_chayi)%></td>
		
	</tr>	

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


</body>
</html>
