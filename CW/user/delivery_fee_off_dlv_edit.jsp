<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.magic.crm.user.entity.*"%>
<%
DeliveryFeeOffDlv entity = (DeliveryFeeOffDlv)request.getAttribute("dlv");
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
	if(document.getElementById("off_type").value=="2"){
		var fee = parseFloat(document.getElementById("off_fee").value);
		if(isNaN(fee) || fee<=0){
		    alert('����д��Ч�Ľ��');
		    document.getElementById("off_fee").focus();
		    return false;
		}
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

<html:form method="post" action="deliveryFeeOff.do?type=saveDlv" onsubmit="return on_submit();">
<input type="hidden" id="off_id" name="off_id" value="<%= entity.getOff_id() %>"></input>
<table  border=0 cellspacing=1 cellpadding=1  width="500" align="center" class="OraTableRowHeader" noWrap  >
	<tr  bgcolor="#FFFFFF">
		<td align="right" ><font color=red>*</font>&nbsp;���ͷ�ʽ</td>
		<td  align="left" >
		    <select name="dlv_id">
			    <% if(entity.getDelivery_id()>0){ %>
			        <option value="<%=entity.getDelivery_id() %>" selected><%= entity.getDelivery_name() %></option>
			    <%}else{ %>
			    <logic:iterate name="deliveryTypes" id="dlv">
			        <option value="<bean:write name="dlv" property="value"/>"><bean:write name="dlv" property="label"/></option>
			    </logic:iterate>
			    <%} %>
			</select>
		</td>		
	</tr>
	<tr  bgcolor="#FFFFFF">
		<td align="right" ><font color=red>*</font>&nbsp;���ʷѷ�ʽ</td>
		<td  align="left" >
		    <select name="off_type" id="off_type">
			    <option value="1" <% if(entity.getOff_type()==1) out.print("selected"); %>>ȫ��</option>
			    <option value="2" <% if(entity.getOff_type()==2) out.print("selected"); %>>������</option>
			</select>
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF">
		<td align="right" >&nbsp;�������ʷѽ��</td>
		<td align="left">
			<input name="off_fee" id="off_fee" type="text" value="<%= entity.getOff_fee() %>" style="width:100px;" maxlength="5"></input>
		</td>
	</tr>
	<tr  bgcolor="#FFFFFF">
		<td align="right" >&nbsp;״̬</td>
		<td align="left">
			<select name="dlv_status">
			    <option value="1" <% if(entity.getStatus()==1) out.print("selected"); %>>��Ч</option>
			    <option value="-1" <% if(entity.getStatus()==-1) out.print("selected"); %>>��Ч</option>
			</select>
		</td>
	</tr>
	<tr height="40"  bgcolor="#FFFFFF">
		<td align="center" colspan=4>
		<input type="submit"  value="����" /> 
		&nbsp;
		<input type="button" class="button2" value="����" onclick="window.location.href='deliveryFeeOff.do?type=deliveryList&id=<%= entity.getOff_id() %>';" />
	</tr>
</table>
</html:form>

</body>
</html>
