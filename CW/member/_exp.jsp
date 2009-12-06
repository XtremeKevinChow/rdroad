<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
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
		
		<!-- <th width="10%"   class="OraTableRowHeader" noWrap   >会员卡号</th> -->
		
		<th width="10%"   class="OraTableRowHeader" noWrap   >业务类型</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >年度积分</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >累计积分</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >创建日期</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >状    态</th>
		
		
		<th width="15%"   class="OraTableRowHeader" noWrap   >凭证号</th>
		<th width="10%"   class="OraTableRowHeader" noWrap   >操作说明</th>
		
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
     
    <!-- <td bgcolor="#FFFFFF" noWrap align="center" >
	<a href="memberDetail.do?id=<bean:write name="giftList" property="memberID"/>"><bean:write name="giftList" property="cardID"/></a>
	</td> -->

	<td bgcolor="#FFFFFF" noWrap align="left" >
	<logic:equal name="giftList" property="opType" value="1">积分兑换</logic:equal>
	<logic:equal name="giftList" property="opType" value="2">礼品取消</logic:equal>
	<logic:equal name="giftList" property="opType" value="3">购买</logic:equal>
	<logic:equal name="giftList" property="opType" value="4">退货</logic:equal>
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
	<logic:equal name="giftList" property="isvalid" value="0">未生效</logic:equal>
	<logic:equal name="giftList" property="isvalid" value="1">有效</logic:equal>
	<logic:equal name="giftList" property="isvalid" value="2">取消</logic:equal>

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
