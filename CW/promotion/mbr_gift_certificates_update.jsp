<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String id=request.getParameter("id");

 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      String sql="";


try{
		 conn = DBManager.getConnection();
		  sql="select * from MBR_GIFT_CERTIFICATES where id="+id;
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String rs_gift_number=rs.getString("gift_number");
		  String person_num=rs.getString("person_num");
		  String amount=rs.getString("amount");

		  String gift_money=rs.getString("gift_money");
		  String order_money=rs.getString("order_money");
		  String start_date=rs.getString("start_date");
		         start_date=(start_date==null)?"":start_date.substring(0,10);
		  String end_date=rs.getString("end_date");
		  	 end_date=(end_date==null)?"":end_date.substring(0,10);
		  String gift_type=rs.getString("gift_type");
		  String member_start_date=rs.getString("member_start_date");
		         member_start_date=(member_start_date==null)?"":member_start_date.substring(0,10);
		  String member_end_date=rs.getString("member_end_date");
		  	 member_end_date=(member_end_date==null)?"":member_end_date.substring(0,10);
		  String description=rs.getString("description");
		         description=(description==null)?"":description;
		  String is_new_member=rs.getString("is_new_member");
		  String is_old_member=rs.getString("is_old_member");
		  String is_web=rs.getString("is_web");
		  String is_member_level=rs.getString("is_member_level");
		  String product_group_id=rs.getString("product_group_id");
		  String is_money_for_order=rs.getString("is_money_for_order");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript">
function queryInput() {
		
        
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
	alert('��������������Ǵ���0������!');
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
	/*if(document.form.product_group_id[0].checked&&document.form.item_group_id.value==""){
	alert('��ѡ���Ʒ��!');
	return false;
	}	
	if(document.form.is_member_level[0].checked&&document.form.level_id.value==""){
	alert('��ѡ���Ա����!');
	return false;
	}*/
												
	document.form.input.disabled = true;

}
function ifchecked(){
        document.form.item_group_id.disabled=false;          	
	return true;
}
function ifchecked1(){
        document.form.item_group_id.disabled=true;     
        document.form.item_group_id.value="";      	
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
function initFocus(){
	
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<form   action="mbr_gift_certificates_add_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ȯ��޸�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="150">���</td>
		<td bgcolor="#FFFFFF" align="left"><%=rs_gift_number%>
		<input type="hidden"  name="gift_number" value="<%=rs_gift_number%>">
		</td>
		<td bgcolor="#FFFFFF" width="150">��ȯ����</td>
		<td bgcolor="#FFFFFF" align="left">
		<input type="hidden"  name="gift_type" value="<%=gift_type%>">
	        <%
		if(gift_type.equals("1")){
		   out.println("���");
		}
		if(gift_type.equals("2")){
		   out.println("��ͨ");
		}
		
		if(gift_type.equals("4")){
		   out.println("������ȯ");
		}
		if(gift_type.equals("5")){
		   out.println("��˽����ȯ");
		}	
		if(gift_type.equals("6")){
		   out.println("����˽����ȯ");
		}								        
	        %>	
	   </td>	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">����</td>
		<td bgcolor="#FFFFFF" colspan="3" align="left"><textarea   name="description" rows="5" cols="50"><%=description%></textarea></td>
	
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr><td colspan="4"><B>��ȯ��������</B></td></tr>
	<!--
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>����ʹ�ô���</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="person_num" value="<%=person_num%>"> </td>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>�ܼ�ʹ�ô���</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="amount"  value="<%=amount%>"> </td>
	</tr>-->
    <tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��ʼ����</td>
		<td bgcolor="#FFFFFF" align="left">
		<input type="text" name="start_date" value="<%=start_date%>" readonly>
		<a href="javascript:show_calendar(form.start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��������</td>
		<td bgcolor="#FFFFFF" align="left">
		<input type="text" name="end_date" value="<%=end_date%>" readonly>
		<a href="javascript:show_calendar(form.end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
        </td>		
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>���ý��</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="gift_money"  value="<%=gift_money%>"> </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>�������</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="order_money"  value="<%=order_money%>"> </td>		
	
	</tr>
	
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��ȯ��ʹ�ô���</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="amount" value="<%=amount%>"> </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>����ʹ�ô���</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="person_num" value="<%=person_num%>"> </td>		
  </tr>
	
 <input type=hidden name="member_start_date" value="<%=member_start_date%>">
 <input type=hidden name="member_end_date" value="<%=member_end_date%>">
 <input type=hidden name="is_new_member" value="<%=is_new_member%>">
 <input type=hidden name="is_old_member" value="<%=is_old_member%>">
 <input type=hidden name="is_web" value="<%=is_web%>">
 <input type=hidden name="is_money_for_order" value="<%=is_money_for_order%>">
 <input type=hidden name="product_group_id" value="<%=product_group_id%>">
 <input type=hidden name="is_member_level" value="<%=is_member_level%>">
 
	<!--
	<tr>		
		<td bgcolor="#FFFFFF" width="150">��Աע�Ὺʼ����</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="member_start_date"  size="10" value="<%=member_start_date%>"> 
		<a href="javascript:calendar(form.member_start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(��ʽ:YYYY-MM-DD)
		</td>
		<td bgcolor="#FFFFFF" width="150">��Աע���������</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="member_end_date"  size="10" value="<%=member_end_date%>"> 
		<a href="javascript:calendar(form.member_end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(��ʽ:YYYY-MM-DD)
		</td>		
	
	</tr>

	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ��»�Ա��</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_new_member" value="1"  <%if(is_new_member.equals("1")){%>checked<%}%>>��
		<input type="radio"  name="is_new_member" value="0"  <%if(is_new_member.equals("0")){%>checked<%}%>>��
		
		</td>
		<td bgcolor="#FFFFFF" width="150">�Ƿ��ϻ�Ա��</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_old_member" value="1" <%if(is_old_member.equals("1")){%>checked<%}%>>��
		<input type="radio"  name="is_old_member" value="0"  <%if(is_old_member.equals("0")){%>checked<%}%>>��	
			
		</td>		
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ����ȯ��Ʒ��ҹ�</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="product_group_id" value="1" onclick="ifchecked()" <%if(Integer.parseInt(product_group_id)>=1){%>checked<%}%>>��
		<input type="radio"  name="product_group_id" value="-1" onclick="ifchecked1()"   <%if(product_group_id.equals("-1")){%>checked<%}%>>��
						
		</td>
		<td bgcolor="#FFFFFF" width="150">ѡ���Ʒ��</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_id">
		 <option value="">--��ѡ��--</option>
	<%
		  sql="select * from MBR_GIFT_ITEM_MST where status=1 order by item_group_id desc ";
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  
	%>		 
		 <option value="<%=rs.getString("ITEM_GROUP_ID")%>"<%if(product_group_id.equals(rs.getString("ITEM_GROUP_ID"))){%>selected<%}%>><%=rs.getString("GROUP_DESC")%></option>	
	<%}%>	 
		</select>				
		</td>	
	</tr>

	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ�ͻ�Ա����ҹ�</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_member_level" value="1"  onclick="ifselect()" <%if(Integer.parseInt(is_member_level)>=0){%>checked<%}%>>��
		<input type="radio"  name="is_member_level" value="-1"  onclick="ifselect1()"  <%if(is_member_level.equals("-1")){%>checked<%}%>>��						
		</td>	
		<td bgcolor="#FFFFFF" width="150">ѡ���Ա����</td>
		<td bgcolor="#FFFFFF">
		<select name="level_id">
		 <option value="">--��ѡ��--</option>	 
		 <option value="4"<%if(is_member_level.equals("4")){%>selected<%}%>>�׽𿨻�Ա</option>	
		 <option value="3"<%if(is_member_level.equals("3")){%>selected<%}%>>�𿨻�Ա</option>	
		 <option value="2"<%if(is_member_level.equals("2")){%>selected<%}%>>������Ա</option>	
		 <option value="1"<%if(is_member_level.equals("1")){%>selected<%}%>>��ͨ��Ա</option>
		 <option value="0"<%if(is_member_level.equals("0")){%>selected<%}%>>��ʱ��Ա</option>		 
		</select>				
		</td>	
	</tr>		
	<tr>		
		<td bgcolor="#FFFFFF" width="150">�Ƿ��������ʹ��</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_web" value="1"  <%if(is_web.equals("1")){%>checked<%}%>>��
		<input type="radio"  name="is_web" value="0" <%if(is_web.equals("0")){%>checked<%}%>>��
				
		</td>
		<td bgcolor="#FFFFFF" width="150">�Ƿ�Ͷ������ü���ҹ�</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_money_for_order" value="1" <%if(is_money_for_order.equals("1")){%>checked<%}%>>��
		<input type="radio"  name="is_money_for_order" value="0"   <%if(is_money_for_order.equals("0")){%>checked<%}%>>��	
										
		</td>		
	
	</tr>	
	-->
		
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="hidden"  name="id" value="<%=id%>">
		<input type="submit" name="input" value=" �� �� ">					
		</td>			
	</tr>	
</table>
</form>

</body>
</html>
<%}%>
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