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
      		-&gt; </font><font color="838383">会员地域分布</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbrzone.jsp" method="post" name="form" onsubmit="return querySubmit();">
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
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle>省份</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>金卡会员</td>		
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>比例</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>银卡会员</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>比例</td>	
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>普通会员</td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle>比例</td>				
	</tr>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";

			if(begin_date.length()>0){
			   condition+=" and create_date >= date '"+begin_date+"'";
			}
			if(end_date.length()>0){
			   condition+=" and create_date < date '"+end_date+"'+1";
			}
		try{
		conn = DBManager.getConnection();		 		 
		String sql="";
		String sql1="select a.id,b.id,c.id from ";
		sql1+=" (select count(1) as id from mbr_members where level_id=3 "+condition+") a,";
		sql1+=" (select count(1) as id from mbr_members where level_id=2 "+condition+") b,";
		sql1+=" (select count(1) as id from mbr_members where level_id=1 "+condition+")c";
		//out.println(sql1);
			pstmt=conn.prepareStatement(sql1);
			rs=pstmt.executeQuery();
			rs.next();
			int all_mcount1=rs.getInt(1);
			int all_mcount2=rs.getInt(2);
			int all_mcount3=rs.getInt(3);			
			rs.close();
			pstmt.close();				
//System.out.println(all_mcount3);
		sql+=" select t1.province,t1.id,t2.id,t3.id ";
		sql+="  from ";
		sql+=" (select count(a.id) as id ,b.province from ";
		sql+="  (select a.id,a.postcode from mbr_members a where a.level_id=3 "+condition+") a ";
		sql+="   right outer join s_postcode b on substr(a.postcode,0,4)=b.postcode group by b.province";
		sql+=" ) t1,";
		sql+=" (select count(a.id) as id,b.province from ";
		sql+="   (select a.id,a.postcode from mbr_members a where a.level_id=2 "+condition+") a ";
		sql+="   right outer join s_postcode b on substr(a.postcode,0,4)=b.postcode group by b.province";
		sql+=" ) t2,";
		sql+=" (select count(a.id) as id,b.province from ";
		sql+="   (select a.id,a.postcode from mbr_members a where  a.level_id=1 "+condition+") a ";
		sql+="   right outer join s_postcode b on substr(a.postcode,0,4)=b.postcode group by b.province";
		sql+=" ) t3";
		sql+=" where t1.province=t2.province and t3.province=t2.province";
		
   // out.println(sql);
			pstmt=conn.prepareStatement(sql);

			rs=pstmt.executeQuery();
			
			
	
				while(rs.next()){ 
				String province=rs.getString("province");
				int mcount1=rs.getInt(2);
				int mcount2=rs.getInt(3);
				int mcount3=rs.getInt(4);
				



%>
	<tr align="center">
		<td width="10%" bgcolor="#FFFFFF"><%=province%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=mcount1%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(mcount1*10000.0/all_mcount1)/100.0%>%</td>		
		<td width="15%" bgcolor="#FFFFFF"><%=mcount2%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(mcount2*10000.0/all_mcount2)/100.0%>%</td>		
		<td width="15%" bgcolor="#FFFFFF"><%=mcount3%></td>
		<td width="15%" bgcolor="#FFFFFF"><%=Math.round(mcount3*10000.0/all_mcount3)/100.0%>%</td>		

	</tr>
	
<%					
	
	
	} 

%>

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
