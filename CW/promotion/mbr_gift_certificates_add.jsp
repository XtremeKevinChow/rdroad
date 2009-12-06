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

<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">
function queryInput() {
	
	
	
	if(!is_integer(document.form.gift_number.value)||document.form.gift_number.value==""){
		alert('礼券批号请填写数字，无需填写字母!');
	document.form.gift_number.select();
	return false;
	}	
	
        
	if(!is_integer(document.form.person_num.value)||document.form.person_num.value==""||parseInt(document.form.person_num.value)<=0){
		alert('个人使用次数必须是大于0的正整数!');
	document.form.person_num.select();
	return false;
	}
	if(!is_integer(document.form.amount.value)||document.form.amount.value==""||parseInt(document.form.amount.value)<=0){
	alert('总使用次数必须是大于0的正整数!');
	document.form.amount.select();
	return false;
	}
	if(parseInt(document.form.amount.value)<parseInt(document.form.person_num.value)){
	alert('总使用次数必须大于等于个人使用次数!');
	document.form.amount.select();
	return false;
	}	
	if(isNaN(document.form.gift_money.value)||document.form.gift_money.value==""||parseInt(document.form.gift_money.value)<=0){
	alert('礼券金额必须是大于0的数字!');
	document.form.gift_money.select();
	return false;
	}	
	if(isNaN(document.form.order_money.value)||document.form.order_money.value==""||parseInt(document.form.order_money.value)<=0){
	alert('订单满足金额必须是大于0的数字!');
	document.form.order_money.select();
	return false;
	}
		
	var sdate = document.form.start_date.value; 
	if(sdate==""){
	alert('请填写开始日期!');
	document.form.start_date.select();
	return false;
	}	
	var edate = document.form.end_date.value; 
	if(edate==""){
	alert('请填写结束日期!');
	document.form.end_date.select();
	return false;
	}
	
	/*if(document.form.product_group_id[0].checked&&document.form.item_group_id.value==""){
	alert('请选择产品组!');
	return false;
	}	
	if(document.form.is_member_level[0].checked&&document.form.level_id.value==""){
	alert('请选择会员级别!');
	return false;
	}*/
												
	document.form.input.disabled = true;

}
function initFocus(){
	document.form.gift_number.select();
	//document.form.level_id.disabled=true;
	//document.form.item_group_id.disabled=true;
	
	return true;
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
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">礼券批号新增</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_certificates_add_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>礼券批号</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="gift_number" > </td>
		<td bgcolor="#FFFFFF" width="150">礼券类型</td>
		<td bgcolor="#FFFFFF" align="left">
		<select name="gift_type">
		  <option value="4">公共礼券</option>
		  <option value="5">绑定的私有礼券</option>
		  <option value="6">不绑定私有礼券</option>
		</select>
		</td>	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">描述</td>
		<td bgcolor="#FFFFFF" colspan="3" align="left"><textarea  name="description" rows="5" cols="50"></textarea></td>
	
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr><td colspan="4"><B>礼券属性设置</B></td></tr>

	<tr>		
		<td bgcolor="#FFFFFF" width="150" ><font color="red">*</font>开始日期</td>
		<td bgcolor="#FFFFFF" align="left">
		<input type="text" name="start_date" readonly>
		<a href="javascript:show_calendar(form.start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
    </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>结束日期</td>
		<td bgcolor="#FFFFFF" align="left">
		<input type="text" name="end_date" readonly>
    <a href="javascript:show_calendar(form.end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
    </td>		
 </tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>抵用金额</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="gift_money" > </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>订单金额</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="order_money" > </td>		
  </tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>礼券总使用次数</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="amount" > </td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>个人使用次数</td>
		<td bgcolor="#FFFFFF" align="left"><input type="text"  name="person_num" value="1"> </td>		
  </tr>


 <!--
 <tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>个人使用次数</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="person_num" > </td>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>总计使用次数</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="amount" > </td>
	</tr>
	-->
 <input type=hidden name="member_start_date" value="">
 <input type=hidden name="member_end_date" value="">
 <input type=hidden name="is_new_member" value="1">
 <input type=hidden name="is_old_member" value="1">
 <input type=hidden name="is_web" value="0">
 <input type=hidden name="is_money_for_order" value="0">
 <input type=hidden name="product_group_id" value="-1">
 <input type=hidden name="is_member_level" value="-1">
 <input type=hidden name="level_id" value="">
  <!--
	<tr>		
		<td bgcolor="#FFFFFF" width="150">会员注册开始日期</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="member_start_date"  size="10"> 
		<a href="javascript:calendar(form.member_start_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(格式:YYYY-MM-DD)
		</td>
		<td bgcolor="#FFFFFF" width="150">会员注册结束日期</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="member_end_date"  size="10"> 
		<a href="javascript:calendar(form.member_end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(格式:YYYY-MM-DD)
		</td>		
	
	</tr>

	<tr>		
		<td bgcolor="#FFFFFF" width="150">是否新会员用</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_new_member" value="1">是
		<input type="radio"  name="is_new_member" value="0" checked>否
		
		</td>
		<td bgcolor="#FFFFFF" width="150">是否老会员用</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_old_member" value="1" checked>是
		<input type="radio"  name="is_old_member" value="0" >否	
		</td>		
	
	</tr>

	<tr>		
		<td bgcolor="#FFFFFF" width="150">是否仅供网上使用</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_web" value="1">是
		<input type="radio"  name="is_web" value="0" checked>否
					
		</td>
		<td bgcolor="#FFFFFF" width="150">是否和订单抵用级别挂钩</td>
		<td bgcolor="#FFFFFF" >
		<input type="radio"  name="is_money_for_order" value="1">是
		<input type="radio"  name="is_money_for_order" value="0" checked>否	
		</td>			
	
	
	</tr>	
	
	<tr>		
		<td bgcolor="#FFFFFF" width="150">是否和礼券产品组挂钩</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="product_group_id" value="1" onclick="ifchecked()">是
		<input type="radio"  name="product_group_id" value="-1" onclick="ifchecked1()" checked>否				
		</td>
		<td bgcolor="#FFFFFF" width="150">选择产品组</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_id">
		 <option value="">--请选择--</option>
	<%
		  sql="select * from MBR_GIFT_ITEM_MST where status=1 order by item_group_id desc ";
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  
	%>		 
		 <option value="<%=rs.getString("ITEM_GROUP_ID")%>"><%=rs.getString("GROUP_DESC")%></option>	
	<%}%>	 
		</select>				
		</td>	
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" width="150">是否和会员级别挂钩</td>
		<td bgcolor="#FFFFFF">
		<input type="radio"  name="is_member_level" value="1" onclick="ifselect()">是
		<input type="radio"  name="is_member_level" value="-1"  onclick="ifselect1()" checked>否			
		</td>	
		<td bgcolor="#FFFFFF" width="150">选择会员级别</td>
		<td bgcolor="#FFFFFF">
		<select name="level_id">
		 <option value="">--请选择--</option>	 
		 
		 <option value="3">VIP会员</option>	
		 <option value="2">正式会员</option>	
		 <option value="1">普通会员</option>	
		 	 
		</select>				
		</td>	
	</tr>
  -->																		
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value=" 保 存 ">
				
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