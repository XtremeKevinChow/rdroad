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
      String condition="";
      String id=request.getParameter("id");
      String sql="";
		try{
		  conn = DBManager.getConnection();  

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/popcalendar.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.MSC_CODE.value==""){
		alert('请填写活动名称!');
	document.form.MSC_CODE.select();
	return false;
	}		
	
	if(!is_integer(document.form.maxgoods.value)||document.form.maxgoods.value==""||parseInt(document.form.maxgoods.value)<0){
		alert('最大可选商品数必须是大于0的正整数!');
	document.form.maxgoods.select();
	return false;
	}
	if(!is_integer(document.form.mingoods.value)||document.form.mingoods.value==""||parseInt(document.form.mingoods.value)<0){
		alert('最小可选商品数必须是大于0的正整数!');
	document.form.mingoods.select();
	return false;
	}	
	if(parseInt(document.form.maxgoods.value)<parseInt(document.form.mingoods.value)){
	alert('最大可选商品数必须大于等于最小可选商品数!');
	document.form.maxgoods.select();
	return false;
	}												
	document.form.input.disabled = true;
	


}
function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function initFocus(){

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
      		-&gt; </font><font color="838383">销售区修改</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="recruit_activity_section_updateok.jsp" method="post" name="form" onsubmit="return queryInput();">
<%

		  sql="select * from Recruit_Activity_section where id="+id;
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String MSC=rs.getString("MSC");
		  String Name=rs.getString("Name");
		  String type=rs.getString("type");
		  String maxgoods=rs.getString("maxgoods");
		  String mingoods=rs.getString("mingoods");
		  String sectionImg=rs.getString("sectionImg");
		         sectionImg=(sectionImg==null)?"":sectionImg;
		  
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>销售区名称</td>
		<td bgcolor="#FFFFFF"><%=Name%>
		<input type="hidden" name="id" value="<%=id%>">
		</td>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>活动编号</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="MSC_CODE" value="<%=MSC%>" readonly> 
		<input type="button" onClick="javascript:winopen('/member/queryMSCList.do','选择MSC号')" value="选择MSC" > 
		</td>		


	</tr>
	<tr>		

		<td bgcolor="#FFFFFF" width="150"> 类型</td>
		<td bgcolor="#FFFFFF">
		<select name="type">
 
		 <option value="A"<%if(type.equals("A")){%>selected<%}%>>主打礼品</option>	
		 <option value="B"<%if(type.equals("B")){%>selected<%}%>>免费礼品</option>	
		 <option value="C"<%if(type.equals("C")){%>selected<%}%>>打折销售商品</option>
		 <option value="D" <%if(type.equals("D")){%>selected<%}%>>打套销售区</option>
		 <option value="E" <%if(type.equals("E")){%>selected<%}%>>有条件赠送礼品区</option>
	 
		</select>		
		 </td>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>最大可选商品数</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="maxgoods" value="<%=maxgoods%>">
		</td>		
	
	</tr>	

	

	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>最小可选商品数</td>
		<td bgcolor="#FFFFFF" ><input type="text" name="mingoods" value="<%=mingoods%>"> </td>
		<td bgcolor="#FFFFFF" width="150">导购图片</td>
		<td bgcolor="#FFFFFF" ><input type="text" name="sectionImg" value="<%=sectionImg%>"></td>		
	</tr>

																	
	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value="修 改 ">					
		</td>			
	</tr>	
</table>
<%}%>
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
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
%>