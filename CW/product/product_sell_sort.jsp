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
DecimalFormat myformat = new DecimalFormat("#");
      String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag;
      String sort_num=request.getParameter("sort_num");
      sort_num=(sort_num==null)?"":sort_num;
      String release_date=request.getParameter("release_date");
      release_date=(release_date==null)?"":release_date;      
String method = request.getMethod();    
System.out.println(method);
      
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
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/sortTable.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--


function query_f() {
	var sdate = document.form.release_date.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
	if(sdate==null){
	alert('�밴��ʽ��д����!');
	document.form.release_date.select();
	return false;
	}
if(document.form.sort_num.value==""){
	alert('�������ֱ����Ǵ���0С��1000��������!');
	document.form.sort_num.select();
	return false;
}else{
	if(!is_integer(document.form.sort_num.value)||parseInt(document.form.sort_num.value)<=0||parseInt(document.form.sort_num.value)>1000){
		alert('�������ֱ����Ǵ���0С��1000��������!');
	document.form.sort_num.select();
	return false;
	}
}


	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}


function load() {
	if("<%=method%>" == "POST") {
		loadSort(DataTable);
	}
	

}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load();">
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ�������з���</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="0" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td>���ڣ�</td>
		<td bgcolor="#FFFFFF"><input type=text name="release_date" size="25" value="<%=release_date%>">
		<a href="javascript:calendar(form.release_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>(��ʽ:YYYY-MM-DD)
		</td>	
		<td width="150">�������֣�</td>
		<td  bgcolor="#FFFFFF"><input type=text name="sort_num" size="20" value="<%=sort_num%>">			
	
		&nbsp;<input type="button" name="btn_query" value=" ��   ѯ " onclick="query_f();">
		<input type="hidden" name="tag" value="1">
		</td>						
	</tr>	
		
</table>
<%if(tag.equals("1")){%>
<br>
<table width="800" align="center" border=0 cellspacing=1 cellpadding=2 class="OraTableRowHeader" noWrap  id="DataTable">
	<tr>

      <td width="100"  bgcolor="#FFFFFF" noWrap align=middle  sort='false'>����</td>
      <td width="250"  bgcolor="#FFFFFF" noWrap align=middle  sort='false'>��Ʒ����</td>
      <td width="90"  bgcolor="#FFFFFF" noWrap align=middle   >��̨�»�Ա����</td>  
      <td width="90"  bgcolor="#FFFFFF" noWrap align=middle   >��̨�ϻ�Ա����</td> 
      <td width="90"  bgcolor="#FFFFFF" noWrap align=middle  >��̨��������</td>      
      <td width="90"  bgcolor="#FFFFFF" noWrap align=middle   >��;��</td> 
      <td width="90"  bgcolor="#FFFFFF" noWrap align=middle  >������</td>     
</tr>

<%

    
    
    
        sql=" select * from (select a.*,c.name,c.item_code as code,d.use_qty-d.frozen_qty as qty from prd_sell_analyze1  a"; 
	sql+=" inner join prd_items c on a.item_id=c.item_id";
	sql+=" inner join jxc.sto_stock  d on a.item_id=d.item_id and d.sto_no='000'";
	sql+=" where a.release_date=date'"+release_date+"' ";
	sql+=" order by  (old_sell_quantity_b+new_sell_quantity_b) desc ) where rownum<="+sort_num;

        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	int i=0;
	while(rs.next()){	
		
	String name=rs.getString("name");
	String item_code=rs.getString("code");

	int all_sell_quantity=rs.getInt("old_sell_quantity")+rs.getInt("new_sell_quantity");//������������_ǰ̨
	int all_sell_quantity_b=rs.getInt("old_sell_quantity_b")+rs.getInt("new_sell_quantity_b");//��������������̨
	int old_sell_quantity_b=rs.getInt("old_sell_quantity_b");//��̨�ϻ�Ա����
	int new_sell_quantity_b=rs.getInt("new_sell_quantity_b");//��̨�»�Ա����
	int afloat_quantity=rs.getInt("afloat_quantity"); //ȡ��;��
	int available_quantity=rs.getInt("qty"); //ȡ��ǰ���ÿ��

			

%>	
	<tr align=center>

		
		<td bgcolor="#FFFFFF" noWrap align="left"><%=item_code%></td>
		<td bgcolor="#FFFFFF" noWrap align="left"><%=name%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=new_sell_quantity_b%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=old_sell_quantity_b%></td>				
		<td bgcolor="#FFFFFF" noWrap align="right"><%=all_sell_quantity_b%></td>	
		<td bgcolor="#FFFFFF" noWrap align="right"><%=afloat_quantity%></td>				
		<td bgcolor="#FFFFFF" noWrap align="right"><%=available_quantity%></td>	


	</tr>
	<%
	i++;}
	%>
<input type="hidden" name="inum" value="<%=i%>">
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