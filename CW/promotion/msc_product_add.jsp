<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.promotion.entity.*"%>
<%
String pricelist_id=request.getParameter("pricelist_id");
%>
<%
Pricelist_line c=new Pricelist_line();
String actionurl="";
String title="";
String buttonvalue="";
c=(Pricelist_line)request.getAttribute("p");
//out.println(c.getID());
if(c.getID()>0){
actionurl="/Pricelist_line.do?type=modify2";
title="活动产品修改";
buttonvalue="修改";
}else{
actionurl="/Pricelist_line.do?type=add2";
title="活动产品新增";
buttonvalue="提交";
}
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript">

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
}
function getOpenwinValue(ret){
	document.forms[0].item_id_key.value = ret;
}

function winFocus(){
	document.forms[0].item_id_key.focus();
	return true;	
}

function addSubmit() {
<%if(c.getID()==0){%>
	if(document.forms[0].item_id_key.value==""){
		alert('货号不能为空!');
		document.forms[0].item_id_key.focus();
		return false;
	}
<%}%>
	if(document.forms[0].sell_type.value==""){
		alert('销售方式不能为空!');
		document.forms[0].sell_type.focus();
		return false;
	}
 
  	if(document.forms[0].sale_price.value == ""){
		alert("会员价不能为空！");
		document.forms[0].sale_price.focus();
		return false;
	}
	if (isNaN(document.forms[0].sale_price.value)){
			alert("会员价应该为数字！");
			document.forms[0].sale_price.focus();
			return false;
	} 	
  	if(document.forms[0].vip_price.value == ""){
		alert("vip价不能为空！");
		document.forms[0].vip_price.focus();
		return false;
	}
	if (isNaN(document.forms[0].vip_price.value)){
			alert("vip价应该为数字！");
			document.forms[0].vip_price.focus();
			return false;
	} 

			    
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">促销管理</font><font color="838383"> 
      		-&gt; </font><font color="838383"><%=title%></font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form method="post" action="<%=actionurl%>" onsubmit="return addSubmit();">

<table  border=0 cellspacing=1 cellpadding=1  width="700" align="center" class="OraTableRowHeader" noWrap  >
	<tr bgcolor="#FFFFFF">
		<td align="right"  ><font color=red>*</font>&nbsp;货号</td>
		<td width="*%" align="left">
<input type="hidden" name="item_code" value="<%=c.getItem_code()%>"> 
<%if(c.getID()>0){%>

<%=c.getItem_code()%>
<%}else{%>
<input  name="item_id_key" value="" readonly >
<a href="javascript:getProduct();">
<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
</a>
&nbsp;<span id="item_id_display" name="item_id_display" ></span>
<%}%>

		</td>
	</tr>
	<input type=hidden name="sell_type" value="0">
	<tr  bgcolor="#FFFFFF" >
		<td align="right" ><font color=red>*</font>&nbsp;会员价</td>
		<td  align="left" >
			<input name="sale_price" value="<%=c.getSale_price()%>">
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF" >	
		<td align="right"  bgcolor="#FFFFFF"><font color=red>*</font>&nbsp; VIP价</td>
		<td  align="left" >
			<input name="vip_price" value="<%=c.getVip_price()%>">
		</td>
		
	</tr>			
	
	<tr height="40"  bgcolor="#FFFFFF">
		<td align="center" colspan=2>
		<input type="submit"  value="<%=buttonvalue%>" > &nbsp;
		<input type="reset" class="button2" value=" 取消 " onclick="javascript:history.back();">
<input type=hidden name="pricelist_id" value="<%=pricelist_id%>">
<input type="hidden" name="id" value="<%=c.getID()%>" >		
	</tr>
</table>
</html:form>

</body>
</html>
