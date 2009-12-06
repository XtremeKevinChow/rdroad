<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.DBManager"%>

<%
    String personId = request.getParameter("personId");
    personId = (personId == null) ? "" : personId; 
	
      		
    String levelGrade = request.getParameter("levelGrade");
    levelGrade = (levelGrade == null) ? "" : levelGrade;

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
	if (frm.personId.value == "" && frm.levelGrade.value == "" 
		&& frm.beginDate.value == "" && frm.endDate.value == "")
	{
		alert("请输入查询条件!");
		return;
	}	   
	frm.queryBtn.disabled = true;
	frm.submit();
}


function _load() {
	var frm = document.forms[0];
	frm.levelGrade.value = "<%=levelGrade%>";
}

//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="_load();">
<form action="ivr_list.jsp" name="form" method="POST">
<input type="hidden" name="tag" value="1">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="0" align="center" >
	<tr>	
		<td width="80" align="right">工号：</td>
		<td align="left">
		<input name="personId" value="<%=personId%>" size="12">	
		<td width="80" align="right">满意度：</td>
		<td align="left">
		<select name="levelGrade" value="<%=levelGrade%>">
		<option value="">选择...</option>
		<option value="1">很满意</option>
		<option value="2">满意</option>
		<option value="3">一般</option>
		<option value="4">不满意</option>
		</select>
		</td>
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
		<th width="20%" class="OraTableRowHeader" noWrap  >工号</th>	
		<th width="20%" class="OraTableRowHeader" noWrap  >主叫号码</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >被叫号码</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >满意度</th>
		<th width="20%" class="OraTableRowHeader" noWrap  >呼叫时间</th>
	</tr>
	<%
			sql="select personid, telephone, telephone2, level_id, ivrdate from org_grade "
			+ "where ivrdate >= to_date(?, 'yyyy-mm-dd') and ivrdate < to_date(?, 'yyyy-mm-dd') + 1";
				
			
			if(personId != null && personId.trim().length() > 0){
				sql+="and personid = ? ";
			}			

			if (levelGrade  !=null && levelGrade.trim().length() >0) {
				sql += "and level_id = ? ";
			}
			
		        //out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, beginDate);
			pstmt.setString(2, endDate);
			if(personId != null && personId.trim().length() > 0){
				pstmt.setString(3, personId.trim());
				if (levelGrade != null && levelGrade.trim().length() > 0) {
					pstmt.setString(4, levelGrade.trim());
				} 
			} else {
				if (levelGrade != null && levelGrade.trim().length() > 0) {
					pstmt.setString(3, levelGrade.trim());
				}
			}

			rs  =  pstmt.executeQuery();
			while(rs.next()){
			
				String personid=rs.getString("personid");
				String telephone=rs.getString("telephone");
				String telephone2=rs.getString("telephone2");
				int level_id=rs.getInt("level_id");
				String ivrdate=rs.getString("ivrdate");
			
			
	%>	
	<tr bgcolor="#FFFFFF" align="center">	
		<td bgcolor="#FFFFFF" align="left"><%=personid%></td>
		<td bgcolor="#FFFFFF" align="left"><%=telephone%></td>
		<td bgcolor="#FFFFFF" align="left"><%=telephone2%></td>
		<td bgcolor="#FFFFFF" align="left">
		<%
			if (level_id == 1) { 
				out.println("很满意");
			} else if (level_id == 2) {
				out.println("满意");
			} else if (level_id == 3) {
				out.println("一般");
			} else if (level_id == 4) {
				out.println("不满意");
			}
		%>
				
		
		</td>
		<td bgcolor="#FFFFFF" align="left"><%=ivrdate%></td>
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