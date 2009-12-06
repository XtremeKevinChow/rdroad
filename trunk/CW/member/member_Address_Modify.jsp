<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
Collection memberAddCol=(Collection)request.getAttribute("memberAddDetail");
Member member=new Member();
member=(Member)request.getAttribute("member");

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function modify_member_address() {

if(
document.memberForm.Relation_person.value==""
||document.memberForm.Telephone.value==""
||document.memberForm.Postcode.value==""
||document.memberForm.Delivery_address.value==""
){
alert('送货信息必须全部填写!');
return false;;
}
if(document.memberForm.Postcode.value==""||document.memberForm.Postcode.value.length!=6||isNaN(document.memberForm.Postcode.value)){
alert('邮编不能为空并且长度为6位数字!');
document.memberForm.Postcode.select();
return false;
}
document.memberForm.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">

<html:form action="/memberAddressModify.do" method="post">
	<%
	   Iterator it=memberAddCol.iterator();
	   MemberAddresses ma=new MemberAddresses();
	   while(it.hasNext()){
	   ma=(MemberAddresses)it.next();
	%>
<table width="95%" align="center" cellspacing="1" cellspacing="1" border="0" noWrap >
	<tr height="26">
		<!-- <td width="80">会员号：</td><td  width=90 bgcolor="#FFFFFF"><%=member.getCARD_ID()%></td> -->
		<td width="30%">联系人：&nbsp;&nbsp;<input type="text" name="Relation_person" value="<%=ma.getRelation_person()%>"> &nbsp;<font color="red">*</font></td>
		<td width="30%">电话号码：&nbsp;&nbsp;<input type="text" name="Telephone" value="<%=ma.getTelephone()%>" maxlength="15"> &nbsp;<font color="red">*</font></td>
		<td width="40%">电话2(可填多个)：&nbsp;&nbsp;<input type="text" name="telephone2" value="<%=ma.getTelephone2()%>" maxlength="25" ></td>
	</tr>
	<tr height="26">
		<td>邮 编：&nbsp;&nbsp;<input type="text" name="Postcode" value="<%=ma.getPostcode()%>" maxlength="6" size="10">&nbsp;<font color="red">*</font></td>
		<td colspan="2"> 省市区：&nbsp;&nbsp;
		<html:select property="province" onchange="listCity();">
		    <option value="">请选择</option>
			<html:optionsCollection property="provs" />
		</html:select>&nbsp;&nbsp;
		<html:select property="city" onchange="listSection();">
		    <option value="">请选择</option>
			<html:optionsCollection property="citys" />
		</html:select>&nbsp;&nbsp;
		<html:select property="section">
		    <option value="">请选择</option>
			<html:optionsCollection property="sects" />
		</html:select>&nbsp;<font color=red>*</font>
    </td>
	</tr>
	
	<tr height="26">
		<td colspan="3">送货地址：<textarea name="Delivery_address" cols="100"><%=ma.getDelivery_address()%></textarea>&nbsp;<font color="red">*</font></td>
	</tr>
</table>
<input type="hidden" name="address_id" value="<%=ma.getID()%>">
<br>
	<%}%>

<input type="hidden" name="type" value="4">

<input type="hidden" name="id" value="<%=member.getID()%>">
<center><input type="button" value="修改会员送货地址信息" onclick="modify_member_address()"></center>
</html:form>

	</table>
</body>
</html>
