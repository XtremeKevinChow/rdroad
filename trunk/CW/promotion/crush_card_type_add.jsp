<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%

    
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
		try{
		 conn = DBManager.getConnection();      
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>


<SCRIPT LANGUAGE="JavaScript">

function ref(){
       document.form.action="";
       document.form.submit();
}
function add(){
	if(!is_integer(document.form.crush_id.value)||document.form.crush_id.value==""||parseInt(document.form.crush_id.value)<=0){
    	alert('��ֵ�����ID������д!');
    	document.form.crush_id.select();
    	return false;
	}
	if(!is_integer(document.form.crush_money.value)||document.form.crush_money.value==""||parseInt(document.form.crush_money.value)<=0){
    	alert('�������Ǵ���0��������!');
    	document.form.crush_money.select();
    	return false;
	}
   document.form.action="crush_card_type_add_ok.jsp";
   document.form.submit();
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ֵ���������</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="60%" border="0"  cellpadding="1" align="center">
	<tr >
		<td width="50%"class="OraTableRowHeader" noWrap >id</td><td> <input type=text name="crush_id">&nbsp;<font color=red>*</font></td>
	</tr>
	<tr >
		<td class="OraTableRowHeader" noWrap >��ֵ</td><td ><input type=text name="crush_money">&nbsp;<font color=red>*</font></td>
	</tr>
	<tr >
		<td class="OraTableRowHeader" noWrap >˵��</td><td ><input type=text name="crush_desc" size="50"></td>
	</tr>
	<tr>
		<td colspan=2 align="center"><input type="button" value="��������" onclick="add()"></td>
	</tr>
</table>



</form>
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