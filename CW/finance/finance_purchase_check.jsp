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
function checkAll(bln, type) {
	
	var len = DataTable.rows.length;
	for (var i = 1; i < len; i ++) {
		var row = DataTable.rows(i);
		if(bln) {
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}

function check_f() {
	var flag = false;
	var len = DataTable.rows.length;
	for (var i = 1; i < len; i ++)
	{
		var row = DataTable.rows(i);
		if (row.getElementsByTagName("INPUT")[0].checked == true)
		{
			flag = true;
			break;
		}
	}
	if (flag == true)//有记录打上钩
	{
		if (confirm("确定审核这(些)记录吗?"))
		{
			document.forms[0].action = "finPurchase.do?type=checkPurchases";
			document.forms[0].btn_check.disabled = "true";
			document.forms[0].submit();
		}
	}
	else 
	{
		alert("请选择记录!");
		return;
	}
	
}
function query_f() {

	if (document.forms[0].startDate.value == "")
	{
		alert("开始日期不能为空");
		document.forms[0].startDate.focus();
		return;
	} else {
		
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }

	}

	if (document.forms[0].endDate.value == "")
	{
		alert("结束日期不能为空");
		document.forms[0].endDate.focus();
		return;
	} else {
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return;
		 }
	}

	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/finPurchase.do?type=query" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">采购到货单审核</font><font color="838383"> 
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
			开始日期：
			<html:text property="startDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
			结束日期：
			<html:text property="endDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btn_query" value=" 查询 " onclick="query_f();">
			<input type="button" name="btn_check" value=" 审核 " onclick="check_f();">
		</td>
	</tr>
</table>
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>
		全选<input type="checkbox" onclick="checkAll(this.checked, 0)"></th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>采购到货单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>相应单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>相应日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>供应商</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>供应商名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>业务类型</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>采购类型</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>创建日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>状态</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>制单人</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<input type="checkbox" name="psIDs" value=<bean:write name="list" property="psID"/> >
		</td>

		<td class=OraTableCellText noWrap align=middle >
		<a href="./finPurchase.do?type=showPurchasesItems&psID=<bean:write name="list" property="psID"/>">
		<bean:write name="list" property="psCode"/>
		</a>
		</td>

		<td class=OraTableCellText noWrap align=middle >
		<bean:write name="list" property="resNO"/>
		</td>
		<td class=OraTableCellText noWrap align=middle >
		<bean:write name="list" property="purchaseDate"/>
		</td>
		
		<td class=OraTableCellText noWrap align=middle >
		<bean:write name="list" property="proNO"/>
		</td>

		<td class=OraTableCellText noWrap align=left >
		<bean:write name="list" property="proName"/>
		</td>

		<td class=OraTableCellText noWrap align=left >
		<bean:write name="list" property="operationClassName"/>
		</td>

		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="purType" value="1">一般采购</logic:equal>
		<logic:equal name="list" property="purType" value="2">其他出入库单</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left >
		<bean:write name="list" property="createDate"/>
		</td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="status" value="1">新建</logic:equal>
		<logic:equal name="list" property="status" value="2">审核</logic:equal>
		<logic:equal name="list" property="status" value="3">部分结算</logic:equal>
		<logic:equal name="list" property="status" value="4">完全结算</logic:equal>
		</td>

		<td class=OraTableCellText noWrap align=left >
		<bean:write name="list" property="creator"/>
		</td>
	</tr>
	</logic:iterate>
	
</table>
</html:form>
</body>
</html>
