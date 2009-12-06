<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td align=left> <font color="#838383"><b>当前位置</b> : 销售管理 -&gt; 发货单明细-&gt;礼券抵用</font></td>
  </tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >礼券号</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >抵用金额</th>
	</tr>
    <logic:iterate name="list" id="list" > 
	<tr>		
	<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="giftNumber" /></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="disAmt" format="#0.00"/></td>		
	</tr>
	</logic:iterate>
</table>

</body>
</html>