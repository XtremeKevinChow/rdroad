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
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	var nCondition = 0;
	if(trim(theForm.cardID.value) != "") nCondition++;
	if(nCondition == 0) {
		// û�в�ѯ����
		alert("�������ѯ������");
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
		if (confirm("ȷʵҪ�һ���?"))
		{
			if ("<%= flag %>" == "order") //���ﳵ���ݹ����ı��
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
		alert("��ѡ���¼!");
	}
}

//ȫѡ��ֵ
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
//��ʼ��
function init() {
	if ("<%= flag %>" == "order") //���ﳵ���ݹ����ı��
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
    <td> <font color="#838383"><b>��Ա���� -&gt; ���ֶһ���Ʒ�б�</b></font></td>
  </tr>
</table>
<html:form action="/expExchange.do?type=queryValidGift" onsubmit="return doSearch();">
<input name="isAdd" value="<%=isAdd%>" type="hidden">
<input name="flag" value="<%=flag%>" type="hidden">
<table  align="center" width="95%" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>��Ա���ţ�
		<html:hidden property="memberID" />
		<html:text property="cardID" size="12" />&nbsp;&nbsp;&nbsp;&nbsp;
		��Ʒ�ţ�
		<html:text property="itemCode" size="12" />
		&nbsp;
		<input type="submit" name="query" value=" ��ѯ ">
		</td>
		
	</tr>
	<logic:notEqual name="expExchangeForm" property="cardID" value="">
	<tr>
		<td><span class="style1">��Ա������</span><bean:write name="expExchangeForm" property="memberName"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="style1">��Ȼ��֣�</span><bean:write name="expExchangeForm" property="amountExp"/>
			<html:hidden property="amountExp" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="addBtn" value=" �һ� " onclick="exchange_f(this)">
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
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  ><!-- ȫѡ<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"> --></th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >����</th>
		<th width="30%"  class="OraTableRowHeader" noWrap align=middle  >��Ʒ����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >���״̬</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >�һ�����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >�һ��۸�</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >��ʼ����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >��������</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >��Ч����</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >�Ƿ���Ч</th>
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
	<!-- <logic:equal name="giftList" property="stockStatus" value="�������">�������</logic:equal>
	<logic:equal name="giftList" property="stockStatus" value="�ֿ�ȱ��"><font color="red">�ֿ�ȱ��</font></logic:equal> -->
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
			
			<input type="button" name="addBtn" value=" �һ� " onclick="exchange_f(this)">&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
