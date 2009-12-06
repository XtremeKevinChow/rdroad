<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/keyPress.js"></script>
<script language="JavaScript" src="../script/sortTable.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function setValue(PKs) {
	document.forms[0].psIDs.value = PKs;
	document.forms[0].action = "./finPurchaseInvoiceManage.do?type=initAdd2";
	document.forms[0].submit();
}
//发票业务函数
function new_f() {
	var ids="";
	var frm = document.forms[0];
	if(frm.psDtlID != null) {
		if (typeof(frm.psDtlID.length) == "undefined") {//不是数组
			
			ids = frm.psDtlID.value;
		} else {
			for (var i = 0; i < frm.psDtlID.length ; i ++ )
			{
				ids += frm.psDtlID[i].value + ",";
			}
			
			ids = ids.substring(0, ids.length - 1)
		}
	}
	
	window.open("finPurchase.do?type=queryCheckedPurchaseItems&proNO="+document.forms[0].proNO.value+"&ids="+ids,'newwindow', 'height=600, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=no, status=yes');

}
function save_f() {
	var frm = document.forms[0];
	if (frm.factAPCode.value == "")
	{
		alert("请输入发票号");
		frm.factAPCode.focus();
		return;
	}

	if (frm.tax.value == "")
	{
		alert("请输入表头税率");
		frm.tax.focus();
		return;
	}
	if (frm.amt0.value == "")
	{
		alert("开票金额不能为空");
		return;
	}
	if (frm.invoiceDate.value == "")
	{
		alert("开票时间不能为空");
		frm.invoiceDate.focus();
		return;
	}
	
	//检查明细
	if (typeof(frm.qty.length) == "undefined") {
		if (frm.qty == null || frm.qty.value == "")
		{
			alert("本次数量不能为空");
			frm.qty.focus();
			return;
		} else {
			if (!is_number(frm.qty.value))
			{
				alert("本次数量必须为数字");
				frm.qty.focus();
				return;
			}
		}

		if (frm.apPrice == null || frm.apPrice.value == "")
		{
			alert("开票单价不能为空");
			frm.apPrice.focus();
			return;
		} else {
			if (!is_number(frm.apPrice.value))
			{
				alert("开票单价必须为数字");
				frm.apPrice.focus();
				return;
			}
		}
		
		if (frm.amt == null || frm.amt.value == "")
		{
			alert("开票金额不能为空");
			frm.amt.focus();
			return;
		} else {
			if (!is_number(frm.amt.value))
			{
				alert("开票金额必须为数字");
				frm.amt.focus();
				return;
			}
		}

		if (frm.itemTax == null || frm.itemTax.value == "")
		{
			alert("税率不能为空");
			frm.itemTax.focus();
			return;
		} else {
			if (!is_number(frm.itemTax.value))
			{
				alert("税率必须为数字");
				frm.itemTax.focus();
				return;
			}
		}

		if (frm.taxAmt == null || frm.taxAmt.value == "")
		{
			alert("税额不能为空");
			frm.taxAmt.focus();
			return;
		} else {
			if (!is_number(frm.taxAmt.value))
			{
				alert("税额必须为数字");
				frm.taxAmt.focus();
				return;
			}
		}

		if (frm.totalAmt == null || frm.totalAmt.value == "")
		{
			alert("含税金额不能为空");
			frm.totalAmt.focus();
			return;
		} else {
			if (!is_number(frm.totalAmt.value))
			{
				alert("含税金额必须为数字");
				frm.totalAmt.focus();
				return;
			}
		}

	} else {
		for (var i = 0; i < frm.qty.length ; i ++ )
		{
			
			if (frm.qty[i] == null || frm.qty[i].value == "")
			{
				alert("本次数量不能为空");
				frm.qty[i].focus();
				return;
			} else {
				if (!is_number(frm.qty[i].value))
				{
					alert("本次数量必须为数字");
					frm.qty[i].focus();
					return;
				}
			}

			if (frm.apPrice[i] == null || frm.apPrice[i].value == "")
			{
				alert("开票单价不能为空");
				frm.apPrice[i].focus();
				return;
			} else {
				if (!is_number(frm.apPrice[i].value))
				{
					alert("开票单价必须为数字");
					frm.apPrice[i].focus();
					return;
				}
			}
			
			if (frm.amt[i] == null || frm.amt[i].value == "")
			{
				alert("开票金额不能为空");
				frm.amt[i].focus();
				return;
			} else {
				if (!is_number(frm.amt[i].value))
				{
					alert("开票金额必须为数字");
					frm.amt[i].focus();
					return;
				}
			}

			if (frm.itemTax[i] == null || frm.itemTax[i].value == "")
			{
				alert("税率不能为空");
				frm.itemTax[i].focus();
				return;
			} else {
				if (!is_number(frm.itemTax[i].value))
				{
					alert("税率必须为数字");
					frm.itemTax[i].focus();
					return;
				}
			}

			if (frm.taxAmt[i] == null || frm.taxAmt[i].value == "")
			{
				alert("税额不能为空");
				frm.taxAmt[i].focus();
				return;
			} else {
				if (!is_number(frm.taxAmt[i].value))
				{
					alert("税额必须为数字");
					frm.taxAmt[i].focus();
					return;
				}
			}

			if (frm.totalAmt[i] == null || frm.totalAmt[i].value == "")
			{
				alert("含税金额不能为空");
				frm.totalAmt[i].focus();
				return;
			} else {
				if (!is_number(frm.totalAmt[i].value))
				{
					alert("含税金额必须为数字");
					frm.totalAmt[i].focus();
					return;
				}
			}


		}
	
	}

	document.forms[0].submit();

}


//表头税率
function check_tax(obj) {
	var frm;
	frm = document.forms[0];

	if (!is_number(obj.value)) {
		alert("表头税率必须是数字");
		obj.select();
		return;
	}
	if (frm.itemTax != null)
	{
	
		if (typeof(frm.itemTax.length) == "undefined") {//不是数组
			frm.itemTax.value = obj.value;
		} else {
			for (var i = 0;i < frm.itemTax.length ; i ++ )
			{
				frm.itemTax[i].value = obj.value;
			}
			
		}
	}
	for (var i= 1; i <= DataTable.rows.length-2; i ++ )
	{

		check_itemTax(i);

	}
	
}

//背景色
function setBgColor(row) {
	var trObj, str;
	str = DataTable.rows(row).cells(0).innerHTML;

	if (str.indexOf("<FONT color=red>") == -1)
	{
		str = "<font color=red>"+str.substring(0, 7) + "</font>" + str.substring(7);
		DataTable.rows(row).cells(0).innerHTML = str;
	}
	
}

//数量
function check_qty(row) {
	var frm;
	frm = document.forms[0];
	if (frm.qty != null)
	{
	
		if (typeof(frm.qty.length) == "undefined") {//不是数组
			if (!is_number(frm.qty.value)) {
				alert("数量必须为数字");
				return; 
			}
		
			if (Math.abs(to_number(frm.qty.value)) > Math.abs(to_number(frm.useQty.value))) {
				alert("数量不能超过可开票数量");
				frm.qty.select();
				return;
			} 
			if (frm.apPrice.value != "" && is_number(frm.apPrice.value)) {
				frm.amt.value = round( to_number(frm.qty.value) * to_number(frm.apPrice.value), 2 );
				frm.taxAmt.value = round( to_number(frm.amt.value) * to_number(frm.itemTax.value), 2 );
				frm.totalAmt.value = round( to_number(frm.amt.value) + to_number(frm.taxAmt.value), 2 );
			} 
		} else {
			if (!is_number(frm.qty[row-1].value)) {
				alert("数量必须为数字");
				return; 
			}
			if (Math.abs(to_number(frm.qty[row-1].value)) > Math.abs(to_number(frm.useQty[row-1].value))) {
				alert("数量不能超过可开票数量");
				frm.qty[row-1].select();
				return;
			}
			if (frm.apPrice[row-1].value != "" && is_number(frm.apPrice[row-1].value)) {
				frm.amt[row-1].value = round( to_number(frm.qty[row-1].value) * to_number(frm.apPrice[row-1].value), 2 
				);
				frm.taxAmt[row-1].value = round( to_number(frm.amt[row-1].value) * to_number(frm.itemTax[row-1].value), 2 );
				frm.totalAmt[row-1].value = round( to_number(frm.amt[row-1].value) + to_number(frm.taxAmt[row-1].value), 2 );
			}
		}
	}
	calcQtyAmt();
	calcAmt(frm.amt);
	calcTaxAmt();
	calcTotalAmt();
	setBgColor(row)
}

//税率
function check_taxAmt(row) {
	var frm;
	frm = document.forms[0];
	if (frm.taxAmt != null)
	{
	
		if (typeof(frm.taxAmt.length) == "undefined") {//不是数组
			//frm.taxAmt.value = round( to_number(frm.amt.value) * to_number(frm.itemTax.value), 2 );
			frm.totalAmt.value = round( to_number(frm.amt.value) + to_number(frm.taxAmt.value), 2 );
			
			
		} else {
			//frm.taxAmt[row-1].value = round( to_number(frm.amt[row-1].value) * to_number(frm.itemTax[row-1].value), 2 );
			frm.totalAmt[row-1].value = round( to_number(frm.amt[row-1].value) + to_number(frm.taxAmt[row-1].value), 2 );

		}
	}
	calcTaxAmt();
	calcTotalAmt();
}
//价格
function check_apprice(row) {
	var frm;
	frm = document.forms[0];
	
	if (frm.apPrice != null)
	{
	
		if (typeof(frm.apPrice.length) == "undefined") {//不是数组
			if (!is_number(frm.apPrice.value)) {
				alert("价格必须为数字");
				return; 
			}
			frm.amt.value = round( to_number(frm.qty.value) * to_number(frm.apPrice.value), 2 );
			frm.taxAmt.value = round( to_number(frm.amt.value) * to_number(frm.itemTax.value), 2 );
			frm.totalAmt.value = round( to_number(frm.amt.value) + to_number(frm.taxAmt.value), 2 );
			
		} else {
			if (!is_number(frm.apPrice[row-1].value)) {
				alert("价格必须为数字");
				return; 
			}
			frm.amt[row-1].value = round( to_number(frm.qty[row-1].value) * to_number(frm.apPrice[row-1].value), 2 );
			frm.taxAmt[row-1].value = round( to_number(frm.amt[row-1].value) * to_number(frm.itemTax[row-1].value), 2 );
			frm.totalAmt[row-1].value = round( to_number(frm.amt[row-1].value) + to_number(frm.taxAmt[row-1].value), 2 );
		}
	}
	calcAmt(frm.amt);
	calcTaxAmt();
	calcTotalAmt();
}
//税额
function check_itemTax(row) {
	var frm;
	frm = document.forms[0];
	if (frm.itemTax != null)
	{
	
		if (typeof(frm.itemTax.length) == "undefined") {//不是数组
			
			if (!is_number(frm.itemTax.value)) {
				alert("税率必须为数字");
				frm.itemTax.select();
				return; 
			}
			frm.taxAmt.value = round( to_number(frm.amt.value) * to_number(frm.itemTax.value), 2 );
			frm.totalAmt.value = round( to_number(frm.amt.value) + to_number(frm.taxAmt.value), 2 );
		} else {

			if (!is_number(frm.itemTax[row-1].value)) {
				alert("税率必须为数字");
				frm.itemTax[row-1].select();
				return; 
			}
			frm.taxAmt[row-1].value = round( to_number(frm.amt[row-1].value) * to_number(frm.itemTax[row-1].value), 2 );
			frm.totalAmt[row-1].value = round( to_number(frm.amt[row-1].value) + to_number(frm.taxAmt[row-1].value), 2 );
		}
	}
	calcTaxAmt();
	calcTotalAmt();
}

//开票金额
function check_amt(row) {
	
	var frm;
	frm = document.forms[0];
	if (frm.qty != null)
	{
	
		if (typeof(frm.qty.length) == "undefined") {//不是数组
			if (frm.qty == null || frm.qty.value == "" || to_number(frm.qty.value) == "0") {
				alert("请先输入数量");
				frm.amt.value = "";
				frm.qty.focus();
				return;
			}
			if (!is_number(frm.amt.value)) {
				alert("开票金额必须为数字");
				frm.amt.select();
				return; 
			}
			
			frm.taxAmt.value = round( to_number(frm.amt.value) * to_number(frm.itemTax.value), 2 );
			frm.apPrice.value = round((to_number(frm.amt.value) / to_number(frm.qty.value)), 2);
			frm.totalAmt.value = round( to_number(frm.amt.value) + to_number(frm.taxAmt.value), 2 );
			
		} else {
		
			if (frm.qty[row-1] == null || frm.qty[row-1].value == "" || to_number(frm.qty[row-1].value) == "0") {
				alert("请先输入数量");
				frm.amt[row-1].value = "";
				frm.qty[row-1].focus();
				return;
			}

			if (!is_number(frm.amt[row-1].value)) {
				alert("开票金额必须为数字");
				frm.amt[row-1].select();
				return; 
			}
			frm.taxAmt[row-1].value = round( to_number(frm.amt[row-1].value) * to_number(frm.itemTax[row-1].value), 2 );
			frm.apPrice[row-1].value = round((to_number(frm.amt[row-1].value) / to_number(frm.qty[row-1].value)), 2);
			frm.totalAmt[row-1].value = round( to_number(frm.amt[row-1].value) + to_number(frm.taxAmt[row-1].value), 2 );
		}
	}
	calcAmt(frm.amt);
	calcTaxAmt();
	calcTotalAmt();

}
//计算数量合计
function calcQtyAmt() {
	var  total = 0;
	var frm;
	frm = document.forms[0];
	
	if (frm.qty != null)
	{
	
		if (typeof(frm.qty.length) == "undefined") {//不是数组
			
			total = to_number(frm.qty.value) ;
			
		} else {
			for (var i =0; i < frm.qty.length ; i ++ )
			{
		
				
				total = total + to_number(frm.qty[i].value);
			}
			
		}
		total0.innerText = round(total, 2);
	} else {
		total0.innerText = "0";
	}
	
}
//计算税额额合计
function calcTaxAmt() {
	
	var  total = 0;
	var frm;
	frm = document.forms[0];

	if (frm.taxAmt != null)
	{
	
		if (typeof(frm.taxAmt.length) == "undefined") {//不是数组
			
			total = to_number(frm.taxAmt.value) ;
			
		} else {
			for (var i =0; i < frm.taxAmt.length ; i ++ )
			{
		
				
				total = total + to_number(frm.taxAmt[i].value);
				
			}
			
		}
	}
	total2.innerText = round(total, 2);
}

//计算价税合计
function calcTotalAmt() {
	var  total = 0;
	var frm;
	frm = document.forms[0];
	if (frm.totalAmt != null)
	{
	
		if (typeof(frm.totalAmt.length) == "undefined") {//不是数组
			total = to_number(frm.totalAmt.value) ;
			
		} else {
			for (var i =0; i < frm.totalAmt.length ; i ++ )
			{
		
				
				total = total + to_number(frm.totalAmt[i].value);
			}
			
		}
	}
	total3.innerText = round(total, 2);
	frm.amt0.value = total3.innerText;
}


//计算开票金额合计
function calcAmt(elem) {
	var  total = 0;
	if (elem != null)
	{
	
		if (typeof(elem.length) == "undefined") {//不是数组
			total = to_number(elem.value);
			
		} else {
			for (var i =0; i < elem.length ; i ++ )
			{
				
				total = total + to_number(elem[i].value);
			}
			
		}
	}
	total1.innerText = round(total, 2);
}

function to_number(str) {
	if (isNaN(str) || str == "")
	{
		
		return 0;
	}
	return parseFloat(str);
	
}
function is_number(str) {
	if (isNaN(str))
	{
		return false;
	}else{
		return true;
	}
}

function round(num,n)  
{
	var  dd=1;  
	var  tempnum;  
	for(i=0;i<n;i++)  
	{  
		dd*=10;  
	}  
	tempnum=num*dd;  
	tempnum=Math.round(tempnum);  
	return (tempnum/dd);  
}  

/**
function init_f() {
	var frm;
	frm = document.forms[0];
	if (frm.qty != null)
	{
		calcAmt(frm.amt);
		check_tax(frm.tax);
	}

	
}
*/
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init2(DataTable);loadSort2(DataTable, DataTable.rows.length-1);">
<html:form action="/finPurchaseInvoiceManage.do?type=modify" method="POST">
<!-- 这个域隐含了形如 “1，2，3，4”的字符串的值 -->
<input type="hidden" name="psIDs">
<!-- 采购发票主ID -->
<html:hidden property="apID"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">修改采购发票</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
  <tr height="26"> 

    <td width="">发票号：</td>
	<td width="" bgcolor="#FFFFFF">
		<html:text property="factAPCode"/>&nbsp;
    </td>
	<td>表头税率：</td>
	<td bgcolor="#FFFFFF">
		<html:text property="tax" size="5" onchange="check_tax(this)"/>&nbsp;
    </td>
	<td>开票金额：</td>
	<td bgcolor="#FFFFFF">
		<html:text property="amt0" size="8" readonly="true"/>&nbsp;
    </td>
  </tr>
  <tr height="26"> 
	<td width="12%">发票日期：</td><td width="15%" bgcolor="#FFFFFF">
	<html:text property="invoiceDate" size="10" readonly="true"/><a href="javascript:calendar(document.forms[0].invoiceDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
	<td width="12%">供应商：</td><td width="15%" bgcolor="#FFFFFF">
	<bean:write name="finPurchaseInvoiceForm" property="proNO" />
	<html:hidden property="proNO"/>
	</td>
	<td width="12%">供应商名称：</td><td bgcolor="#FFFFFF">
	<bean:write name="finPurchaseInvoiceForm" property="proName"/>
	
	</td>
  </tr>
  <tr height="26"> 
	<td>制单人：</td><td bgcolor="#FFFFFF">
	<bean:write name="finPurchaseInvoiceForm" property="creator" />
	<html:hidden property="creator"/>
	</td>
	<td>单据日期：</td><td bgcolor="#FFFFFF">
	<bean:write name="finPurchaseInvoiceForm" property="createDate" />
	
	</td>
	<td>单据状态：</td><td bgcolor="#FFFFFF">
	<logic:equal name="finPurchaseInvoiceForm" property="status" value="1">新建</logic:equal>
	<logic:equal name="finPurchaseInvoiceForm" property="status" value="2">审核</logic:equal>
	<logic:equal name="finPurchaseInvoiceForm" property="status" value="3">记帐</logic:equal>
	</td>

</table>
<br>
<bean:define id="list" name="finPurchaseInvoiceForm" property="invoiceDetail"/>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>预算单价</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>可开票数量</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>本次数量</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>开票单价</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>开票金额</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>税率</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>税额</td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>含税金额</td>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle width="50">
		<bean:write name="list" property="itemCode"/>
		<input type="hidden" name="itemID" value="<bean:write name="list" property="itemID"/>">
		</td>


		<td class=OraTableCellText noWrap align=middle width="200">
		<bean:write name="list" property="itemName"/>
		</td>

		<td class=OraTableCellText noWrap align=right width="50">
		<input type="hidden" name="purPrice" value="<bean:write name="list" property="purPrice" />">
		<bean:write name="list" property="purPrice" format="#0.00"/>
		</td>

		<td class=OraTableCellText noWrap align=right width="50">
		<input type="hidden" name="useQty" value="<bean:write name="list" property="useQty" />">
		<bean:write name="list" property="useQty" format="#0"/>
		</td>

		<td class=OraTableCellText noWrap align=right width="50">
			<html:text name="list" property="qty" size="6" onchange="check_qty(this.parentElement.parentElement.rowIndex);" />
		</td>

		<td class=OraTableCellText noWrap align=right width="50">
		<html:text name="list" property="apPrice" size="6" onchange="check_apprice(this.parentElement.parentElement.rowIndex);"/>
		</td>

		<td class=OraTableCellText noWrap align=right width="">
			<html:text name="list" property="amt" size="6" onchange="check_amt(this.parentElement.parentElement.rowIndex);"/>
		</td>

		<td class=OraTableCellText noWrap align=right width="40">
			<input name="itemTax" size="6" value="<bean:write name="list" property="tax"/>" onchange="check_itemTax(this.parentElement.parentElement.rowIndex)">
		</td>

		<td class=OraTableCellText noWrap align=right width="">
			<html:text name="list" property="taxAmt" size="6" onchange="check_taxAmt(this.parentElement.parentElement.rowIndex)"/>
		</td>

		<td class=OraTableCellText noWrap align=right width="">
		<html:text name="list" property="totalAmt" size="6" readonly="true" />
		
		<!-- 雪藏了采购到货单明细PK -->
		<input type="hidden" name="psDtlID" value="<bean:write name="list" property="psDtlID"/>">
		<!-- 雪藏了采购发票明细PK -->
		<html:hidden name="list" property="apDtlID"/>
		</td>
	</tr>
	</logic:iterate>
	<tr>
		<td class=OraTableCellText colspan="4" align=left ><B>合计：</B></td>
		<td class=OraTableCellText colspan="1" align=right id="total0"><bean:write name="finPurchaseInvoiceForm" property="qtyAll"/></td>
		<td class=OraTableCellText colspan="1" align=right></td>
		<td class=OraTableCellText colspan="1" align=right id="total1"><bean:write name="finPurchaseInvoiceForm" property="amtAll"/></td>
		<td class=OraTableCellText colspan="1" align=right ></td>
		<td class=OraTableCellText colspan="1" align=right id="total2"><bean:write name="finPurchaseInvoiceForm" property="taxAmtAll"/></td>
		<td class=OraTableCellText colspan="1" align=right id="total3"><bean:write name="finPurchaseInvoiceForm" property="amt"/>
		</td>
	</tr>
</table>
<TABLE align="center">
<TR>
	<TD>
		<!-- <input type="button" value=" 新建 " onclick="new_f();">&nbsp;&nbsp; -->
	    <input type="button" value=" 保存 " onclick="save_f();">&nbsp;&nbsp;
		<input type="button" value=" 返回 " onclick="history.back();">&nbsp;&nbsp;
	</TD>
</TR>
</TABLE>
</html:form>
</body>
</html>
