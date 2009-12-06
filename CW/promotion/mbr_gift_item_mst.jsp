<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager"%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript">
function queryInput() {
	if(document.form.group_name.value==""){
	alert('产品组名必须填写!');
	document.form.group_name.select();
	return false;
	}

	if(!is_integer(document.form.number.value)||document.form.number.value==""||parseInt(document.form.number.value)<=0){
	alert('产品数必须是大于0的数字!');
	document.form.number.select();
	return false;
	}
	



	
	document.form.input.disabled = true;

}
function initFocus(){
	document.form.group_name.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">礼券产品组设置</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>
<form   action="mbr_gift_item_mst_ok.jsp" method="post" name="form" onsubmit="return queryInput();">
<table width="95%" align="center" border=0 cellspacing=1  class="OraTableRowHeader" noWrap >
	<tr>		
		<td bgcolor="#FFFFFF" width="100"><font color="red">*</font>组名</td>
		<td bgcolor="#FFFFFF"><input type="text"  name="group_name" ></td>
	
	</tr>
	<tr>		
		<td bgcolor="#FFFFFF" width="100">类型</td>
		<td bgcolor="#FFFFFF">
		<select name="item_group_type">
		 <option value="0">单品挂钩</option>
		 <option value="1">图书</option>
		 <option value="2">影视</option>
		 <option value="3">音乐</option>
		 <option value="4">游戏/软件</option>
		 <option value="5">礼品</option>
		 <option value="6">其他</option>
		 <option value="8">音像、礼品</option>
		
		</select>
		</td>
	
	</tr>	
	<tr>
		<td bgcolor="#FFFFFF" ><font color="red">*</font>最少产品数</td>
		<td bgcolor="#FFFFFF"><input type="text" name="number" ></td>
	</tr>	
	
	<tr>		
		<td bgcolor="#FFFFFF" colspan="2">

		<input type="submit" name="input" value="新  增">					
		</td>			
	</tr>	
</table>
</form>

</body>
</html>
