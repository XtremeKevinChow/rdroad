<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
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
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�˻�����ⵥ����</font><font color="838383"> 
      	</td>
   </tr>
</table>

<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>��ⵥ�ţ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="rkNO"/></td>
	<td>�������ţ�</td><td bgcolor="#FFFFFF" ><bean:write name="inboundForm" property="purNO"/></td>
	<td>��Ӧ���ţ�</td><td bgcolor="#FFFFFF" ><bean:write name="inboundForm" property="quaNO"/></td>
  </tr>
  <tr height="26"> 
	<td>�ջ��ˣ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="consignee"/></td>
	<td>�ջ����ڣ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="conDate"/></td>
	<td>�ʼ��ˣ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="checker"/></td>
	
  </tr>
  <tr height="26"> 
	<td>�ʼ����ڣ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="cheDate"/></td>
	<td>�����ˣ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="writer"/></td>
	<td>�������ڣ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="writeDate"/></td>
  </tr>
  <logic:equal name="inboundForm" property="rkClass" value="R">
  <tr height="26"> 
	<td>�Ƿ���⣺</td><td bgcolor="#FFFFFF">
		<logic:equal name="inboundForm" property="isBad" value="Y">��</logic:equal>
		<logic:equal name="inboundForm" property="isBad" value="N">��</logic:equal>
	</td>
	<td>�˻����ͣ�</td><td bgcolor="#FFFFFF">
		<logic:equal name="inboundForm" property="returnType" value="A">�ڲ�</logic:equal>
		<logic:equal name="inboundForm" property="returnType" value="B">��Ա</logic:equal>
		<logic:equal name="inboundForm" property="returnType" value="C">������</logic:equal>
	</td>
	<td>�˻����ࣺ</td><td bgcolor="#FFFFFF" >
		<logic:equal name="inboundForm" property="returnClass" value="A">ȫ��</logic:equal>
		<logic:equal name="inboundForm" property="returnClass" value="P">������</logic:equal>
	</td>
  </tr>
  </logic:equal>
  <tr height="26"> 
	<td>��(��)��ԭ��</td><td bgcolor="#FFFFFF">
		<bean:write name="inboundForm" property="rrName"/>
	</td>
	<td>�����ţ�</td><td bgcolor="#FFFFFF"><bean:write name="inboundForm" property="postNum"/></td>
	<td>���ʣ�</td><td bgcolor="#FFFFFF">
		<bean:write name="inboundForm" property="postage"/>
	</td>
  </tr>
  <tr height="26"> 
	<td>��ע��</td><td bgcolor="#FFFFFF" colspan="5">
		<bean:write name="inboundForm" property="bz"/>
	</td>
	
  </tr>
</table>
<br>
<!-- <TABLE width="95%" align="center">
<TR>
	<TD><a href="javascript:detail_f();" id="view_ctrl">��ʾ��ϸ</a></TD>
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
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>����(��)</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>����(��)</th>
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
		<td><input type="button" value=" ���� " onclick="history.go(-1)"></td>
	</tr>
</table>
</body>
</html>
