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
function delete_f(detailTable,row) {

	if (detailTable.rows.length <= 2)
	{
		alert("至少要留一条明细在页面上");
		return;
	}
	detailTable.deleteRow(row);
}
function additem_f(detailTable) {

	//添加一行
	var newTr = detailTable.insertRow();

	//添加6列
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	var newTd2 = newTr.insertCell();
	var newTd3 = newTr.insertCell();
	var newTd4 = newTr.insertCell();
	var newTd5 = newTr.insertCell();



	//设置列内容和属性 

	newTd0.innerHTML = "<select><option value=''>选择...</option>"
	<!--<option value='P'>礼包</option><option value='G'>礼品</option>-->
	+ "<option value='T'>礼券</option</select>"; 
	newTd1.innerHTML = "<input type='no' size='20'>";  
	
	newTd2.innerHTML= "满<input type='orderRequire' size='4' value='0' disabled>&nbsp;加<input type='quantity' size='2' value='0'>&nbsp;元";
	newTd4.innerHTML= "有效";
	newTd3.innerHTML= "从<input type='beginDate' size='10' >&nbsp;到<input type='endDate' size='10' >";
	newTd5.innerHTML= "<input type='button' value='删除'onclick='delete_f(detailTable,this.parentElement.parentElement.rowIndex);'>";

	newTd0.bgColor = "#f7f7e7";
	newTd1.bgColor = "#f7f7e7";
	newTd2.bgColor = "#f7f7e7";
	newTd3.bgColor = "#f7f7e7";
	newTd4.bgColor = "#f7f7e7";
	newTd5.bgColor = "#f7f7e7";

	newTd0.setAttribute("align", "left");
	newTd1.setAttribute("align", "left");
	newTd2.setAttribute("align", "left");
	newTd3.setAttribute("align", "left");
	newTd4.setAttribute("align", "left");
	newTd5.setAttribute("align", "left");
	
}

function save_step_gift() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=addFinish";
	frm.submit();
}
function prevStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=backShowAddStep";
	frm.submit();
}
function additem_f2(beginExp) {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=addStepGift&forward=add_step_gift&queryExp=" + beginExp;
	frm.submit();
}
function delete_f2(beginExp, rowId) {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=deleteStepGift&forward=add_step_gift&queryExp=" + beginExp+"&rowId=" + (rowId-1);
	frm.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 市场管理 -&gt; 积分档次设置 </td>
   </tr>
</table>
<TABLE width="800" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<table align="center" width="800" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">积分活动号：</td>
		<td><bean:write name="activity" property="activityNo"/></td>
	</tr>
	
	<tr>
		<td class="dataInput">活动描述</td>
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
<TABLE width="800" align="center">
  <tr>
	<td align="center">
	  <input type="button" name="nextBtn" value="<<上一步" onclick="prevStep();">
	  <input type="button" value=" 保存 " onclick="save_step_gift();">
	</td>
  </tr>
</TABLE>

<bean:define name="activity" property="mstList" id="stepMstList"/>
<logic:iterate name="stepMstList" id="stepMst"><!-- 积分档次 -->
<TABLE width="800" align="center">
  <tr>
	<td class="tableLabel">设置<font color="red"><bean:write name="stepMst" property="beginExp" format="#0"/>分</font>档次的赠送明细</td>
	<td align="right">
	    <input type="button" value="添加赠送明细" onclick="additem_f2('<bean:write name="stepMst" property="beginExp" format="#0"/>');">
	</td>
  </tr>
</TABLE>
<table align="center" width="800" border="1" cellSpacing=0  cellpadding="3" id="detailTable4">
	<tr class="OraTableRowHeader" noWrap>
		<td width="100">赠送类型</td>
		<td width="150">号码</td>
		<!--<td width="160">使用条件</td>-->
		<!--<td width="300">有效日期</td>-->
		<td width="70">状态</td>
		<td width="70">操作</td>
	</tr>
	<bean:define name="stepMst" property="dtlList" id="stepDtlList"/>
	<logic:iterate name="stepDtlList" id="stepDtl"><!-- 档次明细 -->
	<tr>
	<input type="hidden" name="stepDtlId" value="<bean:write name="stepDtl" property="id" format="#0"/>">
	<input type="hidden" name="beginExp" value="<bean:write name="stepMst" property="beginExp" format="#0"/>">
		<td>
			<select name="stepType">
				<option value="">选择...</option>
				<option value="G" <logic:equal name="stepDtl" property="stepType" value="G">selected</logic:equal>>礼品</option>
				<option value="T" selected >礼券</option>
			</select>
		</td>
		<td>
			<input name="no" size="20" value="<bean:write name="stepDtl" property="no"/>">
		</td>
        <input type=hidden name="addMoney" value="0">
        <input type=hidden name="stepBeginDate" value="<bean:write name="stepDtl" property="beginDate" />">
		<input type=hidden name="stepEndDate" value="<bean:write name="stepDtl" property="endDate" />">
		<td>
		<logic:equal name="stepDtl" property="status" value="Y">
			有效
			</logic:equal>
			<logic:equal name="stepDtl" property="status" value="N">
			无效
		</logic:equal>
		</td>
		<td>
			<input type="button" value="删除" onclick="delete_f2('<bean:write name="stepMst" property="beginExp" format="#0"/>',this.parentElement.parentElement.rowIndex);">
		</td>
	</tr>
	</logic:iterate><!-- 档次明细 -->
</table>
</logic:iterate><!-- 积分档次 -->

<TABLE width="800" align="center">
  <tr>
	<td align="center">
	  <input type="button" name="nextBtn" value="<<上一步" onclick="prevStep();">
	  <input type="button" value=" 保存 " onclick="save_step_gift();">
	</td>
  </tr>
</TABLE>
</html:form>
</body>
</html>
