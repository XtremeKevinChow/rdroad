<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page import="com.magic.crm.util.*,com.magic.crm.util.DBManager,com.magic.utils.Arith"%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="JavaScript" src="../script/function.js"></script>
<script language="JavaScript" src="../crmjsp/common.js"></script>
<script language="JavaScript">

function add(){
       document.forms[0].action="crush_card_mst_add_ok.jsp";
       document.forms[0].submit();
       document.forms[0].addexecl.disabled= true;
}
function checkvalid(obj) {
    if (!is_integer(obj.value)||obj.value==""||parseInt(obj.value)< 0||parseInt(obj.value)>10000) {
        alert("�����������������Ҳ��ܳ���1��");
        obj.select();
    }
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ʻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">��ֵ������</font>
      	</td>
   </tr>
</table>
<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td>Ϊ�˱�֤���ݵ�׼ȷ�ԣ���ע��������衣<br>
		<font color="red">
		1�����Ҫ������ֵ�������Ƚ϶�,����Ҫ��ʱ��ϳ�(���Ҫ15��������),�����ĵȺ�,ֱ��ϵͳ��ʾ�㱣��execl��<br>
		2��δ������ʾ"����execl"��ʱ,��Ҫ�ر�ҳ�档<br></font>
		3��������Ǳ���execl,����ϵͳ����Ա��ϵ��
		</td>
	</tr>
</table>
<%
    String card_lot = request.getParameter("lot_no");
    Connection conn = null;
    PreparedStatement pstmt= null;
    ResultSet rs = null;
    try {
        conn = DBManager.getConnection();
        String sql = "select * from crush_card_value ";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
%>
<form>
<table width="60%"  align="center" cellspacing="1" border="0"  noWrap >
	<tr  noWrap>
		<td class="oraTableRowHeader" >����</td>
		<td ><%= card_lot %></td>
	</tr>
	<% while (rs.next()) {
	    String id = rs.getString("id");
	    String money = rs.getString("money");
	%>
	<tr>
	  <td class="oraTableRowHeader"> ��ֵ: <%= money%>
      </td>
      <td>
      <input type=hidden name="type_id" value="<%= id%>">
      <input type=hidden name="money" value="<%= money%>">
      <input type=hidden name="lot_no" value="<%= card_lot%>">
      <input type=text name="quantity" value="0" onblur="checkvalid(this);">
      </td>
    </tr>
    <%}%>
</table>

<table width="60%" border="0"  cellpadding="0" cellspacing="0" align="center">
	
	<tr>
		<td align="center"><input type="button" name="addexecl" value="����Execl" onclick="add()"></td>
	</tr>

</table>
</form>
</body>
</html>
<%
} finally {
if (rs != null)
	try {
		rs.close();
	} catch (Exception e) {}			
if (pstmt != null)
	try {
		pstmt.close();
	} catch (Exception e) {}
 try {
	 conn.close();
 	} catch(SQLException sqe) {}

}
%>
