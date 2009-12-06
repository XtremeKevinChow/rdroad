<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
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
/*if (document.forms[0].catalogCode.value=="" && document.forms[0].catalogName.value==""){
	alert("请输入查询条件！");
	return false;
}*/
return true;
}
function selectItem(){
	var selectedid = getSelectedItem();
	if(selectedid !="") {
		opener.getReturnCate(selectedid);
		self.close();
	}
	
}
function setfocus(){
	document.forms[0].catalogCode.focus();
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" onload="return setfocus();">
<%@ include file = "../common/page.jsp" %>
<html:form action="/prdCatQuery.do" method="post" onsubmit="return checkInput();" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">产品管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">产品分类查询</font><font color="838383"> 
      	</td>
   </tr> 
</table>


  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">
      	分类代码：<html:text name="productCategoryForm" property="catalogCode" size="12"/> 
        分类名：<html:text name="productCategoryForm" property="catalogName" size="16"/>
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" 查询 ">
		<input name="actn" type="hidden" value="1">
		</td>
	</tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<!--产品查询列表begin-->


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

  <table width="95%" onclick="changeItem()" ondblclick="selectItem()" align="center" border=0 cellspacing=1 cellpadding=2 >
    <tr height="26"> 
    	<th width="30%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >ID</th>
	  <th width="30%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >产品分类代码</th>
	  <th width="70%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >产品分类名称</th>
	</tr>
    
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="productCategory" name="modelList">
    
    <tr style="cursor:hand" id ="<bean:write name="productCategory" property="catalogID" filter="false"/>"> 
    	<td align=left ><bean:write name="productCategory" property="catalogID" filter="false"/></td>
      <td align=left ><bean:write name="productCategory" property="catalogCode" filter="false"/></td>
      <td align=left ><bean:write name="productCategory" property="catalogName" filter="false"/></td>
    </tr>
    </logic:iterate>
    <tr><td colspan="3"><font color="red">使用说明:请双击选中产品类别。</font></td></tr>
  </table>
  

</html:form>


</body>
</html>
