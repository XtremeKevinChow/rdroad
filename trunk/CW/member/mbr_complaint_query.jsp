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
String is_answer = request.getParameter("is_answer");//�Ƿ���Ҫ�ظ� 1�ǣ�0��
	is_answer=(is_answer==null)?"":is_answer;
          	
String is_fenye=request.getParameter("is_fenye");
	is_fenye=(is_fenye==null)?"":is_fenye;
	//System.out.println("is_fenye is "+is_fenye);

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
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
			alert('��ʼ���ںͽ������ڶ�Ҫ��д!');
			return false;
		}
		if(document.complaintForm.solve_date.value!=""){
		var bdate = document.complaintForm.solve_date.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 				
		 if(bdate==null){
				alert('�밴��ʽ��д����,����ע����������Ƿ���ȷ!');
				document.complaintForm.solve_date.focus();
				return false;
		 }	
		}
		if(document.complaintForm.solve_date2.value!=""){
		var bdate2 = document.complaintForm.solve_date2.value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-9]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/); 				
		 if(bdate2==null){
				alert('�밴��ʽ��д����,����ע����������Ƿ���ȷ!');
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ����ѯ��ѯ</font><font color="838383"> 
      	</td>
   </tr>
</table>
<html:form  action="/complaintQuery.do?is_fenye=1" method="post" onsubmit="return querySubmit();">
<input type="hidden" name="tag" value="6">
<table  border=0 cellspacing=1 cellpadding=1  width="95%" align="center" >
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">��Ա�ţ�</td>
		<td width="35%">
			<input type="text" name="s_card_id"  size="12" value="<%=s_card_id%>">&nbsp;&nbsp;
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">�������ͣ�</td>
		<td >
			<input type="radio" name="type" value="0" <%if(type.equals("0")){%>checked<%}%> onclick="ref()">Ͷ��
			<input type="radio" name="type" value="1" <%if(type.equals("1")){%>checked<%}%> onclick="ref()">��ѯ
			
			
		</td>
	</tr>	
	<tr>

		<td align="right" class="OraTableRowHeader" noWrap >
		���ࣺ
		</td>
		<td>
			<html:select property="parent_id" onchange="ref()">
				<option value="">-- ��ѡ�� --</option>
			  <html:options collection="complaintType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>
		<td align="right" class="OraTableRowHeader" noWrap >
		С�ࣺ
		</td>
		<td>
			<html:select property="cmpt_type_id">
				<option value="">-- ��ѡ�� --</option>
			  <html:options collection="complaintSunType" property="cmpt_type_id" labelProperty="cmpt_type_name"/>
			</html:select>
			
		</td>			
	
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">״̬��</td>
		<td width="35%">
		<select name="status" >
		<option value="">--����--</option>
		      <option value="0" <%if(status.equals("0")){%>selected<%}%> >δ���</option>	
		      <option value="1" <%if(status.equals("1")){%>selected<%}%> >�ѽ��</option>	
		      <option value="2" <%if(status.equals("2")){%>selected<%}%> >���´���</option>

		</select>
		</td>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">�����ˣ�</td>
		<td >
				<html:select property="creator">		
    					<option value="">--����--</option>
	                    <html:options collection="colUser" property="id" labelProperty="NAME"/>
		        </html:select>					
		</td>
	</tr>		
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="15%">Ͷ�����ڣ�</td>
		<td width="35%">
			<input type=text name="solve_date" value="<%=solve_date%>" size="10">
			<a href="javascript:show_calendar(document.forms[0].solve_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	  
			��
			<input type=text name="solve_date2" value="<%=solve_date2%>"size="10">
			<a href="javascript:show_calendar(document.forms[0].solve_date2)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	  
		</td>
		<td colspan=2>
		&nbsp;
		</td>	

		
	</tr>	
	<tr>
			<td colspan="4" align="center">

		<input type="submit" name="search" value=" �� ѯ ">&nbsp;

		</td>	
	</tr>
	
</table>
<%if (is_fenye.length()>0 ){%>
<%@ include file = "../common/page.jsp" %>
<bean:define id="pageModel" name="memberPageModel" scope="request" type="CommonPageUtil"/>
<%
    //ȡ���ܼ�¼����ҳ����
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
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ա����</th>
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >ʱ��</th>
		<th width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		
		<th width="*%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
		
		<th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >״̬</th>
		<th width="8%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
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
		<logic:equal name="mbr_complaint" property="cmpt_status" value="0">δ���</logic:equal>
		<logic:equal name="mbr_complaint" property="cmpt_status" value="1">�ѽ��</logic:equal>
		<logic:equal name="mbr_complaint" property="cmpt_status" value="2">���´���</logic:equal>
		<logic:equal name="mbr_complaint" property="cmpt_status" value="3">�ͷ�ȷ���ѽ��</logic:equal>

		</td>	
		<td bgcolor="#FFFFFF" align=middle ><bean:write name="mbr_complaint" property="creatorName"/></td>	
	</tr>
</logic:iterate>
</table>
<%}%>
</html:form>
</body>
</html>
