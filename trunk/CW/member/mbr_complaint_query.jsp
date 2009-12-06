<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@page import="com.magic.crm.util.Constants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String iscallcenter = request.getParameter("iscallcenter");
String name="";
String id="";

String type=request.getParameter("type");
       type=(type==null)?"0":type;

String s_card_id=request.getParameter("s_card_id");
       s_card_id=(s_card_id==null)?"":s_card_id;
String status=request.getParameter("status");
       status=(status==null)?"":status;
	String parent_id = request.getParameter("parent_id");
		parent_id=(parent_id==null)?"":parent_id;       
String cmpt_type_id = request.getParameter("cmpt_type_id");
	cmpt_type_id=(cmpt_type_id==null)?"":cmpt_type_id;
String solve_date = request.getParameter("solve_date");
	solve_date=(solve_date==null)?"":solve_date;
String solve_date2 = request.getParameter("solve_date2");
	solve_date2=(solve_date2==null)?"":solve_date2;
String creator = request.getParameter("creator");
	creator=(creator==null)?"":creator;
String is_answer = request.getParameter("is_answer");//是否需要回复 1是，0否
	is_answer=(is_answer==null)?"":is_answer;
          	
String is_fenye=request.getParameter("is_fenye");
	is_fenye=(is_fenye==null)?"":is_fenye;
	//System.out.println("is_fenye is "+is_fenye);

%>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language=javascript>
      function ref(){
        document.complaintForm.action="../member/complaintQuery.do";
        document.complaintForm.submit();
      }
function querySubmit() {
		if(document.complaintForm.solve_date.value==""||document.complaintForm.solve_date2.value==""){
			alert('开始日期和结束日期都要填写!');
			return false;
		}
		if(document.complaintForm.solve_date.value!=""){
		var bdate = document.complaintForm.solve_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 				
		 if(bdate==null){
				alert('请按格式填写日期,并且注意你的日期是否正确!');
				document.complaintForm.solve_date.focus();
				return false;
		 }	
		}
		if(document.complaintForm.solve_date2.value!=""){
		var bdate2 = document.complaintForm.solve_date2.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 				
		 if(bdate2==null){
				alert('请按格式填写日期,并且注意你的日期是否正确!');
				document.complaintForm.solve_date2.focus();
				return false;
		 }	
		}	
document.complaintForm.search.disabled = true;			
document.complaintForm.submit();
}


function load_page() {
	if ("<%=s_card_id%>" != null && "<%=s_card_id%>" != "")
	{
		document.forms[0].search.attachEvent("onClick");
	}
}
    //-->
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">会员管理</font><font color="838383"> 
      		-&gt; </font><font color="838383">投诉咨询查询</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form  action="/complaintQuery.do?is_fenye=1" method="post" onsubmit="return querySubmit();">
<input type="hidden" name="tag" value="6">
<table  border=0 cellspacing=1 cellpadding=1  width="95%" align="center" >
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">会员号：</td>
		<td width="35%">
			<input type="text" name="s_card_id"  size="12" value="<%=s_card_id%>">&nbsp;&nbsp;
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">操作类型：</td>
		<td >
			<input type="radio" name="type" value="0" <%if(type.equals("0")){%>checked<%}%> onclick="ref()">投诉
			<input type="radio" name="type" value="1" <%if(type.equals("1")){%>checked<%}%> onclick="ref()">咨询
			
			
		</td>
	</tr>	
	<tr>

		<td align="right" class="OraTableRowHeader" noWrap >
		大类：
		</td>
		<td>
			<html:select property="parent_id" onchange="ref()">
				<option value="">-- 请选择 --</option>
			  <html:options collection="complaintType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>
		<td align="right" class="OraTableRowHeader" noWrap >
		小类：
		</td>
		<td>
			<html:select property="cmpt_type_id">
				<option value="">-- 请选择 --</option>
			  <html:options collection="complaintSunType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>			
	
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">状态：</td>
		<td width="35%">
		<select name="status" >
		<option value="">--所有--</option>
		      <option value="0" <%if(status.equals("0")){%>selected<%}%> >未解决</option>	
		      <option value="1" <%if(status.equals("1")){%>selected<%}%> >已解决</option>	
		      <option value="2" <%if(status.equals("2")){%>selected<%}%> >重新处理</option>

		</select>
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">经办人：</td>
		<td >
				<html:select property="creator">		
    					<option value="">--所有--</option>
	                    <html:options collection="colUser" property="id" labelProperty="NAME"/>
		        </html:select>					
		</td>
	</tr>		
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">投诉日期：</td>
		<td width="35%">
			<input type=text name="solve_date" value="<%=solve_date%>" size="10">
			<a href="javascript:show_calendar(document.forms[0].solve_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	  
			至
			<input type=text name="solve_date2" value="<%=solve_date2%>"size="10">
			<a href="javascript:show_calendar(document.forms[0].solve_date2)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	  
		</td>
		<td colspan=2>
		&nbsp;
		</td>	

		
	</tr>	
	<tr>
			<td colspan="4" align="center">

		<input type="submit" name="search" value=" 查 询 ">&nbsp;

		</td>	
	</tr>
	
</table>
<%if (is_fenye.length()>0 ){%>
<%@ include file = "../common/page.jsp" %>
<bean:define id="pageModel" name="memberPageModel" scope="request" type="CommonPageUtil"/>
<%
    //取出总记录数和页码数
    int i=0;
     int totalNum= 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>


<table width="95%" align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#003366" >
	<tr >
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >序号</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >会员号码</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >时间</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >类型</th>
		
		<th width="*%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >内容</th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >状态</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >经办人</th>
	</tr>
	<bean:define id="a" name="pageModel" property="modelList"/>
  <logic:iterate id="mbr_complaint" name="a" >	


	<tr>
		<td bgcolor="#FFFFFF" align=middle >
		<a href="complaintDetail.do?cmpt_id=<bean:write name="mbr_complaint" property="cmpt_id"/>&event_id=<bean:write name="mbr_complaint" property="event_id"/>
		&cmpt_type_id=<%=cmpt_type_id%>&solve_date=<%=solve_date%>&solve_date2=<%=solve_date2%>&creator=<%=creator%>
		&is_answer=<%=is_answer%>&status=<%=status%>&parent_id=<%=parent_id%>&card_id=<bean:write name="mbr_complaint" property="card_id"/>&type=<%=type%>&is_fenye=1">
		<bean:write name="mbr_complaint" property="cmpt_id"/></a></td>
		
		
		<td bgcolor="#FFFFFF" align=middle ><a href="../member/memberDetail.do?id=<bean:write name="mbr_complaint" property="mbr_id"/>&service=1"><bean:write name="mbr_complaint" property="card_id"/></a></td>

		<td bgcolor="#FFFFFF" align=middle ><bean:write name="mbr_complaint" property="create_date"/></td>
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="mbr_complaint" property="cmpt_type_name"/></td>
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="mbr_complaint" property="cmpt_content"/></td>
		<td bgcolor="#FFFFFF" align=middle >
		<logic:equal name="mbr_complaint" property="cmpt_status" value="0">未解决</logic:equal>
		<logic:equal name="mbr_complaint" property="cmpt_status" value="1">已解决</logic:equal>
		<logic:equal name="mbr_complaint" property="cmpt_status" value="2">重新处理</logic:equal>
		<logic:equal name="mbr_complaint" property="cmpt_status" value="3">客服确认已解决</logic:equal>

		</td>	
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="mbr_complaint" property="creatorName"/></td>	
	</tr>
</logic:iterate>
</table>
<%}%>
</html:form>
</body>
</html>
