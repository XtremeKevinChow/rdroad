<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%
String begin_date = request.getParameter("begin_date");
			 begin_date=(begin_date==null)?"":begin_date;	
String end_date = request.getParameter("end_date");
			 end_date=(end_date==null)?"":end_date;	

String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

	if(document.form.begin_date.value==""
	&&document.form.end_date.value==""	
	){
		alert('查询条件不能为空!');
		return false;;
	}else{
		var bdate = document.form.begin_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.begin_date.value!=""){
			 if(bdate==null){
					alert('请按格式填写招募起始日期,并且注意你的日期是否正确!');
					document.form.begin_date.focus();
					return false;
			 }
		 }	
		var edate = document.form.end_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		if(document.form.end_date.value!=""){
			 if(edate==null){
					alert('请按格式填写招募结束日期,并且注意你的日期是否正确!');
					document.form.end_date.focus();
					return false;
			 }
		}		 
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
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">分析报表</font><font color="838383"> 
      		-&gt; </font><font color="838383">按照年龄和招募渠道分析</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbrmsczone.jsp" method="post" name="form" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>起始日期</td>
		<td><input type="text" name="begin_date" value="<%=begin_date%>"></td>
		<td>结束日期</td>
		<td><input type="text" name="end_date" value="<%=end_date%>">(日期格式:YYYY-MM-DD)	
		<input type="submit" name="search" value="查  询"></td>		
	</tr>	
<input type="hidden" name="tag" value="1">
</table>

</form>
<% 
if(tag.equals("1")){
%>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr">
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>年龄</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>性别</td>		
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>NETSHOP</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>kf999999</td>
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>合计</td>	
		<td width="20%" class="OraTableRowHeader" noWrap  noWrap align=middle>百分比</td>		
	</tr>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String condition1="";

			if(begin_date.length()>0){
			   condition+=" and create_date > date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and create_date < date '"+end_date+"'+1";
			}
		try{
		 conn = DBManager.getConnection();
		 String totalsql="select count(1) from mbr_members where (msc_code='NETSHOP' or msc_code='kf999999') "+condition;
			pstmt=conn.prepareStatement(totalsql);						
			rs=pstmt.executeQuery();
			rs.next();
			int totalcount=rs.getInt(1);
			rs.close();
			pstmt.close();
			
						
			String sql[]= new String[6];
			int ie_count_m=0;
			int te_count_m=0;
			int ie_count_f=0;
			int te_count_f=0;
			int sum_ie_count_m=0;
			int sum_te_count_m=0;
			int sum_ie_count_f=0;
			int sum_te_count_f=0;			
	
			String age[]= new String[6];

			age[0]="<15";
			age[1]="15-20";
			age[2]="20-25";
			age[3]="25-30";
			age[4]="30-48";
			age[5]=">=48";			
		
			for(int i=0;i<6;i++){ 
		        if(i==0){
		        	condition1=" and months_between(sysdate,birthday)/12<15 ";		        
		        }
		        if(i==1){
		           	condition1=" and months_between(sysdate,birthday)/12>=15 and months_between(sysdate,birthday)/12<20 ";
			}
		        if(i==2){
				condition1=" and months_between(sysdate,birthday)/12>=20 and months_between(sysdate,birthday)/12<25 ";		        
		        }
		        if(i==3){
		        	condition1=" and months_between(sysdate,birthday)/12>=25 and months_between(sysdate,birthday)/12<30 ";
		         }		        		        		        
		        if(i==4){
		        	condition1=" and months_between(sysdate,birthday)/12>=30 and months_between(sysdate,birthday)/12<48 ";		        
		        }
		        if(i==5){
		            	condition1=" and months_between(sysdate,birthday)/12>=48";
			}			
                        sql[i]="";
			sql[i]+= " select a.id,b.id,c.id,d.id from ";
			sql[i]+=" (select count(1) as id  from mbr_members where  msc_code='NETSHOP' "+condition1+" and gender='M'"+condition+") a,";
			sql[i]+=" (select count(1) as id  from mbr_members where  msc_code='kf999999'"+condition1+"  and gender='M'"+condition+") b,";
			sql[i]+=" (select count(1) as id  from mbr_members where  msc_code='NETSHOP'"+condition1+"  and gender='F'"+condition+") c,";
			sql[i]+=" (select count(1) as id  from mbr_members where  msc_code='kf999999'"+condition1+"  and gender='F' "+condition+") d ";

			pstmt=conn.prepareStatement(sql[i]);						
			rs=pstmt.executeQuery();
										
				if(rs.next()){ 
				ie_count_m=rs.getInt(1);
				te_count_m=rs.getInt(2);
				ie_count_f=rs.getInt(3);
				te_count_f=rs.getInt(4);
			sum_ie_count_m=sum_ie_count_m+ie_count_m;
			sum_te_count_m=sum_te_count_m+te_count_m;
			sum_ie_count_f=sum_ie_count_f+ie_count_f;
			sum_te_count_f=sum_te_count_f+te_count_f;				

%>
	<tr align="center">
		<td bgcolor="#FFFFFF" rowspan="3"><%=age[i]%></td>
		<td bgcolor="#FFFFFF">男</td>
		<td bgcolor="#FFFFFF"><%=ie_count_m%></td>
		<td bgcolor="#FFFFFF"><%=te_count_m%></td>
		<td bgcolor="#FFFFFF"><%=ie_count_m+te_count_m%></td>
		<td bgcolor="#FFFFFF"><%=Math.round((ie_count_m+te_count_m)*10000.0/totalcount)/100.0%>%</td>		
	</tr>
	<tr align="center">

		<td bgcolor="#FFFFFF">女</td>
		<td bgcolor="#FFFFFF"><%=ie_count_f%></td>
		<td bgcolor="#FFFFFF"><%=te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=ie_count_f+te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=Math.round((ie_count_f+te_count_f)*10000.0/totalcount)/100.0%>%</td>		
	</tr>
	<tr align="center">

		<td bgcolor="#FFFFFF">合计</td>
		<td bgcolor="#FFFFFF"><%=ie_count_m+ie_count_f%></td>
		<td bgcolor="#FFFFFF"><%=te_count_m+te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=ie_count_m+te_count_m+ie_count_f+te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=Math.round((ie_count_m+te_count_m+ie_count_f+te_count_f)*10000.0/totalcount)/100.0%>%</td>		
	</tr>		
	
<%					
	
	
	} //if(rs.next())
	rs.close();
	pstmt.close();
        }//for(int i=0)
%>
	<tr align="center">
		<td bgcolor="#FFFFFF" rowspan="4">总计</td>
		<td bgcolor="#FFFFFF">男</td>
		<td bgcolor="#FFFFFF"><%=sum_ie_count_m%></td>
		<td bgcolor="#FFFFFF"><%=sum_te_count_m%></td>
		<td bgcolor="#FFFFFF"><%=sum_ie_count_m+sum_te_count_m%></td>
		<td bgcolor="#FFFFFF"><%=Math.round((sum_ie_count_m+sum_te_count_m)*10000.0/totalcount)/100.0%>%</td>		
	</tr>
	<tr align="center">

		<td bgcolor="#FFFFFF">女</td>
		<td bgcolor="#FFFFFF"><%=sum_ie_count_f%></td>
		<td bgcolor="#FFFFFF"><%=sum_te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=sum_ie_count_f+sum_te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=Math.round((sum_ie_count_f+sum_te_count_f)*10000.0/totalcount)/100.0%>%</td>		
	</tr>
	<tr align="center">

		<td bgcolor="#FFFFFF">合计</td>
		<td bgcolor="#FFFFFF"><%=sum_ie_count_m+sum_ie_count_f%></td>
		<td bgcolor="#FFFFFF"><%=sum_te_count_m+sum_te_count_f%></td>
		<td bgcolor="#FFFFFF"><%=sum_ie_count_m+sum_te_count_m+sum_ie_count_f+sum_te_count_f%></td>
		<td bgcolor="#FFFFFF">100%</td>		
	</tr>
	<tr align="center">

		<td bgcolor="#FFFFFF">百分比</td>
		<td bgcolor="#FFFFFF"><%=Math.round((sum_ie_count_m+sum_ie_count_f)*10000.0/totalcount)/100.0%>%</td>
		<td bgcolor="#FFFFFF"><%=Math.round((sum_te_count_m+sum_te_count_f)*10000.0/totalcount)/100.0%>%</td>
		<td bgcolor="#FFFFFF">100%</td>
		<td bgcolor="#FFFFFF">&nbsp;</td>		
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

}%>
</body>
</html>
