<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    response.setHeader("expires","0");
    response.setHeader("Cache-Control", "no-store"); //http1.1
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache"); //http1.0
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
		opener.document.forms[0].replaceItemID.value=selectedid;
		
		self.close();
	}
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<%@ include file = "../common/page.jsp" %>
<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">客户服务</font><font color="838383"> 
      		-&gt; </font><font color="838383">订单查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productQuery.do" method="post" onsubmit="return checkInput();">

  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">货号： 
        <html:text name="product2Form" property="itemCode" size="12"/>
        &nbsp; 产品名： 
        <html:text name="product2Form" property="name" size="16"/>
        &nbsp; 产品类型： <select name="itemType">
          <option value="1">图书</option>
          <option value="2">影视</option>
          <option value="3">音乐</option>
          <option value="4">游戏/软件</option>
          <option value="5">礼品</option>
          <option value="6">其他</option>
        </select>
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" 查询 ">
		<input name="actn" type="hidden" value="<%=actn%>">
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
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >网上价</th>
       <%if (actn !=null && actn.equals("selectProduct")){%>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >操作</th>
      <%}%>
    </tr>
    
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="product" name="modelList">
    <tr style="cursor:hand" id ="<bean:write name="product" property="itemCode" filter="false"/>"> 
      <td class=OraTableCellText noWrap align=middle >
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="itemCode" filter="true"/>
      	<%}else {%>
      	<a href="order_view.html">
      		<bean:write name="product" property="itemCode" filter="true"/>
      	</a>
      	<%}%>
      </td>
      <td class=OraTableCellText noWrap align="left">
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="name" filter="false"/>
      	<%}else{%>
      	<a href="../member/member_desktop.html">
      		<bean:write name="product" property="name" filter="false"/>
      	</a>
      	<%}%>
      </td>
      <td class=OraTableCellText noWrap align=left >图书</td>
      <td class=OraTableCellText noWrap align=right >30</td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="silverPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="godenPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="webPrice" filter="false" format=".00"/></td>
      <%if (actn !=null && !actn.equals("selectProduct")){%>
      <td class=OraTableCellText noWrap align=right >
	  	<input type="button" name="modify" value="修改"><input type="button" name="del" value="删除">
	  	
	  </td>
	  <%}%>
    </tr>
    </logic:iterate>
    
  </table>
<%
}
%>
</html:form>


</body>
</html>
