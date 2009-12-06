<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSearch() {
	var theForm = document.forms[0];
	var nCondition = 0;
	if(trim(theForm.parentID.value) != "") nCondition++;
	if(trim(theForm.parentName.value) != "") nCondition++;
	
	
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入查询条件！");
		return false;
	} 
	theForm.query.disabled;
}
function setId(id, name) {

	document.forms[0].parentID.value = id;
	document.forms[0].parentName.value = name;
	document.forms[0].parentName.focus();
}
function add_f() {
	document.forms[0].action = "/member/memberExpExchangeSetup.do?type=addInit";
	document.forms[0].submit();
}
function del_f() {
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
		if (confirm("确实要禁用吗?"))
		{
			document.forms[0].action = "/member/memberExpExchangeSetup.do?type=delete";
			document.forms[0].submit();
		}
		
	} else {
		alert("请选择记录!");
	}
	
}
//全选充值
function checkAll(bln, type) {
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if(bln) {

			row.getElementsByTagName("INPUT")[type].checked = true;
			
		}else{
			row.getElementsByTagName("INPUT")[type].checked = false;
		}
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">积分兑换礼品设置</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form action="/memberExpExchangeSetup.do?type=query" onsubmit="return doSearch();">
<table  align="center" width="98%" border=0 cellspacing=1 cellpadding=1 >
	<tr>
		<td>活动名称：
		<html:hidden property="parentID" />
		<html:text property="parentName" readonly="true" />
		<input name="select" type="button" value=" 选择 " onclick="openWin('../member/memberExpExchangeSetup.do?type=select', 'PopWin', 700, 450);">&nbsp;&nbsp;&nbsp;
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
</table>
<table width="98%" align="center" border=0  id="DataTable">
	<tr>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >全选<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"></th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >货号</th>
		<th width="40%"  class="OraTableRowHeader" noWrap align=middle  >产品名称</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >兑换积分</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >兑换价格</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >开始日期</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >结束日期</th>
		<!-- <th width="10%"  class="OraTableRowHeader" noWrap align=middle  >有效天数</th> -->
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >是否有效</th>
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
     
	<td align=center noWrap class=OraTableCellText >
	<input type="checkbox" name="delID" value="<bean:write name="giftList" property="ID"/>"></td>

    <td class=OraTableCellText noWrap align=center >
	<a href="/member/memberExpExchangeSetup.do?type=modInit&id=<bean:write name="giftList" property="ID"/>">
	<bean:write name="giftList" property="itemCode"/></a>
	</td>
		
    <td class=OraTableCellText noWrap align="left" >
	<bean:write name="giftList" property="itemName"/>
	</td>
	
	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="giftList" property="expStart" format="#0"/>
	</td>

	<td class=OraTableCellText noWrap align="right" >
	<bean:write name="giftList" property="exchangePrice" format="#0"/>
	</td>

	<td class=OraTableCellText noWrap align=center >
	<bean:write name="giftList" property="startDate"/>
	</td>

	<td class=OraTableCellText noWrap align=center >
	<bean:write name="giftList" property="endDate"/>
	</td>

	<!-- <td class=OraTableCellText noWrap align="right" >
	<bean:write name="giftList" property="validDay"/>
	</td> -->

	<td class=OraTableCellText noWrap align="center" >
	<bean:write name="giftList" property="validFlag"/>
	<!-- <logic:equal name="memberExpExchangeForm" property="validFlag" value="Y">有效</logic:equal>
	<logic:equal name="memberExpExchangeForm" property="validFlag" value="N">无效</logic:equal> -->
	</td>

	</tr>
	</logic:iterate>
</table>
<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center"><input type="button" name="addBtn" value=" 新增 " onclick="add_f()">&nbsp;&nbsp;
		<input type="button" name="delBtn" value=" 禁用 " onclick="del_f()"></td>
		
	</tr>
</table>
</html:form>
</body>
</html>
