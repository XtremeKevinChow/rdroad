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
function checkInput(){
	var form = document.forms[0];
	return true;
}

function delete_rec(item_code,category_id) {
    document.forms[0].action="productCategory.do?type=delete&item_code=" + item_code + "&catalog_id=" + category_id;
    document.forms[0].submit();
}

function addCategory() {
    document.forms[0].action="productCategory.do?type=view";
    document.forms[0].submit();
}
function setfocus() {
    document.forms[0].qry_item_code.focus();
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
      		-&gt; </font><font color="838383">产品对应分类查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productCategory.do?type=list" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
        <td width="80%">
        货号： 
        <html:text property="qry_item_code" size="15"/>
        分类号： 
        <html:text property="qry_category_code" size="12"/>
        <input name="query" type="submit" class="button5" value=" 查询 ">
        <input name="query" type="button" class="button5" value=" 新增 " onclick="javascript:addCategory();">
		</td>
		<td></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<!--产品查询列表begin-->

	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >

    <tr height="26" align="center" class="OraTableRowHeader"> 
      <th >货号</th>
      <th >名称</th>
      <th >分类号</th>
      <th >分类名称</th>
	  <th >操作</th>
    </tr>
    <% int i = 0; %>
    <bean:define id="items" name="list" type="java.util.Collection"/> 
    <logic:iterate id="item" name="items">
    <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> >  
      <td  noWrap align=right ><bean:write name="item" property="item_code" /></td>
      <td  noWrap align=right ><bean:write name="item" property="item_name" /></td>
      <td  noWrap align=right ><bean:write name="item" property="catalog_code" /></td>
      <td  noWrap align=right ><bean:write name="item" property="catalog_name" /></td>
      <td  noWrap align=right >
			<input type="button" name="delete" value="删除" class="button5" onclick="javascript:delete_rec('<bean:write name="item" property="item_code" />','<bean:write name="item" property="catalog_id" />');">
			</td>
	</tr>
    <% i++; %>
    </logic:iterate>
    
  </table>

</html:form>

</body>
</html>
