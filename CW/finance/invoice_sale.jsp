<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@page import="com.magic.crm.finance.entity.FinSales,com.magic.crm.finance.dao.*"%>
<%

DecimalFormat myformat = new DecimalFormat("###,###.00");
			String startDate=request.getParameter("startDate");
						 startDate=(startDate==null)?"":startDate.trim();
			String endDate=request.getParameter("endDate");
						 endDate=(endDate==null)?"":endDate.trim();
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

function query_f() {


if (document.forms[0].startDate.value == ""||document.forms[0].endDate.value == "")
	{
		alert("��ѡ�����ڷ�Χ");
		document.forms[0].startDate.focus();
		return;
	}	
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
      		-&gt; </font><font color="838383">���۳ɱ�������ϸ�����ܱ�</font><font color="838383"> 
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
		</td>
		<td>
			�������ڣ�
			<input type="text" name="endDate" size="10"  value="<%=endDate%>">
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btn_query" value=" ��   ѯ " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">		
		</td>
		</tr>

		
</table>

<br>
<%
		try{
		  conn = DBManager.getConnection();  
if(tag.length()>0){
%>	
<body>

<table width="2350" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
  <tr  align="center" >
    <td rowspan="3" width="100" bgcolor="#FFFFFF">��Ʒ����</td>
    <td colspan="2" rowspan="2" width="150" bgcolor="#FFFFFF">��������</td>
    <td colspan="2" rowspan="2" width="150" bgcolor="#FFFFFF">���������˻�</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF"><a href="invoice_sale_ly.jsp?&startDate=<%=startDate%>&endDate=<%=endDate%>">���ò�Ʒ����</a></td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">���ò�Ʒ����</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">���ò�Ʒ���</td>
    <td colspan="2" rowspan="2" width="150"   bgcolor="#FFFFFF">�۵��ϼ����</td>
    <td  rowspan="2" width="150"   bgcolor="#FFFFFF">�����ɱ�����</td>
 
    <td colspan="4"  width="300"   bgcolor="#FFFFFF">�̵�</td>
    <td colspan="16"  width="900"  align="center" bgcolor="#FFFFFF">�������</td>
  </tr>
  <tr  align="center" bgcolor="#FFFFFF">
  <td colspan="2" width="150" align="center">��Ӯ</td>
  <td colspan="2" width="150" align="center">�̿�</td>
    <td colspan="2" width="150" align="center">�ŵ�������</td>
    <td colspan="2" width="150" align="center">������</td>
    <td colspan="2" width="150" align="center">�۵��ϼ�</td>
    <td colspan="2" width="150" align="center">�˻��ϼ�</td>    
    <td colspan="2" width="150" align="center">�˻�����</td>
    <td colspan="2" width="150" align="center">��������</td>
    <td colspan="2" width="150" align="center">�ֹ������</td>
    <td colspan="2" width="150" align="center">�ֹ����ɱ�</td>
  </tr>
  <tr  align="center" bgcolor="#FFFFFF" >
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>    
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>
    <td>����</td>
    <td>���</td>   
    <td>����</td>
    <td>���</td>    
    <td>����</td>
    <td>���</td>    
    <td>����</td>
    <td>���</td>     
    <td>����</td>
    <td>���</td>           
  </tr>
  <%
/*
1	01	�ɹ����
2	02	ֱ�����
3	03	�������
4	04	�������
5	05	�˻����
6	11	���۳���
7	13	�˳�����
8	14	���ó���
9	15	���ó���
10	17	��������
11	20	������
12	30	�̵�
13	31	��Ӯ
14	32	�̿�
15	60	����
16	63	�˻��ϼܲ���
17	64	�۵��ϼܲ���
18	62	��������
19	65	�̵����
20	99	�ڳ�
        71      �ֹ������
        72      �ֹ����ɱ�
*/

Collection rst = new ArrayList();
FinSalesDAO fsd=new FinSalesDAO();
FinSales info=new FinSales();

rst=fsd.fin_stock_detail_list(conn,startDate,endDate);
	 

%>																																				
<%
int all_qty_11=0;
double all_amt_11=0;
int all_qty_05=0;
double all_amt_05=0;
int all_qty_06=0;
double all_amt_06=0;
int all_qty_14=0;
double all_amt_14=0;
int all_qty_15=0;
double all_amt_15=0;
int all_qty_03=0;
double all_amt_03=0;
int all_qty_31=0;
double all_amt_31=0;
int all_qty_32=0;
double all_amt_32=0;
int all_qty_62=0;
double all_amt_62=0;
int all_qty_20=0;
double all_amt_20=0;
int all_qty_63=0;
double all_amt_63=0;
int all_qty_64=0;
double all_amt_64=0;
int all_qty_66=0;
double all_amt_66=0;
int all_qty_67=0;
double all_amt_67=0;

int all_qty_70=0;
double all_amt_70=0;
double all_amt_71=0;

int all_qty_72=0;
double all_amt_72=0;
for(int i=1;i<7;i++){
int qty_11=fsd.getFin_qty(rst,String.valueOf(i),"11");
double amt_11=fsd.getFin_amt(rst,String.valueOf(i),"11");
int qty_05=fsd.getFin_qty(rst,String.valueOf(i),"05");
double amt_05=fsd.getFin_amt(rst,String.valueOf(i),"05");
int qty_06=fsd.getFin_qty(rst,String.valueOf(i),"06");
double amt_06=fsd.getFin_amt(rst,String.valueOf(i),"06");
int qty_14=fsd.getFin_qty(rst,String.valueOf(i),"14");
double amt_14=fsd.getFin_amt(rst,String.valueOf(i),"14");
int qty_15=fsd.getFin_qty(rst,String.valueOf(i),"15");
double amt_15=fsd.getFin_amt(rst,String.valueOf(i),"15");
int qty_03=fsd.getFin_qty(rst,String.valueOf(i),"03");
double amt_03=fsd.getFin_amt(rst,String.valueOf(i),"03");
int qty_31=fsd.getFin_qty(rst,String.valueOf(i),"31");
double amt_31=fsd.getFin_amt(rst,String.valueOf(i),"31");
int qty_32=fsd.getFin_qty(rst,String.valueOf(i),"32");
double amt_32=fsd.getFin_amt(rst,String.valueOf(i),"32");
int qty_62=fsd.getFin_qty(rst,String.valueOf(i),"62");
double amt_62=fsd.getFin_amt(rst,String.valueOf(i),"62");
int qty_20=fsd.getFin_qty(rst,String.valueOf(i),"20");
double amt_20=fsd.getFin_amt(rst,String.valueOf(i),"20");
int qty_63=fsd.getFin_qty(rst,String.valueOf(i),"63");
double amt_63=fsd.getFin_amt(rst,String.valueOf(i),"63");
int qty_64=fsd.getFin_qty(rst,String.valueOf(i),"64");
double amt_64=fsd.getFin_amt(rst,String.valueOf(i),"64");
int qty_66=fsd.getFin_qty(rst,String.valueOf(i),"66");
double amt_66=fsd.getFin_amt(rst,String.valueOf(i),"66");
int qty_67=fsd.getFin_qty(rst,String.valueOf(i),"67");
double amt_67=fsd.getFin_amt(rst,String.valueOf(i),"67");
int qty_70=fsd.getFin_qty(rst,String.valueOf(i),"70");
double amt_70=fsd.getFin_amt(rst,String.valueOf(i),"70");
double amt_71=fsd.getFin_amt(rst,String.valueOf(i),"71");
int qty_72=fsd.getFin_qty(rst,String.valueOf(i),"72");
double amt_72=fsd.getFin_amt(rst,String.valueOf(i),"72");
all_qty_11=all_qty_11+qty_11;
all_amt_11=all_amt_11+amt_11;
all_qty_05=all_qty_05+qty_05;
all_amt_05=all_amt_05+amt_05;
all_qty_06=all_qty_06+qty_06;
all_amt_06=all_amt_06+amt_06;
all_qty_14=all_qty_14+qty_14;
all_amt_14=all_amt_14+amt_14;
all_qty_15=all_qty_15+qty_15;
all_amt_15=all_amt_15+amt_15;
all_qty_03=all_qty_03+qty_03;
all_amt_03=all_amt_03+amt_03;
all_qty_31=all_qty_31+qty_31;
all_amt_31=all_amt_31+amt_31;
all_qty_32=all_qty_32+qty_32;
all_amt_32=all_amt_32+amt_32;
all_qty_62=all_qty_62+qty_62;
all_amt_62=all_amt_62+amt_62;
all_qty_20=all_qty_20+qty_20;
all_amt_20=all_amt_20+amt_20;
all_qty_63=all_qty_63+qty_63;
all_amt_63=all_amt_63+amt_63;
all_qty_64=all_qty_64+qty_64;
all_amt_64=all_amt_64+amt_64;
all_qty_66=all_qty_66+qty_66;
all_amt_66=all_amt_66+amt_66;
all_qty_67=all_qty_67+qty_67;
all_amt_67=all_amt_67+amt_67;
all_qty_70=all_qty_70+qty_70;
all_amt_70=all_amt_70+amt_70;
all_amt_71=all_amt_71+amt_71;

all_qty_72=all_qty_72+qty_72;
all_amt_72=all_amt_72+amt_72;


%>																																																																																															
 <tr bgcolor="#FFFFFF">
    <td width="100"  align="center">
		<a href="invoice_sale_dtl.jsp?type=<%=i%>&startDate=<%=startDate%>&endDate=<%=endDate%>">
		<%
		if(i==1){
		   out.println("ͼ��");
		}
		if(i==2){
		   out.println("Ӱ��");
		}
		if(i==3){
		   out.println("����");
		}
		if(i==4){
		   out.println("��Ϸ���");
		}
		if(i==5){
		   out.println("��Ʒ");
		}
		if(i==6){
		   out.println("����");
		}
										
		%>		
		</a>    
    </td>

    <td width="75" align="right"><%=qty_11%></td>
    <td width="75" align="right"><%=myformat.format(amt_11)%></td>
    <td width="75" align="right"><%=qty_05%></td>
    <td width="75" align="right"><%=myformat.format(amt_05)%></td>
  
    <td width="75" align="right">
    <%if(qty_14!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&startDate=<%=startDate%>&endDate=<%=endDate%>&qty_type=14"><%=qty_14%></a>
    <%}else{%>
    0
    <%}%>
    </td>
    <td width="75" align="right"><%=myformat.format(amt_14)%></td>
    <td width="75" align="right">
    <%if(qty_15!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&startDate=<%=startDate%>&endDate=<%=endDate%>&qty_type=15"><%=qty_15%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_15)%></td>
    
    <td width="75" align="right">
    <%if(qty_03!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&startDate=<%=startDate%>&endDate=<%=endDate%>&qty_type=03"><%=qty_03%></a>
    <%}else{%>
    0
    <%}%>
    </td>
    <td width="75" align="right"><%=myformat.format(amt_03)%></td>
    <td width="75" align="right">
    <%if(qty_06!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=06&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_06%></a>
    <%}else{%>
    0
    <%}%>
    </td>
    <td width="75" align="right"><%=myformat.format(amt_06)%></td>      
    <td width="75" align="right"><%=myformat.format(amt_71)%></td>
    <td width="75" align="right">
    <%if(qty_31!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=31&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_31%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_31)%></td>
    <td width="75" align="right">
    <%if(qty_32!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=32&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_32%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_32)%></td>
    <td width="75" align="right">
    <%if(qty_62!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=62&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_62%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_62)%></td>
    <td width="75" align="right">
    <%if(qty_20!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=20&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_20%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_20)%></td>
   
    <td width="75" align="right">
    <%if(qty_64!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=64&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_64%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_64)%></td> 
    <td width="75" align="right">
    <%if(qty_63!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=63&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_63%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_63)%></td>         
    <td width="75" align="right">
    <%if(qty_66!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=66&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_66%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_66)%></td>   
    <td width="75" align="right">
    <%if(qty_67!=0){%>
    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=67&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_67%></a></td>
    <%}else{%>
    0
    <%}%>
    <td width="75" align="right"><%=myformat.format(amt_67)%></td>   
   <td width="75" align="right">
   <%if(qty_70!=0){%>
   <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=70&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_70%></a>
   <%}else{%>
    0
    <%}%>
   </td>
    <td width="75" align="right"><%=myformat.format(amt_70)%></td>   
   <td width="75" align="right">

    <a href="invoice_sale_qty.jsp?type=<%=i%>&qty_type=72&startDate=<%=startDate%>&endDate=<%=endDate%>"><%=qty_72%></a></td>
  
   </td>
    <td width="75" align="right"><%=myformat.format(amt_72)%></td>                                                      
  </tr> 
<%}%>
<tr bgcolor="#FFFFFF">
    <td width="100"  align="center">�ϼ�</td>

    <td width="75" align="right"><%=all_qty_11%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_11)%></td>
    <td width="75" align="right"><%=all_qty_05%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_05)%></td>
   
    <td width="75" align="right"><%=all_qty_14%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_14)%></td>
    <td width="75" align="right"><%=all_qty_15%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_15)%></td>
    
    <td width="75" align="right"><%=all_qty_03%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_03)%></td>
    <td width="75" align="right"><%=all_qty_06%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_06)%></td>     
    <td width="75" align="right"><%=myformat.format(all_amt_71)%></td>
    <td width="75" align="right"><%=all_qty_31%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_31)%></td>
    <td width="75" align="right"><%=all_qty_32%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_32)%></td>
    <td width="75" align="right"><%=all_qty_62%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_62)%></td>
    <td width="75" align="right"><%=all_qty_20%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_20)%></td>
    
    <td width="75" align="right"><%=all_qty_64%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_64)%></td> 
    <td width="75" align="right"><%=all_qty_63%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_63)%></td>        
    <td width="75" align="right"><%=all_qty_66%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_66)%></td>   
    <td width="75" align="right"><%=all_qty_67%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_67)%></td>    
    <td width="75" align="right"><%=all_qty_70%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_70)%></td>       
    <td width="75" align="right"><%=all_qty_72%></td>
    <td width="75" align="right"><%=myformat.format(all_amt_72)%></td>                                                      
  </tr> 
<tr bgcolor="#FFFFFF">
    <td width="100"  align="center" colspan="34"><a href="invoice_sale_excel.jsp?startDate=<%=startDate%>&endDate=<%=endDate%>">����excel</a></td>
                                                 
  </tr>   
</table>



<%}%>

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

</form>
</body>
</html>
