<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
    if(trim(document.forms[0].postcode.value)=="") {
        alert("��ѡ���ʱ�");
        return false;
    }
    return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ʱ������</font>
      	</td>
   </tr>
</table>

<html:form  action="/s_area.do?type=queryPostCode" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>
		�ʱ�ǰ4λ:&nbsp;&nbsp;<html:text property="postcode" maxlength="4"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value=" �� ѯ " >
		</td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="95%"  cellspacing="1" border="0"  noWrap >
	<tr class="oraTableRowHeader" noWrap>
		<td width="20%">ʡ��</td>
		<td width="20%">����</td>
		<td width="20%">����</td>
		<td width="20%">�ʱ�ǰ4λ</td>
		<td width="20%">�Ƿ�ֱ��</td>
	</tr>
	<% int i=0; %>
  <logic:iterate id="area" name="list" >
	<tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> >
		<td ><bean:write name="area" property="provinceName" /></td>
		<td ><bean:write name="area" property="cityName" /></td>		
		<td ><bean:write name="area" property="areaname" /></td>
		<td ><bean:write name="area" property="postcode" /></td>
		<td >
		<logic:equal name="area" property="is_express" value="1">
			ֱ��
		</logic:equal>
		<logic:notEqual name="area" property="is_express" value="1">
			����ֱ��
		</logic:notEqual>
		</td>		
    </tr>
 <% i++; %>
</logic:iterate>
</table>
  </td></tr>
</table>
<TABLE align="center">
</TABLE>
</body>
</html>
