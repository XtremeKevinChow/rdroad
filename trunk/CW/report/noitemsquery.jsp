<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String postcode = request.getParameter("postcode");
			 postcode=(postcode==null)?"":postcode;	
String address = request.getParameter("address");
			 address=(address==null)?"":address;	
String return_reason = request.getParameter("return_reason");
			 return_reason=(return_reason==null)?"":return_reason;	
String other_special = request.getParameter("other_special");
			 other_special=(other_special==null)?"":other_special;	

String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

	if(document.form.postcode.value=="" 
	&& document.form.address.value==""
	&&document.form.return_reason.value==""
	&&document.form.other_special.value==""	
	){
		alert('查询条件不能为空!');
		return false;;
	}
	
	document.form.search.disabled = true;
	document.form.submit();
}

function initFocus(){
	document.form.postcode.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">分析报表</font><font color="838383"> 
      		-&gt; </font><font color="838383">三无产品查询</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="noitemsquery.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>邮编</td>
		<td><input type="text" name="postcode" value="<%=postcode%>"></td>
		<td>地址</td>
		<td><input type="text" name="address" value="<%=address%>"></td>
	</tr>
	<tr>
		<td>退货原因</td>
		<td><input type="text" name="return_reason" value="<%=return_reason%>"></td>
		<td>其他特征</td>
		<td><input type="text" name="other_special" value="<%=other_special%>"><input type="submit" name="search" value="查  询"></td>
	</tr>	
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>编号</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>邮编</td>		
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>地址</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>退货原因</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>其他</td>
		<td   width="" class="OraTableRowHeader" noWrap  noWrap align=middle>备注</td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>&nbsp;</td>
	</tr>
<%
			Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
			if(postcode.length()>0){
			   condition+=" and postcode = '"+postcode+"'";
			}
			if(address.length()>0){
			   condition+=" and address like '%"+address+"%'";
			}
			if(return_reason.length()>0){
			   condition+=" and return_reason like '%"+return_reason+"%'";
			}
			if(other_special.length()>0){
			   condition+=" and other_special like '%"+other_special+"%'";
			}
		try{
				 conn = DBManager.getConnection();
					String sql="select * from jxc.sto_rk_ns_mst  where  logout='N'"+condition;
					//String sql="select * from jxc.sto_rk_ns_mst  where    "+condition;
					pstmt=conn.prepareStatement(sql);
					rs=pstmt.executeQuery();
						while(rs.next()){ 
						String rk_no=rs.getString("rk_no");
						String sto_no=rs.getString("sto_no");
						String spostcode=rs.getString("postcode");
									 spostcode=(spostcode==null)?"":spostcode;
						String saaddress=rs.getString("address");
									 saaddress=(saaddress==null)?"":saaddress;						
						String sreturn_reason=rs.getString("return_reason");
									 sreturn_reason=(sreturn_reason==null)?"":sreturn_reason;								
						String aother_special=rs.getString("other_special");
									 aother_special=(aother_special==null)?"":aother_special;							
						String remark=rs.getString("remark");
						       remark=(remark==null)?"":remark;
%>
	<tr align="center">
		<td width="5%" bgcolor="#FFFFFF"><a href="noitemsdtl.jsp?rk_no=<%=rk_no%>"><%=rk_no%></a></td>
		<td width="10%" bgcolor="#FFFFFF"><%=spostcode%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=saaddress%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=sreturn_reason%></td>
		<td width="10%" bgcolor="#FFFFFF"><%=aother_special%></td>
		<td width="" bgcolor="#FFFFFF"><%=remark%></td>
		<td width="10%" bgcolor="#FFFFFF"><a  href="noitemsdel.jsp?rk_no=<%=rk_no%>&postcode=<%=postcode%>&address=<%=address%>&other_special=<%=other_special%>&return_reason=<%=return_reason%>&tag=1">注销</a></td>
	</tr>
<%					
	} 

%>
</table>
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

}%>
</body>
</html>
