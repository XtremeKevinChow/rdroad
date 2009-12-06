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
<title>������Ա��ϵ����ϵͳ</title>
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
		// �ж��Ƿ���Ϲ���
		if(!check_rules()) {
			return;
		}
		var ids = getPriceListIds();

		window.close();
		self.opener.select_recruit_gift_f("<%=isAdd%>", selectItem, ids);
		
		
	} else {
		alert("��ѡ���¼!");
	}
}
//��װIDs
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
		alert("���ײ�Ʒ����ѡ"+parseInt(currSaleQty)+"��");
		return false;
	}
	if (giftCnt>currMaxGoods || giftCnt<currMinGoods)
	{
		alert("��Ʒ���ܳ���"+parseInt(currMaxGoods)+"����Ҳ����С��"+parseInt(currMinGoods)+"��");
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
			if (obj[i].value == selectObj.value) //ƥ��ļ�¼
			{
				
				if (obj[i].checked)
				{
					if (obj2[i].value=="D")
					{
						productCnt = parseInt(document.getElementById("selectedProductCnt").innerText)+1;
						document.getElementById("selectedProductCnt").innerText = productCnt;
						//�����D���ƶ���
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
						//�����D���ƶ���
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
	
	if (type==0) //ѡ��
	{
		tbl.moveRow(trObj.rowIndex,0);
		selectObj.checked = true;
	} else {//ȡ��ѡ��
	
		var giftTr = document.getElementById("giftTR");
		
		if (giftTr != null) //����
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

		if (sectionType[i].value == "E") // ��Ʒ
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
			��ǰλ�� : ��Ա���� -&gt; ѡ����ļ��Ʒ </td>
   </tr>
</table>
<table width="90%" >
   <tr>
      <td><B>��ѡ���ײ�Ʒ��<label id="selectedProductCnt">0</label>��&nbsp;��ѡ��Ʒ��<label id="selectedGiftCnt">0</label>��</b></td>
	  <td align="right">
	  <input type="button" value=" ѡ�� " name="selectBtn" onclick="select_f()">
	  </td>
   </tr>
   <tr> 
    <td colspan=>
      <hr />
    </td>
  </tr>
	<tr> 
    <td colspan=>
      ��ѯ��Ʒ��<input name="queryItemCode" maxlength="6" size="10" onkeydown=" if (event.keyCode == 13) queryItem(this); ">
		<input type="button" value=" ��ѯ " onclick=queryItem(document.getElementsByName("queryItemCode")[0])>
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
<td colspan="2" align="left"><input type="radio" name="gpId" value=<bean:write name="group" property="gpId"/> onclick="selectGroup();"><strong>֧��<bean:write name="group" property="saleAmt" format="#0.00"/>Ԫ��ѡ<bean:write name="group" property="saleQty" format="#0"/><input type="hidden" name="saleQty" value=<bean:write name="group" property="saleQty"/> >����Ʒ</strong></td>
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
	<!-- ���ײ�Ʒ -->
	<bean:define id="product" name="group" property="product"/>
	<!-- ��Ʒ�� -->
	<bean:define id="sectionList" name="group" property="giftSection" />
	

	<logic:equal name="group" property="selected" value="true"><!-- is selected -->
	<logic:iterate id="product" name="product" >
	<tr >
	    <td colspan="2" align="left">
		<input type="checkbox" onclick="countQty(this);" name="priceListId" value="<bean:write name="product" property="id"/>" >
		<input type="hidden" name="sectionType" value="D">
		<font color=<logic:notEqual name="product" property="stockStatusId" value="0">red</logic:notEqual>>
		<bean:write name="product" property="itemCode"/><bean:write name="product" property="itemName"/>(���ۣ�<bean:write name="product" property="standardPrice" format="#0.00"/>Ԫ)(���״̬��<bean:write name="product" property="stockStatusName"/>)</font>
		</td>
	</tr>
	</logic:iterate> <!-- end product loop -->

	<logic:equal name="group" property="isGift" value="1"><!-- have gift Sections -->
	<logic:iterate id="sectionList" name="sectionList" > <!-- gift sections -->
	
	<tr id="giftTR">
		<td>&nbsp;&nbsp;&nbsp;<B>��Ʒ��������ѡ<bean:write name="sectionList" property="minGoods"/>����ѡ<bean:write name="sectionList" property="maxGoods"/></B>
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
		<bean:write name="gift" property="itemCode"/><bean:write name="gift" property="itemName"/>(�ӣ�<bean:write name="gift" property="price" format="#0.00"/>Ԫ)(���״̬��<bean:write name="gift" property="stockStatusName"/>)</td>
	</tr>
	</logic:iterate><!-- gifts -->
	</logic:iterate> <!-- end gift sections loop -->
	</logic:equal><!-- have gifts -->
	</logic:equal><!-- is selected -->
	
	</logic:iterate> 
</table>
<table width="90%" >
   <tr>
      <td></td><td align="right"><input type="button" value=" ѡ�� " name="selectBtn" onclick="select_f()"></td>
   </tr>
</table>
</html:form>
</body>
</html>