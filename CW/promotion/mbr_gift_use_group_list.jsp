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
<title>������Ա��ϵ����ϵͳ</title>
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

				if(trim(row.cells(4).innerText)=='ʧЧ'){
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
			if (confirm("ȷ��Ҫɾ����¼?")){
		
				document.form1.action = "mbr_gift_use_group_del.jsp?status=-1&id="+idvalue;
				document.form1.submit();
			}
	      }else{
	                alert("ֻ���½�������״̬�ſ���ɾ��!");
	      }
	} else {
		alert("��ѡ���¼!");
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
				if(trim(row.cells(4).innerText)=="�½�"){
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
		if (confirm("ȷ��Ҫ�޸ļ�¼?"))	{
	
			document.form1.action = "mbr_gift_use_group_modify.jsp?id="+idvalue;
			document.form1.submit();
		}
	      }else{
	                alert("ֻ���½�״̬�ſ����޸�!");
	      }
	} else {
		alert("��ѡ���¼!");
	}			        	         
}
function add() {	
		document.form1.action = "mbr_gift_use_group_add.jsp";
		document.form1.submit();			        	         
}
function qiyong(status,id) {	  
	if (confirm("���ú���ȯ���ܱ��޸ģ���ȷ��Ҫ����?")){
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա��ȯʹ�����б�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<table  border=0 cellspacing=1 cellpadding=1  width="800" align="center" class="OraTableRowHeader" noWrap   id="DataTable">
	<tr >
	<th align="center" width="100"></th>
		<th align="center" width="200">���</th>
	        <th align="center" width="200">��ȯ��</th>
		<th align="center" width="150">��ȯ����</th>
		<th align="center" width="150">״̬</th>	
	
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
		   out.println("�ιο�");
		}
		if(gift_type.equals("4")){
		   out.println("����������ȯ");
		}
		if(gift_type.equals("5")){
		   out.println("˽�е�����ȯ");
		}
										        
	        %></td>
		<td align="center">
		<%

		if(status.equals("0")){
		 %>
		�½�&nbsp;&nbsp;<input type=button onclick="javascript:qiyong(<%=status%>,<%=id%>)" value="����">
		 <%
		}
		if(status.equals("-1")){
		   out.println("<font color=red>ʧЧ</font>");
		}		
		if(status.equals("1")){
		   out.println("����");
		}
						
		%>
		</td>	

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="������¼">&nbsp;	
		<input type=button onclick="javascript:update()" value="�޸ļ�¼">&nbsp;
		<input type=button onclick="javascript:operation('del')" value="ɾ����¼">&nbsp;


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
