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
		  String sql="select * from MBR_GIFT_USE_GROUP where id="+id;
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String GROUP_NO=rs.getString("GROUP_NO");
		  String GIFT_TYPE=rs.getString("GIFT_TYPE");
		  String GIFT_NUMBER=rs.getString("GIFT_NUMBER");
		  String status=rs.getString("status");	
		  
      String gift_type=request.getParameter("gift_type");
             gift_type=(gift_type==null||gift_type.equals(""))?GIFT_TYPE:gift_type;
      String group_no=request.getParameter("group_no");
             group_no=(group_no==null||group_no.equals(""))?GROUP_NO:group_no; 	
       	  	
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.group_no.value==""){
	alert('组号必须填写!');
	document.form.group_no.select();
	return false;
	}
	if(document.form.gift_type.value!=3&&document.form.gift_number.value==""){
	alert('礼券号必须选择!');
	return false;
	}
	if(document.form.gift_type.value==3&&document.form.gift_number.value!=""){
	alert('刮刮卡不需要选择礼券号！');
	return false;
	}		
	document.form.input.disabled = true;

}
function ifselect(){
	if(document.form.gift_type.value==3){
	document.form.gift_number.disabled = true;
	document.form.gift_number.value = "";
	}else{
	
	document.form.gift_number.disabled = false;
	document.form.action="";
	document.form.submit();	
	}
}
function initFocus(){
<%if(gift_type.equals("3")){%>
document.form.gift_number.disabled = true;
<%}%>
	document.form.group_no.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员礼券使用组修改</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_use_group_modify_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="100"><font color="red">*</font>组号</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="group_no" value="<%=group_no%>">
		<select name="r00" style="margin-left:-85px;width:98px; background-color:#FFEEEE;" onChange="document.all.group_no.value=this.value;"> 
	<option value="">--选择组号--</option>
	<%
		  String sql2="select distinct GROUP_NO from MBR_GIFT_USE_GROUP where status<>-1  ";
		  pstmt=conn.prepareStatement(sql2);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  
	%>		 
		 <option value="<%=rs.getString("GROUP_NO")%>"><%=rs.getString("GROUP_NO")%></option>	
	<%}%>	
	</select>		
		</td>
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="100"><font color="red">*</font>礼券类型</td>
		<td bgcolor="#FFFFFF">
		<select name="gift_type"  onchange="ifselect()">
		  <option value="4" <%if(gift_type.equals("4")){%>selected<%}%>>公有电子礼券</option>
		  <option value="5" <%if(gift_type.equals("5")){%>selected<%}%>>私有电子礼券</option>
		  <option value="3" <%if(gift_type.equals("3")){%>selected<%}%>>刮刮卡</option>
		</select>
		</td>
	
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" width="100"><font color="red">*</font>礼券活动号(幸运卡)</td>
		<td bgcolor="#FFFFFF">
		<select name="gift_number">
		 <option value="" >--请选择--</option>
	<%
		  String sql1="select * from MBR_GIFT_CERTIFICATES where start_date<=sysdate and end_date>=sysdate+1  and gift_type="+gift_type+" order by id desc ";
		  pstmt=conn.prepareStatement(sql1);
		  //System.out.println(sql1);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  String g_number=rs.getString("gift_number");
	%>		 
		 <option value="<%=g_number%>"  <%if(GIFT_NUMBER.equals(g_number)){%>selected<%}%> ><%=rs.getString("gift_number")%></option>	
	<%}%>	 
		</select>&nbsp;<font color="red">提示:刮刮卡不需要选择礼券号！</font>
		</td>
	
	</tr>


	<tr>		
		<td bgcolor="#FFFFFF" colspan="2">
		<%if(status.equals("0")){%>
		<input type="submit" name="input" value="修 改">
		<input type="hidden" name="id" value="<%=id%>">
		<input type="hidden" name="old_group_no" value="<%=GROUP_NO%>">
		<input type="hidden" name="old_gift_number" value="<%=GIFT_NUMBER%>">
		<%}else{%>
		<font color=red>该礼券不是新建状态，不能修改！<a href="javascript:history.back(-1);">返回</a></font>	
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