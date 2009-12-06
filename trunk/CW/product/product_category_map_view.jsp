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
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function getCategory(){
	openWin("prdCatQuery.do","2005",600,400);
}
function getReturnCate(ret) {
    document.forms[0].catalogID.value = ret;
}
function getProduct(para){
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	//document.forms[0].item_code.select();
}

function getOpenwinValue(ret){
	document.forms[0].item_code.value = ret;
}


function checkInput(){
	var form = document.forms[0];
	if(form.item_code.value="") {
	    alert("货号不能为空");
	    return false;
	}
	if(form.catalogID.value="") {
	    alert("分类id不能为空");
	    return false;
	}
	return true;
}

function setfocus() {
    document.forms[0].item_code.focus();
	return true;
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品对应分类</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productCategory.do?type=add" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		<td class="oraTableRowHeader" width="20%">
        货号： 
        </td>
        <td>
        <html:text property="item_code" size="15"/> &nbsp;<font color=red>*</font>&nbsp;
        <a href="javascript:getProduct();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a>
        </td>
        <td></td>
   </tr>
   <tr>
        <td class="oraTableRowHeader" width="20%">    
        分类：
        </td>
        <td> 
        <html:text property="catalogID" size="15"/> &nbsp;<font color=red>*</font>&nbsp;
        <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a>
		</td>
		<td></td>
	</tr>
	<tr>
	 <td colspan=2 align=center>
	 <input name="query" type="submit" class="button5" value=" 保存 ">
     <input  type="button" class="button5" value=" 返回 " onclick="history.back();;">
     </td>
     <td></td>
    </tr>
</table>

</html:form>
</body>
</html>
