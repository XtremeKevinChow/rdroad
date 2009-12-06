<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.form.ProductTypeForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">

function submit_f() {
	var obj = document.forms[0];
	if(obj.catalogCode == null || obj.catalogCode.value.length == 0) {
		alert("分类编码不能为空!");
		obj.catalogCode.focus();
		return;
	}

	if(obj.name == null || obj.name.value.length == 0) {
		alert("名称不能为空!");
		obj.name.focus();
		return;
	}

	if(obj.categoryLevel == null || obj.categoryLevel.value.length == 0) {
		alert("级别不能为空!");

		obj.categoryLevel.focus();
		return;
	}

	if(obj.parentType == null || obj.parentType.value.length == 0) {
		alert("上级类型不能为空!");
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品类别维护</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productType.do?type=add" method="post" target="user">
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>增加产品类别</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp; 分类编码</td>
		<td align="left" width="80%" nowarp>
		<html:text property="catalogCode"/></td>
		
	</tr>

	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;名称</td>
      <td align="left" width="80%" nowarp>
        <html:text property="name"/>
      </td>
			
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;级别</td>
      <td align="left" width="80%" nowarp>
        <html:text property="categoryLevel" readonly="true"/>
      </td>
			
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp; 上级类型</td>
		<td align="left" >
			<html:text property="parentType"  readonly="true"/>
			<html:text property="parentTypeName" readonly="true"/>
			
		</td>
		
	</tr>
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;是否为最小类别</td>
		<td align="left" width="80%" nowarp>
		<html:radio property="isLeaf" value="1" />是
		<html:radio property="isLeaf" value="0" />否
		</td>
		
	</tr>	
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >描述</td>
	  <td align="left" nowarp>
	  <html:textarea cols="30" rows="2" property="description" />
	  </td>
	  
  </tr>
  
</table>





<table width="90%" border="0" cellspacing="1" cellpadding="5" >
	<tr>
	 <td nowrap align="center">
		<input type="button" class="button2" value=" 保存 " name="save"  onclick="submit_f()">
		
		
  </tr>
</table>
</html:form>


</body>
