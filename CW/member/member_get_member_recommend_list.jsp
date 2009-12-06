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
function add_recommend_f() {
	document.forms[0].action = "/member/memberGetMemberInitAdd.do?&memberId=<%=member_id%>&pageType=1";
	document.forms[0].submit();
}
function new_recommend_f() {
	document.forms[0].action = "/member/member_addToken.do";
	document.forms[0].submit();
}
function back_recommend_f() {
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
      		-&gt; </font><font color="838383">���Ƽ��Ļ�Ա��Ϣ</font>&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- <a href="javascript:history.back()">����</a> -->
      	</td>
   </tr>
</table>
<br>

<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="750"  cellspacing=1 cellpadding=5>

	<tr >
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>��Ա��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>��Ա�ȼ�</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>��Ա����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>�Ա�</th>
		<th width="35%"  class="OraTableRowHeader" noWrap   align=middle>������Ʒ</th>
		<th width="15%"  class="OraTableRowHeader" noWrap   align=middle>��Ʒ�۸�</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>״̬</th>
	</tr>
	<logic:iterate name="memberGetMemberCol" id="member"> 
	<tr height="25" align="center">

		<td class=OraTableCellText>
			<a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format='#'/>"><bean:write name="member" property="CARD_ID"/></a>
		</td>

		<td class=OraTableCellText align="left">
		<logic:equal name="member" property="LEVEL_ID" value="0">��ʱ��Ա</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="1">��ͨ��Ա</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="2">������Ա</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="3">�𿨻�Ա</logic:equal>			
		</td>

		<td class=OraTableCellText  align="left">
			<bean:write name="member" property="NAME" />
		</td>

		<td class=OraTableCellText>
		<logic:equal name="member" property="GENDER" value="F">Ů</logic:equal>
		<logic:equal name="member" property="GENDER" value="M">��</logic:equal>
		</td>
		
		<td class=OraTableCellText  align="left">
			<bean:write name="member" property="address1" />
		</td>	

		<td class=OraTableCellText  align="right">
			<bean:write name="member" property="DEPOSIT" />
		</td>	
		
		<td class=OraTableCellText align="left">
			<logic:equal name="member" property="TIME_STATUS" value="0">�½�</logic:equal>
			<logic:equal name="member" property="TIME_STATUS" value="10">�ݴ��</logic:equal>
		</td>
	</tr>
</logic:iterate>

</table>

  </td></tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		
		<td align="center">
			<input type="button" name="addBtn" value="�Ƽ��»�Ա" onclick="add_new_f()">&nbsp;&nbsp;
			
			<input type="button" name="addBtn" value="���Ƽ���Ա" onclick="add_recommend_f()">&nbsp;&nbsp;
			<input type="button" name="backBtn" value=" ���� " onclick="back_recommend_f()">
		</td>
		
	</tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  >
	<tr>
		<td><font color=red>˵����</font>��Ա[<%=cardId%>]�Ƽ��Ļ�Ա�������Ʒ���ϱ���ʾ�������Ե�����Ƽ��»�Ա���Ƽ�һ���µĻ�Ա��Ҳ���Ե�������Ƽ���Ա���Ƽ��Ѿ����ڵĻ�Ա��</td>
	</tr>
</table>
</html:form>
</body>
</html>
