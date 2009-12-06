<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doAdd() {
   if(!ifcheck()){
    alert("请选择纪录");
    return;
   }
   document.forms[0].action="memberAddGift.do?type=audit";
   document.forms[0].submit();
}

function cancelAdd() {
    if(!confirm("确实要取消这些礼品吗？")) {
        return;
    }
    if(!ifcheck()){
        alert("请选择纪录");
        return;
   }
   document.forms[0].action="memberAddGift.do?type=cancelAudit";
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
    <td> <font color="#838383"><b>会员管理 -&gt; 礼品审核</b></font></td>
  </tr>
</table>
<html:form action="mbrGetAward.do?type=listAudit" >
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center" id="queryTable">
	
	<tr>
		<td>
		<!--
		<input type="button" name="queryBtn" value=" 查询 " onclick="doSearch();">&nbsp;&nbsp;
		-->
		<input type="button" value=" 同意 " id="btnAgree" onclick="doAdd();">&nbsp;&nbsp;
		<input type="button" value=" 取消 " id="btnCancel" onclick="cancelAdd();">&nbsp;&nbsp;
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
		
		<th width="3%"  class="OraTableRowHeader" noWrap  noWrap align=middle  ><input type=checkbox  onclick="checkAll(this.checked);">全选</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >姓名</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >数量</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >价格</th>
		<th width="5%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作人</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作时间</th>
		<th width="20%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >描述</th>
	</tr>
    <% int i=0; %>
    <logic:iterate id="list" name="list"> 
	<tr <% if(i%2==1) { %>class=OraTableCellText<% } %> >		
    <td noWrap align=left >
	<input type="checkbox" name="selID" value="<bean:write name="list" property="awardID"/>">
	</td>
    <td  noWrap align=left >
	<bean:write name="list" property="cardID"/>
	</td>
	<td  noWrap align="left" >
	<bean:write name="list" property="memberName"/>
	</td>
	<td  noWrap align="left" >
	<bean:write name="list" property="itemCode"/><bean:write name="list" property="itemName"/>
	</td>
	<td  noWrap align="left" >
	<bean:write name="list" property="total_num"/>
	</td>
	<td  noWrap align="left" >
	<bean:write name="list" property="exchangePrice"/>
	</td>
	<td  noWrap align="left" >
	<bean:write name="list" property="operatorName"/>
	</td>
	<td  noWrap align=left >
	<bean:write name="list" property="create_date"/>
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
