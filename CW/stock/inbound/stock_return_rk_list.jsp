<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function query_f() {
	var frm = document.forms[0];
	if ( (frm.startDate.value == "" || frm.endDate.value == "") && frm.rkNO.value == "" && frm.rrNO.value == "" &&  frm.purNO.value =="")
	{
		alert("请输入查询条件");
		frm.rkNO.focus();
		return;
	}

	if (frm.startDate.value == "")
	{
		
	} else {
		
		var bdate = frm.startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				frm.startDate.focus();
				return;
		 }

	}

	if (frm.endDate.value == "")
	{
		
	} else {
		var edate = frm.endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				frm.endDate.focus();
				return;
		 }
	}
	frm.offset.value = 0;
	frm.btn_query.disabled = "true";
	frm.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="inboundQuery.do?type=query" method="POST">
<bean:define name="inboundForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">退货入库查询</font><font color="838383"> 
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
		<td>退货原因：</td>
		<td bgcolor="#FFFFFF">
			<html:select property="rrNO" style="width:160">
				<option value="">-- 请选择 --</option>
					<html:optionsCollection property="rrList" value="code" label="name"/> 
			</html:select>
			
		</td>
		
	</tr>
	<tr>
		<td>入库单号：</td>
		<td>
			<html:text property="rkNO" size="10"/>
		</td>
		<td>发货单号：</td>
		<td>
			<html:text property="purNO" size="14"/>
		</td>
		<td></td><td><input type="button" value=" 查询 " name="btn_query" onclick="query_f();"></td>
	</tr>
	
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
	<tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>入库单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>发货单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>入库日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否恶意</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>退货类型</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>退货种类</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>退(换)货原因</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>包裹号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>邮资</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<a href="./inboundQuery.do?type=showReturnRK&rkNO=<bean:write name="list" property="rkNO"/>">
		<bean:write name="list" property="rkNO"/></a></td>
		<td class=OraTableCellText noWrap align=middle >
		<a href="../order/snView.do?sn_id=<bean:write name="list" property="purID" format="#"/>"><bean:write name="list" property="purNO"/></a></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="writeDate"/></td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="isBad" value="Y">是</logic:equal>
		<logic:equal name="list" property="isBad" value="N">否</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="returnType" value="A">内部</logic:equal>
		<logic:equal name="list" property="returnType" value="B">会员</logic:equal>
		<logic:equal name="list" property="returnType" value="C">配送商</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left >
		<logic:equal name="list" property="returnClass" value="A">全退</logic:equal>
		<logic:equal name="list" property="returnClass" value="P">部分退</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="rrName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="postNum"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="postage" format="#0.00"/></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>
