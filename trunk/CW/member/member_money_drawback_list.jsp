<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doDrawback() {
   if(!ifcheck()){
    alert("��ѡ���¼");
    return;
   }
   document.forms[0].action="memberMoneyDrawback.do?type=audit";
   document.forms[0].submit();
}

function cancelDrawback() {
    if(!confirm("ȷʵҪȡ����Щ�˿�������")) {
        return;
    }
    if(!ifcheck()){
        alert("��ѡ���¼");
        return;
   }
   document.forms[0].action="memberMoneyDrawback.do?type=cancelAudit";
   document.forms[0].submit();
}


function ifcheck() {
	var objs = DataTable.getElementsByTagName("INPUT");
	for (var i = 0; i < objs.length; i ++)
	{
	    if(objs[i].checked == true) {
	        return true;
	    }
	}
	return false;
}
function checkAll(bln) {
	var objs = DataTable.getElementsByTagName("INPUT");
	for (var i = 0; i < objs.length; i ++)
	{
		
		if (bln)
		{
			objs[i].checked = true;
		}
		else 
		{
			objs[i].checked = false;
		}
		
	}
	
}
//-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>�ʻ����� -&gt; �˿����</b></font></td>
  </tr>
</table>
<html:form action="memberMoneyDrawback.do?type=query" onsubmit="return doSearch();">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center" id="queryTable">
	
	<tr>
		<td>
		<!--
		<input type="button" name="queryBtn" value=" ��ѯ " onclick="doSearch();">&nbsp;&nbsp;
		-->
		<input type="button" value=" ͬ�� " id="btnAgree" onclick="doDrawback();">&nbsp;&nbsp;
		<input type="button" value=" ȡ�� " id="btnCancel" onclick="cancelDrawback();">&nbsp;&nbsp;
		</td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center" >
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
</table>

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable"  id="DataTable" >
	<tr>
		
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  ><input type=checkbox  onclick="checkAll(this.checked);">ȫѡ</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >ʱ��</th>
		<th width="30%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
	</tr>
    <% int i=0; %>
    <logic:iterate id="list" name="list"> 
	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %> >		
    <td noWrap align=left >
	<input type="checkbox" name="selID" value="<bean:write name="list" property="ID"/>">
	</td>
    <td  noWrap align=left >
	<bean:write name="list" property="cardID"/>
	</td>
		
    <td  noWrap align="left" >
	<bean:write name="list" property="name"/>
	</td>
	
	<td  noWrap align="left" >
	<bean:write name="list" property="amount" format="#0.0"/>
	
	</td>

	<td  noWrap align="left" >
	<bean:write name="list" property="operator_name"/>
	</td>


	<td  noWrap align=left >
	<bean:write name="list" property="createDate"/>
	</td>
	<td  noWrap align=left >
	<bean:write name="list" property="description"/>
	</td>
	</tr>
	<% i++; %>
    </logic:iterate>	
</table>

</html:form>
</body>
</html>
