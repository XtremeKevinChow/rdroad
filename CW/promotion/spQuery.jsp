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
function f_new() {
	document.forms[0].action="salePromotion.do?type=addInit";
	document.forms[0].submit();
}
function f_modify(id) {
	document.forms[0].action="salePromotion.do?type=modInit&id=" +id;
	document.forms[0].submit();
}
function f_cancel(id) {
	if (confirm("确定取消该促销产品？")) {
		document.forms[0].action="salePromotion.do?type=cancel&id=" +id;
		document.forms[0].submit();
	}
}
function f_set() {
	if(!f_checkFreeDelivery()) {
		alert("金额设置格式不对");
		return;
	}

	if (confirm("确实要更改免发送费设置吗？")) {
		document.forms[0].action="salePromotion.do?type=setFreeDelivery";
		document.forms[0].submit();
	}
}
function f_checkFreeDelivery() {
	var regex = /^(\d+)(\.\d{0,1})?$/;
	return regex.exec(document.forms[0].free_delivery_require.value);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 促销管理 -&gt; 促销查询</font></td>
  </tr>
</table>
<html:form action="/salePromotion.do?type=query" onsubmit="return doSearch();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		<td>促销活动选择：
        <html:select property="sel_msc">
        	<OPTION value="" selected>老会员促销活动</OPTION> 
            <html:optionsCollection  property="msc_codes" value="msc_code" label="msc_name"/> 
        </html:select>
        <input name="BtnQuery" type="submit" value=" 查询 ">
      </td>
      <td align=center>
      	<input name="BtnQuery" type="button" value=" 新增 " onClick="f_new();">
      </td>		
	</tr>
</table>
<br>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >针对产品群金额要求</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >金卡价</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >银卡价</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >网站价</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >开始日期</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >结束日期</th>
		<!--<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >作用范围</th>-->
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >是否有效</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >&nbsp;</th>
	</tr>
    <logic:iterate name="spGiftForm" property="sps" id="sp"> 
	<tr>		
    <td class=OraTableCellText noWrap align=center >购买<font color=red><bean:write name="sp" property="group_name"/></font>达到<font color=red><bean:write name="sp" property="order_require" format="#0.00"/></font>元</td>
	<td class=OraTableCellText noWrap align=left ><bean:write name="sp" property="item_code"/>&nbsp;<bean:write name="sp" property="item_name"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="sp" property="gold_price" format="#0.00"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="sp" property="silver_price" format="#0.00"/></td>
	<td class=OraTableCellText noWrap align=right ><bean:write name="sp" property="web_price" format="#0.00"/></td>		
	<td class=OraTableCellText noWrap align=left ><bean:write name="sp" property="start_date" format="yyyy-MM-dd"/></td>
	<td class=OraTableCellText noWrap align=left ><bean:write name="sp" property="end_date" format="yyyy-MM-dd"/> </td>
	<!--<td class=OraTableCellText noWrap align=center >
	<logic:equal name="sp" property="scope" value="1">
	网下有效
	</logic:equal>
	<logic:equal name="sp" property="scope" value="2">
	网上有效
	</logic:equal>
	<logic:equal name="sp" property="scope" value="3">
	网上网下均有效
	</logic:equal>
	</td>-->
	<td class=OraTableCellText noWrap align=center >
	<logic:equal name="sp" property="valid_flag" value="Y">
	有效
	</logic:equal>
	<logic:equal name="sp" property="valid_flag" value="N">
	无效
	</logic:equal>
	<logic:equal name="sp" property="valid_flag" value="C">
	取消
	</logic:equal>
	</td>
	<td>
	<input type=button value=" 修改 " onclick="javascript:f_modify('<bean:write name="sp" property="id" format="#"/>');"><input type=button value=" 取消 " onclick="javascript:f_cancel('<bean:write name="sp" property="id" format="#"/>');">
	</td>
	</tr>
	</logic:iterate>
</table>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width=80% class="OraTableRowHeader" noWrap  noWrap align=left  >免发送费规则</th>
		<th  class="OraTableRowHeader" noWrap  noWrap align=left  ></th>
	</tr>
	<tr>
		<td>购物金额满&nbsp;<html:text property="free_delivery_require" size="6"/>&nbsp;元免发送费</td>
		<td><input type=button value=" 设置 " onclick="f_set()"></td>
	</tr>	
</table>
</html:form>
</body>
</html>
