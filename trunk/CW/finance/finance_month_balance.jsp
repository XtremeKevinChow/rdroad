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
function monthBalanceData() {
	if (document.forms[0].periodID.value == "")
	{
		alert("请选择会计期");
		document.forms[0].periodID.focus();
		return;
	}
	if (confirm("是否真的要做月结?") == false)
	{
		return;
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
<html:form action="finDataImport.do?type=importData" method="POST">
<html:hidden property="clazzName" value="com.magic.crm.finance.dao.FinMonthDataImportDAO"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">财务管理</font><font color="838383">月结</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
			会计期：
			<html:select property="periodID">
				<option value="">-- 请选择 --</option>
				<html:optionsCollection property="periodList" value="ID" label="periodDisplay"/> 
			</html:select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btn_import" value=" 结算 " onclick="monthBalanceData();">
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
