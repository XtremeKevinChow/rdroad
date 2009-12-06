<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function detail_f() {
	var str = detailTable.style.display;
	if (str == "none")
	{
		view_ctrl.innerText = "隐藏";
		detailTable.style.display = "block";
	}

	if (str == "block")
	{
		view_ctrl.innerText = "显示明细";
		detailTable.style.display = "none";
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">库存管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">退换货入库单详情</font><font color="838383"> 
      	</td>
   </tr>
</table>

<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>入库单号：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="rkNO"/></td>
	<td>发货单号：</td><td bgcolor="#FFFFFF" ><bean:write name="inboundForm" property="purNO"/></td>
	<td>相应单号：</td><td bgcolor="#FFFFFF" ><bean:write name="inboundForm" property="quaNO"/></td>
  </tr>
  <tr height="26"> 
	<td>收货人：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="consignee"/></td>
	<td>收货日期：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="conDate"/></td>
	<td>质检人：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="checker"/></td>
	
  </tr>
  <tr height="26"> 
	<td>质检日期：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="cheDate"/></td>
	<td>操作人：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="writer"/></td>
	<td>操作日期：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="writeDate"/></td>
  </tr>
  <logic:equal name="inboundForm" property="rkClass" value="R">
  <tr height="26"> 
	<td>是否恶意：</td><td bgcolor="#FFFFFF">
		<logic:equal name="inboundForm" property="isBad" value="Y">是</logic:equal>
		<logic:equal name="inboundForm" property="isBad" value="N">否</logic:equal>
	</td>
	<td>退货类型：</td><td bgcolor="#FFFFFF">
		<logic:equal name="inboundForm" property="returnType" value="A">内部</logic:equal>
		<logic:equal name="inboundForm" property="returnType" value="B">会员</logic:equal>
		<logic:equal name="inboundForm" property="returnType" value="C">配送商</logic:equal>
	</td>
	<td>退货种类：</td><td bgcolor="#FFFFFF" >
		<logic:equal name="inboundForm" property="returnClass" value="A">全退</logic:equal>
		<logic:equal name="inboundForm" property="returnClass" value="P">部分退</logic:equal>
	</td>
  </tr>
  </logic:equal>
  <tr height="26"> 
	<td>退(换)货原因：</td><td bgcolor="#FFFFFF">
		<bean:write name="inboundForm" property="rrName"/>
	</td>
	<td>包裹号：</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="postNum"/></td>
	<td>邮资：</td><td bgcolor="#FFFFFF">
		<bean:write name="inboundForm" property="postage"/>
	</td>
  </tr>
  <tr height="26"> 
	<td>备注：</td><td bgcolor="#FFFFFF" colspan="5">
		<bean:write name="inboundForm" property="bz"/>
	</td>
	
  </tr>
</table>
<br>
<!-- <TABLE width="95%" align="center">
<TR>
	<TD><a href="javascript:detail_f();" id="view_ctrl">显示明细</a></TD>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
</TR>
</TABLE> -->
<bean:define id="list" name="inboundForm" property="items"/>
<table id="detailTable" style="display:block" width="95%" align="center" border=0 cellspacing=1 cellpadding=5>
	<tr>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>数量(好)</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>数量(破)</th>
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
<table align="center">
	<tr>
		<td><input type="button" value=" 返回 " onclick="history.go(-1)"></td>
	</tr>
</table>
</body>
</html>
