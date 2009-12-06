<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.DBManager"%>
<%@ page import="com.magic.utils.Arith"%>

<%
	String beginDate = request.getParameter("beginDate");
    beginDate = (beginDate == null) ? "" : beginDate;
	
	String endDate = request.getParameter("endDate");
    endDate = (endDate == null) ? "" : endDate;
	

    String tag = request.getParameter("tag");
    tag = (tag == null) ? "0" : tag;    
    
    Connection conn=null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
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


function _query() {
	var frm = document.forms[0];
	if (frm.beginDate.value == "" && frm.endDate.value == "")
	{
		alert("请输入查询条件!");
		return;
	}	   
	frm.queryBtn.disabled = true;
	frm.submit();
}




//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="member_origin_sum.jsp" name="form" method="POST">
<input type="hidden" name="tag" value="1">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="0" align="center" >
	<tr>	
		
		<td width="80" align="right">开始日期：</td>
		<td align="left">
		<input name="beginDate" value="<%=beginDate%>" readonly size="10"><a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		<td width="80" align="right">结束日期：</td>
		<td align="left">
		<input name="endDate" value="<%=endDate%>" readonly size="10"><a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>	
		<td >
		<input type=button name="queryBtn" value=" 查询 " onclick="_query();">	
		</td>
	</tr>
</table>
<br>
<%if(tag.equals("1")){%>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" id="DataTable">
	<tr bgcolor="#FFFFFF" align="center">
		<th width="20%" class="OraTableRowHeader" noWrap  >MSC</th>	
		<th width="20%" class="OraTableRowHeader" noWrap  >名称</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >数量</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >比例</th>
		
	</tr>
	<%
			sql = " select count(1)   ";
			sql+= "from mbr_members  ";
			sql+= "where create_date>=to_date(?, 'yyyy-mm-dd') and create_date<to_date(?, 'yyyy-mm-dd') + 1";
			sql+= "and is_organization = 0 and netshop_id <= 0 ";	
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beginDate);
			pstmt.setString(2, endDate);
			//System.out.println("over1-----------------");
			rs  =  pstmt.executeQuery();
			int all_amt =0;
			if(rs.next()){
				all_amt=rs.getInt(1);
			}
			//System.out.println("over2-----------------");
			rs.close();
			pstmt.close();
		
			sql="select  a.msc_code, b.name, count(1) as cnt ";
			sql+= "from mbr_members a left join recruit_activity b on a.msc_code = b.msc ";	
			sql+= "where a.create_date>=to_date(?, 'yyyy-mm-dd') and a.create_date<to_date(?, 'yyyy-mm-dd')+1 ";
			sql+= "and a.is_organization = 0 and a.netshop_id <= 0 group by a.msc_code, b.name ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beginDate);
			pstmt.setString(2, endDate);
			
			rs  =  pstmt.executeQuery();
			
			//System.out.println("over3-----------------");
			while(rs.next()){
			
				String msc_code=rs.getString("msc_code");
				String name=rs.getString("name");
				int cnt=rs.getInt("cnt");
				double rate = Arith.round((cnt*100)/all_amt, 2);
				
				
			
	%>	
	<tr bgcolor="#FFFFFF" align="center">	
		<td bgcolor="#FFFFFF" align="left"><%=msc_code%></td>
		<td bgcolor="#FFFFFF" align="left"><%=name%></td>
		<td bgcolor="#FFFFFF" align="left"><%=cnt%></td>
		
		<td bgcolor="#FFFFFF" align="left"><%=rate%></td>
	</tr>	
	<%}%>	
	<tr>
	    <td colspan=2 bgcolor="#FFFFFF">总计：</td>
		<td bgcolor="#FFFFFF" align="left"><%=all_amt%></td>
		<td bgcolor="#FFFFFF" align="left">100%</td>
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