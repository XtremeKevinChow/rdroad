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

<SCRIPT LANGUAGE="JavaScript">
<!--
function ref(){
       document.form.action="";
       document.form.submit();
}
function add(){
       document.form.action="crush_card_type_add.jsp";
       document.form.submit();
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ֵ������б�</font><font color="838383"> 
      	</td>
   </tr>
</table>

<br>

<table width="60%" align="center" cellspacing="1" border="0" >
	<tr align="center" class="OraTableRowHeader" noWrap>
		<td width="100"  noWrap align=middle><b>���</b></td>
		<td width="100"   noWrap align=middle><b>��ֵ(Ԫ)</b></td>
		<td width="200"   noWrap align=middle><b>����</b></td>

	</tr>
	<%
			sql=" select * from CRUSH_CARD_VALUE order by money desc ";			
 			
			
		        //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String id=rs.getString("id");
			String money=rs.getString("money");
			String desc = rs.getString("description");
			

	%>	
	<tr bgcolor="#FFFFFF" align="center">	
		<td><%=id%>&nbsp;</td>
		<td align="right"><%=money%>&nbsp;</td>
		<td align="right"><%=desc%>&nbsp;</td>
	</tr>	
	<%}%>	
</table><br>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td align="center"><input type="button" value="��������" onclick="add()"></td>
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