<%@ page contentType="text/html;charset=GBK"%>

<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*"%>

<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/function.js"></script>
<script language="javascript" src="../script/default.js"></script>
<title>佰明会员关系管理系统</title>
<script LANGUAGE="JavaScript">
function checkInput() {
var form = document.forms[0];

	if(trim(form.color_code.value) == ""){
		alert("颜色编码不能为空！");
		form.color_code.select();
		return false;
	}

  if(trim(form.color_name.value) == ""){
		alert("颜色名称不能为空！");
		form.color_name.select();
		return false;
	}
}
function return_list() {
	window.location.href="product_color_list.jsp";
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">基础数据</font><font color="838383"> 
      	</td>
   </tr>
</table>
<%
	Connection conn = DBManager.getConnection();
  try {
  PreparedStatement ps = conn.prepareStatement("select code,name from prd_item_color where code=?");
	ps.setString(1,request.getParameter("color_code"));
  ResultSet rs = ps.executeQuery();	
  if(rs.next()) {
%>
<form action="productBase.do?type=updateColor" method="post" onsubmit="return checkInput();">
<input type=hidden name="old_color_code" value="<%= rs.getString("code") %>">

<table width="100%" border="0" cellspacing=1 cellpadding=5 >
  <tr>
    <td><span class="OraHeader">更改颜色</span>
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        <tr background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <td height="1" width=100% background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="100%" border=0 cellspacing=1 cellpadding=5>
<tr>
<th width="30%" class=OraTableRowHeader noWrap align=middle>编号<font color=red>*</font></th><td width="30%"><input type="text" name="color_code" size=30 value="<%= rs.getString("code") %>" readonly></td><td>&nbsp;</td>
</tr>
<tr>
<th width="30%" class=OraTableRowHeader noWrap align=middle>名称<font color=red>*</font></th><td width="30%"><input type="text" name="color_name" size=30 value="<%= rs.getString("name") %>"></td><td>&nbsp;</td>
</tr>
</table>
<table width="60%" border="0" cellspacing=1 cellpadding=5 >
  <tr>
    <td align=center><span class="OraHeader"><input type="submit" class="button5" value="确定" ><input type="button" class="button5" value="返回" onclick="return_list();"></span>
     </td>
  </tr>
</table>
<form>
<% }
	rs.close();
	ps.close();
 } finally {
	conn.close();
}
%>
</body>
</html>