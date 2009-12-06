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

			String cust_name=request.getParameter("cust_name");
						 cust_name=(cust_name==null)?"":cust_name.trim();	
						 
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
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function query_f() {


	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
function getCust_no(cust_no){
		opener.document.forms[0].cust_no.value=cust_no;
		//opener.getOpenwinValue(cust_no);

		self.close();
	
	
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">客户查询</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	

		<tr align="left">
		<td width="15%">
		客户名称：
							
		</td>	
		<td width="35%">
			<input type="text" name="cust_name" size="25" value="<%=cust_name%>">			
		</td>				
		<td>
			<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">
		</td>
		</tr>		
</table>

<br>
<%
if(tag.length()>0){


%>
	
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>

		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>客户号</th>
		<th width="*%"  class="OraTableRowHeader" noWrap  noWrap align=middle>客户名称</th>
	</tr>


<%
		try{
		 conn = DBManager.getConnection();
			sql=" select * from cust_mst where type_id>0";
			if(cust_name.length()>0){
			sql+=" and cust_name like '%"+cust_name+"%'";
			}
			
			     //out.println(sql);
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
		      while(rs.next()){
			String name=rs.getString("cust_name");
			String cust_no=rs.getString("cust_no");      
%>	
		
	<tr>
		<td bgcolor="#FFFFFF" noWrap align="center">
		
	      	<a href="javascript:getCust_no('<%=cust_no%>')">
	      		<%=cust_no%>
	      	</a> 		
		</td> 
		<td bgcolor="#FFFFFF" noWrap align="center">
	      	<a href="javascript:getCust_no('<%=cust_no%>')">
	      		<%=name%>
	      	</a> 			
		</td>
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
<%}%>
</form>
</body>
</html>
