<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
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
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/snAQuery.do?type=list">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
    <td><bean:write name="snForm" property="pageNavigator" filter="false"/></td>
  </tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >发货单号</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单号</th>
		<th width="12%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号码</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >联系人</th>
		<th width="7%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >邮编</th>
		<th width="7%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >送货方式</th>
		<th width="7%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >批号</th>		
		<th width="7%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >创建时间</th>
		<th width="7%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >打印时间</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >状态</th>
	</tr>
    
    <logic:iterate name="sn_list" id="mst" > 
	<bean:define id="order" name="mst" property="order"/>
	<bean:define id="member" name="order" property="member"/>
	<bean:define id="deliveryInfo" name="order" property="deliveryInfo"/>
	<tr>		
    <td class=OraTableCellText noWrap align=center ><a href="snView.do?sn_id=<bean:write name="mst" property="sn_id" format="#"/>"><bean:write name="mst" property="sn_code"/></a></td>    
    <td class=OraTableCellText noWrap align=center ><a href="orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a></td>
	<td class=OraTableCellText noWrap align=center ><a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a></td>
			
    <td class=OraTableCellText noWrap align=center ><bean:write name="deliveryInfo" property="receiptor"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="deliveryInfo" property="postCode"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="deliveryInfo" property="deliveryType"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="lot"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="create_date" /></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="print_date" /></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="status_name"/></td>		
	
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>