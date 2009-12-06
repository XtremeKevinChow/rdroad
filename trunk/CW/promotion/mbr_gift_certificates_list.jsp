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
      String gift_number=request.getParameter("gift_number");
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
		if (confirm("确定要修改记录?"))
		{
		
			document.form1.action = "mbr_gift_certificates_update.jsp?id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("请选择记录!");
	}			        	         
}

function add() {	
		document.form1.action = "mbr_gift_certificates_add.jsp";
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
      		-&gt; </font><font color="838383">礼券设置列表</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<TABLE border=0 cellspacing=1 cellpadding=1 width="95%" >
<tr bgcolor="#FFFFFF">
		<td width="50">&nbsp;</td>
		<td>
		<input type=button onclick="javascript:add()" value="新增记录">&nbsp;	
		<input type=button onclick="javascript:operation('update')" value="修改记录">&nbsp;
		</td>	
	</tr>	
</TABLE>

<table  border=0 cellspacing=1 cellpadding=1  width="95%" align="center" class="OraTableRowHeader" noWrap   id="DataTable">
	<tr>
				  <th align="center" width="50"></th>
	        <th align="center" width="150">礼券批号</th>
	        <!--<th align="center" width="100">个人使用次数</th>
	        <th align="center" width="100">总计使用次数</th>-->
					<th align="center" width="150">类型名称</th>
	        <th align="center" width="100">抵用金额</th>
	        <th align="center" width="100">订单金额</th>
	        <th align="center" width="100">起始日期</th>
	        <th align="center" width="100">终止日期</th>
	        <!--<th align="center" width="100">注册开始日期</th>
	        <th align="center" width="100">注册结束日期</th>
	        <th align="center" width="80">仅供网上使用</th>-->

	
	
	</tr>
<%
		try{
		  conn = DBManager.getConnection();  
		  sql="select * from MBR_GIFT_CERTIFICATES ";;
	  	  sql+=" order by id desc ";
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String rs_gift_number=rs.getString("gift_number");
			//String rs_gift_desc = rs.getString("description");
			//			rs_gift_desc = (rs_gift_desc==null)? "":rs_gift_desc;
		  String person_num=rs.getString("person_num");
		  String id=rs.getString("id");
		  String amount=rs.getString("amount");

		  String gift_money=rs.getString("gift_money");
		  String order_money=rs.getString("order_money");
		  String start_date=rs.getString("start_date");
		         start_date=(start_date==null)?"":start_date.substring(0,10);
		  String end_date=rs.getString("end_date");
		  	 end_date=(end_date==null)?"":end_date.substring(0,10);
		  String gift_type=rs.getString("gift_type");
		  String member_start_date=rs.getString("member_start_date");
		         member_start_date=(member_start_date==null)?"":member_start_date.substring(0,10);
		  String member_end_date=rs.getString("member_end_date");
		  	 member_end_date=(member_end_date==null)?"":member_end_date.substring(0,10);
		  String description=rs.getString("description");
		  String is_new_member=rs.getString("is_new_member");
		  String is_old_member=rs.getString("is_old_member");
		  String is_web=rs.getString("is_web");
		  String is_member_level=rs.getString("is_member_level");
		  String product_group_id=rs.getString("product_group_id");
		  String is_money_for_order=rs.getString("is_money_for_order");

		  


%>      
	<tr bgcolor="#FFFFFF">
		<td align="center"><input type="radio" name="id" value="<%=id%>"></td>
	        <td align="center"><a   href=""  onclick="window.open('mbr_gift_certificates_detail.jsp?id=<%=rs_gift_number%>','_blank','width=600,height=200,scrollbars=0,resizable=0');return   false">

						<%=rs_gift_number%></a></td>
	        <!--<td align="right"><%=person_num%></td>
	        <td align="right"><%=amount%></td>-->
          <td align="center"">
	        <%
		if(gift_type.equals("1")){
		   out.println("入会");
		}
		if(gift_type.equals("2")){
		   out.println("普通");
		}
		if(gift_type.equals("4")){
		   out.println("公共礼券");
		}
		if(gift_type.equals("5")){
		   out.println("绑定私有礼券");
		}	
		if(gift_type.equals("6")){
		   out.println("不绑定私有礼券");
		}								        
	        %></td>	        
	        <td align="right"><%=gift_money%></td>

		<td align="right""><%=order_money%></td>
		<td align="center"><%=start_date%></td>
		<td align="center"><%=end_date%></td>

		<!--<td align="center"><%=member_start_date%></td>
		<td align="center"><%=member_end_date%></td>
		
		<td align="center">
		<%

		if(is_web.equals("1")){
		   out.println("是");
		}
		if(is_web.equals("0")){
		   out.println("否");
		}		

						
		%>
		</td>	-->

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
</form>

</body>
</html>
