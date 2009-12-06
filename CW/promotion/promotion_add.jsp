<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="javascript">
function f_checkData() {
        if(document.forms[0].name.value==""){
           alert("请填写促销名称");
           document.forms[0].name.select();	
           return false;
        }
	if(!f_checkStartDate()) {
		alert("生效日期输入不正确");
		document.forms[0].begindate.select();
		return false;
	}
	if(!f_checkEndDate()) {
		alert("终止日期输入不正确");
		document.forms[0].enddate.select();
		return false;
	}
	//开始日期不能大于结束日期
	var begin, end; 
	begin = document.forms[0].begindate.value.replace("-", "").replace("-","");
	end =   document.forms[0].enddate.value.replace("-", "").replace("-",""); 
	if (begin - end > 0)
	{
		alert("开始日期不能大于结束日期");
		return false;
	}
 	document.forms[0].input.disabled = true;
}



function f_checkStartDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].begindate.value);
}
function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].enddate.value);
}
function initFocus(){
	document.forms[0].name.select();
	return true;
}
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0"  onload="javascript:initFocus();">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">促销管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增促销</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form action="promotionOperation.do?type=add" method="post" onsubmit="return f_checkData();">
  

 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap   width="30%">促销名称</td>
    <td><input type=text name="name" size=30><font color=red>*</font></td>
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap >促销模式</td>
    <td>
    <select name="flag">
    <option value="1">全场促销</option>
    <option value="2">分类促销</option>
    <option value="3">产品组促销</option>
    <option value="4">xx元任选x件</option>
    </select>       
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >使用范围</td>
    <td>
    <select name="putbasket">
    <option value="0">全部可用</option>
    <option value="1">仅网上使用</option>
    <option value="2">仅网下使用</option>
    </select>       
    </td>
 </tr>

 <tr> 
    <td class="OraTableRowHeader" noWrap >生效日期</td>
    <td><input type=text name="begindate" size=30>
    <a href="javascript:show_calendar(document.forms[0].begindate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
    <font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    终止日期</td><td><input type=text name="enddate" size=30>
    <a href="javascript:show_calendar(document.forms[0].enddate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
    <font color=red>*</font>
    </td>
 </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap  >描述</td>
    <td ><textarea name="description" rows="5" cols="50"></textarea></td>
 </tr>
 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      
  </tr>
</table>
</form>

<p>&nbsp;</p>
</body>
</html>

