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
function f_checkData() {
	if(!f_checkOrderRequire()) {
		alert("购物金额输入不正确");
		return false;
	}
	if(!f_checkItemCode()) {
		alert("货号输入不正确");
		return false;
	}
	if(!f_checkGoldPrice()) {
		alert("金卡价输入不正确");
		return false;
	}
	if(!f_checkSilverPrice()) {
		alert("银卡价输入不正确");
		return false;
	}
	if(!f_checkWebPrice()) {
		alert("网站价输入不正确");
		return false;
	}
	if(!f_checkStartDate()) {
		alert("生效日期输入不正确");
		return false;
	}
	if(!f_checkEndDate()) {
		alert("终止日期输入不正确");
		return false;
	}
		
 	return true;
}

function f_checkOrderRequire() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].order_require.value);
}
function f_checkGoldPrice() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].gold_price.value);
}
function f_checkSilverPrice() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].silver_price.value);
}
function f_checkWebPrice() {
	var regex = /^((\d+)(\.\d{0,1})?)?$/;
	return regex.exec(document.forms[0].web_price.value);
}
function f_checkItemCode() {
	var regex = /^\w\d{5}$/;
	return regex.exec(document.forms[0].item_code.value);
}
function f_checkStartDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].start_date.value);
}
function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].end_date.value);
}
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
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

<html:form action="/salePromotion.do?type=add" method="post" onsubmit="return f_checkData();">
  
<table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap >对应促销活动</td>
    <td>
    <html:select property="sel_msc">
    	<OPTION value="" selected>老会员促销活动</OPTION> 
    	<html:optionsCollection  property="msc_codes" value="msc_code" label="msc_name"/>
    </html:select><font color=red>*</font>
    </td>
 </tr>
</table>
<p>
<table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
  <tr> 
    <td >购买
    <html:select property="group_id">
    	<html:optionsCollection  property="groups" value="group_id" label="group_name"/>
    </html:select>&nbsp;中的产品,金额满&nbsp;<html:text property="order_require" size="5"/><font color=red>*</font>&nbsp;元可享受以下优惠
    </td>
 </tr>
 </table>
 <p>
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    货号</td><td><html:text property="item_code"/><font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    金卡价</td><td><html:text property="gold_price"/><font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    银卡价</td><td><html:text property="silver_price"/><font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    网站特价</td><td><html:text property="web_price"/>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    生效日期</td><td><html:text property="start_date"/>
    <a href="javascript:calendar(document.forms[0].start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    终止日期</td><td><html:text property="end_date"/>
    <a href="javascript:calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font>
    </td>
 </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap >
    作用范围</td><td>
    <html:select property="scope">
    <html:option value="1">网下有效</html:option>
    <html:option value="2">网上有效</html:option>
    <html:option value="3">网上网下均有效</html:option>
    </html:select>
    </td>
 </tr>
 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      
  </tr>
</table>
</html:form>

<p>&nbsp;</p>
</body>
</html>

