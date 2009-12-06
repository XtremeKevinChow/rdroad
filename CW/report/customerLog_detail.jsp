<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
Connection conn=null;
ResultSet rs=null;
PreparedStatement pstmt=null;
String begin_date = request.getParameter("begin_date");
       begin_date=(begin_date==null)?"":begin_date;	
String end_date = request.getParameter("end_date");
       end_date=(end_date==null)?"":end_date;		
String person_id = request.getParameter("person_id");	
String name = request.getParameter("name");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

	document.form.search.disabled = true;
	document.form.submit();
}


</script>
</head>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">分析报表</font><font color="838383"> 
      		-&gt; </font><font color="838383">客服操作日志详细</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<p>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>经办人</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>日期</td>		
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>事件类型</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>会员号</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>礼品号</td>

	</tr>
<%
try{
	conn = DBManager.getConnection();
      String condition="";
      String countsql="";
			if(begin_date.length()>0){
			   condition+=" and mbr_events.event_date >= date '"+begin_date+"'";
			   
			}
			if(end_date.length()>0){
			   condition+=" and mbr_events.event_date < date '"+end_date+"'+1";
			}     

        
	countsql="select  mbr_events.event_date,mbr_events.event_type,item_id,";	
	countsql+=" (select card_id from mbr_members where id=mbr_events.member_id ) card_id,mbr_events.member_id ";	
	countsql+=" from mbr_events "; 
	countsql+="left outer join ";  
	countsql+=" ("; 
	countsql+=" select item_id ,mbr_get_award.create_date,operator_id from mbr_get_award";  
	countsql+=" ) a"; 
	countsql+=" on mbr_events.operator_id=a.operator_id and mbr_events.event_date=a.create_date"; 
	countsql+=" where mbr_events.operator_id="+person_id+condition; 
	countsql+=" and mbr_events.event_type in (3001,3002,3003,3004)";
	countsql+=" order by mbr_events.event_date,mbr_events.member_id ";
        
        //out.println(countsql);
	pstmt=conn.prepareStatement(countsql);
	rs=pstmt.executeQuery();
							
	while(rs.next()){ 
	String event_date=rs.getString("event_date");
	String event_type=rs.getString("event_type");
	String card_id=rs.getString("card_id");
	String item_id=rs.getString("item_id");
	       item_id=(item_id==null)?"":item_id;
	String member_id=rs.getString("member_id");



%>
	<tr align="center">
		<td bgcolor="#FFFFFF"><%=name%></td>
		<td bgcolor="#FFFFFF"><%=event_date%></td>
		<td bgcolor="#FFFFFF">
		<%
		if(event_type.equals("3001")){
		   out.println("手工加礼品");
		}
		if(event_type.equals("3002")){
		   out.println("手工删礼品");
		}
		if(event_type.equals("3003")){
		   out.println("手工加卡");
		}
		if(event_type.equals("3004")){
		   out.println("手工删卡");
		}						
		%>
		</td>
		<td bgcolor="#FFFFFF"><a href="/member/memberDetail.do?id=<%=member_id%>"><%=card_id%></a></td>
	        <td bgcolor="#FFFFFF"><%=item_id%></td>

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
