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
function doSearch() {
	var theForm = document.forms[0];

	if(theForm.beginDate.value == "" || theForm.endDate.value == "") {
		// û�в�ѯ����
		alert("���������ڲ�ѯ������");
		return;
	} 

	theForm.queryBtn.disabled = true;
	theForm.submit();
}
function modify_f(id, obj) {
	var theForm = document.forms[0];
	theForm.action = "memberaddMoneyManage.do?type=initModify&ID="+id;
	obj.disabled = true;
	theForm.submit();
}
function delete_f(id, obj) {
	var theForm = document.forms[0];
	if (confirm("ȷʵҪɾ����"))
	{
		theForm.action = "memberaddMoneyManage.do?type=delete&ID="+id;
		obj.disabled = true;
		theForm.submit();
	}
	
}
function load_f() {
	var obj = document.getElementById("DataTable");
	var len = obj.rows.length;
	var total_money = 0;
	if (len > 2) // ��ͷ����Ϊ���ڼ���֮��
	{
		for ( var i = 1; i < len - 1 ; i ++ )
		{
			var col = obj.rows[i].cells[4];
			var money = parseFloat(col.innerText);
			if (!isNaN(money))
			{
				total_money += money;
			}
		}
	}
	document.getElementById("totalMoney").innerText = Math.round(total_money, 2);
}
//-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load_f();">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>�ʻ����� -&gt; ���㿨��ѯ</b></font></td>
  </tr>
</table>
<html:form action="memberaddMoneySxk.do?type=query" onsubmit="return doSearch();">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td>
		��ţ�<html:text property="searchRefId" size="19"/>
        ������<html:text property="searchMbName" size="12"/>
		<font color=red>*</font>¼�����ڣ�
		��<html:text property="beginDate"  readonly="true" size="10"/><a href="javascript:calendar(document.forms[0].beginDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>��
		<html:text property="endDate"  readonly="true" size="10"/><a href="javascript:calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		<br>
		֧����ʽ��
		<html:select property="searchPayMethod" style="width:70">
			<option value="-1" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="-1">selected</logic:equal>>ѡ��...</option>
			<option value="6" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="6">selected</logic:equal>>�������</option>
			<option value="14" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="14">selected</logic:equal>>�ֽ�</option>
			<option value="90" <logic:equal name="memberaddmoneyForm" property="searchPayMethod" value="90">selected</logic:equal>>���лص�</option>
		</html:select>
		״̬��
		<html:select property="status" style="width:70">
			<option value="0" <logic:equal name="memberaddmoneyForm" property="status" value="0">selected</logic:equal>>δ��ֵ</option>
			<option value="1" <logic:equal name="memberaddmoneyForm" property="status" value="1">selected</logic:equal>>�ѳ�ֵ</option>
			<option value="2" <logic:equal name="memberaddmoneyForm" property="status" value="2">selected</logic:equal>>��ֵʧ��</option>
		</html:select>
		<input type="button" name="queryBtn" value=" ��ѯ " onclick="doSearch();">&nbsp;&nbsp;
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

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��;</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ʽ</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��ע</th>
		
	</tr>
    
    <logic:iterate id="list" name="list"> 
	<tr>		
     
    <td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<bean:write name="list" property="REF_ID"/>
	</td>
		
    <td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="list" property="ORDER_CODE"/>
	</td>
	
	<td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="list" property="MB_CODE"/>
	
	</td>

	<td class=OraTableCellText noWrap align="left" class=OraTableCellText>
	<bean:write name="list" property="MB_NAME"/>
	</td>

	<td class=OraTableCellText noWrap align="right" class=OraTableCellText>
	<bean:write name="list" property="MONEY" format="#0.00"/>
	</td>

	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<bean:write name="list" property="bill_date"/>
	</td>

	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<logic:equal name="list" property="USE_TYPE" value="0">
	Ԥ����
	</logic:equal>
	<logic:equal name="list" property="USE_TYPE" value="1">
	�������㿨
	</logic:equal>
	</td>
	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<logic:equal name="list" property="payMethod" value="14">
	�ֽ�
	</logic:equal>
	<logic:equal name="list" property="payMethod" value="90">
	���лص�
	</logic:equal>
	<logic:equal name="list" property="payMethod" value="6">
	�������
	</logic:equal>
	</td>
	<td class=OraTableCellText noWrap align=left class=OraTableCellText>
	<bean:write name="list" property="REMARK"/>
	</td>
	</tr>
	</logic:iterate>
	<tr>
	  <td colspan=5 id="totalMoney" align="right"></td>
	  <td bgcolor="#FFFFFF" noWrap align="center" colspan="4"><a href=member_addmoney_sxk_excel.jsp?beginDate=<bean:write name="memberaddMoneyForm" property="beginDate"/>&endDate=<bean:write name="memberaddMoneyForm" property="endDate"/>&searchRefId=<bean:write name="memberaddMoneyForm" property="searchRefId"/>&searchMbName=<bean:write name="memberaddMoneyForm" property="searchMbName"/>&searchPayMethod=<bean:write name="memberaddMoneyForm" property="searchPayMethod"/>&status=<bean:write name="memberaddMoneyForm" property="status"/>>����excel</a></td>    
	
	</tr>	
</table>

</html:form>
</body>
</html>
