<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.util.Constants"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    String id = request.getParameter("id");
    if(id==null) id = (String)request.getAttribute("id");
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function getOpenwinValue(ret){
    document.getElementById("txtItemCode").value = ret;
}
function openWin(strUrl, WinName, nWidth, nHeight){
	if (screen.Width <= nWidth)
		LeftP = 1;
	else
		LeftP = (screen.Width - nWidth)/2-18; 
	TopP = (screen.Height - nHeight)/2-18;
	var feature = "height=" + nHeight + "px,width=" + nWidth + "px,top=" +
		TopP + "px,left=" + LeftP +
		"px,scrollbars=yes,toolbar=no,menubar=no,directories=no,location=no,resizable=no,status=yes";	
	return window.open(strUrl, WinName, feature);
}
function getProduct(para){	
	var owin = openWin("../product/product2Query.do?type=init4order","wins",700,400);
}
function on_add(){
	if(document.getElementById("txtItemCode").value==""){
		alert("���������");
		return ;
	}
	document.forms[0].action = "/deliveryFeeOff.do?type=addItem&id="+<%=id%>;
	document.getElementById("cmdAdd").disabled  = true;
	document.forms[0].submit();
}
function on_disable(obj, itemCode){
	document.forms[0].action = "/deliveryFeeOff.do?type=updateItemStatus&id="+<%=id%>+"&itemCode="+itemCode+"&status=-1";
	obj.disabled  = true;
	document.forms[0].submit();
}
function on_enable(obj, itemCode){
	document.forms[0].action = "/deliveryFeeOff.do?type=updateItemStatus&id="+<%=id%>+"&itemCode="+itemCode+"&status=1";
	obj.disabled  = true;
	document.forms[0].submit();
	event.cancelBubble=true;
	event.returnValue=false;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
          <td width="21">&nbsp;</td>
        <td>
            <font color="838383"><b>��ǰλ��</b></font><font color="838383"> : </font><font color="838383">��������</font><font color="838383">
              -&gt; </font><font color="838383">�ض���Ʒ�ⷢ�ͷ�</font><font color="838383">
              -&gt; </font><font color="838383">��Ʒ</font>
          </td>
   </tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>

<html:form  action="/deliveryFeeOff.do?type=itemList" method="post">
<table align="center" border=0 cellspacing=1 cellpadding=3 bgcolor="#000000" style="width:700px;">
    <tr>
        <td bgcolor="#FFFFFF" colspan="6">
                                ѡ����Ʒ��
            <input type="text" id="txtItemCode" name="txtItemCode" style="width:90px;" onkeydown="if(event.keyCode == 13) on_add();"></input>
            <a href="javascript:getProduct();">
                <img src="../images/icon_lookup.gif" name ="findcate" width="22" height="23" border=0 align="top">
            </a>
            <input type="button" value="���" onclick='on_add();' id="cmdAdd" />
            &nbsp;
            <input type="button" value="����" onclick='window.location.href="/deliveryFeeOff.do?type=showDfoList";' />
        </td>
    </tr>
	<tr height="26">
		<th width="100px" class="OraTableRowHeader" noWrap >��Ʒ����</th>
		<th width="130px" class="OraTableRowHeader" noWrap >��Ʒ����</th>
		<th class="OraTableRowHeader" noWrap style="min-width:250px;">��Ʒ����</th>
		<th width="50px" class="OraTableRowHeader" noWrap >״̬</th>
		<th width="60px" class="OraTableRowHeader" noWrap >����</th>
	</tr>
<logic:iterate name="list" id="list">
	<tr> 
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="itemtype_name"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="item_code"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="item_name"/></td>
		<td bgcolor="#FFFFFF" <logic:equal name="list" property="status" value="-1">style="color:#999;"</logic:equal>><bean:write name="list" property="status_name"/></td>
		<td bgcolor="#FFFFFF" >
		    <logic:equal name="list" property="status" value="-1">
		        <input type="button" value="����" onclick='on_enable(this,"<bean:write name="list" property="item_code"/>");' />
		    </logic:equal>
		    <logic:equal name="list" property="status" value="1">
		        <input type="button" value="����" onclick='on_disable(this,"<bean:write name="list" property="item_code"/>");' />
		    </logic:equal>
        </td>
	</tr>
</logic:iterate>
</table>
</html:form>
</body>
</html>