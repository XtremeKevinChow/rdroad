<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript">
function querySubmit() {
    if(document.forms[0].city.value==""&&trim(document.forms[0].postcode.value)=="") {
        alert("��ѡ��ʡ�л��ʱ�");
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
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">��������</font><font color="838383"> 
      		-&gt; </font><font color="838383">ֱ������ά��</font>
      	</td>
   </tr>
</table>

<html:form  action="/s_area.do?type=list" method="post" onsubmit="return querySubmit();">
<table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
	<tr>
		<td>ʡ�ݣ�&nbsp;&nbsp;
		<html:select property="province" onchange="document.forms[0].submit();">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="provs" />
		</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;���У�&nbsp;&nbsp;
		<html:select property="city">
		    <option value="">��ѡ��</option>
			<html:optionsCollection property="citys" />
		</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		�ʱ�ǰ4λ:&nbsp;&nbsp;<html:text property="postcode" maxlength="4"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value=" �� ѯ " >
		</td>
		
	</tr>
</table>
<input type="hidden" name="isquery" value="1">
</html:form>
<table width="95%" align="center" cellspacing="1" border="0"  >
<tr><td>
<table width="95%"  cellspacing="1" border="0"  noWrap >
	<tr class="oraTableRowHeader" noWrap>
		<td width="20%">���</td>
		<td width="30%">����</td>
		<td width="30%">�ʱ�ǰ4λ</td>
		<td width="20%">�Ƿ�ֱ��</td>
	</tr>
	<% int i=0; %>
  <logic:iterate id="area" name="list" >
	<tr  <% if(i%2==1) { %>class=OraTableCellText<% } %> >
		<td ><a href="#" onclick="view(<bean:write name="area" property="areacode"/>);"><bean:write name="area" property="areacode" format='#'/></a></td>	
		<td ><bean:write name="area" property="areaname" /></td>
		<td ><bean:write name="area" property="postcode" /></td>
		<td >
		<logic:equal name="area" property="is_express" value="1">
			ֱ��
		</logic:equal>
		<logic:notEqual name="area" property="is_express" value="1">
			����ֱ��
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
