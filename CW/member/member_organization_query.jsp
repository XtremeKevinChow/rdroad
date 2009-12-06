<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String begin_date = request.getParameter("begin_date");
       begin_date=(begin_date==null)?"":begin_date;	
String end_date = request.getParameter("end_date");
       end_date=(end_date==null)?"":end_date;	
String so_number = request.getParameter("so_number");
       so_number=(so_number==null)?"":so_number;	
String card_id = request.getParameter("card_id");
       card_id=(card_id==null)?"":card_id;	
String tag=request.getParameter("tag");
       tag=(tag==null)?"":tag;

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">
function querySubmit() {
	if(document.form.so_number.value=="" 
	&& document.form.card_id.value==""
	&&document.form.begin_date.value==""
	&&document.form.end_date.value==""
	){
		alert('查询条件不能为空!');
		return false;;
	}
	if(document.form.begin_date.value!=""){
		var bdate = document.form.begin_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写起始日期,并且注意你的日期是否正确!');
				document.form.begin_date.focus();
				return false;
		 }
 	}
 		if(document.form.end_date.value!=""){
		var bdate = document.form.end_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.form.end_date.focus();
				return false;
		 }
 	}	
	document.form.search.disabled = true;
	document.form.submit();
}

function initFocus(){
	document.form.so_number.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">

<table width="100%" border="0" cellspacing="0" cellpadding="0" onload="document.forms[0].btnsearch.disabled;">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 订单管理 -&gt; 团购订单查询</font></td>
  </tr>
</table>
<form  action="member_organization_query.jsp" name="form" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td width="80">定单号：</td>
		<td><input type="text" name="so_number" size="12" value="<%=so_number%>"></td>
		<td width="80">团体会员号：</td>
		<td><input type="text" name="card_id" size="12" value="<%=card_id%>"></td>
		
	</tr>
	<tr>
		<td>开始日期：</td><td><input type=text name="begin_date" size="12" value="<%=begin_date%>">
		<a href="javascript:show_calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		<td>结束日期：</td>
		<td><input type=text name="end_date" size="12"  value="<%=end_date%>">
		<a href="javascript:show_calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		&nbsp;&nbsp;&nbsp;
		<input type="submit" value=" 查询 " name="search" >
		</td>
		
	</tr>	
</table>
<input type="hidden" name="tag" value="1">
<input type="hidden" name="isorg" value="1">
</form>


<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单号</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>团体会员号码</td>		
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>应付金额</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>订单状态</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>下单日期</td>
		<td width="" class="OraTableRowHeader" noWrap  noWrap align=middle>经办人员</td>		
	</tr>
<%
			Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
			if(so_number.length()>0){
			   condition+=" and ord_headers.so_number ='"+so_number+"'";
			}
			if(card_id.length()>0){
			   condition+=" and mbr_members.card_id ='"+card_id+"'";
			}			
			if(begin_date.length()>0){
			   condition+=" and ord_headers.release_date >= date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and ord_headers.release_date < date '"+end_date+"'+1";
			}
		try{
		 conn = DBManager.getConnection();
		 

							 
		 
		 String sql="";
		sql+=" select ord_headers.id,ord_headers.so_number,ord_headers.release_date,ord_headers.order_sum,";
		sql+=" mbr_members.card_id,s_order_status.name,org_persons.name ";
		sql+=" from ord_headers,mbr_members,org_persons,s_order_status ";
		sql+=" where mbr_members.id=ord_headers.buyer_id";
		sql+=" and mbr_members.is_organization=1 ";
		sql+=" and ord_headers.status=s_order_status.id";
		sql+=" and ord_headers.creator_id=org_persons.id"+condition;		        
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();							
				while(rs.next()){ 
				String order_id=rs.getString(1);
				String rs_so_number=rs.getString(2);
				String release_date=rs.getString(3);
				String order_sum=rs.getString(4);
				String rs_card_id=rs.getString(5);
				String rs_status_name=rs.getString(6);
				String rs_persons_name=rs.getString(7);							
%>
	<tr align="center">
		<td width="15%" bgcolor="#FFFFFF"><a href="../order/groupOrderView.do?orderId=<%=order_id%>"><%=rs_so_number%></a></td>
		<td width="15%" bgcolor="#FFFFFF"><%=rs_card_id%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=order_sum%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=rs_status_name%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=release_date%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=rs_persons_name%></td>

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
