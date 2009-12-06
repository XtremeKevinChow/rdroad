<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.user.entity.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%

User user=new User();
user = (User)session.getAttribute("user");

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

String cardId = request.getParameter("MB_CODE");
cardId = cardId == null ? "" : cardId;
%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">
function querySubmit() {

		if(document.memberaddmoneyForm.CREATE_DATE.value==""
			&&document.memberaddmoneyForm.MB_CODE.value==""
		){
				alert('请输入查询条件!');
				document.memberaddmoneyForm.MB_CODE.focus();
				return false;;
		}
		if(document.memberaddmoneyForm.CREATE_DATE.value!=""){
		var bdate = document.memberaddmoneyForm.CREATE_DATE.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 
		 if(bdate==null){
				alert('请按格式填写日期,并且注意你的日期是否正确!');
				document.memberaddmoneyForm.CREATE_DATE.focus();
				return false;
		 }		
		 }
	document.memberaddmoneyForm.search.disabled = true;


document.memberaddmoneyForm.submit();
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >


<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">帐户管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">充值历史查询</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form  action="/memberqueryFinanceMoney.do" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>
		开始日期：<html:text name="memberaddmoneyForm" property="CREATE_DATE" size="10"/>
		<a href="javascript:show_calendar(document.forms[0].CREATE_DATE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		&nbsp;&nbsp;
		结束日期：<html:text name="memberaddmoneyForm" property="endDate" size="10"/>
		<a href="javascript:show_calendar(document.forms[0].endDate)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
		(YYYY-MM-DD)	
		&nbsp;&nbsp;
		会员号：<html:text name="memberaddmoneyForm" property="MB_CODE" size="10"/>&nbsp;&nbsp;
		支付方式：<html:select property="payMethod" >
					<html:option value="-1">全部</html:option>
                    <html:optionsCollection property="payments" value="payMethod" label="payMethodName"/> 
				 </html:select>	
<input type="submit" name="search" value=" 查询 "></td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
<input type="hidden" name="tag" value="<%=tag%>">
<%
int i=0;
if (request.getMethod().equals("POST")||tag.equals("1") || !cardId.equals("")){
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


<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000">
	<tr>
		<td width="5%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>编号</b></td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>会员号</b></td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>姓名</b></td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>充值日期</b></td>
		<td width="15%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>摘要</b></td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>充值历史</b></td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>帐户余额</b></td>
		<td width="10%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>经办人</b></td>
		<td width="25%" class="OraTableRowHeader" noWrap  noWrap align=middle><b>充值凭证</b></td>
	</tr>

	<bean:define id="a" name="pageModel" property="modelList"/>
  <logic:iterate id="memberhistory" name="a" >
	<tr align="center">
		<td bgcolor="#FFFFFF">
		<%
		 if(user.getId().equals("214")){
		%>
		<!--<a href="modify_errMoney.jsp?id=<bean:write name="memberhistory" property="ID" format="#"/>&deposit=<bean:write name="memberhistory" property="MONEY_UPDATE" format="#0.00"/>&card_id=<bean:write name="memberhistory" property="CARD_ID"/>" target=_blank><bean:write name="memberhistory" property="ID" format="#"/></a>-->
		<%}else{%>
		<bean:write name="memberhistory" property="ID" format="#"/>
		<%}%>
		</td>
		<td bgcolor="#FFFFFF" align="center"><html:link page="/memberDetail.do" paramId="id" paramName="memberhistory" paramProperty="MEMBER_ID"><bean:write name="memberhistory" property="CARD_ID"/></html:link></td>
		<td bgcolor="#FFFFFF" align="left"><bean:write name="memberhistory" property="CARD_NAME"/></td>
		<td bgcolor="#FFFFFF" align="left"><bean:write name="memberhistory" property="MODIFY_DATE" /></td>
		
		<td bgcolor="#FFFFFF" align="left">
			<logic:empty name="memberhistory" property="payMethodName">
				<bean:write name="memberhistory" property="EVENT_TYPE_NAME" />
			</logic:empty>
			<logic:notEmpty name="memberhistory" property="payMethodName">
				<bean:write name="memberhistory" property="payMethodName" />
			</logic:notEmpty>
			
		</td>
		<td bgcolor="#FFFFFF" align="right"><bean:write name="memberhistory" property="MONEY_UPDATE" format="#0.00"/></td>
		<td  width="10%" bgcolor="#FFFFFF" align="right"><bean:write name="memberhistory" property="DEPOSIT" format="#0.00"/></td>
		<td bgcolor="#FFFFFF" align="left"><bean:write name="memberhistory" property="OPERATOR_NAME"/></td>
		<td bgcolor="#FFFFFF" align="left"><bean:write name="memberhistory" property="credence"/></td>
	</tr>
	<%i++;%>
</logic:iterate>
	<tr bgcolor="#FFFFFF">
		<td noWrap align=left colspan=5>总金额：</td>
		<td noWrap align=right ><%=money%><input type="hidden" name="money" value="<%=money%>"></td>
		<td noWrap align=right colspan=3>
		</td>

	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td  align="center">
		<%
			if(i>0){
		%>
		<a href="../member/memberqueryFinanceMoney.do?isExcel=1&tag=1&isquery=2&MB_CODE=<%=request.getParameter("MB_CODE")%>&CREATE_DATE=<%=request.getParameter("CREATE_DATE")%>&payMethod=<%=request.getParameter("payMethod")%>&endDate=<%=request.getParameter("endDate")%>" target=_black>
		导出记录</a>
		<%}%>
	</td>
</tr>
</table>
<%}%>
	
</html:form>

</body>
</html>
