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
        alert(" ����������д");
        document.userForm.NAME.focus();
        return false;
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
<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">�û�����</font><font color="838383">
              -&gt; </font><font color="838383">�޸��û�</font>
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
                  <td width="120px"  class="OraTableRowHeader" noWrap >����</td>
                  <td >
                  		<html:text property="NAME"/>&nbsp;<font color="red">*</font>
                  </td>
                </tr>
                
                <tr>
                  <td  class="OraTableRowHeader" noWrap >�Ա�</td>
                  <td >
                      <html:radio property="GENDER" value="M"/>��
                      <html:radio property="GENDER" value="F"/>Ů&nbsp;<font color="red">*</font>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�û���</td>
                  <td >
                      
                      <html:text property="USERID" />&nbsp;<font color="red">*</font>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�û�����</td>
                  <td >
                      <html:password property="PWD"/>&nbsp;<font color="red">*</font><input type="checkbox" name="ifModify" value="1" >ȷ��Ҫ�����޸�����
                	</td>
                </tr class="OraTableRowHeader" noWrap >
                
                <tr>
                  <td  class="OraTableRowHeader" noWrap >����</td>
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
                  <td class="OraTableRowHeader" noWrap >���ֲ�ID</td>
                  <td >
											<select name="COMPANY_ID">

											<option value="1" <%if(user.getCOMPANY_ID()==1){%>selected<%}%> >�Ϻ��Ļ�ʵҵ���޹�˾</option>
											<option value="0" <%if(user.getCOMPANY_ID()==0){%>selected<%}%> >��</option>
											</select>&nbsp;<font color="red">*</font>                      
                	</td>
                </tr>-->
                <input type="hidden" name="COMPANY_ID" value="1">
                <input type="hidden" name="EMPLOYEE_TYPE" value="1">
                
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >Ա������</td>
                  <td >
											<select name="EMPLOYEE_TYPE">
											<option value="1" <%if(user.getEMPLOYEE_TYPE()==1){%>selected<%}%> >Ա��</option>
											<option value="0" <%if(user.getEMPLOYEE_TYPE()==0){%>selected<%}%> >�༭</option>
											</select>&nbsp;<font color="red">*</font>                       
                	</td>
                </tr>-->
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >ְ��</td>
                  <td >
                      <html:text property="TITLE"/>&nbsp;<font color="red">*</font>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >Ա������</td>
                  <td >
                      <html:text property="EMPLOYEE_NUMBER"/>&nbsp;<font color="red">*</font>
                	</td>
                </tr>

                <tr>
                  <td class="OraTableRowHeader" noWrap >��ַ</td>
                  <td >
                     
                  <select name="LOCATION_ID">
                  	<option value="1"<%if(user.getLOCATION_ID()==1){%>selected<%}%> >�ܲ�</option>
                  	<option value="2"<%if(user.getLOCATION_ID()==2){%>selected<%}%> >��������</option>
                  	<option value="0"<%if(user.getLOCATION_ID()==0){%>selected<%}%> >(��)</option>
                  </select>&nbsp;<font color="red">*</font>                        
                	</td>
                </tr> 

                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�ƶ��绰</td>
                  <td >
                      <html:text property="MOBILE_PHONE" size="30"/>
                	</td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�绰����</td>
                  <td >
                      <html:text property="PHONE"/>
                	</td>
                </tr>
                
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >BP��</td>
                  <td >
                      <html:text property="BP"/>
                	</td>
                </tr>--><html:hidden property="BP"/>
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap >��Ƭ</td>
                  <td >
                      <html:text property="PIC_URL"/>
                	</td>
                </tr>--><html:hidden property="PIC_URL"/>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >�����ʼ�</td>
                  <td >
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
								<html:hidden property="id"/>
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