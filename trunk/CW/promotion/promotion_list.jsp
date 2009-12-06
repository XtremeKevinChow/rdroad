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
<title>佰明会员关系管理系统</title>
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
		if(confirm("是否把促销设置为有效")){
		location="promotionOperation.do?type=del&tag="+tag+"&id="+id;
		}		      
        }else{
		if(confirm("是否把促销设置为无效")){
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
    <td> <font color="#838383"><b>当前位置</b> : 促销管理 -&gt; 促销查询</font></td>
  </tr>
</table>
<html:form  method="post" action="promotionOperation.do?type=query">
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr align="left">
		<td bgcolor="#FFFFFF" >
		促销名称:<html:text property="name" />&nbsp;&nbsp;
		促销模式:<html:select property="flag">
			    <html:option value="">所有</html:option>
		         <html:option value="1">全场促销</html:option>
		         <html:option value="2">分类促销</html:option>
		         <html:option value="3">产品组促销</html:option>
		         <html:option value="4">xx元任选x件</html:option>
		         </html:select>&nbsp;
		<input type="submit" value=" 查询 " >&nbsp;&nbsp;&nbsp;
		<input type="button" value=" 新增 " onclick="javascript:add()">
		</td>
	</tr>    
 <%
   if(err!=null&&err.equals("1")){
 %>	
	<tr align="left">
		<td bgcolor="#FFFFFF" ><font color="red">新增促销失败：有效的促销名称已存在</font></td>
	</tr>  
 <%}%>	      
</table>

 

<br>
<table width="95%" align="center" cellspacing="1" border="0"  class="OraTableRowHeader" noWrap >
	<tr>
		<th width="30%"  class="OraTableRowHeader">促销名称</th>		
		<th width="12%"  class="OraTableRowHeader">开始日期</th>
		<th width="12%"  class="OraTableRowHeader">结束日期</th>
		<th width="8%"  class="OraTableRowHeader">促销模式</th>
		<th width="8%"  class="OraTableRowHeader">使用范围</th>
		<th width="8%"  class="OraTableRowHeader">状态</th>
		<th width=""  class="OraTableRowHeader">操作;</th>
	</tr>
   
    <logic:iterate id="promotion" name="promotionCol" > 
	<tr align="center">
		<td class="OraTableCellText" align="left">
		<a href="/promotion/promotionOperation.do?type=initModify&id=<bean:write name='promotion' property='id' format='#'/>">
		<bean:write name="promotion" property="name"/></a></td>
		<td class="OraTableCellText"><bean:write name="promotion" property="beginDate"/></td>
		<td class="OraTableCellText"><bean:write name="promotion" property="endDate"/></td>
		<td class="OraTableCellText">
		<logic:equal name="promotion" property="flag" value="1">全场促销</logic:equal>
		<logic:equal name="promotion" property="flag" value="2">分类促销</logic:equal>
		<logic:equal name="promotion" property="flag" value="3">产品组促销</logic:equal>
		<logic:equal name="promotion" property="flag" value="4">xx元任选x件</logic:equal>
		</td>		
		<td class="OraTableCellText">
		<logic:equal name="promotion" property="putbasket" value="0">全部可用</logic:equal>
		<logic:equal name="promotion" property="putbasket" value="1">仅网上可用</logic:equal>
		<logic:equal name="promotion" property="putbasket" value="2">仅网下可用</logic:equal>
		</td>		
		<td class="OraTableCellText">
		<logic:equal name="promotion" property="validFlag" value="1">有效</logic:equal>
		<logic:equal name="promotion" property="validFlag" value="0"><font color="red">失效</font></logic:equal>
		</td>
	
		<td class="OraTableCellText"align="left" >
		<logic:equal name="promotion" property="validFlag" value="0">
		<input type="button" value="启用" onclick="javascript:validflag(1,<bean:write name='promotion' property='id'/>)">
		</logic:equal>
		<logic:equal name="promotion" property="validFlag" value="1">
		<input type="button" value="禁用" onclick="javascript:validflag(0,<bean:write name='promotion' property='id'/>)">
		</logic:equal>
		
		<logic:notEqual name="promotion" property="flag" value="4">
		<input type="button" value="促销礼品" onclick="javascript:prom_Gift(<bean:write name='promotion' property='id'/>,'<bean:write name="promotion" property="name"/>')">	
		</logic:notEqual>
		
		<logic:equal name="promotion" property="flag" value="2">		
		<input type="button" value="分类设置" onclick="javascript:prom_showCate(<bean:write name='promotion' property='id' />)">
		</logic:equal>	
		<logic:equal name="promotion" property="flag" value="3">		
		<input type="button" value="产品组设置" onclick="javascript:prom_Item(<bean:write name='promotion' property='id' />)">
		</logic:equal>
		<logic:equal name="promotion" property="flag" value="4">
		<input type="button" value="规则设置" onclick="javascript:prom_money4qty(<bean:write name='promotion' property='id' />)">		
		<input type="button" value="对应产品设置" onclick="javascript:prom_Item(<bean:write name='promotion' property='id' />)">
		</logic:equal>	
		
			
		</nobr>
		</td>
	</tr>    
    </logic:iterate>        
</table>

</html:form>
</body>
</html>
