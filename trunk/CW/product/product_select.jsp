<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.magic.crm.common.CommonPageUtil"%>
<%@ page import="com.magic.crm.product.Constants"%>
<%@ page import="com.magic.crm.product.entity.Product"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    response.setHeader("expires","0");
    response.setHeader("Cache-Control", "no-store"); //http1.1
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache"); //http1.0
%>
<html>
<head>
<title>������Ա��ϵ����ϵͳ</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
<script language="JavaScript" src="../script/default.js"></script>
<script language="javascript">
function checkInput(){
	var form = document.forms[0];
	return true;
}

function selectItem(){
	var selectedid = getSelectedItem();
	if(selectedid !="") {
		opener.document.forms[0].replaceItemID.value=selectedid;
		
		self.close();
	}
	
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<%@ include file = "../common/page.jsp" %>
<%
String actn = request.getParameter("actn");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      	<td width="21">&nbsp;</td>
    	<td>
			<b><font color="838383">��ǰλ��</font></b><font color="838383"> : </font><font color="838383">�ͻ�����</font><font color="838383"> 
      		-&gt; </font><font color="838383">������ѯ</font><font color="838383"> 
      	</td>
   </tr> 
</table>
<html:form action="/productQuery.do" method="post" onsubmit="return checkInput();">

  <table width="95%" align="center" border=0 cellspacing=1 cellpadding=5 >
    <tr>
		
      <td width="95%">���ţ� 
        <html:text name="product2Form" property="itemCode" size="12"/>
        &nbsp; ��Ʒ���� 
        <html:text name="product2Form" property="name" size="16"/>
        &nbsp; ��Ʒ���ͣ� <select name="itemType">
          <option value="1">ͼ��</option>
          <option value="2">Ӱ��</option>
          <option value="3">����</option>
          <option value="4">��Ϸ/���</option>
          <option value="5">��Ʒ</option>
          <option value="6">����</option>
        </select>
        &nbsp;&nbsp;&nbsp; 
        <input name="query" type="submit" value=" ��ѯ ">
		<input name="actn" type="hidden" value="<%=actn%>">
		</td>
	</tr>
</table>

<table width="95%" border="0"  cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td><hr size="1" noshade color="#FFDB2C" style="border:dashed " width="100%"></td>
	</tr>
</table>
<!--��Ʒ��ѯ�б�begin-->
<%
if (request.getMethod().equals("POST")){
%>
<!--��ҳ����-->
<bean:define id="pageModel" name="<%=Constants.PAGE_MODEL%>" scope="request" type="CommonPageUtil"/>
<%
    //ȡ���ܼ�¼����ҳ����
    int totalNum = 0;
    int curPage = 0;
    int totalPage = 0;
    
    totalNum = pageModel.getRecordCount();
	curPage = pageModel.getPageNo();
	totalPage = pageModel.getTotalPage();
	
%>
<%=turnPagePattern(totalNum,totalPage,curPage)%>
<%=turnPageScript("listFrm")%>
<%if (actn !=null && actn.equals("selectProduct")){%>
  	<table width="95%" align="center" onclick="changeItem()" ondblclick="selectItem()" border=0 cellspacing=1 cellpadding=2 >
<%}else{%>
	<table width="95%" align="center" border=0 cellspacing=1 cellpadding=2 >
<%}%>
    <tr height="26"> 
      <th width="14%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
      <th width="32%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ʒ����</th>
      <th width="11%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >��Ʒ����</th>
      <th width="9%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >������</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >�𿨼�</th>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >���ϼ�</th>
       <%if (actn !=null && actn.equals("selectProduct")){%>
      <th width="10%"  class="OraTableRowHeader" noWrap  noWrap align=middle  >����</th>
      <%}%>
    </tr>
    
    <bean:define id="modelList" name="pageModel" property="modelList"/>
    <logic:iterate id="product" name="modelList">
    <tr style="cursor:hand" id ="<bean:write name="product" property="itemCode" filter="false"/>"> 
      <td class=OraTableCellText noWrap align=middle >
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="itemCode" filter="true"/>
      	<%}else {%>
      	<a href="order_view.html">
      		<bean:write name="product" property="itemCode" filter="true"/>
      	</a>
      	<%}%>
      </td>
      <td class=OraTableCellText noWrap align="left">
      	<%if (actn !=null && actn.equals("selectProduct")){%>
      		<bean:write name="product" property="name" filter="false"/>
      	<%}else{%>
      	<a href="../member/member_desktop.html">
      		<bean:write name="product" property="name" filter="false"/>
      	</a>
      	<%}%>
      </td>
      <td class=OraTableCellText noWrap align=left >ͼ��</td>
      <td class=OraTableCellText noWrap align=right >30</td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="silverPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="godenPrice" filter="false" format="0.00"/></td>
      <td class=OraTableCellText noWrap align=right ><bean:write name="product" property="webPrice" filter="false" format=".00"/></td>
      <%if (actn !=null && !actn.equals("selectProduct")){%>
      <td class=OraTableCellText noWrap align=right >
	  	<input type="button" name="modify" value="�޸�"><input type="button" name="del" value="ɾ��">
	  	
	  </td>
	  <%}%>
    </tr>
    </logic:iterate>
    
  </table>
<%
}
%>
</html:form>


</body>
</html>
