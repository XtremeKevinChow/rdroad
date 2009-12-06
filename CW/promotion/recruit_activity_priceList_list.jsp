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
	String section_name=request.getParameter("section_name");
	       section_name=(section_name==null)?"":section_name.trim();
	String item_code=request.getParameter("item_code");
	       item_code=(item_code==null)?"":item_code.trim();		       	
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
function operation() {
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
                                idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}				
	}
	if (flag == true)
	{

			if (confirm("确定要删除?"))
			{		
				document.form1.action = "recruit_activity_priceList_del.jsp?id="+idvalue;
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
				if(trim(row.cells(6).innerText)=="有效"){
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
				document.form1.action = "recruit_activity_priceList_update.jsp?id="+idvalue;
				document.form1.submit();
			}
	        }else{
	                alert("只有'有效'状态才可以修改!");
	        }		
	} else {
		alert("请选择记录!");
	}
			        	         
}
function add() {	
		document.form1.action = "recruit_activity_priceList_add.jsp";
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
      		-&gt; </font><font color="838383">销售区产品列表</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form method="post" name="form1">
<table  border=0  width="95%"  >
	<tr bgcolor="#FFFFFF">
	        <th  class=OraTableCellText noWrap align="center" width="80">销售区名称</th>		
		<td  class=OraTableCellText noWrap align="center"><input type="text" name="section_name" value="<%=section_name%>"></td>
	        <th  class=OraTableCellText noWrap align="center" width="80">货号</th>		
		<td  class=OraTableCellText noWrap align="center"><input type="text" name="item_code" value="<%=item_code%>"></td>
		<td  class=OraTableCellText noWrap align="center"><input type="submit"  value="查询"></td>
		
	</tr>
</table>
<%
		try{
		  conn = DBManager.getConnection();  
		  Collection rst = new ArrayList();
		  ArrayList activeCol = new ArrayList();
		  Recruit_Activity_PriceListDAO raDAO=new Recruit_Activity_PriceListDAO();
		  Recruit_Activity_PriceList info = new Recruit_Activity_PriceList();
		//************************************************* 分页代码 **********************************************************
		PageAttribute pageUtil = new PageAttribute(30);
		  
		  sql="select a.* from Recruit_Activity_PriceList a,Recruit_Activity_Section b ";
		  sql+="where a.sectionid=b.id ";
		  if(section_name.length()>0){
		  sql+=" and b.name like '%"+section_name+"%'";
		  }
		  if(item_code.length()>0){
		  sql+=" and a.itemcode like '%"+item_code+"%'";
		  }		  
	  	  sql+=" order by a.status desc,  a.sectionid desc";
	  	  
	  	  //out.println(sql);
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
		<th align="center"  class="OraTableRowHeader" noWrap  width="100">销售区</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100" >货号</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="200">产品名称</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">销售类型</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">销售价格</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">状态</th>	
		<th align="center"  class="OraTableRowHeader" noWrap width="">开始日期</th>	
		<th align="center"  class="OraTableRowHeader" noWrap width="">结束日期</th>	
	</tr>
    <%
		java.util.Iterator it=activeCol.iterator();
		while(it.hasNext()){
		info=(Recruit_Activity_PriceList)it.next();    
    %>
	<tr bgcolor="#FFFFFF">
	        <td  class=OraTableCellText noWrap align="center"><input type="radio" name="id" value="<%=info.getId()%>"></td>		
		<td  class=OraTableCellText noWrap align="center">
 <%
 		  sql="select * from Recruit_Activity_Section where ID="+info.getSectionId();
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  out.println("("+rs.getString("msc")+")"+rs.getString("name"));
		  }
		  rs.close();
		  pstmt.close();
 %>		

		</td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getItemCode()%></td>
		<td  class=OraTableCellText noWrap align="center">
 <%
 		  sql="select * from prd_items where item_id="+info.getItemId();
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  out.println(rs.getString("name"));
		  }
		  rs.close();
		  pstmt.close();
 %>		


		</td>
		<td  class=OraTableCellText noWrap align="center">
 <%
 		  sql="select * from s_sell_type where id="+info.getSellType();
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  

		  out.println(rs.getString("name"));
		  }
		  rs.close();
		  pstmt.close();

 %>
		
		</td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getPrice()%></td>
		<td  class=OraTableCellText noWrap align="center">
		<%
		if(info.getStatus()==0){
		     out.println("<font color='red'>失效</font>");
		}
		if(info.getStatus()==1){
		     out.println("有效");
		}		
		%>
		</td>
		<td  class=OraTableCellText noWrap align="center"><%=(info.getStartDate()).substring(0,10)%></td>
		<td  class=OraTableCellText noWrap align="center"><%=(info.getEndDate()).substring(0,10)%></td>
	

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td  class=OraTableCellText noWrap align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="新增">&nbsp;
		<input type=button onclick="javascript:modify()" value="修改">&nbsp;
		<input type=button onclick="javascript:operation()" value="删除">&nbsp;
		
				
		</td>	
	</tr>	
	
</table>
<table width="800" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="left" >
    <td  bgcolor="#FFFFFF">
    共<font color="red"><%=pageUtil.getRecordCount()%></font>条记录，<font color="red"><%=pageUtil.getPageCount()%></font>页&nbsp;当前第<font color="red"><%=cr_page%></font>页&nbsp;
    <%if(cr_page>1){%>
    <a href="recruit_activity_priceList_list.jsp?page=<%=pageno%>">首页</a>&nbsp;
    <a href="recruit_activity_priceList_list.jsp?page=<%=cr_page-1%>">上一页</a>
    <%}%>
    <%if(cr_page<pageUtil.getPageCount()){%>
    <a href="recruit_activity_priceList_list.jsp?page=<%=cr_page+1%>">下一页</a>&nbsp;
    <a href="recruit_activity_priceList_list.jsp?page=<%=pageUtil.getPageCount()%>">末页</a>
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
