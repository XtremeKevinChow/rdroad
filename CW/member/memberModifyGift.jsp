<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
String member_id=request.getParameter("member_id");
String recommended_id=request.getParameter("recommended_id");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function querySubmit() {
document.forms[0].submit();
}
function initFocus(){
	document.forms[0].item_ID.focus();
	return true;
}

function popup(str) 
{ 
window.opener.document.all["GIFT_ID"].value=str;
window.close()  
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">会员管理</font></b><font color="838383"> : </font><font color="838383"> 
      		-&gt; </font><font color="838383">礼品号修改</font>
      	</td>
   </tr>
</table>
<html:form  action="/memberModifyGift.do" method="post">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>货号：<input type=text name="item_ID"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="查询"  onclick="querySubmit()">
		</td>
		
	</tr>
</table>

<input type="hidden" name="recommended_id" value="<%=recommended_id%>">
<input type="hidden" name="member_id" value="<%=member_id%>">
<input type="hidden" name="isquery" value="1">
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<html:form  action="/memberModifyGiftok.do" method="post">
<table width="500"  cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr height="26" align="center">
		<td width="">&nbsp;</td>
		<td width="100">货号</td>
		<td width="320">礼品名称</td>
	</tr>
	<%int i=0;%>
  <logic:iterate id="memberGIFT" name="allmemberGIFT" >
	<tr height="26" align="center">
		<td width="" bgcolor="#FFFFFF"><input type="radio" name="gift_id" value="<bean:write name='memberGIFT' property='item_ID' />"></td>
		<td width="100" bgcolor="#FFFFFF"><bean:write name="memberGIFT" property="item_Code" format='#'/></td>	
		<td width="320" bgcolor="#FFFFFF"><bean:write name="memberGIFT" property="NAME" /></td>
	</tr>
	<%i++;%>
</logic:iterate>
</table>
<input type="hidden" name="recommended_id" value="<%=recommended_id%>">
  	<input type="hidden" name="member_id" value="<%=member_id%>"  >
  <%if(i>0){%>
  	<input type="submit" value="提交"  >
  <%}%>
</html:form>
  </td></tr>

</table>
</body>
</html>
