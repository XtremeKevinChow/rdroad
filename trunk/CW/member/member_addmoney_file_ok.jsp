<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.member.form.MemberaddMoneyForm"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String status=request.getParameter("status");
       status=(status==null)?"":status;
String method = request.getMethod();
int i=0;
int j=0; 
MemberaddMoneyForm myForm = (MemberaddMoneyForm)request.getAttribute("memberaddMoneyForm");
String importType = "";
if (myForm != null) {
	importType = myForm.getTYPE();
}
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>

<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/sortTable.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript">


//��ѯ
function query_f() {
	if(document.forms[0].CREATE_DATE.value!=""){
		var edate = document.forms[0].CREATE_DATE.value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		if(edate==null){
			alert('�밴��ʽ��д�������!');
			document.forms[0].CREATE_DATE.focus();
			return ;
		}		
	 }
	document.forms[0].query.disabled = true;
	document.forms[0].submit();
}
//��ֵ	
function submit_f() {
	if(check_f()) {
		document.forms[0].search.disabled = true;
		document.forms[0].action = "memberaddMoneyok.do";
		document.forms[0].submit();
	}
		
	
}
//��ֵǰ���
function check_f() {
	var isSelect = false;
	var form = document.forms[0];
	if(form.inum.value=="0"){
		alert("��ѡ����Ҫ��ֵ�ļ�¼��");
		return;
	
	 }else{
		for(var i = 1; i < DataTable.rows.length; i ++) {
			//�Ƿ��ֵ
			if (DataTable.rows(i).cells(0).children(0).checked) {
				isSelect = true;
				DataTable.rows(i).cells(0).children(1).value = "true";
			}
			//�Ƿ񷵻����
			if (DataTable.rows(i).cells(1).children(0).checked) {
				DataTable.rows(i).cells(1).children(1).value = "true";
			}
			//�Ƿ�����Ʒ
			if (DataTable.rows(i).cells(2).children(0).checked) {
				DataTable.rows(i).cells(2).children(1).value = "true";
			}
		}
	 }
	 if(!isSelect) {
		alert("��ѡ���ֵ��¼");
		return false;
	 }
	 return true;
}


function over(obj) {
	var chk = obj.getElementsByTagName("INPUT")[0];
	obj.bgColor=MS_OVER_COLOR;
}
function out(obj) {
	var chk = obj.getElementsByTagName("INPUT")[0];
	obj.bgColor=MS_OUT_COLOR;
}

//ȫѡ��ֵ
function checkAll(bln, type) {
	var len = DataTable.rows.length;
	for (i = 1; i < len; i ++) {
		row = DataTable.rows(i);
		if(bln) {

			row.getElementsByTagName("INPUT")[type].checked = true;
			
		}else{
			row.getElementsByTagName("INPUT")[type].checked = false;
		}
	}
}

//�����Ϸ�������Զ����Ϲ�
function checkIsBack(bln) {
	
	var money, status, row, pos1, pos2;
	var len = DataTable.rows.length;	
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		money = row.cells(4).innerText;
		status = row.cells(8).innerText;
		pos1 = status.indexOf("Ԥ��");
		pos2 = status.indexOf("Ԥ��");
		if(parseFloat(money) >= 150 && (pos1 != -1 || pos2 != -1)) {
			
			if(bln) {
				row.getElementsByTagName("INPUT")[2].checked = true;
			}else{
				row.getElementsByTagName("INPUT")[2].checked = false;
			}
		}
	}
}
// ����Ʒ
function checkGift(bln) {
	
	var money, row, pos1, pos2;
	var len = DataTable.rows.length;	
	for (i = 1; i < len; i ++) {
		
		row = DataTable.rows(i);
		money = row.cells(4).innerText;
		
		if(parseFloat(money) >= 150) {
			
			if(bln) {
				row.getElementsByTagName("INPUT")[4].checked = true;
			}else{
				row.getElementsByTagName("INPUT")[4].checked = false;
			}
		}
	}
}
//ҳ���ʼ��
function load() {
	if("<%=method%>" == "POST") {
		loadSort(DataTable);
	}
	document.forms[0].TYPE.value = "<%=importType%>";
}
var MS_OUT_COLOR  = "#FFFFFF";
var MS_OVER_COLOR = "#F6F6F6";
var MS_SEL_COLOR = "#99CCFF";

//���û�Ա��
function setCardId(pValue, rowId) {
	rowId --;
	if(typeof(document.forms[0].MB_CODE.length)=="undefined") {//������
		document.forms[0].MB_CODE.value = pValue;
		document.forms[0].MB_CODE.focus();
	}else{
	
		document.forms[0].MB_CODE[rowId].value = pValue;
		document.forms[0].MB_CODE[rowId].focus();
	}
}


</script>

</head>
<body onload="load();">
<html:form action="/memberqueryMoney.do" method="post" onsubmit="javascript:query_f();">
<table  width="99%" align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��Ա��ֵ</font><font color="838383"> 
      	</td>
   </tr>
</table>

<table  width="99%" align="center" border=0 cellspacing=1 cellpadding=2>
	<tr>
		<td>
		���ͣ�<html:select property="TYPE" style="width:72">
		<option value="">��ѡ��...</option>
		<logic:iterate name="typeList" id="codeName">
		<option value="<bean:write name="codeName" property="code"/>">
		<bean:write name="codeName" property="name"/></option>
		</logic:iterate>
		</html:select>&nbsp;
		������<html:text property="searchMbName" size="10"/>&nbsp;
		��ţ�<html:text property="searchRefId" size="12"/>&nbsp;
		¼�����ڣ�<html:text property="CREATE_DATE" size="10"/>
		<a href="javascript:show_calendar(document.forms[0].CREATE_DATE)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
		<html:radio property="status" value="0" />δ��ֵ
		<html:radio property="status" value="1" />�ѳ�ֵ
		<html:radio property="status" value="2" />ʧ��
		����1
		<html:select property="filter1" style="width:120">
		<option value="0" <logic:equal name="memberaddMoneyForm" property="filter1" value="0">selected</logic:equal>>ȫ��</option>
		<option value="1" <logic:equal name="memberaddMoneyForm" property="filter1" value="1">selected</logic:equal>>�л�Ա���ж�����</option>
		<option value="2" <logic:equal name="memberaddMoneyForm" property="filter1" value="2">selected</logic:equal>>�޻�Ա���޶�����</option>
		</html:select>
		<input type="button" value=" ��ѯ " name="query" onclick="query_f()">
		</td>   
   </tr>
</table>
<input type="hidden" name="isquery" value="1"><!-- ��ѯ��� -->
<%
if (request.getMethod().equals("POST")){
%>
<%@ include file = "../common/page.jsp" %>
<bean:define id="pageModel" name="memberMoneyModel" scope="request" type="CommonPageUtil"/>
<%
    //ȡ���ܼ�¼����ҳ����
    int totalNum = 0;
    int curPage = 0;
    int totalPage = 0;
    float amount = 0;
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	amount = pageModel.getAmount();
	
%>

<table width="99%" align="center" cellspacing="0" border="0">
	<tr>	
		<%
		if (!status.equals("1")){
		%>
		<td width=150>ȫѡ<INPUT TYPE="checkbox" NAME="all" onclick="checkAll(this.checked, 0)"> ���<INPUT TYPE="checkbox" onclick="checkIsBack(this.checked)"> ��Ʒ<INPUT TYPE="checkbox" onclick="checkGift(this.checked)"></td>
		<%
		}
		%>
		<td align=left>
		<%=turnPagePattern(totalNum,totalPage,curPage)%>
		<%=turnPageScript("listFrm")%>
	</td>
	</tr>
</table>
<table width="98%" align="center" cellspacing="0" border="1" bordercolordark="lightblue" bordercolorlight="#ffffff" id="DataTable">
	<tr height="30">
		<script language="JavaScript">
		if(document.forms[0].status[0].checked|| document.forms[0].status[2].checked) {
		document.write("<td width=\"40\"  bgcolor=\"#ECECD1\" noWrap align=middle  sort='false'>�� ֵ</td>")
		document.write("<td width=\"40\"  bgcolor=\"#ECECD1\"  noWrap align=middle   sort='false'>�� ȯ</td>")
		<!--document.write("<td width=\"40\"  bgcolor=\"#ECECD1\"  noWrap align=middle   sort='false'>�� Ʒ</td>-->")
		}
		</script>
		<td width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle>����ַ</td>
		<td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle>�����</td>
		<td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle><font color=blue>��Ա��</font></td>
		<td width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle >�������</td>
		<td width="15%"  class="OraTableRowHeader" noWrap  noWrap align=middle><font color=blue>������</font></td>
		<td width=""  class="OraTableRowHeader" noWrap  noWrap align=middle>����</td>
 		<td width="15%"  class="OraTableRowHeader" noWrap   noWrap align=middle>��������</td>
		<td width="40"  class="OraTableRowHeader" noWrap  noWrap align=middle>״̬</td>
	</tr>
	<bean:define id="memberPrepay" name="pageModel" property="modelList"/>
	<logic:iterate id="memberAddMoney" name="memberPrepay" >
	<logic:equal name="memberAddMoney" property="status" value="0"><%j=0;%></logic:equal>
	<logic:equal name="memberAddMoney" property="status" value="1"><%j=1;%></logic:equal>	 
	<logic:equal name="memberAddMoney" property="status" value="2"><%j=2;%></logic:equal>	 
	<tr onmouseover="over(this)" onmouseout="out(this)">
		<%
		  if (j==0 || j==2) {
		%>
		<td  noWrap align=middle >
		<input type="checkbox" name="inputid" value="<bean:write name='memberAddMoney' property='ID' format='#'/>">
		<input type="hidden" name="flag1" value="false">
		</td>
		
		<td noWrap align=middle >
		<input type="checkbox" value="<bean:write name='memberAddMoney' property='ID' format='#'/>">
		<input type="hidden" name="flag2" value="false">
		</td>

		<!--<td noWrap align=middle >
		<input type="checkbox" value="<bean:write name='memberAddMoney' property='ID' format='#'/>">
		<input type="hidden" name="flag3" value="false">
		</td>-->
		<%
	    }
	    %>

	  <td noWrap align=left >
		   <bean:write name="memberAddMoney" property="ADDRESS"/>&nbsp;<!-- ��ַ (readonly) -->
	  </td>
	  
	  <td noWrap align=right ><bean:write name="memberAddMoney" property="MONEY" format="#0.00"/>
		   <input type="hidden" name="MONEY" value="<bean:write name='memberAddMoney' property='MONEY' format='#0.00'/>"><!-- ����� (readonly,hidden) -->
	  </td>

	  <td noWrap align=middle >
	  <%
		  if (j==0 || j==2) {
	  %>
	  <input type="text" name="MB_CODE"  size="8" value="<bean:write name='memberAddMoney' property='MB_CODE'/>"><!-- ��Ա�� -->
	  <%
	  }else{
		  
	  %>
		<bean:write name="memberAddMoney" property="MB_CODE"/>&nbsp;</td><!-- ��Ա��(readonly) -->
	  <%
	  }
	  %>
	  </td>		 

	  <td noWrap >
	  <%
		  if (j==0 || j==2) {
	  %>
		<input name='MB_NAME' size="16" value="<bean:write name='memberAddMoney' property='MB_NAME'/>">
		<input type="button" value=".." onclick="window.open('../member/memberQueryByAddress.do?id=<bean:write name='memberAddMoney' property='ID' format='#'/>&NAME=<bean:write name='memberAddMoney' property='MB_NAME'/>&postcode=<bean:write name='memberAddMoney' property='postCode'/>&rowId='+this.parentElement.parentElement.rowIndex, 'newwindow', 'left=200,top=50,height=450, width=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes')">	<!-- ������� -->	
	  <%
	  }else{
		  
	  %>
		<bean:write name='memberAddMoney' property='MB_NAME'/>&nbsp;<!-- �������(readonly) -->
	  <%
	  }
	  %>
	  </td>
	  <td noWrap align=middle >
	  <%
		  if (j==0 || j==2) {
	  %>
	  <input type="text" name="ORDER_CODE" size="14" value="<bean:write name='memberAddMoney' property='ORDER_CODE'/>"><!-- ������ -->	
	  <%
	  }else{
	  %>
		<bean:write name="memberAddMoney" property="ORDER_CODE" />&nbsp;</td><!-- ������(readonly) -->	
	  <%
	  }
	  %>
	  </td>
	  <td width="200"><bean:write name="memberAddMoney" property="REMARK"/>&nbsp;</td><!-- ����(eadony) -->	
	  <td noWrap>
	  <bean:write name='memberAddMoney' property='bill_date' /><!-- ��������(readonly) -->	
	  </td>
	  <input type="hidden" name="REF_ID" value="<bean:write name='memberAddMoney' property='REF_ID' format='#'/>"><!-- ���(hidden) -->
	  <input type="hidden" name="ID" value="<bean:write name='memberAddMoney' property='ID' format='#'/>"><!-- ����(hidden) -->	
	  <input type="hidden" name="payMethod" value="<bean:write name='memberAddMoney' property='payMethod' format='#'/>"><!-- ���ʽ(hidden) -->
	  <td noWrap align=middle >
		<logic:equal name="memberAddMoney" property="status" value="0">δ��ֵ</logic:equal>
		<logic:equal name="memberAddMoney" property="status" value="1">�ѳ�ֵ</logic:equal>
		<logic:equal name="memberAddMoney" property="status" value="2">����</logic:equal>
		
	  </td><!-- ״̬(readonly) -->
	  
	</tr>
	<%i++;%>
</logic:iterate>
</table>

<TABLE>
<tr><td colspan=2 align=right><b>�ܼƣ�</b></td><td colspan=7><%=amount%></td></tr>
</TABLE>
<input type="hidden" name="inum" value="<%=i%>">
<%if(j==0||j==2){%>
<table width="100%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td align="center">
			<input type="button" name="search" value=" ��ֵ " onclick="submit_f()">
		</td>		
	</tr>
</table>
<%}%>
<%}%>
</html:form>
</body>
</html>