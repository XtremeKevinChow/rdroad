<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="com.magic.crm.member.entity.*"%>
<%@page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
/*
String name=request.getParameter("name");
String card_id=request.getParameter("card_id");
String id=request.getParameter("id");
*/
String is_query=request.getParameter("is_query");//1 ��������ҳ�� 0 ���ز�ѯҳ��
String name="";
String id="";
String card_id="";
String tag=request.getParameter("tag");
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

if(document.inquiryForm.SOLVE_METHOD.value==""){
alert('������������!');
document.inquiryForm.SOLVE_METHOD.focus();
return false;;
}

document.inquiryForm.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ���б�</font><font color="838383"> <font color="838383"> 
      		-&gt; </font><font color="838383">Ͷ������</font><font color="838383"> 
      	</td>
   </tr>
</table>


<table  border=0 cellspacing=1 cellpadding=3  width="95��" align="center" >	
	<tr>
		<td align="right" colspan="2"><input name="order_serch" value="�����б�" type="button" onclick="document.location.href='./InquiryList.do?id=<%=id%>&name=<%=name%>&card_id=<%=card_id%>'"></td>
	</tr>
</table>
<table  border=0 cellspacing=1 cellpadding=3  width="95��" align="center" bgcolor="#dd3366">
	  <logic:iterate id="inquiry" name="ColListInquiry" >	
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
			<bean:write name="inquiry" property="SOLVE_METHOD"/>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >Ͷ�����ͣ�</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="inquiry" property="inquiryName"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�Ƿ���Ҫ�𸴣�</td>
		<td  bgcolor="#FFFFFF">
		<logic:equal name="inquiry" property="IS_ANSWER" value="0">��</logic:equal>
		<logic:equal name="inquiry" property="IS_ANSWER" value="1">��</logic:equal>
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�����̶ȣ�</td>
		<td  bgcolor="#FFFFFF">
		<logic:equal name="inquiry" property="INQUIRY_LEVEL" value="1">�ߣ�1��֮�ڴ���</logic:equal>
		<logic:equal name="inquiry" property="INQUIRY_LEVEL" value="2">�У�2��֮�ڴ���</logic:equal>	
		<logic:equal name="inquiry" property="INQUIRY_LEVEL" value="3">�ͣ�3��֮�ڴ���</logic:equal>			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >����Ա��</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="inquiry" property="SOLVE_PERSON"/>
			
		</td>
	</tr>
		</logic:iterate>	
</table>
<br>

<%
int i=0;
%>
	  <logic:iterate id="inquiry" name="ColListInquirySolve" >	
<table  border=0 cellspacing=1 cellpadding=3  width="95��" align="center" bgcolor="#dd3366">	
<%

  i++;
%>

	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">��<%=i%>�ν��������</td>
		<td bgcolor="#FFFFFF">
		<bean:write name="inquiry" property="SOLVE_METHOD"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >����Ա��</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="inquiry" property="SOLVE_PERSON"/>
			
		</td>
	</tr>
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap >�������ڣ�</td>
		<td bgcolor="#FFFFFF">
			<bean:write name="inquiry" property="SOLVE_DATE"/>
			
		</td>
	</tr>	
</table>
<br>
		</logic:iterate>

<logic:equal name="inquiry" property="IS_ANSWER" value="1">
<html:form action="/InquirySolve.do" method="post">
<table  border=0 cellspacing=1 cellpadding=1  width="95��" align="center">
	<tr>
		<td align="right" class="OraTableRowHeader" noWrap  width="160">���������</td>
		<td bgcolor="#FFFFFF">
		<textarea cols="50" rows="5" name="SOLVE_METHOD"></textarea>					
		</td>
	</tr>
	<tr height="40">
		<td align="center" colspan=4>
		<input type="button" value=" ȷ��" onclick="inquireSubmit()" >&nbsp;
		<input type="reset" class="button2" value=" ȡ�� " >
	</tr>
</table>
	  <bean:define id="inquiryinfo" name="inquiry" />	
	  <html:hidden name="inquiryinfo" property="ref_department"/>
	  <html:hidden name="inquiryinfo" property="INQUIRY_LEVEL"/>
	  <html:hidden name="inquiryinfo" property="IS_SOLVE"/>
	  <html:hidden name="inquiryinfo" property="IS_ANSWER"/>
	  <html:hidden name="inquiryinfo" property="CREATEID"/>
	  <html:hidden name="inquiryinfo" property="MEMBERID"/>
	  <input type="hidden" name="INQUIRY_TYPE2" value="<bean:write name='inquiryinfo' property='INQUIRY_TYPE'/>">
	  <input type="hidden" name="name" value="<%=name%>">
	  <input type="hidden" name="card_id" value="<%=card_id%>"> 	  
	  <input type="hidden" name="id" value="<%=id%>"> 
  	<input type="hidden" name="is_query" value="<%=is_query%>"> 
	  <%
			//1 ��������ҳ�� 0 ���ز�ѯҳ��
			if(is_query.equals("0")){
			String s_card_id=request.getParameter("s_card_id");
			String deptid=request.getParameter("deptid");	
			String type=request.getParameter("type");	
			String INQUIRY_TYPE=request.getParameter("INQUIRY_TYPE");	
			String SOLVE_DATE=request.getParameter("SOLVE_DATE");
			String SOLVE_DATE2=request.getParameter("SOLVE_DATE2");
			String SOLVE_PERSON=request.getParameter("SOLVE_PERSON");
		%>
	
	  <input type="hidden" name="s_card_id" value="<%=s_card_id%>">
	  <input type="hidden" name="deptid" value="<%=deptid%>">
	  <input type="hidden" name="type" value="<%=type%>">
	  <input type="hidden" name="INQUIRY_TYPE" value="<%=INQUIRY_TYPE%>">
	  <input type="hidden" name="SOLVE_DATE" value="<%=SOLVE_DATE%>">
	  <input type="hidden" name="SOLVE_DATE2" value="<%=SOLVE_DATE2%>">
	  <input type="hidden" name="SOLVE_PERSON" value="<%=SOLVE_PERSON%>">
	  <input type="hidden" name="isquery" value="1">	
	  <input type="hidden" name="is_fenye" value="1">	
	  <%}%>
</html:form>
</logic:equal>

</body>
</html>
