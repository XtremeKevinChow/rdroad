<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.form.ProductTypeForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">

function item_code_change() {
    document.forms[0].action = "memberAddGift.do?type=addInit";
    document.forms[0].submit();
}

function submit_f(btn) {
	var obj = document.forms[0];
	if(obj.cardID == null || obj.cardID.value.length == 0) {
		alert("会员号不能为空!");
		obj.cardID.focus();
		return;
	}

	if(obj.gift_number == null || obj.gift_number.value.length == 0) {
		alert("礼券号不能为空!");
		return;
	}



	btn.disabled = true;
	obj.submit();
}

function load_f() {
	if(document.forms[0].cardID.value == null || document.forms[0].cardID.value.length == 0) { //会员号空
		document.forms[0].cardID.readOnly = false;
		//document.forms[0].cardID.focus();
	} else {
		document.forms[0].cardID.readOnly = true;
	}
	
}

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	
}

function getOpenwinValue(ret){
	document.forms[0].itemCode.value = ret;
	item_code_change();
}

</script>
</head>


<body  text="#000000" leftmargin="0" topmargin="0"  onload="load_f()">

<!-- <br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td> <font color="#838383"><b>会员管理 -&gt; 待发礼品新增</b></font></td>
   </tr>
</table> -->
<html:form action="/memberAddGift.do?type=add2" method="post" >
<!-- <table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>增加待发礼品</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table> -->
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	<tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp; 会员号</td>
		<td align="left" width="80%" nowarp><html:text property="cardID"/>
		</td>
		
	</tr>

	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap><font color=red>*</font>&nbsp;礼券号</td>
      <td align="left" width="80%" nowarp>
        <html:text property="gift_number"/>
      </td>
	</tr>
	<tr>
	  <td align="right" class="OraTableRowHeader" noWrap>备注</td>
	  <td align="left" nowarp>
	  <html:textarea cols="30" rows="2" property="description" />
	  </td>
	  
    </tr>
  
</table>





<table width="90%" border="0" cellspacing="1" cellpadding="5" >
	<tr>
	 <td nowrap align="center">
		<input type="button" class="button2" value=" 保存 " name="save"  onclick="submit_f(this)">
		<input type="button" class="button2" value=" 返回 " name="back"  onclick="window.history.back()">
		
  </tr>
</table>
</html:form>


</body>
