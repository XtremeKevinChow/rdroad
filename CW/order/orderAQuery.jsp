<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.common.Constants"%>
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
	if(theForm.statusId.value != <%= Constants.BLANK_OPTION_VALUE%>) nCondition++;
	if(theForm.prTypeId.value != <%= Constants.BLANK_OPTION_VALUE%>) nCondition++;
	if(theForm.creatorId.value != <%= Constants.BLANK_OPTION_VALUE%>) nCondition++;
	// �����ܽ��
	if(!isPositive(theForm.totalMoneyBottom.value) && trim(theForm.totalMoneyBottom.value) != "") {
		alert("���������������ÿգ�");
		theForm.totalMoneyBottom.focus();
		return;
	}
	if(trim(theForm.totalMoneyBottom.value) != "") nCondition++;
	
	if(!isPositive(theForm.totalMoneyTop.value) && trim(theForm.totalMoneyTop.value) != "") {
		alert("���������������ÿգ�");
		theForm.totalMoneyTop.focus();
		return;
	}
	if(trim(theForm.totalMoneyTop.value) != "") nCondition++;
	// ������������
	if(trim(theForm.createDateBottom.value) != "") nCondition++;
	if(trim(theForm.createDateTop.value) != "") nCondition++;
	
	if(nCondition == 0) {
		// û�в�ѯ����
		alert("�������ѯ������");
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
    <td> <font color="#838383"><b>��ǰλ��</b> : �ͻ����� -&gt; �����߼���ѯ</font></td>
  </tr>
</table>
<html:form action="/orderAQuery.do" onsubmit="return false;">
  
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
  <tr> 
    <td width="7%" nowrap>�����ţ�</td>
    <td width="46%"><html:text name="orderForm" property="orderNumber" size="30" maxlength="20" onchange="changeQuery();"/></td>
    <td width="7%" nowrap>��Ա�ţ�</td>
    <td width="46%"><html:text name="orderForm" property="cardId" size="22" maxlength="20" onchange="changeQuery();"/></td>
  </tr>
  <tr> 
    <td width="5%" nowrap>��Ա������</td>
    <td width="42%"><html:text name="orderForm" property="mbName" size="30" maxlength="20" onchange="changeQuery();"/></td>
    <td width="5%" nowrap>����״̬��</td>
    <td width="42%"><html:select name="orderForm" property="statusId" style="width:130"> <option value="<%= Constants.BLANK_OPTION_VALUE%>"></option> 
      <html:optionsCollection name="orderForm" property="statusList" value="id" label="name"/> 
      </html:select></td>
  </tr>
  <tr> 
    <td width="7%" nowrap>�����ܽ�</td>
    <td width="46%"><html:text name="orderForm" property="totalMoneyBottom" size="12" maxlength="9" onchange="changeQuery();"/>��<html:text name="orderForm" property="totalMoneyTop" size="12" maxlength="9" onchange="changeQuery();"/></td>
    <td width="5%" nowrap>������Դ��</td>
    <td width="42%"><html:select name="orderForm" property="prTypeId" style="width:130"> <option value="<%= Constants.BLANK_OPTION_VALUE%>"></option> 
      <html:optionsCollection name="orderForm" property="prTypes" value="id" label="name"/> 
      </html:select></td>
  </tr>
  <tr> 
    <td width="7%" nowrap>�������ڣ�</td>
    <td width="46%" nowrap><html:text name="orderForm" property="createDateBottom" size="12" maxlength="10" onchange="changeQuery();"/>��<html:text name="orderForm" property="createDateTop" size="12" maxlength="10" onchange="changeQuery();"/><font color="#FF0000"><b>����ʽ��yyyy-MM-dd��</b></font></td>
    <td width="7%" nowrap>�����ˣ�</td>
    <td width="46%"><html:select name="orderForm" property="creatorId" style="width:130"> <option value="<%= Constants.BLANK_OPTION_VALUE%>"></option> 
      <html:optionsCollection name="orderForm" property="creatorList" value="id" label="name"/> 
      </html:select></td>
  </tr>
  <tr> 
    <td width="7%" nowrap>&nbsp;</td>
    <td width="46%">&nbsp;</td>
    <td colspan="2"> 
      <input name="BtnQuery" type="button" value=" ��ѯ " onClick="doSearch();">
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
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="12%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�����ܽ��</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >Ӧ�����</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����״̬</th>		
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������Դ����</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�µ�����</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������Ա</th>
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
