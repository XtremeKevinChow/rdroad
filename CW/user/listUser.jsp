<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rootPath = request.getContextPath();
%>
<html>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>
<script language="javascript">
function delUser(id){

  if (confirm("确定删除该用户？")){
  	window.location.href="deleteUser.do?id="+id;
  }
}
</script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">用户管理</font><font color="838383">
              -&gt; </font><font color="838383">用户查询</font>
          </td>
   </tr>
</table>
<br>

<table width="95%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
  
  <tr height="24" valign="middle">
	<th width="10%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;帐户</th>
    	<th width="15%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;姓名</th>
    	<th width="15%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;部门</th>
    	<th width="10%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;工号</th>
    	<th width="15%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;职务</th>
    	<th width="20%" class="OraTableRowHeader" noWrap  class="TableHeader" align="center" >&nbsp;Email</th>
    	<th width="" class="OraTableRowHeader" noWrap  class="TableText" align="center" >&nbsp;操作</th>
  </tr>


  <logic:iterate id="user" name="<%=Constants.ALL_USERS%>" >
  
  <html:form  action="/initUserEdit.do" method="post">
  <tr align="center">
    <td align="left" >&nbsp;
      <bean:write name="user" property="USERID" filter="true"/>
    </td>  
    <td align="left">&nbsp;
      <bean:write name="user" property="NAME" filter="true"/>
    </td>
    <td align="left">&nbsp;
    <logic:equal name="user" property="DEPARTMENT_ID" value="0">无</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="1">市场部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="2">客服部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="3">编辑部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="4">人事行政部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="5">IT部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="6">物流部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="7">总经理室</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="8">财务部</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="9">网站</logic:equal>
    <logic:equal name="user" property="DEPARTMENT_ID" value="10">销售部</logic:equal>
    </td>      
    <td align="left">&nbsp;
      <bean:write name="user" property="EMPLOYEE_NUMBER" filter="true"/>
    </td>
    <td align="left">&nbsp;
      <bean:write name="user" property="TITLE" filter="true"/>
    </td>
    <td align="left">&nbsp;
      <bean:write name="user" property="EMAIL" filter="true"/>
    </td>
    <td align="center">&nbsp;
      <html:hidden name="user" property="id" />
      <html:submit  value="编辑" />
      <input type="button"  value="删除" onclick="javasript:delUser(<bean:write name="user" property="id"/>)">
    </td>
  </tr>
  </html:form>
</logic:iterate>

</table>


</body>

</html>
