<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
String isorg=request.getParameter("isorg");
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
return false;;
}

document.memberForm.submit();
}

function doSelect(rtValue) {	
	self.close();
	self.opener.setCardId(rtValue);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��:</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա��ѯ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/popQuery.do" method="post"> 
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
  <tr> 
    <td>��Ա�ţ�<html:text property="CARD_ID" size="8"/>&nbsp;��Ա������<html:text property="NAME" size="8"/>&nbsp;
        ���õ绰��<html:text property="TELEPHONE" size="12"/>&nbsp;
      <input type="button" value="��ѯ"  onclick="querySubmit()">
    </td>
  </tr>
</table>
<input type="hidden" name="isquery" value="1">
<input type="hidden" name="isorg" value="<%=isorg%>">
</html:form> 
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
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <bean:define id="items" name="memberForm" property="items" type="java.util.Collection"/> 
  <logic:iterate name="items" id="member"> 
  <tr height="26"> 
    <td rowspan="2" bgcolor="#FFFFFF" width="33" valign="top"> 
      <input type="radio" name="CARD_ID" value="<bean:write name="member" property="CARD_ID"/>" onclick="doSelect(this.value);"> </td>
    <td width="75" height="26" nowrap>��Ա�ţ�</td>
    <td width="100" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="CARD_ID"/></td>
    <td width="39" height="26" nowrap>������</td>
    <td width="117" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="NAME"/></td>
    <td width="74" nowrap>��Ա�ȼ���</td>
    <td  width="248" bgcolor="#FFFFFF"> 
      <logic:equal name="member" property="OLD_LEVEL" value="1">��ͨ��Ա</logic:equal>	
      <logic:equal name="member" property="OLD_LEVEL" value="2">��ʽ��Ա</logic:equal>	
      <logic:equal name="member" property="OLD_LEVEL" value="3">VIP��Ա</logic:equal>	
    </td>
  </tr>
  <tr height="26"> 
    <td width="75" height="26" nowrap>��ϵ�绰��</td>
    <td width="100" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="FAMILY_PHONE"/></td>
    <td width="39" nowrap>��ַ��</td>
    <td  bgcolor="#FFFFFF" colspan="3"><bean:write name="member" property="addressDetail"/></td>
  </tr>
  <tr height="1" bgcolor="#000000"> 
    <td colspan="7" height="1"></td>
  </tr>
  </logic:iterate> 
</table>

</body>
</html>