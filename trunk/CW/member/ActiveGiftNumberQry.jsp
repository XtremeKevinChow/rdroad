<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String isreport=request.getParameter("isreport");
       isreport=(isreport==null)?"":isreport;
%>

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

function selectItem(){

	var selectedid = getSelectedItem();
	
	if(selectedid !="") {
	    
	    //opener.document.forms[0].item_code.value=selectedid;
		opener.getOpenwinGiftNumber(selectedid);
	    self.close();
	}
	
}
function setfocus(){
	document.forms[0].qry_item_code.focus();
	return true;
}


</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">������ȯ�б�</font><font color="838383"> 
      	</td>
   </tr> 
</table>


<html:form action="/mbrGetAward.do?type=qryActiveGiftNumber" method="post" onsubmit="return checkInput();">

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" align="center" onclick="changeItem()" ondblclick="selectItem()" border=0 cellspacing=1 cellpadding=2 >
    <tr height="26" > 
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >��ȯ��</th>
      <th width="12%"  class="OraTableRowHeader" noWrap align=middle  >ʹ������</th>
      <th width="8%"  class="OraTableRowHeader" noWrap align=middle  >���ô���</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >���ô���</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >���ʹ������</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >����</th>
    </tr>
    <% int i=0; %>
    <bean:define id="giftnumbers" name="list" type="java.util.Collection"/>
    <logic:iterate id="giftnum" name="giftnumbers">
    
    <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> style="cursor:hand" id ="<bean:write name="giftnum" property="gift_number"/>"> 
      <td  noWrap align=right ><bean:write name="giftnum" property="gift_number" /></td>
      <td noWrap align=middle >��<bean:write name="giftnum" property="order_require" />Ԫ�ֿ�<bean:write name="giftnum" property="price" />Ԫ</td>
      <td  noWrap align=right ><bean:write name="giftnum" property="total_num" /></td>
      <td  noWrap align=right ><bean:write name="giftnum" property="num" /></td>
      <td  noWrap align=right ><bean:write name="giftnum" property="lastDate" /></td>
      <td  noWrap align=right ><bean:write name="giftnum" property="description"  format="0.00"/></td>
    </tr>
    <% i++; %>
    </logic:iterate>
    <tr><td colspan="3"><font color="red">ʹ��˵��:��˫��ѡ��ѡ�</font></td></tr>
  </table>

</html:form>

</body>
</html>
