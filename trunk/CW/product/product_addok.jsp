<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
  String item_code=request.getAttribute("item_code").toString();
%>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>������Ա��ϵ����ϵͳ</title>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table>
  <tr  align="center">
    <td width="200" align="left">����Ϊ��<font color="red"><%=item_code%></font><td>
	  <td width="120"></td>
	  <td width="120"></td>
	  <td width="120"></td>
  </tr>
  <tr  align="center">
    <td width="200">�����Ѿ��ɹ�,��ѡ������һ���Ĳ���<td>
	  <td width="120"><a href="../product/initProductAdd.do">���Ӳ�Ʒ</href></td>
	  <td width="120"><a href="../product/productSetAdd.do">������װ��Ʒ</href></td>
	  <td width="120"><a href="../product/productQuery.do">���Ҳ��޸Ĳ�Ʒ</href></td>
  </tr>
</table>


</body>
</html>