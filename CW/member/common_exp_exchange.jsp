<%@ page contentType="text/html;charset=GBK"%>

<TABLE width="90%" align=center>
<TR>
	<TD>&nbsp;本次兑换活动（<bean:write name="activity" property="activityNo"/>）为
	<logic:equal  name="activity" property="exchangeType" value="A">一次性兑换</logic:equal>
	<logic:equal  name="activity" property="exchangeType" value="B">实时兑换</logic:equal>
	
	<logic:equal  name="activity" property="dealType" value="A">兑换后积分清零</logic:equal>
	<logic:equal  name="activity" property="dealType" value="B">积分可多次兑换</logic:equal>
	
	</TD>
	
</TR>
</TABLE>
<table align="center" width="90%" border="0" cellSpacing=0 bordercolorlight="#cc3300" bordercolordark="#ffffff" cellpadding="3" id="detailTable">
	<!-- 积分档次 -->
	<bean:define id="stepMstList" name="activity" property="mstList"/>
	<logic:iterate id="stepMstList" name="stepMstList" > 
	<tr>
	    <td colspan="2" align="left"><strong><bean:write name="stepMstList" property="beginExp" format="#0"/>积分可以兑换一下礼品或礼券</strong></td>
	</tr>
	<tr><td height="5"></td></tr>
	<!-- 积分档次明细 -->
	<bean:define id="stepDtlList" name="stepMstList" property="dtlList"/>
	<logic:iterate id="stepDtlList" name="stepDtlList" > 
	<logic:equal name="stepDtlList" property="stepType" value="P">
	<tr>
		<td valign="top"><input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> value=<bean:write name="stepDtlList" property="id" format="#0"/>> <font color="blue">礼包 <bean:write name="stepDtlList" property="no"/></font></td>
	</tr>
	<tr>
		<td align=left align="right">
			<TABLE width="90%" border="0" align="right" cellSpacing=0 bordercolorlight="#DD9442" bordercolordark="#ffffff" cellpadding="3">
			
			<!-- 礼包 -->
			<bean:define id="packMst" name="stepDtlList" property="packMst"/>
			<!-- 礼包明细 -->
			<bean:define id="packDtlList" name="packMst" property="dtlList"/>
			
			<logic:iterate id="packDtlList" name="packDtlList" > 
			<TR>
				<TD>
				<logic:equal name="packDtlList" property="packageType" value="G">
				<!-- 礼品 -->
				<bean:define name="packDtlList" property="gift" id="gift"/>
				<bean:define name="gift" id="stock" property="stock"/><!-- 礼品库存 -->
				礼品
				<bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/>
				(<bean:write name="stock" property="statusName"/> )
				<bean:write name="packDtlList" property="quantity" format="#0" />个
				</logic:equal>
				
				<logic:equal name="packDtlList" property="packageType" value="T">
				礼券
				<bean:write name="packDtlList" property="no" />
				<bean:write name="packDtlList" property="quantity" format="#0" />个
				</logic:equal>
				
				</TD>
			</TR>
			
			</logic:iterate>
			</TABLE>
		
		</td>
	 </tr>
	 
	 </logic:equal>
	 <logic:equal name="stepDtlList" property="stepType" value="G">
	 <!-- 礼品 -->
	 <bean:define name="stepDtlList" property="gift" id="gift"/>
	 <tr>
		<td valign="top"><input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> 
		<font color="blue">礼品 <bean:write name="gift" property="itemCode"/> <bean:write name="gift" property="name"/> 
		</font></td>
		<td></td>
	 </tr>
	 <div id="demo2"  style="display:none">
    	 <tr>
    	    <td>
    	    颜色<select ></select>&nbsp;&nbsp;尺寸<select ></select>
    	    </td>
    	 </tr>
	 </div>
	 </logic:equal>
	 <logic:equal name="stepDtlList" property="stepType" value="T">
	 <tr>
		<td valign="top">
		<input type="radio" name="stepDtlId" <logic:equal name="stepDtlList" property="enabled" value="false">disabled</logic:equal> 
		value=<bean:write name="stepDtlList" property="id" format="#0"/>> 
		<font color="blue">礼券 <bean:write name="stepDtlList" property="no" /></font></td>
		<td></td>
	 </tr>
	 </logic:equal>
	</logic:iterate>
	</logic:iterate> 
</table>