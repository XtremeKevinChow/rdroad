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
String pid=request.getParameter("promotionid");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function prom_Item(id) {
	location="InitPromotion.do?type=prom_item&promotionid="+id;
}

function validflag(tag,id,pid) {
        if(tag==1){
		if(confirm("�Ƿ�Ѵ�����Ʒ����Ϊ��Ч")){
		location="prom_ItemOperation.do?type=del&tag="+tag+"&promotionid="+pid+"&id="+id;
		}		      
        }else{
		if(confirm("�Ƿ�Ѵ�����Ʒ����Ϊ��Ч")){
		location="prom_ItemOperation.do?type=del&tag="+tag+"&promotionid="+pid+"&id="+id;
		}					       
        }

}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �������� -&gt; ��Ʒ�����б�</font></td>
  </tr>
</table>

<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
 <%
   if(err!=null&&err.equals("1")){
 %>	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><font color="red">����������Ʒʧ�ܣ�������Ʒ�Ѵ���</font></td>
	</tr>  
 <%}%>	
 <%
   if(err!=null&&err.equals("2")){
 %>	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><font color="red">����������Ʒʧ�ܣ�����Ĳ�Ʒ������</font></td>
	</tr>  
 <%}%>	        
</table>
<br>
<table width="95%" align="center" cellspacing="1" border="0" >
	<tr height=30>
		<th   class="OraTableRowHeader" >��������</th>	
		<th   class="OraTableRowHeader">�����</th>	
		<th   class="OraTableRowHeader">���Ʒ��</th>
		
		<th   class="OraTableRowHeader">״̬</th>
		<th  class="OraTableRowHeader">&nbsp;</th>
	</tr>
    <logic:iterate id="prom_item" name="prom_itemCol" > 
	<tr align="center">
		<td class=OraTableCellText ><bean:write name="prom_item" property="promotionName"/></td>
		
		<td class=OraTableCellText><bean:write name="prom_item" property="itemcode"/></td>
		<td class=OraTableCellText><bean:write name="prom_item" property="item_name"/></td>
		<td class=OraTableCellText>
		<logic:equal name="prom_item" property="flag" value="1">��Ч</logic:equal>
		<logic:equal name="prom_item" property="flag" value="0"><font color="red">ʧЧ</font></logic:equal>
		</td>
		<td class=OraTableCellText>
		<logic:equal name="prom_item" property="flag" value="0">
		<input type="button" value="����" onclick="javascript:validflag(1,<bean:write name='prom_item' property='ID' format='#'/>,<bean:write name='prom_item' property='promotionID' format='#'/>)">
		</logic:equal>
		<logic:equal name="prom_item" property="flag" value="1">
		<input type="button" value="����" onclick="javascript:validflag(0,<bean:write name='prom_item' property='ID' format='#'/>,<bean:write name='prom_item' property='promotionID' format='#'/>)">
		</logic:equal>
		</td>
	</tr>    
    </logic:iterate>    
      
</table>
<br>
<table width="95%" align="center" cellspacing="1" border="0" >  
	
	<tr align="center">
		<td bgcolor="#FFFFFF" ><input type="button" value=" ������Ʒ " onclick="javascript:prom_Item(<%=pid%>)"></td>

        
</table>

</body>
</html>
