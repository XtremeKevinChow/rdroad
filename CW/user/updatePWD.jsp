<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.entity.User" %>
<%
       User user= new User();
       user = (User)session.getAttribute("user"); 
%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>
<script language=javascript>
      function roleAdd(){
      if(document.userForm.password.value==""){
        alert(" 密码名必须填写");
        document.userForm.password.focus();
        return false;
      }          
             
        document.userForm.submit();
      }
    //-->
    </script>

</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">用户管理</font><font color="838383">
              -&gt; </font><font color="838383">修改用户密码</font>
          </td>
   </tr>
</table>
<br>
<html:form method="post" action="/updatePWD.do" >

<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px  #000000" class="en" frame="box">
	<tr>
        <td align="center">
        	<table width="100%" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >用户名</td>
                  <td >
                      
                      <%=user.getUSERID()%>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >新密码</td>
                  <td >
                      <input type="password" name="password" >&nbsp;<font color="red">*</font>
                	</td>
                </tr class="OraTableRowHeader" noWrap >

								<tr  align="center">
										<td colspan="2">
												<input type="button" value="提交"  onclick="return roleAdd()">
										
												<input type="button" value="取消" onclick="javascript:window.history.go(-1)"/>
										</td>
								
								</tr>
        </table>

              </table>

<input type="hidden" name="tag" value="1">
</html:form>


</body>
</html>