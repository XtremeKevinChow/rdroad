<%@ page contentType="text/html;charset=GBK"%>

<TABLE width="90%" align=center>
<TR>
	<TD>&nbsp;���ζһ����<bean:write name="sets" property="actionDesc"/>��
	����ڴӣ�<bean:write name="sets" property="beginDate"/>��<bean:write name="sets" property="endDate"/>
	</TD>
</TR>
</TABLE>
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	
	<bean:define id="exchangeList" name="sets" property="exchangeList"/>
	<!-- ���� -->
	<logic:iterate id="present" name="exchangeList" > 
	<tr>
	    <td colspan="2" align="left"><strong><bean:write name="present" property="exchangeCount" format="#0"/>������Զһ�������Ʒ����ȯ</strong></td>
	</tr>
	<tr><td height="5"></td></tr>
	
	<logic:equal name="present" property="giftType" value="1">
	<tr>
		<td valign="top"><input type="radio" name="excId" <logic:equal name="present" property="enabled" value="false">disabled</logic:equal> value=<bean:write name="present" property="excId" format="#0"/>> <font color="blue">��� <bean:write name="present" property="packageNo"/></font></td>
	</tr>
	<tr>
		<td align=left align="right">
			<TABLE width="90%" border="0" align="right" cellSpacing=0 bordercolorlight="#DD9442" bordercolordark="#ffffff" cellpadding="3">
			
			<!-- ��� -->
			<bean:define id="packMst" name="present" property="packMst"/>
			<!-- �����ϸ -->
			<bean:define id="packDtlList" name="packMst" property="dtlList"/>
			
			<logic:iterate id="packDtlList" name="packDtlList" > 
			<TR>
				<TD>
				<logic:equal name="packDtlList" property="packageType" value="G">
				<!-- ��Ʒ -->
				<bean:define name="packDtlList" property="gift" id="gift"/>
				<bean:define name="gift" id="stock" property="stock"/><!-- ��Ʒ��� -->
				��Ʒ
				<bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/>
				(<bean:write name="stock" property="statusName"/> )
				<bean:write name="packDtlList" property="quantity" format="#0" />��
				</logic:equal>
				
				<logic:equal name="packDtlList" property="packageType" value="T">
				��ȯ
				<bean:write name="packDtlList" property="no" />
				<bean:write name="packDtlList" property="quantity" format="#0" />��
				</logic:equal>
				
				</TD>
			</TR>
			</logic:iterate>
			</TABLE>
		
		</td>
	 </tr>
	 </logic:equal>
	
	 <logic:equal name="present" property="giftType" value="2">
	 <!-- ��Ʒ -->
	 <bean:define name="present" property="gift" id="gift"/>
	 <bean:define name="gift" id="stock" property="stock"/><!-- ��Ʒ��� -->
	 <tr>
		<td valign="top"><input type="radio" name="excId" <logic:equal name="present" property="enabled" value="false">disabled</logic:equal> value=<bean:write name="present" property="excId" format="#0"/>> <font color="blue">��Ʒ <bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/> (<bean:write name="stock" property="statusName"/>)</font></td>
		<td></td>
	 </tr>
	 </logic:equal>
	 <logic:equal name="present" property="giftType" value="3">
	 <tr>
		<td valign="top">
		<input type="radio" name="excId" <logic:equal name="present" property="enabled" value="false">disabled</logic:equal> value=<bean:write name="present" property="excId" format="#0"/>> <font color="blue">��ȯ <bean:write name="present" property="packageNo" /></font></td>
		<td></td>
	 </tr>
	 </logic:equal>
	</logic:iterate> 
</table>