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
      String condition="";
      String id=request.getParameter("id");
      String sql="";
		try{
		  conn = DBManager.getConnection();  

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/popcalendar.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT Language="JavaScript">dateFormat='yyyy-mm-dd'</SCRIPT>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.MSC_CODE.value==""){
		alert('����д�����!');
	document.form.MSC_CODE.select();
	return false;
	}		
	
	if(!is_integer(document.form.maxgoods.value)||document.form.maxgoods.value==""||parseInt(document.form.maxgoods.value)<0){
		alert('����ѡ��Ʒ�������Ǵ���0��������!');
	document.form.maxgoods.select();
	return false;
	}
	if(!is_integer(document.form.mingoods.value)||document.form.mingoods.value==""||parseInt(document.form.mingoods.value)<0){
		alert('��С��ѡ��Ʒ�������Ǵ���0��������!');
	document.form.mingoods.select();
	return false;
	}	
	if(parseInt(document.form.maxgoods.value)<parseInt(document.form.mingoods.value)){
	alert('����ѡ��Ʒ��������ڵ�����С��ѡ��Ʒ��!');
	document.form.maxgoods.select();
	return false;
	}												
	document.form.input.disabled = true;
	


}
function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function initFocus(){

	return true;
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�������޸�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="recruit_activity_section_updateok.jsp" method="post" name="form" onsubmit="return queryInput();">
<%

		  sql="select * from Recruit_Activity_section where id="+id;
		
		  pstmt=conn.prepareStatement(sql);
		  rs=pstmt.executeQuery();
		  if(rs.next()){		  
		  String MSC=rs.getString("MSC");
		  String Name=rs.getString("Name");
		  String type=rs.getString("type");
		  String maxgoods=rs.getString("maxgoods");
		  String mingoods=rs.getString("mingoods");
		  String sectionImg=rs.getString("sectionImg");
		         sectionImg=(sectionImg==null)?"":sectionImg;
		  
%>
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>����������</td>
		<td bgcolor="#FFFFFF"><%=Name%>
		<input type="hidden" name="id" value="<%=id%>">
		</td>	
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>����</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="MSC_CODE" value="<%=MSC%>" readonly> 
		<input type="button" onClick="javascript:winopen('/member/queryMSCList.do','ѡ��MSC��')" value="ѡ��MSC" > 
		</td>		


	</tr>
	<tr>		

		<td bgcolor="#FFFFFF" width="150"> ����</td>
		<td bgcolor="#FFFFFF">
		<select name="type">
 
		 <option value="A"<%if(type.equals("A")){%>selected<%}%>>������Ʒ</option>	
		 <option value="B"<%if(type.equals("B")){%>selected<%}%>>�����Ʒ</option>	
		 <option value="C"<%if(type.equals("C")){%>selected<%}%>>����������Ʒ</option>
		 <option value="D" <%if(type.equals("D")){%>selected<%}%>>����������</option>
		 <option value="E" <%if(type.equals("E")){%>selected<%}%>>������������Ʒ��</option>
	 
		</select>		
		 </td>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>����ѡ��Ʒ��</td>
		<td bgcolor="#FFFFFF">
		<input type="text" name="maxgoods" value="<%=maxgoods%>">
		</td>		
	
	</tr>	

	

	<tr>		
		<td bgcolor="#FFFFFF" width="150"><font color="red">*</font>��С��ѡ��Ʒ��</td>
		<td bgcolor="#FFFFFF" ><input type="text" name="mingoods" value="<%=mingoods%>"> </td>
		<td bgcolor="#FFFFFF" width="150">����ͼƬ</td>
		<td bgcolor="#FFFFFF" ><input type="text" name="sectionImg" value="<%=sectionImg%>"></td>		
	</tr>

																	
	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="4" align="center">
		<input type="submit" name="input" value="�� �� ">					
		</td>			
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