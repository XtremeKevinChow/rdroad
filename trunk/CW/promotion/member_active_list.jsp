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
<script language="JavaScript">
function operation(typename) {
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
		if (confirm("确定要进行这一步操作?"))
		{
		
			document.form1.action = "Catalog.do?type=operation&price_type_id=1&typename="+typename+"&id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("请选择记录!");
	}			        	         
}
function modify() {
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
		if (confirm("确定要进行这一步操作?"))
		{
		
		
			document.form1.action = "Catalog.do?type=init&price_type_id=1&id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("请选择记录!");
	}			        	         
}
function add() {	
		document.form1.action = "Catalog.do?type=init&price_type_id=1";
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
		  CatalogDAO cataDao=new CatalogDAO();
		  Catalog info = new Catalog();
		//************************************************* 分页代码 **********************************************************
		PageAttribute pageUtil = new PageAttribute(30);
		  
		  sql="select id,name,msc,effect_date,expired_date,status,is_valid,entry_fee,gift_number  from prd_pricelists where price_type_id=1";
	  	  sql+=" order by id desc ";
		if(c_page.length()>0){
		cr_page=Integer.parseInt(c_page);	
			activeCol=cataDao.DataToPages(conn,sql,(cr_page-1)*30,cr_page*30);
		}else{
			activeCol=cataDao.DataToPages(conn,sql,pageUtil.getFrom(),pageUtil.getTo());
		}	
		pageUtil.setRecordCount(CatalogDAO.queryListCount(conn,sql));
			 
	        int pageno=pageUtil.getPageNo();		  	  
		  
%>  
<table  border=0  width="800" align="center" id="DataTable">
	<tr >
	<th class="OraTableRowHeader" noWrap align="center" width="30"></th>
		<th align="center"  class="OraTableRowHeader" noWrap  width="200">活动名称</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100" >招募MSC</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100" >对应礼券</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">起始日期</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">终止日期</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100">状态</th>	
		
			
	</tr>
    <%
		java.util.Iterator it=activeCol.iterator();
		while(it.hasNext()){
		info=(Catalog)it.next();    
    %>
	<tr bgcolor="#FFFFFF">
	        <td  class=OraTableCellText noWrap align="center"><input type="radio" name="id" value="<%=info.getID()%>"></td>		
		<td  class=OraTableCellText noWrap align="center"><a href="msc_product_list.jsp?pricelist_id=<%=info.getID()%>"><%=info.getName()%></a></td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getMsc()%></td>
<td  class=OraTableCellText noWrap align="center">&nbsp;<%=info.getGift_number()%></td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getEffect_date().substring(0,10)%></td>
		<td  class=OraTableCellText noWrap align="center"><%=info.getExpirped_date().substring(0,10)%></td>
		<td  class=OraTableCellText noWrap align="center">
		<%
		if(info.getStatus()==-10){
		   out.println("已删除");
		}
		if(info.getStatus()==-1){
		   out.println("中止");
		}
		if(info.getStatus()==100){
		   out.println("已发布");
		}
		if(info.getStatus()==0){
		   out.println("正常");
		}						
		%>
		</td>	
			
		
	</tr>
	<%}//while%>
<tr class="OraBGAccentDark" >
		<td  class=OraTableCellText noWrap align="center" colspan="9">
		<input type="button" onclick="javascript:add()" value="新增招募活动">&nbsp;
		<input type=button onclick="javascript:modify()" value="修改招募活动">&nbsp;
		<input type=button onclick="javascript:operation('release')" value="招募活动发布">&nbsp;		
		<input type=button onclick="javascript:operation('pause')" value="中止招募活动">&nbsp;		
		<input type=button onclick="javascript:operation('del')" value="删除招募活动">&nbsp;
				
		</td>	
	</tr>	
	
</table>
<table width="800" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
   <tr  align="left" >
    <td  bgcolor="#FFFFFF">
    共<font color="red"><%=pageUtil.getRecordCount()%></font>条记录，<font color="red"><%=pageUtil.getPageCount()%></font>页&nbsp;当前第<font color="red"><%=cr_page%></font>页&nbsp;
    <%if(cr_page>1){%>
    <a href="member_active_list.jsp?page=<%=pageno%>">首页</a>&nbsp;
    <a href="member_active_list.jsp?page=<%=cr_page-1%>">上一页</a>
    <%}%>
    <%if(cr_page<pageUtil.getPageCount()){%>
    <a href="member_active_list.jsp?page=<%=cr_page+1%>">下一页</a>&nbsp;
    <a href="member_active_list.jsp?page=<%=pageUtil.getPageCount()%>">末页</a>
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
