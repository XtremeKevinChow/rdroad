<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>

<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String tag=request.getParameter("tag");
             tag=(tag==null)?"":tag;
      String startDate=request.getParameter("startDate");
             startDate=(startDate==null)?"":startDate;
      String endDate=request.getParameter("endDate");
             endDate=(endDate==null)?"":endDate;
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript">

function search() {	
if (document.forms[0].startDate.value == ""&&document.forms[0].endDate.value == "")
	{
		alert("请选择日期范围");
		document.forms[0].startDate.focus();
		return;
	}	

	if(document.forms[0].startDate.value != ""){
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }	
	}
	if(document.forms[0].endDate.value != ""){
		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return;
		 }	
	 
	}
	document.forms[0].submit();			        	         
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员未收到目录查询</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1" action="mbr_ask_catalog.jsp">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
			开始日期：
			<input type="text" name="startDate" size="10"  value="<%=startDate%>">
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>
			结束日期：
			<input type="text" name="endDate" size="10"  value="<%=endDate%>">
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="query" value=" 查   询 " onclick="javascript:search()">	
			<input type="hidden" name="tag" value="1">		
		</td>
		</tr>

		
</table>
<br>	
<%if(tag.length()>0){%>

<table  border=0 cellspacing=1 cellpadding=1  width="600" align="center" >

<%
		int total=0;
		try{
		  conn = DBManager.getConnection();  
		  
		  sql=" select a.cmpt_type_id,b.card_id,b.postcode,b.address,b.name from mbr_complaint a "
		  +"inner join mbr_members b on a.mbr_id=b.id where a.cmpt_type_id "
		  +"in (select cmpt_type_id from complaint_type start with cmpt_type_id = 29 "
		  +"connect by parent_id = prior cmpt_type_id) ";
		  if(startDate.length()>0){
			sql+=" and a.create_date>=date'"+startDate+"'";
		  }
		  if(endDate.length()>0){
			sql+=" and a.create_date<date'"+endDate+"'+1";
		  }
		  pstmt=conn.prepareStatement(sql);
		  
		  //System.out.println(sql);
		  rs=pstmt.executeQuery();

		  while(rs.next()){		
		  total ++;  
		  String postcode=rs.getString("postcode");
		         postcode=(postcode==null)?"":postcode;
		  String address=rs.getString("address");
		  	 address=(address==null)?"":address;
		  String name=rs.getString("name");
		  	 name=(name==null)?"":name;



%>      
	<tr bgcolor="#FFFFFF">
	        <td align="left"><%=postcode%></td>
        </tr>	
        <tr bgcolor="#FFFFFF">        
	        <td align="center"><%=address%></td>
	</tr>
	<tr bgcolor="#FFFFFF">
	        <td align="center""><%=name%></td>
	 </tr>
	<tr bgcolor="#FFFFFF">
	        <td align="center""></td>
	 </tr>		

	
	<%}//while%>

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
</table>
共：<%=total%>条
<%}%>
</form>

</body>
</html>
