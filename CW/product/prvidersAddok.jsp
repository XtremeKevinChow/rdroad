<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>������Ա��ϵ����ϵͳ</title>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<script language="JavaScript" src="utils.js"></script>
<script src="calendar.js"></script>
<script src="go_top.js"></script>
<script language="JavaScript" src="common.js"></script>
<script language="JavaScript">
  function querySubmit(){
		document.SearchForm.is_query.value="true";
		document.SearchForm.submit();
	}
</script>
<br>



<br>
<table>
  <tr>
    <td>�����Ѿ��ɹ�,<a href="../product/prvidersAdd.do?type=add">�������ӹ�Ӧ��</href></td>
  </tr>
</table>


</body>
</html>