<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    response.setHeader("expires","0");
    response.setHeader("Cache-Control", "no-store"); //http1.1
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache"); //http1.0
%>
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

function getOpenwinValue(ret){
	//ret���飬ret[0]:��ƷID	ret[1]:����
	document.forms[0].replaceItemID.value = ret[0];
	
}
function getProduct(para){
	
	var owin = openWin("productQuery.do?actn=selectProduct","wins",700,400);
	
	
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

}
		function clear_f(){
			document.forms[0].replaceItemID.value ="";
			
		}	
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�޸Ĳ�Ʒ������Ϣ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/productModify.do" method="post" onsubmit="return checkInput();">
  
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td width="150" align="right"  class="OraTableRowHeader" noWrap >&nbsp;����</td>
    <td width="196" align="left" >&nbsp;
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="itemCode" filter="false"/> 
    	<html:hidden name="<%=Constants.PRODUCT_FORM%>" property="itemID" />
    </td>
    <td width="133" align="right"  class="OraTableRowHeader" noWrap  >&nbsp;����/����</td>
    <td width="258" align="left" >&nbsp;
    	<html:text name="<%=Constants.PRODUCT_FORM%>" property="author" maxlength="25"/> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" colspan="3">&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="name" maxlength="100" size="40"/> </td>
    
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <select id="itemType" name="itemType" >
        <option value="">��ѡ��...</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="3">selected</logic:match> value="3" >����</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="5">selected</logic:match> value="5" >��Ʒ</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="2">selected</logic:match> value="2" >Ӱ��</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="4">selected</logic:match> value="4" >��Ϸ/���</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="6">selected</logic:match> value="6" >����</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="itemType" value="1">selected</logic:match> value="1" >ͼ��</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ���</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="categoryID"/> 
      <a href="javascript:getCategory();"><img src="../images/icon_lookup.gif" border=0 align="top"><a> 
      &nbsp;<span style="display:none" id="category_id_display" name="category_id_display" ></span> 
      </a></a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="standardPrice" /> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ��λ</td>
    <td align="left" >&nbsp; <select id="unit" name="unit" >
        <option value="">��ѡ��...</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="unit" value="3">selected</logic:match> value="3" >��</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="unit" value="5">selected</logic:match> value="5" >��</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="unit" value="2">selected</logic:match> value="2" >��</option>
        <option value="0" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="unit" value="0">selected</logic:match> >��</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="unit" value="4">selected</logic:match> value="4" >��</option>
        <option <logic:match name="<%=Constants.PRODUCT_FORM%>" property="unit" value="1">selected</logic:match> value="1" >��</option>
      </select> </td>
  </tr>
  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�ۿ�</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="discount" /> </td>
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
        <option value="0" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="peculiarity" value="0">selected</logic:match> >��װ</option>
        <option value="1" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="peculiarity" value="1">selected</logic:match> >��װ</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;�ɹ��༭</td>
    <td align="left" >&nbsp; <html:select property="productOwnerID">
    								<option value="0"></option>
		                          <html:options collection="allUser" property="id" labelProperty="NAME"/>
			                  </html:select></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ��Ӧ��</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="supplierID" /> 
      <a href="javascript:getProvider('provider');">
      <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a></td>
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;���䷽ʽ</td>
    <td align="left" >&nbsp; <select id="deliveryType" name="deliveryType" >
        <option value="1" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="1">selected</logic:match> >����</option>
        <option value="3" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="3">selected</logic:match> >��Ӧ���ͻ�</option>
        <option value="2" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="deliveryType" value="2">selected</logic:match> >�����ͻ�</option>
         
      </select> 
    
      </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="title" maxlength="50"/> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="publishingHouse" /> 
      <a href="javascript:getProvider('publish');">
      <img src="../images/icon_lookup.gif" border=0 align="top">
      </a></td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;CIP��</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="icpCode" maxlength="20"/> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;ISBN</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="isbn" maxlength="20"/> </td>
  </tr>
  <tr>

    <td align="right" class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ�Ԥ��</td>
    <td align="left"  >&nbsp; 
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="ifPresell" value="0">
    	<input type="radio" id="ifPresell" name="ifPresell" value="0" checked >
      ��&nbsp; <input type="radio" id="ifPresell" name="ifPresell" value="1" >
      ��&nbsp; 
    	</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="ifPresell" value="1">
    	<input type="radio" id="ifPresell" name="ifPresell" value="0"  >
      ��&nbsp; <input type="radio" id="ifPresell" name="ifPresell" value="1" checked>
      ��&nbsp; 
    	</logic:match>
    	    

      </td>
 
  <!-- 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="barCode" maxlength="13"/> </td>
    -->
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�˻���</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="returnRate" /> 
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
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="replaceItemID" readonly="true"/>
     <a href="javascript:getProduct();">
     <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a>
     &nbsp;<input type="button" value="������Ʒ" onclick="clear_f();">
     </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ͬ����</td>
    <td align="left" >&nbsp; <select id="contractTitle" name="contractTitle" >
        <option value="0" >��Ӧ��</option>
        <option value="1" >������</option>
      </select> </td>
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ����꼴ֹ</td>
    <td align="left" >&nbsp; 
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="isLastSel" value="0">
    	<input type="radio" id="is_last_sel" name="isLastSel" value="0" checked >
      ��&nbsp; <input type="radio" id="is_last_sel" name="isLastSel" value="1" >
      ��&nbsp; 
    	</logic:match>
    	<logic:match name="<%=Constants.PRODUCT_FORM%>" property="isLastSel" value="1">
    	<input type="radio" id="is_last_sel" name="isLastSel" value="0"  >
      ��&nbsp; <input type="radio" id="is_last_sel" name="isLastSel" value="1" checked>
      ��&nbsp; 
    	</logic:match>
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�������ֲ�</td>
    <td align="left" colspan="3">&nbsp; 
	<html:select property="clubID">
	<option value="1" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="clubID" value="1">selected</logic:match>>99���ֲ�</option>
	<option value="2" <logic:match name="<%=Constants.PRODUCT_FORM%>" property="clubID" value="2">selected</logic:match>>���䱦�����ֲ�</option>
	</html:select>    
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
    <td align="left" >&nbsp; <html:text property="maxsalenum"/> 
    </td>  
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�ʺ������</td>
    <td align="left" >&nbsp; <html:text property="ageSegment"/> (��ʽ: 2 ~ 5)
    </td>
<tr>     
   <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;�Ƿ���վ��Ʒ</td>
    <td align="left" colspan="3">
      <html:radio property="is_Web" value="0" />
      ��&nbsp; 
      <html:radio property="is_Web" value="1" />
      ��&nbsp; </td>    
    </td>
  <tr>    

  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��ע</td>
    <td align="left" colspan="3">&nbsp; <textarea cols=70 rows=4 name="comments" ><bean:write name="<%=Constants.PRODUCT_FORM%>" property="comments"/></textarea> 
    </td>
    
  </tr>
  
  <tr align="center" valign="middle"> 
    <td height="42" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button2" value=" ȷ�� " > 
      &nbsp; <input type="button" class="button2" value=" ȡ�� " onClick="history.back(-1);">
      <input name="flag" type="hidden" value="">
  </tr>
</table>
</html:form>

<p>&nbsp;</p>
</body>
</html>

