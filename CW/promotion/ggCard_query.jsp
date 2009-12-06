<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.promotion.form.GGCardForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
/*
    response.setHeader("expires","0");
    response.setHeader("Cache-Control", "no-store"); //http1.1
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache"); //http1.0
    */
%>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<script language="javascript">
function disableCard(cardnum) { 　

	var strURL = "GGCardUpdate.do?cardNum="+cardnum+"&isUse=1";　
	if (confirm("确定禁用？")){
		document.location.href = strURL;
	}
} 

function reuseCard(cardnum) { 　

	var strURL = "GGCardUpdate.do?cardNum="+cardnum+"&isUse=0";　
	if (confirm("确定重新启用？")){
		document.location.href = strURL;
	}
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >


<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">市场促销</font><font color="838383"> 
      		-&gt; </font><font color="838383">乐透卡查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/GGCardQuery.do" method="post" >
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">卡号： 
        <html:text property="cardNum" size="12"/>
        
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" 查询 ">
		<input name="actn" type="hidden" value="<%=actn%>">
		</td>
	</tr>
</table>
<%

if (request.getMethod().equals("POST")){
%>


  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
    <tr height="26"> 
    	
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >卡号</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >卡类型</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >状态</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >开始日期</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >结束日期</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >操作</th>
	</tr>
    
    <bean:define id="ggcard" name="CardForm"/>
   
    
    <tr> 
    	<td align=left ><bean:write name="ggcard" property="cardNum" filter="false"/></td>
      <td align=left ><bean:write name="ggcard" property="cardType" filter="false"/></td>
      
      <td align=left >
      	<logic:equal name="ggcard" property="isUse" value="0">
      	未使用
      	</logic:equal>
      	<logic:equal name="ggcard" property="isUse" value="1">
      	已使用
      	</logic:equal>
      	<logic:equal name="ggcard" property="isUse" value="2">
      	抽查卡
      	</logic:equal>
      	</td>
      	
      	 <td align=left ><bean:write name="ggcard" property="beginDate" filter="false"/></td>
      	 <td align=left ><bean:write name="ggcard" property="endDate" filter="false"/></td>
      	 <td align=left ><input name="input1" type="button" value="禁用" onclick="javascript:disableCard('<bean:write name="ggcard" property="cardNum" filter="false"/>')"><input name="input2" type="button" value="重新启用"  onclick="javascript:reuseCard('<bean:write name="ggcard" property="cardNum" filter="false"/>')"></td>
    </tr>
    
    
  </table>
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
  	<tr>
  		<td>注：A-代表乐透宝物 B-代表银版代金币 C-代表金版代金币</td>
  	</tr>
  </table>
<%
}
%>
</html:form>
<br>
</body>
</html>
