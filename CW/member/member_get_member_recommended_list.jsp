<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
String member_id=(String)request.getAttribute("recommendedId");

%>
<html>
<head>
<SCRIPT LANGUAGE="JavaScript">
<!--
function add_recommended_member_f() {
	document.forms[0].action = "/member/memberGetMemberInitAdd.do?&recommendedId=<%=member_id%>&pageType=2";
	document.forms[0].submit();
}
function back_f() {
	document.forms[0].action = "/member/memberDetail.do?&id=<%=member_id%>";
	document.forms[0].submit();
}
//-->
</SCRIPT>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/memberGetMemberInitAdd.do" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">会员管理</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">推荐我的会员信息</font>&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- <a href="javascript:history.back()">返回</a> -->
      	</td>
   </tr>
</table>
<br>
<TABLE>
<TR>
	<TD><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	说明:</font>该会员目前不是会员推荐入会;如果会员确实是会员推荐入会，并且要求增加推荐人信息,请单击“增加推荐人”按钮设定推荐人</TD>
</TR>
</TABLE>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center"><input type="button" name="addBtn" value="增加推荐人" onclick="ajaxpage2('../member/memberGetMemberInitAdd.do?&recommendedId=<%=member_id%>&pageType=2', 'ajaxcontentarea',document.forms[0]);">&nbsp;&nbsp;
		<!-- <input type="button" name="backBtn" value=" 返回 " onclick="back_f()"></td> -->
		
	</tr>
</table>
</html:form>
</body>
</html>
