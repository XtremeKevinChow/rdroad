<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.promotion.entity.*"%>
<%
Catalog c=new Catalog();
String actionurl="";
String title="";
String buttonvalue="";
c=(Catalog)request.getAttribute("c");
String price_type_id=request.getParameter("price_type_id");
if(c.getID()>0){
actionurl="/Catalog.do?type=modify";
title="Ŀ¼�޸�";
buttonvalue="�޸�";
}else{
actionurl="/Catalog.do?type=add";
title="Ŀ¼����";
buttonvalue="�ύ";
}
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
function winFocus(){
	//document.forms[0].periodical_id.focus();
	return true;	
}

function addSubmit() {

	/*if(document.forms[0].periodical_id.value==""){
		alert('Ŀ¼�ںŲ���Ϊ��!');
		document.forms[0].periodical_id.focus();
		return false;
	}*/
	if(document.forms[0].name.value==""){
		alert('Ŀ¼���Ʋ���Ϊ��!');
		document.forms[0].name.focus();
		return false;
	}
	if(document.forms[0].MSC_CODE.value==""){
		alert('��ѡ����ļMSC!');
		document.forms[0].MSC_CODE.focus();
		return false;
	}	


	
	if(document.forms[0].member_category_id.value==""){
		alert('��Ա�����Ϊ��!');
		document.forms[0].member_category_id.focus();
		return false;
	}	

		var bdate = document.forms[0].startDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('�밴��ʽ��д��ʼ����,����ע����������Ƿ���ȷ!');
				document.forms[0].startDate.focus();
				return false;
		 }		
		var edate = document.forms[0].endDate.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(edate==null){
				alert('�밴��ʽ��д��������,����ע����������Ƿ���ȷ!');
				document.forms[0].endDate.focus();
				return false;
		 }		 
         
}

function winopen(url,title) 
{ 
window.open(url,title,"toolbar=no,directories=no,menubar=no,scrollbars=yes,width=600,height=300"); 
}  
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000"  onload="javascript:winFocus()">
<table width="700" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383"><%=title%></font><font color="838383">&nbsp; 
      	</font></td>
   </tr>
</table>

<html:form method="post" action="<%=actionurl%>" onsubmit="return addSubmit();">

<table  border=0 cellspacing=1 cellpadding=1  width="700" align="center" class="OraTableRowHeader" noWrap  >
	<tr  bgcolor="#FFFFFF">
		<td align="right" ><font color=red>*</font>&nbsp;Ŀ¼����</td>
		<td  align="left" >
			<input type="text" name="name" maxlength="50" value="<%=c.getName()%>">
		</td>		
	</tr>
	<tr  bgcolor="#FFFFFF">

		<td align="right" ><font color=red>*</font>&nbsp;��ļMSC</td>
		<td  align="left" >
		<input type="hidden" id="msc" name="msc" value="<%=c.getMscID()%>"> 
		<input type="text" name="MSC_CODE"   value="<%=c.getMsc()%>" readonly> 
		<input type="button" onClick="javascript:winopen('/member/queryMSCList.do','ѡ��MSC��')" value="ѡ��MSC" > 
		
				
		</td>
	</tr>

 <input type=hidden name="member_category_id" value="1">
		
	</tr>
	<tr  bgcolor="#FFFFFF" >
		<td align="right" ><font color=red>*</font>&nbsp;��ʼ����</td>
		<td  align="left" >
			<input type="text" name="startDate" size="10"  value="<%=c.getEffect_date()%>">
			<a href="javascript:show_calendar(document.forms[0].startDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
        </tr>
        <tr  bgcolor="#FFFFFF">	
		<td align="right"  bgcolor="#FFFFFF"><font color=red>*</font>&nbsp;��ֹ����</td>
		<td  align="left" >
			<input type="text" name="endDate" size="10"  value="<%=c.getExpirped_date()%>">
			<a href="javascript:show_calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		</td>
		
	</tr>			
	<tr  bgcolor="#FFFFFF">
		<td align="right" >&nbsp;����</td>
		<td align="left">
			<textarea cols=70 rows=3 name="description" maxlength="500"><%=c.getDescription()%></textarea>
		</td>
	</tr>
	<tr height="40"  bgcolor="#FFFFFF">
		<td align="center" colspan=4>
		<input type="submit"  value="<%=buttonvalue%>" > &nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
		<input type="hidden" name="id" value="<%=c.getID()%>" >
		<input type="hidden" name="price_type_id" value="<%=price_type_id%>" >
	</tr>
</table>
</html:form>

</body>
</html>
