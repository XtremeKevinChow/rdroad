<%@ page contentType="text/html;charset=GBK" import="com.magic.crm.util.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.magic.crm.common.CharacterFormat"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/progressBar.js"></script>
</head>

<script language="JavaScript">

function addSubmit() {
  
  if(document.forms[0].file.value==""){
     alert("��ѡ��Ҫ�ϴ����ļ�");
     document.forms[0].file.focus();
     return false;
  }  

  document.forms[0].importBtn.disabled = true;
  var waitingInfo = document.getElementById(getNetuiTagName("waitingInfo"));
  waitingInfo.style.display = ""; 
  progress_update(); 
  document.forms[0].submit();
}

</script>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��Ա����</font><font color="838383"> 
      		-&gt; </font><font color="838383">�ʱർ��</font><font color="838383"> 
      	</td>
   </tr>
</table>

<form action="importPostcode.do?type=importFile" method="post" enctype="multipart/form-data" >
<table width="90%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>
			</select>&nbsp;
				�ʱ��ļ���
		    <input name="file"  type="file" value="ѡ��">
			
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