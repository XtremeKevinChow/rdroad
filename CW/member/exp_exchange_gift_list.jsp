<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<SCRIPT LANGUAGE="JavaScript">

function doSearch() {
	var theForm = document.forms[0];
	
	if(theForm.cardID.value=="" && theForm.memberName.value=="" && theForm.telephone.value=="" ) {
		// û�в�ѯ����
		alert("�������ѯ������");
		theForm.cardID.focus();
		return false;
	} 
	theForm.offset.value = 0;
	theForm.query.disabled = true;
	theForm.submit();
}

function exchange_f(obj) {
	
	
	var flag = false;
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		if ( row.getElementsByTagName("INPUT")(0) != null )
		{
			if ( row.getElementsByTagName("INPUT")(0).checked == true )
			{
				flag = true;
				break;
			}
		}
		
		
	}
	if (flag == true)
	{
		if (confirm("ȷʵҪȡ����Ʒ��?"))
		{
			document.forms[0].action = "/member/mbrGetAward.do?type=expCancelGift";
			obj.disabled;
			document.forms[0].submit();
		}
		
	} else {
		alert("��ѡ���¼!");
	}
}

//ȫѡ��ֵ
function checkAll(bln, type) {
	if(document.forms[0].selectedID == null) {
		return;
	}
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if(bln) {
			
			if (typeof(row.getElementsByTagName("INPUT")[type]) != "undefined")
			{
				row.getElementsByTagName("INPUT")[type].checked = true;
			}
			
			
		}else{
			if (typeof(row.getElementsByTagName("INPUT")[type]) != "undefined")
			{
				row.getElementsByTagName("INPUT")[type].checked = false;
			}
		}
	}
}
function load() {
	document.forms[0].cardID.focus();
}
function add_f(obj) {
	document.forms[0].action = "/member/memberAddGift.do?type=addInit";
	//obj.disabled = true;
	document.forms[0].submit();
}
function add_f2(obj) {
	document.forms[0].action = "/member/memberAddGift.do?type=addInit2";
	//obj.disabled = true;
	document.forms[0].submit();
}

</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="load()">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ʒ��ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form action="/mbrGetAward.do?type=queryGift">
<bean:define name="mbrGetAwardForm" property="pager" id="pager"/>
<html:hidden name="pager" property="offset"/>
<table  align="center" width="98%" border=0 cellspacing=1 cellpadding=1 >
	<tr>
		<td>��Ա����:
		<html:hidden property="memberID" />
		<html:text property="cardID" size="10"/>&nbsp;&nbsp;
		������
		<html:text property="memberName" size="10"/>&nbsp;&nbsp;
		�绰:
		<html:text property="telephone" size="10"/>&nbsp;&nbsp;
		��Ʒ�ţ�<html:text property="itemCode" size="8"/>&nbsp;&nbsp;
        ���ͣ�
		<html:select property="sellType">
		<html:optionsCollection name="mbrGetAwardForm" property="sellTypeList" value="code" label="name"/> 
		</html:select>&nbsp;&nbsp;
		
		<input type="button" name="query" value=" ��ѯ " onclick="doSearch()">
		</td>
		
	</tr>
	
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table width="98%" align="center" border=0  id="DataTable">
	<tr>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >ȫѡ<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"></th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >����</th>
		<th width="20%"  class="OraTableRowHeader" noWrap align=middle  >��Ʒ����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >����</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >�۸�</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >����</th>
		<th width="5%"  class="OraTableRowHeader" noWrap align=middle  >ʹ�û���</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >����ʱ��</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >��������</th>
		<th width="8%"  class="OraTableRowHeader" noWrap align=middle  >״̬</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >������</th>
		<th width="10%"  class="OraTableRowHeader" noWrap align=middle  >�Ƿ�ת��</th>
	</tr>
    <% int i=0; %>
    <logic:iterate id="giftList" name="list"> 
	<tr <%if(i%2==1) {%> class="OraTableCellText" <%}%>>
			
    <td align=center noWrap >
	<logic:equal name="giftList" property="status" value="0">
	<input type="checkbox" name="selectedID" value="<bean:write name="giftList" property="ID"/>"></td>
	</logic:equal>
    <td class= noWrap align=center >
	&nbsp;<bean:write name="giftList" property="itemCode"/>
	</td>
		
    <td  noWrap align="left" >
	&nbsp;<bean:write name="giftList" property="itemName"/>
	</td>
    
    <td noWrap align="left" >
	<logic:equal name="giftList" property="type" value="3">��������</logic:equal>
	<logic:equal name="giftList" property="type" value="4">��Ʒ��Ʒ</logic:equal>
	<logic:equal name="giftList" property="type" value="5">��������Ʒ</logic:equal>
	<logic:equal name="giftList" property="type" value="6">���ֻ���</logic:equal>
	<logic:equal name="giftList" property="type" value="7">ע������</logic:equal>
	<logic:equal name="giftList" property="type" value="9">���ϻ��Ʒ</logic:equal>
	<logic:equal name="giftList" property="type" value="12">ת����Ʒ</logic:equal>
	<logic:equal name="giftList" property="type" value="13">�˹�����Ʒ</logic:equal>
	<logic:equal name="giftList" property="type" value="14">����</logic:equal>
	<logic:equal name="giftList" property="type" value="16">������ȯ</logic:equal>

	</td>

	<td noWrap align="right" >
	<bean:write name="giftList" property="price" format="#0.00"/>
	</td>
	<td noWrap align="right" >
	<bean:write name="giftList" property="quantity" />
	</td>
	
	<td  noWrap align="right" >
	<bean:write name="giftList" property="used_amount_exp" format="#0"/>
	</td>
	
	<td  noWrap align=center >
	<bean:write name="giftList" property="create_date"/>
	</td>

	<td noWrap align="center" >
	<bean:write name="giftList" property="lastDate"/>
	</td>
	
	
	<td noWrap align="left" >
	<logic:equal name="giftList" property="status" value="0">����</logic:equal>
	<logic:equal name="giftList" property="status" value="-1">����ȡ��</logic:equal>
	<logic:equal name="giftList" property="status" value="-10">����ȡ��</logic:equal>
	<logic:equal name="giftList" property="status" value="-5"><font color="red">�ѹ���</font></logic:equal>
	<logic:equal name="giftList" property="status" value="1">�ѷ�</logic:equal>
	<logic:equal name="giftList" property="status" value="10">�ѷ�</logic:equal>
	</td>

	<td  noWrap align="left" >
	<bean:write name="giftList" property="operatorName"/>
	</td>

	<td  noWrap align="center" >
	<logic:equal name="giftList" property="isTransfer" value="0">��</logic:equal>
	<logic:equal name="giftList" property="isTransfer" value="1">��</logic:equal>
	</td>
	</tr>
	<%i++;%>
	</logic:iterate>
</table>
<br>
<!--
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      	<td>�������Ʒ
      	</td>
   </tr>
</table>
-->

<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center">
		<input type="button" name="addBtn1" value=" ������Ʒ " onclick="add_f(this)">
		<input type="button" name="addBtn3" value="��Ʒȡ��" onclick="exchange_f(this)">&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
