<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String msc_code=request.getParameter("msc_code");
      String sql="";
		try{
		  conn = DBManager.getConnection();  

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/popcalendar.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function queryInput() {
		
	
	var sdate = document.form.start_date.value; 
	if(sdate==""){
	alert('����д��ʼ����!');
	document.form.start_date.select();
	return false;
	}	
	var edate = document.form.end_date.value; 
	if(edate==""){
	alert('����д��������!');
	document.form.end_date.select();
	return false;
	}
	
	
												
	document.form.input.disabled = true;

}
function initFocus(){

	return true;
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ļ��޸�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="recruit_activity_updateok.jsp" method="post" name="form" onsubmit="return queryInput();">
<%

		  sql="select * from Recruit_Activity where msc='"+msc_code+"'";

		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String MSC=rs.getString("MSC");
		  String Name=rs.getString("Name");
		  String StartDate=rs.getString("StartDate").substring(0,10);
		  String EndDate=rs.getString("EndDate").substring(0,10);
		  String Scope=rs.getString("Scope");
		  String Remarks=rs.getString("Remarks");
		  String headhtml=rs.getString("headhtml");
		         headhtml=(headhtml==null)?"":headhtml;
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>	
		<td bgcolor="#FFFFFF" width="150">����</td>
		<td bgcolor="#FFFFFF"><%=MSC%><input type="hidden"  name="msc_code" value="<%=MSC%>" > </td>		
		<td bgcolor="#FFFFFF" width="150">�����</td>
		<td bgcolor="#FFFFFF"><%=Name%><input type="hidden"  name="msc_code" value="<%=Name%>" ></td>

	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��ʼ����</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="start_date" value="<%=StartDate%>" readonly><INPUT TYPE="button" value="" onclick='popUpCalendar(this, form.start_date, dateFormat,-1,-1,true)' style="background-image:url(img/Button.gif);width:25px;height:17px;border:0px;padding:0px;">  
		</td>
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��������</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="end_date" value="<%=EndDate%>"  readonly><INPUT TYPE="button" value="" onclick='popUpCalendar(this, form.end_date, dateFormat,-1,-1,true)' style="background-image:url(img/Button.gif);width:25px;height:17px;border:0px;padding:0px;">  
		</td>		
	
	</tr>	
	<tr>		
		<td bgcolor="#FFFFFF" width="150">ʹ�÷�Χ</td>
		<td bgcolor="#FFFFFF" colspan="3">
		<select name="scope">
 
		 <option value="1" <%if(Scope.equals("1")){%>selected<%}%>>��վʹ��</option>	
		 <option value="2" <%if(Scope.equals("2")){%>selected<%}%>>CRMʹ��</option>	
		 <option value="3" <%if(Scope.equals("3")){%>selected<%}%>>����ʹ��</option>	
	 
		</select>				
		</td>		

	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="150">html����</td>
		<td bgcolor="#FFFFFF" colspan="3"><textarea  name="headhtml" rows="5" cols="50"><%=headhtml%></textarea></td>
	
	</tr>		

	<tr>		
		<td bgcolor="#FFFFFF" width="150">����</td>
		<td bgcolor="#FFFFFF" colspan="3"><textarea  name="remark" rows="5" cols="50"><%=Remarks%></textarea></td>
	
	</tr>

																	
	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value="�� �� �� ��">					
		</td>			
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