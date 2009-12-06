<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.DBManager"%>

<%
    String ref_no=request.getParameter("ref_no");
    ref_no=(ref_no==null)?"":ref_no; 
      		
    String member_name=request.getParameter("member_name");
    member_name=(member_name==null)?"":member_name; 

    String tag=request.getParameter("tag");
    tag=(tag==null)?"0":tag;//0-不查询；1-查询      
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;
	ResultSet rs2=null;
    PreparedStatement pstmt2=null;

    String sql=null;
	try{
		conn = DBManager.getConnection();      
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--


function query_f() {
	
	if (document.form.ref_no.value == "" && document.form.member_name.value == "")
	{
		alert("请输入查询条件!");
		return;
	}	   
	document.form.action = "/member/money_list.jsp?tag=1";
	document.form.queryBtn.disabled = true;
	document.form.submit();
}



//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:document.form.ref_no.focus();">
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td class="navigationLabel">
			<B>当前位置 : 会员管理 -&gt; 汇款单查询</B> </td>
   </tr>
</table>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			说明：由于邮局文件导入系统时候，姓名有可能不能正常显示，所以以<font color="red">汇号</font>查询为准。最多查近90天的数据。
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td width="50">汇号</td>
		<td  bgcolor="#FFFFFF">
		<input type=text name="ref_no" value="<%=ref_no%>">	
		<td width="50">姓名</td>
		<td  bgcolor="#FFFFFF">
		<input type=text name="member_name" value="<%=member_name%>">	
		</td>	
		<td  bgcolor="#FFFFFF">
		<input type=button name="queryBtn" value=" 查询 " onclick="query_f();">	
		</td>
	</tr>	
</table>
<br>
<%if(tag.equals("1")){%>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" id="DataTable">
	<tr bgcolor="#FFFFFF" align="center">
		<th width="100" class="OraTableRowHeader" noWrap  >汇号</th>	
		<th width="100" class="OraTableRowHeader" noWrap  >会员</th>
		<th width="100" class="OraTableRowHeader" noWrap  >金额</th>
		<th width="200" class="OraTableRowHeader" noWrap  >地址</th>
		<th width="200" class="OraTableRowHeader" noWrap  >附言</th>
		<th width="100" class="OraTableRowHeader" noWrap  >导入日期</th>
		<th width="100" class="OraTableRowHeader" noWrap  >邮编</th>
		<th width="80"  class="OraTableRowHeader" noWrap  >状态</th>
		<th width="80"  class="OraTableRowHeader" noWrap  >充值帐户</th>
		<th width="80"  class="OraTableRowHeader" noWrap  >充值日期</th>
	</tr>
	<%
			sql=" select ref_id, mb_code, money/100 as money, remark, order_code, create_date, postcode, status from mbr_money_input where create_date >= sysdate-90 ";
				
			
			if(ref_no != null && ref_no.length()>0){
				sql+="and ref_id = ? ";
			}			

			if (member_name!=null && member_name.length() >0) {
				sql += "and trim(mb_code) = ? ";
			}
			
		    
			pstmt=conn.prepareStatement(sql);
			if(ref_no != null && ref_no.length()>0){
				pstmt.setString(1, ref_no.trim());
				if (member_name!=null && member_name.length() >0) {
					pstmt.setString(2, member_name.trim());
				} 
			} else {
				if (member_name!=null && member_name.length() >0) {
					pstmt.setString(1, member_name.trim());
				}
			}		
 			
			rs=pstmt.executeQuery();
			while(rs.next()){
			String ref_id=rs.getString("ref_id");
			String mb_code=rs.getString("mb_code");
			double money=rs.getDouble("money");
			String address=rs.getString("remark");
			String remark=rs.getString("order_code");
			String create_date = rs.getString("create_date");
			String postcode = rs.getString("postcode");
			String status=rs.getString("status");
			String card_id = "";
			String mb_name = "无";
			String flush_date = "";
			if (status.equals("1")) { //已充，查找所充的帐户
				sql = "select b.card_id, b.name, b.modify_date from mbr_money_history a inner join mbr_members b "
				+ "on a.member_id = b.id and a.credence =  ? and a.pay_method = 6 ";
				pstmt2=conn.prepareStatement(sql);
				pstmt2.setString(1, ref_id);
				rs2 = pstmt2.executeQuery();
				if (rs2.next()) {
					card_id = rs2.getString("card_id");
					mb_name = rs2.getString("name");
					flush_date = rs2.getString("modify_date");
				}
				rs2.close();
				pstmt2.close();
			}
			
	%>	
	<tr bgcolor="#FFFFFF" align="center">	
		<td bgcolor="#FFFFFF" align="left"><%=ref_id%></td>
		<td bgcolor="#FFFFFF" align="left"><%=mb_code%></td>
		<td bgcolor="#FFFFFF" align="right"><%=money%></td>
		<td bgcolor="#FFFFFF" align="left"><%=address%></td>
		<td bgcolor="#FFFFFF" align="left"><%=remark%></td>
		<td bgcolor="#FFFFFF" align="left"><%=create_date%></td>
		<td bgcolor="#FFFFFF" align="left"><%=postcode%></td>
		
		<td bgcolor="#FFFFFF" align="left">
		<%
		    if(status.equals("0")){
		       out.println("未充");
		    }
		    if(status.equals("1")){
		       out.println("已充");
		    }	
			if(status.equals("2")){
		       out.println("充款失败");
		    }	
		%>		
		</td>
		<td bgcolor="#FFFFFF" align="left"><%=card_id%>(<%=mb_name%>)</td>
		<td bgcolor="#FFFFFF" align="left"><%=flush_date%></td>
		
	</tr>	
	<%}%>	
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