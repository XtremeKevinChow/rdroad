<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<%
			String pid=request.getParameter("pid");
						 pid=(pid==null)?"0":pid.trim();
			String item_code=request.getParameter("item_code");
						 item_code=(item_code==null)?"":item_code.trim();
			String pro_no=request.getParameter("pro_no");
						 pro_no=(pro_no==null)?"":pro_no.trim();		
			String tag=request.getParameter("tag");
						 tag=(tag==null)?"":tag.trim();	
			String begin_date	="";
			String end_date	="";						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";						 				 						 
		try{
		 conn = DBManager.getConnection();	
			
		  sql=" select * from fin_period where pid="+pid;
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();	
			if(rs.next()){		
    	begin_date=rs.getString("begin_date");
			end_date=rs.getString("end_date");	  
 			}	 					 
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

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">已记帐采购发票列表</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<form action="" method="POST">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>

		<td width="260">
			供应商代码：
			<input type=text name="pro_no" size=20 value=<%=pro_no%>>&nbsp;&nbsp;&nbsp;
		</td>
		<td width="260">
			货号：
			<input type=text name="item_code" size=20 value=<%=item_code%>>
		</td>
		<td>
		日期：
			<select name="pid">
			<option value="0">--所有--</option>
			<%
		  sql=" select * from fin_period";
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
			<input type="submit" value=" 查询 " >
			<input type="hidden" name="tag" value="1">
				</td>
	
	</tr>

</table>
			
</form>			
<br>
<%
if(tag.length()>0){
%>	

<table width="1200" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
	  <th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算日期</th>
	  <th width="200"  class="OraTableRowHeader" noWrap  noWrap align=middle>供应商</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>入库单号</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>发票号</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>货号</th>
		<th width="300"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算数量</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算单价</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>结算金额</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>暂估单价</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle>暂估金额</th>
	</tr>
<%

		  sql="select a.*,b.item_id,b.qty,b.pur_price,b.ap_price,b.amt,c.name as item_name,c.item_code,d.pro_name, ";
		  sql+=" (select e.res_no from fin_ps_mst e inner join fin_ps_dtl f on e.ps_id=f.ps_id where b.ps_dtl_id=f.ps_dtl_id) as res_no ";
			sql+=" from fin_ap_mst a inner join fin_ap_dtl b ";
			sql+=" on a.ap_id=b.ap_id ";
			sql+=" inner join prd_items c ";
			sql+=" on b.item_id=c.item_id ";
			sql+=" inner join providers d ";
			sql+=" on a.pro_no=d.pro_no ";
			sql+=" where a.status=3 ";
			if(pro_no.length()>0){
			sql+=" and a.pro_no='"+pro_no+"'";
			}
			if(item_code.length()>0){
			sql+=" and c.item_code='"+item_code+"'";
			}		
			if(Integer.parseInt(pid)>0){
			sql+=" and a.createdate>=date'"+begin_date.substring(0,10)+"' and a.createdate<date'"+end_date.substring(0,10)+"'+1";
			}	
			//System.out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String createdate=rs.getString("createdate").substring(0,10);
			String pro_name=rs.getString("pro_name");
			String fact_ap_code=rs.getString("fact_ap_code");
			String item_name=rs.getString("item_name");
			String s_item_code=rs.getString("item_code");
			int qty=rs.getInt("qty");
			double pur_price=rs.getDouble("pur_price");
			double ap_price=rs.getDouble("ap_price");
			double amt=rs.getDouble("amt");
			String res_no=rs.getString("res_no");
		
			
%>	
	<tr>
	  <td bgcolor="#FFFFFF"align="center"><%=createdate%></td>
	  
	  <td bgcolor="#FFFFFF"><%=pro_name%></td>
	  <td bgcolor="#FFFFFF"align="center"><%=res_no%></td>
	  <td bgcolor="#FFFFFF"align="center"><%=fact_ap_code%></td>
	  <td bgcolor="#FFFFFF"align="center"><%=s_item_code%></td>
    <td bgcolor="#FFFFFF"><%=item_name%></td>
    <td bgcolor="#FFFFFF" align="right"><%=qty%></td>
    
    <td bgcolor="#FFFFFF" align="right"><%=pur_price%></td>
   	<td bgcolor="#FFFFFF" align="right"><%=Arith.round(qty*pur_price,2)%></td>
    <td bgcolor="#FFFFFF" align="right"><%=ap_price%></td>
   	<td bgcolor="#FFFFFF" align="right"><%=amt%></td>
    
  
  </tr> 
  <%}//while%>
</table>
<%}%>
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