<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.user.entity.TableColInfo,org.apache.struts.action.DynaActionForm,java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<script language="javascript">
function f_list() {
	document.forms[0].op_type.value="list";
	document.forms[0].submit();
}

function f_insert() {
	document.forms[0].op_type.value="insert";
	document.forms[0].action="autoConfig.do?type=show";
	document.forms[0].submit();
}
function f_update() {
	document.forms[0].op_type.value="update";
	document.forms[0].action="autoConfig.do?type=show";
	document.forms[0].submit();
}
function f_delete() {
	if (confirm("确实要删除该条数据吗？")) {
		document.forms[0].op_type.value="delete";
		document.forms[0].action="autoConfig.do?type=delete";
		document.forms[0].submit();
	}
}
</script>

</head>

<body>
<html:form action="/autoConfig.do?type=list">
<html:hidden property="op_type"/>
<table>
<tr>
<td>
<html:select property="table_select">
<html:optionsCollection property="table_list"/>
</html:select>
<input type="button" value=" 确 定 " onclick="f_list()">
</td>
</tr>
</table>


<logic:notEmpty name="autoConfigForm" property="table_select">
<table align="center" width="100%" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
<tr bgcolor="#30C0C0">
<%
DynaActionForm fm = (DynaActionForm) request.getAttribute("autoConfigForm");
LinkedHashMap hm_col = (LinkedHashMap) fm.get("column_info");
LinkedHashMap hm_data = (LinkedHashMap) fm.get("column_data");
int j =0;
// 显示列信息
Iterator it = hm_col.keySet().iterator();
while(it.hasNext()) {
	TableColInfo col = (TableColInfo) hm_col.get(it.next());
	out.println("<td>" + col.getCol_name() + "</td>");
	if ( col.isIs_pk()) {
		out.println("<input type=hidden name=\"" + col.getCol_code() + "\">");
		j = ((ArrayList)hm_data.get(col.getCol_code())).size();
	}
}
out.println("<td><input type=button value=\"新增\" onclick=\"f_insert()\"></td></tr>");

// 显示数据信息

for(int i=0; i<j; i++) {
	StringBuffer buf = new StringBuffer();
	out.println("<tr>");
	it = hm_col.keySet().iterator();
	while(it.hasNext()) {
		String col_code = (String)it.next();
		ArrayList data = (ArrayList) hm_data.get(col_code);
		String str = (String)data.get(i);
		if (str == null) {
			str = "&nbsp";
		}
		out.println("<td>" + str + "</td>");
		TableColInfo col = (TableColInfo) hm_col.get(col_code);
		if (col.isIs_pk()) {
			buf.append("document.forms[0].").append(col_code).append(".value = '").append(data.get(i)).append("';");
		}
	}
	
	out.println("<td><input type=button value=\"编辑\" onclick=\"" + buf.toString()+ "f_update();\">");
	out.println("<input type=button value=\"删除\" onclick=\""+ buf.toString()+"f_delete();\"></td></tr>");
}

/* 显示pagecontext属性
Enumeration en= pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE );
while (en.hasMoreElements()) {
	out.println(en.nextElement() + "</p>");
}
out.println("---------------------------");
en= pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE );
while (en.hasMoreElements()) {
	out.println(en.nextElement() + "</p>");
}
out.println("---------------------------");
en= pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE  );
while (en.hasMoreElements()) {
	out.println(en.nextElement() + "</p>");
}
out.println("---------------------------");
en= pageContext.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE );
while (en.hasMoreElements()) {
	out.println(en.nextElement() + "</p>");
}*/
%>
</table>
</logic:notEmpty>
</html:form>

</body>
</html>