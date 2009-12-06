<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>选择付款方式</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function doSelect() {	
	self.close();
	self.opener.changePayment(getRadio("id"));
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<table width="600" border=0 cellspacing=1 cellpadding=5 align="center" >
  <tr> 
    <td> <span class="OraHeader"><b>客户服务->>新增订单->>选择付款方式</b></span> 
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif">
        <tr background="../images/headerlinepixel_onwhite.gif"> 
          <td height="1" width="100%" background="../images/headerlinepixel_onwhite.gif"><img src="../images/headerlinepixel_onwhite.gif" height="1" width="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<html:form action="/paymentType.do"> 
<table width="600" cellspacing="0" border="0" align="center">
  <tr> 
    <td><b>会员基本信息</b></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr height="26" class="OraTableRowHeader" noWrap > 
    <td width="80" class="OraTableRowHeader" noWrap >会员号：</td>
    <td  bgcolor="#FFFFFF" width="120">&nbsp;<bean:write name="paymentTypeForm" property="cardId"/></td>
    <td width="80">会员姓名：</td>
    <td  bgcolor="#FFFFFF">&nbsp;<bean:write name="paymentTypeForm" property="mbName"/></td>
  </tr>
  <tr> 
    <td colspan="4"><b>会员送货地址</b></td>
  </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap >送货地址：</td>
    <td colspan="3">&nbsp;<bean:write name="paymentTypeForm" property="address"/><html:hidden name="paymentTypeForm" property="addressId"/></td>
  </tr>
  <tr> 
    <td class="OraTableRowHeader" noWrap >邮政编码：</td>
    <td colspan="3">&nbsp;<bean:write name="paymentTypeForm" property="postCode"/></td>
  </tr>
  <tr>
    <td class="OraTableRowHeader" noWrap >送货方式：</td>
    <td colspan="3">&nbsp;<bean:write name="paymentTypeForm" property="deliveryTypeName"/></td>
  </tr>
</table>
<table width="600" border="0" cellspacing="0" align="center">
  <tr> 
    <td width="20%">选择</td>
    <td>付款方式</td>
  </tr>
  <bean:define id="lst" name="paymentTypeForm" property="typeList" type="java.util.Collection"/> 
  <logic:iterate name="lst" id="item"> 
  <tr> 
    <td> <input type="radio" name='id' value='<bean:write name="item" property="id" format="#"/>'<logic:equal name="item" property="default" value="true"> 
      checked</logic:equal>> </td>
    <td><bean:write name="item" property="name"/></td>
  </tr>
  </logic:iterate> 
</table>
<table width="600" cellspacing="0" border="0" align="center">
  <tr> 
    <td align="right"> 
      <input type="button" name="BtnOk" value="确定" onclick="doSelect();">
    </td>
  </tr>
</table>
</html:form> 
<p>&nbsp;</p></body>
</html>
