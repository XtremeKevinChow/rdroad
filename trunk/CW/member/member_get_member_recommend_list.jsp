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
      		-&gt; </font><font color="838383">我推荐的会员信息</font>&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- <a href="javascript:history.back()">返回</a> -->
      	</td>
   </tr>
</table>
<br>

<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="750"  cellspacing=1 cellpadding=5>

	<tr >
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>会员号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>会员等级</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>会员姓名</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>性别</th>
		<th width="35%"  class="OraTableRowHeader" noWrap   align=middle>关联礼品</th>
		<th width="15%"  class="OraTableRowHeader" noWrap   align=middle>礼品价格</th>
		<th width="10%"  class="OraTableRowHeader" noWrap   align=middle>状态</th>
	</tr>
	<logic:iterate name="memberGetMemberCol" id="member"> 
	<tr height="25" align="center">

		<td class=OraTableCellText>
			<a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format='#'/>"><bean:write name="member" property="CARD_ID"/></a>
		</td>

		<td class=OraTableCellText align="left">
		<logic:equal name="member" property="LEVEL_ID" value="0">临时会员</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="1">普通会员</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="2">银卡会员</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="3">金卡会员</logic:equal>			
		</td>

		<td class=OraTableCellText  align="left">
			<bean:write name="member" property="NAME" />
		</td>

		<td class=OraTableCellText>
		<logic:equal name="member" property="GENDER" value="F">女</logic:equal>
		<logic:equal name="member" property="GENDER" value="M">男</logic:equal>
		</td>
		
		<td class=OraTableCellText  align="left">
			<bean:write name="member" property="address1" />
		</td>	

		<td class=OraTableCellText  align="right">
			<bean:write name="member" property="DEPOSIT" />
		</td>	
		
		<td class=OraTableCellText align="left">
			<logic:equal name="member" property="TIME_STATUS" value="0">新建</logic:equal>
			<logic:equal name="member" property="TIME_STATUS" value="10">暂存架</logic:equal>
		</td>
	</tr>
</logic:iterate>

</table>

  </td></tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		
		<td align="center">
			<input type="button" name="addBtn" value="推荐新会员" onclick="add_new_f()">&nbsp;&nbsp;
			
			<input type="button" name="addBtn" value="补推荐会员" onclick="add_recommend_f()">&nbsp;&nbsp;
			<input type="button" name="backBtn" value=" 返回 " onclick="back_recommend_f()">
		</td>
		
	</tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  >
	<tr>
		<td><font color=red>说明：</font>会员[<%=cardId%>]推荐的会员及获得礼品如上表所示，您可以点击“推荐新会员”推荐一个新的会员，也可以点击“补推荐会员”推荐已经存在的会员。</td>
	</tr>
</table>
</html:form>
</body>
</html>
