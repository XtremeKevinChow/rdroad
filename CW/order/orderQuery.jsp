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
	if(trim(theForm.taobaoWangId.value) != "") nCondition++;
	
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入查询条件！");
		return false;
	} 
	theForm.BtnQuery.disabled;
}
function doNext(url){
	location.href = url;
}
function queryFahuo(url,orderNum){
	
	document.forms[1].strQryOrdCode.value = orderNum;
	document.forms[1].iscallcenter.value="fromOrderList";
	document.forms[1].action = url;
	document.forms[1].submit();
}
function viewCancelInfo(url) {
	openWin(url, 'PopWin', 200, 160);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td align="left"> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 订单查询</font></td>
  </tr>
</table>
<html:form action="/orderQuery.do" onsubmit="return doSearch();">
  <table width="98%" align="center" border=0 cellspacing=1 cellpadding=2 >
    <tr>
		<td>会员号：
        <html:text name="orderForm" property="cardId" size="12"/>
        会员姓名：
        <html:text name="orderForm" property="mbName" size="14"/>
        常用电话：
        <html:text name="orderForm" property="mbTelephone" size="14"/>
        订单号：
        <html:text name="orderForm" property="orderNumber" size="16"/>
        淘宝旺旺号:
        <html:text name="orderForm" property="taobaoWangId" size="20"/>
        &nbsp;&nbsp;&nbsp;
        <input name="BtnQuery" type="submit" value=" 查询 ">
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
    <td><bean:write name="orderForm" property="pageNavigator" filter="false"/></td>
  </tr>
</table>
<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=center  >订单号</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >姓名</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >购物金额</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单应付</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员应付</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单状态</th>		
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单来源</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单类型</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle  >下单时间</th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >经办人</th>
		<th width="80"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作</th>
	</tr>
	<!-- all orders by search -->
	<% int i=0; %>
    <bean:define id="items" name="list" type="java.util.Collection"/> 
    <logic:iterate name="items" id="order">
	<!-- member for one order -->
	<bean:define id="member" name="order" property="member"/>
	<tr <% if(i%2==1) { %>class="OraTableCellText"<% } %> >		
        
    <td ><a href="orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a></td>
		
    <td ><a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a></td>
		
    <td ><bean:write name="member" property="NAME"/></td>
		
    <td  align=right ><bean:write name="order" property="goodsFee" format="#0.00"/></td>
	<td align=right ><bean:write name="order" property="payable" format="#0.00"/></td>
	<td align=right ><bean:write name="order" property="mbPayable" format="#0.00"/></td>	
    <td >
	<bean:write name="order" property="statusName"/>
	<logic:equal name="order" property="statusId" value="-1">
	<a href=javascript:viewCancelInfo("viewCancelInfo.jsp?orderId=<bean:write name='order' property='orderId' format='#'/>") title="点击查看取消信息">View</a>
	</logic:equal>
	</td>		
		
    <td  ><bean:write name="order" property="prTypeName"/></td>
		
    <td  ><bean:write name="order" property="categoryName"/></td>
		
    <td ><bean:write name="order" property="createDate"/></td>
		
    <td ><bean:write name="order" property="creatorName"/></td>
    <td align="center" >
    	<input name="btn" type="button" value="历史" onclick=doNext("../order/order_query_history.jsp?so_number=<bean:write name='order' property='orderNumber'/>") >
    	<logic:equal name="order" property="statusId" value="-8">
    	<input name="btn" type="button" value="发货单" onclick=queryFahuo('/order/snQry.do','<bean:write name="order" property="orderNumber"/>') >
    	</logic:equal>  
    	<logic:greaterThan name="order" property="statusId" value="25">
    	<logic:lessEqual name="order" property="statusId" value="100">
    	<input name="btn" type="button" value="发货单" onclick=queryFahuo('/order/snQry.do','<bean:write name="order" property="orderNumber"/>') >
    	</logic:lessEqual>
    	</logic:greaterThan>    	
    </td>
	</tr>
	<% i++; %>
	</logic:iterate>
</table>
</html:form>
<form name="Fahuo" method="post" >
	<input name="strQryOrdCode" type="hidden">
	<input name="iscallcenter" type="hidden">
</form>
</body>
</html>
