<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="com.magic.utils.Arith,com.magic.crm.util.Config"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
Member member=new Member();
member=(Member)request.getAttribute("member");
String f_phone="";
String c_phone="";
String phone="";

System.out.println("taobao is "+ member.getTaobaoWangId());


if(member.getTELEPHONE()!=null&&!member.getTELEPHONE().equals("")&&member.getTELEPHONE().length()>=8){
	phone=CheckStr.checkPhone(member.getTELEPHONE(),member.getPostcode());
//System.out.println("p is "+phone);
}
if(member.getFAMILY_PHONE()!=null&&!member.getFAMILY_PHONE().equals("")&&member.getFAMILY_PHONE().length()>=8){
	f_phone=CheckStr.checkPhone(member.getFAMILY_PHONE(),member.getPostcode());
//System.out.println("f is "+f_phone);
}
if(member.getCOMPANY_PHONE()!=null&&!member.getCOMPANY_PHONE().equals("")&&member.getCOMPANY_PHONE().length()>=8){
	c_phone=CheckStr.checkPhone(member.getCOMPANY_PHONE(),member.getPostcode());
//System.out.println("c is "+c_phone);
}						
				
String isCallCenter = request.getParameter("iscallcenter");
String recommended_id = request.getParameter("recommended_id");
recommended_id = (recommended_id==null)?"0":"1";

String defaultTab = (String)request.getAttribute("DEFAULT_TAB");
defaultTab = (defaultTab==null)?"0":defaultTab;

String currentTel =(String) session.getAttribute("currentPhone");

String po_url = Config.getValue("ERP_SKU_PO_URL");
%>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<link href="../css/ajaxtabs.css" rel="stylesheet" type="text/css" />
<!-- <script language="vbscript" src="../script/changeCoding.vbs"></script> -->
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script src="../script/ajaxtabs.js" type="text/javascript"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">

///////////////////////////////////////////////////对应换货单页面的函数
function changeItem_C(nIndex) {
		document.forms[0].action = "../order/orderChange.do?type=changeItem";
		document.forms[0].operateId.value = nIndex;
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);

}
function returnItem_C(nIndex) {
	document.forms[0].action = "../order/orderChange.do?type=returnItem";
	document.forms[0].operateId.value = nIndex;
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}
function deleteItem1_C(nIndex) {
	//if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderChange.do?type=deleteItem1";
		//document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].submit();
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	//}
}
function deleteItem2_C(nIndex) {
	//if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderChange.do?type=deleteItem2"
		//document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].submit();
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	//}
}
function updateItem_C(nIndex) {
	document.forms[0].action = "../order/orderChange.do?type=updateItem";
	//document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;
	
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}
//////////////////////////////////////////////////


function pop_poinfo(sku_id) {
    openWin("<%=po_url%>?sku="+ sku_id,"wins",700,400);
}
function goto_chinapay() {
    //alert(document.forms[0].MerId.value);
    //alert(document.forms[0].OrdId.value);
    //alert(document.forms[0].TransAmt.value);
    //alert(document.forms[0].CuryId.value);
    //alert(document.forms[0].TransDate.value);
    //alert(document.forms[0].TransType.value);
    //alert(document.forms[0].ChkValue.value);
    document.forms[0].action= "http://payment.chinapay.com:8081/pay/TransGet" ;
    document.forms[0].method= "post";
    document.forms[0].target= "_blank";
    document.forms[0].submit();
}
function f_changePackage(price) {
    document.getElementById("packageFee").innerHTML = price.toFixed(2);
    document.getElementById("payable").innerHTML = Number(Number(document.forms[0].payable.value) + price).toFixed(2);
    if( Number(document.getElementById("payable").innerHTML) > Number(document.forms[0].usefulMoney.value) ) {
        
        document.getElementById("orderUse").innerHTML = Number(document.forms[0].usefulMoney.value).toFixed(2);
        document.getElementById("orderOwe").innerHTML = Number(Number(document.getElementById("payable").innerHTML) -  Number(document.forms[0].usefulMoney.value)).toFixed(2);
    } else {
        
        document.getElementById("orderUse").innerHTML =  Number(document.getElementById("payable").innerHTML).toFixed(2);
        document.getElementById("orderOwe").innerHTML = "0.00";
    }
    
    

}

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init4order","wins",700,400);
	
}

function getOpenwinValue(ret){
    document.forms[0].queryItemCode.value = ret;
}


function getOpenwinMemberValue(ret) {
     document.forms[0].MemgetmemID.value = ret;
}

function getGiftNumber(para){
	var owin = openWin("../member/mbrGetAward.do?type=qryActiveGiftNumber&cardID=<%=member.getCARD_ID()%>","wins",700,400);
	
}
function getOpenwinGiftNumber(ret){
    document.forms[0].otherGiftNumber.value = ret;
}

/////////////////////////////orderAdd2.jsp begin///////////////////////////
/**
function priorStep() {
	var url="../order/orderAddFirst.do?cardId="+document.forms[0].cardId.value+"&prTypeId="+document.forms[0].prTypeId.value;
	ajaxpage(url, "ajaxcontentarea", "str") ;
}
*/
function nextStep() {
	if(document.forms[0].msc.value == "") {
		alert("请选择订单对应的MSC");
		return;
	}
	var skus = document.getElementsByName("sku_id");
	if(skus.length==0) {
	    alert("您的购物篮内没有任何商品，不能执行下一步操作！");
		return;
	}
    for( var i=0;i<skus.length;i++) {
    if (skus[i].value==0) {
        alert("您的购物篮内存在没有确定的商品，不能执行下一步操作！");
        return;
    }
    }
	
	var x = document.getElementById("promGiftTbl").getElementsByTagName("INPUT");
	var sel = false;
	var ext = false;
	var rd;
	for (var i=0;i<x.length;i++){ 
	    if(x[i].disabled == true && x[i].checked == true) {
           sel = true;
        }
        if(x[i].disabled == false && x[i].checked == false) {
           ext = true;
           rd = i;
        }
    }
    if(sel == false &&ext== true) {
        if(confirm("您还没有选择赠品，请选择是否需要赠品?")) {
            document.getElementById("promGiftTbl").focus();
            return;
        }
    }
	
	document.forms[0].action = "../order/orderAddThird.do";
	//document.forms[0].submit();
	var url= document.forms[0].action;
	url=url+"?mbId="+ document.forms[0].mbId.value;
	url=url+"&msc=" + document.forms[0].msc.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea", "str") ;
}

function addItem() {
	if(trim(document.forms[0].queryItemCode.value) == '') {
		alert("请选择商品！");
		document.forms[0].queryItemCode.focus();
		return;
	}
	var queryItemCode = parseInt( trim(document.forms[0].queryItemCode.value) );
	if(!isNaN(queryItemCode) && queryItemCode>=100000 && queryItemCode <=100010 ) {
		alert("会员卡不能直接购买！");
		document.forms[0].queryItemCode.focus();
		return;
	}
	if(document.forms[0].queryItemQty.value <= 0) {
		alert("请输入商品数量必须大于0！");
		document.forms[0].queryItemQty.focus();
		return;
	}
	if(parseInt(document.forms[0].queryItemQty.value) > parseInt(document.forms[0].maxqty.value) ) {
		alert("输入商品数量不能大于"+document.forms[0].maxqty.value);
		document.forms[0].queryItemQty.focus();
		return;
	}	
	document.forms[0].action = "../order/orderAddSecond.do?type=addItem";
	document.forms[0].actionType.value = "addItem";
	//document.forms[0].submit();
	
	var url= document.forms[0].action+"&queryItemCode="+document.forms[0].queryItemCode.value 
	url=url+"&queryItemQty="+ document.forms[0].queryItemQty.value;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&msc=" + document.forms[0].msc.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea", "str") 
}

function deleteItem(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderAddSecond.do?type=deleteItem";
		document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].submit();
		var url= document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		url=url+"&msc=" + document.forms[0].msc.value;
		//alert(url);
		ajaxpage(url, "ajaxcontentarea","str");
	}
}

function deleteGift2(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderAddSecond.do?type=deleteGift2";
		document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].submit();
		var url= document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		//url=url+"&msc=" + document.forms[0].msc.value;
		//alert(url);
		ajaxpage(url, "ajaxcontentarea","str");
	}
}

function deleteGift2_M(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderModifyFirst.do?type=deleteGift2";
		document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].submit();
		var url= document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		//url=url+"&msc=" + document.forms[0].msc.value;
		//alert(url);
		ajaxpage(url, "ajaxcontentarea","str");
	}
}
function deleteItem2(sectionType, itemId, sellTypeId) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderAddSecond.do?type=deleteItem";
		document.forms[0].actionType.value = "deleteItem";
		
		//document.forms[0].submit();
		var url= document.forms[0].action;
		url = url + "&delItemId=" + itemId;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		url=url+"&sellType="+ sellTypeId;
		url=url+"&sectionType="+ sectionType;
		ajaxpage(url, "ajaxcontentarea","str");
	}
}

function deleteTicket(del_code) {
	if(confirm("您确定删除？")) {
		
		document.forms[0].action = "../order/orderAddSecond.do?type=removeTicket&delTicket="+del_code;
		
		//document.forms[0].submit();
		var url= document.forms[0].action;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		url=url+"&msc=" + document.forms[0].msc.value;
		ajaxpage(url, "ajaxcontentarea","str");
	}
}

function updateItem2(sectionType, itemId, sellTypeId, obj) {
//alert(url);
	document.forms[0].action = "../order/orderAddSecond.do?type=updateItem";
	document.forms[0].actionType.value = "updateItem";
	
	//document.forms[0].submit();
	var url= document.forms[0].action;
	url = url + "&updItemId=" + itemId;
	url = url + "&updateItemQty=" + obj.value;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&sellType="+ sellTypeId;
	url=url+"&sectionType="+ sectionType;
	
	ajaxpage(url, "ajaxcontentarea","str");
}

function updateItem(nIndex, e) {
    
	document.forms[0].action = "../order/orderAddSecond.do?type=updateItem";
	document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;
	//document.forms[0].submit();
	var url= document.forms[0].action;
	url = url + "&operateId=" + document.forms[0].operateId.value;
	url = url + "&updateItemQty=" + e.parentElement.parentElement.cells[6].children[0].value;
	url = url + "&colorCode=" + e.parentElement.parentElement.cells[4].children[0].value;
	url = url + "&sizeCode=" + e.parentElement.parentElement.cells[5].children[0].value;
	
	//alert(url);
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&msc=" + document.forms[0].msc.value;
	ajaxpage(url, "ajaxcontentarea","str");
}

function updateGift2(nIndex, e) {
    
	document.forms[0].action = "../order/orderAddSecond.do?type=updateGift2";
	document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;
	//document.forms[0].submit();
	var url= document.forms[0].action;
	url = url + "&operateId=" + document.forms[0].operateId.value;
	url = url + "&updateItemQty=" + e.parentElement.parentElement.cells[6].children[0].value;
	url = url + "&colorCode=" + e.parentElement.parentElement.cells[4].children[0].value;
	url = url + "&sizeCode=" + e.parentElement.parentElement.cells[5].children[0].value;
	
	//alert(url);
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&msc=" + document.forms[0].msc.value;
	ajaxpage(url, "ajaxcontentarea","str");
}

function updateGift2_M(nIndex, e) {
    
	document.forms[0].action = "../order/orderModifyFirst.do?type=updateGift2";
	document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;
	//document.forms[0].submit();
	var url= document.forms[0].action;
	url = url + "&operateId=" + document.forms[0].operateId.value;
	url = url + "&updateItemQty=" + e.parentElement.parentElement.cells[6].children[0].value;
	url = url + "&colorCode=" + e.parentElement.parentElement.cells[4].children[0].value;
	url = url + "&sizeCode=" + e.parentElement.parentElement.cells[5].children[0].value;
	
	//alert(url);
	url=url+"&mbId="+ document.forms[0].mbId.value;
	//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	//url=url+"&msc=" + document.forms[0].msc.value;
	ajaxpage(url, "ajaxcontentarea","str");
}

function validateTicket() {
	document.forms[0].action = "../order/orderAddSecond.do?type=validTicket";
	document.forms[0].actionType.value = "validateTicket";
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&ticketNumber=" + document.forms[0].ticketNumber.value;
	url = url + "&ticketPassword=" + document.forms[0].ticketPassword.value;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	ajaxpage(url, "ajaxcontentarea","str");
}
function validateTicket2() {
	document.forms[0].action = "../order/orderAddSecond.do?type=validTicket2";
	document.forms[0].actionType.value = "validTicket2";
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&otherGiftNumber=" + document.forms[0].otherGiftNumber.value;
	//url = url + "&otherGiftPassword=" + document.forms[0].otherGiftPassword.value;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&msc=" + document.forms[0].msc.value;
	ajaxpage(url, "ajaxcontentarea","str");
}

function clearItem() {
	if(confirm("您确定清空购物篮？")) {
		document.forms[0].action = "../order/orderAddSecond.do?type=clearItem";
		document.forms[0].actionType.value = "clear";
		//document.forms[0].submit();
		
		var url = document.forms[0].action;
		url=url+"&msc=" + document.forms[0].msc.value;
		ajaxpage(url, "ajaxcontentarea","str");
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
	document.forms[0].action = "../order/orderAddSecond.do?type=addGift";
	document.forms[0].actionType.value = "addGift";
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	//拼装giftCode
	var giftRadios = promGiftTbl.getElementsByTagName("INPUT");
	//alert(giftRadios.length);
	if (giftRadios.length > 0)
	{
	
		for (var i=0; i<giftRadios.length; i++)
		{
			
			//if (giftRadios[i].type == "radio" && giftRadios[i].disabled == false && giftRadios[i].checked == true)
			if ( giftRadios[i].disabled == false && giftRadios[i].checked == true)
			{
				//alert(giftRadios[i].name);
				url=url+"&"+giftRadios[i].name+"="+giftRadios[i].value;
				//break;

			}
		}
	}
	url=url+"&msc=" + document.forms[0].msc.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}

function addGifts2(award_id) {
	document.forms[0].action = "../order/orderAddSecond.do?type=addGift2";
	document.forms[0].actionType.value = "addGift2";
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&award_id="+award_id;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}

function addGifts2_M(award_id) {
	document.forms[0].action = "../order/orderModifyFirst.do?type=addGift2";
	//document.forms[0].actionType.value = "addGift2";
	//document.forms[0].submit();
	var url = document.forms[0].action;
	//url=url+"&mbId="+ document.forms[0].mbId.value;
	//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	url=url+"&award_id="+award_id;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}

function deleteGift(nIndex,sell_type) {
    if(sell_type==4 || confirm("选择“删除”后系统只是从本次订单中删除礼品，该礼品会继续保留在您的暂存架。\n如果您以后也不需要这个礼品，请选则“永久删除”。\n您确定“删除”吗？")) {
		document.forms[0].action = "../order/orderAddSecond.do?type=deleteGift";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].sellTypeId.value = sellType;
		//document.forms[0].submit();
		var url = document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		//url = url + "&sellTypeId=" + document.forms[0].sellTypeId.value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		url=url+"&msc=" + document.forms[0].msc.value;
		ajaxpage(url, "ajaxcontentarea","str");
	}
} 


function comboutGift(nIndex) {
	if(confirm("选择“永久删除”后我们系统不再保留该礼品，\n如果您希望在以后的订单中拿这个礼品，请选择“删除”礼品。\n您确定“永久删除”吗？")) {
		document.forms[0].action = "../order/orderAddSecond.do?type=deleteGift&delType=combout";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].sellTypeId.value = sellType;
		//document.forms[0].submit();
		var url = document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		//url = url + "&sellTypeId=" + document.forms[0].sellTypeId.value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		url=url+"&msc=" + document.forms[0].msc.value;
		ajaxpage(url, "ajaxcontentarea","str");
	}
}   

function updateGift(nIndex,e) {
	    document.forms[0].action = "../order/orderAddSecond.do?type=updateGift";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].sellTypeId.value = sellType;
		//document.forms[0].submit();
		var url = document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		//url = url + "&sellTypeId=" + document.forms[0].sellTypeId.value;
		url = url + "&colorCode=" + e.parentElement.parentElement.cells[4].children[0].value;
	    url = url + "&sizeCode=" + e.parentElement.parentElement.cells[5].children[0].value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		url=url+"&msc=" + document.forms[0].msc.value;
		//alert(url);
		ajaxpage(url, "ajaxcontentarea","str");
} 

function updateGift_M(nIndex,e) {
	    document.forms[0].action = "../order/orderModifyFirst.do?type=updateGift";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].sellTypeId.value = sellType;
		//document.forms[0].submit();
		var url = document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		//url = url + "&sellTypeId=" + document.forms[0].sellTypeId.value;
		url = url + "&colorCode=" + e.parentElement.parentElement.cells[4].children[0].value;
	    url = url + "&sizeCode=" + e.parentElement.parentElement.cells[5].children[0].value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		//alert(url);
		ajaxpage(url, "ajaxcontentarea","str");
} 

function selectGift(nIndex,sellTypeId,awardId,ord_require) {
	
		document.forms[0].action = "../order/orderAddSecond.do?type=selectGift&type1=add&sellTypeId="+sellTypeId+"&item_id="+nIndex+"&awardId="+awardId+"&ord_require="+ord_require;
		document.forms[0].actionType.value = "selectGift";
		document.forms[0].operateId.value = nIndex;
		document.forms[0].submit();
	
}

/*function getOpenwinValue(ret){
	document.forms[0].queryItemCode.value = ret[1];
	document.forms[0].maxqty.value = ret[2];
}*/

function getOpenwinValue2(url){
	url=url+"&mbId="+ document.forms[0].mbId.value;
	if (document.forms[0].orderId == null)
	{
		url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
	}
	
	ajaxpage(url, "ajaxcontentarea","str");
}

function winopen(url,title) 
{ 
	window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 

function add_item_f() {
	if(event.keyCode == 13) {
		//ajax checked
		callAjax(document.forms[0].queryItemCode, document.forms[0].queryItemQty);
		
	}
	
}

//回调函数
function updatePage(response) {

   if (response == "库存正常" || response == "预售缺货")
   {
		
		if (document.forms[0].orderId)
		{
			addItem_M();
		} else {
			addItem();
		}
   } else {
		
		
		document.getElementById("ajaxMessage").innerText = response;
		
		if ( response == "永久缺货" || response == "暂时缺货" || response == "永久缺货"){
			if (confirm("该产品目前"+response+",是否加入购物车?"))
			{
				if (document.forms[0].orderId)
				{
					addItem_M();
				} else {
					addItem();
				}
				
			} else {
				document.getElementById("ajaxMessage").innerText = "";
			}
		}
		return;
		
   }
   
}

//异步函数
function callAjax(obj1, obj2) {
	var ajax=new Ajax("/magicAjax.do?type=checkStockStatusByItemCode&itemCode=" + escape(obj1.value)+"&qty="+escape(obj2.value),"",this.updatePage);
	ajax.postRequest();
}

/***********快捷键**************/
function initHotkey() {
	document.onkeydown=keyDown; //键盘监听器

}
function keyDown() {
	var ieKey = event.keyCode;
	if ((event.ctrlKey) && (ieKey == 38)){ //产品代码
		document.forms[0].queryItemCode.focus();
	}
	if ((event.ctrlKey)&&(ieKey == 37)){ //幸运卡
		document.forms[0].ticketNumber.focus();
	}
	if ((event.ctrlKey)&&(ieKey == 39)) //其他礼券
	{
		document.forms[0].otherGiftNumber.focus();
	}
	if ((event.ctrlKey)&&(ieKey == 40)) //促销礼品
	{
		
		if (typeof(document.forms[0].giftCode1) == "undefined")
		{
			if (document.forms[0].giftCode1 != null)
			{
				document.forms[0].giftCode1.focus();
			}
		} else {
			if (!document.forms[0].giftCode1[0].disabled)
			{
				document.forms[0].giftCode1[0].focus();
				document.forms[0].giftCode1[0].checked = true;

			}
			
		}
		
	}
	
}
/////////////////////////////orderAdd2.jsp end///////////////////////////

/////////////////////////////orderAdd3.jsp begin///////////////////////////
function priorStep() {
	document.forms[0].action = "../order/orderAddSecond.do?type=refresh";
	//document.forms[0].submit();
	ajaxpage2(document.forms[0].action, 'ajaxcontentarea',document.forms[0]);
}

function submitForm() {
	if(document.forms[0].receiptorAddressId.value == 0) {
		alert("请选择送货地址！");
		return;
	}
	if(document.forms[0].deliveryTypeId.value == 0) {
		alert("请选择送货方式！");
		return;
	}
	if(document.forms[0].paymentTypeId.value == 0) {
		alert("请选择付款方式！");
		return;
	}
/*
	if(document.forms[0].paymentTypeId.value == 101) {
		if(document.forms[0].credit_card.value == "") {
		    alert("信用卡号不能为空");
		    return;
		}
		if(document.forms[0].id_card.value == "") {
		    alert("身份证号不能为空");
		    return;
		}
		if(isNaN(document.forms[0].ef_year.value)) {
		    alert("有效期年必须是数字");
		    return;
		}
		if(isNaN(document.forms[0].ef_month.value)) {
		    alert("有效期月必须是数字");
		    return;
		}
	}*/
	
	document.forms[0].actionType.value = "insertOrder";	
	
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	
}

function changeAddress(newValue) {

	if(newValue != null && trim(newValue) != "") {
		if (document.forms[0].orderId)
		{
			document.forms[0].action = "../order/orderModifySecond.do";
		} else {
			document.forms[0].action = "../order/orderAddThird.do";
		}
		document.forms[0].receiptorAddressId.value = newValue;
		document.forms[0].actionType.value = "changeAddress";
		ajaxpage2(document.forms[0].action, "ajaxcontentarea",document.forms[0]);
	}
}
function changeAddress2(newValue) {

	if(newValue != null && trim(newValue) != "") {
		if (document.forms[0].orderId)
		{
			document.forms[0].action = "../order/orderModifySecond.do";
		} else {
			document.forms[0].action = "../order/orderAddThird.do";
		}
		document.forms[0].action = "../order/orderChange.do?type=changeAddress";
		document.forms[0].receiptorAddressId.value = newValue;
		//document.forms[0].actionType.value = "changeAddress";
		ajaxpage2(document.forms[0].action, "ajaxcontentarea",document.forms[0]);
	}
}

function changeDelivery(newValue) {
	if(newValue != null && trim(newValue) != "" && document.forms[0].deliveryTypeId.value != newValue) {
		if (document.forms[0].orderId)
		{
			document.forms[0].action = "../order/orderModifySecond.do";
		} else {
			document.forms[0].action = "../order/orderAddThird.do";
		}
		document.forms[0].deliveryTypeId.value = newValue;
		document.forms[0].actionType.value = "changeDelivery";
		//document.forms[0].submit();
		//var url = document.forms[0].action+"?actionType="+document.forms[0].actionType.value;
		//url = url + "&receiptorAddressId=" + document.forms[0].receiptorAddressId.value;
		//url = url + "&deliveryTypeId=" + document.forms[0].deliveryTypeId.value;
		//url=url+"&mbId="+ document.forms[0].mbId.value;
		//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		//alert(url);
		ajaxpage2(document.forms[0].action, "ajaxcontentarea",document.forms[0]);
	}
}
function exchange(selectedItem, flag) {
		//alert(selectedItem+"***"+flag+"***"+memberId+"***"+cardId);
		if (flag == "true")
		{
			ajaxpage2("../order/orderAddSecond.do?type=expExchange&stepDtlId="+selectedItem, "ajaxcontentarea",document.forms[0]);
		} else {
			ajaxpage2("../order/orderModifyFirst.do?type=expExchange&stepDtlId="+selectedItem, "ajaxcontentarea",document.forms[0]);
		}
		
	
}

function changePayment(newValue) {
	if(newValue != null && trim(newValue) != "" && document.forms[0].paymentTypeId.value != newValue) {
		if (document.forms[0].orderId)
		{
			document.forms[0].action = "../order/orderModifySecond.do";
		} else {
			document.forms[0].action = "../order/orderAddThird.do";
		}
		
		document.forms[0].paymentTypeId.value = newValue;
		document.forms[0].actionType.value = "changePayment";
		//document.forms[0].submit();
		//var url = document.forms[0].action+"?actionType="+document.forms[0].actionType.value;
		//url = url + "&receiptorAddressId=" + document.forms[0].receiptorAddressId.value;
		//url = url + "&paymentTypeId=" + document.forms[0].paymentTypeId.value;
		//url=url+"&mbId="+ document.forms[0].mbId.value;
		//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		//alert(url);
		ajaxpage2(document.forms[0].action, "ajaxcontentarea",document.forms[0]);
	}
}
function selectCatalog(ele) {
	var obj = document.forms[0].catalog;
	if (obj==null || obj.length==0)
	{
		return;
	}
	
	for (var i=0; i < obj.length ; i++)
	{
		obj[i].checked == false;
	}
	ele.checked = true;
}
/////////////////////////////orderAdd3.jsp end///////////////////////////

/////////////////////////////_award.jsp begin///////////////////////////
function add_f(obj) {
	document.forms[0].action = "/member/memberAddGift.do?type=addInit";
	obj.disabled = true;
	document.forms[0].submit();
}

/////////////////////////////_award.jsp end///////////////////////////

//****************************ajax 查询分页包括多条记录翻页(new) begin*************************	
function goFirst() {
	document.forms[0].offset.value = 0;
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&offset=" + document.forms[0].offset.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}
function goPrior(offset) {
	document.forms[0].offset.value = offset;
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&offset=" + document.forms[0].offset.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}
function goPage(currPage) {
	
	document.forms[0].offset.value = currPage;
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&offset=" + document.forms[0].offset.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}
function goNext(offset) {
	document.forms[0].offset.value = offset;
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&offset=" + document.forms[0].offset.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}
function goLast(lastOffset) {
	document.forms[0].offset.value = lastOffset;
	//document.forms[0].submit();
	var url = document.forms[0].action;
	url = url + "&offset=" + document.forms[0].offset.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");
}

//****************************查询，包括多条记录翻页(new) end*************************

/////////////////////////////_order.jsp begin///////////////////////////
function queryFahuo(url,orderNum){
	
	url=url+ "?&strQryOrdCode="+orderNum;
	//document.forms[0].submit();
	ajaxpage(url, "ajaxcontentarea","str");

}
/////////////////////////////_order.jsp end///////////////////////////
/////////////////////////////member_draw_back.jsp begin///////////////////////////
function returnDeposit() {
	if(parseInt(document.form.drawback_amount.value)<=0||document.form.drawback_amount.value==""){
	alert('退款金额必须是大于0的数字!');
	document.form.drawback_amount.select();
	return false;
	}
	var edate = document.form.event_date.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	if(edate==null){
	alert('请按格式填写日期!');
	document.form.event_date.select();
	return false;
	}
	document.form.input.disabled = true;
	//document.form.submit();
	var url = document.forms[0].action;
	url += "?id="+document.forms[0].id.value;
	url += "&drawback_amount="+document.forms[0].drawback_amount.value;
	url += "&event_date="+document.forms[0].event_date.value;
	url += "&comments="+document.forms[0].comments.value;
	//alert(url);
	ajaxpage(url, "ajaxcontentarea","str");

}
/////////////////////////////member_draw_back.jsp end///////////////////////////
/////////////////////////////mbr_complaint_add.jsp begin///////////////////////////
function addComplaint(){
	if(document.complaintForm.cmpt_content.value==""){
	   alert("请输入投诉内容");
	   document.complaintForm.cmpt_content.focus();
	   return false;
	}
	if(document.complaintForm.parent_id.value==""){
		alert("请选择大类!");
		document.complaintForm.parent_id.focus();
			return false;
	}
	if(document.complaintForm.cmpt_type_id.value==""){
		alert("请选择小项!");
		document.complaintForm.cmpt_type_id.focus();
			return false;
	}		
	document.forms[0].submit();
	//var url = document.forms[0].action;
	//ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}
function ref2(){
	
       //document.complaintForm.action="../member/initComplaintCreate.do?";
       //document.complaintForm.submit();
	   var url = "../member/initComplaintCreate.do?";
	   ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}
function ref(tag){
	
       var url = "../member/initComplaintCreate.do?tag="+tag;
	   ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
       //document.complaintForm.submit();
}
function init_page() {

}
/////////////////////////////mbr_complaint_add.jsp end///////////////////////////
/////////////////////////////mbr_complaint_detail.jsp begin///////////////////////////
function showComplaintDetail(url) {
	
	ajaxpage(url, "ajaxcontentarea","null");
}
function backComplaintList(url) {
	ajaxpage(url, "ajaxcontentarea","null");
}
function inquireSubmit() {

	if(document.complaintForm.cmpt_content.value==""){
		alert('请输入解决方法!');
		document.complaintForm.cmpt_content.focus();
		return false;
	}
	var url = document.forms[0].action;
	ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}
/////////////////////////////mbr_complaint_detail.jsp end///////////////////////////
/////////////////////////////member_get_member_recommended_add.jsp begin///////////////////////////
function check_recommended_f() {
	//alert(document.forms[0].recommendedId.value);
	document.forms[0].cardId.value = document.forms[0].MemgetmemID.value;
	if(document.forms[0].cardId.value==""){
		alert('推荐人不能为空!');
		document.forms[0].MemgetmemID.focus();
		return;
	}
	
	ajaxpage2(document.forms[0].action, "ajaxcontentarea",document.forms[0]);
}
/////////////////////////////member_get_member_recommended_add.jsp end///////////////////////////
/////////////////////////////_award.jsp begin///////////////////////////
function add_award_gift(obj) {
	var url = "../member/memberAddGift.do?type=addInit";
	ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}
function cancel_exchanged_gift_f(obj) {
	
	var flag = false;
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("确实要取消礼品吗?"))
		{
			//document.forms[0].action = "/member/mbrGetAward.do?type=expCancelGift";
			//obj.disabled;
			//document.forms[0].submit();
			
			var url = "../member/mbrGetAward.do?type=expCancelGift&cardID=<%=member.getCARD_ID()%>";
			ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
		}
		
	} else {
		alert("请选择记录!");
	}
}
/////////////////////////////_award.jsp end///////////////////////////
/////////////////////////////exp_exchange_gift.jsp begin///////////////////////////
function submit_f(btn) {
	var obj = document.forms[0];
	if(obj.cardID == null || obj.cardID.value.length == 0) {
		alert("会员号不能为空!");
		obj.cardID.focus();
		return;
	}

	if(obj.itemCode == null || obj.itemCode.value.length == 0) {
		alert("产品名称不能为空!");
		obj.itemCode.focus();
		return;
	}

	if(obj.exchangePrice == null || obj.exchangePrice.value.length == 0) {
		alert("价格不能为空!");

		obj.exchangePrice.focus();
		return;
	}

	if(isNaN(obj.exchangePrice.value)) {
		alert("价格必须为数字!");

		obj.exchangePrice.focus();
		return;
	}

	btn.disabled = true;
	var url = "../member/memberAddGift.do?type=add";
	ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}

/////////////////////////////awardexp_exchange_gift.jsp end///////////////////////////
/////////////////////////////member_get_member_recommend_list.jsp begin///////////////////////////
function new_recommand_member_f() {
	document.forms[0].action = "/member/member_addToken.do";
	document.forms[0].submit();
}
/////////////////////////////member_get_member_recommend_list.jsp end///////////////////////////
/////////////////////////////member_get_member_recommend_add.jsp begin///////////////////////////
function add_recommend_member() {
	
	document.forms[0].recommendedCardId.value = document.forms[0].MemgetmemID.value;

	if(document.forms[0].recommendedCardId.value==""){
		alert('被推荐会员号不能为空!');
		document.forms[0].recommendedCardId.focus();
		return;
	}
	
	if(document.forms[0].itemCode.value==""){
		alert('货号不能为空!');
		document.forms[0].itemCode.focus();
		return;
	}
	
	if(document.forms[0].price.value==""){
		alert('价格不能为空!');
		document.forms[0].price.focus();
		return;
	}

	if(isNaN(document.forms[0].price.value)) {
		alert("价格必须为数字");
		document.forms[0].price.focus();
		return;
	}
	var url = "../member/memberGetMemberAdd.do";
	ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}


/////////////////////////////member_get_member_recommend_add.jsp end///////////////////////////
/////////////////////////////member_desktop_modify.jsp begin///////////////////////////
function modifyMemberSubmit() {

	if(document.memberForm.NAME.value==""){
		alert('会员姓名不能为空!');
		document.memberForm.NAME.focus();
		return false;;
	}
	if(document.forms[0].BIRTHDAY.value==""||document.forms[0].BIRTHDAY.value.length!=8||isNaN(document.forms[0].BIRTHDAY.value)){
		alert('出生日期不能为空并且长度为8位数字!!');
		document.forms[0].BIRTHDAY.select();
		return false;
	}

	if(document.forms[0].BIRTHDAY.value.substring(4,6)<1||document.forms[0].BIRTHDAY.value.substring(4,6)>12){
		alert('出生日期的月份必须介于1-12之间!');
		document.forms[0].BIRTHDAY.select();
		return false;
	}

	if(document.forms[0].BIRTHDAY.value.substring(6,8)<1||document.forms[0].BIRTHDAY.value.substring(6,8)>31){
		alert('出生日期的日子必须介于1-31之间!');
		document.forms[0].BIRTHDAY.select();
		return false;
	}
	
	if(document.memberForm.TELEPHONE.value==""){
		alert('常用电话必须填写');
		document.memberForm.TELEPHONE.focus();
		return false;
	}

	if(document.memberForm.postcode.value==""||document.memberForm.postcode.value.length!=6||isNaN(document.memberForm.postcode.value)){
		alert('邮编不能为空并且长度为6位数字!');
		document.memberForm.postcode.focus();
		return false;
	}
		if(document.memberForm.addressDetail.value==""){
		alert('会员地址不能为空!');
		document.memberForm.addressDetail.focus();
		return false;
	}
	document.memberForm.modifyBtn.disabled=true;
	document.memberForm.submit();
	
	//var url = "../member/memberModify.do";
	//ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}
/////////////////////////////member_desktop_modify.jsp end///////////////////////////
/////////////////////////////member_address_add.jsp begin///////////////////////////
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


function add_member_address() {
	if(document.memberForm.Relation_person.value==""){
	alert('联系人必须填写!');
	document.memberForm.Relation_person.focus();
	return false;
	}
	if(document.memberForm.Telephone.value==""){
	alert('联系电话一不能为空');
	document.memberForm.Telephone.focus();
	return false;
	}

	if(document.memberForm.Postcode.value==""||document.memberForm.Postcode.value.length>6||isNaN(document.memberForm.Postcode.value)){
	alert('邮编不能为空或者长度不能大于6位数字!');
	document.memberForm.Postcode.focus();
	return false;
	}

	if(document.memberForm.Delivery_address.value==""){
	alert('送货地址必须填写!');
	document.memberForm.Delivery_address.focus();
	return false;
	}

	//document.memberForm.submit();
	ajaxpage2("memberAddressModify.do","ajaxcontentarea",document.forms[0]);
}
/////////////////////////////member_address_add.jsp end///////////////////////////
/////////////////////////////member_address_modify.jsp begin///////////////////////////

function modify_member_address() {
	if(document.memberForm.Relation_person.value==""){
	alert('联系人必须填写!');
	document.memberForm.Relation_person.focus();
	return false;
	}
	if(document.memberForm.Telephone.value==""){
	alert('联系电话一不能为空!');
	document.memberForm.Telephone.focus();
	return false;
	}

	if(document.memberForm.Postcode.value==""||document.memberForm.Postcode.value.length>6||isNaN(document.memberForm.Postcode.value)){
	alert('邮编不能为空或者长度不能大于6位数字!');
	document.memberForm.Postcode.focus();
	return false;
	}

	if(document.memberForm.Delivery_address.value==""){
	alert('送货地址必须填写!');
	document.memberForm.Delivery_address.focus();
	return false;
	}

	//document.memberForm.submit();
	ajaxpage2("memberAddressModify.do","ajaxcontentarea",document.forms[0]);
}
/////////////////////////////member_address_modify.jsp end///////////////////////////
/////////////////////////////order_view.jsp begin///////////////////////////
function viewGifts(orderId) {
	openWin('../order/orderGiftsView.do?orderId='+orderId, 'PopWin', 320, 200);
}
function viewGifts2(snId) {
	openWin('../order/snGiftsView.do?sn_id='+snId, 'PopWin', 320, 200);
}
/////////////////////////////order_view.jsp end///////////////////////////
/////////////////////////////order_modify1.jsp begin///////////////////////////

function nextStep_M() {
	/*if(document.forms[0].sku_id == null) {
		alert("您的购物篮内没有任何商品，不能执行下一步操作！");
		return;
	}*/
	var skus = document.getElementsByName("sku_id");
	if(skus.length==0) {
	    alert("您的购物篮内没有任何商品，不能执行下一步操作！");
		return;
	}
    for( var i=0;i<skus.length;i++) {
    if (skus[i].value==0) {
        alert("您的购物篮内存在没有确定的商品，不能执行下一步操作！");
        return;
    }
    }
	
	var x = document.getElementById("promGiftTbl").getElementsByTagName("INPUT");
	var sel = false;
	var ext = false;
	var rd;
	for (var i=0;i<x.length;i++){ 
	    if(x[i].disabled == true && x[i].checked == true) {
           sel = true;
        }
        if(x[i].disabled == false && x[i].checked == false) {
           ext = true;
           rd = i;
        }
    }
    if(sel == false &&ext== true) {
        if(confirm("您还没有选择赠品，请选择是否需要赠品?")) {
            document.getElementById("promGiftTbl").focus();
            return;
        }
    }
    
	document.forms[0].action = "../order/orderModifySecond.do";
	//document.forms[0].submit();
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}

function addItem_M() {
	if(trim(document.forms[0].queryItemCode.value) == '') {
		alert("请选择商品！");
		document.forms[0].queryItemCode.focus();
		return;
	}
	
	var queryItemCode = parseInt( trim(document.forms[0].queryItemCode.value) );
	if(!isNaN(queryItemCode) && queryItemCode>=100000 && queryItemCode <=100010 ) {
		alert("会员卡不能直接购买！");
		document.forms[0].queryItemCode.focus();
		return;
	}
	
	//document.forms[0].action = "orderModifyFirst.do?type=addItem";
	//document.forms[0].actionType.value = "addItem";
	//document.forms[0].submit();
	ajaxpage2("../order/orderModifyFirst.do?type=addItem","ajaxcontentarea",document.forms[0]);
}

function deleteItem_M(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderModifyFirst.do?type=deleteItem&operateId="+nIndex;
		document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].submit();
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	}
}
function deleteTicket_M(del_code) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "../order/orderModifyFirst.do?type=removeTicket&delTicket="+del_code;
		//document.forms[0].submit();
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	}
}

function updateItem_M(nIndex) {
	document.forms[0].action = "../order/orderModifyFirst.do?type=updateItem";
	document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;
	
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}

function updateItem_M2(sectionType, itemId, sellTypeId, obj) {

	document.forms[0].action = "../order/orderModifyFirst.do?type=updateItem";
	document.forms[0].actionType.value = "updateItem";
	
	var url= document.forms[0].action;
	url = url + "&updItemId=" + itemId;
	url = url + "&updateItemQty=" + obj.value;
	url=url+"&mbId="+ document.forms[0].mbId.value;
	url=url+"&sellType="+ sellTypeId;
	url=url+"&sectionType="+ sectionType;
	
	ajaxpage2(url, "ajaxcontentarea",document.forms[0]);
}

function validateTicket_M() {
	document.forms[0].action = "../order/orderModifyFirst.do?type=validTicket";
	document.forms[0].actionType.value = "validateTicket";
	//document.forms[0].submit();
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}
function validateTicket2_M() {
	document.forms[0].action = "../order/orderModifyFirst.do?type=validTicket2";
	document.forms[0].actionType.value = "validTicket2";
	//document.forms[0].submit();
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}
function clearItem_M() {
	if(confirm("您确定清空购物篮？")) {
		document.forms[0].action = "../order/orderModifyFirst.do?type=clearItem";
		document.forms[0].actionType.value = "clear";
		//document.forms[0].submit();
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	}
}

function refreshMoney_M() {
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

function addGifts_M() {
	document.forms[0].action = "../order/orderModifyFirst.do?type=addGift";
	document.forms[0].actionType.value = "addGift";
	//document.forms[0].submit();
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}
function deleteGift_M(nIndex,sell_type) {
	if(sell_type==4 ||confirm("选择“删除”后系统只是从本次订单中删除礼品，该礼品会继续保留在您的暂存架。\n如果您以后也不需要这个礼品，请选则“永久删除”。\n您确定“删除”吗？")) {
		document.forms[0].action = "../order/orderModifyFirst.do?type=deleteGift";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].sellTypeId.value = sellType;
		//document.forms[0].submit();
		var url = document.forms[0].action;
		url = url + "&operateId=" + document.forms[0].operateId.value;
		//url = url + "&sellTypeId=" + document.forms[0].sellTypeId.value;
		url=url+"&mbId="+ document.forms[0].mbId.value;
		//url=url+"&prTypeId="+ document.forms[0].prTypeId.value;
		ajaxpage(url, "ajaxcontentarea","str");
	}
	
}
function comboutGift_M(nIndex) {
	if(confirm("选择“永久删除”后我们系统不再保留该礼品，\n如果您希望在以后的订单中拿这个礼品，请选择“删除”礼品。\n您确定“永久删除”吗？")) {
		document.forms[0].action = "../order/orderModifyFirst.do?type=deleteGift&delType=combout";
		document.forms[0].actionType.value = "deleteGift";
		document.forms[0].operateId.value = nIndex;
		//document.forms[0].sellTypeId.value = sellType;
		//document.forms[0].submit();
		ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
	}
}   
function selectGift_M(nIndex,sellTypeId,awardId,ord_require) {

		document.forms[0].action = "../order/orderAddSecond.do?type=selectGift&&type1=modify&&sellTypeId="+sellTypeId+"&&item_id="+nIndex+"&&awardId="+awardId+"&&ord_require="+ord_require;
		document.forms[0].actionType.value = "selectGift";
		document.forms[0].operateId.value = nIndex;
		document.forms[0].submit();

}
function getOpenwinValue_M(ret){
	document.forms[0].queryItemCode.value = ret[1];
}

function add_item_f_M() {
	if(event.keyCode == 13) {
		//ajax checked
		callAjax(document.forms[0].queryItemCode,document.forms[0].queryItemQty);
		
	}
}



//异步函数
function callAjax_M(obj1, obj2) {
	var ajax=new Ajax("/magicAjax.do?type=checkStockStatusByItemCode&itemCode=" + escape(obj1.value)+"&qty="+escape(obj2.value),"",this.updatePage);
	ajax.postRequest();
}

/***********快捷键**************/
function initHotkey() {
	document.onkeydown=keyDown; //键盘监听器

}

function keyDown() {
	var ieKey = event.keyCode;
	if ((event.ctrlKey) && (ieKey == 38)){ //产品代码
		document.forms[0].queryItemCode.focus();
	}
	if ((event.ctrlKey)&&(ieKey == 37)){ //幸运卡
		document.forms[0].ticketNumber.focus();
	}
	if ((event.ctrlKey)&&(ieKey == 39)) //其他礼券
	{
		document.forms[0].otherGiftNumber.focus();
	}
	if ((event.ctrlKey)&&(ieKey == 40)) //促销礼品
	{
		
		if (typeof(document.forms[0].giftCode1) == "undefined")
		{
			if (document.forms[0].giftCode1 != null)
			{
				document.forms[0].giftCode1.focus();
			}
		} else {
			if (!document.forms[0].giftCode1[0].disabled)
			{
				document.forms[0].giftCode1[0].focus();
				document.forms[0].giftCode1[0].checked = true;

			}
			
		}
		
	}
	
}
/////////////////////////////order_modify1.jsp end///////////////////////////
/////////////////////////////order_modify2.jsp begin///////////////////////////
function submitForm_M() {
	if(document.forms[0].receiptorAddressId.value == 0) {
		alert("请选择送货地址！");
		return;
	}
	if(document.forms[0].deliveryTypeId.value == 0) {
		alert("请选择送货方式！");
		return;
	}
	if(document.forms[0].paymentTypeId.value == 0) {
		alert("请选择付款方式！");
		return;
	}
	/*
	if(document.forms[0].paymentTypeId.value == 101) {
		if(document.forms[0].credit_card.value == "") {
		    alert("信用卡号不能为空");
		    return;
		}
		if(document.forms[0].id_card.value == "") {
		    alert("身份证号不能为空");
		    return;
		}
		if(isNaN(document.forms[0].ef_year.value)) {
		    alert("有效期年必须是数字");
		    return;
		}
		if(isNaN(document.forms[0].ef_month.value)) {
		    alert("有效期月必须是数字");
		    return;
		}
	}*/
	
	/*var catalog=document.getElementsByName("catalog");
	var is_seleted = false;
	for (var i=0; i<catalog.length; i++)
	{
		if (catalog[i].checked == true)
		{
			is_seleted = true;
			break;
		}
	}
	if (!is_seleted)
	{
		alert("请选择相关目录!");
		return;
	}*/
	document.forms[0].actionType.value = "updateOrder";
	//document.forms[0].submit();
	document.forms[0].BtnSubmit.disabled = true;
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}

function selectCatalog_M(ele) {
	var obj = document.forms[0].catalog;
	if (obj==null || obj.length==0)
	{
		return;
	}
	
	for (var i=0; i < obj.length ; i++)
	{
		obj[i].checked == false;
	}
	ele.checked = true;
}
/////////////////////////////order_modify2.jsp end///////////////////////////

function outtel(nIndex, obj) {

	document.forms[0].action = "../member/memberDetail.do?OutTel=34014699&id=<%=member.getID()%>&service=1";
	document.forms[0].submit();

}
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function detail_f(detailTable,view_ctrl) {

	var str = detailTable.style.display;
	if (str == "none")
	{
		view_ctrl.src = "../images/UI_OM_collapse.gif";
		view_ctrl.alt="关闭详情";
		detailTable.style.display = "block";
	}

	if (str == "block")
	{
		view_ctrl.src = "../images/UI_OM_expand.gif";
		view_ctrl.alt="查看详情";
		detailTable.style.display = "none";
	}
}
/////////////////////////////_money_list.jsp end///////////////////////////

function _query_money_bill()
{
	if (document.forms[0].ref_no.value == ""&&document.forms[0].member_name.value == "")
	{
		alert("请输入查询条件!");
		return;
	}
	document.forms[0].action = "_money_list.jsp?tag=1";
	document.forms[0].queryBtn.disabled = true;
	ajaxpage2(document.forms[0].action,"ajaxcontentarea",document.forms[0]);
}
function _supply_money(str)
{
	if (confirm("确认给会员"+str+"补礼金吗？"))
	{
		document.forms[0].supplyMoneyBtn.disabled = true;
		ajaxpage2("_return_gift_money_ok.jsp","ajaxcontentarea",document.forms[0]); 
	}
}
/////////////////////////////_returned_catalog_register.jsp end///////////////////////////
function _add_returned_catalog_reason()
{
	if (confirm("确实要登记吗？"))
	{
		document.forms[0].addBtn.disabled = true;
		ajaxpage2("_returned_catalog_register_ok.jsp","ajaxcontentarea",document.forms[0]); 
	}
}
/////////////////////////////_order.jsp end///////////////////////////
function viewCancelInfo(url) {
	openWin(url, 'PopWin', 200, 160);
}

/////////////////////////////cart_diamond_exchange.jsp end///////////////////////////
function diamond_exchange(selectedItem, flag) {
	
	if (flag == "true")
	{
		ajaxpage2("../order/orderAddSecond.do?type=diamondExchange&excId="+selectedItem, "ajaxcontentarea",document.forms[0]);
	} else {
		ajaxpage2("../order/orderModifyFirst.do?type=diamondExchange&excId="+selectedItem, "ajaxcontentarea",document.forms[0]);
	}
}
/////////////////////////////select_recruit_gift.jsp end///////////////////////////
function select_recruit_gift_f(flag, gpId, ids) {
	
	
	ajaxpage2("../order/orderAddSecond.do?type=selectRecruitGift&gpId="+gpId+"&ids="+ids+"&isAdd="+flag, "ajaxcontentarea",document.forms[0]);
	
}

function change_recruit_gift_f(flag, priceListId, oldItemId, oldSectionType) {
	ajaxpage2("../order/orderAddSecond.do?type=changeRecruitGroup&priceListId="+priceListId+"&oldItemId="+oldItemId+"&isAdd="+flag+"&oldSectionType="+oldSectionType, "ajaxcontentarea",document.forms[0]);
	
	
}
//-->
</SCRIPT>

</head>
<body bgcolor="#FFFFFF" text="#000000">

<input type="hidden" name="iscallcenter" value="<%=isCallCenter%>">
      	<logic:equal name="member" property="IS_ORGANIZATION" value="0">
			<table width="100%" border="0"  cellpadding="0" cellspacing="0" align="center">
  				<tr>

				  <td><font color="#990000"><b>基本信息</b></font> 
				  <logic:greaterThan name="member" property="gift_num" value="0">
				  <font color=red> 您帐户中有<bean:write name="member" property="gift_num"/>张礼券尚未使用 </font>
				  </logic:greaterThan>
				  
				  <logic:equal name="member" property="blacklistMember" value="true">
				  （<bean:write name="member" property="blackRemark"/>）
				  </logic:equal></td>
				   <TD width="30" align="left"><input type="image" SRC="../images/UI_OM_expand.gif" onclick="javascript:detail_f(other_info,this);" alt="查看详情"></td>
				</tr>
			</table>
			<table width="100%" align="center" cellspacing="1" cellspacing="1" border="0" noWrap class="OraTableRowHeader" >
				
				<tr height="22">
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">会员号：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">会员号：</font></logic:equal>
					
					</td><td  width="100" 
					<logic:equal name="member" property="LEVEL_ID" value="3">
					    bgcolor="green"
					</logic:equal>
					<logic:notEqual name="member" property="LEVEL_ID" value="3">
					    bgcolor="#FFFFFF"
					</logic:notEqual>
					
					><bean:write name="member" property="CARD_ID"/></td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">姓名：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">姓名：</font></logic:equal>
					
					</td><td width="100" 
					<logic:empty name="member" property="COMMENTS">
					    bgcolor="#FFFFFF"
					</logic:empty>
					<logic:notEmpty name="member" property="COMMENTS">
					    bgcolor="red"
					</logic:notEmpty>
					>
					<p title="<bean:write name="member" property="COMMENTS"/>">
					<bean:write name="member" property="NAME"/>
					(
					<logic:equal name="member" property="GENDER" value="M">男</logic:equal>
					<logic:equal name="member" property="GENDER" value="F">女</logic:equal>
					)
					</p></td>

					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">出生日期：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">出生日期：</font></logic:equal>
					
					</td><td  width="100" bgcolor="#FFFFFF"><bean:write name="member" property="BIRTHDAY"/></td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">电子邮件：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">电子邮件：</font></logic:equal>
					</td><td  width="100" bgcolor="#FFFFFF">
					<bean:write name="member" property="EMAIL"/>
					</td>
				</tr>
                <tr height="22">
				<td>
					<logic:equal name="member" property="blacklistMember" value="false">
					会员等级：
					</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true">
					<font color="red">会员等级：</font>
					</logic:equal>
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="LEVEL_ID" value="1">普通会员</logic:equal>
					<logic:equal name="member" property="LEVEL_ID" value="2">正式会员</logic:equal>
					<logic:equal name="member" property="LEVEL_ID" value="3">VIP会员</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">常用电话：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">常用电话：</font></logic:equal>
					</td><td  bgcolor="#FFFFFF" valign="top"><bean:write name="member" property="TELEPHONE"/>
					<!--
					<img src="../crmjsp/images/ico_insert.gif" alt="更改" height=16 width=16 style="cursor:hand" onclick=javascript:location.href="../member/memberAddTelephone.do?ID=<%=member.getID()%>&TELEPHONE=<%=currentTel%>&telType=T">
					<%
					  if(phone.length()>0){
					%>
					,<img src="../crmjsp/images/ico_insert.gif"  alt="外拨" height=16 width=16 style="cursor:hand" onclick=javascript:window.open("../member/null.jsp?OutTel=9<%=phone%>&id=<%=member.getID()%>&service=1","_blank",'width=40,height=40')>
					<%
					}
					%>
					-->					
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">其他电话1：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">其他电话1：</font></logic:equal>
					</td><td bgcolor="#FFFFFF">
					
					<bean:write name="member" property="FAMILY_PHONE"/>
					<!--
					<img src="../crmjsp/images/ico_insert.gif"  alt="更改" height=16 width=16 style="cursor:hand" onclick=javascript:location.href="../member/memberAddTelephone.do?ID=<%=member.getID()%>&FAMILY_PHONE=<%=currentTel%>&telType=F">
					<%
					  if(f_phone.length()>0){
					%>
					,<img src="../crmjsp/images/ico_insert.gif"  alt="外拨" height=16 width=16 style="cursor:hand" onclick=javascript:window.open("../member/null.jsp?OutTel=9<%=f_phone%>&id=<%=member.getID()%>&service=1","_blank",'width=40,height=40')>
					<%
					}
					%>	
					-->	
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">其他电话2：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">其他电话2：</font></logic:equal>
					
					</td><td  bgcolor="#FFFFFF"><nobr>
					<bean:write name="member" property="COMPANY_PHONE" />
					<!--
					<img height=16 width=16 style="cursor:hand" src="../crmjsp/images/ico_insert.gif" onclick=javascript:location.href="../member/memberAddTelephone.do?ID=<%=member.getID()%>&COMPANY_PHONE=<%=currentTel%>&telType=C"  alt="更新单位电话"></nobr>
					<%
					  if(c_phone.length()>0){
					%>
					,<img src="../crmjsp/images/ico_insert.gif"  alt="外拨" height=16 width=16 style="cursor:hand" onclick=javascript:window.open("../member/null.jsp?OutTel=9<%=c_phone%>&id=<%=member.getID()%>&service=1","_blank",'width=40,height=40')>
					<%
					}
					%>
					-->					
					</td>
				</tr>
				<tr height="22">
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">邮编：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">邮编：</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF"><bean:write name="member" property="postcode"/>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">会员地址：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">会员地址：</font></logic:equal>
					</td>
					<td colspan="3" bgcolor="#FFFFFF"><bean:write name="member" property="sectionName"/>&nbsp;<bean:write name="member" property="addressDetail"/>&nbsp;<!-- <input name="changeNewCardBtn" type="button" value="换新卡" onclick="location.href='changeNewCard.do?type=showPage&ID=<%=member.getID()%>&CARD_ID=<%=member.getCARD_ID()%>'"> --></td>
					<td>淘宝旺旺号
				</td>
				<td  bgcolor="#FFFFFF">
					<bean:write name="member" property="taobaoWangId" />
					</td>
				</tr>	
		        </table>
		        
                <table id="other_info" width="100%" style="display:none" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
				<tr height="22">
					<td width="100" >
					<logic:equal name="member" property="blacklistMember" value="false">性别：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">性别：</font></logic:equal>
					</td>
					<td width="100"  bgcolor="#FFFFFF">
					<logic:equal name="member" property="GENDER" value="M">男</logic:equal>
					<logic:equal name="member" property="GENDER" value="F">女</logic:equal>
					</td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">证件类型：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">证件类型：</font></logic:equal>
					</td>
					<td  width="100" bgcolor="#FFFFFF">
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="0">&nbsp;</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="1">身份证</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="2">学生证</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="3">军官证</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="4">其他</logic:equal>
			        </td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">证件号：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">证件号：</font></logic:equal>
					</td>
					<td colspan="3" width="300" bgcolor="#FFFFFF">
					<font color="red">
					<bean:write name="member" property="CERTIFICATE_CODE"/>
					</font>
					</td>
				</tr>
                <tr height="22">
                    <td >
					<logic:equal name="member" property="blacklistMember" value="false">接收目录类型：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">接收目录类型：</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="CATALOG_TYPE" value="0">纸面版</logic:equal>
					<logic:equal name="member" property="CATALOG_TYPE" value="1">电子版</logic:equal>
					<logic:equal name="member" property="CATALOG_TYPE" value="2">没有目录</logic:equal>
					</td>
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">招募MSC：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">招募MSC：</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF"><bean:write name="member" property="MSC_CODE"/>
					</td>
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">入会时间：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">入会时间：</font></logic:equal>
					</td>
					<td  colspan="3" bgcolor="#FFFFFF"><bean:write name="member" property="CREATE_DATE"/>
					</td>
				</tr>
        </table>
		
		<table width="100%" border="0"  cellpadding="0" cellspacing="0" align="center">
			<tr>

				  <td><font color="#990000"><b>帐户信息</b></font></td>
				  <TD width="30" align="left">
				  <!--
				  <input type="image" SRC="../images/UI_OM_collapse.gif" onclick="javascript:detail_f(other_deposit,this);" alt="查看详情" >
				 -->
				  </TD>
			</tr>
	
		</table>
		
		<table id="other_deposit" style="display:block" width="100%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
			<tr height="22" nowrap>
				<td width="12%" >
				<logic:equal name="member" property="blacklistMember" value="false">帐户余额(礼金)</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">帐户余额(礼金)</font></logic:equal>
				
				</td><td width=12% bgcolor="#FFFFFF"><%=member.getDEPOSIT()%>(<%=member.getEMONEY()%>)</td>
				<td width="12%">
				<logic:equal name="member" property="blacklistMember" value="false">冻结帐户(礼金)</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">冻结帐户(礼金)</font></logic:equal>
				
				</td><td  width=12% bgcolor="#FFFFFF"><%=member.getFORZEN_CREDIT()%>(<%=member.getFROZEN_EMONEY()%>)</td>
				<td width="12%">
				<logic:equal name="member" property="blacklistMember" value="false">可用余额(礼金)</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">可用余额(礼金)</font></logic:equal>
				
				</td><td  width=12% bgcolor="#FFFFFF"><font color="red"><%=Arith.round(member.getDEPOSIT()-member.getFORZEN_CREDIT(), 2)%>
				                (<%=member.getEMONEY()-member.getFROZEN_EMONEY() %>)</font></td>
				<td width="12%">
				<logic:equal name="member" property="blacklistMember" value="false">购买次数：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">购买次数：</font></logic:equal>
				
				</td><td  width=12% bgcolor="#FFFFFF"><%=member.getPURCHASE_COUNT()%></td>
				
			</tr>
			<tr height="22">
				
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">累计积分：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">累计积分：</font></logic:equal>
				
				</td><td  bgcolor="#FFFFFF"><%=member.getEXP()%></td>
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">本年度积分：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">本年度积分：</font></logic:equal>
				
				</td><td  bgcolor="#FFFFFF"><%=member.getAMOUNT_EXP()%></td>
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">上年度积分：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">上年度积分：</font></logic:equal>
				
				</td>
				<td  bgcolor="#FFFFFF"><%=member.getOLD_AMOUNT_EXP()%>
				
				</td>
				<td >
				<logic:equal name="member" property="blacklistMember" value="false">恶意退货次数：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">恶意退货次数：</font></logic:equal>
				
				</td><td   bgcolor="#FFFFFF"><%=member.getANIMUS_COUNT()%></td>
			</tr>	
					
		</table>
		
      	</logic:equal>	

		
		<hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
		
		<ul id="maintab" class="shadetabs" style="width:100%;overflow-x:auto;">
		<li ><a href="../member/consoleOrders.do" rel="ajaxcontentarea">订单查询</a></li>
		<!--<li ><a href="../member/consoleOrders.do" rel="ajaxcontentarea">换货单查询</a></li>
		-->
		<li><a href="../order/orderAddSecond.do?type=init&prTypeId=2&iscallcenter=1" rel="ajaxcontentarea">新增订单</a></li>
		<li><a href="../member/initComplaintCreate.do?iscallcenter=1" rel="ajaxcontentarea">投诉咨询</a></li>
		<li><a href="../member/complaintList.do?iscallcenter=1&type=0" rel="ajaxcontentarea">投诉咨询历史</a></li>
		<li><a href="../member/consoleDeposit.do?iscallcenter=1" rel="ajaxcontentarea">帐户历史</a></li>
		<li><a href="../member/consoleEmoney.do?iscallcenter=1" rel="ajaxcontentarea">礼金历史</a></li>
		<li><a href="../member/consoleAward.do?iscallcenter=1" rel="ajaxcontentarea">暂存架</a></li>
		<li><a href="../member/consoleExp.do?iscallcenter=1" rel="ajaxcontentarea">积分历史</a></li>
		<li><a href="../member/consoleRMembers.do?iscallcenter=1" rel="ajaxcontentarea">我推荐的</a></li>

		<li><a href="../member/memberInitModify.do?id=<%=member.getID()%>&type=0&address_id=<%=member.getADDRESS_ID()%>'" rel="ajaxcontentarea">更改</a></li>
		<li><a href="../crmjsp/member_update_ok.jsp?card_id=<%=member.getCARD_ID()%>&doc_type=2030&id=<%=member.getID()%>" rel="ajaxcontentarea">升降级</a></li>
		<!--
		<li><a href="../crmjsp/member_drawback.jsp?card_id=<%=member.getCARD_ID()%>&doc_type=3320&id=<%=member.getID()%>" rel="ajaxcontentarea">退款</a></li>
		-->
		<li><a href="../member/memberDetail.do?id=<%=member.getID()%>&recommended_id=1" rel="ajaxcontentarea">推荐我的</a></li>
		<li><a href="../order/orderAddSecond.do?type=shopcart" rel="ajaxcontentarea">购物车</a></li>
		<!--
		<li><a href="../member/_money_list.jsp?tag=0" rel="ajaxcontentarea">汇款单</a></li>
		<li><a href="../member/_returned_catalog_register.jsp" rel="ajaxcontentarea">目录</a></li>
		-->
		</ul>
		
		
		<div id="ajaxcontentarea" class="contentstyle" style="width:100%">
		</div>
		<script type="text/javascript">
		<!--

		startajaxtabs("maintab");
		expandtab("maintab",<%=defaultTab%>);
		//-->
		</script>

</body>
</html>
