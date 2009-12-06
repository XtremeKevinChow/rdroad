<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	String isAdd = (String)request.getAttribute("isAdd");
	String gpId = (String)request.getAttribute("gpId");
	
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
function select_f(flag) {
	var arr = document.getElementsByName("gpId");

	var selectItem;
	var flag = false;
	var len = arr.length;
	
	for (i = 0; i < len; i ++) {

		if (typeof(arr[i]) != "undefined")
		{
			if (arr[i].type=="radio" && arr[i].checked)
			{
				selectItem = arr[i].value;
				flag = true;
				break;
			}
		}
	}
	
	if (flag == true)
	{
		
		window.close();
		// 判断是否符合规则
		if(!check_rules()) {
			return;
		}
		var ids = getPriceListIds();

		window.close();
		self.opener.select_recruit_gift_f("<%=isAdd%>", selectItem, ids);
		
		
	} else {
		alert("请选择记录!");
	}
}
//组装IDs
function getPriceListIds() {
	var obj = document.forms[0].priceListId;
	var ids = "";

	if (typeof(obj.length) == "undefined") // single
	{
		if (obj.checked)
		{
			ids = obj.value;
		}
		
	} else {
		for (i = 0; i < obj.length; i ++)
		{
			if (obj[i].checked)
			{
				ids = ids + obj[i].value;
				ids = ids + ",";
			}
			
		}
		ids = ids + "-1";
	}
	return ids;
}

function check_rules() {
	var obj = document.forms[0].priceListId;
	var obj2 = document.forms[0].sectionType;
	var productCnt = 0;
	var giftCnt = 0;
	if (typeof(obj.length) == "undefined") // single
	{
		if (obj.checked)
		{
			
			if (obj2.value=="D")
			{
				productCnt ++;
			}
			if (obj2.value == "E")
			{
				giftCnt ++;
			}
		}
		
	} else {
		for (i = 0; i < obj.length; i ++)
		{
			if (obj[i].checked)
			{
				if (obj2[i].value=="D")
				{
					productCnt ++;
				}
				if (obj2[i].value == "E")
				{
					giftCnt ++;
				}
			}
			
		}
		
	}
	//alert(productCnt+"*****"+giftCnt);
	
	var arr_gpId = document.getElementsByName("gpId");
	var arr_saleQty = document.getElementsByName("saleQty");
	var currSaleQty=0;
	var currMaxGoods = 0;
	var currMinGoods = 0;
	if (document.forms[0].maxGoods != null)
	{
		currMaxGoods=document.forms[0].maxGoods.value;
	}

	if (document.forms[0].minGoods != null)
	{
		currMinGoods=document.forms[0].minGoods.value;
	}
	
	
	if (arr_gpId.length > 0)
	{
		for (i = 0; i < arr_gpId.length; i ++)
		{
			if (arr_gpId[i].checked)
			{
				currSaleQty = arr_saleQty[i].value;
			}
		}
	}
	//alert(currSaleQty+"***"+currMaxGoods+"**"+currMinGoods);
	if (productCnt != currSaleQty)
	{
		alert("打套产品必须选"+parseInt(currSaleQty)+"个");
		return false;
	}
	if (giftCnt>currMaxGoods || giftCnt<currMinGoods)
	{
		alert("赠品不能超过"+parseInt(currMaxGoods)+"个，也不能小于"+parseInt(currMinGoods)+"个");
		return false;
	}
	return true;
}

function countQty(selectObj) {

	var productCnt = 0;
	var giftCnt  =   0;
	var obj = document.forms[0].priceListId;
	var obj2 = document.forms[0].sectionType;
	
	if (typeof(obj.length) == "undefined") // single
	{
		if (obj.checked)
		{
			
			if (obj2.value=="D")
			{
				productCnt = parseInt(document.getElementById("selectedProductCnt").innerText)+1;
				document.getElementById("selectedProductCnt").innerText = productCnt;
			}
			if (obj2.value == "E")
			{
				giftCnt = parseInt(document.getElementById("selectedGiftCnt").innerText)+1;
				document.getElementById("selectedGiftCnt").innerText = giftCnt;
			}
		} else {
			if (obj2.value=="D")
			{
				productCnt = parseInt(document.getElementById("selectedProductCnt").innerText)-1;
				document.getElementById("selectedProductCnt").innerText = productCnt;
				
			}
			if (obj2.value == "E")
			{
				giftCnt = parseInt(document.getElementById("selectedGiftCnt").innerText)-1;
				document.getElementById("selectedGiftCnt").innerText = giftCnt;
			}
		}
		
	} else {
		for (i = 0; i < obj.length; i ++)
		{
			if (obj[i].value == selectObj.value) //匹配的记录
			{
				
				if (obj[i].checked)
				{
					if (obj2[i].value=="D")
					{
						productCnt = parseInt(document.getElementById("selectedProductCnt").innerText)+1;
						document.getElementById("selectedProductCnt").innerText = productCnt;
						//如果是D区移动行
						move_Row(selectObj, 0);
					}
					if (obj2[i].value == "E")
					{
						
						giftCnt = parseInt(document.getElementById("selectedGiftCnt").innerText)+1;
						document.getElementById("selectedGiftCnt").innerText = giftCnt;
					}
				} else {
					if (obj2[i].value=="D")
					{
						productCnt = parseInt(document.getElementById("selectedProductCnt").innerText)-1;
						document.getElementById("selectedProductCnt").innerText = productCnt;
						//如果是D区移动行
						move_Row(selectObj, 1);
					}
					if (obj2[i].value == "E")
					{
						giftCnt = parseInt(document.getElementById("selectedGiftCnt").innerText)-1;
						document.getElementById("selectedGiftCnt").innerText = giftCnt;
					}
					
				}
				break;
			}
			
			
		}
		
	}
}

function move_Row(selectObj, type) {
	var trObj = selectObj.parentElement.parentElement;
	
	var tbl = document.getElementById("detailTable");
	
	if (type==0) //选中
	{
		tbl.moveRow(trObj.rowIndex,0);
		selectObj.checked = true;
	} else {//取消选择
	
		var giftTr = document.getElementById("giftTR");
		
		if (giftTr != null) //存在
		{
			tbl.moveRow(trObj.rowIndex,giftTr.rowIndex - 1);
		} else {
		
			tbl.moveRow(trObj.rowIndex,tbl.rows.length-1);
		}
	}
	
}

function selectGroup() {
	var frm = document.forms[0];
	frm.action = "orderAddSecond.do?type=selectRecruitGroup";
	frm.submit();
}

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
			
				priceListId[i].checked = true;
				countQty(priceListId[i]);

				break;
			}
		}
		
	}
	obj.value = "";
	return;
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
			当前位置 : 会员管理 -&gt; 选择招募礼品 </td>
   </tr>
</table>
<table width="90%" >
   <tr>
      <td><B>已选打套产品：<label id="selectedProductCnt">0</label>个&nbsp;已选赠品：<label id="selectedGiftCnt">0</label>个</b></td>
	  <td align="right">
	  <input type="button" value=" 选择 " name="selectBtn" onclick="select_f()">
	  </td>
   </tr>
   <tr> 
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
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" 
cellpadding="3">
<!-- groups -->

<tr>
<logic:iterate id="group" name="RECRUIT_GIFT" type="com.magic.crm.promotion.entity.GroupPrices"> 	
<td colspan="2" align="left"><input type="radio" name="gpId" value=<bean:write name="group" property="gpId"/> onclick="selectGroup();"><strong>支付<bean:write name="group" property="saleAmt" format="#0.00"/>元任选<bean:write name="group" property="saleQty" format="#0"/><input type="hidden" name="saleQty" value=<bean:write name="group" property="saleQty"/> >个礼品</strong></td>
</logic:iterate>
</tr>
<tr> 
    <td colspan=100>
      <hr />
    </td>
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
		<input type="checkbox" onclick="countQty(this);" name="priceListId" value="<bean:write name="product" property="id"/>" >
		<input type="hidden" name="sectionType" value="D">
		<font color=<logic:notEqual name="product" property="stockStatusId" value="0">red</logic:notEqual>>
		<bean:write name="product" property="itemCode"/><bean:write name="product" property="itemName"/>(定价：<bean:write name="product" property="standardPrice" format="#0.00"/>元)(库存状态：<bean:write name="product" property="stockStatusName"/>)</font>
		</td>
	</tr>
	</logic:iterate> <!-- end product loop -->

	<logic:equal name="group" property="isGift" value="1"><!-- have gift Sections -->
	<logic:iterate id="sectionList" name="sectionList" > <!-- gift sections -->
	
	<tr id="giftTR">
		<td>&nbsp;&nbsp;&nbsp;<B>赠品：您至少选<bean:write name="sectionList" property="minGoods"/>最多可选<bean:write name="sectionList" property="maxGoods"/></B>
		<input type="hidden" name="maxGoods" value=<bean:write name="sectionList" property="maxGoods"/>>
		<input type="hidden" name="minGoods" value=<bean:write name="sectionList" property="minGoods"/>>
		</td>
	</tr>
	<bean:define id="productsList" name="sectionList" property="productsList"/>
	<logic:iterate id="gift" name="productsList">
	<tr>
	    <td colspan="2" align="left">
		<input type="checkbox" onclick="countQty(this);" name="priceListId" value=<bean:write name="gift" property="id"/> >
		<input type="hidden" name="sectionType" value="E">
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