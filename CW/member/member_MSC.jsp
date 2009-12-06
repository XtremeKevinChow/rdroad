<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String type=request.getParameter("type");
String id=request.getParameter("id");
String msc_code=request.getParameter("msc_code");
String card_type=request.getParameter("card_type");
String card_id=request.getParameter("card_id");

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function winopen(url,title) 
{ 
	window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function getItem() {
	if(document.forms[0].MSC_CODE.value==""){
		alert('msc号不能为空!!');
		document.forms[0].MSC_CODE.select();
		return false;
	}
	window.open("memberQueryRecruitProduct.do?msc_code="+document.forms[0].MSC_CODE.value,"选择入会礼品","toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=650,top=0");
}
function recruit_f() {
	document.forms[0].action = "supplyRecruitGifts.do";
	document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td width="280"><nobr>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员服务台</font><font color="838383"> </nobr>
      	</td>
      	<td align="right">
      		&nbsp;
      	</td>
   </tr>
</table>

<html:form action="/memberMSC_Modifyok.do" method="post">
<table width="75%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
<%if(type.equals("msc")){%>
	<tr height="26">
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;招募MSC</td>
		<td  align="left" >
<input type="text"  name="MSC_CODE" value="<%=msc_code%>" readonly> 
<input type="button" onClick="javascript:winopen('queryActiveList.do?tag=crmuse','选择MSC号')" value="选择"> <input type="button" onClick="javascript:getItem()" value="选择礼品" > 	
&nbsp;<input type="button"  value="确定" onclick="recruit_f();">
 &nbsp;	<input type="hidden" name="type" value="msc_code">		
		</td>
	</tr>
	
<%}%>
<%if(type.equals("card_type")){%>
	<tr height="26">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;发卡类型</td>
		<td  align="left" >
			<input type="radio" name="MSC_CODE" value="0" <%if(card_type.equals("0")){%>checked<%}%>>普通会员卡 <input type="radio" name="MSC_CODE" value="1" <%if(card_type.equals("1")){%>checked<%}%>>E龙联名会员卡
    &nbsp;<input type="submit"  value="确定">
     &nbsp;	<input type="hidden" name="type" value="card_type">		
		</td>
	</tr>
<%}%>
</table>

<br>
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="card_id" value="<%=card_id%>">

</html:form>

	</table>
</body>
</html>
