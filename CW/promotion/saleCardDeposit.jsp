<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
String cardId = request.getParameter("card_id");
cardId = cardId == null ? "" : cardId;
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function setfocus(){
	document.form.card_id.focus();
}
function Submit(){

	var form = document.form;
	if(form.card_id.value == ""){
		alert("必须输入会员号码！");
		form.card_id.select();
		return false;
	}
	if(form.card_num.value == ""){
		alert("必须输入销售卡号码！");
		form.card_num.select();
		return false;
	}	
	if(form.pass_num.value == ""){
		alert("必须输入充值卡密码！");
		form.pass_num.select();
		return false;
	}
	if(isNaN(document.form.pass_num.value)){
	alert('充值卡密码只能为数字!');
	document.form.pass_num.select();
	return false;
	}		

		document.form.search.disabled = true;
		document.form.action = "saleCardUpdate.do";
		
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">书香卡充值</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<br><br>
<form action="" name="form" method="post" onsubmit="javascript:return Submit();">
  <table align="center" border=0 cellspacing=1 cellpadding=3 class="OraTableRowHeader" noWrap >
    <tr>		
      <td >会员号：</td>
      <td bgcolor="#FFFFFF"><input name = "card_id" value="<%=cardId%>" type="text" size=16/></td>
    </tr>  
    <tr>		
      <td width="80" >销售卡号：</td>
      <td width="*" bgcolor="#FFFFFF"> <input name = "card_num" value="" type="text" size=16/></td>
    </tr>     

    <tr>		
      <td >密码：</td>
      <td bgcolor="#FFFFFF"><input name = "pass_num" value="" type="password" size=16/></td>
    </tr>    
   

</table>
<br>
<table align="center" border=0 cellspacing=1 cellpadding=3>
 <tr>
	<td bgcolor="#FFFFFF" width="95%" colspan="2" align="center">
      	<input type="submit" name="search" value=" 充值 ">
     </td>
    </tr>
</table>
<input type="hidden" name="tag" value="2">
</form>
</body>
</html>
