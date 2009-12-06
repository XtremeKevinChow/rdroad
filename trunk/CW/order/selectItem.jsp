<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.member.dao.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String item_id=request.getParameter("item_id");
String orderId=request.getParameter("orderId");
       orderId=(orderId==null)?"0":orderId.trim();
String tag=request.getParameter("tag");


%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title></title>
<script language="JavaScript">
function changeItem(item_code) {
		document.forms[0].action = "orderAddSecond.do?type=changeItem&tag=<%=tag%>&orderId=<%=orderId%>&item_code="+item_code;
		document.forms[0].submit();

}


</script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<html:form action="/orderAddSecond.do"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">订单管理</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">会员缺货产品更换列表</font> 
      	</td>
   </tr>
</table>
<br>

<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="500"  cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
	        <td width="50"></td>
		<td width="100">替代品货号</td>
		<td width="*">产品名</td>
		<td width="*">库存数量</td>
	</tr>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      int addmoney=0;
		try{
		 conn = DBManager.getConnection();
		 sql=" select a.name,a.item_code,a.item_id,b.use_qty  from prd_items a,jxc.sto_stock b ";
		 sql+=" where b.sto_no='000' and b.use_qty>0 and a.item_id=b.item_id ";
		 sql+=" and a.item_id in (select replace_item_id from prd_items where item_id="+item_id+")";
		// out.println(sql);
		 pstmt=conn.prepareStatement(sql);						
		 rs=pstmt.executeQuery();
		 while(rs.next()){ 
		 	String item_code=rs.getString("item_code");
			String name=rs.getString("name");
			String use_qty=rs.getString("use_qty");
			String r_item_id=rs.getString("item_id");

%>
	<tr>
	
		<td bgcolor="#FFFFFF"><input type="radio" onClick="changeItem(<%=item_code%>)"></td>
		<td bgcolor="#FFFFFF"><%=item_code%></td>
		<td bgcolor="#FFFFFF"><%=name%></td>
		<td bgcolor="#FFFFFF"><%=use_qty%></td>
	</tr>		
<%					
	
	addmoney++;
	}

	rs.close();
	pstmt.close();

%>
	<%if(addmoney==0){%>
	<tr>
	
		<td bgcolor="#FFFFFF" colspan="4" align="center">没有替代品，<a href="#"  onclick="history.back();">返回</a></td>

	</tr>
	<%}%>	
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
</table>
  </td></tr>
</table>  
</html:form>     
</body>

</html>