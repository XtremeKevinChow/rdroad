<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function Submit() {

if(document.forms[0].card_qty.value==""){
	alert('制卡数量必须填写!');
	document.forms[0].card_qty.select();
	return false;
	
}
if(isNaN(document.forms[0].card_qty.value)){
	alert('制卡数量必须为数字!');
	document.forms[0].card_qty.select();
	return false;
}

if(document.forms[0].pass_num.value==""){
	alert('密码个数必须填写!');
	document.forms[0].pass_num.select();
	return false;
	
}
if(isNaN(document.forms[0].pass_num.value)){
	alert('密码个数必须为数字!');
	document.forms[0].pass_num.select();
	return false;
}	
var c_type=document.forms[0].card_type.value;
var c_qty=document.forms[0].card_qty.value;
var p_num=document.forms[0].pass_num.value;
	document.forms[0].input.disabled = true;
	//document.forms[0].action = "Crush_CardCreate.do";
	openWin("Crush_CardCreate.do?pass_num="+p_num+"&card_qty="+c_qty+"&card_type="+c_type,"wins",720,480);
	//document.forms[0].submit();
}
function initFocus(){
	document.forms[0].card_qty.select();
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
      		-&gt; </font><font color="838383">制作销售卡</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="Crush_CardCreate.do" method="post" name="Crush_CardCreateForm" >
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
	
		<td width="10%">制卡类型</td>
		<td bgcolor="#FFFFFF" >
		<select name="card_type">
		  <option value="1">100元</option>
		  <option value="2">200元</option>
		</select>  
		</td>
	
		<td width="10%">制卡数量</td>
		<td bgcolor="#FFFFFF"><input type="text" name="card_qty"></td>
		<td width="10%">密码个数</td>
		<td bgcolor="#FFFFFF"><input type="text" name="pass_num"></td>				
		<td bgcolor="#FFFFFF" >
		<!--
		<input type="button" name="input" value="新  增" onclick="Submit()">&nbsp;&nbsp;&nbsp;
		-->
		</td>				
	</tr>	

</table>
</form>

</body>
</html>
