<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script language="javascript">
//回调函数
function updatePage(response) {
 
   document.getElementById("MB_NAME").value = response;
}

//异步函数
function callAjax(obj) {
	var ajax=new Ajax("/magicAjax.do?type=getNameByCardId&cardId=" + escape(obj.value),"",this.updatePage);
	ajax.postRequest();
}

function f_checkForm() {
	/*
	if(document.forms[0].ORDER_CODE.value=="" && document.forms[0].MB_CODE.value=="") {
		alert("会员号和订单号不能同时为空");
		document.forms[0].ORDER_CODE.focus();
		return;
	}
	
	if (document.forms[0].MB_CODE.value!="")
	{
		if (document.forms[0].MB_NAME.value=="")
		{
			alert("您输入了会员号，姓名不能为空");
			return;
		}
	}
	*/
	if( isNaN(document.forms[0].MB_CODE.value)) {
		document.forms[0].MB_CODE.focus();
		alert("会员号必须为数字");
		return;
	}
	if( isNaN(document.forms[0].MONEY.value)) {
		document.forms[0].MONEY.focus();
		alert("金额必须为数字");
		return;
	}
	if( parseInt(document.forms[0].MONEY.value)<=0) {
		document.forms[0].MONEY.focus();
		alert("金额不能小于0");
		return;
	}
	if(document.forms[0].USE_TYPE[0].checked==false && document.forms[0].USE_TYPE[1].checked==false) {
		alert("请选择用途");
		return;
	}
	if(document.forms[0].payMethod[0].checked==false && document.forms[0].payMethod[1].checked==false) {
		alert("请选择支付方式");
		return;
	}
	if(document.forms[0].BILL_DATE.value=="") {
		alert("请选输入单据日期");
		return;
	}
	if (document.forms[0].payMethod[1].checked==true) //建行回单需要录入汇号
	{
		if (document.forms[0].REF_ID.value == "")
		{
			alert("建行回单需要提供汇号");
			document.forms[0].REF_ID.focus();
			return;
		}
	}
	document.forms[0].modBtn.disabled = true;
	document.forms[0].REF_ID.disabled = false;
	document.forms[0].submit();
}
function change_f(flag, status) {
	if (status == 0) //现金
	{
		if (flag)//checked
		{
			document.forms[0].REF_ID.disabled = true;
		}
	} else { //回单
		if (flag)
		{
			document.forms[0].REF_ID.disabled = false;
		}
	}
}
function load_f() {
	if (document.forms[0].payMethod[0].checked)
	{
		document.forms[0].REF_ID.disabled = true;
	}
	if (document.forms[0].payMethod[1].checked)
	{
		document.forms[0].REF_ID.disabled = false;
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f()">
<br>
<table width="95%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">手工修改</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form action="memberaddMoneyManage.do?type=modify" >
<!-- 查询条件 -->
<html:hidden property="ID"/>
<html:hidden property="CREATE_DATE"/>
<table width="95%" border=0 cellspacing=1 cellpadding=5 align="center">
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap >&nbsp;汇号</td>
	  <td align="left" width="40%" nowarp >
        <html:text property="REF_ID"/>
	  </td>
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap >&nbsp;订单号</td>
	  <td align="left" width="40%" nowarp >
        <html:text property="ORDER_CODE" />&nbsp;&nbsp;
	  </td>
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap >&nbsp;会员号</td>
	  <td align="left" width="40%" nowarp>
        <html:text property="MB_CODE" onchange="callAjax(this);"/>
	  </td>
	</tr>
	<tr>
	  <td width="20%" align="right" class="OraTableRowHeader" noWrap >&nbsp;会员姓名</td>
	  <td align="left" width="40%" nowarp>
        <html:text property="MB_NAME" />
	  </td>
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;金额</td>
      <td align="left" width="40%" nowarp >
		<html:text property="MONEY"/>  
      </td>
	  <td width="20%" class=OraTipText align="left"></td>
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;用途</td>
      <td align="left" width="40%" nowarp >
		预存款<html:radio property="USE_TYPE" value="0" /> 
		购买书香卡<html:radio property="USE_TYPE" value="1" />
      </td>
	  <td width="20%" class=OraTipText align="left"></td>
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;付款方式</td>
      <td align="left" width="70%" nowarp >
		现金<html:radio property="payMethod" value="14" onclick="change_f(this.checked,0)"/> 
		建行回单<html:radio property="payMethod" value="90" onclick="change_f(this.checked,1)"/>
      </td>
	  <td width="20%" class=OraTipText align="left"></td>
	</tr>
    <tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;单据日期</td>
      <td align="left" width="40%" nowarp >
		<html:text property="BILL_DATE"  readonly="true"/><a href="javascript:calendar(document.forms[0].BILL_DATE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>  
      </td>
	  <td width="20%" class=OraTipText align="left"></td>
	</tr>
    <tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap >&nbsp;备注</td>
      <td align="left" width="70%" nowarp >
		<html:textarea property="REMARK" cols="30" rows="3"/>
	  <td width="20%" class=OraTipText align="left"></td>
	</tr>
    
    <tr>
      <td align="center" colspan=2>
        <input type="button" name="modBtn" value=" 保存 "  onclick="f_checkForm()">&nbsp;&nbsp;
		<input type="button"  value=" 返回 "  onclick="history.back()">
	  </td>
    </tr>
  </table>
	
</html:form>
</body>
</html>