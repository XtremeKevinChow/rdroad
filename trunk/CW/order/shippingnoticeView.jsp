<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function f_deleteShippingNotice() {
	if(confirm("ȷ��ɾ���÷�������")) {
		document.forms[0].action = "snDelete.do?sn_id=" + document.forms[0].sn_id.value;
		document.forms[0].submit();
	}
}
function f_print() {
	document.forms[0].action = "../crmjsp/shippingnotices_edit_action.jsp?act=print&doc_type=0&doc_id=<bean:write name="snMst" property="sn_id" format="#"/>";
	document.forms[0].submit();
}
function viewGifts(snId) {
	openWin('./snGiftsView.do?sn_id='+snId, 'PopWin', 320, 200);
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td align=left> <font color="#838383"><b>��ǰλ��</b> : ���۹��� -&gt; ��������ϸ</font></td>
  </tr>
</table>
<html:form action="/snView.do">

<bean:define id="snMst" name="snMst"/>
<bean:define id="order" name="snMst" property="order"/>
<bean:define id="member" name="order" property="member"/>
<bean:define id="deliveryInfo" name="order" property="deliveryInfo"/>
<table width="98%" cellspacing="0" cellpadding="4"  border="1" bordercolor="#FFFFFF" bordercolorlight="#cccccc" align="center">
	<tr>
		<html:hidden property="sn_id"/>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>��������</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="sn_code"/></td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>������</td>
		<td width="30%" class=OraTableCellText noWrap align=left><a href="orderView.do?orderId=<bean:write name="order" property="orderId" format="#"/>"><bean:write name="order" property="orderNumber"/></a>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>��Ա����</td>
		<td width="30%" class=OraTableCellText noWrap align=left><a href="../member/memberDetail.do?id=<bean:write name="member" property="ID" format="#"/>"><bean:write name="member" property="CARD_ID"/></a>&nbsp;</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>��Ա����</td>
		<td width="30%" class=OraTableCellText noWrap align=left><bean:write name="member" property="NAME"/>&nbsp;</td>
	</tr>

	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>��ϵ��</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="deliveryInfo" property="receiptor"/>&nbsp;</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>�ʱ�</td>
		<td width="30%" class=OraTableCellText noWrap align=left><bean:write name="deliveryInfo" property="postCode"/>&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right >�绰����</td>
		<td width="30%" class=OraTableCellText noWrap align=left><bean:write name="deliveryInfo" property="phone"/>&nbsp;</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>���ͷ�ʽ</td>
		<td width="30%" class=OraTableCellText noWrap align=left><bean:write name="deliveryInfo" property="deliveryType"/>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  align=right>��ַ</td>
		<td colspan=3 class=OraTableCellText  align=left><bean:write name="deliveryInfo" property="address"/>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>����</td>
		<td width="30%" class=OraTableCellText noWrap align=left><bean:write name="snMst" property="lot"/>&nbsp;</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right>״̬</td>
		<td width="30%" class=OraTableCellText noWrap align=left><bean:write name="snMst" property="status_name"/>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right  >��������</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="create_date"/>&nbsp;</td>		
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right  >��ӡʱ��</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="print_date"/>&nbsp;</td>
	</tr>

	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right  >���͹�˾</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="logistic_name"/>&nbsp;</td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right  >���䵥��</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="shipping_number"/>&nbsp;</td>	
		&nbsp;</td>
	</tr>
    <tr>
			
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right  >��Ʊ��</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="invoice_number"/></td>
		<td width="20%"  class="OraTableRowHeader" noWrap  noWrap align=right  >��Ʊ̧ͷ</td>
		<td width="30%" class=OraTableCellText noWrap align=left  ><bean:write name="snMst" property="invoice_title"/>&nbsp;</td>	
		&nbsp;</td>
	</tr>
	
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  align=right>��Ա��ע</td>
		<td colspan=3 class=OraTableCellText  align=left><bean:write name="snMst" property="remark"/>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"  class="OraTableRowHeader" noWrap  align=right>ϵͳ��ע</td>
		<td colspan=3 class=OraTableCellText  align=left><bean:write name="snMst" property="comments"/>&nbsp;</td>
	</tr>
</table>


<table width="98%" border="0" cellspacing="1" cellpadding="5" align="center">
		<tr>
			<td><span class="OraHeader">��������</span>
     		<table width="100%" border="0" cellspacing="0" cellpadding="0" background="images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
       		<tr background="images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        		<td height="1" width=100% background="images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"></td>
      		</tr>
    		</table>
   		</td>
		</tr>
</table>
<table cellspacing=2 cellpadding=5 width="98%"  border=0 align="center">
		<tbody>
			<tr>
				<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="10%">����</th> 
				<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="25%">��Ʒ����</th>
				<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="15%">����</th>
        		<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="5%">����</th>
        		<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="10%">�ϼ�</th>
				<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="15%">״̬</th>
				<th  class="OraTableRowHeader" noWrap  scope=col noWrap width="20%">��ע</th>
      		</tr>
			<logic:iterate name="snMst" property="items" id="dtl">
			<tr> 
		    	<td class=OraTableCellText noWrap align=center><bean:write name="dtl" property="item_code"/></td>			
				<td class=OraTableCellText noWrap align=left><bean:write name="dtl" property="item_name"/> </td>
				<td class=OraTableCellText noWrap align=right><bean:write name="dtl" property="price" format="#0.00"/></td>
				<td class=OraTableCellText noWrap align=right><bean:write name="dtl" property="qty" format="#"/></td>
				<td class=OraTableCellText noWrap align=right><bean:write name="dtl" property="total" format="#0.00"/></td>
				<td class=OraTableCellText noWrap align=left><bean:write name="dtl" property="status_name"/></td>
	        	<td class=OraTableCellText noWrap align=left><bean:write name="dtl" property="comments"/></td>
			</tr>
			</logic:iterate>
			<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>��Ʒ�ϼ�</td>
				<td class=OraTableCellText noWrap colspan="1"  align=right><bean:write name="snMst" property="goods_fee" format="#0.00"/></td>
				<td colspan=2>&nbsp;</td>
      		</tr>
      		<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>���ͷ�</td>
				<td class=OraTableCellText noWrap colspan="1"  align=right><bean:write name="deliveryInfo" property="deliveryFee" format="#0.00"/></td>
				<td colspan=2>&nbsp;</td>
      		</tr>
			<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>��װ��</td>
				<td class=OraTableCellText noWrap colspan="1"  align=right><bean:write name="snMst" property="packageFee" format="#0.00"/></td>
				<td colspan=2>&nbsp;</td>
      		</tr>
      		<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>��ȯ����<a href="javascript:viewGifts('<bean:write name="snMst" property="sn_id" format="#"/>');">[�鿴]</a></td>
				<td class=OraTableCellText noWrap colspan="1"  align=right><bean:write name="snMst" property="append_fee" format="#0.00"/></td>
				<td colspan=2>&nbsp;</td>
      		</tr>
      		<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>�����ۿ�</td>
				<td class=OraTableCellText noWrap colspan="1"  align=right><bean:write name="snMst" property="discount_fee" format="#0.00"/></td>
				<td colspan=2>&nbsp;</td>
      		</tr>
      		<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>�ʻ�����</td>
				<td class=OraTableCellText noWrap colspan="1"  align=right>-<bean:write name="snMst" property="payed_money" format="#0.00"/>'
				(-<bean:write name="snMst" property="payed_emoney" format="#0.00"/>)
				</td>
				<td colspan=2>&nbsp;</td>
      		</tr>
      		<tr>
				<td class=OraTableCellText  noWrap colspan="4" align=left>����Ӧ��</td>
				<td class=OraTableCellText noWrap colspan="1"  align=right><bean:write name="snMst" property="snOwe" format="#0.00"/></td>
				<td colspan=2>&nbsp;</td>
      		</tr>
		</tbody>
  </table>

  <table width="98%" border="0" cellpadding="2" cellspacing="0" align="center">
		
		<tr> 
			<td align="center">
     			<logic:equal name="snMst" property="cancelable" value="true">
				<input type="button" name="Submit243" value="ɾ��" onClick="f_deleteShippingNotice();">
				</logic:equal>
				<!--<input type="button" name="Submit243" class="button1" value="��ӡ" onClick="javascript:f_print();">-->
				
				<input type="button" name="Submit243"  value=" ���� " onclick="javascript:history.back();">
      </td>
    </tr>
  </table>
  <br>
</html:form>
</body>
</html>