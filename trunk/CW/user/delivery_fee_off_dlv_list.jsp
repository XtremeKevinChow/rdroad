<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    String id = request.getParameter("id");
    if(id==null) id = (String)request.getAttribute("id");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function on_edit(obj, offid, dlvid) {
	document.forms[0].action = "/deliveryFeeOff.do?type=showDlv&offid=" + offid+"&dlvid="+dlvid;
	obj.disabled  = true;
	document.forms[0].submit();
}
function on_add(obj){
	document.forms[0].action = "/deliveryFeeOff.do?type=showDlv&offid="+<%=id%>;
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
              -&gt; </font><font color="838383">�ض���Ʒ�ⷢ�ͷ�</font><font color="838383">
              -&gt; </font><font color="838383">���ͷ�ʽ</font>
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
    <tr>
        <td bgcolor="#FFFFFF" colspan="6">
            <input type="button" value="����" onclick='on_add(this);' />
            &nbsp;
            <input type="button" value="����" onclick='window.location.href="/deliveryFeeOff.do?type=showDfoList";' />
        </td>
    </tr>
	<tr height="26">
		<th width="180px" class="OraTableRowHeader" noWrap >���ͷ�ʽ</th>
		<th width="110px" class="OraTableRowHeader" noWrap >���ʷѷ�ʽ</th>
		<th width="130px" class="OraTableRowHeader" noWrap >�������ʷѽ��</th>
		<th width="50px" class="OraTableRowHeader" noWrap >״̬</th>
		<th width="60px" class="OraTableRowHeader" noWrap >����</th>
	</tr>
<logic:iterate name="list" id="list">
	<tr> 
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="delivery_name"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="offtype_name"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="off_fee"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="status_name"/></td>
		<td bgcolor="#FFFFFF" >
		    <input type="button" value="�༭" onclick='on_edit(this,<bean:write name="list" property="off_id"/>,<bean:write name="list" property="delivery_id"/>);' />
        </td>
	</tr>
</logic:iterate>
</table>
</html:form>
</body>
</html>