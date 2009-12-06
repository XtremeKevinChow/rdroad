<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	document.forms[0].queryItemCode.select();
}

function getOpenwinValue(ret){
	document.forms[0].queryItemCode.value = ret;
}

function f_check_itemCode() {
	var regex = /^\w{6,15}$/;
	return regex.exec(document.forms[0].queryItemCode.value);
}
function f_check_itemPrice() {
	var regex = /^\d{1,6}\.{0,1}\d{0,2}$/;
	return regex.exec(document.forms[0].queryItemPrice.value);
}
function f_check_itemQty() {
	var regex = /^\d{1,8}$/;
	return regex.exec(document.forms[0].queryItemQty.value);
}
function nextStep() {
	if(document.forms[0].itemQty == null) {
		alert("您的购物篮内没有任何商品，不能执行下一步操作！");
		return;
	} else {
	    for( var i=0; i<document.forms[0].sku_id.length;i++) {
	        if(document.forms[0].sku_id[i].value==0) {
	            alert("您的购物篮内存在尚未确定的商品，不能执行下一步操作！");
	            return;
	        }
	    }
	
	}
	
	document.forms[0].action = "groupOrderUpdate.do?type=updateSecond";
	document.forms[0].submit();
}

function addItem() {
	/*if(!f_check_itemCode()) {
		alert("请选择商品！");
		document.forms[0].queryItemCode.focus();
		return;
	}*/
	//if(!f_check_itemPrice()) {
		//alert("请输入正确的商品价格！");
		//document.forms[0].queryItemQty.focus();
		//return;
	//}
	
	if(!f_check_itemQty()) {
		alert("请输入正确的商品数量！");
		document.forms[0].queryItemQty.focus();
		return;
	}
	
	document.forms[0].action = "groupOrderUpdate.do?type=addItem";
	document.forms[0].actionType.value = "addItem";
	document.forms[0].submit();
}

function deleteItem(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "groupOrderUpdate.do?type=deleteItem";
		document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		document.forms[0].submit();
	}
}

function updateItem(nIndex) {
	document.forms[0].action = "groupOrderUpdate.do?type=updateItem";
	document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;
	document.forms[0].submit();
}


function clearItem() {
	if(confirm("您确定清空购物篮？")) {
		document.forms[0].action = "groupOrderUpdate.do?type=clearCart";
		document.forms[0].actionType.value = "clear";
		document.forms[0].submit();
	}
}

function refreshMoney() {
	var arrQty = document.forms[0].itemQty;
	var arrPrice = document.forms[0].itemPrice;
	var arrMoney = document.forms[0].all("itemMoney");
	var temp = 0;
	var total = 0;
	if(arrQty.length == null || arrQty.length == 1) {
		temp = roundMoney(arrQty.value * arrPrice.value);
		arrMoney.innerText = formatMoney(temp);
		total += temp;
	} else {
		for(var i = 0; i < arrQty.length; i++) {
			temp = roundMoney(arrQty[i].value * arrPrice[i].value);
			arrMoney[i].innerText = formatMoney(temp);
			total += temp;
		}
	}
	
	document.forms[0].all("totalMoney").innerText = " " + formatMoney(roundMoney(total));
}

function addGifts() {
	document.forms[0].action = "orderModifyFirst.do";
	document.forms[0].actionType.value = "addGift";
	document.forms[0].submit();
}
function deleteGift(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "orderModifyFirst.do";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		document.forms[0].submit();
	}
}


function changeDiscount() {
	if (document.forms[0].discount == null || document.forms[0].discount.value == "")
	{
		alert("折扣不能为空");
		document.forms[0].discount.focus();
		return;
	}
	if (isNaN(document.forms[0].discount.value))
	{
		alert("折扣必须为数字");
		document.forms[0].discount.focus();
		return;
	}
	if (parseFloat(document.forms[0].discount.value) > 1 || parseFloat(document.forms[0].discount.value) <= 0 )
	{
		alert("折扣必须是0到1之间");
		document.forms[0].discount.focus();
		return;
	}

	document.forms[0].action = "groupOrderUpdate.do?type=changeDiscount";

	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<bean:define id="orgCart" name="orderForm" property="orgCart"/>
<bean:define id="orgMember" name="orgCart" property="orgMember"/>
<bean:define id="orgOrder" name="orgCart" property="orgOrder"/>
<bean:define id="deliveryInfo" name="orgCart" property="deliveryInfo"/>
<bean:define id="otherInfo" name="orgCart" property="otherInfo"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

<table width="100%" border="0" cellspacing="0" cellpadding="0" onload="document.forms[0].btnsearch.disabled;">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 修改团体订单</font></td>
  </tr>
</table>

<html:form action="/orderModifyFirst.do"> 
<table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td><b>订单编号：</b><bean:write name="orgOrder" property="orderNumber"/>
	<input type="hidden" name="orderNumber" value="<bean:write name="orgOrder" property="orderNumber"/>">
	<input type="hidden" name="orderId" value="<bean:write name="orgOrder" property="orderId" format="#0"/>"></td>
  </tr>
  <tr> 
    <td><b>订单来源：</b><bean:write name="orgOrder" property="prTypeName"/>
	<html:hidden name="orgOrder" property="prTypeName"/></td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td><b>会员基本信息</b></td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap  >
  
  <tr height="26" > 
    <td >会员号：</td>
    <td  bgcolor="#FFFFFF" ><bean:write name="orgMember" property="CARD_ID"/>
	<input type="hidden" name="mbId" value="<bean:write name="orgMember" property="ID" format="#0"/>"></td>
    <td >会员姓名：</td>
    <td  bgcolor="#FFFFFF"><bean:write name="orgMember" property="NAME"/></td>
    
   </tr>
  
</table>
<br>
<table width="95%" align="center" cellspacing="0" border="0"  >
  <tr> 
    <td>选择商品: 
      <input type="text" name="queryItemCode" size="18" onKeyDown="if(event.keyCode == 13) addItem();">
      <a href="javascript:getProduct();">
        <img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
      </a>
       
     <!--  价格:<input type=text name="queryItemPrice" size="8" >(元)&nbsp;&nbsp; -->
      <!--
      数量:
      <input name="queryItemQty" size="4" onBlur="checkPositiveInteger(this);" value="1" maxlength="3"  onKeyDown="if(event.keyCode == 13) addItem();">
      -->
      <input type=hidden name="queryItemQty" value="1">
      &nbsp; 
      <input name="BtnAddItem" type="button" value=" 确定 " onClick="addItem();">
    </td><td><font color=red>
		<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
		</font>
		</td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
  <tr class="OraTableRowHeader" noWrap  height="26"> 
    <td align="center"><b>货号</b></td>
    <td align="center"><b>名称</b></td>
	<td align="center"><b>库存数</b></td>
    <td align="center"><b>销售方式</b></td>
    <td align="center"><b>库存状态</b></td>
    <td align="center"><b>颜色</b></td>
    <td align="center"><b>尺码</b></td>
    <td align="center"><b>数量</b></td>
    <td align="center"><b>成本价</b></td>
	<td align="center"><b>市场价</b></td>
    <td align="center"><b>会员价</b></td>
	<td align="center"><b>VIP价</b></td>
	<td align="center"><b>团购价</b></td>
    <!-- <td align="center"><b>到货日期</b></td> -->
    <td align="center"><b>合计</b></td>
    <td align="center"><B>操作</B></td>
  </tr>
  <%int data_index =0;%>
  <bean:define id="items" name="orgCart" property="items" type="java.util.List"/> 
  <logic:iterate name="items" id="item"> 
  <tr 
  <logic:equal name="item" property="sku_id" value="0">
  bgcolor="yellow"   
  </logic:equal>
  > 
    <td align="center">&nbsp;<bean:write name="item" property="itemCode"/> 
    <logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
      </logic:notEmpty> 
      <html:hidden name="item" property="sku_id"/> </td>
    <td align="center">&nbsp;<bean:write name="item" property="itemName" ignore="true"/></td>
	<td align="center">&nbsp;<bean:write name="item" property="availQty" ignore="true" format="#0"/></td>
    <td align="center">&nbsp;<bean:write name="item" property="sellTypeName" ignore="true"/></td>
    <td align="center">&nbsp;<bean:write name="item" property="stockStatusName" ignore="true"/></td>
    <td>
    <html:select name="item" property="color_code">
    <html:optionsCollection  name="item" property="colors" />
    </html:select>
    </td>
    <td>
    <html:select name="item" property="size_code">
    <html:optionsCollection  name="item" property="sizes" />
    </html:select>
    </td>
    <td align="center"> <html:text name="item" property="itemQty" size="4" maxlength="3" onblur="checkPositiveInteger(this);"/> 
    </td>
    <td align="center">&nbsp;<bean:write name="item" property="purchaseingCost" format="#0.00" ignore="true"/></td>
	<td align="center">&nbsp;<bean:write name="item" property="standardPrice" format="#0.00" ignore="true"/> 
	<td align="center">&nbsp;<bean:write name="item" property="silverPrice" format="#0.00" ignore="true"/> 
	<td align="center">&nbsp;<bean:write name="item" property="goldenPrice" format="#0.00" ignore="true"/> 
    <td align="center">&nbsp;<bean:write name="item" property="discountPrice" format="#0.00" ignore="true"/> 
      <html:hidden name="item" property="itemPrice"/> </td>
    
    <td align="center" id="itemMoney">&nbsp;<bean:write name="item" property="groupItemMomey" format="#0.00" ignore="true"/></td>
    <td> 
      <input name="BtnDelete" value="删除" type="button" onclick="deleteItem(<%=data_index%>);">
      <input name="BtnUdate" value="确定" type="button" onclick="updateItem(<%=data_index%>);">
    </td>
  </tr>
  <% data_index++; %>
  </logic:iterate>

  <tr > 
	<td colspan="10">总计：</td>
	<td colspan="3">参考总额:
	
	<logic:notEqual name="orgCart" property="groupTotalMoney" value="0.0">
	    <bean:write name="orgCart" property="groupTotalMoney" format="#0.00" ignore="true"/></td>
	</logic:notEqual>
    <td colspan="2" id="totalMoney">&nbsp;
	实际总额:
	<bean:write name="orgCart" property="totalMoney" format="#0.00" ignore="true"/>
	</td>
  </tr>
  <tr>
	<td colspan="13">折扣：</td>
    <td colspan="2" id="totalMoney">&nbsp;<html:text name="orderForm" property="discount" size="4"/>&nbsp;&nbsp;
	<input name="" type="button" value="更新" onClick="changeDiscount();">
	</td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td height="10"></td>
  </tr>
  <tr> 
    <td align="center"> 
      <input name="BtnClearForm" type="button" value="清空购物篮" onclick="clearItem();">
      <input name="BtnNextStep" type="button" value="下一步 &gt;&gt;" onclick="nextStep();">
    </td>
  </tr>
</table>
<input type="hidden" name="actionType" value="">
<input type="hidden" name="operateId" value="-1">
<br>
</html:form> 
</body>
</html>
