<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//��Ʊҵ����
function query_f() {
	
}
function save_f() {
	
}
function check_f() {
	if (confirm("ȷ�������?"))
	{
		os_status.innerText = "ȷ��";
		alert("��˳ɹ�!");
	}
}
function uncheck_f() {
	if (confirm("ȷ��������?"))
	{
		os_status.innerText = "�½�";
		alert("����ɹ�!");
	}
}
function account_f() {
	if (confirm("ȷ��������?"))
	{
		if (os_status.innerText == "�½�")
		{
			alert("��δȷ��!");
			return;
		}
		os_status.innerText = "����̨��";
		alert("���ʳɹ�!");
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�������</font><font color="838383"> 
      		-&gt; </font><font color="838383">���۷�Ʊ����</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<TABLE>
<TR>
	<TD>
		��Ʊ�ţ�
		<input name="invioceNO">&nbsp;&nbsp;
		<input type="button" value=" ��ѯ " onclick="query_f();">&nbsp;&nbsp;
		<!-- <input type="button" value=" ��� " onclick="check_f();">&nbsp;&nbsp;
		<input type="button" value=" ���� " onclick="uncheck_f();">&nbsp;&nbsp;
		<input type="button" value=" ���� " onclick="account_f();">&nbsp;&nbsp; -->
	</TD>
</TR>
</TABLE>
<br>
<table id="main" width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  		
  <tr height="26"> 
	<td>���۷�Ʊ�ţ�</td><td bgcolor="#FFFFFF">INVOICE000000000001</td>
	<td>�ͻ����룺</td><td bgcolor="#FFFFFF">99</td>
	<td>�ͻ����ƣ�</td><td bgcolor="#FFFFFF">�ž�</td>
  </tr>
  <tr height="26"> 
	<td>ҵ�����ͣ�</td><td bgcolor="#FFFFFF">��������</td>
	<td>�������ڣ�</td><td bgcolor="#FFFFFF">2006-06-20</td>
	<td>����״̬��</td><td bgcolor="#FFFFFF" id="os_status">�½�</td>
  <!-- </tr>
    <tr height="26"> 
	<td>�Ƶ��ˣ�</td><td bgcolor="#FFFFFF">��</td>
	<td>����ˣ�</td><td bgcolor="#FFFFFF">��</td>
	<td>�����ˣ�</td><td bgcolor="#FFFFFF">˧</td>
  </tr> -->
</table>
<br>
<table id="detail" width="95%" align="center" border=0 cellspacing=1 cellpadding=5 id="DataTable">
	<tr>
		
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���۶�����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��Ʒ����</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���ۼ۸�</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>��������</th>
		<th width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>���</th>
	

	</tr>
	<tr>
		<td class=OraTableCellText noWrap align=middle >SALES000000000001</td>
		<td class=OraTableCellText noWrap align=middle >119775</td>
		<td class=OraTableCellText noWrap align=left >��Ԫ�ף�1DVD��</td>
		<td class=OraTableCellText noWrap align=left >6.00</td>
		<td class=OraTableCellText noWrap align=left >1</td>
		<td class=OraTableCellText noWrap align=left >����</td>
		<td class=OraTableCellText noWrap align=right >6.00</td>
		
	</tr>
	<tr>
		<td class=OraTableCellText noWrap align=middle >SALES000000000001</td>
		<td class=OraTableCellText noWrap align=middle >120312</td>
		<td class=OraTableCellText noWrap align=left >��ʹ���Σ�6�ᣩ</td>
		<td class=OraTableCellText noWrap align=left >45.00</td>
		<td class=OraTableCellText noWrap align=left >1</td>
		<td class=OraTableCellText noWrap align=left >����</td>
		<td class=OraTableCellText noWrap align=right >45.00</td>
		
	</tr>
	<tr>
		<td class=OraTableCellText noWrap align=middle >SALES000000000001</td>
		<td class=OraTableCellText noWrap align=middle >119999</td>
		<td class=OraTableCellText noWrap align=left >����</td>
		<td class=OraTableCellText noWrap align=left >19.00</td>
		<td class=OraTableCellText noWrap align=left >1</td>
		<td class=OraTableCellText noWrap align=left >����</td>
		<td class=OraTableCellText noWrap align=right >19.00</td>
		
	</tr>
</table>

</body>
</html>
