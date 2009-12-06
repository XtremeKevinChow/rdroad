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
	if(document.form.name.value==""){
		alert('请填写销售区名称!');
	document.form.name.select();
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
      		-&gt; </font><font color="838383">招募活动商品销售区新增</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="recruit_activity_section_addok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>销售区名称</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="name" >
		</td>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>活动编号</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="MSC_CODE"  readonly> 
		<input type="button" onClick="javascript:winopen('/member/queryActiveList.do?method=2','选择MSC号')" value="选择MSC" > 
		</td>		


	</tr>
	<tr>		

		<td bgcolor="#FFFFFF" width="150"> 类型</td>
		<td bgcolor="#FFFFFF">
		<select name="type">
 
		 <option value="A">主打礼品</option>	
		 <option value="B">免费礼品</option>	
		 <option value="C">打折销售商品</option>
		 <option value="D">打套销售区</option>
		 <option value="E">有条件赠送礼品区</option>
	 
		</select>		
		 </td>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>最大可选商品数</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="maxgoods" >
		</td>		
	
	</tr>	

	

	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>最小可选商品数</td>
		<td bgcolor="#FFFFFF" ><input type="text" name="mingoods" ></textarea></td>
		<td bgcolor="#FFFFFF" width="150">导购图片</td>
		<td bgcolor="#FFFFFF" ><input type="text" name="sectionImg" ></textarea></td>	
	</tr>

																	
	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value="新  增">					
		</td>			
	</tr>	
</table>

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