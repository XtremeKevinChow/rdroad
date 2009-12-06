<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function check_f() {
	
	if(document.forms[0].parentName.value==""){
		alert('活动不能为空!');
		
		return false;
	}
	if(document.forms[0].exchangePrice.value==""){
		alert('兑换价不能为空!');
		document.forms[0].exchangePrice.focus();
		return false;
	}
	if(document.forms[0].itemCode.value==""){
		alert('货号不能为空!');
		document.forms[0].itemCode.focus();
		return false;
	}
	
	if(document.forms[0].expStart.value==""){
		alert('兑换积分不能为空!');
		document.forms[0].expStart.focus();
		return false;
	}
	if(document.forms[0].startDate.value==""){
		alert('开始日期不能为空!');
		
		return false;
	}
	if(document.forms[0].endDate.value==""){
		alert('结束日期不能为空!');
		return false;
	}

	//开始日期不能大于结束日期
	var begin, end; 
	begin = document.forms[0].startDate.value.replace("-", "").replace("-","");
	end =   document.forms[0].endDate.value.replace("-", "").replace("-",""); 
	if (begin - end > 0)
	{
		alert("开始日期不能大于结束日期");
		return false;
	}
	if(document.forms[0].validDay.value==""){
		alert('有效日期不能为空!');
		document.forms[0].validDay.focus();
		return false;
	}
	/**
	if(document.forms[0].content.value==""){
		alert('礼品描述不能为空!');
		document.forms[0].content.focus();
		return false;
	}
	*/
	
}
function setId(id, name) {

	document.forms[0].parentID.value = id;
	document.forms[0].parentName.value = name;
	document.forms[0].parentName.focus();
}
function getOpenwinValue(ret){
	//ret数组，ret[0]:产品ID	ret[1]:货号
	var form = document.forms[0];
	form.itemCode.value = ret[1];
	form.itemCode.focus();
}
function back_f() {
	document.forms[0].action = "memberExpExchangeSetup.do?type=query&parentID=" + document.forms[0].parentID.value;
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> <span class="OraHeader"><b>会员管理 -&gt; 新增积分兑换礼品设置</b></span>
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width="100%" background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table>

<html:form method="post" action="/memberExpExchangeSetup.do?type=add" onsubmit="return check_f();">

<table border=0 cellspacing=1 width="600" align="center">
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;活动名称</td>
		<td width="" align="left">
			<html:hidden property="parentID" /><!-- 父表ID -->
			<html:text property="parentName" readonly="true" size="24" />&nbsp;<INPUT TYPE="button" value="选择" NAME="select1" onclick="openWin('../member/memberExpExchangeSetup.do?type=select', 'PopWin', 700, 450);">
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;兑换价</td>
		<td width="" align="left">
			<html:text property="exchangePrice" size="10" />
		</td>
	</tr>
	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;礼品号码</td>
		<td width="" align="left">
			<html:hidden property="itemID"/>
			<html:text property="itemCode" size="10" />&nbsp;<INPUT TYPE="button" value="查询" NAME="select2" onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);">
		</td>
		<td width="" align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;兑换积分</td>
		<td width="" align="left">
			<html:text property="expStart" size="10" />
		</td>
	</tr>
	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;开始日期</td>
		<td width="" align="left">
			<html:text property="startDate" size="10" readonly="true"/><a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;
		</td>
		<td width="" align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;结束日期</td>
		<td width="" align="left">
			<html:text property="endDate" size="10" readonly="true"/><a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	
	 <tr>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color="red">*</font>&nbsp;有效天数</td>
		<td width="" align="left">
			<bean:write name="memberExpExchangeForm" property="validDay"/>
			<html:hidden property="validDay" />
			
		</td>
		
	</tr> 

	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;礼品描述</td>
		<td align="left" colspan="3">
			<html:textarea cols="64" rows="8" property="content" />
		</td>
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input name = "submitButton" type="submit" class="button2" value=" 确定 " >&nbsp;
		<input type="button" class="button2" value=" 返回 "  onclick="back_f()">
	</tr>
</table>
</html:form>

</body>
</html>
