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
DecimalFormat myformat = new DecimalFormat("###,###.00");
			String startDate=request.getParameter("startDate");
						 startDate=(startDate==null)?"":startDate.trim();
			String endDate=request.getParameter("endDate");
						 endDate=(endDate==null)?"":endDate.trim();
			String invoiceNO=request.getParameter("invoiceNO");
						 invoiceNO=(invoiceNO==null)?"":invoiceNO.trim();	
			String tag=request.getParameter("tag");
						 tag=(tag==null)?"":tag.trim();						 			 						 						 
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
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function checkAll(bln, type) {
	
	var len = DataTable.rows.length;
	for (var i = 1; i < len; i ++) {
		var row = DataTable.rows(i);
		if(bln) {
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (row.getElementsByTagName("INPUT")[type].disabled == false)
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}


function query_f() {

if (document.forms[0].startDate.value == ""&&document.forms[0].endDate.value != "")
	{
		alert("��ʼ���ڲ���Ϊ��");
		document.forms[0].startDate.focus();
		return;
	}

	if (document.forms[0].endDate.value == ""&&document.forms[0].startDate.value != "")
	{
		alert("�������ڲ���Ϊ��");
		document.forms[0].endDate.focus();
		return;
	}
	if(document.forms[0].endDate.value != ""&&document.forms[0].startDate.value != ""){
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
				document.forms[0].startDate.focus();
				return;
		 }		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
				document.forms[0].endDate.focus();
				return;
		 }	
	 
	}


	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʊ�������</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
			��ʼ���ڣ�
			<input type="text" name="startDate" size="10"  value="<%=startDate%>">
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
			�������ڣ�
			<input type="text" name="endDate" size="10"  value="<%=endDate%>">
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
			��Ӧ�̺ţ�
			<input type="text" name="invoiceNO" size="16" value="<%=invoiceNO%>">	
			<input type="button" name="btn_query" value=" ��ѯ " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">
		</td>
		</tr>		
</table>
<%
if(tag.length()>0){
%>
<br>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >


<%

		try{
		 conn = DBManager.getConnection();
%>
	<tr align=center>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ӧ�̴���</th>
		<th width="35%"  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ӧ������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>Ԥ��ɱ�</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>�ɱ�����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>˰����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>Ӧ�����</th>
		
</tr>
<%

	
sql=" select d.pro_no,d.pro_name,sum(a.pur_Price*a.qty)as purPrice_amt,sum(a.dis_Amt) as s_dis_Amt ,";
sql+=" sum(a.amt) as s_amt,sum(a.total_amt-a.amt) as s_tax_amt,sum(total_amt) as total_amt ";			
sql+=" from fin_ap_dtl a  ";
      sql+=" inner join fin_ap_mst c on c.ap_id = a.ap_id";
       sql+=" inner join providers d on d.pro_no=c.pro_no "; 
      sql+=" where c.status=3";
      if(startDate.length()>0&&endDate.length()>0){
            sql+=" and c.createdate >= date'"+startDate+"'";
            sql+=" and c.createdate < date'"+endDate+"'+1";
      }
      if(invoiceNO.length()>0){
      			sql+=" and c.pro_no = '"+invoiceNO+"'";
      }
      sql+=" group by d.pro_no,d.pro_name";
    
      //out.println(sql);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			double all_purPrice_amt=0;//Ԥ��ɱ�
			double all_dis_Amt=0;//�ɱ�����
			double all_amt=0;//������
			double all_tax_amt=0;//˰����
			double all_final_amt=0;
						
			while(rs.next()){
			String s_pro_no=rs.getString("pro_no");
			String s_pro_name=rs.getString("pro_name");
			double purPrice_amt=Arith.round(rs.getDouble("purPrice_amt"),2);//Ԥ��ɱ�
			double s_dis_Amt=Arith.round(rs.getDouble("s_dis_Amt"),2);//�ɱ�����
			double s_amt=Arith.round(rs.getDouble("s_amt"),2);//������
			double s_final_amt=Arith.round(rs.getDouble("total_amt"),2);
			double s_tax_amt=Arith.round(rs.getDouble("s_tax_amt"),2);//˰����
			
			//---------------------------------------------------------------------
			all_purPrice_amt=Arith.round(all_purPrice_amt+purPrice_amt,2);//Ԥ��ɱ�
			all_dis_Amt=Arith.round(all_dis_Amt+s_dis_Amt,2);//�ɱ�����
			all_amt=Arith.round(all_amt+s_amt,2);//������
			all_tax_amt=Arith.round(all_tax_amt+s_tax_amt,2);//˰����
			all_final_amt=Arith.round(all_final_amt+s_final_amt,2);
			
			
%>

	<tr align=right>
		<td bgcolor="#FFFFFF" noWrap align="left"><a href="invoice_balance_providers_dtl.jsp?s_pro_no=<%=s_pro_no%>&endDate=<%=endDate%>&startDate=<%=startDate%>&invoiceNO=<%=invoiceNO%>"><%=s_pro_no%></a></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><a href="invoice_balance_providers_dtl.jsp?s_pro_no=<%=s_pro_no%>&endDate=<%=endDate%>&startDate=<%=startDate%>&invoiceNO=<%=invoiceNO%>"><%=s_pro_name%></a></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(purPrice_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(s_dis_Amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(s_amt)%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(s_tax_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(s_final_amt)%></td>
</tr>
<%}%>
	<tr align=center>
		<td bgcolor="#FFFFFF" noWrap colspan=2>�ϼ�</td>
		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_purPrice_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_dis_Amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_amt)%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_tax_amt)%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=myformat.format(all_final_amt)%></td>
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
<%}%>
</form>
</body>
</html>
