<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.form.ProductTypeForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">

function submit_f() {
	var obj = document.forms[0];
	if(obj.catalogCode == null || obj.catalogCode.value.length == 0) {
		alert("������벻��Ϊ��!");
		obj.catalogCode.focus();
		return;
	}

	if(obj.name == null || obj.name.value.length == 0) {
		alert("���Ʋ���Ϊ��!");
		obj.name.focus();
		return;
	}

	if(obj.categoryLevel == null || obj.categoryLevel.value.length == 0) {
		alert("������Ϊ��!");

		obj.categoryLevel.focus();
		return;
	}

	if(obj.parentType == null || obj.parentType.value.length == 0) {
		alert("�ϼ����Ͳ���Ϊ��!");
		obj.parentType.focus();
		return;
	}
	obj.target = "tree";
	obj.save.disabled = true;
	obj.submit();
}

function load_f() {
	document.forms[0].catalogCode.focus();
}
</script>
</head>


<body  text="#000000" leftmargin="0" topmargin="0"  onload="load_f()">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ���ά��</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productType.do?type=add" method="post" target="user">
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>���Ӳ�Ʒ���</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp; �������</td>
		<td align="left" width="80%" nowarp>
		<html:text property="catalogCode"/></td>
		
	</tr>

	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;����</td>
      <td align="left" width="80%" nowarp>
        <html:text property="name"/>
      </td>
			
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;����</td>
      <td align="left" width="80%" nowarp>
        <html:text property="categoryLevel" readonly="true"/>
      </td>
			
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp; �ϼ�����</td>
		<td align="left" >
			<html:text property="parentType"  readonly="true"/>
			<html:text property="parentTypeName" readonly="true"/>
			
		</td>
		
	</tr>
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;�Ƿ�Ϊ��С���</td>
		<td align="left" width="80%" nowarp>
		<html:radio property="isLeaf" value="1" />��
		<html:radio property="isLeaf" value="0" />��
		</td>
		
	</tr>	
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >����</td>
	  <td align="left" nowarp>
	  <html:textarea cols="30" rows="2" property="description" />
	  </td>
	  
  </tr>
  
</table>





<table width="90%" border="0" cellspacing="1" cellpadding="5" >
	<tr>
	 <td nowrap align="center">
		<input type="button" class="button2" value=" ���� " name="save"  onclick="submit_f()">
		
		
  </tr>
</table>
</html:form>


</body>
