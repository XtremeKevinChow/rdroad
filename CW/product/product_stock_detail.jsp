<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>

<%
DecimalFormat myformat = new DecimalFormat("#");
      String tag=request.getParameter("tag");
      tag=(tag==null)?"":tag;
      //out.println(tag);
       String item_code=request.getParameter("item_code");
      item_code=(item_code==null)?"":item_code;      
User user=new User();
user = (User)session.getAttribute("user");
             
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

<SCRIPT LANGUAGE="JavaScript">
<!--
function query_f() {




	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ʒ����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ��������ϸ</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="80%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="80%" border="0"  cellpadding="1" cellspacing="0" align="center" class="OraTableRowHeader" noWrap >
	<tr>	
		<td>���ţ�</td>
		<td bgcolor="#FFFFFF"><input type=text name="item_code" size="25" value="<%=item_code%>">
		
		</td>
		<td>�༭���ƣ�</td>
		<td  bgcolor="#FFFFFF"><%=user.getNAME()%>
		</td>			
	
		
	</tr>	
	<tr>	
		<td colspan="4" align="center" bgcolor="#FFFFFF">
		<input type="button" name="btn_query" value=" ��   ѯ " onclick="query_f();">
		<input type="hidden" name="tag" value="1">
		</td>						
	</tr>	
		
</table>
<%if(tag.equals("1")){%>
<br>
<table width="1300" align="center" border=0 cellspacing=1 cellpadding=2 class="OraTableRowHeader" noWrap >
	<tr>

      <th width="80"  bgcolor="#FFFFFF" noWrap align=middle  >����</th>
      <th width="250"  bgcolor="#FFFFFF" noWrap align=middle  >��Ʒ����</th>
      <th width="80"  bgcolor="#FFFFFF" noWrap align=middle  >���βɹ����ʱ��</th>
     <th width="100"  bgcolor="#FFFFFF" noWrap align=middle  >������</th>      
      <th width="80"  bgcolor="#FFFFFF" noWrap align=middle  >���������</th>
      <th width="80"  bgcolor="#FFFFFF" noWrap align=middle  >���˿�����</th>
      <th width="80"  bgcolor="#FFFFFF" noWrap align=middle  >���˳�����</th>      
      <th width="100"  bgcolor="#FFFFFF" noWrap align=middle  >5��ƽ������</th>
      <th width="100"  bgcolor="#FFFFFF" noWrap align=middle  >10��ƽ������</th>
      <th width="100"  bgcolor="#FFFFFF" noWrap align=middle  >15��ƽ������</th>
      <th width="120"  bgcolor="#FFFFFF" noWrap align=middle  >���㷽ʽ</th>

</tr>
<%

    
    
    
        sql=" select a.*,c.name,c.item_code as code,c.balance_method,b.write_date,b.time,b.rk_qty from prd_sell_analyze1  a"; 
	sql+=" inner join (select b.item_id,min(a.write_date)as write_date, count(a.rk_no) as time,sum(b.use_qty) as rk_qty ";
	sql+=" from jxc.sto_rk_mst a  inner join jxc.sto_rk_dtl b ";
	sql+=" on a.rk_no=b.rk_no ";
	sql+=" where a.rk_calss='P' group by b.item_id ";
	sql+=" ) b on a.item_id=b.item_id ";	
	sql+=" inner join prd_items c on a.item_id=c.item_id";

	sql+=" where   a.release_date=trunc(sysdate)"; 
	if(item_code.length()>0){
	sql+=" and c.item_code='"+item_code+"' ";
	}

	sql+=" and c.product_owner_id="+user.getId();
	
	//sql+=" order by   all_sell_quantity desc ";
        //out.println(sql);
	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
	while(rs.next()){	
		
	String name=rs.getString("name");
	String rs_item_code=rs.getString("code");
	
	int time=rs.getInt("time");//������
	int rk_qty=rs.getInt("rk_qty");//���������
	String write_date=rs.getString("write_date").substring(0,10);//���βɹ����ʱ��
	int hy_rv_qty=rs.getInt("hy_rv_qty");//���˿�����
	int pro_rv_qty=rs.getInt("pro_rv_qty");//���˳�����
	int balance_method=rs.getInt("balance_method");//���㷽ʽ
	
	double sell_last_five=rs.getDouble("sell_last_five");
	double sell_last_ten=rs.getDouble("sell_last_ten");
	double sell_last_fifteen=rs.getDouble("sell_last_fifteen");
	

%>	
	<tr align=center>

		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=rs_item_code%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=name%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=write_date%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=time%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=rk_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=hy_rv_qty%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=pro_rv_qty%></td>		
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_five%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_ten%></td>
		<td bgcolor="#FFFFFF" noWrap align="right"><%=sell_last_fifteen%></td>
		<td bgcolor="#FFFFFF" noWrap align="right">
		<%
		if(balance_method==1){
			out.println("����");
		}
		if(balance_method==2){
			out.println("�ֽ�");
		}
		if(balance_method==3){
			out.println("����");
		}
		if(balance_method==4){
			out.println("ʵ��ʵ��");
		}						
		%>
		</td>


	</tr>
<%
}
%>
	<tr align=center>

		<td bgcolor="#FFFFFF" noWrap colspan="17"><a href="product_stock_detail_execl.jsp?item_code=<%=item_code%>&personid=<%=user.getId()%>">����execl�ļ�</a></td>
	
	</tr>
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