<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function confirm_f() {
	
	if (document.forms[0].shipNO.value == "")
	{
		alert("�������Ų���Ϊ��");
		document.forms[0].shipNO.focus();
		return;
	}
	
	document.forms[0].submit();
}

function detail_f() {
	var str = detailTable.style.display;
	if (str == "none")
	{
		view_ctrl.innerText = "����";
		detailTable.style.display = "block";
	}

	if (str == "block")
	{
		view_ctrl.innerText = "��ʾ��ϸ";
		detailTable.style.display = "none";
	}
}
function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].endDate.value);
}
function f_checkBeginDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].beginDate.value);
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> 
		<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">���޵�ȷ��</font><font color="838383">
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width=100% background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table>


<html:form action="/nsInbound?type=confirm" method="post">
<html:hidden property="rkNO"/>
<table  border=0 cellspacing=1 cellpadding=1  width="700" >
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;��ⵥ��</td>
		<td width="30%" align="left">
			<bean:write name="nsInboundForm" property="rkNO"/>&nbsp;
		</td>

		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;�ʱ�</td>
		<td width="30%" align="left" >
			<bean:write name="nsInboundForm" property="postcode"/>&nbsp;
		</td>
	</tr>
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��ַ</td>
		<td  align="left" colspan="3">
			<bean:write name="nsInboundForm" property="address"/>&nbsp;
			
		</td>
		
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�˻�ԭ��</td>
		<td  align="left" >
			<bean:write name="nsInboundForm" property="returnReason"/>&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�ʴ�����</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="postDate"/>&nbsp;
		</td>
	</tr>
	
	<tr height="25">
		
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��������</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="otherSpecial"/>&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��������</td>
		<td align="left" >
			<html:text name="nsInboundForm" property="shipNO"/>&nbsp;
		</td>
	</tr>
	<tr height="25">
		
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�ͻ�����</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="memberName"/>&nbsp;
		</td>
		
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<logic:equal name="nsInboundForm" property="logOut" value="N">
		<input type="button" class="button2" value=" ȷ�� " onclick="confirm_f()">&nbsp;
		</logic:equal>
		
		<input type="button" class="button2" value=" ���� " onclick="history.go(-1)">
	</tr>
</table>
<TABLE width="95%" align="center">
<TR>
	<TD><a href="javascript:detail_f();" id="view_ctrl">��ʾ��ϸ</a></TD>
	<td></td>
	<td></td>
	<td></td>
</TR>
</TABLE>
<bean:define id="list" name="nsInboundForm" property="items"/>

<table id="detailTable" style="display:none" width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width="40%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="itemCode"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="useQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="disQty" format="#0"/></td>
	</tr>
	</logic:iterate>
	
</table>
</html:form>


</body>
</html>
