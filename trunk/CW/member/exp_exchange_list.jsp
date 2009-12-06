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
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	var nCondition = 0;
	if(trim(theForm.cardID.value) != "") nCondition++;
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入查询条件！");
		theForm.cardID.focus();
		return false;
	} 

	theForm.query.disabled;
}

function exchange_f(obj) {
	
	var flag = false;
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if (row.getElementsByTagName("INPUT")(0).checked == true)
		{
			flag = true;
			break;
		}
		
	}
	if (flag == true)
	{
		if (confirm("确实要兑换吗?"))
		{
			if ("<%= flag %>" == "order") //购物车传递过来的标记
			{
				document.forms[0].action = "/order/orderAddSecond.do?type=addExchangeGift&isAdd=<%=isAdd%>";
				document.forms[0].target="main";
				document.forms[0].submit();
				window.close();
			} else {
				document.forms[0].action = "/member/expExchange.do?type=expChangeGift";
				
				document.forms[0].submit();
			}
			
			
		}
		
	} else {
		alert("请选择记录!");
	}
}

//全选充值
function checkAll(bln, type) {
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
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
//初始化
function init() {
	if ("<%= flag %>" == "order") //购物车传递过来的标记
	{
		document.forms[0].cardID.readOnly = true;
		//document.forms[0].query.disabled = true;
	} else {
		//document.forms[0].cardID.readOnly = false;
		//document.forms[0].query.disabled = false;
		document.forms[0].cardID.focus();
	}
	
	if (document.forms[0].cardID.value == "")
	{
		return;
	}
	var amountExp = document.forms[0].amountExp.value;
	for (var i = 1; i < DataTable.rows.length; i ++)
	{
		
		var exp = DataTable.rows(i).cells(4).innerText;
		
		if (amountExp - exp >= 0)
		{
			DataTable.rows(i).cells(0).getElementsByTagName("INPUT")[0].disabled = false;

		} else {
			DataTable.rows(i).cells(0).getElementsByTagName("INPUT")[0].disabled = true;

		}
	}
}

//-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init()">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>会员管理 -&gt; 积分兑换礼品列表</b></font></td>
  </tr>
</table>
<html:form action="/expExchange.do?type=queryValidGift" onsubmit="return doSearch();">
<input name="isAdd" value="<%=isAdd%>" type="hidden">
<input name="flag" value="<%=flag%>" type="hidden">
<table  align="center" width="95%" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>会员卡号：
		<html:hidden property="memberID" />
		<html:text property="cardID" size="12" />&nbsp;&nbsp;&nbsp;&nbsp;
		礼品号：
		<html:text property="itemCode" size="12" />
		&nbsp;
		<input type="submit" name="query" value=" 查询 ">
		</td>
		
	</tr>
	<logic:notEqual name="expExchangeForm" property="cardID" value="">
	<tr>
		<td><span class="style1">会员姓名：</span><bean:write name="expExchangeForm" property="memberName"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="style1">年度积分：</span><bean:write name="expExchangeForm" property="amountExp"/>
			<html:hidden property="amountExp" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="addBtn" value=" 兑换 " onclick="exchange_f(this)">
		</td>
		
	</tr>
	</logic:notEqual>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  
</table>
<table width="95%" align="center" border=0  id="DataTable">
	<tr>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  ><!-- 全选<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"> --></th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >货号</th>
		<th width="30%"  class="OraTableRowHeader" noWrap align=middle  >产品名称</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >库存状态</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >兑换积分</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >兑换价格</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >开始日期</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >结束日期</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >有效天数</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >是否有效</th>
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
     
	<td align=center noWrap class=OraTableCellText class=OraTableCellText >
	<input type="radio" disabled="true" name="selectedID" value="<bean:write name="giftList" property="ID"/>">
	</td>

    <td class=OraTableCellText noWrap align=center class=OraTableCellText>
	<bean:write name="giftList" property="itemCode"/>
	</td>
		
    <td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="giftList" property="itemName"/>
	</td>
	
	<td class=OraTableCellText noWrap align="center" class=OraTableCellText>
	<bean:write name="giftList" property="stockStatus"/>
	<!-- <logic:equal name="giftList" property="stockStatus" value="库存正常">库存正常</logic:equal>
	<logic:equal name="giftList" property="stockStatus" value="仓库缺货"><font color="red">仓库缺货</font></logic:equal> -->
	</td>

	<td class=OraTableCellText noWrap align="right" class=OraTableCellText>
	<bean:write name="giftList" property="expStart" format="#0"/>
	</td>

	<td class=OraTableCellText noWrap align="right" class=OraTableCellText>
	<bean:write name="giftList" property="exchangePrice" format="#0.00"/>
	</td>

	<td class=OraTableCellText noWrap align=center class=OraTableCellText>
	<bean:write name="giftList" property="startDate"/>
	</td>

	<td class=OraTableCellText noWrap align=center class=OraTableCellText>
	<bean:write name="giftList" property="endDate"/>
	</td>

	<td class=OraTableCellText noWrap align="right" class=OraTableCellText>
	<bean:write name="giftList" property="validDay"/>
	</td>

	<td class=OraTableCellText noWrap align="center" class=OraTableCellText>
	<bean:write name="giftList" property="validFlag"/>
	</td>

	</tr>
	</logic:iterate>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center">
			
			<input type="button" name="addBtn" value=" 兑换 " onclick="exchange_f(this)">&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
