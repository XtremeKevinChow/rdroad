<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String group_desc=request.getParameter("group_desc");
String group_id=request.getParameter("group_id");
String min_item_count=request.getParameter("min_item_count");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.queryItemCode.value==""){
	alert('���ű�����д!');
	document.form.queryItemCode.select();
	return false;
	}		
	document.form.input.disabled = true;

}
function getOpenwinValue(ret){
	document.forms[0].queryItemCode.value = ret;
	if(document.forms[0].queryItemCode.value.length >6) {
		document.forms[0].queryItemCode.value =document.forms[0].queryItemCode.value.substring(7,13);
		document.forms[0].queryItemId.value =document.forms[0].queryItemCode.value.substring(0,6);
	}
}
function initFocus(){
	document.form.item_id_key.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ȯ��Ʒ������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_item_add_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="200">����</td>
		<td bgcolor="#FFFFFF"><%=group_desc%></td>
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="200">���ٲ�Ʒ��</td>
		<td bgcolor="#FFFFFF"><%=min_item_count%></td>
	
	</tr>	
	<tr>
		<td bgcolor="#FFFFFF"  width="200"><font color="red">*</font>����</td>
		<td bgcolor="#FFFFFF">
		      <input type="text" name="queryItemCode" size="16" >
      <input type="hidden" name="queryItemId" size="16" >
      
      <input name="query" type="button" value="��ѯ" onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);">
      	
		
		
		</td>
	</tr>	
	<tr>
		<td bgcolor="#FFFFFF"  width="200">�Ƿ��ѡ</td>
		<td bgcolor="#FFFFFF">
		<input type="radio" name="is_must" value="0" >��
		<input type="radio" name="is_must" value="1" checked>��
		</td>
	</tr>	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="2">

		<input type="submit" name="input" value="��  ��">					
		</td>			
	</tr>	
</table>
<input type=hidden value="<%=group_id%>" name="group_id">
<input type=hidden value="<%=group_desc%>" name="group_desc">
<input type=hidden value="<%=min_item_count%>" name="min_item_count">
</form>

</body>
</html>
