<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	
	if(theForm.startDate.value == "" || theForm.endDate.value == "") {
		// 没有查询条件
		alert("请输入起止日期！");
		return false;
	} 
	theForm.offset.value = 0;
	theForm.query.disabled;
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>财务管理 -&gt; 入库上架差异查询</b></font></td>
  </tr>
</table>
<html:form action="/inbound2ShiftDiffQuery.do" onsubmit="return doSearch();">
<bean:define name="inbound2ShiftDiffQueryForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>

<table  align="center" width="95%" border=0 cellspacing=1 cellpadding=5 >
	<TR>
          <TD align=left width="80%" nowarp>创建日期&nbsp;
          <html:text property="startDate" size="10" readonly="true"/>
          <a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
          至<html:text property="endDate" size="10" readonly="true"/>
          <a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>(日期格式：YYYY-MM-DD)
		  &nbsp;&nbsp;<html:radio property="rsType" value="R"/>退货&nbsp;<html:radio property="rsType" value="K"/>扣单
		  &nbsp;&nbsp;&nbsp;<input type="submit" name="query" value=" 查询 ">
          </TD>
	</TR>
</table>
<table>
	<tr>
		<TD><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;差异数量为要求上架的数量减去实际上架数量，若产品数量没有差异则不在下面查询表格中列出</font></TD>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
	<tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table width="95%" align="center" border=0  id="DataTable">
	<tr>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >上架单号</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >上架类型</th>
		<th width="15%"  class="OraTableRowHeader" noWrap align=middle  >创建日期</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >产品代码</th>
		<th width="35%"  class="OraTableRowHeader" noWrap align=middle  >产品名称</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >差异数量</th>
		<th width="15%"  class="OraTableRowHeader" noWrap align=middle  >货架号</th>
	</tr>
    
    <logic:iterate id="list" name="list"> 
	<tr>		
     
    <td class=OraTableCellText noWrap align=center >
	<bean:write name="list" property="rsNO"/>
	</td>
		
    <td class=OraTableCellText noWrap align="center" >
	<logic:equal name="list" property="rsType" value="R">退货</logic:equal>
	<logic:equal name="list" property="rsType" value="K">扣单</logic:equal> 
	</td>
	
	<td class=OraTableCellText noWrap align="center" >
	<bean:write name="list" property="createDate" />
	</td>

	<td class=OraTableCellText noWrap align="center" >
	<bean:write name="list" property="itemCode"/>
	</td>

	<td class=OraTableCellText noWrap align=left >
	<bean:write name="list" property="itemName"/>
	</td>

	<td class=OraTableCellText noWrap align=right >
	<bean:write name="list" property="diffQty" format="0"/>
	</td>

	<td class=OraTableCellText noWrap align="center" >
	<bean:write name="list" property="shelfNO"/>
	</td>

	</tr>
	</logic:iterate>
</table>

</html:form>
</body>
</html>
