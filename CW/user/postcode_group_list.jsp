<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>
<script language="javascript">
function delete_f(id){

  if (confirm("ȷ��ɾ�����ʱ��飿")){
  	window.location.href="deletePostcodeGroup.do?id="+id;
  }
}
function show_add_f() {
	document.forms[0].action="initPostcodeGroupAdd.do";
	document.forms[0].submit();
}
function show_update_f(id) {
	document.forms[0].action="initPostcodeGroupEdit.do?id="+id;
	document.forms[0].submit();
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
              -&gt; </font><font color="838383">�ʱ����ѯ</font>
          </td>
   </tr>
</table>
<br>

<input type="button" name="addBtn" value="����" onclick="show_add_f()">

<html:form  action="/initPostcodeGroupEdit.do" method="post">
<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
  
  <tr height="24" valign="middle">
	<th width="" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;���к�</th>
    	<th width="" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;�ʱ���</th>
    	
    	<th width="20%" class="OraTableRowHeader" noWrap  class="TableText" align="center" >&nbsp;����</th>
  </tr>


  <logic:iterate id="postcodeGroup" name="groupList" >
  
  
  <tr align="center">
    <td align="left" >&nbsp;
      <bean:write name="postcodeGroup" property="id" filter="true" format="#0"/>
    </td>  
    <td align="left">&nbsp;
      <bean:write name="postcodeGroup" property="groupName" filter="true"/>
    </td>
    
    
    <td align="center" width="100">&nbsp;
      
      <input type="button"  value="�༭" onclick="javasript:show_update_f(<bean:write name="postcodeGroup" property="id" format="#0"/>)">
      <input type="button"  value="ɾ��" onclick="javasript:delete_f(<bean:write name="postcodeGroup" property="id" format="#0"/>)">
    </td>
  </tr>
 
</logic:iterate>

</table>
 </html:form>

</body>

</html>
