<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function detail_f() {
	var str = detailTable.style.display;
	if (str == "none")
	{
		view_ctrl.innerText = "����";
		detailTable.style.display = "block";
	}

	if (str == "block")
	{
		view_ctrl.innerText = "��ʾ��ϸ";
		detailTable.style.display = "none";
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">���۷�Ʊ����</font><font color="838383"> 
      	</td>
   </tr>
</table>

<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>���۷�Ʊ�ţ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="arNO"/></td>
	<td>�ͻ����룺</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="customerID"/></td>
	<td>�ͻ����ƣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="customerName"/></td>
  </tr>
  <tr height="26"> 
	<td>���۽�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="soAmt" format="#0.00"/></td>
	<td>Ӧ�ս�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="arAmt" format="#0.00"/></td>
	<td>��ȯ���ã�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="giftAmt" format="#0.00"/></td>
	
  </tr>
  <tr height="26"> 
	<td>��Ӧ���ţ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="resNO"/></td>
	<td>���ͷѣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="deliverAmt" format="#0.00"/></td>
	<td>��װ�ѣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="packageAmt" format="#0.00"/></td>
  </tr>
  <tr height="26"> 
    <td>�Ѹ���</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="payedAmt" format="#0.00"/></td>
	<td>ҵ�����ͣ�</td><td bgcolor="#FFFFFF">
		<bean:write name="finSalesInvoiceForm" property="operationClassName"/>
	</td>
	<td>�������ڣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesInvoiceForm" property="soDate"/></td>
	
   </tr>
   <tr>
    <td>��Ʊ״̬��</td><td bgcolor="#FFFFFF" id="os_status" colspan="5">
		<logic:equal name="finSalesInvoiceForm" property="status" value="1">�½�</logic:equal>
		<logic:equal name="finSalesInvoiceForm" property="status" value="2">���</logic:equal>
		<logic:equal name="finSalesInvoiceForm" property="status" value="3">����</logic:equal>
	</td>
   </tr>
</table>
<TABLE width="95%" align="center">
<TR>
	<TD><a href="javascript:detail_f();" id="view_ctrl">��ʾ��ϸ</a></TD>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
</TR>
</TABLE>
<bean:define id="list" name="finSalesInvoiceForm" property="invoiceDetail"/>
<table id="detailTable" style="display:none" width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���ۼ۸�</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���</th>
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
		<td class=OraTableCellText noWrap align=left colspan="4">�ϼƣ�</td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="finSalesInvoiceForm" property="detailAmtTotal" format="#0.00"/></td>
		
	</tr>
</table>
<table align="center">
	<tr>
		<td><input type="button" value=" ���� " onclick="history.go(-1)"></td>
	</tr>
</table>
</body>
</html>
