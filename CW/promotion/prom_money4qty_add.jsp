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
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>

<script language="javascript">

function f_checkData() {
    var regex=/^[1-9]{1}$/; 
    if(!regex.exec(document.forms[0].qty.value)){
       alert("可选产品数量格式不正确");
       document.forms[0].qty.select();	
       return false;
    }
    
    regex=/^[1-9]{1}\d{1,6}\.{0,1}\d{0,2}$/;
    if(!regex.exec(document.forms[0].money.value)){
       alert("金额格式不正确");
       document.forms[0].money.select();	
       return false;
    }

		
 	document.forms[0].input.disabled = true;
}

</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0"  >
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">促销管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增促销规则</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form action="prom_money4qtyOperation.do?type=add" method="post" onsubmit="return f_checkData();">
  

 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap  >金额</td>
    <td><input type=text name="money" size="10" maxlength="10" ><font color=red>*</font></td>
 </tr> 
  <tr> 
    <td class="OraTableRowHeader" noWrap  >产品数量</td>
    <td><input type=text name="qty"  maxlength="1" size="4"><font color=red>*</font></td>
 </tr> 

 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      
  </tr>
</table>
<input type="hidden" name="promotionid" value="<%=promotionid%>">
</form>

<p>&nbsp;</p>
</body>
</html>

