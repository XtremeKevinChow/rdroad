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
<script language="javascript">
function delRole(id){

  if (confirm("ȷ��ɾ����ɫ��")) {
  
  	window.location.href="deleteRole.do?roleID="+id
  
  }
}
</script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">�û�����</font><font color="838383">
              -&gt; </font><font color="838383">��ɫ���</font>
          </td>
   </tr>
</table>
<br>

<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">

  <tr height="24" valign="middle">
			<th width="10%" class="OraTableRowHeader" noWrap   align="center" >&nbsp;��ɫ��</th>
    	<th width="40%" class="OraTableRowHeader" noWrap   align="center" >&nbsp;��ɫ����</th>
    	<th width="15%" class="OraTableRowHeader" noWrap  class="TableText" align="center" >&nbsp;����</th>
  </tr>


  <logic:iterate id="role" name="<%=Constants.ALL_ROLES%>" >
  
  <html:form  action="/initRoleEdit.do" method="post">
  <tr>
    <td align="left" >&nbsp;
      <bean:write name="role" property="roleName" filter="true"/>
    </td>
    <td align="left">&nbsp;
      <bean:write name="role" property="description" filter="true"/>
    </td>
    
    <td align="center">&nbsp;
      <html:hidden name="role" property="roleID" />
      <html:submit  value="�༭" />
      <input type="button"  value="ɾ��" onclick="javasript:delRole(<bean:write name="role" property="roleID" />)">
    </td>
  </tr>
  </html:form>
</logic:iterate>

</table>


</body>

</html>
