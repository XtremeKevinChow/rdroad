<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript">
function back_console() {
	var frm = document.forms[0];
	frm.action = "memberDetail.do?iscallcenter=1";
	frm.submit();
}
function winopen(url,title) 
{ 
	window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
} 
function selectCard() {
	var cardId = <bean:write name="member" property="CARD_ID"/>
	//��ͨ��
	if ((cardId >= 70006001 && cardId <= 70009000) ||
		(cardId >= 30000001 && cardId <= 30020000) ||
		(cardId >= 50000001 && cardId <= 50003000) ||
		(cardId >= 30020001 && cardId <= 30025000) ||
		(cardId >= 30025001 && cardId <= 30045000) ||
		(cardId >= 30045001 && cardId <= 30095000) ||
		(cardId >= 30095001 && cardId <= 30145000) ||
		(cardId >= 30145001 && cardId <= 30195000) ||
		(cardId >= 30195001 && cardId <= 30245000) ||
		(cardId >= 30245001 && cardId <= 30295000) ||
		(cardId >= 30295001 && cardId <= 30345000) ||
		(cardId >= 30345001 && cardId <= 30395000) ||
		(cardId >= 30395001 && cardId <= 30432000) 
	)
	{
		document.forms[0].itemId[0].checked = true;
	}

	//e����
	if ((cardId >= 40000001 && cardId <= 40005000) ||
		(cardId >= 60000001 && cardId <= 60002000) ||
		(cardId >= 40005001 && cardId <= 40010000) ||
		(cardId >= 40010001 && cardId <= 40020000) ||
		(cardId >= 40020001 && cardId <= 40045000)
	)
	{
		document.forms[0].itemId[1].checked = true;
	}

	//�׽�
	if (cardId >= 20000001 && cardId <= 20050000) {
		document.forms[0].itemId[2].checked = true;		
	}
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="selectCard();">
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

<html:form action="/changeNewCard.do?type=changeNewCard" method="post">
<table width="75%" align="center" cellspacing="1" cellspacing="1" border="0"  >
	<tr>
		<td>
		��Ա�ţ�<bean:write name="member" property="CARD_ID"/>&nbsp;&nbsp;
		������<bean:write name="member" property="NAME"/>&nbsp;&nbsp;
		�ȼ���
		<logic:equal name="member" property="LEVEL_ID" value="1">
		��ͨ��Ա
		</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="2">
		������Ա
		</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="3">
		�𿨻�Ա
		</logic:equal>
		<logic:equal name="member" property="LEVEL_ID" value="4">
		�׽𿨻�Ա
		</logic:equal>
		</td>
	</tr>
</table>
<table width="75%" align="center" cellspacing="1" cellspacing="1" border="0"  >
	<tr height="26" class="OraTableRowHeader" noWrap >
		<td  align="left" >
			<B>��ѡ���¿�</B>
		</td>
	</tr>
	<tr height="26">
		<td  align="left" >
			<input type="radio" name="itemId" value="100000">100000 ��׼��Ա��-���ӣ����뿨��
		</td>
	</tr>
	<tr height="26">
		<td  align="left" >
			<input type="radio" name="itemId" value="100002">100002 e��VIP������-���ӣ����뿨��
		</td>
	</tr>
	<tr height="26">
		<td  align="left" >
			<input type="radio" name="itemId" value="100004">100004 �׽��Ա��-���ӣ����뿨��
		</td>
	</tr>
	<tr >
		<td>
			<input type="submit"  value=" ȷ�� ">&nbsp;&nbsp;&nbsp;
			<input type="button"  value="���ػ�Ա����̨" onclick="back_console();">	
		</td>
		
	</tr>
</table>
<html:hidden name="memberForm" property="ID" />
<html:hidden name="memberForm" property="CARD_ID" />
</html:form>
</body>
</html>
