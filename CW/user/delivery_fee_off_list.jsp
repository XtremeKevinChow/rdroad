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
function on_edit(obj, updateId) {
	document.forms[0].action = "/deliveryFeeOff.do?type=showDfo&id=" + updateId;
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
              -&gt; </font><font color="838383">�ض���Ʒ�ⷢ�ͷ�����</font>
          </td>
   </tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<html:form  action="/deliveryFeeOff.do?type=modifyDlv" method="post">
<table align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" >
	<tr height="26">
		<th width="50px" class="OraTableRowHeader" noWrap >���</th>
		<th class="OraTableRowHeader" noWrap >����</th>
		<th width="100px" class="OraTableRowHeader" noWrap >��ʼ����</th>
		<th width="100px" class="OraTableRowHeader" noWrap >��������</th>
		<th width="50px" class="OraTableRowHeader" noWrap >״̬</th>
		<th width="150px" class="OraTableRowHeader" noWrap >����</th>
	</tr>
<logic:iterate name="dfoList" id="deliveryFeeOffList">
	<tr> 
		<td bgcolor="#FFFFFF" <logic:equal name="deliveryFeeOffList" property="status" value="-1">style="color:#ccc;"</logic:equal>><bean:write name="deliveryFeeOffList" property="id"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="deliveryFeeOffList" property="status" value="-1">style="color:#ccc;"</logic:equal>><bean:write name="deliveryFeeOffList" property="name"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="deliveryFeeOffList" property="status" value="-1">style="color:#ccc;"</logic:equal>><bean:write name="deliveryFeeOffList" property="begin_date"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="deliveryFeeOffList" property="status" value="-1">style="color:#ccc;"</logic:equal>><bean:write name="deliveryFeeOffList" property="end_date"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="deliveryFeeOffList" property="status" value="-1">style="color:#ccc;"</logic:equal>><bean:write name="deliveryFeeOffList" property="status_name"/></td>
		<td bgcolor="#FFFFFF" ><input type="button" value="�༭" onclick='on_edit(this,<bean:write name="deliveryFeeOffList" property="id"/>);' /></td>
	</tr>
</logic:iterate>
</table>
</html:form>
</body>
</html>
