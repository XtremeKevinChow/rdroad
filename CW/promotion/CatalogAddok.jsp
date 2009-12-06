<%@ page contentType="text/html;charset=GBK"%>
<%
String price_type_id = request.getParameter("price_type_id");

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">促销管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">新增目录成功</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<p>
<table  border=0 cellspacing=1 cellpadding=1  width="700" align="center" class="OraTableRowHeader" noWrap   >
	<tr bgcolor="#FFFFFF">
		<td align="center"  >
		<font color=red>操作成功&nbsp;
		<%if(price_type_id.equals("1")){%>
		<a href="member_active_list.jsp">招募活动列表</a>
		<%}%>
		<%if(price_type_id.equals("3")){%>
		<a href="CatalogList.jsp">目录列表</a>
		<%}%>		
		</font></td>		
	</tr>
	
</table>


</body>
</html>
