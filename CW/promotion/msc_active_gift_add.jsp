<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%
String pid=request.getParameter("pid");

%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../crmjsp/go_top.js"></script>
<script language="javascript" src="../script/default.js"></script>
<script language="javascript">
function getProduct(){
	openWin("../product/productQuery.do?actn=selectProduct&ifproduct=1&isreport=1","2005",700,400);
}
function f_checkData() {
        if(document.form1.itemCode.value==""){
           alert("����д����");
           document.form1.itemCode.select();	
           return false;
        }
        if(document.form1.sell_type.value==""){
           alert("����д���۷�ʽ");
           document.form1.sell_type.select();	
           return false;
        }        
	if(isNaN(document.form1.common_price.value)||document.form1.common_price.value==""){
	alert('�����۸�ֻ��Ϊ����!');
	document.form1.common_price.select();
	return false;
	}
	             	    
		
 	document.form1.input.disabled = true;
}
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="21">&nbsp;</td>
    <td> <font color="#838383"><b>��ǰλ��</b> : �г����� -&gt; ������ļ���Ʒ</font></td>
  </tr>
</table>
<br>
<form name="form1" action="msc_active_gift_add_ok.jsp" method="post" onsubmit="return f_checkData();">
 <table width="95%" border=0 cellspacing=1 cellpadding=1  align="center" >
  
 <tr> 
    <td class="OraTableRowHeader" noWrap  >����<font color=red>*</font></td>
    <td>
<input type="hidden" id="itemID" name="itemID" value=""><input id="itemCode" name="itemCode" value="" readonly>
				<a href="javascript:getProduct();">
     				<img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
     			</a>
    
    </td>
 </tr>  

 <tr> 
    <td class="OraTableRowHeader" noWrap >���۷�ʽ<font color=red>*</font></td>
    <td>
	<select name="sell_type" > 
	<option value="">��ѡ��...</option>
	<option value="3" >��������</option>
	<option value="7" >ע������</option>
	<option value="2" >��������</option>
	<option value="0" selected>��������</option>
	<option value="14" >����</option>
	<option value="1" >�ֿ�����</option>
	<option value="6" >���ֻ���</option>
	<option value="10" >�ιο�</option>
	<option value="5" >��������Ʒ</option>
	<option value="13" >�˹�����Ʒ</option>
	<option value="9" >���ϻ��Ʒ</option>
	<option value="11" >��͸����</option>
	<option value="4" >��Ʒ��Ʒ</option>
	<option value="8" >��Ա��</option>
	<option value="12" >ת����Ʒ</option>
</select>      
    </td>
 </tr>

  <tr> 
    <td class="OraTableRowHeader" noWrap  >������<font color=red>*</font></td>
    <td><input name="common_price" value=""></td>
 </tr> 
  <tr align="center" valign="middle"> 
    <td height="42" colspan=2> 
      <input  type="submit" name="input" class="button2" value=" �� �� " > 
      
  </tr> 
</table>
<input type="hidden" name="pid" value="<%=pid%>">
</form>



</body>
</html>

