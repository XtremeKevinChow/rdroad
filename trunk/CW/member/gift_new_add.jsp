<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.msc.value==""){
		alert('������MSC��!');
		document.form.msc.select();		
		return false;;
	}
	if(document.form.item_id.value==""){
		alert('��������Ʒ����ȯ��!');
		document.form.item_id.select();
		return false;;
	}
if(document.form.money.value!=""||document.form.addmoney.value!=""){
	if(isNaN(document.form.money.value)||isNaN(document.form.addmoney.value)){
	alert('������������!');
	return false;
	}
	
}
if(document.form.gift_id.value=="1"&&(document.form.money.value==""&&document.form.addmoney.value=="")){
	alert('ѡ����Ʒ������д��������!');
	document.form.money.select();
	return false;
}
if(document.form.gift_id.value=="2"&&(document.form.money.value!=""||document.form.addmoney.value!="")){
	alert('��ȯû����������!');
	document.form.money.select();
	return false;
}
	
	document.form.input.disabled = true;

}
function initFocus(){
	document.form.msc.select();
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
      		-&gt; </font><font color="838383">������������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="gift_new_add_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" >MSC��</td><td bgcolor="#FFFFFF"><input type="text" name="msc" ></td>
		<td bgcolor="#FFFFFF" >
		<select name="gift_id">
		  <option value="1">��Ʒ��</option>
		  <option value="2">��ȯ��</option>
		</select>  
		</td>
		<td bgcolor="#FFFFFF"><input type="text" name="item_id">
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" >������</td><td bgcolor="#FFFFFF"><input type="text" name="money" >Ԫ	
		<td bgcolor="#FFFFFF" >��</td><td bgcolor="#FFFFFF"><input type="text" name="addmoney" >Ԫ		
		</td>		
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF">ѡ����ֲ�</td>
		<td bgcolor="#FFFFFF" colspan="3">
			<select  name="club_id" > 		
			<option value="1" >99</option> 			
			<option value="2" >99���䱦��</option> 
			</select>
		<input type="submit" name="input" value="��  ��">&nbsp;&nbsp;&nbsp;						
		</td>			
	</tr>	
</table>
</form>

</body>
</html>
