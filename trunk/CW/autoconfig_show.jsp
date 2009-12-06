<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.magic.crm.user.entity.TableColInfo,org.apache.struts.action.DynaActionForm,java.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<script language="JavaScript" src="dateselect.js" type="text/javascript"></script>
<script language="javascript">
function f_verify() {
	alert("yes");
	return true;
}

function f_submit() {
	
	if (document.forms[0].op_type.value=="insert") {
		document.forms[0].action="autoConfig.do?type=insert";
	} else if (document.forms[0].op_type.value=="update") {
		 
		document.forms[0].action="autoConfig.do?type=update"; 
	} else {
		return;
	} 
	document.forms[0].submit();
} 	

</script>
</head>

<body>
<html:form action="/autoConfig.do?type=insert" onsubmit="return f_verify()">
<html:hidden property="op_type"/>
<table>
<tr>
<td>
<bean:write name="autoConfigForm" property="table_select"/>
<html:hidden property="table_select"/>
</td>
</tr>
</table>

<table align="center" width="100%" cellspacing="0" border="1" bordercolordark="#ffffff" bordercolorlight="#183648" >
<% //动态显示新增或修改页面
DynaActionForm fm = (DynaActionForm) request.getAttribute("autoConfigForm");
LinkedHashMap hm_col = (LinkedHashMap) fm.get("column_info");
String op_type = (String) fm.get("op_type");
LinkedHashMap hm_data = (LinkedHashMap) fm.get("column_data");
if (hm_col != null) {
	Iterator it = hm_col.keySet().iterator();
	while(it.hasNext()) {
		out.println("<tr><td bgcolor=\"#30C0C0\">");
		TableColInfo col = (TableColInfo) hm_col.get(it.next());
		out.println(col.getCol_name());
		if (col.isIs_pk()) {
			out.println("<font color=red>(*)</font>");
		}
		
		out.println("</td><td>");
		
		// 列的类型不同,相应的显示也有所不同，日期型的显示包含日历，其他的为普通输入框
		String str = "";
		if (op_type.equals("update")) {
			str = (String) hm_data.get(col.getCol_code());
		}
		if (col.getData_type().equals("DATE")) {
			out.println("<input type=text name=\"" + col.getCol_code() + "\" size=10 value=\"" + str + "\" onclick =\"show_calendar(this)\" > YYYY-MM-DD");
			
		} else {
			
			int length = col.getData_length(),size=30;
			int precision = col.getData_precision();
			int scale = col.getData_scale();
			if (precision > -1) {
				length = precision+1+scale+1;
			}
			if (length <30) {
				size = length;
			}
			
			out.println("<input type=text name=\"" + col.getCol_code() + "\" value=\"" + str + "\" maxlength=" 
				+ String.valueOf(length)  + " size=" + String.valueOf(size) +" >");
		}
		if (!col.getIs_nullable()) {
			out.println("<font color=red>*</font>");
		}
		out.println("</td>");
		out.println("</tr>");
	}
}
%>
</table>

<input type=button value=" 确定 " onclick="f_submit()">
</html:form>
</body>
</html>