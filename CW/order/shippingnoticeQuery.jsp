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
	if(trim(theForm.strQrySnCode.value) != "") nCondition++;
	if(trim(theForm.strQryShippingNumber.value) != "") nCondition++;
	if(trim(theForm.strQryOrdCode.value) != "") nCondition++;
	if(trim(theForm.strQryMbrCode.value) != "") nCondition++;
	if(trim(theForm.strQryMbrName.value) != "") nCondition++;
	if(trim(theForm.strQryTelephone.value) != "") nCondition++;
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入查询条件！");
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
    <td align=left> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 发货单查询</font></td>
  </tr>
</table>
<html:form action="/snQry.do" onsubmit="return doSearch();">
  <table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
    	<td>
    	发货单号：
        <html:text name="snForm" property="strQrySnCode" size="14"/>
        运单号
        <html:text name="snForm" property="strQryShippingNumber" size="14"/>
        订单号：
        <html:text name="snForm" property="strQryOrdCode" size="14"/>
        会员号：
        <html:text name="snForm" property="strQryMbrCode" size="8"/>
        会员姓名：
        <html:text name="snForm" property="strQryMbrName" size="8"/>
        会员电话：
        <html:text name="snForm" property="strQryTelephone" size="10"/>
        <input name="BtnQuery" type="submit" value=" 查询 ">
        <input name="btnAQry" type=button value="高级查询" onclick="javascript:f_snAQry();">
      </td>		
	</tr>
</table>
<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >发货单号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号码</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >联系人</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >邮编</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >送货方式</th>
		<!--<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >批号</th>-->		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >配货时间</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >完成时间</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >核货人</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >状态</th>
		<!--<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >退货差异</th>-->
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
    	<input name = "btn" type ="button" value="查看" onclick=viewCY("/order/order_return.jsp?queryBy=shippingnotice&barcode=<bean:write name='mst' property='sn_code'/>") >
		</logic:equal>	
		<logic:equal name="mst" property="status" value="-5">
    	<input name = "btn" type ="button" value="查看" onclick=viewCY("/order/order_return.jsp?queryBy=shippingnotice&barcode=<bean:write name='mst' property='sn_code'/>") >
		</logic:equal>	
	</td>-->		
		
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
