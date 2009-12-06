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
      		
       String status=request.getParameter("status");
      		status=(status==null)?"":status; 

        String start_date=request.getParameter("startDate");
		start_date=(start_date==null)?"":start_date;

		String end_date=request.getParameter("endDate");
		end_date=(end_date==null)?"":end_date;

       String tag=request.getParameter("tag");
      		tag=(tag==null)?"":tag;      
    
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
function ref(){
       document.form.action="";
       document.form.submit();
}

function query_f() {
	
	if (document.forms[0].startDate.value == ""&&document.forms[0].endDate.value == "")
	{
		alert("请输入起止日期");
		//document.forms[0].ship_no.focus();
		return;
	}	   
	   
	document.form.action="";
	document.form.submit();
}



//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">销售管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">补货单列表</font><font color="838383"> 
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
		<input type=text name="card_id" value="<%=card_id%>">
		</td>	
		<td width="50">发货单号</td>
		<td  bgcolor="#FFFFFF">
		<input type=text name="ship_no" value="<%=ship_no%>">	
		<td width="50">状态</td>
		<td  bgcolor="#FFFFFF">
		<select name="status">	
		  <option value="1" <%if(status.equals("1")){%>selected<%}%>>新建</option>
		  <option value="2" <%if(status.equals("2")){%>selected<%}%>>审核</option>
		  <option value="3" <%if(status.equals("3")){%>selected<%}%>>完成</option>
		</select>
		</td>
									
	</tr>	
	<tr>	
		<td width="50">开始日期</td>
		<td  bgcolor="#FFFFFF">
		<input type=text readonly name="startDate" value="<%=start_date%>">
		<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>	
		<td width="50">结束日期</td>
		<td  bgcolor="#FFFFFF">
		<input type=text readonly name="endDate" value="<%=end_date%>">	
		<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		<td width="50"></td>
		<td  bgcolor="#FFFFFF">
		&nbsp;<input type="button" name="btn_query" value=" 查 询 " onclick="query_f();">
		<input type="hidden" name="tag" value="1">
		</td>
								
	</tr>	
		
</table>
<br>
<%if(tag.equals("1")){%>
<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr bgcolor="#FFFFFF" align="center">
		<td width="50">编号</td>	
		<td width="50">会员号</td>
		<td width="60">会员姓名</td>
		<td width="80">发货单号</td>
		<td width="80">申请日期</td>
		<td width="60">事件类型</td>
		<td width="100">是否退回产品</td>
		<td width="100">是否报销邮资</td>
		<td width="60">创建人</td>
		<td width="80">创建日期</td>
		<td width="60">审批人</td>
		<td width="80">审批日期</td>
	</tr>
	<%
			sql=" select a.*,b.card_id,b.name ,c.doc_number, ";
			sql+=" (select name from org_persons where id=a.writer) as writer_name, "; 
			sql+=" (select name from org_persons where id=a.operator) as op_name ";
			sql+=" from  jxc.sto_supplement_mst a,mbr_members b,ord_shippingnotices c ";  
                        sql+=" where a.member_id=b.id  and a.ship_id=c.id and a.status="+status;			
			if(card_id.length()>0){
				sql+=" and b.card_id="+card_id;
			}
			if(ship_no.length()>0){
				sql+=" and c.doc_number='"+ship_no+"'";
			}			

			if (start_date.length() >0) {
				sql += " and a.write_date >= date'"+start_date + "' ";
			}
			if (start_date.length() >0) {
				sql += " and a.write_date <= date'"+end_date + "' + 1 ";
			}
			sql+="  order by a.id desc ";			
 			
			
		        //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String rs_id=rs.getString("id");
			String rs_member_id=rs.getString("member_id");
			String rs_card_id=rs.getString("card_id");
			String rs_name=rs.getString("name");
			String rs_ship_id=rs.getString("ship_id");
			String rs_ship_no=rs.getString("doc_number");
			String rs_require_date=rs.getString("require_date").substring(0,10);
			String rs_redelivery_type=rs.getString("redelivery_type");
			String rs_return_orgin=rs.getString("is_return_orgin");	
			String rs_is_postage=rs.getString("is_postage");
			String writer_name=rs.getString("writer_name");
			String write_date=rs.getString("write_date").substring(0,10);
			String op_name=rs.getString("op_name");
			String op_time=rs.getString("op_time").substring(0,10);
	%>	
	<tr bgcolor="#FFFFFF" align="center">	
		<td><a href="supplement_deal.jsp?id=<%=rs_id%>&ship_id=<%=rs_ship_id%>&card_id=<%=rs_card_id%>&name=<%=rs_name%>&ship_no=<%=rs_ship_no%>&status=<%=status%>"><%=rs_id%></a></td>
		<td><%=rs_card_id%></td>
		<td><%=rs_name%></td>
		<td><%=rs_ship_no%></td>
		<td><%=rs_require_date%></td>
		<td>
		<%
		    if(rs_redelivery_type.equals("1")){
		       out.println("错发");
		    }
		    if(rs_redelivery_type.equals("2")){
		       out.println("漏发");
		    }	
		    if(rs_redelivery_type.equals("3")){
		       out.println("其它");
		    }			    	    
		%>		
		</td>
		<td>
		<%
		    if(rs_return_orgin.equals("1")){
		       out.println("是");
		    }
		    if(rs_return_orgin.equals("0")){
		       out.println("否");
		    }		    
		%>		
		</td>
		<td >
		<%
		    if(rs_is_postage.equals("1")){
		       out.println("是");
		    }
		    if(rs_is_postage.equals("0")){
		       out.println("否");
		    }		    
		%>
		</td>
		<td ><%=writer_name%></td>
		<td ><%=write_date%></td>
		<td ><%=op_name%></td>
		<td ><%=op_time%></td>
	</tr>	
	<%}%>	
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