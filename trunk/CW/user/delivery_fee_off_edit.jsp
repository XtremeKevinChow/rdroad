<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.user.entity.*"%>
<%
DeliveryFeeOff dfo = (DeliveryFeeOff)request.getAttribute("dfo");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">
function on_submit() {
	if(document.getElementById("dfo_name").value==""){
		alert('���Ʋ���Ϊ��!');
		document.getElementById("dfo_name").focus();
		return false;
	}
	var bdate = document.getElementById("startDate").value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
	 if(bdate==null){
			alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
			document.getElementById("startDate").focus();
			return false;
	 }		
	var edate = document.getElementById("endDate").value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
	 if(edate==null){
			alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
			document.getElementById("endDate").focus();
			return false;
	 }
	 return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ض���Ʒ���ʻ�༭</font></td>
   </tr>
</table>

<html:form method="post" action="deliveryFeeOff.do?type=saveDfo" onsubmit="return on_submit();">
<table  border=0 cellspacing=1 cellpadding=1  width="500" align="center" class="OraTableRowHeader" noWrap  >
	<tr  bgcolor="#FFFFFF">
		<td align="right" ><font color=red>*</font>&nbsp;���</td>
		<td  align="left" >
		    <input type="hidden" name="dfo_id" id="dfo_id" value="<%=dfo.getId()%>"> 
			<input type="text" readonly="readonly" value="<%=dfo.getId()%>" style="width:40px;">
		</td>		
	</tr>
	<tr  bgcolor="#FFFFFF">
		<td align="right" ><font color=red>*</font>&nbsp;����</td>
		<td  align="left" >
		<input type="text" id="dfo_name" name="dfo_name" maxlength="50" value="<%=dfo.getName()%>" style="width:300px;"> 
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF" >
		<td align="right" ><font color=red>*</font>&nbsp;��Ч����</td>
		<td  align="left" >
			<input type="text" name="startDate" size="10"  value="<%= com.magic.crm.common.LogicUtility.getDateAsString(dfo.getBegin_date())%>">
			<a href="javascript:show_calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
        </tr>
        <tr  bgcolor="#FFFFFF">	
		<td align="right"  bgcolor="#FFFFFF"><font color=red>*</font>&nbsp;��ֹ����</td>
		<td  align="left" >
			<input type="text" name="endDate" size="10"  value="<%=com.magic.crm.common.LogicUtility.getDateAsString(dfo.getEnd_date())%>">
			<a href="javascript:show_calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		
	</tr>			
	<tr  bgcolor="#FFFFFF">
		<td align="right" >&nbsp;״̬</td>
		<td align="left">
			<select name="dfo_status">
			    <option value="1" <% if(dfo.getStatus()==1) out.print("selected"); %>>��Ч</option>
			    <option value="-1" <% if(dfo.getStatus()==-1) out.print("selected"); %>>��Ч</option>
			</select>
		</td>
	</tr>
	<tr height="40"  bgcolor="#FFFFFF">
		<td align="center" colspan=4>
		<input type="submit"  value="����" > &nbsp;
		<input type="button" class="button2" value="����" onclick="window.location.href='deliveryFeeOff.do?type=showDfoList';">
	</tr>
</table>
</html:form>

</body>
</html>
