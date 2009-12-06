<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.crm.common.*"%>
<%@page import="com.magic.crm.promotion.entity.*,com.magic.crm.promotion.dao.*"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
	String msc=request.getParameter("msc");
		msc=(msc==null)?"":msc.trim();
	String name=request.getParameter("name");
		name=(name==null)?"":name.trim();
	String c_page=request.getParameter("page");
	       c_page=(c_page==null)?"":c_page.trim();	
	       int cr_page=1;	      
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function uncheck() {
	var flag = false;
	var len = DataTable.rows.length;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("确定要弃审?"))
		{
		
			document.form1.action = "recruit_activity_issue.jsp?type=uncheck&msc_code="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("请选择记录!");
	}			        	         
}


function check() {
	var flag = false;
	var len = DataTable.rows.length;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("确定要发布?"))
		{
		
			document.form1.action = "recruit_activity_issue.jsp?type=check&msc_code="+idvalue;
			document.form1.submit();
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
				if(trim(row.cells(6).innerText)=="未发布"){
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
			if (confirm("确定要修改?"))
			{		
				document.form1.action = "recruit_activity_update.jsp?msc_code="+idvalue;
				document.form1.submit();
			}
	        }else{
	                alert("只有'未发布'状态才可以修改!");
	        }		
	} else {
		alert("请选择记录!");
	}
			        	         
}
function oratosql() {	
		document.form1.action = "recruit_oratosql.jsp";
		document.form1.submit();			        	         
}
function add() {	
		document.form1.action = "recruit_activity_add.jsp";
		document.form1.submit();			        	         
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">促销管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">招募活动列表</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form method="post" name="form1">
<%
		try{
		  conn = DBManager.getConnection();  
		  Collection rst = new ArrayList();
		  ArrayList activeCol = new ArrayList();
		  Recruit_ActivityDAO raDAO=new Recruit_ActivityDAO();
		  Recruit_Activity info = new Recruit_Activity();
		//************************************************* 分页代码 **********************************************************
		PageAttribute pageUtil = new PageAttribute(30);
		  
		  sql="select * from Recruit_Activity";
	  	  sql+=" order by createdate desc ";
		if(c_page.length()>0){
		cr_page=Integer.parseInt(c_page);	
			activeCol=raDAO.DataToPages(conn,sql,(cr_page-1)*30,cr_page*30);
		}else{
			activeCol=raDAO.DataToPages(conn,sql,pageUtil.getFrom(),pageUtil.getTo());
		}	
		pageUtil.setRecordCount(CatalogDAO.queryListCount(conn,sql));
			 
	        int pageno=pageUtil.getPageNo();		  	  
		  
%>  
<table  border=0  width="800" align="center" id="DataTable">
	<tr >
	<th class="OraTableRowHeader" noWrap align="center" width="30"></th>
		<th align="center"  class="OraTableRowHeader" noWrap  width="200">活动名称</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100" >招募MSC</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">起始日期</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">终止日期</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">使用范围</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">状态</th>	


	</tr>
    <%
		java.util.Iterator it=activeCol.iterator();
		while(it.hasNext()){
		info=(Recruit_Activity)it.next();    
    %>
	<tr bgcolor="#FFFFFF">
	        <td  class=OraTableCellText noWrap align="center"><input type="radio" name="msc_code" value="<%=info.getMsc_Code()%>"></td>		
		<td  class=OraTableCellText noWrap align="center"><%=info.getName()%></td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getMsc_Code()%></td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getStartDate().substring(0,10)%></td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getEndDate().substring(0,10)%></td>
		<td  class=OraTableCellText noWrap align="center">
		<%

		if(info.getScope()==1){
		   out.println("网站使用");
		}
		if(info.getScope()==2){
		   out.println("CRM使用");
		}
		if(info.getScope()==3){
		   out.println("都可使用");
		}						
		%>
		</td>		
		<td  class=OraTableCellText noWrap align="center">
		<%

		if(info.getStatus()==-1){
		   out.println("无效");
		}
		if(info.getStatus()==1){
		   out.println("已发布");
		}
		if(info.getStatus()==0){
		   out.println("未发布");
		}						
		%>
		</td>	
	

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td  class=OraTableCellText noWrap align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="新增招募活动">&nbsp;
		<input type=button onclick="javascript:modify()" value="修改招募活动">&nbsp;
		<input type=button onclick="javascript:check()" value="发布招募活动">&nbsp;	
		<input type=button onclick="javascript:uncheck()" value="弃审">&nbsp;	
		<input type=button onclick="javascript:oratosql()" value="同步数据">&nbsp;	
				
		</td>	
	</tr>	
	
</table>
<table width="800" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="left" >
    <td  bgcolor="#FFFFFF">
    共<font color="red"><%=pageUtil.getRecordCount()%></font>条记录，<font color="red"><%=pageUtil.getPageCount()%></font>页&nbsp;当前第<font color="red"><%=cr_page%></font>页&nbsp;
    <%if(cr_page>1){%>
    <a href="recruit_activity_list.jsp?page=<%=pageno%>">首页</a>&nbsp;
    <a href="recruit_activity_list.jsp?page=<%=cr_page-1%>">上一页</a>
    <%}%>
    <%if(cr_page<pageUtil.getPageCount()){%>
    <a href="recruit_activity_list.jsp?page=<%=cr_page+1%>">下一页</a>&nbsp;
    <a href="recruit_activity_list.jsp?page=<%=pageUtil.getPageCount()%>">末页</a>
    <%}%>
    </td>
  </tr>
 </table>
 <%
		} catch(Exception se) {

			se.printStackTrace();
	
		 } finally {
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
%>
</form>

</body>
</html>
