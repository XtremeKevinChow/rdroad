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
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function operation(typename) {
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
    
				if(trim(row.cells(4).innerText)=="失效"){
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
		if (confirm("确定要删除产品组?"))
		{		
			document.form1.action = "mbr_gift_item_mst_del.jsp?status=-1&id="+idvalue;
			document.form1.submit();
		}
	      }else{
	                alert("只有新建和启用状态才可以删除!");
	      }
	} else {
		alert("请选择记录!");
	}			        	         
}
function modify() {
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
			if (confirm("确定要修改产品组?"))
			{		
				document.form1.action = "mbr_gift_item_mst_modify.jsp?id="+idvalue;
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
		document.form1.action = "mbr_gift_item_mst.jsp";
		document.form1.submit();			        	         
}
function checkstatus() {	  
				        	         
}
function qiyong(status,id) {	  
	if (confirm("启用后产品组不能被修改，你确定要启用?")){
		document.form1.action = "mbr_gift_item_mst_del.jsp?status=1&id="+id;
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
      		-&gt; </font><font color="838383">产品组列表</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<table  border=0 cellspacing=1 cellpadding=1  width="500" align="center" class="OraTableRowHeader" noWrap   id="DataTable">
	<tr >
	<th align="center" width="50"></th>
		<th align="center" width="300">组名称</th>
		<th align="center" width="100">类型</th>
		<th align="center" width="100">最少产品数</th>
		<th align="center" width="150">状态</th>	
	
	</tr>
<%
		try{
		  conn = DBManager.getConnection();  
		  sql="select * from MBR_GIFT_ITEM_MST ";
	  	  sql+=" order by ITEM_GROUP_ID desc ";
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String ITEM_GROUP_ID=rs.getString("ITEM_GROUP_ID");
		  String MIN_ITEM_COUNT=rs.getString("MIN_ITEM_COUNT");
		  String GROUP_DESC=rs.getString("GROUP_DESC");
		  String status=rs.getString("status");
		  String itemgroup_type=rs.getString("itemgroup_type");

%>      
	<tr bgcolor="#FFFFFF">
	        <td align="center"><input type="radio" name="ITEM_GROUP_ID" value="<%=ITEM_GROUP_ID%>"></td>
	        <td align="center""><a href="mbr_gift_item_list.jsp?min_item_count=<%=MIN_ITEM_COUNT%>&group_id=<%=ITEM_GROUP_ID%>&group_desc=<%=GROUP_DESC%>"><%=GROUP_DESC%></a></td>
	        <td align="center">
	        <%
	        if(itemgroup_type.equals("0")){
	          out.println("单品挂钩");
	        }
	        if(itemgroup_type.equals("1")){
	          out.println("图书");
	        }
	        if(itemgroup_type.equals("2")){
	          out.println("影视");
	        }
	        if(itemgroup_type.equals("3")){
	          out.println("音乐");
	        }
	        if(itemgroup_type.equals("4")){
	          out.println("游戏/软件");
	        }
	        if(itemgroup_type.equals("5")){
	          out.println("礼品");
	        }
	        if(itemgroup_type.equals("6")){
	          out.println("其他");
	        }
			if(itemgroup_type.equals("8")){
	          out.println("音像礼品");
	        }
	        %>
	        </td>
        
		
		<td align="right"><%=MIN_ITEM_COUNT%></td>
		<td align="center">
		<%
		if(status.equals("-1")){
		%>
<font color=red>失效</font>
		    <%
		}
		if(status.equals("1")){
		   out.println("启用");
		}
		if(status.equals("0")){
		 %>
		新建&nbsp;&nbsp;<input type=button onclick="javascript:qiyong(<%=status%>,<%=ITEM_GROUP_ID%>)" value="启用">
		 <%
		}						
		%>
		</td>	

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="新增产品组">&nbsp;	
		<input type=button onclick="javascript:operation('del')" value="删除产品组">&nbsp;
		<input type=button onclick="javascript:modify()" value="修改产品组">&nbsp;
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
