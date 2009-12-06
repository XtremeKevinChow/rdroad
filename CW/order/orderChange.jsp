<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ page import="com.magic.crm.order.form.OrderForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="initHotkey();document.forms[0].queryItemCode.focus();">

<html:form action="/orderChange.do?type=changeSubmit"> 
<bean:define id="cart" name="orderForm" property="cart" type="com.magic.crm.order.entity.ShoppingCart2"/>
<bean:define id="member" name="cart" property="member" type="com.magic.crm.member.entity.Member"/>
<bean:define id="deliveryInfo" name="cart" property="deliveryInfo" type="com.magic.crm.order.entity.DeliveryInfo"/>
<html:hidden name="orderForm" property="orderId"/>
<input type="hidden" name="mbId" value="<bean:write name="member" property="ID" format="#0"/>">
<input type="hidden" name="maxqty">
<table  align="center" width="95%" cellspacing="0" border="0">
    <tr> 
      <td><b>ԭʼ��Ʒ��Ϣ</b>&nbsp;<font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
    </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648"  >
  <tr class="OraTableRowHeader" noWrap  height="26"> 
    <td align="center"><b>����</b></td>
    <td align="center"><b>����</b></td>
    <td align="center"><b>���۷�ʽ</b></td>
    <td align="center"><b>���״̬</b></td>
    <td align="center"><b>��ɫ</b></td>
    <td align="center"><b>����</b></td>
    <td align="center"><b>����</b></td>
    <td align="center"><b>����</b></td>
    <td align="center"><b>�ϼ�</b></td>
    <td width="80" align="center"><B>����</B></td>
  </tr>
  
  <bean:define id="gifts" name="cart" property="gifts" type="java.util.Collection"/> 
  <% int gift_index=0; %>
  <logic:iterate name="gifts" id="gift"> 
  <tr 
  <logic:equal name="gift" property="sku_id" value="0">
  bgcolor="yellow"
  </logic:equal>
  > <html:hidden name="gift" property="sku_id"/>
    <td align="center"><bean:write name="gift" property="itemCode" format="#"/> 
      <logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_code"/>)
      </logic:notEmpty>
      </td>
    <td>
    <bean:write name="gift" property="itemName" ignore="true"/>
    </td>
    <td><bean:write name="gift" property="sellTypeName" ignore="true"/>
    <logic:equal name="gift" property="is_transfer" value="1">
    <font color="red">(ת����Ʒ)</font>
    </logic:equal>   
    </td>
    <td align="left">
    <logic:notEqual name="gift" property="stockStatusName" value="�������">
	<font color=red><bean:write name="gift" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
	<logic:equal name="gift" property="stockStatusName" value="�������">
    <bean:write name="gift" property="stockStatusName" ignore="true"/>
    </logic:equal>
	</td>
    <td><bean:write name="gift" property="color_name"/>(<bean:write name="gift" property="color_code"/>)</td>
    <td><bean:write name="gift" property="size_code"/></td>
    <td align="center"><bean:write name="gift" property="itemQty" format="#" ignore="true"/></td>
    <!--<td align="center">&nbsp;<bean:write name="gift" property="itemUnit" ignore="true"/></td>-->
    <td align="right"><bean:write name="gift" property="itemPrice" format="#0.00" ignore="true"/> 
      <html:hidden name="gift" property="itemPrice"/>
      <logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_price" format="#0.00" ignore="true"/>)
      </logic:notEmpty>
      </td>
    <!--<td align="center">&nbsp;<bean:write name="gift" property="landDate" ignore="true"/></td>-->
    <td align="right"><bean:write name="gift" property="itemMoney" format="#0.00" ignore="true"/></td>
    <td> 
      <input name="BtnDelete" value="����" type="button" onClick="changeItem_C(<%=gift_index%>);">
	  
	  <input name="BtnDelete3" value="�˻�" type="button" onClick="returnItem_C(<%=gift_index%>);">
	  
    </td>
  </tr>
  <% gift_index++; %>
  </logic:iterate>
</table>
<table  align="center" width="95%" cellspacing="0" border="0">
    <tr> 
      <td><b>�˻�����Ʒ��Ϣ</b>&nbsp;<font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
    </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
  <tr class="OraTableRowHeader" noWrap  height="26"> 
    <td align="center"><b>����</b></td>
    <td align="center"><b>����</b></td>
    <td align="center"><b>���۷�ʽ</b></td>
    <td align="center"><b>���״̬</b></td>
    <td align="center"><b>��ɫ</b></td>
    <td align="center"><b>����</b></td>
    <td align="center"><b>����</b></td>
    <td width="80" align="center"><B>����</B></td>
  </tr>
  <%int item_index=0; %>
  <bean:define id="items" name="cart" property="items" type="java.util.Collection"/> 
  <logic:iterate name="items" id="item"> 
  <tr <logic:equal name="item" property="sku_id" value="0">
  bgcolor="yellow"
  </logic:equal>
  > <html:hidden name="item" property="sku_id"/>
    <td align="center">&nbsp;<bean:write name="item" property="itemCode"/> 
        <logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
      <html:hidden name="item" property="sku_id"/> </td>
    <td><bean:write name="item" property="itemName" ignore="true"/>      
    </td>
    <td><bean:write name="item" property="sellTypeName" ignore="true"/></td>
    
    <td>   
    <logic:notEqual name="item" property="stockStatusName" value="�������">
	<logic:notEqual name="item" property="stockStatusName" value="����ȱ��">
    <font color=red><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
	</logic:notEqual>
    <logic:equal name="item" property="stockStatusName" value="�������">
    <bean:write name="item" property="stockStatusName" ignore="true"/>
    </logic:equal>
	<logic:equal name="item" property="stockStatusName" value="����ȱ��">
    <font color=green><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:equal>
    </td>
     <td>
    <html:select name="item" property="color_code">
    <html:optionsCollection  name="item" property="colors" />
    </html:select>
    </td>
    <td>
    <html:select name="item" property="size_code">
    <html:optionsCollection  name="item" property="sizes" />
    </html:select>
    </td>
    <td align="center"> 

	<input type="text" name="itemQty" value=<bean:write name="item" property="itemQty"/> onblur="checkPositiveInteger(this);" size="4"/>
    </td>
    <td> 
      <input name="BtnDelete" value="ɾ��" type="button" onclick=deleteItem1_C(<%=item_index%>)>
      <!--
      <logic:notEqual name="item" property="sku_id" value="0">
      <input name="BtnDelete" value="�ɹ���Ϣ" type="button" onClick=pop_poinfo(<bean:write name="item" property="sku_id"/>)>
      </logic:notEqual>
      -->
	  <logic:notEqual name="item" property="sellTypeId" value="-1"><!-- �������Ͳ���-1����ʾ -->
      <input name="BtnUdate" value="ȷ��" type="button" onclick=updateItem_C(<%=item_index%>)>
	  </logic:notEqual>
	</td>
  </tr>
  
  <% item_index++; %>
  </logic:iterate>

  <% item_index=0; %>
  <bean:define id="items" name="cart" property="allGifts" type="java.util.Collection"/> 
  <logic:iterate name="items" id="item">   
  <tr bgcolor="yellow" > <html:hidden name="item" property="sku_id"/>
    <td align="center">&nbsp;<bean:write name="item" property="itemCode"/> 
        <logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
      <html:hidden name="item" property="sku_id"/> </td>
    <td><bean:write name="item" property="itemName" ignore="true"/>      
    </td>
    <td><bean:write name="item" property="sellTypeName" ignore="true"/></td>
    
    <td>   
    <logic:notEqual name="item" property="stockStatusName" value="�������">
	<logic:notEqual name="item" property="stockStatusName" value="����ȱ��">
    <font color=red><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
	</logic:notEqual>
    <logic:equal name="item" property="stockStatusName" value="�������">
    <bean:write name="item" property="stockStatusName" ignore="true"/>
    </logic:equal>
	<logic:equal name="item" property="stockStatusName" value="����ȱ��">
    <font color=green><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:equal>
    </td>
    <td><bean:write name="item" property="color_name"/>(<bean:write name="item" property="color_code"/>)</td>
    <td><bean:write name="item" property="size_code"/></td>
    <td align="center"> <bean:write name="item" property="itemQty"/></td>
    <td> 
      <input name="BtnDelete" value="ɾ��" type="button" onclick=deleteItem2_C(<%=item_index%>)>
	</td>
  </tr>
  <% item_index++; %>
  </logic:iterate>
  

</table>
<table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
	<tr>
		<td colspan="2">
			<img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
			<font color="#990000"><b>�ͻ���ַ��Ϣ</b></font>
		</td>
		
	</tr>
	<tr>
		<td width="75">&nbsp;&nbsp;������</td>
		<td width="350">&nbsp;<bean:write name="deliveryInfo" property="receiptor" ignore="true"/></td>
		
	</tr>
	<tr>
		<td>&nbsp;&nbsp;�绰��</td>
		<td><bean:write name="deliveryInfo" property="phone" ignore="true"/>&nbsp;&nbsp;
		<bean:write name="deliveryInfo" property="phone2" ignore="true"/></td>
						
	</tr>
	<tr>
		<td>&nbsp;&nbsp;�ͻ���ַ��</td>
		<td><bean:write name="deliveryInfo" property="address" ignore="true"/></td>
		
	</tr>
	<tr>
		<td>&nbsp;&nbsp;�ʱࣺ</td>
		<td><bean:write name="deliveryInfo" property="postCode" ignore="true"/></td>
		
	</tr>
	<tr>
	  <td colspan="2"><input name="chgaddress" type="button" value="�޸��ͻ���ַ" onclick="openWin('../order/deliveryInfoList.do?mbId=<bean:write name="member" property="ID" format="#"/>&page=orderChange', '', 650, 500)">
	  <input type="hidden" name="receiptorAddressId" value="<bean:write name="deliveryInfo" property="addressId" ignore="true" format="#"/>">
    </td>
	</tr>
</table>
<table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td height="10"></td>
  </tr>
  <tr> 
    <td align="center"> 
      <input name="BtnReturn" type="button" value=" �� �� " onclick=ajaxpage2("../order/orderView.do?isconsole=Y","ajaxcontentarea",document.forms[0])>
      <input name="BtnNextStep" type="button" value=" ȷ �� " onclick=ajaxpage2("../order/orderChange.do?type=changeSubmit","ajaxcontentarea",document.forms[0])>
    </td>
  </tr>
</table>
<input type="hidden" name="actionType" value="">
<input type="hidden" name="operateId" value="-1">
<input type="hidden" name="sellTypeId" value="-1">
<br>

</html:form> 
</body>
</html>
