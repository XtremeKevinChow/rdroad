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
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function prom_Item(id) {
	location="prom_money4qty_add.jsp?promotionid="+id;
}

function validflag(tag,id,pid) {
        if(tag==1){
		if(confirm("是否把促销规则设置为有效")){
		    location="prom_money4qtyOperation.do?type=del&tag="+tag+"&promotionid="+pid+"&id="+id;
		}		      
        }else{
		if(confirm("是否把促销规则设置为无效")){
		    location="prom_money4qtyOperation.do?type=del&tag="+tag+"&promotionid="+pid+"&id="+id;
		}					       
        }

}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>当前位置</b> : 促销管理 -&gt; 产品促销列表</font></td>
  </tr>
</table>

<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >  
 <%
   if(err!=null&&err.equals("1")){
 %>	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><font color="red">新增促销产品失败：促销产品已存在</font></td>
	</tr>  
 <%}%>	
        
</table>
<br>
<table width="95%" align="center" cellspacing="1" border="0" >
	<tr height=30>
		<th   class="OraTableRowHeader" >金额</th>	
		<th   class="OraTableRowHeader">产品数量</th>	
		<th   class="OraTableRowHeader">状态</th>
		<th  class="OraTableRowHeader">&nbsp;</th>
	</tr>
    <logic:iterate id="prom_item" name="prom_itemCol" > 
	<tr align="center">
		<td class=OraTableCellText>&nbsp;<bean:write name="prom_item" property="money"/>&nbsp;元</td>
		<td class=OraTableCellText>任选&nbsp;<bean:write name="prom_item" property="qty"/>&nbsp;件</td>
		<td class=OraTableCellText>
		<logic:equal name="prom_item" property="flag" value="1">有效</logic:equal>
		<logic:equal name="prom_item" property="flag" value="0"><font color="red">失效</font></logic:equal>
		</td>
		<td class=OraTableCellText>
		<logic:equal name="prom_item" property="flag" value="0">
		<input type="button" value="启用" onclick="javascript:validflag(1,<bean:write name='prom_item' property='ID' format='#'/>,<bean:write name='prom_item' property='promotionID' format='#'/>)">
		</logic:equal>
		<logic:equal name="prom_item" property="flag" value="1">
		<input type="button" value="禁用" onclick="javascript:validflag(0,<bean:write name='prom_item' property='ID' format='#'/>,<bean:write name='prom_item' property='promotionID' format='#'/>)">
		</logic:equal>
		</td>
	</tr>    
    </logic:iterate>    
      
</table>
<br>
<table width="95%" align="center" cellspacing="1" border="0" >  
	
	<tr align="center">
		<td bgcolor="#FFFFFF" ><input type="button" value=" 新增规则 " onclick="javascript:prom_Item(<%=pid%>)"></td>

        
</table>

</body>
</html>
