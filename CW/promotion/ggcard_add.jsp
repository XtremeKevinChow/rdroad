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
String gift_number=request.getParameter("gift_number");
       gift_number=(gift_number==null)?"":gift_number;
    
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
	if(!is_integer(document.form.number.value)||document.form.number.value==""||parseInt(document.form.number.value)<=0){
	alert('���������Ǵ���0��������!');
	document.form.number.select();
	return false;
	}
	if(parseInt(document.form.number.value)>30000){
	alert('�������ܳ���3��!');
	document.form.number.select();
	return false;
	}
       document.form.action="ggcard_add_ok.jsp";
       document.form.submit();
       document.form.addexecl.disabled = true;
}
function addc(){
       document.form.action="mbr_gift_certificates_add.jsp";
       document.form.submit();

}
function insert(){

       window.open("ggcard_add_finish.jsp");
}


</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ������ȯ����</font></td>
  </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>


<br>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>Ϊ�˱�֤���ݵ�׼ȷ�ԣ���ע��������衣<br>
		<font color="red">
		1�����Ҫ������ȯ�����Ƚ϶�,����Ҫ��ʱ��ϳ�(���Ҫ15��������),�����ĵȺ�,ֱ��ϵͳ��ʾ�㱣��execl��<br>
		2��δ������ʾ"����execl"��ʱ,��Ҫ�ر�ҳ�档<br></font>
		3��������Ǳ���execl,����ϵͳ����Ա��ϵ��
		</td>
	</tr>
</table>
<br>
<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr bgcolor="#FFFFFF" align="center">
		<td width="100" align="left">��ȯ����</td>
		<td align="left">
		<%if(gift_number.length()>0){//�����򵼲���%>
			<%=gift_number%><input type=hidden name="gift_number" value="<%=gift_number%>">
		<%}else{%>		
			<select name="gift_number">
		<%
			sql=" select id,gift_number from mbr_gift_certificates where gift_type>4 and end_date>sysdate  order by id desc ";			
 			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String id=rs.getString("id");
			String rs_gift_number=rs.getString("gift_number");					
		%>
		  	<option value="<%=rs_gift_number%>"><%=rs_gift_number%></option>
		<%}%>
			</select>
		<%}%>		
		</td>

		<td width="100" align="left">����</td><td align="left"><input type=text name="number"></td>
		<input type=hidden value="0" name="need_pass">
		<!--
		<td width="100" align="left">�Ƿ���Ҫ����</td>
		<td align="left">
		<select name="need_pass">
		  <option value="1">��</option>	
		  <option value="0">��</option>
		  	
		</select>		
		</td>-->			
	 </tr>

	
</table><br>
<table width="60%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td align="center"><input type="button" name="addexecl" value="����Execl" onclick="add()"></td>
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