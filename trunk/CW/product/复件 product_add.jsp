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
<title>������Ա��ϵ����ϵͳ</title>
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
	//ret���飬ret[0]:��ƷID	ret[1]:����
	document.forms[0].replaceItemID.value = ret[0];
}

function checkInput(){
	var form = document.forms[0];

	if(trim(form.name.value) == ""){
		alert("��Ʒ���Ʋ���Ϊ�գ�");
		form.name.select();
		return false;
	}
	if(trim(form.categoryID.value) == ""){
		alert("��Ʒ�����Ϊ�գ�");
		form.categoryID.select();
		return false;
	}
	
	if(trim(form.standardPrice.value) == ""){
		alert("��Ʒ���۲���Ϊ�գ�");
		form.standardPrice.select();
		return false;
	}else if (!is_number(form.standardPrice.value)){
			alert("��Ʒ���۰����Ƿ��ַ���");
			form.standardPrice.select();
			return false;
			
		//}
	}
	if(trim(form.silverPrice.value) == ""){
		alert("��Ʒ�����۲���Ϊ�գ�");
		form.silverPrice.select();
		return false;
	}else if (!is_number(form.silverPrice.value)){
			alert("��Ʒ�����۰����Ƿ��ַ���");
			form.silverPrice.select();
			return false;
	}
	if(trim(form.godenPrice.value) == ""){
		alert("��Ʒ�𿨼۲���Ϊ�գ�");
		form.godenPrice.select();
		return false;
	}else if (!is_number(form.godenPrice.value)){
			alert("��Ʒ�𿨼۰����Ƿ��ַ���");
			form.godenPrice.select();
			return false;
	}
	if(trim(form.platina_Price.value) == ""){
		alert("��Ʒ�׽𿨼۲���Ϊ�գ�");
		form.platina_Price.focus();
		return false;
	}else if (!is_number(form.platina_Price.value)){
			alert("��Ʒ�׽𿨼۰����Ƿ��ַ���");
			form.platina_Price.focus();
			return false;
	}	
	
	
	if(trim(form.discount.value) == ""){
		alert("��Ʒ�ۿ۲���Ϊ�գ�");
		form.discount.select();
		return false;
	}else{
		
		if (! is_number(form.discount.value)){
			alert("��Ʒ�ۿ۰����Ƿ��ַ���");
			form.discount.select();
			return false;
		}
		if (form.discount.value >= 1){
			alert("��Ʒ�ۿ۲��ܴ���һ��");
			form.discount.select();
			return false;
		}
	}
	if(form.productOwnerID.selectedIndex == 0){
		alert("��ѡ���Ʒ�༭��");
		return false;
	}
	if(form.tax.selectedIndex == 0){
		alert("��ѡ���Ʒ˰�ʣ�");
		return false;
	}
	if(trim(form.supplierID.value) == ""){
		alert("��Ӧ�̲���Ϊ�գ�");
		form.supplierID.select();
		return false;
	}	
     if(form.barCode.value.length>0){
	if(form.barCode.value.length!=12 && form.barCode.value.length!=13){
		alert("����������12λ�����13λ��");
		return false;
	}
     }	
	if(isNaN(form.maxsalenum.value)&&form.maxsalenum.value!=""){
	alert('���������������������!');
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">������Ʒ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productAdd.do" method="post" onsubmit="return checkInput();">
 
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;����</td>
    <td width="196" align="left" >&nbsp; <input name="itemCode" id="itemCode" value="" maxlength="10"> 
    </td>
    <td width="133" align="right"  class="OraTableRowHeader" noWrap  >&nbsp;����/����</td>
    <td width="258" align="left" >&nbsp; <input name="author" value="" maxlength="25"> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <input name="name" id="name" value="" maxlength="100"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ��λ</td>
    <td align="left" >&nbsp; <select id="unit" name="unit" >
        <option value="">��ѡ��...</option>
        <option value="3" >��</option>
        <option value="5" >��</option>
        <option value="2" >��</option>
        <option value="0" selected>��</option>
        <option value="4" >��</option>
        <option value="1" >��</option>
      </select> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <select id="itemType" name="itemType" >
        <option value="">��ѡ��...</option>
        <option value="3" >����</option>
        <option value="5" >��Ʒ</option>
        <option value="2" >Ӱ��</option>
        <option value="4" >��Ϸ/���</option>
        <option value="6" >����</option>
        <option value="1" selected>ͼ��</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ���</td>
    <td align="left" >&nbsp; <input id="categoryID" name="categoryID" value=""> 
      <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <input name="standardPrice" value=""> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color="#000000">��վ��</font></td>
    <td align="left" >&nbsp; <input name="webPrice" value=""></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;������</td>
    <td align="left" >&nbsp; <input name="silverPrice" id="silverPrice" value=""> 
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�𿨼�</td>
    <td align="left" >&nbsp; <input name="godenPrice" id="godenPrice" value=""> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�׽𿨼�</td>
    <td align="left" >&nbsp; <input name="platina_Price" id="platina_Price" value=""> 
    </td>  
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�ʺ������</td>
    <td align="left" >&nbsp; <html:text property="ageSegment"/> (��ʽ: 2 ~ 5)
    </td>
    
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�ۿ�</td>
    <td align="left" >&nbsp; <input name="discount" value=""> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;˰��</td>
    <td align="left" >&nbsp; <html:select property="tax">
    								<option value="0"></option>
		                          <html:options collection="alltax" property="ID" labelProperty="name"/>
			                  </html:select></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <select id="peculiarity" name="peculiarity" >
        <option value="">��ѡ��...</option>
        <option value="0" selected>��װ</option>
        <option value="1" >��װ</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�ɹ��༭</td>
    <td align="left" >&nbsp; <html:select property="productOwnerID">
    								<option value="0"></option>
		                          <html:options collection="allUser" property="id" labelProperty="NAME"/>
			                  </html:select></td>
     
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ��Ӧ��</td>
    <td align="left" >&nbsp; <input id="supplierID" name="supplierID" value=""  > 
      <a href="javascript:getProvider('provider');">
      <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a></td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;���䷽ʽ</td>
    <td align="left" >&nbsp; <select id="deliveryType" name="deliveryType" >
        <option value="1" >����</option>
        <option value="3" >��Ӧ���ͻ�</option>
        <option value="2" >�����ͻ�</option>
      </select> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <input name="title" id="title" value="" maxlength="50"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <input id="publishingHouse" name="publishingHouse" value=""  > 
      <a href="javascript:getProvider('publish');">
      <img src="../images/icon_lookup.gif" border=0 align="top">
      </a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;CIP��</td>
    <td align="left" >&nbsp; <input name="icpCode" id="icpCode" value="" maxlength="20"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;ISBN</td>
    <td align="left" >&nbsp; <input name="isbn" id="isbn" value="" maxlength="20"> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <input name="barCode" value="" maxlength="13"> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�˻���</td>
    <td align="left" >&nbsp; <input name="returnRate" id="returnRate" value=""> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;���ʹ��״̬</td>
    <td align="left" >&nbsp; <select name="userStatus">
        <option value="1">YY</option>
        <option value="2">YN</option>
        <option value="3">NY</option>
        <option value="4">NN</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�����ƷID</td>
    <td align="left" >&nbsp; 
	<input id="replaceItemID" name="replaceItemID" readonly="true">
     <a href="javascript:getProduct();">
     <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ͬ����</td>
    <td align="left" >&nbsp; <select id="contractTitle" name="contractTitle" >
        <option value="0" >��Ӧ��</option>
        <option value="1" >������</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ����꼴ֹ</td>
    <td align="left" >&nbsp; <input type="radio" id="is_last_sel" name="isLastSel" value="0" checked >
      ��&nbsp; <input type="radio" id="is_last_sel" name="isLastSel" value="1" >
      ��&nbsp; </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ�Ԥ��</td>
    <td align="left"  >&nbsp; <input type="radio" id="isPresell" name="isPresell" value="0" checked >
      ��&nbsp; <input type="radio" id="isPresell" name="isPresell" value="1" >
      ��&nbsp; </td>
	<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;ѡ����ֲ�</td>
	<td align="left" >
	<select  name="club_id" > 	
	<option value="1" >99</option> 		
	<option value="2" >99���䱦��</option> 
	</select>			
	</td>      
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ�����˻�</td>
    <td align="left"  >&nbsp; 
      <html:radio property="isReturn" value="0" />
      ��&nbsp; 
      <html:radio property="isReturn" value="1" />
      ��&nbsp; </td>
	<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�˻�ʱ��</td>
	<td align="left" >
	
	<html:text property="returnDays" style="width:80px;height:21px;font-size:10pt;"/>
	<span style="width:18px;border:0px solid red;">
	<select name="r00" style="margin-left:-85px;width:98px; background-color:#FFEEEE;" onChange="document.all.returnDays.value=this.value;"> 
		<option value="0" >��</option>	
		<option value="30" >30</option> 		
		<option value="60" >60</option> 
		<option value="90" >90</option> 		
		<option value="180" >180</option> 
	</select> 
	</span> 		
	</td>      
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ʽ</td>
    <td align="left"  >&nbsp; 
      <html:radio property="balanceMethod" value="1" />
      ����&nbsp; 
      <html:radio property="balanceMethod" value="2" />
      �ֽ�&nbsp;
      <html:radio property="balanceMethod" value="3" /> 
      ����<br>
	  &nbsp;
	  <html:radio property="balanceMethod" value="4" /> 
      ʵ��ʵ��&nbsp;
      </td>
	<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;�˻�����</td>
	<td align="left" >
	<html:text property="returnGoodsRate" style="width:80px;height:21px;font-size:10pt;"/>
	<span style="width:18px;border:0px solid red;">
	<select name="r00" style="margin-left:-85px;width:98px; background-color:#FFEEEE;" onChange="document.all.returnGoodsRate.value=this.value;"> 
		<option value="0" >��</option>	
		<option value="0.1" >10%</option> 		
		<option value="0.2" >20%</option> 	
	</select> 
	</span> 				
	</td>      
  </tr>  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�����������</td>
    <td align="left" >&nbsp; <input name="maxsalenum" id="maxsalenum" value="8"> 
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ���վ��Ʒ</td>
    <td align="left" >
      <html:radio property="is_Web" value="0" />
      ��&nbsp; 
      <html:radio property="is_Web" value="1" />
      ��&nbsp; </td>    
    </td>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ע</td>
    <td align="left" colspan="3">&nbsp; <textarea cols=70 rows=2 name="comments" ></textarea> 
    </td>
    
  </tr>
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button2" value=" ȷ�� " > 
      &nbsp; <input type="button" class="button2" value=" ȡ�� " onClick="history.back();">
      <input name="flag" type="hidden" value="">
  </tr>
</table>
</html:form>

</body>
</html>

