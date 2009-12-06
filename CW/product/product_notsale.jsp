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
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="javascript">
function getCategory(){
	
	//openWin("/mrm/product/product_category_query.jsp","2005",600,400);
	openWin("prdCatQuery.do","2005",600,400);

}
function getReturnCate(selectedid){
	document.forms[0].item_category.value= selectedid;
	
}

function getProvider(para){
	
	document.forms[0].flag.value = para;
	openWin("providerQuery.do","wins",600,400);
	
	
}

function getProduct(para){
	
	var owin = openWin("productQuery.do?actn=selectProduct","wins",700,400);
	
	
}

function getOpenwinValue(ret){
	//ret数组，ret[0]:产品ID	ret[1]:货号
	document.forms[0].replaceItemID.value = ret[0];
}

function checkInput(){
	var form = document.forms[0];

	if(trim(form.item_name.value) == ""){
		alert("产品名称不能为空！");
		form.item_name.select();
		return false;
	}
	if(trim(form.item_category.value) == "0"){
		alert("产品类别不能为空！");
		form.item_category.select();
		return false;
	}
	
	if(trim(form.standard_price.value) == ""){
		alert("产品定价不能为空！");
		form.standard_price.select();
		return false;
	}else if (!is_number(form.standard_price.value)){
			alert("产品定价包含非法字符！");
			form.standard_price.select();
			return false;
			
		//}
	}
	/*if(trim(form.item_cost.value) == ""){
		alert("产品成本不能为空！");
		form.item_cost.select();
		return false;
	}else if (!is_number(form.item_cost.value)){
			alert("产品成本包含非法字符！");
			form.item_cost.select();
			return false;
	}*/
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
	
	if(isNaN(form.max_count.value)&&form.max_count.value!=""){
	alert('最大销售数量必须是数字!');
	form.max_count.select();
	return false;
	}     
  
  form.submitButton.disabled=true;
	return true;
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
      	<td><font color="red">
	        <%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	    </font>
      	</td>
   </tr>
</table>
<html:form action="/productAdd.do" method="post" onsubmit="return checkInput();">
 
<table width="80%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td  align="right"  class="inputLabel" noWrap >&nbsp;货号</td>
    <td  align="left" >&nbsp; <html:text property="item_code" maxlength="10"/>
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;产品名称</td>
    <td align="left" >&nbsp; <html:text property="item_name" maxlength="50"/> </td>
    <td  align="right"  class="inputLabel" noWrap >&nbsp;英文名称</td>
    <td  align="left" >&nbsp; <html:text property="item_name_en" maxlength="50"/></td>
  </tr>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;产品单位</td>
    <td align="left" >&nbsp; <html:select property="item_unit" >
        <option value="3" selected>件</option>
        <option value="2" >套</option>
        <option value="4" >盒</option>
        <option value="1" >包</option>
      </html:select> </td>
      <html:hidden property="item_type" value="0"/>
   
    
  </tr>
<td align="right"  class="inputLabel" noWrap  >&nbsp;条形码</td>
    <td align="left" >&nbsp; <html:text property="barcode"  maxlength="13"/> </td>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  >&nbsp;产品描述</td>
    <td align="left" colspan="3">&nbsp; <html:textarea cols="70" rows="4" property="item_desc" />
    </td>
  </tr>
</table>

<table width="80%" border=0 cellspacing=1 cellpadding=1  align="center" >
  <tr> 
    <td colspan=4  class="oraTableRowHeader" >&nbsp;对应sku</td>
  </tr>
  <tr>
	<html:hidden property="max_count"  value="1"/> 
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;默认市场价</td>
    <td align="left" >&nbsp; <html:text property="standard_price" value=""/> </td>
    <!--
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>默认成本价</font></td>
    <td align="left" >&nbsp; <html:text property="item_cost" /></td>-->
  </tr>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;默认销售价</td>
    <td align="left" >&nbsp; <html:text property="sale_price" value="" /> 
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;默认VIP价</td>
    <td align="left" >&nbsp; <html:text property="vip_price" value="" /> 
    </td>
  </tr>
</table>

<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >  
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button5" value=" 保存 " >
      <input name="flag" type="hidden" value="">
  </tr>
</table>

</html:form>

</body>
</html>

