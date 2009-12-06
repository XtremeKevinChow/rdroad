<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//发票业务函数
function query_f() {
	
}
function save_f() {
	
}
function check_f() {
	if (confirm("确定审核吗?"))
	{
		os_status.innerText = "确认";
		alert("审核成功!");
	}
}
function uncheck_f() {
	if (confirm("确定弃审吗?"))
	{
		os_status.innerText = "新建";
		alert("弃审成功!");
	}
}
function account_f() {
	if (confirm("确定记帐吗?"))
	{
		if (os_status.innerText == "新建")
		{
			alert("还未确认!");
			return;
		}
		os_status.innerText = "记入台帐";
		alert("记帐成功!");
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">销售发票新增</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<TABLE>
<TR>
	<TD>
		发票号：
		<input name="invioceNO">&nbsp;&nbsp;
		<input type="button" value=" 查询 " onclick="query_f();">&nbsp;&nbsp;
		<!-- <input type="button" value=" 审核 " onclick="check_f();">&nbsp;&nbsp;
		<input type="button" value=" 弃审 " onclick="uncheck_f();">&nbsp;&nbsp;
		<input type="button" value=" 记帐 " onclick="account_f();">&nbsp;&nbsp; -->
	</TD>
</TR>
</TABLE>
<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>销售发票号：</td><td bgcolor="#FFFFFF">INVOICE000000000001</td>
	<td>客户代码：</td><td bgcolor="#FFFFFF">99</td>
	<td>客户名称：</td><td bgcolor="#FFFFFF">九久</td>
  </tr>
  <tr height="26"> 
	<td>业务类型：</td><td bgcolor="#FFFFFF">正常销售</td>
	<td>单据日期：</td><td bgcolor="#FFFFFF">2006-06-20</td>
	<td>单据状态：</td><td bgcolor="#FFFFFF" id="os_status">新建</td>
  <!-- </tr>
    <tr height="26"> 
	<td>制单人：</td><td bgcolor="#FFFFFF">朱</td>
	<td>审核人：</td><td bgcolor="#FFFFFF">刘</td>
	<td>记帐人：</td><td bgcolor="#FFFFFF">帅</td>
  </tr> -->
</table>
<br>
<table id="detail" width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>销售订单号</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品代码</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>产品名称</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>销售价格</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>销售数量</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>销售类型</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>金额</th>
	

	</tr>
	<tr>
		<td class=OraTableCellText noWrap align=middle >SALES000000000001</td>
		<td class=OraTableCellText noWrap align=middle >119775</td>
		<td class=OraTableCellText noWrap align=left >霍元甲（1DVD）</td>
		<td class=OraTableCellText noWrap align=left >6.00</td>
		<td class=OraTableCellText noWrap align=left >1</td>
		<td class=OraTableCellText noWrap align=left >正常</td>
		<td class=OraTableCellText noWrap align=right >6.00</td>
		
	</tr>
	<tr>
		<td class=OraTableCellText noWrap align=middle >SALES000000000001</td>
		<td class=OraTableCellText noWrap align=middle >120312</td>
		<td class=OraTableCellText noWrap align=left >天使迷梦（6册）</td>
		<td class=OraTableCellText noWrap align=left >45.00</td>
		<td class=OraTableCellText noWrap align=left >1</td>
		<td class=OraTableCellText noWrap align=left >正常</td>
		<td class=OraTableCellText noWrap align=right >45.00</td>
		
	</tr>
	<tr>
		<td class=OraTableCellText noWrap align=middle >SALES000000000001</td>
		<td class=OraTableCellText noWrap align=middle >119999</td>
		<td class=OraTableCellText noWrap align=left >莲花</td>
		<td class=OraTableCellText noWrap align=left >19.00</td>
		<td class=OraTableCellText noWrap align=left >1</td>
		<td class=OraTableCellText noWrap align=left >正常</td>
		<td class=OraTableCellText noWrap align=right >19.00</td>
		
	</tr>
</table>

</body>
</html>
