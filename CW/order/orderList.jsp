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
	if(trim(theForm.cardId.value) != "") nCondition++;
	if(trim(theForm.mbName.value) != "") nCondition++;
	if(trim(theForm.orderNumber.value) != "") nCondition++;
	
	if(nCondition == 0) {
		// û�в�ѯ����
		alert("�������ѯ������");
	} else {
		submitForm();
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="/orderAQuery.do?type=list">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
    <td><bean:write name="orderForm" property="pageNavigator" filter="false"/></td>
  </tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����Ӧ��</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��ԱӦ��</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����״̬</th>		
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������Դ����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�µ�ʱ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������Ա</th>
	</tr>
	<% int i=0; %>
    <logic:iterate  name="order_list" id="order"> 
	<bean:define id="member" name="order" property="member"/>
	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %> >		
        
    <td noWrap align=center ><a href="orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a></td>
		
    <td noWrap align=center ><a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a></td>
		
    <td noWrap align=center ><bean:write name="member" property="NAME"/></td>
		
    <td noWrap align=right ><bean:write name="order" property="goodsFee" format="#0.00"/></td>
		
    <td noWrap align=right ><bean:write name="order" property="payable" format="#0.00"/></td>
	<td align=right ><bean:write name="order" property="mbPayable" format="#0.00"/></td>	
    <td noWrap align=left ><bean:write name="order" property="statusName"/></td>		
		
    <td noWrap align=left ><bean:write name="order" property="prTypeName"/></td>
		
    <td noWrap align=left ><bean:write name="order" property="categoryName"/></td>
		
    <td noWrap align=left ><bean:write name="order" property="createDate"/></td>
		
    <td noWrap align=left ><bean:write name="order" property="creatorName"/>&nbsp;<a href="../order/order_query_history.jsp?so_number=<bean:write name='order' property='orderNumber'/>">������ʷ</href></td>
	</tr>
	<% i++;%>
	</logic:iterate>
</table>
</html:form>
</body>
</html>