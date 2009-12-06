<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript">
function f_checkData() {
	if(!f_checkOrderRequire()) {
		alert("���������벻��ȷ");
		return false;
	}
	if(!f_checkItemCode()) {
		alert("�������벻��ȷ");
		return false;
	}
	if(!f_checkGoldPrice()) {
		alert("�𿨼����벻��ȷ");
		return false;
	}
	if(!f_checkSilverPrice()) {
		alert("���������벻��ȷ");
		return false;
	}
	if(!f_checkWebPrice()) {
		alert("��վ�����벻��ȷ");
		return false;
	}
	if(!f_checkStartDate()) {
		alert("��Ч�������벻��ȷ");
		return false;
	}
	if(!f_checkEndDate()) {
		alert("��ֹ�������벻��ȷ");
		return false;
	}
		
 	return true;
}

function f_checkOrderRequire() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].order_require.value);
}
function f_checkGoldPrice() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].gold_price.value);
}
function f_checkSilverPrice() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].silver_price.value);
}
function f_checkWebPrice() {
	var regex = /^((\d+)(\.\d{0,1})?)?$/;
	return regex.exec(document.forms[0].web_price.value);
}
function f_checkItemCode() {
	var regex = /^\w\d{5}$/;
	return regex.exec(document.forms[0].item_code.value);
}
function f_checkStartDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].start_date.value);
}
function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].end_date.value);
}
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/salePromotion.do?type=add" method="post" onsubmit="return f_checkData();">
  
<table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap >��Ӧ�����</td>
    <td>
    <html:select property="sel_msc">
    	<OPTION value="" selected>�ϻ�Ա�����</OPTION> 
    	<html:optionsCollection  property="msc_codes" value="msc_code" label="msc_name"/>
    </html:select><font color=red>*</font>
    </td>
 </tr>
</table>
<p>
<table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
  <tr> 
    <td >����
    <html:select property="group_id">
    	<html:optionsCollection  property="groups" value="group_id" label="group_name"/>
    </html:select>&nbsp;�еĲ�Ʒ,�����&nbsp;<html:text property="order_require" size="5"/><font color=red>*</font>&nbsp;Ԫ�����������Ż�
    </td>
 </tr>
 </table>
 <p>
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ����</td><td><html:text property="item_code"/><font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    �𿨼�</td><td><html:text property="gold_price"/><font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ������</td><td><html:text property="silver_price"/><font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ��վ�ؼ�</td><td><html:text property="web_price"/>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ��Ч����</td><td><html:text property="start_date"/>
    <a href="javascript:calendar(document.forms[0].start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ��ֹ����</td><td><html:text property="end_date"/>
    <a href="javascript:calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font>
    </td>
 </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap >
    ���÷�Χ</td><td>
    <html:select property="scope">
    <html:option value="1">������Ч</html:option>
    <html:option value="2">������Ч</html:option>
    <html:option value="3">�������¾���Ч</html:option>
    </html:select>
    </td>
 </tr>
 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" class="button2" value=" ȷ�� " > 
      &nbsp; <input type="button" class="button2" value=" ȡ�� " onClick="history.back();">
      
  </tr>
</table>
</html:form>

<p>&nbsp;</p>
</body>
</html>

