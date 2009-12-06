<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/sortTable.js"></script>
<script language="JavaScript">
function checkAll(bln, type) {
	
	var len = DataTable.rows.length;
	for (i = 1; i < len; i++) {
		row = DataTable.rows(i);
		if(bln) {
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}
function query_f() {

	if ( !document.forms[0].proOrItem[0].checked && !document.forms[0].proOrItem[1].checked && !document.forms[0].proOrItem[2].checked  && !document.forms[0].proOrItem[3].checked  )
	{
		alert("��ѡ��Ӧ�̻��߲�Ʒ");
		return;
	}
	
	if (document.forms[0].proOrItemCondition == null || document.forms[0].proOrItemCondition.value == "")
	{
		alert("�������ѯ����");
		document.forms[0].proOrItemCondition.focus();
		return;
	}

	
	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
	
}
function seletValue() {
	var PKs = getPKs();
	if (PKs == null || PKs == "")
	{
		alert("��ѡ���¼");
		return;
	}
	window.close();
	opener.setValue(PKs);
}

function getPKs() {
	var str = "";
	
	var len = DataTable.rows.length;
	for (i = 1; i < len; i++) {
		row = DataTable.rows(i);
		
		if (row.getElementsByTagName("INPUT")[0].checked == true)//ѡ���ѡ��
		{
			str += row.getElementsByTagName("INPUT")[0].value + ",";

		}
		
	}
	if (document.forms[0].ids.value != "")
	{
		str += document.forms[0].ids.value;
	} else {
		str = str.substring(0, str.length - 1);
	}

	return str;
}

function splitIDs() {
	var arr = new Array();
	var frm = document.forms[0];
	
	if (frm.ids != null && frm.ids != "")
	{
		arr = frm.ids.value.split(",");
		
		if (frm.psDtlIDs != null)
		{
			if (typeof(frm.psDtlIDs.length) == "undefined")
			{
				for (var i = 0 ;i < arr.length ; i ++ )
				{
					if (arr[i] == frm.psDtlIDs.value)
					{
						frm.psDtlIDs.checked = "true";
					}
					
				}
			} else {
				for (var i=0; i < arr.length; i ++)
				{
			
					for (var j=0; j < frm.psDtlIDs.length ; j ++ )
					{
						if ( arr[i] == frm.psDtlIDs[j].value )
						{
							frm.psDtlIDs[j].checked = "true";
						}
					}
				}
			}
		}


	}
}


function load_f() {
	//splitIDs();
	loadSort(DataTable);
	document.forms[0].proOrItemCondition.focus();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f();">
<html:form action="finPurchase.do?type=queryCheckedPurchaseItems" method="POST">
<!-- ѩ���Ѿ�ѡ���ID -->
<html:hidden property="ids"/>
<html:hidden property="proNO"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��:</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">���ɲɹ���Ʊ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table width="100%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td> 
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr> 
    <td></td>
  </tr>
</table>


<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
  
  <tr> 
    <td>
	<html:radio property="proOrItem" value="1"/>��Ӧ������&nbsp;&nbsp;
	<html:radio property="proOrItem" value="2"/>��Ʒ����&nbsp;&nbsp;
	<html:radio property="proOrItem" value="3"/>��Ӧ�̴���&nbsp;&nbsp;
	<html:radio property="proOrItem" value="4"/>��Ʒ����&nbsp;&nbsp;
	<html:text property="proOrItemCondition" size="30" />&nbsp;&nbsp;&nbsp;&nbsp; 
	
   
    </td>
  </tr>
  <tr> 
    <td>
	����1��
	<html:select name="finPurchaseForm" property="orderByCondition1" >
		<html:option value="0">��</html:option>
		<html:option value="1">���ʱ��</html:option>
		<html:option value="2">��Ʒ����</html:option>
		<html:option value="3">��Ʒ����</html:option>
		<html:option value="4">��Ӧ�̴���</html:option>
		<html:option value="5">��Ӧ������</html:option>
		<html:option value="6">�������</html:option>
		<html:option value="7">Ԥ�㵥��</html:option>
		<html:option value="8">δ������</html:option>
		<html:option value="9">��������</html:option>
		<html:option value="10">��������</html:option>
		<html:option value="11">��ⵥ��</html:option>
	</html:select>
	<html:select property="ascOrDesc1">
		<html:option value="0">����</html:option>
		<html:option value="1">����</html:option>
	</html:select>&nbsp;&nbsp;
	����2��
	<html:select name="finPurchaseForm" property="orderByCondition2" >
		<html:option value="0">��</html:option>
		<html:option value="1">���ʱ��</html:option>
		<html:option value="2">��Ʒ����</html:option>
		<html:option value="3">��Ʒ����</html:option>
		<html:option value="4">��Ӧ�̴���</html:option>
		<html:option value="5">��Ӧ������</html:option>
		<html:option value="6">�������</html:option>
		<html:option value="7">Ԥ�㵥��</html:option>
		<html:option value="8">δ������</html:option>
		<html:option value="9">��������</html:option>
		<html:option value="10">��������</html:option>
		<html:option value="11">��ⵥ��</html:option>
	</html:select>
	<html:select property="ascOrDesc2">
		<html:option value="0">����</html:option>
		<html:option value="1">����</html:option>
	</html:select>&nbsp;&nbsp;
	����3��
	<html:select name="finPurchaseForm" property="orderByCondition3" >
		<html:option value="0">��</html:option>
		<html:option value="1">���ʱ��</html:option>
		<html:option value="2">��Ʒ����</html:option>
		<html:option value="3">��Ʒ����</html:option>
		<html:option value="4">��Ӧ�̴���</html:option>
		<html:option value="5">��Ӧ������</html:option>
		<html:option value="6">�������</html:option>
		<html:option value="7">Ԥ�㵥��</html:option>
		<html:option value="8">δ������</html:option>
		<html:option value="9">��������</html:option>
		<html:option value="10">��������</html:option>
		<html:option value="11">��ⵥ��</html:option>
	</html:select>
	<html:select property="ascOrDesc3">
		<html:option value="0">����</html:option>
		<html:option value="1">����</html:option>
	</html:select>&nbsp;&nbsp;
    <input type="button" value=" ��ѯ " name="btn_query" onclick="query_f();">
	<input type="button" value=" ȷ�� " name="btn_confirm" onclick="seletValue();">
    </td>
  </tr>
</table>
<bean:define id="list" name="finPurchaseForm" property="purchaseDetail"/>
<table id="DataTable" width="890" align="center" cellspacing="1" border="0" >
  
    <tr>
		<td width="30"  class="OraTableRowHeader" noWrap  noWrap align=middle sort=false>
		<input type="checkbox" onclick="checkAll(this.checked, 0)"></td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>���ʱ��</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</td>
		<td width="200"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>������</td>
		<td width="200"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ӧ������</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>�������</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>Ԥ�㵥��</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>δ������</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</td>
		<td width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle>��ⵥ��</td>
	</tr>
	<logic:iterate id="list" name="list">
	<tr>
		<td class=OraTableCellText noWrap align=middle >
		<!-- ���ѡ����������ʵ���ǲɹ���������ϸ��PK -->
			<input type="checkbox" name="psDtlIDs" value="<bean:write name="list" property="psDtlID"/>">
		</td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="purchaseDate"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="itemCode"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="itemName"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="proNO"/></td>
		<td class=OraTableCellText noWrap align=left ><bean:write name="list" property="proName"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="purPrice"  format="#0.00"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="useQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=right ><bean:write name="list" property="finishQty" format="#0"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="psCode"/></td>
		<td class=OraTableCellText noWrap align=middle ><bean:write name="list" property="resNO"/></td>
	</tr>
	</logic:iterate>
</table>
</html:form>
</body>
</html>