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
<title>佰明会员关系管理系统</title>
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">订单管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">可用礼券列表</font><font color="838383"> 
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
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >礼券号</th>
      <th width="12%"  class="OraTableRowHeader" noWrap align=middle  >使用条件</th>
      <th width="8%"  class="OraTableRowHeader" noWrap align=middle  >可用次数</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >已用次数</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >最后使用日期</th>
      <th width="8%"  class="OraTableRowHeader" noWrap  align=middle  >描述</th>
    </tr>
    <% int i=0; %>
    <bean:define id="giftnumbers" name="list" type="java.util.Collection"/>
    <logic:iterate id="giftnum" name="giftnumbers">
    
    <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> style="cursor:hand" id ="<bean:write name="giftnum" property="gift_number"/>"> 
      <td  noWrap align=right ><bean:write name="giftnum" property="gift_number" /></td>
      <td noWrap align=middle >满<bean:write name="giftnum" property="order_require" />元抵扣<bean:write name="giftnum" property="price" />元</td>
      <td  noWrap align=right ><bean:write name="giftnum" property="total_num" /></td>
      <td  noWrap align=right ><bean:write name="giftnum" property="num" /></td>
      <td  noWrap align=right ><bean:write name="giftnum" property="lastDate" /></td>
      <td  noWrap align=right ><bean:write name="giftnum" property="description"  format="0.00"/></td>
    </tr>
    <% i++; %>
    </logic:iterate>
    <tr><td colspan="3"><font color="red">使用说明:请双击选中选项。</font></td></tr>
  </table>

</html:form>

</body>
</html>
