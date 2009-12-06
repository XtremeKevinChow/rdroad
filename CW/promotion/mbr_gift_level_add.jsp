<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.order.entity.Ticket"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
Ticket info=new Ticket();
info=(Ticket)session.getAttribute("info"); 


      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String sql="";
		try{
		  conn = DBManager.getConnection(); 
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.gift_number.value==""){
	alert('请选择礼券号!');
	return false;
	}

	if(isNaN(document.form.order_require.value)||document.form.order_require.value==""||parseInt(document.form.order_require.value)<=0){
	alert('订单满足金额必须是大于0的数字!');
	document.form.order_require.select();
	return false;
	}
	if(!is_number(document.form.dis_amt.value)||document.form.dis_amt.value==""||parseInt(document.form.dis_amt.value)<0){
	alert('抵用金额必须是大于0的数字!');
	document.form.dis_amt.select();
	return false;
	}	
	document.form.input.disabled = true;

}
function check_dis_type(obj) {
	if (obj.value == "1")//单一订单
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

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">礼品礼券抵用级别新增</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form action="mbr_gift_level_add_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="600" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
<%if(info!=null&&info.getGiftNumber().length()>0){//根据向导操作%>
	<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>礼券活动号</td>
		<td bgcolor="#FFFFFF"><%=info.getGiftNumber()%><input type=hidden name="gift_number" value="<%=info.getGiftNumber()%>"></td>
	
	</tr>
<%}else{%>
	<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>礼券活动号</td>
		<td bgcolor="#FFFFFF">
		<select name="gift_number">
		 <option value="">--请选择--</option>
	<%
		  sql="select * from MBR_GIFT_CERTIFICATES where is_money_for_order<>0 order by id desc ";
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
		  
	%>		 
		 <option value="<%=rs.getString("gift_number")%>"><%=rs.getString("gift_number")%></option>	
	<%}%>	 
		</select>		

		</td>
	
	</tr>
<%}%>
	<tr>
		<td class="OraTableRowHeader" noWrap  width="120">会员级别</td>
		<td bgcolor="#FFFFFF">
		<select name="level_id" > 
			<option value="-1" >--所有--</option>	
			<option value="4" >白金卡会员</option>			
			<option value="3" >金卡会员</option>
			<option value="2" >银卡会员</option>
			<option value="1" >普通会员</option>
			<option value="0" >临时会员</option>
					
		</select>		
		</td>
	</tr>
	<tr>		
		<td class="OraTableRowHeader" noWrap ><font color="red">*</font>抵扣策略：</td><td bgcolor="#FFFFFF">折扣<input type="checkbox" value="Y" name="is_discount" >&nbsp;&nbsp;&nbsp;
		单一订单<input type="radio" name="dis_type" value="1" onclick="check_dis_type(this)" checked>&nbsp;&nbsp;
		累计订单<input type="radio" name="dis_type" value="2" onclick="check_dis_type(this)">&nbsp;&nbsp;
		从 <input name="begin_date" readonly size="10" disabled><a id="i_begin" href="javascript:void(0);"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a> 到 <input name="end_date" readonly size="10" disabled><a id="i_end" href="javascript:void(0);"><img  src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
	
	</tr>	
		<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>产品组</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_id">
		 <option value="-1">--请选择--</option>
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
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>订单满足金额</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="order_require" size="13">&nbsp;元</td>
	
	</tr>	
	<tr>		
		<td class="OraTableRowHeader" noWrap  width="120"><font color="red">*</font>抵用金额（折扣）</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="dis_amt" size="13">&nbsp;元（折）</td>
	
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="2" align="center">

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