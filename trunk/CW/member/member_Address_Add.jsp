<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
Member member=new Member();
member=(Member)request.getAttribute("member");

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function add_member_address() {
	if(document.memberForm.Relation_person.value==""){
	alert('��ϵ�˱�����д!');
	document.memberForm.Relation_person.focus();
	return false;
	}
	if(document.memberForm.Telephone.value==""){
	alert('��ϵ�绰һ����Ϊ��!');
	document.memberForm.Telephone.focus();
	return false;
	}

	if(document.memberForm.Postcode.value==""||document.memberForm.Postcode.value.length>6||isNaN(document.memberForm.Postcode.value)){
	alert('�ʱ಻��Ϊ�ջ��߳��Ȳ��ܴ���6λ����!');
	document.memberForm.Postcode.focus();
	return false;
	}

	if(document.memberForm.Delivery_address.value==""){
	alert('�ͻ���ַ������д!');
	document.memberForm.Delivery_address.focus();
	return false;
	}

	document.memberForm.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">


<html:form action="/memberAddressModify.do" method="post">
<table width="95%" align="center" cellspacing="1" cellspacing="1" border="0" noWrap >
	<tr height="26">
		<td width="30%"> ��ϵ�ˣ�&nbsp;&nbsp;<input type="text" name="Relation_person" >&nbsp;<font color="red">*</font></td>
		<td width="30%"> �绰���룺&nbsp;&nbsp;<input type="text" name="Telephone"  maxlength="15">&nbsp;<font color="red">*</font></td>
		<td width="40%"> �绰2(������)��&nbsp;&nbsp;<input type="text" name="telephone2"  maxlength="25" ></td>
	</tr>
	<tr height="26">
		<td> ��  �ࣺ&nbsp;&nbsp;<input type="text" name="Postcode" maxlength="6" size="10">&nbsp;<font color="red">*</font></td>
		<td colspan="2"> ʡ������&nbsp;&nbsp;
		<html:select property="province" onchange="listCity();">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="provs" />
		</html:select>&nbsp;&nbsp;
		<html:select property="city" onchange="listSection();">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="citys" />
		</html:select>&nbsp;&nbsp;
		<html:select property="section">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="sects" />
		</html:select>&nbsp;<font color=red>*</font>
    </td>
	</tr>
	
	<tr height="26">
		<td colspan="3">�ͻ���ַ��&nbsp;&nbsp;&nbsp;&nbsp;
		<textarea name="Delivery_address" cols="100"></textarea>&nbsp;<font color="red">*</font></td> 
	</tr>
</table>
<br>


<input type="hidden" name="type" value="5">
<input type="hidden" name="id" value="<%=member.getID()%>">
<center>
<input type="button"  value="���ӻ�Ա�ͻ���ַ��Ϣ" onclick="add_member_address()">

</html:form>

	</table>
</body>
</html>
