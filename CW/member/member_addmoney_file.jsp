<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.*"%>
<%@page import="com.magic.crm.common.CharacterFormat"%>
<%@page import="com.magic.crm.member.dao.MemberaddMoneyDAO"%>
<%@page import="com.magic.crm.util.CodeName"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/progressBar.js"></script>
</head>

<script language="JavaScript">
function refresh(){
var type=document.forms[0].type.value
document.forms[0].action="../member/member_addmoney_file.jsp?type="+type
document.forms[0].submit();
}

function addSubmit() {
   //alert(0);
  if(document.forms[0].type.value==""){
     alert("��ѡ��������");
     document.forms[0].type.focus();
     return false;
  }
  //alert(1);
  if(document.forms[0].fileurl.value==""){
     alert("��ѡ��Ҫ�ϴ����ļ�");
     document.forms[0].fileurl.focus();
     return false;
  }  
  //alert(2);
  document.forms[0].action = document.forms[0].action + "&filetype=" + document.forms[0].filetype.value;

 if(document.forms[0].fileurl.value.indexOf(document.forms[0].filetype.children(document.forms[0].filetype.selectedIndex).text) == -1) {
		alert("�ļ����Ͳ�ƥ��!");
		document.forms[0].action="upload?type="+form.type.value;
		return;
  }
  //alert(form.action);
  
  form.importBtn.disabled = true;
  var waitingInfo = document.getElementById(getNetuiTagName("waitingInfo"));
  waitingInfo.style.display = ""; 
  progress_update(); 
  //alert(4);
  document.forms[0].submit();
}

</script>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">����</font><font color="838383"> 
      	</td>
   </tr>
</table>
<%
  String type=request.getParameter("type");
  if(type==null){
     type="";
  }
%>
<form action="upload?type=<%=type%>" method="post" enctype="multipart/form-data" name="form">
<table width="90%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>������У�
			<select name="type" onchange="refresh()">
				<option value="">--��ѡ��--</option>
				<%
				Connection conn = null;
				Collection coll = new ArrayList();
				try {
					conn = DBManager.getConnection();
					coll = MemberaddMoneyDAO.getImportTypeList(conn, 0);
				} catch(Exception e) {
					throw e;
				} finally {
					if (conn != null) {
						conn.close();
					}
				}
				Iterator it = coll.iterator();
				while (it.hasNext()) {
					CodeName codeName = (CodeName)it.next();
				%>
				<option value="<%=codeName.getCode()%>" <%if(type.equals(codeName.getCode())){%>selected<%}%>><%=codeName.getName()%></option>
				<% }%>
			</select>&nbsp;&nbsp;
				
				�����ʽ��
			<select name="filetype">
			<option value="1">xls</option>
			
			</select>&nbsp;
				����ļ���
		    <input name="fileurl"  type="file" value="ѡ��">
			
			&nbsp;<input type="button" name="importBtn"  value=" ȷ������ " onclick="addSubmit()"></td>
		
	</tr>
	
</table>
<span id="waitingInfo" style="display:none">
<table align="center"><tr><td>
���ڴ�������, ���Ժ�......
<div style="font-size:2pt;padding:2px;border:solid black 1px">
<span id="progress1">&nbsp; &nbsp;</span>
<span id="progress2">&nbsp; &nbsp;</span>
<span id="progress3">&nbsp; &nbsp;</span>
<span id="progress4">&nbsp; &nbsp;</span>
<span id="progress5">&nbsp; &nbsp;</span>
<span id="progress6">&nbsp; &nbsp;</span>
<span id="progress7">&nbsp; &nbsp;</span>
<span id="progress8">&nbsp; &nbsp;</span>
<span id="progress9">&nbsp; &nbsp;</span>
<span id="progress10">&nbsp; &nbsp;</span>
<span id="progress11">&nbsp; &nbsp;</span>
<span id="progress12">&nbsp; &nbsp;</span>
<span id="progress13">&nbsp; &nbsp;</span>
<span id="progress14">&nbsp; &nbsp;</span>
<span id="progress15">&nbsp; &nbsp;</span>
</div>
</td></tr></table>
</span>
</form>

</body>
</html>