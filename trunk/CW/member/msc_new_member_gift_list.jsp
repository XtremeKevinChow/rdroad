<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title></title>
<SCRIPT LANGUAGE="JavaScript">
<!--
function addGifts() {
	document.forms[0].action = "/member/memberSelectRecruitProduct.do";
	document.forms[0].submit();
	
}
function delete_f(id) {
	window.location.href="/member/memberRemoveRecruitProduct.do?deletedId="+id;
}
function clearGifts() {
	window.location.href="/member/memberClearRecruitProduct.do";
}
//-->
</SCRIPT>
</head>
<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0">


<br>
<form name="recruitActityForm" method="post" action="">

<bean:define name="recruit" property="allRecruitGifts" id="allGifts"/><!-- ������ļ� -->
<bean:define name="recruit" property="seletedRecruitGifs" id="selectedGifts" /><!-- �Ѿ�ѡ�����Ʒ -->
<bean:define name="allGifts" id="section" property="sectionsList" type="java.util.Collection"/><!-- ������ -->

<table align="center" cellspacing=1 cellpadding=3 width="98%"  border=0 id="">
  <tr> 
    <td colspan="" align="left"> 
      <B>��ѡ�������Ʒ</B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><%=((String)request.getAttribute("LOGIC MESSAGE"))==null?"":(String)request.getAttribute("LOGIC MESSAGE")%></font>
    </td>
	<td colspan="" align="right"> 
      <input type="button" name="clearBtn" value="�����Ʒ" onclick="clearGifts();">
    </td>
  </tr>
</table>
<table align="center" cellspacing=1 cellpadding=3 width="98%"  border=0 id="">
  <tr>
	<td align="left" bgcolor="DFF4F4" colspan="2">&nbsp;<b>��ѡ��Ʒ</b></td>
  <tr>
  <logic:iterate id="selectedGifts" name="selectedGifts">
	<tr>
		<td align="right"><input type="button" value="ɾ��"  name="deleteBtn" onclick="delete_f(<bean:write name='selectedGifts' property='id'/>);"></td>
		<td align="left" width="">
			<input type="hidden" name="selectedId" value="<bean:write name='selectedGifts' property='id'/>"><bean:write name="selectedGifts" property="itemCode"/>&nbsp;<font color="blue"><bean:write name="selectedGifts" property="itemName"/></font>����ֵ<font color='#177E12'><b><u><bean:write name="selectedGifts" property="price" format="#0.00"/></u></b></font>Ԫ��
			
		</td>
		
	</tr>
	</logic:iterate>			

  <tr> 
    <td bgcolor="" colspan="2" align="right"> 
      <input type="button" name="BtnAddGift" value="ѡ����Ʒ" onClick="addGifts()">&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
  
  <logic:iterate id="sectionList" name="section"> 
	<bean:define name="sectionList" id="product" property="productsList" type="java.util.Collection"/><!-- ���в�Ʒ -->
	<tr> 
		<td bgcolor="DFF4F4" colspan="2" valign="top">&nbsp;<b>[<bean:write name="sectionList" property="name"/>]������ѡ&nbsp;<font color="#177E12"><bean:write name="sectionList" property="minGoods" format="#0"/></font>&nbsp;�֣����ѡ&nbsp;<font color="#177E12"><bean:write name="sectionList" property="maxGoods" format="#0"/></font>&nbsp;�ָ����еĲ�Ʒ</b></td>
	</tr>
	<logic:iterate id="productsList" name="product"> 
	<tr> 
		<td width=20 valign="top"> 
		  <input type="checkbox" <logic:equal name="productsList" property="disabled" value="1">disabled</logic:equal> <logic:equal name="productsList" property="checked" value="1">checked</logic:equal> name="product_<bean:write name="sectionList" property="id" format="#0"/>" value="<bean:write name="productsList" property="id" format="#0"/>"> </td>
		<td><bean:write name="productsList" property="itemCode"/><font color="#CE5D18">&nbsp;<bean:write name="productsList" property="itemName"/></font> 
		  ����<font color='#177E12'><b><u><bean:write name="productsList" property="price" format="#0.00"/></u></b></font>Ԫ��</td>
	</tr>
	</logic:iterate>
  </logic:iterate>


  <tr> 
    <td colspan="2" align="right"> 
      <input type="button" name="BtnAddGift" value="ѡ����Ʒ" onClick="addGifts()">&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
  <tr> 
    <td colspan="2" align="center"> 
      <input type="button" name="closeBtn" value="�ر�" onClick="window.close();">&nbsp;&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
</table>
</form>    
</body>

</html>