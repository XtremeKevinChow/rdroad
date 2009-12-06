<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.common.Constants"%>
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
	if(theForm.statusId.value != <%= Constants.BLANK_OPTION_VALUE%>) nCondition++;
	if(theForm.prTypeId.value != <%= Constants.BLANK_OPTION_VALUE%>) nCondition++;
	if(theForm.creatorId.value != <%= Constants.BLANK_OPTION_VALUE%>) nCondition++;
	// 订单总金额
	if(!isPositive(theForm.totalMoneyBottom.value) && trim(theForm.totalMoneyBottom.value) != "") {
		alert("请输入正数或者置空！");
		theForm.totalMoneyBottom.focus();
		return;
	}
	if(trim(theForm.totalMoneyBottom.value) != "") nCondition++;
	
	if(!isPositive(theForm.totalMoneyTop.value) && trim(theForm.totalMoneyTop.value) != "") {
		alert("请输入正数或者置空！");
		theForm.totalMoneyTop.focus();
		return;
	}
	if(trim(theForm.totalMoneyTop.value) != "") nCondition++;
	// 订单创建日期
	if(trim(theForm.createDateBottom.value) != "") nCondition++;
	if(trim(theForm.createDateTop.value) != "") nCondition++;
	
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入查询条件！");
	} else {
		submitForm();
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000"  oncontextmenu="return false" onselectstart="return false">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 客户服务 -&gt; 订单高级查询</font></td>
  </tr>
</table>
<html:form action="/orderAQuery.do" onsubmit="return false;">
  
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
  <tr> 
    <td width="7%" nowrap>订单号：</td>
    <td width="46%"><html:text name="orderForm" property="orderNumber" size="30" maxlength="20" onchange="changeQuery();"/></td>
    <td width="7%" nowrap>会员号：</td>
    <td width="46%"><html:text name="orderForm" property="cardId" size="22" maxlength="20" onchange="changeQuery();"/></td>
  </tr>
  <tr> 
    <td width="5%" nowrap>会员姓名：</td>
    <td width="42%"><html:text name="orderForm" property="mbName" size="30" maxlength="20" onchange="changeQuery();"/></td>
    <td width="5%" nowrap>订单状态：</td>
    <td width="42%"><html:select name="orderForm" property="statusId" style="width:130"> <option value="<%= Constants.BLANK_OPTION_VALUE%>"></option> 
      <html:optionsCollection name="orderForm" property="statusList" value="id" label="name"/> 
      </html:select></td>
  </tr>
  <tr> 
    <td width="7%" nowrap>订单总金额：</td>
    <td width="46%"><html:text name="orderForm" property="totalMoneyBottom" size="12" maxlength="9" onchange="changeQuery();"/>至<html:text name="orderForm" property="totalMoneyTop" size="12" maxlength="9" onchange="changeQuery();"/></td>
    <td width="5%" nowrap>订单来源：</td>
    <td width="42%"><html:select name="orderForm" property="prTypeId" style="width:130"> <option value="<%= Constants.BLANK_OPTION_VALUE%>"></option> 
      <html:optionsCollection name="orderForm" property="prTypes" value="id" label="name"/> 
      </html:select></td>
  </tr>
  <tr> 
    <td width="7%" nowrap>创建日期：</td>
    <td width="46%" nowrap><html:text name="orderForm" property="createDateBottom" size="12" maxlength="10" onchange="changeQuery();"/>至<html:text name="orderForm" property="createDateTop" size="12" maxlength="10" onchange="changeQuery();"/><font color="#FF0000"><b>（格式：yyyy-MM-dd）</b></font></td>
    <td width="7%" nowrap>经办人：</td>
    <td width="46%"><html:select name="orderForm" property="creatorId" style="width:130"> <option value="<%= Constants.BLANK_OPTION_VALUE%>"></option> 
      <html:optionsCollection name="orderForm" property="creatorList" value="id" label="name"/> 
      </html:select></td>
  </tr>
  <tr> 
    <td width="7%" nowrap>&nbsp;</td>
    <td width="46%">&nbsp;</td>
    <td colspan="2"> 
      <input name="BtnQuery" type="button" value=" 查询 " onClick="doSearch();">
    </td>
  </tr>
</table>
<br>
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
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单号</th>
		<th width="12%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号码</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员姓名</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单总金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >应付金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单状态</th>		
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单来源类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单类型</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >下单日期</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >经办人员</th>
	</tr>
    <bean:define id="items" name="orderForm" property="items" type="java.util.Collection"/> 
    <logic:iterate name="items" id="order"> 
	<tr>		
        
    <td class=OraTableCellText noWrap align=center ><a href="orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a></td>
		
    <td class=OraTableCellText noWrap align=center ><a href="../member/member_desktop.html"><bean:write name="order" property="cardId"/></a></td>
		
    <td class=OraTableCellText noWrap align=left ><bean:write name="order" property="mbName"/></td>
		
    <td class=OraTableCellText noWrap align=right ><bean:write name="order" property="totalMoney" format="#0.00"/></td>
		
    <td class=OraTableCellText noWrap align=right ><bean:write name="order" property="payable" format="#0.00"/></td>
		
    <td class=OraTableCellText noWrap align=left ><bean:write name="order" property="statusName"/></td>		
		
    <td class=OraTableCellText noWrap align=left ><bean:write name="order" property="prTypeName"/></td>
		
    <td class=OraTableCellText noWrap align=left ><bean:write name="order" property="categoryName"/></td>
		
    <td class=OraTableCellText noWrap align=center ><bean:write name="order" property="createDate"/></td>
		
    <td class=OraTableCellText noWrap align=center ><bean:write name="order" property="creatorName"/></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
