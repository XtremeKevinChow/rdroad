<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

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

<SCRIPT LANGUAGE="JavaScript">

function ref(){
       document.form.action="";
       document.form.submit();
}
function add(){
       document.form.action="crush_card_lot_add.jsp";
       document.form.submit();
}
function f_cancel(lot) {
    if (confirm("确实要作废对应充值卡吗?")) {
    document.form.action="crush_card_lot_cancel.jsp?lot_no=" + lot;
    document.form.submit();
    }
}
function f_valid(lot) {
    
    document.form.action="crush_card_lot_valid.jsp?lot_no=" + lot;
    document.form.submit();
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">充值卡批次列表</font><font color="838383"> 
      	</td>
   </tr>
</table>

<br>

<table width="80%" align="center" cellspacing="1" border="0" >
	<tr align="center" class="OraTableRowHeader" noWrap>
		<td width="100"  noWrap align=middle><b>批号</b></td>
		<td width="100"   noWrap align=middle><b>开始日期</b></td>
		<td width="100"   noWrap align=middle><b>结束日期</b></td>
		<td width="100"   noWrap align=middle><b>数量</b></td>
        <td width="100"   noWrap align=middle><b>状态</b></td>
        <td width="100"   noWrap align=middle><b>操作</b></td>
	</tr>
	<%
	        int i=0;
	        
			sql=" select t1.lot_no,to_char(begin_date,'yyyy-mm-dd') begin_date,to_char(end_date,'yyyy-mm-dd') end_date,"
			+ " decode(t1.status,0,'未开通',1,'已开通',-1,'作废','其他') status,nvl(count(t2.card_num),0) qty "
			+ " from CRUSH_CARD_lot t1 "
			+ " left join crush_card_mst t2 on t1.lot_no = t2.card_lot "
			+ " where t1.status >=0 "
			+ " group by t1.lot_No,begin_date,end_date,t1.status "
			+ " order by t1.begin_date desc ";			

			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String lot_no = rs.getString("lot_no");
			String begin_date = rs.getString("begin_date");
			String end_date = rs.getString("end_date");
			String status = rs.getString("status");
			int qty = rs.getInt("qty");
			
	%>	
        	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %> align="center">	
        		<td><%=lot_no%>&nbsp;</td>
        		<td ><%=begin_date%>&nbsp;</td>
        		<td ><%=end_date%>&nbsp;</td>
        		<td ><%=qty%>&nbsp;</td>
        		<td ><%=status%>&nbsp;</td>
        		<td >
        		<input type=button name="cancel" value="作废" onclick="f_cancel('<%=lot_no%>');">
        		<% if (status.equals("未开通")) {%>
        		    <input type=button name="btnok" value="开通" onclick="f_valid('<%=lot_no%>');">
        		<%}%>
        		</td>
        	</tr>	
        	    
	<%  i++;
	}%>	
	<tr>
		<td colspan=6 align="center"><input type="button" value="新增数据" onclick="add()"></td>
	</tr>
</table><br>

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