<%@ page contentType="text/html;charset=GBK"%>

<%@page import="java.sql.Connection,java.sql.PreparedStatement,java.sql.ResultSet"%>
<%@page import="com.magic.crm.util.*"%>
<%@page import="java.util.ArrayList"%>
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
<%
	Connection conn = DBManager.getConnection();
  try {
  PreparedStatement ps;
  ResultSet rs;
  ArrayList cates = new ArrayList();
  ArrayList cates_name = new ArrayList();
  ps = conn.prepareStatement("select catalog_id,catalog_code,catalog_name from prd_item_category where parent_id=100 order by catalog_code");
  rs = ps.executeQuery();
  while (rs.next()) {
		cates.add(rs.getString("catalog_id"));
    cates_name.add(rs.getString("catalog_name"));
	}
  rs.close();
  ps.close();

  ps = conn.prepareStatement("select t1.code,t1.type_id,nvl(t2.catalog_name,t1.type_id) as name from prd_item_size t1 left join prd_item_category t2 on t1.type_id=t2.catalog_id where code=? and type_id=?");
	ps.setString(1,request.getParameter("size_code"));
  ps.setInt(2,Integer.parseInt(request.getParameter("type_id")));
  rs = ps.executeQuery();	
  if(rs.next()) {
  String type_id = rs.getString("type_id");
%>
<form action="productBase.do?type=updateSize" method="post" onsubmit="return checkInput();">
<input type=hidden name="old_size_code" value="<%= rs.getString("code") %>">
<input type=hidden name="old_type_id" value="<%= type_id %>">
<table width="100%" border="0" cellspacing=1 cellpadding=5 >
  <tr>
    <td><span class="OraHeader">更改尺码</span>
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
<th width="30%" class=OraTableRowHeader noWrap align=middle>编号<font color=red>*</font></th><td width="30%"><input type="text" name="size_code" size=30 value="<%= rs.getString("code") %>" ></td><td>&nbsp;</td>
</tr>
<tr>
<th width="30%" class=OraTableRowHeader noWrap align=middle>大类<font color=red>*</font></th><td width="30%">
<select name="type_id" >
<% for( int i=0;i<cates.size();i++) { %>
<option value="<%= cates.get(i) %>"<%if(type_id.equals(cates.get(i))) {out.println("selected");}%>><%= cates_name.get(i) %></option>
<%}%>
</select>
</td><td>&nbsp;</td>
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