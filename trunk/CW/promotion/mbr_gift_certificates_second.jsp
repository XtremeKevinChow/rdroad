<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
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
		try{
		  conn = DBManager.getConnection();  

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/popcalendar.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function queryInput() {
	
	
	
	if(!is_integer(document.form.gift_number.value)||document.form.gift_number.value==""){
		alert('��ȯ�������д���֣�������д��ĸ!');
	document.form.gift_number.select();
	return false;
	}	
	
        
	if(!is_integer(document.form.person_num.value)||document.form.person_num.value==""||parseInt(document.form.person_num.value)<=0){
		alert('����ʹ�ô��������Ǵ���0��������!');
	document.form.person_num.select();
	return false;
	}
	if(!is_integer(document.form.amount.value)||document.form.amount.value==""||parseInt(document.form.amount.value)<=0){
	alert('��ʹ�ô��������Ǵ���0��������!');
	document.form.amount.select();
	return false;
	}
	if(parseInt(document.form.amount.value)<parseInt(document.form.person_num.value)){
	alert('��ʹ�ô���������ڵ��ڸ���ʹ�ô���!');
	document.form.amount.select();
	return false;
	}	
	if(isNaN(document.form.gift_money.value)||document.form.gift_money.value==""||parseInt(document.form.gift_money.value)<=0){
	alert('��ȯ�������Ǵ���0������!');
	document.form.gift_money.select();
	return false;
	}	
	if(isNaN(document.form.order_money.value)||document.form.order_money.value==""||parseInt(document.form.order_money.value)<=0){
	alert('������С�������Ǵ���0������!');
	document.form.order_money.select();
	return false;
	}
		
	var sdate = document.form.start_date.value; 
	if(sdate==""){
	alert('����д��ʼ����!');
	document.form.start_date.select();
	return false;
	}	
	var edate = document.form.end_date.value; 
	if(edate==""){
	alert('����д��������!');
	document.form.end_date.select();
	return false;
	}
	
	
	if(document.form.is_member_level[0].checked&&document.form.level_id.value==""){
	alert('��ѡ���Ա����!');
	return false;
	}
												
	document.form.input.disabled = true;

}
function initFocus(){
	document.form.gift_number.select();
	document.form.level_id.disabled=true;
	return true;
}


function ifselect(){
        document.form.level_id.disabled=false;          	
	return true;
}
function ifselect1(){
        document.form.level_id.disabled=true;    
        document.form.level_id.value="";    	
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ȯ��������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_certificates_second1.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��ȯ����</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="gift_number" > </td>
		<td bgcolor="#FFFFFF" width="150">��ȯ����</td>
		<td bgcolor="#FFFFFF">
		<select name="gift_type">
		  <option value="4">���е�����ȯ</option>
		  <option value="5">˽�е�����ȯ</option>
		</select>
		</td>	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">����</td>
		<td bgcolor="#FFFFFF" colspan="3"><textarea  name="description" rows="5" cols="50"></textarea></td>
	
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr><td colspan="4"><B>��ȯ��������</B></td></tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>����ʹ�ô���</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="person_num" > </td>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>�ܼ�ʹ�ô���</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="amount" > </td>
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��ȯ���ý��</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="gift_money" > </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>������С���</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="order_money" > </td>		
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��ʼ����</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="start_date" readonly><INPUT TYPE="button" value="" onclick='popUpCalendar(this, form.start_date, dateFormat,-1,-1,true)' style="background-image:url(img/Button.gif);width:25px;height:17px;border:0px;padding:0px;">  
		</td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��������</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="end_date" readonly><INPUT TYPE="button" value="" onclick='popUpCalendar(this, form.end_date, dateFormat,-1,-1,true)' style="background-image:url(img/Button.gif);width:25px;height:17px;border:0px;padding:0px;">  
		</td>		
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">��Աע�Ὺʼ����</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="member_start_date"  size="10"> 
		<a href="javascript:calendar(form.member_start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(��ʽ:YYYY-MM-DD)
		</td>
		<td bgcolor="#FFFFFF" width="150">��Աע���������</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="member_end_date"  size="10"> 
		<a href="javascript:calendar(form.member_end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(��ʽ:YYYY-MM-DD)
		</td>		
	
	</tr>

	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ��»�Ա��</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_new_member" value="1">��
		<input type="radio"  name="is_new_member" value="0" checked>��
		
		</td>
		<td bgcolor="#FFFFFF" width="150">�Ƿ��ϻ�Ա��</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_old_member" value="1" checked>��
		<input type="radio"  name="is_old_member" value="0" >��	
		</td>		
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ�ͻ�Ա����ҹ�</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_member_level" value="1" onclick="ifselect()">��
		<input type="radio"  name="is_member_level" value="-1"  onclick="ifselect1()" checked>��			
		</td>	
		<td bgcolor="#FFFFFF" width="150">ѡ���Ա����</td>
		<td bgcolor="#FFFFFF">
		<select name="level_id">
		 <option value="">--��ѡ��--</option>	 
		 <option value="4">�׽𿨻�Ա</option>	
		 <option value="3">�𿨻�Ա</option>	
		 <option value="2">������Ա</option>	
		 <option value="1">��ͨ��Ա</option>	
		 <option value="0">��ʱ��Ա</option>	 
		</select>				
		</td>	
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ��������ʹ��</td>
		<td bgcolor="#FFFFFF" colspan="3">
		<input type="radio"  name="is_web" value="1">��
		<input type="radio"  name="is_web" value="0" checked>��
					
		</td>
		
	
	
	</tr>	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value="��  ��  ��  ��">
			
		</td>			
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