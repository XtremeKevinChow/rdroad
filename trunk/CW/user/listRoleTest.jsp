<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
%>
<html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>

</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>�û�����</b></font><font color="838383"> : </font><font color="838383">��ɫ���</font>
          </td>
   </tr>
</table>
<br>
<table width="95%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px #000000" background="images/backline.gif"  class="en">
	<tr height="20" valign="bottom"><td >&nbsp;��ɫ�б�</td></tr>
</table>
<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">

  <tr height="22" valign="middle">
			<th width="10%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;��ɫ��</th>

  </tr>


  <logic:iterate id="roletest" name="<%=Constants.ALL_ROLES%>" >
  
  <html:form  action="/initRoleEdit.do" method="post">
  <tr>
    <td align="center" >&nbsp;
      <bean:write name="roletest" property="RoleID" />
    </td>

    

  </tr>
  </html:form>
</logic:iterate>

</table>


</body>

</html>
