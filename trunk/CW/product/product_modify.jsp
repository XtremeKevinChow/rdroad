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
<title>������Ա��ϵ����ϵͳ</title>
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
	//ret���飬ret[0]:��ƷID	ret[1]:����
	document.forms[0].replaceItemID.value = ret[0];
}

function checkInput(){
	var form = document.forms[0];

	if(trim(form.item_name.value) == ""){
		alert("��Ʒ���Ʋ���Ϊ�գ�");
		form.item_name.select();
		return false;
	}
	if(trim(form.item_category.value) == "0"){
		alert("��Ʒ�����Ϊ�գ�");
		form.item_category.select();
		return false;
	}
	
	if(trim(form.standard_price.value) == ""){
		alert("��Ʒ�г��۲���Ϊ�գ�");
		form.standard_price.select();
		return false;
	}else if (!is_number(form.standard_price.value)){
			alert("��Ʒ�г��۰����Ƿ��ַ���");
			form.standard_price.select();
			return false;
	}
	if(trim(form.sale_price.value) == ""){
		alert("��Ʒ�ۼ۲���Ϊ�գ�");
		form.sale_price.select();
		return false;
	}else if (!is_number(form.sale_price.value)){
			alert("��Ʒ�ۼ۰����Ƿ��ַ���");
			form.sale_price.select();
			return false;
	}
	if(trim(form.vip_price.value) == ""){
		alert("��ƷVIP�۲���Ϊ�գ�");
		form.vip_price.select();
		return false;
	}else if (!is_number(form.vip_price.value)){
			alert("��ƷVIP�۰����Ƿ��ַ���");
			form.vip_price.select();
			return false;
	}
	
	/*if(form.barcode.value.length>0){
	if(form.barcode.value.length!=12 && form.barcode.value.length!=13){
		alert("����������12λ�����13λ��");
		return false;
	}
    }
    */
     	
	if(isNaN(form.max_count.value)&&form.max_count.value!=""){
	alert('���������������������!');
	form.max_count.select();
	return false;
	} 

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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�޸Ļ���</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productModify.do" method="post" onsubmit="return checkInput();">
 
<table width="80%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td align="right"  class="inputLabel" noWrap >&nbsp;����</td>
    <td align="left" >&nbsp; <html:text property="item_code" maxlength="10" readonly="true"/> 
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <html:text property="item_name" maxlength="50"/> </td>
    <td align="right"  class="inputLabel" noWrap >&nbsp;Ӣ������</td>
    <td align="left" >&nbsp; <html:text property="item_name_en" maxlength="50"/>
    </td>
    </tr>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;��Ʒ��λ</td>
    <td align="left" >&nbsp; <select id="unit" name="unit" >
        <option value="3" selected>��</option>
        <option value="2" >��</option>
        <option value="4" >��</option>
        <option value="1" >��</option>
      </select> </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <html:select  property="item_type" >
        <html:option value="0" >����</html:option>
        <html:option value="1" >��ͨ��Ʒ</html:option>
        <!--<option value="2" >ϵ����Ʒ</option>
        <option value="3" >��װ��Ʒ</option>-->
      </html:select> </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;��Ʒ������</td>
    <td align="left" >&nbsp; <html:text property="item_category" size="8"/> 
      <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a></td>
  </tr>
  <!--
  <tr> 
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;��Ʒ�г���</td>
    <td align="left" >&nbsp; <html:text property="standard_price"/> </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>�ɱ���</font></td>
    <td align="left" >&nbsp; <html:text property="item_cost" /></td>
  </tr>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;���ۼ�</td>
    <td align="left" >&nbsp; <html:text property="sale_price"  /> 
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;VIP��</td>
    <td align="left" >&nbsp; <html:text property="vip_price"  /> 
    </td>
  </tr>
  	<!--
    <td align="right"  class="inputLabel" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <html:text property="barcode"  maxlength="13"/> </td>
    <td align="right"  class="inputLabel" noWrap  >&nbsp;�����������</td>
    <td align="left" >&nbsp; <html:text property="max_count" /> 
    </td>-->
  <tr> 
		<td align="right"  class="inputLabel" noWrap  >&nbsp;����</td>
    <td align="left" >&nbsp; <html:text property="item_origin"  /> 
    </td>
    <td align="right"  class="inputLabel" noWrap  >&nbsp;����</td>
    <td align="left" >&nbsp;  <html:text property="item_fabric" maxlength="25"/></td>
    <td align="right"  class="inputLabel" noWrap  >&nbsp;����</td>
    <td align="left" >&nbsp;  <html:text property="item_lining" maxlength="25"/></td>
  </tr>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  >&nbsp;����</td>
    <td align="left" >&nbsp;  <html:text property="item_bottom"  maxlength="25"/></td>
    <td align="right"  class="inputLabel" noWrap  >&nbsp;����</td>
    <td align="left" >&nbsp;  <html:text property="item_side"  maxlength="25"/></td>
		<td align="right"  class="inputLabel" noWrap  >&nbsp;����</td>
    <td align="left" >&nbsp;  <html:text property="item_other"  maxlength="25"/></td>
  </tr>
  <!--
  <tr> 
    <td align="right"  class="inputLabel" noWrap  >&nbsp;�Ƿ�OVS</td>
    <td align="left" >&nbsp;  <html:radio property="enable_os" value="1"/>��
                              <html:radio property="enable_os" value="0"/>��
    </td>
    <td align="right"  class="inputLabel" noWrap  >&nbsp;OVS����</td>
    <td align="left" >&nbsp;  <html:text property="os_qty"  maxlength="8"/></td>
  </tr>
  -->
  
  <tr> 
    <td align="right"  class="inputLabel" noWrap  >&nbsp;��Ʒ����</td>
    <td align="left" colspan="3">&nbsp; <html:textarea cols="70" rows="4" property="item_desc"/>
    </td>
  </tr>
</table>

<table width="80%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr>
	<td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;Ĭ��ÿ���������</td>
    <td align="left" >&nbsp; <html:text property="max_count"/> 
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;Ĭ���г���</td>
    <td align="left" >&nbsp; <html:text property="standard_price"/> </td>
    <!--
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>Ĭ�ϳɱ���</font></td>
    <td align="left" >&nbsp; <html:text property="item_cost" /></td>-->
  </tr>
  <tr> 
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;Ĭ�����ۼ�</td>
    <td align="left" >&nbsp; <html:text property="sale_price" /> 
    </td>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;Ĭ��VIP��</td>
    <td align="left" >&nbsp; <html:text property="vip_price"/> 
    </td>
  </tr>
  <tr>
    <td align="right"  class="inputLabel" noWrap  ><font color=red>*</font>&nbsp;���ۼ�</td>
    <td align="left" colspan="3">&nbsp; <html:text property="retail_price" /> 
    </td>
  </tr>
</table>
  
 
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >  
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button5" value=" ȷ�� " >
      <input name = "return" id="retrun" type="button" class="button5" value=" ���� " onclick="history.back();" >
  </tr>
</table>
<html:hidden property="qry_item_code"/>
<html:hidden property="qry_item_name"/>
<html:hidden property="qry_item_type"/>
<html:hidden property="qry_item_category"/>
</html:form>

</body>
</html>

