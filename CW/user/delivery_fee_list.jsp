<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<style>
TABLE{COLOR: #000000; FONT-SIZE: 12px; LINE-HEIGHT: 12pt}
BODY{COLOR: #000000; FONT-SIZE: 12px; LINE-HEIGHT: 14pt}
SELECT
{
	
	FONT-SIZE: 12px
}

input 
{
	font-size: 12px;
}
/* 表头 */
.tabletitle{
	background-color:#cc3300;
	font-size:12px; 
	color:#FFFFFF; 
	padding-left:20px; 
	padding-top:3px; 
	padding-bottom:3px;
	text-align:center;
	font-weight:bold;
}
/* 表头 */
.tabletitle2{
	background-color:#cc3300;
	font-size:12px; 
	color:#FFFFFF; 
	padding-left:20px; 
	padding-top:3px; 
	padding-bottom:3px;
	text-align:center;
	font-weight:bold;
}
/* 标签 */
.tableLabel{ font-size:12px; color:#990000; font-weight: bold;}
/* 输入框单元格 */
.dataInput {background-color:#f0f0f0;text-align:right;}
/* 导航标签 */
.navigationLabel{ font-size:12px; color:#000000; font-weight: bold;}

</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function query() {
	var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=query";
	frm.offset.value = 0;
	frm.queryBtn.disabled = true;
	frm.submit();
}
function view(id) {
	window.location.href="deliveryFeeSetting.do?type=view&searchId="+id;
}
function modify(obj,id) {
	obj.disabled = true;
	window.location.href="deliveryFeeSetting.do?type=showModify&searchId="+id;
}
function f_delete(obj,id) {
    var frm = document.forms[0];
	obj.disabled = true;
	frm.action="deliveryFeeSetting.do?type=delete&searchId="+id;
	frm.submit();
}
function changeProvince() {
	var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=selectProvince";
	
	frm.submit();
}
function initAdd() {
    var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=showAdd";
	
	frm.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="deliveryFeeSetting.do?type=query"  method="POST">
<bean:define name="deliveryFeeForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<input type="hidden" name="forward" value="list">
<table width="900" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 系统管理 -&gt; 发送费配置列表 </td>
   </tr>
</table>

<TABLE width="740" align="center">
  <tr>
	<td align="left">
	   <td class="dataInput">省：</td>
		<td>
		<html:select property="searchProvince" >
		<html:optionsCollection name="deliveryFeeForm" property="provinceList" value="code" label="name"/> 
		</html:select>
		</td>
		
		<td align="center"><input type="button" name="queryBtn" value=" 查 询 " onclick="query();">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="btnAdd" value=" 新 增 " onclick="initAdd();"></td>
		
	</td>
  </tr>
</TABLE>
<table width="740" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table align="center" width="740" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" align="center" noWrap>
		<td width="100">发送方式</td>
		<td width="100">会员等级</td>
		<td width="100">发送费</td>
		<td width="100" >操作</td>
	</tr>
	<logic:present name="list">
	<logic:iterate name="list" id="list">
	<tr>
		<td>
			<bean:write name="list" property="deliveryTypeName"/>
		</td>
		<td>
		    <bean:write name="list" property="levelName"/>
		</td>
		<td>
			&nbsp;<bean:write name="list" property="fees" format="#0.00" />
		</td>
		<td align="center">
		    <!--
			<input type=button value="修改" onclick="modify(this, <bean:write name="list" property="id" />);">
			-->
			<input type=button value="删除" onclick="f_delete(this, <bean:write name="list" property="id" />);">
		</td>
		
	</tr>
	</logic:iterate>
	</logic:present>
</table>

</html:form>
</body>
</html>
