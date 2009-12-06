<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000" >



<html:form  action="/consoleDeposit?iscallcenter=1" method="post" >

<bean:define name="pagerForm" property="pager" id="pager"/>
<html:hidden name="pagerForm" property="offset"/>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000">
	<tr>

		<!-- <td class="OraTableRowHeader" noWrap  noWrap align=middle>会员号码</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>会员姓名</td> -->
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>充值日期</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>摘要</td>
		
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>金额</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>礼金余额</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>经办人员</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>备注</td>
	</tr>

	<bean:define id="a" name="pageModel" />
  <logic:iterate id="memberhistory" name="a" >
	<tr>
		<!-- <td bgcolor="#FFFFFF" align="center">
			
			<bean:write name="memberhistory" property="CARD_ID"/>
		</td>
		<td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="CARD_NAME"/></td> -->
		<td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="MODIFY_DATE" /></td>
		<td bgcolor="#FFFFFF">
			[<bean:write name="memberhistory" property="EVENT_TYPE_NAME" />]<bean:write name="memberhistory" property="payMethodName" />
		</td>
		<td bgcolor="#FFFFFF" align="right" nowrap><bean:write name="memberhistory" property="MONEY_UPDATE" format="#0.00"/></td>
		<td bgcolor="#FFFFFF" align="right"><bean:write name="memberhistory" property="DEPOSIT" format="#0.00"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="OPERATOR_NAME"/></td>
		<td bgcolor="#FFFFFF" ><bean:write name="memberhistory" property="COMMENTS"/></td>
	</tr>
	
</logic:iterate>
	
</table>

</html:form>

</body>
</html>
