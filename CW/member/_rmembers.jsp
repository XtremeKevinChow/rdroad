<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.member.entity.Member"%>
<%@ page import="com.magic.crm.user.entity.User"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
String member_id=request.getParameter("member_id");
if (member_id == null || member_id.equals("")) {
	member_id = ((Member)request.getSession().getAttribute("currentMember")).getID()+"";
}

String cardId = (String)request.getAttribute("cardId");
cardId = cardId == null ? "" : cardId;
session.setAttribute("cardId",cardId);
%>
<html>
<head>
<SCRIPT LANGUAGE="JavaScript">
<!--
function add_f() {
	document.forms[0].action = "/member/memberGetMemberInitAdd.do?&memberId=<%=member_id%>&pageType=1";
	document.forms[0].submit();
}
function new_recommand_member_f() {
	document.forms[0].action = "/member/member_addToken.do";
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

<table width="100%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" >

	<tr >
		<th width="20%"  class="OraTableRowHeader" noWrap   align=middle>��Ա��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>��Ա�ȼ�</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>��Ա����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>�Ա�</th>
		<th width="40%"  class="OraTableRowHeader" noWrap   align=middle>��Ӧ��ȯ</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>״̬</th>
	</tr>
	<logic:iterate name="memberGetMemberCol" id="member"> 
	<tr height="25" align="center">

		<td bgcolor="#FFFFFF">
			<a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format='#'/>"><bean:write name="member" property="CARD_ID"/></a>
		</td>

		<td bgcolor="#FFFFFF" align="left">
		<logic:equal name="member" property="LEVEL_ID" value="1">��ͨ��Ա</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="2">��ʽ��Ա</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="3">VIP��Ա</logic:equal>
		</td>

		<td bgcolor="#FFFFFF"  align="left">
			<bean:write name="member" property="NAME" />
		</td>

		<td bgcolor="#FFFFFF">
		<logic:equal name="member" property="GENDER" value="F">Ů</logic:equal>
		<logic:equal name="member" property="GENDER" value="M">��</logic:equal>
		</td>
		
		<td bgcolor="#FFFFFF"  align="left">
			<bean:write name="member" property="address1" />
		</td>	

		<td bgcolor="#FFFFFF" align="left">
			<logic:equal name="member" property="TIME_STATUS" value="0">�½�</logic:equal>
			<logic:equal name="member" property="TIME_STATUS" value="10">�ݴ��</logic:equal>
		</td>
	</tr>
</logic:iterate>

</table>

<!--
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		
		<td align="center">
			<input type="button" name="addBtn" value="�Ƽ��»�Ա" onclick=new_recommand_member_f();>&nbsp;&nbsp;
			
			<input type="button" name="addBtn" value="���Ƽ���Ա" onclick=ajaxpage2("../member/memberGetMemberInitAdd.do?memberId=<%=member_id%>&pageType=1","ajaxcontentarea",document.forms[0])>&nbsp;&nbsp;
			<input type="button" name="backBtn" value=" ���� " onclick=ajaxpage2("../member/memberDetail.do?&id=<%=member_id%>","ajaxcontentarea",document.forms[0])> 
			
		</td>
		
	</tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  >
	<tr>
		<td><font color=red>˵����</font>��Ա[<%=cardId%>]�Ƽ��Ļ�Ա�������ȯ���ϱ���ʾ�������Ե�����Ƽ��»�Ա���Ƽ�һ���µĻ�Ա��Ҳ���Ե�������Ƽ���Ա���Ƽ��Ѿ����ڵĻ�Ա��</td>
	</tr>
</table>-->
</html:form>
</body>
</html>
