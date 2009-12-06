<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function setfocus(){
	document.forms[0].cardNum.focus();
}
function checkInput(){
	var form = document.forms[0];
	if(form.cardNum.value == "" || form.pass.value == ""){
		alert("必须输入销售卡号和密码！");
		form.cardNum.select();
		return false;
	}
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">销售卡抽查</font><font color="838383"> 
      	</td>
   </tr> 
</table>

<br><br>
<html:form action="/crush_CardCheck.do" method="post" onsubmit="return checkInput();">
  <table align="center" border=0 cellspacing=1 cellpadding=3 >
    <tr>
		
      <td width="80">销售卡号：</td>
      <td width="*"> 
        <input name = "cardNum" value="" type="text" size=16/>
 	
		</td>
	</tr>
	<tr>
		
      <td>销售卡密码：</td>
      <td>  
        <input name = "pass" value="" type="text" size=16/>
        
		
		</td>
	</tr>
	<tr>
		
      <td>是否废弃：</td>
      <td>  
        <input name = "isUse" value="0" type="radio" />否
        <input name = "isUse" value="4" type="radio" checked />是
		
		</td>
	</tr>
	<tr height="60">
		
      <td width="95%" colspan="2" align="center">
      	<input name="check" type="submit" value=" 抽查 ">
		<input name="cancel" type="reset" value=" 重置 ">
		</td>
	</tr>
</table>
</html:form>
 <table align="center" width=80% border=0 cellspacing=1 cellpadding=3 >
 	<tr></td>注：此功能只用作抽查制卡公司制作的销售卡卡号和密码是否完全匹配，如果选择废弃则该销售卡以后不可以被使用，否则以后还可以使用。</td></tr>
 </table>
</body>
</html>
