<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<title>佰明会员关系管理系统</title>
<script language="JavaScript" src="utils.js"></script>
<script src="calendar.js"></script>
<script src="go_top.js"></script>
<script language="JavaScript" src="common.js"></script>
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit(){
	if (document.forms[0].pro_no.value==""&&document.forms[0].providerName.value=="")
	{
		alert("请输入查询条件!");
		return;
	}
	document.forms[0].offset.value="0";
	document.forms[0].queryBtn.disabled="true";
	document.forms[0].submit();
}
</script>

<script language="Javascript"> 
//屏蔽鼠标右键、Ctrl+N、Shift+F10、F5刷新、退格键 
//屏蔽F1帮助 
function window.onhelp() 
{ 
return false 
} 
function KeyDown() 
{ 
//alert(event.keyCode); 
//屏蔽 Alt+ 方向键 ← 屏蔽 Alt+ 方向键 → 
if ((window.event.altKey)&&((window.event.keyCode==37)||(window.event.keyCode==39))) 
{ 
//alert("不准你使用ALT+方向键前进或后退网页！"); 
event.returnValue=false; 
} 
//屏蔽退格删除键,屏蔽 F5 刷新键,Ctrl + R 
if ((event.keyCode==116)||(event.ctrlKey && event.keyCode==82)) 
{ 
event.keyCode=0; 
event.returnValue=false; 
} 

//屏蔽 Ctrl+n 
if ((event.ctrlKey)&&(event.keyCode==78)) 
{ 
event.returnValue=false; 
} 

//屏蔽 shift+F10 
if ((event.shiftKey)&&(event.keyCode==121)) 
{ 
event.returnValue=false; 
} 

//屏蔽 shift 加鼠标左键新开一网页 
if (window.event.srcElement.tagName == "A" && window.event.shiftKey) 
{ 
window.event.returnValue = false; 
} 

//屏蔽Alt+F4 
if ((window.event.altKey)&&(window.event.keyCode==115)) 
{ 
window.showModelessDialog("about:blank","","dialogWidth:1px;dialogheight:1px"); 
return false; 
} 

//屏蔽Ctrl+A 
if((event.ctrlKey)&&(event.keyCode==65)) 
{ 
return false; 
} 

//屏蔽Ctrl+C 
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
            编号&nbsp;<html:text property="pro_no" size="10" />&nbsp;&nbsp;&nbsp;
            名称&nbsp;<html:text property="providerName" size="10" />
             <input type="button" class="button5" name="queryBtn" value="查询" onclick="querySubmit();"></td>
            <td NoWrap class="OraPromptText" width="25%" align="left" valign="middle"><img border=0 src="../crmjsp/images/prompt.gif" >&nbsp;<a href="../product/prvidersAdd.do?type=add"><font class=OraChicletText>增加供应商</font></td>
          </html:form>
				</tr>
      </table>
    </td>
  </tr>
</table>

<table width="100.0%" border=0 cellspacing=1 cellpadding=5 >
  <tr>
    <td><span class="OraHeader">供应商维护</span>
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
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>编号</th>
    <th width="34%" class=OraTableColumnHeader noWrap align=middle>供应商名称</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>省份</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>城市</th>
    <th width="8%" class=OraTableColumnHeader noWrap align=middle>邮编</th>
    <th width="8%" class=OraTableColumnHeader noWrap align=middle>税率</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>发票类型</th>
    <th width="10%" class=OraTableColumnHeader noWrap align=middle>合作方式</th>
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
    <logic:equal name="providerForm" property="invocieType" value="A">普通发票</logic:equal>
    <logic:equal name="providerForm" property="invocieType" value="B">增票</logic:equal>

    </td>
    <td class=OraTableCellText noWrap align=left ><bean:write name="providerForm" property="cooperate"/></td>
  </tr>

	</logic:iterate>
</table>




</body>
</html>