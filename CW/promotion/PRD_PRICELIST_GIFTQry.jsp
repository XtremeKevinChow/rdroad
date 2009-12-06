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

function valid(code) {
    document.forms[0].action="prd_pricelist_gift.do?type=valid&id=" + code;
    document.forms[0].submit();
}
function invalid(code) {
    document.forms[0].action="prd_pricelist_gift.do?type=invalid&id=" + code;
    document.forms[0].submit();
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

<html:form action="prd_pricelist_gift.do?type=init" method="post">
<html:hidden property="pricelist_id"/>
<table width="90%"  cellspacing="1" border="0"  align="center" noWrap >
	<tr class="oraTableRowHeader" noWrap>
		<td width="20%">货号</td>
		<td width="20%">名称</td>
		<td width="20%">价格</td>
		<td width="20%">是否有效</td>
		<td width="20%">操作</td>
	</tr>
	<% int i=0; %>
  <logic:iterate id="info" name="list" >
	<tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> >
		<td ><bean:write name="info" property="item_code" /></td>	
		<td ><bean:write name="info" property="item_name" /></td>
		<td >
		<bean:write name="info" property="price"/>
		</td>	
		<td>
		<logic:equal name="info" property="status" value="0">
		有效
		</logic:equal>
		<logic:equal name="info" property="status" value="-1">
		<font color=red>无效</font>
		</logic:equal>
		</td>
		<td align="center">
		<logic:equal name="info" property="status" value="0">
		<input type="button" value=" 禁用 " onclick="invalid(<bean:write name="info" property="id"/>)">
		</logic:equal>
		<logic:equal name="info" property="status" value="-1">
		<input type="button" value=" 启用 " onclick="valid(<bean:write name="info" property="id"/>)">
		</logic:equal>	
		
		</td>
    </tr>
 <% i++; %>
</logic:iterate>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center">
		<input type="submit" value=" 新 增 " >
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
