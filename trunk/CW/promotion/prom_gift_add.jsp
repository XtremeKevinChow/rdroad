<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String promotionid=request.getParameter("promotionid");
String name=request.getParameter("name");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="javascript" src="../script/Ajax.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript">

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
	
}

function getOpenwinValue(ret){
	document.forms[0].item_code.value = ret;
}

//�ص�����
function updatePage(response) {
 
   document.getElementById("item_name").innerText = response;
}

//�첽����
function callAjax(obj) {
	var ajax=new Ajax("/magicAjax.do?type=getItemNameByCode&itemCode=" + escape(obj.value),"",this.updatePage);
	ajax.postRequest();
}


function f_checkData() {
        if(document.forms[0].item_code.value==""){
           alert("����д������Ʒ��");
           document.forms[0].item_code.select();	
           return false;
        }
	if(isNaN(document.forms[0].overx.value)||document.forms[0].overx.value==""){
	alert('��XԪֻ��Ϊ����!');
	document.forms[0].overx.select();
	return false;
	}
	

	if(isNaN(document.forms[0].addy.value)||document.forms[0].addy.value==""){
	alert('��YԪֻ��Ϊ����!');
	document.forms[0].addy.select();
	return false;
	}

	
    /*    if(document.forms[0].prom_url.value==""){
           alert("����д����ҳ���ַ");
           document.forms[0].prom_url.select();	
           return false;
        }               	    
	if(!f_checkStartDate()) {
		alert("��Ч�������벻��ȷ");
		document.forms[0].begindate.select();
		return false;
	}
	if(!f_checkEndDate()) {
		alert("��ֹ�������벻��ȷ");
		document.forms[0].enddate.select();
		return false;
	}
        if(document.forms[0].description.value==""){
           alert("����д����");
           document.forms[0].description.select();	
           return false;
        } 		

	//��ʼ���ڲ��ܴ��ڽ�������
	var begin, end; 
	begin = document.forms[0].begindate.value.replace("-", "").replace("-","");
	end =   document.forms[0].enddate.value.replace("-", "").replace("-",""); 
	
	if (begin - end > 0)
	{
		alert("��ʼ���ڲ��ܴ��ڽ�������");
		return false;
	}
    */
 	document.forms[0].input.disabled = true;
 	return true;
}



function f_checkStartDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].begindate.value);
}
function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].enddate.value);
}
function initFocus(){
	document.forms[0].item_code.select();
	return true;
}
function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0"  onload="javascript:initFocus();">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">������Ʒ����</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form action="prom_GiftOperation.do?type=add" method="post" onsubmit="return f_checkData();">
  

 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap  width="30%">������Ʒ��</td>
    <td>
    <input type=text name="item_code" size=30 readonly>
		<a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     	</a>
     	<font color=red>*</font>
	</td>
	
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap  >��XԪ</td>
    <td><input type=text name="overx" size=30 value="0"><font color=red>*</font></td>
 </tr>  
 <tr> 
    <td class="OraTableRowHeader" noWrap  >��XԪ</td>
    <td><input type=text name="addy" size=30 value="0"><font color=red>*</font></td>
 </tr>
 <!--  
 <tr> 
    <td class="OraTableRowHeader" noWrap >ʹ�÷�Χ</td>
    <td>
    <select name="scope">
    <option value="7">--����--</option>
    <option value="1">--��վ����--</option>
    <option value="2">--CRM����--</option>
    <option value="3">--�ŵ�����--</option>
    <option value="4">--��վ��CRM����--</option>
    <option value="5">--��վ���ŵ�����--</option>
    <option value="6">--�ŵꡢCRM����--</option>
    </select>       
    </td>
 </tr>

  <tr> 
    <td class="OraTableRowHeader" noWrap  >����ҳ���ַ</td>
    <td><input type=text name="prom_url" size=30><font color=red>*</font></td>
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap >��Ч����</td>
    <td><input type=text name="begindate" size=30>
    <a href="javascript:calendar(document.forms[0].begindate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ��ֹ����</td><td><input type=text name="enddate" size=30>
    <a href="javascript:calendar(document.forms[0].enddate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
    <font color=red>*</font><font color="red">��Ʒ��Ч�ڱ����ڴ�����Ч��֮��</font>
    </td>
 </tr>
 -->
  <tr> 
    <td class="OraTableRowHeader" noWrap >����</td>
    <td ><textarea name="description" rows="5" cols="50"></textarea></td>
 </tr>

 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" ȷ�� " > 
      &nbsp; <input type="button" class="button2" value=" ȡ�� " onClick="history.back();">
      
  </tr>
</table>

<input type="hidden" name="promotionid" value="<%=promotionid%>">
</form>

<p>&nbsp;</p>
</body>
</html>

