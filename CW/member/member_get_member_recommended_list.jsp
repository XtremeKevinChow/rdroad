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
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<html:form action="/memberGetMemberInitAdd.do" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��Ա����</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">�Ƽ��ҵĻ�Ա��Ϣ</font>&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- <a href="javascript:history.back()">����</a> -->
      	</td>
   </tr>
</table>
<br>
<TABLE>
<TR>
	<TD><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	˵��:</font>�û�ԱĿǰ���ǻ�Ա�Ƽ����;�����Աȷʵ�ǻ�Ա�Ƽ���ᣬ����Ҫ�������Ƽ�����Ϣ,�뵥���������Ƽ��ˡ���ť�趨�Ƽ���</TD>
</TR>
</TABLE>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center"><input type="button" name="addBtn" value="�����Ƽ���" onclick="ajaxpage2('../member/memberGetMemberInitAdd.do?&recommendedId=<%=member_id%>&pageType=2', 'ajaxcontentarea',document.forms[0]);">&nbsp;&nbsp;
		<!-- <input type="button" name="backBtn" value=" ���� " onclick="back_f()"></td> -->
		
	</tr>
</table>
</html:form>
</body>
</html>
