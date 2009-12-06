<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.promotion.entity.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>

<%
Catalog c=new Catalog();
String actionurl="";
String title="";
String buttonvalue="";
c=(Catalog)request.getAttribute("c");
String price_type_id=request.getParameter("price_type_id");
if(c.getID()>0){
actionurl="/Catalog.do?type=modify";
if(price_type_id.equals("1")){
title="招募活动修改";
}
if(price_type_id.equals("3")){
title="目录修改";
}
if(price_type_id.equals("5")){
title="单页修改";
}
buttonvalue="修改";
}else{
actionurl="/Catalog.do?type=add";
if(price_type_id.equals("1")){
title="招募活动新增";
}
if(price_type_id.equals("3")){
title="目录新增";
}
if(price_type_id.equals("5")){
title="单页新增";
}
buttonvalue="提交";
}



 Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      int keep_days=0;

try{
		 conn = DBManager.getConnection();
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">
function winFocus(){
	document.forms[0].name.focus();
	return true;	
}

function addSubmit() {
	if(document.forms[0].name.value==""){
		alert('活动名称不能为空!');
		document.forms[0].name.focus();
		return false;
	}	
	if(document.forms[0].MSC_CODE.value==""){
		alert('请选择招募MSC!');
		document.forms[0].MSC_CODE.focus();
		return false;
	}
	if(document.forms[0].recruitment_type_key.value==""){
		alert('请选择招募渠道!');
		document.forms[0].recruitment_type_key.focus();
		return false;
	}	
	

		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写开始日期,并且注意你的日期是否正确!');
				document.forms[0].startDate.focus();
				return false;
		 }		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('请按格式填写结束日期,并且注意你的日期是否正确!');
				document.forms[0].endDate.focus();
				return false;
		 }	
	if (document.forms[0].entry_fee.value==""||isNaN(document.forms[0].entry_fee.value)||parseInt(document.forms[0].entry_fee.value)<0){
			alert("入会费应该为正整数！");
			document.forms[0].entry_fee.focus();
			return false;
	} 		 	 
         
}

function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000"  onload="javascript:winFocus()">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">招募活动</font><font color="838383"> 
      		-&gt; </font><font color="838383"><%=title%></font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form method="post" action="<%=actionurl%>" onsubmit="return addSubmit();">

<table  border=0 cellspacing=1 cellpadding=1  width="700" align="center" class="OraTableRowHeader" noWrap  >
	<tr bgcolor="#FFFFFF">
		<td align="right"  ><font color=red>*</font>&nbsp;活动名称</td>
		<td width="*%" align="left">
			<input type="text" name="name"  maxlength="50" value="<%=c.getName()%>">
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF">

		<td align="right" ><font color=red>*</font>&nbsp;招募MSC号</td>
		<td  align="left" ><input name="MSC_CODE" value="<%=c.getMsc()%>">		
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF">

		<td align="right" >赠送礼券</td>
		<td  align="left" >	<select name="gift_number">
			<option value="">不赠送</option>
	    <%  String sql=" select gift_number from mbr_gift_certificates " +
					" where gift_type=5 and start_date <= trunc(sysdate) and end_date> = trunc(sysdate)" +
					" order by gift_number desc ";
		pstmt=conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		String gift_number;
		while(rs.next()) {
				gift_number = rs.getString("gift_number");
		    out.print("<option value='" + gift_number + "' ");
				if(gift_number.equals(c.getGift_number())) {
					out.print(" selected ");
				}
				out.println(">"+ gift_number + "</option>");
		}
		
    %></select>
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF">

		<td align="right" ><font color=red>*</font>&nbsp;招募渠道</td>
		<td  align="left" >
		<input type="hidden" id="recruitment_type" name="recruitment_type" value="<%=c.getRecruitment_type()%>"> 
		<input id="recruitment_type_key" name="recruitment_type_key" value="<%=c.getRecruitmentName()%>"  readonly onclick="javascript:select_item('recruitment_type',catalogForm.recruitment_type,catalogForm.recruitment_type_key,recruitment_type_display);">
		<a href="javascript:select_item('recruitment_type',catalogForm.recruitment_type,catalogForm.recruitment_type_key,recruitment_type_display);"><img src="../crmjsp/images/icon_lookup.gif" border=0 align="top"><a>
		&nbsp;<span style="display:none" id="recruitment_type_display" name="recruitment_type_display" ></span>		
		</td>
	</tr>	
	

	<tr  bgcolor="#FFFFFF" >
		<td align="right" ><font color=red>*</font>&nbsp;起始日期</td>
		<td  align="left" >
			<input type="text" name="startDate" size="10"  value="<%=c.getEffect_date()%>">
			<a href="javascript:show_calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
        </tr>
        <tr  bgcolor="#FFFFFF">	
		<td align="right"  bgcolor="#FFFFFF"><font color=red>*</font>&nbsp;终止日期</td>
		<td  align="left" >
			<input type="text" name="endDate" size="10"  value="<%=c.getExpirped_date()%>">
			<a href="javascript:show_calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		
	</tr>	
	 
			<input type="hidden" name="entry_fee" value="<%=c.getEntry_fee()%>">
		
		
	</tr>			
	<tr  bgcolor="#FFFFFF">
		<td align="right" >&nbsp;描述</td>
		<td align="left">
			<textarea cols=70 rows=3 name="description" maxlength="500"><%=c.getDescription()%></textarea>
		</td>
	</tr>
	<tr height="40"  bgcolor="#FFFFFF">
		<td align="center" colspan=4>
		<input type="submit"  value="<%=buttonvalue%>" > &nbsp;
		<input type="reset" class="button2" value=" 取消 " >
		<input type="hidden" name="id" value="<%=c.getID()%>" >
		<input type="hidden" name="price_type_id" value="<%=price_type_id%>" >
	</tr>
</table>
</html:form>

</body>
</html>
<% 
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