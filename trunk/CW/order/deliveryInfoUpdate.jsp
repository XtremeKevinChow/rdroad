<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>修改送货信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script language="JavaScript">

function listCity() {
    if(document.forms[0].province.value=="") {
        alert("请选择省份");
        document.forms[0].province.focus();
        return;
    }
    var ajax = new Ajax("/magicAjax.do?type=getCitysByProvince&province=" + document.forms[0].province.value,"",this.showCity);
	ajax.postRequest();
}

function showCity(response) {
    //alert(response);
    clearOption(document.forms[0].city);
    
    var city;
    var arr = response.split(",");
    for(var i=0; i < arr.length; i++){
        var arrTemp = arr[i].split("-");
		addOption(document.forms[0].city, arrTemp[0], arrTemp[1]);
		if(i==0) {city = arrTemp[0]};
	}
	
	var ajax = new Ajax("/magicAjax.do?type=getSectionByCity&city=" + city,"",this.showSection);
	ajax.postRequest();
}
function listSection() {
    if(document.forms[0].city.value=="") {
        alert("请选择城市");
        document.forms[0].city.focus();
        return;
    }
    var ajax = new Ajax("/magicAjax.do?type=getSectionByCity&city=" + document.forms[0].city.value,"",this.showSection);
	ajax.postRequest();
}

function showSection(response) {
    //alert(response);
    clearOption(document.forms[0].section);
    
    var arr = response.split(",");
    for(var i=0; i < arr.length; i++){
		var arrTemp = arr[i].split("-");
		addOption(document.forms[0].section, arrTemp[0], arrTemp[1]);
	}
}
function changeDeliveryType() {
	document.forms[0].actionType.value = "changeDelivery";
	document.forms[0].submit();
}
function changePaymentType() {
	document.forms[0].actionType.value = "changePayment";
	document.forms[0].submit();
}
function validate(theForm) {
	if(trim(theForm.receiptor.value) == "") {
		alert("请输入收件人姓名！");
		theForm.receiptor.focus();
		return false;
	}
	if(trim(theForm.phone.value) == "") {
		alert("请输入联系电话！");
		theForm.phone.focus();
		return false;
	}
	if(trim(theForm.address.value) == "") {
		alert("请输入联系地址！");
		theForm.address.focus();
		return false;
	}
	if(trim(theForm.postCode.value) == "") {
		alert("请输入邮政编码！");
		theForm.postCode.focus();
		return false;
	}
	
	return true;
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<table width="600" border=0 cellspacing=1 cellpadding=5 align="center" >
  <tr> 
    <td> <span class="OraHeader"><b>客户服务->>新增订单->>添加送货信息</b></span> 
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="../images/headerlinepixel_onwhite.gif">
        <tr background="../images/headerlinepixel_onwhite.gif"> 
          <td height="1" width="100%" background="../images/headerlinepixel_onwhite.gif"><img src="../images/headerlinepixel_onwhite.gif" height="1" width="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<html:form action="/deliveryInfoUpdate.do" onsubmit="return validate(this)"> 
<html:hidden property="page"/>
<table width="600" cellspacing="0" border="0" align="center">
  <tr> 
    <td colspan="4"><b>会员基本信息</b></td>
  </tr>
  <tr height="26" class="OraTableRowHeader" noWrap > 
    <td width="80" class="OraTableRowHeader" noWrap >会员号：</td>
    <td  bgcolor="#FFFFFF" width="123"><bean:write name="deliveryInfoForm" property="cardId"/><html:hidden name="deliveryInfoForm" property="mbId"/></td>
    <td width="80">会员姓名：</td>
    <td  bgcolor="#FFFFFF" width="309"><bean:write name="deliveryInfoForm" property="mbName"/></td>
  </tr>
  <tr> 
    <td colspan="4"><b>会员送货信息</b></td>
  </tr>
  <tr> 
    <td width="80" class="OraTableRowHeader" noWrap >收件人姓名：</td>
    <td colspan="3"> 
      <html:text name="deliveryInfoForm" property="receiptor" maxlength="20"/>
      <font color="#FF0000"> *</font> </td>
  </tr>
  <tr> 
    <td width="80" class="OraTableRowHeader" noWrap >联系电话：</td>
    <td colspan="3"> 
      <html:text name="deliveryInfoForm" property="phone" maxlength="20"/>
      <font color="#FF0000">*</font> </td>
  </tr>
  <tr> 
    <td width="80" class="OraTableRowHeader" noWrap >联系电话2(可写多个)：</td>
    <td colspan="3"> 
      <html:text name="deliveryInfoForm" property="phone2" style="width:200" maxlength="30"/>
      </td>
  </tr>
	<tr> 
    <td width="80" class="OraTableRowHeader" noWrap >区域：</td>
    <td colspan="3"> 
      <html:select property="province" onchange="listCity();">
		    <option value="">请选择</option>
			<html:optionsCollection property="provs" />
		</html:select>&nbsp;&nbsp;
		<html:select property="city" onchange="listSection();">
		    <option value="">请选择</option>
			<html:optionsCollection property="citys" />
		</html:select>&nbsp;&nbsp;
		<html:select property="section"  onchange="changeDeliveryType();">
		    <option value="">请选择</option>
			<html:optionsCollection property="sects"/>
		</html:select>&nbsp;<font color=red>*</font>
  </tr>
	<tr> 
    <td width="80" class="OraTableRowHeader" noWrap >邮政编码：</td>
    <td colspan="3"> 
      <html:text name="deliveryInfoForm" property="postCode" maxlength="6" onblur="changeDeliveryType();"/>
      <font color="#FF0000">*</font> </td>
  </tr>
  <tr> 
    <td width="80" class="OraTableRowHeader" noWrap >送货地址：</td>
    <td colspan="3"> 
      <html:text name="deliveryInfoForm" property="address" maxlength="100" size="70"/>
      <font color="#FF0000">*</font>
	  <html:hidden name="deliveryInfoForm" property="addressId"/></td>
  </tr>
  <tr> 
    <td width="80" class="OraTableRowHeader" noWrap >送货方式：</td>
    <td colspan="3">
      <html:select name="deliveryInfoForm" property="deliveryTypeId" style="width:122" onchange="changePaymentType();">
	  <option value="-1">-- 请选择 --</option>
	  <html:optionsCollection name="deliveryInfoForm" property="deliveryTypeList" value="id" label="name"/>
      </html:select>
    </td>
  </tr>
  <tr> 
    <td width="80" class="OraTableRowHeader" noWrap >付款方式：</td>
    <td colspan="3"> 
      <html:select name="deliveryInfoForm" property="paymentTypeId" style="width:122">
	  <option value="-1">-- 请选择 --</option>
	  <html:optionsCollection name="deliveryInfoForm" property="paymentTypeList" value="id" label="name"/>
      </html:select>
    </td>
  </tr>
  <tr> 
    <td width="120" class="OraTableRowHeader" noWrap >更新到会员地址：</td>
    <td colspan="3"> 
      <html:checkbox  property="isUpdate2MainAddress" value="1"/>
    </td>
  </tr>
  <tr> 
    <td width="120" class="OraTableRowHeader" noWrap >更新为默认地址：</td>
    <td colspan="3"> 
      <html:checkbox  property="isUpdate2DefaultAddress" value="1" />
    </td>
  </tr>
</table>
<input type="hidden" name="actionType" value="">
<br>
<table width="600" cellspacing="0" border="0" align="center">
  <tr> 
    <td align="center"> 
      <input type="submit" name="BtnSubmit" value=" 确 定 " >&nbsp;&nbsp;
      <input type="button" name="BtnCancel" value=" 取 消 " onclick="history.back();">
    </td>
  </tr>
</table>
</html:form>
</body>
</html>
