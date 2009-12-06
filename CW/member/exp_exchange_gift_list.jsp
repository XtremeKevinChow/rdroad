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

function doSearch() {
	var theForm = document.forms[0];
	
	if(theForm.cardID.value=="" && theForm.memberName.value=="" && theForm.telephone.value=="" ) {
		// 没有查询条件
		alert("请输入查询条件！");
		theForm.cardID.focus();
		return false;
	} 
	theForm.offset.value = 0;
	theForm.query.disabled = true;
	theForm.submit();
}

function exchange_f(obj) {
	
	
	var flag = false;
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("确实要取消礼品吗?"))
		{
			document.forms[0].action = "/member/mbrGetAward.do?type=expCancelGift";
			obj.disabled;
			document.forms[0].submit();
		}
		
	} else {
		alert("请选择记录!");
	}
}

//全选充值
function checkAll(bln, type) {
	if(document.forms[0].selectedID == null) {
		return;
	}
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if(bln) {
			
			if (typeof(row.getElementsByTagName("INPUT")[type]) != "undefined")
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (typeof(row.getElementsByTagName("INPUT")[type]) != "undefined")
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}
function load() {
	document.forms[0].cardID.focus();
}
function add_f(obj) {
	document.forms[0].action = "/member/memberAddGift.do?type=addInit";
	//obj.disabled = true;
	document.forms[0].submit();
}
function add_f2(obj) {
	document.forms[0].action = "/member/memberAddGift.do?type=addInit2";
	//obj.disabled = true;
	document.forms[0].submit();
}

</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load()">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">礼品查询</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form action="/mbrGetAward.do?type=queryGift">
<bean:define name="mbrGetAwardForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<table  align="center" width="98%" border=0 cellspacing=1 cellpadding=1 >
	<tr>
		<td>会员卡号:
		<html:hidden property="memberID" />
		<html:text property="cardID" size="10"/>&nbsp;&nbsp;
		姓名：
		<html:text property="memberName" size="10"/>&nbsp;&nbsp;
		电话:
		<html:text property="telephone" size="10"/>&nbsp;&nbsp;
		礼品号：<html:text property="itemCode" size="8"/>&nbsp;&nbsp;
        类型：
		<html:select property="sellType">
		<html:optionsCollection name="mbrGetAwardForm" property="sellTypeList" value="code" label="name"/> 
		</html:select>&nbsp;&nbsp;
		
		<input type="button" name="query" value=" 查询 " onclick="doSearch()">
		</td>
		
	</tr>
	
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table width="98%" align="center" border=0  id="DataTable">
	<tr>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >全选<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"></th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >货号</th>
		<th width="20%"  class="OraTableRowHeader" noWrap align=middle  >产品名称</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >类型</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >价格</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >数量</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >使用积分</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >创建时间</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >结束日期</th>
		<th width="8%"  class="OraTableRowHeader" noWrap align=middle  >状态</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >经办人</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >是否转移</th>
	</tr>
    <% int i=0; %>
    <logic:iterate id="giftList" name="list"> 
	<tr <%if(i%2==1) {%> class="OraTableCellText" <%}%>>
			
    <td align=center noWrap >
	<logic:equal name="giftList" property="status" value="0">
	<input type="checkbox" name="selectedID" value="<bean:write name="giftList" property="ID"/>"></td>
	</logic:equal>
    <td class= noWrap align=center >
	&nbsp;<bean:write name="giftList" property="itemCode"/>
	</td>
		
    <td  noWrap align="left" >
	&nbsp;<bean:write name="giftList" property="itemName"/>
	</td>
    
    <td noWrap align="left" >
	<logic:equal name="giftList" property="type" value="3">其他销售</logic:equal>
	<logic:equal name="giftList" property="type" value="4">礼品赠品</logic:equal>
	<logic:equal name="giftList" property="type" value="5">介绍人赠品</logic:equal>
	<logic:equal name="giftList" property="type" value="6">积分换礼</logic:equal>
	<logic:equal name="giftList" property="type" value="7">注册送礼</logic:equal>
	<logic:equal name="giftList" property="type" value="9">网上活动礼品</logic:equal>
	<logic:equal name="giftList" property="type" value="12">转移礼品</logic:equal>
	<logic:equal name="giftList" property="type" value="13">人工加礼品</logic:equal>
	<logic:equal name="giftList" property="type" value="14">其他</logic:equal>
	<logic:equal name="giftList" property="type" value="16">电子礼券</logic:equal>

	</td>

	<td noWrap align="right" >
	<bean:write name="giftList" property="price" format="#0.00"/>
	</td>
	<td noWrap align="right" >
	<bean:write name="giftList" property="quantity" />
	</td>
	
	<td  noWrap align="right" >
	<bean:write name="giftList" property="used_amount_exp" format="#0"/>
	</td>
	
	<td  noWrap align=center >
	<bean:write name="giftList" property="create_date"/>
	</td>

	<td noWrap align="center" >
	<bean:write name="giftList" property="lastDate"/>
	</td>
	
	
	<td noWrap align="left" >
	<logic:equal name="giftList" property="status" value="0">待发</logic:equal>
	<logic:equal name="giftList" property="status" value="-1">永久取消</logic:equal>
	<logic:equal name="giftList" property="status" value="-10">永久取消</logic:equal>
	<logic:equal name="giftList" property="status" value="-5"><font color="red">已过期</font></logic:equal>
	<logic:equal name="giftList" property="status" value="1">已发</logic:equal>
	<logic:equal name="giftList" property="status" value="10">已发</logic:equal>
	</td>

	<td  noWrap align="left" >
	<bean:write name="giftList" property="operatorName"/>
	</td>

	<td  noWrap align="center" >
	<logic:equal name="giftList" property="isTransfer" value="0">否</logic:equal>
	<logic:equal name="giftList" property="isTransfer" value="1">是</logic:equal>
	</td>
	</tr>
	<%i++;%>
	</logic:iterate>
</table>
<br>
<!--
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>待审核礼品
      	</td>
   </tr>
</table>
-->

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center">
		<input type="button" name="addBtn1" value=" 新增礼品 " onclick="add_f(this)">
		<input type="button" name="addBtn3" value="礼品取消" onclick="exchange_f(this)">&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
