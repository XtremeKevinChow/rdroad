<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String isreport=request.getParameter("isreport");
       isreport=(isreport==null)?"":isreport;
%>

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

function selectItem(){

	var selectedid = getSelectedItem();
	if(selectedid !="") {
	    
	    //opener.document.forms[0].item_code.value=selectedid;
		opener.getOpenwinValue(selectedid);
	    self.close();
	}
	
}
function setfocus(){
	document.forms[0].qry_item_code.focus();
	return true;
}

function querystock(){
    if(document.forms[0].qry_item_code.value=="") {
        alert("请输入需要查询库存的货号");
        return;
    }
	document.forms[0].action="product2Query.do?type=query4stock";
	document.forms[0].submit();
	
}


</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">货号查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>


<html:form action="/product2Query.do?type=query4order" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td >货号： 
        <html:text property="qry_item_code" size="12"/>
        产品名： 
        <html:text property="qry_item_name" size="16"/>
        &nbsp; 
        <html:select property="qry_item_category" >
		<html:option value="0">--所有--</html:option>
        <html:optionsCollection   property="cates" value="id" label="name"/> 
		</html:select>
        <input name="query" type="submit"  value=" 查询 ">
        <input name="querysku" type="button"  value=" 查询库存 " onclick="querystock();">
		</td>
	</tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" align="center" onclick="changeItem()" ondblclick="selectItem()" border=0 cellspacing=1 cellpadding=2 >
    <tr height="26" > 
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >货号</th>
      <th width="12%"  class="OraTableRowHeader" noWrap align=middle  >产品名称</th>
      <th width="8%"  class="OraTableRowHeader" noWrap align=middle  >颜色</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >尺寸</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >市场价</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >会员价</th>
      <th width="8%"  class="OraTableRowHeader" noWrap   align=middle  >VIP价</th>
      <th width="8%"  class="OraTableRowHeader" noWrap   align=middle  >可用数量</th>
	</tr>
    <% int i=0; %>
    <bean:define id="item" name="product2Form" property="items" type="java.util.Collection"/>
    <logic:iterate id="product" name="item">
    
    <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> style="cursor:hand" id ="<bean:write name="product" property="item_code"/>"> 
      <td noWrap align=middle ><bean:write name="product" property="item_code" /></td>
      <td  noWrap align=right ><bean:write name="product" property="item_name" /></td>
      <td  noWrap align=right ><bean:write name="product" property="color_name" />(<bean:write name="product" property="color_code" />)</td>
      <td  noWrap align=right ><bean:write name="product" property="size_code" /></td>
      <td  noWrap align=right ><bean:write name="product" property="standard_price"  format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="sale_price"  format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="vip_price"  format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="availQty"  format="0.00"/></td>
    </tr>
    <% i++; %>
    </logic:iterate>
    <tr><td colspan="3"><font color="red">使用说明:请双击选中产品。</font></td></tr>
  </table>

</html:form>

</body>
</html>
