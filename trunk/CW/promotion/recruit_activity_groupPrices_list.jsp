<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String err=request.getParameter("err");
String pid=request.getParameter("promotionid");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function delete_f(gpId) {
	location.href="groupPrices.do?type=delete&gpId=" + gpId;
}
function modify_f(gpId) {
	location.href="groupPrices.do?type=showModify&gpId=" + gpId;
}
function check_f(gpId) {
	location.href="groupPrices.do?type=check&gpId=" + gpId;
}
function f_checkData() {
	document.forms[0].queryBtn.disabled = true;
}
function showAdd_f() {
	document.forms[0].action = "groupPrices.do?type=showAdd";
	document.forms[0].addBtn.disabled = true;
	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ���׼۸��б�</font></td>
  </tr>
</table>
<html:form action="groupPrices.do?type=query" method="post" onsubmit="return f_checkData();">
<table width="95%" align="center" cellspacing="1" border="0" >
	<tr height=30>
	<td>��ļMSC��</td><td>
	<html:select property="msc"><html:optionsCollection name="groupPricesForm" property="activityList" value="code" label="name"/> </html:select></td>
	<td><input type="submit" name="queryBtn" value=" ��ѯ ">&nbsp;&nbsp;
	<input type="button" name="addBtn" value=" ���� " onclick="showAdd_f()"></td>
	</tr>
</table>
<br>
<table width="95%" align="center" cellspacing="1" border="0" >
	<tr height=30>
		<th width="26%"   class="OraTableRowHeader" >������</th>	
		<th width="10%"  class="OraTableRowHeader">����</th>	
		<th width="10%"  class="OraTableRowHeader">���</th>
		<th width="8%"  class="OraTableRowHeader">�Ƿ�����</th>
		<th width="22%"  class="OraTableRowHeader">��ֹ����</th>
		<th width="6%"  class="OraTableRowHeader">״̬</th>
		<th width="18%"  class="OraTableRowHeader">����</th>
	</tr>
    <logic:iterate id="groupPrices" name="list" > 
	<tr>
		<td class=OraTableCellText ><bean:write name="groupPrices" property="sectionName"/></td>
		
		<td class=OraTableCellText align="right"><bean:write name="groupPrices" property="saleQty" format="#0"/></td>
		<td class=OraTableCellText align="right"><bean:write name="groupPrices" property="saleAmt"/></td>
		<td class=OraTableCellText>
		<logic:equal name="groupPrices" property="isGift" value="1">��</logic:equal>
		<logic:equal name="groupPrices" property="isGift" value="0">��</logic:equal>
		</td>
		<td class=OraTableCellText>
		��:<bean:write name="groupPrices" property="beginDate" />��:<bean:write name="groupPrices" property="endDate" />
		</td>
		<td class=OraTableCellText>
		<logic:equal name="groupPrices" property="status" value="0">�½�</logic:equal>
		<logic:equal name="groupPrices" property="status" value="-1">ɾ��</logic:equal>
		<logic:equal name="groupPrices" property="status" value="1">���</logic:equal>
		</td>
		<td class=OraTableCellText>
		<logic:equal name="groupPrices" property="status" value="0">
		<input type="button" value="ɾ��" onclick="javascript:delete_f(<bean:write name='groupPrices' property='gpId'/>)">
		<input type="button" value="�޸�" onclick="javascript:modify_f(<bean:write name='groupPrices' property='gpId'/>)">
		
		<input type="button" value="���" onclick="javascript:check_f(<bean:write name='groupPrices' property='gpId'/>)">
		</logic:equal>
		</td>
	</tr>    
    </logic:iterate>    
      
</table>
<br>
</html:form>
</body>
</html>
