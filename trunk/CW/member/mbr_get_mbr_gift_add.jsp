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
      int keep_days=0;

try{
		 conn = DBManager.getConnection();
		 String sql="";
	sql="select value from s_config_keys where key='MGM_GIFT_KEEP_DAY'";

	pstmt=conn.prepareStatement(sql);
	rs=pstmt.executeQuery();
		if(rs.next()){ 	
		  keep_days=rs.getInt(1);
		}	
		rs.close();
		pstmt.close();
 %>		

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="javascript">
function f_checkData() {
        /*if(document.forms[0].item_code.value==""){
           alert("����д����");
           document.forms[0].item_code.select();	
           return false;
        }
     
	if(isNaN(document.forms[0].price.value)){
	alert('������������!');
           document.forms[0].price.select();	
           return false;
	}*/
	if(!f_checkEndDate()) {
		alert("��ʼ��Ч�������벻��ȷ");
		document.forms[0].begin_date.select();
		return false;
	}	
	if(!f_checkEndDate()) {
		alert("��ֹ��Ч�������벻��ȷ");
		document.forms[0].end_date.select();
		return false;
	}

 	document.forms[0].input.disabled = true;
}




function f_checkEndDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].end_date.value);
}
function f_checkBeginDate() {
	var regex = /^\d{4}-\d{2}-\d{2}$/;
	return regex.exec(document.forms[0].begin_date.value);
}
function initFocus(){
	//document.forms[0].item_code.select();
	return true;
}
</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0"  onload="javascript:initFocus();">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�����Ƽ���Ա��ȯ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<form action="mbr_get_mbr_gift_add_ok.jsp" method="post" onsubmit="return f_checkData();">
  

 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap   width="30%">��ȯ��</td>
    <td><select name="gift_number">
    <%  sql=" select gift_number from mbr_gift_certificates " +
					" where gift_type=5 and start_date <= trunc(sysdate) and end_date> = trunc(sysdate)" +
					" order by gift_number desc ";
		pstmt=conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()) {
		    out.println("<option value='" + rs.getString("gift_number") + "'>"+ rs.getString("gift_number") + "</option>");
		}
		
    %>
    </select>
    <font color=red>&nbsp;*</font>
    </td>
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap >��������</td>
    <td><input type=text name="keep_days" value="<%=keep_days%>"><font color=red>&nbsp;*</font></td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ��ʼ��Ч����</td><td><input type=text name="begin_date" size=30>
    <a href="javascript:show_calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
    <font color=red>*</font>
    </td>
 </tr>
 <tr> 
    <td class="OraTableRowHeader" noWrap >
    ��ֹ��Ч����</td><td><input type=text name="end_date" size=30>
    <a href="javascript:show_calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
    <font color=red>*</font>
    </td>
 </tr>

 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" ȷ�� " > 
      &nbsp; <input type="button" class="button2" value=" ȡ�� " onClick="history.back();">
      
  </tr>
</table>
</form>
<table width="90%" border=0 cellspacing=1 cellpadding=1  align="center" >
	<tr>
		<td>
			<font color=red>ע����ԱA�Ƽ���ԱB������ԱB�ɹ����һ��������ϵͳ���趨��ȯ�����ԱA����Ʒ�ݴ�ܣ��������˴��趨�������������˴��趨������������ȯ��Ч��</font>
		</td>
	</tr>
</table>
<p>&nbsp;</p>
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
