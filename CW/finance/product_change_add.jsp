<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="java.text.*"%>
<%@ page import ="java.util.Date"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");     //日期格式
	Date today = new Date();
	String theday = sdf.format(today).toString();
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--



function query_f() {
	if(document.form.item_id_key.value==""){
		alert('请选择货号!');

	return false;
	}
	if(document.form.operateDate.value==""){
		alert('请选择货号!');

	return false;
	}	
	if(!is_integer(document.form.number.value)||document.form.number.value==""||parseInt(document.form.number.value)==0){
		alert('数量必须是不为0的整数!');
	document.form.number.select();
	return false;
	}



	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="product_change_add_ok.jsp" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品库存变动新增</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="700" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	
	<tr>
		<td  bgcolor="#FFFFFF" width="75">货号:</td>
		<td  bgcolor="#FFFFFF" width="375">
		<input type="hidden" id="item_id" name="item_id" value="">
		<input id="item_id_key" size="10" name="item_id_key" value="" readonly onclick="javascript:select_item_ey(form.item_id,form.item_id_key,item_id_display,'','','');"> 
		<a href="javascript:select_item_ey(form.item_id,form.item_id_key,item_id_display,'','','');"><img src="../crmjsp/images/icon_lookup.gif" border=0 align="top"><a>
		&nbsp;<span id="item_id_display" name="item_id_display" ></span>		
		</td>		
		<td  bgcolor="#FFFFFF" width="75">日期:</td>
		<td  bgcolor="#FFFFFF" width="125"><input type="text" name="operateDate" size="10" readonly value="<%=theday%>">
		  <a href="javascript:calendar(document.form.operateDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td  bgcolor="#FFFFFF" width="75">数量:</td>	
		<td  bgcolor="#FFFFFF" width="75"><input type="text" name="number" size="10"  ></td>		
	</tr>
			
	
<tr>
		
		<td  bgcolor="#FFFFFF"  colspan="6" align="center">
			<input type="button" name="btn_query" value=" 提  交 " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">
		</td>
	</tr>
		
</table>

</form>
</body>
</html>
