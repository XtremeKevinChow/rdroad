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
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">��������</font><font color="838383">
              -&gt; </font><font color="838383">����δ��ɴ���Ķ���</font>
          </td>
   </tr>
</table>
<br>
<TABLE width="95%" align="center">
<TR>
	<TD>��ǰ�û���<%=user.getUSERID()%>(<%=user.getNAME()%>)</TD>
</TR>
</TABLE>
<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
  
  <tr>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >Ӧ�����</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����״̬</th>		
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������Դ����</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
		<th width="13%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�µ�ʱ��</th>
		
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
