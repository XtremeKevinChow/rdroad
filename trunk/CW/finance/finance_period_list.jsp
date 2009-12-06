<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function detail_f(id) {
	location.href = "./period.do?type=initModify&ID=" + id;
}

function add_f(obj) {
	document.forms[0].action = "./period.do?type=initAdd";
	obj.disabled = true;
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/period.do" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">会计期查询</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><input type="button" value=" 新增 " onclick="add_f(this);"></td>
	</tr>
</table>
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>会计年</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>会计月</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>开始日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>结束日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否启用</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否关闭</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<a href="javascript:detail_f('<bean:write name="list" property="ID"/>');">
		<bean:write name="list" property="year"/>
		</a>
		</td>

		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="month"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="beginDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="endDate"/></td>
		<td class=OraTableCellText noWrap align=middle >
		<logic:equal name="list" property="isUsed" value="0">否</logic:equal>
		<logic:equal name="list" property="isUsed" value="1">是</logic:equal>
		</td>

		<td class=OraTableCellText noWrap align=middle >
		<logic:equal name="list" property="isClosed" value="0">否</logic:equal>
		<logic:equal name="list" property="isClosed" value="1">是</logic:equal>
		</td>

	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
