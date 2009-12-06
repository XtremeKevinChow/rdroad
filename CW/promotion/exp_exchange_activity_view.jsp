<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
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
function view_f() {
	window.open("Exp_Exchange_Package_View.html");
}
function modify_activity() {
	window.location.href="expExchangeActivity.do?type=showModifyActivity&activityNo=<bean:write name="activity" property="activityNo"/>";
}
function modify_step() {
	window.location.href="expExchangeActivity.do?type=showModifyStep&activityNo=<bean:write name="activity" property="activityNo"/>";
}
function back_f() {
	var frm = document.forms[0];
	frm.action = "expExchangeActivity.do?type=queryActivity";
	frm.submit();
}
function view_package(no) {
	window.open('expExchangePackage.do?type=view&packageNo='+no)
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="expExchangeActivity.do?"  method="POST">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 促销管理 -&gt; 积分换礼查看</font></td>
  </tr>
</table>
<TABLE width="600" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<table width="600" border="0" cellspacing="0" cellpadding="0" align="center" >
    <tr>
      	
    	<td align="right">
		<logic:equal name="activity" property="status" value="0">
		<input type="button" value="修改活动" onclick="javascript:modify_activity();">
		</logic:equal>
		<logic:equal name="activity" property="status" value="-2">
		<input type="button" value="修改活动" onclick="javascript:modify_activity();">
		</logic:equal>
		</td>
   </tr>
</table>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" >
    <tr >
		<td class="dataInput">活动类型：</td>
		<td>&nbsp;
		<logic:equal name="activity" property="activityType" value="1">
		积分兑换
		</logic:equal>
		<logic:equal name="activity" property="activityType" value="2">
		介绍会员兑换
		</logic:equal></td>
	</tr>
    <tr>
		<td class="dataInput">请定义积分活动号：</td>
		<td>&nbsp;<bean:write name="activity" property="activityNo"/></td>
	</tr>
	<tr>
		<td class="dataInput">请定义积分活动名称：</td>
		<td>&nbsp;<bean:write name="activity" property="activityDesc"/></td>
	</tr>
	
	<tr>
		<td class="dataInput">请输入兑换起止日期：</td>
			
		<td>
			&nbsp;从&nbsp;<bean:write name="activity" property="beginDate"/>
			到&nbsp;<bean:write name="activity" property="endDate"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">兑换方式：</td>
			
		<td>
			<logic:equal name="activity" property="exchangeType" value="A">
			&nbsp;一次性兑换
			</logic:equal>
			<logic:equal name="activity" property="exchangeType" value="B">
			&nbsp;实时兑换
			</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">处理方式：</td>
			
		<td>
			<logic:equal name="activity" property="dealType" value="A">
			&nbsp;清零
			</logic:equal>
			<logic:equal name="activity" property="dealType" value="B">
			&nbsp;保留剩余积分
			</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">礼品最后保留日期：</td>
			
		<td>
			&nbsp;<bean:write name="activity" property="giftLastDate"/>
		</td>
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

<TABLE width="600" align="center">
  <tr>
	<td class="tableLabel">积分档次明细</td>
	<td align="right">
	<logic:equal name="activity" property="status" value="0">
	<input type="button" value="修改档次" onclick="javascript:modify_step();">
	</logic:equal>
	<logic:equal name="activity" property="status" value="-2">
	<input type="button" value="修改活动" onclick="javascript:modify_step();">
	</logic:equal>
	</td>
  </tr>
</TABLE>
<table align="center" width="600" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<bean:define name="activity" id="stepMstList" property="mstList"/>
	<logic:iterate name="stepMstList" id="stepMst"><!-- 积分档次 -->
	<bean:define name="stepMst" property="dtlList" id="stepDtlList"/>
	<tr class="OraTableRowHeader" noWrap>
		<td align="left">积分满<bean:write name="stepMst" property="beginExp" />分可选择以下赠品</td>
	</tr>
	
	<logic:iterate name="stepDtlList" id="stepDtl"><!-- 档次明细 -->
	<tr>
		<td>
		<logic:equal name="stepDtl" property="stepType" value="P">
		礼包
		</logic:equal>
		<logic:equal name="stepDtl" property="stepType" value="G">
		礼品
		</logic:equal>
		<logic:equal name="stepDtl" property="stepType" value="T">
		礼券
		</logic:equal>&nbsp;
		<logic:equal name="stepDtl" property="stepType" value="P">
		<a href="javascript:view_package('<bean:write name="stepDtl" property="no"/>');">
		</logic:equal>
		<bean:write name="stepDtl" property="no"/>
		<logic:equal name="stepDtl" property="stepType" value="P">
		</a>
		</logic:equal>

		<logic:equal name="stepDtl" property="stepType" value="G">
		（加<bean:write name="stepDtl" property="addMoney" format="#0"/>元得礼品	
		订单满<bean:write name="stepDtl" property="orderRequire" format="#0.00"/>可使用）
		</logic:equal>
		</td>
	</tr>
	</logic:iterate>
	</logic:iterate>
</table>
<TABLE width="600" align="center">
  <tr>
	<td align="center"><input type="button" value=" 返回 " onclick="back_f();"></td>
  </tr>
</table>
</html:form>
</body>
</html>
