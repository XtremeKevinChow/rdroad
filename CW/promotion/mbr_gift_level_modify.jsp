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
      String condition="";


try{
		 conn = DBManager.getConnection();
		  String sql="select * from MBR_GIFT_MONEY_BY_ORDER where id="+id;
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String gift_number=rs.getString("gift_number");
		  String level_id=rs.getString("level_id");
		  String order_require=rs.getString("order_require");
		  String dis_amt=rs.getString("dis_amt");
		  String status=rs.getString("status");	
		  String is_discount=rs.getString("is_discount");
		  String product_group_id=rs.getString("product_group_id");
		  String dis_type=rs.getString("dis_type");
		  java.sql.Date begin_date = rs.getDate("begin_date");
		  java.sql.Date end_date = rs.getDate("end_date");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.gift_number.value==""){
	alert('��ѡ����ȯ��!');
	return false;
	}

	if(isNaN(document.form.order_require.value)||document.form.order_require.value==""||parseInt(document.form.order_require.value)<=0){
	alert('��������������Ǵ���0������!');
	document.form.order_require.select();
	return false;
	}
	if(!is_number(document.form.dis_amt.value)||document.form.dis_amt.value==""||parseInt(document.form.dis_amt.value)<0){
	alert('���ý������Ǵ���0������!');
	document.form.dis_amt.select();
	return false;
	}	


	
	document.form.input.disabled = true;

}
function check_dis_type(obj) {
	if (obj.value == "1")//��һ����
	{
		if (obj.checked)
		{
			document.forms[0].begin_date.value = "";
			document.forms[0].end_date.value = "";
			document.forms[0].begin_date.disabled = true;
			document.forms[0].end_date.disabled = true;
			document.getElementById("i_begin").href = "javascript:void(0);";
			document.getElementById("i_end").href = "javascript:void(0);";
		} else {
		}
	} else {
		if (obj.checked)
		{
			document.forms[0].begin_date.disabled = false;
			document.forms[0].end_date.disabled = false;
			document.getElementById("i_begin").href = "javascript:calendar(document.forms[0].begin_date);";
			document.getElementById("i_end").href = "javascript:calendar(document.forms[0].end_date);";
		} else {
			document.forms[0].begin_date.value = "";
			document.forms[0].end_date.value = "";
			document.getElementById("i_begin").href = "javascript:void(0);";
			document.getElementById("i_end").href = "javascript:void(0);";
		}
	}
}
function load() {
	check_dis_type(document.forms[0].dis_type[0]);
	check_dis_type(document.forms[0].dis_type[1]);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ��ȯ���ü����޸�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_level_modify_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="600" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>��ȯ���</td>
		<td bgcolor="#FFFFFF">
		<select name="gift_number">
		 <option value="">--��ѡ��--</option>
	<%
		  String sql1="select * from MBR_GIFT_CERTIFICATES where start_date<=sysdate and end_date>=sysdate+1 or gift_number='"+gift_number+"' order by id desc ";
		  pstmt=conn.prepareStatement(sql1);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  String g_number=rs.getString("gift_number");
	%>		 
		 <option value="<%=g_number%>" <%if(gift_number.equals(g_number)){%>selected<%}%>><%=rs.getString("gift_number")%></option>	
	<%}%>	 
		</select>		

		</td>
	
	</tr>
	<tr>
		<td class="OraTableRowHeader" noWrap  >��Ա����</td>
		<td bgcolor="#FFFFFF">
		<select name="level_id" > 
			<option value="-1">--����--</option>
			<option value="4" <%if(level_id.equals("4")){%>selected<%}%> >�׽𿨻�Ա</option>			
			<option value="3" <%if(level_id.equals("3")){%>selected<%}%>>�𿨻�Ա</option>
			<option value="2" <%if(level_id.equals("2")){%>selected<%}%> >������Ա</option>
			<option value="1" <%if(level_id.equals("1")){%>selected<%}%> >��ͨ��Ա</option>
			<option value="0" <%if(level_id.equals("0")){%>selected<%}%> >��ʱ��Ա</option>
						
		</select>		
		</td>
	</tr>	
	<tr>		
		<td class="OraTableRowHeader" noWrap ><font color="red">*</font>�ֿ۲��ԣ�</td><td bgcolor="#FFFFFF">�ۿ�<input type="checkbox" value="Y" <% if (is_discount.equals("Y") ) { %> checked <%}%> name="is_discount" >&nbsp;&nbsp;&nbsp;
		��һ����<input type="radio" <% if (dis_type.equals("1") ) { %> checked <%}%> name="dis_type" value="1" onclick="check_dis_type(this)">&nbsp;&nbsp;
		�ۼƶ���<input type="radio" <% if (dis_type.equals("2") ) { %> checked <%}%> name="dis_type" value="2" onclick="check_dis_type(this)">&nbsp;&nbsp;
		�� <input name="begin_date" value="<%=begin_date%>" readonly size="10" disabled><a id="i_begin" href="javascript:void(0);"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a> �� <input name="end_date" readonly size="10" value="<%=begin_date%>" disabled><a id="i_end" href="javascript:void(0);"><img  src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	
	</tr>
	<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>��Ʒ��</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_id">
		 <option value="-1">--��ѡ��--</option>
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
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>����������</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="order_require" value="<%=order_require%>">&nbsp;Ԫ</td>
	
	</tr>	
	<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>���ý��ۿۣ�</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="dis_amt"  value="<%=dis_amt%>">&nbsp;Ԫ���ۣ�</td>
	
	</tr>	

	<tr>		
		<td bgcolor="#FFFFFF" colspan="2" align="center">
		<%if(status.equals("0")){%>
		<input type="submit" name="input" value="�� ��">		
		<input type="hidden" name="id" value="<%=id%>">
		<input type="hidden" name="old_gift_number" value="<%=gift_number%>">
		<input type="hidden" name="old_level_id" value="<%=level_id%>">
		<input type="hidden" name="old_order_require" value="<%=order_require%>">
		<input type="hidden" name="old_dis_amt" value="<%=dis_amt%>">
		<input type="hidden" name="old_is_discount" value="<%=is_discount%>">
		<input type="hidden" name="old_dis_type" value="<%=dis_type%>">
		<input type="hidden" name="old_begin_date" value="<%=begin_date%>">
		<input type="hidden" name="old_end_date" value="<%=end_date%>">
		<input type="hidden" name="old_item_group_id" value="<%=product_group_id%>">
		<%}else{%>
		<font color=red>�ü�¼�����½�״̬�������޸ģ�<a href="javascript:history.back(-1);">����</a></font>	
		<%}%>				
		</td>			
	</tr>	
</table>
</form>

</body>
</html>
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
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}

%>