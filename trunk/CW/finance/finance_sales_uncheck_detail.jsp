<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function uncheck_f() {
	if (confirm("确定弃审吗?"))
	{
		document.forms[0].action = "finSales.do?type=uncheckSales";
		document.forms[0].btn_uncheck.disabled = "true";
		document.forms[0].submit();
	}
}
function query_f() {
	if (document.forms[0].soNO.value == "")
	{
		alert("请输入单号");
		document.forms[0].soNO.focus();
		return;
	}
	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
function init_f() {
	document.forms[0].soNO.focus();
}
function detail_f() {
	var str = detailTable.style.display;
	if (str == "none")
	{
		view_ctrl.innerText = "隐藏";
		detailTable.style.display = "block";
	}

	if (str == "block")
	{
		view_ctrl.innerText = "显示明细";
		detailTable.style.display = "none";
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_f();">
<html:form action="/finSales.do?type=showUncheckSalesItems" method="POST">
<html:hidden property="status" value="2"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">销售订单弃审</font><font color="838383"> 
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
		<td>
			销售单号：<html:text property="soNO" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btn_query" value=" 查询 " onclick="query_f();">&nbsp;
			<input type="button" name="btn_uncheck" value=" 弃审 " onclick="uncheck_f();">
		</td>
	</tr>
</table>

<br>
<!-- <table width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td width="14%">销售订单号：</td><td width="15%" bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="soNO" /></td>
	<td width="14%">客户代码：</td><td width="15%" bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="customerID" /></td>
	<td width="14%">客户名称：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="customerName" /></td>
  </tr>
  <tr height="26"> 
	<td>销售金额：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="soAmt" format="#0.00"/></td>
	<td>应收金额：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="arAmt" format="#0.00"/></td>
	<td>礼券抵用：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="giftAmt" format="#0.00"/></td>
	
  </tr>
  <tr height="26"> 
	<td>相应单号：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="resNO" /></td>
	<td>发送费：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="deliverAmt" format="#0.00"/></td>
	<td>已付金额：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="payedAmt" format="#0.00"/></td>
  </tr>
  <tr height="26"> 
	<td >业务类型：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="operationClassName" /></td>
	<td>单据日期：</td><td bgcolor="#FFFFFF">&nbsp;<bean:write name="finSalesForm" property="soDate" /></td>
	<td>单据状态：</td><td bgcolor="#FFFFFF">
		<logic:equal name="finSalesForm" property="status" value="1">&nbsp;新建</logic:equal>
		<logic:equal name="finSalesForm" property="status" value="2">&nbsp;确认</logic:equal>
		<logic:equal name="finSalesForm" property="status" value="3">&nbsp;已开票</logic:equal>
		<logic:equal name="finSalesForm" property="status" value="4">&nbsp;开票完成</logic:equal>
	</td>
  </tr>
</table> -->
<%@ include file="finance_sales_detail_common.html" %> 
<!-- <TABLE width="95%" align="center">
<TR>
	<TD><a href="javascript:detail_f();" id="view_ctrl">显示明细</a></TD>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
</TR>
</TABLE>
<bean:define id="list" name="finSalesForm" property="salesDetail"/>

<table id="detailTable" style="display:none" width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>销售价格</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>销售数量</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>金额</th>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="itemCode"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="soPrice" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="soQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="totalAmt" format="#0.00"/></td>
	</tr>
	</logic:iterate>
	<tr>
		<td class=OraTableCellText noWrap align=left colspan="4">合计：</td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="finSalesForm" property="detailAmtTotal" format="#0.00"/></td>
	</tr>
</table> -->
<%@ include file="finance_sales_detail_items_common.html" %>
</html:form>
</body>
</html>
