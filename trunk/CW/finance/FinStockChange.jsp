<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.text.*"%>
<%@ page import ="java.util.Date"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");     //日期格式
	Date today = new Date();
	String theday = sdf.format(today).toString();
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script language="JavaScript">

function nextStep() {
	if(document.forms[0].itemQty == null) {
		alert("你输入货号！");
		return;
	}

		
	document.forms[0].action = "product_change_add_ok.jsp";
	document.forms[0].submit();
}

function addItem() {

	if(trim(document.forms[0].queryItemCode.value) == '') {
		alert("请选择商品！");
		document.forms[0].queryItemCode.focus();
		return;
	}
	if(!is_integer(document.forms[0].queryItemQty.value)){
		alert('请输入商品数量必须是整数!');
	document.forms[0].queryItemQty.select();
	return false;
	}

	
	document.forms[0].action = "AddSecond.do?type=addItem";
	document.forms[0].actionType.value = "addItem";
	document.forms[0].submit();
}

function deleteItem(nIndex) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "AddSecond.do?type=deleteItem";
		document.forms[0].actionType.value = "deleteItem";
		document.forms[0].operateId.value = nIndex;
		document.forms[0].submit();
	}
}
function deleteTicket(del_code) {
	if(confirm("您确定删除？")) {
		document.forms[0].action = "AddSecond.do?type=removeTicket&delCode="+del_code;
		
		document.forms[0].submit();
	}
}

function updateItem(nIndex) {
	document.forms[0].action = "AddSecond.do?type=updateItem";
	document.forms[0].actionType.value = "updateItem";
	document.forms[0].operateId.value = nIndex;

	document.forms[0].submit();
}






function getOpenwinValue(ret){

	document.forms[0].queryItemCode.value = ret[1];
	document.forms[0].maxqty.value = ret[2];
}

function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function setFocus() {
	document.forms[0].queryItemCode.focus();
}

function add_item_f() {
	if(event.keyCode == 13) {
		//ajax checked
		callAjax(document.forms[0].queryItemCode, document.forms[0].queryItemQty);
		
	}
}

//回调函数
function updatePage(response) {
   if (response != "库存正常" || response == "永久缺货")
  // if (response == "预售缺货" || response == "永久缺货" || response == "暂时缺货" )
   {
		document.getElementById("ajaxMessage").innerText = response;
		/**
		if (confirm("是否加入购物车?"))
		{
			addItem();
		} else {
			document.getElementById("ajaxMessage").innerText = "";
		}*/
		return;
   } else {
		
		addItem();
		
   }
   
}

//异步函数
function callAjax(obj1, obj2) {
	var ajax=new Ajax("/magicAjax.do?type=checkStockStatusByItemCode&itemCode=" + escape(obj1.value)+"&qty="+escape(obj2.value),"",this.updatePage);
	ajax.postRequest();
}


</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="setFocus()" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	
    <td> <font color="#838383"><b>当前位置：</b>财务管理 -&gt; 手工调库存  </font></td>
   </tr>
</table>

<html:form action="/AddSecond.do"> 
<html:hidden property="prTypeId"/>

<html:hidden property="remark"/>
<br>
<table width="95%" align="center" cellspacing="0" border="0"  >
  <tr> 
    <td>选择商品: 
      <input type="text" name="queryItemCode" size="16" onkeydown="add_item_f()">
      <input name="query" type="button" value="查询" onclick="openWin('../product/productQuery.do?actn=selectProduct', 'PopWin', 700, 450);">
      &nbsp;&nbsp; 
      <input type="text" name="queryItemQty" size="4"  value="1" maxlength="10"  onkeydown="add_item_f()">
      &nbsp; 
      
      <input name="BtnAddItem" type="button" value=" 确定 " onclick="addItem();">&nbsp;
      <input type="hidden" name="maxqty" >
      
	 
    </td>
	<TD ><font color="red" id="ajaxMessage"></font></TD>
  </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" id="DataTable">
  <tr class="OraTableRowHeader" noWrap  height="26"> 
    <td align="center"><b>编号</b></td>
    <td align="center"><b>名称</b></td>
    <td align="center"><b>库存状态</b></td>
    <td align="center"><b>调整日期</b></td>
    <td align="center"><b>数量</b></td>
    <td align="center"><b>单位</b></td>
    <td width="100" align="center"><B>操作</B></td>
  </tr>
  <%int i=0;%>
  <bean:define id="items" name="orderForm" property="items" type="java.util.Collection"/> 
  <logic:iterate name="items" id="item"> 
  <tr > 
    <td align="center"><bean:write name="item" property="itemCode"/> 
      <html:hidden name="item" property="itemId"/> </td>
    <td align="left"><bean:write name="item" property="itemName" ignore="true"/></td>
    <td align="left">
    <logic:notEqual name="item" property="stockStatusName" value="库存正常">
    <font color=red><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
    <logic:equal name="item" property="stockStatusName" value="库存正常">
    <bean:write name="item" property="stockStatusName" ignore="true"/>
    </logic:equal>

    </td>
		<td ><input type="text" name="operateDate<%=i%>" size="10" readonly value="<%=theday%>">
		  <a href="javascript:calendar(document.forms[0].operateDate<%=i%>)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>    
    <td align="center"> 
  
     <html:text name="item" property="itemQty" size="4" maxlength="10"/>
 
    
    </td>
    <td align="center">&nbsp;<bean:write name="item" property="itemUnit" ignore="true"/></td>
 
    <td align="center"> 
      <input name="BtnDelete" value="删除" type="button" onClick="deleteItem(<bean:write name="item" property="itemId" format="#"/>);">
      <!--<input name="BtnUdate" value="更新" type="button" onClick="updateItem(<bean:write name="item" property="itemId" format="#"/>);">-->
    </td>
  </tr>
    <%i++;%>
  </logic:iterate>

  
</table>


<table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td height="10"></td>
  </tr>
  <tr> 
    <td align="center"> 

      <input name="BtnNextStep" type="button" value="提  交" onClick="nextStep();">
    </td>
  </tr>
</table>
<br>
<input type="hidden" name="actionType" value="">
<input type="hidden" name="operateId" value="-1">



</html:form> 
</body>
</html>
