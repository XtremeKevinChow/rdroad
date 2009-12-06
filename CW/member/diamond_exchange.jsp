<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	String isAdd = (String)request.getAttribute("isAdd");
%>
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
	background-color:#ECECD1;
	font-size:12px; 
	color:#000000; 
	padding-left:20px; 
	padding-top:3px; 
	padding-bottom:3px;
	text-align:center;
	font-weight:bold;
}
/* 表头 */
.tabletitle2{
	background-color:#DD9442;
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

.style1{ font-size:12px; color:red; font-weight: bold;}
</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function exchange() {
	var selectItem;
	var flag = false;
	var len = detailTable.rows.length;
	for (i = 0; i < len; i ++) {
		var row = detailTable.rows(i);
		var obj = row.getElementsByTagName("INPUT")[0];
		if (typeof(obj) != "undefined")
		{
			if (obj.checked)
			{
				selectItem = obj.value;
				flag = true;
				break;
			}
		}
	}
	if (flag == true)
	{
		if (confirm("确实要兑换吗?"))
		{
			document.forms[0].action = "/member/diamondExchange.do?type=exchangeGift";
			document.forms[0].submit();
		}
		
	} else {
		alert("请选择记录!");
	}
}
function query() {
	document.forms[0].action = "/member/diamondExchange.do?type=showExchangePage";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="document.forms[0].cardId.focus();">
<html:form action="/diamondExchange.do"  method="POST" onsubmit="query();">

<!-- 钻信息 -->
<bean:define id="diamond" name="diamond"/>
<bean:define id="member" name="diamond" property="member"/>
<!-- 钻活动信息 -->
<bean:define id="sets" name="sets"/>

<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 会员管理 -&gt; 九久钻兑换 </td>
   </tr>
</table>
<table  align="center" width="90%" border=0 cellspacing=1 cellpadding=5 >
	
	<tr>
	    <td>
			会员号：&nbsp;
			<input name="cardId" value="<bean:write name="member" property="CARD_ID"/>" size="10">
			<input type="submit" value=" 查询 ">
		</td>
		
	</tr>

	<tr>
		<td>
			<bean:write name="member" property="NAME"/>(<bean:write name="member" property="CARD_ID"/>)
			可兑换的钻颗数：<bean:write name="diamond" property="nomalCount" format="#0"/>&nbsp;
			
		</td>
		<td align="right"><input type="button" name="addBtn" value=" 兑换 " onclick="exchange(this)"></td>
	</tr>
	
</table>
<%@ include file="../member/common_diamond_exchange.jsp" %>
<table  align="center" width="90%" border=0 cellspacing=1 cellpadding=5 >
	
	<tr>
		<td align="right">
			<input type="button" name="addBtn" value=" 兑换 " onclick="exchange(this)">
		</td>
		
	</tr>
	
</table>
</html:form>
</body>
</html>
