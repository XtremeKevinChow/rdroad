<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript" src="../script/default.js"></script>
<script language="javascript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript">
function f_checkData() {
 	document.forms[0].addBtn.disabled = true;
}

</script>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body  text="#000000" leftmargin="0" topmargin="0" >
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">�������׼۸�</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="groupPrices.do?type=add" method="post" onsubmit="return f_checkData();">
 <table>
 <tr>
<TD ><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></TD>
	</tr>
</table>
 <table width="80%" border=0 cellspacing=1 cellpadding=1  align="center" >
 <tr> 
    <td class="OraTableRowHeader" noWrap  >��������</td>
    <td><html:select property="sectionId" >
		<html:optionsCollection name="groupPricesForm" property="sectionList" value="code" label="name"/> 
		</html:select></td>
	<td class="OraTableRowHeader" noWrap  >������</td>
    <td><html:text property="saleQty" /></td>
 </tr> 

 <tr> 
    
	<td class="OraTableRowHeader" noWrap  >���۽�</td>
    <td><html:text property="saleAmt"/></td>
	<td class="OraTableRowHeader" noWrap  >�Ƿ�������Ʒ��</td>
    <td>û��<html:radio property="isGift" value="0"/>
	��<html:radio property="isGift" value="1"/>
	</td>
 </tr> 
 
 <tr> 
    
	
 </tr> 
 <tr> 
    <td class="OraTableRowHeader" noWrap  >��ʼ���ڣ�</td>
    <td><html:text property="beginDate" readonly="true"/><a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a></td>
	<td class="OraTableRowHeader" noWrap  >�������ڣ�</td>
    <td><html:text property="endDate" readonly="true"/><a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a></td>
 </tr>

 <tr align="center" valign="middle"> 
   <td height="42" colspan=4> 
      <input  type="submit" name="addBtn" class="button2" value=" �ύ " > 
     
 </tr>
</table>
</html:form>

<p>&nbsp;</p>
</body>
</html>

