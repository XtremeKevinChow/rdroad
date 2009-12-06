<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String rk_no = request.getParameter("rk_no");
			 rk_no=(rk_no==null)?"":rk_no;	


%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">分析报表</font><font color="838383"> 
      		-&gt; </font><font color="838383">三无产品详细信息</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>编号</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>货号</td>		
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>条形码</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>入库数量</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>破损数量</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>&nbsp;</td>
	</tr>
<%
			Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;

		try{
				 conn = DBManager.getConnection();
					String sql="select * from jxc.sto_rk_ns_dtl  where rk_no='"+rk_no+"'";
					pstmt=conn.prepareStatement(sql);
					rs=pstmt.executeQuery();
						while(rs.next()){ 
						String rk_dtl_pk=rs.getString("rk_dtl_pk");
						String item_code=rs.getString("item_code");
									 item_code=(item_code==null)?"":item_code;
						String item_name=rs.getString("item_name");
									 item_name=(item_name==null)?"":item_name;						
						String barcode=rs.getString("barcode");
									 barcode=(barcode==null)?"":barcode;								
						String use_qty=rs.getString("use_qty");
									 use_qty=(use_qty==null)?"":use_qty;							
						String dis_qty=rs.getString("dis_qty");
						       dis_qty=(dis_qty==null)?"":dis_qty;
%>
	<tr align="center">
		<td width="10%" bgcolor="#FFFFFF"><%=rk_dtl_pk%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=item_code%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=item_name%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=barcode%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=use_qty%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=dis_qty%></td>
		<td width="15%" bgcolor="#FFFFFF"></td>
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

%>
</body>
</html>
