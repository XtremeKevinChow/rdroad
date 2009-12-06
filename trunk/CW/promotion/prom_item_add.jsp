<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String promotionid=request.getParameter("promotionid");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>

<script language="javascript">

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	
}

function getOpenwinValue(ret){
	document.forms[0].item_code.value = ret;
}

function f_checkData() {
        if(document.forms[0].item_code.value==""){
           alert("请填写促销货号");
           document.forms[0].item_code.select();	
           return false;
        }

		
 	document.forms[0].input.disabled = true;
}
function initFocus(){
	document.forms[0].item_code.select();
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
      		-&gt; </font><font color="838383">新增促销组产品</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form action="prom_ItemOperation.do?type=add" method="post" onsubmit="return f_checkData();">
  

 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap  >促销货号</td>
    <td><input type=text name="item_code" size=30 readonly>
    <a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     	</a>
    <font color=red>*</font></td>
 </tr> 

 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      
  </tr>
</table>
<input type="hidden" name="promotionid" value="<%=promotionid%>">
</form>

<p>&nbsp;</p>
</body>
</html>

