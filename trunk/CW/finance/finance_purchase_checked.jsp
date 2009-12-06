<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/sortTable.js"></script>
<script language="JavaScript">
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
function query_f() {

	if ( !document.forms[0].proOrItem[0].checked && !document.forms[0].proOrItem[1].checked && !document.forms[0].proOrItem[2].checked  && !document.forms[0].proOrItem[3].checked  )
	{
		alert("请选择供应商或者产品");
		return;
	}
	
	if (document.forms[0].proOrItemCondition == null || document.forms[0].proOrItemCondition.value == "")
	{
		alert("请输入查询内容");
		document.forms[0].proOrItemCondition.focus();
		return;
	}

	
	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
	
}
function seletValue() {
	var PKs = getPKs();
	if (PKs == null || PKs == "")
	{
		alert("请选择记录");
		return;
	}
	window.close();
	opener.setValue(PKs);
}

function getPKs() {
	var str = "";
	
	var len = DataTable.rows.length;
	for (i = 1; i < len; i++) {
		row = DataTable.rows(i);
		
		if (row.getElementsByTagName("INPUT")[0].checked == true)//选择框被选中
		{
			str += row.getElementsByTagName("INPUT")[0].value + ",";

		}
		
	}
	if (document.forms[0].ids.value != "")
	{
		str += document.forms[0].ids.value;
	} else {
		str = str.substring(0, str.length - 1);
	}

	return str;
}

function splitIDs() {
	var arr = new Array();
	var frm = document.forms[0];
	
	if (frm.ids != null && frm.ids != "")
	{
		arr = frm.ids.value.split(",");
		
		if (frm.psDtlIDs != null)
		{
			if (typeof(frm.psDtlIDs.length) == "undefined")
			{
				for (var i = 0 ;i < arr.length ; i ++ )
				{
					if (arr[i] == frm.psDtlIDs.value)
					{
						frm.psDtlIDs.checked = "true";
					}
					
				}
			} else {
				for (var i=0; i < arr.length; i ++)
				{
			
					for (var j=0; j < frm.psDtlIDs.length ; j ++ )
					{
						if ( arr[i] == frm.psDtlIDs[j].value )
						{
							frm.psDtlIDs[j].checked = "true";
						}
					}
				}
			}
		}


	}
}


function load_f() {
	//splitIDs();
	loadSort(DataTable);
	document.forms[0].proOrItemCondition.focus();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f();">
<html:form action="finPurchase.do?type=queryCheckedPurchaseItems" method="POST">
<!-- 雪藏已经选择的ID -->
<html:hidden property="ids"/>
<html:hidden property="proNO"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置:</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">生成采购发票</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table width="100%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td> 
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr> 
    <td></td>
  </tr>
</table>


<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
  
  <tr> 
    <td>
	<html:radio property="proOrItem" value="1"/>供应商名称&nbsp;&nbsp;
	<html:radio property="proOrItem" value="2"/>产品名称&nbsp;&nbsp;
	<html:radio property="proOrItem" value="3"/>供应商代码&nbsp;&nbsp;
	<html:radio property="proOrItem" value="4"/>产品代码&nbsp;&nbsp;
	<html:text property="proOrItemCondition" size="30" />&nbsp;&nbsp;&nbsp;&nbsp; 
	
   
    </td>
  </tr>
  <tr> 
    <td>
	排序1：
	<html:select name="finPurchaseForm" property="orderByCondition1" >
		<html:option value="0">无</html:option>
		<html:option value="1">入库时间</html:option>
		<html:option value="2">产品代码</html:option>
		<html:option value="3">产品名称</html:option>
		<html:option value="4">供应商代码</html:option>
		<html:option value="5">供应商名称</html:option>
		<html:option value="6">入库数量</html:option>
		<html:option value="7">预算单价</html:option>
		<html:option value="8">未结数量</html:option>
		<html:option value="9">记帐数量</html:option>
		<html:option value="10">到货单号</html:option>
		<html:option value="11">入库单号</html:option>
	</html:select>
	<html:select property="ascOrDesc1">
		<html:option value="0">升序</html:option>
		<html:option value="1">倒序</html:option>
	</html:select>&nbsp;&nbsp;
	排序2：
	<html:select name="finPurchaseForm" property="orderByCondition2" >
		<html:option value="0">无</html:option>
		<html:option value="1">入库时间</html:option>
		<html:option value="2">产品代码</html:option>
		<html:option value="3">产品名称</html:option>
		<html:option value="4">供应商代码</html:option>
		<html:option value="5">供应商名称</html:option>
		<html:option value="6">入库数量</html:option>
		<html:option value="7">预算单价</html:option>
		<html:option value="8">未结数量</html:option>
		<html:option value="9">记帐数量</html:option>
		<html:option value="10">到货单号</html:option>
		<html:option value="11">入库单号</html:option>
	</html:select>
	<html:select property="ascOrDesc2">
		<html:option value="0">升序</html:option>
		<html:option value="1">倒序</html:option>
	</html:select>&nbsp;&nbsp;
	排序3：
	<html:select name="finPurchaseForm" property="orderByCondition3" >
		<html:option value="0">无</html:option>
		<html:option value="1">入库时间</html:option>
		<html:option value="2">产品代码</html:option>
		<html:option value="3">产品名称</html:option>
		<html:option value="4">供应商代码</html:option>
		<html:option value="5">供应商名称</html:option>
		<html:option value="6">入库数量</html:option>
		<html:option value="7">预算单价</html:option>
		<html:option value="8">未结数量</html:option>
		<html:option value="9">记帐数量</html:option>
		<html:option value="10">到货单号</html:option>
		<html:option value="11">入库单号</html:option>
	</html:select>
	<html:select property="ascOrDesc3">
		<html:option value="0">升序</html:option>
		<html:option value="1">倒序</html:option>
	</html:select>&nbsp;&nbsp;
    <input type="button" value=" 查询 " name="btn_query" onclick="query_f();">
	<input type="button" value=" 确定 " name="btn_confirm" onclick="seletValue();">
    </td>
  </tr>
</table>
<bean:define id="list" name="finPurchaseForm" property="purchaseDetail"/>
<table id="DataTable" width="890" align="center" cellspacing="1" border="0" >
  
    <tr>
		<td width="30"  class="OraTableRowHeader" noWrap  noWrap align=middle sort=false>
		<input type="checkbox" onclick="checkAll(this.checked, 0)"></td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>入库时间</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</td>
		<td width="200"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>供代码</td>
		<td width="200"  class="OraTableRowHeader" noWrap  noWrap align=middle>供应商名称</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>入库数量</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>预算单价</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>未结数量</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>记帐数量</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>到货单号</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>入库单号</td>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<!-- 这个选择框的内容其实就是采购到货单明细的PK -->
			<input type="checkbox" name="psDtlIDs" value="<bean:write name="list" property="psDtlID"/>">
		</td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="purchaseDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="itemCode"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="proNO"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="proName"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purPrice"  format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="useQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="finishQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="psCode"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="resNO"/></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>