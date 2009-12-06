<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<%@page import="com.magic.crm.promotion.entity.*,com.magic.crm.promotion.dao.*"%>
<%
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pstmt=null;
      String condition="";
      String sql="";
      int doc_type=Integer.parseInt(request.getParameter("doc_type"));
      String pricelist_id=request.getParameter("pricelist_id");
		try{
		  conn = DBManager.getConnection();       
%>
<html>
<head>
<title>目录模版</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0">
<body bgcolor="#FFFFFF" text="#000000">

<br>

 <table width="750" border="0" cellspacing="1" cellpadding="5" summary="POM_AUC_NEGOTIATIONSHOME_S1">
     <tr>
       <td width="2%"><img src="../crmjsp/images/icon_invite.gif"></td>
     <td nowrap colspan="2"> <span class="OraHeader">下载目录数据模板
     	<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
       	<tr background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        	<td height="1" width=100% background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"></td>
      	</tr>
    	</table>
   	</td>
	</tr>
	<tr>
	<td></td>
	<td>
<span class=OraInstructionText>
       
下载导入文件说明，该文件包括成批装入模板、实例和说明。 
</span>
</td>
</tr>
<tr>
<td></td>
            <TD VALIGN="top" align="right">
                
                <a href='../crmjsp/inbound_templates/<%=doc_type%>.xls'>下载</a>
            </TD>
       </tr>
</table>



 <table width="750" border="0" cellspacing="1" cellpadding="5" summary="POM_AUC_NEGOTIATIONSHOME_S1">
     <tr>
       <td width="2%"><img src="../crmjsp/images/icon_invite.gif"></td>
     <td nowrap colspan="2"> <span class="OraHeader">上传目录数据文件
     	<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
       	<tr background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        	<td height="1" width=100% background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"></td>
      	</tr>
    	</table>
   	</td>
	</tr>
	<tr>
	<td></td>
	<td>
<span class=OraInstructionText>
       
选择XLS文件，然后按 <b>开始装入 </b> 按钮。
</span>
</td>
</tr>

<tr>
<td></td>
<td>
  <form method="post" ENCTYPE="multipart/form-data" name="loadFile" id="loadFile" action="process_data_file.jsp?doc_type=<%=request.getParameter("doc_type")%>&doc_id=<%=request.getParameter("parent_doc_id")%>">
     <table width="100%">
        <tr> <td width="15%" align="right"><img src ="../crmjsp/images/requiredfieldicon.gif" HEIGHT="12" WIDTH="10" ALT="必需字段" hspace=7>文件名</td>
             <td vAlign=top CLASS="OraGlobalButtonText" width="85%"><input name="fileName" type=file value="浏览..."></td>
        </tr>
       <tr>
         <td align="right" colspan=2>
			<input type="submit" class="button2" value="开始装入">
         </td>
       </tr>
     </table>
</form>
</td>
</tr>
	<tr>
	<td></td>
	<td>
<span class=OraInstructionText>  
查看上传的详细情况。
</span>
</td>
</tr>
<tr>
<td></td>
<td>
<table width="100%" border="0" cellspacing="1" cellpadding="5" >
   <tr>
	  <th scope="col" width="10%" class=OraTableColumnHeader ><label for="col1t2">编号</label></th>
	  <th scope="col" width="15%" class=OraTableColumnHeader><label for="col4t2">提交时间</label></th>
	  <th scope="col" width="75%" class=OraTableColumnHeader><label for="col4t2">描述</label></th>
   </tr>
   <%
   if(request.getParameter("inbound_id")!=null){
     int inbound_id = Integer.parseInt(request.getParameter("inbound_id"));

     pstmt=conn.prepareStatement("select * from bas_inbounds where id = "+ inbound_id);
     rs=pstmt.executeQuery();     
     if(rs.next()){
   %>
   <tr>
     <td class=OraTableCellText noWrap align=middle><%=rs.getInt("id")%></td>
     <td class=OraTableCellText noWrap align=middle><%=rs.getDate("create_date")%></td>
     <td class=OraTableCellText noWrap align=middle><%=rs.getString("description")%></td>
   </tr>
   <%
     }
    }
   %>
</table>	
</td>
</tr>
</table>
 <table  width="750.0" border=0 cellspacing=1 cellpadding=5>
  <tr>
    <td nowrap colspan="2"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        <tr background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <td height="1" width="100%" background="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="../crmjsp/images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"></td>
      	</tr>
    	</table>
   	</td>
	</tr>
	<tr>
	 <td nowrap colspan="2" align="right">
	 	<input type="button" name="Submit243" class=button2 value="返回"  onClick="javascript:document.location.href='CatalogDetail.jsp?pricelist_id=<%=pricelist_id%>'">
  </tr>
</table>
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