<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="com.magic.utils.Arith"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
Member member=new Member();
member=(Member)request.getAttribute("member");
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<link href="../css/ajaxtabs.css" rel="stylesheet" type="text/css" />

</head>
<body bgcolor="#FFFFFF" text="#000000" >
		
	
      	<logic:equal name="member" property="IS_ORGANIZATION" value="0">
  								
			<table width="100%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
				<tr height="22">
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">会员号：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">会员号：</font></logic:equal>
					
					</td><td  width=100 bgcolor="#FFFFFF"><bean:write name="member" property="CARD_ID"/></td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">姓名：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">姓名：</font></logic:equal>
					
					</td><td  width=100 bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">出生日期：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">出生日期：</font></logic:equal>
					
					</td><td  width=100 bgcolor="#FFFFFF"><bean:write name="member" property="BIRTHDAY"/></td>
					
					<td width=100 >
					<logic:equal name="member" property="blacklistMember" value="false">电子邮件：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">电子邮件：</font></logic:equal>
					</td><td  width=100 bgcolor="#FFFFFF">
					<bean:write name="member" property="EMAIL"/>
					</td>
					
				</tr>
                <tr height="22">
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">会员等级：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">会员等级：</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="LEVEL_ID" value="1">普通会员</logic:equal>
					<logic:equal name="member" property="LEVEL_ID" value="2">正式会员</logic:equal>
					<logic:equal name="member" property="LEVEL_ID" value="3">VIP会员</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">常用电话：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">常用电话：</font></logic:equal>
					</td><td  bgcolor="#FFFFFF"><bean:write name="member" property="TELEPHONE"/></td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">其他电话1：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">其他电话1：</font></logic:equal>
					
					</td><td  bgcolor="#FFFFFF"><bean:write name="member" property="FAMILY_PHONE"/></td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">其他电话2：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">其他电话2：</font></logic:equal>
					
					</td><td  bgcolor="#FFFFFF">
					<bean:write name="member" property="COMPANY_PHONE" />
					</td>
					<!--
					 <td>
					<logic:equal name="member" property="blacklistMember" value="false">是否被禁用：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">是否被禁用：</font></logic:equal>
					</td>
					<td bgcolor="#FFFFFF">
			        <%
					if(member.getVALID_FLAG()!=null&&member.getVALID_FLAG().equals("N")){
					out.println("是");
					}else{
					out.println("否");
					}
					%>
					</td>
					-->
                </tr>
				<tr height="22">
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">邮编：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">邮编：</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF"><bean:write name="member" property="postcode"/>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">会员地址：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">会员地址：</font></logic:equal>
					
					</td>
					<td colspan="5" bgcolor="#FFFFFF"><bean:write name="member" property="addressDetail"/></td>
					
				</tr>	
			

				<tr height="22">
					<td width="">
					<logic:equal name="member" property="blacklistMember" value="false">性别：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">性别：</font></logic:equal>
					</td><td  width=90 bgcolor="#FFFFFF">
					<logic:equal name="member" property="GENDER" value="M">男</logic:equal>
					<logic:equal name="member" property="GENDER" value="F">女</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">证件类型：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">证件类型：</font></logic:equal>
					
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="0"></logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="1">身份证</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="2">学生证</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="3">军官证</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="4">其他</logic:equal>
			        </td>
					<td >
					<logic:equal name="member" property="blacklistMember" value="false">证件号：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">证件号：</font></logic:equal>
					
					</td><td colspan="3" bgcolor="#FFFFFF">
					<font color="red">
					<bean:write name="member" property="CERTIFICATE_CODE"/>
					</font>
					</td>
				</tr>
				<tr height="22">
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">接收目录类型：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">接收目录类型：</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="CATALOG_TYPE" value="0">纸面版</logic:equal>
					<logic:equal name="member" property="CATALOG_TYPE" value="1">电子版</logic:equal>
					<logic:equal name="member" property="CATALOG_TYPE" value="2">没有目录</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">招募MSC：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">招募MSC：</font></logic:equal>
					</td><td  bgcolor="#FFFFFF"><bean:write name="member" property="MSC_CODE"/></td>	
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">入会时间：</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">入会时间：</font></logic:equal>
					</td><td colspan="3"  bgcolor="#FFFFFF"><bean:write name="member" property="CREATE_DATE"/>
					</td>
				</tr>

		</table>
		<br>
		<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
			<tr>

				  <td><font color="#990000"><b>帐户</b></font></td>

				 
			</tr>
	
		</table>
		<table width="100%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
			<tr height="22">
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">帐户余额：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">帐户余额：</font></logic:equal>
				
				</td><td width=90 bgcolor="#FFFFFF"><%=member.getDEPOSIT()%></td>
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">冻结款：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">冻结款：</font></logic:equal>
				
				</td><td  width=90 bgcolor="#FFFFFF"><%=member.getFORZEN_CREDIT()%></td>
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">可用余额：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">可用余额：</font></logic:equal>
				
				</td><td  width=90 bgcolor="#FFFFFF"><%=Arith.round(member.getDEPOSIT()-member.getFORZEN_CREDIT(), 2)%></td>
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">购买次数：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">购买次数：</font></logic:equal>
				
				</td><td  width=90 bgcolor="#FFFFFF"><%=member.getPURCHASE_COUNT()%></td>
				
			</tr>
			<tr height="22">
				
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">累计积分：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">累计积分：</font></logic:equal>
				
				</td><td  bgcolor="#FFFFFF"><%=member.getEXP()%></td>
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">本年度积分：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">本年度积分：</font></logic:equal>
				
				</td><td  bgcolor="#FFFFFF"><%=member.getAMOUNT_EXP()%></td>
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">上年度积分：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">上年度积分：</font></logic:equal>
				
				</td>
				<td  bgcolor="#FFFFFF"><%=member.getOLD_AMOUNT_EXP()%>
				
				</td>
				<td width="90">
				<logic:equal name="member" property="blacklistMember" value="false">恶意退货次数：</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">恶意退货次数：</font></logic:equal>
				
				</td><td  width="90" bgcolor="#FFFFFF"><%=member.getANIMUS_COUNT()%></td>
			</tr>	
						
		</table>
		
      	</logic:equal>	


</body>
</html>
