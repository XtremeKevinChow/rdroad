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
<title>������Ա��ϵ����ϵͳ</title>
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
    
				if(trim(row.cells(4).innerText)=="ʧЧ"){
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
		if (confirm("ȷ��Ҫɾ����Ʒ��?"))
		{		
			document.form1.action = "mbr_gift_item_mst_del.jsp?status=-1&id="+idvalue;
			document.form1.submit();
		}
	      }else{
	                alert("ֻ���½�������״̬�ſ���ɾ��!");
	      }
	} else {
		alert("��ѡ���¼!");
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
				if(trim(row.cells(4).innerText)=="�½�"){
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
			if (confirm("ȷ��Ҫ�޸Ĳ�Ʒ��?"))
			{		
				document.form1.action = "mbr_gift_item_mst_modify.jsp?id="+idvalue;
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
		document.form1.action = "mbr_gift_item_mst.jsp";
		document.form1.submit();			        	         
}
function checkstatus() {	  
				        	         
}
function qiyong(status,id) {	  
	if (confirm("���ú��Ʒ�鲻�ܱ��޸ģ���ȷ��Ҫ����?")){
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ���б�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<table  border=0 cellspacing=1 cellpadding=1  width="500" align="center" class="OraTableRowHeader" noWrap   id="DataTable">
	<tr >
	<th align="center" width="50"></th>
		<th align="center" width="300">������</th>
		<th align="center" width="100">����</th>
		<th align="center" width="100">���ٲ�Ʒ��</th>
		<th align="center" width="150">״̬</th>	
	
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
	          out.println("��Ʒ�ҹ�");
	        }
	        if(itemgroup_type.equals("1")){
	          out.println("ͼ��");
	        }
	        if(itemgroup_type.equals("2")){
	          out.println("Ӱ��");
	        }
	        if(itemgroup_type.equals("3")){
	          out.println("����");
	        }
	        if(itemgroup_type.equals("4")){
	          out.println("��Ϸ/���");
	        }
	        if(itemgroup_type.equals("5")){
	          out.println("��Ʒ");
	        }
	        if(itemgroup_type.equals("6")){
	          out.println("����");
	        }
			if(itemgroup_type.equals("8")){
	          out.println("������Ʒ");
	        }
	        %>
	        </td>
        
		
		<td align="right"><%=MIN_ITEM_COUNT%></td>
		<td align="center">
		<%
		if(status.equals("-1")){
		%>
<font color=red>ʧЧ</font>
		    <%
		}
		if(status.equals("1")){
		   out.println("����");
		}
		if(status.equals("0")){
		 %>
		�½�&nbsp;&nbsp;<input type=button onclick="javascript:qiyong(<%=status%>,<%=ITEM_GROUP_ID%>)" value="����">
		 <%
		}						
		%>
		</td>	

	</tr>
	<%}//while%>
<tr bgcolor="#FFFFFF">
		<td align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="������Ʒ��">&nbsp;	
		<input type=button onclick="javascript:operation('del')" value="ɾ����Ʒ��">&nbsp;
		<input type=button onclick="javascript:modify()" value="�޸Ĳ�Ʒ��">&nbsp;
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
