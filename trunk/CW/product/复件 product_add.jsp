<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
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

	if(trim(form.name.value) == ""){
		alert("产品名称不能为空！");
		form.name.select();
		return false;
	}
	if(trim(form.categoryID.value) == ""){
		alert("产品类别不能为空！");
		form.categoryID.select();
		return false;
	}
	
	if(trim(form.standardPrice.value) == ""){
		alert("产品定价不能为空！");
		form.standardPrice.select();
		return false;
	}else if (!is_number(form.standardPrice.value)){
			alert("产品定价包含非法字符！");
			form.standardPrice.select();
			return false;
			
		//}
	}
	if(trim(form.silverPrice.value) == ""){
		alert("产品银卡价不能为空！");
		form.silverPrice.select();
		return false;
	}else if (!is_number(form.silverPrice.value)){
			alert("产品银卡价包含非法字符！");
			form.silverPrice.select();
			return false;
	}
	if(trim(form.godenPrice.value) == ""){
		alert("产品金卡价不能为空！");
		form.godenPrice.select();
		return false;
	}else if (!is_number(form.godenPrice.value)){
			alert("产品金卡价包含非法字符！");
			form.godenPrice.select();
			return false;
	}
	if(trim(form.platina_Price.value) == ""){
		alert("产品白金卡价不能为空！");
		form.platina_Price.focus();
		return false;
	}else if (!is_number(form.platina_Price.value)){
			alert("产品白金卡价包含非法字符！");
			form.platina_Price.focus();
			return false;
	}	
	
	
	if(trim(form.discount.value) == ""){
		alert("产品折扣不能为空！");
		form.discount.select();
		return false;
	}else{
		
		if (! is_number(form.discount.value)){
			alert("产品折扣包含非法字符！");
			form.discount.select();
			return false;
		}
		if (form.discount.value >= 1){
			alert("产品折扣不能大于一！");
			form.discount.select();
			return false;
		}
	}
	if(form.productOwnerID.selectedIndex == 0){
		alert("请选则产品编辑！");
		return false;
	}
	if(form.tax.selectedIndex == 0){
		alert("请选则产品税率！");
		return false;
	}
	if(trim(form.supplierID.value) == ""){
		alert("供应商不能为空！");
		form.supplierID.select();
		return false;
	}	
     if(form.barCode.value.length>0){
	if(form.barCode.value.length!=12 && form.barCode.value.length!=13){
		alert("条形码至少12位，最多13位！");
		return false;
	}
     }	
	if(isNaN(form.maxsalenum.value)&&form.maxsalenum.value!=""){
	alert('最大销售数量必须是数字!');
	form.maxsalenum.select();
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
      		-&gt; </font><font color="838383">新增产品</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productAdd.do" method="post" onsubmit="return checkInput();">
 
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;货号</td>
    <td width="196" align="left" >&nbsp; <input name="itemCode" id="itemCode" value="" maxlength="10"> 
    </td>
    <td width="133" align="right"  class="OraTableRowHeader" noWrap  >&nbsp;作者/译者</td>
    <td width="258" align="left" >&nbsp; <input name="author" value="" maxlength="25"> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品名称</td>
    <td align="left" >&nbsp; <input name="name" id="name" value="" maxlength="100"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品单位</td>
    <td align="left" >&nbsp; <select id="unit" name="unit" >
        <option value="">请选择...</option>
        <option value="3" >件</option>
        <option value="5" >把</option>
        <option value="2" >套</option>
        <option value="0" selected>本</option>
        <option value="4" >盒</option>
        <option value="1" >包</option>
      </select> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品类型</td>
    <td align="left" >&nbsp; <select id="itemType" name="itemType" >
        <option value="">请选择...</option>
        <option value="3" >音乐</option>
        <option value="5" >礼品</option>
        <option value="2" >影视</option>
        <option value="4" >游戏/软件</option>
        <option value="6" >其他</option>
        <option value="1" selected>图书</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品类别</td>
    <td align="left" >&nbsp; <input id="categoryID" name="categoryID" value=""> 
      <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品定价</td>
    <td align="left" >&nbsp; <input name="standardPrice" value=""> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color="#000000">网站价</font></td>
    <td align="left" >&nbsp; <input name="webPrice" value=""></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;银卡价</td>
    <td align="left" >&nbsp; <input name="silverPrice" id="silverPrice" value=""> 
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;金卡价</td>
    <td align="left" >&nbsp; <input name="godenPrice" id="godenPrice" value=""> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;白金卡价</td>
    <td align="left" >&nbsp; <input name="platina_Price" id="platina_Price" value=""> 
    </td>  
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;适合年龄段</td>
    <td align="left" >&nbsp; <html:text property="ageSegment"/> (格式: 2 ~ 5)
    </td>
    
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;折扣</td>
    <td align="left" >&nbsp; <input name="discount" value=""> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;税率</td>
    <td align="left" >&nbsp; <html:select property="tax">
    								<option value="0"></option>
		                          <html:options collection="alltax" property="ID" labelProperty="name"/>
			                  </html:select></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品特性</td>
    <td align="left" >&nbsp; <select id="peculiarity" name="peculiarity" >
        <option value="">请选择...</option>
        <option value="0" selected>简装</option>
        <option value="1" >精装</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;采购编辑</td>
    <td align="left" >&nbsp; <html:select property="productOwnerID">
    								<option value="0"></option>
		                          <html:options collection="allUser" property="id" labelProperty="NAME"/>
			                  </html:select></td>
     
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;产品供应商</td>
    <td align="left" >&nbsp; <input id="supplierID" name="supplierID" value=""  > 
      <a href="javascript:getProvider('provider');">
      <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a></td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;运输方式</td>
    <td align="left" >&nbsp; <select id="deliveryType" name="deliveryType" >
        <option value="1" >自提</option>
        <option value="3" >供应商送货</option>
        <option value="2" >代理送货</option>
      </select> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;副标题</td>
    <td align="left" >&nbsp; <input name="title" id="title" value="" maxlength="50"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;出版社</td>
    <td align="left" >&nbsp; <input id="publishingHouse" name="publishingHouse" value=""  > 
      <a href="javascript:getProvider('publish');">
      <img src="../images/icon_lookup.gif" border=0 align="top">
      </a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;CIP号</td>
    <td align="left" >&nbsp; <input name="icpCode" id="icpCode" value="" maxlength="20"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;ISBN</td>
    <td align="left" >&nbsp; <input name="isbn" id="isbn" value="" maxlength="20"> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;条形码</td>
    <td align="left" >&nbsp; <input name="barCode" value="" maxlength="13"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;退换率</td>
    <td align="left" >&nbsp; <input name="returnRate" id="returnRate" value=""> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;库存使用状态</td>
    <td align="left" >&nbsp; <select name="userStatus">
        <option value="1">YY</option>
        <option value="2">YN</option>
        <option value="3">NY</option>
        <option value="4">NN</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;替代产品ID</td>
    <td align="left" >&nbsp; 
	<input id="replaceItemID" name="replaceItemID" readonly="true">
     <a href="javascript:getProduct();">
     <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;合同标题</td>
    <td align="left" >&nbsp; <select id="contractTitle" name="contractTitle" >
        <option value="0" >供应商</option>
        <option value="1" >出版社</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否售完即止</td>
    <td align="left" >&nbsp; <input type="radio" id="is_last_sel" name="isLastSel" value="0" checked >
      否&nbsp; <input type="radio" id="is_last_sel" name="isLastSel" value="1" >
      是&nbsp; </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否预售</td>
    <td align="left"  >&nbsp; <input type="radio" id="isPresell" name="isPresell" value="0" checked >
      否&nbsp; <input type="radio" id="isPresell" name="isPresell" value="1" >
      是&nbsp; </td>
	<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;选择俱乐部</td>
	<td align="left" >
	<select  name="club_id" > 	
	<option value="1" >99</option> 		
	<option value="2" >99妈咪宝贝</option> 
	</select>			
	</td>      
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否可以退货</td>
    <td align="left"  >&nbsp; 
      <html:radio property="isReturn" value="0" />
      否&nbsp; 
      <html:radio property="isReturn" value="1" />
      是&nbsp; </td>
	<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;退货时间</td>
	<td align="left" >
	
	<html:text property="returnDays" style="width:80px;height:21px;font-size:10pt;"/>
	<span style="width:18px;border:0px solid red;">
	<select name="r00" style="margin-left:-85px;width:98px; background-color:#FFEEEE;" onChange="document.all.returnDays.value=this.value;"> 
		<option value="0" >无</option>	
		<option value="30" >30</option> 		
		<option value="60" >60</option> 
		<option value="90" >90</option> 		
		<option value="180" >180</option> 
	</select> 
	</span> 		
	</td>      
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;结款方式</td>
    <td align="left"  >&nbsp; 
      <html:radio property="balanceMethod" value="1" />
      代销&nbsp; 
      <html:radio property="balanceMethod" value="2" />
      现结&nbsp;
      <html:radio property="balanceMethod" value="3" /> 
      包销<br>
	  &nbsp;
	  <html:radio property="balanceMethod" value="4" /> 
      实销实结&nbsp;
      </td>
	<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;退货比例</td>
	<td align="left" >
	<html:text property="returnGoodsRate" style="width:80px;height:21px;font-size:10pt;"/>
	<span style="width:18px;border:0px solid red;">
	<select name="r00" style="margin-left:-85px;width:98px; background-color:#FFEEEE;" onChange="document.all.returnGoodsRate.value=this.value;"> 
		<option value="0" >无</option>	
		<option value="0.1" >10%</option> 		
		<option value="0.2" >20%</option> 	
	</select> 
	</span> 				
	</td>      
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;最大销售数量</td>
    <td align="left" >&nbsp; <input name="maxsalenum" id="maxsalenum" value="8"> 
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;是否网站产品</td>
    <td align="left" >
      <html:radio property="is_Web" value="0" />
      否&nbsp; 
      <html:radio property="is_Web" value="1" />
      是&nbsp; </td>    
    </td>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;备注</td>
    <td align="left" colspan="3">&nbsp; <textarea cols=70 rows=2 name="comments" ></textarea> 
    </td>
    
  </tr>
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      <input name="flag" type="hidden" value="">
  </tr>
</table>
</html:form>

</body>
</html>

