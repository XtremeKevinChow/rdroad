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
function set_add() {
    //document.forms[0].action="product2SetDtl.do?type=init&set_item_code=" +set_id;
    if(document.forms[0].op_type.value=="insert") {
        alert("û�б�������Ϣ���ܽ�����ϸ����");
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
		alert("��Ʒ���Ų���Ϊ�գ�");
		form.item_name.select();
		return false;
	}
	
	if(trim(form.item_name.value) == ""){
		alert("��Ʒ���Ʋ���Ϊ�գ�");
		form.item_name.select();
		return false;
	}
	
	
	if(form.item_type.value == "") {
	    alert("��Ʒ���Ͳ���Ϊ��");
	    return false;
	}
	
	if(trim(form.item_category.value) == ""||trim(form.item_category.value) == "0"){
		alert("��Ʒ�����Ϊ�գ�");
		form.item_category.select();
		return false;
	}
	
	/*if(trim(form.standard_price.value) == ""){
		alert("��Ʒ���۲���Ϊ�գ�");
		form.standard_price.select();
		return false;
	}else if (!is_number(form.standard_price.value)){
			alert("��Ʒ���۰����Ƿ��ַ���");
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
	}*/
	
	if(form.barcode.value.length>0){
	if(form.barcode.value.length!=12 && form.barcode.value.length!=13){
		alert("����������12λ�����13λ��");
		return false;
	}
     }	
	if(isNaN(form.max_count.value)&&form.max_count.value!=""){
	alert('���������������������!');
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��װ����</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/product2Set.do?type=save" method="post" onsubmit="return checkInput();">
 
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;����</td>
    <td width="196" align="left" >&nbsp; <html:text property="item_code" maxlength="10" /> 
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <html:text property="item_name" maxlength="50"/> </td>
  </tr>
  <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;Ӣ������</td>
    <td width="196" align="left" >&nbsp; <html:text property="item_name_en" maxlength="50"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <html:text property="barcode"  maxlength="13"/> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <html:select  property="item_type" >
        <html:option value="">��ѡ��...</html:option>
        <html:option value="2" >ϵ����Ʒ</html:option>
        <html:option value="3" >��װ��Ʒ</html:option>
      </html:select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ������</td>
    <td align="left" >&nbsp; <html:text property="item_category" /> 
      <a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a></td>
  </tr>
  <tr> 
    
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�����������</td>
    <td align="left" >&nbsp; <html:text property="max_count" /> 
    <input type=hidden name="item_unit" value="2"> 
    </td>
  </tr>
</table>

<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >  
  <tr align="center" valign="middle"> 
    <td height="32" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button5" value=" ���� " ></td>
      <html:hidden property="op_type" />
  </tr>
</table>

<table width="750" border="0" cellspacing=1 cellpadding=5 align="center" >
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�г���</td>
    <td align="left" >&nbsp; <bean:write name="product2SetForm" property="standard_price"  /> 
    <html:hidden property="standard_price"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp;<bean:write name="product2SetForm" property="web_price" />
    <html:hidden property="web_price"/>
    <html:hidden property="item_cost"/> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ա��</td>
    <td align="left" >&nbsp; <bean:write name="product2SetForm" property="sale_price"  /> 
    <html:hidden property="sale_price"/>
    </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;VIP��</td>
    <td align="left" >&nbsp;<bean:write name="product2SetForm" property="vip_price" />
    <html:hidden property="vip_price"/> 
    </td>
  </tr>
</table>

<table width="750" border="0" cellspacing=1 cellpadding=5  align="center" >
  <tr>
    <td><span class="OraHeader">�׼��б�</span>
    </td>
  </tr>
</table>
<table width="95%" border="0" cellspacing="1" cellpadding="5"  align="center">

<tr> 
	<td noWrap align=left> ����:<html:text property="part_item_code" />
	<input type="hidden" name="set_item_code" value="<bean:write name="product2SetForm" property="item_code"/>">
				<a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     			</a>
     			
     			<html:select property="color_code">
     			<html:option value="">ȫ��</html:option>
     			<html:optionsCollection   property="colors" />
     			</html:select>
     			
     			<input name="buttOK" type="button" value="ȷ��"  onclick="set_add();">
            </td>
		            
          </tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
  <tr align="center" valign="middle"> 
    <td class="OraTableRowHeader" noWrap  align=middle >����</td>
    <td class="OraTableRowHeader" noWrap  align=middle >����</td>
    <td class="OraTableRowHeader" noWrap  align=middle >��ɫ</td>
    <td class="OraTableRowHeader" noWrap  align=middle >�г���</td>
    <td class="OraTableRowHeader" noWrap  align=middle >��Ա��</td>
    <td class="OraTableRowHeader" noWrap  align=middle >vip��</td>
    <td class="OraTableRowHeader" noWrap  align=middle >������</td>
    <td class="OraTableRowHeader" noWrap  align=middle ><!--<input type=button class="button5" value="����" onclick="set_add('<bean:write name="product2SetForm" property="item_code"/>');">--></td> 
  </tr>
    <bean:define id="items" name="product2SetForm" property="items" type="java.util.Collection"/> 
    <% int count=0; %>
    <logic:iterate id="item_part" name="items">
    <tr>
    <td noWrap align=middle ><bean:write name="item_part" property="part_item_code" filter="false"/></td>
    <td noWrap align=middle ><bean:write name="item_part" property="part_item_name" filter="false"/></td>
    <td noWrap align=middle ><bean:write name="item_part" property="color_name" filter="false"/></td>
    <!--<td>
    <logic:equal name="item_part" property="is_optional" value="1">��</logic:equal>
    <logic:notEqual name="item_part" property="is_optional" value="1">��</logic:notEqual>
    </td>-->
    
    <td noWrap align=middle ><input type=text name="part_item_std_price" value='<bean:write name="item_part" property="standard_price" filter="false" format="0.00"/>'></td>
    <td noWrap align=middle ><input type=text name="part_item_sale_price" value='<bean:write name="item_part" property="sale_price" filter="false" format="0.00"/>'></td>
    <td noWrap align=middle ><input type=text name="part_item_vip_price" value='<bean:write name="item_part" property="vip_price" filter="false" format="0.00"/>'></td>
    <td noWrap align=middle ><input type=text name="part_item_web_price" value='<bean:write name="item_part" property="web_price" filter="false" format="0.00"/>'></td>
    <td><input type=button value="�޸�" onclick="set_modify('<bean:write name="item_part" property="set_id" filter="false"/>','<%= count %>')" >
        <input type=button value="ɾ��" onclick="set_delete('<bean:write name="item_part" property="set_id" filter="false"/>')" >
    </td>
    </tr>
    <% count++; %>
    </logic:iterate>
</table>

</html:form>

</body>
</html>

