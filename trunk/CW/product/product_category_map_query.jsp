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
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
	var form = document.forms[0];
	return true;
}

function delete_rec(item_code,category_id) {
    document.forms[0].action="productCategory.do?type=delete&item_code=" + item_code + "&catalog_id=" + category_id;
    document.forms[0].submit();
}

function addCategory() {
    document.forms[0].action="productCategory.do?type=view";
    document.forms[0].submit();
}
function setfocus() {
    document.forms[0].qry_item_code.focus();
	return true;
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ��Ӧ�����ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productCategory.do?type=list" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
        <td width="80%">
        ���ţ� 
        <html:text property="qry_item_code" size="15"/>
        ����ţ� 
        <html:text property="qry_category_code" size="12"/>
        <input name="query" type="submit" class="button5" value=" ��ѯ ">
        <input name="query" type="button" class="button5" value=" ���� " onclick="javascript:addCategory();">
		</td>
		<td></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<!--��Ʒ��ѯ�б�begin-->

	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >

    <tr height="26" align="center" class="OraTableRowHeader"> 
      <th >����</th>
      <th >����</th>
      <th >�����</th>
      <th >��������</th>
	  <th >����</th>
    </tr>
    <% int i = 0; %>
    <bean:define id="items" name="list" type="java.util.Collection"/> 
    <logic:iterate id="item" name="items">
    <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> >  
      <td  noWrap align=right ><bean:write name="item" property="item_code" /></td>
      <td  noWrap align=right ><bean:write name="item" property="item_name" /></td>
      <td  noWrap align=right ><bean:write name="item" property="catalog_code" /></td>
      <td  noWrap align=right ><bean:write name="item" property="catalog_name" /></td>
      <td  noWrap align=right >
			<input type="button" name="delete" value="ɾ��" class="button5" onclick="javascript:delete_rec('<bean:write name="item" property="item_code" />','<bean:write name="item" property="catalog_id" />');">
			</td>
	</tr>
    <% i++; %>
    </logic:iterate>
    
  </table>

</html:form>

</body>
</html>
