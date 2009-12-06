<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.magic.crm.member.entity.*,com.magic.crm.user.entity.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
User user=new User();
user = (User)session.getAttribute("user");

Collection memberAddCol=(Collection)request.getAttribute("memberAddCol");
Member member=new Member();
member=(Member)request.getAttribute("member");

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function modifyMemberSubmit() {

if(document.memberForm.NAME.value==""){
alert('会员姓名不能为空!');
document.memberForm.NAME.focus();
return false;;
}
if(document.forms[0].BIRTHDAY.value==""||document.forms[0].BIRTHDAY.value.length!=8||isNaN(document.forms[0].BIRTHDAY.value)){
alert('出生日期不能为空并且长度为8位数字!!');
document.forms[0].BIRTHDAY.select();
return false;
}

if(document.forms[0].BIRTHDAY.value.substring(4,6)<1||document.forms[0].BIRTHDAY.value.substring(4,6)>12){
alert('出生日期的月份必须介于1-12之间!');
document.forms[0].BIRTHDAY.select();
return false;
}

if(document.forms[0].BIRTHDAY.value.substring(6,8)<1||document.forms[0].BIRTHDAY.value.substring(6,8)>31){
alert('出生日期的日子必须介于1-31之间!');
document.forms[0].BIRTHDAY.select();
return false;
}
/*
 var bdate = document.memberForm.BIRTHDAY.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
 if(bdate==null){
alert('请按YYYY-MM-DD格式填写出生日期!');
document.memberForm.BIRTHDAY.focus();
return false;
 }
 */
if(document.memberForm.FAMILY_PHONE.value=="" ){
alert('常用电话不能为空!');
document.memberForm.TELEPHONE.focus();
return false;;
}

if(document.memberForm.postcode.value==""||document.memberForm.postcode.value.length!=6||isNaN(document.memberForm.postcode.value)){
alert('邮编不能为空并且长度为6位数字!');
document.memberForm.postcode.focus();
return false;;
}
if(document.memberForm.addressDetail.value==""){
alert('会员地址不能为空!');
document.memberForm.addressDetail.focus();
return false;;
}

document.memberForm.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td width="280"><nobr>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">会员服务台</font><font color="838383"> </nobr>
      	</td>
      	<td align="right">
      		&nbsp;
      	</td>
   </tr>
</table>
 -->
<html:form action="/memberModify.do" method="post">
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		  <td class=OraTableCellText colspan=2><font color="#990000"><b>会员基本信息</b></font></td>
		</tr>
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="95%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr height="26">
		<td >姓名：</td><td   bgcolor="#FFFFFF"><html:text name="member" property="NAME" size="13"/></td>
		<td >性别：</td><td  bgcolor="#FFFFFF">
		<select name="GENDER">
			<option value="M"<%if(member.getGENDER().equals("M")){%>selected<%}%>>男</option>
			<option value="F"<%if(member.getGENDER().equals("F")){%>selected<%}%>>女</option>
		</select>	
		</td>
		<td >出生日期：</td><td  bgcolor="#FFFFFF"><html:text name="member" property="BIRTHDAY" size="13"/></td>
		
		<%
		if(user.getDEPARTMENT_ID()==999){
		%>	
		    <td>是否禁用</td>
		<td  bgcolor="#FFFFFF">
		<select name="VALID_FLAG">
			<option value="Y"<%if(member.getVALID_FLAG()==null||member.getVALID_FLAG().equals("Y")){%>selected<%}%>>否</option>		
			<option value="N"<%if(member.getVALID_FLAG()!=null&&member.getVALID_FLAG().equals("N")){%>selected<%}%>>是</option>
		</select>		
		</td>
		<%}else{%>
		<td>&nbsp;</td>
		<td  bgcolor="#FFFFFF"><input type="hidden" name="VALID_FLAG" value="<%=member.getVALID_FLAG()%>"></td>		
		<%}%>
		
	</tr>
	<tr height="26">
	    <td>常用电话：</td><td  bgcolor="#FFFFFF"><html:text name="member" property="TELEPHONE" size="13"/></td>
		<td>其他电话1：</td><td  bgcolor="#FFFFFF"><html:text name="member" property="FAMILY_PHONE" size="13"/></td>
		<td>其他电话2：</td><td  bgcolor="#FFFFFF"><html:text name="member" property="COMPANY_PHONE" size="13"/></td>
		<td>电子邮件：</td><td  bgcolor="#FFFFFF">
		<html:text name="member" property="EMAIL" size="13"/>
		</td>
	</tr>
	<tr height="26">
		
		<td>证件类型：</td><td  bgcolor="#FFFFFF">
		<select name="CERTIFICATE_TYPE">
			<option value="0"<%if(member.getCERTIFICATE_TYPE()==0){%>selected<%}%>>无</option>
			<option value="1"<%if(member.getCERTIFICATE_TYPE()==1){%>selected<%}%>>身份证</option>
			<option value="2"<%if(member.getCERTIFICATE_TYPE()==2){%>selected<%}%>>学生证</option>
			<option value="3"<%if(member.getCERTIFICATE_TYPE()==3){%>selected<%}%>>军官证</option>
			<option value="4"<%if(member.getCERTIFICATE_TYPE()==4){%>selected<%}%>>其他</option>
		</select>		

		</td>
		<td>证件号：</td><td  bgcolor="#FFFFFF">
        <html:text name="member" property="CERTIFICATE_CODE" size="18"/>
		</td>
		 <td>接收目录类型：</td>
		<td  bgcolor="#FFFFFF">
		<select name="CATALOG_TYPE">
			<option value="0"<%if(member.getCATALOG_TYPE()==0){%>selected<%}%>>纸面版</option>
			<option value="1"<%if(member.getCATALOG_TYPE()==1){%>selected<%}%>>电子版</option>
			<option value="2"<%if(member.getCATALOG_TYPE()==2){%>selected<%}%>>不要目录</option>
			<option value="3"<%if(member.getCATALOG_TYPE()==3){%>selected<%}%>>两者都要</option>
		</select>					
		</td>
		<td>邮编：</td><td  bgcolor="#FFFFFF"><html:text name="member" property="postcode" maxlength="6" size="13"/></td>		
	</tr>
	
		
		<input type=hidden name="club_id" value="1">
	
	
	<tr height="26">
	    <td > 省市区：</td>
	    <td bgcolor="#FFFFFF">
		<html:select property="province" onchange="listCity();">
		    <option value="">请选择</option>
			<html:optionsCollection property="provs" />
		</html:select>&nbsp;
		<html:select property="city" onchange="listSection();">
		    <option value="">请选择</option>
			<html:optionsCollection property="citys" />
		</html:select>&nbsp;
		<html:select property="section">
		    <option value="">请选择</option>
			<html:optionsCollection property="sects" />
		</html:select>
    </td>
		<td>会员地址：</td>
		<td colspan="5" bgcolor="#FFFFFF">
			<html:textarea name="member" property="addressDetail" cols="70"/>
				</td>
	</tr>
	<tr height="26">
	    <td>备注</td>
		<td colspan="5" bgcolor="#FFFFFF"><html:textarea name="member" property="COMMENTS" cols="70"/></td>
			<td>淘宝旺旺号</td>
			<td><html:text name="member" property="taobaoWangId"/></td>
	</tr>
</table>
<br>
<html:hidden name="member" property="ID"/>
<html:hidden name="member" property="CARD_ID"/>
<html:hidden name="member" property="ADDRESS_ID"/>
<html:hidden name="member" property="IS_ORGANIZATION"/>
<html:hidden name="member" property="MSC_CODE"/>
<html:hidden name="member" property="OLD_LEVEL"/>
<html:hidden name="member" property="CARD_TYPE"/>
<html:hidden name="member" property="CREATE_DATE"/>



<center><input type="button" value="修改会员基本信息" name="modifyBtn" onclick="modifyMemberSubmit()"></center>
</html:form>



<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		  <td class=OraTableCellText colspan=2><font color="#990000"><b>送货地址</b></font></td>
		</tr>
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>

		<th width="13%" class="OraTableRowHeader" noWrap  noWrap align=middle  >联系人</th>
		<th width="55%" class="OraTableRowHeader" noWrap  noWrap align=middle  >送货地址</th>
		<th width="13%" class="OraTableRowHeader" noWrap  noWrap align=middle  >联系电话</th>
		<th width="13%"class="OraTableRowHeader" noWrap  noWrap align=middle  >邮编</th>
		<th width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle  >&nbsp;</th>
	</tr>
	<%
	   Iterator it=memberAddCol.iterator();
	   MemberAddresses ma=new MemberAddresses();
	   while(it.hasNext()){
	   ma=(MemberAddresses)it.next();
	%>
	<tr>
		
		<td width="13%" class=OraTableCellText noWrap align=left ><%=ma.getRelation_person()%></td>
		<td width="" class=OraTableCellText noWrap align=left ><%=ma.getDelivery_address()%></td>
		<td width="13%" class=OraTableCellText noWrap align=left ><%=ma.getTelephone()%></td>
		<td width="13%" class=OraTableCellText noWrap align=right><%=ma.getPostcode()%></td>
		<td width="10%" class=OraTableCellText noWrap align=middle>
		<input value="修改" type="button" onclick=ajaxpage2("../member/memberInitModify.do?id=<%=member.getID()%>&type=1&address_id=<%=ma.getID()%>","ajaxcontentarea",document.forms[0])>
		<input value="删除" type="button" onclick=javascript:if(confirm("确实要删除吗?"))ajaxpage2("../member/memberInitModify.do?id=<%=member.getID()%>&type=2&address_id=<%=ma.getID()%>","ajaxcontentarea",document.forms[0])>
		</td>
	</tr>	
	<%}%>
	</table>
	<center><input value="增加会员送货信息" type="button" onclick=ajaxpage2("../member/memberInitModify.do?id=<%=member.getID()%>&type=3&address_id=<%=ma.getID()%>","ajaxcontentarea",document.forms[0])></center>

</body>
</html>
