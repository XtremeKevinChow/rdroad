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
      		-&gt; </font><font color="838383">���۶�������</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<br>
<!-- <table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>���۶����ţ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="soNO" /></td>
	<td>�ͻ����룺</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="customerID" /></td>
	<td>�ͻ����ƣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="customerName" /></td>
  </tr>
  
  <tr height="26"> 
	<td>���۽�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="soAmt" format="#0.00"/></td>
	<td>Ӧ�ս�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="arAmt" format="#0.00"/></td>
	<td>��ȯ���ã�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="giftAmt" format="#0.00"/></td>
	
  </tr>
  <tr height="26"> 
	<td>��Ӧ���ţ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="resNO" /></td>
	<td>���ͷѣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="deliverAmt" format="#0.00"/></td>
	<td>�Ѹ���</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="payedAmt" format="#0.00"/></td>
  </tr>

  <tr height="26"> 
	<td>ҵ�����ͣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="operationClassName" /></td>
	<td>�������ڣ�</td><td bgcolor="#FFFFFF"><bean:write name="finSalesForm" property="soDate" /></td>
	<td>����״̬��</td><td bgcolor="#FFFFFF">
		<logic:equal name="finSalesForm" property="status" value="1">�½�</logic:equal>
		<logic:equal name="finSalesForm" property="status" value="2">ȷ��</logic:equal>
		<logic:equal name="finSalesForm" property="status" value="3">�ѿ�Ʊ</logic:equal>
		<logic:equal name="finSalesForm" property="status" value="4">��Ʊ���</logic:equal>
	</td>
  </tr>
</table> -->
<%@ include file="finance_sales_detail_common.html" %>
<!-- <TABLE width="95%" align="center">
<TR>
	<TD><a href="javascript:detail_f();" id="view_ctrl">��ʾ��ϸ</a></TD>
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
		<td class=OraTableCellText noWrap align=right ><bean:write name="finSalesForm" property="soAmt" format="#0.00"/></td>
	</tr>
</table> -->
<%@ include file="finance_sales_detail_items_common.html" %>
<TABLE align="center">
<TR>
	<TD>
		<input type="button" value=" ���� " onclick="history.back(-1);">
	</TD>
</TR>
</TABLE>

</body>
</html>
