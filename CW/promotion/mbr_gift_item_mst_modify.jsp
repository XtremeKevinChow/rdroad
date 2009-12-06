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
		  String sql="select * from MBR_GIFT_ITEM_MST where ITEM_GROUP_ID="+id;
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String ITEM_GROUP_ID=rs.getString("ITEM_GROUP_ID");
		  String MIN_ITEM_COUNT=rs.getString("MIN_ITEM_COUNT");
		  String GROUP_DESC=rs.getString("GROUP_DESC");
		  String status=rs.getString("status");	
		  String rs_item_group_type=rs.getString("itemgroup_type");		
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript">
function queryInput() {


	if(!is_integer(document.form.number.value)||document.form.number.value==""||parseInt(document.form.number.value)<=0){
	alert('产品数必须是大于0的数字!');
	document.form.number.select();
	return false;
	}
	

	document.form.input.disabled = true;

}
function initFocus(){
	document.form.number.select();
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
      		-&gt; </font><font color="838383">礼券产品组设置</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_item_mst_modify_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="100"><font color="red">*</font>组名</td>
		<td bgcolor="#FFFFFF"><%=GROUP_DESC%></td>
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="100">类型</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_type">
		 <option value="0"<%if(rs_item_group_type.equals("0")){%>selected<%}%>>单品挂钩</option>
		 <option value="1"<%if(rs_item_group_type.equals("1")){%>selected<%}%>>图书</option>
		 <option value="2"<%if(rs_item_group_type.equals("2")){%>selected<%}%>>影视</option>
		 <option value="3"<%if(rs_item_group_type.equals("3")){%>selected<%}%>>音乐</option>
		 <option value="4"<%if(rs_item_group_type.equals("4")){%>selected<%}%>>游戏/软件</option>
		 <option value="5"<%if(rs_item_group_type.equals("5")){%>selected<%}%>>礼品</option>
		 <option value="6"<%if(rs_item_group_type.equals("6s")){%>selected<%}%>>其他</option>

		
		</select>
		</td>
	
	</tr>	
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">*</font>最少产品数</td>
		<td bgcolor="#FFFFFF"><input type="text" name="number" value="<%=MIN_ITEM_COUNT%>"></td>
	</tr>	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="2">
		<%if(status.equals("0")){%>
		<input type="submit" name="input" value="修 改">
		<input type="hidden" name="group_id" value="<%=ITEM_GROUP_ID%>">
		<%}else{%>
		<font color=red>该记录不是新建状态，不能修改！<a href="javascript:history.back(-1);">返回</a></font>	
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