<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.magic.crm.common.Constants"%>
<%
Exception ex = (Exception)request.getAttribute(Globals.EXCEPTION_KEY);
String errorMsg = "";
if(ex == null)
{
	errorMsg =  (String)request.getAttribute(Constants.LOGIC_MESSAGE);
}
else
	{
	errorMsg = ex.getClass().getName() + "<br/>" +ex.getMessage()+ "<br/>" + ex.getLocalizedMessage();
	
	}

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
</head>

<body>
<form  >
<br>
<br>
<table width="50%" border="0" cellspacing="1" cellpadding="5" align="center">


   <tr>
      <th scope="col" width="5%" class=OraTableRowHeader >系统提示信息：</th>
   </tr>
  <tr>
            <td class=OraTableCellText noWrap align=middle >
			<!--<%=(String)request.getAttribute(Constants.LOGIC_MESSAGE)%>-->
			<%=errorMsg%>
			</td>
  </tr>
<% if (!"false".equals(hasBtn)) { %>  
<tr>
<td class="OraBGAccentDark" align=center>
<input type="button" value=" <%= btnName %> " class="button1" onclick="javascript:self.location = '<%= url %>'"></td>
</tr>
<% }  else {%>
<tr>
<td class="OraBGAccentDark" heigth=10>
</td>
</tr>
<% } %>
</table>
	
</form>

</body>
</html>
