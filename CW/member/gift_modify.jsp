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
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.item_id.value==""){
		alert('请输入礼品或礼券号!');
		document.form.item_id.select();
		return false;;
	}
if(document.form.money.value!=""){
	if(isNaN(document.form.money.value)){
	alert('满足金额必须是数字!');
	document.form.money.select();
	return false;
	}
	
}
if(document.form.gift_id.value=="1"&&document.form.money.value==""){
	alert('选择礼品必须填写满足金额!');
	document.form.money.select();
	return false;
}
if(document.form.gift_id.value=="2"&&document.form.money.value!=""){
	alert('礼券无需满足金额!');
	document.form.money.select();
	return false;
}
if(document.form.gift_id.value=="1"&&isNaN(document.form.item_id.value)){
	alert('礼品号必须为数字!');
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">修改促销设置</font><font color="838383">&nbsp; 
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
		<td bgcolor="#FFFFFF" >MSC号</td><td bgcolor="#FFFFFF" width="100"><%=msc_code%><input type="hidden" name="id" value="<%=id%>"></td>
		<td bgcolor="#FFFFFF" >
		<select name="gift_id">
		  <option value="1"<%if(type.equals("1")){%>selected<%}%>>礼品号</option>
		  <option value="2"<%if(type.equals("2")){%>selected<%}%>>礼券号</option>
		</select>  
		</td>
		<td bgcolor="#FFFFFF"><input type="text" name="item_id" value="<%=item_id%>">
		<td bgcolor="#FFFFFF">选择俱乐部</td>
		<td bgcolor="#FFFFFF">
			<select  name="club_id" > 
			<option value="" >----</option> 		
			<option value="1" <%if(clubid.equals("1")){%>selected<%}%>>99</option> 			
			<option value="2" <%if(clubid.equals("2")){%>selected<%}%>>99妈咪宝贝</option> 
			</select>						
		</td>						
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" >满足金额</td><td bgcolor="#FFFFFF"><input type="text" name="money" value="<%=order_require%>">元</td>			
		<td bgcolor="#FFFFFF" >加</td><td bgcolor="#FFFFFF"><input type="text" name="addmoney" value="<%=addmoney%>">元	</td>	
		<td bgcolor="#FFFFFF" colspan="2"><input type="submit" name="input" value="修  改">&nbsp;&nbsp;&nbsp;</td>
			
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