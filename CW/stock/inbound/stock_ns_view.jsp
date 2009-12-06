<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
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
		<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">三无单详情</font><font color="838383">
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
		<td align="right"  class="OraTableRowHeader" noWrap  width="20%">&nbsp;邮编</td>
		<td width="30%" align="left" >
			<bean:write name="nsInboundForm" property="postcode" />&nbsp;
		</td>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;客户姓名</td>
		<td align="left" width="30%">
			<bean:write name="nsInboundForm" property="memberName"  />&nbsp;
		</td>
	</tr>
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;地址</td>
		<td  align="left" colspan="3">
			<bean:write name="nsInboundForm" property="address" />&nbsp;
		</td>
		
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;退货原因</td>
		<td  align="left" >
			<bean:write name="nsInboundForm" property="rrName" />&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;邮戳日期</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="postDate" />
		</td>
	</tr>
	
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;包裹号</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="nsInboundForm" property="postNum"/>
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;邮资</td>
		<td align="left" >
			<bean:write name="nsInboundForm" property="postage"/>
		</td>
	</tr>

	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;其他特征</td>
		<td align="left">
			<bean:write name="nsInboundForm" property="otherSpecial" />&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;发货单号</td>
		<td align="left">
			<font color="red"><bean:write name="nsInboundForm" property="shipNO" /></font>&nbsp;
		</td>
	</tr>
	<tr height="25">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;是否确认</td>
		<td align="left">
			<logic:equal name="nsInboundForm" property="logOut" value="N">否</logic:equal>&nbsp;
			<logic:equal name="nsInboundForm" property="logOut" value="Y">是</logic:equal>&nbsp;
		</td>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;是否入库</td>
		<td align="left">
			<logic:equal name="nsInboundForm" property="logOut" value="N">否</logic:equal>&nbsp;
			<logic:equal name="nsInboundForm" property="logOut" value="Y">是</logic:equal>&nbsp;
		</td>
	</tr>
	<tr height="25">
		
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;备注</td>
		<td align="left" colspan="3">
			<bean:write name="nsInboundForm" property="remark" />&nbsp;
		</td>
	</tr>
	
</table>
<br>
<bean:define id="list" name="nsInboundForm" property="items"/>
<table id="detailTable" style="display:block" width="95%" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width="40%"  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>可用数量</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle>破损数量</th>
		

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
			
			<input type="button" class="button2" value=" 返回 " onclick="history.back()">&nbsp;
		</td>
	</tr>
</TABLE>
</html:form>
</body>
</html>
