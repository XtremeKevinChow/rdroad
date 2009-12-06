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
<table width="880" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
  <tr>
    <th  rowspan="2"    width="280" align=middle bgcolor="#FFFFFF">产品名称</th>
    <th  rowspan="2" width="120" align=middle bgcolor="#FFFFFF">产品代码</th>
    <th colspan="2" width="120" align=middle bgcolor="#FFFFFF">期初数据</th>
    <th colspan="2" width="120" align=middle bgcolor="#FFFFFF">本期入库</th>
    <th colspan="2" width="120" align=middle bgcolor="#FFFFFF">本期出库</th>
    <th colspan="2" width="120"  align=middle bgcolor="#FFFFFF">期末结存</th>              
  </tr>
  <tr  align="center"  bgcolor="#FFFFFF">
    <td  width="60"  bgcolor="#FFFFFF">数量</td>
    <td width="60"  bgcolor="#FFFFFF">金额</td>
    <td width="60"  bgcolor="#FFFFFF">数量</td>
    <td width="60"  bgcolor="#FFFFFF">金额</td>
    <td width="60"  bgcolor="#FFFFFF">数量</td>
    <td width="60"  bgcolor="#FFFFFF">金额</td> 
  
    <td width="60"  bgcolor="#FFFFFF">数量</td>
    <td width="60"  bgcolor="#FFFFFF">金额</td>      
  
  </tr>
  <%
			sql="select b.name,b.item_code,";
			sql+=" sum(a.first_amt) as first_amt,sum(a.first_qty) as first_qty,";
			sql+=" sum(a.in_qty) as in_qty,sum(a.out_qty) as out_qty,sum(a.last_qty) as last_qty,";
			sql+=" sum(a.in_amt) as in_amt,sum(a.out_amt) as out_amt,sum(a.last_amt) as last_amt,";
			sql+=" sum(a.out_dis_amt) as out_dis_amt ";
			sql+=" from fin_stock_mst a ";
			sql+=" inner join prd_items b on ";
			sql+="a.item_id=b.item_id";
			if(Integer.parseInt(pid)>0){
			     sql+=" and a.pid="+pid;
			}
			if(type.length()>0){
			     sql+=" and b.item_type="+type;
			}			
			sql+=" group by b.name,b.item_code order by b.item_code";
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
			String item_name=rs.getString("name");
			String item_code=rs.getString("item_code");	
			int first_qty=rs.getInt("first_qty");	
			double first_amt=rs.getDouble("first_amt");	
			int in_qty=rs.getInt("in_qty");	
			double in_amt=rs.getDouble("in_amt");			
			int out_qty=rs.getInt("out_qty");	
			double out_amt=rs.getDouble("out_amt");				
			int last_qty=rs.getInt("last_qty");	
			double last_amt=rs.getDouble("last_amt");
			double out_dis_amt=rs.getDouble("out_dis_amt");	
			all_out_dis_amt=all_out_dis_amt+out_dis_amt;
			
			/******************* 合计 ********************/
			 all_first_qty=all_first_qty+first_qty;	
			 all_first_amt=all_first_amt+first_amt;	
			 all_in_qty=all_in_qty+in_qty;	
			 all_in_amt=all_in_amt+in_amt;			
			 all_out_qty=all_out_qty+out_qty;	
			 all_out_amt=all_out_amt+out_amt;				
			 all_last_qty=all_last_qty+last_qty;	
			 all_last_amt=all_last_amt+last_amt;											
			
  %>
  <tr>

		<td bgcolor="#FFFFFF" noWrap align="center"><%=item_name%></td>
		<td bgcolor="#FFFFFF" noWrap align="center"><%=item_code%></td>
    <td bgcolor="#FFFFFF"align="right"><%=first_qty%></td>
    <td bgcolor="#FFFFFF"align="right"><%=myformat.format(first_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=in_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(in_amt)%></td>
   	<td bgcolor="#FFFFFF" align="right"><%=out_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(out_amt)%></td>

   	<td bgcolor="#FFFFFF" align="right"><%=last_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(last_amt)%></td>    
  
  </tr> 
  <%}%>
  <tr>
    <td bgcolor="#FFFFFF" colspan="2" align="center"><b>合计</b></td>
    <td bgcolor="#FFFFFF" align="right"><%=all_first_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_first_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=all_in_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_in_amt)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=all_out_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_out_amt)%></td>

    <td bgcolor="#FFFFFF" align="right"><%=all_last_qty%></td>
    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_last_amt)%></td>  

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
