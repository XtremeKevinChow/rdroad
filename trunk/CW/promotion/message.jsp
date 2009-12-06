<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.magic.crm.common.Constants"%>
<%
String hasBtn = (String)request.getAttribute("hasBtn");
String url = (String)request.getAttribute("url");
if(url == null) {
	url = "javascript:history.back(-1);";
} else {
	url = request.getContextPath() + "/" + url;
}
String btnName = (String)request.getAttribute("btnName");
if(btnName == null) {
	btnName = " 返 回 ";
}
%>
<html>
<head>
<title>操作信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="../css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>

<body>
<form  >
<br>
<br>
<table width="50%" border="0" cellspacing="1" cellpadding="5" align="center">


   <tr>
      <th scope="col" width="5%" class=OraTableColumnHeader >Message:&nbsp;</th>
   </tr>
  <tr>
            <td class=OraTableCellText noWrap align=middle >
			<%=(String)request.getAttribute(Constants.LOGIC_MESSAGE)%>
			</td>
  </tr>
<% if (!"false".equals(hasBtn)) { %>  
<tr>
<td class="OraBGAccentDark" align=center>
<input type="button" value=" <%= btnName %> " class="button1" onclick="javascript:self.location = '<%= url %>'"></td>
</td>
</tr>
<% }  else {%>
<tr>
<td class="OraBGAccentDark" heigth=10>
</td>
</td>
</tr>
<% } %>
</table>
	
</form>

</body>
</html>
