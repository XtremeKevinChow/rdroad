<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function cancelOrder() {
	if(confirm("确定取消该订单？")) {
		document.forms[0].action = "orderCancel.do";
		document.forms[0].submit();
	}
}

function modifyOrder() {
	document.forms[0].action = "orderModifyFirst.do?type=init";
	document.forms[0].submit();
}

function cancelModifyOrder() {
	document.forms[0].action = "orderView.do";
	document.forms[0].actionType.value = "cancelModify";
	document.forms[0].submit();
}
function viewGifts(orderId) {
	openWin('./orderGiftsView.do?orderId='+orderId, 'PopWin', 320, 200);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	
    <td> <b><font color="#838383">当前位置：</font></b><font color="#838383"> 客户服务 
      -&gt; 订单详细信息</font> </td>
   </tr>
</table> -->

<html:form action="/orderView.do">
<bean:define id="cart" name="orderForm" property="cart" type="com.magic.crm.order.entity.ShoppingCart2"/>
<bean:define id="order" name="cart" property="order" type="com.magic.crm.order.entity.Order"/>
<bean:define id="member" name="cart" property="member" type="com.magic.crm.member.entity.Member"/>
<bean:define id="deliveryInfo" name="cart" property="deliveryInfo" type="com.magic.crm.order.entity.DeliveryInfo"/>
<bean:define id="otherInfo" name="cart" property="otherInfo" type="com.magic.crm.order.entity.OtherInfo"/>
  <table width="95%" align="center" cellspacing="0" border="0">
    <tr>
      <td><b>订单编号：</b><bean:write name="order" property="orderNumber"/>
	  <input type="hidden" name="orderId" value="<bean:write name="order" property="orderId" format="#0"/>">
	  </td>
	  <td><b>订单来源：</b><bean:write name="order" property="prTypeName"/></td>
	  <td><b>销售类型：</b>
	  <logic:equal name="order" property="orderType" value="10">普通订单</logic:equal>
	  <logic:equal name="order" property="orderType" value="5">预售订单</logic:equal>
	  <logic:equal name="order" property="orderType" value="15">团购订单</logic:equal>
	  <logic:equal name="order" property="orderType" value="20">换货单</logic:equal>
	  </td>
	  <td>
	  <b>订单状态：</b>
	  <bean:write name="order" property="statusName"/>
	  </td>
    </tr>
    
  </table>
  <table  align="center" width="95%" cellspacing="0" border="0">
    <tr> 
      <td><b>会员基本信息</b></td>
    </tr>
  </table>
  <table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
  <tr height="26"> 
    <td >会员号：</td>
    <td  bgcolor="#FFFFFF" ><a href="/member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a>
	
	<input type="hidden" name="mbId" value="<bean:write name="member" property="ID" format="#0"/>">
	</td>
    <td >会员姓名：</td>
    <td  bgcolor="#FFFFFF"><bean:write name="member" property="NAME"/></td>
    <logic:equal name="member" property="IS_ORGANIZATION" value="0" >
    <td>会员帐户:</td>
    <td bgcolor="#FFFFFF"><bean:write name="member" property="DEPOSIT" format="#0.00"/></td>
    <td>冻结帐户:</td>
    <td bgcolor="#FFFFFF"><bean:write name="member" property="FORZEN_CREDIT" format="#0.00"/></td>
    </logic:equal>
  </tr>
</table>
  <br>
  <table width="95%" align="center" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
    <tr class="OraTableRowHeader" noWrap  height="26"> 
      <td align="center"><b>货号</b></td>
      <td align="center"><b>名称</b></td>
      <td align="center"><b>销售方式</b></td>
      <td align="center"><b>颜色</b></td>
      <td align="center"><b>尺寸</b></td>
      <td align="center"><b>数量</b></td>
      <td align="center"><b>单位</b></td>
      <td align="center"><b>单价</b></td>
      <td align="center"><b>产品状态</b></td>
      <!--<td align="center"><b>到货日期</b></td>-->
      <td align="center"><b>合计</b></td>
    </tr>
    <bean:define id="items" name="cart" property="items" type="java.util.Collection"/> 
    <logic:iterate name="items" id="item"> 
    <tr > 
      <td align="center">&nbsp;<bean:write name="item" property="itemCode"/>
        <logic:notEmpty name="item" property="set_code">
        (<bean:write name="item" property="set_code"/>)
        </logic:notEmpty>
      </td>
      <td>&nbsp;<bean:write name="item" property="itemName"/></td>
      <td>&nbsp;<bean:write name="item" property="sellTypeName"/></td>
      <td align="left">&nbsp;<bean:write name="item" property="color_name"/>(<bean:write name="item" property="color_code"/>)</td>
      <td align="left">&nbsp;<bean:write name="item" property="size_code"/></td>
      <td align="right">&nbsp;<bean:write name="item" property="itemQty" format="#"/></td>
      <td align="left">&nbsp;<bean:write name="item" property="itemUnit"/></td>
      <td align="right">&nbsp;<bean:write name="item" property="itemPrice" format="#0.00"/></td>
      <td>&nbsp;<bean:write name="item" property="status"/></td>
      <!--<td align="center">&nbsp;<bean:write name="item" property="landDate"/></td>-->
      <td align="right">&nbsp;<bean:write name="item" property="itemMoney" format="#0.00"/></td>
    </tr>
    </logic:iterate> <bean:define id="gifts" name="cart" property="gifts" type="java.util.Collection"/> 
    <logic:iterate name="gifts" id="gift"> 
    <tr > 
      <td align="center">&nbsp;<bean:write name="gift" property="itemCode"/>
      <logic:notEmpty name="gift" property="set_code">
        (<bean:write name="gift" property="set_code"/>)
        </logic:notEmpty>
      </td>
      <td>&nbsp;<bean:write name="gift" property="itemName"/></td>
      <td>&nbsp;<bean:write name="gift" property="sellTypeName"/></td>
      <td align="left">&nbsp;<bean:write name="gift" property="color_name"/>(<bean:write name="gift" property="color_code"/>)</td>
        <td align="left">&nbsp;<bean:write name="gift" property="size_code"/></td>
      <td align="right">&nbsp;<bean:write name="gift" property="itemQty" format="#"/></td>
      <td align="left">&nbsp;<bean:write name="gift" property="itemUnit"/></td>
      <td align="right">&nbsp;<bean:write name="gift" property="itemPrice" format="#0.00"/></td>
      <td align="left">&nbsp;<bean:write name="gift" property="status"/></td>
      <!--<td align="center">&nbsp;<bean:write name="gift" property="landDate"/></td>-->
      <td align="right">&nbsp;<bean:write name="gift" property="itemMoney" format="#0.00"/></td>
    </tr>
    </logic:iterate>
    <tr> 
      <td colspan="9" align="right">正价金额</td>
      <td colspan="1" align="right">&nbsp;<bean:write name="order" property="normalFee" format="#0.00" ignore="true"/></td>
    </tr> 
    <tr> 
      <td colspan="9" align="right">购物金额：</td>
      <td colspan="1" align="right">&nbsp;<bean:write name="order" property="goodsFee" format="#0.00" ignore="true"/></td>
    </tr>
    <tr> 
      <td colspan="9" align="right">发送费：</td>
      <td colspan="1" align="right">&nbsp;<bean:write name="deliveryInfo" property="deliveryFee" format="#0.00" ignore="true"/></td>
    </tr>
	<tr>
		<td colspan="9" align="right">包装费：</td>
		
    <td colspan="1" align="right">&nbsp;<bean:write name="cart" property="packageFee" format="#0.00" ignore="true"/></td>
	</tr>
    <tr> 
      <td colspan="9" align="right">礼券抵用<a href="javascript:viewGifts('<bean:write name="orderForm" property="orderId" format="#"/>');">[查看]</a>：</td>

      <td colspan="1" align="right">&nbsp;-<bean:write name="order" property="appendFee" format="#0.00"/></td>
    </tr>
    <tr>
		<td colspan="9" align="right">付款方式折扣</td>
		<td align="right">&nbsp;<bean:write name="order" property="discount_fee" format="#0.00"/></td>
	</tr>
    <tr>
		<td colspan="9" align="right">帐户抵用：</td>
		
    <td colspan="1" align="right">&nbsp;-<bean:write name="order" property="orderUse" format="#0.00" ignore="true"/>
    (&nbsp;-<bean:write name="order" property="orderEmoney" format="#0.00" ignore="true"/>)
    </td>
	</tr>
  </table>
  <input type="hidden" name="actionType"><br>
  <table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
    <tr> 
      <td colspan="2"> <img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
        <font color="#990000"><b>送货地址信息</b></font> </td>
      <td width="180"> <img src="/images/gouwu/1dian.GIF" width="7" height="6"> 
        <font color="#990000"><b>送货方式</b></font> </td>
    </tr>
    <tr> 
      <td width="75">&nbsp;&nbsp;姓名：</td>
      <td width="350">&nbsp;<bean:write name="deliveryInfo" property="receiptor" ignore="true"/></td>
      <td width="180"> &nbsp;&nbsp;
      <bean:write name="deliveryInfo" property="deliveryType" ignore="true"/>
      <logic:equal name="deliveryInfo" property="deliveryType" value="邮寄">
      <input type="button" value="邮包" onclick="openWin('../order/showPostPackage.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 150);">
	  <logic:equal name="order" property="changed" value="true">
	  <input type="button" value="换货邮包" onclick="openWin('../order/showChangedPostPackage.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 150);">
	  </logic:equal>

	  <logic:equal name="order" property="supply" value="true">
	  <input type="button" value="补货邮包" onclick="openWin('../order/showSupplyPostPackage.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 150);">
	  </logic:equal>
      </logic:equal>
	  
      </td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;电话：</td>
      <td><bean:write name="deliveryInfo" property="phone" ignore="true"/>&nbsp;&nbsp;
		  <bean:write name="deliveryInfo" property="phone2" ignore="true"/>
	  </td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;送货地址：</td>
      <td>[<bean:write name="deliveryInfo" property="sectionName" ignore="true"/>]<bean:write name="deliveryInfo" property="address" ignore="true"/></td>
      <td> <img src="/images/gouwu/1dian.GIF" width="7" height="6"> <font color="#990000"><b>付款方式</b></font> 
      </td>
    </tr>
    <tr> 
      <td>&nbsp;&nbsp;邮编：</td>
      <td><bean:write name="deliveryInfo" property="postCode" ignore="true"/></td>
      <td> &nbsp;&nbsp;<bean:write name="deliveryInfo" property="paymentType" ignore="true"/> 
      </td>
    </tr>
  </table>
  <table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
    <tr> 
      <td>
        <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
      </td>
    </tr>
  </table>
  <table width="95%" border="0"  cellpadding="1" cellspacing="1" align="center">
    <tr> 
      <td width="50%"> <b><font color="#990000">缺货商品处理方式</font></b> </td>
      <td width="50%"> <b><font color="#990000">您需要发票吗？</font></b> </td>
    </tr>
    <tr> 
      <td>&nbsp; <logic:equal name="otherInfo" property="OOSPlan" value="2">全部商品到齐再发</logic:equal> 
        <logic:equal name="otherInfo" property="OOSPlan" value="3">不等待，取消订单中缺货商品</logic:equal></td>
      <td>&nbsp; 
      <logic:equal name="otherInfo" property="needInvoice" value="1">
      要 &nbsp;&nbsp;<bean:write name="otherInfo" property="invoice_number"/>&nbsp;&nbsp;
      <bean:write name="otherInfo" property="invoice_title"/>
      </logic:equal> 
        <logic:equal name="otherInfo" property="needInvoice" value="0">不要</logic:equal> 
      </td>
    </tr>
    <tr> 
      <td width="50%"> <b><font color="#990000">信用卡号</font></b> </td>
      <td width="50%"> <b><font color="#990000">身份证号</font></b> </td>
    </tr>
    <tr> 
      <td>&nbsp; 
        <bean:write name="otherInfo" property="credit_card" />
      </td>
      <td>&nbsp; 
        <bean:write name="otherInfo" property="id_card" />
      </td>
    </tr>
    <tr> 
      <td width="50%"> <b><font color="#990000">信用卡有效期年月</font></b> </td>
      <td width="50%"> <b><font color="#990000">信用卡CV2号码</font></b> </td>
    </tr>
    <tr> 
      <td>&nbsp; 
        <bean:write name="otherInfo" property="ef_year" />&nbsp;<bean:write name="otherInfo" property="ef_month" />
      </td>
      <td>&nbsp; 
        <bean:write name="otherInfo" property="ver_code" />
      </td>
    </tr>
    <tr> 
      <td colspan="2"><strong><font color="#990000">备注信息</font></strong><br>
	  <logic:empty name="otherInfo" property="remark">&nbsp;（无）</logic:empty>
	  <bean:write name="otherInfo" property="remark" filter="true" ignore="true"/>
      </td>
    </tr>
  </table>
  <TABLE width="95%" align="center">
	<tr>
		<td colspan="4"><strong><font color="#990000">相关目录</font></strong></td>
	</tr>
	
		
		<td>
			<bean:write name="otherInfo" property="catalog"/>
		</td>
	
</TABLE>
  <table width="95%" align="center" cellspacing="0" border="0"  >
	
	<tr><td height="10"></td></tr>
	<logic:equal parameter="isconsole" value="Y">
	<tr>
		<td align="center">
			<input name="BtnBack" type="button" value=" 返回 " onclick=ajaxpage2("../member/consoleOrders.do","ajaxcontentarea",document.forms[0]) >
			<logic:equal name="order" property="changeable" value="true"><input name="BtnChange" type="button" value="退换货" onclick=ajaxpage2("../order/orderChange.do?type=init","ajaxcontentarea",document.forms[0])> </logic:equal>
			<logic:equal name="order" property="modifiable" value="true"><input name="BtnModify" type="button" value="修改订单" onclick=ajaxpage2("../order/orderModifyFirst.do?type=init","ajaxcontentarea",document.forms[0])> </logic:equal>
			<logic:equal name="order" property="statusId" value="-6"><input name="BtnCancelModify" type="button" value="取消修改" onclick=ajaxpage2("../order/orderView.do?actionType=cancelModify","ajaxcontentarea",document.forms[0])></logic:equal>
			<logic:equal name="order" property="cancelable" value="true"><input name="BtnCancel" type="button" value="取消订单" onclick="javascript:if(confirm('确定取消该订单？')) { ajaxpage2('../order/orderCancel.do?actionType=cancelModify','ajaxcontentarea',document.forms[0]) }"></logic:equal>
			
			<logic:equal name="order" property="statusId" value="22">
			<!--
			<input name="BtnChangeComplete" type="button" value="收货确认" onclick=ajaxpage2("../order/orderChange.do?type=changeConfirm","ajaxcontentarea",document.forms[0])>
			-->
			<input name="BtnChangeCancel" type="button" value="退换货取消" onclick=ajaxpage2("../order/orderChange.do?type=changeCancel","ajaxcontentarea",document.forms[0])>
			</logic:equal>
			<!--
			<logic:equal name="order" property="returned" value="true"><input name="BtnSeeRet" type="button" value="退货原因" onclick="openWin('../order/showReturnReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
			<logic:equal name="order" property="changed" value="true"><input name="BtnSeeRet" type="button" value="换货原因" onclick="openWin('../order/showChangeReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
		    -->
		</td>
	</tr>
	</logic:equal>

	<logic:notEqual parameter="isconsole" value="Y">
	<tr>
		<td align="center">
			<input name="BtnBack" type="button" value=" 返回 " onclick=window.history.back() >
			<!--
			<logic:equal name="order" property="modifiable" value="true"><input name="BtnModify" type="button" value="修改订单" onclick=alert("请登陆会员服务台修改，谢谢!"); return;> </logic:equal>
			
			<logic:equal name="order" property="statusId" value="-6"><input name="BtnCancelModify" type="button" value="取消修改" onclick=cancelModifyOrder()></logic:equal>
			<logic:equal name="order" property="cancelable" value="true"><input name="BtnCancel" type="button" value="取消订单" onclick=cancelOrder()></logic:equal>
			<logic:equal name="order" property="returned" value="true"><input name="BtnSeeRet" type="button" value="退货原因" onclick="openWin('../order/showReturnReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
			<logic:equal name="order" property="changed" value="true"><input name="BtnSeeRet" type="button" value="换货原因" onclick="openWin('../order/showChangeReason.do?orderId=<bean:write name="orderForm" property="orderId" format="#"/>', '', 200, 100);"></logic:equal>
		    -->
		</td>
	</tr>
	</logic:notEqual>
</table>
<br>
</html:form>
</body>
</html>
