<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.io.*,java.sql.*"%>
<%@ page contentType="text/html;charset=GBK"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
String card_num=request.getAttribute("card_num").toString();
String sale_check=request.getAttribute("sale_check").toString();
System.out.println("card_num :"+card_num);
System.out.println("sale_check :"+sale_check);
%>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../css/style.css" type="text/css">
<title></title>
</head>
<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">
<center>

<br><br><br><br><br><br><br><br><br>

<table width="360" cellspacing="4" cellspacing="2" border="0"  background="../images/backline.gif">
<tr>
<td>
    <table width="300" cellspacing="0" cellspacing="0">
        <tr height="20" valign="bottom"><td><font color="red"><b><font style="font-size:14px;">系统提示</font></b></font></td></tr>
    </table>
    <table width="100%" height="60" cellspacing="0" cellspacing="0" border="0" bgcolor="#ffffff">
        <tr>
            <td align="center"><br>
                <table width="98%" cellspacing="0" cellspacing="0" border="0" class="en">
                <%if(sale_check.equals("1")){%>
                    <tr >
                        <td><font style="font-size:12px;">操作已经完成!</font></td>
                    </tr>
                <%}else{%>
                    <tr >
                        <td><font style="font-size:12px;">卡号：<%=card_num%>,还未销售，不能充值!</font></td>
                    </tr>                
                <%}%>
                </table>
                <br>
            </td>
        </tr>
    </table>
</td>
</tr>

</table>
</center>
</body>
</html>







