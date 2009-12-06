<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.DBManager"%>
<%
  String item_code=request.getParameter("item_code");
  item_code=(item_code==null)?"":item_code;
	
%>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function add() {

    document.form.action="mbr_get_mbr_gift_add.jsp";
	//document.form.input.disabled = true;
	document.form.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 会员管理 -&gt; 推荐会员礼券设置</font></td>
  </tr>
</table>
<form name="form" method="post" action="mbr_get_mbr_gift_list.jsp">
<table width="95%" align="center" cellspacing="1" border="0" >
	<tr align="left">
		<td bgcolor="#FFFFFF" >
		<input type="button" value="新增设置" onclick="javascript:add()">
		</td>
	</tr>    
      
</table>

 <% 
 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;



try{
		 conn = DBManager.getConnection();
		 String sql="";
%>

<br>
<table width="95%" align="center" cellspacing="1" border="0">
	<tr>
	    <th width="8%" class="OraTableRowHeader" noWrap align=middle  >记录id</th>
		<th width="8%" class="OraTableRowHeader" noWrap align=middle  >礼券号</th>
		<th width="10%" class="OraTableRowHeader" noWrap align=middle  >开始日期</th>
		<th width="10%" class="OraTableRowHeader" noWrap align=middle  >结束日期</th>
		
		<th width="10%" class="OraTableRowHeader" noWrap align=middle  >保留天数</th>	
		<th width="10%" class="OraTableRowHeader" noWrap align=middle  >是否有效</th>	
		<th width="10%" class="OraTableRowHeader" noWrap align=middle  >操作</th>
	</tr>
	<%
	sql= " select a.id,a.gift_number,a.KEEP_DAYS,is_valid,a.end_date,a.begin_date  from mbr_get_mbr_gift a "
	   + " order by  a.IS_VALID desc,id desc";
	//out.println(sql);
	pstmt=conn.prepareStatement(sql);
	String OPName = "";
	String beginDate = "";
	rs=pstmt.executeQuery();
		while(rs.next()){ 	
		String gift_number=rs.getString("gift_number");   
		String keep_days=rs.getString("keep_days");   
		String is_valid=rs.getString("is_valid");   
		String end_date=rs.getString("end_date").substring(0,10);
		java.sql.Date sqlEndDate = rs.getDate("end_date");   
		beginDate = rs.getString("begin_date").substring(0,10);;
		String id=rs.getString("id"); 
		
	%>
	<tr>
		<td  class=OraTableCellText><%=id%></td>
		<td  class=OraTableCellText>
		<a   href=""  onclick="window.open('../promotion/mbr_gift_certificates_detail.jsp?id=<%=gift_number%>','_blank','width=600,height=200,scrollbars=0,resizable=0');return   false">
		<%=gift_number%></a></td>
		<td  class=OraTableCellText><%=beginDate%></td>
		<td  class=OraTableCellText><%=end_date%></td>
		<td  class=OraTableCellText><%=keep_days%></td>
		<td  class=OraTableCellText>
		<%
		if(Integer.parseInt(is_valid)==0 ){
		   out.println("有效");
		}else{
		    out.println("<font color='red'>失效</font>");
		 }
		%></td>
		<td  class=OraTableCellText><nobr>
		<input type=button value="禁用" onclick="javascript:location.href='mbr_get_mbr_gift_del.jsp?id=<%=id%>&is_valid=-1';">
		<input type=button value="启用" onclick="javascript:location.href='mbr_get_mbr_gift_del.jsp?id=<%=id%>&is_valid=0';"></nobr>
		</td>
	</tr> 
	<%}%>     
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

%>
</form>
</body>
</html>
