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
<title>佰明会员关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">

function checkInput(){
	var form = document.forms[0];
	
	
	if(trim(form.silverPrice.value) == ""){
		alert("产品银卡价不能为空！");
		form.silverPrice.select();
		return false;
	
	}else if (isNaN(form.silverPrice.value)){
			alert("产品银卡价包含非法字符！");
			form.silverPrice.select();
			return false;
	}
	if(trim(form.godenPrice.value) == ""){
		alert("产品金卡价不能为空！");
		form.godenPrice.select();
		return false;
	}else if (isNaN(form.godenPrice.value)){
			alert("产品金卡价包含非法字符！");
			form.godenPrice.select();
			return false;
	}

	if (isNaN(form.platina_Price.value)&&trim(form.platina_Price.value)!= ""){
			alert("产品白金卡价包含非法字符！");
			form.platina_Price.select();
			return false;
	}	
	
	
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">修改产品销售价</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/modifyPrice.do" method="post" onsubmit="return checkInput();">
  
<table width="750.0" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  width="40%">&nbsp;货号</td>
    <td width="196" align="left" >&nbsp;
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="itemCode" filter="false"/> 
    	<html:hidden name="<%=Constants.PRODUCT_FORM%>" property="itemID" />
    </td>
    
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;产品名称</td>
    <td align="left" >&nbsp; <bean:write name="<%=Constants.PRODUCT_FORM%>" property="name" /> </td>
    
  </tr>
  
  
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;银卡价</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="silverPrice" /> 
    </td>
    
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;金卡价</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="godenPrice"/> 
    </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;白金卡价</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="platina_Price"/> 
    </td>
  </tr>  
 <tr> 
    
    <td align="right"  class="OraTableRowHeader" noWrap  ><font color="#000000">网站价</font></td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="webPrice" /></td>
  </tr>
  <tr align="center" valign="middle"> 
    <td height="42" colspan=17> 
      <input type="submit" class="button2" value=" 确定 " > 
      &nbsp; <input type="button" class="button2" value=" 取消 " onClick="history.back();">
      <input name="flag" type="hidden" value="">
  </tr>
  
</table>
</html:form>

<p>&nbsp;</p>
</body>
</html>

