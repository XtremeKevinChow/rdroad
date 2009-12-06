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
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>


<SCRIPT LANGUAGE="JavaScript">
<!--
function ref(){
       document.form.action="";
       document.form.submit();
}
function add(){
	if(!is_integer(document.form.number.value)||document.form.number.value==""||parseInt(document.form.number.value)<=0){
	alert('数量必须是大于0的正整数!');
	document.form.number.select();
	return false;
	}
	if(parseInt(document.form.number.value)>30000){
	alert('数量不能超过3万!');
	document.form.number.select();
	return false;
	}	
       //window.open("crush_card_add_ok.jsp?code="+document.form.code.value+"&number="+document.form.number.value);
       document.form.action="crush_card_add_ok.jsp";
       document.form.submit();
}
function insert(){

       window.open("crush_card_add_finish.jsp");
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<form action="" name="form" method="POST">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增充值卡</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>


<br>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>为了保证数据的准确性，请注意操作步骤。<br>
		<font color="red">
		1、如果要生成礼券数量比较多,则需要的时间较长(最长需要15分钟左右),请耐心等候,直到系统提示你保存execl。<br>
		2、未弹出提示"保存execl"框时,不要关闭页面。<br></font>
		3、如果忘记保存execl,请通知杨上明或帅诗颀。
		</td>
	</tr>
</table>

<table width="96%" border="0"  cellpadding="1" cellspacing="2" align="center" class="OraTableRowHeader" noWrap >
	<tr bgcolor="#FFFFFF" align="center">
		<td width="100" align="left">数量</td><td align="left"><input type=text name="number"></td>
		
		<td width="100" align="left">金额</td><td align="left">
		<select name="code">
		<%
			sql=" select * from CRUSH_CARD_VALUE order by money ";			
 			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
			String code=rs.getString("code");
			String money=rs.getString("money");
					
		%>
		  <option value="<%=code%>"><%=code%>--<%=money%></option>
		<%}%>
		</select>
		</td>

	</tr>
</table><br>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td align="center"><input type="button" value="生成Execl" onclick="add()">&nbsp;&nbsp;</td>
	</tr>

</table>


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