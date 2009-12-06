<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.magic.crm.common.Constants"%>
<%
    String MerId = "808080090301003";
    String OrdId = (String)request.getAttribute("OrdId");
    String TransAmt = (String)request.getAttribute("TransAmt");
    String TransDate = (String)request.getAttribute("TransDate");
    String TransType ="0001";
    String ChkValue = (String)request.getAttribute("ChkValue");
%>

<html>
<head>
<title>操作信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<body oncontextmenu="return false">
<form action="http://payment.chinapay.com:8081/pay/TransGet" METHOD=POST target="_blank">
<input type=hidden name="MerId" value="<%=MerId%>" >
<input type=hidden name="OrdId" value="<%=OrdId%>" >
<input type=hidden name="TransAmt" value="<%=TransAmt%>">
<input type=hidden name="CuryId" value="156">
<input type=hidden name="TransDate" value="<%=TransDate%>">
<input type=hidden name="TransType" value=<%=TransType%> >

<input type=hidden name="Version" value="20040916">
<input type=hidden name="BgRetUrl" value="http://www.XX.com/pay/payback1.php">
<input type=hidden name="PageRetUrl" value="http://www.XX.com/pay/payback2.php">
<input type=hidden name="GateId" value="">
<input type=hidden name="Priv1" value="<%=OrdId%>">

<input type=hidden name="ChkValue" value=<%=ChkValue%>>
</form>
<br>
<br>
<table width="50%" border="0" cellspacing="1" cellpadding="5" align="center">


   <tr>
      <th scope="col" width="5%" class=OraTableRowHeader >系统提示信息：</th>
   </tr>
  <tr>
            <td class=OraTableCellText noWrap align=middle >
			<%=(String)request.getAttribute(Constants.LOGIC_MESSAGE)%>
			</td>
  </tr>

<tr>
<td class="OraBGAccentDark" heigth=10 align=middle>
<input type=button value=" 支 付 " onclick="goto_chinapay()">
</td>
</tr>

</table>


</body>
</html>
