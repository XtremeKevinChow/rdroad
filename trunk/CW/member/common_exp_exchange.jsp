<%@ page contentType="text/html;charset=GBK"%>

<TABLE width="90%" align=center>
<TR>
	<TD>&nbsp;���ζһ����<bean:write name="activity" property="activityNo"/>��Ϊ
	<logic:equal  name="activity" property="exchangeType" value="A">һ���Զһ�</logic:equal>
	<logic:equal  name="activity" property="exchangeType" value="B">ʵʱ�һ�</logic:equal>
	
	<logic:equal  name="activity" property="dealType" value="A">�һ����������</logic:equal>
	<logic:equal  name="activity" property="dealType" value="B">���ֿɶ�ζһ�</logic:equal>
	
	</TD>
	
</TR>
</TABLE>
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	<!-- ���ֵ��� -->
	<bean:define id="stepMstList" name="activity" property="mstList"/>
	<logic:iterate id="stepMstList" name="stepMstList" > 
	<tr>
	    <td colspan="2" align="left"><strong><bean:write name="stepMstList" property="beginExp" format="#0"/>���ֿ��Զһ�һ����Ʒ����ȯ</strong></td>
	</tr>
	<tr><td height="5"></td></tr>
	<!-- ���ֵ�����ϸ -->
	<bean:define id="stepDtlList" name="stepMstList" property="dtlList"/>
	<logic:iterate id="stepDtlList" name="stepDtlList" > 
	<logic:equal name="stepDtlList" property="stepType" value="P">
	<tr>
		<td valign="top"><input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> value=<bean:write name="stepDtlList" property="id" format="#0"/>> <font color="blue">��� <bean:write name="stepDtlList" property="no"/></font></td>
	</tr>
	<tr>
		<td align=left align="right">
			<TABLE width="90%" border="0" align="right" cellSpacing=0 bordercolorlight="#DD9442" bordercolordark="#ffffff" cellpadding="3">
			
			<!-- ��� -->
			<bean:define id="packMst" name="stepDtlList" property="packMst"/>
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
	 <logic:equal name="stepDtlList" property="stepType" value="G">
	 <!-- ��Ʒ -->
	 <bean:define name="stepDtlList" property="gift" id="gift"/>
	 <tr>
		<td valign="top"><input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> 
		<font color="blue">��Ʒ <bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/> 
		</font></td>
		<td></td>
	 </tr>
	 <div id="demo2"  style="display:none">
    	 <tr>
    	    <td>
    	    ��ɫ<select ></select>&nbsp;&nbsp;�ߴ�<select ></select>
    	    </td>
    	 </tr>
	 </div>
	 </logic:equal>
	 <logic:equal name="stepDtlList" property="stepType" value="T">
	 <tr>
		<td valign="top">
		<input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> 
		<font color="blue">��ȯ <bean:write name="stepDtlList" property="no" /></font></td>
		<td></td>
	 </tr>
	 </logic:equal>
	</logic:iterate>
	</logic:iterate> 
</table>