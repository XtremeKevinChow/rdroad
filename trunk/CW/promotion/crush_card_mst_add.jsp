<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">

function add(){
       document.forms[0].action="crush_card_mst_add_ok.jsp";
       document.forms[0].submit();
       document.forms[0].addexecl.disabled= true;
}
function checkvalid(obj) {
    if (!is_integer(obj.value)||obj.value==""||parseInt(obj.value)< 0||parseInt(obj.value)>10000) {
        alert("数量必须是正整数且不能超过1万");
        obj.select();
    }
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">充值卡制作</font>
      	</td>
   </tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>为了保证数据的准确性，请注意操作步骤。<br>
		<font color="red">
		1、如果要制作充值卡数量比较多,则需要的时间较长(最长需要15分钟左右),请耐心等候,直到系统提示你保存execl。<br>
		2、未弹出提示"保存execl"框时,不要关闭页面。<br></font>
		3、如果忘记保存execl,请与系统管理员联系。
		</td>
	</tr>
</table>
<%
    String card_lot = request.getParameter("lot_no");
    Connection conn = null;
    PreparedStatement pstmt= null;
    ResultSet rs = null;
    try {
        conn = DBManager.getConnection();
        String sql = "select * from crush_card_value ";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
%>
<form>
<table width="60%"  align="center" cellspacing="1" border="0"  noWrap >
	<tr  noWrap>
		<td class="oraTableRowHeader" >批号</td>
		<td ><%= card_lot %></td>
	</tr>
	<% while (rs.next()) {
	    String id = rs.getString("id");
	    String money = rs.getString("money");
	%>
	<tr>
	  <td class="oraTableRowHeader"> 面值: <%= money%>
      </td>
      <td>
      <input type=hidden name="type_id" value="<%= id%>">
      <input type=hidden name="money" value="<%= money%>">
      <input type=hidden name="lot_no" value="<%= card_lot%>">
      <input type=text name="quantity" value="0" onblur="checkvalid(this);">
      </td>
    </tr>
    <%}%>
</table>

<table width="60%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td align="center"><input type="button" name="addexecl" value="生成Execl" onclick="add()"></td>
	</tr>

</table>
</form>
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
