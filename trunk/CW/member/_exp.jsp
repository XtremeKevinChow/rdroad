<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>


</head>
<body bgcolor="#FFFFFF" onload="load()">
<html:form action="/consoleExp.do?iscallcenter=1" method="post">
<bean:define name="expExchangeHisForm" property="pager" id="pager"/>
<html:hidden name="expExchangeHisForm" property="offset"/>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" id="DataTable">
	<tr>
		
		<!-- <th width="10%"   class="OraTableRowHeader" noWrap   >��Ա����</th> -->
		
		<th width="10%"   class="OraTableRowHeader" noWrap   >ҵ������</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >��Ȼ���</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >�ۼƻ���</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >��������</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >״    ̬</th>
		
		
		<th width="15%"   class="OraTableRowHeader" noWrap   >ƾ֤��</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >����˵��</th>
		
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
     
    <!-- <td bgcolor="#FFFFFF" noWrap align="center" >
	<a href="memberDetail.do?id=<bean:write name="giftList" property="memberID"/>"><bean:write name="giftList" property="cardID"/></a>
	</td> -->

	<td bgcolor="#FFFFFF" noWrap align="left" >
	<logic:equal name="giftList" property="opType" value="1">���ֶһ�</logic:equal>
	<logic:equal name="giftList" property="opType" value="2">��Ʒȡ��</logic:equal>
	<logic:equal name="giftList" property="opType" value="3">����</logic:equal>
	<logic:equal name="giftList" property="opType" value="4">�˻�</logic:equal>
	</td>
	<td bgcolor="#FFFFFF" noWrap align="right" >
	<bean:write name="giftList" property="exp"/>
	</td>

	<td bgcolor="#FFFFFF" noWrap align="right" >
	<bean:write name="giftList" property="totalExp"/>
	</td>
	<td bgcolor="#FFFFFF" noWrap align="center" >
	<bean:write name="giftList" property="createDate"/>
	</td>
	<td bgcolor="#FFFFFF" noWrap align="left" >
	<logic:equal name="giftList" property="isvalid" value="0">δ��Ч</logic:equal>
	<logic:equal name="giftList" property="isvalid" value="1">��Ч</logic:equal>
	<logic:equal name="giftList" property="isvalid" value="2">ȡ��</logic:equal>

	</td>
	<td bgcolor="#FFFFFF" noWrap align="left" >
	<!-- <a href=javascript:ajaxpage("../order/snView.do?queryKey=findBySNNum&sn_id=<bean:write name="giftList" property="docNumber"/>","ajaxcontentarea","obj")> --><bean:write name="giftList" property="docNumber"/><!-- </a> -->
	</td>
	<td bgcolor="#FFFFFF" noWrap align="left" >
	<bean:write name="giftList" property="operatorName"/>
	</td>

	</tr>
	</logic:iterate>
</table>

</html:form>
</body>
</html>
