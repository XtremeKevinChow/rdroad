<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.finance.dao.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%

  String[] itemId=request.getParameterValues("itemId");
  String[] itemQty=request.getParameterValues("itemQty");
  String operateDate=null;
  
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu="return false" onselectstart="return false">
<%
User user=new User();
user = (User)session.getAttribute("user");


String err="";

 Connection conn=null;

      String condition="";
      CallableStatement cstmt = null;
      String sp = null;
      int re = 0;  
      int ret=0;    

try{

 	conn = DBManager.getConnection();
 	 for(int i=0;i<itemId.length;i++){
 	 operateDate=request.getParameter("operateDate"+i); 	  
 	 if(itemQty[i]==null||itemQty[i].equals("")){
 	     itemQty[i]="0";
 	 }
 	//System.out.println(Integer.parseInt(itemId[i]));  
	sp = "{?=call accounts.f_adjustfinstock(?,?,?,?,?)}";
	cstmt = conn.prepareCall(sp);
	cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
	cstmt.setInt(2,Integer.parseInt(itemId[i]));
	cstmt.setDate(3,java.sql.Date.valueOf(operateDate));
	cstmt.setString(4,"70");
	cstmt.setInt(5,Integer.parseInt(itemQty[i]));
	cstmt.setInt(6,Integer.parseInt(user.getId()));        
 	  
	cstmt.execute();
	re = cstmt.getInt(1); 
		
		if (re < 0) {
		ret=-1;	  
		}
 	}
 	 
	
	cstmt.close();			
	if (ret < 0) {
	
	%>
 	<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
		<tr>
			<td bgcolor="#FFFFFF" ><font color="red">操作失败，accounts.F_AdjustFinStock存储过程错误。<a href="javascript:history.back(-1);">返回</a></font></td>
		
			</td>		
		</tr>	
	</table>
	<%	   
	}else{
	%>
		<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
			<tr>
				<td bgcolor="#FFFFFF" ><font color="red">操作成功!&nbsp;<a href="AddSecond.do?type=init">继续手工调整</a></font></td>
			
				</td>		
			</tr>	
		</table>
         <%}%>


<!--*************************************************************************** -->
<%
} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {
				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
}

%>
</body>
</html>
