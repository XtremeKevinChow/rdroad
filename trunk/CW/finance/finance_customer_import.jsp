<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/progressBar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function importCustomer() {
	
	if (document.forms[0].typeID.value == "0" || document.forms[0].typeID.value == "")
	{
		document.forms[0].typeID.focus();
		alert("请选择类型");
		return;
	}
	if (document.forms[0].typeID.value == "4")
	{
		document.forms[0].action = "./customer.do?type=importOrgMembers";
	}
	if (document.forms[0].typeID.value == "5")
	{
		document.forms[0].action = "./customer.do?type=importProviders";
	}
	
	document.forms[0].btn_import.disabled = true;

	var waitingInfo = document.getElementById(getNetuiTagName("waitingInfo"));
    waitingInfo.style.display = ""; 
    progress_update(); 

	document.forms[0].submit();
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
      		-&gt; </font><font color="838383">客户导入</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<html:form action="/customer.do?type=importOrgMembers" method="POST">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
			类型：
			<html:select property="typeID">
				<option value="">-- 请选择 --</option>
				<html:optionsCollection name="customerForm" property="customerTypeList" value="typeID" label="typeDesc"/> 
			</html:select>&nbsp;&nbsp;
			<input type="button" name="btn_import" value=" 导入 " onclick="importCustomer();">
		</td>
	</tr>
</table>
<br>
<br>
<span id="waitingInfo" style="display:none">
<table align="center"><tr><td>
正在处理数据, 请稍候......
<div style="font-size:2pt;padding:2px;border:solid black 1px">
<span id="progress1">&nbsp; &nbsp;</span>
<span id="progress2">&nbsp; &nbsp;</span>
<span id="progress3">&nbsp; &nbsp;</span>
<span id="progress4">&nbsp; &nbsp;</span>
<span id="progress5">&nbsp; &nbsp;</span>
<span id="progress6">&nbsp; &nbsp;</span>
<span id="progress7">&nbsp; &nbsp;</span>
<span id="progress8">&nbsp; &nbsp;</span>
<span id="progress9">&nbsp; &nbsp;</span>
<span id="progress10">&nbsp; &nbsp;</span>
<span id="progress11">&nbsp; &nbsp;</span>
<span id="progress12">&nbsp; &nbsp;</span>
<span id="progress13">&nbsp; &nbsp;</span>
<span id="progress14">&nbsp; &nbsp;</span>
<span id="progress15">&nbsp; &nbsp;</span>
</div>
</td></tr></table>
</span>
</html:form>

</body>
</html>
