<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String id=request.getParameter("id");
String create_date=request.getParameter("create_date");

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td width="280"><nobr>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա����̨</font><font color="838383"> </nobr>
      	</td>
      	<td align="right">
      		&nbsp;
      	</td>
   </tr>
</table>

<form action="../member/member_changeClub_ok.jsp" method="post">
<table width="75%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >

	<tr height="26">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;���ֲ�</td>
		<td  align="left" bgcolor="#FFFFFF">
		<select name="clubid">
			<option value="2">99���䱦��</option>
		</select>&nbsp;<input type="submit"  value="ȷ��">	
		</td>
	</tr>
</table>

<br>
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="create_date" value="<%=create_date%>">

</form>

	</table>
</body>
</html>
