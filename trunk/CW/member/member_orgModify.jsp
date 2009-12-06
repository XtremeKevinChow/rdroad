<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String card_id=request.getParameter("card_id");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function modi() {
if(document.form1.name.value==""){
alert('会员姓名不能为空!');
document.form1.name.focus();
return false;;
}



if(document.form1.telephone.value==""){
alert('联系电话不能为空!');
document.form1.telephone.focus();
return false;;
}
if(document.form1.company_phone.value==""){
alert('联系电话二不能为空!');
document.form1.company_phone.focus();
return false;;
}

if(document.form1.postcode.value==""||document.form1.postcode.value.length!=6||isNaN(document.form1.postcode.value)){
alert('邮编不能为空并且长度为6位数字!');
document.form1.postcode.focus();
return false;;
}
if(document.form1.address.value==""){
alert('会员地址不能为空!');
document.form1.address.focus();
return false;;
}

}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">团体会员</font><font color="838383"> 
      		-&gt; </font><font color="838383">团体会员修改</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>


<%
    Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";

		try{
		 conn = DBManager.getConnection();
		 String sql="select * from mbr_members where card_id='"+card_id+"'";
		 pstmt=conn.prepareStatement(sql);
		 //out.println(sql);
		 rs=pstmt.executeQuery();		 
		 if(rs.next()){
		 String name=rs.getString("name");
		 String telephone=rs.getString("telephone");
		 String company_phone=rs.getString("company_phone");
		 String postcode=rs.getString("postcode");
		 String address=rs.getString("address");
			
				

%>
<form name="form1" action="../member/member_orgModify_ok.jsp"  method="post" onsubmit="return modi();">
			<table width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		

				<tr height="26">
					<td width="80">团体会员号：</td><td  width=90 bgcolor="#FFFFFF"><%=card_id%></td>
					<td width="80">公司名称：</td><td  width=200 bgcolor="#FFFFFF">
					<input type="text" name="name" value="<%=name%>" size="15">
					</td>
					<td width="80">联系电话：</td><td  bgcolor="#FFFFFF">
					<input type="text" name="telephone" value="<%=telephone%>"size="15">
					</td>
					<td width="80">联系电话二：</td><td  bgcolor="#FFFFFF">
					<input type="text" name="company_phone" value="<%=company_phone%>"size="15">
					</td>
				</tr>	      	     
				<tr height="26">
					<td width="80">邮编：</td><td  width=90 bgcolor="#FFFFFF">
					<input type="text" name="postcode" value="<%=postcode%>" size="15">
					</td>
					<td>会员地址：</td>
					<td colspan="5" bgcolor="#FFFFFF">
					<textarea  name="address" cols="20"><%=address%></textarea>
					<input type="submit"  name="" value="修 改" >
					<input type="hidden" name="card_id" value="<%=card_id%>">
					</td>
				</tr>	
								
			</table>
			</form>
	
<%					
	
	
	} 

%>

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
</body>
</html>
