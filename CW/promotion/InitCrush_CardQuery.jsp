<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript" src="../crmjsp/calendar.js"></script>
<script language="javascript">
function saleCard(cardnum,tag,card_type) { ��
	var strURL = "saleCardUpdate.jsp?card_num="+cardnum+"&tag="+tag+"&card_type="+card_type;��
	    document.location.href = strURL;

} 

function f_checkData() {
        if(document.forms[0].card_num.value==""&&(document.forms[0].begin_date.value==""||document.forms[0].end_date.value=="" )){
           alert("���ۿ��ź��������ڲ���ȫΪ��");
           document.forms[0].card_num.select();	
           return false;
        }
        document.forms[0].input.disabled = true;
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >


<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�г�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">���ۿ���ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="Crush_CardQuery.do" method="post" onsubmit="return f_checkData();">

<table width="95%" cellspacing="0" cellpadding="0" align="center" border=0>  	
	<tr> 
	  <td>���㿨�ţ�<html:text property="card_num" size="10"/>&nbsp;&nbsp;
	  ��
	
	  <html:select name="ccmForm"  property="card_type">
	    <option value="">--����--</option>
		<html:optionsCollection property="crushTypeList" value="code" label="name"/> 
	  </html:select>
	&nbsp;&nbsp;
	  ״̬��
	  <html:select name="ccmForm"   property="status">
	    <option value="">--����--</option>
	    <html:option value="2">������</html:option>
	    <html:option value="3">�ѳ�ֵ</html:option>
	  </html:select>	  
	  </td>
	</tr>

	<tr> 	  
	  <td>�������ڣ�<html:text property="begin_date" size="10"/>
	  <a href="javascript:calendar(document.forms[0].begin_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
	  
	   &nbsp;��&nbsp; <html:text property="end_date" size="10"/> 
	   <a href="javascript:calendar(document.forms[0].end_date)"><img src="../crmjsp/images/icon_date.gif" border=0 align="top"></a>
	   &nbsp;&nbsp;(���ڸ�ʽ:YYYY-MM-DD)	
	    <input  type="submit" name="input" class="button2" value=" ��ѯ " > 
		<input name="actn" type="hidden" value="<%=actn%>">
	  </td>
	</tr>
	
</table>
<br>

<%

if (request.getMethod().equals("POST")){
%>


  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
       <tr >     	
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >����</td>
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >��ֵ</td>
	  <td width=""  class="OraTableRowHeader"  noWrap align="center"  >״̬</td>
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >��������</td>
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >������</td>
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >��������</td>
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >������</td>
	  <td width="13%"  class="OraTableRowHeader"  noWrap align="center"  >����</td>
	</tr>
    
    <bean:define id="ccm" name="allccm"  type="java.util.Collection"/> 
    <%int i=0;%>    
    <logic:iterate name="ccm" id="ccmdtl">    
    
    <tr align="center" > 
    	<td class=OraTableCellText><bean:write name="ccmdtl" property="card_num" /></td>
      <td class=OraTableCellText>
		<bean:write name="ccmdtl" property="money" format="0"/>
      	
      </td>
      
      <td class=OraTableCellText>
      	<logic:equal name="ccmdtl" property="status" value="1">
      	�Ѵ���
      	</logic:equal>
      	<logic:equal name="ccmdtl" property="status" value="2">
      	������
      	</logic:equal>
      	<logic:equal name="ccmdtl" property="status" value="3">
      	�ѳ�ֵ
      	</logic:equal>
      	<logic:equal name="ccmdtl" property="status" value="4">
      	����
      	</logic:equal>      	
      	</td>
      	
      	 <td class=OraTableCellText><bean:write name="ccmdtl" property="sale_date"/></td>
      	 <td class=OraTableCellText><bean:write name="ccmdtl" property="sale_person_name" /></td>
      	 <td class=OraTableCellText><bean:write name="ccmdtl" property="crush_date" /></td>
      	 <td class=OraTableCellText><bean:write name="ccmdtl" property="crush_person_name" /></td>
      	 <td class=OraTableCellText>
      	<logic:equal name="ccmdtl" property="status" value="1"> 
      	 <input name="input1" type="button" value="����" onclick="javascript:saleCard('<bean:write name="ccmdtl" property="card_num"/>',1,'<bean:write name="ccmdtl" property="card_type"/>')">
      	 </logic:equal>  
      	 <!--
      	 <input name="input2" type="button" value="��ֵ"  onclick="javascript:saleCard('<bean:write name="ccmdtl" property="card_num"/>',2,'<bean:write name="ccmdtl" property="card_type"/>')"></td>
      	 -->
    </tr>
    <%i++;%>
    </logic:iterate>
       <!-- <tr >     	
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  >���� <font color="blue"><%=i%></font> ����¼ </td>
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	  <td width=""  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	  <td width="13%"  class="OraTableRowHeader" noWrap  noWrap align="center"  ></td>
	</tr>     -->
  </table>

<%
}
%>
</html:form>
<br>
</body>
</html>
