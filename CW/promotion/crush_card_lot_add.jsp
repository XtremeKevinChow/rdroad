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
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>


<SCRIPT LANGUAGE="JavaScript">

function ref(){
       document.form.action="";
       document.form.submit();
}
function add(){
	if(document.forms[0].lot_no.value == ""){
    	alert('充值卡批号必须填写!');
    	document.form.lot_no.select();
    	return false;
	}
	if(document.forms[0].begin_date.value == ""){
    	alert('批号开始时间必须填写!');
    	document.form.begin_date.select();
    	return false;
	}
	if(document.forms[0].end_date.value == ""){
    	alert('批号结束时间必须填写!');
    	document.form.end_date.select();
    	return false;
	}
   document.form.action="crush_card_lot_add_ok.jsp";
   document.form.submit();
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">充值卡批次设置</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="60%" border="0"  cellpadding="1" align="center">
	<tr >
		<td width="50%"class="OraTableRowHeader" noWrap >批次号</td><td> <input type=text name="lot_no">&nbsp;<font color=red>*</font></td>
	</tr>
	<tr >
		<td class="OraTableRowHeader" noWrap >开始日期</td><td ><input type=text name="begin_date" readonly=true>
		<a href="javascript:show_calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>&nbsp;<font color=red>*</font>
		</td>
	</tr>
	<tr >
		<td class="OraTableRowHeader" noWrap >结束日期</td><td ><input type=text name="end_date" readonly=true>
		<a href="javascript:show_calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>&nbsp;<font color=red>*</font>
		</td>
	</tr>
	<tr>
		<td colspan=2 align="center"><input type="button" value="新增数据" onclick="add()"></td>
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