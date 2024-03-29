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
function modify() {
	var frm = document.forms[0];
	frm.action = "deliveryFeeSetting.do?type=modify";
	frm.modifyBtn.disabled = true;
	frm.submit();
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<html:form action="deliveryFeeSetting.do?type=add" method="POST">
<html:hidden property="id"/>
<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 系统管理 -&gt; 发送费修改</td>
   </tr>
</table>
<TABLE width="500" align="center">
  <tr>
	<td><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></td>
  </tr>
</TABLE>
<br>
<table align="center" width="500" border="1" cellSpacing=0 bordercolorlight="#183648" bordercolordark="#ffffff">
	<tr>
		<td class="dataInput">发送方式：</td>
		<td>
		<logic:equal name="deliveryFee" property="deliveryType" value="1">
			邮寄
			</logic:equal>
			<logic:equal name="deliveryFee" property="deliveryType" value="2">
			会员中心
			</logic:equal>
			<logic:equal name="deliveryFee" property="deliveryType" value="3">
			直送
		</logic:equal>
		</td>
	</tr>
	<tr>
		<td class="dataInput">会员等级：</td>
		<td>
		<logic:equal name="deliveryFee" property="levelId" value="-1">
			所有
			</logic:equal>
			<logic:equal name="deliveryFee" property="levelId" value="1">
			普通
			</logic:equal>
			<logic:equal name="deliveryFee" property="levelId" value="2">
			正式
			</logic:equal>
			<logic:equal name="deliveryFee" property="levelId" value="3">
			VIP
			</logic:equal>
			
		</td>
	</tr>
	<tr>
		<td class="dataInput">邮编：</td>
		<td>
		<bean:write name="deliveryFee" property="postcode"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">发送费：</td>
		<td>
		<html:text property="feeM"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">开始日期：</td>
		<td>
		<html:text property="beginDateM"/><a href="javascript:calendar(document.forms[0].beginDateM)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	<tr>
		<td class="dataInput">结束日期：</td>
		<td>
		<html:text property="endDateM"/><a href="javascript:calendar(document.forms[0].endDateM)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	<tr>
		<td class="dataInput">金额要求：</td>
		<td>
		<html:text property="requireAmtM" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="dataInput">备注：</td>
		<td>
		<html:textarea property="remarkM"/>
		</td>
	</tr>
</table>

<TABLE width="500" align="center">
<tr align="center">
	<td >
		<input type="button" name="modifyBtn" value=" 修改 " onclick="modify();">&nbsp;&nbsp;
		<input type="button" name="backBtn" value=" 返回 " onclick="history.back();">
	</td>
</tr>
</TABLE>
</html:form>
</body>
</html>
