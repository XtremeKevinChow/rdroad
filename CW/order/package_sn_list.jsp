<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function open(value1, value2) {
	
	var obj = document.getElementById(value1);
	var obj2 = document.getElementById(value2)
	
	if (obj != null)
	{
		if (obj.style.display == "none")
		{
			obj.style.display = "block";
			obj2.innerHTML = "<a href=javascript:open('"+value1+"','"+value2+"')>�ر�</a>";
		} else {
			obj.style.display = "none";
			obj2.innerHTML = "<a href=javascript:open('"+value1+"','"+value2+"')>��</a>";
		}
	}
	
}
function query(type) {
	
	var frm = document.forms[0];
	/*
	if (frm.sn_code == null || frm.sn_code.value.length == 0)
	{
		alert("�����뷢������!");
		return;
	}
	if (type == 1)
	{
		frm.action = "packSnQuery.do?";
	} else {
		frm.action = "snView.do?sn_id="+frm.sn_code.value+"&queryKey=findBySNNum";
	}
	*/
	frm.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/packSnQuery.do?">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">���۹���</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ϲ���������ѯ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<br>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
	  ���ţ�<html:text property="lot"/>
      �ϲ��������ţ�<html:text property="sn_code"/>
	  �����ţ�<html:text property="order_number"/>
	  <input type="button" value=" ��ѯ " onclick="query('1');">
	  
    </td>
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
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ϲ���������</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա��</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ջ���</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�ʱ�</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��ַ</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�绰</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >״̬</th>
		<th width="20%"  bgcolor="" noWrap align=middle  >����</th>
	</tr>
    
    <logic:iterate name="list" id="mst" > 
	<tr>		
    <td class=OraTableCellText noWrap align=center ><a href="snView.do?sn_id=<bean:write name="mst" property="sn_id" format="#"/>"><bean:write name="mst" property="sn_code"/></a></td>    
    <td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="mb_code"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="mb_name"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="contactor"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="postcode"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="address"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="telephone"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="lot"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="mst" property="status_name"/></td>
	<td id=<bean:write name="mst" property="sn_code"/>_label class=OraTableCellText noWrap align=center ><a href="javascript:open('<bean:write name="mst" property="sn_code"/>', '<bean:write name="mst" property="sn_code"/>_label');">��</a></td>
	</tr>
	<tr>

	<td id=<bean:write name="mst" property="sn_code"/> colspan="9" style="display:none">
		<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
		<tr>
			<th width="20%"  bgcolor="" noWrap align=middle  >��������</th>
			<th width="20%"  bgcolor="" noWrap align=middle  >������</th>
			<th width="20%"  bgcolor="" noWrap align=middle  >״̬</th>
			
			
		</tr>
		<bean:define name="mst" id="childrenList" property="childrenList"/>
		<logic:iterate name="childrenList" id="childMst" > 
		<tr>		
		<td class= noWrap align=center ><a href="snView.do?sn_id=<bean:write name="childMst" property="sn_id" format="#"/>"><bean:write name="childMst" property="sn_code"/></a></td>    
		<td class= noWrap align=center ><a href="orderView.do?orderId=<bean:write name="childMst" property="ref_order" format="#"/>"><bean:write name="childMst" property="order_number"/></a></td>
		<td class= noWrap align=center ><bean:write name="childMst" property="status_name"/></td>
		</tr>
		</logic:iterate>
		</table>
	</td>
	</tr>
	</logic:iterate>
</table>



</html:form>
</body>
</html>