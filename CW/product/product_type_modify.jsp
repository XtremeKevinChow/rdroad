<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.form.ProductTypeForm"%>
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

function submit_f() {
	if(document.all("inputQty").style.display=="" || document.all("inputType").style.display=="") {
		alert("���ȹر��ִ���!");
		return;
	}
	var obj = document.forms[0];
	if(obj.catalogCode == null || obj.catalogCode.value.length == 0) {
		alert("������벻��Ϊ��!");
		obj.catalogCode.focus();
		return;
	}

	if(obj.name == null || obj.name.value.length == 0) {
		alert("���Ʋ���Ϊ��!");
		obj.name.focus();
		return;
	}

	if(obj.categoryLevel == null || obj.categoryLevel.value.length == 0) {
		alert("������Ϊ��!");

		obj.categoryLevel.focus();
		return;
	}

	if(obj.parentType == null || obj.parentType.value.length == 0) {
		alert("�ϼ����Ͳ���Ϊ��!");
		obj.parentType.focus();
		return;
	}
	obj.target = "tree";
	//obj.save.disabled = true;
	obj.submit();
}
//��ʾ����ҳ��
function add_f() {
	
	if(document.all("inputQty").style.display==""  || document.all("inputType").style.display=="") {
		alert("���ȹر��Ӵ���!");
		return;
	}
	//oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=addInit";
	//document.parentWindow.parent.document.operation.src="productType.do?type=addInit";
	document.forms[0].target="operation";
	document.forms[0].submit();
	
	
}
//ɾ����¼
function delete_f() {
	if(document.all("inputQty").style.display==""  || document.all("inputType").style.display=="") {
		alert("���ȹر��ִ���!");
		return;
	}
	//oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=delete";
	document.forms[0].target = "operation";
	document.forms[0].submit();
}
function load_f() {
	document.forms[0].catalogCode.focus();
}

function poup_f() {
	if(document.all("inputType").style.display=="") {
		alert("���ȹر��ִ���!");
		return;
	}
	document.all("inputQty").style.display="";
	document.all("inputQty").style.position="absolute";	
	document.all("inputQty").style.pixelLeft=100;
	document.all("inputQty").style.pixelTop=200;
	document.all("qty").focus();
	
}
function poup_f2() {
	if(document.all("inputQty").style.display=="") {
		alert("���ȹر��ִ���!");
		return;
	}
	document.all("inputType").style.display="";
	document.all("inputType").style.position="absolute";	
	document.all("inputType").style.pixelLeft=100;
	document.all("inputType").style.pixelTop=200;
	document.all("newType").focus();
	
}
function query_f() {
	var qty = document.forms[0].qty.value;
	if (qty == null || qty == "")
	{
		return;
	}
	
	if (isNaN(qty))
	{
		alert("����Ϊ����");
		return;
	}
	if (qty.indexOf(".") != -1)
	{
		alert("������С��");
		return;
	}
	qty = qty == "" ? 0 : qty;
	document.forms[0].action = "productType.do?type=queryProductByCategory&qty="+qty;
	document.forms[0].target = "operation";
	document.forms[0].submit();
}

function batUpdateType() {
	var qty = document.forms[0].newType.value;
	if (qty == null || qty == "")
	{
		return;
	}
	
	
	
	document.forms[0].action = "productType.do?type=moveCategory";
	document.forms[0].target = "operation";
	document.forms[0].submit();
}
</script>

<script>   
  var   Obj   
  function   MouseDown(obj){   
	  
	  Obj=obj   
	  Obj.setCapture()   
	  Obj.l=event.x-Obj.style.pixelLeft   
	  Obj.t=event.y-Obj.style.pixelTop   
  }   
  function   MouseMove(){   
	  if(Obj!=null){   
		  Obj.style.left   =   event.x-Obj.l   
		  Obj.style.top   =   event.y-Obj.t   
	  }   
  }   
  function   MouseUp(){   
	  if(Obj!=null){   
		  Obj.releaseCapture()   
		  Obj=null   
	  }   
  }   
</script>
</head>


<body id="mainbody" text="#000000" leftmargin="0" topmargin="0" onload="load_f()">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ���ά��</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productType.do?type=modify" method="post" target="user">
<html:hidden property="ID"/>

<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>�޸Ĳ�Ʒ���</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp; �������</td>
		<td align="left" width="80%" nowarp>
		<html:text property="catalogCode"/></td>
		
	</tr>

	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;����</td>
      <td align="left" width="80%" nowarp>
        <html:text property="name"/>
      </td>
			
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;����</td>
      <td align="left" width="80%" nowarp>
        <html:text property="categoryLevel" readonly="true" />
      </td>
			
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp; �ϼ�����</td>
		<td align="left" >
			<html:text readonly="true" property="parentType"  />
			<html:text property="parentTypeName"  readonly="true" />
			
		</td>
		
	</tr>
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;�Ƿ�Ϊ��С���</td>
		<td align="left" width="80%" nowarp>
		<html:radio property="isLeaf" value="1" />��
		<html:radio property="isLeaf" value="0" />��
		</td>
		
	</tr>	
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >����</td>
	  <td align="left" nowarp>
	  <html:textarea cols="30" rows="2" property="description" />
	  </td>
	  
  </tr>
  
</table>

<table width="90%" border="0" cellspacing="1" cellpadding="5" >
	<tr>
	 <td nowrap align="center">
		<input type="button" class="button2" value=" ���� " name="save" onclick="submit_f()">&nbsp;
		<input type="button" class="button2" value=" ���� " name="add" onclick="add_f()">&nbsp;
		<input type="button" class="button2" value=" ɾ�� " name="delete" onclick="delete_f()">&nbsp;
		<!--<input type="button" class="button2" value=" ��ѯ " name="query" onclick="poup_f()">&nbsp;-->
		<!--<input type="button" class="button2" value=" �������� " name="updateType" onclick="poup_f2()">&nbsp;-->
		<!-- <input type="button" class="button2" value=" ���� " name="export" onclick=""> -->
		
		
  </tr>
</table>

<!-- layer1 -->
<div id="inputQty" style="display:none" onmousedown=MouseDown(this) onmousemove=MouseMove() onmouseup=MouseUp()>
  <TABLE bgcolor='f5f5f5' height="" width="330" border=1 bordercolorlight="#000000" bordercolordark="#FFFFFF" cellspacing="0">
  <TR >
  	<TD colspan="2"><font color="red">��ѯ�������������50��ô�㽫��Ѱ�����>=50�Ĳ�Ʒ</font></TD>
  	
  </TR>
  <TR>
  	<TD>������������</TD>
  	<TD><input name="qty" value="50"></TD>
  </TR>
  <TR>
  	<TD align="center"  colspan="2">
	<input type="button" value="ȷ��" onclick="query_f()">&nbsp;&nbsp;
	<input type="button" value="�ر�" onclick="inputQty.style.display='none'">
	</TD>
	
  </TR>
  </TABLE>
</div>
<!-- layer2 -->
<div id="inputType" style="display:none" onmousedown=MouseDown(this)   onmousemove=MouseMove()   onmouseup=MouseUp()>
  <TABLE bgcolor='f5f5f5' height="" width="330" border=1 bordercolorlight="#000000" bordercolordark="#FFFFFF" cellspacing="0">
  <TR>
  	<TD colspan="2">��<font color="red"><bean:write name="productTypeForm" property="name"/>(<bean:write property="catalogCode" name="productTypeForm"/>)</font>�����µ����в�Ʒ���µ���������</TD>
  	
  </TR>
  <TR>
  	<TD>�����ʹ��룺</TD>
  	<TD><input name="newType" value=""></TD>
  </TR>
  <TR>
  	<TD align="center"  colspan="2">
	<input type="button" value="��������" onclick="batUpdateType()">&nbsp;&nbsp;
	<input type="button" value="�ر�" onclick="inputType.style.display='none'">
	</TD>
	
  </TR>
  </TABLE>
</div>
</html:form>


</body>
