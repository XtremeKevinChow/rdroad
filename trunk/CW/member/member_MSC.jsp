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
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function winopen(url,title) 
{ 
	window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function getItem() {
	if(document.forms[0].MSC_CODE.value==""){
		alert('msc�Ų���Ϊ��!!');
		document.forms[0].MSC_CODE.select();
		return false;
	}
	window.open("memberQueryRecruitProduct.do?msc_code="+document.forms[0].MSC_CODE.value,"ѡ�������Ʒ","toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=650,top=0");
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա����̨</font><font color="838383"> </nobr>
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
		<td align="right" class="OraTableRowHeader" noWrap  ><font color=red>*</font>&nbsp;��ļMSC</td>
		<td  align="left" >
<input type="text"  name="MSC_CODE" value="<%=msc_code%>" readonly> 
<input type="button" onClick="javascript:winopen('queryActiveList.do?tag=crmuse','ѡ��MSC��')" value="ѡ��"> <input type="button" onClick="javascript:getItem()" value="ѡ����Ʒ" > 	
&nbsp;<input type="button"  value="ȷ��" onclick="recruit_f();">
 &nbsp;	<input type="hidden" name="type" value="msc_code">		
		</td>
	</tr>
	
<%}%>
<%if(type.equals("card_type")){%>
	<tr height="26">
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;��������</td>
		<td  align="left" >
			<input type="radio" name="MSC_CODE" value="0" <%if(card_type.equals("0")){%>checked<%}%>>��ͨ��Ա�� <input type="radio" name="MSC_CODE" value="1" <%if(card_type.equals("1")){%>checked<%}%>>E��������Ա��
    &nbsp;<input type="submit"  value="ȷ��">
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
