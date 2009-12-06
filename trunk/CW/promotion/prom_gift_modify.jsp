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
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript">

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	
}

function getOpenwinValue(ret){
	document.forms[0].itemcode.value = ret;
}

function f_checkData() {
        if(document.forms[0].itemcode.value==""){
           alert("请填写促销礼品号");
           document.forms[0].itemcode.select();	
           return false;
        }
	if(isNaN(document.forms[0].overx.value)||document.forms[0].overx.value==""){
	alert('满X元只能为数字!');
	document.forms[0].overx.select();
	return false;
	}
	

	if(isNaN(document.forms[0].addy.value)||document.forms[0].addy.value==""){
	alert('加Y元只能为数字!');
	document.forms[0].addy.select();
	return false;
	}

	/*
    if(document.forms[0].prom_url.value==""){
        alert("请填写销售页面地址");
        document.forms[0].prom_url.select();	
        return false;
    }               	    
	if(!f_checkStartDate()) {
		alert("生效日期输入不正确");
		document.forms[0].beginDate.select();
		return false;
	}
	if(!f_checkEndDate()) {
		alert("终止日期输入不正确");
		document.forms[0].endDate.select();
		return false;
	}
    if(document.forms[0].description.value==""){
        alert("请填写描述");
        document.forms[0].description.select();	
        return false;
    } 		

	//开始日期不能大于结束日期
	var begin, end; 
	begin = document.forms[0].beginDate.value.replace("-", "").replace("-","");
	end =   document.forms[0].endDate.value.replace("-", "").replace("-",""); 
	
	if (begin - end > 0)
	{
		alert("开始日期不能大于结束日期");
		return false;
	}
	*/
	document.forms[0].promotionid.value=document.forms[0].promotionID.value;
	document.forms[0].name.value=document.forms[0].promotionName.value;
 	document.forms[0].input.disabled = true;
}



function f_checkStartDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].beginDate.value);
}
function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].endDate.value);
}
function initFocus(){
	document.forms[0].itemcode.select();
	return true;
}
function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
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
      		-&gt; </font><font color="838383">修改礼品促销</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="prom_GiftOperation.do?type=modify" method="post" onsubmit="return f_checkData();">
<!-- 隐含主键 -->
<html:hidden property="ID"/>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><bean:write name="pGiftForm" property="promotionName"/></td>
	</tr>  
	        
</table>
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap  width="30%">促销礼品号</td>
    <td><html:text property="itemcode" size="30" readonly="true"/>
    <a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     	</a>
    <font color=red>*</font>
    </td>
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap  >满X元</td>
    <td><html:text property="overx" size="30" /><font color=red>*</font></td>
 </tr>  
 <tr> 
    <td class="OraTableRowHeader" noWrap  >加X元</td>
    <td><html:text property="addy" size="30" /><font color=red>*</font></td>
 </tr>  
 <!--
 <tr> 
    <td class="OraTableRowHeader" noWrap >使用范围</td>
    <td>
	<html:select property="scope"> 
	<html:option value= "7">--所有--</html:option> 
	<html:option value= "1">--网站适用--</html:option> 
	<html:option value= "2">--CRM适用--</html:option> 
	<html:option value= "3">--门店适用--</html:option> 
	<html:option value= "4">--网站、CRM适用--</html:option> 
	<html:option value= "5">--网站、门店适用--</html:option> 
	<html:option value= "6">--门店、CRM适用--</html:option> 
	</html:select>
    </td>
 </tr>

  <tr> 
    <td class="OraTableRowHeader" noWrap  >销售页面地址</td>
    <td><html:text property="prom_url" size="30" /><font color=red>*</font></td>
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap >生效日期</td>
    <td><html:text property="beginDate" size="30" />
    <a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    终止日期</td><td><html:text property="endDate" size="30" />
    <a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font><font color="red">礼品有效期必须在促销有效期之内</font>
    </td>
 </tr>
 -->
  <tr> 
    <td class="OraTableRowHeader" noWrap >描述</td>
    <td ><html:textarea property="description" rows="5" cols="50" /></td>
 </tr>

 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      
  </tr>
</table>
<input type="hidden" name="promotionid"> 
<input type="hidden" name="name"> 
<html:hidden property="promotionName" />
<html:hidden property="promotionID" />
</html:form>

<p>&nbsp;</p>
</body>
</html>

