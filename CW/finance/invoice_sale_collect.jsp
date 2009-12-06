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
			String cust_no=request.getParameter("cust_no");
						 cust_no=(cust_no==null)?"":cust_no.trim();	
						 
			String tag=request.getParameter("tag");
						 tag=(tag==null)?"":tag.trim();		
			String s_type=request.getParameter("s_type");
						 s_type=(s_type==null)?"":s_type.trim();							 				 			 						 						 
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<SCRIPT LANGUAGE="JavaScript">

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


	
if (document.forms[0].startDate.value == "")
	{
		alert("开始日期不能为空");
		document.forms[0].startDate.focus();
		return;
	}

	if (document.forms[0].endDate.value == "")
	{
		alert("结束日期不能为空");
		document.forms[0].endDate.focus();
		return;
	}
	if(document.forms[0].endDate.value != ""&&document.forms[0].startDate.value != ""){
		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return;
		 }		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return;
		 }	
	 
	}


	document.forms[0].btn_query.disabled = "true";
	document.forms[0].submit();
}
function getProduct(){
	openWin("cust_mst_search.jsp?","2005",700,400);
}

</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form action="invoice_sale_collect.jsp" method="POST" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">销售普通发票汇总</font><font color="838383"> 
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
			开始日期：
			<input type="text" name="startDate" size="10"  value="<%=startDate%>">
			<a href="javascript:calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		</td>
		<td>
			结束日期：
			<input type="text" name="endDate" size="10"  value="<%=endDate%>">
			<a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		</tr>

		<tr>
		<td>
		客户号：
			<input type="text" name="cust_no" size="20" value="<%=cust_no%>">
		<a href="javascript:getProduct();">
		    <img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
		</a>				
		</td>		
		<td>
			销售类型：
			<select name="s_type">
			 <option value="1"<%if(s_type.equals("1")){%>selected<%}%>>正常销售 </option>
			 <option value="2"<%if(s_type.equals("2")){%>selected<%}%>>销售退回</option>
			 <option value="3"<%if(s_type.equals("3")){%>selected<%}%>>非正常销售</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="btn_query" value=" 查   询 " onclick="query_f();">	
			<input type="hidden" name="tag" value="1">
		</td>
		</tr>
		<tr align="center">	
			<td colspan=4>
				
				
			</td>
		</tr>			
</table>

<br>
<%
if(tag.length()>0){
try{
 conn = DBManager.getConnection();
String cust_sql="select * from cust_mst where cust_no='"+cust_no+"'";
pstmt=conn.prepareStatement(cust_sql);
rs=pstmt.executeQuery();
String cust_name="";
if(rs.next()){
  cust_name=rs.getString("cust_name");
}
%>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	   <td align="left" >客户名称：<%=cust_name%></td><td align="right">日期段：<%=startDate%>至<%=endDate%></td>	
	</tr>
</table>	
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 class="OraTableRowHeader" noWrap >
	<tr>

		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>销售类别</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>图书</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>影视</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>音乐</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>游戏\软件</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>礼品</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>其他</th>
		<th width="6%"  class="OraTableRowHeader" noWrap  noWrap align=middle>合计</th>
	</tr>


<%

			
			
			sql=" select d.id,nvl(sum(f.total_amt),0) as so_amt";
				
			sql+=" from s_item_type d left outer join ";
			sql+=" ( ";
			sql+=" select b.total_amt,c.item_type from fin_ar_mst a,fin_ar_dtl b,prd_items c ";
			sql+=" where a.ar_id=b.ar_id and b.item_id=c.item_id ";
			if(startDate.length()>0&&endDate.length()>0){
			sql+=" and a.so_date>=date'"+startDate+"'";
			 sql+=" and a.so_date<date'"+endDate+"'+1 ";
			}
			sql+="  and a.status=3 ";
	
			if(cust_no.length()>0){
			sql+=" and a.custom_id='"+cust_no+"'  ";								
			}						
						
			sql+=" and a.so_type="+s_type;
						
			sql+=" ) f on d.id=f.item_type ";
			sql+=" group by d.id ";
                        //System.out.println(sql);
 
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			

      
%>	
	<tr>

		<th bgcolor="#FFFFFF" noWrap align=middle>销售收入</th>
		<%
			int i=0;
			double all_so_amt=0;
				while(rs.next()){	
			double so_amt=rs.getDouble("so_amt");
			all_so_amt=all_so_amt+so_amt;
			//销售收入小计＝销售收入+礼金折扣+配送费用
      
         
		%>
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(so_amt)%></td>
    <%
    i++;
    }
    %>
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(all_so_amt)%></td>
	</tr>	
	<%
	  String sum_sql="select sum(so_amt) as so_amt,sum(deliver_amt) as deliver_amt,sum(ar_amt) as ar_amt,sum(payed_amt) as payed_amt,sum(gift_amt) as gift_amt, sum(package_amt) as package_amt ";
	  sum_sql+=" from fin_ar_mst a,cust_mst b where a.custom_id=b.cust_no and  so_type="+s_type;
			if(startDate.length()>0&&endDate.length()>0){
			sum_sql+=" and so_date>=date'"+startDate+"'";
			 sum_sql+=" and so_date<date'"+endDate+"'+1 ";			 
			}
			if(cust_no.length()>0){
			sum_sql+=" and a.custom_id='"+cust_no+"'  ";								
			}
			sum_sql+=" and a.status=3 ";
					  
			pstmt=conn.prepareStatement(sum_sql);
			//out.println(sum_sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
			double slxj=rs.getDouble("so_amt");
			double deliver_amt=0;
			double package_amt=0;
			double gift_amt=0;
			double ar_amt=0;			
			double payed_amt=0;
			deliver_amt=rs.getDouble("deliver_amt");
			package_amt=rs.getDouble("package_amt");
			gift_amt=rs.getDouble("gift_amt");			
			ar_amt=rs.getDouble("ar_amt");			
			payed_amt=rs.getDouble("payed_amt");						
	%>	
	<tr>
		<th   bgcolor="#FFFFFF" noWrap align=middle>礼金折扣</th>
		<td bgcolor="#FFFFFF" noWrap align="center" colspan="6"></td> 
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(gift_amt)%></td>
	</tr>			
	<tr>
		<th   bgcolor="#FFFFFF" noWrap align=middle>配送费用</th>
		<td bgcolor="#FFFFFF" noWrap align="center" colspan="6"></td> 
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(deliver_amt)%></td>
	</tr>
	<tr>
		<th   bgcolor="#FFFFFF" noWrap align=middle>包装费用</th>
		<td bgcolor="#FFFFFF" noWrap align="center" colspan="6"></td> 
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(package_amt)%></td>
	</tr>
	<tr>
		<th bgcolor="#FFFFFF" noWrap align=middle>销售收入小计</th>
		<td bgcolor="#FFFFFF" noWrap align="center" colspan="6"></td> 
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(slxj+gift_amt+deliver_amt+package_amt)%></td>
	</tr>		
	<tr>
		<th bgcolor="#FFFFFF" noWrap align=middle>会员帐户支付</th>
		<td bgcolor="#FFFFFF" noWrap align="center" colspan="6"></td> 
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(payed_amt)%></td>
	</tr>		
	<tr>
		<th  bgcolor="#FFFFFF" noWrap align=middle>会员实际支付</th>
		<td bgcolor="#FFFFFF" noWrap align="center" colspan="6"></td> 
		<td bgcolor="#FFFFFF" noWrap align="center"><%=myformat.format(ar_amt)%></td>
	</tr>			
        <%}%>
	
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
	   <td align="center" ><a href="invoice_sale_collect_execl.jsp?cust_name=<%=cust_name%>&s_type=<%=s_type%>&startDate=<%=startDate%>&endDate=<%=endDate%>&cust_no=<%=cust_no%>">生成Execl</a></td>	
	</tr>
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
			 try {
				 conn.close();
			 	} catch(SQLException sqe) {}
	
		 }
%>
<%}%>
</form>
</body>
</html>
