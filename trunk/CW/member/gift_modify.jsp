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
      Statement stmt = null;
      String condition="";
      String id=request.getParameter("id");
try{
		 conn = DBManager.getConnection();
		 String sql="";      
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.item_id.value==""){
		alert('��������Ʒ����ȯ��!');
		document.form.item_id.select();
		return false;;
	}
if(document.form.money.value!=""){
	if(isNaN(document.form.money.value)){
	alert('���������������!');
	document.form.money.select();
	return false;
	}
	
}
if(document.form.gift_id.value=="1"&&document.form.money.value==""){
	alert('ѡ����Ʒ������д������!');
	document.form.money.select();
	return false;
}
if(document.form.gift_id.value=="2"&&document.form.money.value!=""){
	alert('��ȯ����������!');
	document.form.money.select();
	return false;
}
if(document.form.gift_id.value=="1"&&isNaN(document.form.item_id.value)){
	alert('��Ʒ�ű���Ϊ����!');
	document.form.item_id.select();
	return false;
}	
	document.form.input.disabled = true;

}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�޸Ĵ�������</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="gift_modify_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<%
			 sql="select * from mbr_msc_gift where id="+id;
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){
				String type=rs.getString("type");
				String item_id=rs.getString("item_id");
				String order_require=rs.getString("order_require");
				String msc_code=rs.getString("msc_code");
				String addmoney=rs.getString("addmoney");
				String clubid=rs.getString("clubid");
				       clubid=(clubid==null)?"":clubid;

%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>
		<td bgcolor="#FFFFFF" >MSC��</td><td bgcolor="#FFFFFF" width="100"><%=msc_code%><input type="hidden" name="id" value="<%=id%>"></td>
		<td bgcolor="#FFFFFF" >
		<select name="gift_id">
		  <option value="1"<%if(type.equals("1")){%>selected<%}%>>��Ʒ��</option>
		  <option value="2"<%if(type.equals("2")){%>selected<%}%>>��ȯ��</option>
		</select>  
		</td>
		<td bgcolor="#FFFFFF"><input type="text" name="item_id" value="<%=item_id%>">
		<td bgcolor="#FFFFFF">ѡ����ֲ�</td>
		<td bgcolor="#FFFFFF">
			<select  name="club_id" > 
			<option value="" >----</option> 		
			<option value="1" <%if(clubid.equals("1")){%>selected<%}%>>99</option> 			
			<option value="2" <%if(clubid.equals("2")){%>selected<%}%>>99���䱦��</option> 
			</select>						
		</td>						
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" >������</td><td bgcolor="#FFFFFF"><input type="text" name="money" value="<%=order_require%>">Ԫ</td>			
		<td bgcolor="#FFFFFF" >��</td><td bgcolor="#FFFFFF"><input type="text" name="addmoney" value="<%=addmoney%>">Ԫ	</td>	
		<td bgcolor="#FFFFFF" colspan="2"><input type="submit" name="input" value="��  ��">&nbsp;&nbsp;&nbsp;</td>
			
	</tr>		
</table>
<%}%>
<input type="hidden" name="id" value="<%=id%>">
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
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}
%>