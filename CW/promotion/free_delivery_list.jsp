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
function changeStatus(free_id, status) {
    document.forms[0].ID.value= free_id;
    document.forms[0].status.value = status;
    document.forms[0].action = "freeDeliveryFee.do?type=updateStatus";
    //alert(1);
    document.forms[0].submit();
}
function view(free_id) {
    document.forms[0].ID.value = free_id;
    document.forms[0].action = "freeDeliveryFee.do?type=view";
    document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ⷢ�ͷ�����</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="freeDeliveryFee.do?type=view" method="post">
<table  border=0  width="800" align="center" id="DataTable">
   
    <tr class="OraTableRowHeader" noWrap align="center">
	<th width="60">id</th>
	<th width="100">���</th>
	<th width="100">��ʼ����</th>
	<th width="100">��ֹ����</th>
	<th width="60">״̬</th>	
	<th width="100">����</th>	
	</tr>
    <% int i=0;%>
<bean:define id="free_list" name="list" type="java.util.Collection"/> 
<logic:iterate name="free_list" id="free"> 
	<tr 
	<% if (i%2==1) {%> class=OraTableCellText <% }%>
	>
	    <td   noWrap align="center"><a href="freeDeliveryFee.do?type=view&ID=<bean:write name="free" property="ID"/>">
	    <bean:write name="free" property="ID"/></td>
		<td   noWrap align="center"><bean:write name="free" property="order_require"/></td>
		<td   noWrap align="center"><bean:write name="free" property="begin_date"/></td>
		<td   noWrap align="center"><bean:write name="free" property="end_date"/></td>
		<td   noWrap align="center">
		<logic:equal name="free" property="status" value="0"> ��Ч </logic:equal>
		<logic:equal name="free" property="status" value="-1"> <font color=red> ��Ч </font></logic:equal>
		</td>
		<td noWrap align="center">
		<logic:equal name="free" property="status" value="-1">
		    <input type=button value="��Ч" onclick="changeStatus(<bean:write name="free" property="ID"/>,0);">
		</logic:equal>   
		<logic:equal name="free" property="status" value="0">
		    <input type=button value="��Ч" onclick="changeStatus(<bean:write name="free" property="ID"/>,-1);">
		</logic:equal>  
		</td>	
	</tr>
	<% i++; %>
	
    </logic:iterate>
    <tr>
   <td colspan="7" align="center">
   <input type=submit value=" ���� ">
   </td>
   </tr>
</table>
<html:hidden property="ID"/>
<html:hidden property="status"/>
</html:form>

</body>
</html>
