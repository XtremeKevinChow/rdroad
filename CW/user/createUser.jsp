<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.dao.*" %>
<%
UserDAO userdao=new UserDAO();
%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title></title>
<script language=javascript>
      function roleAdd(){
      if(document.userForm.NAME.value==""){
        alert(" 姓名必须填写");
        document.userForm.NAME.focus();
        return false;
      }

	   if(!document.userForm.GENDER[0].checked&&!document.userForm.GENDER[1].checked){
	   	alert('请选择性别!');
			document.userForm.GENDER[0].focus();
			return false;;   
		}
      if(document.userForm.USERID.value==""){
        alert(" 用户名必须填写");
        document.userForm.USERID.focus();
        return false;
      }
      if(document.userForm.PWD.value==""){
        alert(" 密码名必须填写");
        document.userForm.PWD.focus();
        return false;
      }          
      if(document.userForm.TITLE.value==""){
        alert(" 职务必须填写");
        document.userForm.TITLE.focus();
        return false;
      }    
      if(document.userForm.EMPLOYEE_NUMBER.value==""){
        alert(" 员工号码必须填写");
        document.userForm.EMPLOYEE_NUMBER.focus();
        return false;
      }                  
        document.userForm.submit();
      }
    //-->
    </script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">用户管理</font><font color="838383">
              -&gt; </font><font color="838383">新增用户</font>
          </td>
   </tr>
</table>
<br>
<html:form method="post" action="/createUser.do">

<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px" class="en" frame="box">
	<tr>
        <td align="right">
        
         <table width="100%" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
              	<tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>姓名</td>
                  <td>
                  		<html:text property="NAME"/>
                  </td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>性别</td>
                  <td>
                      <input type="radio" name="GENDER" value="M" checked>男<input type="radio" name="GENDER" value="F">女
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>用户名</td>
                  <td>
                      
                      <html:text property="USERID"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>用户密码</td>
                  <td>
                      <html:password property="PWD"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>部门</td>
                  <td>
											<select name="DEPARTMENT_ID">
											<%
											  for(int i=0;i<userdao.getDeptment().length;i++){
											%>
											<option value="<%=i%>"<%if(i==1){%>selected<%}%>><%=userdao.getDeptment()[i]%></option>
											<%}%>
											</select>
	
                	</td>
                </tr>
                <input type=hidden name="COMPANY_ID" value="0">
                <input type=hidden name="EMPLOYEE_TYPE" value="1">
                <input type=hidden name="LOCATION_ID" value="1">
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>职务</td>
                  <td>
                      <html:text property="TITLE"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>员工号</td>
                  <td>
                      <html:text property="EMPLOYEE_NUMBER"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >移动电话</td>
                  <td>
                      <html:text property="MOBILE_PHONE" size="30"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >电话号码</td>
                  <td>
                      <html:text property="PHONE"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >电子邮件</td>
                  <td>
                      <html:text property="EMAIL"/>
                	</td>
                </tr>
                
                  
                                         
                
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >所属角色</td>
                  <td>
                  		
                      <table border="0" width="90%">
                      		<%int i=0;%>
                     			<logic:iterate id="role" name="allRoles">	
                     			<%
                
                     				if(i++%3==0) {
                     					out.println("<tr>");
                     				}
                     			%>
                      	
                      		<td>
                      			<html:multibox property="roles" >
                      					<bean:write name="role" property="roleID"/>
                      			</html:multibox>
                      					<bean:write name="role" property="roleName"/>
                      			
                      		</td>
                      		
                      	  <%
                     				if(i%3==0) {
                     					out.println("</tr>");
                     				}
                     			%>
                      			</logic:iterate>
                      		<%
                      			if (i%3 != 0){
                      				out.println("</tr>");
                      			}
                      		%>
                      </table>
                      
                	</td>
                </tr>

                <tr  align="center">
								
										<td colspan="2">
												<input type="button" value="提交"  onclick="return roleAdd()">
										
												<input type="button" value="取消" onclick="javascript:window.history.go(-1)"/>
										</td>
								
								</tr>

        </table>

              </table>


</html:form>


</body>
</html>