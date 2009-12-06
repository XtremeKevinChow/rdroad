<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
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
</head>


<body  text="#000000" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��װƷ��ϸ��Ϣ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
  		  <logic:iterate id="product" name="itemdetail" >
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>��װ������Ϣ</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;��Ʒ��</td>
		<td align="left" width="80%" nowarp><bean:write name="product" property="name" /></td>
		
	</tr>


	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ����</td>
      <td align="left" width="80%" nowarp>
		<logic:equal name="product" property="type" value="1">ͼ��</logic:equal>
		<logic:equal name="product" property="type" value="2">Ӱ��</logic:equal>     
		<logic:equal name="product" property="type" value="3">����</logic:equal>
		<logic:equal name="product" property="type" value="4">��Ϸ/���</logic:equal>    
		<logic:equal name="product" property="type" value="5">��Ʒ</logic:equal>
		<logic:equal name="product" property="type" value="6">����</logic:equal> 
      </td>
			
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  >&nbsp;��Ʒ���</td>
		<td align="left" >
	<bean:write name="product" property="author" />
		</td>
		
	</tr>
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >�������ֲ�</td>
		<td align="left" width="80%" nowarp>
			<logic:equal name="product" property="clubID" value="1">99</logic:equal> 
			<logic:equal name="product" property="clubID" value="2">99���䱦��</logic:equal> 
		</td>					
		
	</tr>	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >�Ƿ����꼴ֹ</td>
		<td align="left" width="80%" nowarp>
			<logic:equal name="product" property="isLastSel" value="1">��</logic:equal> 
			<logic:equal name="product" property="isLastSel" value="0">��</logic:equal> 
	</tr>
	<tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >�Ƿ�Ԥ��</td>
		<td align="left" width="80%" nowarp>
			<logic:equal name="product" property="ifPresell" value="1">��</logic:equal> 
			<logic:equal name="product" property="ifPresell" value="0">��</logic:equal> 
    </tr>	
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >��ע</td>
	  <td align="left" nowarp>
	  <bean:write name="product" property="comments" />
	  </td>
	  
  </tr>
  
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	<tr> 
	  <td width="60px">��Ʒ����:</td>
	  <td width="60px"><bean:write name="product" property="standardPrice" format="#.00"/>Ԫ</td>
	  <td width="60px">������:</td>
	  <td width="60px"><bean:write name="product" property="silverPrice" format="#.00"/>Ԫ</td>
	  <td width="60px">�𿨼�:</td>
	  <td width="60px"><bean:write name="product" property="godenPrice" format="#.00"/>Ԫ</td>
	  <td width="60px">��վ��:</td>
	  <td width="*"><bean:write name="product" property="webPrice" format="#.00"/>Ԫ</td>	
  
  </tr>
</table>
  		</logic:iterate>
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	<tr>
		<td width="20%" colspan=3></td>
	</tr>
	<tr>
		<td><b>��װ��Ʒ��Ϣ</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>


<table cellspacing=2 cellpadding=2 width="90%"  border=0 align="center">
  <tbody> 
        <tr>
		   
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">����</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="20%">��Ʒ����</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">�ɹ��ɱ�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">����</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%"> ������</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">�𿨼�</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">���ϼ�</th>

       </tr>

  		<%int count = 0;%>
  		  <logic:iterate id="product2Form" name="item" >
  		
  		<tr>
  			<td align="center"><bean:write name="product2Form" property="itemCode"/></td>
  			<td><bean:write name="product2Form" property="name"/></td>
  			<td align="right"><bean:write name="product2Form" property="purchasingCost" format="#.00"/></td>
  			<td align="right"><bean:write name="product2Form" property="standardPrice" format="#.00"/></td>
  			<td align="right"><bean:write name="product2Form" property="height" format="#.00"/></td>
  			<td align="right"><bean:write name="product2Form" property="weight"  format="#.00"/></td>
  			<td align="right"><bean:write name="product2Form" property="width"  format="#.00"/></td>
  		</tr>
  		<% count ++ ;%>
  		</logic:iterate>

	</tbody>
</table>



</body>
