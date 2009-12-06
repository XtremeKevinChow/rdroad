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
function add_f() {
	location.href = "./nsInbound.do?type=initAdd";
}

function inbound_f(shipNO, rkNO) {
	if (shipNO == null || shipNO == "" || rkNO == null || rkNO == "")
	{
		alert("发货单号和三无单号都不能为空，请检查");
		return;

	}
	location.href = "Inbound.do?type=initReturnRk&purNO=" + shipNO + "&quaNO=" + rkNO;
}

function change_f(shipNO, rkNO) {
	if (shipNO == null || shipNO == "" || rkNO == null || rkNO == "")
	{
		alert("发货单号和三无单号都不能为空，请检查");
		return;

	}
	location.href = "changeInbound.do?type=initRk&purNO=" + shipNO + "&quaNO=" + rkNO;
}

function confirm_f(id) {
	location.href = "./nsInbound.do?type=initConfirm&rkNO=" + id;
}
function modify_f(id) {
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
	frm.offset.value = 0;
	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/nsInboundQuery.do?type=query" method="POST">
<bean:define name="nsInboundForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">查询未确认的三无入库单</font><font color="838383"> 
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
			
		</td>
	</tr>
	<tr>
		<td>是否确认：</td>
		<td>
			<html:select property="logOut" style="width:73">
				<html:option value="">请选择</html:option>
				<html:option value="N">否</html:option>
				<html:option value="Y">是</html:option>
			</html:select>&nbsp;
		</td>
		<td>是否入库：</td>
		<td>
			<html:select property="isRk" style="width:73">
				<html:option value="">请选择</html:option>
				<html:option value="N">否</html:option>
				<html:option value="Y">是</html:option>
			</html:select>
		</td>
	</tr>
	<tr>
		<td>产品名称：</td>
		<td colspan="3">
			<html:text property="itemName" size="30"/>&nbsp;
			&nbsp;
			<input type="button" value=" 查询 " name="btn_query" onclick="query_f();">
			<input type="button" value=" 新增 " name="btn_query" onclick="add_f();">
		</td>
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
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>入库单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>发货单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>邮编</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>地址</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>姓名</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>邮戳日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>包裹号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>邮资</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否确认</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>是否入库</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>操作</th>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<a href="./nsInboundQuery.do?type=initView&rkNO=<bean:write name="list" property="rkNO"/>">
		<bean:write name="list" property="rkNO"/></a>
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="shipNO"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postcode"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="address"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="memberName"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postNum"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="postage" format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="logOut" value="N">否</logic:equal>
			<logic:equal name="list" property="logOut" value="Y">是</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=middle >
			<logic:equal name="list" property="isRk" value="N">否</logic:equal>
			<logic:equal name="list" property="isRk" value="Y">是</logic:equal>
		</td>
		<td class=OraTableCellText noWrap align=left>
			<logic:equal name="list" property="logOut" value="N">
			<input type="button" value="修改" onclick="modify_f('<bean:write name="list" property="rkNO"/>')">
			<input type="button" value="确认" onclick="confirm_f('<bean:write name="list" property="rkNO"/>')">
			</logic:equal>
			<logic:equal name="list" property="logOut" value="Y">
			<logic:equal name="list" property="isRk" value="N">
			<input type="button" value="入库" onclick="inbound_f('<bean:write name="list" property="shipNO"/>','<bean:write name="list" property="rkNO"/>')">
			<input type="button" value="换货" onclick="change_f('<bean:write name="list" property="shipNO"/>','<bean:write name="list" property="rkNO"/>')">
			</logic:equal>
			</logic:equal>
		</td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>