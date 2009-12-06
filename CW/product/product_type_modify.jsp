<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.form.ProductTypeForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">

function submit_f() {
	if(document.all("inputQty").style.display=="" || document.all("inputType").style.display=="") {
		alert("请先关闭字窗口!");
		return;
	}
	var obj = document.forms[0];
	if(obj.catalogCode == null || obj.catalogCode.value.length == 0) {
		alert("分类编码不能为空!");
		obj.catalogCode.focus();
		return;
	}

	if(obj.name == null || obj.name.value.length == 0) {
		alert("名称不能为空!");
		obj.name.focus();
		return;
	}

	if(obj.categoryLevel == null || obj.categoryLevel.value.length == 0) {
		alert("级别不能为空!");

		obj.categoryLevel.focus();
		return;
	}

	if(obj.parentType == null || obj.parentType.value.length == 0) {
		alert("上级类型不能为空!");
		obj.parentType.focus();
		return;
	}
	obj.target = "tree";
	//obj.save.disabled = true;
	obj.submit();
}
//显示新增页面
function add_f() {
	
	if(document.all("inputQty").style.display==""  || document.all("inputType").style.display=="") {
		alert("请先关闭子窗口!");
		return;
	}
	//oper_win.style.display = "none";
	document.forms[0].action = "productType.do?type=addInit";
	//document.parentWindow.parent.document.operation.src="productType.do?type=addInit";
	document.forms[0].target="operation";
	document.forms[0].submit();
	
	
}
//删除记录
function delete_f() {
	if(document.all("inputQty").style.display==""  || document.all("inputType").style.display=="") {
		alert("请先关闭字窗口!");
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
		alert("请先关闭字窗口!");
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
		alert("请先关闭字窗口!");
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
		alert("必须为数字");
		return;
	}
	if (qty.indexOf(".") != -1)
	{
		alert("不能有小数");
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品类别维护</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<html:form action="/productType.do?type=modify" method="post" target="user">
<html:hidden property="ID"/>

<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>修改产品类别</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp; 分类编码</td>
		<td align="left" width="80%" nowarp>
		<html:text property="catalogCode"/></td>
		
	</tr>

	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;名称</td>
      <td align="left" width="80%" nowarp>
        <html:text property="name"/>
      </td>
			
	</tr>
	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;级别</td>
      <td align="left" width="80%" nowarp>
        <html:text property="categoryLevel" readonly="true" />
      </td>
			
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp; 上级类型</td>
		<td align="left" >
			<html:text readonly="true" property="parentType"  />
			<html:text property="parentTypeName"  readonly="true" />
			
		</td>
		
	</tr>
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap ><font color=red>*</font>&nbsp;是否为最小类别</td>
		<td align="left" width="80%" nowarp>
		<html:radio property="isLeaf" value="1" />是
		<html:radio property="isLeaf" value="0" />否
		</td>
		
	</tr>	
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >描述</td>
	  <td align="left" nowarp>
	  <html:textarea cols="30" rows="2" property="description" />
	  </td>
	  
  </tr>
  
</table>

<table width="90%" border="0" cellspacing="1" cellpadding="5" >
	<tr>
	 <td nowrap align="center">
		<input type="button" class="button2" value=" 保存 " name="save" onclick="submit_f()">&nbsp;
		<input type="button" class="button2" value=" 新增 " name="add" onclick="add_f()">&nbsp;
		<input type="button" class="button2" value=" 删除 " name="delete" onclick="delete_f()">&nbsp;
		<!--<input type="button" class="button2" value=" 查询 " name="query" onclick="poup_f()">&nbsp;-->
		<!--<input type="button" class="button2" value=" 批量更新 " name="updateType" onclick="poup_f2()">&nbsp;-->
		<!-- <input type="button" class="button2" value=" 导出 " name="export" onclick=""> -->
		
		
  </tr>
</table>

<!-- layer1 -->
<div id="inputQty" style="display:none" onmousedown=MouseDown(this) onmousemove=MouseMove() onmouseup=MouseUp()>
  <TABLE bgcolor='f5f5f5' height="" width="330" border=1 bordercolorlight="#000000" bordercolordark="#FFFFFF" cellspacing="0">
  <TR >
  	<TD colspan="2"><font color="red">查询规则：如果你输入50那么你将查寻到库存>=50的产品</font></TD>
  	
  </TR>
  <TR>
  	<TD>请输入数量：</TD>
  	<TD><input name="qty" value="50"></TD>
  </TR>
  <TR>
  	<TD align="center"  colspan="2">
	<input type="button" value="确定" onclick="query_f()">&nbsp;&nbsp;
	<input type="button" value="关闭" onclick="inputQty.style.display='none'">
	</TD>
	
  </TR>
  </TABLE>
</div>
<!-- layer2 -->
<div id="inputType" style="display:none" onmousedown=MouseDown(this)   onmousemove=MouseMove()   onmouseup=MouseUp()>
  <TABLE bgcolor='f5f5f5' height="" width="330" border=1 bordercolorlight="#000000" bordercolordark="#FFFFFF" cellspacing="0">
  <TR>
  	<TD colspan="2">将<font color="red"><bean:write name="productTypeForm" property="name"/>(<bean:write property="catalogCode" name="productTypeForm"/>)</font>类型下的所有产品更新到新类型上</TD>
  	
  </TR>
  <TR>
  	<TD>新类型代码：</TD>
  	<TD><input name="newType" value=""></TD>
  </TR>
  <TR>
  	<TD align="center"  colspan="2">
	<input type="button" value="批量更新" onclick="batUpdateType()">&nbsp;&nbsp;
	<input type="button" value="关闭" onclick="inputType.style.display='none'">
	</TD>
	
  </TR>
  </TABLE>
</div>
</html:form>


</body>
