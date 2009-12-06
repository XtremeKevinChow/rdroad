<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
  String item_code=request.getAttribute("item_code").toString();
%>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>佰明会员关系管理系统</title>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table>
  <tr  align="center">
    <td width="200" align="left">货号为：<font color="red"><%=item_code%></font><td>
	  <td width="120"></td>
	  <td width="120"></td>
	  <td width="120"></td>
  </tr>
  <tr  align="center">
    <td width="200">操作已经成功,请选择你下一步的操作<td>
	  <td width="120"><a href="../product/initProductAdd.do">增加产品</href></td>
	  <td width="120"><a href="../product/productSetAdd.do">增加套装产品</href></td>
	  <td width="120"><a href="../product/productQuery.do">查找并修改产品</href></td>
  </tr>
</table>


</body>
</html>