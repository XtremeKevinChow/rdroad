<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String pid=request.getParameter("pid");

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="javascript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/dateselect.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="javascript">
function getProduct(){
	openWin("../product/productQuery.do?actn=selectProduct&ifproduct=1&isreport=1","2005",700,400);
}
function f_checkData() {

        var form = document.forms[0];
        
        if(form.order_require.value==""){
           alert("����д�������");
           form.order_require.select();	
           return false;
        }
        
        if(!is_number(form.order_require.value)) {
            alert("�������ĸ�ʽ����ȷ");
            return false;
        } 	
        
        if(form.ID.value==""||form.ID.value=="0") {
            form.action="freeDeliveryFee.do?type=add";
        } else {
            form.action="freeDeliveryFee.do?type=modify";
        }
        return true;
}
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �г����� -&gt; �����ⷢ�ͷѹ��� </font></td>
  </tr>
</table>
<br>
<html:form  action="/freeDeliveryFee.do?type=add" method="post" onsubmit="return f_checkData();">
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >

 <tr> 
    <td class="OraTableRowHeader" noWrap  >������� <font color=red>*</font></td>
    <td>
    
    <html:text property="order_require"/>
	</td>
 </tr>  
 <tr> 
    <td class="OraTableRowHeader" noWrap  >��ʼ���� <font color=red>*</font></td>
    <td>
    <html:text property="begin_date"/>
	<a href="javascript:show_calendar(document.forms[0].begin_date)">
	<img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	</td>
 </tr> 
  <tr> 
    <td class="OraTableRowHeader" noWrap  >�������� <font color=red>*</font></td>
    <td>
    <html:text property="end_date"/>
	<a href="javascript:show_calendar(document.forms[0].end_date)">
	<img src="../crmjsp/images/icon_date.gif" border=0 align="top"><a>
	</td>
 </tr> 

  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" �� �� " > 
      
  </tr> 
</table>

    <html:hidden property="ID"/>
    <html:hidden property="order_type"/>
</html:form>

</body>
</html>

