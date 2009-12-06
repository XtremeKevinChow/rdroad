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
        String card_id=request.getParameter("card_id");
      		card_id=(card_id==null)?"":card_id;
       String ship_no=request.getParameter("ship_no");
      		ship_no=(ship_no==null)?"":ship_no; 
        String name=request.getParameter("name");
      		name=(name==null)?"":name;
        String ship_id=request.getParameter("ship_id");
      	String id=request.getParameter("id"); 
      	String status=request.getParameter("status"); 
      	 
    
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
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--

function submit_f() {
	

	document.form.action="supplement_deal_ok.jsp";
	document.form.checkBtn.disabled = true;
	document.form.submit();
}
function update_f() {
	

	document.form.action="supplement_deal_update.jsp?id=<%=id%>";
	document.form.finishBtn.disabled = true;
	document.form.submit();
}


//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="supplement_deal_ok.jsp" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">销售管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">补货单审核</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td width="50">会员号</td>
		<td  bgcolor="#FFFFFF">
		<%=card_id%>
		</td>	
		<td width="50">发货单号</td>
		<td  bgcolor="#FFFFFF">
		<%=ship_no%>
		</td>	
		<%if(status.equals("1")){%>
		<td width=""  align="left" bgcolor="#FFFFFF">
		&nbsp;<input type="button" name="checkBtn" value=" 审核 " onclick="submit_f()">
		<input type="hidden" name="supply_id" value="<%=id%>">
		<%}%>

		&nbsp;<input type="button" name="finishBtn" value=" 完成 " onclick="update_f()">
		
		</td>									
	</tr>		
		
</table>
<br>

<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr bgcolor="#FFFFFF" align="center">	

			<th width="60">货号</th>
			<th width="220">产品名称</th>
			<th width="60">原数量</th>		
			<th width="60">操作类型</th>
			<th width="60">新货号</th>
			<th width="220">新产品名称</th>
			<th width="">补发数量</th>
	</tr>
	<%
				
			sql="select a.*,b.item_id,b.item_code,b.name,c.dtl_type,c.item_id as new_item_id,";
			sql+=" (select name from prd_items where item_id=c.item_id) as new_item_name,c.qty as new_qty ";
			sql+=" from ord_shippingnotice_lines a  ";
			sql+=" inner join prd_items b on a.item_id=b.item_id ";
		  	sql+=" left outer join jxc.sto_supplement_dtl c on a.id=c.orgin_dtl_id and c.ss_id="+id;
			sql+=" where a.sn_id="+ship_id ;			
	
		        //out.println(sql);
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
				String rs_id=rs.getString("id");
				String rs_item_id=rs.getString("item_id");
				String rs_item_code=rs.getString("item_code");
				String rs_item_name=rs.getString("name");
				String dtl_type=rs.getString("dtl_type");
				       dtl_type=(dtl_type==null)?"":dtl_type;
				String new_item_id=rs.getString("new_item_id");
				       new_item_id=(new_item_id==null)?"":new_item_id;
				String new_item_name=rs.getString("new_item_name");
				       new_item_name=(new_item_name==null)?"":new_item_name;
				String new_qty=rs.getString("new_qty");
				       new_qty=(new_qty==null)?"":new_qty;
				String qty=rs.getString("quantity");
	%>	
	<tr <%if(new_item_id.length()>0){%>bgcolor="lightyellow"<%}else{%>bgcolor="#FFFFFF"<%}%> align="center" >	

			<td><%=rs_item_code%></td>
			<td><%=rs_item_name%></td>
			<td><%=qty%></td>
			<td>
			<%
			    if(dtl_type.equals("1")){
			       out.println("错发");
			    }
			    if(dtl_type.equals("2")){
			       out.println("漏发");
			    }	
			    if(dtl_type.equals("3")){
			       out.println("其他");
			    }		    	    
			%>			
		
			</td>
			<td><%=new_item_id%></td>
			<td><%=new_item_name%></td>
			<td><%=new_qty%></td>

		</tr>
	<%}%>	
</table>



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