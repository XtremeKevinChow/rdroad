<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%

			String item_id=request.getParameter("item_id");
						 item_id=(item_id==null)?"":item_id;
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

	if (document.forms[0].item_id.value == "")
	{
		alert("货号必须填写");
		document.forms[0].item_id.focus();
		return;
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
      		-&gt; </font><font color="838383">产品变动明细</font><font color="838383"> 
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
			货号：
			<input type=text name="item_id" size="25" value="<%=item_id%>">	&nbsp;<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">	
		</td>
		</tr>		
</table>

<br>
<table width="1200" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>

		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>货号</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>相应单号</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>单据日期</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>操作日期</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>操作数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>操作价格</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>操作金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>最后数量</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>最后价格</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>最后金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>是否暂估</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>是否红冲</th>

	</tr>

<%
		try{
		 conn = DBManager.getConnection();
       sql=" SELECT b.item_code,b.name ,c.name as item_type_name,res_no,operation_date,write_date,a.item_id,op_qty,CASE doc_type ";
       sql+=" WHEN '01' THEN (decode(is_temp,'N',fact_price,'Y',pre_price)) ";
       sql+=" ELSE pre_price ";
       sql+=" END AS op_price, ";
       sql+=" CASE doc_type ";
       
       sql+="  WHEN '01' THEN (decode(is_temp,'N',fact_amt,'Y',pre_amt)) ";
       sql+=" ELSE pre_amt ";
       sql+=" END AS op_amt, ";
       
       sql+=" last_qty,last_amt,last_price, ";
       sql+="  decode(doc_type,'01','采购','02','销售','03','期初','04','其它') as type, ";
       sql+=" CASE doc_type ";
       sql+=" WHEN '01' THEN (decode(is_temp,'N','正常','Y','暂估')) ";
       sql+=" ELSE '无' ";
       sql+=" END as if_zg, ";
       sql+=" CASE doc_type ";
       sql+=" WHEN '01' THEN (decode(is_red,'N','正常','Y','红冲')) ";
       sql+=" ELSE '无' ";
       sql+=" END as if_hc"; 
			sql+=" FROM fin_stock_detail a  ";
			sql+=" inner join prd_items b on a.item_id=b.item_id  ";
			sql+=" inner join s_item_type c on b.item_type=c.id "; 
			sql+=" WHERE b.item_code= '"+item_id+"'";
			sql+=" ORDER BY write_date DESC,sd_id desc ";
			//out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){	
			String name=rs.getString("name");	
			String item_type_name=rs.getString("item_type_name");	
			String res_no=rs.getString("res_no");	
			String operation_date=rs.getString("operation_date").substring(0,10);	
			String write_date=rs.getString("write_date").substring(0,10);	
			String op_qty=rs.getString("op_qty");	
			String op_price=rs.getString("op_price");	
			String op_amt=rs.getString("op_amt");	
			String last_amt=rs.getString("last_amt");	
			String last_price=rs.getString("last_price");	
			String last_qty=rs.getString("last_qty");	
			String type=rs.getString("type");	
			String if_zg=rs.getString("if_zg");	
			String if_hc=rs.getString("if_hc");	
			String item_code=rs.getString("item_code");	

			

%>	
	<tr align=center>

		<td bgcolor="#FFFFFF" noWrap><%=name%></td>
		<td bgcolor="#FFFFFF" noWrap><%=item_code%></td>
		<td bgcolor="#FFFFFF" noWrap><%=item_type_name%></td>
		<td bgcolor="#FFFFFF" noWrap><%=res_no%></td>
		<td bgcolor="#FFFFFF" noWrap><%=operation_date%></td>
		<td bgcolor="#FFFFFF" noWrap><%=write_date%></td>
		<td bgcolor="#FFFFFF" noWrap><%=op_qty%></td>
		<td bgcolor="#FFFFFF" noWrap><%=op_price%></td>
		<td bgcolor="#FFFFFF" noWrap><%=op_amt%></td>
		<td bgcolor="#FFFFFF" noWrap><%=last_qty%></td>
		<td bgcolor="#FFFFFF" noWrap><%=last_price%></td>
		<td bgcolor="#FFFFFF" noWrap><%=last_amt%></td>
		<td bgcolor="#FFFFFF" noWrap><%=type%></td>
		<td bgcolor="#FFFFFF" noWrap><%=if_zg%></td>
		<td bgcolor="#FFFFFF" noWrap ><%=if_hc%></td>
</tr>
<%}
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
</form>
</body>
</html>
