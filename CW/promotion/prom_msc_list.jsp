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
String tag=request.getParameter("tag");
tag=(tag==null)?"":tag.trim();
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function msc_Gift(id) {
	location="msc_active_gift_list.jsp?id="+id;
		
}
function msc_set(tag,id) {
	location="msc_active_gift.jsp?tag="+tag+"&id="+id;
		
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 市场促销 -&gt; 招募活动查询</font></td>
  </tr>
</table>

<form name="form1" method="post" action="">
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr align="left">
		<td bgcolor="#FFFFFF" >
		活动名称:<input type="text" name="name" >&nbsp;
		招募MSC:<input type="text" name="msc" >&nbsp;
		<input type="submit" value="查 询" >&nbsp;&nbsp;&nbsp;
		</td>
	</tr>  
</table>
<input type="hidden" value="1" name="tag">
</form>
<br>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr align="center">
		<td width="25%"  class="OraTableRowHeader" noWrap  noWrap >活动名称</td>		
		<td width="15%"  class="OraTableRowHeader" noWrap  noWrap >招募MSC</td>
		<td width="10%"  class="OraTableRowHeader" noWrap  noWrap >起始日期</td>
		<td width="10%"  class="OraTableRowHeader" noWrap  noWrap >终止日期</td>
		<td width="10%"  class="OraTableRowHeader" noWrap  noWrap >状态</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap ></td>
	</tr> 
<% 
if(tag.equals("1")){

      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
       
		try{
		 conn = DBManager.getConnection();
		String name=request.getParameter("name");
		       name=(name==null)?"":name.trim();
		String msc=request.getParameter("msc");
	       	       msc=(msc==null)?"":msc.trim();
		       
		String sql="select * from PRD_PRICELISTS where name like '%"+name+"%' and msc like '%"+msc+"%' order by id desc";
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		while(rs.next()){
		String id=rs.getString("id");
		String msc_name=rs.getString("name");
		String effect_date=rs.getString("EFFECT_DATE").substring(0,10);
		String expired_date=rs.getString("EXPIRED_DATE").substring(0,10);
		String status=rs.getString("STATUS");
		String msc_code=rs.getString("MSC");
		String is_valid=rs.getString("IS_VALID");
		 %>	  
	<tr align="center">
		<td bgcolor="#FFFFFF" ><%=msc_name%></td>
		<td bgcolor="#FFFFFF" ><%=msc_code%></td>
		<td bgcolor="#FFFFFF" ><%=effect_date%></td>	
		<td bgcolor="#FFFFFF" ><%=expired_date%></td>
		<td bgcolor="#FFFFFF" >
		<%
		if(status.equals("0")){
		   out.println("创建");
		}
		if(status.equals("100")){
		   out.println("发布");		
		}
		if(status.equals("-1")){
		   out.println("<font color='red'>终止</font>");		
		}				

		%></td>	
		<td bgcolor="#FFFFFF" >
		<input type="button" value="发布" onclick="javascript:msc_set(1,<%=id%>)">&nbsp;
		<input type="button" value="终止" onclick="javascript:msc_set(0,<%=id%>)">&nbsp;
		
		<input type="button" value="礼品设置" onclick="javascript:msc_Gift(<%=id%>)">&nbsp;
		</td>		
	</tr>   
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

}%>	  
</table>
</body>
</html>
