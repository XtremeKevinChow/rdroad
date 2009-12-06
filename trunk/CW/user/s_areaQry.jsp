<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>佰明会员关系管理系统</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
    if(document.forms[0].city.value==""&&trim(document.forms[0].postcode.value)=="") {
        alert("请选择省市或邮编");
        return false;
    }
    return true;
}
function view(code) {
    document.forms[0].action="s_area.do?type=view&areacode=" + code;
    document.forms[0].submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">当前位置</font></b><font color="838383"> : </font><font color="838383">基础数据</font><font color="838383"> 
      		-&gt; </font><font color="838383">直送区域维护</font>
      	</td>
   </tr>
</table>

<html:form  action="/s_area.do?type=list" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>省份：&nbsp;&nbsp;
		<html:select property="province" onchange="document.forms[0].submit();">
		    <option value="">请选择</option>
			<html:optionsCollection property="provs" />
		</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;城市：&nbsp;&nbsp;
		<html:select property="city">
		    <option value="">请选择</option>
			<html:optionsCollection property="citys" />
		</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		邮编前4位:&nbsp;&nbsp;<html:text property="postcode" maxlength="4"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value=" 查 询 " >
		</td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="95%"  cellspacing="1" border="0"  noWrap >
	<tr class="oraTableRowHeader" noWrap>
		<td width="20%">编号</td>
		<td width="30%">区域</td>
		<td width="30%">邮编前4位</td>
		<td width="20%">是否直送</td>
	</tr>
	<% int i=0; %>
  <logic:iterate id="area" name="list" >
	<tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> >
		<td ><a href="#" onclick="view(<bean:write name="area" property="areacode"/>);"><bean:write name="area" property="areacode" format='#'/></a></td>	
		<td ><bean:write name="area" property="areaname" /></td>
		<td ><bean:write name="area" property="postcode" /></td>
		<td >
		<logic:equal name="area" property="is_express" value="1">
			直送
		</logic:equal>
		<logic:notEqual name="area" property="is_express" value="1">
			不可直送
		</logic:notEqual>
		</td>		
    </tr>
 <% i++; %>
</logic:iterate>
</table>
  </td></tr>
</table>
<TABLE align="center">
</TABLE>
</body>
</html>
