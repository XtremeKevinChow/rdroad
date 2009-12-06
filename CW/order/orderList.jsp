<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
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
		// 没有查询条件
		alert("请输入查询条件！");
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
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号码</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员姓名</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >购物金额</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单应付</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员应付</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单状态</th>		
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单来源类型</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单类型</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >下单时间</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >经办人员</th>
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
		
    <td noWrap align=left ><bean:write name="order" property="creatorName"/>&nbsp;<a href="../order/order_query_history.jsp?so_number=<bean:write name='order' property='orderNumber'/>">操作历史</href></td>
	</tr>
	<% i++;%>
	</logic:iterate>
</table>
</html:form>
</body>
</html>