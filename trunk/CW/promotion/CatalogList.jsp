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
		if (confirm("ȷ��Ҫ������һ������?"))
		{
		
			document.form1.action = "Catalog.do?type=operation&price_type_id=3&typename="+typename+"&id="+idvalue;
			document.form1.submit();
		}
		
	} else {
		alert("��ѡ���¼!");
	}			        	         
}
function modify() {
	var flag = false;
	var ifdel= false
	var len = DataTable.rows.length;
	var idvalue;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
				//if(trim(row.cells(6).innerText)=="����"){
				   ifdel= true;
				//}			
                 idvalue=row.getElementsByTagName("INPUT")(0).value;
				flag = true;
				break;
			}
		}
		
		
	}
	
	if (flag == true)
	{
		if(ifdel==true){
			if (confirm("ȷ��Ҫ�޸�?"))
			{		
			document.form1.action = "Catalog.do?type=init&price_type_id=3&id="+idvalue;
			document.form1.submit();
			}
	        }else{
	                alert("ֻ��'����'״̬�ſ����޸�!");
	        }		
	} else {
		alert("��ѡ���¼!");
	}	
			        	         
}

function add() {	
		document.form1.action = "Catalog.do?type=init&price_type_id=3";
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
      		-&gt; </font><font color="838383">Ŀ¼�б�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form method="post" name="form1">
<table  border=0  width="800" align="center" id="DataTable">
	<tr >
	<th class="OraTableRowHeader" noWrap align="center" width="30"></th>
		<th align="center"  class="OraTableRowHeader" noWrap  width="100">Ŀ¼����</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="100" >��ļMSC</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="80">��ʼ����</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="80">��ֹ����</th>
		<th align="center"  class="OraTableRowHeader" noWrap width="60">״̬</th>	
			
	</tr>
<%
		try{
		  conn = DBManager.getConnection();  
		  sql="select a.id,a.name,a.effect_date,a.expired_date,a.status, a.is_valid,a.msc "
		     +" from prd_pricelists a "
	  	   +" where a.price_type_id=3 order by a.id desc ";
	  	  //out.println(sql);
	  	  
	  	  int i=0;
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  while(rs.next()){		  
		  String id=rs.getString("id");
		  String name=rs.getString("name");
		  String effect_date=rs.getString("effect_date");
		  String expired_date=rs.getString("expired_date");
		  String status=rs.getString("status");
		  String is_valid=rs.getString("is_valid");
		  String periodical_id="";//rs.getString("periodical_id");
		  String msc=rs.getString("msc");
		  String member_category_id="";//rs.getString("member_category_id");
%>      
	<tr 
	<% if (i%2==1) {%> class=OraTableCellText <% }%>
	>
	    <td   noWrap align="center"><input type="radio" name="id" value="<%=id%>"></td>
		<td   noWrap align="center"><a href="CatalogDetail.jsp?pricelist_id=<%=id%>"><%=name%></td>
		<td   noWrap align="center"><%=msc%></td>
		<td   noWrap align="center"><%=effect_date.substring(0,10)%></td>
		<td   noWrap align="center"><%=expired_date.substring(0,10)%></td>
		<td   noWrap align="center">
		<%
		if(status.equals("-10")){
		   out.println("��ɾ��");
		}
		if(status.equals("-1")){
		   out.println("��ֹ");
		}
		if(status.equals("100")){
		   out.println("�ѷ���");
		}
		if(status.equals("0")){
		   out.println("����");
		}						
		%>
		</td>	
	</tr>
	<% i++; } %>
<tr bgcolor="#FFFFFF">
		<td  class=OraTableCellText noWrap align="center" colspan="9">
		<input type=button onclick="javascript:add()" value="����Ŀ¼">&nbsp;
		<input type=button onclick="javascript:operation('release')" value="Ŀ¼����">&nbsp;		
		<input type=button onclick="javascript:operation('pause')" value="��ֹĿ¼">&nbsp;
		<input type=button onclick="javascript:operation('del')" value="ɾ��Ŀ¼">&nbsp;
		<input type=button onclick="javascript:modify()" value="�޸�Ŀ¼">&nbsp;
	
		
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
