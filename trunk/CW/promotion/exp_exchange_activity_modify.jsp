<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<style>
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
/* 标签 */
.tableLabel{ font-size:12px; color:#990000; font-weight: bold;}
/* 输入框单元格 */
.dataInput {background-color:#f0f0f0;text-align:right;}
/* 导航标签 */
.navigationLabel{ font-size:12px; color:#000000; font-weight: bold;}
</style>
<SCRIPT LANGUAGE="JavaScript">
function save_activity() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=modifyActivity";
	frm.submit();
}

function delete_f(table,row) {

	table1 = document.getElementById("dataTable");
	table1.deleteRow(row);
}

function additem_f(detailTable) {

	//添加一行
	var newTr = detailTable.insertRow(5);
	newTr.setAttribute("id","new_tr");

	//添加6列
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	


	//设置列内容和属性 

	newTd0.innerHTML = "<td class='dataInput'>帐户处理方式：</td>"; 
	newTd1.innerHTML = "清零<input type='radio' name='dealType' value='A' >&nbsp;&nbsp;不清零，可多次兑换<input type='radio' name='dealType' value='B' >";  
	
	
	newTd0.className = "dataInput";
	//newTd1.bgColor = "#f7f7e7";
	
	//newTd0.setAttribute("align", "left");
	//newTd1.setAttribute("align", "left");
	
	
}

function change_f(obj) {

    var table = document.getElementById("dataTable");
	var new_tr = document.getElementById("new_tr");//新行id
	if (obj.value == "A") //一次性
	{
	
		if (obj.checked)//选中
		
		{
			if (!new_tr)
			{
				additem_f(dataTable);
			}
			
			
			
		} else {
			if (new_tr)
			{
				delete_f(dataTable,5);
			}
			
			
		}
	}
	if (obj.value == "B")
	{
		if (obj.checked)//选中
		{
			if (table.rows.length >=7)
			{
				delete_f(dataTable,5);
			}
			
		} else {
			
		}
	}

}

function load() {
	var frm = document.forms[0];
	//document.forms[0].exchangeType[0].checked=false;
	change_f(frm.exchangeType[0]);
	if (typeof(frm.dealType)!= "undefined")
	{
		if ("<bean:write name="activityForm" property="dealType"/>" == "A")
		{
			frm.dealType[0].checked = true;
			frm.dealType[1].checked = false;
		} else if ("<bean:write name="activityForm" property="dealType"/>" == "B") {
			frm.dealType[0].checked = false;
			frm.dealType[1].checked = true;
		} else {
			frm.dealType[0].checked = false;
			frm.dealType[1].checked = false;
		}
	}
}
function nextStep() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=showAddStep";
	frm.submit();
}
function back_f() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=view";
	frm.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 促销管理 -&gt; 积分换礼活动修改</font></td>
  </tr>
</table>

<TABLE width="500" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<table id="dataTable" align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
    <tr>
		<td class="dataInput">活动类型：</td>
		<td>
		<logic:equal name="activityForm" property="activityType" value="1">
		积分兑换
		</logic:equal>
		<logic:equal name="activityForm" property="activityType" value="2">
		介绍会员兑换
		</logic:equal></td>
	</tr>
	<html:hidden  property="activityType" />
    <tr>
		<td class="dataInput">请定义积分活动号：</td>
		<td><html:text name="activityForm" property="activityNo" readonly="true"/></td>
	</tr>
	<tr>
		<td class="dataInput">请定义积分活动名称：</td>
		<td><html:text name="activityForm" property="activityDesc"/></td>
	</tr>
	
	<tr>
		<td class="dataInput">请输入兑换起止日期：</td>
			
		<td>
			从&nbsp;<html:text name="activityForm" property="beginDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
			到&nbsp;<html:text name="activityForm" property="endDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	<tr>
		<td class="dataInput">礼品最后保留日期：</td>
		<td><html:text name="activityForm" property="giftLastDate" size="10"/>
		<a href="javascript:calendar(document.forms[0].giftLastDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	<tr>
		<td class="dataInput">兑换方式：</td>
			
		<td>
			一次性兑换<html:radio name="activityForm" property="exchangeType" value="A" />&nbsp;&nbsp;
			实时兑换<html:radio name="activityForm" property="exchangeType" value="B" />
		</td>
	</tr>
	<!--
	<tr>
		<td class="dataInput">网站HTML代码：</td>
			
		<td>
			<html:textarea name="activityForm" property="headHtml" cols="40" rows="5"/>
		</td>
	</tr>-->

</table>
<TABLE width="500" align="center">
<tr align="center">
	<td >
	   <!-- <input type="button" name="nextBtn" value="下一步>>" onclick="nextStep();"> -->
	   <input type="button" name="submitBtn" value=" 返回 " onclick="back_f();">
	   <input type="button" name="submitBtn" value=" 保存 " onclick="save_activity();">
	</td>
</tr>
</TABLE>
</html:form>
</body>
</html>
