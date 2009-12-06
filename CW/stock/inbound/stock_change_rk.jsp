<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
response.addHeader("Cache-Control", "no-cache");
response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
%>
<html>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">

<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function getOpenwinValue(ret){
	
	var frm = document.forms[0];
	var index = frm.whichLine.value;

	
	if (typeof(frm.itemID.length) == "undefined")
	{
		frm.itemID.value = ret[0];
		DataTable.rows[index].cells[1].innerHTML = "&nbsp;<font color=\"red\">"+ret[1]+"</font>&nbsp;<input type=\"button\" value=\"..\" title=\"换货\" onclick=\"openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);document.forms[0].whichLine.value=this.parentElement.parentElement.rowIndex;\">";
		DataTable.rows[index].cells[1].setAttribute("align","left");
		DataTable.rows[index].cells[2].innerHTML = "<font color=\"red\">"+ret[2]+"</font>";

	} else {
		
		frm.itemID[index - 1].value = ret[0];
		DataTable.rows[index].cells[1].innerHTML = "&nbsp;<font color=\"red\">"+ret[1]+"</font>&nbsp;<input type=\"button\" value=\"..\" title=\"换货\" onclick=\"openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);document.forms[0].whichLine.value=this.parentElement.parentElement.rowIndex;\">";
		DataTable.rows[index].cells[1].setAttribute("align","left");
		DataTable.rows[index].cells[2].innerHTML = "<font color=\"red\">"+ret[2]+"</font>";
	}
}

function checkAll(bln, type) {
	
	var len = DataTable.rows.length;
	for (i = 1; i < len; i++) {
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

function save_f() {
	if (document.forms[0].sn_code.value == "")
	{
		alert("发货单号不能为空");
		document.forms[0].sn_code.focus();
		return;
	}
	if (document.forms[0].consignee.value == "")
	{
		alert("请输入收货人");
		document.forms[0].consignee.focus();
		return;
	}
	if (document.forms[0].conDate.value == "")
	{
		alert("请输入收货日期");
		document.forms[0].conDate.focus();
		return;
	}
	if (document.forms[0].checker.value == "")
	{
		alert("请输入质检人");
		document.forms[0].checker.focus();
		return;
	}
	if (document.forms[0].cheDate.value == "")
	{
		alert("请输入质检日期");
		document.forms[0].cheDate.focus();
		return;
	}
	if (document.forms[0].rrNO.value == "")
	{
		alert("请输入换货原因");
		document.forms[0].rrNO.focus();
		return;
	}
	for(var i = 1; i < DataTable.rows.length; i ++) {
		
		//如果没有check就将隐含的shiDtlIDs置为-1
		if (!DataTable.rows(i).cells(0).children(0).checked) {	
			DataTable.rows(i).cells(0).children(1).value = "-1";

		}


	}

	document.forms[0].purNO.value = document.forms[0].sn_code.value;
	document.forms[0].purID.value = document.forms[0].sn_id.value;
	document.forms[0].action = "changeInbound.do?type=rk";
	document.forms[0].btn_save.disabled = true;
	document.forms[0].submit();

}

function query_f() {
	if (document.forms[0].purNO.value == "")
	{
		alert("请输入发货单号");
		document.forms[0].purNO.focus();
		return;
	}

	document.forms[0].btn_query.disabled = true;
	document.forms[0].submit();
}
function init_f() {
	document.forms[0].purNO.focus();
}

function resetQty(obj, index) {
	var frm = document.forms[0];

	if (typeof(frm.goodQty.length) == "undefined")
	{
		
		if ( !is_number(frm.goodQty.value) || !is_number(frm.badQty.value) )
		{
			alert("请输入正整数");
			return;
		}
		if ( parseInt(obj.value) > parseInt(frm.qty.value) )
		{
			alert("不能超过发货数量");
			obj.focus();
			return;
		}
		if (obj.name == "goodQty")
		{
			frm.badQty.value = parseInt(frm.qty.value) - parseInt(obj.value);
		} else {
			frm.goodQty.value = parseInt(frm.qty.value) - parseInt(obj.value);
		}
	} else {
		
		if ( !is_number(frm.goodQty[index-1].value) || !is_number(frm.badQty[index-1].value) )
		{
			alert("请输入正整数");
			return;
		}
		if ( parseInt(obj.value) > parseInt(frm.qty[index-1].value) )
		{	
			alert("不能超过发货数量");
			obj.focus();
			return;
		}
		if (obj.name == "goodQty")
		{
			frm.badQty[index-1].value = parseInt(frm.qty[index-1].value) - parseInt(obj.value);
		} else {
			frm.goodQty[index-1].value = parseInt(frm.qty[index-1].value) - parseInt(obj.value);
		}
	}
}
//-->
</SCRIPT>

</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_f();">
<html:form action="changeInbound.do?type=initRk" method="POST">
<input type="hidden" name="whichLine">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">换货入库</font><font color="838383"> 
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
			发货单号：
			<html:text property="purNO" size="15" onkeydown="if(event.keyCode == 13) query_f();"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value=" 查询 " name="btn_query" onclick="query_f();">&nbsp;
			<input type="button" value=" 保存 " name="btn_save" onclick="save_f();">
		</td>
	</tr>
</table>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="blue"><b>发货单信息</b></font>
<bean:define id="header" name="shipForm" property="mst"/>
<bean:define id="order" name="header" property="order"/>
<bean:define id="deliveryInfo" name="order" property="deliveryInfo"/>
<!-- 发货单ID -->
<html:hidden name="inboundForm" property="purID" />
<html:hidden name="header" property="sn_id"/>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
  <tr height="26"> 
    <td width="10%">&nbsp;&nbsp;批次：</td><td bgcolor="#FFFFFF" >
	<bean:write name="header" property="lot"/></td width="10%">
	<td>&nbsp;&nbsp;销售订单号：</td><td bgcolor="#FFFFFF">
	<bean:write name="order" property="orderNumber"/></td>
	<td width="10%">&nbsp;&nbsp;发送日期：</td><td width="21%" bgcolor="#FFFFFF">
	<bean:write name="header" property="checkDate"/></td>
  </tr>
  <tr height="26"> 
	<td width="12%">&nbsp;&nbsp;发货单：</td><td width="22%" bgcolor="#FFFFFF">
	<html:text name="header" property="sn_code" readonly="true" size="15"/></td>
	<td width="12%">&nbsp;&nbsp;联系人：</td><td width="21%" bgcolor="#FFFFFF">
	<bean:write name="deliveryInfo" property="receiptor"/></td>
	<td width="12%">&nbsp;&nbsp;收货人邮编：</td><td width="21%" bgcolor="#FFFFFF">
	<bean:write name="deliveryInfo" property="postCode"/></td>
  </tr>
  <tr height="26"> 
	<td>&nbsp;&nbsp;联系电话：</td><td bgcolor="#FFFFFF">
	<bean:write name="deliveryInfo" property="phone"/></td>
	<td>&nbsp;&nbsp;收货人地址：</td><td bgcolor="#FFFFFF" colspan=3>
	<bean:write name="deliveryInfo" property="address"/></td>
</table>
<br>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="blue"><b>入库信息</b></font>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
  <tr height="26"> 
	<td width="12%"><font color=red>*</font>&nbsp;收货人：</td>
	<td bgcolor="#FFFFFF" width="21%">
		<html:text property="consignee" size="10" readonly="true"/>
    </td>
    <td width="12%"><font color=red>*</font>&nbsp;收货日期：</td>
	<td bgcolor="#FFFFFF" width="22%">
		<html:text property="conDate" size="10"/><a href="javascript:calendar(document.forms[0].conDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    </td>
	
	<td width="12%">&nbsp;<font color="red">三无单号</font></td>
	<td bgcolor="#FFFFFF"  width="21%">
		<html:text property="quaNO" size="15" readonly="true"/>
    </td>
  </tr>

  <tr height="26"> 

    <td width="12%"><font color=red>*</font>&nbsp;质检人：</td>
	<td bgcolor="#FFFFFF" width="22%">
		<html:text property="checker" size="10" readonly="true"/>
    </td>
	<td width="12%"><font color=red>*</font>&nbsp;质检日期：</td>
	<td bgcolor="#FFFFFF" width="21%">
		<html:text property="cheDate" size="10"/><a href="javascript:calendar(document.forms[0].cheDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    </td>
	<td width="12%">&nbsp;</td>
	<td bgcolor="#FFFFFF"  width="21%">
		
  </tr>

  <tr height="26"> 
	
	<td><font color=red>*</font>&nbsp;换货原因：</td>
	<td bgcolor="#FFFFFF">
		<html:select property="rrNO" style="width:140">
			<option value="">-- 请选择 --</option>
				<html:optionsCollection name="inboundForm" property="rrList" value="code" label="name"/> 
		</html:select>
	</td>
	<td>&nbsp;&nbsp;包裹号：</td>
	<td bgcolor="#FFFFFF">
		<html:text property="postNum" size="15"/>
	</td>
	<td width="12%">&nbsp;</td>
	<td bgcolor="#FFFFFF"  width="21%">
		
  </tr>
  <tr height="26"> 
	<td>&nbsp;&nbsp;邮资：</td><td bgcolor="#FFFFFF">
		<html:text property="postage" size="10"/>
	</td>
	<td>&nbsp;&nbsp;备注：</td><td bgcolor="#FFFFFF" colspan=3><html:textarea property="bz" cols="56"/></td>
</table>
<br>
<%@ include file="stock_rk_items.html" %>
</html:form>
</body>
</html>
