<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function update(obj, updateId) {
	document.forms[0].action = "/defaultDeliveryFee.do?type=updateDefault&updateId=" + updateId;
	obj.disabled  = true;
	document.forms[0].submit();
}
function sync(obj) {
	document.forms[0].action = "/defaultDeliveryFee.do?type=syncDefault";
	obj.disabled  = true;
	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">��������</font><font color="838383">
              -&gt; </font><font color="838383">���ͷ�����</font>
          </td>
   </tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<html:form  action="/defaultDeliveryFee.do?type=updateDefault" method="post">
<table width="460" align="center" border=0 >
	<!--<tr>
	<td><input type=button value=" ͬ������վ " name="syncBtn" onclick="sync(this);"></td>
	<TD ><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></TD>
	</tr>-->
</table>
<table width="460" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" >
	<tr height="26">
		<th width="50" class="OraTableRowHeader" noWrap >���</th>
		<th width="100" class="OraTableRowHeader" noWrap >���ͷ�ʽ</th>
		<th width="100" class="OraTableRowHeader" noWrap >��Ա�ȼ�</th>
		<th width="100" class="OraTableRowHeader" noWrap >���ͷ�</th>
		<th width="50" class="OraTableRowHeader" noWrap >����</th>
	</tr>
<logic:iterate name="feeList" id="defaultFee"> 
<bean:define name="defaultFee" property="deliveryType" id="deliveryType"/>
<bean:define name="defaultFee" property="memberLevel" id="memberLevel"/>
	<tr>
		<td bgcolor="#FFFFFF" ><bean:write name="defaultFee" property="id"/><html:hidden name="defaultFee" property="id"/></td>
		<td bgcolor="#FFFFFF" ><bean:write name="deliveryType" property="name"/></td>
		<td bgcolor="#FFFFFF" ><bean:write name="memberLevel" property="name"/></td>
		<td bgcolor="#FFFFFF" ><input name="deliveryFee" size="8" value="<bean:write name="defaultFee" property="deliveryFee"/>"></td>
		<td bgcolor="#FFFFFF" ><input type="button" value="����" onclick="update(this,<bean:write name="defaultFee" property="id"/>);"></td>
	</tr>
</logic:iterate>
</table>
</html:form>
</body>
</html>
