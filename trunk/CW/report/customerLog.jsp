<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
Connection conn=null;
ResultSet rs=null;
PreparedStatement pstmt=null;
String begin_date = request.getParameter("begin_date");
       begin_date=(begin_date==null)?"":begin_date;	
String end_date = request.getParameter("end_date");
       end_date=(end_date==null)?"":end_date;	

String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;
try{
	conn = DBManager.getConnection();
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
	if(document.form.begin_date.value==""&&document.form.end_date.value==""){
	alert('��ѯ��������Ϊ��!');
	return false;;
	}
	document.form.search.disabled = true;
	document.form.submit();
}
function initFocus(){
	document.form.begin_date.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ͷ�������־</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="customerLog.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>��ʼ����</td>
		<td><input type="text" name="begin_date" value="<%=begin_date%>"></td>
		<td>��������</td>
		<td>
		<input type="text" name="end_date" value="<%=end_date%>">
		<input type="submit" name="search" value="��  ѯ">
		</td>     		
	</tr>		
				
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
	if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="16%" class="OraTableRowHeader" noWrap  noWrap align=middle>������</td>
		<td width="16%" class="OraTableRowHeader" noWrap  noWrap align=middle>�ֹ��ӿ�</td>
		<td width="16%" class="OraTableRowHeader" noWrap  noWrap align=middle>�ֹ�ɾ��</td>		
		<td width="16%" class="OraTableRowHeader" noWrap  noWrap align=middle>�ֹ�����Ʒ</td>
		<td width="16%" class="OraTableRowHeader" noWrap  noWrap align=middle>�ֹ�ɾ��Ʒ</td>
		<td width="" class="OraTableRowHeader" noWrap  noWrap align=middle>С��</td>		
	</tr>
<%

      String condition="";
      String countsql="";
			if(begin_date.length()>0){
			   condition+=" and mbr_events.event_date >= date '"+begin_date+"'";
			   
			}
			if(end_date.length()>0){
			   condition+=" and mbr_events.event_date < date '"+end_date+"'+1";
			}     

						 
			countsql="select org_persons.name,org_persons.id,addgift,delgift,addcard,delcard ";
			countsql+=" from org_persons ";
			countsql+=" left outer join ";
			countsql+=" (";
			countsql+=" select count(mbr_events.id) addgift, mbr_events.operator_id from mbr_events ";
			countsql+=" where mbr_events.event_type=3001"+condition;
			countsql+=" group by mbr_events.operator_id";
			countsql+=" ) a";
			countsql+=" on org_persons.id=a.operator_id";
			countsql+=" left outer join ";
			countsql+=" (";
			countsql+=" select count(mbr_events.id) delgift, mbr_events.operator_id from mbr_events ";
			countsql+=" where mbr_events.event_type=3002"+condition;
			countsql+=" group by mbr_events.operator_id";
			countsql+=" ) b";
			countsql+=" on org_persons.id=b.operator_id";
			countsql+=" left outer join ";
			countsql+=" (";
			countsql+=" select count(mbr_events.id) addcard, mbr_events.operator_id from mbr_events ";
			countsql+=" where mbr_events.event_type=3003"+condition;
			countsql+=" group by mbr_events.operator_id";
			countsql+=" ) c";
			countsql+=" on org_persons.id=c.operator_id";
			countsql+=" left outer join ";
			countsql+=" (";
			countsql+=" select count(mbr_events.id) delcard, mbr_events.operator_id from mbr_events ";
			countsql+=" where mbr_events.event_type=3004"+condition;
			countsql+=" group by mbr_events.operator_id";
			countsql+=" ) d";
			countsql+=" on org_persons.id=d.operator_id";	
			countsql+=" where org_persons.department_id=2 and org_persons.status=0 ";
			countsql+="order by org_persons.name";								
        //out.println(countsql);
	pstmt=conn.prepareStatement(countsql);
	rs=pstmt.executeQuery();
							
	while(rs.next()){ 
	String mb_name =rs.getString("name");
	String operator_id=rs.getString("id");
	int countaddcard=rs.getInt("addcard");
	int countaddgift=rs.getInt("addgift");
	int countdelgift=rs.getInt("delgift");
	int countdelcard=rs.getInt("delcard");
	



%>
	<tr align="center">
		<td bgcolor="#FFFFFF">
		<a href="customerLog_detail.jsp?person_id=<%=operator_id%>&begin_date=<%=begin_date%>&end_date=<%=end_date%>&name=<%=mb_name%>" target=_blank>
		<%=mb_name%></a></td>
		<td bgcolor="#FFFFFF"><%=countaddcard%></td>
		<td bgcolor="#FFFFFF"><%=countdelcard%></td>
		<td bgcolor="#FFFFFF"><%=countaddgift%></td>
		<td bgcolor="#FFFFFF"><%=countdelgift%></td>
		<td bgcolor="#FFFFFF"><%=countdelgift+countaddgift+countdelcard+countaddcard%></td>
	</tr>
	
<%					
	
	
	} 

%>

</table>
<%
}//if(tag)
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
</body>
</html>
