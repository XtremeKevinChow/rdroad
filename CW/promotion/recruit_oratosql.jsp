<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.promotion.entity.*"%>
<%@ page import="com.magic.crm.promotion.dao.*"%>
<%@ page import="com.magic.crm.io.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
User user=new User();
user = (User)session.getAttribute("user");
OraToSql oratosql=new OraToSql();
try {
	oratosql.execute();
} catch (Exception e) {
	out.println("<font color=\"red\">����ʧ��</font>");
	return;
}
%>

<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">�����ɹ�<a href="recruit_activity_list.jsp">�����б�</a></font></td>
	
		</td>		
	</tr>	
</table>

</body>
</html>
