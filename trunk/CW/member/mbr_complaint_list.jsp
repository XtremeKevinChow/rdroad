<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.user.entity.User"%>
<%@page import="java.sql.*"%>
<%@page import="com.magic.crm.user.dao.UserDAO"%>
<%@page import="com.magic.crm.member.dao.*"%>

<%
//String name=request.getParameter("name");
//String id=request.getParameter("id");
//String card_id=request.getParameter("card_id");
//String type=request.getParameter("type");//0 Ͷ�� 1 ��ѯ

String iscallcenter = request.getParameter("iscallcenter");
String name="";
String id="";
String card_id="";
if (iscallcenter != null && iscallcenter.equals("1")) {
	Member mb = (Member)request.getSession().getAttribute(Constants.CURRENT_MEMBER_KEY);
	if (mb != null) {
		id = String.valueOf(mb.getID());
		card_id = mb.getCARD_ID();
		name = mb.getNAME();
	}
}
Collection listComplaint=(Collection)request.getAttribute("listComplaint");

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function load_f(){
	
	for (var i=1; i < myTable.rows.length; i ++) {
		var tr =  myTable.rows(i);
		if (tr.cells(5).innerText == "δ��� "||tr.cells(5).innerText == "���´��� ") {
			tr.bgColor = "lightyellow";
		}
	}
}
function showComplaintDetail(url) {
	location.href = url;
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ����ѯ��ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95��" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>��Ա�ţ�<a href="memberDetail.do?id=<%=id%>"><%=card_id%></a>&nbsp;
		��Ա������<%=name%>&nbsp;&nbsp;
		
		
	</tr>
</table> -->

<table width="100%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000">
	<tr >
		<th width="40"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���</th>
		
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >ʱ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >С��</th>
		<th width="*%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >״̬</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
	</tr>
<%
UserDAO userDAO=new UserDAO();
User user=new User();
String condition="";
//String type=request.getParameter("type");
	condition="  and a.mbr_id="+id;
	condition+="  order by a.cmpt_id desc";
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String sql="";
		try{
		 conn = DBManager.getConnection();   
	       		String sQuery="SELECT a.*, c.cmpt_type_name,c.type,d.cmpt_type_name as parent_name from mbr_complaint a, complaint_type c,complaint_type d "
		         +" where a.cmpt_type_id=c.cmpt_type_id and c.parent_id = d.cmpt_type_id "+condition;
	          pstmt = conn.prepareStatement(sQuery);
	          //out.println(sQuery);
	          rs = pstmt.executeQuery();
	          while (rs.next()) {	
	          String event_id=rs.getString("event_id");	
	          String cmpt_content=rs.getString("cmpt_content");
	          String creator=rs.getString("creator");
	          user.setId(rs.getString("creator"));
	          String creatorname=userDAO.find(conn,user).getUSERID();
	          String cmpt_type_name=rs.getString("cmpt_type_name");
	          String cmpt_type_id=rs.getString("cmpt_type_id");
	          String Mbr_id=rs.getString("Mbr_id");
	          String Cmpt_status=rs.getString("Cmpt_status");
	          String create_date=rs.getString("create_date");  
	          String Last_deal_date=rs.getString("Last_deal_date").substring(0,10);
	          String is_answer=rs.getString("is_answer");
	          String rs_type=rs.getString("type");
	          String Cmpt_id=rs.getString("Cmpt_id");
	          String parent_name = rs.getString("parent_name");
	          if(rs_type.equals("0")){
                 			 	
%>  
	<tr>
		<td  align=middle bgcolor="#FFFFFF"><a href=javascript:showComplaintDetail("complaintDetail.do?id=<%=id%>&tag=1&event_id=<%=event_id%>&name=<%=name%>&cmpt_id=<%=Cmpt_id%>&card_id=<%=card_id%>&type=0")><%=Cmpt_id%></a></td>
		<td  align=middle bgcolor="#FFFFFF"><%=create_date%></td>

		<td  align=middle bgcolor="#FFFFFF"><%=parent_name%></td>
		<td  align=middle bgcolor="#FFFFFF"><%=cmpt_type_name%></td>		
		<td align=middle bgcolor="#FFFFFF"><%=cmpt_content%></td>
		<td align=middle bgcolor="#FFFFFF">
		<%
		if(Cmpt_status.equals("0")){
		   out.println("δ���");
		}
		if(Cmpt_status.equals("1")){
		   out.println("�ѽ��");
		}
		if(Cmpt_status.equals("2")){
			  out.println("���´���");
		}		
		if(Cmpt_status.equals("3")){
		   out.println("�ͷ�ȷ���ѽ��");
		}				
		%></td>
		<td align=middle bgcolor="#FFFFFF"><%=creatorname%></td>	
		<td align=middle bgcolor="#FFFFFF">Ͷ��</td>	
	</tr>
	<%}else{%>
	<tr>
		<td  align=middle bgcolor="#FFFFFF"><a href=javascript:showComplaintDetail("complaintDetail.do?id=<%=id%>&tag=1&event_id=<%=event_id%>&name=<%=name%>&cmpt_id=<%=Cmpt_id%>&card_id=<%=card_id%>&type=1")><%=Cmpt_id%></a></td>
		<td  align=middle bgcolor="#FFFFFF"><%=create_date%></td>
        <td  align=middle bgcolor="#FFFFFF"><%=parent_name%></td>
		<td  align=middle bgcolor="#FFFFFF"><%=cmpt_type_name%></td>
		<td align=middle bgcolor="#FFFFFF"><%=cmpt_content%></td>
		<td align=middle bgcolor="#FFFFFF">
		<%
		if(Cmpt_status.equals("0")){
		   out.println("δ���");
		}
		if(Cmpt_status.equals("1")){
		   out.println("�ѽ��");
		}
		if(Cmpt_status.equals("2")){
			  out.println("�ٴδ���");
		}		
		if(Cmpt_status.equals("3")){
		   out.println("�ͷ�ȷ���ѽ��");
		}				
		%>
		</td>
		<td align=middle bgcolor="#FFFFFF"><%=creatorname%></td>	
		<td align=middle bgcolor="#FFFFFF">��ѯ</td>
	</tr>	
	
	<%}%>
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

</body>
</html>
