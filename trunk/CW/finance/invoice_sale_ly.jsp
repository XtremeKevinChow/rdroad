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
						 
					 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      ResultSet rs1=null;
      PreparedStatement pstmt1=null;      
      String condition="";
      String sql="";
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
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
<form action="" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">库存商品台账明细及汇总</font><font color="838383"> 
      	</td>
   </tr>
</table>


<%
  try{
  conn = DBManager.getConnection();
%>
	
<table width="800" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
  <tr>
    <th width="150" align=middle bgcolor="#FFFFFF">日期</th>
    <th  width="150" align=middle bgcolor="#FFFFFF">领用单号</th>
    <th width="150" align=middle bgcolor="#FFFFFF">申请单号</th>
    <th width="150" align=middle bgcolor="#FFFFFF">产品类型</th>
    <th  width="100" align=middle bgcolor="#FFFFFF">领用数量</th>
    <th  width="100"  align=middle bgcolor="#FFFFFF">销售成本</th>            
  </tr>

  

  <%
  String squery="";
	squery="select distinct A.RES_NO AS RES_NO ";
	squery+=" from magic.fin_stock_detail a";
	/*squery+=" inner join jxc.sto_ck_mst  b on a.res_no=b.ck_no  and b.ck_class='L'";*/
	squery+=" where a.operation_class='14' AND A.OPERATION_DATE>=DATE '"+startDate+"' AND A.OPERATION_DATE<DATE '"+endDate+"'+1 ";
	pstmt1=conn.prepareStatement(squery);		
	rs1=pstmt1.executeQuery();  
	while(rs1.next()){
	String PUR_NO=rs1.getString("RES_NO");
			sql="select B.CK_NO,B.PUR_NO, B.WRITE_DATE,D.ID,D.NAME,SUM(OP_QTY) as qty,SUM(FACT_AMT) as amt";
			sql+=" from magic.fin_stock_detail a";
			sql+=" inner join jxc.sto_ck_mst  b on a.res_no=b.ck_no  and b.ck_class='L'";
			sql+=" INNER JOIN magic.PRD_ITEMS C ON C.ITEM_iD=A.ITEM_ID";
			sql+=" INNER JOIN magic.S_ITEM_TYPE D ON C.ITEM_TYPE=D.ID";
			sql+=" where a.operation_class='14' AND A.OPERATION_DATE>=DATE '"+startDate+"' AND A.OPERATION_DATE<DATE '"+endDate+"'+1 ";
			sql+=" and B.CK_NO='"+PUR_NO+"'";
			sql+=" GROUP BY B.CK_NO,B.PUR_NO,B.WRITE_DATE,D.ID,D.NAME";
			sql+=" ORDER BY B.CK_NO,B.PUR_NO";
						
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();  
			int i=0;
			int all_qty=0;	
			double all_amt=0;	
			while(rs.next()){
			int id=rs.getInt("ID");
			String CK_NO=rs.getString("PUR_NO");
			String name=rs.getString("name");
			
			String WRITE_DATE=rs.getString("WRITE_DATE").substring(0,10);	
			int qty=rs.getInt("qty");	
			double amt=rs.getDouble("amt");	
                        
                        
		
			/******************* 合计 ********************/
			 all_qty=all_qty+qty;	
			 all_amt=all_amt+amt;	
						
			
  %>
 
	<tr>
		<%if(i==0){%>
		    <td bgcolor="#FFFFFF" align="center"><%=WRITE_DATE%></td>
		    <td bgcolor="#FFFFFF" align="center"><%=PUR_NO%></td>
		    <td bgcolor="#FFFFFF" align="center"><%=CK_NO%></td>
		<%}else{
		%>
		    <td bgcolor="#FFFFFF" align="center" colspan="3" >&nbsp;</td>
		<%}%>	
		<td bgcolor="#FFFFFF" align="center">
			<%
			if(id==1){
			   out.println("图书");
			}
			if(id==2){
			   out.println("影视");
			}
			if(id==3){
			   out.println("音乐");
			}
			if(id==4){
			   out.println("游戏软件");
			}
			if(id==5){
			   out.println("礼品");
			}
			if(id==6){
			   out.println("其他");
			}
											
			%>    
		</td>
		<td bgcolor="#FFFFFF" align="right"><%=qty%></td>
		<td bgcolor="#FFFFFF" align="right"><%=myformat.format(amt)%></td>   
         </tr>
        
	  <%i++;}%>
	   <tr >
	   <td bgcolor="#FFFFFF" align="center"colspan="3"></td>
	    <td bgcolor="#FFFFFF" align="center">合计</td>
	    <td bgcolor="#FFFFFF" align="right"><%=all_qty%></td>
	    <td bgcolor="#FFFFFF" align="right"><%=myformat.format(all_amt)%></td>   
	  </tr>
<%}%>	  
</table>



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
			if (rs1 != null)
				try {
					rs1.close();
				} catch (Exception e) {}			
			if (pstmt1 != null)
				try {
					pstmt1.close();
				} catch (Exception e) {}				
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
%>

</form>
</body>
</html>
