<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String rowId = (String)request.getAttribute("rowId");
String tag=request.getParameter("tag");
       tag=(tag==null)?"":tag;
String cardid=request.getParameter("cardid");
       cardid=(cardid==null)?"":cardid;
String adress=request.getParameter("adress");
       adress=(adress==null)?"":adress;
String p_code=request.getParameter("p_code");
       p_code=(p_code==null)?"":p_code;              

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function query_f() {
	var name, postcode;
	name = document.forms[0].NAME;
	postcode = document.forms[0].postcode;
	if(name.value == "" ) {
		alert("��������Ϊ��");
		return false;
	}
	document.forms[0].search.disabled = true;
	//document.forms[0].submit();
}

function doSelect(rtValue) {	
	self.close();
	
	self.opener.setCardId(trim(rtValue), "<%=rowId%>");
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="document.forms[0].search.focus()">

<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��:</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա��ѯ</font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form action="/memberQueryByAddress.do" method="post" onsubmit="return query_f()"> 
<input type="hidden" name="rowId" value="<%=rowId%>">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
  <tr> 
    <td>
	
	��Ա������
	<html:text property="NAME"/>&nbsp;&nbsp;&nbsp;&nbsp; 
	�ʱࣺ
	<html:text property="postcode"/>&nbsp;&nbsp;
      <input type="submit" value="��ѯ"  name="search">
	  <input type="button" value="�ر�" onclick="window.close();">
    </td>
  </tr>
</table>
<%if(tag.equals("1")){%>
	 <input type=hidden name="cardid" value="<%=cardid%>">
   <input type=hidden name="adress" value="<%=adress%>">
   <input type=hidden name="p_code" value="<%=p_code%>">
   <input type=hidden name="tag" value="1">

<%}else{%>
  <logic:iterate id="a" name="mbinfo" >
	 <input type=hidden name="cardid" value="<bean:write name='a' property='CARD_ID'/>">
   <input type=hidden name="adress" value="<bean:write name='a' property='addressDetail'/>">
   <input type=hidden name="p_code" value="<bean:write name='a' property='postcode'/>">
   <input type=hidden name="tag" value="1">
   </logic:iterate> 
<%}%>
</html:form>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td> 
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr> 
    <td></td>
  </tr>
</table>
<table width="95%" align="center" cellspacing="1" border="0"  >
<%if(tag.equals("1")){%>
  <tr height="26"> 
    <td width="200" height="26" nowrap><font color="red">��Ա�ţ� <%=cardid%> </font></td>   
	<td width="150" nowrap><font color="red">�ʱࣺ<%=p_code%></font></td>
  
	
	<td  nowrap><font color="red">��ַ��<%=adress%></font></td>
  </tr>
  <tr height="1" bgcolor="#000000"> 
    <td colspan="7" height="1"></td>
  </tr>
<%}%>
  <logic:iterate id="membera" name="mbinfo" >
  
  <tr height="26"> 
    <td width="130" height="26" nowrap><font color="red">��Ա�ţ�<bean:write name="membera" property="CARD_ID"/></font></td>   
	<td width="100" nowrap><font color="red">�ʱࣺ<bean:write name="membera" property="postcode"/></font></td>
  
	
	<td  nowrap><font color="red">��ַ��<bean:write name="membera" property="addressDetail"/></font></td>
  </tr>
  <tr height="1" bgcolor="#000000"> 
    <td colspan="7" height="1"></td>
  </tr>
   </logic:iterate> 
</table>

<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >

  <logic:iterate id="member" name="memberList" >
  
  <tr height="26"> 
    <td rowspan="2" bgcolor="#FFFFFF" width="33" valign="top"> 
      <input type="radio" name="CARD_ID" value="<bean:write name='member' property='CARD_ID'/>
      " onclick="doSelect(this.value);"> </td>
    <td width="75" height="26" nowrap>��Ա�ţ�</td>
    <td width="100" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="CARD_ID"/></td>
    <td width="39" height="26" nowrap>������</td>
    <td width="117" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="NAME"/></td>
    <td width="74" nowrap>��Ա�ȼ���</td>
    <td  width="248" bgcolor="#FFFFFF">
      <bean:write name="member" property="LEVEL_ID"/>
    </td>
  </tr>
  <tr height="26"> 
    <td width="75" height="26" nowrap>��ϵ�绰��</td>
    <td width="100" bgcolor="#FFFFFF" height="26"><bean:write name="member" property="TELEPHONE"/></td>
    
	<td width="" nowrap>�ʱࣺ</td>
    <td  bgcolor="#FFFFFF"><bean:write name="member" property="postcode"/></td>
	
	<td width="39" nowrap>��ַ��</td>
    <td  bgcolor="#FFFFFF"><bean:write name="member" property="addressDetail"/></td>
  </tr>
  <tr height="1" bgcolor="#000000"> 
    <td colspan="7" height="1"></td>
  </tr>
   </logic:iterate> 
</table>


</body>
</html>