<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>选择送货信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function doCopy() {
	location.href = "deliveryInfoCopy.do?type=query&mb2Id=<bean:write name="deliveryInfoForm" property="mbId" format="#"/>&page="+ document.forms[0].page.value;
}
function doAdd() {
	location.href = "deliveryInfoAdd.do?mbId=<bean:write name="deliveryInfoForm" property="mbId" format="#"/>&page="+ document.forms[0].page.value;
}
function doUpdate() {
	var selId = getRadio("addressId");
	if(trim(selId) == "") {
		alert("请选择一条信息然后执行本操作");
	}
	location.href = "deliveryInfoUpdate.do?mbId=<bean:write name="deliveryInfoForm" property="mbId" format="#"/>&addressId=" + trim(selId) + "&page="+ document.forms[0].page.value;
}
function doSelect() {
    if (document.forms[0].page==null ||document.forms[0].page.value=="")	{
        self.close();
	    self.opener.changeAddress(getRadio("addressId"));
    } else {
	    self.close();
	    self.opener.changeAddress2(getRadio("addressId"));
	}
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<table width="600" border=0 cellspacing=1 cellpadding=5 align="center" >
  <tr> 
    <td> <span class="OraHeader"><b>客户服务->>新增订单->>选择送货信息</b></span> 
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif">
        <tr background="../images/headerlinepixel_onwhite.gif"> 
          <td height="1" width="100%" background="../images/headerlinepixel_onwhite.gif"><img src="../images/headerlinepixel_onwhite.gif" height="1" width="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<html:form action="/deliveryInfoList.do"> 
<html:hidden property="page"/>
<table width="600" cellspacing="0" border="0" align="center">
  <tr> 
    <td><b>会员基本信息</b></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr height="26"> 
    <td width="80" class="OraTableRowHeader" noWrap >会员号：</td>
    <td  bgcolor="#FFFFFF" width="120"><bean:write name="deliveryInfoForm" property="cardId"/></td>
    <td width="80" class="OraTableRowHeader" noWrap >会员姓名：</td>
    <td  bgcolor="#FFFFFF"><bean:write name="deliveryInfoForm" property="mbName"/></td>
  </tr>
  <tr height="26"> 
    <td colspan="4"><b>会员送货选择列表</b></td>
  </tr>
</table>
<table width="600" border="0" cellspacing="0" align="center">
  <tr> 
    <th width="26">选择</th>
    <th width="77">收件人姓名</th>
    <th width="75">联系电话</th>
    <th width="229">送货地址</th>
    <th width="60">邮政编码</th>
    <th width="54">送货方式</th>
    <th width="65">付款方式</th>
  </tr>
  <bean:define id="lstInfo" name="deliveryInfoForm" property="deliveryInfoList" type="java.util.Collection"/> 
  <logic:iterate name="lstInfo" id="info"> 
  <tr> 
    <td width="26"> <input type="radio" name='addressId' value='<bean:write name="info" property="addressId" format="#"/>'<logic:equal name="info" property="default" value="true"> 
      checked</logic:equal>> </td>
    <td width="77" nowrap>&nbsp;<bean:write name="info" property="receiptor"/></td>
    <td width="75" nowrap>&nbsp;<bean:write name="info" property="phone"/></td>
    <td width="229" nowrap>&nbsp;<bean:write name="info" property="address"/></td>
    <td width="60" nowrap>&nbsp;<bean:write name="info" property="postCode"/></td>
    <td width="54" nowrap>&nbsp;<bean:write name="info" property="deliveryType"/></td>
    <td width="65" nowrap>&nbsp;<bean:write name="info" property="paymentType"/></td>
  </tr>
  </logic:iterate> 
</table>
<br>
<table width="600" cellspacing="0" border="0" align="center">
  <tr> 
    <td align="right"> 
      <input type="button" name="BtnAdd" value=" 复 制 " onClick="doCopy();">
      <input type="button" name="BtnAdd" value=" 新 增 " onClick="doAdd();">
      <input type="button" name="BtnUpdate" value=" 修 改 " onClick="doUpdate();">
      <input type="button" name="BtnOk" value=" 确 定 " onclick="doSelect();">
    </td>
  </tr>
</table>
</html:form> 
<p>&nbsp;</p></body>
</html>
