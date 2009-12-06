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
<script language="javascript">
function getCategory(){
	
	//openWin("/mrm/product/product_category_query.jsp","2005",600,400);
	openWin("../product/prdCatQuery.do","2005",600,400);

}
function getReturnCate(selId) {
    document.forms[0].item_category.value= selId;
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
      		-&gt; </font><font color="838383">促销分类</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>


<html:form action="promotionOperation.do?type=saveCategory" method="post" >  
  <!-- 隐含主键 -->
 <html:hidden property="id"/>
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap   width="30%">促销名称</td>
    <td><html:text property="name" size="30" readonly="true"/>
	<font color=red>*</font></td>
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap >选择分类</td>
    <td>
    <html:text property="item_category"/>
	<a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a>
    </td>
 </tr>
  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input type="submit" name="input" class="button2" value=" 确定 "> 
      &nbsp; 
	  <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      
  </tr>
</table>
</html:form>
<p>&nbsp;</p>
</body>
</html>

