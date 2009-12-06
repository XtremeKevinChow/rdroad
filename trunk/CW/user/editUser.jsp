<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.dao.*" %>
<%@ page import="com.magic.crm.user.entity.*" %>
<%
UserDAO userdao=new UserDAO();
User user=new User();
user=(User)request.getAttribute("user");
%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>
<script language=javascript>
      function roleAdd(){
      if(document.userForm.NAME.value==""){
        alert(" 姓名必须填写");
        document.userForm.NAME.focus();
        return false;
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
<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>当前位置</b></font><font color="838383"> : </font><font color="838383">用户管理</font><font color="838383">
              -&gt; </font><font color="838383">修改用户</font>
          </td>
   </tr>
</table>
<br>
<html:form method="post" action="/editUser.do" >

<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px  #000000" class="en" frame="box">
	<tr>
        <td align="center">
        	<table width="100%" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
              	<tr>
                  <td width="120px"  class="OraTableRowHeader" noWrap >姓名</td>
                  <td >
                  		<html:text property="NAME"/>&nbsp;<font color="red">*</font>
                  </td>
                </tr>
                
                <tr>
                  <td  class="OraTableRowHeader" noWrap >性别</td>
                  <td >
                      <html:radio property="GENDER" value="M"/>男
                      <html:radio property="GENDER" value="F"/>女&nbsp;<font color="red">*</font>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >用户名</td>
                  <td >
                      
                      <html:text property="USERID" />&nbsp;<font color="red">*</font>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >用户密码</td>
                  <td >
                      <html:password property="PWD"/>&nbsp;<font color="red">*</font><input type="checkbox" name="ifModify" value="1" >确定要重新修改密码
                	</td>
                </tr class="OraTableRowHeader" noWrap >
                
                <tr>
                  <td  class="OraTableRowHeader" noWrap >部门</td>
                  <td >
											<select name="DEPARTMENT_ID">
											<%
											  for(int i=0;i<userdao.getDeptment().length;i++){
											%>
											<option value="<%=i%>"<%if(i==user.getDEPARTMENT_ID()){%>selected<%}%>><%=userdao.getDeptment()[i]%></option>
											<%}%>
											</select>&nbsp;<font color="red">*</font>
                	</td>
             
                </tr>
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >俱乐部ID</td>
                  <td >
											<select name="COMPANY_ID">

											<option value="1" <%if(user.getCOMPANY_ID()==1){%>selected<%}%> >上海文化实业有限公司</option>
											<option value="0" <%if(user.getCOMPANY_ID()==0){%>selected<%}%> >无</option>
											</select>&nbsp;<font color="red">*</font>                      
                	</td>
                </tr>-->
                <input type="hidden" name="COMPANY_ID" value="1">
                <input type="hidden" name="EMPLOYEE_TYPE" value="1">
                
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >员工类型</td>
                  <td >
											<select name="EMPLOYEE_TYPE">
											<option value="1" <%if(user.getEMPLOYEE_TYPE()==1){%>selected<%}%> >员工</option>
											<option value="0" <%if(user.getEMPLOYEE_TYPE()==0){%>selected<%}%> >编辑</option>
											</select>&nbsp;<font color="red">*</font>                       
                	</td>
                </tr>-->
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >职务</td>
                  <td >
                      <html:text property="TITLE"/>&nbsp;<font color="red">*</font>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >员工号码</td>
                  <td >
                      <html:text property="EMPLOYEE_NUMBER"/>&nbsp;<font color="red">*</font>
                	</td>
                </tr>

                <tr>
                  <td class="OraTableRowHeader" noWrap >地址</td>
                  <td >
                     
                  <select name="LOCATION_ID">
                  	<option value="1"<%if(user.getLOCATION_ID()==1){%>selected<%}%> >总部</option>
                  	<option value="2"<%if(user.getLOCATION_ID()==2){%>selected<%}%> >配送中心</option>
                  	<option value="0"<%if(user.getLOCATION_ID()==0){%>selected<%}%> >(无)</option>
                  </select>&nbsp;<font color="red">*</font>                        
                	</td>
                </tr> 

                
                <tr>
                  <td class="OraTableRowHeader" noWrap >移动电话</td>
                  <td >
                      <html:text property="MOBILE_PHONE" size="30"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >电话号码</td>
                  <td >
                      <html:text property="PHONE"/>
                	</td>
                </tr>
                
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >BP机</td>
                  <td >
                      <html:text property="BP"/>
                	</td>
                </tr>--><html:hidden property="BP"/>
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >照片</td>
                  <td >
                      <html:text property="PIC_URL"/>
                	</td>
                </tr>--><html:hidden property="PIC_URL"/>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >电子邮件</td>
                  <td >
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
								<html:hidden property="id"/>
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