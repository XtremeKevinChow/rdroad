<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.dao.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String roleid=request.getParameter("roleID");
      //out.println("roleid is "+roleid); 
			try{
			conn = DBManager.getConnection();		        
%>

<html>
<head>

    <link rel="stylesheet" href="<html:rewrite page='/css/style.css'/>" type="text/css">
<title>right</title>

<script language=javascript>
      function roleAdd(){
      if(document.roleForm.roleName.value==""){
        alert(" 角色名必须填写");
        document.roleForm.roleName.focus();
        return false;
      }
        document.roleForm.submit();
      }

	  function checkAll(flag, obj) {
		
		var frm  = document.forms[0];

			for (var i = 0; i < frm.rights.length ; i ++)
			{
				var obj2 = frm.rights[i];
				
				if ( obj2.rtname == obj.value )
				{
					if (flag)
					{
						obj2.checked = true;
					} else {
						obj2.checked = false;
						
					}
					 
				} 
			}
		
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
              -&gt; </font><font color="838383">新增角色</font>
          </td>
   </tr>
</table>
<html:form method="post" action="/createRole.do">

<table width="100%" cellspacing="0" cellspacing="0" border="0" style="border: 1px #000000" class="en" frame="box">
	<tr>
        <td align="center">
        <br>
         <table width="90%" align="center" cellspacing="0" cellspacing="0" border="1" bordercolordark="#ffffff"  class="en">
              	<tr>
                  <td width="12%" class="OraTableRowHeader" noWrap >角色名</td>
                  <td>
                  		<html:text property="roleName"/><font color="red">*</font>
                  		<html:hidden property="roleID"/>
                  </td>
                </tr>
                
                <tr>
                  <td class="OraTableRowHeader" noWrap >角色描述</td>
                  <td>
                      <html:textarea cols="60" rows="3" property="description"/>
                	</td>
                </tr>
                
               
                <tr>
                  <td class="OraTableRowHeader" noWrap >所具权限</td>
                  <td>
                  		
                      <table border="0" width="100%">
                      <%
                         sql="select * from crm_rights where parentid=0";
													pstmt=conn.prepareStatement(sql);
													rs=pstmt.executeQuery();  
													
													while(rs.next()){ 
													int i=0; 
													String rightid=rs.getString("rightid");      
													String rightname=rs.getString("rightname");                 
                      %>
                          <tr class="OraTableRowHeader" noWrap >
                             <td colspan="3">全选<input type="checkbox" onclick="checkAll(this.checked, this.parentElement.getElementsByTagName('INPUT')[1]);">&nbsp;<font color="red"><input type="hidden" name="parentRight" value="<%=rightname%>"><%=rightname%></font></td>
                          </tr>

                             <%
                                  String sql1="select * from crm_rights where parentid="+rightid;
                                  PreparedStatement pstmt1=conn.prepareStatement(sql1);
													        ResultSet rs1=pstmt1.executeQuery(); 
													while(rs1.next()){    
													String rightname1=rs1.getString("rightname"); 
													String rightid1=rs1.getString("rightid");
															        
                                %>  
                     			<%
                
                     				if(i++%3==0) {
                     					out.println("<tr>");
                     				}
                     			%>                                                    	 
                      		<td><input type="checkbox" value="<%=rightid1%>" name="rights" rtname="<%=rightname%>"><%=rightname1%></td>
                      		
                      	  <%
                     				if(i%3==0) {
                     					out.println("</tr>");
                     				}
                     			 }//while;
                      			
                      		%>  
                      		<%
       
                      		rs1.close();
                      		pstmt1.close();
                      		%>                        
                      <%
                      }//first while//                      
                      %>
                      </table>
                      
                	</td>
                </tr>
						
        </table>
     </td>
   </tr>

</table>
<p align ="center">
<input type="button" value=" 提交 "  onclick="return roleAdd()">
<input type="button" value=" 取消 " onclick="javascript:window.history.go(-1)"/>
</p>

</html:form>
</body>
</html>
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