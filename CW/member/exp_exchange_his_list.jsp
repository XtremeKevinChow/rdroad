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
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	//var nCondition = 0;
	//if(trim(theForm.cardID.value) != "") nCondition++;
	
	//if(nCondition == 0) {
		// 没有查询条件
		//alert("请输入查询条件！");
		//theForm.cardID.focus();
		//return false;
	//} 
	document.forms[0].offset.value = 0;
	theForm.query.disabled;
}

function exchange_f(obj) {
	
	var flag = false;
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if (row.getElementsByTagName("INPUT")(0).checked == true)
		{
			flag = true;
			break;
		}
		
	}
	if (flag == true)
	{
		if (confirm("确实要兑换吗?"))
		{
			document.forms[0].action = "/member/expExchange.do?type=expChangeGift";
			obj.disabled;
			document.forms[0].submit();
		}
		
	} else {
		alert("请选择记录!");
	}
}

//全选充值
function checkAll(bln, type) {
	if(document.forms[0].ID == null) {
		
		return;
	}
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if(bln) {
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}
function load() {
	document.forms[0].cardID.focus();
}
//-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" onload="load()">
<html:form action="/expExchangeHis.do?type=query" onsubmit="return doSearch();">
<html:hidden name="expExchangeHisForm" property="offset"/>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">积分历史查询</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table  align="center" width="98%" border=0 cellspacing=1 cellpadding=0 >
	<tr>
		<td>会员卡号：
		<html:text property="cardID" size="12" />&nbsp;&nbsp;&nbsp;&nbsp;
		业务类型：
		<html:select property="opType">
		<html:optionsCollection name="expExchangeHisForm" property="opTypeList" value="code" label="name"/> 
		</html:select>&nbsp;&nbsp;
		状  态：
		<html:select property="isvalid">
		<html:optionsCollection name="expExchangeHisForm" property="isvalidList" value="code" label="name"/> 
		</html:select>
		</td>
	</tr>

	<tr>
		<td>开始时间：
		<html:text property="startDate" size="12" readonly="true"/><a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a> 
		结束时间：
		<html:text property="endDate" size="12" readonly="true"/><a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a> (格式:YYYY-MM-DD) &nbsp;&nbsp;
		<input type="submit" name="query" value=" 查询 ">
		</td>
	</tr>
	
	
</table>
<table width="98%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
 
	<td>
		<bean:define name="expExchangeHisForm" property="pager" id="pager"/>
		<logic:present name="pager">
		<bean:write name="pager" property="pageNavigation" filter="false"/>
		</logic:present>
	</td>
  </tr>
</table>
<table width="98%" align="center" border=0  id="DataTable">
	<tr>
		<th width="8%"  class="OraTableRowHeader"  >全选<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"></th>
		<th width="10%"  class="OraTableRowHeader"  >会员卡号</th>
		
		<th width="10%"  class="OraTableRowHeader"  >业务类型</th>
		<th width="10%"  class="OraTableRowHeader"  >年度积分</th>
		<th width="10%"  class="OraTableRowHeader"  >累计积分</th>
		<th width="10%"  class="OraTableRowHeader"  >创建日期</th>
		<th width="10%"  class="OraTableRowHeader"  >状    态</th>
		
		
		<th width="15%"  class="OraTableRowHeader"  >凭证号</th>
		<th width="10%"  class="OraTableRowHeader"  >操作说明</th>
		
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
     
	<td align=center noWrap class=OraTableCellText >
	<input type="checkbox" name="ID" value="<bean:write name="giftList" property="ID"/>">
	</td>

    <td class=OraTableCellText noWrap align="center" >
	<a href="memberDetail.do?id=<bean:write name="giftList" property="memberID"/>"><bean:write name="giftList" property="cardID"/></a>
	</td>
		
    
	
	<td class=OraTableCellText noWrap align="left" >
	<logic:equal name="giftList" property="opType" value="1">积分兑换</logic:equal>
	<logic:equal name="giftList" property="opType" value="2">礼品取消</logic:equal>
	<logic:equal name="giftList" property="opType" value="3">购买</logic:equal>
	<logic:equal name="giftList" property="opType" value="4">退货</logic:equal>
	</td>
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="giftList" property="exp"/>
	</td>

	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="giftList" property="totalExp"/>
	</td>
	<td class=OraTableCellText noWrap align="center" >
	<bean:write name="giftList" property="createDate"/>
	</td>
	<td class=OraTableCellText noWrap align="left" >
	<logic:equal name="giftList" property="isvalid" value="0">未生效</logic:equal>
	<logic:equal name="giftList" property="isvalid" value="1">有效</logic:equal>
	<logic:equal name="giftList" property="isvalid" value="2">取消</logic:equal>

	</td>

	

	
	<td class=OraTableCellText noWrap align="left" >
	<a href="/order/snView.do?queryKey=findBySNNum&sn_id=<bean:write name="giftList" property="docNumber"/>"><bean:write name="giftList" property="docNumber"/></a>
	</td>
	<td class=OraTableCellText noWrap align="left" >
	<bean:write name="giftList" property="operatorName"/>
	</td>

	</tr>
	</logic:iterate>
</table>
<!-- <table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center"><input type="button" name="addBtn" value=" 兑换 " onclick="exchange_f(this)">&nbsp;&nbsp;
		
		
	</tr>
</table> -->
</html:form>
</body>
</html>
