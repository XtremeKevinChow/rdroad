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

function query_f() {
	var frm = document.forms[0];
	if (frm.proNO.value == "" && frm.proName.value == "" && frm.factAPCode.value == "" && frm.startDate.value == "" && frm.endDate.value == "")
	{
		alert("请输入查询条件");
		frm.proNO.focus();
		return;
	}
	if (document.forms[0].startDate.value == "")
	{
		
	} else {
		var edate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }
	}	

	if (document.forms[0].endDate.value == "")
	{
		
	} else {
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return;
		 }
	}	

	frm.submit();
}
function modify_f(apID) {

	location.href = "finPurchaseInvoiceManage.do?type=showPurchasesItemsModify&apID=" + apID;
}

function cancel_f(apID) {
	if (confirm("确定要取消吗?"))
	{
		location.href = "finPurchaseInvoiceManage.do?type=cancelPurchase&apID=" + apID;
	}
	
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="finPurchaseInvoiceManage.do?type=query" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">采购发票查询</font><font color="838383"> 
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
			供应商代码：
			<html:text property="proNO" size="10"/>&nbsp;&nbsp;&nbsp;
		</td>
		<td>
			供应商名称：
			<html:text property="proName" size="35"/>
		</td>
	</tr>
  <tr>
		<td>
			发　票　号：
			<html:text property="factAPCode" size=""/>&nbsp;&nbsp;&nbsp;
		</td>
		<td>
			发票日期：　
			<html:text property="startDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;到
			<html:text property="endDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
			
		</td>		
	</tr>
	<!-- 货号对应creator为了避免破坏Form的结构 -->
	<tr>
		<td>产　品　号：
			<html:text property="creator" size="10"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
			<input type="button" value=" 查询 " onclick="query_f();">
		</td>
	<tr>
</table>
			
			
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>采购发票号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>发票日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>供应商代码</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>供应商名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>单据日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>单据状态</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>操作</th>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=left >
		<a href="./finPurchaseInvoiceManage.do?type=showPurchasesItemsView&apID=<bean:write name="list" property="apID"/>">
		<bean:write name="list" property="factAPCode"/></a></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="invoiceDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="proNO"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="proName"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="createDate"/></td>
		<td class=OraTableCellText noWrap align=middle >
		<logic:equal name="list" property="status" value="1">新建</logic:equal>
		<logic:equal name="list" property="status" value="2">审核</logic:equal>
		<logic:equal name="list" property="status" value="3">记帐</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="status" value="1">
			<input type="button" value="修改" onclick="modify_f('<bean:write name="list" property="apID"/>');">
			<input type="button" value="取消" onclick="cancel_f('<bean:write name="list" property="apID"/>');">
			</logic:equal>
		</td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
