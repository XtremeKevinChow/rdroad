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

			String pid=request.getParameter("pid");
						 pid=(pid==null)?"0":pid.trim();
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

function query_f() {


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
      		-&gt; </font><font color="838383">库存商品台账明细及汇总</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<%

		try{
		  conn = DBManager.getConnection();


%>	
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
		日期：
			<select name="pid">
			<option value="0">--所有--</option>
			<%
		  sql=" select * from fin_period order by pid";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();	
			while(rs.next()){		
			int rs_pid=	rs.getInt("pid");	  
			String b_date=rs.getString("prd_year");
			String e_date=rs.getString("prd_month");	  
			%>
			 
			 <option value="<%=rs_pid%>"<%if(rs_pid==Integer.parseInt(pid)){%>selected<%}%>><%=b_date%>年<%=e_date%>月</option>
     <%}%>
			</select>		
		</td>
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
		</tr>

		<tr>
	
		<td colspan="2">
			<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">
		</td>
		</tr>		
</table>

<br>
<%
if(tag.length()>0){
%>	
<table width="750" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
  <tr>
    <th  rowspan="2" width="150" align=middle bgcolor="#FFFFFF">产品类型</th>
    <th colspan="2" width="150" align=middle bgcolor="#FFFFFF">期初数据</th>
    <th colspan="2" width="150" align=middle bgcolor="#FFFFFF">本期入库</th>
    <th colspan="2" width="150" align=middle bgcolor="#FFFFFF">本期出库</th>

    <th colspan="2" width="150"  align=middle bgcolor="#FFFFFF">期末结存</th>            
  </tr>
  <tr align="center"  bgcolor="#FFFFFF">
    <td width="75"  bgcolor="#FFFFFF">数量</td>
    <td width="75"  bgcolor="#FFFFFF">金额</td>
    <td width="75"  bgcolor="#FFFFFF">数量</td>
    <td width="75"  bgcolor="#FFFFFF">金额</td>
    <td width="75"  bgcolor="#FFFFFF">数量</td>
    <td width="75"  bgcolor="#FFFFFF">金额</td>     
    <td width="75"   bgcolor="#FFFFFF">数量</td>
    <td width="75"   bgcolor="#FFFFFF">金额</td>      
  
  </tr>
  

  <%
			sql="select b.item_type,c.name,";
			sql+=" sum(a.first_amt) as first_amt,sum(a.first_qty) as first_qty,";
			sql+=" sum(a.in_qty) as in_qty,sum(a.out_qty) as out_qty,sum(a.last_qty) as last_qty,";
			sql+=" sum(a.in_amt) as in_amt,sum(a.out_amt) as out_amt,sum(a.last_amt) as last_amt,";
			sql+=" sum(a.out_dis_amt) as out_dis_amt ";			
			sql+=" from fin_stock_mst a ";
			sql+=" inner join prd_items b on ";
			sql+=" a.item_id=b.item_id";
			sql+=" inner join s_item_type c on ";
			sql+=" b.item_type=c.id ";			
			if(Integer.parseInt(pid)>0){
			     sql+=" and a.pid="+pid;
			}
			if(type.length()>0){
			     sql+=" and b.item_type="+type;
			}			
			sql+=" group by b.item_type,c.name order by b.item_type";
			//out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();  
			
			int all_first_qty=0;	
			double all_first_amt=0;	
			int all_in_qty=0;	
			double all_in_amt=0;			
			int all_out_qty=0;	
			double all_out_amt=0;				
			int all_last_qty=0;	
			double all_last_amt=0;
			double all_out_dis_amt=0;
			 
			while(rs.next()){
			String s_item_type=rs.getString("item_type");
			String name=rs.getString("name");	
			int s_first_qty=rs.getInt("first_qty");	
			double s_first_amt=rs.getDouble("first_amt");	
			int s_in_qty=rs.getInt("in_qty");	
			double s_in_amt=rs.getDouble("in_amt");			
			int s_out_qty=rs.getInt("out_qty");	
			double s_out_amt=rs.getDouble("out_amt");				
			int s_last_qty=rs.getInt("last_qty");	
			double s_last_amt=rs.getDouble("last_amt");
			double out_dis_amt=rs.getDouble("out_dis_amt");	
			
			/******************* 合计 ********************/
			 all_first_qty=all_first_qty+s_first_qty;	
			 all_first_amt=all_first_amt+s_first_amt;	
			 all_in_qty=all_in_qty+s_in_qty;	
			 all_in_amt=all_in_amt+s_in_amt;			
			 all_out_qty=all_out_qty+s_out_qty;	
			 all_out_amt=all_out_amt+s_out_amt;				
			 all_last_qty=all_last_qty+s_last_qty;	
			 all_last_amt=all_last_amt+s_last_amt;	
			 all_out_dis_amt=all_out_dis_amt+out_dis_amt;							
			
  %>
  <tr >
		<td bgcolor="#FFFFFF" noWrap align="center">
		<a href="invoice_stock_dtl.jsp?type=<%=s_item_type%>&pid=<%=pid%>"><%=name%></a>
		</td>
    <td bgcolor="#FFFFFF" align="right"><%=s_first_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(s_first_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=s_in_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(s_in_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=s_out_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(s_out_amt)%></td>

        
    <td bgcolor="#FFFFFF" align="right"><%=s_last_qty%></td>

    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(s_last_amt)%></td>     

  </tr>
  <%}%>
  <tr>
    <td bgcolor="#FFFFFF" align="center"><b>合计</b></td>
    <td bgcolor="#FFFFFF" align="right"><%=all_first_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_first_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=all_in_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_in_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=all_out_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_out_amt)%></td>

    <td bgcolor="#FFFFFF" align="right"><%=all_last_qty%></td>
 
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_last_amt)%></td>  

  </tr>
  <tr>
    <td bgcolor="#FFFFFF" align="center" colspan="9"><a href="invoice_stock_excel.jsp?pid=<%=pid%>&type=<%=type%>">导出excel</ab></td>


  </tr>  
</table>


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

</form>
</body>
</html>
