<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.dao.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title></title>
<script language=javascript>
      function roleAdd(){
      if(document.roleForm.pathName.value==""){
        alert("·����������д");
        document.roleForm.pathName.focus();
        return false;
      }

      if(document.roleForm.path.value==""){
        alert("·��������д");
        document.roleForm.path.focus();
        return false;
      }
      //document.userForm.submit();
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
              -&gt; </font><font color="838383">����·��</font>
          </td>
   </tr>
</table>
<br>
<html:form method="post" action="/pathaddok.do">

<table width="90%" align="center" cellspacing="0" cellspacing="0" border="0" style="border: 1px" class="en" frame="box">
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
        		
			try{
			conn = DBManager.getConnection();		  
			String sql=" select * from crm_rights where parentid=0 ";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
		%>
	<tr>
        <td align="right">
        
         <table width="100%" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
               <tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>�ڵ�����</td>
                  <td>
                  		<select name="rightid">
                  		<option value="0">--�½ڵ�--</option>
<%
				while(rs.next()){ 	
				String rightname=rs.getString("rightname");
				String rightid=rs.getString("rightid");										                		
                  		%>
                  		  <option value="<%=rightid%>"><%=rightname%></option>
 
        <%}%>
       
                  		</select>
                  </td>
                </tr>
<%
} catch(Exception se) {

			se.printStackTrace();
	
 } finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {}			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
 }

%> 
              	<tr>
                  <td width="20%"  class="OraTableRowHeader" noWrap ><font color="red">*</font>·������</td>
                  <td>
                  		<html:text property="pathName" size="50" />
                  </td>
                </tr>
                

                
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>·��</td>
                  <td>
                      
                      <input type="text" name="path" size="50" />����:/pathadd.do
                	</td>
                </tr>
                <!--
                <tr>
                  <td class="OraTableRowHeader" noWrap ><font color="red">*</font>�Ƿ񱨱�ڵ�</td>
                  <td>
                      
                      <input type="radio" name="radio" value="0"  />��
                      <input type="radio" name="radio" value="1" checked />��
                	</td>
                </tr>                
                -->
                <input type="hidden" name="radio" value="1">

                <tr  align="center">
								
										<td colspan="2">
												<input type="submit" value="�ύ"  onclick="return roleAdd()">
										
												<input type="button" value="ȡ��" onclick="javascript:window.history.go(-1)"/>
										</td>
								
								</tr>

        </table>

              </table>


</html:form>


</body>
</html>