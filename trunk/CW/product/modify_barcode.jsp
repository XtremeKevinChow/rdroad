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

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
	
	var form = document.forms[0];
	
	var barcodeValue = form.barCode.value;
	
	if (barcodeValue != "" && trim(barcodeValue).length > 13){
		alert("��Ʒ���볤�ȱ���С��13���ַ����ȵ��ַ�����");
		return false;
	}
	return true;
}
</script>
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
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
<br><br><br><br>
<html:form action="/modifyBarcode.do" method="post" onsubmit="return checkInput();">
  
<table width="750.0" border=0 cellspacing=3 cellpadding=1  align="center" >
 <tr> 
    <td width="40%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;����</td>
    <td width="196" align="left" >&nbsp;
    	<bean:write name="<%=Constants.PRODUCT_FORM%>" property="itemCode" filter="false"/> 
    	<html:hidden name="<%=Constants.PRODUCT_FORM%>" property="itemID" /> 
    </td>
    
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ����</td>
    <td align="left" >&nbsp; <bean:write name="<%=Constants.PRODUCT_FORM%>" property="name" /> </td>
  </tr>
  <tr> 
    <td align="right"  class="OraTableRowHeader" noWrap  >&nbsp;������</td>
    <td align="left" >&nbsp; <html:text name="<%=Constants.PRODUCT_FORM%>" property="barCode" maxlength="13"/> </td>
    
  </tr>
  
  <tr align="center" valign="middle"> 
    <td height="42" colspan=17> 
      <input name = "submitButton" id="submitButton" type="submit" class="button2" value=" ȷ�� " > 
      <input type="reset" class="button2" value=" ���� " >
      
  </tr>
</table>
</html:form>

<p>&nbsp;</p>
</body>
</html>

