<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

if(document.memberForm.NAME.value==""
&&document.memberForm.CARD_ID.value==""
&&document.memberForm.TELEPHONE.value==""
){
alert('��ѯ��������Ϊ��!');
return false;
}

document.memberForm.submit();
}
function initFocus(){
	document.memberForm.NAME.focus();
	return true;
}

function selectItem(){

	var selectedid = getSelectedItem();
	
	if(selectedid !="") {
	    opener.getOpenwinMemberValue(selectedid);
	    self.close();
	}
	
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��Ա����</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա��ѯ</font>
      	</td>
   </tr>
</table>
<html:form  action="/queryList.do" method="post">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>��Ա�ţ�<html:text property="CARD_ID" size="8"/>&nbsp;��Ա������<html:text property="NAME" size="8"/>&nbsp;�绰��<html:text property="TELEPHONE" size="10"/>
		&nbsp;<input type="submit" value="��ѯ"  onclick="return querySubmit()"></td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
    <td><bean:write name="memberForm" property="pageNavigator" filter="false"/></td>
  </tr>
</table>
</html:form>

<table width="95%" align="center" onclick="changeItem()" ondblclick="selectItem()" border=0 cellspacing=1 cellpadding=2 >
    <tr class="oraTableRowHeader" noWrap>
        <td noWarp width="10%">��Ա��</td>
        <td noWarp width="10%">��Ա����</td>
        <td noWarp width="10%">��Ա�ȼ�</td>
        <td noWarp width="15%">��ϵ�绰</td>
        <td noWarp width="15%">�ʼ���ַ</td>
        <td noWarp width="10%">�ʱ�</td>
        <td >��ַ</td>
    </tr>
<% int i=0; %>
<bean:define id="items" name="memberForm" property="items" type="java.util.Collection"/> 
<logic:iterate name="items" id="member"> 
	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %> 
	style="cursor:hand" id ="<bean:write name="member" property="CARD_ID"/>"
	>
		<td>
		<bean:write name="member" property="CARD_ID"/>
		</td>
		<td><bean:write name="member" property="NAME"/></td>
		<td>
		<logic:equal name="member" property="LEVEL_ID" value="1">��ͨ��Ա</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="2">��ʽ��Ա</logic:equal>	
		<logic:equal name="member" property="LEVEL_ID" value="3">VIP��Ա</logic:equal>
		</td>
		<td ><bean:write name="member" property="TELEPHONE"/></td>
	    <td ><bean:write name="member" property="EMAIL"/></td>
		<td ><bean:write name="member" property="postcode"/></td>
		<td ><bean:write name="member" property="addressDetail"/></td>
	</tr>
	<% i++; %>
</logic:iterate>
</table>
  </td></tr>
</table>
</body>
</html>
