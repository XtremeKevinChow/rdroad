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
<!--
function sync_activity() {
	if (confirm("数据同步：系统将把审核过的积分活动同步到网站数据库"))
	{
		var frm = document.forms[0];
		frm.action = "expExchangeActivity.do?type=syncActivity";
		frm.submit();
	}
}
function view_f(activityNo) {
	window.location.href="/promotion/expExchangeActivity.do?type=view&activityNo="+activityNo;
}
function show_add_activity() {
	window.location.href="/promotion/expExchangeActivity.do?type=showAddActivity";
}
function delete_f(activityNo) {
	if (confirm("确实要删除？"))
	{
		location.href="/promotion/expExchangeActivity.do?type=deleteActivity&activityNo="+activityNo;
	}
}
function check_f(activityNo) {
	if (confirm("确实要审核？"))
	{
		location.href="/promotion/expExchangeActivity.do?type=checkActivity&activityNo="+activityNo;
	}
}
function recheck_f(activityNo) {
	if (confirm("确实要重新审核？"))
	{
		location.href="/promotion/expExchangeActivity.do?type=recheckActivity&activityNo="+activityNo;
	}
}
function uncheck_f(activityNo) {
	if (confirm("确实要弃审？"))
	{
		location.href="/promotion/expExchangeActivity.do?type=uncheckActivity&activityNo="+activityNo;
	}
}
function close_f(activityNo) {
	if (confirm("确实要关闭？"))
	{
		location.href="/promotion/expExchangeActivity.do?type=closeActivity&activityNo="+activityNo;
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 促销管理 -&gt; 积分换礼活动</font></td>
  </tr>
</table>

<TABLE width="800" align="center">
  <tr>
	<td align="left">
	   <input type="button" value=" 新增 " onclick="show_add_activity();">&nbsp;&nbsp;&nbsp;&nbsp;
	   <!--<input type="button" value="同步数据" onclick="sync_activity();">-->
	</td>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>

<table align="center"  width="800" id="detailTable">
	
	<tr class="OraTableRowHeader" noWrap >
		<td width="80">活动号</td>
		<td width="180">活动描述</td>
		<td width="100">兑换方式</td>
		<td width="100">处理方式</td>
		<td width="180">起止日期</td>
		<td width="60">状态</td>
		<td width="100">操作</td>
	</tr>
	<logic:iterate name="list" id="list">
	<tr class=OraTableCellText noWrap align=center >
		<td>
			<a href="javascript:view_f('<bean:write name="list" property="activityNo"/>');"><bean:write name="list" property="activityNo"/></a>
		</td>

		<td>
			&nbsp;<bean:write name="list" property="activityDesc"/>
		</td>

		<td>
			<logic:equal name="list" property="exchangeType" value="A">
			&nbsp;一次性兑换
			</logic:equal>
			<logic:equal name="list" property="exchangeType" value="B">
			&nbsp;实时兑换兑换
			</logic:equal>
		</td>

		<td>
			&nbsp;<logic:equal name="list" property="dealType" value="A">
			清零
			</logic:equal>
			<logic:equal name="list" property="dealType" value="B">
			保留剩余积分
			</logic:equal>
			<logic:equal name="list" property="dealType" value="">
			无
			</logic:equal>
		</td>

		<td>
			&nbsp;从<bean:write name="list" property="beginDate" />到<bean:write name="list" property="endDate"/>
		</td>

		<td>
		<logic:equal name="list" property="status" value="-2">
		弃审
		</logic:equal>
		<logic:equal name="list" property="status" value="-1">
		删除
		</logic:equal>
		<logic:equal name="list" property="status" value="0">
		新建
		</logic:equal>
		<logic:equal name="list" property="status" value="1">
		审核
		</logic:equal>
		<logic:equal name="list" property="status" value="2">
		关闭
		</logic:equal>
		</td>

		<td>
		&nbsp;<logic:equal name="list" property="status" value="0">
		<input type="button" value="审核" onclick="check_f('<bean:write name="list" property="activityNo"/>');">
		<input type="button" value="删除" onclick="delete_f('<bean:write name="list" property="activityNo"/>');">
		</logic:equal>
		<logic:equal name="list" property="status" value="1">
		<input type="button" value="弃审" onclick="uncheck_f('<bean:write name="list" property="activityNo"/>'
		);">
		</logic:equal>
		<logic:equal name="list" property="status" value="-2">
		<input type="button" value="重审核" onclick="recheck_f('<bean:write name="list" property="activityNo"/>');">
		</logic:equal>
		<logic:equal name="list" property="status" value="1">
		<input type="button" value="关闭" onclick="close_f('<bean:write name="list" property="activityNo"/>');">
		</logic:equal>
		</td>
	</tr>
	</logic:iterate>
</table>

</html:form>
</body>
</html>
