<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String err=request.getParameter("err");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function add() {
	location="InitPromotion.do?type=promotion";
	
}
function prom_showCate(id) {
    location="promotionOperation.do?type=showCategory&id="+id;
}
function prom_Item(id) {
	//location="InitPromotion.do?type=prom_item&promotionid="+id;
	location="prom_ItemOperation.do?type=list&promotionid="+id;
}
function prom_Gift(id,name) {
	//location="InitPromotion.do?type=prom_gift&promotionid="+id;
	location="prom_GiftOperation.do?type=list&promotionid="+id+"&name="+name;
}
function prom_money4qty(id) {
	//location="InitPromotion.do?type=prom_gift&promotionid="+id;
	location="prom_money4qtyOperation.do?type=list&promotionid="+id;
}

function validflag(tag,id) {
        if(tag==1){
		if(confirm("�Ƿ�Ѵ�������Ϊ��Ч")){
		location="promotionOperation.do?type=del&tag="+tag+"&id="+id;
		}		      
        }else{
		if(confirm("�Ƿ�Ѵ�������Ϊ��Ч")){
		location="promotionOperation.do?type=del&tag="+tag+"&id="+id;
		}					       
        }
	//document.forms[0].action="promotionOperation.do?type=del&tag="+tag+"&id="+id;
	//document.forms[0].submit();
}

function initFocus(){
	document.forms[0].name.select();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="javascript:initFocus();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ������ѯ</font></td>
  </tr>
</table>
<html:form  method="post" action="promotionOperation.do?type=query">
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr align="left">
		<td bgcolor="#FFFFFF" >
		��������:<html:text property="name" />&nbsp;&nbsp;
		����ģʽ:<html:select property="flag">
			    <html:option value="">����</html:option>
		         <html:option value="1">ȫ������</html:option>
		         <html:option value="2">�������</html:option>
		         <html:option value="3">��Ʒ�����</html:option>
		         <html:option value="4">xxԪ��ѡx��</html:option>
		         </html:select>&nbsp;
		<input type="submit" value=" ��ѯ " >&nbsp;&nbsp;&nbsp;
		<input type="button" value=" ���� " onclick="javascript:add()">
		</td>
	</tr>    
 <%
   if(err!=null&&err.equals("1")){
 %>	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><font color="red">��������ʧ�ܣ���Ч�Ĵ��������Ѵ���</font></td>
	</tr>  
 <%}%>	      
</table>

 

<br>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
		<th width="30%"  class="OraTableRowHeader">��������</th>		
		<th width="12%"  class="OraTableRowHeader">��ʼ����</th>
		<th width="12%"  class="OraTableRowHeader">��������</th>
		<th width="8%"  class="OraTableRowHeader">����ģʽ</th>
		<th width="8%"  class="OraTableRowHeader">ʹ�÷�Χ</th>
		<th width="8%"  class="OraTableRowHeader">״̬</th>
		<th width=""  class="OraTableRowHeader">����;</th>
	</tr>
   
    <logic:iterate id="promotion" name="promotionCol" > 
	<tr align="center">
		<td class="OraTableCellText" align="left">
		<a href="/promotion/promotionOperation.do?type=initModify&id=<bean:write name='promotion' property='id' format='#'/>">
		<bean:write name="promotion" property="name"/></a></td>
		<td class="OraTableCellText"><bean:write name="promotion" property="beginDate"/></td>
		<td class="OraTableCellText"><bean:write name="promotion" property="endDate"/></td>
		<td class="OraTableCellText">
		<logic:equal name="promotion" property="flag" value="1">ȫ������</logic:equal>
		<logic:equal name="promotion" property="flag" value="2">�������</logic:equal>
		<logic:equal name="promotion" property="flag" value="3">��Ʒ�����</logic:equal>
		<logic:equal name="promotion" property="flag" value="4">xxԪ��ѡx��</logic:equal>
		</td>		
		<td class="OraTableCellText">
		<logic:equal name="promotion" property="putbasket" value="0">ȫ������</logic:equal>
		<logic:equal name="promotion" property="putbasket" value="1">�����Ͽ���</logic:equal>
		<logic:equal name="promotion" property="putbasket" value="2">�����¿���</logic:equal>
		</td>		
		<td class="OraTableCellText">
		<logic:equal name="promotion" property="validFlag" value="1">��Ч</logic:equal>
		<logic:equal name="promotion" property="validFlag" value="0"><font color="red">ʧЧ</font></logic:equal>
		</td>
	
		<td class="OraTableCellText"align="left" >
		<logic:equal name="promotion" property="validFlag" value="0">
		<input type="button" value="����" onclick="javascript:validflag(1,<bean:write name='promotion' property='id'/>)">
		</logic:equal>
		<logic:equal name="promotion" property="validFlag" value="1">
		<input type="button" value="����" onclick="javascript:validflag(0,<bean:write name='promotion' property='id'/>)">
		</logic:equal>
		
		<logic:notEqual name="promotion" property="flag" value="4">
		<input type="button" value="������Ʒ" onclick="javascript:prom_Gift(<bean:write name='promotion' property='id'/>,'<bean:write name="promotion" property="name"/>')">	
		</logic:notEqual>
		
		<logic:equal name="promotion" property="flag" value="2">		
		<input type="button" value="��������" onclick="javascript:prom_showCate(<bean:write name='promotion' property='id' />)">
		</logic:equal>	
		<logic:equal name="promotion" property="flag" value="3">		
		<input type="button" value="��Ʒ������" onclick="javascript:prom_Item(<bean:write name='promotion' property='id' />)">
		</logic:equal>
		<logic:equal name="promotion" property="flag" value="4">
		<input type="button" value="��������" onclick="javascript:prom_money4qty(<bean:write name='promotion' property='id' />)">		
		<input type="button" value="��Ӧ��Ʒ����" onclick="javascript:prom_Item(<bean:write name='promotion' property='id' />)">
		</logic:equal>	
		
			
		</nobr>
		</td>
	</tr>    
    </logic:iterate>        
</table>

</html:form>
</body>
</html>
