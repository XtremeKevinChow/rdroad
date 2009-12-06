<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function detail_f(id) {
	location.href = "./nsInbound.do?type=initModify&rkNO=" + id;
}

function query_f() {
	var frm = document.forms[0];
	if (  (frm.startDate.value == "" || frm.endDate.value == "") && frm.rkNO.value == "" && frm.memberName.value == ""  )
	{
		alert("查询条件不能为空");
		frm.rkNO.focus();
		return;
	}

	if (document.forms[0].startDate.value == "")
	{
		
	} else {
		
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }

	}

	if (document.forms[0].endDate.value == "")
	{
		
	} else {
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return;
		 }
	}

	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/nsInbound.do?type=queryModifyRecords" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">查询未确认的三无入库单(修改)</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>开始日期：</td>
		<td>
			<html:text property="startDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>结束日期：</td>
		<td>
			<html:text property="endDate" size="10"/>
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	</tr>
	<tr>
		<td>入库单号：</td>
		<td>
			<html:text property="rkNO" size="10"/>
		</td>
		<td>客户姓名：</td>
		<td>
			<html:text property="memberName" size="10"/>&nbsp;
			<input type="button" value=" 查询 " name="btn_query" onclick="query_f();">
		</td>
	</tr>
	
</table>
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>入库单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>邮编</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>地址</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>姓名</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>退货原因</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>邮戳日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>其他特征</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否确认</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否入库</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<a href="javascript:detail_f('<bean:write name="list" property="rkNO"/>');">
		<bean:write name="list" property="rkNO"/>
		</a>
		</td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postcode"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="address"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="memberName"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="returnReason"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="otherSpecial"/></td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="logOut" value="N">否</logic:equal>
			<logic:equal name="list" property="logOut" value="Y">是</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="isRk" value="N">否</logic:equal>
			<logic:equal name="list" property="isRk" value="Y">是</logic:equal>
		</td>

	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
