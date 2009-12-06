<%@ page contentType="text/html;charset=GBK"%>

<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*"%>

<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>佰明会员关系管理系统</title>
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script LANGUAGE="JavaScript">
function checkInput() {
var form = document.forms[0];

	if(trim(form.size_code.value) == ""){
		alert("尺码编码不能为空！");
		form.size_code.select();
		return false;
	}


}
function return_list() {
	window.location.href="product_color_list.jsp";
}
</script>
</head>
<body >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">基础数据</font><font color="838383"> 
      	</td>
   </tr>
</table>
<form action="productBase.do?type=addSize" method="post" onsubmit="return checkInput();">
<table width="100%" border="0" cellspacing=1 cellpadding=5 >
  <tr>
    <td><span class="OraHeader">增加主尺码</span>
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
<th width="30%" class=OraTableRowHeader noWrap align=middle>编号<font color=red>*</font></th><td width="30%"><input type="text" name="size_code" size=30></td><td>&nbsp;</td>
</tr>
<tr>
<th width="30%" class=OraTableRowHeader noWrap align=middle>大类<font color=red>*</font></th><td width="30%">
<select name="type_id">
<%
	Connection conn = DBManager.getConnection();
  try {
  PreparedStatement ps = conn.prepareStatement("select catalog_id,catalog_code,catalog_name from prd_item_category where parent_id=100 order by catalog_code");
	ResultSet rs = ps.executeQuery();	
  while(rs.next()) {
%>
<option value="<%= rs.getString("catalog_id") %>"><%= rs.getString("catalog_name") %></option>
<% }
  rs.close();
  ps.close();
	} finally {
	conn.close();
}
%>
</select>
</td>
</tr>
</table>
<table width="60%" border="0" cellspacing=1 cellpadding=5 >
  <tr>
    <td align=center><span class="OraHeader"><input type="submit" class="button5" value="确定" ><input type="button" class="button5" value="返回" onclick="return_list();"></span>
     </td>
  </tr>
</table>
<form>
</body>
</html>