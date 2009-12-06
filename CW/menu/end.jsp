<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*,com.magic.crm.user.entity.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE></TITLE>
<%
User user=new User();
user = (User)session.getAttribute("user");
%>
<META content="text/html; charset=gb2312" http-equiv=Content-Type>

<link rel="stylesheet" href="/css/style.css" type="text/css">
<script language="javascript">
	function closeWindow() {
		
		parent.parent.window.close()
	}
</script>
<META content="MSHTML 5.00.3315.2870" name=GENERATOR></HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
<table border=0 cellpadding=0 cellspacing=0 height=20 width="100%" bgcolor="C3D5FD">
  <tr> 
  	<td width="200" align="left"><nobr>&nbsp;&nbsp;<b>工号：</b><%=user.getEMPLOYEE_NUMBER()%> &nbsp;&nbsp;<b>姓名：</b><%=user.getNAME()%></nobr></td>
    <td valign=bottom width=*%> 
      <center>
        版权 &copy; 2008 上海轶轮信息技术有限公司 
      </center>
    </td>
    <td align="right"><a href="/userLogonOut.do" class="color3" target=_top><nobr>退出系统</nobr></a>&nbsp;&nbsp;</td>
  </tr>
</table>
</BODY>
</HTML>
