<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@page import="com.magic.crm.promotion.entity.*,com.magic.crm.promotion.dao.*"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      String gift_no=request.getParameter("gift_no");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function operation(typename) {
	var flag = false;
	var len = DataTable.rows.length;
	var ifdel= false;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{

				if(trim(row.cells(4).innerText)=='失效'){
				   ifdel= false;
				}else{
				   ifdel= true;
				}			
                                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{	  
	      if(ifdel==true){
			if (confirm("确定要删除记录?")){
		
				document.form1.action = "mbr_gift_use_group_del.jsp?status=-1&id="+idvalue;
				document.form1.submit();
			}
	      }else{
	                alert("只有新建和启用状态才可以删除!");
	      }
	} else {
		alert("请选择记录!");
	}			        	         
}
function update() {
	var flag = false;
	var ifdel= false;
	var len = DataTable.rows.length;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
				if(trim(row.cells(4).innerText)=="新建"){
				   ifdel= true;
				}else{
				   ifdel= false;
				}			
                                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}

	if (flag == true)
	{	  
	      if(ifdel==true){
		if (confirm("确定要修改记录?"))	{
	
			document.form1.action = "mbr_gift_use_group_modify.jsp?id="+idvalue;
			document.form1.submit();
		}
	      }else{
	                alert("只有新建状态才可以修改!");
	      }
	} else {
		alert("请选择记录!");
	}			        	         
}
function add() {	
		document.form1.action = "mbr_gift_use_group_add.jsp";
		document.form1.submit();			        	         
}
function qiyong(status,id) {	  
	if (confirm("启用后礼券不能被修改，你确定要启用?")){
		document.form1.action = "mbr_gift_use_group_del.jsp?status=1&id="+id;
		document.form1.submit();
		}				        	         
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">促销管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员礼券使用组列表</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<table  border=0 cellspacing=1 cellpadding=1  width="800" align="center" class="OraTableRowHeader" noWrap   id="DataTable">
	<tr >
	<th align="center" width="100"></th>
		<th align="center" width="200">组号</th>
	        <th align="center" width="200">礼券号</th>
		<th align="center" width="150">礼券类型</th>
		<th align="center" width="150">状态</th>	
	
	</tr>
<%
		try{
		  conn = DBManager.getConnection();  
		  sql="select * from MBR_GIFT_USE_GROUP ";;
	  	  sql+="  order by group_no ,status desc ";
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String rs_gift_number=rs.getString("gift_number");
		  String rs_gift_no=rs.getString("group_no");
		  String id=rs.getString("id");
		  String gift_type=rs.getString("gift_type");
		  String status=rs.getString("status");


%>      
	<tr bgcolor="#FFFFFF">
	        <td align="center"><input type="radio" name="id" value="<%=id%>"></td>
	        <td align="center"><%=rs_gift_no%></td>
	        <td align="center"><%=rs_gift_number%></td>
	        <td align="center"">
	        <%
		if(gift_type.equals("3")){
		   out.println("刮刮卡");
		}
		if(gift_type.equals("4")){
		   out.println("公共电子礼券");
		}
		if(gift_type.equals("5")){
		   out.println("私有电子礼券");
		}
										        
	        %></td>
		<td align="center">
		<%

		if(status.equals("0")){
		 %>
		新建&nbsp;&nbsp;<input type=button onclick="javascript:qiyong(<%=status%>,<%=id%>)" value="启用">
		 <%
		}
		if(status.equals("-1")){
		   out.println("<font color=red>失效</font>");
		}		
		if(status.equals("1")){
		   out.println("启用");
		}
						
		%>
		</td>	

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="新增记录">&nbsp;	
		<input type=button onclick="javascript:update()" value="修改记录">&nbsp;
		<input type=button onclick="javascript:operation('del')" value="删除记录">&nbsp;


		</td>	
	</tr>	
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
</form>

</body>
</html>
