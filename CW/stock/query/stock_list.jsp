<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	
	document.forms[0].offset.value = 0;
	theForm.query.disabled;
}

function load() {

	document.forms[0].itemCode.focus();
}
function viewProductPurchase(itemID){
	openWin("../crmjsp/sto_pur_mst_list2.jsp?itemId="+itemID,"wins",900,480);
}
//-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" onload="load()">
<html:form action="/stockQuery.do?" onsubmit="return doSearch();">
<html:hidden name="stockForm" property="offset"/>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">������</font><font color="838383"> 
      		-&gt; </font><font color="838383">����ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table  align="center" width="98%" border=0 cellspacing=1 cellpadding=0 >
	<tr>
		<td>�� �� �ţ�
		<html:select property="stoNO">
				<option value="">-- ��ѡ�� --</option>
				<html:optionsCollection name="stockForm" property="storageList" value="code" label="name"/> 
		</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
		
		��Ʒ���룺
		&nbsp;&nbsp;<html:text property="itemCode" size="12" />&nbsp;&nbsp;&nbsp;&nbsp;
		��Ʒ���ƣ�
		&nbsp;&nbsp;<html:text property="itemName" size="12" />&nbsp;&nbsp;&nbsp;&nbsp;
		
		</td>
	</tr>
	<tr>
		<td>
		��Ʒ���ͣ�
		<html:select property="itemType">
				<option value="">-- ��ѡ�� --</option>
				<html:optionsCollection name="stockForm" property="itemTypeList" value="code" label="name"/> 
		</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
		
		<input type="submit" name="query" value=" ��ѯ ">
		</td>
	</tr>
</table>
<table width="98%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
 
	<td>
		<bean:define name="stockForm" property="pager" id="pager"/>
		<logic:present name="pager">
		<bean:write name="pager" property="pageNavigation" filter="false"/>
		</logic:present>
	</td>
  </tr>
</table>
<table width="98%" align="center" border=0  id="DataTable">
	<tr>
		<th width="8%"  class="OraTableRowHeader"  >��Ʒ����</th>
		<th width="24%" class="OraTableRowHeader"  >��Ʒ����</th>
		<th width="9%"  class="OraTableRowHeader"  >��������</th>
		<th width="8%"  class="OraTableRowHeader"  >��������</th>
		<th width="8%"  class="OraTableRowHeader"  >���µ�����</th>
		<th width="8%"  class="OraTableRowHeader"  >��;����</th>
		<th width="8%"  class="OraTableRowHeader"  >�������</th>
		<!-- <th width="8%"  class="OraTableRowHeader"  >�ɹ��ɱ�</th>
		<th width="8%"  class="OraTableRowHeader"  >���</th> -->
	</tr>
    <logic:iterate id="list" name="list"> 
	<bean:define name="list" property="product" id="product" />
	<tr>		
    <td class=OraTableCellText noWrap align="left">
	<a href="javascript:viewProductPurchase('<bean:write name="list" property="itemID"/>')">
	<bean:write name="product" property="itemCode"/>
	</a>
	</td>
	<td class=OraTableCellText noWrap align="left" >
	<bean:write name="product" property="name"/>
	</td>
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="useQty" format="#0" filter="false"/>
	</td>
	
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="frozenQty" format="#0" filter="false"/>
	</td>

	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="uncheckQty" format="#0" filter="false"/>
	</td>
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="onthewayQty" format="#0" filter="false"/>
	</td>
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="inboundQty" format="#0" filter="false"/>
	</td>
	
	<!-- <td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="purchasingCost" format="#0.00" filter="false"/>
	</td>
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="list" property="amt" format="#0.00" filter="false"/>
	</td> -->
	</tr>
	</logic:iterate>
</table>

</html:form>
</body>
</html>
