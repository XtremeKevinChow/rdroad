<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function cancelOrder() {
	if(confirm("ȷ��ȡ���ö�����")) {
		document.forms[0].action = "groupOrderUpdate.do?type=cancelOrder";
		document.forms[0].submit();
	}
}

function modifyOrder() {
	document.forms[0].action = "groupOrderUpdate.do?type=updateFirst";
	document.forms[0].submit();
}
function runOrder() {
	document.forms[0].action = "groupOrderUpdate.do?type=runOrder";
	document.forms[0].submit();
}
function cancelModifyOrder() {
	document.forms[0].action = "groupOrderUpdate.do?type=cancelUpdate";
	document.forms[0].actionType.value = "cancelModify";
	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<bean:define id="orgCart" name="orderForm" property="orgCart"/>
<bean:define id="orgMember" name="orgCart" property="orgMember"/>
<bean:define id="orgOrder" name="orgCart" property="orgOrder"/>
<bean:define id="deliveryInfo" name="orgCart" property="deliveryInfo"/>
<bean:define id="otherInfo" name="orgCart" property="otherInfo"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0" onload="document.forms[0].btnsearch.disabled;">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; �Ź�������ϸ��Ϣ</font></td>
  </tr>
</table>
<html:form action="/orderView.do">
  <table width="95%" align="center" cellspacing="0" border="0">
    <tr>
      <td><b>������ţ�</b>
	  <bean:write name="orgOrder" property="orderNumber"/>
	  <input type="hidden" name="orderId"  value="<bean:write name="orgOrder" property="orderId" format="#0"/>"></td>
    </tr>
    <tr> 
      <td><b>������Դ��</b><bean:write name="orgOrder" property="prTypeName"/></td>
    </tr>
  </table>
  <table  align="center" width="95%" cellspacing="0" border="0">
    <tr> 
      <td><b>��Ա������Ϣ</b></td>
    </tr>
  </table>
  <table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <tr height="26"> 
    <td >��Ա�ţ�</td>
    <td  bgcolor="#FFFFFF" ><bean:write name="orgMember" property="CARD_ID"/>
	<input type="hidden" name="mbId" value="<bean:write name="orgMember" property="ID" format="#0"/>"></td>
    <td >��Ա������</td>
    <td  bgcolor="#FFFFFF"><bean:write name="orgMember" property="NAME"/></td>
  </tr>
</table>
  <br>
  <table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
    <tr class="OraTableRowHeader" noWrap  height="26"> 
		<td align="center"><b>����</b></td>
		<td align="center"><b>����</b></td>
		<td align="center"><b>��ɫ</b></td>
		<td align="center"><b>�ߴ�</b></td>
		<td align="center"><b>���۷�ʽ</b></td>
		<td align="center"><b>����</b></td>
		<td align="center"><b>��λ</b></td>
		<td align="center"><b>�ɱ���</b></td>
		<td align="center"><b>�г���</b></td>
		
		<td align="center"><b>�ۿۼ�</b></td>
		<!-- <td align="center"><b>��������</b></td> -->
		<td align="center"><b>�ϼ�</b></td>
    </tr>
    <bean:define id="items" name="orgCart" property="items" type="java.util.Collection"/> 
    <logic:iterate name="items" id="item"> 
    <tr >
		<td align="center">&nbsp;<bean:write name="item" property="itemCode"/>
		<logic:notEmpty name="item" property="set_code">
            (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
		</td>
		<td align="center">&nbsp;<bean:write name="item" property="itemName"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="color_name"/>(<bean:write name="item" property="color_code"/>)</td>
		<td align="center">&nbsp;<bean:write name="item" property="size_code"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="sellTypeName"/></td>
		
		<td align="center">&nbsp;<bean:write name="item" property="itemQty" format="#"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="itemUnit"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="purchaseingCost" format="#0.00"/></td>
		<td align="center">&nbsp;<bean:write name="item" property="standardPrice" format="#0.00"/></td>
		
		<td align="center">&nbsp;<bean:write name="item" property="discountPrice" format="#0.00"/></td>
		<!-- <td align="center">&nbsp;<bean:write name="item" property="landDate"/></td> -->
		<td align="center">&nbsp;<bean:write name="item" property="groupItemMomey" format="#0.00"/></td>
	</tr>
    </logic:iterate> 
    <tr> 
      <td colspan="10">������</td>
      <td  align="center">&nbsp;<bean:write name="orgOrder" property="goodsFee" format="#0.00" ignore="true"/></td>
    </tr>
  </table>
  <input type="hidden" name="actionType"><br>
  <table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
    <tr> 
      <td colspan="2"> <img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
        <font color="#990000"><b>�ͻ���ַ��Ϣ</b></font> </td>
      <td width="180"> <img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
        <font color="#990000"><b>�ͻ���ʽ</b></font> </td>
    </tr>
    <tr> 
      <td width="75">&nbsp;&nbsp;������</td>
      <td width="350">&nbsp;<bean:write name="deliveryInfo" property="receiptor" ignore="true"/></td>
      <td width="180"> &nbsp;&nbsp;<bean:write name="deliveryInfo" property="deliveryType" ignore="true"/> 
      </td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;�绰��</td>
      <td><bean:write name="deliveryInfo" property="phone" ignore="true"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;�ͻ���ַ��</td>
      <td><bean:write name="deliveryInfo" property="address" ignore="true"/></td>
      <td> <img src="/images/gouwu/1dian.GIF" width="7" height="6"> <font color="#990000"><b>���ʽ</b></font> 
      </td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;�ʱࣺ</td>
      <td><bean:write name="deliveryInfo" property="postCode" ignore="true"/></td>
      <td> &nbsp;&nbsp;<bean:write name="deliveryInfo" property="paymentType" ignore="true"/> 
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
  <table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
    <tr> 
      <td width="50%"> <b><font color="#990000">ȱ����Ʒ����ʽ</font></b> </td> 
      <td width="50%"> <b><font color="#990000">����Ҫ��Ʊ��</font></b> </td>
    </tr>
     <tr>
      <td>&nbsp; <logic:equal name="otherInfo" property="OOSPlan" value="2">ȫ����Ʒ�����ٷ�</logic:equal> 
        <logic:equal name="otherInfo" property="OOSPlan" value="3">���ȴ���ȡ��������ȱ����Ʒ</logic:equal></td>
      <td>&nbsp; <logic:equal name="otherInfo" property="needInvoice" value="1">Ҫ</logic:equal> 
        <logic:equal name="otherInfo" property="needInvoice" value="0">��Ҫ</logic:equal> 
      </td>
    </tr>
    <tr> 
      <td colspan="2"><strong><font color="#990000">��ע��Ϣ</font></strong><br>
	  <logic:empty name="otherInfo" property="remark">&nbsp;���ޣ�</logic:empty>
	  <bean:write name="otherInfo" property="remark" filter="true" ignore="true"/>
      </td>
    </tr>
  </table>
  <table width="95%" align="center" cellspacing="0" border="0"  >
	
	<tr><td height="10"></td></tr>
	<tr>
		<td align="center">
			<input name="BtnBack" type="button" value=" ���� " onclick="history.go(-1)" >
			<logic:equal name="orgOrder" property="modifiable" value="true"><input name="BtnModify" type="button" value="�޸Ķ���" onclick="modifyOrder();"></logic:equal>
			<logic:equal name="orgOrder" property="statusId" value="-6"><input name="BtnCancelModify" type="button" value="ȡ���޸�" onclick="cancelModifyOrder();"></logic:equal>
			<logic:equal name="orgOrder" property="cancelable" value="true"><input name="BtnCancel" type="button" value="ȡ������" onclick="cancelOrder();"></logic:equal>
			<!--<logic:equal name="orgOrder" property="runnable" value="true"><input name="BtnRun" type="button" value="���ж���" onclick="runOrder();"></logic:equal>-->
		</td>
	</tr>
</table>
<br>
</html:form>
</body>
</html>
