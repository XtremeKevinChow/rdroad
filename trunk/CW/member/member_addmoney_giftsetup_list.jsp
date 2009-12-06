<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function doSearch() {
	var theForm = document.forms[0];
	var nCondition = 0;
	if(trim(theForm.cardId.value) != "") nCondition++;
	if(trim(theForm.mbName.value) != "") nCondition++;
	if(trim(theForm.orderNumber.value) != "") nCondition++;
	
	if(nCondition == 0) {
		// 没有查询条件
		alert("请输入查询条件！");
		return false;
	} 
	theForm.BtnQuery.disabled;
}
function add_f() {
	document.forms[0].action = "/member/memberAddMoneyGiftSetup.do?type=addInit";
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
		if(confirm("要设置为无效吗?")) {
			document.forms[0].action = "/member/memberAddMoneyGiftSetup.do?type=delete";
			document.forms[0].submit();
		}
	} else {
		alert("请选择记录");
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
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 帐户管理 -&gt; 会员充值礼券设置</font></td>
  </tr>
</table>
<html:form action="/memberAddMoneyGiftSetup.do?type=query" onsubmit="return doSearch();">
  

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 border=1 id="DataTable">
	<tr>
		<th width="65"  class="OraTableRowHeader" noWrap  noWrap align=middle  >全选<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"></th>
		<th width="60"  class="OraTableRowHeader" noWrap  noWrap align=middle  >记录号</th>
		<!--
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >货号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >价格</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >兑换价格</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >保留天数</th>-->
    <th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >是否有效</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >金额档次</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >礼券批号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >保留天数</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >礼券金额</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >礼券开始日期</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle  >礼券结束日期</th>
	</tr>
    
    <logic:iterate id="giftList" name="list"> 
	<tr>		
     
	<td class=OraTableCellText noWrap align=center ><input type="checkbox" name="delID" value="<bean:write name="giftList" property="id"/>"/></td>

    <td class=OraTableCellText noWrap align=center>
	<bean:write name="giftList" property="id"/></td>
	<td class=OraTableCellText noWrap align=center >
	<logic:equal name="giftList" property="status" value="0">有效</logic:equal>
	<logic:equal name="giftList" property="status" value="-1">无效</logic:equal>
	</td>
  <td class=OraTableCellText noWrap align=right ><bean:write name="giftList" property="money" format="#0.00"/></td>
	<td class=OraTableCellText noWrap align=right >
	<a   href=""  onclick="window.open('../promotion/mbr_gift_certificates_detail.jsp?id=<bean:write name="giftList" property="gift_number" />','_blank','width=600,height=200,scrollbars=0,resizable=0');return   false">
	<bean:write name="giftList" property="gift_number" /></a>
  
  </td>
  <td class=OraTableCellText noWrap align=center ><bean:write name="giftList" property="keepDays"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="giftList" property="gift_money"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="giftList" property="gift_start_date"/></td>
	<td class=OraTableCellText noWrap align=center ><bean:write name="giftList" property="gift_end_date"/></td>
	
	</tr>
	</logic:iterate>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
<tr>
		<td align="center"><input type="button" name="addBtn" value=" 新 增 " onclick="add_f()">&nbsp;&nbsp;
		<input type="button" name="delBtn" value="设置无效" onclick="del_f()"></td>
		
	</tr>
</table>
</html:form>
</body>
</html>
