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
        alert(" ����������д");
        document.userForm.NAME.focus();
        return false;
      }

	   if(!document.userForm.GENDER[0].checked&&!document.userForm.GENDER[1].checked){
	   	alert('��ѡ���Ա�!');
			document.userForm.GENDER[0].focus();
			return false;;   
		}
      if(document.userForm.USERID.value==""){
        alert(" �û���������д");
        document.userForm.USERID.focus();
        return false;
      }
      if(document.userForm.PWD.value==""){
        alert(" ������������д");
        document.userForm.PWD.focus();
        return false;
      }          
      if(document.userForm.TITLE.value==""){
        alert(" ְ�������д");
        document.userForm.TITLE.focus();
        return false;
      }    
      if(document.userForm.EMPLOYEE_NUMBER.value==""){
        alert(" Ա�����������д");
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
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">�û�����</font><font color="838383">
              -&gt; </font><font color="838383">�����û�</font>
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
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>����</td>
                  <td>
                  		<html:text property="NAME"/>
                  </td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>�Ա�</td>
                  <td>
                      <input type="radio" name="GENDER" value="M" checked>��<input type="radio" name="GENDER" value="F">Ů
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>�û���</td>
                  <td>
                      
                      <html:text property="USERID"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>�û�����</td>
                  <td>
                      <html:password property="PWD"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>����</td>
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
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>ְ��</td>
                  <td>
                      <html:text property="TITLE"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>Ա����</td>
                  <td>
                      <html:text property="EMPLOYEE_NUMBER"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�ƶ��绰</td>
                  <td>
                      <html:text property="MOBILE_PHONE" size="30"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�绰����</td>
                  <td>
                      <html:text property="PHONE"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�����ʼ�</td>
                  <td>
                      <html:text property="EMAIL"/>
                	</td>
                </tr>
                
                  
                                         
                
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >������ɫ</td>
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
												<input type="button" value="�ύ"  onclick="return roleAdd()">
										
												<input type="button" value="ȡ��" onclick="javascript:window.history.go(-1)"/>
										</td>
								
								</tr>

        </table>

              </table>


</html:form>


</body>
</html>