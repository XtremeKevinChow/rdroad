<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	
}

function getOpenwinValue(ret){
	document.forms[0].item_code.value = ret;
}


function checkSubmit() {
    if(document.forms[0].item_code.value==""){
           alert("请填写促销礼品号");
           document.forms[0].item_code.select();	
           return false;
        }
	if(isNaN(document.forms[0].price.value)||document.forms[0].price.value==""){
    	alert('促销品价格只能为数字!');
    	document.forms[0].price.select();
    	return false;
	}
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
    	<b><font color="838383">当前位置</font></b><font color="838383"> : </font>
    	<font color="838383">促销管理</font><font color="838383">-&gt; </font>
    	<font color="838383">招募礼品管理</font><font color="838383">&nbsp; </font>
    	</td>
      	
   </tr>
</table>

<html:form action="prd_pricelist_gift.do?type=insert" method="post" onsubmit="return checkSubmit();">
<html:hidden property="pricelist_id"/>

<table width="80%"  cellspacing="1" border="0"  align="center" noWrap >
    <tr>
		<td class="oraTableRowHeader">礼品号</td>	
		<td ><html:text property="item_code" readonly="true"/>
		    <a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     	    </a>
     	    &nbsp;<font color=red>*</font>
		</td>
	</tr>
	<tr>
		<td class="oraTableRowHeader">价格</td>	
		<td ><html:text property="price" value=""/>
		&nbsp;<font color=red>*</font>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		    <input type="submit" value=" 确 定 "><input type="button" value=" 返 回 " onclick="history.back();">
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
