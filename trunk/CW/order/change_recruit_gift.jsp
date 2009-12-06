<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	String isAdd = (String)request.getAttribute("isAdd");
	String gpId = (String)request.getAttribute("gpId");
	String oldItemId = (String)request.getAttribute("oldItemId");
	String oldSectionType = (String)request.getAttribute("oldSectionType");
	
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<style>
hr {
        height: 1px;
        border: 0;
        border-bottom: 1px dashed #FFDB2C;
}
</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function queryItem(obj) {
	if (obj == null || obj.value.length != 6 || isNaN(obj.value))
	{
		obj.value = "";
		return;
	}
	var table = document.getElementById("detailTable");
	var priceListId = document.getElementsByName("priceListId");
	for (var i = 0; i < priceListId.length; i ++)
	{
		var cell = priceListId[i].parentElement.parentElement.cells(0);
		
		if (!priceListId[i].checked)
		{
			if (cell.innerText.indexOf(obj.value) != -1) // find ok
			{
			
				
				if (!priceListId[i].disabled)
				{
					priceListId[i].checked = true;
				}
				
				
				break;
			}
		}
		
	}
	obj.value = "";
	return;
}

function select_f(flag) {
	var arr = document.getElementsByName("priceListId");
	
	var priceListId;
	var flag = false;
	var len = arr.length;
	
	for (i = 0; i < len; i ++) {

		if (typeof(arr[i]) != "undefined")
		{
			if (arr[i].type=="radio" && arr[i].checked)
			{
				priceListId = arr[i].value;
				flag = true;
				break;
			}
		}
	}
	
	if (flag == true)
	{
		
		window.close();
		self.opener.change_recruit_gift_f("<%=isAdd%>", priceListId, "<%=oldItemId%>", "<%=oldSectionType%>");
		
		
	} else {
		alert("请选择记录!");
	}
}

function load() {
	var frm = document.forms[0];
	var arrGpId = document.getElementsByName("gpId");
	for (i = 0; i < arrGpId.length; i ++)
	{
		if (arrGpId[i].value == "<%=gpId%>")
		{
			arrGpId[i].checked = true;
		}
	}
	//checkAllGift();
	
/*
	var priceListId = document.getElementsByName("priceListId");
	for (i = 0;i < priceListId.length ; i ++)
	{
		countQty(priceListId[i]);
	}
	*/
	document.getElementsByName("queryItemCode")[0].focus();
}

function checkAllGift() {
	
	var priceListId = document.getElementsByName("priceListId");
	var sectionType = document.getElementsByName("sectionType");
	for (var i = 0; i < priceListId.length; i ++)
	{

		if (sectionType[i].value == "E") // 赠品
		{
			priceListId[i].checked = true;
			countQty(priceListId[i]);
		}
		
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load();">

<table width="500" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			当前位置 : 会员管理 -&gt; 更换招募礼品 </td>
   </tr>
</table>
<table width="90%" >
   
    <td colspan=>
      <hr />
    </td>
  </tr>
	<tr> 
    <td colspan=>
      查询产品：<input name="queryItemCode" maxlength="6" size="10" onkeydown=" if (event.keyCode == 13) queryItem(this); ">
		<input type="button" value=" 查询 " onclick=queryItem(document.getElementsByName("queryItemCode")[0])>
	</td>
  </tr>
  <tr> 
    <td colspan=>
      <hr />
    </td>
  </tr>
</table>

<html:form action="/orderAddSecond.do"  method="POST">
<input type="hidden" value="<%=isAdd%>" name="isAdd">
<input type="hidden" value="<%=oldItemId%>" name="oldItemId">
<input type="hidden" value="<%=oldSectionType%>" name="oldSectionType">
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" 
cellpadding="3">
<!-- groups -->

<tr> 
    <td colspan=100>
      <hr />
    </td>
  </tr>
</table>
<table width="90%" >
   <tr>
      <td></td><td align="right"><input type="button" value=" 选择 " name="selectBtn" onclick="select_f()"></td>
   </tr>
</table>
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<!-- groups -->
	<logic:iterate id="group" name="RECRUIT_GIFT" type="com.magic.crm.promotion.entity.GroupPrices"> 
	<!-- 打套产品 -->
	<bean:define id="product" name="group" property="product"/>
	<!-- 赠品区 -->
	<bean:define id="sectionList" name="group" property="giftSection" />
	

	<logic:equal name="group" property="selected" value="true"><!-- is selected -->
	<logic:iterate id="product" name="product" >
	<tr >
	    <td colspan="2" align="left">
		<input type="radio"  name="priceListId" value="<bean:write name="product" property="id"/>" <logic:equal name="product" property="disabled" value="1">disabled</logic:equal>>
		<input type="hidden" name="sectionType" value="D">
		<input type="hidden" name="itemId" value=<bean:write name="product" property="itemId"/>>
		<font color=<logic:notEqual name="product" property="stockStatusId" value="0">red</logic:notEqual>>
		<bean:write name="product" property="itemCode"/><bean:write name="product" property="itemName"/>(定价：<bean:write name="product" property="standardPrice" format="#0.00"/>元)(库存状态：<bean:write name="product" property="stockStatusName"/>)</font>
		</td>
	</tr>
	</logic:iterate> <!-- end product loop -->

	<logic:equal name="group" property="isGift" value="1"><!-- have gift Sections -->
	<logic:iterate id="sectionList" name="sectionList" > <!-- gift sections -->
	
	<tr>
	<td>&nbsp;&nbsp;礼品</td>
	</tr>
	<bean:define id="productsList" name="sectionList" property="productsList"/>
	<logic:iterate id="gift" name="productsList">
	<tr>
	    <td colspan="2" align="left">
		<input type="radio" name="priceListId" value=<bean:write name="gift" property="id"/> <logic:equal name="gift" property="disabled" value="1">disabled</logic:equal>>
		<input type="hidden" name="sectionType" value="E">
		<input type="hidden" name="itemId" value=<bean:write name="gift" property="itemId"/>>
		<bean:write name="gift" property="itemCode"/><bean:write name="gift" property="itemName"/>(加：<bean:write name="gift" property="price" format="#0.00"/>元)(库存状态：<bean:write name="gift" property="stockStatusName"/>)</td>
	</tr>
	</logic:iterate><!-- gifts -->
	</logic:iterate> <!-- end gift sections loop -->
	</logic:equal><!-- have gifts -->
	</logic:equal><!-- is selected -->
	
	</logic:iterate> 
</table>
<table width="90%" >
   <tr>
      <td></td><td align="right"><input type="button" value=" 选择 " name="selectBtn" onclick="select_f()"></td>
   </tr>
</table>
</html:form>
</body>
</html>