<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">
function winFocus(){
	
	if (document.product2Form.rm.value == "GET"){
		document.product2Form.name.focus();
	}else{
		document.product2Form.itemCode.focus();
	}
	return true;
	
}

function checkInput(para){
	var form = document.forms[0];
	
	if(para == "submit"){
		var itemName = form.name.value;
		var categoryID = form.categoryID.value;
		var count = form.count.value;				
		if (trim(itemName) == ""  ){
			alert("��װ��Ʒ������Ϊ�գ�");
			return false;
		}
		if (trim(categoryID) == ""  ){
			alert("��װ��Ʒ�����Ϊ�գ�");
			return false;
		}
		if (trim(count) <2  ){
			alert("��װ��Ʒ������2�������ϵĲ�Ʒ��");
			return false;
		}		
	}
	if((para == "add")){
		if(form.itemCode.value == ""){
			alert("���Ų���Ϊ�գ�");
			form.itemCode.focus();
			return false;
		}

		if(form.itemCode.value.substring(0,1) == "T"){
			alert("��װƷ�����ٴ���!");
			form.itemCode.focus();
			return false;
		}		
	}
	return true;
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

function delproduct(para){
	var form = document.forms[0];
	url = "productSetAdd.do?actn=del&index="+para;
	form.action = url;
	form.submit();
}

function getProduct(){
	var owin = openWin("productQuery.do?actn=selectProduct","wins",700,400);
}
function getOpenwinValue(ret){

	//ret���飬ret[0]:��ƷID	ret[1]:����
	var form = document.forms[0];
	form.itemID.value = ret[0];
	form.itemCode.value = ret[1];
	form.itemCode.focus();
}

function winExe(){
	var ele = event.srcElement;
	var form = document.forms[0];
	if (event.keyCode == "13"){
		if( (ele.name == "itemCode" && ele.value != "")){
			//to do ѡ��Ʒ
			submitForm("1");
		}else{
			if(ele.tagName != "A" && ele.name != "del"  && ele.name != "buttOK" ) {
				if (form.count.value == "0" || form.count.value =="1" ){
					alert("��Ҫ����ЦŶ����װ�������ֻ��������ϲ�Ʒ��");
					return false;
				}
				
				var confirm=window.confirm("��ȷ��Ҫ���������װ��Ʒ��Ϣ��");
				if (!confirm){
					return false;
				}
				//to do �ύ
				alert("�ύ");
			}
		}
		
	}
	return true;
}

function getCategory(){
	
	openWin("prdCatQuery.do","2005",600,400);
	
}
function getRetCateID(ret){
	//ret���飬ret[0]:��Ʒ����ID	ret[2]:��Ʒ������
	var form = document.forms[0];
	form.categoryID.value = ret[0];
	
}

</script>
</head>


<body  text="#000000" leftmargin="0" topmargin="0" onkeydown="javascript:winExe();" onload="javascript:winFocus()">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">���²�Ʒ����</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productSetAdd.do" method="post">
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>��װ������Ϣ</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;��Ʒ��</td>
		<td align="left" width="80%" nowarp><html:text property="name"/></td>
		
	</tr>

	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ����</td>
      <td align="left" width="80%" nowarp>
        <html:select property="itemType" >           			  
	  <html:option value="1" >ͼ��</html:option>     
	  <html:option value="2" >Ӱ��</html:option>     
	  <html:option value="3" >����</html:option>     
	  <html:option value="4" >��Ϸ/���</html:option>     
	  <html:option value="5" >��Ʒ</html:option>     
	  <html:option value="6" >����</html:option>       			    
        </html:select>
      </td>
			
	</tr>		
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��Ʒ���</td>
		<td align="left" >
			<html:text property="categoryID" readonly="true"/>
			<a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a>
		</td>
		
	</tr>
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;ѡ����ֲ�</td>
		<td align="left">

	        <html:select property="clubID" >           			  
		  <html:option value="1" >99</html:option> 
		  <html:option value="2" >99���䱦��</html:option> 			    			    
	        </html:select>			
			
		</td>
	</tr>	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;�Ƿ����꼴ֹ</td>
		<td align="left" width="80%" nowarp>
		<html:radio property="isLastSel" value="1" />��
		<html:radio property="isLastSel" value="0" />��</td>
		
	</tr>	
	<tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red></font>&nbsp;�Ƿ�Ԥ��</td>
		<td align="left" width="80%" nowarp><html:radio property="ifPresell" value="1"/>��
		<html:radio  property="ifPresell" value="0"/>��</td>
		
	</tr>
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >��ע</td>
	  <td align="left" nowarp>
	  <html:textarea cols="30" rows="2" property="comments"></html:textarea></td>
	  
  </tr>
  
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	<tr> 
	  <td width="60px">��Ʒ����:</td>
	  <td width="60px"><bean:write name="product2Form" property="standardPrice" format="#.00"/>Ԫ</td>
	  <td width="60px">������:</td>
	  <td width="60px"><bean:write name="product2Form" property="silverPrice" format="#.00"/>Ԫ</td>
	  <td width="60px">�𿨼�:</td>
	  <td width="60px"><bean:write name="product2Form" property="godenPrice" format="#.00"/>Ԫ</td>	
	  <td width="60px">�׽𿨼�:</td>
	  <td width="60px"><bean:write name="product2Form" property="platina_Price" format="#.00"/>Ԫ</td>		    
	  <td width="60px">��վ��:</td>
	  <td width="*"><bean:write name="product2Form" property="webPrice" format="#.00"/>Ԫ</td>	
  
  </tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	<tr>
		<td width="20%" colspan=3></td>
	</tr>
	<tr>
		<td><b>��װ��Ʒ��Ϣ</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="5"  align="center">

<tr> 
		  
            <td noWrap align=left> ����:<input type="hidden" id="itemID" name="itemID" value=""><input id="itemCode" name="itemCode" value="" >
				<a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     			</a>
     			<input name="buttOK" type="button" value="ȷ��"  onclick="javascript:submitForm('1')">
            </td>
		            
          </tr>
</table>

<table cellspacing=2 cellpadding=2 width="90%"  border=0 align="center">
  <tbody> 
        <tr>
		   
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">����</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="20%">��Ʒ����</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">�ɹ��ɱ�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">����</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%"> ������</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%"> ������</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">�𿨼�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">�𿨼�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">�׽𿨼�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">�׽𿨼�</th>			
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">���ϼ�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="7%">���ϼ�</th>
			
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="6%">����</th>
        </tr>
        <bean:define id="psf" name="product2Form" property="item" type="java.util.Collection"/> 
  		<%int count = 0;%>
  		<logic:iterate name="psf" id="product2Form"> 
  		
  		<tr>
  			<td align="center"><bean:write name="product2Form" property="itemCode"/></td>
  			<td><bean:write name="product2Form" property="name"/></td>
  			<td align="right"><bean:write name="product2Form" property="purchasingCost" format="#.00"/></td>
  			<td align="right"><bean:write name="product2Form" property="standardPrice" format="#.00"/><input type="hidden" name="unpurchasingCost"  value="<bean:write name='product2Form' property='unpurchasingCost' format='#.00'/>"></td>
  			<td align="right"><bean:write name="product2Form" property="silverPrice" format="#.00"/></td>
  			<td><html:text name="product2Form" property="arraySilverPrice" size="8" /></td>
  			<td align="right"><bean:write name="product2Form" property="godenPrice"  format="#.00"/></td>
  			<td><html:text name="product2Form" property="arrayGodenPrice" size="8"/></td>
			<td align="right"><bean:write name="product2Form" property="platina_Price"  format="#.00"/></td>
  			<td><html:text name="product2Form" property="arrayPlatina_Price" size="8"/></td>  			
  			<td align="right"><bean:write name="product2Form" property="webPrice"  format="#.00"/></td>
  			<td><html:text name="product2Form" property="arrayWebPrice" size="8"/></td>
  			<td><input name="del" value="ɾ��" type="button" onclick="javascript:delproduct('<%=count%>')"></td>
  		</tr>
  		<% count ++ ;%>
  		</logic:iterate>

	</tbody>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="5" >
	<tr>
	 <td nowrap align="center">
		<input type="button" class=button2 value=" ȷ�� "  onclick="javascript:submitForm('2')">
		<input type="button" name="Submit243" class=button2 value=" ȡ�� "  onClick="javascript:document.fm_add.act.value='cancel';document.fm_add.submit();;">
		<input name="rm" value="<%=request.getMethod()%>" type="hidden">
		<input name="count" value="<%=count%>" type="hidden">
  </tr>
</table>
</html:form>


</body>
