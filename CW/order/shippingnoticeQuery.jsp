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
function doSearch() {
	var theForm = document.forms[0];
	var nCondition = 0;
	if(trim(theForm.strQrySnCode.value) != "") nCondition++;
	if(trim(theForm.strQryShippingNumber.value) != "") nCondition++;
	if(trim(theForm.strQryOrdCode.value) != "") nCondition++;
	if(trim(theForm.strQryMbrCode.value) != "") nCondition++;
	if(trim(theForm.strQryMbrName.value) != "") nCondition++;
	if(trim(theForm.strQryTelephone.value) != "") nCondition++;
	if(nCondition == 0) {
		// û�в�ѯ����
		alert("�������ѯ������");
		return false;
	} 
	
}
function f_snAQry() {
	document.forms[0].action="snAQuery.do?type=init";
	document.forms[0].submit();
}
function viewCY(url){
	location.href = url;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td align=left> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ��������ѯ</font></td>
  </tr>
</table>
<html:form action="/snQry.do" onsubmit="return doSearch();">
  <table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
    	<td>
    	�������ţ�
        <html:text name="snForm" property="strQrySnCode" size="14"/>
        �˵���
        <html:text name="snForm" property="strQryShippingNumber" size="14"/>
        �����ţ�
        <html:text name="snForm" property="strQryOrdCode" size="14"/>
        ��Ա�ţ�
        <html:text name="snForm" property="strQryMbrCode" size="8"/>
        ��Ա������
        <html:text name="snForm" property="strQryMbrName" size="8"/>
        ��Ա�绰��
        <html:text name="snForm" property="strQryTelephone" size="10"/>
        <input name="BtnQuery" type="submit" value=" ��ѯ ">
        <input name="btnAQry" type=button value="�߼���ѯ" onclick="javascript:f_snAQry();">
      </td>		
	</tr>
</table>
<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��ϵ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ʱ�</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ͻ���ʽ</th>
		<!--<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>-->		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ʱ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ʱ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�˻���</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >״̬</th>
		<!--<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�˻�����</th>-->
	</tr>
    
    <logic:iterate id="mst" name="list" > 
    
    <!-- order for one shippingnotice -->
	<bean:define id="order" name="mst" property="order"/>
	<!-- member for one order -->
	<bean:define id="member" name="order" property="member"/>
	<!-- delivery infomation for one order -->
	<bean:define id="deliveryInfo" name="order" property="deliveryInfo"/>
	<tr>		
    <td class=OraTableCellText noWrap align=center ><a href="snView.do?sn_id=<bean:write name="mst" property="sn_id" format="#"/>"><bean:write name="mst" property="sn_code"/></a></td>    
    <td class=OraTableCellText noWrap align=center ><a href="orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a></td>
	<td class=OraTableCellText noWrap align=center ><a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a></td>
			
    <td class=OraTableCellText noWrap align=center ><bean:write name="deliveryInfo" property="receiptor"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="deliveryInfo" property="postCode"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="deliveryInfo" property="deliveryType"/></td>
	<!--<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="lot"/></td>-->
	<td class=OraTableCellText  align=center ><bean:write name="mst" property="print_date" /></td>
	<td class=OraTableCellText  align=center ><bean:write name="mst" property="checkDate" /></td>
	<td class=OraTableCellText  align=center ><bean:write name="mst" property="checkPersonName" /></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="status_name"/></td>		
	<!--<td class=OraTableCellText noWrap align=center >
		<logic:equal name="mst" property="status" value="39">
    	<input name = "btn" type ="button" value="�鿴" onclick=viewCY("/order/order_return.jsp?queryBy=shippingnotice&barcode=<bean:write name='mst' property='sn_code'/>") >
		</logic:equal>	
		<logic:equal name="mst" property="status" value="-5">
    	<input name = "btn" type ="button" value="�鿴" onclick=viewCY("/order/order_return.jsp?queryBy=shippingnotice&barcode=<bean:write name='mst' property='sn_code'/>") >
		</logic:equal>	
	</td>-->		
		
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
