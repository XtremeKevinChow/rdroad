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
function delete_f2(row) {
	var frm = document.forms[0];
	if (confirm("确实要删除吗？"))
	{
		frm.action="expExchangeActivity.do?type=deleteStep&forward=modify_step&rowId=" + (row-1);
		frm.submit();
	}
	
}
function additem_f2() {
	var frm = document.forms[0];
	frm.action="expExchangeActivity.do?type=addStep&forward=modify_step";
	frm.submit();
}
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

	newTd0.innerHTML = "<input type='quantity' size='4' value=''>&nbsp;分"; 
	newTd1.innerHTML= "有效";
	newTd2.innerHTML= "<input type='button' value='删除' onclick='delete_f(this.parentElement.parentElement.rowIndex);'>";
	

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	
	newTd0.setAttribute("align", "left");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "left");
}
function save_step() {
	window.location.href="视图/Exp_Exchange_Activity_List.html";
}
function prevStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=backShowModifyActivity";
	frm.submit();
}
function nextStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=showModifyStepGift";
	frm.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<input type="hidden" name="activityNo" value="<bean:write name="activity" property="activityNo"/>">
<table width="940" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 市场管理 -&gt; 积分档次修改 </td>
   </tr>
</table>
<TABLE width="500" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>

<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">积分活动号 :</td>
		<td><bean:write name="activity" property="activityNo"/></td>
	</tr>
	<tr>
		<td class="dataInput">活动描述 :</td>
		<td><bean:write name="activity" property="activityDesc"/></td>
	</tr>
	<tr>
		<td class="dataInput">状态：</td>
		<td>
		<logic:equal name="activity" property="status" value="-2">
		弃审
		</logic:equal>
		<logic:equal name="activity" property="status" value="-1">
		删除
		</logic:equal>
		<logic:equal name="activity" property="status" value="0">
		新建
		</logic:equal>
		<logic:equal name="activity" property="status" value="1">
		审核
		</logic:equal>
		<logic:equal name="activity" property="status" value="2">
		关闭
		</logic:equal>
		</td>
	</tr>
</table>
<br>
<TABLE width="500" align="center">
  <tr>
	<td align="center">
	   <!-- <input type="button" name="nextBtn" value="<<上一步" onclick="prevStep();"> -->
	   <input type="button" name="nextBtn" value="下一步>>" onclick="nextStep();">
	   <!-- <input type="button" value=" 保存 " onclick="save_step();"> -->
	</td>
  </tr>
</TABLE>
<TABLE width="500" align="center">
  <tr>
	<td class="tableLabel">积分档次</td>
	<td align="right">
	  <input type="button" value="添加积分档次" onclick="additem_f2();">
	</td>
  </tr>
</TABLE>
<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap>
		<td width="200">开始积分</td>
		<td width="200">状态</td>
		<td width="100">操作</td>
	</tr>
	<bean:define name="activity" id="stepMstList" property="mstList"/>
	<logic:iterate id="stepMst" name="stepMstList">
	<tr>
		<td>
			<input type="hidden" name="stepMstId" value="<bean:write name="stepMst" property="id" format="#0"/>">
			<logic:equal name="stepMst" property="enabled" value="false">
			<input type="hidden" name="beginExp" value="<bean:write name="stepMst" property="beginExp" format="#0"/>"><bean:write name="stepMst" property="beginExp" format="#0"/>&nbsp;分
			</logic:equal>
			<logic:equal name="stepMst" property="enabled" value="true">
			<input name="beginExp" size="4" value="<bean:write name="stepMst" property="beginExp" format="#0"/>">&nbsp;分
			</logic:equal>
			
		</td>
		<td>
			<logic:equal name="stepMst" property="status" value="Y">
			有效
			</logic:equal>
			<logic:equal name="stepMst" property="status" value="N">
			无效
			</logic:equal>
		</td>
		<td>
			<input type="button" value="删除" onclick="delete_f2(this.parentElement.parentElement.rowIndex);">
		</td>
	</tr>
	</logic:iterate>
</table>
<TABLE width="500" align="center">
  <tr>
	<td align="right">
	    <input type="button" value="添加积分档次" onclick="additem_f2();">
		
	</td>
  </tr>
</TABLE>
<TABLE width="500" align="center">
  <tr>
	<td align="center">
	   <!-- <input type="button" name="nextBtn" value="<<上一步" onclick="prevStep();"> -->
	   <input type="button" name="nextBtn" value="下一步>>" onclick="nextStep();">
	   <!-- <input type="button" value=" 保存 " onclick="save_step();"> -->
	</td>
  </tr>
</TABLE>
</html:form>
</body>
</html>
