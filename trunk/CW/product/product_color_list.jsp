<%@ page contentType="text/html;charset=GBK"%>

<%@page import="java.sql.*"%>
<%@ page import="com.magic.crm.util.*"%>

<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>佰明会员关系管理系统</title>
<script LANGUAGE="JavaScript">
function add_color() {
    window.location.href="product_color_add.jsp";
}
function add_size() {
    window.location.href="product_size_add.jsp";
}
function add_size2() {
    window.location.href="product_size2_add.jsp";
}

function update_color(code) {
    window.location.href="product_color_update.jsp?color_code=" + code;
}
function update_size(code,type_id) {
    window.location.href="product_size_update.jsp?size_code="+code + "&type_id=" +type_id;
}
function update_size2(code,type_id) {
    window.location.href="product_size2_update.jsp?size_code="+code + "&type_id=" +type_id;
}

function delete_size2(code,type_id) {
  if (confirm("确定删除该副尺码？")){
  	window.location.href="productBase.do?type=deleteSize2&size_code="+code + "&type_id=" +type_id;
  }
}
function delete_size(code,type_id) {
  if (confirm("确定删除主尺码？")){
  	window.location.href="productBase.do?type=deleteSize&size_code="+code + "&type_id=" +type_id;
  }
}
function delete_color(code) {
  if (confirm("确定删除该颜色？")){
  	window.location.href="productBase.do?type=deleteColor&color_code="+code;
  }
}
</script>
</head>
<body  >
<table width="95%" border="0" cellspacing="0" cellpadding="0" >
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">基础数据</font><font color="838383"> 
      	</td>
   </tr>
</table>
<form>
<input type="hidden" name="color_code">
</form>
<table width="95%" border="0" cellspacing=1 cellpadding=5 align=center>
  <tr>
    <td><span class="OraHeader">颜色列表</span>
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        <tr background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <td height="1" width=100% background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="95%" border=0 cellspacing=1 cellpadding=5 align=center>
  <tr>
    <th width="25%" class=OraTableRowHeader noWrap align=middle>编号</th>
    <th width="50%" class=OraTableRowHeader noWrap align=middle>名称</th>
    <th width="20%" class=OraTableRowHeader noWrap align=middle><input type=button class="button5" value="新增" onclick="add_color();"></th>
  </tr>
  <%
  Connection conn = DBManager.getConnection();
  int i=0;
  try {
  	PreparedStatement ps = conn.prepareStatement("select code,name from prd_item_color where code!='0' order by code");
  	ResultSet rs = ps.executeQuery();
  	while(rs.next()) {
  %>
  <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> ><td>&nbsp;<%= rs.getString("code") %></td><td>&nbsp;<%= rs.getString("name") %></td>
  <td align=center><input type="button" class="button5" value="修改" onclick="update_color('<%= rs.getString("code") %>');">
  <input type="button" class="button5" value="删除" onclick="delete_color('<%= rs.getString("code") %>')"></td>
  </tr>
  <%
    	i++; 
    } 
    rs.close();
    ps.close();
  
  %>
</table>

<table width="95%" border="0" cellspacing=1 cellpadding=5 align=center>
  <tr>
    <td><span class="OraHeader">尺码列表</span>
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        <tr background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <td height="1" width=100% background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table width="95%" border=0 cellspacing=1 cellpadding=5 align=center>
  <tr>
    <th width="25%" class=OraTableRowHeader noWrap align=middle>编号</th>
    <th width="50%" class=OraTableRowHeader noWrap align=middle>大类</th>
    <th width="20%" class=OraTableRowHeader noWrap align=middle><input type=button class="button5" value="新增" onclick="add_size();"></th>
  </tr>
  <%
  	i = 0;
  	ps = conn.prepareStatement("select t1.code,t1.type_id,nvl(t2.catalog_name,t1.type_id) as name from prd_item_size t1 left join prd_item_category t2 on t1.type_id=t2.catalog_id where t1.code<>'0' order by t2.catalog_name,t1.code ");
  	rs = ps.executeQuery();
  	while(rs.next()) {
  %>
  <tr <% if(i%2==1) { %>class=OraTableCellText<% } %> ><td><%= rs.getString("code") %></td><td><%= rs.getString("name") %></td>
  <td align=center><input type="button" class="button5" value="修改" onclick="update_size('<%= rs.getString("code") %>','<%= rs.getString("type_id") %>');">
                   <input type="button" class="button5" value="删除" onclick="delete_size('<%= rs.getString("code") %>','<%= rs.getString("type_id") %>')"></td>
  </tr>
  <% 
			i++;
		} 
  	rs.close();
  	ps.close();
		
  } finally {
    conn.close();
  }
  %>
</table>

</body>
</html>