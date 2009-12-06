<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.entity.User;" %>
<%
	 User user = (User)request.getSession().getAttribute("user");
	
%>
<html>
<head>
	<link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>

</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">订单管理</font><font color="838383">
              -&gt; </font><font color="838383">个人未完成处理的订单</font>
          </td>
   </tr>
</table>
<br>
<TABLE width="95%" align="center">
<TR>
	<TD>当前用户：<%=user.getUSERID()%>(<%=user.getNAME()%>)</TD>
</TR>
</TABLE>
<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
  
  <tr>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单号</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >购物金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >应付金额</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单状态</th>		
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单来源类型</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >订单类型</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >下单时间</th>
		
	</tr>

   <% int i=0;%>
  <logic:iterate  name="orders" id="order"> 
	<bean:define id="member" name="order" property="member"/>
	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %>>		
        
    <td  noWrap align=center ><a href="../order/orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a></td>
		
   <td  noWrap align=right ><bean:write name="order" property="goodsFee" format="#0.00"/></td>
		
    <td  noWrap align=right ><bean:write name="order" property="payable" format="#0.00"/></td>
		
    <td  noWrap align=left ><bean:write name="order" property="statusName"/></td>		
		
    <td  noWrap align=left ><bean:write name="order" property="prTypeName"/></td>
		
    <td  noWrap align=left ><bean:write name="order" property="categoryName"/></td>
		
    <td noWrap align=left ><bean:write name="order" property="createDate"/></td>
	</tr>
	<%i++;%>
	</logic:iterate>

</table>


</body>

</html>
