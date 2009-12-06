<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%

String gift_id=request.getParameter("gift_id");
gift_id=(gift_id==null)?"":gift_id;
String item_id=request.getParameter("item_id");
item_id=(item_id==null)?"":item_id;
String msc=request.getParameter("msc");
msc=(msc==null)?"":msc;
String query=request.getParameter("query");
String status = request.getParameter("status");
query=(query==null)?"":query;
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function query() {

        document.form.action="gift_new_add.jsp";
	document.form.input.disabled = true;
	document.form.submit();
}
function queryInput() {

        document.form.action="gift_add.jsp?query=1";
	document.form.search.disabled = true;
	document.form.submit();
}
function initFocus(){
	document.form.msc.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">查询促销设置</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="gift_add.jsp" method="post" name="form" >
<table width="95%" align="center" border=0 cellspacing=1>
	<tr>
		<td bgcolor="#FFFFFF">MSC号：&nbsp;&nbsp;
			<input type="text" name="msc" value="<%=msc%>">&nbsp;&nbsp;
			<input type=radio name="status" value="0" checked> 有效
			<input type=radio name="status" value="1"> 无效&nbsp;&nbsp;
			<input type="button" name="search" value=" 查询 " onclick="queryInput()";>	
			&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="input" value=" 新增礼品/礼券 " onclick="query()";>	
		</td>		
	</tr>	
</table>

<% 
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String condition="";


try{
		 conn = DBManager.getConnection();
		 String sql="";

if(query.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0" >
	<tr>
		<td width="5%" class="OraTableRowHeader" noWrap >编号</td>
		<td width="10%" class="OraTableRowHeader" noWrap >MSC号</td>		
		<td width="12%" class="OraTableRowHeader" noWrap >礼品/礼券号</td>
		<td width="" class="OraTableRowHeader" noWrap >礼品名称</td>
		<td width="5%" class="OraTableRowHeader" noWrap >类型</td>
		<td width="10%" class="OraTableRowHeader" noWrap >满足金额</td>
		<td width="10%" class="OraTableRowHeader" noWrap >加X元</td>		
		<td width="5%" class="OraTableRowHeader" noWrap >状态</td>
	
		<td width="5%" class="OraTableRowHeader" noWrap ></td>
		<td width="5%" class="OraTableRowHeader" noWrap ></td>
		
		
	</tr>
<%
			sql="select * from mbr_msc_gift where status = "+status;

			
			if(msc.length()>0){
			 sql+=" and msc_code='"+msc+"'";
			}
			sql+=" order by msc_code ";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
				while(rs.next()){ 
				String type=rs.getString("type");
				String rs_msc=rs.getString("msc_code");	
				String rs_item_id=rs.getString("item_id");
				int rs_id=rs.getInt("id");
				status=rs.getString("status");	
				String order_require=rs.getString("order_require");
				String addmoney=rs.getString("addmoney");					
%>
	<tr align="center">
		<td class="OraTableCellText" ><%=rs_id%></td>
		<td class="OraTableCellText" align="left"><%=rs_msc%></td>
		<td  class="OraTableCellText" align="left"><%=rs_item_id%></td>
		<td class="OraTableCellText" align="left">
		<%
		if(type.equals("1")){
			sql="select name from prd_items where  item_id='"+rs_item_id+"'";
			pstmt=conn.prepareStatement(sql);
			ResultSet item_rs=pstmt.executeQuery();
			if(item_rs.next()){
			  out.println(item_rs.getString("name"));
			}
			item_rs.close();
		}
		%>		
		</td>
		<td class="OraTableCellText">
		<%
		 if(type.equals("1")){
		    out.println("礼品");
		 }else{
		    out.println("礼券");
		 }
		%></td>
		<td class="OraTableCellText"><%=order_require%>元</td>
		<td class="OraTableCellText"><%=addmoney%>元</td>
		<td class="OraTableCellText">
		<%
		 if(status.equals("0")){
		    out.println("有效");
		 }else{
		    out.println("<font color='red'>失效</font>");
		 }
		%>		
		</td>	
			
		<td class="OraTableCellText">
		<%if(status.equals("0")){%>
		<a href="gift_del.jsp?id=<%=rs_id%>&status=1&msc=<%=msc%>&gift_id=<%=gift_id%>&item_id=<%=rs_item_id%>">删除</a>
		<%}else{%>
		<a href="gift_del.jsp?id=<%=rs_id%>&status=0&msc=<%=msc%>&gift_id=<%=gift_id%>&item_id=<%=rs_item_id%>">启用</a>
		<%}%>
		</td>
		<td class="OraTableCellText"><a href="gift_modify.jsp?id=<%=rs_id%>&msc=<%=msc%>&gift_id=<%=gift_id%>&item_id=<%=rs_item_id%>">修改</a></td>

	</tr>
	
<%					
	
	
	                } 
	}

%>
</table>
<br>

</form>
<!--*************************************************************************** -->
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
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}

%>
</body>
</html>
