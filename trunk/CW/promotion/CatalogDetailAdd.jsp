<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.promotion.entity.*"%>
<%
String pricelist_id=request.getParameter("pricelist_id");
%>
<%
Pricelist_line c=new Pricelist_line();
String actionurl="";
String title="";
String buttonvalue="";
c=(Pricelist_line)request.getAttribute("p");
//out.println(c.getID());
if(c.getID()>0){
actionurl="/Pricelist_line.do?type=modify";
title="Ŀ¼���޸�";
buttonvalue="�޸�";
}else{
actionurl="/Pricelist_line.do?type=add";
title="Ŀ¼������";
buttonvalue="�ύ";
}
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript">

function getProduct(para){
	
	var owin = openWin("../product/product2Query.do?type=init","wins",700,400);
}
function getOpenwinValue(ret){
	document.forms[0].item_id_key.value = ret;
}

function winFocus(){
	document.forms[0].item_id_key.focus();
	return true;	
}

function addSubmit() {
<%if(c.getID()==0){%>
	if(document.forms[0].item_id_key.value==""){
		alert('���Ų���Ϊ��!');
		document.forms[0].item_id_key.focus();
		return false;
	}
<%}%>
	if(document.forms[0].sell_type.value==""){
		alert('���۷�ʽ����Ϊ��!');
		document.forms[0].sell_type.focus();
		return false;
	}
	if(document.forms[0].catalog_edition_key.value==""){
		alert('Ŀ¼��鲻��Ϊ��!');
		document.forms[0].catalog_edition_key.focus();
		return false;
	}		 
  	if(document.forms[0].page.value == ""){
		alert("ҳ�벻��Ϊ�գ�");
		document.forms[0].page.focus();
		return false;
	}

	if (!is_integer(document.forms[0].page.value)||parseInt(document.forms[0].page.value)<=0){
			alert("ҳ��Ӧ��Ϊ����0��������");
			document.forms[0].page.focus();
			return false;
	}   
  	if(document.forms[0].sale_price.value == ""){
		alert("��Ա�۲���Ϊ�գ�");
		document.forms[0].sale_price.focus();
		return false;
	}
	if (isNaN(document.forms[0].sale_price.value)){
			alert("��Ա��Ӧ��Ϊ���֣�");
			document.forms[0].sale_price.focus();
			return false;
	} 	
  	if(document.forms[0].vip_price.value == ""){
		alert("vip�۲���Ϊ�գ�");
		document.forms[0].vip_price.focus();
		return false;
	}
	if (isNaN(document.forms[0].vip_price.value)){
			alert("vip��Ӧ��Ϊ���֣�");
			document.forms[0].vip_price.focus();
			return false;
	} 

			    
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383"><%=title%></font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form method="post" action="<%=actionurl%>" onsubmit="return addSubmit();">

<table  border=0 cellspacing=1 cellpadding=1  width="700" align="center" class="OraTableRowHeader" noWrap  >
	<tr bgcolor="#FFFFFF">
		<td align="right"  ><font color=red>*</font>&nbsp;����</td>
		<td width="*%" align="left">
<input type="hidden" name="item_code" value="<%=c.getItem_code()%>"> 
<%if(c.getID()>0){%>

<%=c.getItem_code()%>
<%}else{%>
<input  name="item_id_key" value="" readonly >
<a href="javascript:getProduct();">
<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
</a>
&nbsp;<span id="item_id_display" name="item_id_display" ></span>
<%}%>

		</td>
	</tr>
	<tr  bgcolor="#FFFFFF" >		
		<td align="right" ><font color=red>*</font>&nbsp;���۷�ʽ</td>
		<td  align="left" >
		<select id="sell_type" name="sell_type" > 
			<option value="">��ѡ��...</option>
			<option value="0" <%if(c.getSell_type()==0){%>selected<%}%>>��������</option>
			<option value="2" <%if(c.getSell_type()==2){%>selected<%}%> >��������</option>
		</select>
		</td>		
	</tr>
	<tr  bgcolor="#FFFFFF">

		<td align="right" ><font color=red>*</font>&nbsp;Ŀ¼���</td>
		<td  align="left" >
		<input type="hidden" id="catalog_edition" name="catalog_edition" value="<%=c.getCatalog_editon()%>"> 
		<input id="catalog_edition_key" name="catalog_edition_key" value="<%=c.getCatalog_editon_name()%>"  readonly onclick="javascript:select_item('catalog_edition',PlineForm.catalog_edition,PlineForm.catalog_edition_key,catalog_edition_display);">
		<a href="javascript:select_item('catalog_edition',PlineForm.catalog_edition,PlineForm.catalog_edition_key,catalog_edition_display);"><img src="../crmjsp/images/icon_lookup.gif" border=0 align="top"><a>
		&nbsp;<span style="display:none" id="catalog_edition_display" name="catalog_edition_display" >p;<span id="msc_display" name="msc_display" ></span>		
				
		</td>
	</tr>
	
	<tr  bgcolor="#FFFFFF" >		
		<td align="right"><font color=red>*</font>&nbsp;ҳ��</td>
		<td width="*%" align="left">
			<input name="page" value="<%=c.getPage()%>">
		</td>		
		
	</tr>

	<tr  bgcolor="#FFFFFF" >
		<td align="right" ><font color=red>*</font>&nbsp;��Ա��</td>
		<td  align="left" >
			<input name="sale_price" value="<%=c.getSale_price()%>">
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF" >	
		<td align="right"  bgcolor="#FFFFFF"><font color=red>*</font>&nbsp; VIP��</td>
		<td  align="left" >
			<input name="vip_price" value="<%=c.getVip_price()%>">
		</td>
		
	</tr>			
	
	<tr height="40"  bgcolor="#FFFFFF">
		<td align="center" colspan=2>
		<input type="submit"  value="<%=buttonvalue%>" > &nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
<input type=hidden name="pricelist_id" value="<%=pricelist_id%>">
<input type="hidden" name="id" value="<%=c.getID()%>" >		
	</tr>
</table>
</html:form>

</body>
</html>
