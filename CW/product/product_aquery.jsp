<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">

function getCategory(){
	
	//openWin("/mrm/product/product_category_query.jsp","2005",600,400);
	openWin("prdCatQuery.do","2005",600,400);
	/*
	var vReturnValue = openModalDialog("/mrm/product/product_category_query.jsp",2005,600,400);
	if ((vReturnValue== -1)||(vReturnValue== null)){
 		alert("no");
	}else{
 		alert("yes");;
	}	      
	*/
}

function getProvider(para){
	
	document.forms[0].flag.value = para;
	openWin("providerQuery.do","wins",600,400);
	
	
}
function checkInput(){
	var form = document.forms[0];
	return true;
}



function viewProduct(itemID){
	openWin("productView.do?itemID="+itemID,"wins",720,480);
}

function productopen(itemID) { 　
	openWin("productModifyItem.do?actn=modify&itemID="+itemID,"wins",720,480);　
} 
function productopen2(itemID) { 　

	openWin("productViewItem.do?actn=view&item_id="+itemID,"wins",720,480);　
} 

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >

<%@ include file = "../common/page.jsp" %>
<%
CommonPageUtil pageModel = null;
String categoryID = "";
String productType = "";
String standardPrice1 = "";
String standardPrice2 = "";
String webPrice1 = "";
String webPrice2 = "";
String silverPrice1 = "";
String silverPrice2 = "";

String godenPrice1 = "";
String godenPrice2 = "";
String platina_Price1 = "";
String platina_Price2 = "";
String discount1 = "";
String discount2 = "";
String supplierID = "";
String publishingHouse = "";
String ifPresell = "";
String clubID = "";
String isSet = "";
String ageSegment = "";


if (request.getMethod().equals("POST")){
    pageModel = (CommonPageUtil)request.getAttribute(Constants.PAGE_MODEL);
    categoryID = (String)pageModel.getCondition().get("categoryID");
    standardPrice1 = (String)pageModel.getCondition().get("standardPrice1");
	standardPrice2 = (String)pageModel.getCondition().get("standardPrice2");
	webPrice1 = (String)pageModel.getCondition().get("webPrice1");
	webPrice2 = (String)pageModel.getCondition().get("webPrice2");
	silverPrice1 = (String)pageModel.getCondition().get("silverPrice1");
	silverPrice2 = (String)pageModel.getCondition().get("silverPrice2");
	godenPrice1 = (String)pageModel.getCondition().get("godenPrice1");
	godenPrice2 = (String)pageModel.getCondition().get("godenPrice2");
	platina_Price1 = (String)pageModel.getCondition().get("platina_Price1");
	platina_Price2 = (String)pageModel.getCondition().get("platina_Price2");	
	discount1 = (String)pageModel.getCondition().get("discount1");
	discount2 = (String)pageModel.getCondition().get("discount2");
	supplierID = (String)pageModel.getCondition().get("supplierID");
	publishingHouse = (String)pageModel.getCondition().get("publishingHouse");
	ifPresell = (String)pageModel.getCondition().get("ifPresell");
	clubID = (String)pageModel.getCondition().get("clubID");
	isSet = (String)pageModel.getCondition().get("isSet");
	ageSegment = (String)pageModel.getCondition().get("ageSegment");
}      
%>     
<%     
String actn = request.getParameter("actn");
%>     
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品高级查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productAQuery.do" method="post" onsubmit="return checkInput();">
  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
  	<tr> 
	    <td class="OraTableRowHeader" noWrap  >&nbsp;类型：&nbsp;&nbsp;	    
			<html:select name="product2Form" property="itemType" >
                  <html:option value="">--所有--</html:option>
          		  <html:option value="1">图书</html:option>
		          <html:option value="2">影视</html:option>
		          <html:option value="3">音乐</html:option>
		          <html:option value="4">游戏/软件</html:option>
		          <html:option value="5">礼品</html:option>
		          <html:option value="6">其他</html:option>
            </html:select>    
	
	    &nbsp;产品类别：
	    <input name="categoryID" size=8 value="<%=categoryID%>"><a href="javascript:getCategory();"> <img src="../images/icon_lookup.gif" border=0 align="top"></a>
	    </td>
  	</tr>
  	<tr> 
	    <td class="OraTableRowHeader" noWrap  >
	    	&nbsp;定价：&nbsp;&nbsp;
	    	<input type="text" name="standardPrice1" size=8 value="<%=standardPrice1%>">到<input type="text" name="standardPrice2" size=8 value="<%=standardPrice2%>">
	    	
	    </td>
	</tr>
	<tr>
	    <td class="OraTableRowHeader" noWrap  >
	    	&nbsp;网站价：
	    	<input type="text" name="webPrice1" size=8 value="<%=webPrice1%>">到<input type="text" name="webPrice2" size=8 value="<%=webPrice2%>">
	    </td>
  	</tr>
  	<tr>
	    <td class="OraTableRowHeader" noWrap  >
	    	&nbsp;银卡价：
	    	<input type="text" name="silverPrice1" size=8 value="<%=silverPrice1%>">到<input type="text" name="silverPrice2" size=8 value="<%=silverPrice2%>">
	    </td>
  	</tr>
  	<tr>
	    <td class="OraTableRowHeader" noWrap  >
	    &nbsp;金卡价：
	    	<input type="text" name="godenPrice1" size=8 value="<%=godenPrice1%>">到<input type="text" name="godenPrice2" size=8 value="<%=godenPrice2%>">
	    </td>
  	</tr>
  	<tr>
	    <td class="OraTableRowHeader" noWrap  >
	    &nbsp;白金卡价：
	    	<input type="text" name="platina_Price1" size=8 value="<%=platina_Price1%>">到<input type="text" name="platina_Price2" size=8 value="<%=platina_Price2%>">
	    </td>
  	</tr>  	
  	<tr>
	    <td class="OraTableRowHeader" noWrap  >
	    	&nbsp;折扣率：&nbsp;<input type="text" name="discount1" size=6 value="<%=discount1%>">到<input type="text" name="discount2" value="<%=discount2%>" size=6><font color=red>(注：填写小数)</font>
	    	&nbsp;供应商：
	    	<input type="text" name="supplierID" size=8><a href="javascript:getProvider('provider');">
      <img src="../images/icon_lookup.gif" width="22" height="23" border=0 align="top"></a>
	    	&nbsp;&nbsp;&nbsp;出版社：
	    	<input type="text" name="publishingHouse" size=8 value="<%=publishingHouse%>"> <a href="javascript:getProvider('publish');">
      			<img src="../images/icon_lookup.gif" border=0 align="top">
      			</a>
	    </td>
  	</tr>
  	  	
  	<tr> 
	    <td class="OraTableRowHeader" noWrap  >
	    	&nbsp;是否预售：
	    	<input type="radio" name="ifPresell" value="0" <%if(ifPresell !=null && ifPresell.equals("0")){out.println("checked");}%>>否
	    	<input type="radio" name="ifPresell" value="1"<%if(ifPresell !=null && ifPresell.equals("1")){out.println("checked");}%> >是  
	    	&nbsp;&nbsp;&nbsp;&nbsp;是否套装：
	    	<input type="radio" name="isSet" value="0" <%if(isSet !=null && isSet.equals("0")){out.println("checked");}%> >否
	    	<input type="radio" name="isSet" value="1" <%if(isSet !=null && isSet.equals("1")){out.println("checked");}%> >是   
	    	&nbsp;&nbsp;&nbsp;&nbsp;所属俱乐部：
			<input type="radio" name="clubID" value="1" <%if(clubID !=null && clubID.equals("1")){out.println("checked");}%> >99 
			<input type="radio" name="clubID" value="2" <%if(clubID !=null && clubID.equals("2")){out.println("checked");}%> >99妈咪宝贝
		</td>	    
  	</tr> 

  	<tr> 
	    <td class="OraTableRowHeader" noWrap  >
	    	&nbsp;适合年龄段： <input name="ageSegment" value="<%=ageSegment%>"/>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<input name="flag" type="hidden" value="">
	    	<input name="query" type="submit" value=" 查询 ">
	    </td>
    
  	</tr>
   
</table>

<%
if (request.getMethod().equals("POST")){
    //CommonPageUtil pageModel = (CommonPageUtil)request.getAttribute(Constants.PAGE_MODEL);
    //取出总记录数和页码数
    int totalNum = 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >

    <tr height="26"> 
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >货号</th>
      <th width="35%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品名称</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >产品类型</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >定价</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >银卡价</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >金卡价</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >白金卡价</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >网上价</th>
      
    </tr>
    
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="product" name="modelList">
    <tr style="cursor:hand" id ="<bean:write name="product" property="itemCode" filter="false"/>##<bean:write name="product" property="itemCode" filter="true"/>"> 
      <td noWrap align=middle >
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="itemCode" filter="true"/>
      	<%}else {%>
      	
      	<logic:equal name="product" property="isSet" value="1">
      	<!--
		      	<a href="../product/productViewItem.do?actn=view&item_id=<bean:write name='product' property='itemID' filter='true'/>" target=_blank>
		      		<bean:write name="product" property="itemCode" filter="true"/> 
		      	</a>      
		      	-->
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
      <td  noWrap align=right ><bean:write name="product" property="platina_Price" filter="false" format="0.00"/></td>
      <td  noWrap align=right ><bean:write name="product" property="webPrice" filter="false" format=".00"/></td>
      	      

    </tr>
    </logic:iterate>
    
  </table>
<%
}
%>
</html:form>

</body>
</html>
