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
      String group_id=request.getParameter("group_id");
      String group_desc=request.getParameter("group_desc");
      String min_item_count=request.getParameter("min_item_count");
      
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
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
		if (confirm("ȷ��Ҫɾ����Ʒ?"))
		{
		
			document.form1.action = "mbr_gift_item_del.jsp?id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("��ѡ���¼!");
	}			        	         
}

function add() {	
		document.form1.action = "mbr_gift_item_add.jsp";
		document.form1.submit();			        	         
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ȯ��Ʒ��ϸ�б�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<table  border=0 cellspacing=1 cellpadding=1  width="800" align="center" class="OraTableRowHeader" noWrap   id="DataTable">
	<tr >
	<th align="center" width="50"></th>
	        <th align="center" width="200">������</th>
		<th align="center" width="250">��Ʒ����</th>
		<th align="center" width="100">����</th>
		<th align="center" width="100">�Ƿ��ѡ</th>
	        <th align="center" width="100">�������ٲ�Ʒ��</th>			
	
	</tr>
<%
		try{
		  conn = DBManager.getConnection();  
		  sql="select a.*,b.item_code,b.name from MBR_GIFT_BY_ITEMS a,prd_items b where a.item_id=b.item_id and a.ITEM_GROUP_ID="+group_id;
	  	  sql+=" order by a.id desc ";
	  	  //out.println(sql);
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String ITEM_GROUP_ID=rs.getString("ITEM_GROUP_ID");
		  String item_id=rs.getString("item_id");
		  String is_must=rs.getString("is_must");
		  String id=rs.getString("id");
		  String name=rs.getString("name");
		  String item_code=rs.getString("item_code");


%>      
	<tr bgcolor="#FFFFFF">
	        <td align="center"><input type="radio" name="id" value="<%=id%>"></td>
	        <td align="center"><%=group_desc%></td>
		<td align="center""><a href="/product/productView.do?itemID=<%=item_id%>" target=_blank><%=name%></a></td>
		<td align="center"><%=item_code%></td>
		<td align="center">
		<%

		if(is_must.equals("0")){
		   out.println("��");
		}
		if(is_must.equals("1")){
		   out.println("��");
		}
						
		%>
		</td>	
	        <td align="right"><%=min_item_count%></td>		

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="������Ʒ">&nbsp;	
		<input type=button onclick="javascript:operation('del')" value="ɾ����Ʒ">&nbsp;
		<input type=hidden value="<%=group_id%>" name="group_id">
		<input type=hidden value="<%=group_desc%>" name="group_desc">
		<input type=hidden value="<%=min_item_count%>" name="min_item_count">
		

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
