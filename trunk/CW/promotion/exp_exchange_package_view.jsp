<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
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
function delete_f(row) {

	if (detailTable.rows.length <= 2)
	{
		alert("至少要留一条明细在页面上");
		return;
	}
	detailTable.deleteRow(row);
}
function additem_f() {

	//添加一行
	var newTr = detailTable.insertRow();

	//添加6列
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	var newTd2 = newTr.insertCell();
	var newTd3 = newTr.insertCell();
	var newTd4 = newTr.insertCell();


	//设置列内容和属性 

	newTd0.innerHTML = "<select name='packageType'><option value=''>选择...</option><option value='T'>礼券</option><option value='G'>礼品</option></select>"; 
	newTd1.innerHTML = "<input name='no' size='20'>";  
	
	newTd2.innerHTML= "<input name='quantity' size='2' value='1'>";
	newTd3.innerHTML= "有效";
	newTd4.innerHTML= "<input type='button' value='删除' onclick='delete_f(this.parentElement.parentElement.rowIndex);'>";

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	newTd3.bgColor = "#f7f7e7";
	newTd4.bgColor = "#f7f7e7";

	newTd0.setAttribute("align", "left");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "left");
	newTd3.setAttribute("align", "left");
	newTd4.setAttribute("align", "left");
	
}

function modify_package() {
	var frm = document.forms[0];
	if (frm.packageNo.value == "")
	{
		alert("请输入礼包号!");
		frm.packageNo.focus();
		return;
	}
	if (frm.desc.value == "")
	{
		alert("请输入描述!");
		frm.desc.focus();
		return;
	}
	frm.submit();
}
function show_modify_page() {
	window.location.href="/promotion/expExchangePackage.do?type=showModifyPage&packageNo=<bean:write name="package" property="packageNo" />";
}
function list_f(packageNo) {
	//window.location.href="/promotion/expExchangePackage.do?type=view&packageNo="+packageNo;
	window.location.href="/promotion/expExchangePackage.do?type=query";
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangePackage.do?type=add"  method="POST">

<table width="940" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 市场管理 -&gt; 查看礼包 </td>
   </tr>
</table>
<table align="center" width="400" >
   <tr>
     <td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
   </tr>
</table>
<table align="center" width="400" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">礼包号</td>
		<td><bean:write name="package" property="packageNo" /></td>
	</tr>

	<tr>
		<td class="dataInput">状态</td>
		<td>
		<logic:equal name="package" property="status" value="Y">
		有效
		</logic:equal>
		<logic:equal name="package" property="status" value="N">
		无效
		</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">描述</td>
		<td><bean:write name="package" property="desc"/></td>
	</tr>
	<tr>
		<td class="dataInput">URL</td>
		<td>&nbsp;<bean:write name="package" property="url" /></td>
	</tr>
</table>
<TABLE width="500" align="center">
  <tr>
	<td class="tableLabel">礼包明细</td>
	
  </tr>
</TABLE>
<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap>
		<td width="100">类型</td>
		<td width="200">号码</td>
		<td width="100">个数</td>
		<td width="100">状态</td>
		
	</tr>
	<bean:define name="package" property="dtlList" id="packDtlList"/>
	<logic:iterate name="packDtlList" id="packageDtl">
	<tr>
		
		<td>
			<logic:equal name="packageDtl" property="packageType" value="T">
			礼券
			</logic:equal>
			<logic:equal name="packageDtl" property="packageType" value="G">
			礼品
			</logic:equal>
			
		</td>
		<td>
			<bean:write name="packageDtl" property="no" />
		</td>
		<td>
			<bean:write name="packageDtl" property="quantity" format="#0"/>
		</td>
		<td>
			<logic:equal name="packageDtl" property="status" value="Y">
			有效
			</logic:equal>
			<logic:equal name="packageDtl" property="status" value="N">
			无效
			</logic:equal>
		</td>
		
	</tr>
	</logic:iterate>
	
</table>

<TABLE width="600" align="center">
  <tr>
	<td align="center">
	    <input type="button" value=" 返回 " onclick="list_f();">
		<input type="button" value=" 修改 " onclick="show_modify_page();">
	</td>
  </tr>
</TABLE>
</html:form>
</body>
</html>
