<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="com.magic.crm.member.entity.Member"%>
<%@ page import="com.magic.crm.user.entity.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%
Member member = (Member)request.getSession().getAttribute("currentMember");
member = member == null ? new Member() : member;

HttpSession userSession = request.getSession();
User user = (User)userSession.getAttribute("user");
        String LogID=request.getParameter("LogID");
        LogID=(LogID==null)?"":LogID;
%>
<html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../css/style.css" type="text/css">
	<link rel="stylesheet" href="css/webboard.css" type="text/css">
	
<title>CRM</title>
<SCRIPT src="/toolbar/coolbuttons.js" type=text/javascript></SCRIPT>
</head>

<body  class=toolBarBody bgcolor="#183648" marginheight="0" marginwidth="0" leftmargin="0" topmargin="0" >

<table  width="100%" cellpadding="1" cellspacing="0" border="0" bgcolor="#183648" >
	<tr height="36">
		<td >
			<img src="images/logo.gif"  height="36" width="260" border="0" >&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	
		<td  align="right">
			<table cellpadding="0" cellspacing="0" border="0" >
				<tr height="25"  align="right">
					<td  align="right">
					<img src="images/icon.gif" align="middle">
					<a href="./member/memberDetail.do?iscallcenter=1" target="main" class="color1">服务台</a>&nbsp;&nbsp;
					<img src="images/icon.gif" align="middle">
					<a href="./order/orderAddFirst.do" target="main" class="color1">新增订单</a>&nbsp;&nbsp;
					<img src="images/icon.gif" align="middle">
					<a href="./member/member_addToken.do" target="main" class="color1">新增会员</a>&nbsp;&nbsp;
					<img src="images/icon.gif" align="middle">
					<a href="./member/query.do" target="main" class="color1">会员查询</a>&nbsp;&nbsp;
					<img src="images/icon.gif" align="middle">
					<a href="./order/orderQuery.do" target="main" class="color1">订单查询</a>&nbsp;&nbsp;
					<img src="images/icon.gif" align="middle">
					<a href="./order/snQry.do" target="main" class="color1">发货单查询</a>&nbsp;&nbsp;
					<img src="images/icon.gif" align="middle">
					<a href="/userLogonOut.do?flag=logout&LogID=<%=LogID%>" class="color1" target="_top">退出系统</a>					
					<img src="images/spacer.gif" width="6" height="1">
					</td>
				</tr>
			</table>			
		</td>
	</tr>
</table>

</body>

</html>
