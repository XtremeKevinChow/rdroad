<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>������Ա��ϵ����ϵͳ</title>
<script language="JavaScript" src="utils.js"></script>
<script src="calendar.js"></script>
<script src="go_top.js"></script>
<script language="JavaScript" src="common.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit(){
	if (document.forms[0].pro_no.value==""&&document.forms[0].providerName.value=="")
	{
		alert("�������ѯ����!");
		return;
	}
	document.forms[0].offset.value="0";
	document.forms[0].queryBtn.disabled="true";
	document.forms[0].submit();
}
</script>

<script language="Javascript"> 
//��������Ҽ���Ctrl+N��Shift+F10��F5ˢ�¡��˸�� 
//����F1���� 
function window.onhelp() 
{ 
return false 
} 
function KeyDown() 
{ 
//alert(event.keyCode); 
//���� Alt+ ����� �� ���� Alt+ ����� �� 
if ((window.event.altKey)&&((window.event.keyCode==37)||(window.event.keyCode==39))) 
{ 
//alert("��׼��ʹ��ALT+�����ǰ���������ҳ��"); 
event.returnValue=false; 
} 
//�����˸�ɾ����,���� F5 ˢ�¼�,Ctrl + R 
if ((event.keyCode==116)||(event.ctrlKey && event.keyCode==82)) 
{ 
event.keyCode=0; 
event.returnValue=false; 
} 

//���� Ctrl+n 
if ((event.ctrlKey)&&(event.keyCode==78)) 
{ 
event.returnValue=false; 
} 

//���� shift+F10 
if ((event.shiftKey)&&(event.keyCode==121)) 
{ 
event.returnValue=false; 
} 

//���� shift ���������¿�һ��ҳ 
if (window.event.srcElement.tagName == "A" && window.event.shiftKey) 
{ 
window.event.returnValue = false; 
} 

//����Alt+F4 
if ((window.event.altKey)&&(window.event.keyCode==115)) 
{ 
window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px"); 
return false; 
} 

//����Ctrl+A 
if((event.ctrlKey)&&(event.keyCode==65)) 
{ 
return false; 
} 

//����Ctrl+C 
if((event.ctrlKey)&&(event.keyCode==67)) 
{ 
return false; 
} 

} 
function load() {
	document.onkeydown=KeyDown;
}
</script> 

</head>
<body bgcolor="#FFFFFF" text="#000000" oncontextmenu=self.event.returnValue=false onload="load();">

<br>

<table width = "100%" class="OraBGAccentDark" cellpadding = "0" cellspacing = "1">
  <tr>
    <td width = "100%" class="OraBGAccentLight">
      <table>
        <tr>
			<html:form  action="/prvidersQuery.do" method="post"> 
			<bean:define name="providerForm" property="pager" id="pager"/>
			<html:hidden name="pager" property="offset"/>
            <td NoWrap width="50%" valign="left"> &nbsp;&nbsp;&nbsp;
            ���&nbsp;<html:text property="pro_no" size="10" />&nbsp;&nbsp;&nbsp;
            ����&nbsp;<html:text property="providerName" size="10" />
             <input type="button" class="button5" name="queryBtn" value="��ѯ" onclick="querySubmit();"></td>
            <td NoWrap class="OraPromptText" width="25%" align="left" valign="middle"><img border=0 src="../crmjsp/images/prompt.gif" >&nbsp;<a href="../product/prvidersAdd.do?type=add"><font class=OraChicletText>���ӹ�Ӧ��</font></td>
          </html:form>
				</tr>
      </table>
    </td>
  </tr>
</table>

<table width="100.0%" border=0 cellspacing=1 cellpadding=5 >
  <tr>
    <td><span class="OraHeader">��Ӧ��ά��</span>
      <table width="100%" border=0 cellspacing=0 cellpadding=0 background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
        <tr background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1">
          <td height="1" width=100% background="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1"><img src="./images/headerlinepixel_onwhite.gif" HEIGHT="1" WIDTH="1" ></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
		<td>
			<logic:present name="pager">
			<bean:write name="pager" property="pageNavigation" filter="false"/>
			</logic:present>
		</td>
	</tr>
</table>
<table width="100.0%" border=0 cellspacing=1 cellpadding=5>
  <tr>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>���</th>
    <th width="34%" class=OraTableColumnHeader noWrap align=middle>��Ӧ������</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>ʡ��</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>����</th>
    <th width="8%" class=OraTableColumnHeader noWrap align=middle>�ʱ�</th>
    <th width="8%" class=OraTableColumnHeader noWrap align=middle>˰��</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>��Ʊ����</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>������ʽ</th>
  </tr>
    <logic:iterate id="providerForm" name="allProviders">	
  <tr>
    <td class=OraTableCellText noWrap align=left ><a href="../crmjsp/providers_detail.jsp?doc_type=1530&doc_id=<bean:write name='providerForm' property='pro_no'/>"><bean:write name="providerForm" property="pro_no"/></a></td>
    <td class=OraTableCellText noWrap align=left ><bean:write name="providerForm" property="providerName"/></td>
    <td class=OraTableCellText noWrap align=left ><bean:write name="providerForm" property="province"/></td>
    <td class=OraTableCellText noWrap align=left ><bean:write name="providerForm" property="city"/></td>
    <td class=OraTableCellText noWrap align=left ><bean:write name="providerForm" property="providerZip"/></td>
    <td class=OraTableCellText noWrap align=right ><bean:write name="providerForm" property="taxRate" format="#0.00"/></td>	
    <td class=OraTableCellText noWrap align=left >
    <logic:equal name="providerForm" property="invocieType" value="A">��ͨ��Ʊ</logic:equal>
    <logic:equal name="providerForm" property="invocieType" value="B">��Ʊ</logic:equal>

    </td>
    <td class=OraTableCellText noWrap align=left ><bean:write name="providerForm" property="cooperate"/></td>
  </tr>

	</logic:iterate>
</table>




</body>
</html>