<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String pageNo=request.getParameter("pageNo");
		String money="";
if(pageNo==null||pageNo.equals("1")){
		if(request.getAttribute("summoney")!=null){
			money=request.getAttribute("summoney").toString();
		}
}else{
   money=request.getParameter("money");
}
String rootPath = request.getContextPath();
String paytype=request.getParameter("payMethod");
if(paytype==null){
paytype="";
}
String tag=request.getParameter("tag");
if(tag==null){
tag="0";
}

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {

	if(document.memberaddmoneyForm.MB_CODE.value==""){
			alert('请输入查询条件!');
			document.memberaddmoneyForm.MB_CODE.focus();
			return false;;
	}

	document.memberaddmoneyForm.search.disabled = true;


document.memberaddmoneyForm.submit();
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >


<table width="98%" border="0" cellspacing="0" cellpadding="0" align=center>
    <tr>
      	<td align=left>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">帐户明细</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form  action="/memberqueryHistoryMoney.do" method="post" onsubmit="return querySubmit();">
<table width="98%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>会员号：<html:text name="memberaddmoneyForm" property="MB_CODE" size="10"/>&nbsp;&nbsp;
	
<input type="submit" name="search" value=" 查询 "></td>
		
	</tr>
</table>
<table width="98%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td>
      <hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%">
    </td>
  </tr>
</table>
<input type="hidden" name="isquery" value="1">
<input type="hidden" name="tag" value="<%=tag%>">
<%
int i=0;
if (request.getMethod().equals("POST")||tag.equals("1")){
%>
<%@ include file = "../common/page.jsp" %>
<bean:define id="pageModel" name="memberPageModel" scope="request" type="CommonPageUtil"/>
<%
    //取出总记录数和页码数
     int totalNum= 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>


<table width="98%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000">
	<tr>
		<!-- <td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle>编号</td> -->
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>会员号码</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>会员姓名</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>充值日期</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>摘要</td>
		
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>金额</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>帐户余额</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>经办人员</td>
		<td class="OraTableRowHeader" noWrap  noWrap align=middle>备注</td>
	</tr>

	<bean:define id="a" name="pageModel" property="modelList"/>
  <logic:iterate id="memberhistory" name="a" >
	<tr>
		<!-- <td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="ID" format="#"/></td> -->
		<td bgcolor="#FFFFFF" align="center">
			<html:link page="/memberDetail.do" paramId="id" paramName="memberhistory" paramProperty="MEMBER_ID" >
			<bean:write name="memberhistory" property="CARD_ID"/></html:link>
		</td>
		<td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="CARD_NAME"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="MODIFY_DATE" /></td>
		<td bgcolor="#FFFFFF">
			[<bean:write name="memberhistory" property="EVENT_TYPE_NAME" />]<bean:write name="memberhistory" property="payMethodName" />
		</td>
		<td bgcolor="#FFFFFF" align="right" nowrap><bean:write name="memberhistory" property="MONEY_UPDATE" format="#0.00"/></td>
		<td bgcolor="#FFFFFF" align="right"><bean:write name="memberhistory" property="DEPOSIT" format="#0.00"/></td>
		<td bgcolor="#FFFFFF"><bean:write name="memberhistory" property="OPERATOR_NAME"/></td>
		<td bgcolor="#FFFFFF" ><bean:write name="memberhistory" property="COMMENTS"/></td>
	</tr>
	<%i++;%>
</logic:iterate>
	
</table>
<%}%>
	
</html:form>

</body>
</html>
