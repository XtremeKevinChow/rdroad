<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.order.entity.ItemInfo"%>
<%@ page import="com.magic.crm.order.form.OrderForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="initHotkey();document.forms[0].queryItemCode.focus();">

<html:form action="/orderModifyFirst.do"> 
<bean:define id="cart" name="orderForm" property="cart" type="com.magic.crm.order.entity.ShoppingCart2"/>
<bean:define id="member" name="cart" property="member" type="com.magic.crm.member.entity.Member"/>
<html:hidden name="orderForm" property="orderId"/>
<input type="hidden" name="mbId" value="<bean:write name="member" property="ID" format="#0"/>">
<input type="hidden" name="maxqty">
<table width="95%" align="center" cellspacing="0" border="0"  >
  <tr> 
    <td>选择商品(M): 
      <input type="text" name="queryItemCode" size="18" onKeyDown="if(event.keyCode == 13) add_item_M();">
      <a href="javascript:getProduct();">
      <img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
      </a>
      <!--
      <input name="queryItemQty" size="4" onBlur="checkPositiveInteger(this);" value="1" maxlength="3"  onKeyDown="add_item_f_M();">
      -->
      <input type=hidden name="queryItemQty" value="1">
      &nbsp; 
      <input name="BtnAddItem" type="button" value=" 确定 " onClick="addItem_M();">
	  <logic:equal name="orderForm" property="recruitBtnActive" value="true">
	  <bean:define name="cart" property="activeMsc" id="activeMsc"/><!-- 按钮有效性控制 -->
	  <logic:iterate id="msc" name="activeMsc">
		<input name="selectRGift" type="button" value=<bean:write name="msc" property="msc_Code"/>  onclick="openWin('../order/orderAddSecond.do?type=showRecruitGroupPage&isAdd=false&msc=<bean:write name="msc" property="msc_Code"/>','exchangeGift',700,500);" >&nbsp;
	  </logic:iterate>
	  </logic:equal>
	 <!--  <input name="exchangeJJZGift2" type="button" value="钻兑礼"  onclick=openWin('../order/orderAddSecond.do?type=showDiamondExchangePage&isAdd=false','exchangeGift',700,500) >
	  <input name="exchangeJFGift2" type="button" value="积分兑礼"  onclick=openWin('../order/orderAddSecond.do?type=showExchangePage&isAdd=false','exchangeGift',700,500) >  -->
	  <!-- <input name="3yearGiftBtn" type="button" value="获取3周年纪念品"  onclick= ajaxpage2("../order/orderModifyFirst.do?type=add3YearGift","ajaxcontentarea",document.forms[0]);javascript:this.disabled="true"> -->
    </td>
	<TD ><font color="red" id="ajaxMessage">
	<%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%>
	</font></TD>
  </tr>
</table>
<table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
  <tr class="OraTableRowHeader" noWrap  height="26"> 
    <td align="center"><b>货号</b></td>
    <td align="center"><b>名称</b></td>
    <td align="center"><b>销售方式</b></td>
    <td align="center"><b>库存状态</b></td>
    <td align="center"><b>颜色</b></td>
    <td align="center"><b>尺码</b></td>
    <td align="center"><b>数量</b></td>
    <td align="center"><b>单价</b></td>
    <!--<td align="center"><b>到货日期</b></td>-->
    <td align="center"><b>合计</b></td>
    <td align="center"><B>操作</B></td>
  </tr>
  <%int item_index=0; %>
  <bean:define id="items" name="cart" property="items" type="java.util.Collection"/> 
  <logic:iterate name="items" id="item"> 
  <tr <logic:equal name="item" property="sku_id" value="0">
  bgcolor="yellow"
  </logic:equal>
  > <html:hidden name="item" property="sku_id"/>
    <td align="center">&nbsp;<bean:write name="item" property="itemCode"/> 
        <logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
      <html:hidden name="item" property="sku_id"/> </td>
    <td><bean:write name="item" property="itemName" ignore="true"/>      
    </td>
    <td><bean:write name="item" property="sellTypeName" ignore="true"/></td>
    
    <td>   
    <logic:notEqual name="item" property="stockStatusName" value="库存正常">
	<logic:notEqual name="item" property="stockStatusName" value="即将缺货">
    <font color=red><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
	</logic:notEqual>
    <logic:equal name="item" property="stockStatusName" value="库存正常">
    <bean:write name="item" property="stockStatusName" ignore="true"/>
    </logic:equal>
	<logic:equal name="item" property="stockStatusName" value="即将缺货">
    <font color=green><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:equal>
    </td>
     <td>
    <html:select name="item" property="color_code">
    <html:optionsCollection  name="item" property="colors" />
    </html:select>
    </td>
    <td>
    <html:select name="item" property="size_code">
    <html:optionsCollection  name="item" property="sizes" />
    </html:select>
    </td>
    <td align="center"> 
	<logic:equal name="item" property="sellTypeId" value="-1">
	<bean:write name="item" property="itemQty"/><input type="hidden" name="itemQty" value=<bean:write name="item" property="itemQty"/> />
	</logic:equal>

	<logic:notEqual name="item" property="sellTypeId" value="-1">
    <html:text name="item" property="itemQty" size="4" maxlength="3" onblur="checkPositiveInteger(this);"/> 
    </logic:notEqual>
    </td>
    
    <td align="right">
    <logic:equal name="item" property="isDiscount" value="1">
    <font color="red">[折扣]</font>
    </logic:equal>
    <bean:write name="item" property="itemPrice" format="#0.00" ignore="true"/> 
      <html:hidden name="item" property="itemPrice"/> 
      <logic:notEmpty name="item" property="set_code">
    (<bean:write name="item" property="set_price" format="#0.00" ignore="true"/>)
    </logic:notEmpty>
      </td>
    <!--<td align="center">&nbsp;<bean:write name="item" property="landDate" ignore="true"/></td>-->
    <td align="right" id="itemMoney"><bean:write name="item" property="itemMoney" format="#0.00" ignore="true"/></td>
    <td nowrap > 
      <input name="BtnDelete" value="删除" type="button" onclick=deleteItem_M(<%=item_index%>)>
      <logic:notEqual name="item" property="sku_id" value="0">
      <input name="BtnDelete" value="采购信息" type="button" onClick=pop_poinfo(<bean:write name="item" property="sku_id"/>)>
      </logic:notEqual>
	  <logic:notEqual name="item" property="sellTypeId" value="-1"><!-- 销售类型不是-1才显示 -->
      <input name="BtnUdate" value="确认" type="button" onclick=updateItem_M(<%=item_index%>)>
	  </logic:notEqual>
	</td>
  </tr>
  <% item_index++; %>
  </logic:iterate>
  <tr><td>正价产品金额：</td>
  <td colspan="5" align="right">&nbsp;</td>
  <td colspan="1" align="right">&nbsp;<bean:write name="cart" property="normalSaleQty" format="#0" ignore="true"/></td>
  <td colspan="2" align="right"><bean:write name="cart" property="normalSaleMoney" format="#0.00" ignore="true"/>  </td><td>&nbsp;</td></tr>
  <tr><td>产品金额：</td>
  <td colspan="5" align="right">&nbsp;</td>
  <td colspan="1" align="right">&nbsp;<bean:write name="cart" property="notGiftQty" format="#0" ignore="true"/></td>
  <td colspan="2" align="right"><bean:write name="cart" property="notGiftMoney" format="#0.00" ignore="true"/>  </td><td>&nbsp;</td></tr>
  
   <% item_index=0;%>
  <bean:define id="items" name="cart" property="gifts2" type="java.util.Collection"/> 
  <logic:iterate name="items" id="item"> 
  <tr noWrap <logic:equal name="item" property="sku_id" value="0">
  bgcolor="yellow"
  </logic:equal>
  > <html:hidden name="item" property="sku_id"/>
    <td align="center">
    <bean:write name="item" property="itemCode"/>
    <logic:notEmpty name="item" property="set_code">
    (<bean:write name="item" property="set_code"/>)
    </logic:notEmpty>
    </td>
    <td align="left"><bean:write name="item" property="itemName" ignore="true"/></td>
    <td align="left"><bean:write name="item" property="sellTypeName" ignore="true"/></td>
    <td align="left">
    <logic:notEqual name="item" property="stockStatusName" value="库存正常">
	<font color=red><bean:write name="item" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
    <logic:equal name="item" property="stockStatusName" value="库存正常">
    <bean:write name="item" property="stockStatusName" ignore="true"/>
    </logic:equal>
    </td>
    <td>
    <html:select name="item" property="color_code">
    <html:optionsCollection  name="item" property="colors" />
    </html:select>
    </td>
    <td>
    <html:select name="item" property="size_code">
    <html:optionsCollection  name="item" property="sizes" />
    </html:select>
    </td>
    <td align="center"> 
    <bean:write name="item" property="itemQty"/><input type="hidden" name="itemQty" value=<bean:write name="item" property="itemQty"/> />
	</td>
    <td align="right">
    <logic:equal name="item" property="isDiscount" value="1">
    <font color="red">[折扣]</font>
    </logic:equal>
    <bean:write name="item" property="itemPrice" format="#0.00" ignore="true"/> 
      <html:hidden name="item" property="itemPrice"/> 
      <logic:notEmpty name="item" property="set_code">
    (<bean:write name="item" property="set_price" format="#0.00" ignore="true"/>)
    </logic:notEmpty>
    </td>
    <!--<td align="center">&nbsp;<bean:write name="item" property="landDate" ignore="true"/></td>-->
    <td align="right" id="itemMoney"><bean:write name="item" property="itemMoney" format="#0.00" ignore="true"/></td>
    <td nowrap> 
      <input name="BtnDelete" value="删除" type="button" onClick=deleteGift2_M(<%= item_index%>)>
      <logic:notEqual name="item" property="sku_id" value="0">
      <input name="BtnDelete" value="采购信息" type="button" onClick="pop_poinfo(<bean:write name="item" property="sku_id"/>)">
      </logic:notEqual>
	  <logic:notEqual name="item" property="sellTypeId" value="-1">
      <input name="BtnUdate" value="确认" type="button" onClick="updateGift2_M(<%= item_index%>,this)">
	  </logic:notEqual>
	</td>
  </tr>
  <% item_index++; %>
  </logic:iterate>
  
  <% int gift_index=0; %>
  <bean:define id="gifts" name="cart" property="gifts" type="java.util.Collection"/> 
  <logic:iterate name="gifts" id="gift"> 
  <tr 
  <logic:equal name="gift" property="sku_id" value="0">
  bgcolor="yellow"
  </logic:equal>
  > <html:hidden name="gift" property="sku_id"/>
    <td align="center">&nbsp;
	<bean:write name="gift" property="itemCode"/> 
	<logic:notEmpty name="gift" property="set_code">
    (<bean:write name="gift" property="set_code"/>)
    </logic:notEmpty>
    <html:hidden name="gift" property="sku_id"/> </td>
    <td align="left"><bean:write name="gift" property="itemName" ignore="true"/>  
        
    </td>
    <td align="left"><bean:write name="gift" property="sellTypeName" ignore="true"/></td>
    <td align="left">
	<logic:notEqual name="gift" property="stockStatusName" value="库存正常">
	<logic:notEqual name="gift" property="stockStatusName" value="即将缺货">
    <font color=red><bean:write name="gift" property="stockStatusName" ignore="true"/></font>
    </logic:notEqual>
	</logic:notEqual>
	
    <logic:equal name="gift" property="stockStatusName" value="库存正常">
    <bean:write name="gift" property="stockStatusName" ignore="true"/>
    </logic:equal>
	<logic:equal name="gift" property="stockStatusName" value="即将缺货">
    <font color=green><bean:write name="gift" property="stockStatusName" ignore="true"/></font>
    </logic:equal>
    </td>
     <td>
    <html:select name="gift" property="color_code">
    <html:optionsCollection  name="gift" property="colors" />
    </html:select>
    </td>
    <td>
    <html:select name="gift" property="size_code">
    <html:optionsCollection  name="gift" property="sizes" />
    </html:select>
    </td>
    <td align="center">&nbsp;<bean:write name="gift" property="itemQty" format="#" ignore="true"/></td>
    <!--
    <td align="center">&nbsp;<bean:write name="gift" property="itemUnit" ignore="true"/></td>
    -->
    <td align="right">
     <bean:write name="gift" property="itemPrice" format="#0.00" ignore="true"/> 
      <html:hidden name="gift" property="itemPrice"/>
      <logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_price" format="#0.00" ignore="true"/>)
      </logic:notEmpty>
    </td>
    <!--<td align="center">&nbsp;<bean:write name="gift" property="landDate" ignore="true"/></td>-->
    <td align="right">&nbsp;<bean:write name="gift" property="itemMoney" format="#0.00" ignore="true"/>
    </td>
    <td nowrap> 
      <input name="BtnDelete" value="删除" type="button" onclick="deleteGift_M(<%=gift_index%>,<bean:write name="gift" property="sellTypeId"/>);">
      <!--
      <logic:greaterEqual name="gift" property="sellTypeId" value="4">
    		<logic:notEqual name="gift" property="sellTypeId" value="4" >
    				<input name="combout" value="永久删除" type="button" onClick="comboutGift_M(<%=gift_index%>);">
				</logic:notEqual>
    	</logic:greaterEqual>
    	-->
    	<logic:notEqual name="gift" property="sku_id" value="0">
      <input name="BtnDelete" value="采购信息" type="button" onClick=pop_poinfo(<bean:write name="gift" property="sku_id"/>)>
      </logic:notEqual>
      <input name="BtnDelete" value="确认" type="button" onClick="updateGift_M(<%=gift_index%>,this);">
    </td>
  </tr>
  <% gift_index++; %>
  </logic:iterate> 
  <tr><td>礼品金额：</td>
  <td colspan="5" align="right">&nbsp;</td>
  <td colspan="1" align="right">&nbsp;<bean:write name="cart" property="giftQty" format="#0" ignore="true"/></td>
  <td colspan="2" align="right"><bean:write name="cart" property="giftMoney" format="#0.00" ignore="true"/>  </td><td>&nbsp;</td>
  </tr>
  <tr><td>购物总金额：</td>
  <td colspan="5" align="right">&nbsp;</td>
  <td colspan="1" align="right">&nbsp;<bean:write name="cart" property="totalQty" format="#0" ignore="true"/></td>
  <td colspan="2" align="right"><bean:write name="cart" property="totalMoney" format="#0.00" ignore="true"/>  </td><td>&nbsp;</td>
  </tr>

  <tr class="OraTableRowHeader" noWrap  height="26"> 
    <td align="center"><B>礼券号</B></td>
    <td align="center"><B>礼券类型</B></td>
    <td align="center" colspan="3"><B>使用状态</B></td>
    <td align="center" colspan="3"><B>抵扣策略</B></td>
    <td align="center"><B>金额</B></td>
    <td width="80">&nbsp;</td>
  </tr>
  <bean:define id="ticketMoney" name="cart" property="tickets" />
  <logic:iterate name="ticketMoney" id="ticket"> 
  </tr>
	<tr height="26"> 
    <td align="left"><bean:write name="ticket" property="ticketCode"/></td>
    <td align="left">
	<logic:equal name="ticket" property="ticketType" value="1">
	入会礼券
	</logic:equal>
	<logic:equal name="ticket" property="ticketType" value="2">
	普通礼券
	</logic:equal>
	
	<logic:equal name="ticket" property="ticketType" value="4">
	公共电子礼券
	</logic:equal>
	<logic:equal name="ticket" property="ticketType" value="5">
	私有电子礼券
	</logic:equal>
	</td>

    <td colspan="3">
	<logic:equal name="ticket" property="useStatus" value="0">
	正常
	</logic:equal>
	<logic:equal name="ticket" property="useStatus" value="-1">
	<font color="red">异常</font>
	</logic:equal>
	</td>
    
	<td colspan="3">
	<logic:equal name="ticket" property="itemType" value="-1">
	所有正常销售产品
	</logic:equal>
	<logic:equal name="ticket" property="itemType" value="0">
	相应单品
	</logic:equal>
	<logic:equal name="ticket" property="itemType" value="5">
	礼品
	</logic:equal>
	<logic:equal name="ticket" property="itemType" value="6">
	其他
	</logic:equal>
	<bean:write name="ticket" property="itemTypeMoney" format="#0.00"/>
  <!--
	<logic:equal name="ticket" property="isDiscount" value="N">
	实际金额
	</logic:equal>
	<logic:equal name="ticket" property="isDiscount" value="Y">
	折扣
	</logic:equal>
	<logic:equal name="ticket" property="disType" value="1">
	单一订单
	</logic:equal>
	<logic:equal name="ticket" property="disType" value="2">
	累计订单
	</logic:equal>
	<logic:equal name="ticket" property="disType" value="0">
	无
	</logic:equal>
	-->
  </td>

    <td  align="right"><bean:write name="ticket" property="money" format="#0.00"/></td>
    <td width="80"><input name="" value="删除" type="button" onClick="deleteTicket_M('<bean:write name="ticket" property="ticketCode"/>');"></td>
  </tr>
  </logic:iterate>
  <tr > 
    <td colspan="8">礼券总计：</td>
    <td align="right">&nbsp;<bean:write name="cart" property="ticketKill" format="#0.00" ignore="true"/></td>
	<td>&nbsp;</td>
  </tr>

</table>
<table width="95%" align="center" cellspacing="0" border="0">
  <tr> 
    <td height="10"></td>
  </tr>
  <tr> 
    <td align="center"> 
      <input name="BtnClearForm" type="button" value="清空购物篮" onclick="clearItem_M();">
      <input name="BtnNextStep" type="button" value="下一步 &gt;&gt;" onclick="nextStep_M();">
    </td>
  </tr>
</table>
<input type="hidden" name="actionType" value="">
<input type="hidden" name="operateId" value="-1">
<input type="hidden" name="sellTypeId" value="-1">
<br>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td colspan=2 class=OraTableCellText height="34" width="35%">
    礼券号码：<html:text property="otherGiftNumber" />
    <a href="javascript:getGiftNumber();">
      <img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
    </a>
	<input type=button value=" 使 用 " onclick="validateTicket2_M();">
    </td>
  </tr>

  <tr> 
    <td class=OraTableCellText height="34" width="89%"><b>促销礼品选择</b></td>
    <td class=OraTableCellText height="34" align="right" width="11%"> 
      <input type="button" name="BtnAddGift" value="放入购物车" onclick="addGifts_M()">
    </td>
  </tr>
  <tr> 
    <td colspan="2"> 
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
</table>

<table align="center" cellspacing=1 cellpadding=3 width="98%"  border=0 id="promGiftTbl">
  <% double order_require = -1;
     int group_id = -1;
     int i=0;
  %>
  <bean:define id="gifts" name="cart" property="allGifts" type="java.util.Collection"/> 
  <logic:iterate name="gifts" id="gift" type="com.magic.crm.order.entity.ItemInfo"> 
  <%if( gift.getFloorMoney()!=order_require||gift.getGift_group_id()!=group_id ) {%>
  <tr> 
    <td colspan="2" valign="top">&nbsp;<b>购买[<bean:write name="gift" property="gift_group_name"/>]中的商品满 <bean:write name="gift" property="floorMoney" format="#"/> 元</b></td>
  </tr>
  <%
  	order_require = gift.getFloorMoney();
  	group_id = gift.getGift_group_id();
  	i++;
  }
  %>
  <tr> 
    <td width=20 valign="top"> 
      <logic:equal name="gift" property="catalog" value="老会员促销">
      <input type='radio' name='giftCode<%=i%>' value='<bean:write name="gift" property="awardId" format="#"/>'
      </logic:equal>
      <logic:notEqual name="gift" property="catalog" value="老会员促销">
      <input type='checkbox' name='giftCode<%=i%>' value='<bean:write name="gift" property="awardId" format="#"/>'
      </logic:notEqual>
      <logic:equal name="gift" property="selected" value="true"> checked disabled</logic:equal>
      <logic:equal name="gift" property="valid" value="false"> 
      disabled</logic:equal>> </td>
    <td><bean:write name="gift" property="itemCode" format="#"/><font color="#990000"><bean:write name="gift" property="itemName"/></font> 
      （加<font color='#006699'><b><u><bean:write name="gift" property="addy" format="#0.00" ignore="true"/></u></b></font>元）[<bean:write name="gift" property="catalog"/>]</td>
  </tr>
  </logic:iterate> 
  </table>
  
  <table align="center" cellspacing=1 cellpadding=3 width="98%"  border=0 >
  <tr> 
    <td class=OraTableCellText colspan="2" align="right"> 
       <input type="button" name="BtnAddGift" value="放入购物车" onclick="addGifts_M()">&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
</table>

<table align="center" cellspacing=1 cellpadding=3 width="98%"  border=0 >
<bean:define id="gifts2" name="cart" property="allGifts2" type="java.util.Collection"/>
<logic:iterate name="gifts2" id="gift2" type="com.magic.crm.order.entity.Proms2">
    <tr>
    <td colspan=2>&nbsp;<b>购买[<bean:write name="gift2" property="name"/>]中的商品</b>
    
    <bean:define id="moneys" name="gift2" property="money4qty" type="java.util.Collection"/>
    <bean:define id="items" name="gift2" property="items" type="java.util.Collection"/>
    
    <logic:iterate name="moneys" id="money" type="com.magic.crm.order.entity.Money4Qty">
    &nbsp;&nbsp;<font color="#990000"><bean:write name="money" property="money" format="#0.00"/></font> 元可任选 <font color="#990000"><bean:write name="money" property="qty" /></font> 件&nbsp;&nbsp;
    </logic:iterate>
    </td>
    </tr>
    <logic:iterate name="items" id="item" type="com.magic.crm.order.entity.ItemInfo">
    <tr>
    <td width="50%">&nbsp;&nbsp;<bean:write name="item" property="itemCode"/>&nbsp;&nbsp;<font color="#990000"><bean:write name="item" property="itemName"/></font></td>
    <td align="left"><input type="button" value="购买" onclick="addGifts2_M(<bean:write name="item" property="awardId"/>)">
    </td>
    </tr>
    </logic:iterate>
</logic:iterate>
</table>
</html:form> 
</body>
</html>
