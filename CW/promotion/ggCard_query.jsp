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
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">

<script language="javascript">
function disableCard(cardnum) { ��

	var strURL = "GGCardUpdate.do?cardNum="+cardnum+"&isUse=1";��
	if (confirm("ȷ�����ã�")){
		document.location.href = strURL;
	}
} 

function reuseCard(cardnum) { ��

	var strURL = "GGCardUpdate.do?cardNum="+cardnum+"&isUse=0";��
	if (confirm("ȷ���������ã�")){
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��͸����ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/GGCardQuery.do" method="post" >
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">���ţ� 
        <html:text property="cardNum" size="12"/>
        
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" ��ѯ ">
		<input name="actn" type="hidden" value="<%=actn%>">
		</td>
	</tr>
</table>
<%

if (request.getMethod().equals("POST")){
%>


  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
    <tr height="26"> 
    	
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >����</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >������</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >״̬</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >��ʼ����</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >��������</th>
	  <th width="20%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >����</th>
	</tr>
    
    <bean:define id="ggcard" name="CardForm"/>
   
    
    <tr> 
    	<td align=left ><bean:write name="ggcard" property="cardNum" filter="false"/></td>
      <td align=left ><bean:write name="ggcard" property="cardType" filter="false"/></td>
      
      <td align=left >
      	<logic:equal name="ggcard" property="isUse" value="0">
      	δʹ��
      	</logic:equal>
      	<logic:equal name="ggcard" property="isUse" value="1">
      	��ʹ��
      	</logic:equal>
      	<logic:equal name="ggcard" property="isUse" value="2">
      	��鿨
      	</logic:equal>
      	</td>
      	
      	 <td align=left ><bean:write name="ggcard" property="beginDate" filter="false"/></td>
      	 <td align=left ><bean:write name="ggcard" property="endDate" filter="false"/></td>
      	 <td align=left ><input name="input1" type="button" value="����" onclick="javascript:disableCard('<bean:write name="ggcard" property="cardNum" filter="false"/>')"><input name="input2" type="button" value="��������"  onclick="javascript:reuseCard('<bean:write name="ggcard" property="cardNum" filter="false"/>')"></td>
    </tr>
    
    
  </table>
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
  	<tr>
  		<td>ע��A-������͸���� B-������������ C-����������</td>
  	</tr>
  </table>
<%
}
%>
</html:form>
<br>
</body>
</html>
