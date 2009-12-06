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
	/*
	var vReturnValue = openModalDialog("/mrm/product/product_category_query.jsp",2005,600,400);
	if ((vReturnValue== -1)||(vReturnValue== null)){
 		alert("no");
	}else{
 		alert("yes");;
	}	      
	*/
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
	if(trim(form.categoryID.value) == ""){
		alert("产品类别不能为空！");
		form.categoryID.select();
		return false;
	}
	
	if(trim(form.standard_price.value) == ""){
		alert("产品定价不能为空！");
		form.standard_price.select();
		return false;
	}else if (!is_number(form.standard_price.value)){
			alert("产品定价包含非法字符！");
			form.standardPrice.select();
			return false;
			
		//}
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
	
	if(form.barCode.value.length>0){
	if(form.barCode.value.length!=12 && form.barCode.value.length!=13){
		alert("条形码至少12位，最多13位！");
		return false;
	}
     }	
	if(isNaN(form.max_count.value)&&form.max_count.value!=""){
	alert('最大销售数量必须是数字!');
	form.max_count.select();
	return false;
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
      		-&gt; </font><font color="838383">套装设置</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/product2SetDtl.do?type=save" method="post" onsubmit="return checkInput();">
 
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;套装编码</td>
    <td width="196" align="left" >&nbsp; <html:text property="set_item_code" maxlength="18" readonly="true"/> 
    </td>
  </tr>
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;套件编码</td>
    <td width="196" align="left" >&nbsp; <html:text property="part_item_code" maxlength="18" /> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;颜色</td>
    <td align="left" >&nbsp; <html:select  property="color_code" >
        <html:option value="">全部</html:option>
        <html:optionsCollection   property="colors" />
      </html:select> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品定价</td>
    <td align="left" >&nbsp; <input name="standard_price" value=""> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;销售价</td>
    <td align="left" >&nbsp; <input name="sale_price"  value=""> 
    </td>
  </tr>

  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否可选</td>
    <td align="left" >&nbsp; <html:radio property="is_optional" value="1"/>可选<html:radio property="is_optional" value="0"/>必选</td>
  </tr>

</table>

<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >  
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button5" value=" 保存" >
      <html:hidden property="op_type"/>
  </tr>
</table>

</html:form>

</body>
</html>

