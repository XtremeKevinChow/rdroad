<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0" onload="document.forms[0].btnsearch.disabled;">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 发货单高级查询</font></td>
  </tr>
</table>
<html:form action="/snAQuery.do?type=query" onsubmit="return f_checkData();">

<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px" class="en" frame="box">
		<TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >发货单号</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <INPUT name=sn_code> </TD></TR>
		<TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >订单号</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <INPUT name=order_number> </TD></TR>
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >会员号码</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          	<INPUT name=cardid> </TD></TR>
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >会员姓名</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          	<INPUT name=member_name> </TD></TR>
		<TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >核货人</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          	<INPUT name=check_person> </TD></TR>
       <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >批号</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          	<INPUT size=8 name=lot> 
            </TD></TR>
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >邮编</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          	<INPUT size=8 name=postcode> 
            </TD></TR>    
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >发货单状态</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <html:select property="status"> 
          <OPTION value="" selected>请选择...</OPTION> 
            <html:optionsCollection name="snForm" property="statusList" value="id" label="name"/> 
              </html:select>
               </TD></TR>       
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >产生日期(YYYY-MM-DD)</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <INPUT id=release_date_from name=release_date_from>
          <a href="javascript:show_calendar(document.forms[0].release_date_from)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
          至<INPUT id=release_date_to name=release_date_to>
          <a href="javascript:show_calendar(document.forms[0].release_date_to)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
          </TD>
		</TR>

		<TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >打印日期(YYYY-MM-DD)</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <INPUT id=print_date_from name=print_date_from>
          <a href="javascript:show_calendar(document.forms[0].print_date_from)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
          至<INPUT id=print_date_to name=print_date_to>
          <a href="javascript:show_calendar(document.forms[0].print_date_to)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
          </TD>
		</TR>

        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >发送方式</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <html:select property="delivery_type"> 
          <OPTION value="" selected>请选择...</OPTION> 
            <html:optionsCollection name="snForm" property="deliveryList" value="id" label="name"/> 
            </html:select> 
            </TD></TR>
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >支付方式</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <html:select property="pay_type"> 
          <OPTION value="" selected>请选择...</OPTION> 
          <html:optionsCollection name="snForm" property="payList" value="id" label="name"/>     
              </html:select>
           </TD></TR>
        <TR>
          <TD align=right width="20%" class="OraTableRowHeader" noWrap >sku</TD>
          <TD align=left width="80%" nowarp>&nbsp;
          <INPUT name=itemCode> </TD></TR>
        <tr>
        	<td colspan=2 align="center"><input type=submit value=" 查询 " name="btnsearch"></td>
        </tr>
</table>
</html:form>
</body>
</html>
<script language=javascript>
function f_checkData() {
	if(!f_checkShippingNoticeNumber()) {
		alert("发货单号输入不正确");
		return false;
	} 
	if(!f_checkOrderNumber()) {
		alert("订单号输入不正确");
		return false;
	} 
	if(!f_checkMemberCode()) {
		alert("会员号输入不正确");
		return false;
	} 
	if(!f_checkMemberName()) {
		alert("会员名输入不正确");
		return false;
	} 
	if(!f_checkReleaseDate()) {
		alert("下单日期输入不正确");
		return false;
	} 
	if(!f_checkItemCode()) {
		alert("货号输入不正确");
		return false;
	}
	document.forms[0].btnsearch.disabled = true; 
	return true;
}
function f_checkShippingNoticeNumber() {
	var regex = /^(\w?\d{12})?$/;
	return regex.exec(document.forms[0].sn_code.value);
}
function f_checkOrderNumber() {
	var regex = /^(\w?\d{12})?$/;
	return regex.exec(document.forms[0].order_number.value);
}
function f_checkMemberCode() {
	var regex = /^(\w{8,10})?$/;
	return regex.exec(document.forms[0].cardid.value);
}
function f_checkMemberName() {
	var regex = /^([\u4e00-\u9fa5,a-z,A-Z]{2,10})?$/;
	return regex.exec(document.forms[0].member_name.value);
}

function f_checkGoodsSum() {
	var regex = /^((\d+)?(\.\d{0,1})?)?$/;
	return regex.exec(document.forms[0].goods_sum_from.value)&&regex.exec(document.forms[0].goods_sum_to.value);
}
function f_checkOrderSum() {
	var regex = /^((\d+)?(\.\d{0,1})?)?$/;
	return regex.exec(document.forms[0].order_sum_from.value)&&regex.exec(document.forms[0].order_sum_to.value);
}

function f_checkReleaseDate() {
	var regex = /^(\d{4}-\d{2}-\d{2})?$/;
	return regex.exec(document.forms[0].release_date_from.value)&&regex.exec(document.forms[0].release_date_to.value);
}
function f_checkItemCode() {
	var regex = /^(\w{6})?$/;
	return regex.exec(document.forms[0].itemCode.value);
}

</script>