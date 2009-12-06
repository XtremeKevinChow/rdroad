<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		  conn = DBManager.getConnection();  

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/popcalendar.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.queryItemCode.value==""){
		alert('请填写货号!');
	document.form.queryItemCode.select();
	return false;
	}	
	if(!is_number(document.form.price.value)||document.form.price.value==""||parseInt(document.form.price.value)<0){
	alert('销售价格必须是数字!');
	document.form.price.select();
	return false;
	}		
	if(!is_number(document.form.overx.value)||document.form.overx.value==""||parseInt(document.form.overx.value)<0){
	alert('满x元必须是数字!');
	document.form.overx.select();
	return false;
	}	
	var sdate = document.form.start_date.value; 
	if(sdate==""){
	alert('请填写开始日期!');
	document.form.start_date.select();
	return false;
	}	
	var edate = document.form.end_date.value; 
	if(edate==""){
	alert('请填写结束日期!');
	document.form.end_date.select();
	return false;
	}
	
	
												
	document.form.input.disabled = true;

}
function getOpenwinValue(ret){
	//alert(ret);
	document.forms[0].queryItemCode.value = ret;
	if(document.forms[0].queryItemCode.value.length >6) {
		document.forms[0].queryItemId.value =document.forms[0].queryItemCode.value.substring(0,6);
		document.forms[0].queryItemCode.value =document.forms[0].queryItemCode.value.substring(7,13);
	}
}
function initFocus(){

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
      		-&gt; </font><font color="838383">销售区产品新增</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="recruit_activity_priceList_addok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>产品名称</td>
		<td bgcolor="#FFFFFF">
      <input type="text" name="queryItemCode" size="16" >
      <input type="hidden" name="queryItemId" size="16" >
      
      <input name="query" type="button" value="查询" onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);">
      		
		</td>	
		<td bgcolor="#FFFFFF" width="150">销售区</td>
		<td bgcolor="#FFFFFF">
		<select name="sectionid">
 <%
 		  sql="select * from Recruit_Activity_Section order by id ";
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String section_id=rs.getString("id");
		  String section_name=rs.getString("name");
		  String msc = rs.getString("msc");
 %>
		 <option value="<%=section_id%>">(<%=msc%>)<%=section_name%></option>	
<%}%>
	 
		</select>		
		</td>		


	</tr>
	<tr>		
	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>销售价格</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="price" > </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>满x元</td>
		<td bgcolor="#FFFFFF" ><input type="text"  name="overx" > </td>		
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" width="150">销售类型</td>
		<td bgcolor="#FFFFFF"  colspan="3">
		<select name="selltype">
 <%
 		  sql="select * from s_sell_type order by id ";
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String id=rs.getString("id");
		  String name=rs.getString("name");

 %>
		 <option value="<%=id%>"><%=name%></option>	
<%}%>
	 
		</select>				
		</td>	

	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>开始日期</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="start_date" readonly><INPUT TYPE="button" value="" onclick='popUpCalendar(this, form.start_date, dateFormat,-1,-1,true)' style="background-image:url(img/Button.gif);width:25px;height:17px;border:0px;padding:0px;">  
		</td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>结束日期</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="end_date" readonly><INPUT TYPE="button" value="" onclick='popUpCalendar(this, form.end_date, dateFormat,-1,-1,true)' style="background-image:url(img/Button.gif);width:25px;height:17px;border:0px;padding:0px;">  
		</td>		
	
	</tr>	

	

																
	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value="新  增">					
		</td>			
	</tr>	
</table>

</form>

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