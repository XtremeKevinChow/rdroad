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
<title>佰明会员关系管理系统</title>
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">套装品详细信息</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
  		  <logic:iterate id="product" name="itemdetail" >
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	
	<tr>
		<td><b>套装基本信息</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >&nbsp;产品名</td>
		<td align="left" width="80%" nowarp><bean:write name="product" property="name" /></td>
		
	</tr>


	<tr>
      <td width="20%" align="right" class="OraTableRowHeader" noWrap  >&nbsp;产品类型</td>
      <td align="left" width="80%" nowarp>
		<logic:equal name="product" property="type" value="1">图书</logic:equal>
		<logic:equal name="product" property="type" value="2">影视</logic:equal>     
		<logic:equal name="product" property="type" value="3">音乐</logic:equal>
		<logic:equal name="product" property="type" value="4">游戏/软件</logic:equal>    
		<logic:equal name="product" property="type" value="5">礼品</logic:equal>
		<logic:equal name="product" property="type" value="6">其他</logic:equal> 
      </td>
			
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  >&nbsp;产品类别</td>
		<td align="left" >
	<bean:write name="product" property="author" />
		</td>
		
	</tr>
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >所属俱乐部</td>
		<td align="left" width="80%" nowarp>
			<logic:equal name="product" property="clubID" value="1">99</logic:equal> 
			<logic:equal name="product" property="clubID" value="2">99妈咪宝贝</logic:equal> 
		</td>					
		
	</tr>	
	 <tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >是否售完即止</td>
		<td align="left" width="80%" nowarp>
			<logic:equal name="product" property="isLastSel" value="1">是</logic:equal> 
			<logic:equal name="product" property="isLastSel" value="0">否</logic:equal> 
	</tr>
	<tr>
		<td width="20%" align="right"  class="OraTableRowHeader" noWrap >是否预售</td>
		<td align="left" width="80%" nowarp>
			<logic:equal name="product" property="ifPresell" value="1">是</logic:equal> 
			<logic:equal name="product" property="ifPresell" value="0">否</logic:equal> 
    </tr>	
  <tr>
	  <td align="right" class="OraTableRowHeader" noWrap  >备注</td>
	  <td align="left" nowarp>
	  <bean:write name="product" property="comments" />
	  </td>
	  
  </tr>
  
</table>
<table width="90%" border="0" cellspacing="1" cellpadding="2" align="center">
	<tr> 
	  <td width="60px">产品定价:</td>
	  <td width="60px"><bean:write name="product" property="standardPrice" format="#.00"/>元</td>
	  <td width="60px">银卡价:</td>
	  <td width="60px"><bean:write name="product" property="silverPrice" format="#.00"/>元</td>
	  <td width="60px">金卡价:</td>
	  <td width="60px"><bean:write name="product" property="godenPrice" format="#.00"/>元</td>
	  <td width="60px">网站价:</td>
	  <td width="*"><bean:write name="product" property="webPrice" format="#.00"/>元</td>	
  
  </tr>
</table>
  		</logic:iterate>
<table width="90%" border="0" cellspacing="1" cellpadding="5" align="center" >
	<tr>
		<td width="20%" colspan=3></td>
	</tr>
	<tr>
		<td><b>套装产品信息</b><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>


<table cellspacing=2 cellpadding=2 width="90%"  border=0 align="center">
  <tbody> 
        <tr>
		   
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">货号</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="20%">产品名称</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">采购成本</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">定价</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%"> 银卡价</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">金卡价</th>
			<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="8%">网上价</th>

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
