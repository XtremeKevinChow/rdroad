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
		document.forms[0].action = "orderCancel.do";
		document.forms[0].submit();
	}
}

function modifyOrder() {
	document.forms[0].action = "orderModifyFirst.do?type=init";
	document.forms[0].submit();
}

function cancelModifyOrder() {
	document.forms[0].action = "orderView.do";
	document.forms[0].actionType.value = "cancelModify";
	document.forms[0].submit();
}
function viewGifts(orderId) {
	openWin('./orderGiftsView.do?orderId='+orderId, 'PopWin', 320, 200);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	
    <td> <b><font color="#838383">��ǰλ�ã�</font></b><font color="#838383"> �ͻ����� 
      -&gt; ������ϸ��Ϣ</font> </td>
   </tr>
</table> -->

<html:form action="/orderView.do">
<bean:define id="cart" name="orderForm" property="cart" type="com.magic.crm.order.entity.ShoppingCart2"/>
<bean:define id="order" name="cart" property="order" type="com.magic.crm.order.entity.Order"/>
<bean:define id="member" name="cart" property="member" type="com.magic.crm.member.entity.Member"/>
<bean:define id="deliveryInfo" name="cart" property="deliveryInfo" type="com.magic.crm.order.entity.DeliveryInfo"/>
<bean:define id="otherInfo" name="cart" property="otherInfo" type="com.magic.crm.order.entity.OtherInfo"/>
  <table width="95%" align="center" cellspacing="0" border="0">
    <tr>
      <td><b>������ţ�</b><bean:write name="order" property="orderNumber"/>
	  <input type="hidden" name="orderId" value="<bean:write name="order" property="orderId" format="#0"/>">
	  </td>
	  <td><b>������Դ��</b><bean:write name="order" property="prTypeName"/></td>
	  <td><b>�������ͣ�</b>
	  <logic:equal name="order" property="orderType" value="10">��ͨ����</logic:equal>
	  <logic:equal name="order" property="orderType" value="5">Ԥ�۶���</logic:equal>
	  <logic:equal name="order" property="orderType" value="15">�Ź�����</logic:equal>
	  <logic:equal name="order" property="orderType" value="20">������</logic:equal>
	  </td>
	  <td>
	  <b>����״̬��</b>
	  <bean:write name="order" property="statusName"/>
	  </td>
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
    <td  bgcolor="#FFFFFF" ><a href="/member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a>
	
	<input type="hidden" name="mbId" value="<bean:write name="member" property="ID" format="#0"/>">
	</td>
    <td >��Ա������</td>
    <td  bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
    <logic:equal name="member" property="IS_ORGANIZATION" value="0" >
    <td>��Ա�ʻ�:</td>
    <td bgcolor="#FFFFFF"><bean:write name="member" property="DEPOSIT" format="#0.00"/></td>
    <td>�����ʻ�:</td>
    <td bgcolor="#FFFFFF"><bean:write name="member" property="FORZEN_CREDIT" format="#0.00"/></td>
    </logic:equal>
  </tr>
</table>
  <br>
  <table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
    <tr class="OraTableRowHeader" noWrap  height="26"> 
      <td align="center"><b>����</b></td>
      <td align="center"><b>����</b></td>
      <td align="center"><b>���۷�ʽ</b></td>
      <td align="center"><b>��ɫ</b></td>
      <td align="center"><b>�ߴ�</b></td>
      <td align="center"><b>����</b></td>
      <td align="center"><b>��λ</b></td>
      <td align="center"><b>����</b></td>
      <td align="center"><b>��Ʒ״̬</b></td>
      <!--<td align="center"><b>��������</b></td>-->
      <td align="center"><b>�ϼ�</b></td>
    </tr>
    <bean:define id="items" name="cart" property="items" type="java.util.Collection"/> 
    <logic:iterate name="items" id="item"> 
    <tr > 
      <td align="center">&nbsp;<bean:write name="item" property="itemCode"/>
        <logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
      </td>
      <td>&nbsp;<bean:write name="item" property="itemName"/></td>
      <td>&nbsp;<bean:write name="item" property="sellTypeName"/></td>
      <td align="left">&nbsp;<bean:write name="item" property="color_name"/>(<bean:write name="item" property="color_code"/>)</td>
      <td align="left">&nbsp;<bean:write name="item" property="size_code"/></td>
      <td align="right">&nbsp;<bean:write name="item" property="itemQty" format="#"/></td>
      <td align="left">&nbsp;<bean:write name="item" property="itemUnit"/></td>
      <td align="right">&nbsp;<bean:write name="item" property="itemPrice" format="#0.00"/></td>
      <td>&nbsp;<bean:write name="item" property="status"/></td>
      <!--<td align="center">&nbsp;<bean:write name="item" property="landDate"/></td>-->
      <td align="right">&nbsp;<bean:write name="item" property="itemMoney" format="#0.00"/></td>
    </tr>
    </logic:iterate> <bean:define id="gifts" name="cart" property="gifts" type="java.util.Collection"/> 
    <logic:iterate name="gifts" id="gift"> 
    <tr > 
      <td align="center">&nbsp;<bean:write name="gift" property="itemCode"/>
      <logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_code"/>)
        </logic:notEmpty>
      </td>
      <td>&nbsp;<bean:write name="gift" property="itemName"/></td>
      <td>&nbsp;<bean:write name="gift" property="sellTypeName"/></td>
      <td align="left">&nbsp;<bean:write name="gift" property="color_name"/>(<bean:write name="gift" property="color_code"/>)</td>
        <td align="left">&nbsp;<bean:write name="gift" property="size_code"/></td>
      <td align="right">&nbsp;<bean:write name="gift" property="itemQty" format="#"/></td>
      <td align="left">&nbsp;<bean:write name="gift" property="itemUnit"/></td>
      <td align="right">&nbsp;<bean:write name="gift" property="itemPrice" format="#0.00"/></td>
      <td align="left">&nbsp;<bean:write name="gift" property="status"/></td>
      <!--<td align="center">&nbsp;<bean:write name="gift" property="landDate"/></td>-->
      <td align="right">&nbsp;<bean:write name="gift" property="itemMoney" format="#0.00"/></td>
    </tr>
    </logic:iterate>
    <tr> 
      <td colspan="9" align="right">���۽��</td>
      <td colspan="1" align="right">&nbsp;<bean:write name="order" property="normalFee" format="#0.00" ignore="true"/></td>
    </tr> 
    <tr> 
      <td colspan="9" align="right">�����</td>
      <td colspan="1" align="right">&nbsp;<bean:write name="order" property="goodsFee" format="#0.00" ignore="true"/></td>
    </tr>
    <tr> 
      <td colspan="9" align="right">���ͷѣ�</td>
      <td colspan="1" align="right">&nbsp;<bean:write name="deliveryInfo" property="deliveryFee" format="#0.00" ignore="true"/></td>
    </tr>
	<tr>
		<td colspan="9" align="right">��װ�ѣ�</td>
		
    <td colspan="1" align="right">&nbsp;<bean:write name="cart" property="packageFee" format="#0.00" ignore="true"/></td>
	</tr>
    <tr> 
      <td colspan="9" align="right">��ȯ����<a href="javascript:viewGifts('<bean:write name="orderForm" property="orderId" format="#"/>');">[�鿴]</a>��</td>

      <td colspan="1" align="right">&nbsp;-<bean:write name="order" property="appendFee" format="#0.00"/></td>
    </tr>
    <tr>
		<td colspan="9" align="right">���ʽ�ۿ�</td>
		<td align="right">&nbsp;<bean:write name="order" property="discount_fee" format="#0.00"/></td>
	</tr>
    <tr>
		<td colspan="9" align="right">�ʻ����ã�</td>
		
    <td colspan="1" align="right">&nbsp;-<bean:write name="order" property="orderUse" format="#0.00" ignore="true"/>
    (&nbsp;-<bean:write name="order" property="orderEmoney" format="#0.00" ignore="true"/>)
    </td>
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
      <td width="180"> &nbsp;&nbsp;
      <bean:write name="deliveryInfo" property="deliveryType" ignore="true"/>
      <logic:equal name="deliveryInfo" property="deliveryType" value="�ʼ�">
      <input type="button" value="�ʰ�" onclick="openWin('../order/showPostPackage.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 150);">
	  <logic:equal name="order" property="changed" value="true">
	  <input type="button" value="�����ʰ�" onclick="openWin('../order/showChangedPostPackage.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 150);">
	  </logic:equal>

	  <logic:equal name="order" property="supply" value="true">
	  <input type="button" value="�����ʰ�" onclick="openWin('../order/showSupplyPostPackage.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 150);">
	  </logic:equal>
      </logic:equal>
	  
      </td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;�绰��</td>
      <td><bean:write name="deliveryInfo" property="phone" ignore="true"/>&nbsp;&nbsp;
		  <bean:write name="deliveryInfo" property="phone2" ignore="true"/>
	  </td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;�ͻ���ַ��</td>
      <td>[<bean:write name="deliveryInfo" property="sectionName" ignore="true"/>]<bean:write name="deliveryInfo" property="address" ignore="true"/></td>
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
      <td>&nbsp; 
      <logic:equal name="otherInfo" property="needInvoice" value="1">
      Ҫ &nbsp;&nbsp;<bean:write name="otherInfo" property="invoice_number"/>&nbsp;&nbsp;
      <bean:write name="otherInfo" property="invoice_title"/>
      </logic:equal> 
        <logic:equal name="otherInfo" property="needInvoice" value="0">��Ҫ</logic:equal> 
      </td>
    </tr>
    <tr> 
      <td width="50%"> <b><font color="#990000">���ÿ���</font></b> </td>
      <td width="50%"> <b><font color="#990000">���֤��</font></b> </td>
    </tr>
    <tr> 
      <td>&nbsp; 
        <bean:write name="otherInfo" property="credit_card" />
      </td>
      <td>&nbsp; 
        <bean:write name="otherInfo" property="id_card" />
      </td>
    </tr>
    <tr> 
      <td width="50%"> <b><font color="#990000">���ÿ���Ч������</font></b> </td>
      <td width="50%"> <b><font color="#990000">���ÿ�CV2����</font></b> </td>
    </tr>
    <tr> 
      <td>&nbsp; 
        <bean:write name="otherInfo" property="ef_year" />&nbsp;<bean:write name="otherInfo" property="ef_month" />
      </td>
      <td>&nbsp; 
        <bean:write name="otherInfo" property="ver_code" />
      </td>
    </tr>
    <tr> 
      <td colspan="2"><strong><font color="#990000">��ע��Ϣ</font></strong><br>
	  <logic:empty name="otherInfo" property="remark">&nbsp;���ޣ�</logic:empty>
	  <bean:write name="otherInfo" property="remark" filter="true" ignore="true"/>
      </td>
    </tr>
  </table>
  <TABLE width="95%" align="center">
	<tr>
		<td colspan="4"><strong><font color="#990000">���Ŀ¼</font></strong></td>
	</tr>
	
		
		<td>
			<bean:write name="otherInfo" property="catalog"/>
		</td>
	
</TABLE>
  <table width="95%" align="center" cellspacing="0" border="0"  >
	
	<tr><td height="10"></td></tr>
	<logic:equal parameter="isconsole" value="Y">
	<tr>
		<td align="center">
			<input name="BtnBack" type="button" value=" ���� " onclick=ajaxpage2("../member/consoleOrders.do","ajaxcontentarea",document.forms[0]) >
			<logic:equal name="order" property="changeable" value="true"><input name="BtnChange" type="button" value="�˻���" onclick=ajaxpage2("../order/orderChange.do?type=init","ajaxcontentarea",document.forms[0])> </logic:equal>
			<logic:equal name="order" property="modifiable" value="true"><input name="BtnModify" type="button" value="�޸Ķ���" onclick=ajaxpage2("../order/orderModifyFirst.do?type=init","ajaxcontentarea",document.forms[0])> </logic:equal>
			<logic:equal name="order" property="statusId" value="-6"><input name="BtnCancelModify" type="button" value="ȡ���޸�" onclick=ajaxpage2("../order/orderView.do?actionType=cancelModify","ajaxcontentarea",document.forms[0])></logic:equal>
			<logic:equal name="order" property="cancelable" value="true"><input name="BtnCancel" type="button" value="ȡ������" onclick="javascript:if(confirm('ȷ��ȡ���ö�����')) { ajaxpage2('../order/orderCancel.do?actionType=cancelModify','ajaxcontentarea',document.forms[0]) }"></logic:equal>
			
			<logic:equal name="order" property="statusId" value="22">
			<!--
			<input name="BtnChangeComplete" type="button" value="�ջ�ȷ��" onclick=ajaxpage2("../order/orderChange.do?type=changeConfirm","ajaxcontentarea",document.forms[0])>
			-->
			<input name="BtnChangeCancel" type="button" value="�˻���ȡ��" onclick=ajaxpage2("../order/orderChange.do?type=changeCancel","ajaxcontentarea",document.forms[0])>
			</logic:equal>
			<!--
			<logic:equal name="order" property="returned" value="true"><input name="BtnSeeRet" type="button" value="�˻�ԭ��" onclick="openWin('../order/showReturnReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
			<logic:equal name="order" property="changed" value="true"><input name="BtnSeeRet" type="button" value="����ԭ��" onclick="openWin('../order/showChangeReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
		    -->
		</td>
	</tr>
	</logic:equal>

	<logic:notEqual parameter="isconsole" value="Y">
	<tr>
		<td align="center">
			<input name="BtnBack" type="button" value=" ���� " onclick=window.history.back() >
			<!--
			<logic:equal name="order" property="modifiable" value="true"><input name="BtnModify" type="button" value="�޸Ķ���" onclick=alert("���½��Ա����̨�޸ģ�лл!"); return;> </logic:equal>
			
			<logic:equal name="order" property="statusId" value="-6"><input name="BtnCancelModify" type="button" value="ȡ���޸�" onclick=cancelModifyOrder()></logic:equal>
			<logic:equal name="order" property="cancelable" value="true"><input name="BtnCancel" type="button" value="ȡ������" onclick=cancelOrder()></logic:equal>
			<logic:equal name="order" property="returned" value="true"><input name="BtnSeeRet" type="button" value="�˻�ԭ��" onclick="openWin('../order/showReturnReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
			<logic:equal name="order" property="changed" value="true"><input name="BtnSeeRet" type="button" value="����ԭ��" onclick="openWin('../order/showChangeReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
		    -->
		</td>
	</tr>
	</logic:notEqual>
</table>
<br>
</html:form>
</body>
</html>
