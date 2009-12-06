<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String so_number=request.getParameter("so_number");
       so_number=(so_number==null)?"":so_number.trim();

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function doSearch() {

	if(document.form1.so_number.value == "") {
		// û�в�ѯ����
		alert("�������ѯ������");
		return false;
	} 
	theForm.BtnQuery.disabled;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ������ʷ</font></td>
  </tr>
</table>

<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<th width="20%" class="OraTableRowHeader" noWrap align=middle>������</th>
		<th width="15%" class="OraTableRowHeader" noWrap align=middle>������</th>
		<th width="20%" class="OraTableRowHeader" noWrap align=middle>��������</th>
		<th width="20%"  class="OraTableRowHeader" noWrap align=middle>������</th>
		<th width=""	class="OraTableRowHeader" noWrap align=middle>����ʱ��</th>

	</tr>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      try{
		conn = DBManager.getConnection();
		String sql = "select so_number, op_type, goods_fee, "
		+ "(select name from org_persons where id=nvl(op_user,0)) as creator,"
		+ " to_char(op_time,'yyyy-mm-dd hh24:mi:ss') op_time"
		+ " from ord_header_modify_his where so_number= '"+so_number+"'"
		+ " order by op_time";
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();

		while(rs.next()){
			String rs_number=rs.getString("so_number");
			String order_sum=rs.getString("goods_fee");
			String op_type = rs.getString("op_type");
			String creator=rs.getString("creator");
			String mod_date=rs.getString("op_time");							 
%>
	<tr align="center">
		<td class=OraTableCellText><%=rs_number%></td>
		<td class=OraTableCellText align="right"><%=order_sum%></td>
		<td class=OraTableCellText align="left"><%=op_type%></td>
		<td class=OraTableCellText align="left"><%=creator%></td>
		<td class=OraTableCellText><%=mod_date%></td>
	</tr>
<%
		}//while
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
		if (conn != null)
			try {
				conn.close();
			 } catch(SQLException sqe) {}
	
	}

%>
</table>
<TABLE align="center">
<TR>
	<TD><input type="button" onclick="history.back();" value=" ���� " name="backBtn"></TD>
</TR>
</TABLE>
</body>
</html>
