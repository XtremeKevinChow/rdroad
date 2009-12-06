<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String isreport=request.getParameter("isreport");
       isreport=(isreport==null)?"":isreport;
%>

<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
	var form = document.forms[0];
	return true;
}

function selectItem(){

	var selectedid = getSelectedItem();
	
	if(selectedid !="") {
	      <%
	      if(isreport.equals("1")){
	      %>
	      //alert(selectedid.substring(0,selectedid.indexOf("##"));
		opener.document.forms[0].itemCode.value=selectedid.substring(0,selectedid.indexOf("##"));
		<%}else{%>
		var temp = selectedid.split("##");
//alert(temp);
		opener.getOpenwinValue(temp);
	      <%}%>
		self.close();
	}
	
}
function setfocus(){
	document.forms[0].itemCode.focus();
	return true;
}

function viewProduct(itemID){
	openWin("productView.do?itemID="+itemID,"wins",720,480);
}
function updatestock(itemID){
	openWin("../product/product_update_stock.jsp?itemID="+itemID,"wins",720,480);
}
function productopen(itemID) { 　
	openWin("productModifyItem.do?actn=modify&itemID="+itemID,"wins",720,480);　
} 
function productopen2(itemID) { 　

	openWin("productViewItem.do?actn=view&item_id="+itemID,"wins",720,480);　
} 
function modifyProduct(flag,itemID){
	var url;
	switch (flag){
		case "1":
			url = "productModify.do?itemID="+itemID;
			break;
		case "2":
			url = "modifyPrice.do?itemID="+itemID;
			break;
		case "3":
			url = "modifyBarcode.do?itemID="+itemID;
			break;
		case "4":
			var confirm=window.confirm("您确定要删除该评分卡吗？");
			if (!confirm){
				return false;
			}
			url = "productDelete.do?itemID="+itemID;
			break;
		case "5":
			url = "productModifyItem.do?actn=modify&item_id="+itemID;
			break;
		case "6":
			url = "productAView.do?itemID="+itemID;
			break;			
		
	}
	
	document.location.href=url;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">

<%@ include file = "../common/page.jsp" %>
<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productQuery.do" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">货号： 
        <html:text name="product2Form" property="itemCode" size="12"/>
        产品名： 
        <html:text name="product2Form" property="name" size="16"/>
        &nbsp; 
                <html:select name="product2Form" property="itemType" >
                                  <html:option value="">--所有--</html:option>
                  		  <html:option value="1">图书</html:option>
				          <html:option value="2">影视</html:option>
				          <html:option value="3">音乐</html:option>
				          <html:option value="4">游戏/软件</html:option>
				          <html:option value="5">礼品</html:option>
				          <html:option value="6">其他</html:option>
                </html:select>
        <br>
		
		作　者：
		<html:text name="product2Form" property="author" size="12"/>
		出版社：
		<html:text name="product2Form" property="publishingHouseName" size="20"/>
		&nbsp;
	
        <input name="query" type="submit" value=" 查询 ">
		<input name="actn" type="hidden" value="<%=actn%>">
		<input name="isreport" type="hidden" value="<%=isreport%>">
		</td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<!--产品查询列表begin-->
<%
if (request.getMethod().equals("POST")){
%>
<!--翻页处理-->
<bean:define id="pageModel" name="<%=Constants.PAGE_MODEL%>" scope="request" type="CommonPageUtil"/>
<%
    //取出总记录数和页码数
    int totalNum = 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>
<%if (actn !=null && actn.equals("selectProduct")){%>
  	<table width="95%" align="center" onclick="changeItem()" ondblclick="selectItem()" border=0 cellspacing=1 cellpadding=2 >
<%}else{%>
	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
<%}%>
    <tr height="26"> 
      <th width="14%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >货号</th>
      <th width="32%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品名称</th>
      <th width="11%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品类型</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >定价</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >银卡价</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >金卡价</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >白金卡价</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >网上价</th>
	  <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >出版社</th>
	  <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >作者</th>
	  
       <%if (actn !=null && !actn.equals("selectProduct")){%>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作</th>
      <%}%>
    </tr>
    
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="product" name="modelList">
    <tr style="cursor:hand" id ="<bean:write name="product" property="itemID" filter="false"/>##<bean:write name="product" property="itemCode" filter="true"/>##<bean:write name="product" property="name" filter="false" format="0"/>"> 
      <td noWrap align=middle >
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="itemCode" filter="true"/>
      	<%}else {%>
      	
      	<logic:equal name="product" property="isSet" value="1">
	      	<a href="javascript:productopen2('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="itemCode" filter="true"/> 
	      	</a> 			      		
      	</logic:equal>  
      	
      	<logic:equal name="product" property="isSet" value="0">
	      	<a href="javascript:viewProduct('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="itemCode" filter="true"/>
	      	</a>   	
      	</logic:equal>       	    	
      	<%}%>
      </td>
      <td  noWrap align="left">
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="name" filter="false"/>
      	<%}else{%>
      	<logic:equal name="product" property="isSet" value="1">

	      	<a href="javascript:productopen2('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="name" filter="false"/>  
	      	</a> 		      	     	
      	</logic:equal>
      	
      	<logic:equal name="product" property="isSet" value="0">
	      	<a href="javascript:viewProduct('<bean:write name="product" property="itemID" filter="true"/>')">
	      		<bean:write name="product" property="name" filter="false"/> 
	      	</a>   	
      	</logic:equal>      	

      	<%}%>

      </td>
      <td  noWrap align=left >
		<logic:equal name="product" property="type" value="1">图书</logic:equal>
		<logic:equal name="product" property="type" value="2">影视</logic:equal>     
		<logic:equal name="product" property="type" value="3">音乐</logic:equal>
		<logic:equal name="product" property="type" value="4">游戏/软件</logic:equal>    
		<logic:equal name="product" property="type" value="5">礼品</logic:equal>
		<logic:equal name="product" property="type" value="6">其他</logic:equal>    				
	 
      </td>
      <td  noWrap align=right ><bean:write name="product" property="standardPrice" filter="false" format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="silverPrice" filter="false" format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="godenPrice" filter="false" format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="platina_Price" filter="platina_Price" format=".00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="webPrice" filter="false" format=".00"/></td>
	  <td  noWrap align=right ><bean:write name="product" property="publishingHouseName" filter="false" /></td>
	  <td  noWrap align=right ><bean:write name="product" property="author" filter="false" /></td>
      	<logic:equal name="product" property="isSet" value="1">
			      <%if (actn !=null && !actn.equals("selectProduct")){%>
			      <td  noWrap align=right >
				  	<input type="button" name="modify" value="修改" onclick="javascript:modifyProduct('5','<bean:write name="product" property="itemID" filter="false"/>');">
				  	<input type="button" name="aa" value="同步库存" onclick="javascript:updatestock('<bean:write name="product" property="itemID" filter="false"/>');">
				  	
				  	</td>
				  <%}%>      	
      	</logic:equal>
      	
      	<logic:equal name="product" property="isSet" value="0">
			      <%if (actn !=null && !actn.equals("selectProduct")){%>
			      <td  noWrap align=right >
				  	<input type="button" name="modify" value="修改" onclick="javascript:modifyProduct('1','<bean:write name="product" property="itemID" filter="false"/>');">
				  	<input type="button" name="modify" value="价格" onclick="javascript:modifyProduct('2','<bean:write name="product" property="itemID" filter="false"/>');">
				  	<input type="button" name="modify" value="条码" onclick="javascript:modifyProduct('3','<bean:write name="product" property="itemID" filter="false"/>');">
				  	<input type="button" name="modify" value="详细信息" onclick="javascript:modifyProduct('6','<bean:write name="product" property="itemID" filter="false"/>');">
				  	<!--input type="button" name="del" value="删除" onclick="javascript:modifyProduct('4','<bean:write name="product" property="itemID" filter="false"/>');"-->
				  	
				  	</td>
				  <%}%>    	
      	</logic:equal>      	      

    </tr>
    </logic:iterate>
    
  </table>
<%
}
%>
</html:form>
<%if (request.getMethod().equals("POST")){%>
<table width="90%" align="center" border=0 cellspacing=1 cellpadding=2 >
	<tr>
		<td><font color=red>使用说明：双击产品行选择产品</font></td>
	</tr>
</table>
<%}%>
</body>
</html>
