<%@ page contentType="text/html;charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<body bgcolor="#FFFFFF" >
<html:form action="/consoleOrders.do?iscallcenter=1" >
<bean:define name="pagerForm" property="pager" id="pager"/>
<html:hidden name="pagerForm" property="offset"/>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
		<div id="purchaseRecords" style="width:100%;">
		<table width="100%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000">
			<tr>
				<th width="12%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
				<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
				<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����Ӧ��</th>
				<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��ԱӦ��</th>
				<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����״̬</th>
				<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ͷ�ʽ</th>
				<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
				<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
				<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�µ�����</th>
				<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������Ա</th>
				<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
			</tr>
			  <logic:iterate id="order" name="memberBuy" >
			<tr>
				<td bgcolor="#FFFFFF" noWrap align=middle >
				<a href=javascript:ajaxpage("../order/orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>&isconsole=Y","ajaxcontentarea","obj")><bean:write name="order" property="orderNumber"/></a></td>
				<td bgcolor="#FFFFFF" noWrap align=right ><bean:write name="order" property="totalMoney" format="#0.00" /></td>
				<td bgcolor="#FFFFFF" noWrap align=right ><bean:write name="order" property="payable" format="#0.00" /></td>
				<td bgcolor="#FFFFFF" noWrap align=right ><bean:write name="order" property="mbPayable" format="#0.00"/></td>
				<td bgcolor="#FFFFFF" noWrap align=left >
				<bean:write name="order" property="statusName"/>
				
				</td>
				
				<td bgcolor="#FFFFFF" noWrap align=left ><bean:write name="order" property="deliveryTypeName"/></td>
				<td bgcolor="#FFFFFF" noWrap align=left ><bean:write name="order" property="categoryName"/></td>
				<td bgcolor="#FFFFFF" noWrap align=left >
				<logic:equal name="order" property="orderType" value="10">��ͨ����</logic:equal>
				<logic:equal name="order" property="orderType" value="5">Ԥ�۶���</logic:equal>
				<logic:equal name="order" property="orderType" value="15">�Ź�����</logic:equal>
				<logic:equal name="order" property="orderType" value="20">������</logic:equal>
				</td>
				<td bgcolor="#FFFFFF" noWrap align=left ><bean:write name="order" property="createDate"/></td>
				<td bgcolor="#FFFFFF" noWrap align=left >
				<logic:equal name="order" property="creatorName" value="">��</logic:equal>
				<bean:write name="order" property="creatorName"/>
				</td>
				<td bgcolor="#FFFFFF" noWrap align="center">
					<logic:greaterThan name="order" property="statusId" value="25">
    				<logic:lessEqual name="order" property="statusId" value="100">
					<input name="btn" type="button" value="������" onclick=queryFahuo('../member/consoleSnQuery.do','<bean:write name="order" property="orderNumber"/>') >
					</logic:lessEqual>
    				</logic:greaterThan>
					<logic:equal name="order" property="statusId" value="-8">
					<input name="btn" type="button" value="������" onclick=queryFahuo('../member/consoleSnQuery.do','<bean:write name="order" property="orderNumber"/>') >
					</logic:equal>
					<logic:equal name="order" property="statusId" value="-1">
					<input name="cancelbtn" type="button" value="ȡ����Ϣ" onclick=viewCancelInfo("../order/viewCancelInfo.jsp?orderId=<bean:write name="order" property="orderId"/>") >
					
					</logic:equal>
				</td>
			</tr>
			</logic:iterate>
		</table>
		</div>
</html:form>

</body>
</html>