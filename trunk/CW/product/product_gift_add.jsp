<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>

<%@ page import="com.magic.crm.product.entity.Product"%>

<%@ page import="java.sql.*" %>
<%@ page import="com.magic.crm.util.DBManager" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="javascript">

function item_code_change() {
    document.forms[0].action = "productSKU.do?type=addinit";
    document.forms[0].submit();
}

function getCategory(){
	
	//openWin("/mrm/product/product_category_query.jsp","2005",600,400);
	openWin("prdCatQuery.do","2005",600,400);
	/*
	var vReturnValue = openModalDialog("/mrm/product/product_category_query.jsp",2005,600,400);
	if ((vReturnValue== -1)||(vReturnValue== null)){
 		alert("no");
	}else{
 		alert("yes");;
	}	      
	*/
}

function getReturnCate(selectedid){
	document.forms[0].item_category.value= selectedid;
	
}


function getProduct(para){
	
	var owin = openWin("productQuery.do?actn=selectProduct","wins",700,400);
	
	
}


function checkInput(){
	var form = document.forms[0];

	if(trim(form.item_code.value) == ""){
		alert("货号不能为空！");
		form.item_code.select();
		return false;
	}
	
	if(trim(form.item_name.value) == ""){
		alert("产品名称不能为空！");
		form.item_name.select();
		return false;
	}
	
	if(trim(form.standard_price.value) == ""){
		alert("产品市场价不能为空！");
		form.standard_price.select();
		return false;
	}else if (!is_number(form.standard_price.value)){
			alert("产品市场价包含非法字符！");
			form.standard_price.select();
			return false;
			
		//}
	}
	if(trim(form.sale_price.value) == ""){
		alert("产品成本不能为空！");
		form.sale_price.select();
		return false;
	}else if (!is_number(form.sale_price.value)){
			alert("产品成本包含非法字符！");
			form.sale_price.select();
			return false;
	}
	if(trim(form.sale_price.value) == ""){
		alert("产品售价不能为空！");
		form.sale_price.select();
		return false;
	}else if (!is_number(form.sale_price.value)){
			alert("产品售价包含非法字符！");
			form.sale_price.select();
			return false;
	}
	if(trim(form.vip_price.value) == ""){
		alert("产品VIP价不能为空！");
		form.vip_price.select();
		return false;
	}else if (!is_number(form.vip_price.value)){
			alert("产品VIP价包含非法字符！");
			form.vip_price.select();
			return false;
	}
	
	if(form.barcode.value.length>0){
	if(form.barcode.value.length!=12 && form.barcode.value.length!=13){
		alert("条形码至少12位，最多13位！");
		return false;
	}
    }	
    
}
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="30">
    <tr>
      	<td width="30">&nbsp;</td>
    	<td valign="bottom">
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增赠品</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productSKU.do?type=addGift" method="post" onsubmit="return checkInput();">
 
<table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td align="right" ><font color=red>*</font>&nbsp;货号</td>
    <td align="left" >&nbsp; <html:text property="item_code" maxlength="10" /></td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;产品名称</td>
    <td align="left" >&nbsp; <html:text property="item_name" maxlength="50"/> </td>
    <td align="right"  class="inputLabel" noWrap >&nbsp;英文名称</td>
    <td align="left" >&nbsp; <html:text property="item_name_en" maxlength="50"/></td>
  </tr>
  
  <tr> 
    <td align="right"  >&nbsp;条形码</td>
    <td align="left" >&nbsp; <html:text property="barcode" maxlength="13"/> </td>
    <td align="right"   ><font color=red>*</font>&nbsp;产品单位</td>
    <td align="left" >&nbsp; <html:select property="item_unit" >
        <option value="3" >件</option>
        <option value="2" >套</option>
        <option value="4" >盒</option>
        <option value="1" >包</option>
      </html:select> </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;产品大类</td>
    <td align="left" >&nbsp; <html:text property="item_category" readonly="true" size="8"/> 
      <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a></td>
  </tr>
  <tr> 
    <td align="right"  ><font color=red>*</font>&nbsp;产品市场价</td>
    <td align="left" >&nbsp; <html:text property="standard_price"/> </td>
    <td align="right"  ><font color=red>*</font>&nbsp;销售价</td>
    <td align="left" >&nbsp; <html:text property="sale_price"/> 
    </td>
    <td align="right" ><font color=red>*</font>&nbsp;VIP价</td>
    <td align="left" >&nbsp; <html:text property="vip_price"/>
    </td>
  </tr>
  <!--<tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否OVS</td>
    <td align="left" >&nbsp;  <html:radio property="enable_os" value="1"/>是<html:radio property="enable_os" value="0"/>否</td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;OVS数量</td>
    <td align="left" >&nbsp;  <html:text property="os_qty"  maxlength="8"/></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;最大销售数量</td>
    <td align="left" >&nbsp; <html:text property="max_count"/> 
    </td>
  </tr>
  -->
  <tr> 
    <td align="right"  class="inputLabel" noWrap  >&nbsp;产品描述</td>
    <td align="left" colspan="5">&nbsp; <html:textarea cols="70" rows="4" property="item_desc" />
    </td>
  </tr>
</table>

<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >  
  <tr align="center" valign="middle"> 
    <td height="32" > 
      <input name = "submitButton" id="submitButton" type="submit" class="button5" value=" 保存 " >
      
  </tr>
</table>

</html:form>

</body>
</html>

