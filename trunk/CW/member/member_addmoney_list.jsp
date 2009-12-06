<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String flag = (String)request.getAttribute("flag");
String isAdd = (String)request.getAttribute("isAdd");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	var nCondition = 0;
	if(trim(theForm.CREATE_DATE.value) != "") nCondition++;
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入日期查询条件！");
		return;
	} 

	theForm.queryBtn.disabled = true;
	theForm.submit();
}
function modify_f(id, obj) {
	var theForm = document.forms[0];
	theForm.action = "memberaddMoneyManage.do?type=initModify&ID="+id;
	obj.disabled = true;
	theForm.submit();
}
function delete_f(id, obj) {
	var theForm = document.forms[0];
	if (confirm("确实要删除吗？"))
	{
		theForm.action = "memberaddMoneyManage.do?type=delete&ID="+id;
		obj.disabled = true;
		theForm.submit();
	}
	
}
function load_f() {
	var obj = document.getElementById("DataTable");
	var len = obj.rows.length;
	var total_money = 0;
	if (len > 2) // 表头、表为不在计算之列
	{
		for ( var i = 1; i < len - 1 ; i ++ )
		{
			var col = obj.rows[i].cells[4];
			var money = parseFloat(col.innerText);
			if (!isNaN(money))
			{
				total_money += money;
			}
		}
	}
	document.getElementById("totalMoney").innerText = Math.round(total_money, 2);
}
//-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f();">
<table width="95%" border="0" cellspacing="0" cellpadding="0" >
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>帐户管理 -&gt; 现金（回单）查询</b></font></td>
  </tr>
</table>
<html:form action="memberaddMoneyManage.do?type=query" onsubmit="return doSearch();">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
		汇号：<html:text property="searchRefId" size="19"/>
        姓名：<html:text property="searchMbName" size="9"/>
		<font color=red>*</font>录入日期：
		<html:text property="CREATE_DATE"  readonly="true" size="10"/><a href="javascript:calendar(document.forms[0].CREATE_DATE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		用途：<html:select property="searchUseType" style="width:70">
		    <option value="" <logic:equal name="memberaddmoneyForm" property="searchUseType" value="">selected</logic:equal>>选择...</option>
			<option value="0" <logic:equal name="memberaddmoneyForm" property="searchUseType" value="0">selected</logic:equal>>预存款</option>
			<option value="1" <logic:equal name="memberaddmoneyForm" property="searchUseType" value="1">selected</logic:equal>>购买书香卡</option>
		</html:select>
		支付方式：
		<html:select property="searchPayMethod" style="width:70">
			<option value="-1" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="-1">selected</logic:equal>>选择...</option>
			<option value="14" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="14">selected</logic:equal>>现金</option>
			<option value="90" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="90">selected</logic:equal>>建行回单</option>
		</html:select>
		<input type="button" name="queryBtn" value=" 查询 " onclick="doSearch();">&nbsp;&nbsp;
	  <font color="red"><%=request.getAttribute("LOGIC MESSAGE")==null?"":request.getAttribute("LOGIC MESSAGE")%></font>
		</td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >汇号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >姓名</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >金额</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >单据日期</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >用途</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >付款方式</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作</th>
	</tr>
    
    <logic:iterate id="list" name="list"> 
	<tr>		
     
    <td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<bean:write name="list" property="REF_ID"/>
	</td>
		
    <td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="list" property="ORDER_CODE"/>
	</td>
	
	<td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="list" property="MB_CODE"/>
	
	</td>

	<td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="list" property="MB_NAME"/>
	</td>

	<td class=OraTableCellText noWrap align="right" class=OraTableCellText>
	<bean:write name="list" property="MONEY" format="#0.00"/>
	</td>

	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<bean:write name="list" property="bill_date"/>
	</td>

	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<logic:equal name="list" property="USE_TYPE" value="0">
	预付款
	</logic:equal>
	<logic:equal name="list" property="USE_TYPE" value="1">
	购买书香卡
	</logic:equal>
	</td>
	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<logic:equal name="list" property="payMethod" value="14">
	现金
	</logic:equal>
	<logic:equal name="list" property="payMethod" value="90">
	建行回单
	</logic:equal>
	</td>
	<td class=OraTableCellText noWrap align=center class=OraTableCellText>
	<input type=button name="updateBtn" value="修改" onclick="modify_f(<bean:write name="list" property="ID"/>,this)">
	<input type=button name="deleteBtn" value="删除" onclick="delete_f(<bean:write name="list" property="ID"/>,this)">
	</td>
	</tr>
	</logic:iterate>
	<tr>
	  <td align="right"  colspan=4><b>合计：</b></td>
	  <td id="totalMoney" align="right"></td>
	  <td bgcolor="#FFFFFF" noWrap align="center" colspan="4"></td>
	</tr>	
</table>

</html:form>
</body>
</html>
