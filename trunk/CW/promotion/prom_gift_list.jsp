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
String checkItem_Gift=request.getAttribute("checkItem_Gift").toString();
String name=request.getParameter("name");
name = name == null ? "" : name;
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function prom_Gift(id,name) {
	location="InitPromotion.do?type=prom_gift&promotionid="+id+"&name="+name;
}
function validflag(tag,id,pid) {
        if(tag==1){
		if(confirm("�Ƿ�Ѵ�����Ʒ����Ϊ��Ч")){
		location="prom_GiftOperation.do?type=del&tag="+tag+"&promotionid="+pid+"&id="+id+"&name=<%=name%>";
		}		      
        }else{
		if(confirm("�Ƿ�Ѵ�����Ʒ����Ϊ��Ч")){
		location="prom_GiftOperation.do?type=del&tag="+tag+"&promotionid="+pid+"&id="+id+"&name=<%=name%>";
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
		<td bgcolor="#FFFFFF" ><font color="red">����������Ʒʧ�ܣ�������Ʒ�ѱ���������ʹ�ã���������������Ʒ</font></td>
	</tr>  
 <%}%>	
 <%
   if(err!=null&&err.equals("2")){
 %>	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><font color="red">����������Ʒʧ�ܣ��������Ʒ������</font></td>
	</tr>  
 <%}%>	        
</table>
<br>

<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
		
		<th  class="OraTableRowHeader" >������Ʒ��</th>
		<th  class="OraTableRowHeader" >������Ʒ��</th>
		<th  class="OraTableRowHeader" >��xԪ</th>
		<th  class="OraTableRowHeader" >��yԪ</th>
		<!--		
		<th width="6%" class="OraTableRowHeader" >��Χ</th>
		<th width="10%" class="OraTableRowHeader" >��ʼ����</th>
		<th width="10%" class="OraTableRowHeader" >��������</th>
		-->		
		<th  class="OraTableRowHeader" >״̬</th>
		<th  class="OraTableRowHeader" >&nbsp;</th>
	</tr>
    <logic:iterate id="prom_gift" name="prom_giftCol" > 
	<tr>

		<td class=OraTableCellText>
		<a href="/promotion/prom_GiftOperation.do?type=initModify&id=<bean:write name='prom_gift' property='ID' format='#'/>">
		<bean:write name="prom_gift" property="item_name"/>
		</td>
		<td class=OraTableCellText><bean:write name="prom_gift" property="itemcode"/></td>
		<td class=OraTableCellText><bean:write name="prom_gift" property="overx" format="#0.0"/></td>
		<td class=OraTableCellText><bean:write name="prom_gift" property="addy" format="#0.0"/></td>
		<!--
		<td class=OraTableCellText>
		<logic:equal name="prom_gift" property="scope" value="1">��վ</logic:equal>		
		</td>
		<td class=OraTableCellText><bean:write name="prom_gift" property="beginDate"/></td>		
		<td class=OraTableCellText><bean:write name="prom_gift" property="endDate"/></td>
		-->
			
		<td class=OraTableCellText>
		<logic:equal name="prom_gift" property="validflag" value="1">��Ч</logic:equal>
		<logic:equal name="prom_gift" property="validflag" value="0"><font color="red">ʧЧ</font></logic:equal>
		</td>
		<td class=OraTableCellText align="center" >
		<logic:equal name="prom_gift" property="validflag" value="0">
		<input type="button" value="����" onclick="javascript:validflag(1,<bean:write name='prom_gift' property='ID' format='#'/>,<bean:write name='prom_gift' property='promotionID' format='#'/>)">
		</logic:equal>
		<logic:equal name="prom_gift" property="validflag" value="1">
		<input type="button" value="����" onclick="javascript:validflag(0,<bean:write name='prom_gift' property='ID' format='#'/>,<bean:write name='prom_gift' property='promotionID' format='#'/>)">	
		</logic:equal>
		</td>
	</tr>    
    </logic:iterate>    
      
</table>
<br>
<table width="95%" align="center" cellspacing="1" border="0">  
	
	<tr align="center">
	<% 
	   if(checkItem_Gift.equals("3")){
	  
	%>
		<td bgcolor="#FFFFFF" >�������ʱ������ά��������Ʒ����ά��������Ʒ</td>
		
        <%}else{%>
        	<td bgcolor="#FFFFFF" ><input type="button" value=" ������Ʒ " onclick="javascript:prom_Gift(<%=pid%>,'<%=name%>')"></td>
        <%}%>
        </tr>
        
</table>

</body>
</html>
