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
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.group_name.value==""){
	alert('��Ʒ����������д!');
	document.form.group_name.select();
	return false;
	}

	if(!is_integer(document.form.number.value)||document.form.number.value==""||parseInt(document.form.number.value)<=0){
	alert('��Ʒ�������Ǵ���0������!');
	document.form.number.select();
	return false;
	}
	



	
	document.form.input.disabled = true;

}
function initFocus(){
	document.form.group_name.select();
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
      		-&gt; </font><font color="838383">��ȯ��Ʒ������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_item_mst_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="100"><font color="red">*</font>����</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="group_name" ></td>
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="100">����</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_type">
		 <option value="0">��Ʒ�ҹ�</option>
		 <option value="1">ͼ��</option>
		 <option value="2">Ӱ��</option>
		 <option value="3">����</option>
		 <option value="4">��Ϸ/���</option>
		 <option value="5">��Ʒ</option>
		 <option value="6">����</option>
		 <option value="8">������Ʒ</option>
		
		</select>
		</td>
	
	</tr>	
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">*</font>���ٲ�Ʒ��</td>
		<td bgcolor="#FFFFFF"><input type="text" name="number" ></td>
	</tr>	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="2">

		<input type="submit" name="input" value="��  ��">					
		</td>			
	</tr>	
</table>
</form>

</body>
</html>
