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
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<link href="../css/ajaxtabs.css" rel="stylesheet" type="text/css" />

</head>
<body bgcolor="#FFFFFF" text="#000000" >
		
	
      	<logic:equal name="member" property="IS_ORGANIZATION" value="0">
  								
			<table width="100%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
				<tr height="22">
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">��Ա�ţ�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">��Ա�ţ�</font></logic:equal>
					
					</td><td  width=100 bgcolor="#FFFFFF"><bean:write name="member" property="CARD_ID"/></td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">������</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">������</font></logic:equal>
					
					</td><td  width=100 bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
					<td width="100">
					<logic:equal name="member" property="blacklistMember" value="false">�������ڣ�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�������ڣ�</font></logic:equal>
					
					</td><td  width=100 bgcolor="#FFFFFF"><bean:write name="member" property="BIRTHDAY"/></td>
					
					<td width=100 >
					<logic:equal name="member" property="blacklistMember" value="false">�����ʼ���</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�����ʼ���</font></logic:equal>
					</td><td  width=100 bgcolor="#FFFFFF">
					<bean:write name="member" property="EMAIL"/>
					</td>
					
				</tr>
                <tr height="22">
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">��Ա�ȼ���</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">��Ա�ȼ���</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="LEVEL_ID" value="1">��ͨ��Ա</logic:equal>
					<logic:equal name="member" property="LEVEL_ID" value="2">��ʽ��Ա</logic:equal>
					<logic:equal name="member" property="LEVEL_ID" value="3">VIP��Ա</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">���õ绰��</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">���õ绰��</font></logic:equal>
					</td><td  bgcolor="#FFFFFF"><bean:write name="member" property="TELEPHONE"/></td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">�����绰1��</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�����绰1��</font></logic:equal>
					
					</td><td  bgcolor="#FFFFFF"><bean:write name="member" property="FAMILY_PHONE"/></td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">�����绰2��</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�����绰2��</font></logic:equal>
					
					</td><td  bgcolor="#FFFFFF">
					<bean:write name="member" property="COMPANY_PHONE" />
					</td>
					<!--
					 <td>
					<logic:equal name="member" property="blacklistMember" value="false">�Ƿ񱻽��ã�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�Ƿ񱻽��ã�</font></logic:equal>
					</td>
					<td bgcolor="#FFFFFF">
			        <%
					if(member.getVALID_FLAG()!=null&&member.getVALID_FLAG().equals("N")){
					out.println("��");
					}else{
					out.println("��");
					}
					%>
					</td>
					-->
                </tr>
				<tr height="22">
				    <td>
					<logic:equal name="member" property="blacklistMember" value="false">�ʱࣺ</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�ʱࣺ</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF"><bean:write name="member" property="postcode"/>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">��Ա��ַ��</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">��Ա��ַ��</font></logic:equal>
					
					</td>
					<td colspan="5" bgcolor="#FFFFFF"><bean:write name="member" property="addressDetail"/></td>
					
				</tr>	
			

				<tr height="22">
					<td width="">
					<logic:equal name="member" property="blacklistMember" value="false">�Ա�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�Ա�</font></logic:equal>
					</td><td  width=90 bgcolor="#FFFFFF">
					<logic:equal name="member" property="GENDER" value="M">��</logic:equal>
					<logic:equal name="member" property="GENDER" value="F">Ů</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">֤�����ͣ�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">֤�����ͣ�</font></logic:equal>
					
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="0"></logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="1">���֤</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="2">ѧ��֤</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="3">����֤</logic:equal>
					<logic:equal name="member" property="CERTIFICATE_TYPE" value="4">����</logic:equal>
			        </td>
					<td >
					<logic:equal name="member" property="blacklistMember" value="false">֤���ţ�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">֤���ţ�</font></logic:equal>
					
					</td><td colspan="3" bgcolor="#FFFFFF">
					<font color="red">
					<bean:write name="member" property="CERTIFICATE_CODE"/>
					</font>
					</td>
				</tr>
				<tr height="22">
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">����Ŀ¼���ͣ�</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">����Ŀ¼���ͣ�</font></logic:equal>
					</td>
					<td  bgcolor="#FFFFFF">
					<logic:equal name="member" property="CATALOG_TYPE" value="0">ֽ���</logic:equal>
					<logic:equal name="member" property="CATALOG_TYPE" value="1">���Ӱ�</logic:equal>
					<logic:equal name="member" property="CATALOG_TYPE" value="2">û��Ŀ¼</logic:equal>
					</td>
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">��ļMSC��</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">��ļMSC��</font></logic:equal>
					</td><td  bgcolor="#FFFFFF"><bean:write name="member" property="MSC_CODE"/></td>	
					<td>
					<logic:equal name="member" property="blacklistMember" value="false">���ʱ�䣺</logic:equal>
					<logic:equal name="member" property="blacklistMember" value="true"><font color="red">���ʱ�䣺</font></logic:equal>
					</td><td colspan="3"  bgcolor="#FFFFFF"><bean:write name="member" property="CREATE_DATE"/>
					</td>
				</tr>

		</table>
		<br>
		<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
			<tr>

				  <td><font color="#990000"><b>�ʻ�</b></font></td>

				 
			</tr>
	
		</table>
		<table width="100%" align="center" cellspacing="1" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
			<tr height="22">
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">�ʻ���</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�ʻ���</font></logic:equal>
				
				</td><td width=90 bgcolor="#FFFFFF"><%=member.getDEPOSIT()%></td>
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">����</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">����</font></logic:equal>
				
				</td><td  width=90 bgcolor="#FFFFFF"><%=member.getFORZEN_CREDIT()%></td>
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">������</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">������</font></logic:equal>
				
				</td><td  width=90 bgcolor="#FFFFFF"><%=Arith.round(member.getDEPOSIT()-member.getFORZEN_CREDIT(), 2)%></td>
				<td width="80">
				<logic:equal name="member" property="blacklistMember" value="false">���������</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">���������</font></logic:equal>
				
				</td><td  width=90 bgcolor="#FFFFFF"><%=member.getPURCHASE_COUNT()%></td>
				
			</tr>
			<tr height="22">
				
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">�ۼƻ��֣�</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�ۼƻ��֣�</font></logic:equal>
				
				</td><td  bgcolor="#FFFFFF"><%=member.getEXP()%></td>
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">����Ȼ��֣�</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">����Ȼ��֣�</font></logic:equal>
				
				</td><td  bgcolor="#FFFFFF"><%=member.getAMOUNT_EXP()%></td>
				<td>
				<logic:equal name="member" property="blacklistMember" value="false">����Ȼ��֣�</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">����Ȼ��֣�</font></logic:equal>
				
				</td>
				<td  bgcolor="#FFFFFF"><%=member.getOLD_AMOUNT_EXP()%>
				
				</td>
				<td width="90">
				<logic:equal name="member" property="blacklistMember" value="false">�����˻�������</logic:equal>
				<logic:equal name="member" property="blacklistMember" value="true"><font color="red">�����˻�������</font></logic:equal>
				
				</td><td  width="90" bgcolor="#FFFFFF"><%=member.getANIMUS_COUNT()%></td>
			</tr>	
						
		</table>
		
      	</logic:equal>	


</body>
</html>
