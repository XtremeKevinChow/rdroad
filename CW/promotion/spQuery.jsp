<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function f_new() {
	document.forms[0].action="salePromotion.do?type=addInit";
	document.forms[0].submit();
}
function f_modify(id) {
	document.forms[0].action="salePromotion.do?type=modInit&id=" +id;
	document.forms[0].submit();
}
function f_cancel(id) {
	if (confirm("ȷ��ȡ���ô�����Ʒ��")) {
		document.forms[0].action="salePromotion.do?type=cancel&id=" +id;
		document.forms[0].submit();
	}
}
function f_set() {
	if(!f_checkFreeDelivery()) {
		alert("������ø�ʽ����");
		return;
	}

	if (confirm("ȷʵҪ�����ⷢ�ͷ�������")) {
		document.forms[0].action="salePromotion.do?type=setFreeDelivery";
		document.forms[0].submit();
	}
}
function f_checkFreeDelivery() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].free_delivery_require.value);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ������ѯ</font></td>
  </tr>
</table>
<html:form action="/salePromotion.do?type=query" onsubmit="return doSearch();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		<td>�����ѡ��
        <html:select property="sel_msc">
        	<OPTION value="" selected>�ϻ�Ա�����</OPTION> 
            <html:optionsCollection  property="msc_codes" value="msc_code" label="msc_name"/> 
        </html:select>
        <input name="BtnQuery" type="submit" value=" ��ѯ ">
      </td>
      <td align=center>
      	<input name="BtnQuery" type="button" value=" ���� " onClick="f_new();">
      </td>		
	</tr>
</table>
<br>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Բ�ƷȺ���Ҫ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ʒ</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�𿨼�</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��վ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��ʼ����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
		<!--<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���÷�Χ</th>-->
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�Ƿ���Ч</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >&nbsp;</th>
	</tr>
    <logic:iterate name="spGiftForm" property="sps" id="sp"> 
	<tr>		
    <td class=OraTableCellText noWrap align=center >����<font color=red><bean:write name="sp" property="group_name"/></font>�ﵽ<font color=red><bean:write name="sp" property="order_require" format="#0.00"/></font>Ԫ</td>
	<td class=OraTableCellText noWrap align=left ><bean:write name="sp" property="item_code"/>&nbsp;<bean:write name="sp" property="item_name"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="sp" property="gold_price" format="#0.00"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="sp" property="silver_price" format="#0.00"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="sp" property="web_price" format="#0.00"/></td>		
	<td class=OraTableCellText noWrap align=left ><bean:write name="sp" property="start_date" format="yyyy-MM-dd"/></td>
	<td class=OraTableCellText noWrap align=left ><bean:write name="sp" property="end_date" format="yyyy-MM-dd"/> </td>
	<!--<td class=OraTableCellText noWrap align=center >
	<logic:equal name="sp" property="scope" value="1">
	������Ч
	</logic:equal>
	<logic:equal name="sp" property="scope" value="2">
	������Ч
	</logic:equal>
	<logic:equal name="sp" property="scope" value="3">
	�������¾���Ч
	</logic:equal>
	</td>-->
	<td class=OraTableCellText noWrap align=center >
	<logic:equal name="sp" property="valid_flag" value="Y">
	��Ч
	</logic:equal>
	<logic:equal name="sp" property="valid_flag" value="N">
	��Ч
	</logic:equal>
	<logic:equal name="sp" property="valid_flag" value="C">
	ȡ��
	</logic:equal>
	</td>
	<td>
	<input type=button value=" �޸� " onclick="javascript:f_modify('<bean:write name="sp" property="id" format="#"/>');"><input type=button value=" ȡ�� " onclick="javascript:f_cancel('<bean:write name="sp" property="id" format="#"/>');">
	</td>
	</tr>
	</logic:iterate>
</table>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=80% class="OraTableRowHeader" noWrap  noWrap align=left  >�ⷢ�ͷѹ���</th>
		<th  class="OraTableRowHeader" noWrap  noWrap align=left  ></th>
	</tr>
	<tr>
		<td>��������&nbsp;<html:text property="free_delivery_require" size="6"/>&nbsp;Ԫ�ⷢ�ͷ�</td>
		<td><input type=button value=" ���� " onclick="f_set()"></td>
	</tr>	
</table>
</html:form>
</body>
</html>
