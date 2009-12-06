<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String id=request.getParameter("id");

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function msc_gift_add(id) {
	location="msc_active_gift_add.jsp?pid="+id;
		
}
function msc_gift_set(pid,id) {
	location="msc_active_gift_del.jsp?pid="+pid+"&id="+id;
		
}
function msc_gift_update(pid,id) {
	location="msc_active_gift_update.jsp?pid="+pid+"&id="+id;
		
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 市场促销 -&gt; 招募活动礼品设置</font></td>
  </tr>
</table>

<br>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr align="center">
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap >货号</td>		
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap >产品名称</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap >销售方式</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap >银卡价</td>

		<td width=""  class="OraTableRowHeader" noWrap  noWrap ></td>
	</tr> 
<% 

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
       
		try{
		 conn = DBManager.getConnection();
		      
		String sql="select a.id,a.COMMON_PRICE,a.item_id,b.name,c.name,c.item_code from PRD_PRICELIST_LINES a,s_sell_type b,prd_items c";
		       sql+=" where a.item_id=c.item_id and a.sell_type=b.id and a.status=0 and a.pricelist_id="+id;
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		while(rs.next()){
		String rs_id=rs.getString("id");
		String common_price=rs.getString(2);
		String item_id=rs.getString(3);
		String item_code=rs.getString(6);
		String sell_name=rs.getString(4);
		String item_name=rs.getString(5);
		 %>	  
	<tr align="center">
		<td bgcolor="#FFFFFF" ><%=item_code%></td>
		<td bgcolor="#FFFFFF" ><%=item_name%></td>
		<td bgcolor="#FFFFFF" ><%=sell_name%></td>	
		<td bgcolor="#FFFFFF" ><%=common_price%></td>
		<td bgcolor="#FFFFFF" >
		<input type="button" value="修改" onclick="javascript:msc_gift_update(<%=id%>,<%=rs_id%>)">&nbsp;		
		<input type="button" value="删除" onclick="javascript:msc_gift_set(<%=id%>,<%=rs_id%>)">&nbsp;
		</td>		
	</tr>  	 
	<%}%>
	<tr align="center">
		<td bgcolor="#FFFFFF" colspan="6" >
		<input type="button" value="增加礼品" onclick="javascript:msc_gift_add(<%=id%>)">&nbsp;
		</td>		
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
</table>
		
</body>
</html>
