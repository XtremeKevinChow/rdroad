<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%

String name="";
String id="";
String card_id="";
String is_fenye=request.getParameter("is_fenye");//is_fenye=1 ˵���Ƿ�ҳ��ѯ������
is_fenye=(is_fenye==null)?"":is_fenye;

String tag=request.getParameter("tag");
tag=(tag==null)?"":tag;
String type=request.getParameter("type");
String event_id=request.getParameter("event_id");
if(tag.equals("1")){
	name=request.getParameter("name");
	id=request.getParameter("id");
	card_id=request.getParameter("card_id");
}else{
	Member member=new Member();
	member=(Member)request.getAttribute("member");
	name=member.getNAME();
	id=String.valueOf(member.getID());
	card_id=member.getCARD_ID();
}
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript">
function inquireSubmit() {

	if(document.complaintForm.cmpt_content.value==""){
		alert('������������!');
		document.complaintForm.cmpt_content.focus();
		return false;;
	}

	document.complaintForm.submit();
}

function backComplaintList(url) {
	location.href=url;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ��/��ѯ�б�</font><font color="838383"> <font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ��/��ѯ���</font><font color="838383"> 
      	</td>
   </tr>
</table>
 -->

<table  border=0 cellspacing=1 cellpadding=3  width="95%" align="center" >	
	<tr>
		<td align="right" colspan="2"><input name="order_serch" value="�����б�" type="button" onclick=backComplaintList('./complaintList.do?iscallcenter=1&name=<%=name%>&card_id=<%=card_id%>&type=<%=type%>')></td>
	</tr>
</table>
<table  border=0 cellspacing=1 cellpadding=3  width="95%" align="center" class="OraTableRowHeader" noWrap >
	  <logic:iterate id="complaint" name="ColListComplaint" >	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap  width="160">&nbsp;��Ա���룺</td>
		<td width="*%" align="left" colspan="1" width="*%"  bgcolor="#FFFFFF">
			<%=card_id%>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >&nbsp;��Ա������</td>
		<td  align="left" bgcolor="#FFFFFF"><%=name%>

		</td>
	</tr>
	
	<tr>
		<td align="right"  class="OraTableRowHeader" noWrap >&nbsp;���ݣ�</td>
		<td align="left" bgcolor="#FFFFFF">
			<bean:write name="complaint" property="cmpt_content"/>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >���ࣺ</td>
		<td bgcolor="#FFFFFF">
		<logic:equal name="complaint" property="type" value="0">Ͷ��</logic:equal>
		<logic:equal name="complaint" property="type" value="1">��ѯ</logic:equal>	
		
			
		</td>
	</tr>	
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >���ͣ�</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="cmpt_type_name"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�Ƿ�ظ���</td>
		<td  bgcolor="#FFFFFF">
		<logic:equal name="complaint" property="is_answer" value="1">��Ҫ�ظ�</logic:equal>
		<logic:equal name="complaint" property="is_answer" value="0">����Ҫ�ظ�</logic:equal>	
		
		</td>
	</tr>

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >����Ա��</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="creatorName"/>
			
		</td>
	</tr>
		</logic:iterate>	
</table>
<br>

<%
int i=0;
%>
	  <logic:iterate id="complaint" name="ColListComplaintDeal" >	
<table  border=0 cellspacing=1 cellpadding=3  width="95��" align="center" bgcolor="#dd3366">	
<%

  i++;
%>

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">��<%=i%>�ν��������</td>
		<td bgcolor="#FFFFFF">
		<bean:write name="complaint" property="cmpt_content"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >����Ա��</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="creatorName"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�������ڣ�</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="complaint" property="create_date"/>
			
		</td>
	</tr>	
</table>
<br>
		</logic:iterate>


<html:form action="/complaintDeal.do" method="post">
<table  border=0 cellspacing=1 cellpadding=1  width="95%" align="center">
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">���������</td>
		<td bgcolor="#FFFFFF">
		<textarea cols="50" rows="5" name="cmpt_content"></textarea>					
		</td>
		</tr>
		<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">�����</td>
		<td bgcolor="#FFFFFF">
		<input type=radio name="cmt_status" value="1"  checked>���
		<input type=radio name="cmt_status" value="2"  >�ٴδ�����	
				
		</td>		
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input type="button" value=" ȷ��" onclick="inquireSubmit()" >&nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
	</tr>
</table>
	  <bean:define id="complaint" name="complaintForm" />
	  <html:hidden name="complaint" property="mbr_id"/>	
	  <html:hidden name="complaint" property="deptID"/>
	  <html:hidden name="complaint" property="cmpt_level"/>
	  <html:hidden name="complaint" property="creator"/>
	  <html:hidden name="complaint" property="cmpt_id"/>
	<input type="hidden" name="cmpt_type_id" value="<bean:write name='complaint' property='cmpt_type_id'/>">
	  <input type="hidden" name="type" value="<%=type%>">
	  
	  <input type="hidden" name="name" value="<%=name%>">
	  <input type="hidden" name="card_id" value="<%=card_id%>"> 	  
	  <input type="hidden" name="id" value="<%=id%>"> 
	  <input type="hidden" name="event_id" value="<%=event_id%>"> 
	  <input type="hidden" name="iscallcenter" value="1"> 
	  <%
	  if(is_fenye.equals("1")){//�Ӳ�ѯҳ������Ĳ��������ز�ѯҳ��ʱҲҪ����ȥ
 	//�����Ѿ��еĲ�����creator��cmpt_type_id�Ͳ�������ֵ��

	String status=request.getParameter("status");
	       status=(status==null)?"":status;
	String solve_date = request.getParameter("solve_date");
		solve_date=(solve_date==null)?"":solve_date;
	String solve_date2 = request.getParameter("solve_date2");
		solve_date2=(solve_date2==null)?"":solve_date2;
	String parent_id = request.getParameter("parent_id");
		parent_id=(parent_id==null)?"":parent_id;		
	String is_answer = request.getParameter("is_answer");//�Ƿ���Ҫ�ظ� 1�ǣ�0��
		is_answer=(is_answer==null)?"":is_answer;          	
	  
	  %>
	  <input type="hidden" name="is_answer" value="<%=is_answer%>"> 
	  <input type="hidden" name="solve_date" value="<%=solve_date%>"> 
	  <input type="hidden" name="solve_date2" value="<%=solve_date2%>"> 
	  <input type="hidden" name="parent_id" value="<%=parent_id%>"> 
	  
	  <input type="hidden" name="status" value="<%=status%>"> 
	  <input type="hidden" name="is_fenye" value="1"> 


	  <%
	  }
	  %>

</html:form>


</body>
</html>
