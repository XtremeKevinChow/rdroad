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

</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/nsInbound?type=modify" method="post">

<table width="750.0" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	<td> 
		<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">������</font><font color="838383"> 
      		-&gt; </font><font color="838383">���޵�����</font><font color="838383">
		<table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<tr background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
			<td height="1" width=100% background="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
			</tr>
		</table>
	</td>
	</tr>
</table>

<table  border=0 cellspacing=1 cellpadding=1  width="600">
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;�ʱ�</td>
		<td width="30%" align="left" >
			<bean:write name="nsInboundForm" property="postcode" />&nbsp;
		</td>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;�ͻ�����</td>
		<td align="left" width="30%">
			<bean:write name="nsInboundForm" property="memberName"  />&nbsp;
		</td>
	</tr>
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��ַ</td>
		<td  align="left" colspan="3">
			<bean:write name="nsInboundForm" property="address" />&nbsp;
		</td>
		
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�˻�ԭ��</td>
		<td  align="left" >
			<bean:write name="nsInboundForm" property="rrName" />&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�ʴ�����</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="postDate" />
		</td>
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;������</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="nsInboundForm" property="postNum"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;����</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="postage"/>
		</td>
	</tr>

	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��������</td>
		<td align="left">
			<bean:write name="nsInboundForm" property="otherSpecial" />&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��������</td>
		<td align="left">
			<font color="red"><bean:write name="nsInboundForm" property="shipNO" /></font>&nbsp;
		</td>
	</tr>
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�Ƿ�ȷ��</td>
		<td align="left">
			<logic:equal name="nsInboundForm" property="logOut" value="N">��</logic:equal>&nbsp;
			<logic:equal name="nsInboundForm" property="logOut" value="Y">��</logic:equal>&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�Ƿ����</td>
		<td align="left">
			<logic:equal name="nsInboundForm" property="logOut" value="N">��</logic:equal>&nbsp;
			<logic:equal name="nsInboundForm" property="logOut" value="Y">��</logic:equal>&nbsp;
		</td>
	</tr>
	<tr height="25">
		
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��ע</td>
		<td align="left" colspan="3">
			<bean:write name="nsInboundForm" property="remark" />&nbsp;
		</td>
	</tr>
	
</table>
<br>
<bean:define id="list" name="nsInboundForm" property="items"/>
<table id="detailTable" style="display:block" width="95%" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width="40%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		

	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<bean:write name="list" property="itemCode"/>
		</td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="useQty"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="disQty"/></td>
		
	</tr>
	</logic:iterate>
</table>
<TABLE align="center">
	<tr height="40">
		<td align="center" colspan=4>
			
			<input type="button" class="button2" value=" ���� " onclick="history.back()">&nbsp;
		</td>
	</tr>
</TABLE>
</html:form>
</body>
</html>
