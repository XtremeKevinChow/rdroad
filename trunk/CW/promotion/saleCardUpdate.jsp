<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String card_num=request.getParameter("card_num");
String tag=request.getParameter("tag");
String card_type=request.getParameter("card_type");
%>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function setfocus(){
<%if(tag.equals("2")){%>
	document.form.card_id.focus();
	<%}%>
}
function Submit(){

	var form = document.form;
	<%if(tag.equals("2")){%>
	if(form.card_id.value == ""){
		alert("���������Ա���룡");
		form.card_id.select();
		return false;
	}
	if(form.pass_num.value == ""){
		alert("���������ֵ�����룡");
		form.pass_num.select();
		return false;
	}
	if(isNaN(document.form.pass_num.value)){
	alert('��ֵ������ֻ��Ϊ����!');
	document.form.pass_num.select();
	return false;
	}		
	<%}%>
		document.form.search.disabled = true;
		document.form.action = "saleCardUpdate.do";
		document.form.submit();	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">���ۿ����ۻ��ֵ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<br><br>
<form action="" name="form" method="post">
  <table align="center" border=0 cellspacing=1 cellpadding=3 class="OraTableRowHeader" noWrap >
    <tr>
		
      <td width="80" >���ۿ��ţ�</td>
      <td width="*" bgcolor="#FFFFFF"> <%=card_num%></td>
    </tr>
      <%
      if(tag.equals("2")){
      %>      
    <tr>		
      <td >��Ա�ţ�</td>
      <td bgcolor="#FFFFFF"><input name = "card_id" value="" type="text" size=16/></td>
    </tr>
    <tr>		
      <td >���룺</td>
      <td bgcolor="#FFFFFF"><input name = "pass_num" value="" type="password" size=16/></td>
    </tr>    
    <tr>
	<td bgcolor="#FFFFFF" width="95%" colspan="2" align="center">
      	<input type="button" name="search" value="�� ֵ" onclick="Submit()">
     </td>
    </tr>
    <%}%>    
      <%
      if(tag.equals("1")){
      %>    
    <tr>		
      <td >������Ա��</td>
      <td bgcolor="#FFFFFF">
      <select name="sale_person">
	<%
	   Connection conn=null;
	   ResultSet rs=null;
	   PreparedStatement pstmt=null;
	   try{
	        conn = DBManager.getConnection();
		String sql="select * from org_persons where DEPARTMENT_ID=1 and status=0 order by name";
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		while(rs.next()){		 	      
	%>      
        <option value="<%=rs.getInt("id")%>"><%=rs.getString("name")%></option>   
        <%
        	}
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
      </select>
      </td>
    </tr>  
    <tr>
	<td bgcolor="#FFFFFF" width="95%" colspan="2" align="center">
      	<input type="button" name="search" value="�� ��" onclick="Submit()">
     </td>
    </tr>
    <%}%>
</table>
	<input type="hidden" name="tag" value="<%=tag%>">
	<input type="hidden" name="card_num" value="<%=card_num%>">
	<input type="hidden" name="card_type" value="<%=card_type%>">
</form>
</body>
</html>
