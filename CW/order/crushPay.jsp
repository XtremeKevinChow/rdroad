<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="init()">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">充值卡充值</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form action="orderPay.do?type=submitPayCrushCard" onsubmit="return f_checkForm();">
<table width="750.0" border=0 cellspacing=1 cellpadding=5>

	<tr>
      <td width="30%" align="right" ><font color=red>*</font>&nbsp;会员号</td>
			<td align="left" width="40%" nowarp>
        <html:text property="cardId"/>
	</td>

	</tr>	
		<tr>
      <td width="30%" align="right" ><font color=red>*</font>&nbsp;充值卡密码</td>
      <td align="left" width="40%" nowarp>
			  <html:text property="remark" size="30" />
			  
      </td>
			<td width="30%" class=OraTipText align="left"></td>
	</tr>
    <tr>
      <td align="center" colspan="3">
        <input type="submit" class="button2" value="提交" >&nbsp;
        <!--<input type="button" class="button2" value="取消" onClick="history.back();">-->
	    </td>
    </tr>
  </table>
	
</html:form>
</body>
</html>
<script lanuage=javascript>
function f_checkForm() {
	
    if(document.forms[0].cardId.value =="") {
        alert("会员号不能为空");
        return false;
    }

	if(document.fors[0].remark.value =="") {
		alert("密码不能为空");
		return false;
	}
	
	return true;
}
function f_checkOrderNumber() {
	var regex = /^\w?\d{12}$/;
	return regex.exec(document.forms[0].orderNumber.value);
}
function f_checkMbMoney() {
	var regex = /^\d{1,6}(\.\d{1,2})?$/;
	return regex.exec(document.forms[0].mbMoney.value);
}
function f_checkDate() {
	var regex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
	return regex.exec(document.forms[0].createDate.value);
}
function f_checkRemark() {
	var regex = /^[\u4e00-\u9fa5,a-z,A-Z,\,0-9\.\，\。\,]*$/
	return regex.exec(document.forms[0].remark.value);
}

</script>