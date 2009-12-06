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
function set_add() {
    //document.forms[0].action="product2SetDtl.do?type=init&set_item_code=" +set_id;
    if(document.forms[0].op_type.value=="insert") {
        alert("没有保存主信息不能进行明细操作");
        return;
    }
    
    document.forms[0].action="product2Set.do?type=addItem";
    document.forms[0].submit();
}
function set_modify(id,index) {
    document.forms[0].action="product2Set.do?type=modifyItem&set_id="+id+ "&index=" +index;
    document.forms[0].submit();
}

function set_delete(id) {
    document.forms[0].action="product2Set.do?type=deleteItem&set_id="+id;
    document.forms[0].submit();
}

function getCategory(){
	
	//openWin("/mrm/product/product_category_query.jsp","2005",600,400);
	openWin("prdCatQuery.do","2005",600,400);

}

function getReturnCate(selectedid){
	document.forms[0].item_category.value= selectedid;
	
}

function submitForm(para){
	var form = document.forms[0];
	var url = "";
	
	switch(para){
		case "1":
			if (!checkInput("add")) return false;
			url = "productSetAdd.do?actn=add";
			break;
		case "2":
			if(! checkInput("submit")) return false;
			url = "productSetAdd.do?actn=submit";
			break;
		
	}
	form.action = url;
	form.submit();
}



function getProduct(para){
	
	var owin = openWin("product2Query.do?type=init","wins",700,400);
	
}

function getOpenwinValue(ret){
	document.forms[0].part_item_code.value = ret;
}

function checkInput(){
	var form = document.forms[0];
    if(trim(form.item_code.value) == ""){
		alert("产品货号不能为空！");
		form.item_name.select();
		return false;
	}
	
	if(trim(form.item_name.value) == ""){
		alert("产品名称不能为空！");
		form.item_name.select();
		return false;
	}
	
	
	if(form.item_type.value == "") {
	    alert("产品类型不能为空");
	    return false;
	}
	
	if(trim(form.item_category.value) == ""||trim(form.item_category.value) == "0"){
		alert("产品类别不能为空！");
		form.item_category.select();
		return false;
	}
	
	/*if(trim(form.standard_price.value) == ""){
		alert("产品定价不能为空！");
		form.standard_price.select();
		return false;
	}else if (!is_number(form.standard_price.value)){
			alert("产品定价包含非法字符！");
			form.standard_price.select();
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
	}*/
	
	if(form.barcode.value.length>0){
	if(form.barcode.value.length!=12 && form.barcode.value.length!=13){
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
<html:form action="/product2Set.do?type=save" method="post" onsubmit="return checkInput();">
 
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;货号</td>
    <td width="196" align="left" >&nbsp; <html:text property="item_code" maxlength="10" /> 
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品名称</td>
    <td align="left" >&nbsp; <html:text property="item_name" maxlength="50"/> </td>
  </tr>
  <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;英文名称</td>
    <td width="196" align="left" >&nbsp; <html:text property="item_name_en" maxlength="50"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;条形码</td>
    <td align="left" >&nbsp; <html:text property="barcode"  maxlength="13"/> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品类型</td>
    <td align="left" >&nbsp; <html:select  property="item_type" >
        <html:option value="">请选择...</html:option>
        <html:option value="2" >系列商品</html:option>
        <html:option value="3" >套装商品</html:option>
      </html:select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品主分类</td>
    <td align="left" >&nbsp; <html:text property="item_category" /> 
      <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a></td>
  </tr>
  <tr> 
    
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;最大销售数量</td>
    <td align="left" >&nbsp; <html:text property="max_count" /> 
    <input type=hidden name="item_unit" value="2"> 
    </td>
  </tr>
</table>

<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >  
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button5" value=" 保存 " ></td>
      <html:hidden property="op_type" />
  </tr>
</table>

<table width="750" border="0" cellspacing=1 cellpadding=5 align="center" >
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;市场价</td>
    <td align="left" >&nbsp; <bean:write name="product2SetForm" property="standard_price"  /> 
    <html:hidden property="standard_price"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;促销价</td>
    <td align="left" >&nbsp;<bean:write name="product2SetForm" property="web_price" />
    <html:hidden property="web_price"/>
    <html:hidden property="item_cost"/> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;会员价</td>
    <td align="left" >&nbsp; <bean:write name="product2SetForm" property="sale_price"  /> 
    <html:hidden property="sale_price"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;VIP价</td>
    <td align="left" >&nbsp;<bean:write name="product2SetForm" property="vip_price" />
    <html:hidden property="vip_price"/> 
    </td>
  </tr>
</table>

<table width="750" border="0" cellspacing=1 cellpadding=5  align="center" >
  <tr>
    <td><span class="OraHeader">套件列表</span>
    </td>
  </tr>
</table>
<table width="95%" border="0" cellspacing="1" cellpadding="5"  align="center">

<tr> 
	<td noWrap align=left> 货号:<html:text property="part_item_code" />
	<input type="hidden" name="set_item_code" value="<bean:write name="product2SetForm" property="item_code"/>">
				<a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     			</a>
     			
     			<html:select property="color_code">
     			<html:option value="">全部</html:option>
     			<html:optionsCollection   property="colors" />
     			</html:select>
     			
     			<input name="buttOK" type="button" value="确定"  onclick="set_add();">
            </td>
		            
          </tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
  <tr align="center" valign="middle"> 
    <td class="OraTableRowHeader" noWrap  align=middle >货号</td>
    <td class="OraTableRowHeader" noWrap  align=middle >名称</td>
    <td class="OraTableRowHeader" noWrap  align=middle >颜色</td>
    <td class="OraTableRowHeader" noWrap  align=middle >市场价</td>
    <td class="OraTableRowHeader" noWrap  align=middle >会员价</td>
    <td class="OraTableRowHeader" noWrap  align=middle >vip价</td>
    <td class="OraTableRowHeader" noWrap  align=middle >促销价</td>
    <td class="OraTableRowHeader" noWrap  align=middle ><!--<input type=button class="button5" value="新增" onclick="set_add('<bean:write name="product2SetForm" property="item_code"/>');">--></td> 
  </tr>
    <bean:define id="items" name="product2SetForm" property="items" type="java.util.Collection"/> 
    <% int count=0; %>
    <logic:iterate id="item_part" name="items">
    <tr>
    <td noWrap align=middle ><bean:write name="item_part" property="part_item_code" filter="false"/></td>
    <td noWrap align=middle ><bean:write name="item_part" property="part_item_name" filter="false"/></td>
    <td noWrap align=middle ><bean:write name="item_part" property="color_name" filter="false"/></td>
    <!--<td>
    <logic:equal name="item_part" property="is_optional" value="1">是</logic:equal>
    <logic:notEqual name="item_part" property="is_optional" value="1">否</logic:notEqual>
    </td>-->
    
    <td noWrap align=middle ><input type=text name="part_item_std_price" value='<bean:write name="item_part" property="standard_price" filter="false" format="0.00"/>'></td>
    <td noWrap align=middle ><input type=text name="part_item_sale_price" value='<bean:write name="item_part" property="sale_price" filter="false" format="0.00"/>'></td>
    <td noWrap align=middle ><input type=text name="part_item_vip_price" value='<bean:write name="item_part" property="vip_price" filter="false" format="0.00"/>'></td>
    <td noWrap align=middle ><input type=text name="part_item_web_price" value='<bean:write name="item_part" property="web_price" filter="false" format="0.00"/>'></td>
    <td><input type=button value="修改" onclick="set_modify('<bean:write name="item_part" property="set_id" filter="false"/>','<%= count %>')" >
        <input type=button value="删除" onclick="set_delete('<bean:write name="item_part" property="set_id" filter="false"/>')" >
    </td>
    </tr>
    <% count++; %>
    </logic:iterate>
</table>

</html:form>

</body>
</html>

