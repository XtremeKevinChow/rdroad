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

function check_f() {
	if (confirm("确定审核吗?"))
	{
		document.forms[0].action = "finPurchase.do?type=checkPurchases&psID=" + document.forms[0].psID.value;
		document.forms[0].btn_check.disabled = "true";
		document.forms[0].submit();
	}
	
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="finPurchase.do?type=checkPurchases" method="POST">
<html:hidden property="psID"/>
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

<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>采购到货单号：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="psCode"/></td>
	<td>供应商：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="proNO"/></td>
	<td>供应商名称：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="proName"/></td>
  </tr>
  <tr height="26"> 
	<td>业务类型：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="operationClassName"/></td>
	<td>制单日期：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="createDate"/></td>
	<td>单据状态：</td><td bgcolor="#FFFFFF" id="os_status">
	<logic:equal name="finPurchaseForm" property="status" value="1">新建</logic:equal>
	<logic:equal name="finPurchaseForm" property="status" value="2">审核</logic:equal>
	<logic:equal name="finPurchaseForm" property="status" value="3">部分结算</logic:equal>
	<logic:equal name="finPurchaseForm" property="status" value="4">完全结算</logic:equal>
	</td>
  </tr>
  <tr height="26"> 
	<td>业务员：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="operator"/></td>
	<td>操作日期：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="purchaseDate"/></td>
	<td>制单人：</td><td bgcolor="#FFFFFF"><bean:write name="finPurchaseForm" property="creator"/></td>
  </tr>
</table>
<br>
<bean:define id="list" name="finPurchaseForm" property="purchaseDetail"/>
<table id="detail" width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>采购到货单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>采购金额</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>数量</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>预算单价</th>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<bean:write name="finPurchaseForm" property="psCode"/>
		</td>
		<td class=OraTableCellText noWrap align=middle >
		<bean:write name="list" property="itemCode"/>
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purAmt" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purPrice" format="#0.00"/></td>
	</tr>
	</logic:iterate>
</table>
<TABLE align="center">
<TR>
	<TD>
		<input type="button" name="btn_check" value=" 审核 " onclick="check_f();">
		<input type="button" name="btn_back" value=" 返回 " onclick="history.back(-1);">
	</TD>
</TR>
</TABLE>
</html:form>
</body>
</html>
