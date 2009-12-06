<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function init_f() {
	document.forms[0].customerNO.focus();
}

function submit_f() {
	
	if(document.forms[0].customerNO.value == "") {
		alert("客户代码不能为空");
		document.forms[0].customerNO.focus();
		return;
	}	
	if(document.forms[0].customerName.value == "") {
		alert("客户名称不能为空");
		document.forms[0].customerName.focus();
		return;
	}	
	if(document.forms[0].typeID.value == "0" || document.forms[0].typeID.value == "") {
		alert("类型不能为空");
		document.forms[0].typeID.focus();
		return;
	}	
	document.forms[0].submit();
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init_f()">
<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> 
		<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">修改客户</font><font color="838383"> 
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width=100% background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table>

<html:form action="/customer.do?type=modify" method="post">
<!-- Primary Key -->

<table border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;客户代码</td>
		<td width="*%" align="left">
			<html:text property="customerNO" readonly="true" />&nbsp;
		</td>

		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;客户名称</td>
		<td width="*%" align="left">
			<html:text property="customerName" />&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;类型</td>
		<td  align="left" >
			<html:select property="typeID">
				<option value="">-- 请选择 --</option>
				<html:optionsCollection name="customerForm" property="customerTypeList" value="typeID" label="typeDesc"/> 
			</html:select>
		</td>
		
	</tr>
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;备注</td>
		<td  align="left" colspan="3">
			<html:textarea property="remark" cols="66" rows="5"></html:textarea>
			
		</td>
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input type="button" class="button2" value=" 确定 " onclick="submit_f()">&nbsp;
		<input type="button" class="button2" value=" 返回 " onclick="history.go(-1)">
	</tr>
</table>
</html:form>

</body>
</html>
