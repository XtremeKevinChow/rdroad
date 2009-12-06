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
	if(document.form.msc_code.value==""){
		alert('请填写活动编号!');
	document.form.msc_code.select();
	return false;
	}	
	if(document.form.name.value==""){
		alert('请填写活动名称!');
	document.form.name.select();
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
      		-&gt; </font><font color="838383">招募活动新增</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="recruit_activity_addok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>活动编号</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="msc_code" > </td>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>活动名称</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="name" > </td>

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
		<td bgcolor="#FFFFFF" width="150">使用范围</td>
		<td bgcolor="#FFFFFF" colspan="3">
		<select name="scope">
 
		 <option value="1">网站使用</option>	
		 <option value="2">CRM使用</option>	
		 <option value="3">都可使用</option>	
	 
		</select>				
		</td>		

	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">html代码</td>
		<td bgcolor="#FFFFFF" colspan="3"><textarea  name="headhtml" rows="5" cols="50"></textarea></td>
	
	</tr>	

	<tr>		
		<td bgcolor="#FFFFFF" width="150">描述</td>
		<td bgcolor="#FFFFFF" colspan="3"><textarea  name="remark" rows="5" cols="50"></textarea></td>
	
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